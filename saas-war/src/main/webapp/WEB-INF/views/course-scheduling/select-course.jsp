<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>选课任务</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/select-course.css?v=20170309" />
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
                    <li class="active">选课任务</li>
                </ul>
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="main-title">
                            <h3>选课任务</h3>
                        </div>
                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addTask-btn">新建选课任务</button>
                                <button class="btn btn-inverse" id="updateTask-btn">修改</button>
                                <button class="btn btn-success" id="deleteTask-btn">删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="select-course-table" class="table">
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
                                    <th class="center">年级</th>
                                    <th class="center">开始时间</th>
                                    <th class="center">结束时间</th>
                                    <th class="center">状态</th>
                                </tr>
                                </thead>
                                <tbody id="select-course-list" class="check-template">
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
                <input type="checkbox" dataId="{{id}}" selectCourseName="{{name}}" gradeName="{{grade}}" state = "{{state}}" startTime="{{FormatTime startTime}}" endTime="{{FormatTime endTime}}" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{addOne @index}}</td>
        <td class="center" gradeName="{{grade}}" selectCourseName="{{name}}" dataId="{{id}}"><a state="{{state}}" href="javascript:;" {{{reStatusClass state}}}>{{name}}</a></td>
        <td class="center">{{gradeTxt grade}}</td>
        <td class="center">{{FormatTime startTime}}</td>
        <td class="center">{{FormatTime endTime}}</td>
        <td class="center" gradeName="{{grade}}" selectCourseName="{{name}}" dataId="{{id}}">{{{reStatus state}}}</td>
        </td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp"%>
<link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/js/layui/css/layui.css"  media="all">
<script src="<%=ctx%>/static/src/lib/assets/js/layui/layui.js" charset="utf-8"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/select-course.js?v=20170309"></script>

</body>
</html>
