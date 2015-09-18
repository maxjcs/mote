<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
        <title>React</title>
        <link rel="stylesheet" type="text/css" href="${webServer}assets/res/css/common/base.css">
        <link rel="stylesheet" type="text/css" href="${webServer}assets/res/css/business/common.css">
        <link rel="stylesheet" type="text/css" href="${webServer}assets/res/css/business/index.css">
        
    </head>
    <body>
        <div id="J_wrapBody" class="global">
            <div class="side">
                <div class="category">全民模特 系统后台</div>
                <dl>
                    <dt>主板</dt>
                    <dd class="active"><a href="./index">指示板</a></dd>
                    <dd><a href="./systemControl">系统控制</a></dd>
                    <dd><a href="./message">站内信</a></dd>
                </dl>

                <dl>
                    <dt>用户管理模块</dt>
                    <dd><a href="./fund-process">商家管理</a></dd>
                    <dd><a href="./fund-add">模特管理</a></dd>
                </dl>

                <dl>
                    <dt>财务管理模块</dt>
                    <dd><a href="#">充值管理</a></dd>
                    <dd><a href="#">体现管理</a></dd>
                </dl>

                <dl>
                    <dt>自拍秀任务管理模块</dt>
                    <dd><a href="#">项目管理</a></dd>
                </dl>
            </div>
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
        </div>
    </body>
</html>

