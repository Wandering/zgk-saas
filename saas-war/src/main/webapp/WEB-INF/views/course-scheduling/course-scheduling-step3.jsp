<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 自动排课</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step3.css"/>
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
                    <li>排选课</li>
                    <li class="active">排课任务</li>
                </ul>
            </div>
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div id="page-container">
                            <%@ include file="./../common/footer.jsp" %>
                            <%@ include file="../course-scheduling/step3-common.jsp"%>
                            <script src="/static/src/lib/jquery.table2excel/jquery.table2excel.js"></script>
                            <script>
                                $(function() {
                                    $(".output-tpl").click(function(){
                                        $(".table").table2excel({
                                            exclude: ".noExl",
                                            name: "Excel Document Name",
                                            filename: "课表",
                                            exclude_img: true,
                                            exclude_links: true,
                                            exclude_inputs: true,
                                            fileext: ".xls"
                                        });
                                    });
                                });
                            </script>
                            <div id="control-jsp">
                                <div class="bottom-page">
                                    <%@ include file="../course-scheduling/step3-child-class.jsp"%>
                                </div>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-teacher.jsp"%>
                                </div>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-student.jsp"%>
                                </div>
                                <div class="bottom-page dh">
                                    <%@ include file="../course-scheduling/step3-child-all.jsp"%>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="<%=ctx%>/static/src/js/course-scheduling/step3-common.js"></script>
<script src="<%=ctx%>/static/src/js/course-scheduling/step3-child-class.js"></script>
</body>
</html>
