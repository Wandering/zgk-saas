<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 专业测评</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/professional-assessment.css">
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
                        <div class="assessment-main">
                        <dl class="assessment1">
                            <dt>
                                <span class="icon-cp1"></span>
                            </dt>
                            <dd>
                                <h3>选课测评</h3>
                                <p>学科兴趣直接决定选课后的成绩成长空间，应通过多维度权威测试，判断学生各学科兴趣情况，发掘考生的显性学科兴趣、隐性学科兴趣、短期学科兴趣、长期实质性性学科兴趣等，帮助考生深入了解自己，在选课时排除短期环境影响，做出正确的选择</p>
                                <button class="btn-assessment">开始测评</button>
                            </dd>
                        </dl>
                        <dl class="assessment2">
                            <dt>
                                <span class="icon-cp3"></span>
                            </dt>
                            <dd>
                                <h3>专业测评</h3>
                                <p>系统为理科生测试者提供了最适合的20种专业以及106种专业的适合度指数排行，通过评估个体与专业选择相关的兴趣、价值观、个性等因素，得出被测试者关于专业发展方向的指导性参数，为高中学填报大学志愿选择合适专业提供科学的参考。</p>
                                <button class="btn-assessment">开始测评</button>
                            </dd>
                        </dl>
                        <dl class="assessment3">
                            <dt>
                                <span class="icon-cp2"></span>
                            </dt>
                            <dd>
                                <h3>职业测评</h3>
                                <p>霍兰德提出的6种基本职业类型：实用型R、研究型I、艺术型A、社会型S、企业型E、常规型C，主要用于确定被测试者的职业兴趣倾向,进而用于指导被测试者选择适合自身职业兴趣的专业发展方向和职业发展方向，帮助高中生提前规划职业方向。</p>
                                <button class="btn-assessment">开始测评</button>
                            </dd>
                        </dl>
                        </div>
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/professional-assessment/professional-assessment.js"></script>
</body>
</html>
