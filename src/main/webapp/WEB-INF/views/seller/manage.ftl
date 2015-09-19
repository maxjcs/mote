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
                    <li><a><i class="fa-home"></i>指示板</a></li>
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
                                  action="${webServer}api/back/seller/manage">
                                <div class="form-inline">
                                   <div class="form-group">
                                        <label class="control-label">注册时间</label>
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
                                        <label class="control-label">状态</label>
                                        <select class="form-control" name="status">
                                        <#if resultVO.status?exists>
                                            <option value="-1">全部</option>
                                            <option value="1" <#if resultVO.status==1>selected="selected"</#if>>待审核
                                            </option>
                                            <option value="2" <#if resultVO.status==2>selected="selected"</#if>>正常
                                            </option>
                                            <option value="3" <#if resultVO.status==3>selected="selected"</#if>>停用
                                            </option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="1">待审核</option>
                                            <option value="2">正常</option>
                                            <option value="3">停用</option>
                                        </#if>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">昵称</label>
                                        <input type="text" class="form-control" size="11" id="nickname"
                                               name="nickname" value="${resultVO.nickname?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">店铺名</label>
                                        <input type="text" class="form-control" size="11" id="shopName"
                                               name="shopName" value="${resultVO.shopName?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">推荐人</label>
                                        <input type="text" class="form-control" size="13" id="referee"
                                               name="referee" value="${resultVO.referee?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">项目数</label>
                                        <input type="text" class="form-control" size="22" id="projectNumBegin" name="projectNumBegin" value="${resultVO.projectNumBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="projectNumEnd" name="projectNumEnd" value="${resultVO.projectNumEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">任务数</label>
                                        <input type="text" class="form-control" size="22" id="moteTaskNumBegin" name="moteTaskNumBegin" value="${resultVO.moteTaskNumBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="moteTaskNumEnd" name="moteTaskNumEnd" value="${resultVO.moteTaskNumEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">预存款</label>
                                        <input type="text" class="form-control" size="22" id="remindFeeBegin" name="remindFeeBegin" value="${resultVO.remindFeeBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="remindFeeEnd" name="remindFeeEnd" value="${resultVO.remindFeeEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">酬金花费</label>
                                        <input type="text" class="form-control" size="22" id="moteTaskFeeBegin" name="moteTaskFeeBegin" value="${resultVO.moteTaskFeeBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="moteTaskFeeEnd" name="moteTaskFeeEnd" value="${resultVO.moteTaskFeeEnd?default('')}">
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
                                    <th>注册日期</th>
                                    <th>昵称</th>
                                    <th>店铺名</th>
                                    <th>推荐人</th>
                                    <th>项目数</th>
                                    <th>任务数</th>
                                    <th>预存款</th>
                                    <th>酬金花费</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as sellerVO>
                                <tr>
                                    <td>${sellerVO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    <td><a class="text-blue" href="#">${sellerVO.nickname?default('-')}</a></td>
                                    <td>${sellerVO.shopName?default('')}</td>
                                    <td>${sellerVO.referee?default('')}</td>
                                    <td>${sellerVO.projectNum?default(0)}</td>
                                    <td>${sellerVO.taskNum?default(0)}</td>
                                    <td>${sellerVO.totalFee?default(0)}</td>
                                    <td>${sellerVO.taskFee?default(0)}</td>
                                    <td>
                                        <#if sellerVO.status==1><span class="text-success">待审核</span></#if>
                                        <#if sellerVO.status==2><span class="text-danger">正常</span></#if>
                                        <#if sellerVO.status==3><span class="text-danger">停用</span></#if>
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