<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>排课任务</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling.css" />
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
                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addTask-btn">新建排课任务</button>
                                <button class="btn btn-inverse" id="updateTask-btn">修改</button>
                                <button class="btn btn-success" id="deleteTask-btn">删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="schedule-table" class="table">
                                <thead>
                                <tr>
                                    <th class="center" width="50px">
                                        <label>
                                            <input type="checkbox" class="ace checkAll" id="checkAll" />
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="center" width="80px">序号</th>
                                    <th class="center" width="250px">名称</th>
                                    <th class="center" width="250px">年级</th>
                                    <th class="center" width="250px">学期</th>
                                    <th class="center">操作</th>
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
<script id="task-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" dataId="{{id}}" scheduleName="{{scheduleName}}" gradeName="{{gradeName}}" year="{{year}}" termName="{{termName}}" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{addOne @index}}</td>
        <td class="center" dataId="{{id}}" gradeName="{{gradeName}}" scheduleName="{{scheduleName}}"><a href="/course-scheduling-step3" class="timetable-btn btn-split">{{scheduleName}}</a></td>
        <td class="center">{{gradeName}}</td>
        <td class="center">{{year}}年{{termName}}</td>
        <td class="center" gradeName="{{gradeName}}" scheduleName="{{scheduleName}}" dataId="{{id}}">{{{reStatus status}}}</td>
        </td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp"%>
<link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/datepicker.css">
<script src="<%=ctx%>/static/src/lib/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-scheduling.js"></script>

</body>
</html>
