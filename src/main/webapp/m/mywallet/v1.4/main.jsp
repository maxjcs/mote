<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="page">
    <div class="wallet-hd">
        <p class="t">当前余额</p>

        <div class="balance">&yen;{{balance | centToYuan}}</div>
    </div>
    <div class="wallet-tabs">
        <a ms-repeat="tabs" ms-class="active:currentTabsIndex==$index" ms-click="switchTabs($index)">
            <span>{{el}}</span>

            <p ms-if="$first">&yen;{{totalPrice | centToYuan}}</p>
            <p ms-if="$last">&yen;{{paidPrice | centToYuan}}</p>
        </a>
    </div>
    <div class="wallet-bd">
        <div class="wallet-content" ms-class="tab-out-left:currentTabsIndex==1">
            <div class="title">累计收入明细</div>
            <div id="recordWrapperA" class="record-wrapper">
                <div>
                    <ul class="record-list">
                        <li ms-repeat="incomeList" ms-class="active:currentItemIndex == $index">
                            <div class="hd" ms-click="getDetailRecord(this,el.month,0)">
                                <div class="t">
                                    {{el.month | date("yyyy年MM月")}}，收入<span>{{el.price | centToYuan}}</span>元
                                </div>
                                <span class="switch"><i class="icon icon-arrow"></i></span>
                            </div>
                            <div class="bd">
                                <div ms-if="!$first" class="loading"></div>
                                <ul class="detail-list detail-list-a">
                                    <li ms-if="el.details.size()==0" class="nodata">无数据</li>
                                    <li ms-repeat="el.details||[]" ms-class="warning:!el.isPlus">
                                        <div><em>{{el.time | date("MM月dd日HH:mm")}}</em><span>{{el.product}}</span></div>
                                        <p><i ms-text="el.isPlus?'+':'-'"></i>{{el.price | centToYuan}}元</p>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                    <div ms-class="show:income.totalcount==0" class="error-layer"><p>暂时没有记录哦！</p></div>
                </div>
            </div>
            <a class="btn-fold-all" ms-class="show:incomeList.length>0"><p>收起</p><p>所有</p></a>
        </div>
        <div class="wallet-content tab-out-right" ms-class="tab-out-right:currentTabsIndex==0">
            <div class="title">已发工资明细</div>
            <div id="recordWrapperB" class="record-wrapper">
                <div>
                    <ul class="record-list">
                        <li ms-repeat="wageList" ms-class="active:currentItemIndex == $index">
                            <div class="hd" ms-click="getDetailRecord(this,el.month,1)">
                                <div class="t">
                                    {{el.month | date("yyyy年MM月")}}{{el.day}}，工资<span>{{el.price | centToYuan}}</span>元
                                </div>
                                <span class="switch"><i class="icon icon-arrow"></i></span>
                            </div>
                            <div class="bd">
                                <div ms-if="!$first" class="loading"></div>
                                <ul class="detail-list detail-list-b">
                                    <li class="nodata" ms-class="show:el.details.size()==0">无数据</li>
                                    <li ms-repeat="el.details||[]" ms-class-1="warning:!el.isPlus" ms-class-2="info:el.isPlus&el.description!=''">
                                        <div><em>{{el.product}}</em></div>
                                        <p><i ms-text="el.isPlus?'+':'-'"></i>{{el.price | centToYuan}}元</p>
                                        <section ms-if="el.description!=''"><label ms-text="el.isPlus?'奖励原因':'扣款原因'"></label>：{{el.description}}</section>
                                    </li>
                                </ul>
                            </div>
                        </li>
                    </ul>
                    <div ms-class="show:wage.totalcount==0" class="error-layer"><p>暂时没有记录哦！</p></div>
                </div>
            </div>
            <a class="btn-fold-all" ms-class="show:wageList.length>0"><p>收起</p><p>所有</p></a>
        </div>
    </div>
</div>