<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 选课分析</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/school-reform/trinity.css" />
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
                            <a href="#">首页</a>
                        </li>
                        <li>高考改革</li>
                        <li class="active">三位一体招生</li>
                    </ul>
                </div>
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>16年招生数据</h3>
                            </div>
                            <div class="enroll-total">
                                <div class="enroll-university-total">
                                    <div id="enrollTotalChart" style="width: 100%;height: 280px;"></div>
                                </div>
                                <div class="enroll-plan-total">
                                    <div id="planTotalLineChart" style="width: 100%;height: 280px;"></div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>招生院校详情</h3>
                            </div>
                            <table class="enroll-university-detail-table" cellpadding="0" cellspacing="0">
                                <thead>
                                    <tr>
                                        <th>院校排名</th>
                                        <th>学校名称</th>
                                        <th>招生人数</th>
                                        <th>高考成绩占比</th>
                                        <th>其他条件</th>
                                    </tr>
                                </thead>
                                <tbody id="enroll-university-list">
                                    <%--<tr>--%>
                                        <%--<td>1</td>--%>
                                        <%--<td>北京大学(985)</td>--%>
                                        <%--<td>12</td>--%>
                                        <%--<td>50%</td>--%>
                                        <%--<td>学考7A，综合素质A</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>2</td>--%>
                                        <%--<td>北京大学(985)</td>--%>
                                        <%--<td>12</td>--%>
                                        <%--<td>50%</td>--%>
                                        <%--<td>学考7A，综合素质A</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>3</td>--%>
                                        <%--<td>北京大学(985)</td>--%>
                                        <%--<td>12</td>--%>
                                        <%--<td>50%</td>--%>
                                        <%--<td>学考7A，综合素质A</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>4</td>--%>
                                        <%--<td>北京大学(985)</td>--%>
                                        <%--<td>12</td>--%>
                                        <%--<td>50%</td>--%>
                                        <%--<td>学考7A，综合素质A</td>--%>
                                    <%--</tr>--%>
                                    <%--<tr>--%>
                                        <%--<td>5</td>--%>
                                        <%--<td>北京大学(985)</td>--%>
                                        <%--<td>12</td>--%>
                                        <%--<td>50%</td>--%>
                                        <%--<td>学考7A，综合素质A</td>--%>
                                    <%--</tr>--%>
                                </tbody>
                            </table>
                            <script id="enroll-university-list-data-template" type="text/x-handlebars-template">
                                {{#each this}}
                                <tr>
                                    <td>{{rank}}</td>
                                    <td>{{universityName}}</td>
                                    <td>{{planEnrollingNumber}}人</td>
                                    <td>-</td>
                                    <td>-</td>
                                </tr>
                                {{/each}}
                            </script>
                            <div class="pagination-bar">
                                <div class="pagination">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="./../common/footer.jsp"%>
    <script src="<%=ctx%>/static/src/js/school-reform/trinity.js"></script>
    </body>
</html>
