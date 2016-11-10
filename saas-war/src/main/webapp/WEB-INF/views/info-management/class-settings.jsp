<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 角色管理</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/.css" />
    </head>
    <body>
        <div class="main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="title-2">
                            <span class="txt-t"></span>
                            <div class="btns">
                                <button class="btn btn-pink" id="addRole-btn">添加</button>
                                <button class="btn btn-success">批量删除</button>
                            </div>
                            <div class="main-title"></div>
                        </div>
                        <div>
                            <table id="class-table" class="table">
                                <thead>
                                    <%--<tr>--%>
                                        <%--<th class="center">--%>
                                            <%--<label>--%>
                                                <%--<input type="checkbox" class="ace" />--%>
                                                <%--<span class="lbl"></span>--%>
                                            <%--</label>--%>
                                        <%--</th>--%>
                                        <%--<th class="center">编号</th>--%>
                                        <%--<th class="center">班级名称</th>--%>
                                        <%--<th class="center">班级类型</th>--%>
                                        <%--<th class="center">班级编号</th>--%>
                                        <%--<th class="center">入学年份</th>--%>
                                        <%--<th class="center">班主任</th>--%>
                                        <%--<th class="center">所属年级</th>--%>
                                        <%--<th class="center">班级科类</th>--%>
                                        <%--<th class="center">班级人数</th>--%>
                                    <%--</tr>--%>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@ include file="./../common/footer.jsp"%>
        <script src="<%=ctx%>/static/src/js/info-management/class-settings.js"></script>
    </body>
</html>
