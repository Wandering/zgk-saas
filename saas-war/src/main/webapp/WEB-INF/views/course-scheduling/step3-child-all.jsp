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
    <table class="table" id="all-timetable">

    </table>
    <script type="text/x-handlebars-template" id="all-timetable-tpl">
        <thead>
            <tr>
                <th></th>
                {{#headerTh teachDate}}{{/headerTh}}
            </tr>
        </thead>
        <%--<tbody class="check-template ui-sortable">--%>
            <%--{{#each this}}--%>
            <%--<tr>--%>
                <%--{{this}}--%>
                <%--&lt;%&ndash;{{#each this}}&ndash;%&gt;--%>
                <%--&lt;%&ndash;<td class="center">this</td>&ndash;%&gt;--%>
                <%--&lt;%&ndash;{{/each}}&ndash;%&gt;--%>
            <%--</tr>--%>
            <%--{{/each}}--%>
        <%--</tbody>--%>
    </script>
</div>
