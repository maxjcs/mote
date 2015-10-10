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
                    <li><a><i class="fa-home"></i>模特管理</a></li>
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
                                  action="${webServer}api/back/mote/manage">
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
                                        <label class="control-label">性别</label>
                                        <select class="form-control" name="gender">
                                        <#if resultVO.gender?exists>
                                            <option value="-1">全部</option>
                                            <option value="1" <#if resultVO.gender==1>selected="selected"</#if>>男 </option>                                           </option>
                                            <option value="2" <#if resultVO.gender==0>selected="selected"</#if>>女</option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="1">男</option>
                                            <option value="2">女</option>
                                        </#if>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">年龄</label>
                                        <input type="text" class="form-control" size="22" id="ageBegin" name="ageBegin" value="${resultVO.ageBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="ageEnd" name="ageEnd" value="${resultVO.ageEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">身高</label>
                                        <input type="text" class="form-control" size="22" id="heigthBegin" name="heigthBegin" value="${resultVO.heigthBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="heigthEnd" name="heigthEnd" value="${resultVO.heigthEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">体型</label>
                                        <select class="form-control" name="shape">
                                        <#if resultVO.shape?exists>
                                            <option value="-1">全部</option>
                                            <option value="1" <#if resultVO.shape==1>selected="selected"</#if>>偏瘦 </option>                                           </option>
                                            <option value="2" <#if resultVO.shape==2>selected="selected"</#if>>中等</option>
                                            <option value="3" <#if resultVO.shape==3>selected="selected"</#if>>偏胖</option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="1">偏瘦</option>
                                            <option value="2">中等</option>
                                            <option value="3">偏胖</option>
                                        </#if>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">预存款</label>
                                        <input type="text" class="form-control" size="22" id="remindFeeBegin" name="remindFeeBegin" value="${resultVO.remindFeeBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="remindFeeEnd" name="remindFeeEnd" value="${resultVO.remindFeeEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">收益</label>
                                        <input type="text" class="form-control" size="22" id="moteTaskFeeBegin" name="moteTaskFeeBegin" value="${resultVO.moteTaskFeeBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="moteTaskFeeEnd" name="moteTaskFeeEnd" value="${resultVO.moteTaskFeeEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">完成任务数</label>
                                        <input type="text" class="form-control" size="22" id="taskNumBegin" name="taskNumBegin" value="${resultVO.taskNumBegin?default('')}">
                                        ~ <input type="text" class="form-control" size="22" id="taskNumEnd" name="taskNumEnd" value="${resultVO.taskNumEnd?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">排序</label>
                                        <select class="form-control" name="sort">
                                        <#if resultVO.sort?exists>
                                            <option value="-1">全部</option>
                                            <option value="1" <#if resultVO.sort==1>selected="selected"</#if>>注册时间-升序</option>
                                            <option value="2" <#if resultVO.sort==2>selected="selected"</#if>>注册时间-降序</option>
                                            <option value="3" <#if resultVO.sort==3>selected="selected"</#if>>收益-升序</option>
                                            <option value="4" <#if resultVO.sort==4>selected="selected"</#if>>收益-降序</option>
                                            <option value="5" <#if resultVO.sort==5>selected="selected"</#if>>预存款-升序</option>
                                            <option value="6" <#if resultVO.sort==6>selected="selected"</#if>>预存款-降序</option>
                                            <option value="7" <#if resultVO.sort==7>selected="selected"</#if>>任务数-升序</option>
                                            <option value="8" <#if resultVO.sort==8>selected="selected"</#if>>任务数-降序</option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="1" >注册时间-升序</option>
                                            <option value="2" >注册时间-降序</option>
                                            <option value="3" >收益-升序</option>
                                            <option value="4" >收益-降序</option>
                                            <option value="5" >预存款-升序</option>
                                            <option value="6" >预存款-降序</option>
                                            <option value="7" >任务数-升序</option>
                                            <option value="8" >任务数-降序</option>
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
                                    <th>注册日期</th>
                                    <th>昵称</th>
                                    <th>手机号</th>
                                    <th>性别</th>
                                    <th>年龄</th>
                                    <th>身高</th>
                                    <th>体型</th>
                                    <th>收益</th>
                                    <th>预存款</th>
                                    <th>完成任务数</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as moteVO>
                                <tr>
                                    <td>${moteVO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    <td>${moteVO.nickname?default('-')}</td>
                                    <td><a class="text-blue" href="./moteDetail?moteId=${moteVO.userId}">${moteVO.phoneNumber?default('')}</a></td>
                                    <td>
                                       <#if moteVO.gender?exists>
                                         <#if moteVO.gender==1>男</#if>
                                         <#if moteVO.gender==0>女</#if>
                                         <#if moteVO.gender==3>未知</#if>
                                       </#if>   
                                     </td>
                                    <td>${moteVO.age?default('')}</td>
                                    <td>${moteVO.heigth?default('')}</td>
                                    <td>
                                        <#if moteVO.shape?exists>
	                                        <#if moteVO.shape==1>偏瘦</#if>
	                                        <#if moteVO.shape==2>中等</#if>
	                                        <#if moteVO.shape==3>偏胖</#if>
	                                    </#if>    
                                    </td>
                                    <td>${moteVO.taskFee?default(0)}</td>
                                    <td>${moteVO.remindFee?default(0)}</td>
                                    <td>${moteVO.taskNum?default(0)}</td>
                                    <td>
                                        <#if moteVO.status==1><span class="text-success">待审核</span></#if>
                                        <#if moteVO.status==2><span class="text-danger">正常</span></#if>
                                        <#if moteVO.status==3><span class="text-danger">停用</span></#if>
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