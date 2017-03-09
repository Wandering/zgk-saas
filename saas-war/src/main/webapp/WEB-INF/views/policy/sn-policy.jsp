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
                                        <li>1.2022年起，统一高考科目为语文、数学、外语</li>
                                        <li>2.高考不分文理</li>
                                        <li>3.外语考试包括笔试和听力测试，引导外语教学注重应用能力的培养。外语科目提供两次考试机会，选择较好的一次成绩计入总分</li>
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
                                                <td>思想政治、历史、地理、物理、化学、生物，6个科目中自主选择3科参加考试（每科可报考1次）</td>
                                                <td>每科满分100分</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>考试安排</h3>
                                </div>
                                <div class="item-title">考试科目、类别、时间及卷面满分、考试日期：</div>
                                <div class="exam-time">
                                    <table class="exam-time-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>考试科目</th>
                                                <th>考试类别</th>
                                                <th>考试时间及卷面满分</th>
                                                <th>考试日期</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>物理、化学、政治、历史、地理、生物</td>
                                                <td>合格性考试</td>
                                                <td>90分钟/100分</td>
                                                <td>高二第二学期4月</td>
                                            </tr>
                                            <tr>
                                                <td>物理、化学、政治、历史、地理、生物</td>
                                                <td rowspan="2">合格性考试</td>
                                                <td>90分钟/100分</td>
                                                <td rowspan="2">高三第二学期4月</td>
                                            </tr>
                                            <tr>
                                                <td>语文、数学、外语</td>
                                                <td>100分钟/120分</td>
                                            </tr>
                                            <tr>
                                                <td>物理、化学、政治、历史、地理、生物</td>
                                                <td rowspan="2">等级性考试</td>
                                                <td>90分钟/100分</td>
                                                <td>高考后</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="item-title">合格性考试日程安排：</div>
                                <div class="exam-time">
                                    <table class="exam-time-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>日期</th>
                                                <th colspan="2">上午</th>
                                                <th colspan="2">下午</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td rowspan="2">周五</td>
                                                <td rowspan="2" colspan="2">语文  8:30-10:10</td>
                                                <td>外语</td>
                                                <td>14:30-16:10（含听力20分钟）</td>
                                            </tr>
                                            <tr>
                                                <td>数学</td>
                                                <td>17:00-18:30</td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">周六</td>
                                                <td>物理</td>
                                                <td>8:00-9:30</td>
                                                <td>生物</td>
                                                <td>14:30-16:00</td>
                                            </tr>
                                            <tr>
                                                <td>政治</td>
                                                <td>10:30-12:00</td>
                                                <td>历史</td>
                                                <td>17:00-18:30</td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">周日</td>
                                                <td>化学</td>
                                                <td>8:00-9:30</td>
                                                <td rowspan="2" colspan="2"></td>
                                            </tr>
                                            <tr>
                                                <td>地理</td>
                                                <td>10:30-12:00</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="item-title">等级性考试日程安排：</div>
                                <div class="exam-time">
                                    <table class="exam-time-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>日期</th>
                                                <th colspan="2">上午</th>
                                                <th colspan="2">下午</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td rowspan="2">6月8日</td>
                                                <td colspan="2" rowspan="2">全国统一高考</td>
                                                <td>物理</td>
                                                <td>14:30-16:00</td>
                                            </tr>
                                            <tr>
                                                <td>政治</td>
                                                <td>17:00-18:30</td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">6月9日</td>
                                                <td>生物</td>
                                                <td>8:00-9:30</td>
                                                <td>化学</td>
                                                <td>14:30-16:00</td>
                                            </tr>
                                            <tr>
                                                <td>历史</td>
                                                <td>10:30-12:00</td>
                                                <td>地理</td>
                                                <td>17:00-18:30</td>
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
        <script src="<%=ctx%>/static/src/js/school-reform/policy-interpret.js?v=20170309"></script>
    </body>
</html>
