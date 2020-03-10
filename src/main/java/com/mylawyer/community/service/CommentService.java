package com.mylawyer.community.service;

import com.mylawyer.community.enums.CommentTypeEnum;
import com.mylawyer.community.exception.CustomizeErrorCode;
import com.mylawyer.community.exception.CustomizeException;
import com.mylawyer.community.mapper.CommentMapper;
import com.mylawyer.community.mapper.QuestionExtMapper;
import com.mylawyer.community.mapper.QuestionMapper;
import com.mylawyer.community.model.Comment;
import com.mylawyer.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author bsnowflake04
 * Date on 2020/3/9  10:43
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.COMMENT_TYPE_ERROR);
        }

        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }else{
                commentMapper.insert(comment);
            }
        }else{
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (dbQuestion == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }else{
                commentMapper.insert(comment);

                Question question = new Question();
                question.setCommentCount(1);
                question.setId(dbQuestion.getId());
                questionExtMapper.incComment(question);
            }
        }

    }
}
