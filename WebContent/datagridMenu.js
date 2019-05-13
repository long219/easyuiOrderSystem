
function Info(){
	
		$("#dg").datagrid({
			toolbar : "#tooDiv",
			fit:true,
			url:"menuViewServlet",
			//如果为true，则显示一个行号列。
			rownumbers: true,
			//multiSort:true,
			//单选
			singleSelect:true,

			pageSize:10,
			pageList:[5,6,7,8,10],
			//序号
			striped:true,

			//分页工具栏
			pagination:true,

			columns:[[
				{
					title:"选购",
					field:"checkeds",
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
					width:'12%',
					align:"center"
				},
				{
					title:"折扣",
					field:"discount_price",
					width:'12.8%',
					align:"center"
				},
				{
					title:"是否置顶",
					field:"isTop",
					width:'16%',
					align:"center",
					resizable : false,
					formatter:function(value,row,index){
						if (value=="Y") {
							return '是';
						} else {
							return '否';
						}
					},
					styler: function(value,row,index){
						if (value=="N") {
							return "color:red";
						}
					}
				},
				{
					title : "状态",
					field : "state",
					width : '16%',
					align:"center",
					formatter : function(value, row, index) {
						if (value == 1) {
							return "上架";
						} else if (value == 2) {
							return "<b>下架</b>";
						} else {
							return "<b>删除</b>";
						}
					},
					styler : function(value, row, index) {
						if (value == 2) {
							return 'color:red ;background:#00bcd4';
						} else if (value == 0) {
							return 'color:red';
						}
					}
				},
				{
					title : "上架时间",
					field : "createTime",
					width : '22.5%',
					align:"center"
					
				}
			]],

			//在用户双击一个单元格的时候触发。 
			onDblClickCell:function(index , field , value){
				debugger;
				if (field=="name") {
					//将选中的值 映射到输入框中
					$('#te').textbox('setValue',value);
				}else if (field=="isTop") {
					//将选中的是否置顶的值 映射到下拉框中
					$('#isTop').combobox('setValue',value);									
				}else if (field=="state") {
					//状态（将值映射下拉框中）
					$('#isTop').combobox('setValue',value);				
				}else{
					return false;
				}
			}
		})

	//输入框
	$('#te').textbox({ });

	//下拉框
	$('#isTop').combobox({    
	    valueField:'value',    
	    textField:'text',
	    editable:false,
	    data:[
	    	{value:"",text:"请选择"},
	   		{value:"Y",text:"是"},
	   		{value:"N",text:"否"}
	    ] 
	}); 

	//下拉框 (状态)
	$('#state').combobox({    
	    valueField:'value',    
	    textField:'text',
	     editable:false,
	    data:[
	    	{value:"",text:"请选择"},
	   		{value:"1",text:"上架"},
	   		{value:"2",text:"下架"},
	   		{value:"0",text:"删除"}
	    ] 
	}); 

	//搜索
	$("#menuBtnSearch").on('click',function(){
		//获取输入框中的name
		var name=$('#te').textbox('getValue');
		//获取下拉框中的值
		var isTop=$('#isTop').combobox('getValue');
		//获取下拉框中的  (状态)
		var state=$('#state').combobox('getValue');
		getMenuName(name,isTop,state);
	})	
};
	//隐藏window窗口
	function getHide(){
		$(".divAdd").window('destroy');
	}

	//增加时将输入框中的数据清空
	function getInitializeEmpty(){
		//显示
		$("#dg").append('<div class="divAdd" ></div>');
		$(".divAdd").window({
			title:"增加菜品",
			width: 500,
    		height: 300,
			href:"datagridMenuUpdate.html",
			modal:true ,  
			onLoad:function(){
				//菜名
				$("#menuName").textbox({value:""});
				//价格
				$("#moneyPrice").textbox({value:""});
				//折扣
				$("#discountPrice").textbox({value:""});
				getMenuMessage();						
			},
			onClose:function(){
				getHide();
			}
		})	
	}

	//刷新
	function datagridMenuRefresh(){
		$("#dg").datagrid("load",{})
	}

	//获取菜品信息
	function getMenuMessage(){	
		var menuSave = {};
		//菜名
		var name = $("#menuName").textbox("getValue");
		menuSave.name = name;		

		//价格
		var price = $("#moneyPrice").textbox("getValue");
		menuSave.price = price;

		//折扣
		var discount_price = $("#discountPrice").textbox("getValue");
		menuSave.discount_price = discount_price;

		//是否置顶
		var isTop = $(".divAdd input[name=isTop]:checked").val();
		menuSave.isTop = isTop;

		//状态
		var state =$(".divAdd #state").val();
		menuSave.state = state;

		//获取菜品ID
		var id=$(".divAdd input[name=ID]").attr("id");
		menuSave.id= id;	
		return menuSave;
	}
	
	//菜品增加
	function getMenuAdd(){
		var datalist = {
			menuLis:JSON.stringify(getMenuMessage())
		};
		$.ajax({
			url:"menuViewServlet?mothed=menuAdd",
			type:"POST",
			data:datalist,
			success:function(text){
				$("#dg").datagrid("reload");
				getHide();
				alert(text);
			}
		});
	}
	
	//菜品修改
	function getMenuUpdate(){

		//选中要修改的数据
		var menuRow = $("#dg").datagrid('getSelected');
		if (menuRow==null) {
				$.messager.show({
					title:'警告',
					msg:"请选择要修改的菜品！！",
					timeout:1000,
					showType:'slide',
					style:{
						top:"20%",
						left:"40%"
					}
				});
				return;
		}
		$("#dg").append('<div class="divAdd" ></div>');
		
		$(".divAdd").window({
			title:"修改菜品",
			width: 500,
    		height: 300,
			href:"datagridMenuUpdate.html",
			modal:true ,  
			onLoad:function(){
				getMenuInfo(menuRow)		
			},
			/*窗口在关闭时销毁*/
			onClose:function(){
				getHide();
			}
		})
	}

	//将要修改的数据映射到界面上
	function getMenuInfo(row){

		//菜名
		$("#menuName").textbox({value:row.name});
		//价格
		$("#moneyPrice").textbox({value:row.price});
		//折扣
		$("#discountPrice").textbox({value:row.discount_price});
		//是否置顶(单选)
		$(".divAdd input[name=isTop][value="+row.isTop+"]")[0].checked=true;
		//状态（下拉框）
		$(".divAdd #state").val(row.state);
		$('.divAdd input[name=ID]').attr("id",""+row.id+"")
		var rowJson = row;
		$('#sub').attr("onclick",'getupdateSub()');
	}

	//修改菜品
	function getupdateSub(){
		var datalist = {
			menuLis:JSON.stringify(getMenuMessage())
		};
		$.ajax({
			url:"menuViewServlet?mothed=menuUpdate",
			type:"POST",
			data:datalist,
			success:function(text){
				//隐藏修改框
				getHide();
				$('#sub').attr("onclick",'getMenuAdd()');
				alert(text);
				$.messager.show({
					title:'提示',
					msg:text,
					timeout:1000,
					showType:'slide',
					style:{
						top:"20%",
						left:"40%"
					}
				});
				$("#dg").datagrid("reload");
			}
		});
	}

	//删除菜品（软删除 将状态修改为删除）
	function deleteMenu(){
		//获取被选中的菜品信息
		var menuRow = $("#dg").datagrid('getSelected');
		if (menuRow==null) {
			$.messager.alert('警告','请选择要删除的菜品！！');  
		}else{
			if (menuRow.state !=0) {
				$.ajax({
					url:"menuViewServlet?mothed=menuDelete&menuId="+menuRow.id,
					type:"POST",
					success:function(text){
						//提示框
						$.messager.show({
							title:'提示',
							msg:text,
							timeout:1000,
							showType:'slide',
							style:{
								top:"20%",
								left:"40%"
							}
						});
						$("#dg").datagrid("reload");
					}
				})
			}else{
				$.messager.show({
					title:'提示',
					msg:"该菜品状态已为删除了！！",
					timeout:1000,
					showType:'slide',
					style:{
						top:"20%",
						left:"40%"
					}
				});
			}	
		}
	}


	//根据菜品名查询
	function getMenuName(searchName,isTop,state){

		$("#dg").datagrid('load',{
				name : searchName,
				isTop : isTop,
				menuState : state
		})
	}
