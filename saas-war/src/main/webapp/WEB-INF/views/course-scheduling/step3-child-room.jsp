<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--教室课表--%>
<div id="step3-child-room" class="role-scheduling-content">
    <div class="select-condition">
        <select id="select-room">
            <option value="">请选择教室</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl room-name"><span class="room-label"></span></div>
        <button class="fr btn btn-warning output-tpl" id="export-room-table">导出所有教室课程表</button>
    </div>
    <table class="table">
        <thead id="room-thead-list">
        </thead>
        <tbody id="room-tbody-list" class="check-template ui-sortable">
        </tbody>
    </table>
</div>
<script id="room-thead-list-template" type="text/x-handlebars-template">
    <tr>
        {{{thead bizData.result.teachDate}}}
    </tr>
</script>
<script id="room-tbody-list-template" type="text/x-handlebars-template">
    {{{week bizData.result}}}
</script>
