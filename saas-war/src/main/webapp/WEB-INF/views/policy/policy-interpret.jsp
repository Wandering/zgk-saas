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
                                首页
                            </li>
                        </ul>
                    </div>
                    <div class="page-content">
                        <div class="row">
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>成绩组成</h3>
                                </div>
                                <div class="score-component">
                                    <div class="title">高考成绩组成为必考科目加选考科目</div>
                                    <table class="score-component-table" cellpadding="0" cellspacing="0">
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
                                                <td>必考科目</td>
                                                <td>语文、数学、外语3科</td>
                                                <td>每科满分150分</td>
                                                <td rowspan="2">750分</td>
                                            </tr>
                                            <tr>
                                                <td>选考科目</td>
                                                <td>政治、历史、地理、物理、化学、生物、通用技术7科中选择其中3科</td>
                                                <td>每科满分100分</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>分数规则</h3>
                                </div>
                                <div class="score-rule">
                                    <table class="score-rule-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>科目</th>
                                                <th>语文</th>
                                                <th>数学</th>
                                                <th>外语</th>
                                                <th>思想政治</th>
                                                <th>历史</th>
                                                <th>地理</th>
                                                <th>物理</th>
                                                <th>化学</th>
                                                <th>生物</th>
                                                <th>通用技术</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td class="gray-bg">学考分值</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                                <td>150分</td>
                                                <td>70分</td>
                                                <td>70分</td>
                                                <td>70分</td>
                                                <td>70分</td>
                                                <td>70分</td>
                                                <td>70分</td>
                                                <td>70分</td>
                                            </tr>
                                            <tr>
                                                <td class="gray-bg">选考分值</td>
                                                <td>150分</td>
                                                <td>150分</td>
                                                <td>150分</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                                <td>100分</td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <div class="level-divide">
                                        <div class="title">学考科目等级划分</div>
                                        <table class="level-divide-table" cellpadding="0" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>等级</th>
                                                    <th>A</th>
                                                    <th>B</th>
                                                    <th>C</th>
                                                    <th>D</th>
                                                    <th>E</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td class="gray-bg">人数比例</td>
                                                    <td>15%</td>
                                                    <td>30%</td>
                                                    <td>30%</td>
                                                    <td>20%</td>
                                                    <td>不超过5%</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <div class="subject-level-divide">
                                        <div class="title">选课科目等级划分及赋分</div>
                                        <table class="subject-level-divide-table" cellpadding="0" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>等级</th>
                                                    <th>1</th>
                                                    <th>2</th>
                                                    <th>3</th>
                                                    <th>4</th>
                                                    <th>5</th>
                                                    <th>6</th>
                                                    <th>7</th>
                                                    <th>8</th>
                                                    <th>9</th>
                                                    <th>10</th>
                                                    <th>11</th>
                                                    <th>12</th>
                                                    <th>13</th>
                                                    <th>14</th>
                                                    <th>15</th>
                                                    <th>16</th>
                                                    <th>17</th>
                                                    <th>18</th>
                                                    <th>19</th>
                                                    <th>20</th>
                                                    <th>21</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td class="gray-bg">人数比例</td>
                                                    <td>1%</td>
                                                    <td>2%</td>
                                                    <td>3%</td>
                                                    <td>4%</td>
                                                    <td>5%</td>
                                                    <td>6%</td>
                                                    <td>7%</td>
                                                    <td>8%</td>
                                                    <td>7%</td>
                                                    <td>7%</td>
                                                    <td>7%</td>
                                                    <td>7%</td>
                                                    <td>7%</td>
                                                    <td>7%</td>
                                                    <td>6%</td>
                                                    <td>5%</td>
                                                    <td>4%</td>
                                                    <td>3%</td>
                                                    <td>2%</td>
                                                    <td>1%</td>
                                                    <td>不超过5%</td>
                                                </tr>
                                                <tr>
                                                    <td class="gray-bg">赋分</td>
                                                    <td>100分</td>
                                                    <td>97分</td>
                                                    <td>94分</td>
                                                    <td>91分</td>
                                                    <td>88分</td>
                                                    <td>85分</td>
                                                    <td>83分</td>
                                                    <td>80分</td>
                                                    <td>77分</td>
                                                    <td>74分</td>
                                                    <td>71分</td>
                                                    <td>68分</td>
                                                    <td>65分</td>
                                                    <td>62分</td>
                                                    <td>59分</td>
                                                    <td>56分</td>
                                                    <td>53分</td>
                                                    <td>50分</td>
                                                    <td>47分</td>
                                                    <td>43分</td>
                                                    <td>40分</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xs-12">
                                <div class="main-title">
                                    <h3>考试时间</h3>
                                </div>
                                <div class="exam-time">
                                    <div class="tips">从高二开始，每年上学期的10月和下学期的4月各组织一次学考选考，考试时间一般为其中的某个周五、六、日连续3天。语文、数学、外语的高考时间为考生报名参加高考当年的6月7—8日。</div>
                                    <div class="title">各科学考、选考时间安排</div>
                                    <table class="exam-time-table" cellpadding="0" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th rowspan="2" colspan="2">日期</th>
                                                <th rowspan="2">考试科目</th>
                                                <th colspan="2">考试时间</th>
                                            </tr>
                                            <tr>
                                                <th>学考</th>
                                                <th>选考</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td rowspan="3">第一天（周五）</td>
                                                <td rowspan="2">上午</td>
                                                <td>历史</td>
                                                <td>8:10-9:10</td>
                                                <td>8:10-9:40</td>
                                            </tr>
                                            <tr>
                                                <td>物理</td>
                                                <td>10:30-11:30</td>
                                                <td>10:30-12:00</td>
                                            </tr>
                                            <tr>
                                                <td>下午</td>
                                                <td>技术</td>
                                                <td>13:30-14:30</td>
                                                <td>13:30-15:00</td>
                                            </tr>
                                            <tr>
                                                <td rowspan="4">第二天（周六）</td>
                                                <td rowspan="2">上午</td>
                                                <td>化学</td>
                                                <td>8:10-9:10</td>
                                                <td>8:10-9:40</td>
                                            </tr>
                                            <tr>
                                                <td>思想政治</td>
                                                <td>10:30-11:30</td>
                                                <td>10:30-12:00</td>
                                            </tr>
                                            <tr>
                                                <td rowspan="2">下午</td>
                                                <td>数学</td>
                                                <td>13:30-14:50</td>
                                                <td>/</td>
                                            </tr>
                                            <tr>
                                                <td>语文</td>
                                                <td>15:40-17:00</td>
                                                <td>/</td>
                                            </tr>
                                            <tr>
                                                <td rowspan="3">第三天（周日）</td>
                                                <td rowspan="2">上午</td>
                                                <td>地理</td>
                                                <td>8:10-9:10</td>
                                                <td>8:10-9:40</td>
                                            </tr>
                                            <tr>
                                                <td>生物</td>
                                                <td>10:30-11:30</td>
                                                <td>10:30-12:00</td>
                                            </tr>
                                            <tr>
                                                <td>下午</td>
                                                <td>外语</td>
                                                <td>13:30-14:30</td>
                                                <td>/</td>
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
