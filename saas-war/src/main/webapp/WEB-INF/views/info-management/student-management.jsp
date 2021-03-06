<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 学生管理</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/student-management/student-management.css?v=20170309">
</head>
<body>
<%@ include file="./../common/header.jsp" %>
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
        <%@ include file="./../common/sidebar.jsp" %>
        <div class="main-content">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>
                <ul class="breadcrumb">
                    <li>
                        首页
                    </li>
                    <li>
                        <a href="/student-management">基础信息管理</a>
                    </li>
                    <li class="active">学生管理</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="main-title">
                            <h3>学生管理</h3>
                            <div class="top-handle">
                                <%--<button class="btn-top"--%>
                                        <%--id="xz-template-download">行政班&文理科班模板下载</button>--%>
                                <%--<button class="btn-top hide" id="jx-template-download">教学班模板下载</button>--%>
                                <button class="btn-top"
                                        id="xz-template-download">无教学班年级学生模板下载</button>
                                <button class="btn-top hide" id="jx-template-download">有教学班年级学生模板下载</button>
                                <button class="btn-top" id="student-upload">批量上传</button>
                                <%--<button class="btn-top" id="student-setting">添加字段</button>--%>
                            </div>
                        </div>

                        <div class="title-2">
                            <span class="txt-t">
                                <ul class="grade-list" id="grade-list"></ul>
                                <script type="text/x-handlebars-template" id="grade-list-tpl">
                                    {{#each this}}
                                        {{#if @index}}
                                        <li>
                                            <input type="radio" name="grade-li" id="grade-{{@index}}" keyId="{{id}}" classType="{{classType}}">
                                            <label for="grade-{{@index}}">{{grade}}</label>
                                        </li>
                                        {{else}}
                                        <li>
                                            <input type="radio" name="grade-li" id="grade-{{@index}}" keyId="{{id}}" classType="{{classType}}" checked>
                                            <label for="grade-{{@index}}">{{grade}}</label>
                                        </li>
                                        {{/if}}
                                    {{/each}}
                                </script>
                            </span>
                            <div class="btns">
                                <button class="btn btn-pink" id="student-add" type="add">添加学生</button>
                                <button class="btn btn-inverse" id="student-modify" type="update">修改</button>
                                <button class="btn btn-success" id="student-remove">删除</button>

                                <%--<button class="btn btn-warning" id="student-template-download">模板下载</button>--%>
                                <%--<button class="btn btn-warning" id="student-upload">批量上传</button>--%>
                                <%--<button class="btn btn-warning" id="student-setting">学生设置</button>--%>
                            </div>
                        </div>

                        <%--行政办管理|教学班管理--%>
                        <%--<div class="toggle-tab" id="class-type-toggle">--%>
                            <%--<button class="tab" type="1">行政班管理</button>--%>
                            <%--<button class="tab" type="0">教学班管理</button>--%>
                        <%--</div>--%>

                        <div id="student-table">
                                <table class="table">
                                    <thead></thead>
                                    <tbody class="check-template"></tbody>
                                </table>

                                <div class="pagination-bar">
                                    <div class="pagination"></div>
                                </div>
                        </div>

                        <%--添加学生--%>
                        <div id="student-add-layer" class="dh">
                            <ul class="student-add-box" id="student-add-box">

                            </ul>

                        </div>

                        <%--学生设置--%>
                        <div id="sub-student-setting" class="dh">
                            <div class="page-content">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="main-title">
                                            <h3>添加学生字段</h3>
                                        </div>
                                        <div class="title-2">
                                            <span class="txt-t"></span>
                                            <div class="btns">
                                                <button class="btn btn-inverse" id="sub-student-add">添加</button>
                                                <button class="btn btn-success" id="sub-student-remove">移除</button>
                                            </div>
                                        </div>
                                        <div>
                                            <table class="table" id="setting-student-table">
                                                <thead>
                                                <tr>
                                                    <th class="center">
                                                        <label>
                                                            <input type="checkbox" class="ace delCheckAll" id="stdCheckAll"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </th>
                                                    <th class="center">排序</th>
                                                    <th class="center">字段名称</th>
                                                    <th class="center">操作</th>
                                                </tr>
                                                </thead>
                                                <tbody id="sub-student-table"  class="check-template">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <script type="text/x-handlebars-template" id="sub-student-table-tpl">
                            {{#each this}}
                                {{#if isRetain}}
                                    <tr id="configKey-{{configKey}}" class="isRetain{{isRetain}}">
                                        <td class="center">
                                            <label>
                                                <%--<input type="checkbox" disabled class="ace" id="configOrder-{{id}}"/>--%>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center index" indexid="{{id}}">{{configKey}}</td>
                                        <td class="center">{{name}}</td>
                                        <td class="center">
                                            <span>-</span>
                                        </td>
                                    </tr>
                                {{else}}
                                    <tr id="configKey-{{configKey}}">
                                        <td class="center">
                                            <label>
                                                <input type="checkbox" class="ace" cid="{{id}}" id="configOrder-{{id}}"/>
                                                <span class="lbl"></span>
                                            </label>
                                        </td>
                                        <td class="center index" indexid="{{id}}">{{configKey}}</td>
                                        <td class="center">{{name}}</td>
                                        <td class="center">
                                            <a href="javascript:void(0)" class="active student-setting-remove-head"
                                               data-id="{{id}}">移除</a>
                                        </td>
                                    </tr>
                                {{/if}}


                            {{/each}}
                        </script>

                        <div id="sub-choose-field" class="dh">
                            <div id="field"></div>
                            <div class="btn btn-info" id="btn-choose">确认选择</div>
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
<link rel="stylesheet" type="text/css" href="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.css">
<script src="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.js"></script>
<script>
    var BASE_URL = '<%=ctx%>/static/src/lib/';
    var rootPath = '<%=ctx%>';
</script>
<script src="<%=ctx%>/static/src/lib/assets/js/jquery-ui-1.12.1.js"></script>
<script src="<%=ctx%>/static/src/js/info-management/student-management.js?v=20170309"></script>
</body>
</html>
