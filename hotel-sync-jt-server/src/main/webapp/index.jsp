<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<html>
  <head>
    <meta charset="utf-8">
  <body>
  	<form id="testForm" name="testForm" action="" method="POST">
  		<input type="text" id="xml" name="xml" value="123123">
 		<input type="submit" value="123" onclick="onKick()">	
	</form>
    <script src="http://quimg.com/admin/assets/js/base/jquery-2.0.3.min.js"></script>
   
    <script type="text/javascript">
   		function onKick(){
   			alert("1111");
   		}
    </script>
  </body>
</html>