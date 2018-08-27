search.parse();
var str2 = [];
/**
 * 页面加载。
 */
function setData(data){
	$.ajax({
		url : CTX+"/orgs/gobs",
		data : {sid : data.sid},
		success : function(result){				
			//封装附件List
			for(var i =0;i<result.result.attch2.length;i++){
				str2.push({sid:result.result.attch2[i].sid,sname:result.result.attch2[i].sname,spath:result.result.attch2[i].spath,sdesc:result.result.attch2[i].sdesc,sdictionarieid:result.result.attch2[i].sdictionarieid});
			}
			Upload2.init({
    		    callback: function (resultArr,delArr) {
    		    	sumbit(resultArr,delArr);
    		    },
		        url: "/orgs/gorgqua",
		        data: {},
		        initData: str2
    		});
		}
	});
}
