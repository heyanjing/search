<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!doctype html>
<html>
<head>
    <title>流程弹框</title>
    <meta name='description' content='首页head中的内容'>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>

</head>
<body>

<div id="content" style="text-align:center;margin:40px auto;"><a style="font-size:36px;" href="#">点击弹出层</a></div>

<div id="alert" class="">
    <div class="model-head">
        <span class="close"><i class="glyphicon glyphicon-remove"></i></span>
        <h4 class="modal-title">model弹出层</h4>
    </div>
    <div class="model-content">
        <div class="main">
            <div class="row">
                <div class="col-md-12">
                    <div class="flow">
                        <div class="flowListBox"></div>
                    </div>
                </div>
                <div class="col-md-12" id="flowDiv">
                    <ol id="iList" style="padding-left:15px;">
                        <div id="contA">aaaaa<br>aaaaaaaaa<br>aaaaaaaaa<br>aaaaaaaaa<br>aaaaaaaaa<br>aaaaaaaaa<br>aaaaaaaaa<br>aaaaaaaaa<br>aaaaaaaaa</div>
                        <div class="contentList" id="contB">bb<br>bbbbbb<br>22222222<br>22222222222<br>222222222<br>222222<br>2222222<br>222222<br>222222</div>
                        <div class="contentList" id="contC">cc</div>
                        <div class="contentList" id="contD">dd</div>
                    </ol>
                </div>
            </div>
        </div>
    </div>

    <div class="foot-btn">
        <button type="button" class="btn btn-primary" id="btnBack">上一步</button>
        <button type="button" class="btn btn-default" style="float:right; display:none" id="btnok">完成</button>
        <button type="button" class="btn btn-success" style="float:right; margin-right:10px;" id="btnNext">下一步</button>
    </div>
</div>

<div id="mask"></div>

<script type="text/javascript" src="${JS}/html/demo/flow.js?${V}"></script>
<script type="text/javascript">
    var myAlert = document.getElementById("alert");
    var myMask = document.getElementById('mask');

    $("#content a").click(function () {
        myMask.style.display = "block";
        myAlert.style.display = "block";
        document.body.style.overflow = "hidden";
    })
    $(".close").click(function () {
        myAlert.style.display = "none";
        myMask.style.display = "none";
    })
</script>
</body>
</html>