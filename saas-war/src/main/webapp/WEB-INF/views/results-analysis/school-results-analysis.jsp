<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 学校成绩分析</title>
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
                    <li class="active">学校成绩分析</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="main-title">
                            <h3>学校成绩管理</h3>
                        </div>

                        <div class="title-2">
                            <div class="txt-t">
                                <div class="radio no-padding-left" id="grade-body"></div>
                            </div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                    <div class="school-main-content">
                        <div class="col-xs-12">
                            <div class="grade3-t"><h5 class="h5 p-t"><span class="line"></span>本科线成绩分析：</h5></div>
                            <div class="grade3-main" style="display: none;">
                                <div id="lineNumberByDate-chart" style="width: 100%;height: 250px;"></div>
                            </div>
                            <p><h5 class="h5 p-t"><span class="line"></span>各班上线人数统计：</h5><span class="p-t2">根据去年上线比例：一本全校位次线<span
                                class="batchOne"></span>名，二本位次线<span class="batchTwo"></span>名</p>
                            <div class="txt-t">
                                <div class="radio no-padding-left">
                                    <label>
                                        <span class="lbl">排序规则:</span>
                                    </label>
                                    <label>
                                        <input name="sort-radio" type="radio" value="batchAll"
                                               class="ace batchAllRadio"/>
                                        <span class="lbl">本科上线人数</span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input name="sort-radio" type="radio" value="batchOne"
                                               class="ace batchOneRadio"/>
                                        <span class="lbl">一本上线人数</span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input name="sort-radio" type="radio" value="batchTwo"
                                               class="ace batchTwoRadio"/>
                                        <span class="lbl">二本上线人数</span>
                                    </label>
                                </div>
                            </div>
                            <div class="sort-txt"></div>
                            <table id="sort-table" class="table table-hover">
                                <thead id="sort-thead">
                                <tr>
                                </tr>
                                </thead>
                                <tbody id="sort-tbody">

                                </tbody>
                            </table>

                            <div><h5 class="h5 p-t"><span class="line"></span>重点关注学生：</h5><span class="p-t2">根据去年上线比例：一本全校位次线<span
                                    class="batchOne"></span>名，二本位次线<span class="batchTwo"></span>名</div>
                            <div class="core-txt"></div>
                            <table class="table table-hover">
                                <thead id="core-thead"></thead>
                                <tbody id="core-tbody"></tbody>
                            </table>

                            <div id="MostAdvanced">
                                <div><h5 class="h5 p-t"><span class="line"></span>进步较大学生：</h5></div>
                                <form class="form-horizontal" role="form">
                                    <div class="form-group">
                                        <div class="col-sm-3">
                                            <select class="form-control" id="ranking-sel">
                                            </select>
                                        </div>
                                        <div class="col-sm-3 counselor-sel">
                                            <select class="form-control" id="counselor-sel">
                                            </select>
                                        </div>
                                    </div>
                                </form>
                                <div class="progress-txt"></div>
                                <table id="progress-table" class="table table-hover">
                                    <thead id="progress-thead">
                                    <tr>
                                    </tr>
                                    </thead>
                                    <tbody id="progress-tbody">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="class-no-content">
                        <img src="<%=ctx%>/static/src/img/nodata.png" alt="">
                        <p>该年级还没有成绩录入，无法使用此功能！<br/>
                            请至成绩分析>学生成绩管理页上传该年级成绩！</p>
                    </div>
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="grade-template" type="text/x-handlebars-template">
    {{#each bizData.grades}}
    <label>
        <input name="results-radio" type="radio" value="{{grade}}" name="{{grade}}" class="ace"/>
        <span class="lbl">{{grade}}</span>
    </label>
    &nbsp;&nbsp;&nbsp;&nbsp;
    {{/each}}
</script>
<script id="sort-thead-template" type="text/x-handlebars-template">
    <tr>
        <th class="center">班级</th>
        {{#if bizData.counselor}}
        <th class="center">班主任</th>
        {{else}}
        {{/if}}
        <th class="center">本科上线人数</th>
        <th class="center">一本上线人数</th>
        <th class="center">二本上线人数</th>
    </tr>
</script>
<script id="sort-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">{{className}}</td>
        {{#if bizData.counselor}}
        <th class="center">{{counselor}}</th>
        {{else}}
        {{/if}}
        <td class="center">{{batchAll}}</td>
        <td class="center">{{batchOne}}</td>
        <td class="center">{{batchTwo}}</td>
    </tr>
    {{/each}}
</script>
<script id="progress-thead-template" type="text/x-handlebars-template">
    <tr>
        <th class="center">班级</th>
        <th class="center">班主任</th>
        <th class="center">进步较大学生人数</th>
    </tr>
</script>
<script id="progress-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">{{className}}</td>
        <th class="center">{{counselor}}</th>
        <td class="center">{{advancedNumber}}</td>
    </tr>
    {{/each}}
</script>
<script id="details-main-template" type="text/x-handlebars-template">
    {{#each bizData.list}}
    <tr>
        <td class="center">{{className}}</td>
        <td class="center">{{studentName}}</td>
        <td class="center">{{gradeRank}}</td>
        <td class="center">{{weakCourseOne}}</td>
        <td class="center">{{weakCourseTwo}}</td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
<script src="<%=ctx%>/static/src/lib/jquery.page/jquery.page.js"></script>
<script src="<%=ctx%>/static/src/js/results-analysis/school-results-analysis.js"></script>
</body>
</html>
