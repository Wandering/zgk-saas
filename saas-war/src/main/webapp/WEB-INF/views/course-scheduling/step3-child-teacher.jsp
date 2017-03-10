<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--教师课表--%>
<div id="step3-child-teacher" class="role-scheduling-content">
    <div class="select-condition">

        <select id="select-queryCourse">
            <option value="">请选择科目</option>
        </select>
        <select id="select-teacher">
        </select>
        <span class="teacher-name"><span class="teacher-label"></span><span class="course-label"></span></span>
    </div>
    <div class="scheduling-info">
        <div class="fl colors-box">
            <p class="colors-info">调课说明：<span class="color1"></span>可调空闲区域&nbsp;&nbsp;&nbsp;&nbsp;<span class="color2"></span>与预设规则冲突&nbsp;&nbsp;&nbsp;&nbsp;<span class="color3"></span>该区域已经设置不排课&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <p class="colors-tips">温馨提示:</p>
            <p class="colors-tips"><span class="color1"></span><span class="color2"></span><span class="color3"></span></span>三种颜色下都可以调整课表，白色区域不可以调整课表区域。</p>
            <p class="colors-tips"><span class="color2"></span>三种颜色下都可以调整课表，白色区域不可以调整课表区域。</p>
            <p class="colors-tips"><span class="color3"></span>与预设的不排课规则冲突，包括年级不排课、班级不排课、课程不排课、老师不排课</p>
        </div>
        <button class="fr btn btn-warning output-tpl2" id="export-teacher-table"><i class="icon-output-down"></i>导出所有教师课程表</button>
    </div>
    <table class="table">
        <thead id="teacher-thead-list">
        </thead>
        <tbody id="teacher-tbody-list" class="check-template ui-sortable">
        </tbody>
    </table>
</div>
<script id="teacher-thead-list-template" type="text/x-handlebars-template">
    <tr>
        {{{thead bizData.result.teachDate}}}
    </tr>
</script>
<script id="teacher-tbody-list-template" type="text/x-handlebars-template">
    {{{week bizData.result}}}
</script>
