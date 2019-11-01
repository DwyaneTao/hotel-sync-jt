// supplyConfig参数配置信息展示
function configDisplayModel(){
	var editRow = undefined;
	$("#supplyConfigInfo").datagrid({
		title : 'supplyConfig参数配置信息',
		iconCls : 'icon-ok',
		width:1850,
		height:"auto",
		rownumbers : true,
		pagination : true,// 显示底部分页栏
		pageSize : 10,// 默认每页记录数，pagination参数为true时才有效
		pageList : [ 10, 20, 30 ], // 显示列表记录数的下拉框选项，pagination参数为true时才有效
		fitColumns : true,// 自适应宽度，防止水平滚动
		triped : true,// 隔行变色
		remoteSort : false,
		loadMsg : '加载中...',
		resize:true,
		columns : [ [ {
			field : 'supplierClass',
			title : '供应商class',
			width : 60,
			align : 'center',
			sortable : true
		},  {
			field : 'supplierCode',
			title : '供应商编码',
			width : 60,
			align : 'center',
			sortable : true  
		} ,{
			field : 'merchantCode',
			title : '商家编码',
			align : 'center',
			sortable : true
		},{
			field : 'fieldName',
			title : '参数项名称',
			align : 'center',
			sortable : true
		},{
			field : 'fieldValue',
			title : '配置值Value',
			width : 80,
			align : 'center',
			editor: 'text',
			sortable : true
		}
		] ],
		singleSelect : true,
		enableHeaderClickMenu: false,
        enableHeaderContextMenu: false,
        enableRowContextMenu: false,
        autoFocusField: "url",
        autoEditing: true,          //该属性启用双击行时自定开启该行的编辑状态
        extEditing: true,           //该属性启用行编辑状态的 ExtEditing 风格效果，该属性默认为 true。
        singleEditing: true,        //该属性启用datagrid的只允许单行编辑效果，该属性默认为 true。
		toolbar: [{ text: '修改', iconCls: 'icon-edit', handler: function () {
              //修改时要获取选择到的行
              var rows = $("#supplyConfigInfo").datagrid("getSelections");
              //如果只选择了一行则可以进行修改，否则不操作
              if (rows.length == 1) {
                  //修改之前先关闭已经开启的编辑行，当调用endEdit该方法时会触发onAfterEdit事件
                  if (editRow != undefined) {
                	  $("#supplyConfigInfo").datagrid("endEdit", editRow);
                  }
                  //当无编辑行时
                  if (editRow == undefined) {
                      //获取到当前选择行的下标
                      var index = $("#supplyConfigInfo").datagrid("getRowIndex", rows[0]);
                      //开启编辑
                      $("#supplyConfigInfo").datagrid("beginEdit", index);
                      //把当前开启编辑的行赋值给全局变量editRow
                      editRow = index;
                      //当开启了当前选择行的编辑状态之后，
                      //应该取消当前列表的所有选择行，要不然双击之后无法再选择其他行进行编辑
                      $("#supplyConfigInfo").datagrid("unselectAll");
                  }
              }
          }
          }, '-',
          { text: '保存', iconCls: 'icon-save', handler: function () {
              //保存时结束当前编辑的行，自动触发onAfterEdit事件如果要与后台交互可将数据通过Ajax提交后台
        	  $("#supplyConfigInfo").datagrid("endEdit", editRow);
          	}
          },'-',
          { text: '取消编辑', iconCls: 'icon-redo', handler: function () {
              //取消当前编辑行把当前编辑行罢undefined回滚改变的数据,取消选择的行
              editRow = undefined;
              $("#supplyConfigInfo").datagrid("rejectChanges");
              $("#supplyConfigInfo").datagrid("unselectAll");
          }
          }, '-'],
         onAfterEdit: function (rowIndex, rowData, changes) {
             //endEdit该方法触发此事件
             editRow = undefined;
             // 实现后台数据交互
             if(rowData.fieldValue == null || rowData.fieldValue.trim() == '') {
                 $.messager.show({title:'提示', msg:"修改后的配置value值不能为空"});
                 $("#supplyConfigInfo").datagrid("reload");
                 return;
             }
             $.post("config/updateSupplyConfig",
             {	
            	 supplyClass : rowData.supplierClass,		
                 supplyCode: rowData.supplierCode,
                 merchantCode : rowData.merchantCode,
                 configValue : rowData.fieldValue,
                 fieldName : rowData.fieldName

             },
                    function (json) {
                        if (json.success) {
                        	$("#supplyConfigInfo").datagrid("reload");
                        } else {
                        	$("#supplyConfigInfo").datagrid("reload");
                        }
                    }, "JSON");
         },
         onDblClickRow: function (rowIndex, rowData) {
         //双击开启编辑行
             if (editRow != undefined) {
            	 $("#supplyConfigInfo").datagrid("endEdit", editRow);
             }
             if (editRow == undefined) {
            	 $("#supplyConfigInfo").datagrid("beginEdit", rowIndex);
                 editRow = rowIndex;
             }
         }
     });
}

