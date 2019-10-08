package com.hanxiao.codingcommunity.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不换个试试~"),
    TARGET_NOT_FOUND(2002, "未选中任何问题或评论进行回复"),
    NO_LOGIN(2003, "当前操作需要登录，请登陆后重试"),
    SYSTEM_ERROR(2004, "服务器冒烟了，要不稍后再试~"),
    COMMENT_NOT_FOUND(2005, "回复的评论不存在了，要不换个试试~"),
    CONTENT_IS_EMPTY(2006, "亲~输入的内容不能为空哦~"),
    READ_NOTIFICATION_FAIL(2007, "宝贝儿，你这是读别人的信息呢~"),
    NOTIFICATION_NOT_FOUND(2008, "消息飞走了咯~");

    private Integer code;
    private String message;


    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
