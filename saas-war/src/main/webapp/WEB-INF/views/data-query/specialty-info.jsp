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
                            <div class="top-info"><span class="title">{{majorName}}</span>居专业就业排行榜第<span
                                    class="red-dashed">1</span>位
                            </div>
                            <ul class="middle-info">
                                <li>专业代码：<span>{{majorCode}}</span></li>
                                <li>授予学位：<span>{{degreeOffered}}</span></li>
                                <li>修学年限：<span>{{schoolingDuration}}</span></li>
                            </ul>
                            <div class="bottom-info">
                            </div>
                            <div class="specialty-about">
                                <p>开设课程：</p>
                                <span class="common-intro">哲学概论、马克思主义哲学原理、中国哲学史、西方哲学史、科学技术哲学、伦理学、宗教学、美学、逻辑学、心理学、中外哲学原著导读等。主要实践性教学环节：包括社会实习、社会调查、社会公益活动等，一般安排10周左右。</span>
                                <p>相近专业：</p>
                                <span class="common-intro">哲学概论、马克思主义哲学原理、中国哲学史、西方哲学史、科学技术哲学、伦理学、宗教学、美学、逻辑学、心理学、中外哲学原著导读等。主要实践性教学环节：包括社会实习、社会调查、社会公益活动等，一般安排10周左右。</span>
                            </div>
                            <ul class="tab-detail-title">
                                <li class="detail-tab">专业解读</li>
                                <li class="detail-tab active">开设院校</li>
                                <li class="detail-tab">就业前景与方向</li>
                            </ul>
                            <div class="content">
                                <div class="sub-content dh">
                                    {{专业解读}}
                                    业务培养目标:本专业培养具备比较扎实的马克思主义经济学理论基础,熟悉现代西方经济学理论，比较熟练地掌握现代经济分析方法,知识面较宽飞具有向经济学相关领域扩展渗透的能
                                    力,能在综合经济管理部门、政策研究部门,金融机构和企业从事经济分析、预测、规划和经济管理工作的高级专门人才。

                                    业务培养要求 : 本专业要求学生系统掌握经济学基本理论和相关的基础专业知识 ,了解市场经济的运行机制
                                    ,熟悉党和国家的经济方针、政策和法规,了解中外经济发展的历史和现状；了解经济学的学术动态 ；具有运用数量分析方法和现代技术手段进行社会经济调查、经济分析和实际操作的能力
                                    ; 具有较强的文字和口头表达能力的专门人才 , 能熟练掌握一门外语。

                                    毕业生应获得以下几方面的知识和能力 :

                                    1. 掌握马克思主义经济学、当代西方经济学的基本理论和分析方法 ;

                                    2. 掌握现代经济分析方法和计算机应用技能 ;

                                    3. 了解中外经济学的学术动态及应用前景 ;

                                    4. 了解中国经济体制改革和经济发展 ;

                                    5. 熟悉党和国家的经济方针、政策和法规 ;

                                    6. 掌握中外经济学文献检索、资料查询的基本方法、具有一定的经济研究和实际工作能力

                                    主干学科 : 经济学。

                                    主要课程 :
                                    政治经济学、〈资本论〉、西方经济学、会计学、统计学、计量经济学、国际经济学、货币银行学、财政学、经济学说史、发展经济学、企业管理、市场营销、国际金融、国际贸易等。

                                    主要实践性教学环节 : 包括社会调查、毕业实习 , 一般安排 12 周。

                                    修业年限 : 四年。

                                    授予学位 : 经济学学土。

                                </div>
                                <div class="sub-content">
                                    {{开设院校}}
                                    <ul id="open-school">
                                        <li class="school-list">
                                            <img src="http://123.59.12.77:8080//images/schlogo/201510/20151019164917597.png"
                                                 class="school-logo" sid="1">
                                            <div class="top">
                                                <span class="school-name" sid="1">北京大学</span>
                                                <i class="icon-flags"></i>全国排名：<span class="national-rank">1</span>
                                            </div>
                                            <div class="middle">
                                                <div id="property">
                                                    <span class="type-985">985</span> <span class="type-211">211</span>
                                                    <span class="type-yan">研</span> <span class="type-guo">国</span>
                                                    <span class="type-zi">自</span>
                                                </div>
                                            </div>
                                            <div class="bottom">
                                                <span class="province">北京市</span>
                                                <b>隶属：</b><span class="belong">教育部</span>
                                                <b>院校类型：</b><span class="school-type">综合</span>
                                                <a target="_blank" href="http://www.pku.edu.cn/" class="enter-site">
                                                    <span>进入网站</span>
                                                </a>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                                <div class="sub-content">
                                    {{就业前景于方向}}
                                </div>
                            </div>
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
