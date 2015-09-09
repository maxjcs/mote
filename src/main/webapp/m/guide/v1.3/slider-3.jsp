<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="page">
    <div class="swiper-container swiper-container-horizontal">
        <ul class="swiper-wrapper">
            <li class="swiper-slide">
                <div class="guide-pic">
                    <img class="swiper-lazy" data-src="<%=webServer%>static/images/guide/3-1.jpg"/>
                    <div class="swiper-lazy-preloader"></div>
                </div>
                <div class="desc-container">
                    <div class="desc-body">
                        <h2>功能介绍</h2>

                        <div class="text-center">
                            <p>进入抢答专区，用音频或白板回答提问</p>

                            <p>录制完成后可获得奖金</p>

                            <p>抢答权限星级要求：3星及以上</p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="swiper-slide">
                <div class="guide-pic">
                    <img class="swiper-lazy" data-src="<%=webServer%>static/images/guide/3-2.jpg"/>
                    <div class="swiper-lazy-preloader"></div>
                </div>
                <div class="desc-container">
                    <div class="desc-body">
                        <h2>抢答列表</h2>

                        <div class="text-center">
                            <p>抢答题目列表中选择适合自己讲解的题目</p>

                            <p>如果没有适合题目</p>

                            <p>下拉抢答列表，刷新试一试</p>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div class="swiper-pagination"></div>
    </div>
</div>