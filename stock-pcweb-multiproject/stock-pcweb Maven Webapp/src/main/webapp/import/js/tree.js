/*
var FileSearch = (function () {
	
    var TableData;
    var treeObj;
    
    var treetableSetting = {
            id: "id",
            pId: "parentId",
            name: "name"
        };
    
    var paral = {

            "name": "名称"
        };
        
    InitTable();
    
    
    function InitTable() {
        $("#categoryTreetable tbody").html("");
        $.ajax({
            type: 'POST',
            url: getContextPath() + 'syscategory/getCategory.do',
            //  url: '../mock_data/resource.json',
            // url: '../js/demo.json',
            async: false,
            dataType: "json",
            success: function (data) {
            	//console.log(data);
                //TableData = data;//为子对象展开使用
                //treeObj = $.fn.zTree.init($("#treeResouce"), setting, TableData);//初始化treeResouce用作增加和编辑的选择框
                //treeObj.expandAll(true);//节点全部展开
                var heads = [];
                var name = treetableSetting.name;
                var id = treetableSetting.id;
                var pId = treetableSetting.pId;

                heads.push(paral[name]);
                for (var key in paral) {
                    if (key != id && key != pId && key != name) {
                        heads.push(paral[key]);
                    }
                }
                console.log(heads);
                for (var i = 0; i < data.length; i++) {
                    data[i].td = [];
                    for (var key in paral) {
                        if (key != id && key != pId && key != name) {
                            var td_obj = {};
                            td_obj[key] = data[i][key];
                            // data[i].td.push('<label name='+key+'>'+data[i][key]+'</label>');
                            data[i].td.push(td_obj);
                        }

                    }
                }
                console.log(data);

                //通过指定id和pid以及name来生成treetable
                $.TreeTable("categoryTreetable", heads, data, treetableSetting);

            }
        })
    }
        
    function editOpen(){
    	var parentId;
    	var oldTagName;
    	$('#formTreeInsert')[0].reset();
    	var tr=$("tr.selected")[0];
    	oldTagName=tr.innerText;
    	for (var i = 0; i < tr.attributes.length; i++){
    		if (tr.attributes[i].name == "data-tt-parent-id"){
    			parentId=tr.attributes[i].value;
    		}
    	}
    	
    	if(typeof(parentId) == "undefined"){
    		callAlert("不能更改一级分类");
    	}
    	else{
        	$("#treeCategory").val(parentId);
        	$("#oldTreeTagName").val(oldTagName);
        	$("#treeTagName").val(oldTagName);
        	
        	$('#treeInsert').modal({backdrop:"static"}); 
    	}
    	 	
    }
    

    
    function insertOpen(){
    	$('#formTreeInsert')[0].reset();
    	$('#treeInsert').modal({backdrop:"static"});
    }
    
    function operate(){
        var form = $('#formTreeInsert')[0];
        var formdata = new FormData(form);
        var oldTreeTagName=$('#oldTreeTagName').val();
        
        if(oldTreeTagName==''){
        	        $.ajax({
            type: 'POST',
            mimeType: "multipart/form-data",
            url: getContextPath() + "tag/addTag.do",
            data: formdata,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    // configure.configure_table.ajax.reload();
                    $("#treeInsert").modal('hide');
                    InitTable();
                    //refreshAdministration();
                }
                else
                {
                	callAlert(rsp.message);
                }
            },
            error: function (err) {
                // hideMask();
                callAlert("处理数据失败！");
            }
        });
        }
        else{
        	        $.ajax({
            type: 'POST',
            mimeType: "multipart/form-data",
            url: getContextPath() + "tag/updateTag.do",
            data: formdata,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    // configure.configure_table.ajax.reload();
                    $("#treeInsert").modal('hide');
                    InitTable();
                    //refreshAdministration();
                }
                else
                {
                	callAlert(rsp.message);
                }
            },
            error: function (err) {
                // hideMask();
                callAlert("处理数据失败！");
            }
        });
        }
        
    }
    
    function deleteTag(){
    	var parentId;
    	var tagName;
    	var tr=$("tr.selected")[0];
    	tagName=tr.innerText;
    	for (var i = 0; i < tr.attributes.length; i++){
    		if (tr.attributes[i].name == "data-tt-parent-id"){
    			parentId=tr.attributes[i].value;
    		}
    	}
    	
    	
        $.ajax({
            type: 'POST',
            contentType: "application/x-www-form-urlencoded",
            url: getContextPath() + "tag/deleteTag.do",
            data: "tagName="+tagName+"&categoryId="+parentId,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    // configure.configure_table.ajax.reload();
                    InitTable();
                    //refreshAdministration();
                }
                else
                {
                	callAlert(rsp.message);
                }
            },
            error: function (err) {
                // hideMask();
                callAlert("处理数据失败！");
            }
        });
    }
	
    return {
        // 将供页面该方法调用
    	insertOpen: insertOpen,
    	operate: operate,
    	editOpen: editOpen,
    	deleteTag: deleteTag
    };
})();*/


