namespace.ns("easyuiOrderSystem.js.datagrid_userShopping");
easyuiOrderSystem.js.datagrid_userShopping={
	init:function(){
		//获取用户ID
		var userId=getUserId();
		$("#userShoppingBg").datagrid({

				toolbar:"#toolShopping",
				fit:true,
				url:"menuViewServlet?mothed=userShippingTrolleyQuery&userId="+userId,
				//如果为true，则显示一个行号列。
				rownumbers: true,
				//单选
				//singleSelect:true,
				ctrlSelect:true,
				pageSize:10,
				pageList:[10,12,14,16,20],
				//序号
				striped:true,
				//分页工具栏
				pagination:true,
				columns:[[
					{
						title:"选购",
						field:"cd",
						width:'10%',
						checkbox:true
					},
					
					{
						title:"菜名",
						field:"name",
						width:'20%',
						align:"center"
					},
					{
						title:"价格",
						field:"price",
						width:'18%',
						align:"center"
					},
					{
						title:"折扣",
						field:"discount",
						width:'18%',
						align:"center"
					},
					{
						title: "上架时间",
						field: "createTime",
						width: '29.5%',
						align:"center"
					},
					{
						title:"数量",
						field:"amounts",
						width:'13.8%',
						formatter:function(value,row,index){
							//debugger;
							return "<input id='sp"+row.id+"'>";							
						}
					}
				]],

				//数据加载完时
				onLoadSuccess:function(data){
					
					for (var i = 0; i < data.rows.length; i++) {
						$("#sp"+data.rows[i].id+"").numberspinner({    
						    min: 1,    
						    max: 10, 
						    width:"100%",
						  	value:data.rows[i].amount,   
						    editable: false   
						}); 
					}	
				}
		});
		//输入框
		$('#shopingM').textbox({ });
	}
}

//添加订单
function addUserOrder(){
	debugger;
	var userID=getUserId();
	//获取返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
	var rowsSelect=$('#userShoppingBg').datagrid('getSelections');
	if (rowsSelect.length==0) {
		$.messager.alert('警告','请选择要添加下单的菜品！！');  
		return;
	}
	
	for (var i = 0; i < rowsSelect.length; i++) {
		var amount= $('#sp'+rowsSelect[i].id+'').numberspinner('getValue');
		rowsSelect[i].amount = amount;
		//获取用户ID
		rowsSelect[i].userID=userID;
	};

	var dateOrder = {
		//将JSON数组转为字符串
		listOrder:JSON.stringify(rowsSelect)
	}
	$.ajax({
		url:"orderServlet?mothed=addOrder",
		type:"POST",
		async:true,
		data:dateOrder,
		dataType:"text",
		success:function(text){
			$.messager.alert('提示',''+text+'！！');
			$("#userShoppingBg").datagrid("load");
		}	
	});
}

//购物车删除
function shoppingDelete(){
	var rowsSelect=$('#userShoppingBg').datagrid('getSelections');
	if (rowsSelect.length!=0) {
		var dateOrderDelete = {
			//将JSON数组转为字符串
			listOrderDelete:JSON.stringify(rowsSelect)
		}
		$.ajax({
			url:"orderServlet?mothed=shoppingDelete",
			type:"POST",
			data:dateOrderDelete,
			success:function(text){
				$.messager.alert('提示',''+text+'！！');
				$("#userShoppingBg").datagrid("load");
			}
		})
	}else{
		$.messager.alert('警告','请选择要删除的购物车信息！！'); 
		return; 
	}
}


//刷新
function shoppingRefresh(){
	$("#userShoppingBg").datagrid("load");
}