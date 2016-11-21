<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 专业测评</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/professional-assessment/professional-assessment.css">
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
                    <li>专业测评</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12 ">
                        <div class="main-title">
                            <h3>班级成绩分析</h3>
                        </div>
                        <div class="assessment-main" id="assessment-main"></div>
                        <script type="text/x-handlebars-template" id="assessment-main-tpl">
                            {{#each this}}
                            <dl class="assessment1">
                                <dt>
                                    <span class="icon-cp{{@index}}"></span>
                                </dt>
                                <dd>
                                    <h3>{{name}}</h3>
                                    <p>{{introduce}}</p>
                                    <button class="btn-assessment" type="{{type}}" name="{{name}}">开始测评</button>
                                </dd>
                            </dl>
                            {{/each}}
                        </script>


                        <%--课程兴趣测评--%>
                        <div id="course-test-container" class="dh">
                            <div id="course-container">
                                <div id="question"></div>
                                <script id="question-tpl" type="text/x-handlebars-template">
                                    {{#each this}}
                                    <div class="col dh" id="question-{{id}}">
                                        <div class="question-title">
                                            {{id}}.{{title}}
                                        </div>
                                        <form class="question-answer">
                                            <label for="{{id}}-A" class="choose">
                                                <input type="radio" id="{{id}}-A" name="option" value="A"/>A.很符合自己的情况
                                            </label>
                                            <label for="{{id}}-B" class="choose">
                                                <input type="radio" id="{{id}}-B" name="option" value="B"/>B.比较符合自己的情况
                                            </label>
                                            <label for="{{id}}-C" class="choose">
                                                <input type="radio" id="{{id}}-C" name="option" value="C"/>C.很难说
                                            </label>
                                            <label for="{{id}}-D" class="choose">
                                                <input type="radio" id="{{id}}-D" name="option" value="D"/>D.较不符合自己的情况
                                            </label>
                                            <label for="{{id}}-E" class="choose">
                                                <input type="radio" id="{{id}}-E" name="option" value="E"/>E.很不符合自己的情况
                                            </label>
                                        </form>
                                    </div>
                                    {{/each}}
                                </script>
                                <div id="page"></div>
                                <script id="page-tpl" type="text/x-handlebars-template">
                                    <ul>
                                        {{#each this}}
                                        <li>{{this}}</li>
                                        {{/each}}
                                    </ul>
                                </script>
                                <div class="submit-box">
                                    <div class="submit-tip"></div>
                                    <button class="btn submit-question">提交</button>
                                </div>
                            </div>
                        </div>


                        <%--
                        图标结果
                        --%>
                        <div id="analyze-result" class="dh">
                            <div id="analyze-result-container">
                                <div class="chart" id="myBarChart" style="width: 950px;height: 320px">
                                </div>
                                <p class="advise-tip"></p>
                            </div>
                        </div>


                    </div>


                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->
</div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/professional-assessment/evaluation-data-base.js"></script>
<script src="<%=ctx%>/static/src/js/professional-assessment/professional-assessment.js"></script>
</body>
</html>
