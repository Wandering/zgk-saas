<%--
  Created by IntelliJ IDEA.
  User: liusven
  Date: 2016/11/1
  Time: 下午2:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>上传文件</title>
</head>
<body>
<form name="serForm" action="/scoreAnalyse/uploadData" method="post"  enctype="multipart/form-data">
    <input type="file" name="file">
    <input type="submit" value="upload"/>
</form>
</body>
</html>
