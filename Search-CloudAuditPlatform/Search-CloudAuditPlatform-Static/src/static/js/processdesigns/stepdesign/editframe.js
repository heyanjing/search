search.parse();
var treedatagrid_u = search.get("treedatagrid_u");
var treedatagrid_f = search.get("treedatagrid_f");
var sname = search.get("sname");
var isupportback = search.get("isupportback");
var isupportopinion = search.get("isupportopinion");
var index;

var uname = search.get("uname");
var fname = search.get("fname");

var gStepId = null, gStepName = null;

var ustate = false;
var fstate = false;

function pageload(orgid,stepname,isupportproject,sfromorgid,stepid,stepExtend){
	sname.setValue(stepname);
	isupportback.loadData(Globle.constant.isupportback);
	isupportopinion.loadData(Globle.constant.isupportback);
	treedatagrid_u.url = CTX+"/stepdesign/gtcsra";
	treedatagrid_u.loaded = function(){
		ustate = true;
		if(ustate && fstate){
			dataload(stepid,stepExtend);
		}
	}
	treedatagrid_u.load({"orgid":orgid});
	treedatagrid_f.url = CTX+"/stepdesign/gtcsraf";
	treedatagrid_f.loaded = function(){
		fstate = true;
		if(ustate && fstate){
			dataload(stepid,stepExtend);
		}
	}
	treedatagrid_f.load({"isupportproject":isupportproject,"sfromorgid":sfromorgid,"sorgid":orgid});
}

function initPage(menuIndex,stepId,stepName,stepType,stepExtend,exdata){
	pageload(exdata.orgid,stepName,exdata.isupportproject,exdata.sfromorgid,stepId,stepExtend);
	if(stepType != Globle.constant.stepType[1].value){
		isupportback.setEnabled(false);
		isupportopinion.setEnabled(false);
		if(stepType == Globle.constant.stepType[0].value || stepType == Globle.constant.stepType[3].value){
			isupportopinion.setValue(Globle.constant.isupportback[1].value);
		}else{
			isupportopinion.setValue(Globle.constant.isupportback[0].value);
		}
	}
	loadtab(menuIndex);
	gStepId = stepId, gStepName = stepName;
}

function dataload(stepid,stepExtend){
	if($.isEmptyObject(stepExtend)){
		$.post({
			url:CTX + "/stepdesign/gtsdbsid",
			data:{"stepid":stepid},
			success:function(e){
				if(e.status){
					if(e.result.status){
						isupportback.setValue(e.result.isback);
						isupportopinion.setValue(e.result.isopin);
						treedatagrid_u.setValue(e.result.uid);
						treedatagrid_f.setValue(e.result.fid);
					}
				}else{
					top.search.warn({content:"系统错误!"});
				}
			}
		});
	}else{
		isupportback.setValue(stepExtend.isupportback);
		isupportopinion.setValue(stepExtend.isupportopinion);
		treedatagrid_u.setValue(stepExtend.soperatorid_u);
		treedatagrid_f.setValue(stepExtend.soperatorid_f);
	}
}

function closeClick(){
	var name = sname.getValue();
	var isback = isupportback.getValue();
	var isopin = isupportopinion.getValue();
	var uids = treedatagrid_u.getValue();
	var fids = treedatagrid_f.getValue();
	gStepName = name;
	window.closeWindow({action:"save",stepExtend : {"isupportback" : isback, "isupportopinion" : isopin, "soperatorid_u" : uids, "soperatorid_f" : fids}, stepName : gStepName});
}

$('#table-construction').on('click', 'li', function () {
    var $li = $(this), index = $li.index(), $constructionBd = $('#table-construction-bd').children().eq(index);
    $li.addClass('table-active').siblings().removeClass('table-active');
    $constructionBd.addClass('table-thisclass').siblings().removeClass('table-thisclass');
});

function loadtab(index){
	var $li = $("#table-construction").children().eq(index-1), $constructionBd = $('#table-construction-bd').children().eq(index-1);
	$li.addClass('table-active').siblings().removeClass('table-active');
    $constructionBd.addClass('table-thisclass').siblings().removeClass('table-thisclass');
}

function treedatagriddrawcell_u(e){
	
}
function treedatagriddrawcell_f(e){
	
}
function toSearch(tab){
	if(tab == 1){
		var un = uname.getValue();
		var filters = [new DatagridFilter("sname", un, false)];
		treedatagrid_u.doFilter(filters,true);
	}else{
		var fn = fname.getValue();
		var filters = [new DatagridFilter("sname", fn, false)];
		treedatagrid_f.doFilter(filters,true);
	}
}