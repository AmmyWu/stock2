//@ sourceURL=setToolbar.js

//button控制js，根据用户登陆的角色控制button是否显示
$(document).ready(function(){
	var roleResourceIds = localStorage.button;
		if (roleResourceIds==undefined || roleResourceIds=="")return;
		 $.each($("button[id ^='BTN_']"), function(i,a) {
			 if(roleResourceIds.indexOf($(this).attr('id')) == -1)
//					$(this).attr('class',"l-btn l-btn-plain l-btn-disabled l-btn-plain-disabled");
				 $(this).attr('style',"display:none");
		   }); 
	// };
 
});

