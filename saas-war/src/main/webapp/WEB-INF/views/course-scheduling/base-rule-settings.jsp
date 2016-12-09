<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 角色管理</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/course-scheduling/course-scheduling-step2.css"/>
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
                        <div class="main-title">
                            <h3>排课任务</h3>
                        </div>
                        <div class="common-back-title">
                            <a href="/course-scheduling">&lt;返回</a>
                            <span class="title">高一排课</span>
                        </div>
                        <div class="course-scheduling-base">
                            <div class="procedure">
                                <a href="/course-scheduling-step1" class="disabled"><i>1</i>基本信息设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="/course-scheduling-step2"><i>2</i>排课规则设置</a>
                                <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
                                <a href="/course-scheduling-step3" class="disabled"><i>3</i>自动排课</a>
                            </div>
                        </div>
                        <ul class="rule-item-tab">
                            <li>
                                <a href="/course-scheduling-step2">不排课时间</a>
                            </li>
                            <li>
                                <a href="/course-no-proceed">不连堂</a>
                            </li>
                            <li>
                                <a href="/class-mixed">合班</a>
                            </li>
                            <li>
                                <a href="/base-rule-settings" class="active">基本规则设置</a>
                            </li>
                        </ul>
                        <div class="rule-content">
                            <div class="base-rule-tab">
                                <ul class="rule-tab-list">
                                    <li>
                                        <a href="javascript: void(0);" class="active">教案平齐</a>
                                    </li>
                                    <li>
                                        <a href="javascript: void(0);">周任课规则</a>
                                    </li>
                                    <li>
                                        <a href="javascript: void(0);">日任课规则</a>
                                    </li>
                                    <li>
                                        <a href="javascript: void(0);">连上限制</a>
                                    </li>
                                </ul>
                            </div>
                            <select id="course-list">
                            </select>
                            <script type="text/x-handlebars-template" id="course-list-tpl">
                                {{#each this}}
                                <option value="{{courseId}}" cTime="{{time}}">{{courseName}}</option>
                                {{/each}}
                            </script>
                            <div class="base-rule-content">

                                <span class="rule-tips">教案平齐：张三老师教完1班教2班，而不是1班上了两节课后2班才上1节课</span>
                                <table class="base-rule-table table  table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th width="250px">课程</th>
                                        <th width="250px">教师</th>
                                        <th>重要程度</th>
                                    </tr>
                                    </thead>
                                    <tbody id="japq-list">
                                    </tbody>
                                    <script type="text/x-handlebars-template" id="japq-list-tpl">
                                        <%--"courseId": "1",--%>
                                        <%--"courseName": "语文",--%>
                                        <%--"createDate": "",--%>
                                        <%--"id": "1",--%>
                                        <%--"importantType": "2",--%>
                                        <%--"taskId": "1",--%>
                                        <%--"teacherId": "1",--%>
                                        <%--"teacherName": "左浩",--%>
                                        <%--"tnId": "1"--%>
                                        {{#each this}}
                                        <tr>
                                            <td>{{courseName}}</td>
                                            <td>{{teacherName}}</td>
                                            <td>
                                                {{#compare importantType '==' 1}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="japq-{{@index}}" id="japq-a-{{@index}}" checked/>
                                                            <label for="japq-a-{{@index}}">非常重要</label>
                                                        </span>
                                                {{else}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="japq-{{@index}}" id="japq-a-{{@index}}"/>
                                                            <label for="japq-a-{{@index}}">非常重要</label>
                                                        </span>
                                                {{/compare}}

                                                {{#compare importantType '==' 2}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="japq-{{@index}}" id="japq-b-{{@index}}" checked/>
                                                            <label for="japq-b-{{@index}}">相对重要</label>
                                                        </span>
                                                {{else}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="japq-{{@index}}" id="japq-b-{{@index}}"/>
                                                            <label for="japq-b-{{@index}}">相对重要</label>
                                                        </span>
                                                {{/compare}}

                                                {{#compare importantType '==' 3}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="japq-{{@index}}" id="japq-c-{{@index}}" checked/>
                                                            <label for="japq-c-{{@index}}">一般</label>
                                                        </span>
                                                {{else}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="japq-{{@index}}" id="japq-c-{{@index}}"/>
                                                            <label for="japq-c-{{@index}}">一般</label>
                                                        </span>
                                                {{/compare}}
                                            </td>
                                        </tr>
                                        {{/each}}
                                    </script>
                                </table>
                                <button type="button" class="btn-teaching-plan" id="btn-teaching-plan">保存</button>
                            </div>
                            <div class="base-rule-content base-rule-content-none">
                                <%--<select id="week-course-list">--%>
                                    <%--<option value="语文">语文</option>--%>
                                    <%--<option value="数学">数学</option>--%>
                                    <%--<option value="英语">英语</option>--%>
                                    <%--<option value="物理">物理</option>--%>
                                    <%--<option value="化学">化学</option>--%>
                                    <%--<option value="生物">生物</option>--%>
                                    <%--<option value="政治">政治</option>--%>
                                    <%--<option value="历史">历史</option>--%>
                                    <%--<option value="地理">地理</option>--%>
                                <%--</select>--%>
                                <table class="base-rule-table table  table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th width="220px">课程</th>
                                        <th width="220px">教师</th>
                                        <th width="260px">周内分布</th>
                                        <th>重要程度</th>
                                    </tr>
                                    </thead>
                                    <tbody id="zrk-list">
                                    </tbody>
                                    <script type="text/x-handlebars-template" id="zrk-list-tpl">
                                        <%--"courseId": "1",--%>
                                        <%--"courseName": "语文",--%>
                                        <%--"createDate": "",--%>
                                        <%--"id": "1",--%>
                                        <%--"importantType": "1",//重要程度--%>
                                        <%--"taskId": "1",--%>
                                        <%--"teacherId": "1",--%>
                                        <%--"teacherName": "左浩",--%>
                                        <%--"tnId": "1",--%>
                                        <%--"weekType": "1"//周内分布--%>
                                        {{#each this}}
                                        <tr>
                                            <td>{{courseName}}</td>
                                            <td>{{teacherName}}</td>
                                            <td>
                                                {{#compare weekType '==' 1}}
                                                    <span class="rule-radio rule-radio1">
                                                        <input type="radio" name="zrk-weekType-{{@index}}" id="zrk-weekType-a-{{@index}}" checked/>
                                                        <label for="zrk-weekType-a-{{@index}}">周内分散</label>
                                                    </span>
                                                    <span class="rule-radio rule-radio1">
                                                        <input type="radio" name="zrk-weekType-{{@index}}" id="zrk-weekType-b-{{@index}}"/>
                                                        <label for="zrk-weekType-b-{{@index}}">周内集中</label>
                                                    </span>
                                                {{/compare}}
                                                {{#compare weekType '==' 2}}
                                                    <span class="rule-radio rule-radio1">
                                                            <input type="radio" name="zrk-weekType-{{@index}}" id="zrk-weekType-a-{{@index}}"/>
                                                            <label for="zrk-weekType-a-{{@index}}">周内分散</label>
                                                        </span>
                                                        <span class="rule-radio rule-radio1">
                                                            <input type="radio" name="zrk-weekType-{{@index}}" id="zrk-weekType-b-{{@index}}" checked/>
                                                            <label for="zrk-weekType-b-{{@index}}">周内集中</label>
                                                        </span>
                                                {{/compare}}
                                            </td>
                                            <td>
                                                {{#compare importantType '==' 1}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="zrk-{{@index}}" id="zrk-a-{{@index}}" checked/>
                                                            <label for="zrk-a-{{@index}}">非常重要</label>
                                                        </span>
                                                {{else}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="zrk-{{@index}}" id="zrk-a-{{@index}}"/>
                                                            <label for="zrk-a-{{@index}}">非常重要</label>
                                                        </span>
                                                {{/compare}}

                                                {{#compare importantType '==' 2}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="zrk-{{@index}}" id="zrk-b-{{@index}}" checked/>
                                                            <label for="zrk-b-{{@index}}">相对重要</label>
                                                        </span>
                                                {{else}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="zrk-{{@index}}" id="zrk-b-{{@index}}"/>
                                                            <label for="zrk-b-{{@index}}">相对重要</label>
                                                        </span>
                                                {{/compare}}

                                                {{#compare importantType '==' 3}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="zrk-{{@index}}" id="zrk-c-{{@index}}" checked/>
                                                            <label for="zrk-c-{{@index}}">一般</label>
                                                        </span>
                                                {{else}}
                                                <span class="rule-radio" importantId={{id}}>
                                                            <input type="radio" name="zrk-{{@index}}" id="zrk-c-{{@index}}"/>
                                                            <label for="zrk-c-{{@index}}">一般</label>
                                                        </span>
                                                {{/compare}}
                                            </td>
                                        </tr>
                                        {{/each}}
                                    </script>
                                </table>
                                <button type="button" class="btn-week-save" id="btn-week-save">保存</button>
                            </div>
                            <div class="base-rule-content base-rule-content-none">
                                <%--<select id="day-course-list">--%>
                                    <%--<option value="语文">语文</option>--%>
                                    <%--<option value="数学">数学</option>--%>
                                    <%--<option value="英语">英语</option>--%>
                                    <%--<option value="物理">物理</option>--%>
                                    <%--<option value="化学">化学</option>--%>
                                    <%--<option value="生物">生物</option>--%>
                                    <%--<option value="政治">政治</option>--%>
                                    <%--<option value="历史">历史</option>--%>
                                    <%--<option value="地理">地理</option>--%>
                                <%--</select>--%>
                                <table class="base-rule-table table  table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th width="220px">课程</th>
                                        <th width="220px">教师</th>
                                        <th width="260px">日内分布</th>
                                        <th>重要程度</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>语文</td>
                                        <td>李华</td>
                                        <td>
                                                <span class="rule-radio rule-radio1">
                                                    <input type="radio" name="teacher-a" id="radio116"/>
                                                    <label for="radio116">日内集中</label>
                                                </span>
                                            <span class="rule-radio rule-radio1">
                                                    <input type="radio" name="teacher-a" id="radio117"/>
                                                    <label for="radio117">日内分散</label>
                                                </span>
                                        </td>
                                        <td>
                                                <span class="rule-radio">
                                                    <input type="radio" name="teacher-a" id="radio16"/>
                                                    <label for="radio16">非常重要</label>
                                                </span>
                                            <span class="rule-radio">
                                                    <input type="radio" name="teacher-a" id="radio17"/>
                                                    <label for="radio17">相对重要</label>
                                                </span>
                                            <span class="rule-radio">
                                                    <input type="radio" name="teacher-a" id="radio18"/>
                                                    <label for="radio18">一般</label>
                                                </span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <button type="button" class="btn-day-save" id="btn-day-save">保存</button>
                            </div>
                            <div class="base-rule-content base-rule-content-none">
                                <%--<select id="proceed-course-list">--%>
                                    <%--<option value="语文">语文</option>--%>
                                    <%--<option value="数学">数学</option>--%>
                                    <%--<option value="英语">英语</option>--%>
                                    <%--<option value="物理">物理</option>--%>
                                    <%--<option value="化学">化学</option>--%>
                                    <%--<option value="生物">生物</option>--%>
                                    <%--<option value="政治">政治</option>--%>
                                    <%--<option value="历史">历史</option>--%>
                                    <%--<option value="地理">地理</option>--%>
                                <%--</select>--%>
                                <table class="base-rule-table table  table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th width="220px">课程</th>
                                        <th width="220px">教师</th>
                                        <th width="260px">一天连上节数</th>
                                        <th>重要程度</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <td>语文</td>
                                        <td>李华</td>
                                        <td>
                                            <span class="rule-radio rule-radio1">
                                                <input type="radio" name="teacher-a" id="radio119"/>
                                                <label for="radio119">连上2节</label>
                                            </span>
                                            <span class="rule-radio rule-radio1">
                                                <input type="radio" name="teacher-a" id="radio120"/>
                                                <label for="radio120">连上3节</label>
                                            </span>
                                        </td>
                                        <td>
                                            <span class="rule-radio">
                                                <input type="radio" name="teacher-a" id="radio19"/>
                                                <label for="radio19">非常重要</label>
                                            </span>
                                            <span class="rule-radio">
                                                <input type="radio" name="teacher-a" id="radio20"/>
                                                <label for="radio20">相对重要</label>
                                            </span>
                                            <span class="rule-radio">
                                                <input type="radio" name="teacher-a" id="radio21"/>
                                                <label for="radio21">一般</label>
                                            </span>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                <button type="button" class="btn-base-rule-save" id="btn-base-rule-save">保存</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/course-scheduling/base-rule-settings.js"></script>
</body>
</html>
