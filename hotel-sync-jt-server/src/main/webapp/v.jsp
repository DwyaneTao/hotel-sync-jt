<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.lang.Class"%>
<%@page import="java.lang.reflect.Field"%>
<%@page import="java.io.StringWriter"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="com.alibaba.fastjson.JSON"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内存值查看页面</title>
</head>
<body>
<h3>常用地址：</h3>
<p>?className=com.fangcang.hotel.sync.yl.service.impl.YlExtendsConfigServiceImpl&fieldName=supplyRelationMap</p>
<p>----------------------------------------------------------------------------------------------------</p>

<%
	String className = request.getParameter("className");
	String fieldName = request.getParameter("fieldName");
	
	if (null == className || className.trim().length() < 1) {
		out.println("className is null.");
		return;
	}
%>

<h3>内存值：</h3>
<%
	try {
		Class x = Class.forName(className);
		Field[] fields = x.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if (null == fieldName || fieldName.trim().length() < 1) {
				if ((field.getModifiers() & 8) == 8) {
					out.print(JSON.toJSONString(field.get(null)));
				}
			} else if (field.getName().equals(fieldName)) {
				if ((field.getModifiers() & 8) == 8) {
					out.print(JSON.toJSONString(field.get(null)));
				}
			}
		}
	} catch (Exception e) {
		out.print(getStackTrace(e));
	}	 
%>
<%!
String getStackTrace(Throwable t) {  
    StringWriter sw = new StringWriter();  
    PrintWriter pw = new PrintWriter(sw);  
    try {  
        t.printStackTrace(pw);  
        return sw.toString();  
    } finally {  
        pw.close();  
    }
} 
%>
</body>
</html>