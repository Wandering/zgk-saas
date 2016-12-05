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
                        <div class="main-title">
                            <h3>排课任务</h3>
                        </div>
                        <div id="page-container">
                            <%--一键生成课表--%>
                            <div id="one-key-page" class="dh">
                                <div class="btn-one-key">一键生成课表</div>
                                <div class="info-modify">
                                    <p>基础信息/排课规则已更改，是否重新排课</p>
                                    <div class="retry-scheduling">重新排课</div>
                                    <div class="look-origin-schedule">查看原课表</div>
                                </div>
                                <div class="scheduling-error">
                                    <p>排课失败~  因为*******，所以无法排出课表。请调整**规则/信息后，再进行排课</p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-scheduling-step3.js"></script>
</body>
</html>
