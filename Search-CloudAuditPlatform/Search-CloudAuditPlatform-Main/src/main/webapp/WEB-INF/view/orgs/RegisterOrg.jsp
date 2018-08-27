<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>机构注册</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/login/sign.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">

    <style>
        table tr td {
            padding: 10px 0 0 0;
        }

        .mini-fit {
            bottom: 58px;
        }

        .btn-primary {
            float: left;
        }

        table tr td {
            padding-top: 10px;
        }

        table tr td span a {
            display: inline-block;
            margin-top: 10px;
            padding-left: 1px;
        }

        table tr td span i {
            color: green;
            font-size: 14px !important;
        }
    </style>
</head>

<body>
<div class="wrap">
    <div class="user-circle" style="width: 1000px; height:600px; top:10%; left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
        <div id="alert" class="" style="left: 0;top: 0;margin-left: 0;width:100%;height:100%;display:block; ">
            <div class="mode2-head">
                <span class="close"><i class="glyphicon glyphicon-remove"></i></span>
                <h4 class="moda2-title"><i class="fa fa-user-circle"></i>机构注册</h4>
            </div>
            <div class="mode2-content" style="position:absolute;top:50px; bottom:0; height:auto; width:100%;">
                <div class="main">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="flow">
                                <div class="flowListBox"></div>
                            </div>
                        </div>
                        <div class="mini-fit">
                            <div class="col-md-12" id="flowDiv">
                                <ol id="iList" style="padding:15px 15px 0 15px;">
                                    <div id="contA" style="">
                                        <form id="form">
                                            <table class="around_list" style="padding-left:100px;">
                                                <tr>
                                                    <td>
                                                        <div id="sid" class="search-texthide"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>名　　称：</label></td>
                                                    <td>
                                                        <div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
                                                    </td>
                                                    <td><label>父　　级：</label></td>
                                                    <td>
                                                        <div id="sparentid" style="float: left;" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择父级机构" onvaluechanged="changsparentid" onkeyup="querysparentid" onblur="parentid"></div>
                                                        <!-- <input class="org-search" style="float: left;width:50" type="button" onclick="getOrgs();" value="查询"/> -->
                                                        <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                                    </td>
                                                </tr>

                                                <tr>

                                                    <td><label>机构类型：</label></td>
                                                    <td>
                                                        <div id="itype" style="float: left;" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" placeHolder="请选择机构类型" multiSelect="true"></div>
                                                        <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                                    </td>
                                                    <td id="iisdepartment1"><label>是否部门：</label></td>
                                                    <td id="iisdepartment2">
                                                        <div id="iisdepartment" class="search-textradio" idField="value" textField="text" width="300" defaultValue="2" groupName="iisdepartment" style="line-height:30px;float:left;" onvaluechanged="changiisdepartment"></div>
                                                        <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                                    </td>
                                                    <td id="org1"><label>审计机构：</label></td>
                                                    <td id="org2">
                                                        <div id="sauditorgid" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择审计局机构" multiSelect="true"></div>
                                                        <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td id="user1"><label>联系人：</label></td>
                                                    <td id="user2">
                                                        <div id="username" class="search-textbox" width="300" height="30" onvalidate="nameValidate"></div>
                                                    </td>
                                                    <td id="phone1"><label>手机号码：</label></td>
                                                    <td id="phone2">
                                                        <div id="userphone" class="search-textbox" rules="phonenumber" width="300" height="30" onvalidate="phoneValidate"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>描　　述：</label></td>
                                                    <td colspan="3">
                                                        <div id="sdes" class="search-textarea" width="750" style="display:inline-block; float:left;"></div>
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
                                    <div class="contentList" id="contB" style="">
                                        <div id="uploader" class="cl-uploader" style="width:100%;">
                                            <div class="queueList">
                                                <%--原始拖拽div--%>
                                                <div id="dndArea" class="placeholder" style="min-height: 120px;">
                                                    <div id="filePicker" class="cl-picker"></div>
                                                    <p>或将照片拖到这里，单次最多可选300张</p>
                                                </div>
                                            </div>
                                            <%--状态div--%>
                                            <div class="statusBar" style="display:none;">
                                                <div class="progress">
                                                    <span class="text">0%</span> <span class="percentage"></span>
                                                </div>
                                                <div class="info"></div>
                                                <div class="btns">
                                                    <div id="filePicker2" class="cl-picker2"></div>
                                                    <%--<div class="uploadBtn">开始上传</div>--%>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="contentList" id="contC">
                                        <table border="1" style="width:100%; height:100px; border-collapse:collapse;">
                                            <tbody id="qua"></tbody>
                                        </table>
                                        <button id="btn3" onclick="Upload2.addQua()" class="qua-add">添加</button>
                                    </div>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="foot-btn">
                        <button type="button" class="btn btn-primary" id="btnBack">上一步</button>
                        <button type="button" class="btn btn-default" style="float:right; display:none" id="btnok" onclick="saveData()">完成</button>
                        <button type="button" class="btn btn-success" style="float:right; margin-right:10px;" id="btnNext">下一步</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- <div id="mask" class="mask"></div> -->

<!-- <script type="text/javascript">
    var myAlert = document.getElementById("alert");
    var myMask = document.getElementById('mask');
    myMask.style.display = "block";
    myAlert.style.display = "block";

</script> -->
<script type="text/javascript" language="javascript" src="${JS}/orgs/RegisterOrg.js?v=${RES_VER}"></script>
</body>
</html>