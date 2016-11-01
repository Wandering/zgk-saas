<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>SAAS 基础信息设置</title>
    <%@ include file="./../common/meta.jsp"%>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/seting-process.css"/>
</head>
<body>
<%@ include file="./../common/header.jsp"%>
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

        <div class="main-content flow-main-content">
            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="flow-box">
                            <div class="line"></div>
                            <ul class="flow-ul">
                                <li class="list active"><span>年级设置</span></li>
                                <li class="list active"><span>教室设置</span></li>
                                <li class="list active"><span>班级设置</span></li>
                                <li class="list active"><span>升学率设置</span></li>
                                <li class="list active"><span>教师设置</span></li>
                            </ul>
                        </div>
                        <p class="flow-tips">温馨提示：用户可以在左侧菜单，基础信息中更新设置内容</p>

                        <div class="main-title">
                            <h3>教师设置</h3>
                        </div>


                        <div class="title-2">
                            <span class="txt-t">教师字段设置</span>
                            <div class="btns">
                                <button class="btn btn-pink" id="add-btn">添加</button>
                                <button class="btn btn-success" id="del-batch-btn">批量删除</button>
                            </div>
                        </div>
                        <div class="">
                            <table id="" class="table">
                                <thead>
                                <tr>
                                    <th class="center">
                                        <label>
                                            <input type="checkbox" class="ace" id="checkAll" />
                                            <span class="lbl"></span>
                                        </label>
                                    </th>
                                    <th class="center">排序</th>
                                    <th class="center">字段名称</th>
                                    <th class="center">操作</th>
                                </tr>
                                </thead>
                                <tbody id="teacher-template">
                                <%--<tr>--%>
                                <%--<td class="center">--%>
                                <%--<label>--%>
                                <%--<input type="checkbox" class="ace" />--%>
                                <%--<span class="lbl">111</span>--%>
                                <%--</label>--%>
                                <%--</td>--%>
                                <%--<td class="center">1</td>--%>
                                <%--<td class="center">教师姓名</td>--%>
                                <%--<td class="center"><span class="disabled">删除</span></td>--%>
                                <%--</tr>--%>
                                </tbody>
                            </table>
                        </div>
                        <div class="fun-box">
                            <span class="txt1">导入教师数据</span>
                            <!--dom结构部分-->
                            <span id="uploader-demo">
                                <!--用来存放item-->
                                <span id="fileList" class="uploader-list"></span>
                                <%--<div id="filePicker">选择文件</div>--%>
                                <button class="btn btn-info btn-import" id="btn-import">导入教师数据Excel</button>
                            </span>


                            <a href="javascript:;" class="links" id="export-excel-btn">请先导出Excel模板，进行填写</a>
                        </div>
                        <div class="btn-box">
                            <a class="btn btn-info btn-save" href="javascript:;" id="seting-process5-btn">开始SAAS</a>
                        </div>



                    </div><!-- /.col -->
                </div><!-- /.row -->
            </div><!-- /.page-content -->
        </div><!-- /.main-content -->
    </div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp"%>
<link rel="stylesheet" type="text/css" href="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.css">
<script type="text/javascript" src="<%=ctx%>/static/src/lib/webuploader-0.1.5 2/webuploader.js"></script>
<script>
    var BASE_URL = '<%=ctx%>/static/src/lib/';
    var rootPath = '<%=ctx%>';
</script>
<script src="<%=ctx%>/static/src/lib/assets/js/jquery-ui-1.12.1.js"></script>
<script src="<%=ctx%>/static/src/js/basis-settings/seting-process5.js"></script>
</body>
</html>
