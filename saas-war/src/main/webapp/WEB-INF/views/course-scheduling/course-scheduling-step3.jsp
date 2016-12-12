<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 角色管理</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step3.css"/>
</head>
<body>
<%@ include file="./../common/header.jsp" %>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@ include file="./../common/sidebar.jsp" %>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>
                <ul class="breadcrumb">
                    <li>
                        <a href="#">首页</a>
                    </li>
                    <li>排选课</li>
                    <li class="active">排课任务</li>
                </ul>
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="page-container">
                            <%@ include file="../course-scheduling/step3-common.jsp"%>
                            <div id="control-jsp">
                                <div class="bottom-page">
                                    <%@ include file="../course-scheduling/step3-child-class.jsp"%>
                                </div>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-teacher.jsp"%>
                                </div>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-student.jsp"%>
                                </div>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-all.jsp"%>
                                </div>
                            </div>

                            <%--<jsp:include page="../course-scheduling/step3-common.jsp" flush="true">--%>
                                <%--<jsp:param name="ref1" value="AAA"/>--%>
                                <%--<jsp:param name="ref2" value="BBB"/>--%>
                            <%--</jsp:include>--%>
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
                            <%--一键生成课表--%>
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
                            <%--&lt;%&ndash;教室课表|教师课表|学生课表|总课表&ndash;%&gt;--%>
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
                            <%--课表生成后table导出--%>
                            <%--<div id="role-scheduling-content">--%>
                                <%--<div class="select-condition">--%>
                                    <%--<select id="select-class">--%>
                                        <%--<option>201教室</option>--%>
                                        <%--<option>202教室</option>--%>
                                    <%--</select>--%>
                                <%--</div>--%>
                                <%--<div class="scheduling-info">--%>
                                    <%--<div class="fl scheduling-name">行政1班教室课表/走读教室1课程表</div>--%>
                                    <%--<button class="fr btn btn-warning" id="output-tpl"><i class="icon-output-down"></i>导出所有教室课程表</button>--%>
                                <%--</div>--%>
                                <%--<table class="table">--%>
                                    <%--<thead>--%>
                                    <%--<tr>--%>
                                        <%--<th></th>--%>
                                        <%--<th class="center">星期一</th>--%>
                                        <%--<th class="center">星期二</th>--%>
                                        <%--<th class="center">星期三</th>--%>
                                        <%--<th class="center">星期四</th>--%>
                                        <%--<th class="center">星期五</th>--%>
                                    <%--</tr>--%>
                                    <%--</thead>--%>
                                    <%--<tbody id="grade-list" class="check-template ui-sortable">--%>
                                    <%--<tr class="ui-sortable-handle">--%>
                                        <%--<td class="center index">1</td>--%>
                                        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                                        <%--<td class="center index">历史</td>--%>
                                        <%--<td class="center index">生物</td>--%>
                                        <%--<td class="center index">化学</td>--%>
                                        <%--<td class="center index">通用技术</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr class="ui-sortable-handle">--%>
                                        <%--<td class="center index">1</td>--%>
                                        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                                        <%--<td class="center index">历史</td>--%>
                                        <%--<td class="center index">生物</td>--%>
                                        <%--<td class="center index">化学</td>--%>
                                        <%--<td class="center index">通用技术</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr class="ui-sortable-handle">--%>
                                        <%--<td class="center index">1</td>--%>
                                        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                                        <%--<td class="center index">历史</td>--%>
                                        <%--<td class="center index">生物</td>--%>
                                        <%--<td class="center index">化学</td>--%>
                                        <%--<td class="center index">通用技术</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr class="ui-sortable-handle">--%>
                                        <%--<td class="center index">1</td>--%>
                                        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                                        <%--<td class="center index">历史</td>--%>
                                        <%--<td class="center index">生物</td>--%>
                                        <%--<td class="center index">化学</td>--%>
                                        <%--<td class="center index">通用技术</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr class="ui-sortable-handle">--%>
                                        <%--<td class="center index">1</td>--%>
                                        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                                        <%--<td class="center index">历史</td>--%>
                                        <%--<td class="center index">生物</td>--%>
                                        <%--<td class="center index">化学</td>--%>
                                        <%--<td class="center index">通用技术</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr class="ui-sortable-handle">--%>
                                        <%--<td class="center index">1</td>--%>
                                        <%--<td class="center index">英语（韩嘉琛）行政1班</td>--%>
                                        <%--<td class="center index">历史</td>--%>
                                        <%--<td class="center index">生物</td>--%>
                                        <%--<td class="center index">化学</td>--%>
                                        <%--<td class="center index">通用技术</td>--%>
                                    <%--</tr>--%>
                                    <%--</tbody>--%>
                                <%--</table>--%>
                            <%--</div>--%>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="<%=ctx%>/static/src/js/course-scheduling/step3-common.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-scheduling-step3.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/step3-child-class.js"></script>
</body>
</html>
