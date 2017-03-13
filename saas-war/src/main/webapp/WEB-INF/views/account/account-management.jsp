<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 账号管理</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/role-management.css?v=20170309">
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
                        首页
                    </li>
                    <li class="active">控制台</li>
                </ul><!-- .breadcrumb -->


            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="main-title">
                            <h3>账号管理</h3>
                        </div>

                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addAccount-btn">添加账号</button>
                                <button class="btn btn-inverse" id="modify-btn">修改账号</button>
                                <button class="btn btn-danger" id="resetPass-btn">重设密码</button>
                                <button class="btn btn-success" id="close-btn">删除</button>
                            </div>
                        </div>


                        <div class="">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center"></th>
                                    <th class="center">登陆账号</th>
                                    <th class="center">用户名</th>
                                    <th class="center">联系方式</th>
                                    <th class="center">权限</th>
                                    <th class="center">状态</th>
                                    <th class="center">创建时间</th>
                                </tr>
                                </thead>
                                <tbody id="account-tbody"></tbody>
                            </table>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="account-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" role="{{role}}" roleId="{{roleId}}" status="{{status}}" telephone="{{telephone}}" userAccount="{{userAccount}}" userId="{{userId}}" userName="{{userName}}" class="ace" />
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{userAccount}}</td>
        <td class="center">{{userName}}</td>
        <td class="center">{{telephone}}</td>
        <td class="center">{{role}}</td>
        <td class="center">{{statusV status}}</td>
        <td class="center">{{FormatTime createDate}}</td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/lib/md5/jQuery.md5.js"></script>
<script src="<%=ctx%>/static/src/js/account/account-management.js?v=20170309"></script>
</body>
</html>
