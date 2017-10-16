var TableEditable = function() {
	
	var handleTable = function() {

		function restoreRow(oTable, nRow) {
			
			var jqInputs = $('input', nRow);
			var param = {};
            for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
            	oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
            	param[jqInputs[i].name]=jqInputs[i].value;
            }

			AjaxHelper.call({
				url : "/zncrm/rest/pro_lib",
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

		var table = $('#sample_editable_1');
		
		table.on('click', '.btn-edit', function (e) {
            e.preventDefault();

            if(this.innerHTML=="修改"){
            	var nRow = $(this).parents('tr')[0];
                editRow(oTable, nRow);
            }else if(this.innerHTML=="保存"){
            	var nRow = $(this).parents('tr')[0];
            	restoreRow(oTable, nRow);
            }
            
        });
		
		table.on('click', '.btn-more', function (e) {
			e.preventDefault();
            var nRow = $(this).parents('tr')[0];
			var aData = oTable.fnGetData(nRow);
            var host = window.location.host;
            $.session.set('project_id', aData.id);
            window.open("http://"+host+"/zncrm/page/bus_cus/project_detail.html");
			
        });
		
		var oTable = table
				.dataTable({

					"bServerSide" : true,// 这个用来指明是通过服务端来取数据
					"sAjaxSource" : "/zncrm/rest/bus_cus",// 这个是请求的地址
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
						data : "menu_name"
					}, {
						data : "cus_name"
					}, {
						data : "cur_status"
					}, {
						data : "cus_phone"
					}, {
						data : "con_add"
					}, {
						data : "receptionist"
					}, {
						data : "reception_time"
					}, {
						data : null,
						defaultContent : ""
					}, {
						data : "id",
						className : "id_display"
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnMore = $('<button type="button" class="btn btn-small btn-warning btn-more">详细</button>');
						$('td', row).eq(7).append($btnMore);
					}
				});

		$("#btn-simple-search").click(function(){
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

$('#save_button').click(function (e) {
    var body = $(this).parents('div')[1];
    var jqInputs = $('input', body);
    var param = {};
    for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
    	param[jqInputs[i].name]=jqInputs[i].value;
    }
    AjaxHelper.call({
		url : "/zncrm/rest/pro_lib/add",
		data : JSON.stringify(param),
		async : false,
		cache : false,
		type : "POST",
		contentType : 'application/json; charset=UTF-8',
		dataType : "html",
		success : function(result) {
			for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
		    	jqInputs[i].value="";
		    }
			$('#responsive').modal('hide');
			alert("创建成功");
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
});

function retrieveData(source, data, callback) {

	var param = manager.getQueryCondition(data);
	param.menu_id = $.session.get('menu_id');
	var host = window.location.host;
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
			//封装返回数据
            var returnData = {};
            returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
            returnData.recordsTotal = result.iTotalRecords;
            returnData.recordsFiltered = result.iTotalRecords;//后台不实现过滤功能，每次查询均视作全部结果
            returnData.result = result.result;
			callback(returnData);
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
}

var table_row = {
		id:""
};

var manager = {
	fuzzySearch : false,
	getQueryCondition : function(data) {
		var param = {};
		for(var temp in data){
			if(data[temp].name=="iDisplayStart"){
				param.start_index = data[temp].value;
			}
			if(data[temp].name=="iDisplayLength"){
				param.page_size = data[temp].value;
			}
		}
		if (manager.fuzzySearch) {
			param.search_key = $("#search_key").val();
		}

		return param;
	}
};

var AuthInit = function() {
	
	var handleAuth = function() {
		var authArray = $.session.get('authorities');
		//添加权限
		if($.inArray("4", authArray)==-1){
			$("#add_bus_cus").hide();
		}
	}

	return {

		// main function to initiate the module
		init : function() {
			handleAuth();
		}

	};

}();
