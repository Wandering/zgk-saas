<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>专业信息</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/data-query/specialty-info.css"/>
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
                        首页
                    </li>
                    <li>
                        数据查询
                    </li>
                    <li class="active"><a href="#">专业信息</a></li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="common-title">
                            <h3 class="fl">专业信息</h3>
                            <div class="search-box fr">
                                <input type="text" placeholder="请输入专业名称" class="search-input">
                                <button class="search-btn">搜索</button>
                            </div>
                        </div>
                        <div class="common-select">
                            <ul class="tab-li">
                                <li class="active">本科专业</li>
                                <li>专科专业</li>
                            </ul>
                            <dl class="select">
                                <dd>
                                    <span class="active">天津市</span>
                                    <span>河北省</span>
                                    <span>山西省</span>
                                    <span>内蒙古自治区</span>
                                    <span>辽宁省</span>
                                    <span>吉林省</span>
                                    <span>黑龙江省</span>
                                    <span>上海市</span>
                                    <span>江苏省</span>
                                    <span>浙江省</span>
                                    <span>安徽省</span>
                                </dd>
                            </dl>
                        </div>
                        <div class="professional-detail">
                            <p class="title">专业门类：(经济学)</p>

                            <dl class="tab-detail-li">
                                <dt>经济学(6)</dt>
                                <dd>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                </dd>
                            </dl>
                            <dl class="tab-detail-li">
                                <dt>经济学(6)</dt>
                                <dd>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                </dd>
                            </dl>
                            <dl class="tab-detail-li">
                                <dt>经济学(6)</dt>
                                <dd>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                    <span>经济学1223</span>
                                </dd>
                            </dl>


                        </div>

                        <%--////////////////////////////////////////////--%>
                    </div>
                    <!-- PAGE CONTENT ENDS -->
                </div><!-- /.col -->
            </div><!-- /.row -->
        </div><!-- /.page-content -->
    </div><!-- /.main-content -->
</div><!-- /.main-container-inner -->
</div><!-- /.main-container -->
<%@ include file="./../common/footer.jsp" %>
<script src="<%=ctx%>/static/src/js/data-query/specialty-info.js"></script>

</body>
</html>
