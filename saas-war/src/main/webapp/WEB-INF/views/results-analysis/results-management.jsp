<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 学校成绩管理</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/results-analysis.css">
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
                                        <input name="results-radio" type="radio" value="1" class="ace" />
                                        <span class="lbl">高一</span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input name="results-radio" type="radio" value="2"  class="ace" />
                                        <span class="lbl">高二</span>
                                    </label>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <label>
                                        <input name="results-radio" type="radio" value="3"  class="ace" />
                                        <span class="lbl">高三</span>
                                    </label>
                                </div>
                            </div>
                            <div class="btns">
                                <button class="btn btn-danger">上传成绩</button>
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
                                            <input type="checkbox" class="ace" />
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="center">考试名称</th>
                                    <th class="center">年级</th>
                                    <th class="center">考试时间</th>
                                    <th class="center">成绩上传时间</th>
                                    <th class="center">状态</th>
                                    <th class="center">创建时间</th>
                                </tr>
                                </thead>
                                <tbody id="account-tbody"></tbody>
                            </table>
                        </div>



                        <!-- PAGE CONTENT ENDS -->
                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<script id="account-template" type="text/x-handlebars-template">
    {{#each bizData}}
    <tr>
        <td class="center">
            <label>
                <input type="checkbox" class="ace" />
                <span class="lbl"></span>
            </label>
        </td>
        <td class="center">{{userAccount}}</td>
        <td class="center">{{userName}}</td>
        <td class="center">{{telephone}}</td>
        <td class="center">管理员</td>
        <td class="center">启用</td>
        <td class="center">2015.9.23 16:38</td>
    </tr>
    {{/each}}
</script>
<%@ include file="./../common/footer.jsp"%>
<script src="<%=ctx%>/static/src/js/results-analysis/results-management.js"></script>
</body>
</html>
