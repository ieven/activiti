var TableEditable = function() {

	var handleTable = function() {
		var param = {};
		param.id = $.session.get('project_id');
		AjaxHelper.call({
			url : "/zncrm/rest/bus_cus/get",
			data : JSON.stringify(param),
			async : false,
			cache : false,
			type : "POST",
			contentType : 'application/json; charset=UTF-8',
			dataType : "html",
			success : function(result) {
				result = eval("(" + result + ")");
				result = result.DATA;
				
				var sb=new StringBuffer();
				var appended = false;
				for ( var key in result) {
					if(key=='cus_require'){
						var requires = result[key].split(";");
						//有的浏览器怎么都不生效，我只能这么干了
						if($.inArray("泊声",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"><input type=\"checkbox\" id=\"bosheng\" value=\"泊声\" checked />泊声</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"bosheng\" value=\"泊声\" />泊声</label>");
						}
						if($.inArray("悠达",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"youda\" value=\"悠达\" checked />悠达</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"youda\" value=\"悠达\" />悠达</label>");
						}
						if($.inArray("视听室",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"shitingshi\" value=\"视听室\" checked />视听室</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"shitingshi\" value=\"视听室\" />视听室</label>");
						}
						if($.inArray("影院",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"yingyuan\" value=\"影院\" checked />影院</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"yingyuan\" value=\"影院\" />影院</label>");
						}
						if($.inArray("水处理",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"shuichuli\" value=\"水处理\" checked />水处理</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"shuichuli\" value=\"水处理\" />水处理</label>");
						}
						if($.inArray("除尘",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"chuchen\" value=\"除尘\" checked />除尘</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"chuchen\" value=\"除尘\" />除尘</label>");
						}
						if($.inArray("新风",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"xinfeng\" value=\"新风\" checked />新风</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"xinfeng\" value=\"新风\" />新风</label>");
						}
						if($.inArray("安防",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"anfang\" value=\"安防\" checked />安防</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"anfang\" value=\"安防\" />安防</label>");
						}
						if($.inArray("电动窗帘",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"ddcl\" value=\"电动窗帘\" checked />电动窗帘</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"ddcl\" value=\"电动窗帘\" />电动窗帘</label>");
						}
						if($.inArray("智能照明",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"znzm\" value=\"智能照明\" checked />智能照明</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"znzm\" value=\"智能照明\" />智能照明</label>");
						}
						if($.inArray("集中控制",requires)!=-1){
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"jzkz\" value=\"集中控制\" checked />集中控制</label>");
						}else{
							sb.append("<label class=\"checkbox-inline\"> <input type=\"checkbox\" id=\"jzkz\" value=\"集中控制\" />集中控制</label>");
						}
					}
					if(key=='others'){
						sb.append("<label class=\"checkbox-inline\"><div class=\"form-group\"><label class=\"control-label col-md-5\"> <input type=\"checkbox\" id=\"others\" value=\"其他\" checked>其他</label><div class=\"col-md-7\"><input type=\"text\" id=\"others_text\" class=\"form-control\" value=\""+result[key]+"\"></div></div></label>");
						appended=true;
					}
					if(key=='note'){
						var role_id = $.session.get('role_id');
						var real_name = $.session.get('real_name');
						if(role_id=='admin'||real_name==result[recorder]){
							$("#" + key).val(result[key]);
						}
					}else{
						
						$("#" + key).val(result[key]);
					}
				}
				if(!appended){
					sb.append("<label class=\"checkbox-inline\"><div class=\"form-group\"><label class=\"control-label col-md-5\"> <input type=\"checkbox\" id=\"others\" value=\"其他\">其他</label><div class=\"col-md-7\"><input type=\"text\" id=\"others_text\" class=\"form-control\" ></div></div></label>");
				}
				$("#checkbox-list").html(sb.toString());
				$("#menu_name").find("option:contains("+result["menu_name"]+")").attr("selected",true);
			},
			error : function(result) {
				alert("服务器异常");
			}
		});

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
	param.id=$.session.get('project_id');
	//组装次级菜单类别
	param.menu_id = $("#menu_name").val();
	param.menu_name = $("#menu_name").find("option:selected").text();
	//备注信息
	param.note = $("#note").val();
	
	AjaxHelper.call({
		url : "/zncrm/rest/bus_cus/update",
		data : JSON.stringify(param),
		async : false,
		cache : false,
		type : "POST",
		contentType : 'application/json; charset=UTF-8',
		dataType : "html",
		success : function(result) {
			alert("更新成功");
			window.opener=null;
			window.open('','_self');
			window.close();
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
			url : "/zncrm/rest/m/bus_cus/menu_list",
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
	    			sb.append('<option value="'+result[key].menu_id+'">'+result[key].menu_name+'</option>');
	    		}
				$("#menu_name").html(sb.toString());
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
