<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 选课指导</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/school-reform/course-guide.css" />
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
                        <li class="active">选课指导</li>
                    </ul>
                </div>
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>历年单科选课对应上线情况</h3>
                            </div>
                            <div class="history-subject-situation">
                                <div id="subjectCourseBar" style="width: 80%;height: 300px;"></div>
                                <ul class="subject-chk">
                                    <li>
                                        <input type="checkbox" name="single-course-all" id="all-subject-show" />
                                        <label for="all-subject-show">全部</label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="jishu-show" />
                                        <label for="jishu-show"><i class="jishu"></i><span>通用技术</span></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="zhengzhi-show" />
                                        <label for="zhengzhi-show"><i class="zhengzhi"></i><span>政治</span></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="lishi-show" />
                                        <label for="lishi-show"><i class="lishi"></i><span>历史</span></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="dili-show" />
                                        <label for="dili-show"><i class="dili"></i><span>地理</span></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="shengwu-show" />
                                        <label for="shengwu-show"><i class="shengwu"></i><span>生物</span></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="huaxue-show" />
                                        <label for="huaxue-show"><i class="huaxue"></i><span>化学</span></label>
                                    </li>
                                    <li>
                                        <input type="checkbox" name="single-course" id="wuli-show" />
                                        <label for="wuli-show"><i class="wuli"></i><span>物理</span></label>
                                    </li>
                                </ul>
                            </div>
                            <div class="course-bar-tips">注：2017届学生表示2017年参加高考的学生，入学年份为：2014年</div>
                            <div class="course-bar-analyse">根据图表显示，<span id="maxYear">****</span>届录取数人数最多，选课情况为：</div>
                            <ul class="course-bar-analyse-results">
                                <li>
                                    <span class="span-wuli" id="percent-wuli">0%</span>的学生选择<span class="span-wuli">物理</span>
                                </li>
                                <li>
                                    <span class="span-huaxue" id="percent-huaxue">0%</span>的学生选择<span class="span-huaxue">化学</span>
                                </li>
                                <li>
                                    <span class="span-shengwu" id="percent-shengwu">0%</span>的学生选择<span class="span-shengwu">生物</span>
                                </li>
                                <li>
                                    <span class="span-zhengzhi" id="percent-zhengzhi">0%</span>的学生选择<span class="span-zhengzhi">政治</span>
                                </li>
                                <li>
                                    <span class="span-lishi" id="percent-lishi">0%</span>的学生选择<span class="span-lishi">历史</span>
                                </li>
                                <li>
                                    <span class="span-dili" id="percent-dili">0%</span>的学生选择<span class="span-dili">地理</span>
                                </li>
                                <li>
                                    <span class="span-jishu" id="percent-jishu">0%</span>的学生选择<span class="span-jishu">通用技术</span>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>历届学生弱势学科</h3>
                                <span class="title-tips">历年弱势学科是各届学生的弱势学科的统计分析</span>
                            </div>
                            <div class="webkSubject">
                                <div id="weakSubjectChart1" class="weakSubjectChart"></div>
                                <div id="weakSubjectChart2" class="weakSubjectChart"></div>
                                <div id="weakSubjectChart3" class="weakSubjectChart"></div>
                                <div id="weakSubjectChart4" class="weakSubjectChart"></div>
                                <div id="weakSubjectChart5" class="weakSubjectChart"></div>
                                <div class="weakSubjectBottom">
                                    <ul>
                                        <li>
                                            <i class="jishu"></i>
                                            <span>通用技术</span>
                                        </li>
                                        <li>
                                            <i class="zhengzhi"></i>
                                            <span>政治</span>
                                        </li>
                                        <li>
                                            <i class="lishi"></i>
                                            <span>历史</span>
                                        </li>
                                        <li>
                                            <i class="dili"></i>
                                            <span>地理</span>
                                        </li>
                                        <li>
                                            <i class="shengwu"></i>
                                            <span>生物</span>
                                        </li>
                                        <li>
                                            <i class="huaxue"></i>
                                            <span>化学</span>
                                        </li>
                                        <li>
                                            <i class="wuli"></i>
                                            <span>物理</span>
                                        </li>
                                    </ul>
                                </div>
                                <div class="weak-subject-analyse">根据图表显示，5年间：</div>
                                <ul class="weak-subject-analyse-results">
                                    <li>
                                        人数递增的弱势学科是<span class="span-wuli">物理</span>、<span class="span-huaxue">化学</span>
                                    </li>
                                    <li>
                                        人数递减的弱势学科是<span class="span-shengwu">生物</span>、<span class="span-lishi">历史</span>、<span class="span-zhengzhi">政治</span>、<span class="span-dili">地理</span>
                                    </li>
                                    <li>
                                        人数累计增加的弱势学科是<span class="span-wuli">物理</span>、<span class="span-huaxue">化学</span>
                                    </li>
                                    <li>
                                        人数累计减少的弱势学科是<span class="span-jishu">通用技术</span>、<span class="span-zhengzhi">政治</span>
                                    </li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>招生计划分析</h3>
                            </div>
                            <ul class="enrolling-plan-radio-group">
                                <li>
                                    <input type="radio" name="plan-radio" id="year1" />
                                    <label for="year1">2017</label>
                                </li>
                                <li>
                                    <input type="radio" name="plan-radio" id="year2" />
                                    <label for="year2">2018</label>
                                </li>
                                <li>
                                    <input type="radio" name="plan-radio" id="year3" />
                                    <label for="year3">2019</label>
                                </li>
                            </ul>
                            <div class="plan-table-title">选课组合招生计划情况</div>
                            <table class="plan-table">
                                <thead>
                                    <tr>
                                        <th>排名</th>
                                        <th>组合</th>
                                        <th>专业选择</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Top1</td>
                                        <td>物理+化学+生物</td>
                                        <td>1039个学校10563个专业可选</td>
                                    </tr>
                                    <tr>
                                        <td>Top2</td>
                                        <td>物理+化学+生物</td>
                                        <td>12336个学校    173个专业可选</td>
                                    </tr>
                                    <tr>
                                        <td>Top3</td>
                                        <td>物理+化学+生物</td>
                                        <td>12336个学校    173个专业可选</td>
                                    </tr>
                                    <tr>
                                        <td>4</td>
                                        <td>物理+化学+生物</td>
                                        <td>12336个学校    173个专业可选</td>
                                    </tr>
                                    <tr>
                                        <td>5</td>
                                        <td>物理+化学+生物</td>
                                        <td>12336个学校    173个专业可选</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>师资配置</h3>
                            </div>
                            <ul class="teacher-configuration-radio-group">
                                <li>
                                    <input type="radio" name="plan-radio" id="senior1" />
                                    <label for="senior1">高一</label>
                                </li>
                                <li>
                                    <input type="radio" name="plan-radio" id="senior2" />
                                    <label for="senior2">高二</label>
                                </li>
                            </ul>
                            <table class="plan-table teacher-config-table">
                                <thead>
                                    <tr>
                                        <th>科目</th>
                                        <th>教师数量</th>
                                        <th>最大带班数</th>
                                        <th>最大选课人数</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td><a href="javascript: void(0);" class="config-btn">物理</a></td>
                                        <td>5</td>
                                        <td>15</td>
                                        <td>1039个学校10563个专业可选</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="./../common/footer.jsp"%>
    <script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
    <script src="<%=ctx%>/static/src/js/school-reform/course-guide.js"></script>
    </body>
</html>
