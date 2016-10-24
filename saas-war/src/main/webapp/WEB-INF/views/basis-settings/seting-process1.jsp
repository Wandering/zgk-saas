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
                            <button class="btn btn-success">删除</button>
                            <button class="btn btn-warning">禁用</button>
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
                                <li class="list"><span>教室设置</span></li>
                                <li class="list"><span>班级设置</span></li>
                                <li class="list"><span>升学率设置</span></li>
                                <li class="list"><span>教师设置</span></li>
                            </ul>
                        </div>
                        <p class="flow-tips">温馨提示：用户可以在左侧菜单，基础信息中更新设置内容</p>

                        <div class="main-title">
                            <h3>年级设置</h3>
                        </div>







                        <div class="setting-grade">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center">
                                        序号
                                    </th>
                                    <th class="center">年级简称</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td class="center">1</td>
                                    <td class="center">
                                        <input type="text" id="seting-process1-1" placeholder="" class="col-xs-10 col-sm-10 seting-process1-input"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center">2</td>
                                    <td class="center">
                                        <input type="text" id="seting-process1-2" placeholder="" class="col-xs-10 col-sm-10 seting-process1-input"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center">3</td>
                                    <td class="center">
                                        <input type="text" id="seting-process1-3" placeholder="" class="col-xs-10 col-sm-10 seting-process1-input"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center">4</td>
                                    <td class="center">
                                        <input type="text" id="seting-process1-4" placeholder="" class="col-xs-10 col-sm-10 seting-process1-input"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center">5</td>
                                    <td class="center">
                                        <input type="text" id="seting-process1-5" placeholder="" class="col-xs-10 col-sm-10 seting-process1-input"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="center">6</td>
                                    <td class="center">
                                        <input type="text" id="seting-process1-6" placeholder="" class="col-xs-10 col-sm-10 seting-process1-input"/>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="btn-box">
                            <a class="btn btn-return">返回</a>
                            <a class="btn btn-info btn-save seting-process1-btn" id="seting-process1-btn" href="javascript:;">保存并下一步</a>
                        </div>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->

<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/basis-settings/seting-process1.js"></script>
</body>
</html>
