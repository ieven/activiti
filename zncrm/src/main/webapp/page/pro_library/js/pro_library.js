function load_pro_library() {
	$("#mainContent").attr("src", "/zncrm/page/pro_library/pro_library.html");
}

var TableEditable = function() {

	var handleTable = function() {

		function restoreRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);

			for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
				oTable.fnUpdate(aData[i], nRow, i, false);
			}

			oTable.fnDraw();
		}

		function editRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.pro_name + '">';
			jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData[1] + '">';
			jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData[2] + '">';
			jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData[3] + '">';
			jqTds[4].innerHTML = '<a class="edit" href="">Save</a>';
			jqTds[5].innerHTML = '<a class="cancel" href="">Cancel</a>';
		}

		function saveRow(oTable, nRow) {
			var jqInputs = $('input', nRow);
			oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
			oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
			oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
			oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
			oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
			oTable.fnUpdate('<a class="delete" href="">Delete</a>', nRow, 5,
					false);
			oTable.fnDraw();
		}

		function cancelEditRow(oTable, nRow) {
			var jqInputs = $('input', nRow);
			oTable.fnUpdate(jqInputs[0].value, nRow, 0, false);
			oTable.fnUpdate(jqInputs[1].value, nRow, 1, false);
			oTable.fnUpdate(jqInputs[2].value, nRow, 2, false);
			oTable.fnUpdate(jqInputs[3].value, nRow, 3, false);
			oTable.fnUpdate('<a class="edit" href="">Edit</a>', nRow, 4, false);
			oTable.fnDraw();
		}

		var table = $('#sample_editable_1');
		
		table.on('click', '.btn-edit', function (e) {
            e.preventDefault();

            /* Get the row as a parent of the link that was clicked on */
            var nRow = $(this).parents('tr')[0];
            console.log(nRow);
            editRow(oTable, nRow);
        });

		var oTable = table
				.dataTable({

					"bServerSide" : true,// 这个用来指明是通过服务端来取数据
					"sAjaxSource" : "/zncrm/rest/pro_lib",// 这个是请求的地址
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
						data : "pro_name"
					}, {
						data : "pro_series"
					}, {
						data : "pro_out_name"
					}, {
						data : "pro_in_name"
					}, {
						data : "pro_unit"
					}, {
						data : "pro_intro"
					}, {
						data : "pro_pur_price"
					}, {
						data : "pro_assist_price"
					}, {
						data : "pro_selling_price"
					}, {
						data : "pro_linkman"
					}, {
						data : "pro_linkman_phone"
					}, {
						data : "pro_linkman_qq"
					}, {
						data : "pro_source"
					}, {
						data : null,
						defaultContent : ""
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit">修改</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						$('td', row).eq(13).append($btnEdit).append($btnDel);
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
			param.fuzzy = $("#fuzzy-search").val();
		}

		return param;
	}
};