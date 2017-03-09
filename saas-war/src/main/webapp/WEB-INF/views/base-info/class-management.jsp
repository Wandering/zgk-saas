<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 班级管理</title>
    <%@ include file="./../common/meta.jsp"%>
</head>
<body>
<%@ include file="./../common/header.jsp"%>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container' , 'fixed');
        } catch (e) {

        }
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
                    <li class="active">控制台</li>
                </ul><!-- .breadcrumb -->
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main-title">
                            <h3>班级管理</h3>
                        </div>
                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addGrade-btn">+&nbsp;添加班级</button>
                                <button class="btn btn-inverse">修改</button>
                                <button class="btn btn-success">x&nbsp;删除</button>
                                <button class="btn btn-purple">模版下载</button>
                                <button class="btn btn-warning">批量上传</button>
                                <button class="btn btn-danger">班级设置</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="" class="table">
                                <thead>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center">编号</th>
                                        <th class="center">班级名称</th>
                                        <th class="center">班级类型</th>
                                        <th class="center">班级编号</th>
                                        <th class="center">入学年份</th>
                                        <th class="center">班主任</th>
                                        <th class="center">所属年级</th>
                                        <th class="center">班级科类</th>
                                        <th class="center">班级人数</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <td class="center">1</td>
                                        <td class="center">高一年级17班</td>
                                        <td class="center">班级类型</td>
                                        <td class="center">班级编号</td>
                                        <td class="center">2007</td>
                                        <td class="center">马成成</td>
                                        <td class="center">高一</td>
                                        <td class="center">理科</td>
                                        <td class="center">86人</td>
                                    </tr>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <td class="center">2</td>
                                        <td class="center">高一年级17班</td>
                                        <td class="center">班级类型</td>
                                        <td class="center">班级编号</td>
                                        <td class="center">2007</td>
                                        <td class="center">马成成</td>
                                        <td class="center">高一</td>
                                        <td class="center">理科</td>
                                        <td class="center">86人</td>
                                    </tr>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <td class="center">3</td>
                                        <td class="center">高一年级17班</td>
                                        <td class="center">班级类型</td>
                                        <td class="center">班级编号</td>
                                        <td class="center">2007</td>
                                        <td class="center">马成成</td>
                                        <td class="center">高一</td>
                                        <td class="center">理科</td>
                                        <td class="center">86人</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/base-info/class-management.js?v=20170309"></script>
</body>
</html>
