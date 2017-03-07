<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>教师信息</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step1.css" />
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
                            <span class="title scheduleName"></span>
                        </div>
                        <div class="course-scheduling-base">
                            <div class="procedure">
                                <a href="javascript: void(0);"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" id="rule-settings" class="disabled"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" id="auto-assign-course" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="base-item-tab">
                            <li>
                                <a href="/course-scheduling-step1">教学时间</a>
                            </li>
                            <li>
                                <a href="/course-info">课程信息</a>
                            </li>
                            <li>
                                <a href="/teacher-info" class="active">教师信息</a>
                            </li>
                            <li>
                                <a href="/class-info">教室信息</a>
                            </li>
                        </ul>
                        <div class="base-content">
                            <div class="title-2">
                                <span class="txt-t"></span>
                                <%--<div class="btns">--%>
                                    <%--<button class="btn btn-pink" id="add-teacher-btn">添加教师</button>--%>
                                    <%--<button class="btn btn-inverse" id="modify-teacher-btn">修改</button>--%>
                                    <%--<button class="btn btn-success del-btn" id="delete-teacher-btn">删除</button>--%>
                                <%--</div>--%>
                            </div>
                            <table id="teacher-table" class="table">
                                <thead>
                                    <tr>
                                        <%--<th class="center" width="50px">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox" id="checkAll" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        <%--</th>--%>
                                        <th class="center" width="80px">序号</th>
                                        <th class="center" width="200px">教师姓名</th>
                                        <th class="center" width="200px">所授课程</th>
                                        <th class="center" width="100px">最大带班数</th>
                                        <th class="center">所带班级</th>
                                        <th class="center" width="120px">是否排课</th>
                                    </tr>
                                </thead>
                                <tbody id="teacher-list" class="check-template">
                                    <%--<tr>--%>
                                        <%--<td class="center">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        <%--</td>--%>
                                        <%--<td class="center">1</td>--%>
                                        <%--<td class="center">黎明</td>--%>
                                        <%--<td class="center">语文</td>--%>
                                        <%--<td class="center">2</td>--%>
                                        <%--<td class="center">高一二班</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td class="center">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        <%--</td>--%>
                                        <%--<td class="center">2</td>--%>
                                        <%--<td class="center">张华</td>--%>
                                        <%--<td class="center">物理</td>--%>
                                        <%--<td class="center">2</td>--%>
                                        <%--<td class="center">高一三班</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td class="center">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        <%--</td>--%>
                                        <%--<td class="center">3</td>--%>
                                        <%--<td class="center">张华</td>--%>
                                        <%--<td class="center">化学</td>--%>
                                        <%--<td class="center">3</td>--%>
                                        <%--<td class="center">高一四班</td>--%>
                                    <%--</tr>--%>
                                </tbody>
                            </table>
                            <button class="btn-save-base" id="save-teacher" style="margin-top: 20px">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="teacher-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">{{addOne @index}}</td>
        <td class="center">{{teacherName}}</td>
        <td class="center">{{courseName}}</td>
        <td class="center">{{classNum}}</td>
        <td class="center">{{classes}}</td>
        <td>
            {{#if isAttend}}
                <input type="checkbox" id="pai-{{@index}}" style="margin:0 10px"  tId="{{id}}" value="1" checked><label for="pai-{{@index}}">对其排课</label>
            {{else}}
                <input type="checkbox" id="pai-{{@index}}" style="margin:0 10px"  tId="{{id}}" value="1"><label for="pai-{{@index}}">对其排课</label>
            {{/if}}
        </td>
    </tr>
    {{/each}}
</script>



<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/lib/jquery.bigautocomplete/jquery.bigautocomplete.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/teacher-info.js"></script>
</body>
</html>
