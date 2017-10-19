var TableEditable = function() {
	
	var handleTable = function() {

		var table = $('#sample_editable_1');
		
		table.on('click', '.btn-edit', function (e) {
            e.preventDefault();
            manager.updateUser = true;
            var nRow = $(this).parents('tr')[0];
        	var jqInputs = $('td', nRow);
        	var param = {};
        	param.username = jqInputs[0].innerText;
        	
        	AjaxHelper.call({
				url : "/zncrm/rest/user/get",
				data : JSON.stringify(param),
				async : false, 
				cache : false,
				type : "POST",
				contentType : 'application/json; charset=UTF-8',
				dataType : "html",
				success : function(result) {
					result = eval("(" + result + ")");
	    			result = result.DATA;
	    			for(var key in result){
	    				$("#"+key).val(result[key]);
	    			}
				},
				error : function(result) {
					alert("服务器异常");
				}
			});
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
					"sAjaxSource" : "/zncrm/rest/user",// 这个是请求的地址
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
						data : "username"
					}, {
						data : "password"
					}, {
						data : "real_name"
					}, {
						data : "phone"
					}, {
						data : "job"
					}, {
						data : "role_id"
					}, {
						data : "status",
						render: function (data, type, row, meta) {
							if(data=='-1'){
								return '<span class="label label-sm label-danger">离职</span>';
							}else{
								return '<span></span>';
							}
	                    }
					}, {
						data : null,
						defaultContent : ""
					} ],
					"createdRow" : function(row, data, index) {
						// 行渲染回调,在这里可以对该行dom元素进行任何操作
						// 不使用render，改用jquery文档操作呈现单元格
						var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit" data-toggle="modal" href="#responsive">修改</button>');
						var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
						$('td', row).eq(7).append($btnEdit).append($btnDel);
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
    var jqSelect = $('select', body);
    for (var i = 0, iLen = jqSelect.length; i < iLen; i++) {
    	param[jqSelect[i].name]=jqSelect[i].value;
    }
    if(!manager.updateUser){
    	AjaxHelper.call({
    		url : "/zncrm/rest/user/add",
    		data : JSON.stringify(param),
    		async : false,
    		cache : false,
    		type : "POST",
    		contentType : 'application/json; charset=UTF-8',
    		dataType : "html",
    		success : function(result) {
    			result = eval("(" + result + ")");
    			result = result.CODE;
    			if(result=='-2'){
    				alert("创建失败，请检查用户名是否重复");
    			}
    			else{
    				for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
    			    	jqInputs[i].value="";
    			    }
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
    }else{
    	AjaxHelper.call({
    		url : "/zncrm/rest/user",
    		data : JSON.stringify(param),
    		async : false,
    		cache : false,
    		type : "PUT",
    		contentType : 'application/json; charset=UTF-8',
    		dataType : "html",
    		success : function(result) {
    			result = eval("(" + result + ")");
    			result = result.CODE;
    			if(result=='-2'){
    				alert("更新失败");
    			}
    			else{
    				for (var i = 0, iLen = jqInputs.length; i < iLen; i++) {
    			    	jqInputs[i].value="";
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
	param.parent_id="5";
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
	updateUser : false,
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

function setManager(){
	manager.updateUser=false;
	
}

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


var SelectContent = function() {
	
	var handleSelect = function() {
		AjaxHelper.call({
			url : "/zncrm/rest/user/job",
			data : null,
			async : false,
			cache : false,
			type : "GET",
			contentType : 'application/json; charset=UTF-8',
			dataType : "html",
			success : function(result) {
				result = eval("(" + result + ")");
				result = result.DATA;
				//封装返回数据
				var sb=new StringBuffer();
				for(var key in result){
	    			sb.append('<option value="'+result[key].job_name+'">'+result[key].job_name+'</option>');
	    		}
				$("#job").html(sb.toString());
			},
			error : function(result) {
				alert("服务器异常");
			}
		});
		
		AjaxHelper.call({
			url : "/zncrm/rest/role",
			data : null,
			async : false,
			cache : false,
			type : "GET",
			contentType : 'application/json; charset=UTF-8',
			dataType : "html",
			success : function(result) {
				result = eval("(" + result + ")");
				result = result.DATA;
				//封装返回数据
				var sb=new StringBuffer();
				for(var key in result){
	    			sb.append('<option value="'+result[key].id+'">'+result[key].id+'</option>');
	    		}
				$("#role_id").html(sb.toString());
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