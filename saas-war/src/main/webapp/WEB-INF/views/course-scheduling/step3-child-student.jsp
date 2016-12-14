<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--学生课表--%>
<div id="step3-child-student" class="role-scheduling-content">
    <div class="select-condition">
        <select id="select-classes">
            <option value="">请选择班级</option>
        </select>
        <select id="select-student">
            <option value="">请选择学生</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl scheduling-name"><span class="student-label"></span><span class="classes-label"></span></div>
        <button class="fr btn btn-warning output-tpl" id="output-tpl"><i class="icon-output-down"></i>导出学生课程表</button>
    </div>
    <table class="table">
        <thead id="student-thead-list">
        </thead>
        <tbody id="student-tbody-list" class="check-template ui-sortable">
        </tbody>
    </table>
</div>
<script id="student-thead-list-template" type="text/x-handlebars-template">
    <tr>
        <th></th>
        {{#each this}}
        <th class="center">{{this}}</th>
        {{/each}}
    </tr>
</script>
<script id="student-tbody-list-template" type="text/x-handlebars-template">
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