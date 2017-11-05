var TableEditable = function() {

	var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
	
	var handleTable = function() {

		$("#recorder").attr("value", $.session.get('real_name'));
		var mydate = new Date();
		var str = "" + mydate.getFullYear() + "/";
		str += (zeroize(mydate.getMonth() + 1)) + "/";
		str += zeroize(mydate.getDate());
		$("#record_time").attr("value", str);
		$("#menu_name").attr("value", $.session.get('menu_name'));

	}

	return {

		init : function() {
			handleTable();
		}

	};

}();

$('#save_btn').click(function(e) {
	var param = {};
	//获取form
	var body = $(this).parents('form')[0];
	//获取信息的rows
	var rows = $('.row', body);
	//组装基本信息
	var jiben = $('input', rows[0]);
	for (var i = 0, iLen = jiben.length; i < iLen; i++) {
    	param[jiben[i].id]=jiben[i].value;
    }
	param.menu_id = $.session.get('menu_id');
	var status=$("#cur_status").find("option:selected").text();
	param.cur_status = status;
	//组装客户需求信息
	var xuqiu = $('input', rows[1]);
	var sb=new StringBuffer();
	for (var i = 0, iLen = xuqiu.length; i < iLen; i++) {
		if(xuqiu[i].checked&&xuqiu[i].value!="其他"){
			sb.append(";"+xuqiu[i].value);
		}
		if(xuqiu[i].checked&&xuqiu[i].value=="其他"){
			param.others=$('#others_text').val();
		}
    }
	param.cus_require=sb.toString();
	//组装客户需求和备注
	for (var i = 2; i < 9; i++) {
		var msg = $('input', rows[i]);
		for (var j = 0, iLen = msg.length; j < iLen; j++) {
	    	param[msg[j].id]=msg[j].value;
	    }
    }
	//获取接待人员
	var names = "";
    $("#receptionist :checked").each(function(i,item){
        names = names+$(item).attr("value")+";";
    });
    param.receptionist = names;
    
	AjaxHelper.call({
		url : "/zncrm/rest/bus_cus/add",
		data : JSON.stringify(param),
		async : false,
		cache : false,
		type : "POST",
		contentType : 'application/json; charset=UTF-8',
		dataType : "html",
		success : function(result) {
			alert("创建成功");
			location.reload();
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
});

var SelectContent = function() {
	
	var handleSelect = function() {
		var param = {};
		AjaxHelper.call({
			url : "/zncrm/rest/user/all",
			data : JSON.stringify(param),
			async : false,
			cache : false,
			type : "POST",
			contentType : 'application/json; charset=UTF-8',
			dataType : "html",
			success : function(result) {
				result = eval("(" + result + ")");
				result = result.DATA;
				//封装返回数据
				var sb=new StringBuffer();
				for(var key in result){
	    			sb.append('<option value="'+result[key].real_name+'">'+result[key].real_name+'</option>');
	    		}
				$("#receptionist").html(sb.toString());
			},
			error : function(result) {
				alert("服务器异常");
			}
		});
		
	}

	return {

		// main function to initiate the module
		init : function() {
			handleSelect();
		}

	};

}();
