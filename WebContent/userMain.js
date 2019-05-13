$(function(){
	//获取用户名
	var addrs=decodeURIComponent(location.href);
	var arrs=addrs.split("?");
	var userIphone=arrs[1].split("=");
	$("#userName").html(" 尊敬的 "+userIphone[1]+" 欢迎您来到羊村小卖铺");

	$("#userTab").tabs("add",{

		title:"首页",
		content:"<div style='background-image:url(dataImage/8989.jpeg)' class='w-h-100'></div>",
		
	})

	//加载菜品信息
	$("#menuAll").click(function(){
		if (!$("#userTab").tabs('exists',"菜品信息")) {
			$("#userTab").tabs("add",{
				title:"菜品信息",
				closable:true, 
				href:"datagrid_userMenu.html",
				onLoad:function(){
					easyuiOrderSystem.js.datagrid_userMenu.init();
				}
			})
		}else{
			$("#userTab").tabs("select","菜品信息");
		}		
	});

	//个人购物车
	$("#myShoppingAll").click(function(){
		if (!$("#userTab").tabs('exists',"我的购物车")) {
			$("#userTab").tabs("add",{
				title:"我的购物车",
				closable:true, 
				href:"datagrid_userShopping.html",
				onLoad:function(){
					easyuiOrderSystem.js.datagrid_userShopping.init();
				}
			})
		}else{
			$("#userTab").tabs("select","我的购物车");
		}
	})

	//个人订单
	$("#myOrderAll").click(function(){
		if (!$("#userTab").tabs('exists',"我的订单")){
			$("#userTab").tabs("add",{
				title:"我的订单",
				closable:true, 
				href:"datagrid_userOrder.html",
				onLoad:function(){
					easyuiOrderSystem.js.datagrid_userOrder.init();
				}
			})
		}else{
			$("#userTab").tabs("select","我的订单");
		}	
	})

	//个人订单
	$("#toMoney").click(function(){
		if (!$("#userTab").tabs('exists',"个人充值")){
			$("#userTab").tabs("add",{
				title:"个人充值",
				closable:true, 
				href:"userRechargeMoney.html",
				onLoad:function(){
					easyuiOrderSystem.js.userRechargeMoney.init();
				}
			})
		}else{
			$("#userTab").tabs("select","个人充值");
		}		
	})
});

//获取用户Id
function getUserId(){
	var userId=-1;
	var addrs=decodeURIComponent(location.href);
	var arrs=addrs.split("?");
	var userIphone=arrs[1].split("=");
	$.ajax({
		url:"userRegister?usm="+userIphone[1],
		type:"GET",
		dateType:"text",
		async:false,
		success:function(text){
			userId = text;
		}
	});
	return userId;
}