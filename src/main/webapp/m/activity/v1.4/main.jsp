<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="masker-loading" ms-class="hide:initialized"></div>
<div class="page">
    <div id="titileBar" class="activity-title">{{chargetNotice.message}}</div>
    <div class="main-container">
        <div class="form-area">
            <div class="form-control"><input type="number" id="phoneNumber" placeholder="输入学生的学习宝注册账号" /></div>
            <button id="submitBtn" class="btn-submit">提交</button>
        </div>
        <div class="record-area">
            <div class="rec-title">
                <span>提交记录：</span>
                <a href="javascript:;">查看钱包</a>
            </div>
            <div id="recordScroller" class="record-main">
                <div>
                    <ul class="record-list">
                        <li ms-repeat="listRecharge" ms-class="waitting:el.status==1"><span class="date">{{el.createTime | date("MM月dd日HH:mm")}} 提交</span><span class="phone">{{el.studentPhoneNumber}}</span><span class="status" ms-text="el.status==1?'等待学生充值':'已返现到钱包'"></span></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>