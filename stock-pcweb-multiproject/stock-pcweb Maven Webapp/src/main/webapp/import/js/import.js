//@ sourceURL=File.js
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
var File = (function () {
    $.validator.setDefaults({
        submitHandler: submitEditFileModal
    });
    
       
    function test(e)
    {
    	console.log($(e));
    	var text=$(e)[0].text;
    	var value=$("#keywords").val();
    	var editValue=$("#editKeywords").val();
    	value +=text+";";
    	editValue += text+";";
    	$("#keywords").val(value);
    	$("#editKeywords").val(editValue);
    }

    $(function () {

        //初始化组织架构树
        //   InitOrganizationZtree();

        //Date picker //时间控件
        $(".time").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        });
    });

    $().ready(
        function validateFileForm() {
            $("#editFileForm").validate({
                rules: {
                    fileName: {
                        required: true,
                        maxlength: 20
                    },
                    fileFormat: {
                        required: true,
                        maxlength: 20
                    },
                    tag: {
                        required: true,
                        maxlength: 20
                    },
                    keyword: {
                        maxlength: 60
                    },
                    fileType: {
                        maxlength: 20
                    },
/*                    resource: {
                        maxlength: 20
                    },*/
                    publishTime: {
                        maxlength: 20
                    }
                    //hireDate: "雇佣日期",
                    //birthday: "出生年月"
                },
                messages: {

                }
            });
        }
    );
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
        File_table = $("#fileTable").DataTable({
            fnRowCallback: rightClick, //利用行回调函数，来实现右键事件
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
                	d.keys=JSON.stringify($('#searchFileForm').serializeObject());
                	d.type=1;
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

                },
                {
                    title: "课题名称",
                    data: "title",
                    "render": function (data, type, full, meta) {
                    	return '<div style="width:150px;overflow: hidden;text-overflow: ellipsis;" data-toggle="tooltip" data-placement="right" title="'+data+'">'+data+'</div>'
                    }
                },
/*                {
                    title: "文档格式",
                    data: "fileFormat"
                },*/
/*                {
                    title: "文档标签",
                    data: "tag"
                },*/
                {
                    title: "关键词",
                    data: "keyword",
                    "render": function (data, type, full, meta){
                    	return '<div style="width:150px;overflow: hidden;text-overflow: ellipsis;" data-toggle="tooltip" data-placement="right" title="'+data+'">'+data+'</div>'
                    }
                },
                {
                    title: "课题分类",
                    data: "fileCategory"
                },
/*                {
                    title: "课题类型",
                    data: "fileType"
                },*/
/*                {
                    title: "课题来源",
                    data: "resource",
                },*/
                {
                    title: "作者",
                    data: "author"
                },
                {
                    title: "摘要",
                    data: "summary",
                    "render": function (data, type, full, meta) {
                    	return '<div style="width:150px;overflow: hidden;text-overflow: ellipsis;" data-toggle="tooltip" data-placement="right" title="'+data+'">'+data+'</div>'
                    }                	
                },
                {
                    title: "课题上传时间",
                    data: "publishTime"
                },
                {
                    title: "操作",
                    data: "fileId",
                    "render": function (data, type, full, meta) {
                    	//console.log(full);
                    	//console.log(data);
                        var urlDownload = 'File.downloadById(' + data + ')';
                        var urlDelete = 'File.deleteById(' + data + ')';
                        var urlEdit='File.editById(' + data + ')';

                        return '<button type="button" style="padding:0px;width:20px;height:20px" class="btn btn-default btn-lg" onclick="' + urlDownload + '"><span class="glyphicon glyphicon-download-alt" style="font-size:12px;color: #229158;" aria-hidden="true"/></button>' +
                            '<button type="button" style="padding:0px;width:20px;;height:20px" class="btn btn-default btn-lg" onclick="' + urlDelete + '"><span class="glyphicon glyphicon-trash" style="font-size:12px;color:#b23418;" aria-hidden="true"/></button>'+
                            '<button type="button" style="padding:0px;width:20px;;height:20px" class="btn btn-default btn-lg" onclick="' + urlEdit + '"><span class="glyphicon glyphicon-edit" style="font-size:12px;color:#b23418;" aria-hidden="true"/></button>';
                    }
                }

                // {
                //     title: "最新修改人",
                //     "data": "baseModel",
                //
                //     "render": function (data, type, full, meta) {
                //         return (data == null || data == undefined ) ? '' : data.creatorName;
                //     },
                //     // "bSortable": false,
                // },

            ],
            columnDefs: [{
                    orderable: false,
                    targets: 0
                },
                {
                    "render": function (data, type, full, meta) {
                        if ($.string.isNullOrEmpty(data))
                            return "";
                        else if (type === 'display')
                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' : data;
                        else if (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data + "  ";
                        }
                        return data;
                    },
                    targets: [1, 2, 3, 4, 5, 6]
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
                {
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

                }
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
            select: {
                // blurable: true,
                style: 'multi', //选中多行
                // info: false
            }
        });
    }
    
    var tree = [
                {
                  text: "农林牧渔/科学/卫生研究",
                  nodes: [
                    {
                    	id:1,
                      text: "农业",
/*                      nodes: [
                        {
                          text: "Grandchild 1"
                        },
                        {
                          text: "Grandchild 2"
                        }
                      ]*/
                    },
                    {
                    	id:2,
                      text: "食品"
                    }
                  ]
                },
                {
                  text: "Parent 2"
                }
              ];
    
    $("#txt_departmentname").click(function() {  
        var options = {  
            bootstrap2 : false,  
            showTags : true,  
            levels : 5,  
            showCheckbox : true,  
            checkedIcon : "glyphicon glyphicon-check",  
            data : tree,  
            onNodeSelected : function(event, data) {  
                $("#txt_departmentname").val(data.text);  
                $("#treeview").hide();  
            }  
        };  
  
        $('#treeview').treeview(options);  
    });

    // select/not select all
    $('body').on('click', '.File .checkall', function () {
        var check = $(this).prop("checked");
        $(".File .checkchild").prop("checked", check);
        $("#fileTable tbody tr").each(function () {
            if (File_table.row(this, {
                    selected: true
                }).any()) {
                File_table.row(this).deselect();
            } else {
                File_table.row(this).select();
            }
        });

    });
    
