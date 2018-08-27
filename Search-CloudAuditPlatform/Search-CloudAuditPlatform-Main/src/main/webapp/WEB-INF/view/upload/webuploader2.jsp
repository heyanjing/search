<%@ page language="java" pageEncoding="utf-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>文件上传</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css">
    <style>

        /*.element-invisible {*/
        /*position: absolute !important;*/
        /*clip: rect(1px 1px 1px 1px); !* IE6, IE7 *!*/
        /*clip: rect(1px,1px,1px,1px);*/
        /*}*/
    </style>
</head>

<body>
<button id="btn1">开始上传1</button>
<button id="btn2">开始上传2</button>
<button id="btn3" onclick="Upload2.addQua()">添加</button>
<button id="btn4" onclick="Upload2.upload()">上传</button>
<button id="btn5" onclick="preview()">图片展示</button>


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
<table border="1" style="width:200px; height:100px; border-collapse:collapse;">
    <%--<thead>--%>
    <%--<tr>--%>
    <%--<td>资质</td>--%>
    <%--<td>备注</td>--%>
    <%--<td>附件</td>--%>
    <%--<td>操作</td>--%>
    <%--</tr>--%>
    <%--</thead>--%>
    <tbody id="qua">
    <%-- <tr>
         <td colspan="3">
             <div id="qua1" class="search-select" rules="required" multiSelect="false" idField="id" textField="text" width="400" height="40"></div>
         </td>
          <td>
             <button class="del" data-line="2" data-sid="sid2" >删除</button>
         </td>
     </tr>
     <tr>
         <td>
             <div id="sdesc1" class="search-textarea" width="300"></div>
         </td>
         <td id="img1" style="padding-left:20px;">
              <div class="queueList2">
                 <div class="imgIndex1" style="position:relative">
                    <p class="imgWrap">
                         <img id="fileId1" data-line="line" src="${NETWORK_ROOT}/search/a/f3.jpg" style="width: 100px;height: 100px;">
                    </p>
                    <div class="file-panel" style="height:28px;">
                          <span><i class="fa fa-bank"></i></span>
                    </div>
                 </div>
             </div>
         </td>
     </tr>



     <tr>
         <td colspan="3">
             <div id="qua2" class="search-select" rules="required" multiSelect="false" idField="id" textField="text" width="400" height="40"></div>
         </td>
          <td>
             <button class="del" data-line="2" data-sid="sid2" >删除</button>
         </td>
     </tr>
     <tr>
         <td>
             <div id="sdesc2" class="search-textarea" width="300"></div>
         </td>
         <td id="img2">
             <div class="placeholder2">
                 <div id="pickers1">选择文件</div>
             </div>
             <div class="queueList2">
             </div>
         </td>
     </tr>--%>
    </tbody>

</table>

<script type="text/javascript" src="${JS}/upload/webuploader2.js"></script>
</body>
</html>