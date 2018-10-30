function back(){
	window.location.href="http://localhost:8080/stock-pcweb/firstPage.html";
}
document.getElementById('register-yzm').onclick=function(){
	var second=60;
	//document.getElementById('register-yzm').setAttribute("id","stophere");
	document.getElementById('register-yzm').innerHTML=""+second+"后重新发送";
	document.getElementById('register-yzm').style.background="gray";
	setInterval(function(){
		if(second==60){
			second--;
			document.getElementById('register-yzm').setAttribute("id","stophere");
			document.getElementById('stophere').innerHTML=""+second+"后重新发送";
		}else if(second==0){
			document.getElementById('stophere').innerHTML=""+second+"后重新发送";
			document.getElementById('stophere').setAttribute("id","register-yzm");
			document.getElementById('register-yzm').style.background="#4A90E2";
			document.getElementById('register-yzm').innerHTML="发送验证码";
			second=60;
			clearInterval(1);
		}else{
			second--;
			document.getElementById('stophere').innerHTML=""+second+"后重新发送";
		}
	},1000);
}

document.getElementById('nextStep-btn').onclick=function(){
	var firstImage=document.getElementById('first');
	var secondImage=document.getElementById('second');
	var registerMain=document.getElementById('register-main');
	secondImage.setAttribute("src","./images/icon_2_selected  @1x.png" );
	firstImage.setAttribute("src","./images/icon_1_default @1x.png" );
	registerMain.innerHTML="<input   placeholder='设置密码' class='manyinput' type='password'/>"+
							"<input style='margin-left:4.5rem'  placeholder='再次输入密码' class='manyinput' type='password'/>"+
							"<div class='error-tips'>"+
							"<img src='./images/amazing@1x.png' alt='success'>"+
							"<div style='height:24px;line-height:24px;font-family: MicrosoftYaHei;font-size: 12px;color: #EF5657;word-break:keep-all;white-space:nowrap;margin-top:-24px;margin-left:4rem'>两次输入的密码不一致</div>"+
						    "</div>"+
							"<div style='margin-left:4.5rem' class='nextStep-btn' onclick='lastStep()'>下一步</div>"
	
}

function checkEmail(obj){
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
	if(obj.value === ""){ //输入不能为空
		console.log("输入为空")
	return false;
	}else if(!reg.test(obj.value)){ //正则验证不通过，格式不对
		console.log("验证不通过!");
	return false;
	}else{
		console.log("通过！");
	return true;
	}
}

function lastStep(){
	var secondImage=document.getElementById('second');
	var thirdImage=document.getElementById('third');
	var registerMain=document.getElementById('register-main');
	secondImage.setAttribute("src","./images/icon_2@1x.png" );
	thirdImage.setAttribute("src","./images/icon_3_selected @1x.png");
	registerMain.innerHTML="<img src='./images/zccg@1x.png' alt='注册成功'>"+
							"<p style='margin-top:30px;font-family: MicrosoftYaHei;font-size: 24px;color: #666666;'>密码重置成功！</p>"+
							"<span style='font-family: MicrosoftYaHei;font-size: 12px;color: #666666;'>5秒后将自动登录，若跳转不成功，请点击</span><span style='font-family: MicrosoftYaHei;font-size: 12px;color: #666666;'><a style='cursor:pointer'>登录</a>。</span>";
}