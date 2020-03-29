package com.mylawyer.community.controller;

import com.mylawyer.community.dto.CommentsDTO;
import com.mylawyer.community.dto.QuestionDTO;
import com.mylawyer.community.enums.CommentTypeEnum;
import com.mylawyer.community.service.CommentService;
import com.mylawyer.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        QuestionDTO questionDTO = questionService.getQuestionById(id);
        List<CommentsDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        questionService.incView(id);
        model.addAttribute("questionDTO",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
