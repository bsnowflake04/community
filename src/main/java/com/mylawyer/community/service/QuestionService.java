package com.mylawyer.community.service;

import com.mylawyer.community.dto.PaginationDTO;
import com.mylawyer.community.dto.QuestionDTO;
import com.mylawyer.community.mapper.QuestionMapper;
import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.Question;
import com.mylawyer.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//数据库后端之间的处理
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO listAll(Integer page, Integer size) {

        Integer totalCount = questionMapper.count();
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
        List<Question> questions = questionMapper.list(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());//通过question.creator找user.avatarurl
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//复制一个类的所有属性
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public PaginationDTO listQuestionsByCreator(Integer creator, Integer page, Integer size) {
        Integer totalCount = questionMapper.countByCreator(creator);
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
        List<Question> questions = questionMapper.listQuestionByCreator(creator, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());//通过question.creator找user.avatarurl
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//复制一个类的所有属性
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }

    public QuestionDTO getQuestionById(Integer id) {
        Question question = questionMapper.getQuestionById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        return questionDTO;
    }
}
