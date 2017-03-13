<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 选课指导</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/school-reform/course-guide.css?v=20170309" />
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
                            首页
                        </li>
                        <li>高考改革</li>
                        <li class="active">选课指导</li>
                    </ul>
                </div>
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>历年单科选课对应上线情况</h3>
                            </div>
                            <div class="subject-box">
                                <ul class="subject-list">
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-physics" checked />
                                        <label for="subject-physics">物理</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-chemistry" />
                                        <label for="subject-chemistry">化学</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-biology" />
                                        <label for="subject-biology">生物</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-geography" />
                                        <label for="subject-geography">地理</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-history" />
                                        <label for="subject-history">历史</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-politics" />
                                        <label for="subject-politics">政治</label>
                                    </li>
                                    <li class="subject-tyjs-item" style="display: none;">
                                        <input type="radio" name="subject-analysis" id="subject-technology" />
                                        <label for="subject-technology">通用技术</label>
                                    </li>
                                    <li>
                                        <input type="radio" name="subject-analysis" id="subject-unlimited" />
                                        <label for="subject-unlimited">不限学科</label>
                                    </li>
                                </ul>
                            </div>
                            <div class="subject-require"><span class="require-num">0所院校的0个专业对物理有要求</span><a href="javascript: void(0);" subject="00" id='university-detail' class="red-link">查看院校详情</a></div>
                            <script id="university-detail-data-template" type="text/x-handlebars-template">
                                {{#each this}}
                                <tr>
                                    <td>{{rank}}</td>
                                    <td>
                                        {{universityName}}<br/>
                                        {{#if property}}
                                        ({{property}})
                                        {{/if}}
                                    </td>
                                    <td>{{majorName}}</td>
                                    <td>{{batchName}}</td>
                                    <td>{{planNumber}}人</td>
                                    <td>{{selSubject}}</td>
                                </tr>
                                {{/each}}
                            </script>
                            <ul class="plan-to-enroll">
                                <%--<li><!--985院校招生计划人数：876人--></li>--%>
                                <%--<li><!--211院校招生计划人数：876人--></li>--%>
                            </ul>
                            <script id="university-results-item-data-template" type="text/x-handlebars-template">
                                {{#each this}}
                                <li uid="{{id}}">{{name}}</li>
                                {{/each}}
                            </script>
                            <div class="plan-analysis">
                                <div class="batch-analysis">
                                    <div id="subjectLineChart" style="width: 100%;height: 250px;"></div>
                                </div>
                                <div class="major-type-analysis">
                                    <span id="major-type-count">共计0个专业门类:</span>
                                    <div id="major-type-list" class="major-type-box">
                                        <table class="major-type-top">
                                            <thead>
                                                <tr>
                                                    <th width="94px">专业门类</th>
                                                    <th>-</th>
                                                    <th>-</th>
                                                    <th>-</th>
                                                    <th>-</th>
                                                    <th>-</th>
                                                    <th>-</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td width="94px">计划人数</td>
                                                    <td>-</td>
                                                    <td>-</td>
                                                    <td>-</td>
                                                    <td>-</td>
                                                    <td>-</td>
                                                    <td>-</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <table class="major-type-bottom">
                                            <thead>
                                            <tr>
                                                <th width="94px">专业门类</th>
                                                <th>-</th>
                                                <th>-</th>
                                                <th>-</th>
                                                <th>-</th>
                                                <th>-</th>
                                                <th>-</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <tr>
                                                <td width="94px">计划人数</td>
                                                <td>-</td>
                                                <td>-</td>
                                                <td>-</td>
                                                <td>-</td>
                                                <td>-</td>
                                                <td>-</td>
                                            </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div id="subjectPieChart" style="width: 100%;height: 250px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row row1">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>16年招生数据</h3>
                            </div>
                            <div class="plan-enroll">
                                <div class="university-total">
                                    <div id="enrollTotalChart" style="width: 100%;height: 280px;"></div>
                                </div>
                                <div class="plan-total">
                                    <div id="planTotalLineChart" style="width: 100%;height: 280px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="history-enroll-data">
                                <div class="title">历年招生数据</div>
                                <table class="history-enroll-table" cellpadding="0" cellspacing="0">
                                    <thead>
                                        <tr>
                                            <th width="135px" rowspan="2" colspan="2"></th>
                                            <th colspan="2">一批本科</th>
                                            <th colspan="2">二批本科</th>
                                            <th colspan="2">高职高专</th>
                                        </tr>
                                        <tr>
                                            <th>招生院校数</th>
                                            <th>招生计划数</th>
                                            <th>招生院校数</th>
                                            <th>招生计划数</th>
                                            <th>招生院校数</th>
                                            <th>招生计划数</th>
                                        </tr>
                                    </thead>
                                    <tbody id="history-enroll-list">
                                        <%--<tr>--%>
                                            <%--<td colspan="2">2014年</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td colspan="2">2015年</td>--%>
                                            <%--<td>231</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>231</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>231</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>231</td>--%>
                                            <%--<td>12345</td>--%>
                                        <%--</tr>--%>
                                        <%--<tr>--%>
                                            <%--<td colspan="2">2016年</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                            <%--<td>234</td>--%>
                                            <%--<td>12345</td>--%>
                                        <%--</tr>--%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="./../common/footer.jsp"%>
    <script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
    <script src="<%=ctx%>/static/src/js/school-reform/course-guide.js?v=20170309"></script>
    </body>
</html>
