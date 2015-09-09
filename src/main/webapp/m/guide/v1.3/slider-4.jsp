<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="page">
    <div class="swiper-container swiper-container-horizontal">
        <ul class="swiper-wrapper">
            <li class="swiper-slide">
                <div class="guide-pic">
                    <img class="swiper-lazy" data-src="<%=webServer%>static/images/guide/4-1.jpg"/>
                    <div class="swiper-lazy-preloader"></div>
                </div>
                <div class="desc-container">
                    <div class="desc-body">
                        <h2>功能介绍</h2>

                        <div class="text-center">
                            <p>学生购买你的讲解后</p>

                            <p>如果有疑问，会在这里向你提问</p>

                            <p>以题为例，一对一为他们排忧解难</p>
                        </div>
                    </div>
                </div>
            </li>
            <li class="swiper-slide">
                <div class="guide-pic">
                    <img class="swiper-lazy" data-src="<%=webServer%>static/images/guide/4-2.jpg"/>
                    <div class="swiper-lazy-preloader"></div>
                </div>
                <div class="desc-container">
                    <div class="desc-body">
                        <h2>互动详情</h2>

                        <div class="text-center">
                            <p>与学生交流，内容支持图片</p>

                            <p>点击左下角相机按钮，可上传图片</p>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
        <div class="swiper-pagination"></div>
    </div>
</div>