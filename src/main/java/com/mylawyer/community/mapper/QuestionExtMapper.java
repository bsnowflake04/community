package com.mylawyer.community.mapper;

import com.mylawyer.community.model.Question;

import java.util.List;

/**
 * @Author bsnowflake04
 * Date on 2020/3/6  13:03
 */
public interface QuestionExtMapper {
    int incView(Question record);
    int incComment(Question record);
    List<Question> selectRelated(Question record);

}