/*    $('#keywords').focus(function(){
    	alert(123);
    });*/

    //add
    function addFile() {
        // $("#addFile").on('click', function () {
        emptyAddForm();

        InitOrganizationZtree(); // 初始化资源树

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editFileModalBody"), 'insert');

        //员工代码表单可编辑
        $("#editFileForm input[name='FileCode']").attr('readonly', false);

        $('#editFileModal').modal('show'); //现实模态框
        // });
    }
    //edict item
    function editFile() {
        // $("#editFile").click(function () {
        emptyAddForm();
        //   InitOrganizationZtree();// 初始化资源树

        var selectedRowData = File_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        //"departmentName": "海盟集团 || 贸易子公司 || 贸易代理部"，作处理截取最后一部分；
        var organizationStructureNameAttr = data.organizationStructureName.split("||");
        data.organizationStructureName = organizationStructureNameAttr[organizationStructureNameAttr.length - 1].trim();

        // 循环给表单赋值
        $.each($("#editFileForm input,#editFileForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });
        $("#editFileForm input[name='name']").val(data['organizationStructureName']);
        //$("#editFileForm input[name='saveType']").val("update");
        setDefaultValue($("#editFileModalBody"), 'update');
        $("#editFileModalBody input[name='amendTime']").val(data['amendTime']);

        //设置表单不可编辑
        $("#editFileForm input[name='FileCode']").attr('readonly', true);
        $('#editFileModal').modal('show'); //现实模态框

        // });
    }
    //确定增加或者保存编辑；
    function submitEditFileModal() {

        var data = $("#editFileForm").serializeObject();
        var saveType = $("#editFileForm input[name='saveType']").val();
        data["organizationStructureId"] = $("#editFileForm input[name='gocode']").val();
        data["FileDepartment"] = $("#editFileForm input[name='gocode']").val();
        // 测试使用
        // File_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'File/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask(); //显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    $('#editFileModal').modal('hide'); //现实模态框
                    File_table.ajax.reload(function () {
                        callSuccess('操作成功！');
                    });
                } else
                    callAlert(res.message);
            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
    }


    // delete item
    // $("#deleteFile").click(function () {
    function deleteFile() {
        var info;
        var selectedRowData = File_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            callAlert("请选择需要删除的数据！");
            return;
        }
        info = "删除员工时，员工对应的用户信息将一起被删除，确定要删除" + selectedRowData.length + "条数据吗?";

        callAlertModal(info, 'File_confirmDelete');

        $('.File_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.FileId);
            });

            $.ajax({
                url: getContextPath() + 'File/delete.do',
                data: {
                    FileIds: ids.join(',')
                },
                // dataType: 'json',
                beforeSend: function () {
                    showMask(); //显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp == 'success') {
                        File_table.ajax.reload(
                            function () {
                                callSuccess('删除成功！');
                            }
                        );
                    } else
                        callAlert('删除失败!')


                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });
    }
    // });
    function importFile() {
    	//alert(1);
    	//console.log($('#form2Inner'));reset()
    	$('#form2Inner')[0].reset();
        //$('#form2Inner input[name=file]').val("");
        //$('#importFileAdministrationModal1').modal('show'); //现实模态框
        $('#myModalInfoInsert').modal({backdrop:"static"}); //现实模态框
    }
    
    function editInfo(formId){
    	$('#edit_btn_submit').button('loading');
        var form = $('#' + formId)[0];
        var formdata = new FormData(form);
        //console.log(form);
        //console.log(formdata.keys().next());
        $.ajax({
            type: 'POST',
            mimeType: "multipart/form-data",
            url: getContextPath() + "fileManagement/updateReSearchFileInfo.do",
            data: formdata,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
            	$('#edit_btn_submit').button('reset');
                if (rsp.code == 0) {
                    callSuccess(rsp.message);          
                    // configure.configure_table.ajax.reload();
                    $("#editModal").modal('hide');
                    refreshAdministration();
                }
                else
                {	
                	callAlert(rsp.message);
                }
            },
            error: function (err) {
                // hideMask();
            	$('#edit_btn_submit').button('reset');
                callAlert("处理数据失败！");
            }
        });
    }
    
    function analyseFile(formId)
    {
    	var titleName = $('#showname').val();
        if (!titleName) {
            callAlert("请选择文件");
            return;
        }
        
        var form = $('#' + formId)[0];
        var formdata = new FormData(form);
        
        $.ajax({
            type: 'POST',
            mimeType: "multipart/form-data",
            url: getContextPath() + "fileManagement/analyseFile.do",
            data: formdata,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    //console.log(rsp);
                    $("input[name='title']").val(rsp.data.title);
                    $('#summary').val(rsp.data.summary);
                    // configure.configure_table.ajax.reload();
                    //$("#myModalInfoInsert").modal('hide');
                    //refreshAdministration();
                }
                else
                {
                	callAlert(rsp.message);
                	$("input[name='title']").val('');
                    $('#summary').val('');
                }
            },
            error: function (err) {
                // hideMask();
                callAlert("处理数据失败！");
            }
        });
    }

    function importFile2(formId, selectedFile, showUploadedFileLabelId) {
        // var modelName = "";
        //let tag = $("input[name='tag']").val();
        //let resource = $("input[name='resource']").val();
        let fileType = $('#type_list').val();
        let publishTime = $("input[name='publishTime']").val();
        let author = $("input[name='author']").val();
        let keywords = $("input[name='keywords']").val();
        let title= $("input[name='title']").val();
        let theme = $("input[name='theme']").val();
        let source = $("input[name='source']").val();
        let summary = $('#summary').val();
        let category = $('#category').val();
        //console.log($('#category').val());
        $("input[name='author']").val($("input[name='author']").val() || "未知");
        if (fileType) {
            //var titleName = $('#form2Inner input[name=file]').val();
        	var titleName = $('#showname').val();
            if (!titleName) {
                callAlert("请选择文件");
                return;
            }
            // $("#importResultsTable tbody .importResultsTable").remove();
            var form = $('#' + formId)[0];
            var formdata = new FormData(form);
            $.ajax({
                type: 'POST',
                mimeType: "multipart/form-data",
                url: getContextPath() + "fileManagement/import.do",
                data: formdata,
                contentType: false,
                cache: false,
                processData: false,
                dataType: "json",
                success: function (rsp) {
                    if (rsp.code == 0) {
                    	Init();
                    	//File_table.ajax.reload();
                        //callSuccess(rsp.message);
                        // configure.configure_table.ajax.reload();
                        $("#myModalInfoInsert").modal('hide');
                        //refreshAdministration();
                        File_table.ajax.reload(function () {
                            callSuccess(rsp.message);
                        });
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
        } else {
            callAlert("请输入必填项！");
        }

    }

    function buildIndexForAll() {
        $.ajax({
            type: 'GET',
            mimeType: "multipart/form-data",
            url: getContextPath() + "fileManagement/buildIndexForAll.do",
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                }
            },
            error: function (err) {
                callAlert("创建索引失败！");
            }
        });
    }


    function refreshAdministration() {
        // $("#refreshDuties").click(function () {
        Init();
        // Duties_table.ajax.reload();
        // });
    }
    //refesh table
    // $("#refreshFile").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
    	//console.log(JSON.stringify($('#searchFileForm').serializeObject()))
        File_table.ajax.reload();
    }

    function downloadById(fileId) {
        // $.ajax({
        //     type: 'GET',
        //     url: getContextPath() +"fileQuery/download.do?fileId="+fileId,
        //     contentType: false,
        //     cache: false,
        //     processData: false,
        //     dataType: "json",
        //     success: function (rsp) {
        //         alert(JSON.stringify(rsp));
        //         if (rsp.code == 0) {
        //             callSuccess(rsp.message);
        //         }
        //     },
        //     error: function (err) {
        //         debugger;
        //         alert(JSON.stringify(err));
        //         callAlert("下载失败！");
        //     }
        // });
        try {
            window.location.href = getContextPath() + "fileQuery/download.do?fileId=" + fileId;
            callSuccess("下载成功！");
        } catch (e) {
            callAlert("下载失败！");
        }
    }
    
    function editById(fileId){
    	//console.log($('#myModal'))
    	
    	
        $.ajax({
            type: 'GET',
            url: getContextPath() + "fileManagement/getFileById.do?id=" + fileId,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                //alert(rsp.fileTypeID);
            	$("#editId").val(rsp.fileId);
                $("#edit_type_list").val(rsp.fileTypeID);
                $("#editCategory").val(rsp.categoryId);
                $('#editKeywords').val(rsp.keyword);
                $('#editTitle').val(rsp.title);
                $('#editTheme').val(rsp.theme);
                $('#editAuthor').val(rsp.author);
                $('#editSource').val(rsp.resource);
                $('#editPublishTime').val(rsp.publishTime);
                $('#editSummary').val(rsp.summary);
                
            },
            error: function (err) {
                alert(JSON.stringify(err));
                callAlert("错误！");
            }
        });
        
        $('#editModal').modal({backdrop:"static"});
    }

    function deleteById(fileId) {
        var info = "是否确认删除该文档？";

        callAlertModal(info, 'File_confirmDelete');

        $('.File_confirmDelete').unbind('click').click(function () {

            $.ajax({
                type: 'GET',
                url: getContextPath() + "fileManagement/deleteFileById.do?fileId=" + fileId,
                contentType: false,
                cache: false,
                processData: false,
                dataType: "json",
                success: function (rsp) {
                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        File_table.ajax.reload();
                    }
                },
                error: function (err) {
                    alert(JSON.stringify(err));
                    callAlert("删除失败！");
                }
            });
        });
    }

    $('#fileTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check); //把查找到checkbox并且勾选
        //console.log(File_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#fileTable tbody').on('dblclick', 'tr', function () {
        var data = File_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showFileDetail').on('click', function () {
        var rows_data = File_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    });


    // 清空弹框
    function emptyAddForm() {
        $("#editFileForm")[0].reset();
        $("label.error").remove(); //清除提示语句
    };
    
    function makeThisfile()
    {
    	$('#file').click();
    }
    
    $('#file').change(function(){
        $('#showname').val($(this).val())
    })


    function InitOrganizationZtree() {
        $.ajax({
            // url : "../mock_data/organizational-structure.json",
            url: getContextPath() + 'companyControl/listAll.do',
            type: 'POST',
            "data": {
                keys: JSON.parse($.cookie('loginingEmployee'))['customer']['customerId']
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                //初始化form弹框的资源树
                InitAdministrationzTree("FileTreeResouce", data)

                InitAdministrationzTree("FileOrganizationalstructureTreeResouce_search", data);

            },
            error: function () {
                callAlert("获取数据失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });
    };


    //重置查询条件
    $("#resetsearchFileForm").click(function () {


        $("#searchFileForm")[0].reset();
        File_table.ajax.reload();
        //Init();

    })
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        //console.log("fnRowCallback");
        $.contextMenu({
            selector: '#fileTable tbody tr',
            callback: function (key, options) {
                //var row_data = File_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add": //增加一条数据
                        addFile();
                        break;
                    case "Delete": //删除该节点
                        $("#fileTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false); //把行取消选中；
                        options.$trigger.click(); //选中该行selected
                        deleteFile();
                        break;
/*                    case "Edit": //编辑该节点
                        $("#fileTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false); //把行取消选中；
                        options.$trigger.click(); //选中该行selected
                        editFile();
                        break;*/
                    default:
                        options.$trigger.removeClass("selected").find("input[type=checkbox]").prop("checked", false);; //取消选择selected
                }
            },
            items: {
/*                "Edit": {
                    name: "修改",
                    icon: "edit"
                },*/
                "Delete": {
                    name: "删除",
                    icon: "delete"
                },
                "Add": {
                    name: "新增",
                    icon: "add"
                },
/*                "sep1": "---------",
                "quit": {
                    name: "取消操作",
                    icon: function () {
                        return 'context-menu-icon context-menu-icon-quit';
                    }
                }*/
            }
        });
    };
    return {
        // 将供页面该方法调用
        editFile: editFile,
        addFile: addFile,
        deleteFile: deleteFile,
        doSearch: doSearch,
        importFile: importFile,
        importFile2: importFile2,
        buildIndexForAll: buildIndexForAll,
        downloadById: downloadById,
        deleteById: deleteById,
        editById: editById,
        editInfo: editInfo,
        makeThisfile: makeThisfile,
        analyseFile: analyseFile,
        test: test
    };
})();

//初始化
$(function () {


    $(".form_datetime").datetimepicker({
        format: 'yyyy-mm-dd', //显示格式
        todayHighlight: 1, //今天高亮
        minView: "month", //设置只显示到月份
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        autoclose: 1, //选择后自动关闭
        endDate: new Date()
    });
    $.ajax({
        url: getContextPath() + 'fileManagement/getDataList.do',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
            //const tagList = data.tagList;
            const typeList = data.typeList;
            const categoryList = data.categoryList;
            //console.log(data.categoryList);
            //const resourceList = data.resourceList;
            //fullDataList('tag_list', tagList, 'tagName', 'tagId');
            fullDataList('type_list', typeList, 'typeName', 'typeId');
            fullDataList('edit_type_list', typeList, 'typeName', 'typeId');
            categoryDataList('category',categoryList);
            categoryDataList('editCategory',categoryList);
            //fullDataList('source_list', resourceList, 'rescourceName', 'resourceId');

        },
        error: function () {
            callAlert("获取下拉框数据失败!");
        }
    });
    
    var style=["label label-primary","label label-success","label label-info","label label-warning","label label-danger"];
	var content='';
    $.ajax({
        url: getContextPath() + 'tag/getTapList.do',
        type: 'GET',
        dataType: 'json',
        async: false,
        success: function (data) {
        	for (let i = 0; i < data.length; i++){
        		var n=1;
        		content +='<div>'+data[i]['onelevelCategory']+'</div>'
        		for(let j=0;j<data[i]['tagList'].length;j++){
        			//alert(parseInt(Math.random()*5));
        			//alert(style[parseInt(Math.random()*4)]);
        			content +='<a class="'+style[parseInt(Math.random()*4)]+'" onclick="File.test(this)" style="margin-left: 3px;">'+
        						data[i]['tagList'][j]['tagName']+
        					  '</a>';
        			if(n%3==0){	
        				content +='<div style="height: 3px"></div>';
        			}
        			n++;
        		}
        	}
        	//alert(content);
        },
        error: function () {
            callAlert("获取下拉框数据失败!");
        }
    });
    
    $("#tagSelectBtn,#editTagSelectBtn").popover({
    	container: 'body',
    	title:"请选择标签",
    	trigger:"manual",
    	html:true,
    	content:content
/*    	content:"<div>1</div>"+
    			"<a class='label label-default' onclick='File.test(this)'>默认标签</a>"+
    			"<a class='label label-primary'>主要标签</a>"+
    			"<a class='label label-success'>成功标签</a>"+
    			"<a class='label label-success'>成功标签</a>"+
    			"<a class='label label-success'>成功标签</a>"+
    			"<br>"+
    			"<a class='label label-success'>成功标签</a>"+
    			"<a class='label label-success'>成功标签</a>"+
    			"<div>2</div>"*/
    }).on("mouseenter", function () {
        var _this = this;
        $(this).popover("show");
        $(".popover").on("mouseleave", function () {
            $(_this).popover('hide');
        });
    }).on("mouseleave", function () {
        var _this = this;
        setTimeout(function () {
            if (!$(".popover:hover").length) {
                $(_this).popover("hide");
            }
        }, 300);
});

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