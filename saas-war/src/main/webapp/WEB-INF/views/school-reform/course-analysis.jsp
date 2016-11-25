<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 选课分析</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/school-reform/course-analysis.css" />
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
                            <li>高考改革</li>
                            <li class="active">选课分析</li>
                        </ul>
                    </div>
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>往年选课分析</h3>
                                </div>
                                <div class="course-analysis-box">
                                    <ul class="course-analysis-list">
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-physics" />
                                            <label for="course-physics">物理</label>
                                        </li>
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-chemistry" />
                                            <label for="course-chemistry">化学</label>
                                        </li>
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-biology" />
                                            <label for="course-biology">生物</label>
                                        </li>
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-geography" />
                                            <label for="course-geography">地理</label>
                                        </li>
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-history" />
                                            <label for="course-history">历史</label>
                                        </li>
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-politics" />
                                            <label for="course-politics">政治</label>
                                        </li>
                                        <li>
                                            <input type="checkbox" name="course-analysis" id="course-technology" />
                                            <label for="course-technology">通用技术</label>
                                        </li>
                                    </ul>
                                    <div id="historyCourseAnalysisChart" style="width: 90%;height: 385px;"></div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>单科选课情况分析</h3>
                                </div>
                                <div class="single-course-analysis-box">
                                    <ul class="single-course-analysis-list">
                                        <li>
                                            <input type="radio" name="senior-analysis" id="senior-two" checked />
                                            <label for="senior-two">高二</label>
                                        </li>
                                        <li>
                                            <input type="radio" name="senior-analysis" id="senior-three" />
                                            <label for="senior-three">高三</label>
                                        </li>
                                    </ul>
                                    <div class="single-course-info">
                                        <span class="item">物理（0人）</span>
                                        <span class="item">化学（0人）</span>
                                        <span class="item">生物（0人）</span>
                                        <span class="item">政治（0人）</span>
                                        <span class="item">地理（0人）</span>
                                        <span class="item">历史（0人）</span>
                                        <span class="item">通用技术（0人）</span>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title"></div>
                                <div class="part-course-analysis">
                                    <div class="title">前<span class="stu-count">0</span>名学生选课情况分析</div>
                                    <div class="analysis-ratio">
                                        <span class="total-student">高二共计考生数量<span class="stu-count">0</span>人</span>
                                        <div class="ratio-detail">
                                            <span class="ratio-left reach-line">预计0人上线</span>
                                            <span class="ratio-right"></span>
                                        </div>
                                        <div id="partCourseAnalysisChart" style="width: 100%;height: 361px;margin-bottom: 35px;"></div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title"><h3>组合选课情况分析</h3></div>
                                <div class="group-course-analysis-box">
                                    <%--<ul class="group-course-analysis-list">--%>
                                        <%--<li>--%>
                                            <%--<input type="radio" name="group-analysis" id="group-item1" checked />--%>
                                            <%--<label for="group-item1">物理+化学+生物</label>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<input type="radio" name="group-analysis" id="group-item2" />--%>
                                            <%--<label for="group-item2">物理+化学+生物</label>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<input type="radio" name="group-analysis" id="group-item3" />--%>
                                            <%--<label for="group-item3">物理+化学+生物</label>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<input type="radio" name="group-analysis" id="group-item4" />--%>
                                            <%--<label for="group-item4">物理+化学+生物</label>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<input type="radio" name="group-analysis" id="group-item5" />--%>
                                            <%--<label for="group-item5">物理+化学+生物</label>--%>
                                        <%--</li>--%>
                                        <%--<li>--%>
                                            <%--<input type="radio" name="group-analysis" id="group-item6" />--%>
                                            <%--<label for="group-item6">物理+化学+生物</label>--%>
                                        <%--</li>--%>
                                    <%--</ul>--%>
                                    <div id="groupCourseAnalysisBar" style="width: 100%;height: 350px;margin: 20px auto 0;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="./../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
        <script src="<%=ctx%>/static/src/js/school-reform/course-analysis.js"></script>
    </body>
</html>
