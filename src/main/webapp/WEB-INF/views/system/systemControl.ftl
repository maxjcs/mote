<!DOCTYPE html>
<html>
    <#include "../inc/head.ftl">
    <body>
        <div id="J_wrapBody" class="global">
            <#include "../inc/side.ftl">
            <div class="content">
              <form action="./setMoteAcceptNum" method="post">
                <table>
                    <tr>
                        <td>模特日接单上限:</td>
                        <td><input type="text" name="moteAcceptNum" value="${moteAcceptNum?default(10)}"/></td>
                        <td><input type="submit" value="确定" /></td>
                    </tr>
                </table>
               </form>
            </div>
        </div>
    </body>
</html>

