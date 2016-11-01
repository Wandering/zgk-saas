<%
    String path = request.getContextPath();
    String ctx = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- basic styles -->
<link href="<%=ctx%>/static/src/lib/assets/css/bootstrap.min.css" rel="stylesheet" />
<link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/font-awesome.min.css" />

<!--[if IE 7]>
  <link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/font-awesome-ie7.min.css" />
<![endif]-->

<!-- page specific plugin styles -->

<!-- fonts -->

<%--<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" />--%>

<!-- ace styles -->

<link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/ace.min.css" />

<!--[if lte IE 8]>
  <link rel="stylesheet" href="<%=ctx%>/static/src/lib/assets/css/ace-ie.min.css" />
<![endif]-->

<!-- inline styles related to this page -->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

<!--[if lt IE 9]>
<script src="<%=ctx%>/static/src/lib/assets/js/html5shiv.js"></script>
<script src="<%=ctx%>/static/src/lib/assets/js/respond.min.js"></script>
<![endif]-->
