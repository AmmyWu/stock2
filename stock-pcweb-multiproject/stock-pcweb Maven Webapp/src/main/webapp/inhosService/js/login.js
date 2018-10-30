var LOGININGEMPLOYEE = null;

// dom 就绪后设置 nav bar 和 copyright
$(document).ready(function () {


	$("#loginButton").click(function () {

		$("#loginButton").val("登录中...").css('background-color','#ddd').attr("disabled","true");
		doLogin();
	});


	//	$("#loginCellPhone").blur(function(){
	//		
	//		
	////		checkCellPhoneIntput(this,true);
	//		
	//
	//		isUnExistAccount(this);
	//		
	//	});

	$("#resetPassword").click(function () {

		if (!checkCellPhoneIntput($("#loginCellPhone")))
			return;

		if (isUnExistCellPhone($("#loginCellPhone")))
			return;

		if (!$.string.isNullOrEmpty($(".alert_span").text()))
			return;


		if (confirm("重置后的新密码将以短信方式发您手机，请确认你的手机号是否输入正确！"))
			resetPassword();
	});
});



function isLogin() {

	if ($.cookie("loginingCustomer") == undefined || $.cookie("loginingCustomer") == null) {


		$('#btn-toCenter').remove();
		$('#btn-logout').remove();
		return false;
	}
	if ("客商" == LOGININGCUSTOMER.user.type)
		$('#btn-toCenter').remove();

	$('#btn-login-div').remove();
	$('#link-register').remove();

	LOGININGCUSTOMER = JSON.parse($.cookie("loginingCustomer"));

	return true;

}

function doLogin() {

	//checkCellPhoneIntput($("#loginCellPhone")) || 


	if (!checkAccountIntput($("#loginCellPhone")) || !checkPasswordInput($("#loginPassword")) ||
		!$.string.isNullOrEmpty($(".alert_span").text()))
		return false;

	var type = $("#type").val();
	$.ajax({
		type: "POST",
		url: getContextPath() + "j_spring_security_check", //baseURL
		data: {
			//method : "doLogin",
			account: $("#loginCellPhone").val(),
			password: $("#loginPassword").val(),
			type: type
			//				rememberMe : rememberMe
		},
		dataType: "json",

		success: function (returnMsg) {

			if (returnMsg.status && returnMsg.menuJSON != null) {
				loginSuccess(returnMsg);
			} else {
				(returnMsg);
			}
		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus + errorThrown);
			return false;
		}
	});
}

function checkAccountIntput(input) {

	$(".alert_span").remove();

	$(".alert_span").remove();

	// if($.string.isNullOrEmpty( $(input).val()) || !$.string.isTel(
	// $(input).val()) ){
	if ($(input).val() == "") {
		$(input)
			.parent()
			.after(
				"<span class='alert_span'><font color='red'>帐号不能为空！</font></span>");
		$(input).focus();
		return false;
	}

	return true;
}

function checkPasswordInput(input) {

	$(".alert_span").remove();

	if ($(input).val() == "" || $(input).val().length < 6) {
		$(input)
			.parent()
			.after(
				"<span class='alert_span'><font color='red'>请输入6位及上密码！</font></span>");
		$(input).focus();
		return false;
	}
	return true;

}

function loginSuccess(returnMsg) {

	if ("员工" == returnMsg.user.type) {
		//		returnMsg.menuJSON == null;
		//		alert(returnMsg.menu);
		//主菜单
		localStorage.menu = JSON.stringify(returnMsg.menuJSON);

		localStorage.button = returnMsg.btnResourceIds;
		//		$.cookie('menuJSON',returnMsg.menu,{path: '/cnscecms-admin' });
		//		$.cookie('menuJSON',JSON.stringify("aaabbb"),{  path: '/cnscecms-admin' });

		delete returnMsg.menuJSON;
		delete returnMsg.btnResourceIds;
		$.cookie('loginingEmployee', JSON.stringify(returnMsg), {
			path: '/stock-pcweb'
		}); //expires: 7,
		//$.cookie('loginingEmployee',JSON.stringify(returnMsg),{ path: '/stock-pcweb' }); //expires: 7,
		window.location.href = "index-for-login.html";
		return;
	}

	//	if("嘉宾" == returnMsg.user.type){
	//		
	//		
	//		$.cookie('loginingCustomer', JSON.stringify(returnMsg),{path: '/cnscecms-portalsystem' }); // expires: 7, 
	//		
	//		window.location.href = "./center.html";
	//		return ;
	//	}
	//	
	//	if("客商" == returnMsg.user.type){
	//		$.cookie('loginingCustomer', JSON.stringify(returnMsg),{path:'/cnscecms-portalsystem' }); //expires: 7,
	//		window.location.href = "./signup.html";
	//		return ;
	//	}

	return false;
}

function loginFailure(returnMsg) {
	// if (returnMsg.msg)
	// 	alert(returnMsg.msg);
	// var content = '<div class="alert alert-danger"><a href="#" class="close" data-dismiss="alert">&times;</a>' + '用户名密码错误' + '</div>';
	// $("#myAlert").html(content);
	callAlertModal("用户名密码错误！");

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