package com.mylawyer.community.dto;

import lombok.Data;

/**
 * @Author bsnowflake04
 * Date on 2020/3/7  9:13
 */
@Data
public class CommentDTO {
    private String content;
    private Long parentId;
    private Integer type;
}
