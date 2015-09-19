<!DOCTYPE html>
<html>
    <#include "../inc/head.ftl">
    <body>
        <div id="J_wrapBody" class="global">
            <#include "../inc/side.ftl">
            <div class="content">
              <form action="./sendMessage" method="post">
                <table>
                   <tr>
                        <td>接手人群:</td>
                        <td>
                            <select name="type">
                                <option value="1">所有用户</option>
                                <option value="2">所有模特</option>
                                <option value="3">所有商家</option>
                            </select>
                        </td>
                        <td>
                            <textarea name="content"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td><input type="submit" value="确定" /></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
              </form>
            </div>
        </div>
    </body>
</html>

