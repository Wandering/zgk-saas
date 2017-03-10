<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 自动排课</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step3.css?v=20170309"/>
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
                            <%@ include file="./../common/footer.jsp" %>
                            <%--<%@ include file="../course-scheduling/step3-common.jsp"%>--%>
                            <script src="/static/src/lib/jquery.table2excel/jquery.table2excel.js"></script>

                            <div class="main-title">
                                <h3>排课任务</h3>
                            </div>
                            <div class="common-back-title">
                                <a href="/course-scheduling">&lt;返回</a>
                                <span class="title scheduleName"></span>
                            </div>
                            <div class="course-scheduling-base">
                                <div class="procedure">
                                    <a href="/course-scheduling-step1" class="disabled"><i>1</i>基本信息设置</a>
                                    <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                    <a href="/course-scheduling-step2" class="disabled"><i>2</i>排课规则设置</a>
                                    <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                    <a href="/course-scheduling-step3"><i>3</i>自动排课</a>
                                </div>
                            </div>
                            <div id="one-key-page" class="">

                                <div class="btn-one-key dh">一键生成课表</div>

                                <%-- 正在排课 --%>
                                <div class="arranging-course-tips dh">
                                    <div class="progress">
                                        <div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                                            <span class="sr-only">100% Complete</span>
                                        </div>
                                    </div>
                                    <p>正在努力排课中,预计<span class="loading-date"></span>分钟,请耐心等待哦</p>
                                </div>

                                <%-- 排出课表 软性规则冲突 --%>
                                <div class="info-modify dh">
                                    <span class="retry-scheduling">重新排课</span>
                                    <a target="_blank" class="rules-links" href="/rules-page">点击查看规则冲突列表</a>
                                </div>

                                <%-- 排课失败 规则提示 --%>
                                <div class="scheduling-error dh">
                                    <i class="icon-cry-face"></i>
                                    <p>排课失败!,请调整基础规则&nbsp;&nbsp;&nbsp;&nbsp;<span class="retry-scheduling">重新排课</span></p>
                                    <ul class="error-box-list"></ul>
                                </div>
                                <%-- 排课失败2 系统错误 规则提示 --%>
                                <div class="scheduling-error2 dh">
                                    <i class="icon-cry-face"></i>
                                    <p>排课失败!&nbsp;&nbsp;&nbsp;&nbsp;<span class="retry-scheduling">重新排课</span></p>
                                </div>
                            </div>
                            <%--教室课表|教师课表|学生课表|总课表--%>
                            <div id="role-scheduling-tab" class="dh">
                                <div class="role-tab">
                                    <ul>
                                        <li class="no-before active"><i class="icon-step3-all-yes"></i><i class="icon-step3-all-no"></i>总课表</li>
                                        <li class="class-tab dh"><i class="icon-step3-classes-yes"></i><i class="icon-step3-classes-no"></i>班级课表</li>
                                        <li><i class="icon-step3-teacher-yes"></i><i class="icon-step3-teacher-no"></i>教师课表</li>
                                        <li class="student-tab dh"><i class="icon-step3-std-yes dh"></i><i class="icon-step3-std-no"></i>学生课表</li>
                                        <li class="room-tab dh"><i class="icon-step3-class-yes"></i><i class="icon-step3-class-no"></i>教室课表</li>
                                    </ul>
                                </div>
                            </div>

                            <div id="control-jsp" class="dh">
                                 <%--总课表--%>
                                <div class="bottom-page">
                                    <%@ include file="../course-scheduling/step3-child-all.jsp"%>
                                </div>
                                     <%--班级课表--%>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-class.jsp"%>
                                </div>
                                     <%--教师课表--%>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-teacher.jsp"%>
                                </div>
                                     <%--学生课表--%>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-student.jsp"%>
                                </div>
                                     <%--教师课表--%>
                                 <div class="bottom-page dh">
                                     <%@ include file="../course-scheduling/step3-child-room.jsp"%>
                                 </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%--<script src="<%=ctx%>/static/src/js/course-scheduling/step3-common.js"></script>--%>
<%--<script src="<%=ctx%>/static/src/js/course-scheduling/step3-child-class.js"></script>--%>
<script src="<%=ctx%>/static/src/js/course-scheduling/step3-child-class.js?v=20170309"></script>
</body>
</html>
