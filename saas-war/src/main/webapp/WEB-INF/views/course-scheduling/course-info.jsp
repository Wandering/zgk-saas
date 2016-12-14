<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>课程信息</title>
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
                            <span class="title">高一排课</span>
                        </div>
                        <div class="course-scheduling-base">
                            <div class="procedure">
                                <a href="javascript: void(0);"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" id="rule-settings" class="disabled"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="base-item-tab">
                            <li>
                                <a href="/course-scheduling-step1">教学时间</a>
                            </li>
                            <li>
                                <a href="/course-info" class="active">课程信息</a>
                            </li>
                            <li>
                                <a href="/teacher-info">教师信息</a>
                            </li>
                        </ul>
                        <div class="base-content">
                            <div class="title-2">
                                <span class="txt-t"></span>
                                <div class="btns">
                                    <button class="btn btn-pink" id="settings-class-btn">设置课时</button>
                                </div>
                            </div>
                            <table id="schedule-table" class="table">
                                <thead>
                                    <tr>
                                        <th class="center" width="50px">
                                            <%--<label>--%>
                                                <%--<input type="checkbox" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        </th>
                                        <th class="center" width="80px">序号</th>
                                        <th class="center" width="250px">课程名称</th>
                                        <th class="center" width="250px">课时</th>
                                    </tr>
                                </thead>
                                <tbody id="schedule-list" class="check-template">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script id="schedule-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" dataId="{{courseId}}" courseName="{{courseName}}" time="{{time}}" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{addOne @index}}</td>
        <td class="center">{{courseName}}</td>
        <td class="center">{{times time}}</td>
        </td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-info.js"></script>
</body>
</html>
