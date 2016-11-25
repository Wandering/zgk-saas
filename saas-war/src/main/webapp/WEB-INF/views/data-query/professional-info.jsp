<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>职业信息</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/data-query/professional-info.css"/>
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
                    <li class="active">职业信息</li>
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="common-title">
                            <h3 class="fl">职业信息</h3>
                            <div class="search-box fr">
                                <input type="text" placeholder="请输入查询内容..." class="search-input">
                                <button class="search-btn">搜索</button>
                            </div>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>职业分类</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd id="profession-category">
                                </dd>
                                <script id="profession-category-tpl" type="text/x-handlebars-template">
                                    <span class="active" pid="">全部</span>
                                    {{#each this}}
                                    <span pid="{{id}}">{{Type}}</span>
                                    {{/each}}
                                </script>
                            </dl>
                        </div>
                        <div class="professional-detail">
                            <h3 class="sub-title">全部</h3>
                            <ul class="tab-detail-title" id="detail-title">
                            </ul>
                            <script id="detail-title-tpl" type="text/x-handlebars-template">
                                {{#each this}}
                                {{{firstActive this}}}
                                {{/each}}
                            </script>
                            <div class="tab-detail-content" id="tab-detail-content"></div>
                            <script type="text/x-handlebars-template" id="tab-detail-content-tpl">
                                {{#each this.rows}}
                                <dl>
                                    <dt>
                                        <span class="sub-name"><a href="javascript:void(0)" pid="{{id}}">{{#if
                                            professionName}}{{professionName}}{{else}}数据整理中{{/if}}</a></span>
                                    </dt>
                                    <dd>
                                        <p>{{#if professionShort}}{{professionShort}}{{else}}数据整理中{{/if}}</p>
                                    </dd>
                                </dl>
                                {{/each}}
                            </script>
                            <img src="/static/src/img/loading.gif" class="table-loading-img"
                                 id="professional-loading-img">
                            <div id="professional-load-more" class="load-more dh" page-no="1">
                                加载更多
                            </div>
                        </div>
                        <%--////////////////////////////////////////////--%>
                    </div>
                    <%--
                    职业信息详情
                    --%>
                    <div class="dh" id="professional-detail">
                        <div class="professional-detail-content" id="detail-content">

                        </div>
                        <script id="detail-content-tpl" type="text/x-handlebars-template">
                            <dl class="select">
                                <h4>{{this.gkProfessional.professionName}}</h4>
                                <dt>
                                    <span>相关专业</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd><span>{{this.gkProfessionDetail.relateMajor}}</span></dd>
                            </dl>
                            <h5>职业介绍</h5>
                            <p>{{this.gkProfessionDetail.introduction}}</p>
                            <h5>职业前景</h5>
                            <p>{{this.gkProfessionDetail.careerProspects}}</p>
                            <%--<h5>工作内容</h5>--%>
                            <%--<p>{{this.gkProfessionDetail.workContent}}</p>--%>
                            <h5>从业要求</h5>
                            <p>{{this.gkProfessionDetail.vocationalDemand}}</p>
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
<script src="<%=ctx%>/static/src/js/data-query/professional-info.js"></script>

</body>
</html>
