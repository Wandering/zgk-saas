<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 角色管理</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-base.css" />
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
                                <a href="javascript: void(0);"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" class="disabled"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="javascript: void(0);" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="base-item-tab">
                            <li>
                                <a href="javascript: void(0);" class="active">教学时间</a>
                            </li>
                            <li>
                                <a href="javascript: void(0);">课程信息</a>
                            </li>
                            <li>
                                <a href="javascript: void(0);">教师信息</a>
                            </li>
                        </ul>
                        <div class="base-content">
                            <div class="item-title">每周上课天数</div>
                            <ul>
                                <li>
                                    <input type="checkbox" id="monday" />
                                    <label for="monday">星期一</label>
                                </li>
                                <li>
                                    <input type="checkbox" id="tuesday" />
                                    <label for="tuesday">星期二</label>
                                </li>
                                <li>
                                    <input type="checkbox" id="wednesday" />
                                    <label for="wednesday">星期三</label>
                                </li>
                                <li>
                                    <input type="checkbox" id="thursday" />
                                    <label for="thursday">星期四</label>
                                </li>
                                <li>
                                    <input type="checkbox" id="friday" />
                                    <label for="friday">星期五</label>
                                </li>
                                <li>
                                    <input type="checkbox" id="saturday" />
                                    <label for="saturday">星期六</label>
                                </li>
                                <li>
                                    <input type="checkbox" id="sunday" />
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
                        <div class="base-content base-content-none">
                            <div class="title-2">
                                <span class="txt-t"></span>
                                <div class="btns">
                                    <button class="btn btn-pink" id="settings-class-btn">设置课时</button>
                                </div>
                            </div>
                            <table id="schedule-table" class="table">
                                <thead>
                                    <tr>
                                        <th class="center" width="50px">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center" width="80px">序号</th>
                                        <th class="center" width="250px">课程名称</th>
                                        <th class="center" width="250px">课时</th>
                                    </tr>
                                </thead>
                                <tbody id="schedule-list" class="check-template">
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center">1</td>
                                        <td class="center">语文</td>
                                        <td class="center">1节/周</td>
                                    </tr>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center">2</td>
                                        <td class="center">数学</td>
                                        <td class="center">2节/周</td>
                                    </tr>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center">3</td>
                                        <td class="center">英语</td>
                                        <td class="center">6节/周</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="base-content base-content-none">
                            <div class="title-2">
                                <span class="txt-t"></span>
                                <div class="btns">
                                    <button class="btn btn-pink" id="add-teacher-btn">添加教师</button>
                                    <button class="btn btn-inverse" id="modify-teacher-btn">修改</button>
                                    <button class="btn btn-success del-btn" id="delete-teacher-btn">删除</button>
                                </div>
                            </div>
                            <table id="teacher-table" class="table">
                                <thead>
                                    <tr>
                                        <th class="center" width="50px">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center" width="80px">序号</th>
                                        <th class="center" width="250px">教师姓名</th>
                                        <th class="center" width="250px">所授课程</th>
                                        <th class="center" width="250px">最大带班数</th>
                                        <th class="center">所带班级</th>
                                    </tr>
                                </thead>
                                <tbody id="teacher-list" class="check-template">
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center">1</td>
                                        <td class="center">黎明</td>
                                        <td class="center">语文</td>
                                        <td class="center">2</td>
                                        <td class="center">高一二班</td>
                                    </tr>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center">2</td>
                                        <td class="center">张华</td>
                                        <td class="center">物理</td>
                                        <td class="center">2</td>
                                        <td class="center">高一三班</td>
                                    </tr>
                                    <tr>
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center">3</td>
                                        <td class="center">张华</td>
                                        <td class="center">化学</td>
                                        <td class="center">3</td>
                                        <td class="center">高一四班</td>
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
<script src="<%=ctx%>/static/src/js/course-scheduling/course-scheduling-base.js"></script>
</body>
</html>
