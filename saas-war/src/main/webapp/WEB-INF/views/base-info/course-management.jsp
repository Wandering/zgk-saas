<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>基础信息设置-课程管理</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management.css">
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
                            <h3>课程管理</h3>
                        </div>
                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addCourse-btn">+&nbsp;添加课程</button>
                                <button class="btn btn-inverse" id="updateCourse-btn">修改</button>
                                <button class="btn btn-success" id="deleteCourse-btn">x&nbsp;删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="" class="table">
                                <thead>
                                    <tr>
                                        <th class="center"></th>
                                        <th class="center">序号</th>
                                        <th class="center">课程名称</th>
                                        <th class="center">开课年级</th>
                                        <th class="center">高一年级课程类型</th>
                                        <th class="center">高二年级课程类型</th>
                                        <th class="center">高三年级课程类型</th>
                                    </tr>
                                </thead>
                                <tbody id="course-tbody">

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
<%--<script id="course-tbody-template" type="text/x-handlebars-template">--%>
    <%--{{#each this}}--%>
    <%--<tr>--%>
        <%--<th class="center">--%>
            <%--<label>--%>
                <%--<input type="checkbox" dataId="{{addOne @index}}" class="ace" />--%>
                <%--<span class="lbl"></span>--%>
            <%--</label>--%>
        <%--</th>--%>
        <%--<td class="center">{{addOne @index}}</td>--%>
        <%--<td class="center">{{this}}</td>--%>
        <%--<td class="center"></td>--%>
        <%--<td class="center"></td>--%>
        <%--<td class="center"></td>--%>
        <%--<td class="center"></td>--%>
    <%--</tr>--%>
    <%--{{/each}}--%>
<%--</script>--%>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/base-info/course-management.js"></script>
</body>
</html>
