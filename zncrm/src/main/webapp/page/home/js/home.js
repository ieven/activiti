var Charts = function() {
	
	var handleChart = function(days) {
		// 基于准备好的dom，初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('main1'));
        var myChart2 = echarts.init(document.getElementById('main2'));

        // 指定图表的配置项和数据
        var option1 = {
            title: {
                text: '潜在客户统计'
            },
            tooltip: {},
            grid: {
            	y2: 140
            },
            xAxis: {
            	axisLabel:{  
                    interval:0,//横轴信息全部显示  
                    rotate:-30,//-30度角倾斜显示  
               }
            },
            yAxis: {},
            series: [{
                name: '销量',
                type: 'bar',
                barWidth : 10
            }]
        };
        var param1 = {};
        param1.cur_status = '潜在';
        param1.days = days;
        AjaxHelper.call({
    		url : "/zncrm/rest/home",
    		data : JSON.stringify(param1),
    		async : false,
    		cache : false,
    		type : "POST",
    		contentType : 'application/json; charset=UTF-8',
    		dataType : "html",
    		success : function(result) {
    			result = eval("(" + result + ")");
    			result = result.DATA;
    			var xData = new Array();
    			var yData = new Array();
    			for(var i in result){
    				xData[i]=result[i].recorder;
    				yData[i]=result[i].num;
    			}
    			option1.xAxis.data = xData;
    			option1.series[0].data = yData;
    			
    		},
    		error : function(result) {
    			alert("服务器异常");
    		}
    	});
        
        // 使用刚指定的配置项和数据显示图表。
//        myChart1.setOption(option1);
        myChart1.setOption(option1, true);
        
        var option2 = {
                title: {
                    text: '成交客户统计'
                },
                tooltip: {},
//                legend: {
//                    data:['销量']
//                },
                grid: {
                	y2: 140
                },
                xAxis: {
                	axisLabel:{  
                        interval:0,//横轴信息全部显示  
                        rotate:-30,//-30度角倾斜显示  
                   }
                },
                yAxis: {},
                series: [{
                    name: '销量',
                    type: 'bar',
                    barWidth : 10
                }]
            };
        
        var param2 = {};
        param2.cur_status = '成交';
        param2.days = days;
        AjaxHelper.call({
    		url : "/zncrm/rest/home",
    		data : JSON.stringify(param2),
    		async : false,
    		cache : false,
    		type : "POST",
    		contentType : 'application/json; charset=UTF-8',
    		dataType : "html",
    		success : function(result) {
    			result = eval("(" + result + ")");
    			result = result.DATA;
    			var xData = new Array();
    			var yData = new Array();
    			for(var i in result){
    				xData[i]=result[i].recorder;
    				yData[i]=result[i].num;
    			}
    			option2.xAxis.data = xData;
    			option2.series[0].data = yData;
    			
    		},
    		error : function(result) {
    			alert("服务器异常");
    		}
    	});
        
        myChart2.setOption(option2);
        
	}

	return {

		// main function to initiate the module
		init : function() {
			handleChart(30);
		},
		
		change_days : function(){
			var days = $("#days").find("option:selected").val();
			handleChart(days);
		}

	};

}();
