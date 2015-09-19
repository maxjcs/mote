<#--通用宏定义-->


<#--底部通用分页配置-->
<#macro pagenation pageVO formName>
    <#assign totalPages=(pageVO.total/pageVO.pageSize)?ceiling/>
    <#if (totalPages>0)>
    <#assign pagesLengthCfg=9/><#--配置分页长度，显示包括“...”在内的页码数量，必须为奇数-->
    <#assign currentPage=pageVO.index/>
<div class="pagination-wrapper clearfix">
    <div class="pull-left">当前第${currentPage}页，共 ${pageVO.total?default(0)} 条记录</div>
    <ul class="pagination pull-right">
        <li <#if currentPage==1>class="disabled"</#if>"><a href="javascript:;" title="上一页" onclick="Pagination.prev(${formName},${currentPage});"><i class="fa-angle-left"></i></a></li>

        <#--判断总页数如果小于等于分页的长度，若小于则直接显示-->
        <#if totalPages<=pagesLengthCfg-1>
            <#list 1..totalPages as index >
                <li <#if index==currentPage>class="active"</#if>>
                    <a href="javascript:;" onclick="Pagination.jump(${formName},${index});">${index}</a>
                </li>
            </#list>
        <#else>
            <#-- 总页数大于分页长度（此时分为三种情况：1.左边没有...2.右边没有...3.左右都有...）-->
            <#-- 计算中心偏移量-->
            <#assign pagesOffset=(pagesLengthCfg-1)/2/>
            <#if currentPage<=pagesOffset>
                <#--左边没有...-->
                <#list 1..pagesOffset+1 as index>
                    <li <#if index==currentPage>class="active"</#if>>
                        <a href="javascript:;" onclick="Pagination.jump(${formName},${index});">${index}</a>
                    </li>
                </#list>
                <li class="separate"><a>...</a></li>
                <li <#if currentPage==totalPages>class="active"</#if>><a href="javascript:;" onclick="Pagination.jump(${formName},${totalPages})">${totalPages}</a></li>
            <#elseif (totalPages - pagesOffset) < currentPage>
                <#--右边边没有...-->
                <li <#if currentPage==1>class="active"</#if>><a href="javascript:;" onclick="Pagination.jump(${formName},1)">1</a></li>
                <li class="separate"><a>...</a></li>
                <#list pagesOffset+1..1 as index>
                    <li <#if totalPages-index==currentPage>class="active"</#if>>
                        <a href="javascript:;" onclick="Pagination.jump(${formName},${totalPages-index});">${totalPages-index}</a>
                    </li>
                </#list>
                <li <#if currentPage==totalPages>class="active"</#if>><a href="javascript:;" onclick="Pagination.jump(${formName},${totalPages})">${totalPages}</a></li>
            <#else>
                <#--最后一种情况，两边都有...-->
                <li <#if currentPage==1>class="active"</#if>><a href="javascript:;" onclick="Pagination.jump(${formName},1)">1</a></li>
                <li class="separate"><a>...</a></li>
                <#list pagesOffset/2..1 as index>
                    <li <#if index==currentPage>class="active"</#if>>
                        <a href="javascript:;" onclick="Pagination.jump(${formName},${currentPage-index});">${currentPage-index}</a>
                    </li>
                </#list>
                <li class="active"><a href="javascript:;" onclick="Pagination.jump(${formName},${currentPage})">${currentPage}</a></li>
                <#list 1..pagesOffset/2 as index>
                    <li <#if index==currentPage>class="active"</#if>>
                        <a href="javascript:;" onclick="Pagination.jump(${formName},${currentPage+index});">${currentPage+index}</a>
                    </li>
                </#list>
                <li class="separate"><a>...</a></li>
                <li <#if currentPage==totalPages>class="active"</#if>><a href="javascript:;" onclick="Pagination.jump(${formName},${totalPages})">${totalPages}</a></li>
            </#if>
        </#if>

        <li <#if currentPage==totalPages>class="disabled"</#if>"><a href="javascript:;" title="下一页" onclick="Pagination.next(${formName},${currentPage});"><i class="fa-angle-right"></i></a></li>
    </ul>
</div>
<script type="text/javascript">
    (function (W, $) {
        var currentPage = ${currentPage},
                totalPages =${totalPages};
        W.Pagination = {
            jump: function (formName, targetIndex) {
                if (targetIndex == currentPage) return;
                var inputIndex = $(formName).find('input[name="index"]');
                inputIndex.val(targetIndex);
                $(formName).submit();
            },
            prev: function (formName, targetIndex) {
                if (targetIndex == 1)return;
                this.jump(formName, targetIndex - 1);
            },
            next: function (formName, targetIndex) {
                if (targetIndex == totalPages) return;
                this.jump(formName, targetIndex + 1);
            }
        };
    }(window, jQuery));
</script>
    </#if>
</#macro>

