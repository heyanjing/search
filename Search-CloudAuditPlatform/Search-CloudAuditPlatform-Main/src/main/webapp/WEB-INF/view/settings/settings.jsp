<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>系统设置</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <meta name='description' content='系统设置'>
    <style type="text/css">
        .uploader-demo .thumbnail img {
            width: 100%;
        }

        .file-item {
            position: relative;
            width: 100px;
            height: 100px;
            margin: auto;
        }

        .file-item .error {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            background: red;
            color: white;
            text-align: center;
            height: 20px;
            font-size: 14px;
            line-height: 23px;
        }

        .file-item .info {
            position: absolute;
            left: 0;
            bottom: 0;
            right: 0;
            height: 20px;
            line-height: 20px;
            text-indent: 5px;
            background: rgba(0, 0, 0, 0.6);
            color: white;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
            font-size: 12px;
            z-index: 10;
        }

        .file-item .success {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            background: green;
            color: white;
            text-align: center;
            height: 20px;
            font-size: 14px;
            line-height: 23px;
        }

        .file-item .panel {
            position: absolute;
            top: 4px;
            left: 4px;
            right: 4px;
            background: rgba(0, 0, 0, 0.5);
            color: white;
            text-align: center;
            font-size: 14px;
            line-height: 23px;
        }

        .file-item .panel .cancel {
            width: 24px;
            height: 24px;
            display: inline;
            float: right;
            text-indent: -9999px;
            overflow: hidden;
            background: url(${IMG}/upload/icons.png) no-repeat;
            margin: 5px 1px 1px;
            cursor: pointer;
            background-position: -48px -24px;
        }

        .file-item .progress {
            position: absolute;
            right: 4px;
            bottom: 4px;
            height: 3px;
            left: 4px;
            height: 4px;
            overflow: hidden;
            z-index: 15;
            margin: 0;
            padding: 0;
            border-radius: 0;
            background: transparent;
        }

        .file-panel span i {
            font-size: 18px;
            display: inline;
            float: right;
            color: #ffffff;
            margin: 5px;
            cursor: pointer;
        }

        @-webkit-keyframes progressmove {
            0% {
                background-position: 0 0;
            }
            100% {
                background-position: 17px 0;
            }
        }

        @-moz-keyframes progressmove {
            0% {
                background-position: 0 0;
            }
            100% {
                background-position: 17px 0;
            }
        }

        @keyframes progressmove {
            0% {
                background-position: 0 0;
            }
            100% {
                background-position: 17px 0;
            }
        }

        .webuploader-container {
            padding-top: 40px;
        }

        table tr td label {
            float: right;
        }

        table tr td {
            padding-top: 10px;
        }
    </style>
</head>
<body>
<form id="setting">
    <div id="sid" class="search-texthide"></div>
    <div id="sicon" class="search-texthide"></div>
    <div id="slogo" class="search-texthide"></div>
    <table class="around_list" style="margin:0 auto; margin-top:20px;">
        <tr>
            <td>
                <label>系统图标：</label>
            </td>
            <td>
                <div class="uploader-demo">
                    <div id="siconList" class="uploader-list" style="float:left;"></div>
                    <div id="siconPicker">选择图标</div>
                </div>
            </td>
            <td>
                <label>系统LOGO：</label>
            </td>
            <td>
                <div class="uploader-demo">
                    <div id="slogoList" class="uploader-list" style="float:left;"></div>
                    <div id="slogoPicker">选择LOGO</div>
                </div>
            </td>
        </tr>

        <tr>
            <td>
                <label>应用单位名称：</label>
            </td>
            <td>
                <div id="sorgname" class="search-textbox" rules="required" width="200" height="30"></div>
            </td>
            <td>
                <label>系统名称：</label>
            </td>
            <td>
                <div id="ssystemname" class="search-textbox" rules="required" width="200" height="30"></div>
            </td>
        </tr>
        <tr>
            <td>
                <label>是否启用用户数量控制：</label>
            </td>
            <td>
                <div id="isupportusernumber" class="search-textradio" idField="value" textField="text" defaultValue="2" onvaluechanged="isupportusernumberchange" width="200" height="30" style="line-height:30px;"></div>
            </td>
            <td style="padding-left:20px;">
                <label>最大用户数：</label>
            </td>
            <td>
                <div id="imaxnumber" class="search-textbox" width="200" height="30"></div>
            </td>
        </tr>
        <tr>
            <td>
                <label>用户数量不受控制单位类型：</label>
            </td>
            <td>
                <div id="sorgtype" class="search-select" idField="value" textField="text" multiSelect="true" width="200" height="30"></div>
            </td>
        </tr>
        <tr>
            <td colspan="4" style="text-align:center; padding-top:5px;">
                <input class="org-save" type="button" onclick="saveData()" value="保存"/>
            </td>
        </tr>
    </table>
</form>
<script type="text/javascript" src="${JS}/settings/settings.js"></script>
</body>
</html>