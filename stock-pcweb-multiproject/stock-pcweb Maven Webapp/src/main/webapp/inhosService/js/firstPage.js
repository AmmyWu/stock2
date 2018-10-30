//这是点击注册的车
function register(){
	window.location.href="./register.html";
}
//点击回首页
function backHome(){
	var headerLogin=document.getElementById('line-blue');
	headerLogin.setAttribute("class","line-moveBack");
	var mainContent=document.getElementById('mainContent');
	mainContent.style.display="block";
	mainContent.setAttribute("class", "homeBack");
	var login=document.getElementById('login');
	login.setAttribute("class", "loginLeave");
	var bd=document.getElementById('body');
	bd.style.overflowX="hidden";
	setTimeout(function(){
		login.style.display="none";
		bd.style.overflowX="auto";
	},500);
}
//这是点击登录的车
function clickLogin(){
	var headerLogin=document.getElementById('line-blue');
	var mainContent=document.getElementById('mainContent');
	var login=document.getElementById('login');
	headerLogin.setAttribute("class","line-move");
	mainContent.setAttribute("class", "homeLeave");
	login.setAttribute("class", "loginBack");
	login.style.display="block";
	var bd=document.getElementById('body');
	bd.style.overflowX="hidden";
	setTimeout(function(){
		mainContent.style.display="none";
		bd.style.overflowX="auto";
	},500);
};
//检测手机号格式是否正确
function check(obj,id){
	var numError=document.getElementById(id);
	var regu = /^[1][0-9][0-9]{9}$/; 
	var re = new RegExp(regu); 
	if(id=="loginCellPhone"){
		if (re.test(obj.value)) {
			numError.innerHTML="";
		} else { 
			numError.innerHTML="请输入正确手机号。";
		} 
	}else{
		if (re.test(obj.value)) {
			numError.innerHTML="";
			return true;
		} else { 
			numError.style.visibility="visible";
			numError.innerHTML="<img src='./images/amazing@1x.png' alt='success'>"+
						       "<div class='telErrTip'>请输入正确的手机号</div>";
			return false;
		} 
	}
}