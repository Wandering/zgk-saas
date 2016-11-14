<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>院校录取数据</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/data-query/school-admission.css"/>
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
                    <li class="active"><a href="#">院校录取数据</a></li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="common-title">
                            <h3 class="fl">院校录取数据</h3>
                            <div class="search-box fr">
                                <input type="text" placeholder="请输入院校名称" class="search-input">
                                <button class="search-btn">搜索</button>
                            </div>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>院校属地</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd  id="province-list">

                                </dd>
                                <script type="text/x-handlebars-template" id="province-list-tpl">
                                    <span class="active">全部</span>
                                    {{#each this}}
                                    <span code="{{code}}" provinceId="id">{{name}}</span>
                                    {{/each}}
                                </script>
                            </dl>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>招生年份</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd>
                                    <span>2013</span>
                                    <span>2014</span>
                                    <span>2015</span>
                                    <span>2016</span>
                                </dd>
                            </dl>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>录取批次</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd>
                                    <span class="active">全部</span>
                                    <span>本科一批</span>
                                    <span>本科二批</span>
                                    <span>本科三批</span>
                                    <span>高职专科批</span>
                                </dd>
                            </dl>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>院校特征</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd>
                                    <span class="active">全部</span>
                                    <span>985</span>
                                    <span>211</span>
                                    <span>有研究生院</span>
                                    <span>含国防生</span>
                                    <span>卓越计划</span>
                                </dd>
                            </dl>
                        </div>
                        <div class="admission-detail">
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
<script src="<%=ctx%>/static/src/js/data-query/school-admission.js"></script>

</body>
</html>
