function setData(data) {
	var str="";
	$.each(data,function (i, v) {
        str+='<a href="javascript:void(0)" data-orgid="'+v.orgid+'">'+v.orgname+'</a>';
    });
	$('#container').append(str);
}
$('#container').on('click','a',function () {
	var orgid = $(this).data('orgid');
	console.log("机构id为:",orgid);
	top.window.location.href=CTX+'/index/switchOrg?orgId='+orgid;
});