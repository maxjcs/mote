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
				            <table>
				            <tr>
				                <td>累计注册模特数：${moteTotalNum?default(0)}人</td>
				                <td>昨日新注册模特数：${moteYesterDayNum?default(0)}人</td>
				                <td>今日新注册模特数：${moteToDayNum?default(0)}人</td>
				            </tr>
				            <tr>
				                <td>累计注册商家数：${sellerTotalNum?default(0)}家</td>
				                <td>昨日新注册商家数：${sellerYesterDayNum?default(0)}家</td>
				                <td>今日新注册商家数：${sellerToDayNum?default(0)}家</td>
				            </tr>
				            <tr>
				                <td>审核通过的项目数：${taskTotalNum?default(0)}个</td>
				                <td>总商品金额：${taskTotalMoney?default(0)}元</td>
				                <td>总拍摄金额：${taskTotalShotFee?default(0)}元</td>
				            </tr>
				            <tr>
				                <td>总任务量：${moteTaskTotalNum?default(0)}个</td>
				                <td>执行完毕的任务量：${moteTaskFinishNum?default(0)}个</td>
				                <td>执行中的任务量：${moteTaskPerformNum?default(0)}个</td>
				            </tr>
				            <tr>
				                <td>模特收下的商品数：${moteItemNum?default(0)}件</td>
				                <td>&nbsp;</td>
				                <td>&nbsp;</td>
				            </tr>
				
				            <tr>
				                <td>昨日发布并审核的项目数：${taskYesterdayNum?default(0)}个</td>
				                <td>任务量：${moteTaskYesterdayNum?default(0)}个</td>
				                <td>&nbsp;</td>
				            </tr>
				
				            <tr>
				                <td>今日发布并审核的项目数：${taskTodayNum?default(0)}个</td>
				                <td>任务量：${moteTaskTodayNum?default(0)}个</td>
				                <td>&nbsp;</td>
				            </tr>
				
				            <tr>
				                <td>赚钱最多的模特：${top1_nickname?default('')}</td>
				                <td>已经赚了：${top1_total_fee?default(0)}元酬金</td>
				                <td>&nbsp;</td>
				            </tr>
				            
				        </table>
                    </div>
                </div>
            </div>
        </div>
    <#include "../inc/footer.ftl">
    </div>
</div>
</body>
</html>