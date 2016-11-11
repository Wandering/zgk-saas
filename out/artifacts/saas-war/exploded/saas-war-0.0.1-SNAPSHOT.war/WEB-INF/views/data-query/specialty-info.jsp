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
                            <h3 class="sub-title">互联网/IT</h3>
                            <ul class="tab-detail-title">
                                <li class="active">计算机软件</li>
                                <li>计算机软件111323</li>
                                <li>计算机软件32</li>
                            </ul>
                            <ul class="tab-detail-content">
                                <li>
                                    <dl>
                                        <dt>
                                            <span class="sub-name"><a href="/data-occupational-detail.html?id=40">硬件工程师</a></span>
                                        </dt>
                                        <dd>
                                            <p>硬件工程师是指从事维护硬件运行，修理硬件故障的专业技术人员。</p>
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>
                                            <span class="sub-name"><a href="/data-occupational-detail.html?id=40">1232323</a></span>
                                        </dt>
                                        <dd>
                                            <p>硬件工程师是指从事维护硬件运行，修理硬件故障的专业技术人员。</p>
                                        </dd>
                                    </dl>
                                </li>
                                <li class="dh">内容2</li>
                                <li class="dh">内容3</li>
                            </ul>
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
