<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>教务首页</title>
        <%@ include file="../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/school-reform/policy-interpret.css?v=20170309" />
    </head>
    <body>
        <%@ include file="../common/header.jsp"%>
        <div class="main-container" id="main-container">
            <script type="text/javascript">
                try{ace.settings.check('main-container' , 'fixed')}catch(e){}
            </script>
            <div class="main-container-inner">
                <a class="menu-toggler" id="menu-toggler" href="#">
                    <span class="menu-text"></span>
                </a>
                <%@ include file="../common/sidebar.jsp"%>
                <div class="main-content">
                    <div class="breadcrumbs" id="breadcrumbs">
                        <script type="text/javascript">
                            try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
                        </script>
                        <ul class="breadcrumb">
                            <li>
                                首页
                            </li>
                        </ul>
                    </div>
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>考试科目</h3>
                                </div>
                                <div class="score-rule">
                                    <table class="score-rule-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th width="212px"></th>
                                            <th>科目</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr>
                                            <td>必考科目</td>
                                            <td>语文、数学、外语3科</td>
                                        </tr>
                                        <tr>
                                            <td>选考科目</td>
                                            <td>政治、历史、地理、物理、化学、生物，6门学科中选择3门进行考试，以等级确定，换算成分数，计入总分</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>考试方式</h3>
                                </div>
                                <div class="score-component">
                                    <ul>
                                        <li>• 仍自主命题，不用全国卷，高考时间不变</li>
                                        <li>• 听力口语一年两考，英语总分值将提高</li>
                                        <li>• 高中期间必考科目同一科目可考两次</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>招生录取</h3>
                                </div>
                                <div class="score-component">
                                    <ul>
                                        <li>• 少数民族考生加分调整为：报考省属高校加3分投档</li>
                                        <li>• 高校对各门科目分类要求，本一、本二批次或将消失</li>
                                        <li>• 普通本科高校和高职院校分类考试、分类录取</li>
                                        <li>• 普通高校、高职、成人高校实现学分转换</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>考试总分</h3>
                                </div>
                                <div class="score-component">
                                    <ul>
                                        <li>• 改革后高考总分值定位多高还在调研阶段，将增至700分左右</li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/js/school-reform/policy-interpret.js?v=20170309"></script>
    </body>
</html>
