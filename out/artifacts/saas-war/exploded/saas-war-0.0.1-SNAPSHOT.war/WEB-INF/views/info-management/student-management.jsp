<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 学生管理</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/student-management/student-management.css">
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
                        首页
                    </li>
                    <li>
                        基础信息管理
                    </li>
                    <li class="active"><a href="#">学生管理</a></li>
                </ul><!-- .breadcrumb -->


            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="main-title">
                            <h3>学生管理</h3>
                        </div>

                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="student-add">添加学生</button>
                                <button class="btn btn-inverse" id="student-modify">修改</button>
                                <button class="btn btn-success" id="student-remove">删除</button>
                                <button class="btn btn-green" id="student-template">模板下载</button>
                                <button class="btn btn-warning" id="student-upload">批量上传</button>
                                <button class="btn btn-grey" id="student-setting">学生设置</button>
                            </div>
                        </div>


                        <div class="">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center">
                                        <label>
                                            <input type="checkbox" class="ace"/>
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="center">编号</th>
                                    <th class="center">学生名称</th>
                                    <th class="center">所在年级</th>
                                    <th class="center">所在班级</th>
                                    <th class="center">性别</th>
                                    <th class="center">年龄</th>
                                    <th class="center">入学年份</th>
                                    <th class="center">选择科目</th>
                                    <th class="center">家庭地址</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="center">
                                        <label>
                                            <input type="checkbox" class="ace"/>
                                            <span class="lbl"></span>
                                        </label>
                                    </td>
                                    <td class="center">1</td>
                                    <td class="center">2</td>
                                    <td class="center">3</td>
                                    <td class="center">4</td>
                                    <td class="center">5</td>
                                    <td class="center">6</td>
                                    <td class="center">7</td>
                                    <td class="center">8</td>
                                    <td class="center">9</td>
                                </tr>
                                </tbody>
                            </table>


                            <%--layer-tpl--%>
                            <%--添加学生--%>
                            <div id="student-add-layer" class="dh">
                                <ul class="student-add-box">
                                    <li class="f50">
                                        <span>选择年级</span>
                                        <select id="select-grade"></select>
                                    </li>
                                    <li class="f50">
                                        <span>入校年份</span>
                                        <select id="select-year"></select>
                                    </li>
                                    <li class="f50">
                                        <span>班级类型</span>
                                        <select id="select-class-type">
                                            <option value="00">选择班级类型</option>
                                            <option>重点班</option>
                                            <option>普通班</option>
                                        </select>
                                    </li>
                                    <li class="f50"><span>所在班级</span>
                                        <select id="now-class">
                                            <option value="">所在班级</option>
                                        </select>
                                    </li>
                                    <li>
                                        <span class="f20">选择科目</span>
                                        <div id="subject-list" class="f70">
                                            <label>
                                                <input type="checkbox" class="ace">
                                                <span class="lbl">物理</span>
                                            </label>
                                            <label>
                                                <input type="checkbox" class="ace">
                                                <span class="lbl">化学</span>
                                            </label>
                                        </div>
                                    </li>
                                    <li><span>学生名称</span><input type="text" id="student-name" class="input-common-w"/></li>
                                    <li><span>联系电话</span><input type="text" id="student-tel" class="input-common-w"/></li>
                                    <li><span>联&nbsp;&nbsp;系&nbsp;人</span><input type="text" id="link-name" class="input-common-w"/></li>
                                    <li>
                                        <span class="f20">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</span>
                                        <div id="sex-type" class="f70">
                                            <label>
                                                <input name="form-field-radio" type="radio" class="ace">
                                                <span class="lbl">男</span>
                                            </label>
                                            <label>
                                                <input name="form-field-radio" type="radio" class="ace">
                                                <span class="lbl">女</span>
                                            </label>
                                        </div>
                                    </li>
                                    <li><span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</span><input type="text" id="student-age" class="input-common-w"/></li>
                                    <li><span>家庭住址</span><input type="text" id="student-address" class="input-common-w"/></li>
                                        <div class="opt-btn-box">
                                            <button class="btn btn-info save-btn" id="add-btn">确认添加</button>
                                            <button class="btn btn-primary close-btn">取消</button>
                                        </div>
                                </ul>
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/info-management/student-management.js"></script>
</body>
</html>
