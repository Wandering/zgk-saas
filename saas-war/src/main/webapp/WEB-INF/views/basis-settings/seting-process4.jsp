<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 基础信息设置</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/seting-process.css"/>
</head>
<body>
<%@ include file="./../common/header.jsp"%>
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

        <div class="main-content flow-main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">

                        <div class="btn-box">
                            <button class="btn disabled">禁用</button>
                            <button class="btn btn-primary">取消</button>
                            <button class="btn btn-info">保存</button>
                            <button class="btn btn-success">
                                <i class="icon-remove bigger-110"></i>
                                删除
                            </button>

                            <button class="btn btn-warning">
                                <i class="icon-ban bigger-110"></i>
                                禁用
                            </button>
                            <button class="btn btn-danger">重设密码</button>
                            <button class="btn btn-inverse">修改密码</button>
                            <button class="btn btn-pink">添加账号</button>
                            <button class="btn btn-return">返回</button>
                            <button class="btn btn-info btn-save">保存并下一步</button>
                        </div>


                        <div class="flow-box">
                            <div class="line"></div>
                            <ul class="flow-ul">
                                <li class="list active"><span>年级设置</span></li>
                                <li class="list active"><span>教室设置</span></li>
                                <li class="list active"><span>班级设置</span></li>
                                <li class="list active"><span>升学率设置</span></li>
                                <li class="list"><span>教师设置</span></li>
                            </ul>
                        </div>
                        <p class="flow-tips">温馨提示：用户可以在左侧菜单，基础信息中更新设置内容</p>

                        <div class="main-title">
                            <h3>升学率设置</h3>
                        </div>


                        <div class="setting-grade">
                            <form class="form-horizontal" role="form">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-grade1">
                                        高三年级考生数量 </label>
                                    <div class="col-sm-9">
                                        <input type="text" id="form-grade1" placeholder=""
                                               class="col-xs-10 col-xs-10 col-sm-10"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-grade2">
                                        去年一本上线人数 </label>
                                    <div class="col-sm-9">
                                        <input type="text" id="form-grade2" placeholder=""
                                               class="col-xs-10 col-xs-10 col-sm-10"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-grade3">
                                        去年二本上线人数 </label>
                                    <div class="col-sm-9">
                                        <input type="text" id="form-grade3" placeholder=""
                                               class="col-xs-10 col-xs-10 col-sm-10"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-grade4">
                                        去年三本上线人数 </label>
                                    <div class="col-sm-9">
                                        <input type="text" id="form-grade4" placeholder=""
                                               class="col-xs-10 col-xs-10 col-sm-10"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="form-grade5">
                                        去年高职上线人数 </label>
                                    <div class="col-sm-9">
                                        <input type="text" id="form-grade5" placeholder=""
                                               class="col-xs-10 col-xs-10 col-sm-10"/>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="btn-box">
                            <a class="btn btn-return" href="/seting-process3">返回上一步</a>
                            <a class="btn btn-info btn-save" href="/seting-process5">保存并下一步</a>
                        </div>


                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp"%>
</body>
</html>
