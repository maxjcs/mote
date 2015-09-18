<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
        <title>React</title>
        <link rel="stylesheet" type="text/css" href="${webServer}assets/res/css/common/base.css">
        <link rel="stylesheet" type="text/css" href="${webServer}assets/res/css/business/common.css">
        <link rel="stylesheet" type="text/css" href="${webServer}assets/res/css/business/message.css">
        
    </head>
    <body>
        <div id="J_wrapBody" class="global">
            <div class="side">
                <div class="category">全民模特 系统后台</div>
                <dl>
                    <dt>主板</dt>
                    <dd><a href="./index">指示板</a></dd>
                    <dd><a href="./systemControl">系统控制</a></dd>
                    <dd class="active"><a href="./message">站内信</a></dd>
                </dl>

                <dl>
                    <dt>用户管理模块</dt>
                    <dd><a href="./fund-process">商家管理</a></dd>
                    <dd><a href="./fund-add">模特管理</a></dd>
                </dl>

                <dl>
                    <dt>财务管理模块</dt>
                    <dd><a href="#">充值管理</a></dd>
                    <dd><a href="#">体现管理</a></dd>
                </dl>

                <dl>
                    <dt>自拍秀任务管理模块</dt>
                    <dd><a href="#">项目管理</a></dd>
                </dl>
            </div>
            <div class="content">
                <table>
                    <tr>
                        <td>接手人群:</td>
                        <td>
                            <select>
                                <option>所有模特</option>
                                <option>所有商家</option>
                                <option>所有用户</option>
                            </select>
                        </td>

                        <td>
                            <textarea></textarea>
                        </td>
                    </tr>

                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="button" value="确定" /></td>
                        <td>&nbsp;</td>
                    </tr>
                    
                </table>
            </div>
        </div>
    </body>
</html>

