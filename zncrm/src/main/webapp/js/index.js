jQuery(document).ready(function() {    
   Metronic.init(); // init metronic core componets
   Layout.init(); // init layout
   Index.init();   
   Index.initDashboardDaterange();
   Index.initJQVMAP(); // init index page's custom scripts
   Index.initMiniCharts();
   Tasks.initDashboardWidget();
   load_menu($.session.get('username'));
   $("#mainContent").attr("src","/activiti-webapp-explorer2/zncrmlogin="+$.session.get('username'));
   var picAddress = "/zncrm/rest/get_pic/"+$.session.get('picId');
   $("#picId").attr("src",picAddress);
   $("#username").html($.session.get('username'));
});

function load_page(url) {
	$("#mainContent").attr("src", url);
}

function logout() {
	$("#mainContent").attr("src","/activiti-webapp-explorer2/zncrmlogout");
	window.location.href = "/zncrm/";
}

function load_menu(username){
	var sb=new StringBuffer();
	sb.append('<!-- DOC: To remove the sidebar toggler from the sidebar you just need to completely remove the below "sidebar-toggler-wrapper" LI element -->'+
			'<li class="sidebar-toggler-wrapper">'+
			'<div class="sidebar-toggler">'+
			'</div>'+
		'</li>'+
		'<li class="sidebar-search-wrapper">'+
			'<form class="sidebar-search " action="extra_search.html" method="POST">'+
				'<a href="javascript:;" class="remove">'+
				'<i class="icon-close"></i>'+
				'</a>'+
				'<div class="input-group">'+
					'<input type="text" class="form-control" placeholder="Search...">'+
					'<span class="input-group-btn">'+
					'<a href="javascript:;" class="btn submit"><i class="icon-magnifier"></i></a>'+
					'</span>'+
				'</div>'+
			'</form>'+
		'</li>');
	
	AjaxHelper.call({
		
		url: "rest/menu",
	    data: JSON.stringify({"username":username}),
	    async:false,
	    cache:false,
	    type: "POST",
	    contentType:'application/json; charset=UTF-8',
	    dataType: "html",
	    success: function (result) {
	    	result = eval("(" + result + ")");
	    	if(result.CODE == "1"){
	    		result = result.DATA;
	    		for(var key in result){
	    			sb.append(installMenu(result[key]).toString());
	    		}
            }else{
            	alert(result.MSG);
            }
	    },
	    error:function(result){
	    	alert("服务器异常");
	    }
	});
	
	$("#page-sidebar-menu").html(sb.toString());
}

function installMenu(result){
	var sb=new StringBuffer();
	if(result.children.length==0){
		sb.append(
				'<li>'+
				'<a href="#" onclick="load_page(\''+result.menu_url+'\')">'+
				'<i class="icon-basket"></i>'+
				result.text+'</a>'+
				'</li>');
		return sb;
	}else{
		sb.append(
				'<li>'+
				'<a href="javascript:;">'+
				'<i class="icon-basket"></i>'+
				'<span class="title">'+result.text+'</span>'+
				'<span class="arrow "></span>'+
				'</a>');
		sb.append('<ul class="sub-menu">');
		for(id in result.children){
			sb.append(installMenu(result.children[id]));
		}
		sb.append('</ul>');
		sb.append('</li>')
		return sb;
	}
}