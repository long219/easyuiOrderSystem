$(function(){

	//获取用户名
	var addrs=decodeURIComponent(location.href);
	var arrs=addrs.split("?");
	var userIphone=arrs[1].split("=");
	$("#uName").html(" 尊敬的 "+userIphone[1]+" 欢迎来到羊村小卖铺");

	$("#tab").tabs("add",{
		title:"首页",
		content:"<div style='background-image:url(dataImage/286223.jpg)' class='w-h-100'></div>"
	})

	//加载菜品信息
	$("#menuAll").click(function(){
		if (!$("#tab").tabs('exists',"菜品信息")) {
			$("#tab").tabs("add",{
				title:"菜品信息",
				closable:true, 
				href:"datagridMenu.html",
				onLoad:function(){
					Info();
				}
			})
		}else{
			$("#tab").tabs("select","菜品信息");
		}		
	});

	$("#orderAll").click(function(){

		if (!$("#tab").tabs('exists',"订单信息")){
			$("#tab").tabs("add",{
				title:"订单信息",
				closable:true, 
				href:"datagridOrder.html",
				onLoad:function(){
					easyuiOrderSystem.js.datagridOrder.init();
				}
			})
		}else{
			$("#tab").tabs("select","订单信息");
		}	
	})
});
