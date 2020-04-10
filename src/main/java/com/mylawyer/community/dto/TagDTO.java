package com.mylawyer.community.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author bsnowflake04
 * Date on 2020/4/6  19:44
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
