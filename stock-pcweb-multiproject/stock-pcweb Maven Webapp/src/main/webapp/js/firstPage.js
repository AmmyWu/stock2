	var code = GetArgsFromHref(window.location.href,"encryptCode");

		 doLogin(code);



function GetArgsFromHref(sHref,sArgName) 
{ 
	var args = sHref.split("?");
	var retval = "";
	if(args[0] == sHref) /*参数为空*/
	{
		return retval; /*无需做任何处理*/
	}
	var str = args[1];
	args = str.split("&");
	for(var i = 0; i < args.length; i ++)
	{
		str = args[i];
		var arg = str.split("=");
		if(arg.length <= 1) continue;
		if(arg[0] == sArgName) retval = arg[1];
	}
	return retval;
}
function doLogin(code1) {
	
	$.ajax({
		type: "POST",
		url: getContextPath()+"ssoClient/loginBySSO.do", //baseURL
		data: {
			"encryptCode": code1
		},
		dataType: "json",
		success: function (returnMsg) {
			console.log(returnMsg);
			if(!returnMsg.status||!returnMsg.menuJSON){
				loginFailure(returnMsg.msg , code1);
			}
			else{
				loginSuccess(returnMsg);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus + errorThrown);
		}	
	});
	return false;
}

function loginFailure(returnMsg,mi){
	// alert("错误！");
	console.log(returnMsg);
	var str='/stock-pcweb/register.html?code='+returnMsg+"&mi="+mi;
	window.location.href=str;
}

function loginSuccess(returnMsg) {

	
	if ("员工" == returnMsg.user.type) {
		//主菜单
		localStorage.menu = JSON.stringify(returnMsg.menuJSON);

		localStorage.button = returnMsg.btnResourceIds;

		delete returnMsg.menuJSON;
		delete returnMsg.btnResourceIds;
		$.cookie('loginingEmployee', JSON.stringify(returnMsg), {
			path: '/stock-pcweb'
		}); //expires: 7,
		window.location.href = "index-for-login.html";
		return;
	}
	return false;
}