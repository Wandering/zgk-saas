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
                            <div id="sub-majored-category"></div>
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
                            <div class="top-info"><span class="title">{{经济学}}</span>居专业就业排行榜第<span class="">1</span>位</div>
                            <div class="middle-info">
                                专业代码：<b>10101</b>
                                授予学位：<b>哲学学士</b>
                                修学年限：<b>四年</b>
                            </div>
                            <div class="bottom-info">
                                图标
                            </div>
                            <div class="specialty-about">
                                <p>开设课程：</p>
                                <span class="open-course">哲学概论、马克思主义哲学原理、中国哲学史、西方哲学史、科学技术哲学、伦理学、宗教学、美学、逻辑学、心理学、中外哲学原著导读等。主要实践性教学环节：包括社会实习、社会调查、社会公益活动等，一般安排10周左右。</span>
                                <p>相近专业：</p>
                                <span class="open-course">哲学概论、马克思主义哲学原理、中国哲学史、西方哲学史、科学技术哲学、伦理学、宗教学、美学、逻辑学、心理学、中外哲学原著导读等。主要实践性教学环节：包括社会实习、社会调查、社会公益活动等，一般安排10周左右。</span>
                            </div>
                        </div>
                        <ul class="sub-title">
                            <li class="detail-tab active">专业解读</li>
                            <li class="detail-tab">开设院校</li>
                            <li class="detail-tab">就业前景于方向</li>
                        </ul>
                        <div class="content">
                            <div class="sub-content">
                                {{专业解读}}
                            </div>
                            <div class="sub-content">
                                {{开设院校}}
                            </div>
                            <div class="sub-content">
                                {{就业前景于方向}}
                            </div>
                        </div>
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
