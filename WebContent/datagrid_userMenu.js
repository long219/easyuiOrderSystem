
namespace.ns("easyuiOrderSystem.js.datagrid_userMenu");

easyuiOrderSystem.js.datagrid_userMenu={

		init:function(){
			$("#userBg").datagrid({
				toolbar:"#toolDiv",
				fit:true,
				//?mothed=userMenuQuery
				url:"menuViewServlet",
				method:'get',
				//如果为true，则显示一个行号列。
				rownumbers: true,
				//单选
				//singleSelect:true,
				ctrlSelect:true,
				pageSize:10,
				pageList:[5,6,7,8,10],
				//序号
				striped:true,
				//分页工具栏
				pagination:true,

				//义是否允许多列排序
				//multiSort:true,

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
						width:'15%',
						align:"center",
						sortable:true,
						order:'desc'
					},
					{
						title:"折扣",
						field:"discount_price",
						width:'12.8%',
						align:"center"
					},
					{
						title : "上架时间",
						field : "createTime",
						width : '22.5%',
						align:"center"
					},
					{
						title:"销量",
						field:"sales_volume",
						width:'12.8%',
						align:"center",
						sortable:true,
						order:'desc'
					},
					{
						title:"数量",
						field:"amountSelect",
						width:'12.8%',
						formatter:function(value,row,index){
							return "<input id='"+row.id+"'>";							
						}
					}
				]],

				//数据加载完时
				onLoadSuccess:function(data){
					for (var i = 0; i < data.rows.length; i++) {
						$("#"+data.rows[i].id+"").numberspinner({    
						    min: 1,    
						    max: 10, 
						    value:1,   
						    editable: false   
						}); 
					}	
				}
				
			});
			
			//输入框
			$('#shopp').textbox({});
			//debugger;
			//搜索
			$("#shoppingbtn").on('click',function(){
				//获取输入框中的name
				var name=$('#shopp').textbox('getValue');
				//获取下拉框中的值
				var isTop=null;
				//获取下拉框中的  (状态)
				var state=null;
				getMenuNameQuery(name,isTop,state);
			})	
		}
}

//刷新
function userMRefirsh(){
	$("#userBg").datagrid("load",{});
}

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


//添加购物车
function addShoppingCart (){
	var userID=getUserId();
	//获取返回所有被选中的行，当没有记录被选中的时候将返回一个空数组。
	var rowsSelect=$('#userBg').datagrid('getSelections');
	if (rowsSelect.length==0) {
		$.messager.alert('警告','请选择要添加购物车的菜品！！');  
		return;
	}
	
	for (var i = 0; i < rowsSelect.length; i++) {
		var amount= $('#'+rowsSelect[i].id+'').numberspinner('getValue');
		rowsSelect[i].amount = amount;
		//获取用户ID
		rowsSelect[i].userID=userID;
	};

	var dateShopping = {
		//将JSON数组转为字符串
		list:JSON.stringify(rowsSelect)
	}
	$.ajax({
		url:"menuViewServlet?mothed=userAddShoppingTrolley",
		type:"POST",
		async:true,
		data:dateShopping,
		dataType:"text",
		success:function(text){
			$.messager.alert('提示',''+text+'！！');
			$("#userBg").datagrid("load");
		}	
	});
}


//根据菜品名查询
function getMenuNameQuery(searchName,isTop,state){

	$("#userBg").datagrid('load',{
			name : searchName,
			isTop : isTop,
			menuState : state
	})
}

$(function(){
	easyuiOrderSystem.js.datagrid_userMenu.init();
})