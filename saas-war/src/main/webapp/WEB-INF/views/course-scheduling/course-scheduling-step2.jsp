<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 角色管理</title>
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
                                <a href="javascript: void(0);" class="disabled"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="rule-item-tab">
                            <li>
                                <a href="javascript: void(0);" class="active">不排课时间</a>
                            </li>
                            <li>
                                <a href="javascript: void(0);">不连堂</a>
                            </li>
                            <li>
                                <a href="javascript: void(0);">合班</a>
                            </li>
                            <li>
                                <a href="javascript: void(0);">基本规则设置</a>
                            </li>
                        </ul>
                        <div class="rule-content">
                            <select id="no-course-time">
                                <option value="1">班级不排课时间</option>
                                <option value="2">教师不排课时间</option>
                                <option value="3">课程不排课时间</option>
                            </select>
                            <div class="no-course-time">
                                <div class="class-no-course">

                                </div>
                            </div>
                        </div>
                        <div class="rule-content rule-content-none">
                            不连堂
                        </div>
                        <div class="rule-content rule-content-none">
                            合班
                        </div>
                        <div class="rule-content rule-content-none">
                            基本规则设置
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
