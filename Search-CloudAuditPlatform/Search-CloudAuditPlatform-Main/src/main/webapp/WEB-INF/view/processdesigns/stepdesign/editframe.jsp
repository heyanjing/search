<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>流程设计-弹框编辑</title>
    <style>
        ul, li {
            list-style: none;
        }
        .wrap {
            width: 100%;
            height: 100%;
            background: #ffffff;
        }
        .construction-tab {
            width: 750px;
            margin: 0 auto 50px;
        }
        .construction-tab-title {
            background: #0584a9;
            overflow: hidden;
            zoom: 1;
        }
        .construction-tab-title li {
            float: left;
            width: 150px;
            height: 30px;
            line-height: 30px;
            color: #fff;
            text-align: center;
            cursor: pointer;
        }
        .construction-tab-title li.active {
            background: #06a8d7;
        }
        .construction-tab-bd li {
            display: none;
            padding: 20px;
            border: 1px solid #ddd;
            border-top: 0 none;
            font-size: 24px;
        }
        .construction-table {
            width: 750px;
            margin: 0 auto;
        }
        .construction-table-title {
            background: #0584a9;
            overflow: hidden;
            zoom: 1;
        }
        .construction-table-title li {
            float: left;
            width: 150px;
            height: 30px;
            line-height: 30px;
            color: #fff;
            text-align: center;
            cursor: pointer;
        }
        .construction-table-title li.table-active {
            background: #06a8d7;
        }
        .construction-table-bd li {
            display: none;
            padding: 20px;
            border: 1px solid #ddd;
            border-top: 0 none;
            font-size: 24px;
        }
        .construction-table-bd li.table-thisclass {
            display: list-item;
        }
        
    </style>
</head>
<body>
	<div class="construction-table">
        <ul id="table-construction" class="construction-table-title">
            <li class="table-active">步骤属性</li>
            <li>步骤权限</li>
        </ul>
        <ul id="table-construction-bd" class="construction-table-bd">
            <li class="table-thisclass">
                <table>
					<tr>
						<td><label>步骤名称：</label></td>
						<td style="text-align: left;">
							<div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
						</td>
					</tr>
					<tr>
						<td><label>是否回退：</label></td>
						<td>
							<div id ="isupportback" class ="search-textradio" width ="300" itemWidth ="80" idField ="value" textField ="text" multiSelect ="true" verticalRank ="false" groupName ="2" defaultValue="2"></div>
						</td>
					</tr>
					<tr>
						<td><label>是否显示意见区：</label></td>
						<td>
							<div id ="isupportopinion" class ="search-textradio" width ="300" itemWidth ="80" idField ="value" textField ="text" multiSelect ="true" verticalRank ="false" groupName ="3" defaultValue="2"></div>
						</td>
					</tr>
				</table>
            </li>
            <li>
            	<table>
            		<tr>
            			<td style="text-align: left;">
            				<fieldset>
            					<legend>对应用户</legend>
	            				<table>
	            					<tr>
	            						<td style="width: 160px;">
            								<div id="uname" class="search-textbox" width="150" height="25" placeholder="搜索用户"></div>
	            						</td>
	            						<td>
            								<button type="button" value="搜索" onclick="toSearch(1)">搜索</button>
	            						</td>
	            					</tr>
	            					<tr>
	            						<td colspan="2">
								            <div id="treedatagrid_u" class="search-treedatagrid" idField="id" parentField="pid" textField="sname" 
												showCheckBox="true" multiSelect="true" selectedRows="false" alternatingRows="true" expandLevel="0" 
												ondrawcell="treedatagriddrawcell_u" style="width:100%;height:160px;">
												<div property="columns">
													<div field="sname" width="200px" headAlign="left">用户名</div>
												</div>
											</div>
	            						</td>
	            					</tr>
	            				</table>
							</fieldset>
            			</td>
            		</tr>
            		<tr>
            			<td style="text-align: left;">
            				<fieldset>
            					<legend>对应功能组</legend>
            					<table>
	            					<tr>
	            						<td style="width: 160px;">
            								<div id="fname" class="search-textbox" width="150" height="25" placeholder="搜索功能组"></div>
	            						</td>
	            						<td>
            								<button type="button" value="搜索" onclick="toSearch(2)">搜索</button>
	            						</td>
	            					</tr>
	            					<tr>
	            						<td colspan="2">
											<div id="treedatagrid_f" class="search-treedatagrid" idField="id" parentField="pid" textField="sname" 
												showCheckBox="true" multiSelect="true" selectedRows="false" alternatingRows="true" expandLevel="0" 
												ondrawcell="treedatagriddrawcell_f" style="width:100%;height:160px;">
												<div property="columns">
													<div field="sname" width="200px" headAlign="left">功能组</div>
													<div field="orgname" width="150px" headAlign="left">来源机构</div>
												</div>
											</div>
	            						</td>
	            					</tr>
	            				</table>
							</fieldset>
            			</td>
            		</tr>
            	</table>
            </li>
        </ul>
    </div>
<button type="button" value="保存" onclick="closeClick();">保存</button>
<script type="text/javascript" src="${JS}/processdesigns/stepdesign/editframe.js?${V}"></script>
</body>
</html>