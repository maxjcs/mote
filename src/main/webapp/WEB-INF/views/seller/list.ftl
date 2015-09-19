<!doctype html>
<html>
<#include "../inc/head.ftl">
<body>
    <div id="J_wrapBody" class="global">
        <#include "../inc/side.ftl">
        <div class="content">
          <div class="row">
            <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="filter-area">
                            <form id="searchForm" method="post" action="${webServer}front/audioLibs/audioManage">
                                <input type="hidden" name="index" value="1">
                                <input type="hidden" name="lastId" value="${resultVO.lastId}">

                                <div class="form-inline">
                                    <div class="form-group">
                                        <label class="control-label">音频状态</label>
                                        <select class="form-control" name="status">
                                            <option value="-1">全部</option>
                                            <option value="0" <#if resultVO?exists && resultVO.status?exists && resultVO.status=='0'>
                                                    selected="selected"
                                            </#if>>未审
                                            </option>
                                            <option value="1" <#if resultVO?exists && resultVO.status?exists && resultVO.status=='1'>
                                                    selected="selected"
                                            </#if>>正常
                                            </option>
                                            <option value="2" <#if resultVO?exists && resultVO.status?exists && resultVO.status=='2'>
                                                    selected="selected"
                                            </#if>>审核未通过
                                            <option value="3" <#if resultVO?exists && resultVO.status?exists && resultVO.status=='3'>
                                                    selected="selected"
                                            </#if>>已下线
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">音频来源</label>
                                        <select class="form-control" name="source">
                                            <option value="-1">全部</option>
                                            <option value="1" <#if resultVO?exists && resultVO.source?exists && resultVO.source=='1'>
                                                    selected="selected"
                                            </#if>>每日任务
                                            </option>
                                            <option value="3" <#if resultVO?exists && resultVO.source?exists && resultVO.source=='3'>
                                                    selected="selected"
                                            </#if>>抢答
                                            </option>
                                            <option value="2" <#if resultVO?exists && resultVO.source?exists && resultVO.source=='2'>
                                                    selected="selected"
                                            </#if>>拍题录制讲解
                                            </option>
                                        </select>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">是否机构</label>
                                        <select class="form-control" name="organizationStatus">
                                        <#if resultVO.organizationStatus??>
                                            <option value="-1">全部</option>
                                            <option value="0"
                                                    <#if resultVO.organizationStatus==0>selected="selected"</#if>>机构音频
                                            </option>
                                            <option value="1"
                                                    <#if resultVO.organizationStatus==1>selected="selected"</#if>>普通音频
                                            </option>
                                        <#else >
                                            <option value="-1">全部</option>
                                            <option value="0">机构音频</option>
                                            <option value="1">普通音频</option>
                                        </#if>
                                        </select>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label">教师身份</label>
                                        <select class="form-control" name="teacherIdentify">
                                            <option value="-1">全部</option>
                                            <option value="1"
                                            <#if resultVO?exists && resultVO.teacherIdentify?exists && resultVO.teacherIdentify=='1'>
                                                    selected="selected"
                                            </#if>>
                                                专职老师
                                            </option>
                                            <option value="2"
                                            <#if resultVO?exists && resultVO.teacherIdentify?exists && resultVO.teacherIdentify=='2'>
                                                    selected="selected"</#if>>
                                                在校学生
                                            </option>
                                        </select>
                                    </div>
                                <#--省市联动菜单 Start-->
                                <#import "../inc/linkage.ftl" as macro />
                                <@macro.linkage cityVO=cityList pageVO=resultVO/>
                                <#--省市联动菜单 End-->
                                </div>
                                <div class="form-inline">
                                    <div class="form-group">
                                        <label class="control-label">题目ID</label>
                                        <input type="text" class="form-control" size="11"
                                               id="questionId" name="questionId"
                                               value="${resultVO.questionId?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">音频ID</label>
                                        <input type="text" class="form-control" size="11"
                                               id="audioId" name="audioId" value="${resultVO.audioId?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">录音时间</label>
                                        <input type="text" class="form-control Wdate" id="createStart" size="22"
                                               name="createStart"
                                               onfocus="var $createEnd = $dp.$('createEnd');WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',onpicked:function (){$createEnd.focus()}});"
                                               <#if resultVO.createStart?? >value="${resultVO.createStart}" </#if>>
                                        ~
                                        <input type="text" class="form-control Wdate" id="createEnd" size="22"
                                               name="createEnd"
                                               onfocus="WdatePicker({dateFmt: 'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'createStart\')}'});"
                                               <#if resultVO.createEnd?? >value="${resultVO.createEnd}" </#if>>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">教师姓名</label>
                                        <input type="text" class="form-control" size="11"
                                               id="teacherName" name="teacherName"
                                               value="${resultVO.teacherName?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">手机</label>
                                        <input type="text" class="form-control" size="15"
                                               id="phoneNumber" name="phoneNumber"
                                               value="${resultVO.phoneNumber?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label">所在学校</label>
                                        <input type="text" class="form-control" size="15"
                                               id="schoolName" name="schoolName"
                                               value="${resultVO.schoolName?default('')}">
                                    </div>
                                    <div class="form-group">
                                        <button class="btn btn-secondary btn-single" type="submit">搜索</button>
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
                                    <th colspan="2">音频ID</th>
                                    <th>题目ID</th>
                                    <th>教师姓名</th>
                                    <th>手机号码</th>
                                    <th>省份</th>
                                    <th>城市</th>
                                    <th>录制时间</th>
                                    <th>音频时长（s）</th>
                                    <th>音频来源</th>
                                    <th>购买次数</th>
                                    <th>评价次数</th>
                                    <th>状态</th>
                                    <th>机构名称</th>
                                    <th>类型</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as audioLibsVO>
                                <tr>
                                    <td>
                                        <#assign status=audioLibsVO.status?default(-1)/>
                                        <#if status==1>
                                            <input type="checkbox" name="checkbox-item" value="${audioLibsVO.audioId!}">
                                        </#if>
                                    </td>
                                    <th><a class="text-blue"
                                           href="${webServer}front/audioLibs/audioDetail?audioId=${audioLibsVO.audioId}">${audioLibsVO.audioId}</a>
                                    </th>
                                    <td>${audioLibsVO.questionId}</td>
                                    <td>${audioLibsVO.teacherName?default('')}</td>
                                    <td>${audioLibsVO.phoneNumber?default('')}</td>
                                    <td>${audioLibsVO.provinceName?default('')}</td>
                                    <td>${audioLibsVO.cityName?default('')}</td>
                                    <#if audioLibsVO.createTime?exists>
                                        <td>${audioLibsVO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    <#else>
                                        <td></td>
                                    </#if>
                                    <td>${audioLibsVO.duration?default(0)}s</td>

                                    <#if !audioLibsVO.source?exists||audioLibsVO.source==0>
                                        <td></td>
                                    <#elseif audioLibsVO.source==1>
                                        <td>每日任务</td>
                                    <#elseif audioLibsVO.source==2>
                                        <td>拍题录制讲解</td>
                                    <#elseif audioLibsVO.source==3>
                                        <td>抢答</td>
                                    </#if>
                                    <td>${audioLibsVO.buyNums?default(0)}</td>
                                    <td>${audioLibsVO.evelNums?default(0)}</td>
                                    <td class="J_status">
                                        <#if status==1>
                                            <span class="text-success">正常</span>
                                        <#elseif status==2>
                                            <span class="text-danger">审核未通过</span>
                                        <#elseif status==3>
                                            <span class="text-danger">下线</span>
                                        <#elseif status==0>
                                            <span class="text-warning">未审</span>
                                        </#if>
                                    </td>
                                    <td>${audioLibsVO.organizationName?default('--')}</td>
                                    <td>
                                        <#if audioLibsVO.type==1>
                                            <span>音频</span>
                                        </#if>
                                        <#if audioLibsVO.type==2>
                                            <span>白板</span>
                                        </#if>
                                    </td>
                                </tr>
                                </#list>
                                </tbody>
                            </table>
                        </div>
                        <div class="batch-offline-group clearfix">
                            <div class="checkbox col-md-6 mg0">
                                <label for="selectAll">
                                    <input id="selectAll" type="checkbox">
                                    全选
                                </label>
                                <button id="batchOfflineBtn" class="btn btn-danger" style="margin-left: 30px;">批量下架
                                </button>
                            </div>
                        </div>
                        <span>共有${resultVO.total!}个结果，共有${(resultVO.total/resultVO.pageSize)?ceiling}页</span>
                    <#--<#import "../inc/macro.ftl" as macro />-->
                    <#--<@macro.pagenation pageVO=resultVO formName='searchForm'/>-->
                    <#import "../inc/pagination.ftl" as macro />
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