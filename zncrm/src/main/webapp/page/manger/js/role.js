var TableEditable = function() {

	var handleTable = function() {

		function restoreRow(oTable, nRow) {

			var jqInputs = $('input', nRow);
			var param = {};
			for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
				oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
				param[jqInputs[i].name] = jqInputs[i].value;
			}
			param.parent_id = '5';

			AjaxHelper.call({
				url : "/zncrm/rest/menu",
				data : JSON.stringify(param),
				async : false,
				cache : false,
				type : "PUT",
				contentType : 'application/json; charset=UTF-8',
				dataType : "html",
				success : function(result) {
					oTable.fnDraw();
				},
				error : function(result) {
					alert("服务器异常");
				}
			});
		}

		function editRow(oTable, nRow) {

		}

		var table = $('#sample_editable_1');

		table.on('click', '.btn-edit', function(e) {
			e.preventDefault();
			manager.updateUser = true;
			var nRow = $(this).parents('tr')[0];
			var jqInputs = $('td', nRow);
			var param = {};
			param.group_id = jqInputs[0].innerText;
			$("#role_id").val(jqInputs[0].innerText);
			$("#role_id").attr("readonly","readonly")
			AjaxHelper.call({
				url : "/zncrm/rest/role_auth",
				data : JSON.stringify(param),
				async : false,
				cache : false,
				type : "POST",
				contentType : 'application/json; charset=UTF-8',
				dataType : "html",
				success : function(result) {
					result = eval("(" + result + ")");
					result = result.DATA;
					var menuList = result.menuList;
					var authorities = result.authorities;
					var checkboxs = $(":checkbox");
					for(var key in checkboxs){
						
						if($.inArray(checkboxs[key].value, menuList)!=-1&&$.inArray(checkboxs[key].id, menu_checkbox_ids)!=-1){
							$("#uniform-"+checkboxs[key].id).children("span").addClass("checked");
							$("#"+checkboxs[key].id).attr("checked", true);
						}
						else if($.inArray(checkboxs[key].value, authorities)!=-1&&$.inArray(checkboxs[key].id, menu_checkbox_ids)==-1){
							$("#uniform-"+checkboxs[key].id).children("span").addClass("checked");
							$("#"+checkboxs[key].id).attr("checked", true);
						}
					}
				},
				error : function(result) {
					alert("服务器异常");
				}
			});
		});

		table.on('click', '.btn-cancel', function(e) {
			e.preventDefault();
			oTable.fnDraw();
		});

		table.on('click', '.btn-del', function(e) {
			e.preventDefault();
			if (confirm("确定删除？")) {
				var nRow = $(this).parents('tr')[0];
				var jqInputs = $('td', nRow);
				var param = {};
				param.username = jqInputs[0].innerText;
				AjaxHelper.call({
					url : "/zncrm/rest/user",
					data : JSON.stringify(param),
					async : false,
					cache : false,
					type : "DELETE",
					contentType : 'application/json; charset=UTF-8',
					dataType : "html",
					success : function(result) {
						oTable.fnDraw();
					},
					error : function(result) {
						alert("服务器异常");
					}
				});
			}
		});

		var oTable = table
				.dataTable({

					"bServerSide" : true,// 这个用来指明是通过服务端来取数据
					"sAjaxSource" : "/zncrm/rest/role_list",// 这个是请求的地址
					"fnServerData" : retrieveData,
					"sAjaxDataProp" : "result",
					"searching" : false,
					"bSort" : false,
					"language" : {
						"emptyTable" : "No data available in table",
						"info" : "Showing _START_ to _END_ of _MAX_ entries",
						"infoEmpty" : "No entries found",
						"infoFiltered" : "(filtered1 from _MAX_ total entries)",
						"zeroRecords" : "No matching records found",
						"paginate" : {
							"previous" : "Prev",
							"next" : "Next",
							"last" : "Last",
							"first" : "First"
						}
					},
					"bLengthChange" : false,
					"pagingType" : "bootstrap_full_number",
					"columns" : [ {
						data : "id"
					}, {
						data : null,
						defaultContent : ""
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit" data-toggle="modal" href="#responsive">修改</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						$('td', row).eq(1).append($btnEdit).append($btnDel);
					}
				});

		$("#btn-simple-search").click(function() {
			manager.fuzzySearch = true;
			var temp = oTable.api();
			temp.ajax.reload();
		});

	}

	return {

		// main function to initiate the module
		init : function() {
			handleTable();
		}

	};

}();

