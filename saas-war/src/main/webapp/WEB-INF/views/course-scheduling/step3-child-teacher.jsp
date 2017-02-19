<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--教师课表--%>
<div id="step3-child-teacher" class="role-scheduling-content">
    <div class="select-condition">
        <select id="select-queryCourse">
            <option value="">请选择科目</option>
        </select>
        <select id="select-teacher">
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl teacher-name"><span class="teacher-label"></span><span class="course-label"></span></div>
        <button class="fr btn btn-warning output-tpl" id="output-tpl"><i class="icon-output-down"></i>导出所有教师课程表</button>
    </div>
    <table class="table">
        <thead id="teacher-thead-list">
        </thead>
        <tbody id="teacher-tbody-list" class="check-template ui-sortable">
        </tbody>
    </table>
</div>
<script id="teacher-thead-list-template" type="text/x-handlebars-template">
    <tr>
        {{{thead bizData.result.teachDate}}}
    </tr>
</script>
<script id="teacher-tbody-list-template" type="text/x-handlebars-template">
    {{{week bizData.result}}}
</script>
