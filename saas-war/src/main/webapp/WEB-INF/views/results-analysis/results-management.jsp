<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 学生成绩管理</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/results-analysis.css">
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
                        <a href="#">首页</a>
                    </li>
                    <li class="active">学生成绩管理</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="main-title">
                            <h3>学校成绩管理</h3>
                        </div>

                        <div class="title-2">
                            <div class="txt-t">
                                <div class="radio no-padding-left" id="grade-body"></div>
                            </div>
                            <div class="btns">
                                <button class="btn btn-danger" id="uploadResultsBtn">上传成绩</button>
                                <button class="btn btn-inverse" id="modify-btn">修改</button>
                                <button class="btn btn-success" id="close-btn">删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center"></th>
                                    <th class="center">考试名称</th>
                                    <th class="center">年级</th>
                                    <th class="center">考试时间</th>
                                    <th class="center">成绩上传时间</th>
                                    <th class="center">下载成绩</th>
                                    <th class="center">操作</th>
                                </tr>
                                </thead>
                                <tbody id="results-tbody"></tbody>
                            </table>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->

                <div class="row" style="" id="details-main">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <%--<div class="main-title">--%>
                            <%--<h3>成绩明细</h3>--%>
                        <%--</div>--%>
                        <%--<div class="title-2">--%>
                            <%--<div class="btns">--%>
                                <%--<button class="btn btn-inverse" id="details-modify-btn">修改</button>--%>
                                <%--<button class="btn btn-success" id="details-close-btn">删除</button>--%>
                                <%--<a target="_blank" href="javascript:;" class="btn btn-danger" id="details-download-btn">下载</a>--%>
                            <%--</div>--%>
                        <%--</div>--%>
                        <div class="">
                            <%--<table id="" class="table table-hover">--%>
                                <%--<thead>--%>
                                <%--<tr>--%>
                                    <%--<th class="center" rowspan="2"></th>--%>
                                    <%--<th class="center" rowspan="2">姓名</th>--%>
                                    <%--<th class="center" rowspan="2">班级</th>--%>
                                    <%--<th class="center" colspan="3">主课</th>--%>
                                    <%--<th class="center" colspan="7">选课</th>--%>
                                    <%--<th class="center" rowspan="2">班级排名</th>--%>
                                    <%--<th class="center" rowspan="2">年级排名</th>--%>
                                <%--</tr>--%>
                                <%--<tr>--%>
                                    <%--<th class="center">语文</th>--%>
                                    <%--<th class="center">数学</th>--%>
                                    <%--<th class="center">英语</th>--%>
                                    <%--<th class="center">物理</th>--%>
                                    <%--<th class="center">化学</th>--%>
                                    <%--<th class="center">生物</th>--%>
                                    <%--<th class="center">政治</th>--%>
                                    <%--<th class="center">地理</th>--%>
                                    <%--<th class="center">历史</th>--%>
                                    <%--<th class="center">通用技术</th>--%>
                                <%--</tr>--%>
                                <%--</thead>--%>
                                <%--<tbody id="details-tbody">--%>

                                <%--</tbody>--%>

                            <%--</table>--%>
                            <%--<div class="tcdPageCode"></div>--%>

                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="grade-template" type="text/x-handlebars-template">
    {{#each bizData.grades}}
    <label>
        <input name="results-radio" type="radio" value="{{grade}}" name="{{grade}}" class="ace"/>
        <span class="lbl">{{grade}}</span>
    </label>
    &nbsp;&nbsp;&nbsp;&nbsp;
    {{/each}}
</script>
<script id="results-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" id="{{id}}" examName="{{examName}}" examTime="{{examTime}}"
                       uploadFilePath="{{uploadFilePath}}" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{examName}}</td>
        <td class="center">{{grade}}</td>
        <td class="center">{{examTime}}</td>
        <td class="center">{{createDate}}</td>
        <td class="center"><a href="{{uploadFilePath}}" target="_blank">{{originFileName}}</a></td>
        <td class="center"><a href="javascript:;" urlId="{{id}}" grade="{{grade}}" examName="{{examName}}"
                              examTime="{{examTime}}" uploadFilePath="{{uploadFilePath}}" class="look-details">查看明细</a>
        </td>
    </tr>
    {{/each}}
</script>
<script id="details-template" type="text/x-handlebars-template">
    {{#each bizData.list}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" dataid="{{id}}" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{studentName}}</td>
        <td class="center">{{className}}</td>
        <td class="center">
            {{#if yuWenScore}}
            {{yuWenScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if shuXueScore}}
            {{shuXueScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if yingYuScore}}
            {{yingYuScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if wuLiScore}}
            {{wuLiScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if huaXueScore}}
            {{huaXueScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if shengWuScore}}
            {{shengWuScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if zhengZhiScore}}
            {{zhengZhiScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if diLiScore}}
            {{diLiScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if liShiScore}}
            {{liShiScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if commonScore}}
            {{commonScore}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if classRank}}
            {{classRank}}
            {{else}}
            -
            {{/if}}
        </td>
        <td class="center">
            {{#if gradeRank}}
            {{gradeRank}}
            {{else}}
            -
            {{/if}}
        </td>
    </tr>
    {{/each}}
</script>







<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
<link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/datepicker.css">
<script src="<%=ctx%>/static/src/lib/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.css">
<script type="text/javascript" src="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.js"></script>
<script>
    var BASE_URL = '<%=ctx%>/static/src/lib/';
    var rootPath = '<%=ctx%>';
</script>
<script src="<%=ctx%>/static/src/js/results-analysis/results-management.js"></script>
</body>
</html>
