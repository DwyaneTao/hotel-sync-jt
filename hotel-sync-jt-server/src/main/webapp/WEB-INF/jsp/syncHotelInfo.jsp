<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% String path = request.getContextPath(); String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";%>
<html>
<head>
    <title>同步供应商酒店信息</title>
</head>
<body>
<div><h2>同步供应商酒店信息</h2></div>
<div>
  <select name="supplierClass" id="selectSupply">
<!--     <option value="">--请选择供应商--</option> -->
<!--     <option value="JJDC">锦江都城</option> -->
<!--     <option value="SU8">速8</option> -->
    <%--<option value="YL">盈利</option>--%>
<!--     <option value="WYN">维也纳</option> -->
<!--     <option value="DCH">东呈</option> -->
    <option value="HZ">华住</option>
<!--     <option value="BT">铂涛</option> -->
<!--     <option value="YZHG">云掌柜</option> -->
<!--     <option value="GLHT">格林豪泰</option> -->
<!--     <option value="RJ">如家</option> -->
<!--     <option value="HH">海航</option> -->
<!--     <option value="XMDLD">美团落地</option> -->
  </select>
</div>
<div>
  <span><h5>输入要同步的酒店ID，以英文逗号","分隔</h5></span>
  <textarea rows="12" cols="85" name="parameters" id="parameters"></textarea>
</div>
<div><input type="button" onclick="submitRequest();" value="执行同步" /></div>
</body>
<script type="text/javascript" src="<%=basePath%>js/jquery.min.js"></script>
<script type="text/javascript">
  /**提交请求报文*/
  function submitRequest() {
    var supplyClass=$("#selectSupply").val();
    var parameters=$("#parameters").val();
    if (supplyClass == "") {
      alert("请选择供应商");
    } else if($.trim(parameters)==""){
      alert("请输入要同步的酒店编码");
    }else  {
      var url = "<%=basePath%>sync/hotelinfo";
      var content='{"supplyClass":'+'"'+supplyClass+'","spHotelIds":'+'"'+parameters+'"}';
      $.ajax({
        type : "POST",
        url : url,
        cache : false,
        async : true,
        data:content,
        success : function(data) {
          alert(data.msg)
        }
      });
    }
  }
</script>
</html>
