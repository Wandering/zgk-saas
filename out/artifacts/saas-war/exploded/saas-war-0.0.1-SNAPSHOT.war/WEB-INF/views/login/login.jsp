<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 登录</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/login/login.css">
</head>
<body>
<div class="navbar navbar-default" id="navbar">
    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="javascript:;" class="navbar-brand">
                <h1 id="logo"><img class="" src="<%=ctx%>/static/src/lib/assets/images/logo.png"/></h1> <span
                    class="school-name" id="header-school-name"></span>
            </a><!-- /.brand -->
        </div><!-- /.navbar-header -->
    </div><!-- /.container -->
</div>
<div class="main-container" id="main-container">
    <div class="main-container-inner">
        <div class="main-content flow-main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <h1 class="logo"><img src="<%=ctx%>/static/src/img/logo.png" alt=""></h1>
                        <div class="login-main">
                            <div class="">
                                <form class="form-horizontal" role="form">
                                    <strong class="t">登录SaaS</strong>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <input type="text" id="user-name" placeholder="登录账号"
                                                   class="col-xs-10 col-sm-5"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <input type="password" id="password" placeholder="输入密码"
                                                   class="col-xs-10 col-sm-5"/>
                                        </div>
                                    </div>
                                    <div class="forgot-password"><a href="/forgot-password">忘记密码?</a></div>
                                    <div class="">
                                        <button type="button" id="login-btn" class="login-btn">登录</button>
                                    </div>
                                </form>
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
<script src="<%=ctx%>/static/src/js/login/login.js" charset="utf-8"></script>
</body>
</html>
