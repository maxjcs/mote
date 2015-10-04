<div class="sidebar-menu toggle-others fixed">
    <div class="sidebar-menu-inner">
        <header class="logo-env">
            <!-- logo -->
            <div class="logo">
                <span class="logo-expanded"><img src="${webServer}assets/images/logo.png"><span>全民模特后台系统</span></span>
                <span class="logo-collapsed"><img src="${webServer}assets/images/logo.png"></span>
            </div>
            <div class="mobile-menu-toggle visible-xs">
                <a href="javascript:;" data-toggle="mobile-menu">
                    <i class="fa-bars"></i>
                </a>
            </div>
        </header>
        <#assign iconJson="{'taobaoExerciseSets':'linecons-doc','revenueManage':'linecons-database','exerciseSets':'linecons-doc','teacherManage':'linecons-user','operate':'linecons-comment','audioLibs':'linecons-music','everydayTask':'linecons-note','organization':'linecons-shop','directional':'linecons-paper-plane','quickAnswer':'linecons-megaphone','teacherSetting':'linecons-params','systemUser':'linecons-cog'}"?eval />
        <ul id="main-menu" class="main-menu">
            <li >
                <a href="javascript:;">
                    <i style="font-family:'iconfont2'">&#xe600</i>
                    <span class="title">主板</span>
                </a>
                <ul>
                    <li>
                        <a href="${webServer}api/back/system/index">
                            <span class="title">指示板</span>
                        </a>
                    </li>
                    <li>
                        <a href="${webServer}api/back/system/systemControl">
                            <span class="title">系统控制</span>
                        </a>
                    </li>
                    <li>
                        <a href="${webServer}api/back/system/message">
                            <span class="title">站内信</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li >
                <a href="javascript:;">
                <i style="font-family:'iconfont2'">&#xe603</i>
                    <span class="title">用户管理模块</span>
                </a>
                <ul>
                    <li>
                        <a href="${webServer}api/back/seller/manage">
                            <span class="title">商家管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="${webServer}api/back/mote/manage">
                            <span class="title">模特管理</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li >
             
                <a href="javascript:;">
                   <i style="font-family:'iconfont2'">&#xe601</i>
                    <span class="title">财务管理模块</span>
                </a>
                <ul>
                    <li>
                        <a href="${webServer}api/back/cash/addCashList">
                            <span class="title">充值管理</span>
                        </a>
                    </li>
                    <li>
                        <a href="${webServer}api/back/cash/reduceCashList">
                            <span class="title">提现管理</span>
                        </a>
                    </li>
                </ul>
            </li>
            <li >
                <a href="javascript:;">
                 <i style="font-family:'iconfont2'">&#xe606</i>
                    <span class="title">自拍秀项目管理</span>
                </a>
                <ul>
                    <li>
                        <a href="${webServer}api/back/task/manage">
                            <span class="title">自拍秀项目管理</span>
                        </a>
                    </li>
                </ul>
            </li>
        </ul>
    </div>
</div>