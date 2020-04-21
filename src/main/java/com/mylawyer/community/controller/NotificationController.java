package com.mylawyer.community.controller;

import com.mylawyer.community.dto.NotificationDTO;
import com.mylawyer.community.enums.NotificationTypeEnum;
import com.mylawyer.community.model.User;
import com.mylawyer.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;


    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }

        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (notificationDTO.getType() == NotificationTypeEnum.REPLY_QUESTION.getType()
            || notificationDTO.getType() == NotificationTypeEnum.REPLY_COMMENT.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }
        return null;
    }
}
