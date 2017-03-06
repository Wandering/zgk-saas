<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>教室信息</title>
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
                                <a href="/teacher-info">教师信息</a>
                            </li>
                            <li>
                                <a href="/class-info" class="active">教室信息</a>
                            </li>
                        </ul>
                        <div class="base-content">
                            <div class="title-2">
                                <span class="txt-t"><span class="grade-now"></span>教室信息</span>
                            </div>
                            <div class="scheduling-course">
                                <p><span class="grade-now"></span>行政班数量： <span id="class-number"></span></p>
                                <label for="optional-class"> <em>*</em>可排课教室数量 <input type="text" id="optional-class"> </label>
                                <p class="warm-tip">提醒：可排教室数量不能小于行政班的班级数量，请修改可排课教室数量。</p>
                            </div>
                            <button class="btn-save-base" id="save-class" style="margin-top: 20px">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>



<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/lib/jquery.bigautocomplete/jquery.bigautocomplete.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/class-info.js"></script>
</body>
</html>
