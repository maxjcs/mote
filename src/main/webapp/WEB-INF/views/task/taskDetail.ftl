<!DOCTYPE html>
<html>
<head>
<#include "../inc/header.ftl">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
        <title>${task.title?default('')}</title>
        <style>
            @font-face {
                font-family: 'iconfont';
                src: url('//at.alicdn.com/t/font_1443933845_6627495.eot'); /* IE9*/
                src: url('//at.alicdn.com/t/font_1443933845_6627495.eot?#iefix') format('embedded-opentype'), /* IE6-IE8 */
                url('//at.alicdn.com/t/font_1443933845_6627495.woff') format('woff'), /* chrome、firefox */
                url('//at.alicdn.com/t/font_1443933845_6627495.ttf') format('truetype'), /* chrome、firefox、opera、Safari, Android, iOS 4.2+*/
                url('//at.alicdn.com/t/font_1443933845_6627495.svg#iconfont') format('svg'); /* iOS 4.1- */
            }

            .clearfix:after {
                display: block;
                clear: both;
                height: 0;
                content: ".";
                visibility: hidden;
            }
            *html.clearfix {
                height: 1%;
            }
            .clearfix {
                display: block;
            }
            .contentWrap h2 {
                font:normal 12px/18px arial;
            }
            .detailWrap {
                border:1px solid #C4C4C4;
                padding-left: 10px;
            }
            .child-block {
                border:1px solid #C4C4C4;
                margin-top: 10px;
                padding-left: 10px;
                height:30px;
                font:normal 12px/30px airal;
            }
            .imgAndText {
                float:left;
            }
            .imgAndText img {
                float: left;
            }

            .imgAndText ul{
                float: left;

            }

            .imgAndText li {
                list-style: none;
                font:normal 12px/30px arial;
                color: #888888;
            }
            .imgAndText li i {
                color:green;
                font:normal 18px/30px arial "iconfont";
            }
            .detailWrap .detail h2 {
                padding-bottom: 15px;
                font:normal 18px/26px arial;
                color: #888888;
                border-bottom: 1px solid #888888;
            }

            .detailWrap .detail {
                margin-left:370px;
                margin-right:10px;
            }
            .detailWrap .detail p{
                color: #888888;
                font:normal 12px/18px arial;
                text-align:left;
                text-indent:2em;
            }

            .product-link ,.link-text{
                width: 100%;
                padding:5px 10px;
                clear: both;
                font:normal 12px arial;
                color: #888888;
            }
            .product-link label {
                font:normal 12px arial;
                color: #888888;
            }
            .product-link input {
                min-width:300px;
                width: 60%;
                outline-style: none;
                color: #888888;
            }
            .product-link a{
                padding:5px;
                background: green;
                color: white;
                border-radius: 4px;
                text-decoration: none;
                font:normal 12px arial;
            }

            .child-block label {
                float: left;
                wdith:100px;
                color: #888888;
            }

            .child-block dl {
                float: left;
                height: 30px;
                margin: 0;
                padding: 0;
                line-height: 30px;
            }
            .child-block dl dd {
                float: left;
                padding-right: 30px;
                font:normal 12px/30px airal;
                color: #888888;
            }

            .child-block .statusTab {
                margin-left: 30px;
                border-radius: 4px;
            }

            .child-block .statusTab dd{
                padding: 5px 5px;
                margin-top: 3px;
                font: normal 12px airal;
                border: 1px solid #888888;
                margin-left: -1px;
            }
            .child-block .statusTab .disabled{
                background:gray;
                color:white;
            }
            .child-block .statusTab .active{
                background: green;
                color:white;
            }

        </style>
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
                    <li><a><i class="fa-home"></i>任务管理  ---${task.title?default('')}</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                   <form method="post" role="form" id="searchForm" action="${webServer}api/back/task/detail">
	                  <input type="hidden" name="taskId" value="${task.id}">
	                  <input type="hidden" id="index" name="index" value="${resultVO.index}">
	               </form> 
                    <div class="panel-body">
                      <div class="contentWrap">
			            <div class="detailWrap clearfix">
			                  <h2>商品基本信息</h2>
			                  <div class="imgAndText clearfix">
			                      <img width="150" height="150" src="${task.imgUrl?default('')}" />
			                      <ul>
			                        <li><i>&#xe609;</i>商品售价：¥${task.price?default('')}</li>
			                        <li><i>&#xe608;</i>模特折扣：${task.selfBuyOff?default('')}%</li>
			                        <li><i>&#xe607;</i>任务酬金：¥${task.shotFee?default('')}</li>
			                        <li><i>&#xe603;</i>商品所在地：浙江 杭州</li>
			                      </ul>
			                  </div>
			                  <div class="detail">
			                      <h2>${task.title?default('')}</h2>
			                      <p>
			                        ${task.shotDesc?default('')}
			                      </p>
			                  </div>
			                  <div class="product-link">
			                    <label>商品链接</label>
			                    <form action="./updateTaskUrl" method="post">
			                    <input type="hidden" name="taskId" value="${task.id?default(0)}"/>
			                    <input type="text" name="url" value="${task.url?default('')}"/>
			                     <button type="submit" name="aaa">修改链接</a>
			                     </form>
			                  </div>
			                  <div class="link-text">商家提交的原始链接：${task.oldUrl?default('')}</div>
			            </div>
			            <div class="child-block">
			                <label>模特筛选信息</label>
			                <dl>
			                    <dd>姓别：<#if task.gender?exists>
                                         <#if task.gender==1>男</#if>
                                         <#if task.gender==0>女</#if>
                                         <#if task.gender==3>未知</#if>
                                       </#if>
                                </dd>
			                    <dd>年龄：${task.ageMin?default(0)}-${task.ageMax?default(0)}</dd>
			                    <dd>体型：<#if task.shape?exists>
	                                        <#if task.shape==1>偏瘦</#if>
	                                        <#if task.shape==2>中等</#if>
	                                        <#if task.shape==3>偏胖</#if>
	                                    </#if>
	                             </dd>
			                    <dd>身高：${task.heightMin?default(0)}-${task.heightMax?default(0)}</dd>
			                </dl>
			            </div>
			
			            <div class="child-block">
			                <label>付款信息</label>
			                <dl>
			                    <dd>任务数：${task.number?default(0)}</dd>
			                    <dd>应冻结费用：${task.number*task.price+task.shotFee}元</dd>
			                    <dd>已经冻结：
			                    <#if task.status==2 || task.status==4>
			                      ${task.number*task.price+task.shotFee}
			                    <#else>
			                      0
                                </#if>
			                    元</dd>
			                    <dd>已消耗：0元</dd>
			                    <dd>剩余：
			                      <#if task.status==2 || task.status==4>
			                      ${task.number*task.price+task.shotFee}
			                      <#else>
			                      0
                                  </#if>
                                 元</dd>
			                </dl>
			            </div>
			
			            <div class="child-block">
			                <label>当前状态</label>
			                <dl class="statusTab">
			                    <#if task.status==0><span class="text-danger">未支付</span></#if>
	                            <#if task.status==1>
	                            <span class="text-danger">待审核 
		                            <a href="${webServer}api/back/task/approve?status=2&id=${task.id}">[审核通过]</a>
		                            <a href="${webServer}api/back/task/approve?status=3&id=${task.id}">[审核不通过]</a>
                                </span>
	                            </#if>
	                            <#if task.status==2><span class="text-success">执行中</span></#if>
	                            <#if task.status==3><span class="text-danger">审核不通过</span></#if>
	                            <#if task.status==4><span class="text-success">已经结束</span></#if>
			                </dl>
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
                                    <th>模特昵称</th>
                                    <th>订单号</th>
                                    <th>已接单</th>
                                    <th>已好评</th>
                                    <th>已上传</th>
                                    <th>自购</th>
                                    <th>退还</th>
                                    <th>完结</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#list resultVO.rows as moteTaskVO>
                                <tr>
                                    <td><a class="text-blue" href="../seller/moteTaskDetail?moteTaskId=${moteTaskVO.id}">${moteTaskVO.nickname?default('')}</a></td>
                                    <td>${moteTaskVO.orderNo?default('')}</td>
                                    <td>是</td>
                                    <td>
                                       <#if moteTaskVO.status gte 2 && moteTaskVO.status!=9>
                                           是
                                       <#else>   
                                           -
                                       </#if>
                                    </td>
                                    <td>
										<#if moteTaskVO.status gte 3 && moteTaskVO.status!=9>
                                           是
                                        <#else>   
                                           -
                                       </#if>
                                    </td>
                                    <td> 
                                      <#if moteTaskVO.status gte 4 && moteTaskVO.status!=9>
                                           是
                                       <#else>   
                                           -    
                                       </#if>
             						</td>
                                    <td>
                                       <#if moteTaskVO.status==5 && moteTaskVO.status!=9>
                                           是
                                       <#else>   
                                           -    
                                       </#if>    
                                    </td>
                                    <td>
                                       <#if moteTaskVO.status==6>
                                           是
                                       <#else>   
                                           -    
                                       </#if>
                                    </td>
                                    <td>
                                       <#if moteTaskVO.finishStatus==0 && moteTaskVO.status==6>
                                           待确认
                                       </#if>
                                      <#if moteTaskVO.finishStatus==1>
                                           已完成
                                       </#if>
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