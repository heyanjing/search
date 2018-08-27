<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>流程设计-弹框编辑</title>
</head>
<body>
	<!-- 
	<div>
		<table>
			<tr>
				<td><label>对应字段:</label></td>
				<td style="text-align: left;">
					<div id="sfieldname" class="search-select" idField="tableName" textField="comments" width="300" height="30" multiSelect="true"></div>
				</td>
			</tr>
		</table>
    </div>
	 -->
	 <div id="datagrid" class="search-datagrid" idField="tableName"  alternatingRows="true"
         showPager="false" ondrawcell="datagriddrawcell" style="width:100%;height:90%;">
        <div property="columns">
            <div type="indexcolumn" width="20" headAlign="center" textAlign="center">序号</div>
            <div field="comments" width="120" headAlign="center" textAlign="center">字段名</div>
            <div field="action" width="120" headAlign="center" textAlign="center" allowSort="true">操作</div>
        </div>
    </div>
<button type="button" value="保存" onclick="closeClick();">保存</button>
<script type="text/javascript" src="${JS}/processdesigns/stepdesign/editField.js?${V}"></script>
</body>
</html>