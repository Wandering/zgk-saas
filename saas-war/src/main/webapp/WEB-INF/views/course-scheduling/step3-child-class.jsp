<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<div class="main-title">--%>
    <%--<h3>排课任务</h3>--%>
<%--</div>--%>
<%--<div class="course-scheduling-base">--%>
    <%--<div class="procedure">--%>
        <%--<a href="javascript: void(0);" class="disabled"><i>1</i>基本信息设置</a>--%>
        <%--<span class="gap"><i></i><i></i><i></i><i></i><i></i></span>--%>
        <%--<a href="javascript: void(0);" class="disabled"><i>2</i>排课规则设置</a>--%>
        <%--<span class="gap"><i></i><i></i><i></i><i></i><i></i></span>--%>
        <%--<a href="javascript: void(0);"><i>3</i>自动排课</a>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<div id="one-key-page" class="dh">--%>
    <%--<div class="btn-one-key">一键生成课表</div>--%>
    <%--<div class="info-modify">--%>
        <%--<p>基础信息/排课规则已更改，是否重新排课</p>--%>
        <%--<div class="retry-scheduling">重新排课</div>--%>
        <%--<div class="look-origin-schedule">查看原课表</div>--%>
    <%--</div>--%>
    <%--<div class="scheduling-error">--%>
        <%--<p>排课失败~ 因为*******，所以无法排出课表。请调整**规则/信息后，再进行排课</p>--%>
    <%--</div>--%>
<%--</div>--%>
<%--教室课表|教师课表|学生课表|总课表--%>
<%--<div id="role-scheduling-tab" class="">--%>
    <%--<div class="role-tab">--%>
        <%--<ul>--%>
            <%--<li class="no-before active"><i class="icon-step3-class-yes"></i><i--%>
                    <%--class="icon-step3-class-no"></i>教室课表--%>
            <%--</li>--%>
            <%--<li><i class="icon-step3-teacher-yes"></i><i class="icon-step3-teacher-no"></i>教师课表--%>
            <%--</li>--%>
            <%--<li><i class="icon-step3-std-yes"></i><i class="icon-step3-std-no"></i>学生课表</li>--%>
            <%--<li><i class="icon-step3-all-yes"></i><i class="icon-step3-all-no"></i>总课表</li>--%>
        <%--</ul>--%>
    <%--</div>--%>
<%--</div>--%>
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
            <td class="center index">英语（韩嘉琛）行政1班</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">2</td>
            <td class="center index">英语（韩嘉琛）行政1班</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">3</td>
            <td class="center index">英语（韩嘉琛）行政1班</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">4</td>
            <td class="center index">英语（韩嘉琛）行政1班</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        <tr class="ui-sortable-handle">
            <td class="center index">5</td>
            <td class="center index">历史（韩嘉琛）行政1班</td>
            <td class="center index">历史</td>
            <td class="center index">生物</td>
            <td class="center index">化学</td>
            <td class="center index">通用技术</td>
        </tr>
        </tbody>
    </table>
</div>