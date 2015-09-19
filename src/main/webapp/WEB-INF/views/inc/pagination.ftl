<#--底部精简版分页组件-->
<#macro pagenation pageVO formName>
    <#assign currentItemsLength=pageVO.rows?size/>
        <#assign currentPage=pageVO.index/>
    <div class="pagination-wrapper clearfix">
        <div class="pull-left">当前第${currentPage}页</div>
        <ul class="pagination pull-right">
            <li <#if currentPage==1>class="disabled"</#if>"><a href="javascript:;" title="上一页" onclick="Pagination.prev(${formName},${currentPage});"><i class="fa-angle-left"></i></a></li>
            <li <#if currentItemsLength<10>class="disabled"</#if>"><a href="javascript:;" title="下一页" onclick="Pagination.next(${formName},${currentPage});"><i class="fa-angle-right"></i></a></li>
        </ul>
    </div>
    <script type="text/javascript">
        (function (W, $) {
            var currentPage = ${currentPage},
                    currentItemsLength =${currentItemsLength};
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
                    if (currentItemsLength < 10) return;
                    this.jump(formName, targetIndex + 1);
                }
            };
        }(window, jQuery));
    </script>
</#macro>