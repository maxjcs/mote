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
            <div class="sidebar-toggle" title="返回" onclick="window.history.go(-1);">
                <a href="javascript:;">
                    <i class="fa-chevron-left"></i>
                </a>
            </div>
            <div class="breadcrumb-env">
                <ol class="breadcrumb bc-1">
                    <li><a><i class="fa-home"></i>教师管理</a></li>
                    <li class="active"><strong>教师列表</strong></li>
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
                                  action="${webServer}front/teacherManage/teacherList">
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label class="control-label">教师身份</label>
                                        <select class="form-control" name="teacherIdentify">
                                        <#if resultVO.teacherIdentify?exists>
                                            <option value="-1">全部</option>
                                            <option value="1"
                                                <#if resultVO.teacherIdentify==1>
                                                    selected="selected"</#if>>
                                                老师
                                            </option>
                                            <option value="2"
                                                <#if resultVO.teacherIdentify==2>
                                                    selected="selected"</#if>>
                                                大学生
                                            </option>
                                        <#else>
                                            <option value="-1" selected="selected">全部</option>
                                            <option value="1">老师</option>
                                            <option value="2">大学生</option>
                                        </#if>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">星级</label>
                                        <select class="form-control" name="star">
                                        <#if resultVO.star?exists>
                                            <option value="-1">全部</option>
                                            <option value="0" <#if resultVO.star==0>selected="selected"</#if>>0星
                                            </option>
                                            <option value="1" <#if resultVO.star==1>selected="selected"</#if>>1星
                                            </option>
                                            <option value="2" <#if resultVO.star==2>selected="selected"</#if>>2星
                                            </option>
                                            <option value="3" <#if resultVO.star==3>selected="selected"</#if>>3星
                                            </option>
                                            <option value="4" <#if resultVO.star==4>selected="selected"</#if>>4星
                                            </option>
                                            <option value="5" <#if resultVO.star==5>selected="selected"</#if>>5星
                                            </option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="0">0星</option>
                                            <option value="1">1星</option>
                                            <option value="2">2星</option>
                                            <option value="3">3星</option>
                                            <option value="4">4星</option>
                                            <option value="5">5星</option>
                                        </#if>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">辅导年级</label>
                                        <select class="form-control" name="grade">
                                            <option value="-1">全部</option>
                                        <#if resultVO.grade?exists>
                                            <#list grades as grade>
                                                <option value="${grade.id}"
                                                        <#if resultVO.grade==grade.id>selected="selected"</#if>>${grade.name}</option>
                                            </#list>
                                        <#else >
                                            <#list grades as grade>
                                                <option value="${grade.id}">${grade.name}</option>
                                            </#list>
                                        </#if>

                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">辅导学科</label>
                                        <select class="form-control" name="subject">
                                            <option value="-1">全部</option>
                                        <#if resultVO.subject?exists>
                                            <#list subjects as subject>
                                                <option value="${subject.id}"
                                                        <#if resultVO.subject==subject.id>selected="selected"</#if>>${subject.name}</option>
                                            </#list>
                                        <#else >
                                            <#list subjects as subject>
                                                <option value="${subject.id}">${subject.name}</option>
                                            </#list>
                                        </#if>

                                        </select>
                                    </div>
                                <#--省市联动菜单 Start-->
                                <#import "../inc/linkage.ftl" as macro />
                                <@macro.linkage cityVO=cityList pageVO=resultVO/>
                                <#--省市联动菜单 End-->
                                </div>
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label class="control-label">教师姓名</label>
                                        <input type="text" class="form-control" size="11" id="teacherName"
                                               name="teacherName" value="${resultVO.teacherName?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">教师昵称</label>
                                        <input type="text" class="form-control" size="11" id="nickname"
                                               name="nickname" value="${resultVO.nickname?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">手机</label>
                                        <input type="text" class="form-control" size="13" id="phoneNumber"
                                               name="phoneNumber" value="${resultVO.phoneNumber?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">所在学校</label>
                                        <input type="text" class="form-control" size="15" name="schoolName">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">注册时间</label>
                                        <input type="text" class="form-control Wdate" size="22" id="createStart"
                                               name="createStart"
                                               onfocus="var $createEnd = $dp.$('createEnd');WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',onpicked:function (){$createEnd.focus()}});"
                                               <#if resultVO.createStart?? >value="${resultVO.createStart}" </#if>>
                                        ~
                                        <input type="text" class="form-control Wdate" size="22" id="createEnd"
                                               name="createEnd"
                                               onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createStart\')}'});"
                                               <#if resultVO.createEnd?? >value="${resultVO.createEnd}" </#if> >
                                    </div>
                                    <input type="hidden" id="index" name="index" value="${resultVO.index}">

                                    <div class="form-group">
                                        <button class="btn btn-secondary btn-single" type="submit">搜索</button>
                                    </div>


                                    <div class="form-group">
                                        <button id="addTeacherBtn" class="btn btn-secondary btn-single" type="button">
                                            <i></i><span>新建教师</span></button>
                                    </div>
                                    <div class="form-group">
                                        <button id="exportExcel" class="btn btn-secondary btn-single" type="button">
                                            导出excel
                                        </button>
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
                                <#--教师姓名、手机号码、星级、教师身份、省份、城市、所在学校、账号状态（正常、已禁用）-->
                                    <th>教师姓名</th>
                                    <th>教师昵称</th>
                                    <th>手机号码</th>
                                    <th>星级</th>
                                    <th>教师身份</th>
                                    <th>省份</th>
                                    <th>城市</th>
                                    <th>所在学校</th>
                                    <th>关注数量</th>

                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as teacherVO>
                                <tr>
                                    <td>${teacherVO.name?default('-')}</td>
                                    <td>${teacherVO.nickname?default('-')}</td>
                                    <th><a class="text-blue"
                                           href="${webServer}front/teacherManage/teacherDetail/${teacherVO.id}">${teacherVO.phoneNumber!}</a>
                                    </th>
                                    <td>${teacherVO.star?default(0)}星</td>
                                    <#assign teacherIdentify=teacherVO.teacherIdentify?default(-1)/>
                                    <#assign teacherIdentifyName=''/>
                                    <#if teacherIdentify==1>
                                        <#assign teacherIdentifyName='老师'/>
                                    </#if>
                                    <#if teacherIdentify==2>
                                        <#assign teacherIdentifyName='大学生'/>
                                    </#if>
                                    <#if teacherIdentify==-1>
                                        <#assign teacherIdentifyName='未知'/>
                                    </#if>
                                    <td>${teacherIdentifyName}</td>
                                    <td>${teacherVO.provinceName!}</td>
                                    <td>${teacherVO.cityName!}</td>
                                    <td>${teacherVO.schoolName!}</td>
                                    <td>${teacherVO.followerNum!}</td>

                                    <td><#if teacherVO.status==1><span
                                            class="text-success">正常</span></#if><#if teacherVO.status==0><span
                                            class="text-danger">已禁用</span></#if></td>
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