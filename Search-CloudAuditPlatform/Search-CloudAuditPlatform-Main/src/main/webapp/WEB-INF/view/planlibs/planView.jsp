<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>用户注册</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/sign.css?${V}"/>
    
    <script type="text/javascript">
        var upload1 = "";
        var str1 = [];
        var ationpath = "";//附件上传的地址
        var ationsNams = "";//附件名称

        var sdictionarieid = "";//资质id
        
		var admin = "${admin}";    
		var orgname= "${orgname}";
		var orgid = "${orgid}";
		var orgpid = "${orgpid}";
    </script>
    <style>
        .mini-fit {
            bottom: 58px;
        }
        #tb1 tr th{height:35px;background-color: #E0E0E0;border-bottom: 0px; border:1px #ccc solid;}
        .fixedTb table{ border-collapse:collapse; }
    	.fixedTb td{border:1px solid #ccc; }
    	.bo td{border:0px;cursor: pointer;}
    	.select_province span{border:none;}
    	.search-datepicker .search-text{border:none;}
    	.search-datepicker .search-text{border:none;}
    	.treeselect_title span{border:none;}
    	.mode2-content table input{border:none;}
    	#sorgid .treeselect_title span{border:1px #ccc solid;}
    	#iyear.search-datepicker .search-text{border:1px #ccc solid; text-align:left;}
    	.fa{font-size:20px;}
    	.search-datepicker .search-text{text-align:center;}
    	.selectedTreeRow{width:auto!important;}
    </style>
</head>

<body>
<div class="wrap">
    <div class="user-circle" style="width: 100%; height:559px; top:0; left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
        <div id="alert" style="left: 0;top: 0;margin-left: 0;width:100%;height:100%;display:block;">
            <div class="mode2-content" style="position:absolute;top:0; bottom:0; height:auto; width:100%;">
                <div class="main">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="flow">
                                <div class="flowListBox"></div>
                            </div>
                        </div>
                        <div class="mini-fit">
                            <div class="col-md-12" id="flowDiv">
                                <ol id="iList" style="padding:0 15px 0 15px">
                                    <div id="contA" style="margin-top: 10px;">
                                        <form id="Planform">
                                            <div id="sid" class="search-texthide"></div>
                                            <div id="processinstancesid" class="search-texthide"></div>
                                            <table class="useradd_list" style=" text-align: right;">
                                            	<tr>
                                                    <td width="100"><label>计划标题：</label></td>
                                                    <td>
                                                        <div id="sname" name="sname" class="search-textbox" rules="required" width="280" height="30" enabled = 'false'></div>
                                                    </td>
                                                    <td><label>审计年度：</label></td>
                                                    <td>
                                                        <div id="iyear" name="iyear" class="search-datepicker" rules="required" format="yyyy" width="280"  height="30" enabled = 'false'  placeHolder="请选择 年份" style="float:left;"></div>
                                                    </td>
                                                 </tr>
                                                 <tr>
                                                    <c:if test="${admin == 1 }">
	                                                    <td style="padding-top:10px;"><label>审计机构：</label></td>
	                                                    <td style="padding-top:10px;">
	                                                        <div id="sorgid" name="sorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" enabled="false" dropdownHeight="300" width="280" height="30" placeHolder="请选择"></div>
	                                                    </td>
                                                    </c:if>
                                               	  </tr>
                                                <tr>
                                                	<td width="100"><label>项目列表：</label></td>
                                                	<td colspan="3">
                                                		<div class="fixedTb" style="position:relative;margin-top:10px;" > 
															<!-- <div id="hDiv" style="height:32px;overflow:hidden;position:relative; " >
																	 <table id="tb0" style="position:absolute; height:33px;background-color: #E0E0E0;border-bottom: 0px;" >
																	 </table>
															</div> -->
																	<div id="dDiv">
																	<div id="prosid_1" class="search-texthide"></div>
																	<table id="tb1" >
																		<tr>
																		   <th align="center">项目名称</th>
																		   <th align="center">实施单位</th>
																		   <th align="center">审计组长</th>
																		   <th align="center">开始时间</th>
																		   <th align="center">结束时间</th>
																		   <th align="center">驳回理由</th>
																		 </tr>
																	  <tr id = "t_1" height= "30">
																		   <td align="center" class="bo">
																				<div id="prolib_1" class="search-select" idField="sid" textField="sname" width="100" height="30" placeHolder="请选择" enabled = 'false'></td>
																		   <td align="center" class="bo">
																				<div id="org_1" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="100" height="30" enabled="false" dropdownHeight="200" placeHolder="请选择"></div>
																			</td>
																		   <td align="center" class="bo">
																				<div id="userid_1" class="search-select"  width="100" height="30" idField="sid" textField="sname" placeHolder="请选择" enabled = 'false'></div>
																			</td>
																		   <td align="center">
																		   		<div id="ldstartdate_1" class="search-datepicker" width="100"  height="30" placeHolder="请选择开始时间" enabled = 'false'></div>
																		   </td>
																		   <td align="center">
																				<div id="ldenddate_1" class="search-datepicker" width="100" height="30" placeHolder="请选择线束时间" enabled = 'false'></div>
																			</td> 
																		   <td align="center">
																				<div id="sreason_1" class="search-textbox" width="160" height="30" enabled = 'false'></div>
																				<div id="state_1" class="search-texthide"></div>
																			</td>
																	  </tr>
																	</table>
															</div>
														</div>
                                                	</td>
                                                </tr>
                                                
                                            </table>
                                        </form>
                                    </div>

                                    <div class="contentList" id="contB" style="">
                                        <div id="idCard"></div>
                                    </div>

                                    <div class="contentList" id="contC" style="padding-top:10px;">
                                          <jsp:include page="/WEB-INF/view/processsteps/ProcessSteps.jsp"></jsp:include> 
                                    </div>
                                </ol>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- <div id="mask" class="mask"></div> -->
<script type="text/javascript" src="${JS}/planlibs/palanView.js?v=${RES_VER}"></script>

</body>
</html>