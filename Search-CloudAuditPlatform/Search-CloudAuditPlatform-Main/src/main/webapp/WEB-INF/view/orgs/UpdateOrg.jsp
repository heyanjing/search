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

        /* table tr td{padding-top:10px;} */
        .search-treeselect {
            float: left;
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
                                            <td style="padding-top:5px;"><label>名　　称：</label></td>
                                            <td style="padding-top:5px;">
                                                <div id="sname" class="search-textbox" rules="required" width="300" height="30"></div>
                                            </td>
                                            <td style="padding-top:5px;"><label style="margin-left:10px;">父　　级：</label></td>
                                            <td style="padding-top:5px;">
                                                <div id="sparentid" style="float: left;" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择父级机构" onvaluechanged="changsparentid"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>

                                        <tr>

                                            <td id="orgtype1" style="padding-top:5px;"><label>机构类型：</label></td>
                                            <td id="orgtype2" style="padding-top:5px;">
                                                <div id="itype" class="search-select" rules="required" idField="value" textField="text" width="300" height="30" placeHolder="请选择机构类型" multiSelect="true" onvaluechanged="changsauditorgid"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>

                                            <td id="org3" style="padding-top:5px;"><label>是否部门：</label></td>
                                            <td id="org4" style="padding-top:5px;">
                                                <div id="iisdepartment" class="search-textradio" idField="value" textField="text" width="300" defaultValue="1" groupName="iisdepartment" style="line-height:30px;float:left;" onvaluechanged="changiisdepartment"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                             <td id="area1" style="padding-top:5px;"><label>所属区县：</label></td>
                                            <td id="area2" style="padding-top:5px;">
                                                <div id="area" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择所属区县"  onvalidate="areaValidate"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="user1" style="padding-top:5px;"><label>联系人：</label></td>
                                            <td id="user2" style="padding-top:5px;">
                                                <div id="username" class="search-textbox" width="300" height="30" onvalidate="nameValidate"></div>
                                            </td>
                                            <td id="phone1" style="padding-top:5px;"><label>手机号码：</label></td>
                                            <td id="phone2" style="padding-top:5px;">
                                                <div id="userphone" class="search-textbox" rules="phonenumber" width="300" height="30" onvalidate="phoneValidate"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td id="org1" style="padding-top:5px;"><label>审计机构：</label></td>
                                            <td id="org2" style="padding-top:5px;">
                                                <div id="sauditorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择审计局机构" onvalidate="sauditorgidValidate" multiSelect="true"></div>
                                                <span><a href="javascript:void(0)"><i class="fa fa-question-circle"></i></a></span>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td style="padding-top:5px;"><label>描　　述：</label></td>
                                            <td colspan="3" style="padding-top:5px;">
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
                            <div class="contentList" id="cont2" style="height:auto;">
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
                            <div class="contentList" id="cont3">
                                <table border="1" style="width:100%; height:100px; border-collapse:collapse; border:#ccc 1px solid; margin-top:10px;">
                                    <tbody id="qua"></tbody>
                                </table>
                                <button class="qua-add" id="btn3" onclick="Upload2.addQua()">添加</button>
                            </div>
                            <div class="contentList" id="cont4" style="margin-top:10px;height:100%;">
                                <div class="fixedTb" style="position:relative;height:100%;">
                                    <div class="flow-item contentList" data-title="授权功能" style="display:block;height:100%;">
                                        <div id="datagrid" class="search-treedatagrid" idField="id" textField="sname" ondrawcell="drawcell"
                                             style="width: 100%; height: 100%;">
                                            <div property="columns">
                                                <c:forEach var="cls" items="${headlist}" varStatus="status">
                                                    <div field="${cls.head}" width="80" headAlign="center" textAlign="center">${cls.sname}</div>
                                                </c:forEach>
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

<script type="text/javascript" src="${JS}/orgs/UpdateOrg.js?${V}"></script>
</body>
</html>