/**初始化supplyClass列表*/
 function initSupply() {
	$.ajax({
		url : "configTemplet/getSupplyClass",
		type : "post",
		contentType: "application/json",
		dataType: "json",
		success : function(data) {
			var ddl = $("#pSupplyClass");
			var templetClass = $("#templetSupplyClass");
			var qSupplyClass = $("#qSupplyClass");
			var cSupplyClass = $("#cSupplyClass");
			
			//删除节点
			removeOption();
			removeTempletOption();
			removeQueryOption();
			removeCommonOption();
			//方法1：添加默认节点 
			ddl.append("<option value='0'>----请选择----</option>");
			templetClass.append("<option value='0'>----请选择----</option>");
			qSupplyClass.append("<option value='0'>----请选择----</option>");
			cSupplyClass.append("<option value='0'>----请选择----</option>");
			//转成Json对象
			var result = eval(data);
			
			//循环遍历 下拉框绑定
			$(result).each(
					function(key) {
					    var opt ="<option value='"+result[key].supplierClass+"'>"+result[key].supplierClass+"</option>";
						//var opt =$("<option></option>").text(result[key].supplierClass).val(result[key].supplierClass);
						ddl.append(opt);
						templetClass.append(opt);
						qSupplyClass.append(opt);
						cSupplyClass.append(opt);
						
					});
		},
		error : function() {
			alert("system error");
		}
	});

} 

 
/** 新增供应商config数据库配置*/
function addSupplyConfig(){
	var url = "config/addSupplyConfig";
	var param = createAddConfigJsonStr();
	if(param != null && param != ''){
		ajaxWrapper(url,param);
	}
	
}

/** 公共配置页面config数据库配置*/
function addCommonSupplyConfig(){
	var url = "config/addSupplyConfig";
	var param = createAddCommonConfigJsonStr();
	if(param != null && param != ''){
		ajaxWrapper(url,param);
	}
}

/** 新增供应商模板配置*/
function addSupplyTemplet(){
	var url = "configTemplet/oprateConfigTemplet";
	var param = createTempletParaModel();
	if(param != null && param != ''){
		ajaxWrapper(url,param);
	}
}
//ajax请求类
function ajaxWrapper(v_url,v_params){
	$.ajax({
		url : v_url,
		type : "post",
		contentType: "application/json",
		dataType: "json",
		data: v_params,
		success : function(data) {
			alert("操作成功！");
		},
		error : function() {
			alert("system error");
		}
		
	})
}

	
function removeOption() {
	$("#pSupplyClass option").remove();
}

function removeTempletOption() {
	$("#templetSupplyClass option").remove();
}

function removeQueryOption(){
	$("#qSupplyClass option").remove();
} 

function removeCommonOption(){
	$("#cSupplyClass option").remove();
}

//重置
function inputClear(){
	jQuery(":text").val("");
	$("#commonMerchantCode").val("shareProduct");
}


/** 将模板配置项key展示在个性化config配置页面*/
function showTempletKey(data){
	//后台返回模板key json字符串数据
	var content = data;
	var trs = "<tr>"
			  +"<th style=\"width:120px;\">描述</th>"
			  +"<th>配置名称</th>"
			  +"<th>配置值</th>"
			  +"<th>示例</th>"
			  +"</tr>";
	var desc = '';
	var example = '';
	for(var i = 0; i < content.length; i++){
		if(content[i].description == undefined){
			desc='';
		}else{
			desc = content[i].description;
		}
		
		if(content[i].example == undefined){
			example='';
		}else{
			example = content[i].example;
		}
		//动态构建html文本格式
		var configData = "<tr>"
						+"<td id=\"sdesc\" style=\"font-family:黑体;width:120px;text-align:center;\">" +desc+ "</td>"
						+"<td id=\"fieldName\">" +content[i].templetPara+ "</td>"
						+"<td><input id=\"fieldValue\" type=\"text\" name="+content[i].templetPara+" style=\"width:300px;\"></td>" 
						+"<td id=\"sexample\" style=\"color:#FFAF60\">" +example+ "</td>"
						+"</tr>";
		trs += configData;
		
	}
	$("#configTemplet tr").remove();
	$("#configTemplet").append(trs);
}

/** 将模板配置项key展示共享config配置页面*/
function showCommonTempletKey(data){
	//后台返回模板key json字符串数据
	var content = data;
	var trs = "<tr>"
			  +"<th style=\"width:120px;\">描述</th>"
			  +"<th>配置名称</th>"
			  +"<th>配置值</th>"
			  +"<th>示例</th>"
			  +"</tr>";
	var desc = '';
	var example = '';
	for(var i = 0; i < content.length; i++){
		if(content[i].description == undefined){
			desc='';
		}else{
			desc = content[i].description;
		}
		
		if(content[i].example == undefined){
			example='';
		}else{
			example = content[i].example;
		}
		//动态构建html文本格式
		var configData = "<tr>"
						+"<td id=\"cdesc\" style=\"font-family:黑体; width:120px;text-align:center;\">" +desc+"</td>"
						+"<td id=\"fieldName\">" +content[i].templetPara+"</td>"
						+"<td><input id=\"fieldValue\" type=\"text\" name="+content[i].templetPara+" style=\"width:300px;\"></td>"
						+"<td id=\"cexample\" style=\"color:#FFAF60\">" +example+"</td>"
						+"</tr>";
		trs += configData;
		
	}
	$("#commonConfigTemplet tr").remove();
	$("#commonConfigTemplet").append(trs);
}

