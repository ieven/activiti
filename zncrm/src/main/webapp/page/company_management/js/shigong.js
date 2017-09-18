function load_shigong() {
	$("#mainContent").attr("src", "/zncrm/page/company_management/shigong.html");
}

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

		function editRow(oTable, nRow) {
			var aData = oTable.fnGetData(nRow);
			var jqTds = $('>td', nRow);
			jqTds[0].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.pro_name + '" name="pro_name" >';
			jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.pro_series + '" name="pro_series">';
			jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.pro_out_name + '" name="pro_out_name">';
			jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="'
					+ aData.pro_in_name + '" name="pro_in_name">';
			jqTds[4].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.pro_unit + '" name="pro_unit">';
			jqTds[5].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.pro_intro + '" name="pro_intro">';
			jqTds[6].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.pro_pur_price + '" name="pro_pur_price">';
			jqTds[7].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.pro_assist_price + '" name="pro_assist_price">';
			jqTds[8].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.pro_selling_price + '" name="pro_selling_price">';
//			jqTds[9].innerHTML = '<input type="text" class="form-control input-small" value="'
//				+ aData.pro_linkman + '" name="pro_linkman">';
//			jqTds[10].innerHTML = '<input type="text" class="form-control input-small" value="'
//				+ aData.pro_linkman_phone + '" name="pro_linkman_phone">';
//			jqTds[11].innerHTML = '<input type="text" class="form-control input-small" value="'
//				+ aData.pro_linkman_qq + '" name="pro_linkman_qq">';
//			jqTds[12].innerHTML = '<input type="text" class="form-control input-small" value="'
//				+ aData.pro_source + '" name="pro_source">';
			jqTds[9].innerHTML='<button type="button" class="btn btn-small btn-primary btn-edit">保存</button>'+'<a class="btn btn-small btn-danger btn-cancel" href="">取消</a>';
			jqTds[10].innerHTML = '<input type="text" class="form-control input-small" style="display:none" value="'
				+ aData.pro_id + '" name="pro_id">';
		}

		var table = $('#sample_editable_1');
		
		table.on('click', '.btn-download', function (e) {
            e.preventDefault();
            var nRow = $(this).parents('tr')[0];
			var aData = oTable.fnGetData(nRow);
            var host = window.location.host;
            window.open("http://"+host+"/zncrm/rest/file/"+aData.file_id);
        });
		
		table.on('click', '.btn-more', function (e) {
			var nRow = $(this).parents('tr')[0];
			var aData = oTable.fnGetData(nRow);
			var host = window.location.host;
			window.open("http://view.officeapps.live.com/op/view.aspx?src="+encodeURIComponent("http://"+host+"/zncrm/rest/file/"+aData.file_id));
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
            	param.file_id = jqInputs[0].innerText;
            	AjaxHelper.call({
    				url : "/zncrm/rest/file",
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
					"sAjaxSource" : "/zncrm/rest/file/"+menu.id,// 这个是请求的地址
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
						data : "file_id"
					}, {
						data : "title"
					}, {
						data : "author"
					}, {
						data : "last_modify"
					}, {
						data : null,
						defaultContent : ""
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-download">下载</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						var $btnMore = $('<button type="button" class="btn btn-small btn-danger btn-more">预览</button>');
						$('td', row).eq(4).append($btnEdit).append($btnDel).append($btnMore);
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
	$("#fileupload").attr("action","/zncrm/rest/file/add?username="+$.session.get('username')+"&menu_id="+menu.id);
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

var menu = {
		id:"6"
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
                acceptFileTypes: /(\.|\/)(gif|jpe?g|png|xlsx|xls|ppt|doc|docx)$/i,
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
