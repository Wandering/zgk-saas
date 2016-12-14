<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SaaS排课规则设置</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step2.css" />
</head>
<body>
<%@ include file="./../common/header.jsp"%>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@ include file="./../common/sidebar.jsp"%>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
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
                        <div class="main-title">
                            <h3>排课任务</h3>
                        </div>
                        <div class="common-back-title">
                            <a href="/course-scheduling">&lt;返回</a>
                            <span class="title">高一排课</span>
                        </div>
                        <div class="course-scheduling-base">
                            <div class="procedure">
                                <a href="/course-scheduling-step1" class="disabled"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="/course-scheduling-step2"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="/course-scheduling-step3" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="rule-item-tab">
                            <li>
                                <a href="/course-scheduling-step2" class="active">不排课时间</a>
                            </li>
                            <li>
                                <a href="/course-no-proceed">不连堂</a>
                            </li>
                            <li>
                                <a href="/class-mixed">合班</a>
                            </li>
                            <li>
                                <a href="/base-rule-settings">基本规则设置</a>
                            </li>
                        </ul>
                        <div class="rule-content">
                            <select id="no-course-time">
                                <option value="class">班级不排课时间</option>
                                <option value="teacher">教师不排课时间</option>
                                <option value="course">课程不排课时间</option>
                            </select>
                            <div class="no-course-time">
                                <div class="class-no-course">
                                    <div class="class-items">
                                        <div id="class-no-array" class="class-no-array">
                                            <div class="title">高一</div>
                                            <ul id="class-list" class="class-list">
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);" class="active">全部班级</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">一班</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">二班</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">三班</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">四班</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">五班</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">六班</a>--%>
                                                <%--</li>--%>
                                            </ul>
                                        </div>
                                        <div id="teacher-no-array" class="teacher-no-array">
                                            <select id="teacher-course" class="teaching-class">
                                                <%--<option value="00">选择所授课程</option>--%>
                                                <%--<option value="语文">语文</option>--%>
                                                <%--<option value="数学">数学</option>--%>
                                                <%--<option value="英语">英语</option>--%>
                                                <%--<option value="物理">物理</option>--%>
                                                <%--<option value="政治">政治</option>--%>
                                                <%--<option value="历史">历史</option>--%>
                                                <%--<option value="地理">地理</option>--%>
                                            </select>
                                            <ul id="teacher-list" class="teacher-list">
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);" class="active">李华</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">李小龙</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">周杰伦</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">陈港生</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">成龙</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">周润发</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">鹿晗</a>--%>
                                                <%--</li>--%>
                                            </ul>
                                        </div>
                                        <div id="course-no-array" class="course-no-array">
                                            <ul id="course-list" class="course-list">
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);" class="active">语文</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">数学</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">英语</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">物理</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">化学</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">生物</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">政治</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">历史</a>--%>
                                                <%--</li>--%>
                                                <%--<li>--%>
                                                    <%--<a href="javascript: void(0);">地理</a>--%>
                                                <%--</li>--%>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="class-assign-time">
                                        <table id="no-assign-table" class="no-assign-table" cellpadding="0" cellspacing="0">
                                            <thead>
                                                <%--<tr>--%>
                                                    <%--<th width="135px"></th>--%>
                                                    <%--<th>星期一</th>--%>
                                                    <%--<th>星期二</th>--%>
                                                    <%--<th>星期三</th>--%>
                                                    <%--<th>星期四</th>--%>
                                                    <%--<th>星期五</th>--%>
                                                <%--</tr>--%>
                                            </thead>
                                            <tbody>
                                                <%--<tr>--%>
                                                    <%--<td>1</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                <%--</tr>--%>
                                                <%--<tr>--%>
                                                    <%--<td>2</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                <%--</tr>--%>
                                                <%--<tr>--%>
                                                    <%--<td>3</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                <%--</tr>--%>
                                                <%--<tr>--%>
                                                    <%--<td>4</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td></td>--%>
                                                <%--</tr>--%>
                                                <%--<tr>--%>
                                                    <%--<td>5</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                <%--</tr>--%>
                                                <%--<tr>--%>
                                                    <%--<td>6</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                <%--</tr>--%>
                                                <%--<tr>--%>
                                                    <%--<td>7</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td></td>--%>
                                                    <%--<td>不排课</td>--%>
                                                    <%--<td>不排课</td>--%>
                                                <%--</tr>--%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <button type="button" class="btn-save-rule hide" id="btn-save-assign">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-scheduling-step2.js"></script>
</body>
</html>
