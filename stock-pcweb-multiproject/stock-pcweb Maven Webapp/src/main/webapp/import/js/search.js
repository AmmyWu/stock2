var FileSearch = (function () {
		
    var File_table;

    var paral = {
        "fileId": "文件ID",
        "fileName": "文件名称",
        "fileFormat": "文件格式",
        "tag": "标签",
        "keyword": "关键词",
        "fileType": "文件类型",
        "resource": "文件来源",
        "publishTime": "发布时间"
    };

    Init();

    function Init() {
        File_table = $("#fileTableQuery").DataTable({
            //fnRowCallback: rightClick, //利用行回调函数，来实现右键事件
            fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            // 动态分页加载数据方式
            bProcessing: true,
            bServerSide: true,
            aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
            searching: false, // 禁用搜索
            lengthChange: true, // 是否启用改变每页显示多少条数据的控件
            /*
             * sort : "position",
             * //是否开启列排序，对单独列的设置在每一列的bSor选项中指定
             */
            deferRender: true, // 延迟渲染
            stateSave: true, //开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false, // 全局禁用排序
            scrollX: true,
            autoWidth: true,
            serverSide: true,
            colReorder: true, //列位置的拖动
            destroy: true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
            //			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                "url": getContextPath() + 'fileManagement/listByPage.do',
                "data": function (d) {
                	d.keys=JSON.stringify($('#searchFileForm1').serializeObject());
                	d.type=2;
                }

            },
            dom: '<"top">Brt<"bottom"flip><"clear">',
            language: {
                "url": "js/Chinese.json",
                select: {
                    rows: "%d 行被选中"
                },
                buttons: {
                    copyTitle: '复制到剪切板',

                    copySuccess: {
                        _: '将%d 行复制到剪切板',
                        1: '将1行复制到剪切板'
                    }
                }
            },
            columns: [{
                    "sClass": "text-center",
                    "data": "fileId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,
                    "width": "15px",
                }
            ],
            columnDefs: [{
                    orderable: false,
                    targets: 0
                },
                {
                	targets: 1,
                	"render": function (data, type, row, meta) {
                		var urlDownload = 'FileSearch.downloadById(' + row.fileId + ')';
                		var urlFindById = 'FileSearch.findById(' + row.fileId + ')';
                		return '<h4 class="list-group-item-heading text-primary" style="margin-top: 10px;">'+
                					'<a href="#" onclick="' + urlDownload + '">'+row.title+'</a>'+
                				'</h4>'+
                				'<p class="text-success">'+
                					'作者：'+row.author+
                				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                					'课题来源：'+row.resource+
                				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                					'关键字/标签：'+row.keyword+
                				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                					'<span class="text-muted">类型：'+row.fileType+'</span>'+               					
                				'</p>'+
                				'<p class="text-muted" style="white-space:normal;word-break:break-all;'+
                				'word-wrap:break-word;overflow: hidden;text-overflow:ellipsis;'+
                				'display:-webkit-box;-webkit-line-clamp:3;-webkit-box-orient:vertical;">'+
                				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                					row.summary+
                				'</p>'+
                				'<div style="float:right;margin-bottom: 10px;">发表时间：'+
                					row.publishTime+
                				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'+
                				'<button type="button" data-toggle="tooltip" data-placement="bottom" title="下载" style="padding:0px;width:20px;height:20px" class="btn btn-default btn-lg" onclick="' + urlDownload + '">'+
                				'<span class="glyphicon glyphicon-download-alt" style="font-size:12px;color: #229158;" aria-hidden="true"/></button>' +
                				'<button type="button" data-toggle="tooltip" data-placement="bottom" title="查看" style="padding:0px;width:20px;height:20px" class="btn btn-default btn-lg" onclick="' + urlFindById + '">'+
                				'<span class="glyphicon glyphicon-eye-open" style="font-size:12px;color: #229158;" aria-hidden="true"/></button>' +
                				'</div>'
                				
                	}
                	
                }
            ],
            buttons: [
/*               {
                    extend: 'excelHtml5',
                    title: '宏观经济',
                    exportOptions: {
                        columns: ':visible',
                        rows: {
                            selected: true
                        }
                    },
                    text: "选中行导出Excel",
                    container: '#File_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text: "当前页导出Excel",
                    container: '#File_export-excel-current'

                },*/
/*                {
                    text: "全量索引",
                    header: false,
                    exportOptions: {
                        columns: ':visible',
                        orthogonal: 'index'
                    },
                    container: '#File_build-index-all'
                },
                {
                    text: "增量索引",
                    container: '#File_build-index-part'

                }*/
/*                {
                    extend: 'copyHtml5',
                    text: '拷贝选中行',
                    header: false,
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            selected: true
                        },
                        orthogonal: 'copy'
                    },
                    container: '#File_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#File_export-print-all'
                },
                {
                    extend: 'print',
                    text: '打印选中行',
                    exportOptions: {
                        columns: ':visible',
                        modifier: {
                            selected: true
                        }
                    },
                    container: '#File_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#File_export-columnVisibility'
                }*/
            ],
            /*select: {
                // blurable: true,
                style: 'multi', //选中多行
                // info: false
            }*/
        });
    };
    
    //重置查询条件
    $("#resetsearchFileForm1").click(function () {

        $("#searchFileForm1")[0].reset();
        File_table.ajax.reload();
        //Init();

    })
    
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
    	//console.log(JSON.stringify($('#searchFileForm').serializeObject()))
        File_table.ajax.reload();
    }
    
    function findById(fileId){
        $.ajax({
            type: 'GET',
            url: getContextPath() + "fileManagement/getFileById.do?id=" + fileId,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                //alert(rsp);
            	$("#checkId").val(rsp.fileId);
                $("#check_type_list").val(rsp.fileTypeID);
                $("#checkCategory").val(rsp.categoryId);
                $('#checkKeywords').val(rsp.keyword);
                $('#checkTitle').val(rsp.title);
                $('#checkTheme').val(rsp.theme);
                $('#checkAuthor').val(rsp.author);
                $('#checkSource').val(rsp.resource);
                $('#checkPublishTime').val(rsp.publishTime);
                $('#checkSummary').val(rsp.summary);
                
            },
            error: function (err) {
                alert(JSON.stringify(err));
                callAlert("错误！");
            }
        });
        
        
        $('#subjectCheckModal').modal({backdrop:"static"});
    }
    
    function downloadById(fileId) {
        try {
            window.location.href = getContextPath() + "fileQuery/download.do?fileId=" + fileId;
            callSuccess("下载成功！");
        } catch (e) {
            callAlert("下载失败！");
        }
    }
    
    $.ajax({
        url: getContextPath() + 'fileManagement/getDataList.do',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            const typeList = data.typeList;
            const categoryList = data.categoryList;
            fullDataList('check_type_list', typeList, 'typeName', 'typeId');
            categoryDataList('checkCategory',categoryList);

        },
        error: function () {
            callAlert("获取下拉框数据失败!");
        }
    });
    
    function fullDataList(listId, data, labelKey, valueKey) {
        let content;
        for (let i = 0; i < data.length; i++) {
            //content += '<option label="' + data[i][labelKey] + '" value="' + data[i][valueKey] + '" />';//<option value="1">农业</option>
        	if(data[i][labelKey]=="课题")
        	{
            	content += '<option value="' + data[i][valueKey] + '" selected>'+data[i][labelKey]+'</option>';
        	}
        	else
        	{
            	content += '<option value="' + data[i][valueKey] + '" >'+data[i][labelKey]+'</option>';
        	}
        }
        $('#' + listId).html(content);
    }
    
    function categoryDataList(listId, data){
    	let content;
    	for(let i = 0; i < data.length; i++){
    		content += '<option value="' + data[i]['id'] + '" >'+data[i]['onelevelCategory']+'</option>';
    	}
    /*	for(let i = 0; i < data.length; i++){
    		content += '<optgroup label="'+data[i]['oneLevel']+'">';
    		for(let j=0;j<data[i]['twoLevel'].length;j++){
    			content += '<option value="' + data[i]['twoLevel'][j]['id'] + '" >'+data[i]['twoLevel'][j]['value']+'</option>';
    		}
    		content += '</optgroup>';
    	}*/
    	//alert(content);
    	$('#' + listId).html(content);
    }
    
    return {
        // 将供页面该方法调用
    	doSearch: doSearch,
    	downloadById: downloadById,
    	findById: findById
    };
})();