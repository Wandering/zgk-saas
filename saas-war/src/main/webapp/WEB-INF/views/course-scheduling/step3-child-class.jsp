<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="step3-child-class" class="role-scheduling-content">
    <div class="select-condition">
        <select id="select-class">
            <option value="">请选择班级</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl scheduling-name"></div>
        <button class="fr btn btn-warning output-tpl" id="output-tpl"><i class="icon-output-down"></i>导出所有班级课程表</button>
    </div>
    <table class="table" id="test1">
        <thead id="room-thead-list"></thead>
        <tbody id="room-tbody-list" class="check-template ui-sortable"></tbody>
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