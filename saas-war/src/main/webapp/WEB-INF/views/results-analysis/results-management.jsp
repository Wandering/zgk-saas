<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 学校成绩管理</title>
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
                        <a href="#">首页</a>
                    </li>
                    <li class="active">学校成绩管理</li>
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
                                <div class="radio">
                                    <label>
                                        <input name="results-radio" type="radio" value="1" class="ace"/>
                                        <span class="lbl">高一</span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input name="results-radio" type="radio" value="2" class="ace"/>
                                        <span class="lbl">高二</span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input name="results-radio" type="radio" value="3" class="ace"/>
                                        <span class="lbl">高三</span>
                                    </label>
                                </div>
                            </div>
                            <div class="btns">
                                <button class="btn btn-danger" id="uploadResultsBtn">上传成绩</button>
                                <button class="btn btn-inverse">修改</button>
                                <button class="btn btn-success">删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center">
                                        <label>
                                            <input type="checkbox" class="ace"/>
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="center">考试名称</th>
                                    <th class="center">年级</th>
                                    <th class="center">考试时间</th>
                                    <th class="center">成绩上传时间</th>
                                    <th class="center"></th>
                                </tr>
                                </thead>
                                <tbody id="results-tbody"></tbody>
                            </table>
                        </div>

                        <div id="uploadLayer" class="uploadLayer">
                            <div class="form-horizontal upload-layer">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="examName"> 考试名称 </label>
                                    <div class="col-sm-9">
                                        <input type="text" id="examName" placeholder="输入考试名称"
                                               class="col-xs-10 col-sm-10"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="sel-grade">
                                        年级 </label>
                                    <div class="col-sm-9">
                                        <select class="sel-role col-xs-10 col-sm-10" id="sel-grade">
                                            <option value="00">选择年级</option>
                                            <option value="1">1年级</option>
                                            <option value="2">2年级</option>
                                            <option value="3">3年级</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="account-name">
                                        考试时间 </label>
                                    <div class="col-sm-9">
                                        <input class="col-xs-10 col-sm-10 date-picker" id="account-name" type="text"
                                               placeholder="选择考试时间" data-date-format="yyyy-mm-dd"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label no-padding-right" for="account-phone">
                                        添加成绩 </label>
                                    <div class="col-sm-9">
                                        <button class="btn btn-pink">添加账号</button>(智能上传一个文件)
                                        <p><a href="">请先导出Excel模板,进行填写</a></p>
                                        <p>温馨提示:上传与模板不一致的成绩单,系统无法识别</p>
                                    </div>
                                </div>
                                <div class="btn-box">
                                    <button class="btn btn-info save-btn">保存</button>
                                    <button class="btn btn-primary close-btn">取消</button>
                                </div>
                            </div>
                        </div>


                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="results-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" class="ace"/>
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{examName}}</td>
        <td class="center">{{grade}}</td>
        <td class="center">{{examTime}}</td>
        <td class="center">{{createDate}}</td>
        <td class="center"><a href="{{uploadFilePath}}" target="_blank">{{excel uploadFilePath}}</a></td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp" %>
<link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/datepicker.css">
<script src="<%=ctx%>/static/src/lib/assets/js/date-time/bootstrap-datepicker.min.js"></script>
<script src="<%=ctx%>/static/src/js/results-analysis/results-management.js"></script>
</body>
</html>
