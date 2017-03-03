<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 选课设置</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/select-course.css"/>
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
                        <a href="/index">首页</a>
                    </li>
                    <li>排选课</li>
                    <li class="active">选课设置</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main-title">
                            <h3>选课设置</h3>
                        </div>
                        <div class="settings-box">
                            <h3 class="title"><span class="line"></span>选课相关信息</h3>
                            <ul class="info-list">
                                <li>1.学生选课平台：智高考网站（www.zhigaokao.cn），注册登录后进入<a href="">选课程-我的选课</a>进行选课</li>
                                <li>2.学生选课结束后，学校可在“选课分析”中查看选课结果</li>
                            </ul>
                        </div>
                        <div class="settings-box">
                            <h3 class="title"><span class="line"></span>选课任务名称</h3>
                            <div class="task-list">
                                <h4 class="t-h4">高考课程选课设置</h4>
                                <div class="course-info">学生可选课程：<span class="gk-course-list"></span></div>
                                <div class="course-info">学生选课数：3门</div>
                            </div>
                            <div class="task-list">
                                <h4 class="t-h4">非高考课程选课设置</h4>
                                <div class="course-info">学生可选课程：<span class="fgk-course-list"></span></div>
                                <div class="course-info select-course-max">设置学生选课数：<select
                                        class="select-course-num"></select></div>
                            </div>
                        </div>
                        <div class="btn-box">
                            <button type="button" class="btn btn-info btn-save btn-save-base" id="btn-save-base">提交
                            </button>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->

                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->

<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/course-scheduling/select-course-settings.js"></script>
</body>
</html>
