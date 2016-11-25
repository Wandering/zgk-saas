<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!-- basic scripts -->

		<!--[if !IE]> -->

		<script src="<%=ctx%>/static/src/lib/assets/js/jquery-2.0.3.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
<script src="<%=ctx%>/static/src/lib/assets/js/jquery-1.10.2.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='<%=ctx%>/static/src/lib/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='<%=ctx%>/static/src/lib/assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='<%=ctx%>/static/src/lib/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>

        <%--layer弹层--%>
        <script src="<%=ctx%>/static/src/lib/layer/layer.js"></script>
		<script src="<%=ctx%>/static/src/lib/assets/js/bootstrap.min.js"></script>
		<script src="<%=ctx%>/static/src/lib/assets/js/ace.min.js"></script>
		<script src="<%=ctx%>/static/src/js/common.js"></script>
		<script src="<%=ctx%>/static/src/lib/handlebars/handlebars-v4.0.5.js"></script>
        <script src="<%=ctx%>/static/src/lib/echarts/echarts.js"></script>
    	<script src="<%=ctx%>/static/src/lib/jquery.page/jquery.page.js"></script>
    	<script src="<%=ctx%>/static/src/lib/md5/jQuery.md5.js"></script>
