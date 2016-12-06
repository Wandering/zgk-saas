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
                                <a href="/course-no-proceed">不连堂</a>
                            </li>
                            <li>
                                <a href="/class-mixed" class="active">合班</a>
                            </li>
                            <li>
                                <a href="/base-rule-settings">基本规则设置</a>
                            </li>
                        </ul>
                        <div class="rule-content">
                            <div class="tips-procedure">
                                <div class="procedure-item"><span class="order">1</span>选择课程</div>
                                <div class="procedure-item"><span class="order">2</span>勾选班级</div>
                                <div class="procedure-item"><span class="order">3</span>合班</div>
                            </div>
                            <div class="choose-course">
                                <span>选择课程：</span>
                                <select class="course-select">
                                    <option value="语文">语文</option>
                                    <option value="数学">数学</option>
                                    <option value="英语">英语</option>
                                </select>
                            </div>
                            <div class="choose-class">
                                <div class="title">勾选班级：</div>
                                <ul class="choose-class-list">
                                    <li>
                                        <input type="checkbox" id="class1" />
                                        <label for="class1">高二1班</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" id="class2" />
                                        <label for="class2">高二2班</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" id="class3" />
                                        <label for="class3">高二3班</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" id="class4" />
                                        <label for="class4">高二4班</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" id="class5" />
                                        <label for="class5">高二5班</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" id="class6" />
                                        <label for="class6">高二6班</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" id="class7" />
                                        <label for="class7">高二7班</label>
                                    </li>
                                </ul>
                            </div>
                            <button type="button" class="mixed-class-btn">合班</button>
                            <div class="mixed-class-tips">合班结果：</div>
                            <table class="class-mixed-table" cellpadding="0" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>合班结果</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>语文：高二3班、高二4班</td>
                                        <td><a href="javascript: void(0);">删除合班</a></td>
                                    </tr>
                                    <tr>
                                        <td>语文：高二3班、高二4班</td>
                                        <td><a href="javascript: void(0);">删除合班</a></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/course-scheduling/class-mixed.js"></script>
</body>
</html>