<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增功能</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>
        .around_list {
            text-align: right !important;
        }

        .search-treeselect {
            float: left;
        }
		
		table tr td.hg{
			padding-top:10px;
		}
        .search-select {
            float: left;
        }

        .search-textradio {
            float: left;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 3px;
        }

        table tr td span i {
            color: green;
            font-size: 14px !important;
        }
    </style>
    <script type="text/javascript">
        var __usertype = ${usertype};
    </script>
</head>

<body>
<div id="alert" class="" style="left: 0;top: 0;margin-left: 0;width:100%; height:100%;">
    <div class="model-content">
        <div class="main">
            <div class="row">
                <div class="col-md-12">
                    <div class="flow">
                        <div class="flowListBox"></div>
                    </div>
                </div>
                <div class="mini-fitbox">
                    <div class="col-md-12" id="flowDiv" style="height:100%;">
                        <ol id="iList" style="padding:0 15px 0 15px;height:100%;">
                            <div id="cont1">
                                <form id="form">
                                    <table class="around_list">
                                        <tr>
                                            <td>
                                                <div id="sid" class="search-texthide"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td class="hg"><label>名　　称：</label></td>
                                            <td class="hg">
                                                <div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td class="hg"><label style="margin-left:10px;">父　　级：</label></td>
                                            <td class="hg">
                                                <div id="sparentid" style="float: left;" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择父级机构" onvaluechanged="changsparentid"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>

                                        <tr>

                                            <td class="hg"><label>机构类型：</label></td>
                                            <td class="hg">
                                                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" placeHolder="请选择机构类型" onvaluechanged="itypechange"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>

                                            <td id="area1" class="hg"><label>所属区县：</label></td>
                                            <td id="area2" class="hg">
                                                <div id="area" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择所属区县"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td class="hg"><label>描　　述：</label></td>
                                            <td colspan="3" class="hg">
                                                <div id="sdes" class="search-textarea" width="680" style="float:left;"></div>
                                            </td>
                                        </tr>

                                        <!-- <tr>
                                            <td colspan="4" style="text-align:center; padding-top:5px;">
                                                <input type="button" onclick="saveData()" value="保存"/>
                                                <input type="button" onclick ="windowClose()" value="关闭"/>
                                            </td>
                                        </tr> -->
                                    </table>
                                </form>
                            </div>
                            <div class="contentList" id="cont2" style="height:auto;padding-top:10px;">
                                <form id="form1">
                                    <table class="around_list">
                                        <tr>
                                            <td id="user1"><label>联系人：</label></td>
                                            <td id="user2">
                                                <div id="username" class="search-textbox" width="300" height="30"></div>
                                            </td>
                                            <td id="phone1"><label>手机号码：</label></td>
                                            <td id="phone2">
                                                <div id="userphone" class="search-textbox" rules="phonenumber" width="300" height="30" onvalidate="phoneValidate"></div>
                                            </td>
                                        </tr>
                                    </table>
                                </form>
                            </div>
                            <div class="contentList" id="cont3" style="padding-top:10px; height:100%;">
                                <div class="fixedTb" style="position:relative; height:100%;">
                                    <div class="flow-item contentList" data-title="授权功能" style="display:block; height:100%;">
                                        <div id="datagrid" class="search-treedatagrid" idField="id" textField="sname" ondrawcell="drawcell"
                                             style="width: 100%; height: 100%;">
                                            <div property="columns">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </ol>
                    </div>
                </div>
            </div>
            <div class="foot-btn">
                <button type="button" class="btn btn-primary" id="btnBack">上一步</button>
                <button type="button" class="btn btn-default" style="display:none" id="btnok" onclick="saveData()">保存</button>
                <button type="button" class="btn btn-success" id="btnNext">下一步</button>
            </div>
        </div>
    </div>
</div>
<div id="mask"></div>

<script type="text/javascript" src="${JS}/orgs/AddSpecialOrg.js?${V}"></script>
</body>
</html>