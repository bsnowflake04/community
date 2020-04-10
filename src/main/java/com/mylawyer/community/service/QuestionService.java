package com.mylawyer.community.service;

import com.mylawyer.community.dto.PaginationDTO;
import com.mylawyer.community.dto.QuestionDTO;
import com.mylawyer.community.exception.CustomizeErrorCode;
import com.mylawyer.community.exception.CustomizeException;
import com.mylawyer.community.mapper.QuestionExtMapper;
import com.mylawyer.community.mapper.QuestionMapper;
import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.Question;
import com.mylawyer.community.model.QuestionExample;
import com.mylawyer.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

//数据库后端之间的处理
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void incView(Long id) {
        /*Question question = questionMapper.selectByPrimaryKey(id);
        question.setViewCount(question.getViewCount() + 1);
        int updated = questionMapper.updateByPrimaryKeySelective(question);*/
        Question question = new Question();
        question.setViewCount(1);
        question.setId(id);
        questionExtMapper.incView(question);//处理并发
        /*if (updated != 1){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }*/
    }

    public PaginationDTO listAll(Integer page, Integer size) {

        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        Integer totalPage;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPaginationDTO(totalPage, page);
        Integer offset = size * (page - 1);
//        List<Question> questions = questionMapper.list(offset, size);
        QuestionExample example = new QuestionExample();
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());//通过question.creator找user.avatarurl
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//复制一个类的所有属性
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listQuestionsByCreator(Long creator, Integer page, Integer size) {

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(creator);
        Integer totalCount = (int)questionMapper.countByExample(questionExample);
//        Integer totalCount = questionMapper.countByCreator(questionExample);
        Integer totalPage;//size=5 0  1-4  5  5+
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (totalPage == 0) totalPage = 1;

        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        PaginationDTO paginationDTO = new PaginationDTO();
        paginationDTO.setPaginationDTO(totalPage, page);
        Integer offset = size * (page - 1);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(creator);
        example.setOrderByClause("gmt_create desc");
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(offset, size));
//        List<Question> questions = questionMapper.listQuestionByCreator(creator, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());//通过question.creator找user.avatarurl
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//复制一个类的所有属性
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
//        Question question = questionMapper.getQuestionById(id);

        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void CreateOrUpdate(Question question) {
        if (question.getId() == null) {//发布页面
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        } else {//编辑页面
            question.setGmtModified(System.currentTimeMillis());
            int updated = questionMapper.updateByPrimaryKeySelective(question);
            if (updated != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public List<QuestionDTO> selectedRelated(QuestionDTO questionDTO) {
        if (StringUtils.isBlank(questionDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(questionDTO.getTag(), "；");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexTag);
        List<Question> relatedQuestions = questionExtMapper.selectRelated(question);

        List<QuestionDTO> relatedQuestionDTOs = relatedQuestions.stream().map(q -> {
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;
        }).collect(Collectors.toList());
        return relatedQuestionDTOs;
    }
}
