<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>在线编辑文件</title>
    <meta name='description' content='在线编辑文件'>
</head>
<body>
	<iframe id="onlineFile" src="" style="width: 100%; height: 100%;"></iframe>
	<!-- 内容结束 -->
	<script type="text/javascript">
		function setData(data) {
			data.isedit = (data.isedit != undefined ? data.isedit : true);
			if (data.url) {
				document.getElementById("onlineFile").src = data.url;
			} else if (data.path) {
				$.post(CTX + '/applys/findOnlineEditFileUrl', {relativePath : data.path, isedit : data.isedit, iscopy : data.iscopy, pathpre : data.pathpre}, function(result) {
					if (result.status) {
						document.getElementById("onlineFile").src = result.result.operurl;
					}
				}) 
			}
		}
	</script>
</body>
</html>