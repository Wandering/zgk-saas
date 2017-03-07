<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>教学时间</title>
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
                                <a href="/course-scheduling-step1" class="active">教学时间</a>
                            </li>
                            <li>
                                <a href="/course-info">课程信息</a>
                            </li>
                            <li>
                                <a href="/teacher-info">教师信息</a>
                            </li>

                            <li>
                                <a href="/class-info">教室信息</a>
                            </li>
                        </ul>
                        <div class="base-content">
                            <div class="item-title">每周上课天数</div>
                            <ul class="week-list">
                                <li>
                                    <input type="checkbox" data="星期一" id="monday" />
                                    <label for="monday">星期一</label>
                                </li>
                                <li>
                                    <input type="checkbox" data="星期二" id="tuesday" />
                                    <label for="tuesday">星期二</label>
                                </li>
                                <li>
                                    <input type="checkbox" data="星期三" id="wednesday" />
                                    <label for="wednesday">星期三</label>
                                </li>
                                <li>
                                    <input type="checkbox" data="星期四" id="thursday" />
                                    <label for="thursday">星期四</label>
                                </li>
                                <li>
                                    <input type="checkbox" data="星期五" id="friday" />
                                    <label for="friday">星期五</label>
                                </li>
                                <li>
                                    <input type="checkbox" data="星期六" id="saturday" />
                                    <label for="saturday">星期六</label>
                                </li>
                                <li>
                                    <input type="checkbox" data="星期日" id="sunday" />
                                    <label for="sunday">星期日</label>
                                </li>
                            </ul>
                            <div class="item-title">每天上课节次</div>
                            <div class="day-class">
                                <div class="item">
                                    <span>上午：</span>
                                    <select id="morning-list">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                </div>
                                <div class="item">
                                    <span>下午：</span>
                                    <select id="afternoon-list">
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                        <option value="5">5</option>
                                    </select>
                                </div>
                                <div class="item">
                                    <span>晚上：</span>
                                    <select id="evening-list">
                                        <option value="0">0</option>
                                        <option value="1">1</option>
                                        <option value="2">2</option>
                                        <option value="3">3</option>
                                        <option value="4">4</option>
                                    </select>
                                </div>
                            </div>
                            <button type="button" class="btn-save-base" id="btn-save-base">保存</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/course-scheduling/course-scheduling-step1.js"></script>
</body>
</html>
