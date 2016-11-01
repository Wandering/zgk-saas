<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 教室管理</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management.css">
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
                    <li class="active">控制台</li>
                </ul><!-- .breadcrumb -->


            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="main-title">
                            <h3>教室管理</h3>
                        </div>

                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="add-btn">添加教室</button>
                                <button class="btn btn-inverse">修改</button>
                                <button class="btn btn-success">删除</button>
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
                                    <th class="center">年级</th>
                                    <th class="center">教室数量</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="center">
                                        <label>
                                            <input type="checkbox" class="ace" />
                                            <span class="lbl"></span>
                                        </label>
                                    </td>
                                    <td class="center">1</td>
                                    <td class="center">1</td>
                                    <td class="center">1</td>
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
<script src="<%=ctx%>/static/src/js/info-management/classroom-management.js"></script>
</body>
</html>
