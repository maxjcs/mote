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
                    <li><a><i class="fa-home"></i>任务管理</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
				        <div class="filter-area">
                            <form method="post" role="form" id="searchForm"
                                  action="${webServer}api/back/task/manage">
                                <div class="form-inline">
                                   <div class="form-group">
                                        <label class="control-label">创建时间</label>
                                        <input type="text" class="form-control Wdate" size="22" id="registerBegin"
                                               name="registerBegin"
                                               onfocus="var $registerEnd = $dp.$('registerEnd');WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',onpicked:function (){$registerEnd.focus()}});"
                                               <#if resultVO.registerBegin?? >value="${resultVO.registerBegin}" </#if>>
                                        ~
                                        <input type="text" class="form-control Wdate" size="22" id="registerEnd"
                                               name="registerEnd"
                                               onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'registerBegin\')}'});"
                                               <#if resultVO.registerEnd?? >value="${resultVO.registerEnd}" </#if> >
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">任务名称</label>
                                        <input type="text" class="form-control" size="11" id="title"
                                               name="title" value="${resultVO.title?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">状态</label>
                                        <select class="form-control" name="status">
                                        <#if resultVO.status?exists>
                                            <option value="-1">全部</option>
                                            <option value="0" <#if resultVO.status==0>selected="selected"</#if>>未支付</option>
                                            <option value="1" <#if resultVO.status==1>selected="selected"</#if>>待审核</option>
                                            <option value="2" <#if resultVO.status==2>selected="selected"</#if>>执行中</option>
                                            <option value="3" <#if resultVO.status==3>selected="selected"</#if>>审核不通过</option>
                                            <option value="4" <#if resultVO.status==4>selected="selected"</#if>>已经结束</option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="0">未支付</option>
                                            <option value="1">待审核</option>
                                            <option value="2">执行中</option>
                                            <option value="3">审核不通过</option>
                                            <option value="4">已经结束</option>
                                        </#if>
                                        </select>
                                    </div>
                                    
                                    <input type="hidden" id="index" name="index" value="${resultVO.index}">
                                    <div class="form-group">
                                        <button class="btn btn-secondary btn-single" type="submit">搜索</button>
                                    </div>

                                </div>
                            </form>
                        </div>
                        <div class="separator"></div>
                        <div class="table-responsive">
                            <table cellspacing="0"
                                   class="table table-small-font table-bordered table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>商家昵称</th>
                                    <th>项目名称</th>
                                    <th>售价</th>
                                    <th>拍摄费</th>
                                    <th>任务量</th>
                                    <th>总费用</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as task>
                                <tr>
                                   <td>${task.nickname?default('')}</td>
                                    <td><a class="text-blue" href="./detail?taskId=${task.id}">${task.title?default('')}</a></td>
                                    <td>${task.price?default(0)}</td>
                                    <td>${task.shotFee?default(0)}</td>
                                    <td>${task.number?default(0)}</td>
                                    <td>${task.totalFee?default(0)}</td>
                                    <td>
                                        <#if task.status==0><span class="text-success">未支付</span></#if>
                                        <#if task.status==1><span class="text-danger">待审核</span></#if>
                                        <#if task.status==2><span class="text-danger">执行中</span></#if>
                                        <#if task.status==3><span class="text-danger">审核不通过</span></#if>
                                        <#if task.status==4><span class="text-danger">已经结束</span></#if>
                                    </td>
                                    <td>${task.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
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