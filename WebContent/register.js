
$(function(){

	//账号
	$("#username").textbox({
		type:"text",
		required:true,
		prompt:"请输入用户名",
		label:'用户名：',
		width:260

	});

	//密码
	$("#password").textbox({
		type:"password",
		required:true,
		prompt:"请输入密码",
		label:'密码：',
		width:260

	});

	//电话
	$("#phone").numberbox({
		validType:'length[11,11]',
		required:true,
		prompt:"请输入联系电话",
		label:'联系电话：',
		width:260
	});

	//地址
	$("#address").textbox({
		type:"text",
		required:true,
		prompt:"请输入地址",
		label:'地址：',
		width:260

	});

	//金额
	$("#money").numberbox({
		required:true,
		prompt:"账户金额",
		label:'金额：',
		width:260

	});

	$("#register").click(function(){

			var userArray = {};
			debugger;
			//获取用户名   $("input[name=username]").val();
			var username = 	$("#username").textbox("getValue")
			userArray.username = username;

			//获取用户密码   $("input[name=password]").val()
			var password = $("#password").textbox("getValue");
			userArray.password = password;

			//用户电话   //$("input[name=phone]").val();
			var phone =$("#phone").numberbox("getValue") 
			userArray.phone=phone;

			//地址  //$("input[name=address]").val();
			var address =$("#address").textbox("getValue") 
			userArray.address=address;

			//金额   //$("input[name=money]").val();
			var money =$("#money").numberbox("getValue")

			userArray.money=money;

			var dataUser={
				list:JSON.stringify(userArray)
			}

			$.ajax({
				url:"userRegister?method=userAdd",
				type:"POST",
				async:true,
				data:dataUser,
				dataType:"text",
				success:function(text){
					if (text=='注册成功') {
						alert(text);
						location.href="index.html";
					}else{
						alert(text);
					}	
				}
			});		
	});

	//返回登录界面
	$("#returnPage").click(function(){
		location.href="index.html";
	})
});
