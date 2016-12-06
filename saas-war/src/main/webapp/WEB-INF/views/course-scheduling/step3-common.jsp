<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="main-title">
    <h3>排课任务</h3>
</div>
<div class="course-scheduling-base">
    <div class="procedure">
        <a href="javascript: void(0);" class="disabled"><i>1</i>基本信息设置</a>
        <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
        <a href="javascript: void(0);" class="disabled"><i>2</i>排课规则设置</a>
        <span class="gap"><i></i><i></i><i></i><i></i><i></i></span>
        <a href="javascript: void(0);"><i>3</i>自动排课</a>
    </div>
</div>
<div id="one-key-page" class="dh">
    <div class="btn-one-key">一键生成课表</div>
    <div class="info-modify">
        <p>基础信息/排课规则已更改，是否重新排课</p>
        <div class="retry-scheduling">重新排课</div>
        <div class="look-origin-schedule">查看原课表</div>
    </div>
    <div class="scheduling-error">
        <p>排课失败~ 因为*******，所以无法排出课表。请调整**规则/信息后，再进行排课</p>
    </div>
</div>
<%--教室课表|教师课表|学生课表|总课表--%>
<div id="role-scheduling-tab" class="">
    <div class="role-tab">
        <ul>
            <li class="no-before active"><i class="icon-step3-class-yes"></i><i
                    class="icon-step3-class-no"></i>教室课表
            </li>
            <li><i class="icon-step3-teacher-yes"></i><i class="icon-step3-teacher-no"></i>教师课表
            </li>
            <li><i class="icon-step3-std-yes"></i><i class="icon-step3-std-no"></i>学生课表</li>
            <li><i class="icon-step3-all-yes"></i><i class="icon-step3-all-no"></i>总课表</li>
        </ul>
    </div>
</div>