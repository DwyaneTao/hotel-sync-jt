    var apiParams = new Array();
    var apiNames = new Array();
    var apiDescs = new Array();

    $(document).ready(
        function (){
            var index = $("#hiddenIndex").val();
            $.ajax({
                type: "GET",
                url: "./request/getHotelInfo.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[0] = msg;
                $("#xml").val(msg);
            });

            $.ajax({
                type: "GET",
                url: "./request/addHotelMapping.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[1] = msg;
            });

            $.ajax({
                type: "GET",
                url: "./request/addRoomTypeMapping.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[2] = msg;
            });
            $.ajax({
                type: "GET",
                url: "./request/deleteHotelMapping.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[3] = msg;
            });
            $.ajax({
                type: "GET",
                url: "./request/deleteRoomTypeMapping.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[4] = msg;
            });
            $.ajax({
                type: "GET",
                url: "./request/syncPricePlan.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[5] = msg;
            });
            $.ajax({
                type: "GET",
                url: "./request/deleteRatePlan.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[6] = msg;
            });
            $.ajax({
                type: "GET",
                url: "./request/syncRateInfo.xml",
                dataType: "text"
            }).done(function (msg) {
                apiParams[7] = msg;
            });

            var getHotelInfo = '该接口用于根据房仓酒店ID,查询有效的房仓酒店基本信息，包含酒店与房型的信息。';
            var addHotelMapping = '该接口用于合作方将自身酒店与房仓酒店的酒店的映射信息通过调用房仓提供的API接口，将酒店映射信息保存在房仓数据库中。';
            var addRoomTypeMapping = '该接口用于合作方将自身酒店房型与房仓酒店的酒店房型的映射信息通过调用房仓提供的API接口，将酒店房型映射信息保存在房仓数据库中。';
            var deleteHotelMapping = '该接口用于删除合作方的酒店与房仓酒店的映射信息，调用此接口后，酒店下的房型映射、价格计划也跟着删除。';
            var deleteRoomTypeMapping = '该接口用于删除合作方的房型与房仓房型的映射信息，调用此接口后，酒店下的房型下的所有价格计划也跟着删除。';
            var syncRatePlan = '该接口用于合作方的价格计划信息同步到房仓价格计划信息。';
            var deleteRatePlan = '该接口用于根据合作方传递的价格计划信息删除房仓价格计划信息。。';
            var syncRateInfo = '该接口用于合作方的价格信息同步到房仓价格信息。';

            apiNames.push('./api_v1/getHotelInfo');
            apiNames.push('./api_v1/addHotelMapping');
            apiNames.push('./api_v1/addRoomTypeMapping');
            apiNames.push('./api_v1/deleteHotelMapping');
            apiNames.push('./api_v1/deleteRoomTypeMapping');
            apiNames.push('./api_v1/syncRatePlan');
            apiNames.push('./api_v1/deleteRatePlan');
            apiNames.push('./api_v1/syncRateInfo');

            apiDescs.push(getHotelInfo);
            apiDescs.push(addHotelMapping);
            apiDescs.push(addRoomTypeMapping);
            apiDescs.push(deleteHotelMapping);
            apiDescs.push(deleteRoomTypeMapping);
            apiDescs.push(syncRatePlan);
            apiDescs.push(deleteRatePlan);
            apiDescs.push(syncRateInfo);

            //初始状态
            $("form:first").attr("action", apiNames[0]);
            $("#xml").val(apiParams[0]);
            $("#apiDesc").html(apiDescs[0]);
        }
    );

	function getApiListName(value) {
		if (null != value && "" != value && value < apiNames.length) {
            $("form:first").attr("action", apiNames[value]);
			$("#xml").val(apiParams[value]);
			$("#apiDesc").html( apiDescs[value]);
            $("#result").val("");
		}
	}

	function submitMsg() {
		var interfaceIndex = $("#apiInterface").val();
		if (null != interfaceIndex && "" != interfaceIndex && interfaceIndex < apiNames.length) {
            var requestPath = apiNames[interfaceIndex];
            var param = $("#xml").val();
            //alert("requestPath=" + requestPath + "\r\nparam=" + param);
            $.ajax({
                type: "POST",
                url: requestPath,
                data: {xml:param},
                dataType: "text"
            }).done(function (msg) {
                $("#result").val(msg);
            });
        }
	}