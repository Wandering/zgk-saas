<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="step3-child-class" class="role-scheduling-content">
    <div class="select-condition">
        <select id="select-class">
            <option value="">请选择教室</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl scheduling-name"></div>
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
            <td class="pm0">
                    {{{createN this.[0].length}}}
            </td>
            {{#each this}}
                <td class="pm0">
                    {{#each this}}<p class="tbody-item">{{this}}</p>{{/each}}
                </td>
            {{/each}}
        </tr>
    {{/with}}
</script>
