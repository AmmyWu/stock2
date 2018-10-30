//@ sourceURL=ReportHistory.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var ReportHistory = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditReportHistoryModal
    });


    $(function () {

        //初始化组织架构树
    //    InitOrganizationZtree();

        //Date picker //时间控件
        $(".time").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        });
    });

    $().ready(
        function validateReportHistoryForm() {
            $("#editReportHistoryForm").validate({
                rules: {
                    templetdataId:{
                        maxlength:11
                    },
                    templetdataName:{
                        required:true,
                        maxlength:100
                    },
                    templetdataLink: {
                        required:true,
                        maxlength:100,
                      
                    },
                    comment:{
                        required:true,
                        maxlength:100
                    },
                },
                messages: {

                }
            });
        }
    );
    var reportTempletHistory_table;
    var zTree_OrganizationStructure;//资源树
    var paral = {
        // "customerId":"员工ID",
        "templetdataId": "报表ID",
        "templetdataName": "报表名称",
       
        "templetdataLink": "报表链接",
        "templetId": "所属模板",
        "comment": "报表备注",
        "creator": "创建人",
       
        "createTime": "创建时间",
        "amenderName": "修改人",
        
        "amendTime": "修改时间",
  
    };

    Init();
    function Init() {
        reportTempletHistory_table = $("#reportTempletHistoryTable").DataTable({
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            fnDrawCallback: changePage,//重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            // 动态分页加载数据方式
            bProcessing: true,
            bServerSide: true,
            aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
            searching: false,// 禁用搜索
            lengthChange: true, // 是否启用改变每页显示多少条数据的控件
            /*
             * sort : "position",
             * //是否开启列排序，对单独列的设置在每一列的bSor选项中指定
             */
            deferRender: true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            scrollX: true,
            autoWidth: false,
            serverSide: true,
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url": "../mock_data/customer.json",
                "url": getContextPath() + 'templetData/getByPage.do',
                "data": function (d) {
                    // alert(JSON.stringify($('#searchcustomerForm').serializeObject()));
                    d.keys = JSON.stringify($('#searchReportHistoryForm').serializeObject());
                },
        "dataSrc": function ( json ) {
            for ( var i=0, ien=json.aaData.length ; i<ien ; i++ ) {
            	for(j in json.aaData[i].baseModel){
              json.aaData[i][j] = json.aaData[i].baseModel[j];
            	}
            }
            return json.aaData;
          }

            },
            dom:'<"top">Brt<"bottom"flip><"clear">',
            language: {
                "url": "js/Chinese.json",
                select: {
                    rows: "%d 行被选中"
                },
                buttons: {
                    copyTitle: '复制到剪切板',
                    // copyKeys: 'Appuyez sur <i>ctrl</i> ou <i>\u2318</i> + <i>C</i> pour copier les données du tableau à votre presse-papiers. <br><br>Pour annuler, cliquez sur ce message ou appuyez sur Echap.',
                    copySuccess: {
                        _: '将%d 行复制到剪切板',
                        1: '将1行复制到剪切板'
                    }
                }
            },
            columns: [
                {
                    "sClass": "text-center",
                    "data": "templetdataId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                {title: "报表名称", data: "templetdataName"},
                {title: "报表链接", data: "templetdataLink",
                    "render": function (data, type, full, meta) {
                        return '<a  onclick=downloadReport("'+data+'")>报表链接</a>';
                    },
                },
                {title: "所属报表", data: "templetId"},
                {title: "备注", data: "comment"},
                {title: "创建人", data: "creatorName"},
                {title: "创建时间", data: "createTime"},
                {title: "修改人", data: "amenderName"},
                {title: "修改时间", data: "amendTime"},
              

            ],
            columnDefs: [
                {
                    orderable: false,
                    targets: 0
                },
                {
                    "render": function (data, type, full, meta) {
                        if ($.string.isNullOrEmpty(data))
                            return "";
                        else if  (type === 'display')
                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' : data;
                        else if  (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                        }
                        return data;
                    },
                    targets: [1, 2, 3, 4, 5, 6,7]
                }
            ],
            buttons: [
               
            ],
            select: {
                // blurable: true,
                style: 'multi',//选中多行
                // info: false
            }
        });
    }

    // select/not select all
    $('body').on('click', '.customer .checkall', function () {
        var check = $(this).prop("checked");
        $(".customer .checkchild").prop("checked", check);
        $("#reportTempletHistoryTable tbody tr").each(function () {
            if ( reportTempletHistory_table.row( this, { selected: true } ).any() ) {
                reportTempletHistory_table.row( this ).deselect();
            }
            else {
                reportTempletHistory_table.row( this ).select();
            }
        });

    });

    //add
    function addReportHistory() {
        // $("#addReportHistory").on('click', function () {
        emptyAddForm();

      //  InitOrganizationZtree(); // 初始化资源树

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editReportHistoryModalBody"), 'insert');


        $('#editReportHistoryModal').modal('show');//现实模态框
        // });
    }
    //edict item
    function editReportHistory() {
        // $("#editReportHistory").click(function () {
        emptyAddForm();
      //  InitOrganizationZtree();// 初始化资源树

        var selectedRowData = reportTempletHistory_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];


        // 循环给表单赋值
        $.each($("#editReportHistoryForm input,#editReportHistoryForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //$("#editReportHistoryForm input[name='saveType']").val("update");
        setDefaultValue($("#editReportHistoryModalBody"), 'update');

        //设置表单不可编辑
        $('#editReportHistoryModal').modal('show');//现实模态框

        // });
    }
    //确定增加或者保存编辑；
    function submitEditReportHistoryModal() {

        var data = $("#editReportHistoryForm").serializeObject();
        var saveType = $("#editReportHistoryForm input[name='saveType']").val();

        // 测试使用
        // ReportHistory_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'templetData/' + saveType + '.do',
            data: {"templetData":JSON.stringify(data)},
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    $('#editReportHistoryModal').modal('hide');//现实模态框
                    reportTempletHistory_table.ajax.reload(function () {
                            callSuccess('操作成功！');
                        }
                    );
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
    // $("#deletecustomer").click(function () {
    function deleteReportHistory() {
        var info;
        var selectedRowData = reportTempletHistory_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            callAlert("请选择需要删除的数据！");
            return;
        }
        info = "删除模板，确定要删除" + selectedRowData.length + "条数据吗?";

        callAlertModal(info,'ReportHistory_confirmDelete');

        $('.ReportHistory_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.templetdataId);
            });

            $.ajax({
                url: getContextPath() + 'templetData/delete.do',
                data: {
                	templetdataIds: ids.join(',')
                },
                // dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                        reportTempletHistory_table.ajax.reload(
                            function () {
                                callSuccess('删除成功！');
                            }
                        );


                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });
    }
    // });


    //refesh table
    // $("#refreshcustomer").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        reportTempletHistory_table.ajax.reload();
    }

    $('#reportTempletHistoryTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(reportTempletHistory_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#reportTempletHistoryTable tbody').on('dblclick', 'tr', function () {
        var data = reportTempletHistory_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showReportHistoryDetail').on('click', function () {
        var rows_data = reportTempletHistory_table.rows('.selected').data();
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
        $("#editReportHistoryForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };

//重置查询条件
    $("#resetSearchReportHistoryForm").click(function () {


        $("#searchReportHistoryForm")[0].reset();

    })
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#reportTempletHistoryTable tbody tr',
            callback: function (key, options) {
                //var row_data = reportTempletHistory_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addReportHistory();
                        break;
                    case "Delete"://删除该节点
                        $("#reportTempletHistoryTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteReportHistory();
                        break;
                    case "Edit"://编辑该节点
                        $("#reportTempletHistoryTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editReportHistory();
                        break;
                    default:
                        options.$trigger.removeClass("selected").find("input[type=checkbox]").prop("checked", false);;//取消选择selected
                }
            },
            items: {
                "Edit": {name: "修改", icon: "edit"},
                // "cut": {name: "Cut", icon: "cut"},
                // copy: {name: "Copy", icon: "copy"},
                // "paste": {name: "Paste", icon: "paste"},
                "Delete": {name: "删除", icon: "delete"},
                "Add": {name: "新增", icon: "add"},
                "sep1": "---------",
                "quit": {
                    name: "取消操作", icon: function () {
                        return 'context-menu-icon context-menu-icon-quit';
                    }
                }
            }
        });
    };
    return {
        // 将供页面该方法调用
        editReportHistory: editReportHistory,
        addReportHistory:addReportHistory,
        deleteReportHistory:deleteReportHistory,
        doSearch:doSearch
    };
})();

function downloadReport(myURL){
	
	window.location = cjkEncode(myURL);
}
function cjkEncode(text) {       
    if (text == null) {       
        return "";       
    }       
    var newText = "";       
    for (var i = 0; i < text.length; i++) {       
        var code = text.charCodeAt (i);        
        if (code >= 128 || code == 91 || code == 93) {//91 is "[", 93 is "]".       
            newText += "[" + code.toString(16) + "]";       
        } else {       
            newText += text.charAt(i);       
        }       
    }       
    return newText;       
}   
