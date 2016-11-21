<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 教师管理</title>
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
                    </ul>
                </div>
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>教师管理</h3>
                            </div>
                            <div class="title-2">
                                <span class="txt-t"></span>
                                <div class="btns">
                                    <button class="btn btn-pink" id="addRole-btn">添加教师</button>
                                    <button class="btn btn-inverse" id="updateRole-btn">修改</button>
                                    <button class="btn btn-success" id="deleteTeacherBtn">删除</button>
                                    <button class="btn btn-warning" id="downloadBtn">模板下载</button>
                                    <button class="btn btn-warning" id="uploadBtn">批量上传</button>
                                    <button class="btn btn-warning" id="teacher-settings-btn">教师设置</button>
                                </div>
                            </div>
                            <div class="">
                                <table id="teacher-manage-table" class="table">
                                    <thead>
                                    <%--<tr>--%>
                                        <%--<th class="center">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        <%--</th>--%>
                                        <%--<th class="center">编号</th>--%>
                                        <%--<th class="center">教师名称</th>--%>
                                        <%--<th class="center">联系电话</th>--%>
                                        <%--<th class="center">性别</th>--%>
                                        <%--<th class="center">年龄</th>--%>
                                        <%--<th class="center">职位</th>--%>
                                        <%--<th class="center">所教年级</th>--%>
                                        <%--<th class="center">所教科目</th>--%>
                                        <%--<th class="center">所带班级</th>--%>
                                    <%--</tr>--%>
                                    </thead>
                                    <tbody id="teacher-manage-list" class="check-template">
                                        <%--<tr>--%>
                                            <%--<td class="center">--%>
                                                <%--<label>--%>
                                                    <%--<input type="checkbox" class="ace" />--%>
                                                    <%--<span class="lbl"></span>--%>
                                                <%--</label>--%>
                                            <%--</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                        <%--</tr>--%>
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
    <link rel="stylesheet" type="text/css" href="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.css">
    <script src="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.js"></script>
    <script>
        var BASE_URL = '<%=ctx%>/static/src/lib/';
        var rootPath = '<%=ctx%>';
    </script>
    <script src="<%=ctx%>/static/src/lib/assets/js/jquery-ui-1.12.1.js"></script>
    <script src="<%=ctx%>/static/src/js/info-management/teacher-management.js"></script>
    </body>
</html>
