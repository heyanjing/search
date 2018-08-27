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
        
    </script>
    <style>
      .mini-fit {
            bottom: 58px;
        }
    	.search-datepicker .search-text{text-align:center;}
    	.selectedTreeRow{width:auto!important;}
    </style>
</head>

<body>
<div class="wrap">
    <div class="user-circle" style="width: 100%; height:450px; top:0; left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
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
                                <ol id="iList" style="padding:0 15px 0 15px;height: 300px;">
                                    <div id="contA" style="margin-top: 10px;">
                                        <form id="fileform">
                                            <div id="sid" class="search-texthide"></div>
                                            <table class="useradd_list" style=" text-align: right;">
                                            	<tr>
                                                    <td width="100"><label>名称：</label></td>
                                                    <td>
                                                        <div id="sname" name="sname" class="search-textbox" rules="required" width="300" height="30" enabled = 'false'></div>
                                                    </td>
                                                 </tr>
                                                <tr>
										            <td><label>类型：</label></td>
										            <td>
										                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" defaultValue="0" enabled = 'false'></div>
										            </td>
										        </tr>
										        <tr>
										            <td><label>描述：</label></td>
										            <td>
										                <div id="sdesc" name="sdesc" class="search-textarea" width="300" height="60" enabled = 'false'></div>
										            </td>
										        </tr>
                                            </table>
                                        </form>
                                    </div>

                                    <div class="contentList" id="contB" style="height: ">
                                        <div id="fileAttgrid" class="search-datagrid" idField="sid" showCheckBox="false" multiSelect="true"
									         alternatingRows="true" showPager="false" ondrawcell="datagriddrawcell"
									         style="width:100%;height:250px;">
									        <div property="columns">
									            <div type="indexcolumn" width="50" headAlign="center" textAlign="center">序号</div>
									            <div field="snameattach" width="100" headAlign="center" allowSort="true">名称</div>
									            <div field="action" width="180" headAlign="center" textAlign="center">操作</div>
									        </div>
									    </div>
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
<script type="text/javascript" src="${JS}/filetpls/divView.js?v=${RES_VER}"></script>

</body>
</html>