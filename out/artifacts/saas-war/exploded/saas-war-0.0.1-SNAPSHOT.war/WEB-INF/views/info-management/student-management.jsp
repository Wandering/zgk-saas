<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 学生管理</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/student-management/student-management.css">
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
                        <a href="/index.html">首页</a>
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
                        </div>

                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="student-add" type="add">添加学生</button>
                                <button class="btn btn-inverse" id="student-modify" type="update">修改</button>
                                <button class="btn btn-success" id="student-remove">删除</button>
                                <button class="btn btn-warning" id="student-template-download">模板下载</button>
                                <button class="btn btn-warning" id="student-upload">批量上传</button>
                                <button class="btn btn-grey" id="student-setting">学生设置</button>
                            </div>
                        </div>


                        <div id="student-table">
                                <table class="table">
                                    <thead></thead>
                                    <tbody></tbody>
                                </table>
                        </div>
                        <%--<script type="text/x-handlebars-template" id="student-table-tpl">--%>
                            <%--<table class="table">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                    <%--<th class="center">--%>
                                        <%--<label>--%>
                                            <%--<input type="checkbox" class="ace" cid="{{#each this.tableHeader}}{{id}}{{/each}}"/>--%>
                                            <%--<span class="lbl"></span>--%>
                                        <%--</label>--%>
                                    <%--</th>--%>
                                    <%--<th class="center">编号</th>--%>
                                    <%--{{#each this.tableHeader}}--%>
                                    <%--<th class="center">{{name}}</th>--%>
                                    <%--{{/each}}--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody>--%>
                                <%--{{#each this.tableBody}}--%>
                                <%--<tr>--%>
                                    <%--<td class="center">--%>
                                        <%--<label>--%>
                                            <%--<input type="checkbox" class="ace"/>--%>
                                            <%--<span class="lbl"></span>--%>
                                        <%--</label>--%>
                                    <%--</td>--%>
                                    <%--{{#each this}}--%>
                                    <%--<td class="center">{{this}}</td>--%>
                                    <%--{{/each }}--%>
                                <%--</tr>--%>
                                <%--{{/each}}--%>
                                <%--</tbody>--%>
                            <%--</table>--%>
                        <%--</script>--%>

                        <%--////////////////////////////////////////////--%>
                        <%--添加学生--%>
                        <div id="student-add-layer" class="dh">
                            <ul class="student-add-box" id="student-add-box">
                                <%--<li>--%>
                                <%--<span>选择年级</span>--%>
                                <%--<select id="select-grade"></select>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<span>入校年份</span>--%>
                                <%--<select id="select-year"></select>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<span>班级类型</span>--%>
                                <%--<select id="select-class-type">--%>
                                <%--<option value="00">选择班级类型</option>--%>
                                <%--<option>重点班</option>--%>
                                <%--<option>普通班</option>--%>
                                <%--</select>--%>
                                <%--</li>--%>
                                <%--<li><span>所在班级</span>--%>
                                <%--<select id="now-class"></select>--%>
                                <%--</li>--%>
                                <%--<li>--%>
                                <%--<span class="f20">选择科目</span>--%>
                                <%--<div id="subject-list" class="f70">--%>
                                <%--<label>--%>
                                <%--<input type="checkbox" class="ace">--%>
                                <%--<span class="lbl">物理</span>--%>
                                <%--</label>--%>
                                <%--<label>--%>
                                <%--<input type="checkbox" class="ace">--%>
                                <%--<span class="lbl">化学</span>--%>
                                <%--</label>--%>
                                <%--</div>--%>
                                <%--</li>--%>
                                <%--<li><span>学生名称</span><input type="text" id="student-name" class="input-common-w"/>--%>
                                <%--</li>--%>
                                <%--<li><span>联系电话</span><input type="text" id="student-tel" class="input-common-w"/>--%>
                                <%--</li>--%>
                                <%--<li><span>联&nbsp;&nbsp;系&nbsp;人</span><input type="text" id="link-name"--%>
                                <%--class="input-common-w"/></li>--%>
                                <%--<li>--%>
                                <%--<span class="f20">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</span>--%>
                                <%--<div id="sex-type" class="f70">--%>
                                <%--<label>--%>
                                <%--<input name="form-field-radio" type="radio" class="ace">--%>
                                <%--<span class="lbl">男</span>--%>
                                <%--</label>--%>
                                <%--<label>--%>
                                <%--<input name="form-field-radio" type="radio" class="ace">--%>
                                <%--<span class="lbl">女</span>--%>
                                <%--</label>--%>
                                <%--</div>--%>
                                <%--</li>--%>
                                <%--<li><span>年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;龄</span><input type="text"--%>
                                <%--id="student-age"--%>
                                <%--class="input-common-w"/>--%>
                                <%--</li>--%>
                                <%--<li><span>家庭住址</span><input type="text" id="student-address"--%>
                                <%--class="input-common-w"/></li>--%>
                                    <%--<div class="opt-btn-box">--%>
                                        <%--<button class="btn btn-info save-btn" id="add-btn">确认添加</button>--%>
                                        <%--<button class="btn btn-primary close-btn">取消</button>--%>
                                    <%--</div>--%>
                            </ul>

                        </div>
                        <%--////////////////////////////////////////////--%>


                        <%--////////////////////////////////////////////--%>
                        <%--学生设置--%>
                        <div id="sub-student-setting" class="dh">
                            <div class="page-content">
                                <div class="row">
                                    <div class="col-xs-12">
                                        <div class="main-title">
                                            <h3>学生设置</h3>
                                        </div>
                                        <div class="title-2">
                                            <span class="txt-t"></span>
                                            <div class="btns">
                                                <button class="btn btn-inverse" id="sub-student-add">添加</button>
                                                <button class="btn btn-success" id="sub-student-remove">移除</button>
                                            </div>
                                        </div>
                                        <div class="">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th class="center">
                                                        <label>
                                                            <input type="checkbox" class="ace"/>
                                                            <span class="lbl"></span>
                                                        </label>
                                                    </th>
                                                    <th class="center">排序</th>
                                                    <th class="center">字段名称</th>
                                                    <th class="center"></th>
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
                            <tr id="configKey-{{configKey}}">
                                <td class="center">
                                    <label>
                                        <input type="checkbox" class="ace" id="configOrder-{{id}}"/>
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
                            {{/each}}
                        </script>

                        <%--////////////////////////////////////////////--%>

                        <%--选择添加字段--%>
                        <%--////////////////////////////////////////////--%>
                        <%--<div id="sub-choose-field" class="dh"></div>--%>

                        <%--<script type="text/x-handlebars-template" id="sub-choose-field-tpl">--%>
                        <%--<div>--%>
                        <%--{{#each this}}--%>
                        <%--<label>--%>
                        <%--<input type="checkbox" class="ace" id="{{id}}">--%>
                        <%--<span class="lbl">{{chName}}</span>--%>
                        <%--</label>--%>
                        <%--{{/each}}--%>
                        <%--</div>--%>
                        <%--<div class="btn btn-info" id="btn-choose">确认选择</div>--%>
                        <%--</script>--%>


                        <div id="sub-choose-field" class="dh">
                            <div id="field"></div>
                            <div class="btn btn-info" id="btn-choose">确认选择</div>
                        </div>


                        <%--////////////////////////////////////////////--%>
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
<script src="<%=ctx%>/static/src/js/info-management/student-management.js"></script>
</body>
</html>
