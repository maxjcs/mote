<!DOCTYPE html>
<html>
<head>
<#include "../inc/header.ftl">
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
                    <li><a><i class="fa-home"></i>指示板</a></li>
                </ol>
            </div>
        <#include "../inc/user.ftl">
        </div>
        <!-- Responsive Table -->
        <div class="row">
           <div class="col-md-12">
                <div class="panel panel-default">
                    <div class="panel-body">
				        <form action="./save" method="post">
		                <table>
		                    <tr>
		                        <td>模特日接单上限:</td>
		                        <td><input type="text" name="moteAcceptNum" value="${config.moteAcceptNum?default(10)}"/></td>
		                    </tr>
		                    <tr>
		                        <td>接单超时时间（分）:</td>
		                        <td><input type="text" name="acceptTimeOut" value="${config.acceptTimeOut?default(30)}"/></td>
		                    </tr>
		                    <tr>
		                        <td colspan=2 ><input type="submit" value="确定" /></td>
		                    </tr>
		                </table>
		               </form>
                    </div>
                </div>
            </div>
        </div>
    <#include "../inc/footer.ftl">
    </div>
</div>
</body>
</html>