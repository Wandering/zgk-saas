<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 选课结果</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/select-course-result.css"/>
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
                        <a href="/index">首页</a>
                    </li>
                    <li>排选课</li>
                    <li class="active">选课结果</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main-title">
                            <h3>选课设置</h3>
                        </div>
                        <div class="result-box">
                            <h3 class="title"><span class="line"></span>选课相关信息</h3>
                            <ul class="info-list">
                                <li>1.学生选课平台：智高考网站（www.zhigaokao.cn），注册登录后进入
                                    <a href="http://zj.zhigaokao.cn/sel-courses.html" target="_blank">选课程-我的选课</a>进行选课</li>
                                <li>2.学生选课结束后，学校可在“选课分析”中查看选课结果</li>
                            </ul>
                        </div>
                        <%--选课概况--%>
                        <div class="result-box" id="choose-task-about"> </div>
                        <script id="choose-task-about-tpl" type="text/x-handlebars-template">
                            <h3 class="title"><span class="line"></span>选课任务名称</h3>
                            <p>{{name}}选课已结束，结果如下：</p>
                            <p class="sub-title">选课概况</p>
                            <p>选课开始时间：{{startTime}}</p>
                            <p>选课结束时间：{{endTime}}</p>
                            <p>{{grade}}年级已选课学生{{selectedCount}}人 {{#if unSelectedCount}}<span class="no-choose-course">未选课学生{{unSelectedCount}}人</span>{{/if}}</p>
                        </script>

                        <%--未选课学生--%>
                        <div id="no-choose-std" class="dh">
                            <table class="table table-border">
                                <thead>
                                <tr>

                                    <th class="center">班级</th>
                                    <th class="center">学生名称</th>
                                </tr>
                                </thead>
                                <tbody class="check-template" id="no-choose-std-list">

                                </tbody>
                                <script id="no-choose-std-list-tpl" type="text/x-handlebars-template">
                                    {{#each this}}
                                    <tr>
                                        <td class="center">{{className}}</td>
                                        <td class="center">{{stuName}}</td>
                                    </tr>
                                    {{/each}}
                                </script>
                            </table>
                        </div>

                        <%--选课结果--%>
                        <div class="main-title">
                            <h3>选课结果</h3>
                        </div>
                        <div class="result-box">
                            <h3 class="title"><span class="line"></span>单科选课情况分析</h3>
                            <ul class="sel-single-course" id="sel-single-course">
                            </ul>
                            <script type="text/x-handlebars-template" id="sel-single-course-tpl">
                                {{#each this}}
                                <li>{{courseName}}（{{#if stuCount}}{{stuCount}}人{{else}}0{{/if}})</li>
                                {{/each}}
                            </script>
                        </div>
                        <div class="result-box">
                            <h3 class="title"><span class="line"></span>组合选课情况分析</h3>
                            <ul class="info-list">
                                <div id="assembly-course-chart" style="width: 100%;height: 350px;margin: 20px auto 0;"></div>
                            </ul>
                        </div>

                        <div class="title-2">
                            <span class="txt-t">
                                <ul class="course-type-list" id="course-type-list">
                                        <li>
                                            <input type="radio" name="type-li" id="type-0"
                                                   checked value="0">
                                            <label for="type-0">高考课程选课结果</label>
                                        </li>
                                        <li>
                                            <input type="radio" name="type-li" id="type-1" value="1">
                                            <label for="type-1">非高考课程选课结果</label>
                                        </li>
                                </ul>
                            </span>
                            <div class="btns">
                                <button class="btn btn-inverse" id="select-modify">修改</button>
                                <%--<button class="btn btn-success" id="look-sel-course">查看选课分析</button>--%>
                            </div>
                        </div>



                        <%--选课结果table--%>
                        <div id="sel-course-table">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th class="center"><label><span
                                            class="lbl"></span></label></th>
                                    <th class="center">学号</th>
                                    <th class="center">学生名称</th>
                                    <th class="center">班级名称</th>
                                    <th class="center">选择科目一</th>
                                    <th class="center">选择科目二</th>
                                    <th class="center">选择科目三</th>
                                </tr>
                                </thead>
                                <tbody class="check-template" id="table-list"></tbody>
                                <script type="text/x-handlebars-template" id="table-list-tpl">
                                    {{#each this.list}}
                                    <tr>
                                        <td class="center"><label><input type="checkbox" cid="496" class="ace"><span
                                                class="lbl"></span></label></td>
                                        <td>9232323</td>
                                        <td class="center">{{stuNo}}</td>
                                        <td class="center">{{className}}</td>
                                        {{#each this.courses}}
                                        <td class="center" courseId="{{courseId}}">{{courseName}}</td>
                                        {{/each}}
                                    </tr>
                                    {{/each}}
                                </script>
                            </table>

                            <%--<div class="pagination-bar">--%>
                                <%--<div class="pagination"><span class="disabled">上一页</span><span--%>
                                        <%--class="current">1</span><span class="disabled">下一页</span></div>--%>
                            <%--</div>--%>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->

                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->

<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/select-course-result.js"></script>
</body>
</html>
