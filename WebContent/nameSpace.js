var namespace = namespace || {};

(function(){
	 namespace.ns=function(strs){
	 	var  array = strs.split(".");
	 	obj =window;
	 	for (var i = 0; i < array.length; i++) {
	 		//当节点不存在时，才创建对象
	 		if (undefined==obj[array[i]]) {
	 			obj[array[i]] = {};
	 		}
	 		obj = obj[array[i]];
	 	}
	 	//最后一层的对象
	 	return obj
	 }
})()