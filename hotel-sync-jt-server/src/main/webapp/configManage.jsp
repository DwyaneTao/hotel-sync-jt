<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Config配置管理</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="config管理">
<link type="text/css" rel="stylesheet" href="<%=path%>/js/easyui/themes/default/easyui.css" />
<link type="text/css" rel="stylesheet" href="<%=path%>/js/easyui/themes/icon.css" />
<script type="text/javascript" src="<%=path%>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path%>/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=path%>/js/util/common.js"></script>
</head>

<body>
	<center>
		<h2>Config配置管理</h2>
	</center>
	
	<div id="tabs">
		<div id="tabs-1" title="编辑supplyConfig数据">
			<div style="padding:20px;">
				<center>
				supplierClass：<select id="qSupplyClass"></select>&nbsp;
				supplyCode(供应商编码):<input name="qSupplyCode" id="qSupplyCode" type="text"/> &nbsp;
				merchantCode(商家编码):<input name="qmerchantCode" id="qmerchantCode" type="text"/>
				<input id="querySupplyConfig" name="querySupplyConfig" type="button" value="查询" onclick="displayConfigData()"/>&nbsp; 
				<input id="queryConfigReset"  type="button" name="queryConfigReset" value="重置" onclick="inputClear()" />
				</center>
			</div>
			<table id="supplyConfigInfo"></table>
		</div>
	
		<div id="tabs-2" title="Config个性化配置">
			<div style="padding:20px;">
				<center>
				请选择supplyClass：<select id="pSupplyClass"></select>&nbsp;
				请输入供应商编码supplyCode:<input name="pSupplyCode" id="pSupplyCode" type="text"/> &nbsp;
				请输入商家编码merchantCode:<input name="merchantCode" id="merchantCode" type="text"/>
				<table id="configTemplet">
					<tr>
						<th>配置名称</th>
						<th>配置值</th>
					</tr>
				</table>
				<input id="addPSupplyConfig" name="addPSupplyConfig" type="button" value="新增"  onclick="addSupplyConfig()"/>&nbsp; 
				<input id="pConfigReset" class="pConfigReset" type="button" name="pConfigReset" value="重置" onclick="inputClear()" />
				</center>
			</div>
		</div>
		
		
		<div id="tabs-3" title="新增Config公共配置信息">
			<div class="configData" style="padding:20px;">
				<center>
					请选择supplyClass：<select id="cSupplyClass"></select>
					请输入supplyCode:<input name="commonSupplyCode" id="commonSupplyCode" type="text"/>
				    merchantCode:<input name="commonMerchantCode" id="commonMerchantCode" type="text" value="shareProduct"/>
					<table id ="commonConfigTemplet">
						<tr>
							<th>配置名称(FieldName)</th>
							<th>配置值(FieldValue)</th>
						</tr>
					</table>
						<input id="addCommon" type="button" name="addCommon" value="添加" onclick="addCommonSupplyConfig()"/>&nbsp;
						<input id="ccReset" class="ccReset" type="button" name="ccReset" value="重置" onclick="inputClear()" />
				</center>
			</div>
		</div>
		
		<div id="tabs-4" title="供应商配置模板管理">
			<div class="templetManage" style="padding:20px;">
				<center>
					<span style="margin-left:100px;width:18px;overflow:hidden;">
						请选择supplierClass：
						<select onchange="this.parentNode.nextSibling.value=this.value" id="templetSupplyClass">
						</select></span><input name="box" id="box">
					<p style="color: red;">注：通过该页面增加供应商模板配置项（FieldName）</p>
					<table  border="1" cellspacing="0" id="templetConfig">
						<tr>
							<th>Para配置参数名</th>
							<th>Desc描述信息</th>
							<th>Type类型(0:公共  1:商家)</th>
							<th>Example示例</th>
							<th>操作</th>
						</tr>
						<tr>
							<td><input type="text" name="templetPara" value="email" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}" style="color:#999" /></td>
							<td><input type="text" name="description" value="联系电话" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}" style="color:#999" /></td>
							<td><input type="text" name="type" value="1" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}" style="color:#999" /></td>
							<td><input type="text" name="example" value="示例：email" onFocus="if(value==defaultValue){value='';this.style.color='#000'}" onBlur="if(!value){value=defaultValue; this.style.color='#999'}" style="color:#999" /></td>
							<td><input type="button" value="Add" onclick="addTr()"/>
								<input type="button" value="Del" onclick="delTr(this)"></td>
						</tr>
					</table>
						<br>		
						<input id="addTemplet" type="button" name="addTemplet" value="添加"  onclick="addSupplyTemplet()"/>&nbsp;
						<input id="templetReset" class="templetReset" type="button" name="templetReset" value="重置" onclick="inputClear()" />
				</center>
				
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	(function() {
		$("#tabs").tabs({
			active:0,
		 	collapsible:true,
		 	event:"click"
		}); 
		initSupply();
		configDisplayModel();
		
		$("#pSupplyClass").change(function(){
			var supplierClass = $("#pSupplyClass").val();
			var jsonString = "{\"supplierClass\""+":"+'"'+supplierClass+'"'+","+"\"type\""+":"+'"'+1+'"'+"}";
			if(supplierClass != 0 ){
				$.ajax({
					url : "configTemplet/queryConfigTemplet",
					type : "post",
					contentType: "application/json",
					dataType: "json",
					data: jsonString,
					success : function(data) {
						showTempletKey(data);
					},
					error : function() {
						alert("system error");
					}
		    		
		   		 })
			}
		}); 
		
		$("#cSupplyClass").change(function(){
			var supplierClass = $("#cSupplyClass").val();
			var jsonString = "{\"supplierClass\""+":"+'"'+supplierClass+'"'+","+"\"type\""+":"+'"'+0+'"'+"}";
			if(supplierClass != 0 ){
				$.ajax({
					url : "configTemplet/queryConfigTemplet",
					type : "post",
					contentType: "application/json",
					dataType: "json",
					data: jsonString,
					success : function(data) {
						showCommonTempletKey(data);
					},
					error : function() {
						alert("system error");
					}
		    		
		   		 })
			}
		}); 
	})();
	
	function displayConfigData(){
		var sClass = $("#qSupplyClass option:selected").val();
		var sCode = $("#qSupplyCode").val();
		var mCode = $("#qmerchantCode").val();
		
	    $("#supplyConfigInfo").datagrid({
	    	url : 'config/showSupplyConfig',
		  	queryParams: {
	    		supplyCode: sCode,
	    		supplyClass: sClass,
	    		merchantCode : mCode
	    	} 
	    });
		
	}
	
</script>
</html>
