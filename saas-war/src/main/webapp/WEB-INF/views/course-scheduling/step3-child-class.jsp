<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="step3-child-class" class="role-scheduling-content">
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
        <%--<tr class="ui-sortable-handle">--%>
        <%--<td class="center index">2</td>--%>
        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
        <%--<td class="center index">历史</td>--%>
        <%--<td class="center index">生物</td>--%>
        <%--<td class="center index">化学</td>--%>
        <%--<td class="center index">通用技术</td>--%>
        <%--</tr>--%>
        <%--<tr class="ui-sortable-handle">--%>
        <%--<td class="center index">3</td>--%>
        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
        <%--<td class="center index">历史</td>--%>
        <%--<td class="center index">生物</td>--%>
        <%--<td class="center index">化学</td>--%>
        <%--<td class="center index">通用技术</td>--%>
        <%--</tr>--%>
        <%--<tr class="ui-sortable-handle">--%>
        <%--<td class="center index">4</td>--%>
        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
        <%--<td class="center index">历史</td>--%>
        <%--<td class="center index">生物</td>--%>
        <%--<td class="center index">化学</td>--%>
        <%--<td class="center index">通用技术</td>--%>
        <%--</tr>--%>
        <%--<tr class="ui-sortable-handle">--%>
        <%--<td class="center index">5</td>--%>
        <%--<td class="center index">历史（韩嘉琛）行政1班</td>--%>
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
    <%--<tr class="ui-sortable-handle">--%>
    <%--<td class="center index">{{addOne @index}}</td>--%>
    <%--&lt;%&ndash;<td class="center index">历史（韩嘉琛）行政1班</td>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<td class="center index">历史</td>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<td class="center index">生物</td>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<td class="center index">化学</td>&ndash;%&gt;--%>
    <%--&lt;%&ndash;<td class="center index">通用技术</td>&ndash;%&gt;--%>
    <%--</tr>--%>

        <%--<tr class="ui-sortable-handle">--%>
        <%--&lt;%&ndash;{{#each this}}&ndash;%&gt;--%>
            <%--&lt;%&ndash;&lt;%&ndash;<td class="center index">{{addOne @index this}}</td>&ndash;%&gt;&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td class="center index">&ndash;%&gt;--%>

            <%--&lt;%&ndash;</td>&ndash;%&gt;--%>
            <%--&lt;%&ndash;&ndash;%&gt;--%>
        <%--&lt;%&ndash;{{/each}}&ndash;%&gt;--%>
            <%----%>

        <%--</tr>--%>
    <%--<tr>--%>
        <%--<td></td>--%>
    <%--</tr>--%>
    {{#each week}}
    <tr>
        <td>
            {{weekList this}}
        </td>
    </tr>
    {{/each}}






</script>
<script src="<%=ctx%>/static/src/js/course-scheduling/step3-child-class.js"></script>