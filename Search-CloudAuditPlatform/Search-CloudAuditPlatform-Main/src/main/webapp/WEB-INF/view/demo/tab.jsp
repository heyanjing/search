<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert title here</title>

    <style>
        ul, li {
            list-style: none;
        }

        .wrap {
            width: 100%;
            height: 100%;
            background: #ffffff;
        }

        .construction-tab {
            width: 750px;
            margin: 0 auto 50px;
        }

        .construction-tab-title {
            background: #0584a9;
            overflow: hidden;
            zoom: 1;
        }

        .construction-tab-title li {
            float: left;
            width: 150px;
            height: 30px;
            line-height: 30px;
            color: #fff;
            text-align: center;
            cursor: pointer;
        }

        .construction-tab-title li.active {
            background: #06a8d7;
        }

        .construction-tab-bd li {
            display: none;
            padding: 20px;
            border: 1px solid #ddd;
            border-top: 0 none;
            font-size: 24px;
        }

        /*.construction-tab-bd li.thisclass{ display:list-item;}*/

        .construction-table {
            width: 750px;
            margin: 0 auto 50px;
        }

        .construction-table-title {
            background: #0584a9;
            overflow: hidden;
            zoom: 1;
        }

        .construction-table-title li {
            float: left;
            width: 150px;
            height: 30px;
            line-height: 30px;
            color: #fff;
            text-align: center;
            cursor: pointer;
        }

        .construction-table-title li.table-active {
            background: #06a8d7;
        }

        .construction-table-bd li {
            display: none;
            padding: 20px;
            border: 1px solid #ddd;
            border-top: 0 none;
            font-size: 24px;
        }

        .construction-table-bd li.table-thisclass {
            display: list-item;
        }

        .construction-table-bd li.table-thisclass table {
            width: 100%;
            height: 100px;
            border: 1px solid #ccc;
            border-collapse: collapse;
        }

        .construction-table-bd li.table-thisclass table tr td {
            border: 1px solid #ccc;
        }
    </style>

</head>
<body>
<div class="wrap">
    <div class="construction-tab">
        <ul id="construction" class="construction-tab-title">
            <li class="active">立项</li>
            <li>可研</li>
            <li>概算</li>
            <li>预算</li>
            <li>施工招投标</li>
        </ul>
        <ul id="construction-bd" class="construction-tab-bd">
            <li class="thisclass">内容1</li>
            <li>内容2</li>
            <li>内容3</li>
            <li>内容4</li>
            <li>内容5</li>
        </ul>
    </div>

    <div class="construction-table">
        <ul id="table-construction" class="construction-table-title">
            <li class="table-active">立项</li>
            <li>可研</li>
        </ul>
        <ul id="table-construction-bd" class="construction-table-bd">
            <li class="table-thisclass">
                <table>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </li>
            <li>内容2</li>
        </ul>
    </div>

</div>


<script>
    $('#construction').on('click', 'li', function () {
        var $li = $(this), index = $li.index(), $constructionBd = $('#construction-bd').children().eq(index);
        $li.addClass('active').siblings().removeClass('active');
        $constructionBd.addClass('thisclass').siblings().removeClass('thisclass');
    });

    $('#table-construction').on('click', 'li', function () {
        var $li = $(this), index = $li.index(), $constructionBd = $('#table-construction-bd').children().eq(index);
        $li.addClass('table-active').siblings().removeClass('table-active');
        $constructionBd.addClass('table-thisclass').siblings().removeClass('table-thisclass');
    });
</script>

</body>
</html>