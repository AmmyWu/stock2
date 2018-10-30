function back(){
	window.location.href="http://localhost:8080/stock-pcweb/login.html";
}

function Store(){}
Store.prototype.yzm=null;
(function(){
	var second=60;
	var  register=document.getElementById('register-yzm');
	register.addEventListener('click', yzmClick, false);
	var telPhone=document.getElementById('telPhone');
	var telTips=document.getElementById('telTips');
	function yzmClick(){
			if(check(telPhone,"telTips")){
				var tel=document.getElementById('telPhone').value;
				register.removeEventListener('click', yzmClick, false);
				$.ajax({
					url:"http://localhost:8080/stockWebappBase/employee/sendValidateCode.do",
					type:"POST",
					dataType: 'text',
				    crossDomain: true,
					contentType:"application/x-www-form-urlencoded; charset=UTF-8",
					data:{
						cellPhone:tel
					},
					success:function(data){
						Store.prototype.yzm=data;
					},
					error:function(err){
						console.log("错误");
					}
				});
				register.style.background="gray";
				register.innerHTML=""+second+"后重新发送";
				var init=setInterval(function(){
					if(second==0){
						register.innerHTML=""+second+"后重新发送";
						//document.getElementById('stophere').setAttribute("id","register-yzm");
						register.style.background="#4A90E2";
						register.innerHTML="发送验证码";
						clearInterval(init);
						second=60;
						register.addEventListener('click', yzmClick, false);
					}else{
						second--;
						register.innerHTML=""+second+"后重新发送";
					}
				},1000);
			}else{
				telTips.style.visibility="visible";
				telTips.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
							       "<div class='telErrTip'>请输入正确的手机号</div>";
			}
		}
})();


document.getElementById('nextStep-btn').onclick=function(){
	var anotherTips=document.getElementById('another-tips');
	var store=new Store();
	var tel=document.getElementById('telPhone');
	var num=document.getElementById('yzm').value;
	var phoneNum=tel.value;
	if(check(tel,"telTips")){
		if(num==""){
			anotherTips.style.visibility="visible";
			anotherTips.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
						          "<div class='yzmErrTip'>验证码为空</div>";
		}else if(store.yzm!=num){
			anotherTips.style.visibility="visible";
			anotherTips.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
						          "<div class='yzmErrTip'>验证码错误</div>";
		}else{
			checkYZM(phoneNum);
		}
	}else{
		
	}
};
//检查邮箱格式
function checkEmail(obj){
	var emailErr=document.getElementById('emailErr');
    var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$"); //正则表达式
	if(obj.value === ""){ //输入不能为空
		emailErr.style.visibility="visible";
		emailErr.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
						   "<div class='yzmErrTip'>邮箱输入为空</div>";
	return false;
	}else if(!reg.test(obj.value)){ //正则验证不通过，格式不对
		emailErr.style.visibility="visible";
		emailErr.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
						   "<div class='yzmErrTip'>邮箱格式错误</div>";
	return false;
	}else{
	return true;
	}
}
//点击第二个下一步
function lastStep(phoneNum){
	var lxrName=document.getElementById('lxrName').value;
	var email=document.getElementById('email').value;
	var pwd=document.getElementById('pwd').value;
	var pwd2=document.getElementById('pwd2').value;
	var companyName=document.getElementById('companyName').value;
	var anotherPwd=document.getElementById('anotherPwd');
	if(pwd==pwd2){
		$.ajax({
			url :"http://localhost:8080/stockWebappBase/customer/insertNewCustomer.do",
			contentType:"application/x-www-form-urlencoded; charset=UTF-8",
			type:"POST",
			dateType:"json",
			data:{
				companyName:companyName,
				customerName:lxrName,
				cellPhone:phoneNum,
				email:email,
				password:pwd
			},
			success:function(data){
				gotoLogin();
			},
			error:function(err){
				console.log(err);
				alert("网络错误");
			}
		});
	}else{
		anotherPwd.style.visibility="visible";
		anotherPwd.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
        					 "<div class='yzmErrTip'>密码不一致</div>";
	}
}

//验证码正确后跳转
function checkYZM(phoneNum){
	var firstImage=document.getElementById('first');
	var secondImage=document.getElementById('second');
	var registerMain=document.getElementById('register-main');
		secondImage.setAttribute("src","./images/icon_2_selected  @1x.png" );
		firstImage.setAttribute("src","./images/icon_1_default @1x.png" );
		registerMain.innerHTML="<input id='companyName' style='width:36rem;display:inline-block;'  placeholder='公司名称' class='pwd-zh' type='text'/>"+
								"<input id='lxrName'  placeholder='联系人姓名'  class='manyinput' type='text'/>"+
								"<input id='email' style='margin-left:4.5rem'  onblur='checkEmail(this);'  placeholder='邮箱' class='manyinput' type='text'/>"+
								"<div class='error-tips' id='emailErr'>"+
								"<img src='./images/amazing@1x.png' alt='success'>"+
								"<div style='height:24px;line-height:24px;font-family: MicrosoftYaHei;font-size: 12px;color: #EF5657;word-break:keep-all;white-space:nowrap;margin-top:-24px;margin-left:4rem'>请输入正确的邮箱</div>"+
							    "</div>"+
								"<input id='pwd'  placeholder='设置密码' class='manyinput' type='password'/>"+
								"<input id='pwd2' style='margin-left:4.5rem'  placeholder='重复密码' class='manyinput' type='password'/>"+
								"<div class='error-tips' id='anotherPwd'>"+
								"<img src='./images/amazing@1x.png' alt='success'>"+
								"<div style='height:24px;line-height:24px;font-family: MicrosoftYaHei;font-size: 12px;color: #EF5657;word-break:keep-all;white-space:nowrap;margin-top:-24px;margin-left:4rem'>密码不一致</div>"+
							    "</div>"+
								"<div style='margin-left:4.5rem' class='nextStep-btn' onclick='lastStep("+phoneNum+")'>下一步</div>";
}


//跳转至登录界面
function gotoLogin(){
	var secondImage=document.getElementById('second');
	var thirdImage=document.getElementById('third');
	var registerMain=document.getElementById('register-main');
	secondImage.setAttribute("src","./images/icon_2@1x.png" );
	thirdImage.setAttribute("src","./images/icon_3_selected @1x.png");
	registerMain.innerHTML="<img src='./images/zccg@1x.png' alt='注册成功'>"+
	"<p style='margin-top:30px;font-family: MicrosoftYaHei;font-size: 24px;color: #666666;'>恭喜您，注册成功！</p>"+
	"<span style='font-family: MicrosoftYaHei;font-size: 12px;color: #666666;'>注册成功,请点击</span><span style='font-family: MicrosoftYaHei;font-size: 12px;color: #666666;'><a onclick='clickLogin()' style='cursor:pointer'>登录</a>。</span>";
	
}