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
                    <div class="col-xs-12">
                        <h5 class="h5">本科线成绩分析</h5>
                        <div class="grade3-main"></div>
                        <p><h5 class="h5">班级情况统计：</h5>根据去年上线比例：一本全校位次线<span class="batchOne"></span>名，二本位次线<span class="batchTwo"></span>名，三本位次线<span class="batchThr"></span>名</p>
                        <h5 class="h5">各班上线人数统计：</h5>
                        <div class="txt-t">
                            <div class="radio no-padding-left">
                                <label>
                                    <span class="lbl">排序规则:</span>
                                </label>
                                <label>
                                    <input name="sort-radio" type="radio" value="batchAll" class="ace batchAllRadio"/>
                                    <span class="lbl">本科上线人数</span>
                                </label>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <label>
                                    <input name="sort-radio" type="radio" value="batchOne" class="ace batchOneRadio"/>
                                    <span class="lbl">一本上线人数</span>
                                </label>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <label>
                                    <input name="sort-radio" type="radio" value="batchTwo" class="ace batchTwoRadio"/>
                                    <span class="lbl">二本上线人数</span>
                                </label>
                            </div>
                        </div>
                        <table id="" class="table table-hover">
                            <thead id="sort-thead">
                            <tr>
                            </tr>
                            </thead>
                            <tbody id="sort-tbody">

                            </tbody>
                        </table>
                        <p><h5 class="h5">重点关注学生：</h5>根据去年上线比例：一本全校位次线<span class="batchOne"></span>名，二本位次线<span class="batchTwo"></span>名，三本位次线<span class="batchThr"></span>名</p>
                        <table class="table table-hover">
                            <thead id="core-thead"></thead>
                            <tbody id="core-tbody"></tbody>
                        </table>
                        <h5 class="h5">进步较大学生：</h5>
                        <form class="form-horizontal" role="form">
                            <div class="form-group">
                                <div class="col-sm-3">
                                    <select class="form-control" id="ranking-sel">
                                        <option value="">选择进步名次</option>
                                    </select>
                                </div>
                                <div class="col-sm-3">
                                    <select class="form-control" id="">
                                        <option value="">选择班主任</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                        <table id="" class="table table-hover">
                            <thead>
                            <tr>
                                <th class="center">班级</th>
                                <th class="center">班主任</th>
                                <th class="center">进步较大学生人数</th>
                            </tr>
                            </thead>
                            <tbody id="progress-tbody">

                            </tbody>
                        </table>
                    </div>
                </div><!-- /.row -->

                <div class="row" style="display:none;" id="batch-main-layer">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="class-chart">
                            <div id="class-chart" style="width: 100%;height: 250px;"></div>
                        </div>
                        <div class="subject-chart">
                            <div id="subject-chart" class="" style="width: 100%;height: 250px;"></div>
                        </div>
                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
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
        {{#if bizData.className1}}
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
        {{#if bizData.className1}}
        <th class="center">{{className1}}</th>
        {{else}}
        {{/if}}
        <td class="center">{{batchAll}}</td>
        <td class="center">{{batchOne}}</td>
        <td class="center">{{batchTwo}}</td>
    </tr>
    {{/each}}
</script>
<script id="progress-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center"></td>
        <td class="center"></td>
        <td class="center"></td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/lib/jquery.page/jquery.page.js"></script>
<script src="<%=ctx%>/static/src/js/results-analysis/school-results-analysis.js"></script>
</body>
</html>
