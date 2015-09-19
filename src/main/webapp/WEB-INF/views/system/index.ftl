<html>

<head>
<#include "../inc/header.ftl">
</head>
<style type="text/css">
    #left {
        width: 30%; /*设定宽度*/
        text-align: left; /*文字左对齐*/
        float: left; /*浮动居左*/
        clear: left; /*不允许左侧存在浮动*/
        overflow: hidden; /*超出宽度部分隐藏*/
        margin-left: 30px;
        margin-top: 20px;
    }

    #right {
        width: 50%;
        text-align: left;
        float: right; /*浮动居右*/
        clear: right; /*不允许右侧存在浮动*/
        overflow: hidden;
        margin-top: 20px;
        margin-left: 30px;
    }
</style>
<body class="page-body">
<h1 style="margin-left: 15px">指示板</h1><br/>
<div>
    <div class="content">
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
    <p/>
</div>
</body>

</html>

