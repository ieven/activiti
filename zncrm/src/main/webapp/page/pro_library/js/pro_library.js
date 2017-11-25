var TableEditable = function() {
	
	var handleTable = function() {

		function restoreCallRow(oTable, nRow) {
			
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
			$("#pro_name_2").val(aData.pro_name_2);
			$("#pro_place").val(aData.pro_place);
			$("#pro_out_name").val(aData.pro_out_name);
			$("#pro_in_name").val(aData.pro_in_name);			
			$("#pro_unit").val(aData.pro_unit);
			$("#pro_intro").val(aData.pro_intro);
			$("#pro_pur_price").val(aData.pro_pur_price);
			$("#pro_assist_price").val(aData.pro_assist_price);			
			$("#pro_selling_price").val(aData.pro_selling_price);
			$("#fileupload").attr("action","/zncrm/rest/pro_lib/add_pic/"+aData.pro_id);
			if(aData.is_hot=='1'){
				$("#is_hot").html('<input type="checkbox" class="checkboxes" value="'
						+ aData.is_hot + '" name="is_hot" checked="true">');
			}
			else{
				$("#is_hot").html('<input type="checkbox" class="checkboxes" value="'
						+ aData.is_hot + '" name="is_hot">');
			}
			$.session.set('pro_id',aData.pro_id);
			//将五个说明分开显示
			var pro_intro_list = aData.pro_intro.split(";");
			for(var key in pro_intro_list){
				var temp = pro_intro_list[key].split(":");
				var temp_key = parseInt(key) +1;
				$("#pro_intro"+temp_key+"_key").val(temp[0]);
				$("#pro_intro"+temp_key+"_value").val(temp[1]);
			}
		}

		var table = $('#sample_editable_1');
		
		table.on('click', '.btn-edit', function (e) {
            e.preventDefault();
            	var nRow = $(this).parents('tr')[0];
            	editRow(oTable, nRow);      	
                        
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
            	
            	var length = jqInputs.length-1;
            	param.pro_id = jqInputs[length].innerText;
            	AjaxHelper.call({
    				url : "/zncrm/rest/pro_lib",
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
					"columns" : [ 
		               {
						data : "pro_name_2"
					}, {
						data : "pro_out_name",
						createdCell: function (td, cellData, rowData, row, col) {
							if(manager.showPrice){
							}else{
								$(td).css('display','none');
							}
						},
						render: function (data, type, row, meta) {
							if(manager.showPrice){
								return '<span>'+data+'</span>';
							}else{
								$("#pro_out_name_title").css('display','none');
								return '<span></span>';
							}
	                    }
					}, {
						data : "pro_in_name"
					}, {
						data : "pro_place"
					}, {
						data : "pro_unit"
					}, {
						data : "pro_intro"
					}, {
						data : "pro_pur_price",
						createdCell: function (td, cellData, rowData, row, col) {
							if(manager.showPrice){
							}else{
								$(td).css('display','none');
							}
						},
						render: function (data, type, row, meta) {
							if(manager.showPrice){
								return '<span>'+data+'</span>';
							}else{
								$("#pro_pur_price_title").css('display','none');
								$(".pro_pur_price_class").css('display','none');
								return '<span style="display: none"></span>';
							}
	                    }
					}, {
						data : "pro_assist_price",
						createdCell: function (td, cellData, rowData, row, col) {
							if(manager.showPrice){
							}else{
								$(td).css('display','none');
							}
						},
						render: function (data, type, row, meta) {
							if(manager.showPrice){
								return '<span>'+data+'</span>';
							}else{
								$("#pro_assist_price_title").css('display','none');
								return '<span style="display: none"></span>';
							}
	                    }
					}, {
						data : "pro_selling_price"
					}, {
	                    orderable: false,
	                    data: null,
	                    render: function (data, type, row, meta) {
	                        return '<img id="pro_pic" src="/zncrm/rest/pro_lib/get_pic/'+data.pro_id+'" alt="图片未上传" class="img-responsive">';
	                    }
					}, {
						data : "is_hot",
						render: function (data, type, row, meta) {
							if(data=='1'){
								return '<span class="label label-sm label-danger">主推</span>';
							}else{
								return '<span></span>';
							}
	                    }
					}, {
						data : null,
						defaultContent : ""
					}, {
						data : "pro_id",
						className : "id_display"
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit" data-toggle="modal" href="#edit_responsive">修改</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						if(manager.showUpdate){
							$('td', row).eq(11).append($btnEdit);
						}
						if(manager.showDel){
							$('td', row).eq(11).append($btnDel);
						}
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

$('#edit_save_button').click(function restoreRow(e) {
	var body = $(this).parents('div')[1];
    var jqInputs = $('input', body);
	var param = {};
    for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
    	if(jqInputs[i].name=='is_hot'){
    		if(jqInputs[i].checked){
    			param[jqInputs[i].name]='1';
    		}else{
    			param[jqInputs[i].name]='-1';
    		}
    	}else{
    		param[jqInputs[i].name]=jqInputs[i].value;
    	}
    }
    param.pro_id = $.session.get('pro_id');
    //拼接五个说明
    var pro_intro1_key = $('#pro_intro1_key').val();
    var pro_intro1_value = $('#pro_intro1_value').val();
    var pro_intro2_key = $('#pro_intro2_key').val();
    var pro_intro2_value = $('#pro_intro2_value').val();
    var pro_intro3_key = $('#pro_intro3_key').val();
    var pro_intro3_value = $('#pro_intro3_value').val();
    var pro_intro4_key = $('#pro_intro4_key').val();
    var pro_intro4_value = $('#pro_intro4_value').val();
    var pro_intro5_key = $('#pro_intro5_key').val();
    var pro_intro5_value = $('#pro_intro5_value').val();
    param.pro_intro = pro_intro1_key+":"+pro_intro1_value+";"+pro_intro2_key+":"+pro_intro2_value+";"+pro_intro3_key+":"+pro_intro3_value+";"+
    				  pro_intro4_key+":"+pro_intro4_value+";"+pro_intro5_key+":"+pro_intro5_value;
    
	AjaxHelper.call({
		url : "/zncrm/rest/pro_lib",
		data : JSON.stringify(param),
		async : false,
		cache : false,
		type : "PUT",
		contentType : 'application/json; charset=UTF-8',
		dataType : "html",
		success : function(result) {
			for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
		    	jqInputs[i].value="";
		    }
			$('#edit_responsive').modal('hide');
			alert("修改成功");
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
});

$('#save_button').click(function (e) {
    var body = $(this).parents('div')[1];
    var jqInputs = $('input', body);
    var param = {};
    for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
    	param[jqInputs[i].name]=jqInputs[i].value;
    }
    //拼接那五个输入框
    var pro_intro1_key = $('#pro_intro1_save_key').val();
    var pro_intro1_value = $('#pro_intro1_save_value').val();
    var pro_intro2_key = $('#pro_intro2_save_key').val();
    var pro_intro2_value = $('#pro_intro2_save_value').val();
    var pro_intro3_key = $('#pro_intro3_save_key').val();
    var pro_intro3_value = $('#pro_intro3_save_value').val();
    var pro_intro4_key = $('#pro_intro4_save_key').val();
    var pro_intro4_value = $('#pro_intro4_save_value').val();
    var pro_intro5_key = $('#pro_intro5_save_key').val();
    var pro_intro5_value = $('#pro_intro5_save_value').val();
    param.pro_intro = pro_intro1_key+":"+pro_intro1_value+";"+pro_intro2_key+":"+pro_intro2_value+";"+pro_intro3_key+":"+pro_intro3_value+";"+
    				  pro_intro4_key+":"+pro_intro4_value+";"+pro_intro5_key+":"+pro_intro5_value;
    
    param.pro_name_id = $.session.get('menu_id');
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
	if(!manager.fuzzySearch){
		param.pro_name_id = $.session.get('menu_id');
	}
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
            AuthInit.init();
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
	showPrice : false,
	showUpdate : false,
	showDel : false,
	showLinkman : false,
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
			if($("#search_key").val()!=""){
				param.search_key = $("#search_key").val();
			}
			else{
				manager.fuzzySearch = false;
			}
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


var TableEditable2 = function() {
	
	var handleTable = function() {

		function restoreCallRow(oTable, nRow) {
			
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
		
		function restoreRow(oTable, nRow) {
			
			var jqInputs = $('input', nRow);
			var param = {};
            for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
            	oTable.fnUpdate(jqInputs[i].value, nRow, i, false);
            	param[jqInputs[i].name]=jqInputs[i].value;
            }

			AjaxHelper.call({
				url : "/zncrm/rest/pro_lib/link_man",
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
				+ aData.link_man_name + '" name="link_man_name">';
			jqTds[1].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.link_man_phone + '" name="link_man_phone">';
			jqTds[2].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.link_man_qq + '" name="link_man_qq">';
			jqTds[3].innerHTML = '<input type="text" class="form-control input-small" value="'
				+ aData.link_man_source + '" name="link_man_source">';
			jqTds[4].innerHTML='<button type="button" class="btn btn-small btn-primary btn-edit">保存</button>'+'<a class="btn btn-small btn-danger btn-callcancel" href="">取消</a>';
			jqTds[5].innerHTML = '<input type="text" class="form-control input-small" style="display:none" value="'
				+ aData.link_man_id + '" name="link_man_id">';
		}

		var table = $('#sample_editable_2');
		
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
        });
		
		table.on('click', '.btn-cancel', function (e) {
            e.preventDefault();
            oTable.fnDraw();
        });
		
		table.on('click', '.btn-del', function (e) {
            e.preventDefault();
            if(confirm("确定删除联系人？")){
            	var nRow = $(this).parents('tr')[0];
            	var jqInputs = $('td', nRow);
            	var param = {};
            	
            	var length = jqInputs.length-1;
            	param.link_man_id = jqInputs[length].innerText;
            	AjaxHelper.call({
    				url : "/zncrm/rest/pro_lib/link_man",
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
					"sAjaxSource" : "/zncrm/rest/pro_lib/link_man",// 这个是请求的地址
					"fnServerData" : retrieveData2,
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
					"columns" : [ 
		               {
						data : "link_man_name"
					}, {
						data : "link_man_phone"
					}, {
						data : "link_man_qq"
					}, {
						data : "link_man_source"
					}, {
						data : null,
						defaultContent : ""
					}, {
						data : "link_man_id",
						className : "id_display"
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit">修改</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						if(manager.showUpdate){
							$('td', row).eq(4).append($btnEdit);
						}
						if(manager.showDel){
							$('td', row).eq(4).append($btnDel);
						}
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

$('#save_callbutton').click(function (e) {
    var body = $(this).parents('div')[1];
    var jqInputs = $('input', body);
    var param = {};
    for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
    	param[jqInputs[i].name]=jqInputs[i].value;
    }
    param.pro_name_id = $.session.get('menu_id');
    AjaxHelper.call({
		url : "/zncrm/rest/pro_lib/link_man/add",
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
			$('#callresponsive').modal('hide');
			alert("创建成功");
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
});

function retrieveData2(source, data, callback) {

	var param = manager.getQueryCondition(data);
	param.pro_name_id = $.session.get('menu_id');
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
            AuthInit.init();
			callback(returnData);
		},
		error : function(result) {
			alert("服务器异常");
		}
	});
}


var AuthInit = function() {
	
	var handleAuth = function() {
		var authArray = $.session.get('authorities').split(",");
		//添加权限
		if($.inArray("7", authArray)==-1){
			$("#add_pro_library").hide();
			$("#add_pro_linkman").hide();
		}else{
			$("#add_pro_library").show();
			$("#add_pro_linkman").show();
		}
		//修改权限
		if($.inArray("8", authArray)!=-1){
			manager.showUpdate = true;
		}
		//删除权限
		if($.inArray("9", authArray)!=-1){
			manager.showDel = true;
		}
		//显示价格
		if($.session.get('role_id')=='admin'||$.session.get('role_id')=='财务'){
			manager.showPrice = true;
		}
		//显示联系人
		if($.inArray("22", authArray)!=-1){
			manager.showLinkman = true;
			$("#pro_lib_linkman").show();
		}else{
			$("#pro_lib_linkman").hide();
		}
	}

	return {

		// main function to initiate the module
		init : function() {
			handleAuth();
		}

	};

}();