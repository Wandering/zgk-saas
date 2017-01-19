<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>教务首页</title>
        <%@ include file="../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/school-reform/policy-interpret.css" />
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
                                <a href="#">首页</a>
                            </li>
                        </ul>
                    </div>
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>调整统一高考科目</h3>
                                </div>
                                <div class="score-component">
                                    <ul>
                                        <li>1.2020年起，统一高考科目为语文、数学、外语（分为英语、日语、俄语、德语、法语、西班牙语）</li>
                                        <li>2.高考不分文理</li>
                                        <li>3.外语考试包括笔试和听力测试，引导外语教学注重应用能力的培养。最多参加两次外语考试，可选择其中较好的一次成绩计入高考总分。</li>
                                    </ul>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>高考录取成绩构成</h3>
                                </div>
                                <div class="score-rule">
                                    <table class="score-rule-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th width="212px"></th>
                                                <th>科目</th>
                                                <th width="204px">分值</th>
                                                <th width="199px">总分</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>统一高考成绩</td>
                                                <td>语文、数学、外语3科</td>
                                                <td>每科满分150分</td>
                                                <td rowspan="2">750分</td>
                                            </tr>
                                            <tr>
                                                <td>等级性考试</td>
                                                <td>物理、化学、生物、历史、地理、政治，6选3</td>
                                                <td>每科满分100分</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>高校招生录取的选考科目要求</h3>
                                </div>
                                <div class="exam-time">
                                    <table class="exam-time-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th width="20%"></th>
                                                <th>条件</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>选考科目要求</td>
                                                <td>分专业大类（或专业）自主提出选考科目范围0-3科，学生满足其中任何1科，既符合报考条件对于未提出选考科目要求的普通高校，
                                                    考生在报考该校时无科目限制</td>
                                            </tr>
                                            <tr>
                                                <td>公布选考范围</td>
                                                <td>高校要提前2年向社会公布选考科目要求</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/js/school-reform/policy-interpret.js"></script>
    </body>
</html>
