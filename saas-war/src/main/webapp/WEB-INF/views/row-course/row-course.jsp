<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 角色管理</title>
    <%@ include file="../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/role-management.css?v=20170309">
</head>
<body>
<%@ include file="../common/header.jsp"%>
<div class="main-container" id="main-container">
    <script type="text/javascript">
        try{ace.settings.check('main-container' , 'fixed')}catch(e){}
    </script>
    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>
        <%@ include file="../common/sidebar.jsp"%>
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
                            <h3>角色管理</h3>
                        </div>

                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addRole-btn">添加角色</button>
                                <button class="btn btn-inverse" id="modify-btn">修改</button>
                                <button class="btn btn-success" id="close-btn">删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center">
                                        <%--<label>--%>
                                            <%--<input type="checkbox" id="checkAll" class="ace" />--%>
                                            <%--<span class="lbl"></span>--%>
                                        <%--</label>--%>
                                    </th>
                                    <th class="center">角色名称</th>
                                    <th class="center">角色内容</th>
                                    <th class="center">创建时间</th>
                                </tr>
                                </thead>
                                <tbody id="role-tbody" class="check-template"></tbody>
                            </table>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="role-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" roleid="{{id}}" roleName="{{roleName}}" roleDesc="{{roleDesc}}" class="ace" />
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{roleName}}</td>
        <td class="center">{{roleDesc}}</td>
        <td class="center">{{FormatTime createDate}}</td>
    </tr>
    {{/each}}
</script>
<%@ include file="../common/footer.jsp"%>
<link rel="stylesheet" href="<%=ctx%>/static/src/lib/ztree/zTreeStyle.css">
<script src="<%=ctx%>/static/src/lib/ztree/jquery.ztree.core.js"></script>
<script src="<%=ctx%>/static/src/lib/ztree/jquery.ztree.excheck.js"></script>
<script src="<%=ctx%>/static/src/js/account/role-management.js?v=20170309"></script>
</body>
</html>
