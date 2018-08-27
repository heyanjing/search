<%@page language="java" pageEncoding="UTF-8" %>

<fieldset id="StepHistoryOpinionField" style=" border: 2px dashed #1ebcea; margin:0 auto; padding:10px;">
	<legend style="font-size:18px; font-family: '微软雅黑'; background-color:#fff;font-weight:500; color:#1ebcea; text-align:left;">&nbsp;&nbsp;历史意见区&nbsp;&nbsp;</legend>
	<div></div>
</fieldset>
<fieldset id="StepOpinionField" style=" border: 2px dashed #1ebcea; margin:0 auto; padding:10px;">
	<legend style="font-size:18px; font-family: '微软雅黑'; background-color:#fff;font-weight:500; color:#1ebcea; text-align:left;">&nbsp;&nbsp;审批意见区&nbsp;&nbsp;</legend>
	<form id="StepOpinionForm">
	    <table class="around_list">
	    	<tr>
	            <td>
	                <label>审批意见：</label>
	            </td>
	            <td>
	                <div id="StepOpinion" class="search-textradio" idField="id" textField="text" defaultValue="1" width="650" height="30" style="line-height:30px;"></div>
	            </td>
	        </tr>
	        <tr>
	            <td>
	                <label>意见详情：</label>
	            </td>
	            <td>
	                <div id="StepSdesc" class="search-textarea" width="650" height="150"></div>
	            </td>
	        </tr>
	    </table>
	</form>
</fieldset>
<fieldset id="StepProcessStep" style="height: 100%;border: 2px dashed #1ebcea; margin:0 auto;  margin-top:10px;">
	<legend style="font-size:18px; font-family: '微软雅黑'; background-color:#fff;font-weight:500; color:#1ebcea; text-align:left;">&nbsp;&nbsp;业务流转区&nbsp;&nbsp;</legend>
	<div class="left" style="margin:10px;">
	    <div id="StepStepsGrid" class="search-treedatagrid" idField="sid" textField="sname" showCheckBox="false" multiSelect="false"
	         alternatingRows="true" expandLevel="4" style="width:100%;height:175px;" ondrawcell="processstepsdrawcell" onselectchanged="getUsers" onloaded="processstepsloaded">
	        <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="sname" width="200" headAlign="center" textAlign="left">下一步骤</div>
	            <!-- <div field="itype" width="50" headAlign="center" textAlign="center">步骤类型</div>
	            <div field="isupportback" width="50" headAlign="center" textAlign="center">是否支持回退</div> -->
	        </div>
	    </div>
	</div>
	<div class="right" style="margin:10px;">
		<div id="StepUsersGrid" class="search-treedatagrid" idField="sid" textField="sname" showCheckBox="false" multiSelect="false"
	         alternatingRows="true" expandLevel="4" style="width:100%;height:175px;" ondrawcell="usersdrawcell">
	        <div property="columns">
	            <div type="checkcolumn"></div>
	            <div field="sname" width="200" headAlign="center" textAlign="left">下一步骤处理人</div>
	            <!-- <div field="itype" width="50" headAlign="center" textAlign="center">步骤类型</div>
	            <div field="isupportback" width="50" headAlign="center" textAlign="center">是否支持回退</div> -->
	        </div>
	    </div>
	</div>
</fieldset>
<script type="text/javascript" src="${JS}/processsteps/ProcessSteps.js?${V}"></script>
