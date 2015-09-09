package com.xuexibao.teacher.enums;

/**
 * 消息类型枚举类
 */
public enum PushMsgType {

    //互动学习消息类型
    learnTalk(4010);

    private int type;

    PushMsgType(int source) {
        this.type = source;
    }

    public int getValue() {
        return type;
    }
}
