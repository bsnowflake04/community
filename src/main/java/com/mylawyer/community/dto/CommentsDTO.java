package com.mylawyer.community.dto;

import com.mylawyer.community.model.User;
import lombok.Data;

/**
 * @Author bsnowflake04
 * Date on 2020/3/25  10:51
 */
@Data
public class CommentsDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtModified;
    private Long gmtCreate;
    private Integer likeCount;
    private User user;
}
