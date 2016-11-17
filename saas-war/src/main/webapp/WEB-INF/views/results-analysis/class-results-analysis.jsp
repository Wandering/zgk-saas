<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 班级成绩分析</title>
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
                        <a href="/index">首页</a>
                    </li>
                    <li>成绩分析</li>
                    <li class="active">班级成绩分析</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main-title">
                            <h3>班级成绩分析</h3>
                        </div>

                        <div class="title-2">
                            <div class="txt-t">
                                <div class="radio no-padding-left" id="grade-body"></div>
                            </div>
                            <select class="class-sel" id="select"></select>
                            <%--<select id="select">--%>
                                <%--<option value="1">1</option>--%>
                                <%--<option value="2">2</option>--%>
                                <%--<option value="3">3</option>--%>
                                <%--<option value="4">4</option>--%>
                                <%--<option value="5">5</option>--%>
                            <%--</select>--%>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                    <div class="col-xs-12">
                        <p class="txt1">班级排名趋势:<span class="sel-class-txt"></span></p>
                        <div class="chart-main1">
                            <div class="chart-tab">
                                <button class="btn btn-return cur">查看总分趋势</button>
                                <button class="btn btn-return">查看各科情况</button>
                            </div>
                            <div class="chart-box">
                                  <div id="totalScoreChart-chart" style="width: 100%;height: 400px;"></div>
                            </div>
                            <div class="chart-box">
                                <div id="subjectsChart-chart" style="width: 100%;height: 400px;"></div>
                            </div>
                        </div>
                    </div>

                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="grade-template" type="text/x-handlebars-template">
    {{#each bizData.grades}}
    <label>
        <input name="results-radio" type="radio" value="{{grade}}" name="{{grade}}" class="ace grade-item"/>
        <span class="lbl">{{grade}}</span>
    </label>
    &nbsp;&nbsp;&nbsp;&nbsp;
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/results-analysis/class-results-analysis.js"></script>
</body>
</html>
