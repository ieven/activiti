var TableEditable = function() {

	var handleTable = function() {

		function restoreRow(oTable, nRow) {

			var jqInputs = $('input', nRow);
			var param = {};
			for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
				oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
				param[jqInputs[i].name] = jqInputs[i].value;
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

		table.on('click', '.btn-edit', function(e) {
			e.preventDefault();

			if (this.innerHTML == "修改") {
				var nRow = $(this).parents('tr')[0];
				editRow(oTable, nRow);
			} else if (this.innerHTML == "保存") {
				var nRow = $(this).parents('tr')[0];
				restoreRow(oTable, nRow);
			}

		});

		table.on('click', '.btn-danger', function(e) {
			e.preventDefault();
			var nRow = $(this).parents('tr')[0];
			var aData = oTable.fnGetData(nRow);
			var param={};
			param.id = aData.id;
			AjaxHelper.call({
				url : "/zncrm/rest/bus_cus/log",
				data : JSON.stringify(param),
				async : false,
				cache : false,
				type : "DELETE",
				contentType : 'application/json; charset=UTF-8',
				dataType : "html",
				success : function(result) {
					alert("删除成功");
					oTable.fnDraw();
				},
				error : function(result) {
					alert("服务器异常");
				}

			});
		});

		var oTable = table
				.dataTable({

					"bServerSide" : true,// 这个用来指明是通过服务端来取数据
					"sAjaxSource" : "/zncrm/rest/bus_cus/log",// 这个是请求的地址
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
						data : "recorder"
					}, {
						data : "record_time"
					}, {
						data : "info"
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
						var $btnMore = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						$('td', row).eq(3).append($btnMore);
					}
				});

		$("#btn-simple-search").click(function() {
			manager.fuzzySearch = true;
			var temp = oTable.api();
			temp.ajax.reload();
		});
		
		$('#submit_btn').click(function(e) {
			var sHTML = $('#summernote_1').code();
			var param = {};
			param.project_id = $.session.get('project_id');
			param.info = sHTML;
			var myDate = new Date();
			//获取当前年
			var year=myDate.getFullYear();
			//获取当前月
			var month=myDate.getMonth()+1;
			//获取当前日
			var date=myDate.getDate(); 
			var h=myDate.getHours();       //获取当前小时数(0-23)
			var m=myDate.getMinutes();     //获取当前分钟数(0-59)
			var s=myDate.getSeconds();
			var time = year+"-"+month+"-"+date+" "+h+":"+m+":"+s;
			param.record_time=time;
			param.recorder=$.session.get('real_name');
			AjaxHelper.call({
				url : "/zncrm/rest/bus_cus/add_log",
				data : JSON.stringify(param),
				async : false,
				cache : false,
				type : "POST",
				contentType : 'application/json; charset=UTF-8',
				dataType : "html",
				success : function(result) {
					oTable.fnDraw();
					$('#summernote_1').summernote("");
					alert("添加成功");
				},
				error : function(result) {
					alert("服务器异常");
				}
			});
		});

	}

	return {

		// main function to initiate the module
		init : function() {
			handleTable();
		}

	};

}();

var Project = function() {

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
				for ( var key in result) {
					$("#" + key).text(result[key]);
				}
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
	var host = window.location.host;
    window.open("http://"+host+"/zncrm/page/bus_cus/update_project.html");
});

function retrieveData(source, data, callback) {

	var param = manager.getQueryCondition(data);
	param.project_id = $.session.get('project_id');
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


var ComponentsEditors = function () {
    
    var handleWysihtml5 = function () {
        if (!jQuery().wysihtml5) {
            return;
        }

        if ($('.wysihtml5').size() > 0) {
            $('.wysihtml5').wysihtml5({
                "stylesheets": ["../../assets/global/plugins/bootstrap-wysihtml5/wysiwyg-color.css"]
            });
        }
    }

    var handleSummernote = function () {
        $('#summernote_1').summernote({height: 300});
        //API:
        //var sHTML = $('#summernote_1').code(); // get code
        //$('#summernote_1').destroy(); // destroy
    }

    return {
        //main function to initiate the module
        init: function () {
            handleWysihtml5();
            handleSummernote();
        }
    };

}();

