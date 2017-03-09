<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SaaS排课规则设置</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step2.css?v=20170309" />
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
                                <a href="/course-scheduling-step1" class="disabled"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="/course-scheduling-step2"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="/course-scheduling-step3" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="rule-item-tab">
                            <li>
                                <a href="/course-scheduling-step2">不排课时间</a>
                            </li>
                            <li>
                                <a href="/course-no-proceed" class="active">不连堂</a>
                            </li>
                            <li>
                                <a href="/class-mixed">合班</a>
                            </li>
                            <li>
                                <a href="/base-rule-settings">基本规则设置</a>
                            </li>
                        </ul>
                        <div class="rule-content">
                            <div id="link-subjects">
                                <div class="item-title">已设连堂科目</div>
                                <table class="proceed-course-table" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th>科目</th>
                                            <th>课时</th>
                                            <th>连堂数</th>
                                            <th>单堂数</th>
                                        </tr>
                                    </thead>
                                    <tbody id="link-list-data">
                                        <%--<tr>--%>
                                        <%--<td>语文</td>--%>
                                        <%--<td>6</td>--%>
                                        <%--<td>1</td>--%>
                                        <%--<td>4</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                        <%--<td>数学</td>--%>
                                        <%--<td>5</td>--%>
                                        <%--<td>1</td>--%>
                                        <%--<td>3</td>--%>
                                        <%--</tr>--%>
                                    </tbody>
                                </table>
                            </div>
                            <div class="item-title">不可连堂节次</div>
                            <ul class="course-no-proceed-list">
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed1" />--%>
                                    <%--<label for="proceed1">1-2节</label>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed2" />--%>
                                    <%--<label for="proceed2">2-3节</label>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed3" />--%>
                                    <%--<label for="proceed3">3-4节</label>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed4" />--%>
                                    <%--<label for="proceed4">4-5节</label>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed5" />--%>
                                    <%--<label for="proceed5">5-6节</label>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed6" />--%>
                                    <%--<label for="proceed6">6-7节</label>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                    <%--<input type="checkbox" id="proceed7" />--%>
                                    <%--<label for="proceed7">7-8节</label>--%>
                                <%--</li>--%>
                            </ul>
                            <%--<div class="conflict-tips">您设置的不连堂节次与已设连堂科目课时冲突！</div>--%>
                            <button type="button" class="btn-save-rule" id="btn-save-link">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-no-proceed.js?v=20170309"></script>
</body>
</html>
