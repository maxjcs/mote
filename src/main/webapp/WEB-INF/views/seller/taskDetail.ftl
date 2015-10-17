<!DOCTYPE html>
<html>
<head>
<#include "../inc/header.ftl">
</head>
<body class="page-body">
<div class="page-container">
<#assign nav='teacherManage' subNav='teacherList'/>
<#include "../inc/sidebar.ftl">
    <div class="main-content">
        <div class="page-title">
            <div class="hidden-sm hidden-xs sidebar-toggle">
                <a href="javascript:;" data-toggle="sidebar">
                    <i class="fa-bars"></i>
                </a>
            </div>
            <div class="breadcrumb-env">
                <ol class="breadcrumb bc-1">
                    <li><a><i class="fa-home"></i>商家管理---项目管理---${task.title?default('')}</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="form clearfix">
                            <div class="col-md-4">
                              <form method="post" role="form" id="searchForm"
                                  action="${webServer}api/back/seller/taskDetail">
                                  <input type="hidden" name="taskId" value="${task.id}">
                                  <input type="hidden" id="index" name="index" value="${resultVO.index}">
                              </form>    
                                <div class="form-group">
                                    <label class="control-label">模特昵称：</label>
                                    <span class="control-label">${mote.nickname?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">手机号：</label>
                                    <span class="control-label">${mote.phoneNumber?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">性别：</label>
                                    <span class="control-label">
                                         <#if mote.gender?exists>
	                                         <#if mote.gender==1>男</#if>
	                                         <#if mote.gender==0>女</#if>
	                                         <#if mote.gender==3>未知</#if>
	                                     </#if>    
                                    </span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">出生日期：</label>
                                    <span class="control-label">
                                      <#if mote.birdthday?exists>
                                    	 ${mote.birdthday?string("yyyy-MM-dd HH:mm:ss")}
                                      </#if>	
                                    </span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">身高：</label>
                                    <span class="control-label">${mote.heigth?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">体重：</label>
                                    <span class="control-label">${mote.weight?default('')}kg</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">体型：</label>
                                    <span class="control-label">
                                         <#if mote.shape?exists>
	                                         <#if mote.shape==1>偏瘦</#if>
	                                         <#if mote.shape==2>中等</#if>
	                                         <#if mote.shape==3>偏胖</#if>
                                         </#if>
                                    </span>
                                </div>
                              </div>
                               <div class="col-md-5">  
                                <div class="form-group">
                                    <label class="control-label">旺旺：</label>
                                    <span class="control-label">${mote.wangwang?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">支付宝账号：</label>
                                    <span class="control-label">${mote.alipayId?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">支付宝名：</label>
                                    <span class="control-label">${mote.alipayName?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">预计款：</label>
                                    <span class="control-label">${mote.remindFee?default(0)}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">账号状态：</label>
                                    <span class="control-label"><font color='red'>
                                    <#assign status=(mote.status)?default(-1) >
                                    <#if status==1>待审核
                                    <form action="${webServer}api/user/approve" method="post">
	                                    <input type="hidden" name='status' value="2">
	                                    <input type="hidden" name='type' value="2">
	                                    <input type="hidden" name='id' value="${mote.id}">
	                                    <input type="submit" name='aa' value="审核通过">
                                    </form>
                                    <form action="${webServer}api/user/approve" method="post">
	                                    <input type="hidden" name='status' value="3">
	                                    <input type="hidden" name='type' value="2">
	                                    <input type="hidden" name='id' value="${mote.id}">
	                                    <input type="submit" name='aa' value="审核不通过">
                                    </form>
                                    </#if>
                                    <#if status==2>正常
                                    <form action="${webServer}api/user/approve" method="post">
	                                    <input type="hidden" name='status' value="3">
	                                    <input type="hidden" name='type' value="2">
	                                    <input type="hidden" name='id' value="${mote.id}">
	                                    <input type="submit" name='aa' value="停用">
                                    </form>
                                    </#if>
                                    <#if status==3>停用
                                    <form action="${webServer}api/user/approve" method="post">
	                                    <input type="hidden" name='status' value="2">
	                                    <input type="hidden" name='type' value="2">
	                                    <input type="hidden" name='id' value="${mote.id}">
	                                    <input type="submit" name='aa' value="启用">
                                    </form>
                                    </#if>
                                    </font>
                                    </span>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="avatar">
                                    <img src="${mote.avartUrl?default('')}" class="img-circle" width="120">
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
                        <div class="separator"></div>
                        <div class="table-responsive">
                            <table cellspacing="0"
                                   class="table table-small-font table-bordered table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>项目名称</th>
                                    <th>售价</th>
                                    <th>拍摄费</th>
                                    <th>自购折扣</th>
                                    <th>状态</th>
                                    <th>接单时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as detailVO>
                                <tr>
                                    <td>${detailVO.title?default('')}</td>
                                    <td>${detailVO.price?default(0)}</td>
                                    <td>${detailVO.shotFee?default(0)}</td>
                                    <td>${detailVO.selfBuyOff?string("0.##")}%</td>
                                    <td>
                                        <#if detailVO.status==1><span class="text-success">已接单</span></#if>
                                        <#if detailVO.status==2><span class="text-danger">已拍下</span></#if>
                                        <#if detailVO.status==3><span class="text-danger">已好评</span></#if>
                                        <#if detailVO.status==4><span class="text-danger">已上传</span></#if>
                                        <#if detailVO.status==5><span class="text-danger">已自购</span></#if>
                                        <#if detailVO.status==6><span class="text-danger">已退还，未确认</span></#if>
                                        <#if detailVO.status==7><span class="text-danger">争议</span></#if>
                                        <#if detailVO.status==8><span class="text-danger">确认退还</span></#if>
                                        <#if detailVO.status==9><span class="text-danger">超时</span></#if>
                                    </td>
                                    <td>${detailVO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
	                    <#import "../inc/macro.ftl" as macro />
	                    <@macro.pagenation pageVO=resultVO formName='searchForm'/>
                    </div>
                </div>
            </div>
        </div>
    <#include "../inc/footer.ftl">
    </div>
</div>
</body>
</html>