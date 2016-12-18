<%--
  Created by IntelliJ IDEA.
  User: pdeng
  Date: 2016/12/6
  Time: 下午2:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--总课表--%>
<div id="step3-child-all" class="mt15 role-scheduling-content">
    <div class="scheduling-info">
        <div class="fl">总课表</div>
        <button class="fr btn btn-warning output-tpl" id="output-tpl"><i class="icon-output-down"></i>导出总课表</button>
    </div>
    <div class="all-time-date-container">
        <table class="table table-bordered" id="all-timetable">

        </table>
        <table class="table table-bordered">
            <thead id="all-thead-list"></thead>
            <tbody id="all-tbody-list"></tbody>
        </table>

    </div>

    <%--<script type="text/x-handlebars-template" id="all-timetable-tpl">--%>
        <%--<thead>--%>
        <%--<tr>--%>
            <%--<th></th>--%>
            <%--{{#each teachDate}}--%>
            <%--<th class="center second-th-width">{{this}}</th>--%>
            <%--{{/each}}--%>
        <%--</tr>--%>
        <%--</thead>--%>
        <%--<tbody class="check-template ui-sortable">--%>
        <%--<tr>--%>
            <%--<th></th>--%>
            <%--{{#each roomData.[0]}}--%>
            <%--<th class="no-p-m">--%>
                <%--{{#each this}}--%>
                <%--<span class="create-class-number common-span-w">{{addOne @index}}</span>--%>
                <%--{{/each}}--%>
            <%--</th>--%>
            <%--{{/each}}--%>
        <%--</tr>--%>
        <%--{{#each room}}--%>
        <%--<tr>--%>
            <%--<th class="center">{{this}}</th>--%>
            <%--{{#creatClass @index ../roomData}}{{/creatClass}}--%>
        <%--</tr>--%>
        <%--{{/each}}--%>
        <%--</tbody>--%>
    <%--</script>--%>
</div>



<script id="all-thead-list-template" type="text/x-handlebars-template">
    <tr>
        {{{thead bizData.result}}}
    </tr>
</script>
<script id="all-tbody-list-template" type="text/x-handlebars-template">
    {{{room bizData.result}}}
</script>

<%--<script>--%>

    <%--console.log($(window).width()-190-40);--%>

    <%--$('.all-time-date-container').css({--%>
        <%--'width':$(window).width()-190-40,--%>
        <%--'overflow':'auto'--%>
    <%--});--%>

    <%--var res = {--%>
        <%--"bizData": {--%>
            <%--"result": {--%>
                <%--"room": "教室3|教室2|教室1",--%>
                <%--"roomData": [--%>
                    <%--[--%>
                        <%--[--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"历史\n(李洋18)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"政治\n(李洋17)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"地理\n(李洋19)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"通用技术\n(李洋20)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"历史\n(李洋18)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"通用技术\n(李洋20)"--%>
                        <%--]--%>
                    <%--],--%>
                    <%--[--%>
                        <%--[--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"语文\n(李洋11)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"政治\n(李洋17)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"地理\n(李洋19)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"通用技术\n(李洋20)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"英语\n(李洋13)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"政治\n(李洋17)"--%>
                        <%--]--%>
                    <%--],--%>
                    <%--[--%>
                        <%--[--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"化学\n(李洋15)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"物理\n(李洋14)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"化学\n(李洋15)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"语文\n(李洋11)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"语文\n(李洋11)",--%>
                            <%--"数学\n(李洋12)",--%>
                            <%--"政治\n(李洋17)",--%>
                            <%--"数学\n(李洋12)"--%>
                        <%--],--%>
                        <%--[--%>
                            <%--"英语\n(李洋13)",--%>
                            <%--"通用技术\n(李洋20)",--%>
                            <%--"生物\n(李洋16)",--%>
                            <%--"物理\n(李洋14)",--%>
                            <%--"历史\n(李洋18)",--%>
                            <%--"地理\n(李洋19)",--%>
                            <%--"化学\n(李洋15)"--%>
                        <%--]--%>
                    <%--]--%>
                <%--],--%>
                <%--"teachDate": "星期一|星期二|星期三|星期四|星期五",--%>
                <%--"teachTime": "430"--%>
            <%--}--%>
        <%--},--%>
        <%--"rtnCode": "0000000",--%>
        <%--"ts": 1481874458121--%>
    <%--}--%>


    <%--var theadTemplate = Handlebars.compile($("#all-thead-list-template").html());--%>
    <%--Handlebars.registerHelper("thead", function (res) {--%>
        <%--var wkDate = res.teachTime;--%>
        <%--var Num1 = parseInt(wkDate.substr(0, 1));--%>
        <%--var Num2 = parseInt(wkDate.substr(1, 1));--%>
        <%--var Num3 = parseInt(wkDate.substr(2, 1));--%>
        <%--var itemCount = Num1 + Num2 + Num3;--%>
        <%--var resData = res.teachDate.split('|');--%>
        <%--var str = '<td></td>';--%>
        <%--for (var i = 0; i < resData.length; i++) {--%>
            <%--str += '<td class="center" colspan="' + itemCount + '">' + resData[i] + '</td>';--%>
        <%--}--%>
        <%--return str;--%>
    <%--});--%>
    <%--$("#all-thead-list").html(theadTemplate(res));--%>


    <%--var tbodyTemplate = Handlebars.compile($("#all-tbody-list-template").html());--%>
    <%--Handlebars.registerHelper("room", function (res) {--%>
        <%--var rmWk = res.roomData;--%>
        <%--console.log(rmWk.length)--%>
        <%--var rmList = res.room.split('|');--%>
        <%--var trHtml = '';--%>
        <%--for (var i = 0; i < rmList.length; i++) {--%>
            <%--trHtml += '<tr>';--%>
            <%--trHtml += '<td class="center" style="width:100px">' + rmList[i] + '</td>';--%>
            <%--console.log(rmWk[i])--%>
            <%--for (var j = 0; j < rmWk[i].length; j++) {--%>
                <%--for (var k = 0; k < rmWk[i][j].length; k++) {--%>
                    <%--trHtml += '<td class="center">' + rmWk[i][j][k] + '</td>';--%>
                <%--}--%>
            <%--}--%>
            <%--trHtml += '</tr>';--%>
        <%--}--%>
        <%--return trHtml;--%>
    <%--});--%>
    <%--$("#all-tbody-list").html(tbodyTemplate(res));--%>
<%--</script>--%>
