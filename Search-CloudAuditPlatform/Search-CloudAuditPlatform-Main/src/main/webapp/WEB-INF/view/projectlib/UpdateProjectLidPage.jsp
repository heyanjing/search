<%@page language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/layout/taglib/head.jsp" %>
<!doctype html>
<html>
<head>
    <title>新增/编辑</title>
    <link rel="stylesheet" type="text/css" href="${CSS}/demo/demo.css?${V}"/>
    <link rel="stylesheet" type="text/css" href="${CSS}/upload/webuploader.css?${V}">
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
            width: 100%;
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

        .construction-tab-bd li.this {
            display: none;
            border-top: 0 none;
            font-size: 24px;
            height: 100%
        }

        .construction-tab-bd li.thisclass {
            display: list-item;
        }

        table tr td {
            padding-top: 10px;
        }

        .search-treeselect td {
            padding-top: 0;
        }

        .cl-uploader .queueList {
            margin: 0;
            width: 745px;
        }

        .proLibParams {
            width: 100%;
            height: auto;
            position: absolute;
            top: 30px;
            left: 0;
            bottom: 0;
        }

        .develop {
            padding: 10px;
        }

        .org-btn a {
            color: blue;
            cursor: pointer;
        }

        .org-btn a:hover {
            color: #06a8d7;
        }
    </style>
    <script type="text/javascript">
        var __funcid = "${funcid}", _prolib_id = "${sid}", _prolib_istate = "${prolibState}", _pro_lib_auditorgid = "${sauditorgid}", _user_type = ${usertype};
    </script>
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
            <li class="this thisclass">
                <form id="ApprovalForm" style="position:absolute; top:30px; bottom:50px; width:100%; overflow:auto;">
                    <div id="sprojectlibsid" class="search-texthide"></div>
                    <div id="sid" class="search-texthide"></div>
                    <table class="around_list" style=" margin:0 auto; width:900px;">
                        <c:if test="${usertype}">
                            <tr>
                                <td style="width: 100px;" align="right"><label>项目归属审计机构：</label></td>
                                <td colspan="3" align="left">
                                    <div id="sauditorgid" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择项目归属审计机构" onvaluechanged="onApprovalValueChanged" onloaded="onAuditOrgIdLoaded" ></div>
                                </td>
                            </tr>
                        </c:if>
                        <tr>
                            <td style="width: 100px;" align="right"><label>项目名称：</label></td>
                            <td colspan="3">
                                <div id="sname" class="search-textbox" rules="required" width="745" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 100px;" align="right"><label>项目业主：</label></td>
                            <td>
                                <div id="sproprietororgid" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择项目业主" onvalidate="proprietororgValidate" onloaded="onProprietorOrgidLoaded" ></div>
                            </td>
                            <td style="width: 100px;" align="right"><label>审批单位：</label></td>
                            <td>
                                <div id="sapprovalorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择审批单位" onloaded="onApprovalOrgidLoaded" ></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>代建单位：</label></td>
                            <td>
                                <div id="sdeputyorgid" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择代建单位" onloaded="onDeputyOrgidLoaded"></div>
                            </td>
                            <td align="right"><label>审批文号：</label></td>
                            <td>
                                <div id="sapprovalnum" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>项目地址：</label></td>
                            <td>
                                <div id="saddress" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                            <td align="right"><label>审批时间：</label>
                            </td>
                            <td align="left">
                                <div id="ldapprovaldate" class="search-datepicker" rules="date:YYYY-MM-DD" width="300" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>资金来源：</label></td>
                            <td>
                                <div id="scapitalsource" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                            <td align="right"><label>项目估算总金额(万元)：</label>
                            </td>
                            <td align="left">
                                <div id="destimateamount" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>招标核准：</label></td>
                            <td colspan="3" align="left">
                                <div style="display:inline-block; height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">勘察</div>
                                    <div id="iprospectingtype" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block;height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">设计</div>
                                    <div id="idesigntype" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block; height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">监理</div>
                                    <div id="isupervisiontype" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block;height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">施工</div>
                                    <div id="iconstructiontype" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block;height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">咨询</div>
                                    <div id="iintermediarytype" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>建设规模和内容：</label></td>
                            <td colspan="3" align="left">
                                <div id="sdesc" class="search-textarea" width="748" height="150" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>相关文件：</label></td>
                            <td colspan="3" align="left">
                                <div id="uploader" class="cl-uploader">
                                    <div class="queueList">
                                        <!-- 原始拖拽div -->
                                        <div id="dndArea" class="placeholder" style="min-height: 120px; width:745px;">
                                            <div id="filePicker" class="cl-picker"></div>
                                            <p>或将文件拖到这里，单次最多可选300份</p>
                                        </div>
                                    </div>
                                    <!-- 状态div -->
                                    <div class="statusBar" style="display: none;">
                                        <div class="progress">
                                            <span class="text">0%</span> <span class="percentage"></span>
                                        </div>
                                        <div class="info"></div>
                                        <div class="btns">
                                            <div id="filePicker2" class="cl-picker2"></div>
                                            <!-- <div class="uploadBtn">开始上传</div> -->
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="org-btn" colspan="4" style="position:absolute;bottom:0;">
                    <input class="org-back" type="button" onclick="goBack()" value="返回"/>
                    <input class="org-save" type="button" onclick="saveApproval()" value="保存"/>
                    <input class="org-close" type="button" onclick="adjustApprovals()" value="调整"/>
                    <a onclick="getApprovalHistory();">调整历史</a>
                </div>
            </li>
            <li class="this">
                <form id="BilityForm" style="position:absolute; top:30px; bottom:50px; width:100%; overflow:auto;">
                    <div id="sprojectlibsid_0" class="search-texthide"></div>
                    <div id="sid_0" class="search-texthide"></div>
                    <table class="around_list" style=" margin:0 auto; width:900px;">
                        <tr>
                            <td style="width: 100px;" align="right"><label>项目名称：</label></td>
                            <td colspan="3">
                                <div id="sname_0" class="search-textbox" rules="required" width="745" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td style="width: 100px;" align="right"><label>项目业主：</label></td>
                            <td>
                                <div id="sproprietororgid_0" class="search-treeselect" rules="required" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择项目业主" onvalidate="proprietororgValidate" onloaded="onProprietorOrgidsLoaded"></div>
                            </td>
                            <td style="width: 100px;" align="right"><label>审批单位：</label></td>
                            <td>
                                <div id="sapprovalorgid_0" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择审批单位" onloaded="onApprovalOrgidsLoaded"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>代建单位：</label></td>
                            <td>
                                <div id="sdeputyorgid_0" class="search-treeselect" idField="sid" parentField="sparentid" textField="sname" width="300" height="30" placeHolder="请选择代建单位" onloaded="onDeputyOrgidsLoaded"></div>
                            </td>
                            <td align="right"><label>审批文号：</label></td>
                            <td>
                                <div id="sapprovalnum_0" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>项目地址：</label></td>
                            <td>
                                <div id="saddress_0" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                            <td align="right"><label>审批时间：</label>
                            </td>
                            <td align="left">
                                <div id="ldapprovaldate_0" class="search-datepicker" rules="date:YYYY-MM-DD" width="300" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>资金来源：</label></td>
                            <td>
                                <div id="scapitalsource_0" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                            <td align="right"><label>项目估算总金额(万元)：</label>
                            </td>
                            <td align="left">
                                <div id="destimateamount_0" class="search-textbox" width="300" height="30" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>招标核准：</label></td>
                            <td colspan="3" align="left">
                                <div style="display:inline-block; height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">勘察</div>
                                    <div id="iprospectingtype_0" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block;height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">设计</div>
                                    <div id="idesigntype_0" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block; height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">监理</div>
                                    <div id="isupervisiontype_0" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block;height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">施工</div>
                                    <div id="iconstructiontype_0" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                                <div style="display:inline-block;height:30px; line-height:30px;">
                                    <div style="position:relative; float:left;width:40px; text-align:center;">咨询</div>
                                    <div id="iintermediarytype_0" class="search-select" idField="value" textField="text" height="30" width="105" dropdownHeight="200" style="float:left;"></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>建设规模和内容：</label></td>
                            <td colspan="3" align="left">
                                <div id="sdesc_0" class="search-textarea" width="748" height="150" style="display:inline-block;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>计划开工时间：</label></td>
                            <td>
                                <div id="ldstartdate_0" class="search-datepicker" rules="date:YYYY-MM-DD" width="300" height="30" style="text-align:left;"></div>
                            </td>
                            <td align="right"><label>计划竣工时间：</label>
                            </td>
                            <td align="left">
                                <div id="ldenddate_0" class="search-datepicker" rules="date:YYYY-MM-DD" width="300" height="30" style="text-align:left;"></div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right"><label>相关文件：</label></td>
                            <td colspan="3" align="left">
                                <div id="uploaders" class="cl-uploader">
                                    <div class="queueList">
                                        <!-- 原始拖拽div -->
                                        <div id="dndArea" class="placeholder" style="min-height: 120px; width:745px;">
                                            <div id="filePickers" class="cl-picker"></div>
                                            <p>或将文件拖到这里，单次最多可选300份</p>
                                        </div>
                                    </div>
                                    <!-- 状态div -->
                                    <div class="statusBar" style="display: none;">
                                        <div class="progress">
                                            <span class="text">0%</span> <span class="percentage"></span>
                                        </div>
                                        <div class="info"></div>
                                        <div class="btns">
                                            <div id="filePicker2s" class="cl-picker2"></div>
                                            <!-- <div class="uploadBtn">开始上传</div> -->
                                        </div>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
                <div class="org-btn" colspan="4" style="position:absolute;bottom:0;">
                    <input class="org-back" type="button" onclick="goBack()" value="返回"/>
                    <input class="org-save" type="button" onclick="saveFeasibility()" value="保存"/>
                    <input class="org-close" type="button" onclick="adjustFeasibilitys()" value="调整"/>
                    <a onclick="getFeasibilityHistory();">调整历史</a>
                </div>
            </li>
            <li class="this">
                <div class="proLibParams">
                    <div id="proLibParams" class="develop">
                        <table>
                            <tr>
                                <td style="padding-top:0;"><label>关键字：</label></td>
                                <td style="padding-top:0;">
                                    <div id="calculationkeyword" class="search-textbox" width="300" height="25"></div>
                                </td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="calculation.queryCalculation();" value="查询"/></td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="calculation.addCalculat();" value="新增"/></td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="goBack();" value="返回"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="mini-fit">
                        <div id="CalculationGrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
                             showPager="true" pageSize="20" ondrawcell="onCalculatDrawCell" style="width:100%;height:100%;">
                            <div property="contextmenu">
                                <div id="detail" type="menu" text="详情" enabled="true" onclick="queryCalculDetail"></div>
                            </div>
                            <div property="columns">
                                <!-- <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div> -->
                                <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
                                <div field="sname" width="200" headAlign="center" textAlign="left">项目名称</div>
                                <div field="sapprovalnum" width="80" headAlign="center" textAlign="center">审批文号</div>
                                <div field="auditname" width="150" headAlign="center" textAlign="left">审批单位</div>
                                <div field="ldapprovaldate" width="80" headAlign="center" textAlign="center" allowSort="true">审批时间</div>
                                <div field="dcalculationamount" width="80" headAlign="center" textAlign="center" allowSort="true">项目概算总投资</br>（元）</div>
                                <div field="sdesc" width="200" headAlign="center" textAlign="left">建设规模和内容</div>
                                <div field="ldstartdate" width="80" headAlign="center" textAlign="center" allowSort="true">计划开工时间</div>
                                <div field="ldenddate" width="80" headAlign="center" textAlign="center" allowSort="true">计划竣工时间</div>
                                <div field="operation" width="60" headAlign="center" textAlign="center">操作</div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="this">
                <div class="proLibParams">
                    <div id="proLibParams" class="develop">
                        <table>
                            <tr>
                                <td style="padding-top:0;"><label>关键字：</label></td>
                                <td style="padding-top:0;">
                                    <div id="budgetkeyword" class="search-textbox" width="300" height="25"></div>
                                </td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="budget.queryBudget();" value="查询"/></td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="budget.addBudget();" value="新增"/></td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="goBack();" value="返回"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="mini-fit">
                        <div id="BudgetGrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
                             showPager="true" pageSize="20" style="width:100%;height:100%;" ondrawcell="onBudgetDrawCell">
                            <div property="contextmenu">
                                <div id="detail" type="menu" text="详情" enabled="true" onclick="queryBudgetDetail"></div>
                            </div>
                            <div property="columns">
                                <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div>
                                <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
                                <div field="sname" width="100" headAlign="center" textAlign="left">项目名称</div>
                                <div field="sapprovalnum" width="100" headAlign="center" textAlign="center">审批文号</div>
                                <div field="auditname" width="100" headAlign="center" textAlign="center">审批单位</div>
                                <div field="dbudgetamount" width="80" headAlign="center" textAlign="left">项目预算工程费用</br>（元）</div>
                                <div field="dengineeringcost" width="80" headAlign="center" textAlign="left">其中安装工程费</br>（元）</div>
                                <div field="dcommissioncost" width="80" headAlign="center" textAlign="center">其中招标代理服务费</br>（元）</div>
                                <div field="scapitalsource" width="80" headAlign="center" textAlign="left">资金来源</div>
                                <div field="operation" width="100" headAlign="center" textAlign="center">操作</div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li class="this">
                <div class="proLibParams">
                    <div id="proLibParams" class="develop">
                        <table>
                            <tr>
                                <td style="padding-top:0;"><label>关键字：</label></td>
                                <td style="padding-top:0;">
                                    <div id="bidkeyword" class="search-textbox" width="300" height="25"></div>
                                </td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="bid.queryBid();" value="查询"/></td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="bid.addBid();" value="新增"/></td>
                                <td style="padding-top:0;"><input class="org-search" type="button" onclick="goBack();" value="返回"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="mini-fit">
                        <div id="BidGrid" class="search-datagrid" idField="sid" showCheckBox="true" multiSelect="true" alternatingRows="true"
                             showPager="true" pageSize="20" style="width:100%;height:100%;" ondrawcell="onBidDrawCell">
                            <div property="contextmenu">
                                <div id="detail" type="menu" text="详情" enabled="true" onclick="queryBidDetail"></div>
                            </div>
                            <div property="columns">
                                <div type="checkcolumn" width="30" headAlign="center" textAlign="center"></div>
                                <div type="indexcolumn" width="30" headAlign="center" textAlign="center">序号</div>
                                <div field="sname" width="100" headAlign="center" textAlign="left">项目名称</div>
                                <div field="ibiddingtype" width="100" headAlign="center" textAlign="center">招标方式</div>
                                <div field="ownername" width="100" headAlign="center" textAlign="center">项目业主</div>
                                <div field="constname" width="80" headAlign="center" textAlign="left">代建单位</div>
                                <div field="sbidder" width="80" headAlign="center" textAlign="left">中标人</div>
                                <div field="ldbiddate" width="80" headAlign="center" textAlign="center">中标时间</div>
                                <div field="ilimitday" width="80" headAlign="center" textAlign="left">中标工期</br>（天）</div>
                                <div field="dbidamount" width="80" headAlign="center" textAlign="left">中标金额</br>（元）</div>
                                <div field="operation" width="100" headAlign="center" textAlign="center">操作</div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript" src="${JS}/projectlib/UpdateProjectLidHandle.js?${V}"></script>
</body>
</html>