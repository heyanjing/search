<%@ page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/jspTaglib.jsp" %>
<script type="text/javascript">
    var __state = '${param.recycle}' == 'recycle' ? 2 : 1;
    var __funcid = "${button.funcid}";
    var tailButList = new Array(); //尾部功能数组。
    var leftButList = new Array(); //快捷功能数组。
    var quickOnClickList = new Array(); //所有快捷功能数组。
    <c:forEach items="${button.tailButList}" var="tail">
    tailButList.push(${tail});
    </c:forEach>
    <c:forEach items="${button.leftButList}" var="left">
    leftButList.push(${left});
    </c:forEach>
    <c:forEach items="${button.quickOnClick}" var="quick">
    quickOnClickList.push(${quick});
    </c:forEach>

    var _quick_map = new Globle.obj.Map();
    var _all_quick_map = new Globle.obj.Map();
    $(function () {
        for (var i = 0; i < leftButList.length; i++) {
            var event = leftButList[i].sbindevent;
            var __event_arr = event.split(":");
            _quick_map.put(__event_arr[0], __event_arr[1]);
        }

        for (var i = 0; i < quickOnClickList.length; i++) {
            var event = quickOnClickList[i].sbindevent;
            var __event_arr = event.split(":");
            _all_quick_map.put(__event_arr[0], __event_arr[1]);
        }
    });
    //返回
    function back() {
        window.parent.Index.classify2.back();
    }
</script>
<div class="tab_cz" id="button-id">
    <c:choose>
        <c:when test="${param.recycle != null}">
            <div class="search-button blue" onclick="delData();" text="删除" style="margin-left: 10px; width: 80px; height: 30px; line-height: 30px;">删除</div>
            <div class="search-button blue" onclick="restData();" text="恢复" style="margin-left: 10px; width: 80px; height: 30px; line-height: 30px;">恢复</div>
            <div class="search-button blue" onclick="doQuery();" text="展开查询" style="margin-left: 10px; width: 80px; height: 30px; line-height: 30px;">展开查询</div>
            <div class="search-button blue" onclick="back();" text="返回" style="margin-left: 10px; width: 80px; height: 30px; line-height: 30px;">返回</div>
        </c:when>
        <c:otherwise>
            <c:forEach items="${button.list}" var="func">
                <c:if test="${fn:contains(func.sbtnlocation, '101')}">
                    <div id="${func.sId}" class="search-button blue" onclick="${func.spcmethod}();" style="margin-left: 10px; padding:0 10px; height: 30px; line-height: 30px;">${func.sname}</div>
                </c:if>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>