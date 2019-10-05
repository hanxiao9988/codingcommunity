package com.hanxiao.codingcommunity.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    Long parentId;
    String content;
    Integer type;



}
