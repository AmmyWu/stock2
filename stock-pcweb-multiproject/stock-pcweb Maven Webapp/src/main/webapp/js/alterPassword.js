var authentication ;
$(document).ready(function () {


	$("#confirmButton").click(function () {

		$("#confirmButton").val("修改中").css('background-color','#ddd').attr("disabled","true");
		var flag = checkFormat();
		if (flag) {
			write2database();
		}
	});
});

function checkFormat(){
    password = $("#alterPassword").val();
    confirmPassword = $("#checkPassword").val();
    var reg = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{8,50}$/;//正则表达式
    if (password == ''||confirmPassword == '') {
    	alert("请填写完整信息!")
    	$("#confirmButton").attr("disabled",false);
		$("#confirmButton").val("确定").css('background-color','#7552F5');
        return false;
    }
    if (password.length < 8 || confirmPassword.length < 8) {
        alert('密码长度不能小于8位！');
        $("#confirmButton").attr("disabled",false);
		$("#confirmButton").val("确定").css('background-color','#7552F5');
        return false;
    }
    if(!reg.test(password)){
        alert('密码必须包含数字和字母！请重新输入！')
        $("#confirmButton").attr("disabled",false);
		$("#confirmButton").val("确定").css('background-color','#7552F5');
        return false;
    }
    if (confirmPassword != password) {
        alert('两次密码不一致！请重新输入');
        $("#confirmButton").attr("disabled",false);
		$("#confirmButton").val("确定").css('background-color','#7552F5');
        return false;
    }
    return true;
}
function write2database(){
	getAuthentication();
	$.ajax({
		type: "POST",
		url: getContextPath() + "authentication/updatePassword.do", //baseURL
		data: {
			account : authentication.account,
			password : authentication.password,
			newPassword : $("#alterPassword").val()
 		},
 		dataType: "text",
		success: function (res) {
	        dologin();

	    },
	    error: function () {
	        alert("修改失败!");
	        // $.messager.alert('系统提示','申请失败,请重试！','warning');
	    }
	})
}
function getUrlParam(key) {//用来获取页面url的参数
    // 获取参数
    var url = window.location.search;
    // 正则筛选地址栏
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    // 匹配目标参数
    var result = url.substr(1).match(reg);
    //返回参数值
    return result ? decodeURIComponent(result[2]) : null;
}

function getAuthentication()
{
	// var url = getContextPath() + "authentication/getAuthenticationMap.do?userId=" +getUrlParam(userId);
 	$.ajax({
 		url: getContextPath() +'authentication/getAuthenticationMap.do',
 		type:'POST',
 		data:{
 			userId : getUrlParam("userId")
 		},
 		dataType:'json',
 		async: false,
 		success:function(res){
 			authentication=  res.aaData[0];
 		}
 	});
}
function dologin(){
	// alert(hex_md5($("#alterPassword").val()));
	$.ajax({
		type: "POST",
		url: getContextPath() + "j_spring_security_check", //baseURL
		data: {
			account: authentication.account,
			password: hex_md5($("#alterPassword").val()),
			type: authentication.type
		},
		dataType: "json",

		success: function (returnMsg) {
			loginSuccess(returnMsg);

		},
		error: function (XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus + errorThrown);
			return false;
		}
	});
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