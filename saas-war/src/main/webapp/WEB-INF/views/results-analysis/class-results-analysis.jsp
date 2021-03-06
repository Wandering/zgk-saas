<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 班级成绩分析</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/results-analysis.css?v=20170309">
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
                        首页
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
                        <br>
                        <div><h5 class="h5 p-t"><span class="line"></span>总体成绩分析：</h5></div>
                        <div class="title-2">
                            <div class="txt-t">
                                <div class="radio no-padding-left" id="grade-body"></div>
                            </div>
                            <select class="class-sel" id="select-class"></select>
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
                    <div class="class-main-content">
                        <div class="col-xs-12">
                            <p class="txt1">班级排名趋势:<span class="sel-class-txt"></span></p>
                            <p class="class-rank-tips-txt"></p>
                            <div class="chart-main1">
                                <div class="chart-tab">
                                    <button class="btn btn-return cur">查看总分趋势</button>
                                    <button class="btn btn-return">查看各科情况</button>
                                </div>
                                <div class="chart-box">
                                    <div id="totalScoreChart-chart" style="width: 100%;height: 400px;"></div>
                                </div>
                                <div class="chart-box hds">
                                    <div id="subjectsChart-chart" style="width: 100%;height: 400px;"></div>
                                </div>
                            </div>
                            <p class="txt1">人数分布变化:</p>
                            <table id="" class="table table-hover">
                                <thead id="student-change-thead">
                                <%--<tr>--%>
                                    <%--<th class="center">年级排名</th>--%>
                                    <%--<th class="center">考试1</th>--%>
                                    <%--<th class="center">考试2</th>--%>
                                    <%--<th class="center">变化人数</th>--%>
                                <%--</tr>--%>
                                </thead>
                                <tbody id="student-change-tbody"></tbody>
                            </table>
                        </div>
                        <div class="col-xs-12">
                            <!-- PAGE CONTENT BEGINS -->
                            <div><h5 class="h5 p-t"><span class="line"></span>重点线成绩分析：</h5>
                                <div class="grade3-main" style="display: none;">
                                    <div id="lineNumberByDate-chart" style="width: 100%;height: 250px;"></div>
                                </div>
                                <div class="grade1-2-line">
                                    <span class="online-txt">设置关注位次线</span><input type="txt" id="set-line"
                                                                                  class="online-input" value="">
                                    <button type="button" class="set-line-btn" id="set-line-btn">确定</button>
                                </div>

                                <div class="grade-student-num">
                                    <span class="class-name"></span>年级前<span class="student-num"></span>名人数统计：
                                </div>
                                <div class="txt-t grade3-student-batch">
                                    <div class="radio no-padding-left">
                                        <label>
                                            <span class="lbl">选择上线人数:</span>
                                        </label>
                                        <label>
                                            <input name="sort-radio" type="radio" value="batchAll"
                                                   class="ace classBatchAllRadio"/>
                                            <span class="lbl">本科上线人数</span>
                                        </label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <label>
                                            <input name="sort-radio" type="radio" value="batchOne"
                                                   class="ace classBatchOneRadio"/>
                                            <span class="lbl">一本上线人数</span>
                                        </label>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <label>
                                            <input name="sort-radio" type="radio" value="batchTwo"
                                                   class="ace classBatchTwoRadio"/>
                                            <span class="lbl">二本上线人数</span>
                                        </label>
                                    </div>
                                </div>
                                <table id="" class="table table-hover">
                                    <thead>
                                    <tr>
                                        <th class="center">班级排名</th>
                                        <th class="center">学生姓名</th>
                                        <th class="center">成绩</th>
                                        <th class="center">年级排名</th>
                                    </tr>
                                    </thead>
                                    <tbody id="overLineDetail-tbody">

                                    </tbody>
                                </table>

                                <div><h5 class="h5">重点关注学生：</h5><span class="batch-info">根据去年上线比例：一本全校位次线<span
                                        class="batchOne"></span>名，二本位次线<span class="batchTwo"></span>名</span></div>
                                <div class="txt-t">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-3">
                                                <select class="form-control" id="batch-sel">
                                                    <option value="">请选择批次</option>
                                                    进步较大学生  <option value="batchOne">一本</option>
                                                    <option value="batchTwo">二本</option>
                                                    <%--<option value="batchThr">三本</option>--%>
                                                </select>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="table-details-main">
                                    <table class="table table-hover">
                                        <thead>
                                        <tr>
                                            <th class="center">班级排名</th>
                                            <th class="center">学生名称</th>
                                            <th class="center">年级排名</th>
                                            <th class="center">弱势学科一</th>
                                            <th class="center">弱势学科二</th>
                                        </tr>
                                        </thead>
                                        <tbody id="details-main-tbody"></tbody>
                                    </table>
                                    <div class="tcdPageCode"></div>
                                </div>
                                <div id="MostAdvanced">
                                    <h5 class="h5 p-t"><span class="line"></span>进步较大学生：</h5>
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <div class="col-sm-3">
                                                <select class="form-control" id="ranking-sel"></select>
                                            </div>
                                            <div class="col-sm-3">
                                                <select class="form-control" id="gradeTop-sel"></select>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="progress-txt"></div>
                                    <table id="progress-table" class="table table-hover">
                                        <thead id="progress-thead">
                                        <tr>
                                            <th class="center">学生姓名</th>
                                            <th class="center">平均进步名次</th>
                                            <th class="center">年级排名</th>
                                            <th class="center">弱势学科一</th>
                                            <th class="center">弱势学科二</th>
                                        </tr>
                                        </thead>
                                        <tbody id="progress-tbody">

                                        </tbody>
                                    </table>
                                </div><!-- /.col -->
                            </div>
                        </div><!-- /.row -->
                    </div><!-- /.page-content -->
                    <div class="class-no-content">
                        <img src="<%=ctx%>/static/src/img/nodata.png" alt="">
                        <p>该年级还没有成绩录入，无法使用此功能！<br/>
                        请至成绩分析>学生成绩管理页上传该年级成绩！</p>
                    </div>
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
        <script id="student-change-thead-template" type="text/x-handlebars-template">
            <tr>
                {{#each bizData.[0]}}
                {{#compare @key '!=' 'data'}}
                <th class="center">{{@key}}</th>
                {{/compare}}
                {{/each}}
            </tr>
        </script>
        <script id="student-change-template" type="text/x-handlebars-template">
            {{#each bizData}}
            <tr>
                {{#each this}}
                    {{#compare @key '!=' 'data'}}
                        {{#compare @key '!=' '变化人数'}}
                        <td class="center">{{this}}</td>
                        {{/compare}}
                    {{/compare}}
                {{/each}}
                <td class="center"><a href="javascript:;" class="change-student-btn"
                                      data="{{#each data}}{{agree_button}}{{/each}}">{{变化人数}}</a></td>
            </tr>
            {{/each}}
        </script>
        <script id="overLineDetail-template" type="text/x-handlebars-template">
            {{#each bizData}}
            <tr>
                <td class="center">{{班级排名}}</td>
                <td class="center"><a href="javascript:;" class="student-btn">{{学生姓名}}</a></td>
                <td class="center">{{成绩}}</td>
                <td class="center">{{年级排名}}</td>
            </tr>
            {{/each}}
        </script>
        <script id="details-main-template" type="text/x-handlebars-template">
            {{#each this}}
            <tr>
                <td class="center">{{classRank}}</td>
                <td class="center"><a href="javascript:;" class="student-btn">{{studentName}}</a></td>
                <td class="center">{{gradeRank}}</td>
                <td class="center">{{weakCourseOne}}</td>
                <td class="center">{{weakCourseTwo}}</td>
            </tr>
            {{/each}}
        </script>

        <script id="progress-template" type="text/x-handlebars-template">
            {{#each bizData}}
            <tr>
                <td class="center"><a href="javascript:;" class="student-btn">{{studentName}}</a></td>
                <th class="center">{{advancedScore}}</th>
                <td class="center">{{gradeRank}}</td>
                <td class="center">{{weakCourseOne}}</td>
                <td class="center">{{weakCourseTwo}}</td>
            </tr>
            {{/each}}
        </script>
        <%@ include file="./../common/footer.jsp" %>
        <script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
        <script src="<%=ctx%>/static/src/js/results-analysis/class-results-analysis.js?v=20170309"></script>
</body>
</html>
