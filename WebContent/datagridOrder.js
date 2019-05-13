
namespace.ns("easyuiOrderSystem.js.datagridOrder");
easyuiOrderSystem.js.datagridOrder = {
	init:function(){
		$("#orderTb").datagrid({
				//工具栏
				toolbar : "#ordderSearch",
				//发送远程请求
				url:"orderServlet",
				//显示分页栏
				pagination:true,
				//设置分页初始页面大小
				pageSize : 10,
				//设置初始化页面大小选择列表。
				pageList:[10,20,30,50],
				fit:true,
				striped:true,
				singleSelect:true,
				fitColumns:true, 
				columns:[[
					{title:"订单号",field:"id",width:"10%",align:"center"},
					{title:"状态",field:"state",width:'20%',align:"center",formatter:function(value,row,index){
						if(value==1){
							return "未接单";
						}else if (value==2) {
							return"已接单";
						}else if (value==3) {
							return "待收货"
						}else if(value==4){
							return "已收货"
						}else{
							return "订单已取消"
						}
					},styler:function(value,row,index){
						if(value==1){
							return "background:	Cyan;color:red";
						}
					}},
					{title:"下单时间",field:"createTime",width:'30%',align:"center",editor:"datetimebox"},
					{title:"备注",field:"comment",width:'21%',align:"center",styler:function(value,row,index){
						if (row.state==1 && row.comment !=null) {
							return "background : MediumAquamarine";
						}
					},editor:"text"},
					{title:"操作",field:"del",width:'18.5%',align:"center",formatter:function(value,row,index){
						if (row.state==1) {
							return "<a href='javascript:void(0)' onclick='getUpdateState(2)' style='text-decoration:none'>接单</a> ";
						}else if (row.state==2) {
							return "<a href='javascript:void(0)' onclick='getUpdateState(3)' style='text-decoration:none'>生产</a> ";
						}else if (row.state==3) {
							return "<a href='javascript:void(0)' style='text-decoration:none'>待收货</a> ";
						}else if (row.state==4) {
							return "<a href='javascript:void(0)' style='text-decoration:none;color:DarkGray'>完成</a>";
						}else{
							return "<spen style='color:red'>"+row.cancelReason+"</span> ";
						}						
					}}
				]],

				//菜单表格展示
				view:detailview,
				detailFormatter: function(rowIndex, rowData){	
					return getMenuByOrder(rowData);
				}
		})

		//拼接订单信息
		function getMenuByOrder(ordersList){
			var codeMenu = [];
			//获取菜品信息
			var menus = ordersList.menuByde || [];
			//计算总金额 和 折扣
			var totalMoneyBy = getTotalMoneyAndPice(menus);
			codeMenu.push('<table class="order-list">');
				for (var i = 0; i < menus.length; i++) {
					var m =menus[i];
					m.amount = m.amount || 1;
					if (i==0) {
						codeMenu.push('<tr >');
							codeMenu.push('<td width="20%">'+m.name+' *'+m.amount+'</td>');
							codeMenu.push('<td rowspan ="'+menus.length+'" class="tdSize"  width="20%">' );
								codeMenu.push('<div>总金额：￥'+totalMoneyBy.totalMoney+'</div>');
								codeMenu.push('<div>应付：￥'+(totalMoneyBy.totalMoney-totalMoneyBy.totaldiscountPrice)+' (-'+totalMoneyBy.totaldiscountPrice+')</div>');
							codeMenu.push('</td>');
							codeMenu.push('<td rowspan="'+menus.length+'" class="tdSize b-r-n">');
								codeMenu.push('<div>'+ordersList.userlis[0].username+'</div>');
								codeMenu.push('<div>'+ordersList.userlis[0].phone+'</div>');
								codeMenu.push('<div>'+ordersList.userlis[0].address+'</div>');
							codeMenu.push('</td>');
						codeMenu.push('</tr>');

					}else{
						codeMenu.push('<tr><td>'+m.name+' *'+m.amount+'</td></tr>')
					}
				}
			codeMenu.push('</table>');
			return codeMenu.join("")
		};

		//总金额
		function getTotalMoneyAndPice(menus){
			 var totalMoney = 0;
			 var totaldiscountPrice=0;
			 for (var i = 0; i < menus.length; i++) {
			 	var mu = menus[i];
			 	mu.amount = mu.amount || 1;
			 	//总金额
			 	totalMoney+=mu.price*mu.amount;
			 	//优惠总金额
			 	mu.discount_price = mu.discount_price || 0;
			 	totaldiscountPrice+=mu.discount_price*mu.amount;
			 }
			 return{
			 	totalMoney:totalMoney,
			 	totaldiscountPrice:totaldiscountPrice
			 };
		};

		//输入框
		$("#orSearch").textbox({ });
	}
}

function datagridOrRefresh(){
	$("#orderTb").datagrid("load",{});
}

//根据状态查询
function getAdminStateQueryOrder(state){
	if (state=="all") {
		$("#orderTb").datagrid("load",{});
	}else{
		$("#orderTb").datagrid("load",{
			mothed:"queryStateOrder",
			state:state,
			userIdOr:"-1"
		})
	}
}

function getHideOd(){
	$('#winCancel').window('close');
}

//修改状态
function getUpdateState(state){
	var row=$("#orderTb").datagrid("getSelected");
	if (row!=null) {
		if (state==1&&row.state==1) {
			$("#winCancel").window({
				title:"取消订单",
				href:'cancelOrder.html',
				width:600,    
			    height:400,    
			    modal:true ,
			})
			
		}else if (state==2) {
			sendRequestUpdataState(state,row.id);
		}else if(state==3){	
			sendRequestUpdataState(state,row.id);
		}
	}else{
		$.messager.alert("警告！","请选择要操作的数据！！")
	}
}

function sendRequestUpdataState (state,roderId){
	var dataState={
		state:state,
		orderId:roderId
	}
	$.ajax({
		url:"/easyuiOrderSystem/orderServlet?mothed=updataStateOrder",
		data:dataState,
		dataType:"text",
		success:function(text){
			$("#orderTb").datagrid("load",{});
		}
	});
}