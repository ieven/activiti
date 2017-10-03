function load_stock_list() {
	$("#mainContent").attr("src", "/zncrm/page/ku_cun/stock_list.html");
}

var TableEditable = function() {
	
	var handleTable = function() {

		function restoreRow(oTable, nRow) {
			
			var jqInputs = $('input', nRow);
			console.log(jqInputs);
			var param = {};
            for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
            	oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
            	param[jqInputs[i].name]=jqInputs[i].value;
            }

			AjaxHelper.call({
				url : "/zncrm/rest/stock_list",
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
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].innerHTML = '<input readonly="readonly" type="text" class="form-control input-small" value="'
					+ aData.id + '" name="id" >';
			jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.productName + '" name="productName">';
			jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.model + '" name="model">';
			jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.unit + '" name="unit">';
			jqTds[4].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.storeNumber + '" name="storeNumber">';
			jqTds[5].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.purchasingPrice + '" name="purchasingPrice">';
			jqTds[6].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.owner + '" name="owner">';
			jqTds[7].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.taskTime + '" name="taskTime">';
			jqTds[8].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.remark + '" name="remark">';
			jqTds[9].innerHTML='<button type="button" class="btn btn-small btn-primary btn-edit">保存</button>'+'<a class="btn btn-small btn-danger btn-cancel" href="">取消</a>';
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
			var nRow = $(this).parents('tr')[0];
			var aData = oTable.fnGetData(nRow);
			var param = {};
			param.pro_id = aData.pro_id;
		    AjaxHelper.call({
				url : "/zncrm/rest/pro_lib/"+aData.pro_id,
				data : JSON.stringify(param),
				async : false,
				cache : false,
				type : "GET",
				contentType : 'application/json; charset=UTF-8',
				dataType : "html",
				success : function(result) {
					result = eval("(" + result + ")");
					result = result.DATA;
					$("#pro_linkman").text(result.pro_linkman);
					$("#pro_linkman_phone").text(result.pro_linkman_phone);
					$("#pro_linkman_qq").text(result.pro_linkman_qq);
					$("#pro_source").text(result.pro_source);
				},
				error : function(result) {
					alert("服务器异常");
				}
			});
		    var picAddress = "/zncrm/rest/pro_lib/get_pic/"+aData.pro_id;
		    $("#pro_pic").attr("src",picAddress);
		    $("#fileupload").attr("action","/zncrm/rest/pro_lib/add_pic/"+aData.pro_id);
			
        });
		
		table.on('click', '.btn-cancel', function (e) {
            e.preventDefault();
            oTable.fnDraw();
        });
		
		table.on('click', '.btn-del', function (e) {
            e.preventDefault();
            if(confirm("确定删除？")){
            	var nRow = $(this).parents('tr')[0];
            	var jqInputs = $('td', nRow);
            	var param = {};
            	param.id = jqInputs[0].innerText;
            	AjaxHelper.call({
    				url : "/zncrm/rest/stock_list",
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
					"sAjaxSource" : "/zncrm/rest/stock_list",// 这个是请求的地址
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
						data : "productName"
					}, {
						data : "model"
					}, {
						data : "unit"
					},{
						data : "storeNumber"
					}, {
						data : "purchasingPrice"
					}, {
						data : "owner"
					}, {
						data : "taskTime"
					}, {
						data : "remark"
					}, {
						data : null,
						defaultContent : ""
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit">修改</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						$('td', row).eq(9).append($btnEdit).append($btnDel);
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
		url : "/zncrm/rest/stock_list/add",
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

var FormFileUpload = function () {

    return {
        //main function to initiate the module
        init: function () {

             // Initialize the jQuery File Upload widget:
            $('#fileupload').fileupload({
                disableImageResize: false,
                autoUpload: false,
                disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
                maxFileSize: 5000000,
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png|xlsx|xls|ppt|doc)$/i,
                // Uncomment the following to send cross-domain cookies:
                //xhrFields: {withCredentials: true},                
            });

            // Enable iframe cross-domain access via redirect option:
            $('#fileupload').fileupload(
                'option',
                'redirect',
                window.location.href.replace(
                    /\/[^\/]*$/,
                    '/cors/result.html?%s'
                )
            );

            // Upload server status check for browsers with CORS support:
            if ($.support.cors) {
                $.ajax({
                    type: 'HEAD'
                }).fail(function () {
                    $('<div class="alert alert-danger"/>')
                        .text('Upload server currently unavailable - ' +
                                new Date())
                        .appendTo('#fileupload');
                });
            }

            // Load & display existing files:
            $('#fileupload').addClass('fileupload-processing');
            $.ajax({
                // Uncomment the following to send cross-domain cookies:
                //xhrFields: {withCredentials: true},
                url: $('#fileupload').attr("action"),
                dataType: 'multipart/form-data',
                context: $('#fileupload')[0]
            }).always(function () {
                $(this).removeClass('fileupload-processing');
            }).done(function (result) {
                $(this).fileupload('option', 'done')
                .call(this, $.Event('done'), {result: result});
            });
        }

    };

}();