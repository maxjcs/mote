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
                    <li><a><i class="fa-home"></i>任务管理  ---${task.title?default('')}</a></li>
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
                                  action="${webServer}api/back/task/detail">
                                  <input type="hidden" name="taskId" value="${task.id}">
                                  <input type="hidden" id="index" name="index" value="${resultVO.index}">
                              </form>    
                                <div class="form-group">
                                    <label class="control-label">商家售价：</label>
                                    <span class="control-label">${task.price?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">模特折扣：</label>
                                    <span class="control-label">${task.selfBuyOff?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">任务酬金：</label>
                                    <span class="control-label">${task.shotFee?default('')}</span>
                                </div>
                                
                                <div class="form-group">
                                    <label class="control-label">商品链接：</label>
                                    <span class="control-label">${task.url?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">性别：</label>
                                    <span class="control-label">
                                      <#if task.gender?exists>
                                         <#if task.gender==1>男</#if>
                                         <#if task.gender==2>女</#if>
                                         <#if task.gender==3>未知</#if>
                                       </#if>
                                    </span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">年龄：</label>
                                    <span class="control-label">${task.ageMin?default(0)}~${task.ageMax?default(0)}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">身高：</label>
                                    <span class="control-label">${task.heightMin?default(0)}~${task.heightMax?default(0)}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">体型：</label>
                                    <span class="control-label">
                                        <#if task.shape?exists>
	                                        <#if task.shape==1>偏瘦</#if>
	                                        <#if task.shape==2>中等</#if>
	                                        <#if task.shape==3>偏胖</#if>
	                                    </#if>
                                    </span>
                                </div>
                               
                               </div>
                               <div class="col-md-5">
                                <div class="form-group">
                                    <label class="control-label">任务标题：</label>
                                    <span class="control-label">${task.title?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">拍摄描述：</label>
                                    <span class="control-label">${task.shotDesc?default('')}</span>
                                </div>
                                 <div class="form-group">
                                    <label class="control-label">任务数：</label>
                                    <span class="control-label">${task.number?default(0)}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">状态：</label>
                                    <span class="control-label">
                                        <#if task.status==0><span class="text-success">未支付</span></#if>
                                        <#if task.status==1><span class="text-danger">待审核</span></#if>
                                        <#if task.status==2><span class="text-danger">执行中</span></#if>
                                        <#if task.status==3><span class="text-danger">审核不通过</span></#if>
                                        <#if task.status==4><span class="text-danger">已经结束</span></#if>
                                    </span>
                                </div>
                               </div>
                            <div class="col-md-3">
                                <div class="avatar">
                                    <img src="${task.url?default('')}" class="img-circle" width="120">
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
                                    <th>模特昵称</th>
                                    <th>订单号</th>
                                    <th>已接单</th>
                                    <th>已拍下</th>
                                    <th>已接单</th>
                                    <th>已好评</th>
                                    <th>自购</th>
                                    <th>退还</th>
                                    <th>完结</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as moteTaskVO>
                                <tr>
                                    <td><a class="text-blue" href="../seller/moteTaskDetail?moteTaskId=${moteTaskVO.id}">${moteTaskVO.nickname?default('')}</a></td>
                                    <td>${moteTaskVO.orderNo?default('')}</td>
                                    <td>${moteTaskVO.status?default('')}</td>
                                    <td>${moteTaskVO.status?default('')}</td>
                                    <td>${moteTaskVO.status?default('')}</td>
                                    <td>${moteTaskVO.status?default('')}</td>
                                    <td>
                                       <#if moteTaskVO.status==5>
                                           是
                                       </#if>    
                                    </td>
                                    <td>
                                       <#if moteTaskVO.status==6>
                                           是
                                       </#if>
                                    </td>
                                    <td>
                                       <#if moteTaskVO.finishStatus==0 && moteTaskVO.status==6>
                                           待确认
                                       </#if>
                                      <#if moteTaskVO.finishStatus==1>
                                           已完成
                                       </#if>
                                    </td>
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