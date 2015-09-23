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
                    <li><a><i class="fa-home"></i>商家管理  ---${seller.nickname?default('')}</a></li>
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
                                  action="${webServer}api/back/seller/manage">
                                  <input type="hidden" name="sellerId" value="${seller.id}">
                                  <input type="hidden" id="index" name="index" value="${resultVO.index}">
                              </form>    
                                <div class="form-group">
                                    <label class="control-label">商家昵称：</label>
                                    <span class="control-label">${seller.nickname?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">手机号：</label>
                                    <span class="control-label">${seller.phoneNumber?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">邮箱地址：</label>
                                    <span class="control-label">${seller.email?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">微信：</label>
                                    <span class="control-label">${seller.weixin?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">店铺名：</label>
                                    <span class="control-label">${seller.shopName?default('')}</span>
                                </div>
                               </div>
                               <div class="col-md-5">
                                <div class="form-group">
                                    <label class="control-label">收件地址：</label>
                                    <span class="control-label">${seller.address?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">推荐人：</label>
                                    <span class="control-label">${seller.referee?default('')}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">预计款：</label>
                                    <span class="control-label">${seller.remindFee?default(0)}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">冻结款：</label>
                                    <span class="control-label">${seller.freezeFee?default(0)}</span>
                                </div>
                                <div class="form-group">
                                    <label class="control-label">账号状态：</label>
                                    <span class="control-label">
                                    <#assign status=(seller.status)?default(-1) >
                                    <#if status==1>待审核</#if>
                                    <#if status==2>正常</#if>
                                    <#if status==3>停用</#if>
                                    </span>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="avatar">
                                    <img src="${webServer}assets/images/user-4.png" class="img-circle" width="120">
                                </div>
                                <div class="forbidden-btn">
                                <#if seller.status==1>
                                    <button class="btn btn-success btn-icon btn-icon-standalone"
                                            onclick="forOpen('${seller.id}');">
                                        <i class="fa-lock"></i>
                                        <span>审核通过账户</span>

                                    </button>
                                </#if>
                                <#if seller.status==2>
                                    <button class="btn btn-red btn-icon btn-icon-standalone"
                                            onclick="forbidden('${seller.id}');">
                                        <i class="fa-lock"></i>
                                        <span>禁用账户</span>
                                    </button>
                                </#if>
                                <#if seller.status==3>
                                    <button class="btn btn-red btn-icon btn-icon-standalone"
                                            onclick="forbidden('${seller.id}');">
                                        <i class="fa-lock"></i>
                                        <span>启用账户</span>
                                    </button>
                                </#if>
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
                                    <th>任务量</th>
                                    <th>总费用</th>
                                    <th>状态</th>
                                    <th>创建时间</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as task>
                                <tr>
                                    <td><a class="text-blue" href="#">${task.title?default('')}</a></td>
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