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
        <tr>
            <td class="center">1</td>
            <td class="center">英语（韩嘉琛）行政1班</td>
            <td class="center">历史</td>
            <td class="center">生物</td>
            <td class="center">化学</td>
            <td class="center">通用技术</td>
        </tr>
        <tr>
            <td class="center">2</td>
            <td class="center">语文（李嘉）行政5班</td>
            <td class="center">历史</td>
            <td class="center">生物</td>
            <td class="center">化学</td>
            <td class="center">通用技术</td>
        </tr>
        </tbody>
    </table>
</div>
