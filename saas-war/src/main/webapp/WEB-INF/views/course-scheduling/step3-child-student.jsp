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
        <select id="select-class">
            <option>历史</option>
            <option>数学</option>
        </select>
        <select id="select-class">
            <option>化学</option>
            <option>历史</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl scheduling-name">学生-小明课程表</div>
        <button class="fr btn btn-warning" id="output-tpl"><i class="icon-output-down"></i>导出学生课程表</button>
    </div>
    <table class="table">
        <thead>
        <tr>
            <th></th>
            <th class="center">星期一</th>
            <th class="center">星期二</th>
            <th class="center">星期三</th>
            <th class="center">星期四</th>
            <th class="center">星期五</th>
        </tr>
        </thead>
        <tbody id="grade-list" class="check-template ui-sortable">
        <tr class="ui-sortable-handle">
            <td class="center index">1</td>
            <td class="center index">英语</td>
            <td class="center index">英语</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">2</td>
            <td class="center index">通用技术</td>
            <td class="center index">化学</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">3</td>
            <td class="center index">生物</td>
            <td class="center index">历史</td>
            <td class="center index">英语</td>
            <td class="center index">化学</td>
            <td class="center index">英语</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">4</td>
            <td class="center index">生物</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">5</td>
            <td class="center index">化学</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">化学</td>
        </tr>
        </tbody>
    </table>
</div>