//初始化
$(function () {
   /* $.ajax({
        url: getContextPath() + 'fileManagement/getDataList.do',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            const categoryList = data.categoryList;
            console.log(categoryList);
            categoryDataList('treeCategory',categoryList);
            //fullDataList('source_list', resourceList, 'rescourceName', 'resourceId');

        },
        error: function () {
            callAlert("获取下拉框数据失败!");
        }
    });
    
    function categoryDataList(listId, data){
    	let content;
    	for(let i = 0; i < data.length; i++){
    		content += '<option value="' + data[i]['id'] + '" >'+data[i]['onelevelCategory']+'</option>';
    	}
    	//alert(content);
    	$('#' + listId).html(content);
    }*/
	
	function createRandomItemStyle() {
	    return {
	        normal: {
	            color: 'rgb(' + [
	                Math.round(Math.random() * 160),
	                Math.round(Math.random() * 160),
	                Math.round(Math.random() * 160)
	            ].join(',') + ')'
	        }
	    };
	}
	
    // 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('treemain')); 
    
    option = {
    	    title: {
    	        text: 'Google Trends',
    	        link: 'http://www.google.com/trends/hottrends'
    	    },
    	    tooltip: {
    	        show: true
    	    },
    	    series: [{
    	        name: 'Google Trends',
    	        type: 'wordCloud',
    	        size: ['80%', '80%'],
    	        textRotation : [0, 45, 90, -45],
    	        textPadding: 0,
    	        autoSize: {
    	            enable: true,
    	            minSize: 14
    	        },
    	        data: [
    	            {
    	                name: "Sam S Club",
    	                value: 10000,
    	                itemStyle: {
    	                    normal: {
    	                        color: 'black'
    	                    }
    	                }
    	            },
    	            {
    	                name: "Macys",
    	                value: 6181,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Amy Schumer",
    	                value: 4386,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Jurassic World",
    	                value: 4055,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Charter Communications",
    	                value: 2467,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Chick Fil A",
    	                value: 2244,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Planet Fitness",
    	                value: 1898,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Pitch Perfect",
    	                value: 1484,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Express",
    	                value: 1112,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Home",
    	                value: 965,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Johnny Depp",
    	                value: 847,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Lena Dunham",
    	                value: 582,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Lewis Hamilton",
    	                value: 555,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "KXAN",
    	                value: 550,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Mary Ellen Mark",
    	                value: 462,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Farrah Abraham",
    	                value: 366,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Rita Ora",
    	                value: 360,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Serena Williams",
    	                value: 282,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "NCAA baseball tournament",
    	                value: 273,
    	                itemStyle: createRandomItemStyle()
    	            },
    	            {
    	                name: "Point Break",
    	                value: 265,
    	                itemStyle: createRandomItemStyle()
    	            }
    	        ]
    	    }]
    	};

    // 为echarts对象加载数据 
    myChart.setOption(option); 
});
