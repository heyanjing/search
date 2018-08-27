search.parse();

// 父页面传值
function setData(data) {
	$.post(CTX + "/user/findMapUsersBySid", {sid : data.sid}, function(result) {
		if (result.status) {
			var options = {
				url : "/user/findListMapDictionariesByItype",
				data : {type : 2},
				initData : result.result.aptitudes
			}
			Upload2.init(options);
		} else {
			top.search.error({ content : "系统错误！" });
		}
	})
}