var menu_checkbox_ids = new Array('home_s','gzl_o','bus_cus_s','pro_library_s','ku_cun_s','company_management_s','caiwu_s','manger_o');

$('#save_button').click(
		function(e) {
			var body = $(this).parents('div')[1];
			var jqInputs = $('input', body);
			var menuParam = new Array();
			var menuParamP = 0;
			var authoritiesParam = {};
			var sb=new StringBuffer();
			for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
				console.log(jqInputs[i]);
				if (jqInputs[i].checked) {
					if (jqInputs[i].id == 'home_s' || jqInputs[i].id == 'gzl_o'
							|| jqInputs[i].id == 'bus_cus_s'
							|| jqInputs[i].id == 'pro_library_s'
							|| jqInputs[i].id == 'ku_cun_s'
							|| jqInputs[i].id == 'company_management_s'
							|| jqInputs[i].id == 'caiwu_s'
							|| jqInputs[i].id == 'manger_o') {
						var temp = {};
						temp.group_id = $("#role_id").val();
						temp.menu_id = jqInputs[i].value;
						menuParam[menuParamP] = temp;
						menuParamP++;
					}
					else{
						if(sb.count()==0){
							sb.append(jqInputs[i].value);
						}else{
							sb.append(","+jqInputs[i].value);
						}
					}
				}
			}
			var param = {};
			param.role_id = $("#role_id").val();
			param.menu_param = menuParam;
			param.authorities = sb.toString();
			console.log(param);
			 if (!manager.updateUser) {
						AjaxHelper
								.call({
									url : "/zncrm/rest/user/menu",
									data : JSON.stringify(param),
									async : false,
									cache : false,
									type : "POST",
									contentType : 'application/json; charset=UTF-8',
									dataType : "html",
									success : function(result) {
										result = eval("(" + result + ")");
										result = result.CODE;
										if (result == '-2') {
											alert("创建失败，请检查角色名是否重复");
										} else {
											$('#responsive').modal('hide');
											alert("创建成功");
										}
										manager.updateUser = false;
									},
									error : function(result) {
										alert("服务器异常");
										manager.updateUser = false;
									}
								});
					} else {
						AjaxHelper
								.call({
									url : "/zncrm/rest/user/menu",
									data : JSON.stringify(param),
									async : false,
									cache : false,
									type : "PUT",
									contentType : 'application/json; charset=UTF-8',
									dataType : "html",
									success : function(result) {
										result = eval("(" + result + ")");
										result = result.CODE;
										if (result == '-2') {
											alert("更新失败");
										} else {
											for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
												jqInputs[i].value = "";
											}
											$('#responsive').modal('hide');
											alert("更新成功");
										}
										manager.updateUser = false;
									},
									error : function(result) {
										alert("服务器异常");
										manager.updateUser = false;
									}
								});
					}

		});

function retrieveData(source, data, callback) {

	var param = manager.getQueryCondition(data);
	AjaxHelper.call({
		url : source,
		data : JSON.stringify(param),
		async : false,
		cache : false,
		type : "POST",
		contentType : 'application/json; charset=UTF-8',
		dataType : "html",
		success : function(result) {
			result = eval("(" + result + ")");
			result = result.DATA;
			// 封装返回数据
			var returnData = {};
			returnData.draw = data.draw;// 这里直接自行返回了draw计数器,应该由后台返回
			returnData.recordsTotal = result.iTotalRecords;
			returnData.recordsFiltered = result.iTotalRecords;// 后台不实现过滤功能，每次查询均视作全部结果
			returnData.result = result.result;
			callback(returnData);
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
}

var table_row = {
	id : ""
};

var manager = {
	updateUser : false,
	fuzzySearch : false,
	getQueryCondition : function(data) {
		var param = {};
		for ( var temp in data) {
			if (data[temp].name == "iDisplayStart") {
				param.start_index = data[temp].value;
			}
			if (data[temp].name == "iDisplayLength") {
				param.page_size = data[temp].value;
			}
		}
		if (manager.fuzzySearch) {
			param.search_key = $("#search_key").val();
		}

		return param;
	}
};

function setManager(){
	manager.updateUser=false;
	$("#role_id").val("");
	$("#role_id").attr("readonly",false);
	var checkboxs = $(":checkbox");
	console.log(checkboxs);
	for(var key in checkboxs){
		$("#uniform-"+checkboxs[key].id).children("span").removeClass("checked");
		$("#"+checkboxs[key].id).attr("checked", false);
	}
	
}