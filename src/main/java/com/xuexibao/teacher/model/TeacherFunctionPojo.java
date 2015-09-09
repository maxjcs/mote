package com.xuexibao.teacher.model;

/**
 * 作者：付国庆
 * 时间：15/4/16－下午3:35
 * 描述：教师功能模块配置表 teacher_function pojo
 */

public class TeacherFunctionPojo {

    //teacherId
    private String id;
    //每日任务功能模块，1:可用，0:不可用 默认1
    private Integer everydayTask;
    //抢答功能模块，1:可用，0:不可用 默认1
    private Integer feud;
    //互动学习功能模块，1:可用，0:不可用 默认1
    private Integer learnTalk;
    //支付模块，1:可用，0:不可用 默认1
    private Integer pay;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getEverydayTask() {
        return everydayTask;
    }

    public void setEverydayTask(Integer everydayTask) {
        this.everydayTask = everydayTask;
    }

    public Integer getFeud() {
        return feud;
    }

    public void setFeud(Integer feud) {
        this.feud = feud;
    }

    public Integer getLearnTalk() {
        return learnTalk;
    }

    public void setLearnTalk(Integer learnTalk) {
        this.learnTalk = learnTalk;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }
}
