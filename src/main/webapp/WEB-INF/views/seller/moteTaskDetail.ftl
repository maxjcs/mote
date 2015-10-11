<!DOCTYPE html>
<html>
<head>
<#include "../inc/header.ftl">
<#include "../inc/buildcss.ftl">
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
                    <li><a><i class="fa-home"></i>商家管理--项目管理--${task.title?default('')}--任务进程</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
					<div class=" contentWrap" data-reactid=".0.1">
					<div class="user-data" data-reactid=".0.1.1">
					<div class="detail" data-reactid=".0.1.1.0">
					<img width="100" height="100" data-reactid=".0.1.1.0.0" src="${task.imgUrl?default('')}">
					<div data-reactid=".0.1.1.0.1">
						<span data-reactid=".0.1.1.0.1.0">模特:</span>
						<span data-reactid=".0.1.1.0.1.1">${user.nickname?default('')}</span>
					</div>
					<div data-reactid=".0.1.1.0.2">
						<span data-reactid=".0.1.1.0.2.0">性别:</span>
						<span data-reactid=".0.1.1.0.2.1">
			                <#if user.gender?exists>
                                 <#if user.gender==1>男</#if>
                                 <#if user.gender==0>女</#if>
                                 <#if user.gender==3>未知</#if>
                            </#if> 
						</span>
						<span data-reactid=".0.1.1.0.2.2">&nbsp;&nbsp;年龄:</span>
						<span data-reactid=".0.1.1.0.2.3">${user.age?default('')}</span>
						<span data-reactid=".0.1.1.0.2.4">&nbsp;&nbsp;身高:</span>
						<span data-reactid=".0.1.1.0.2.5">${user.height?default('')}</span>
					</div>
					<div data-reactid=".0.1.1.0.3">
						<span data-reactid=".0.1.1.0.3.0">体重:</span>
						<span data-reactid=".0.1.1.0.3.1">${user.weight?default('')}kg</span>
						<span data-reactid=".0.1.1.0.3.2">&nbsp;&nbsp;所在地:</span>
						<span data-reactid=".0.1.1.0.3.3">浙江杭州</span>
					</div>
					</div>
					<ul class="img-list" data-reactid=".0.1.1.1">
					   <#if cardList?exists>
						   <#list cardList as card>
							 <li data-reactid=".0.1.1.1.0"><img width="132" height="132" src="${card.imgUrl}!v1" data-reactid=".0.1.1.1.0.0"></li>
						   </#list>
					   </#if>	   
					</ul>
					</div>
					<div class="user-process" data-reactid=".0.1.2">
					<img width="55" height="55" data-reactid=".0.1.2.0" src="${user.avartUrl?default('')}">
					<div class="line-process" data-reactid=".0.1.2.1">
					</div>
					<ul class="milestone" data-reactid=".0.1.2.2">
					   <li data-reactid=".0.1.2.2.0">
						    <div class="time" data-reactid=".0.1.2.2.0.0">
								<span data-reactid=".0.1.2.2.0.0.0">${moteTask.createTime?string("yyyy-MM-dd HH:mm:ss")}</span>
								<i class="point" data-reactid=".0.1.2.2.0.0.1"></i>
							</div>
							<div class="con" data-reactid=".0.1.2.2.0.1">
							<span data-reactid=".0.1.2.2.0.1.0">我已开始接下该任务</span>
						</li>
						<#if moteTask.acceptedTime?exists >
						<li  data-reactid=".0.1.2.2.1">
						<div class="time" data-reactid=".0.1.2.2.0.0">
							<span data-reactid=".0.1.2.2.0.0.0">${moteTask.acceptedTime?string("yyyy-MM-dd HH:mm:ss")}</span>
							<i class="point" data-reactid=".0.1.2.2.0.0.1"></i>
						</div>
						<div class="con" data-reactid=".0.1.2.2.0.1">
						<span data-reactid=".0.1.2.2.0.1.0">模特确定要接下本次任务,&nbsp;</span>
						<a href="${task.url?default('')}" data-reactid=".0.1.2.2.0.1.1">商品链接点这里</a></div>
						</li>
						</#if>
						<#if moteTask.orderNoTime?exists >
							<li  data-reactid=".0.1.2.2.1">
							<div class="time" data-reactid=".0.1.2.2.1.0">
								<span data-reactid=".0.1.2.2.1.0.0">${moteTask.orderNoTime?string("yyyy-MM-dd HH:mm:ss")}</span>
								<i class="point" data-reactid=".0.1.2.2.1.0.1"></i>
							</div>
							<div class="con" data-reactid=".0.1.2.2.1.1">
								<span data-reactid=".0.1.2.2.1.1.0">模特已在购物平台上下单，</span>
								<span data-reactid=".0.1.2.2.1.1.2">订单号为：</span>
								<span data-reactid=".0.1.2.2.1.1.3">${moteTask.orderNo?default('')}</span>
							</div>
							</li>
						</#if>
						<#if moteTask.showPicTime?exists >
							<li data-reactid=".0.1.2.2.2">
								<div class="time" data-reactid=".0.1.2.2.2.0">
								<span data-reactid=".0.1.2.2.2.0.0">${moteTask.showPicTime?string("yyyy-MM-dd HH:mm:ss")}</span><i class="point" data-reactid=".0.1.2.2.2.0.1"></i>
								</div>
								<div class="con" data-reactid=".0.1.2.2.2.1">模特已在购物平台上确认收货并给予了好评与晒图</div>
							</li>
						</#if>
						<#if moteTask.uploadPicTime?exists >	
							<li data-reactid=".0.1.2.2.3">
								<div class="time" data-reactid=".0.1.2.2.3.0">
								<span data-reactid=".0.1.2.2.3.0.0">${moteTask.uploadPicTime?string("yyyy-MM-dd HH:mm:ss")}</span>
								<i class="point" data-reactid=".0.1.2.2.3.0.1"></i>
								</div>
								<div class="con" data-reactid=".0.1.2.2.3.1">
									<span data-reactid=".0.1.2.2.3.1.0">模特已经上传了</span>
									<span data-reactid=".0.1.2.2.3.1.1">6</span>
									<span data-reactid=".0.1.2.2.3.1.2">张照片</span>
								</div>
							</li>
						</#if>
					<#if moteTask.returnItemTime?exists >		
						<li data-reactid=".0.1.2.2.4">
							<div class="time" data-reactid=".0.1.2.2.4.0">
							<span data-reactid=".0.1.2.2.4.0.0">${moteTask.returnItemTime?string("yyyy-MM-dd HH:mm:ss")}</span>
							<i class="point" data-reactid=".0.1.2.2.4.0.1"></i>
							</div>
							<div class="con" data-reactid=".0.1.2.2.4.1">模特以将商品寄还给雇主，${user.address?default('')}
							</div>
						</li>
					</#if>
					<#if moteTask.finishStatusTime?exists >	
					<li data-reactid=".0.1.2.2.5">
						<div class="time" data-reactid=".0.1.2.2.5.0">
						<i class="point" data-reactid=".0.1.2.2.5.0.0"></i>
						</div>
						<div  data-reactid=".0.1.2.2.5.1">
						<a href="#" class="mui-small-btn" data-reactid=".0.1.2.2.5.1.0">已经收到退件</a>
						</div><div class="hide" data-reactid=".0.1.2.2.5.2">商品退还已确认</div>
					</li>
					</#if>
					</ul>
					</div>
					</div>
				</div>
            </div>
        </div>
    </div>
</div>
</body>
</html>