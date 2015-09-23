<!DOCTYPE html>
<html>
<head>
<#include "../inc/header.ftl">
</head>
<body class="page-body">
<div class="page-container">
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
                    <li><a><i class="fa-home"></i>充值管理</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                    <form method="post" role="form" id="searchForm"
                                  action="${webServer}api/back/cash/addCashList">
                        <input type="hidden" id="index" name="index" value="${resultVO.index}">
                     </form>             
                      <div class="separator"></div>
                        <div class="table-responsive">
                            <table cellspacing="0"
                                   class="table table-small-font table-bordered table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>申请日期</th>
                                    <th>手机号</th>
                                    <th>昵称</th>
                                    <th>店铺名</th>
                                    <th>支付宝账号</th>
                                    <th>支付宝账号名</th>
                                    <th>充值金额</th>
                                    <th>订单号</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as applyVO>
                                <tr>
                                    <td>${applyVO.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
                                    <td><a class="text-blue" href="./addCashDetail?id=${applyVO.applyId}">${applyVO.phoneNumber?default('-')}</a></td>
                                    <td>${applyVO.nickname?default('')}</td>
                                    <td>${applyVO.shopName?default('')}</td>
                                    <td>${applyVO.alipayId?default('')}</td>
                                    <td>${applyVO.alipayName?default('')}</td>
                                    <td>${applyVO.money?default(0)}</td>
                                    <td>${applyVO.orderNo?default(0)}</td>
                                    <td>
                                        <#if applyVO.status==1><span class="text-success">申请中</span></#if>
                                        <#if applyVO.status==2><span class="text-danger">已支付</span></#if>
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