/** 构建config配置model*/
function createAddConfigJsonStr(){
	var jsonString = "";
	var pSupplyClass = $("#pSupplyClass option:selected").val();
	var pSupplyCode = $("#pSupplyCode").val();
	var merchantCode = $("#merchantCode").val();
	
	if(pSupplyCode =='' || merchantCode == ''){
		alert("供应商编码与商家编码不能为空");
		return jsonString;
	}
	var obj= $("#configTemplet").find(':text');
	var keyValue ='';
	var keyName = '';
	for(var i = 0; i < obj.length;i++){ 
		keyValue = obj.eq(i).val(); 
		keyName = obj.eq(i).attr("name");
		if(keyValue != null && keyValue.trim() != ''){
			jsonString += "{\"supplierClass\""+":"+'"'+pSupplyClass+'"'+","+"\"supplierCode\""+":"+'"'+pSupplyCode+'"'+","+"\"merchantCode\""+":"+'"'+merchantCode+'"'+",";
			//配置值存在"-"的情况，改"-"为";"
			jsonString =jsonString+"\"fieldName\""+":"+'"'+keyName+'"'+","+"\"fieldValue\""+":"+'"'+keyValue+'"'+"}"+";";
		}
	 }
	return jsonString.substring(0,jsonString.length-1);
	}

/** 构建Common config配置model*/
function createAddCommonConfigJsonStr(){
	var jsonString = "";
	var cSupplyClass = $("#cSupplyClass option:selected").val();
	var cSupplyCode = $("#commonSupplyCode").val();
	var merchantCode = $("#commonMerchantCode").val();
	
	if(cSupplyCode =='' || merchantCode == ''){
		alert("供应商编码与商家编码不能为空");
		return jsonString;
	}
	var obj= $("#commonConfigTemplet").find(':text');
	var keyValue ='';
	var keyName = '';
	for(var i = 0; i < obj.length;i++){ 
		keyValue = obj.eq(i).val(); 
		keyName = obj.eq(i).attr("name");
		if(keyValue != null && keyValue.trim() != ''){
			jsonString += "{\"supplierClass\""+":"+'"'+cSupplyClass+'"'+","+"\"supplierCode\""+":"+'"'+cSupplyCode+'"'+","+"\"merchantCode\""+":"+'"'+merchantCode+'"'+",";
			//配置值存在"-"的情况，改"-"为";"
			jsonString =jsonString+"\"fieldName\""+":"+'"'+keyName+'"'+","+"\"fieldValue\""+":"+'"'+keyValue+'"'+"}"+";";
		}
	 }
	return jsonString.substring(0,jsonString.length-1);
	}

function addTr() {
    var trObj = document.createElement("tr");  
    trObj.id = new Date().getTime();  
    trObj.innerHTML = "<td><input name='templetPara' type ='text'/></td>"+
    		"<td><input name='description' type ='text'/></td>"+
    		"<td><input name='type' type ='text'/></td>"+
    		"<td><input name='example' type ='text'/></td>"+
    		"<td><input type='button' value='Add' onclick='addTr()'/><input type='button' value='Del' onclick='delTr(this)'></td>";
	 
    document.getElementById("templetConfig").appendChild(trObj);  
}  

function delTr(obj) {  
    var trId = obj.parentNode.parentNode.id;  
    var trObj = document.getElementById(trId);  
    document.getElementById("templetConfig").removeChild(trObj);  
}  

function createTempletParaModel(){
	var jsonString = '';
	var supplierClass = $("#box").val();
	if(supplierClass.trim() == ''){
		alert('供应商类型不能为空');
		return jsonString;
	}
	
	var obj= $("#templetConfig tr");
	var paraValue ='';
	var desc ='';
	var type ='';
	var example ='';
	for(var i = 0; i < obj.length-1;i++){ 
		paraValue =  $($("input[name='templetPara']").eq(i)).val();
		type = $($("input[name='type']").eq(i)).val();
		desc = $($("input[name='description']").eq(i)).val();
		example = $($("input[name='example']").eq(i)).val();
		if(paraValue.trim() =='' || type ==''){
			alert('模板参数值和类型不能为空');
			continue;
		}
		jsonString += "{\"supplierClass\""+":"+'"'+supplierClass+'"'+",";
		//配置值存在"-"的情况，改"-"为";"
		jsonString =jsonString+"\"templetPara\""+":"+'"'+paraValue+'"'+","+"\"description\""+":"+'"'+desc+'"'+","+"\"type\""+":"+'"'+type+'"'+","+"\"example\""+":"+'"'+example+'"'+"}"+";";
		
	 }
	return jsonString.substring(0,jsonString.length-1);
	
}
