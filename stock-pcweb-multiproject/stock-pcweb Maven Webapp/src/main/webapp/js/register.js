var LOGININGEMPLOYEE = null;
var flag;
var mi;
// dom 就绪后设置 nav bar 和 copyright
$(document).ready(function () {

    var employid = GetArgsFromHref(window.location.href,"code");
	mi=GetArgsFromHref(window.location.href,"mi");
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
	$("#employeeId").val(employid);

//登录按钮
	$("#loginButton").click(function () {

		$("#loginButton").val("注册中...").css('background-color','#ddd').attr("disabled","true");
		var success = register();
		if(!success){
			$("#loginButton").attr("disabled",false);
			$("#loginButton").val("注册").css('background-color','#7552F5');
		}
	});


//重置方法
	 $("#resetPassword").click(function () {

		if (!checkCellPhoneIntput($("#loginCellPhone")))
			return;

		if (isUnExistCellPhone($("#loginCellPhone")))
			return;

		if (!$.string.isNullOrEmpty($(".alert_span").text()))
			return;
		checkStatus();
		if(!flag)
			return;
		if (confirm("重置后的新密码将以短信方式发您手机，请确认你的手机号是否输入正确！"))
			resetPassword();
	});
});


function register(){
	var username=$("#loginName").val();
	var cell=$('#loginCellPhone').val();
	if(username == "" || cell == "" ){
		alert("请填写必要信息！");
		return false;
	}

    $.ajax({
        type: "POST",
        url: getContextPath() + "ssoClient/registerEmployee.do", //baseURL
        data: {
            employeeName: $("#loginName").val(),
            employeeCellPhone: $('#loginCellPhone').val(),
            employeeCode:  $("#employeeId").val()
        },
        dataType: "json",
        success: function (returnMsg) {
        	var str ="/stock-pcweb/firstPage.html?encryptCode="+mi;
            window.location.href=str;
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(textStatus + errorThrown);
        }
    });

	return false;

}





function callAlertModal(info) {
    $('#alertContent').html(info);
    $('#alertModal').modal('show');
}


function resetPassword() {
	//todo  发送重置密码的短信

	var result = $.util.requestAjaxData(BASE_URL + '/portal/resetPassword.do', {
		'cellPhone': $("#loginCellPhone").val(),
		"type": $('input[name="type"]:checked').val()
	});

	$(".alert_span").remove();
	$("#resetPassword").after("<span class='alert_span'><font color='red'>" + result + "</font></span>");
	return false;

}

