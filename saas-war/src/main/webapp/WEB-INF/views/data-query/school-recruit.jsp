<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>SAAS 院校招生计划</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/data-query/school-recruit.css?v=20170309"/>
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
                    <ul class="breadcrumb">
                        <li>
                            首页
                        </li>
                        <li>
                            <a href="/school-admission">数据查询</a>
                        </li>
                        <li class="active">院校招生计划</li>
                    </ul><!-- .breadcrumb -->
                </ul><!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->
                        <div class="common-title">
                            <h3 class="fl">院校招生数据</h3>
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
                                <dd id="province-list">
                                </dd>
                                <script type="text/x-handlebars-template" id="province-list-tpl">
                                    <span class="active" provinceId="">全部</span>
                                    {{#each this}}
                                    <span code="{{code}}" provinceId="{{id}}">{{name}}</span>
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
                                <dd id="year-list">
                                </dd>
                                <%--<script type="text/x-handlebars-template" id="year-list-tpl">--%>
                                <%--{{#each this}}{{{firstActive this}}}{{/each}}--%>
                                <%--</script>--%>
                                <script type="text/x-handlebars-template" id="year-list-tpl">
                                    <span class="active" yearId="">全部</span>
                                    {{#each this}}
                                    <span yearId="{{this}}">{{this}}</span>
                                    {{/each}}
                                </script>
                            </dl>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>录取批次</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd id="batch-list">
                                </dd>
                                <script type="text/x-handlebars-template" id="batch-list-tpl">
                                    <span class="active" dictid="">全部</span>
                                    {{#each this}}
                                    <span dictid="{{dictid}}">{{name}}</span>
                                    {{/each}}
                                </script>
                            </dl>
                        </div>
                        <div class="common-select">
                            <dl class="select">
                                <dt>
                                    <span>院校特征</span>
                                    <span>&gt;&nbsp;&nbsp;</span>
                                </dt>
                                <dd id="feature-list"></dd>
                                <script type="text/x-handlebars-template" id="feature-list-tpl">
                                    <span class="active" dictid="">全部</span>
                                    {{#each this}}
                                    <span dictid="{{dictId}}">{{name}}</span>
                                    {{/each}}
                                </script>
                            </dl>
                        </div>
                        <div class="admission-detail">
                            <ul class="tab-detail-title">
                                <li class="active" type="2">理工招生数据</li>
                                <li type="1">文史招生数据</li>
                            </ul>
                            <table class="table-list table-hover table-bordered">
                                <thead>
                                <tr>
                                    <th>院校名称</th>
                                    <th>科类</th>
                                    <th>年份</th>
                                    <th>录取批次</th>
                                    <th>隶属</th>
                                    <th>总计划数</th>
                                    <th></th>
                                </tr>
                                </thead>
                                <tbody id="school-admission-plan">
                                </tbody>
                                <script id="school-admission-plan-tpl" type="text/x-handlebars-template">
                                    {{#each this.rows}}
                                    <tr>
                                        <td>
                                            {{#if name}}{{name}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if typename}}{{typename}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if year}}{{year}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if batchname}}{{batchname}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if subjection}}{{subjection}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if enrollingNumber}}{{enrollingNumber}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            <a href="javascript:void(0)" class="active" type="{{id}}">各专业计划详情</a>
                                        </td>
                                    </tr>
                                    {{/each}}
                                </script>
                            </table>
                        </div>
                        <img src="/static/src/img/loading.gif" class="table-loading-img" id="table-loading-img">
                        <div id="recruit-load-more" class="load-more dh" page-no="1">
                            加载更多
                        </div>
                        <%--////////////////////////////////////////////--%>
                    </div>


                    <%--
                    院校详情信息
                    --%>
                    <div id="school-detail-info" class="dh">
                        <div id="school-detail-top"></div>
                            <div class="student-enroll-table">
                                <div class="sub-title">
                                    <div class="title">招生计划</div>
                                    <div class="select-condition">
                                        <select class="professional-plan-year" id="plan-year">
                                            <%--<option value="2016">2016年</option>--%>
                                            <%--<option value="2015">2015年</option>--%>
                                        </select>
                                        <select class="professional-plan-subject" id="plan-subject">
                                            <%--<option value="1">文史</option>--%>
                                            <%--<option value="2">理工</option>--%>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div id="school-detail-bottom"></div>
                        </div>
                        <script id="school-detail-top-tpl" type="text/x-handlebars-template">
                            <div class="school-base-info">
                                <div class="top">
                                    <span class="school">{{name}}</span>
                                    {{#if rank}}
                                    <i class="icon-flags"></i>全国排名：<span class="national-rank">{{rank}}</span>
                                    {{/if}}
                                    <%--<a class="collect" href="javascript:void(0)" type="1" sid="1">--%>
                                    <%--<i class="icon-collect-yes"></i>已收藏--%>
                                    <%--</a>--%>
                                </div>
                                <div class="middle">
                                    <div id="property">
                                        <span class="type-985">985</span> <span class="type-211">211</span> <span
                                            class="type-yan">研</span> <span class="type-guo">国</span> <span
                                            class="type-zi">自</span>
                                    </div>
                                </div>
                                <div class="bottom">
                                    <div class="bottom-common-li"><b>所在省份：</b><span class="province">{{#if province}}{{province}}{{else}}-{{/if}}</span></div>
                                    <div class="bottom-common-li"><b>院校隶属：</b><span class="belong">{{#if subjection}}{{subjection}}{{else}}-{{/if}}</span></div>
                                    <div class="bottom-common-li"><b>院校类型：</b><span class="school-type">{{#if typeName}}{{typeName}}{{else}}-{{/if}}</span></div>
                                    <div class="bottom-common-li"><b>学历层次：</b><span class="education-level">{{#if levelName}}{{levelName}}{{else}}-{{/if}}</span>
                                    </div>
                                    <div class="bottom-common-li"><b>联系电话：</b><span
                                            class="education-level">{{#if contactPhone}}{{contactPhone}}{{else}}-{{/if}}</span></div>
                                    <div class="bottom-common-li" style="min-width:500px"><b>院校地址：</b><span
                                            class="education-level">{{#if address}}{{address}}{{else}}-{{/if}}</span></div>
                                    <div class="bottom-common-li"><b>院校网址：</b><span class="education-level">{{#if url}}{{url}}{{else}}-{{/if}}</span>
                                    </div>
                                </div>
                            </div>
                        </script>

                        <%--table招生计划--%>
                        <script id="school-detail-bottom-tpl" type="text/x-handlebars-template">
                                <table class="table-list table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th>年份</th>
                                        <th>专业名称</th>
                                        <th>招生性质</th>
                                        <th>科类</th>
                                        <th>计划人数</th>
                                        <th>学制</th>
                                        <th>学费</th>
                                    </tr>
                                    </thead>
                                    <tbody id="professional-plan-select">
                                    {{#each this}}
                                    <tr>
                                        <td>{{#if year}}{{year}}{{else}}-{{/if}}</td>
                                        <td style="width: 250px">{{#if majoredName}}{{majoredName}}{{else}}-{{/if}}</td>
                                        <td>{{#if enrollType}}{{enrollType}}{{else}}-{{/if}}</td>
                                        <td>{{#if majorType}}{{majorType}}{{else}}-{{/if}}</td>
                                        <td>{{#if planEnrolling}}{{planEnrolling}}{{else}}-{{/if}}</td>
                                        <td>{{#if lengthOfSchooling}}{{lengthOfSchooling}}{{else}}-{{/if}}</td>
                                        <td>{{#if schoolFee}}{{schoolFee}}{{else}}-{{/if}}</td>
                                    </tr>
                                    {{/each}}
                                    </tbody>
                                </table>
                                <img src="/static/src/img/loading.gif" class="table-loading-img dh" id="school-detail-loading-img">
                                <div id="school-detail-load-more" class="load-more dh">
                                    加载更多
                                </div>
                        </script>


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
<script src="<%=ctx%>/static/src/js/data-query/school-recruit.js?v=20170309"></script>

</body>
</html>
