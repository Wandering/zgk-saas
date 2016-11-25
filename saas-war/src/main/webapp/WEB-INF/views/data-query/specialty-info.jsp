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
                        <a href="/index.html">首页</a>
                    </li>
                    <li>
                        <a href="/school-admission">数据查询</a>
                    </li>
                    <li class="active">专业信息</li>
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
                                <ul id="search-list">
                                </ul>
                            </div>
                        </div>
                        <div class="common-select">
                            <ul class="tab-li">
                                <li type="1">本科专业</li>
                                <li class="active" type="2">专科专业</li>
                            </ul>
                            <dl class="select" id="majored-category"></dl>
                            <script id="majored-category-tpl" type="text/x-handlebars-template">
                                <dd>
                                    {{#each this.childList}}
                                    {{{firstActive this}}}
                                    {{/each}}
                                </dd>
                            </script>
                        </div>
                        <div class="professional-detail">
                            <div id="sub-majored-category" type="2"></div>
                        </div>
                        <script type="text/x-handlebars-template" id="sub-majored-category-tpl">
                            <p class="title" t-id="{{id}}">专业门类：({{name}})</p>
                            {{#each this.childList}}
                            <dl class="tab-detail-li">
                                <dt m-id="{{id}}">{{name}}({{majoredNumber}})</dt>
                                <dd>
                                    {{#each this.childList}}
                                    <span b-id="{{id}}">{{name}}</span>
                                    {{/each}}
                                </dd>
                            </dl>
                            {{/each}}
                        </script>
                        <%--////////////////////////////////////////////--%>
                    </div>
                    <%--
                    专业信息-
                    --%>
                    <div id="specialty-detail" class="dh">
                        <div id="detail-content" class="detail-content">

                        </div>
                        <script type="text/x-handlebars-template" id="detail-content-tpl">
                            <div class="top-info"><span
                                    class="title">{{majorName}}{{#if salaryRank}}</span>居专业就业排行榜第<span
                                    class="red-dashed">{{salaryRank}}</span>位{{/if}}
                            </div>
                            <ul class="middle-info">
                                <li>专业代码：<span>{{#if majorCode}}{{majorCode}}{{else}}-{{/if}}</span></li>
                                <li>授予学位：<span>{{#if degreeOffered}}{{degreeOffered}}{{else}}-{{/if}}</span></li>
                                <li>修学年限：<span>{{#if schoolingDuration}}{{schoolingDuration}}{{else}}-{{/if}}</span>
                                </li>
                            </ul>
                            {{#if fmRatio}}
                            <div class="bottom-info">
                                <div class="sex-proportion">
                                    <img id="male-icon" src="data:image/png;base64,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">
                                    <span class="male-txt">男生</span>
                                    <div class="sex-bar">
                                        <span class="male-bar"></span>
                                        <span class="center-bar"></span>
                                        <span class="female-bar"></span>
                                    </div>
                                    <img id="female-icon" src="data:image/png;base64,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">
                                    <span class="female-txt">女生</span>
                                </div>
                                {{#if salary}}
                                <div class="monthly-salary"><span class="price" id="year-salary">{{salary}}</span><p class="txt">毕业5年月薪</p></div>
                                {{/if}}
                            </div>
                            {{/if}}
                            <div class="specialty-about">
                                <p>开设课程：</p>
                                <span class="common-intro">{{#if offerCourses}}{{offerCourses}}{{else}}{{数据整理中}}{{/if}}</span>
                                <%--<p>相近专业：</p>--%>
                                <%--<span class="common-intro">哲学概论、马克思主义哲学原理、中国哲学史、西方哲学史、科学技术哲学、伦理学、宗教学、美学、逻辑学、心理学、中外哲学原著导读等。主要实践性教学环节：包括社会实习、社会调查、社会公益活动等，一般安排10周左右。</span>--%>
                            </div>
                            <ul class="tab-detail-title">
                                <li class="detail-tab active">专业解读</li>
                                <li class="detail-tab">开设院校</li>
                                <li class="detail-tab">就业前景与方向</li>
                            </ul>
                            <div class="content">
                                <div class="sub-content">
                                    {{#if majorIntroduce}}{{{majorIntroduce}}}{{else}}数据整理中{{/if}}
                                </div>
                                <div class="sub-content dh">
                                    <ul id="open-school">
                                        <%--<li class="school-list">--%>
                                            <%--<img src="http://123.59.12.77:8080//images/schlogo/201510/20151019164917597.png"--%>
                                                 <%--class="school-logo" sid="1">--%>
                                            <%--<div class="top">--%>
                                                <%--<span class="school-name" sid="1">北京大学</span>--%>
                                                <%--<i class="icon-flags"></i>全国排名：<span class="national-rank">1</span>--%>
                                            <%--</div>--%>
                                            <%--<div class="middle">--%>
                                                <%--<div id="property">--%>
                                                    <%--<span class="type-985">985</span> <span class="type-211">211</span>--%>
                                                    <%--<span class="type-yan">研</span> <span class="type-guo">国</span>--%>
                                                    <%--<span class="type-zi">自</span>--%>
                                                <%--</div>--%>
                                            <%--</div>--%>
                                            <%--<div class="bottom">--%>
                                                <%--<span class="province">北京市</span>--%>
                                                <%--<b>隶属：</b><span class="belong">教育部</span>--%>
                                                <%--<b>院校类型：</b><span class="school-type">综合</span>--%>
                                                <%--<a target="_blank" href="http://www.pku.edu.cn/" class="enter-site">--%>
                                                    <%--<span>进入网站</span>--%>
                                                <%--</a>--%>
                                            <%--</div>--%>
                                        <%--</li>--%>
                                    </ul>
                                    <img src="/static/src/img/loading.gif" class="table-loading-img" id="table-loading-img">
                                    <div id="specialty-load-more" class="load-more dh" page-no="1">
                                        加载更多
                                    </div>
                                </div>
                                <div class="sub-content dh">
                                    就业率：<span id="employmentRate"></span>
                                </div>
                            </div>
                        </script>
                        <%--script开设院校--%>
                        <script id="open-school-tpl" type="text/x-handlebars-template">

                        </script>
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
