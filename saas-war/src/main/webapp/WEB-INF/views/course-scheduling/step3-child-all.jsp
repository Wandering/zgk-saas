<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--总课表--%>
<div id="step3-child-all" class="mt15 role-scheduling-content">
    <div class="scheduling-info">
        <div class="fl scheduling-name">总课表</div>
        <button class="fr btn btn-warning" id="output-tpl"><i class="icon-output-down"></i>导出总课表</button>
    </div>
    <table class="table">
        <thead>
        <tr id="all-timetable-head">
        </tr>
        <script type="text/x-handlebars-template" id="all-timetable-head-tpl">
            <th></th>
            {{#each this}}
            <th class="center">{{this}}</th>
            {{/each}}
        </script>
        </thead>
        <tbody id="all-timetable-body" class="check-template ui-sortable">
        <%--<tr>--%>
            <%--<td class="center">2</td>--%>
            <%--<td class="center">语文（李嘉）行政5班</td>--%>
            <%--<td class="center">历史</td>--%>
            <%--<td class="center">生物</td>--%>
            <%--<td class="center">化学</td>--%>
            <%--<td class="center">通用技术</td>--%>
        <%--</tr>--%>
        </tbody>
        <script id="all-timetable-body-tpl" type="text/x-handlebars-template">
            {{#each this}}
                <tr>
                    {{this}}
                    <%--{{#each this}}--%>
                        <%--<td class="center">this</td>--%>
                    <%--{{/each}}--%>
                </tr>
            {{/each}}
        </script>
    </table>
</div>
