package com.mylawyer.community.controller;

import com.mylawyer.community.dto.PaginationDTO;
import com.mylawyer.community.mapper.UserMapper;
import com.mylawyer.community.model.User;
import com.mylawyer.community.service.NotificationService;
import com.mylawyer.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "9") Integer size) {

        User user = null;
       /* Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }*/
        user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return "redirect:/";
        }

        if ("myquestions".equals(action) || "".equals(action)) {
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("section", "myquestions");
            PaginationDTO paginationDTO = questionService.listQuestionsByCreator(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);
        } else if ("replies".equals(action)) {
            model.addAttribute("sectionName", "最新回复");
            model.addAttribute("section", "replies");
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("paginationDTO", paginationDTO);



        }

        return "profile";
    }
}
