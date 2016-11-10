<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 角色管理</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/class-settings.css" />
    </head>
    <body>
        <div class="main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink opt-btn" id="addColumn-btn">添加</button>
                                <button class="btn btn-success opt-btn">批量删除</button>
                            </div>
                            <div class="main-title"></div>
                        </div>
                        <div>
                            <table id="class-table" class="table">
                                <thead>
                                    <tr>
                                        <th class="center">
                                            <label>
                                                <input type="checkbox" class="ace" />
                                                <span class="lbl"></span>
                                            </label>
                                        </th>
                                        <th class="center">排序</th>
                                        <th class="center">字段名称</th>
                                        <th class="center"></th>
                                    </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script id="column-list-data-template" type="text/x-handlebars-template">
            {{#each this}}
            <li><input type="checkbox" class='class-column' columnid="{{id}}" id="{{enName}}" /><label for="{{enName}}">{{chName}}</label></li>
            {{/each}}
        </script>
        <%@ include file="./../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/lib/assets/js/jquery-ui-1.12.1.js"></script>
        <script src="<%=ctx%>/static/src/js/info-management/class-settings.js"></script>
    </body>
</html>
