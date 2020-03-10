package com.mylawyer.community.controller;

import com.mylawyer.community.dto.CommentDTO;
import com.mylawyer.community.dto.ResultDTO;
import com.mylawyer.community.exception.CustomizeErrorCode;
import com.mylawyer.community.mapper.CommentMapper;
import com.mylawyer.community.model.Comment;
import com.mylawyer.community.model.User;
import com.mylawyer.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author bsnowflake04
 * Date on 2020/3/7  9:08
 * 评论功能
 * 1.确定用json进行前后端交互，建立comment表，commentDTO,将前端的dto插入表，用postman进行测试
 * 2.返回json到前端，判断插入条件：未登录，建立resultDTO传输错误信息，判断插入条件：type类型（问题还是评论）->需要questionMapper
 * ->建立commentService封装插入->判断插入条件：问题不存在->在内层应用exception返回错误信息->更改exception包添加code，在内层应用json返回错误信息
 * ->在exceptionhandler中进行两种响应（跳转exception页面和原页面返回json），判断插入条件：type不存在，母评论不存在，问题不存在->comment父id和questionid类型不匹配->
 * 更改question,user,comment表的id，creator,commentator类型为long，在ext里添加累加commentcount的方法->
 *
 */
@Controller
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request/*请求session*/){//通过requestbody接受前端json转对象，通过responsebody将obj转json到前端
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }

        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
}
