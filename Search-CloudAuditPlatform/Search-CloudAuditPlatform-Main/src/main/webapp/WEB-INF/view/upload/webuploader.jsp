<%@ page language="java" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>文件上传</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
</head>
<body>
<button id="btn1">开始上传1</button>
<button id="btn2">开始上传2</button>
<button id="btn3">开始上传3</button>


<div id="uploader" class="cl-uploader" style="width: 400px;height: 200px;float: left">
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
<%--<div id="uploaderx" class="cl-uploader" style="width: 400px;height: 200px;float: left">--%>
<%--<div class="queueList">--%>
<%--&lt;%&ndash;原始拖拽div&ndash;%&gt;--%>
<%--<div id="dndArea" class="placeholder" style="min-height: 120px;">--%>
<%--<div id="filePickerx" class="cl-picker"></div>--%>
<%--<p>或将照片拖到这里，单次最多可选300张</p>--%>
<%--</div>--%>
<%--</div>--%>
<%--&lt;%&ndash;状态div&ndash;%&gt;--%>
<%--<div class="statusBar" style="display:none;">--%>
<%--<div class="progress">--%>
<%--<span class="text">0%</span> <span class="percentage"></span>--%>
<%--</div>--%>
<%--<div class="info"></div>--%>
<%--<div class="btns">--%>
<%--<div id="filePicker2x" class="cl-picker2"></div>--%>
<%--&lt;%&ndash;<div class="uploadBtn">开始上传</div>&ndash;%&gt;--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<div id="uploaderx" class="cl-uploader" style="width: 400px;height: 200px;float: left">
    <div class="queueList">
        <%--原始拖拽div--%>
        <div id="dndArea" class="placeholder" style="min-height: 120px;">
            <div id="filePickerx" class="cl-picker"></div>
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
            <div id="filePicker2x" class="cl-picker2"></div>
            <%--<div class="uploadBtn">开始上传</div>--%>
        </div>
    </div>
</div>
<div id="uploaderxx" class="cl-uploader" style="width: 400px;height: 200px;float: left">
    <div class="queueList">
        <%--原始拖拽div--%>
        <div id="dndArea" class="placeholder" style="min-height: 120px;">
            <div id="filePickerxx" class="cl-picker"></div>
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
            <div id="filePicker2xx" class="cl-picker2"></div>
            <%--<div class="uploadBtn">开始上传</div>--%>
        </div>
    </div>
</div>
<script type="text/javascript" src="${JS}/upload/webuploader.js"></script>
</body>
</html>