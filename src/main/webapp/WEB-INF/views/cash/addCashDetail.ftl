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
                    <li><a><i class="fa-home"></i>充值申请详情</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                    <form method="post" role="form" id="searchForm"
                                  action="${webServer}api/back/cash/verifyAddCash">
                        <input type="hidden" name="id" value="${resultVO.id}">          
                        <table>
                          <tr>
                           <td><font color='red'>${message?default("")}</font></td>
                           <td>&nbsp;</td>
                         </tr>
                         <tr>
                           <td>支付宝账号:</td>
                           <td>${resultVO.alipayId?default('')}</td>
                         </tr>
                         <tr>
                           <td>支付宝名:</td>
                           <td>${resultVO.alipayName?default('')}</td>
                         </tr>
                         <tr>
                           <td>金额:</td>
                           <td>
                                <#if resultVO.status==1>
	                               <input type="text" name="money" value="">
                           		<#else>
	                                ${resultVO.money?default(0)}
	                       		</#if>
                           </td>
                         </tr>
                         <tr>
                           <td>订单号:</td>
                           <td>
                               <#if resultVO.status==1>
	                               <input type="text" name="lastSixOrderNo" value="">
                           		<#else>
	                                ${resultVO.lastSixOrderNo?default('')}
	                       		</#if>
                           </td>
                         </tr>
                         <tr>
                           <#if resultVO.status==1>
	                           <td><input type="submit" name="aa" value="确认录入"></td>
	                           <td><input type="button" name="aa" value="取消"></td>
                           <#else>
	                           <td>状态:</td>
	                           <td>已处理完成</td>
	                       </#if>    
                         </tr>
                        </table>
                     </form>             
                </div>
            </div>
        </div>
    <#include "../inc/footer.ftl">
    </div>
</div>
</body>
</html>