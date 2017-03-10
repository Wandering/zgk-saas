<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div id="step3-child-class" class="role-scheduling-content">
    <div class="select-condition">
        <span class="scheduling-name"></span>
        <select id="select-class">
            <option value="">请选择班级</option>
        </select>
    </div>
    <div class="scheduling-info">
        <div class="fl colors-box">
            <p class="colors-info">调课说明：<span class="color1"></span>可调空闲区域&nbsp;&nbsp;&nbsp;&nbsp;<span class="color2"></span>与预设规则冲突&nbsp;&nbsp;&nbsp;&nbsp;<span class="color3"></span>该区域已经设置不排课&nbsp;&nbsp;&nbsp;&nbsp;</p>
            <p class="colors-tips">温馨提示:</p>
            <p class="colors-tips"><span class="color1"></span><span class="color2"></span><span class="color3"></span></span>三种颜色下都可以调整课表，白色区域不可以调整课表区域。</p>
            <p class="colors-tips"><span class="color2"></span>三种颜色下都可以调整课表，白色区域不可以调整课表区域。</p>
            <p class="colors-tips"><span class="color3"></span>与预设的不排课规则冲突，包括年级不排课、班级不排课、课程不排课、老师不排课</p>
        </div>
        <button class="fr btn btn-warning output-tpl1" id="export-class-table"><i class="icon-output-down"></i>导出所有班级课程表</button>
    </div>
    <table class="table" id="test1">
        <thead id="class-thead-list"></thead>
        <tbody id="class-tbody-list" class="check-template ui-sortable"></tbody>
    </table>
</div>
<script id="class-thead-list-template" type="text/x-handlebars-template">
    <tr>
        {{{thead bizData.result.teachDate}}}
    </tr>
</script>
<script id="class-tbody-list-template" type="text/x-handlebars-template">
    {{{week bizData.result}}}
</script>