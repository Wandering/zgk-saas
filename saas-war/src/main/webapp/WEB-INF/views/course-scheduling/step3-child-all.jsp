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
        <div class="fl">总课表</div>
        <button class="fr btn btn-warning output-tpl" id="output-tpl"><i class="icon-output-down"></i>导出总课表</button>
    </div>
    <div class="all-time-date-container" style="width: 1100px;overflow-x: auto">
        <table class="table table-bordered" id="all-timetable">

        </table>
    </div>

    <script type="text/x-handlebars-template" id="all-timetable-tpl">
            <thead>
                <tr>
                    <th></th>
                    {{#each teachDate}}
                        <th class="center second-th-width">{{this}}</th>
                    {{/each}}
                </tr>
            </thead>
        <tbody class="check-template ui-sortable">
            <tr>
                <th></th>
                {{#each roomData.[0]}}
                <th class="no-p-m">
                    {{#each this}}
                    <span class="create-class-number common-span-w">{{addOne @index}}</span>
                    {{/each}}
                </th>
                {{/each}}
            </tr>
            {{#each room}}
            <tr>
                <th class="center">{{this}}</th>
                {{#creatClass @index ../roomData}}{{/creatClass}}
            </tr>
            {{/each}}
        </tbody>
    </script>
</div>
