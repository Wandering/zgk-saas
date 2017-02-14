<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8" />
        <title>SAAS 升学率管理</title>
        <%@ include file="./../common/meta.jsp"%>
        <link rel="stylesheet" href="<%=ctx%>/static/src/css/info-management.css" />
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
                        <li class="active">升学率设置</li>
                    </ul>
                </div>
                <div class="page-content">
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="main-title">
                                <h3>升学率管理</h3>
                            </div>
                            <div class="title-2">
                                <span class="txt-t"></span>
                                <div class="btns">
                                    <button class="btn btn-pink" id="addRole-btn">添加年份</button>
                                    <button class="btn btn-inverse" id="updateRole-btn">修改</button>
                                </div>
                            </div>
                            <div class="">
                                <table id="ratio-manage-table" class="table">
                                    <thead>
                                        <tr>
                                            <th class="center">
                                                <label>
                                                    <input type="checkbox" id="checkAll" class="ace" />
                                                    <span class="lbl"></span>
                                                </label>
                                            </th>
                                            <th class="center">序号</th>
                                            <th class="center">高考年份</th>
                                            <th class="center">高三考生数量</th>
                                            <th class="center">一本上线人数</th>
                                            <th class="center">二本上线人数</th>
                                            <th class="center">三本上线人数</th>
                                            <th class="center">高职上线人数</th>
                                        </tr>
                                    </thead>
                                    <tbody id="ratio-manage-list" class="check-template">
                                        <%--<tr>--%>
                                            <%--<td class="center">--%>
                                                <%--<label>--%>
                                                    <%--<input type="checkbox" class="ace" />--%>
                                                    <%--<span class="lbl"></span>--%>
                                                <%--</label>--%>
                                            <%--</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                            <%--<td class="center">1</td>--%>
                                        <%--</tr>--%>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%@ include file="./../common/footer.jsp"%>
    <script src="<%=ctx%>/static/src/lib/assets/js/jquery-ui-1.12.1.js"></script>
    <script src="<%=ctx%>/static/src/js/info-management/graduation-rates-management.js"></script>
    </body>
</html>
