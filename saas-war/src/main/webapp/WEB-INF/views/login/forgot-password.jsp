<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 找回密码</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/login/forgot-password.css?v=20170309">
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
                        <div class="forgot-password-main">
                            <div class="">
                                <form class="form-horizontal" role="form">
                                    <strong class="t">重设密码</strong>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="account">登录账号</label>
                                        <div class="col-sm-9">
                                            <input type="text" id="account"  placeholder="输入登录账号1~12位 "
                                                   class="col-xs-10 col-sm-5 input-txt"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="phone">联系电话</label>
                                        <div class="col-sm-9">
                                            <input type="text" id="phone" placeholder="输入联系电话11位 "
                                                   class="col-xs-10 col-sm-5 input-txt"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="verification-code">验证码</label>
                                        <div class="col-sm-9">
                                            <input type="text" id="verification-code" placeholder="输入验证码"
                                                   class="col-xs-10 col-sm-5 input-txt"/>
                                            <button type="button" id="verification-btn" class="verification-btn">获取验证码</button>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="reset-pwd">重设密码</label>
                                        <div class="col-sm-9">
                                            <input type="password" id="reset-pwd" placeholder="重设密码至少6位"
                                                   class="col-xs-10 col-sm-5 input-txt"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-3 control-label no-padding-right" for="confirm">确认密码</label>
                                        <div class="col-sm-9">
                                            <input type="password" id="confirm" placeholder="确认密码至少6位"
                                                   class="col-xs-10 col-sm-5 input-txt"/>
                                        </div>
                                    </div>
                                    <div class="">
                                        <button type="button" id="back-btn" class="back-btn">重设密码</button>
                                        <p class="txt">已有账号，请<a href="/login">登录账号</a></p>
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
<script src="<%=ctx%>/static/src/lib/md5/jQuery.md5.js"></script>
<script src="<%=ctx%>/static/src/js/login/forgot-password.js?v=20170309" charset="utf-8"></script>
</body>
</html>
