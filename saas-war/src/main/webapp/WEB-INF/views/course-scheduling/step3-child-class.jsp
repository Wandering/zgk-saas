<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="step3-child-class" class="role-scheduling-content dh">
    <div class="select-condition">
        <select id="select-class">
            <option>201教室</option>
            <option>202教室</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl scheduling-name">行政1班教室课表/走读教室1课程表</div>
        <button class="fr btn btn-warning" id="output-tpl"><i class="icon-output-down"></i>导出所有教室课程表</button>
    </div>
    <table class="table">
        <thead id="grade-thead-list">
            <%--<tr>--%>
            <%--<th></th>--%>
            <%--<th class="center">星期一</th>--%>
            <%--<th class="center">星期二</th>--%>
            <%--<th class="center">星期三</th>--%>
            <%--<th class="center">星期四</th>--%>
            <%--<th class="center">星期五</th>--%>
            <%--</tr>--%>
        </thead>
        <tbody id="grade-tbody-list" class="check-template ui-sortable">
            <%--<tr class="ui-sortable-handle">--%>
                <%--<td class="center index">1</td>--%>
                <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                <%--<td class="center index">历史</td>--%>
                <%--<td class="center index">生物</td>--%>
                <%--<td class="center index">化学</td>--%>
                <%--<td class="center index">通用技术</td>--%>
            <%--</tr>--%>
        </tbody>
    </table>
</div>
<script id="grade-thead-list-template" type="text/x-handlebars-template">
    <tr>
        <th></th>
        {{#each this}}
        <th class="center">{{this}}</th>
        {{/each}}
    </tr>
</script>
<script id="grade-tbody-list-template" type="text/x-handlebars-template">
    {{#with this}}
        <tr class="ui-sortable-handle">
            <td style="margin:0;padding:0">
                    {{{createN this.[0].length}}}
            </td>
            {{#each this}}
                <td style="margin:0;padding:0">
                    {{#each this}}<p style="border-bottom:1px solid #ccc;margin:0;height:35px;line-height:35px;text-align:center">{{this}}</p>{{/each}}
                </td>
            {{/each}}
        </tr>
    {{/with}}
</script>
<script src="<%=ctx%>/static/src/js/course-scheduling/step3-child-class.js"></script>