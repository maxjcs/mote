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
				        <form action="./sendMessage" method="post">
			                <table>
			                   <tr>
			                        <td>接收人群:</td>
			                        <td>
			                            <select name="type">
			                                <option value="1">所有用户</option>
			                                <option value="2">所有模特</option>
			                                <option value="3">所有商家</option>
			                            </select>
			                        </td>
			                    </tr>
			                    <tr>
			                       <td colspan=2>
			                        <textarea name="content" rows=10 cols=50></textarea>
			                        </td>
			                    </tr>
			                    <tr>
			                        <td><input type="submit" value="确定" /></td>
			                        <td>&nbsp;</td>
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