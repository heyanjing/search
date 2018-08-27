var gStepId = null, gStepName = null;

function initPage(menuIndex,stepId,stepName,stepType,stepExtend,customParams){
	gStepId = stepId, gStepName = stepName;
	if (!gStepId) gStepId = new Date().getTime();
	
	gStepName = "弹框";
}

function closeClick(){
	//alert(2);
	window.closeWindow({action:"save",stepExtend : {backword : true, authority : [{a : 1, b : 2}]}, stepName : gStepName});
}