<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 班级管理</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management.css" />
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
                            <li class="active">班级管理</li>
                        </ul>
                    </div>
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>班级管理</h3>
                                    <div class="top-handle">
                                        <button class="btn-top" id="xz-template-download">行政班&文理科班模板下载</button>
                                        <button class="btn-top hide" id="jx-template-download">教学班模板下载</button>
                                        <button class="btn-top" id="uploadBtn">批量上传</button>
                                        <%--<button class="btn-top" id="student-setting">添加字段</button>--%>
                                    </div>
                                </div>
                                <div class="title-2">
                                    <div id="grade-level" class="grade-level">
                                        <%--<span class="grade-item">--%>
                                            <%--<input type="radio" name="high-school" id="senior-one" />--%>
                                            <%--<label for="senior-one">高一</label>--%>
                                        <%--</span>--%>
                                        <%--<span class="grade-item">--%>
                                            <%--<input type="radio" name="high-school" id="senior-two" />--%>
                                            <%--<label for="senior-two">高二</label>--%>
                                        <%--</span>--%>
                                        <%--<span class="grade-item">--%>
                                            <%--<input type="radio" name="high-school" id="senior-three" />--%>
                                            <%--<label for="senior-three">高三</label>--%>
                                        <%--</span>--%>
                                    </div>
                                    <span class="txt-t"></span>
                                    <div class="btns">
                                        <button class="btn btn-pink" id="addRole-btn">添加班级</button>
                                        <button class="btn btn-inverse" id="updateRole-btn">修改</button>
                                        <button class="btn btn-success" id="deleteClassBtn">删除</button>

                                        <%--<button class="btn btn-warning" id="downloadBtn">模板下载</button>--%>
                                        <%--<button class="btn btn-warning" id="uploadBtn">批量上传</button>--%>
                                        <%--<button class="btn btn-warning" id="class-settings-btn">班级设置</button>--%>
                                    </div>
                                </div>
                                <div class="toggle-tab" id="class-type-toggle">
                                    <button class="tab active" type="class_adm">行政班管理</button>
                                    <button class="tab" type="class_edu">教学班管理</button>
                                </div>
                                <div>
                                    <table id="class-manage-table" class="table">
                                        <thead></thead>
                                        <tbody id="class-manage-list" class="check-template"></tbody>
                                    </table>
                                </div>
                                <div class="pagination-bar">
                                    <div class="pagination"></div>
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
        <script src="<%=ctx%>/static/src/js/info-management/class-management.js"></script>
    </body>
</html>
