<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>院校招生计划</title>
    <%@ include file="./../common/meta.jsp" %>
    <link rel="stylesheet" href="<%=ctx%>/static/src/css/data-query/school-recruit.css"/>
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
                            <a href="/index.html">首页</a>
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
                                    <span class="active">全部</span>
                                    {{#each this}}
                                    <span>{{this}}</span>
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
                                    <th>最低分</th>
                                    <th>最高分</th>
                                    <th>平均分</th>
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
                                            {{#if lowestScore}}{{lowestScore}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if highestScore}}{{highestScore}}{{else}}-{{/if}}
                                        </td>
                                        <td>
                                            {{#if averageScore}}{{averageScore}}{{else}}-{{/if}}
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
                    <div class="col-xs-12 dh">
                        <!-- PAGE CONTENT BEGINS -->
                        <%--院校基础信息--%>
                        <div class="school-base-info">
                            <div class="top">
                                <span class="school">北京大学</span>
                                <i class="icon-flags"></i>全国排名：<span class="national-rank">1</span>
                                <a class="collect" href="javascript:void(0)" type="1" sid="1">
                                    <i class="icon-collect-yes"></i>已收藏
                                </a>
                            </div>
                            <div class="middle">
                                <div id="property">
                                    <span class="type-985">985</span> <span class="type-211">211</span> <span
                                        class="type-yan">研</span> <span class="type-guo">国</span> <span class="type-zi">自</span>
                                </div>
                            </div>
                            <div class="bottom">
                                <div class="bottom-common-li"><b>所在省份：</b><span class="province">北京市</span></div>
                                <div class="bottom-common-li"><b>院校隶属：</b><span class="belong">教育部</span></div>
                                <div class="bottom-common-li"><b>院校类型：</b><span class="school-type">综合</span></div>
                                <div class="bottom-common-li"><b>学历层次：</b><span class="education-level">本科</span></div>
                                <div class="bottom-common-li"><b>联系电话：</b><span class="education-level">029-12232435</span></div>
                                <div class="bottom-common-li"><b>院校地址：</b><span class="education-level">北京市海淀区颐和园</span></div>
                                <div class="bottom-common-li"><b>院校网址：</b><span class="education-level">http://www.pku.edu.cn</span></div>
                            </div>
                        </div>


                        <%--table招生计划--%>
                        <div class="student-enroll-table">
                            <div class="sub-title">
                                <div class="title">招生计划</div>
                                <div class="select-condition">
                                    <select class="professional-plan-year">
                                        <option value="2016">2016年</option>
                                        <option value="2015">2015年</option>
                                    </select>
                                    <select class="professional-plan-subject">
                                        <option value="1">文史</option>
                                        <option value="2">理工</option>
                                    </select>
                                </div>
                            </div>
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
                                <tr>
                                    <td>2016年</td>
                                    <td style="width: 250px">文科试验班类（元培学院）</td>
                                    <td>非定向</td>
                                    <td>文史</td>
                                    <td>1</td>
                                    <td>4</td>
                                    <td>5300</td>
                                </tr>
                                <tr>
                                    <td>2016年</td>
                                    <td style="width: 250px">中国语言文学类</td>
                                    <td>非定向</td>
                                    <td>文史</td>
                                    <td>1</td>
                                    <td>4</td>
                                    <td>5000</td>
                                </tr>
                                </tbody>
                            </table>
                            <div class="load-more">加载更多</div>
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
<script src="<%=ctx%>/static/src/js/data-query/school-recruit.js"></script>

</body>
</html>
