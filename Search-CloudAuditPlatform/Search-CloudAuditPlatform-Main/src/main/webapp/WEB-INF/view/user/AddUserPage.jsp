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
        var sphone = "";
        var code = "";
        var identificationpath = "";//身份证上传的地址
        var identificationsNams = "";//身份证附件名称

        var qualificationspath = "";//资质上传的地址
        var qualinames = "";//资质附件名称
        var sdesc = "";//备注

        var sdictionarieid = "";//资质id
    </script>
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
    </style>
</head>

<body>
<div class="wrap">
    <div class="user-circle" style="width: 1000px; height:600px; top:10%; left: 0; box-shadow: 0 5px 8px 5px rgba(144, 144, 144, 0.5);  margin: 0 auto; position: relative;background:#ffffff; border-radius:5px;">
        <div id="alert" style="left: 0;top: 0;margin-left: 0;width:100%;height:100%;display:block;">
            <div class="mode2-head">
                <span class="close"><i class="glyphicon glyphicon-remove"></i></span>
                <h4 class="moda2-title">
                    <i class="fa fa-user-circle"></i>用户注册
                </h4>
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
                                <ol id="iList" style="padding:15px 15px 0 15px">
                                    <div id="contA" style="margin-top: 10px;">
                                        <form id=Usersform>
                                            <div id="sid" class="search-texthide"></div>
                                            <table class="useradd_list"
                                                   style=" text-align: right; margin-left:100px;">
                                                <tr>
                                                    <td><label>姓名：</label></td>
                                                    <td>
                                                        <div id="sname" name="sname,name" class="search-textbox"
                                                             rules="required" width="300" height="30"></div>
                                                    </td>
                                                    <td><label>手机：</label></td>
                                                    <td>
                                                        <div id="sphone" name="sphone"
                                                             class="search-textarea" rules="required,phonenumber"
                                                             width="300" height="30"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>身份证：</label></td>
                                                    <td>
                                                        <div id="sidcard" name="sidcard"
                                                             class="search-textarea" rules="required,number"
                                                             width="300" height="30"></div>
                                                    </td>
                                                    <td><label>选择机构：</label></td>
                                                    <td>
                                                        <div id="orgs"
                                                             class="search-treeselect" idField="sid" parentField="sparentid" textField="sname"
                                                             width="300" enabled="true" dropdownHeight="300" height="30"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>邮箱：</label></td>
                                                    <td>
                                                        <div id="semail" name="semail" rules="required,email"
                                                             class="search-textarea" width="300" height="30"></div>
                                                    </td>
                                                    <td><label>毕业院校：</label></td>
                                                    <td>
                                                        <div id="sgraduateschool" name="sgraduateschool"
                                                             class="search-textarea" width="300" height="30"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>毕业时间：</label></td>
                                                    <td>
                                                        <div id="ldgraduationdate" class="search-datepicker"
                                                             width="300" style="float: left;"></div>
                                                    </td>
                                                    <td><label>昵称：</label></td>
                                                    <td>
                                                        <div id="snickname" name="snickname"
                                                             class="search-textarea" width="300" height="30"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>签名：</label></td>
                                                    <td>
                                                        <div id="ssignature" name="ssignature"
                                                             class="search-textarea" width="300" height="30"></div>
                                                    </td>
                                                    <td><label>性别：</label></td>
                                                    <td>
                                                        <div id="igender" name="igender"
                                                             class="search-textradio" width="300" idField="id"
                                                             textField="text" defaultValue="1" style="line-height:30px;"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><label>生日：</label></td>
                                                    <td>
                                                        <div id="ldbirthday" name="ssignature"
                                                             class="search-datepicker" width="300" height="30"
                                                             style="float: left;"></div>
                                                    </td>
                                                    <!-- <td><label>选择机构：</label></td>
                                                    <td><div id="orgs"
                                                            class="search-select" idField="sid" textField="sname"
                                                            width="300" enabled="true" dropdownHeight="100" height="30"></div></td>
                                                    <td><label>选择职务：</label></td>
                                                    <td><div id="organduserrefs"
                                                            class="search-select" idField="sid" textField="sname"
                                                            width="300" enabled="true" height="30"></div></td> -->
                                                </tr>
                                                <tr>
                                                    <td><label>验证码：</label></td>
                                                    <td>
                                                        <div id="yzm" name="yzm" class="search-textbox"
                                                             rules="required" width="300" height="30">
                                                    </td>
                                                    <td colspan="2" align="left"><input id="code" type="button" value="免费获取验证码" onclick="getCode()"></td>
                                                </tr>

                                            </table>
                                        </form>
                                    </div>

                                    <div class="contentList" id="contB" style="">
                                        <div id="uploader" class="cl-uploader"
                                             style="width: 100%;">
                                            <div class="queueList">
                                                <%--原始拖拽div--%>
                                                <div id="dndArea" class="placeholder"
                                                     style="min-height: 120px;">
                                                    <div id="filePicker" class="cl-picker"></div>
                                                    <p>职务附件上传</p>
                                                </div>
                                            </div>
                                            <%--状态div--%>
                                            <div class="statusBar" style="display: none;">
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

                                    <div class="contentList" id="contC" style="">

                                        <table border="1" style="width:100%; height:200px; border-collapse:collapse;">
                                            <tbody id="qua">

                                            </tbody>
                                        </table>
                                        <button id="btn3" onclick="Upload2.addQua()" class="qua-add">添加</button>
                                    </div>
                                </ol>
                            </div>
                        </div>
                    </div>
                    <div class="foot-btn">
                        <button type="button" class="btn btn-primary" id="btnBack">上一步</button>
                        <button type="button" class="btn btn-default"
                                style="float: right; display: none" id="btnok">完成
                        </button>
                        <button type="button" class="btn btn-success"
                                style="float: right; margin-right: 10px;" id="btnNext">下一步
                        </button>
                    </div>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- <div id="mask" class="mask"></div> -->
<script type="text/javascript" language="javascript"
        src="${JS}/user/addUser.js?v=${RES_VER}"></script>
<script type="text/javascript" src="${JS}/user/flow.js?${V}"></script>
<!-- <script type="text/javascript">
    var myAlert = document.getElementById("alert");
    var myMask = document.getElementById('mask');
    myMask.style.display = "block";
    myAlert.style.display = "block";
</script> -->

</body>
</html>