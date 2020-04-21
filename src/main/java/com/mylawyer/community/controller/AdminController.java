package com.mylawyer.community.controller;

import com.mylawyer.community.dto.OperationDTO;
import com.mylawyer.community.dto.PaginationDTO;
import com.mylawyer.community.dto.ResultDTO;
import com.mylawyer.community.exception.CustomizeErrorCode;
import com.mylawyer.community.model.User;
import com.mylawyer.community.service.QuestionService;
import com.mylawyer.community.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/admin/{action}")
    public String admin(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "10") Integer size) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null || user.getStatus() == 0) {
            return "redirect:/";
        }


        if ("usersAdmin".equals(action) || "".equals(action)) {
            model.addAttribute("sectionName", "用户管理");
            model.addAttribute("section", "usersAdmin");
            PaginationDTO paginationDTO = userService.list(page, size);
            model.addAttribute("paginationDTO", paginationDTO);
        } else if ("questionAdmin".equals(action)) {
            model.addAttribute("sectionName", "内容管理");
            model.addAttribute("section", "questionAdmin");
            PaginationDTO paginationDTO = questionService.listAll(page, size);
            model.addAttribute("paginationDTO", paginationDTO);
        } else if ("adAdmin".equals(action)) {
            model.addAttribute("sectionName", "宣传管理");
            model.addAttribute("section", "adAdmin");

        }

        return "admin";
    }

    @ResponseBody
    @RequestMapping(value = "/admin/{action}", method = RequestMethod.POST)
    public Object post(@RequestBody OperationDTO operationDTO,
                       @PathVariable(name = "action") String action,
                       HttpServletRequest request/*请求session*/){//通过requestbody接受前端json转对象，通过responsebody将obj转json到前端

        if (operationDTO == null || operationDTO.getId() == null ) {
            return ResultDTO.errorOf(CustomizeErrorCode.USER_OR_QUESTION_NOT_EXIST);
        }
        if (StringUtils.isBlank(operationDTO.getOperation())) {
            return ResultDTO.errorOf(CustomizeErrorCode.OPERATION_FAILED);
        }

        if ("alterUser".equals(action)) {
            userService.alterStatus(operationDTO.getId());
            return ResultDTO.okOf();
        } else if ("deleteUser".equals(action)) {
            userService.deleteUser(operationDTO.getId());
            return ResultDTO.okOf();
        } else if ("deleteQuestion".equals(action)) {
            questionService.deleteQuestion(operationDTO.getId());
            return ResultDTO.okOf();
        }

        return "admin";

    }
}
