//@ sourceURL=printFormat.js
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
//     var printFormat_Validator;
var printFormat = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditPrintFormatModal
    });
    $().ready(
        function validatePrintFormatForm() {
            $("#editPrintFormatForm").validate({
                rules: {
                    equipmentCode: {
                        required: true,
                        maxlength: 5
                    },
                    printFormat: {
                        maxlength: 5
                    },
                    isoCode: {
                        maxlength: 50
                    },
                    containerSize: {
                        required: true,
                        maxlength: 2
                    },
                    teu: {
                        required: true,
                        // digits: true
                        number: true,     //输入必须是数字
                        min: 0.01,          //输入的数字最小值为0.01，不能为0或者负数
                    },
                    feu: {
                        required: true,
                        // digits: true
                        number: true,     //输入必须是数字
                        min: 0.01,          //输入的数字最小值为0.01，不能为0或者负数
                    },
                    containerDescCn: {
                        maxlength: 200
                    },
                    containerCategory: {
                        maxlength: 4
                    },
                    description: {
                        maxlength: 200
                    }
                },
                messages: {
                    equipmentCode: {
                        required: "这是必填字段",
                        maxlength: "请不要超过限制的5个字符数"
                    },
                    printFormat: {
                        maxlength: "请不要超过限制的5个字符数"
                    },
                    isoCode: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    containerSize: {
                        required: "这是必填字段",
                        maxlength: "请不要超过限制的2个字符数"
                    },
                    teu: {
                        required: "这是必填字段"
                    },
                    feu: {
                        required: "这是必填字段"
                    },
                    containerDescCn: {
                        maxlength: "请不要超过限制的200个字符数"
                    },
                    containerCategory: {
                        maxlength: "请不要超过限制的4个字符数"
                    },
                    // description:{
                    //     maxlength: "请不要超过限制的200个字符数"
                    // }
                }
            });
            // return printFormat_Validator.form();
        }
    );


    var printFormat_table;
    var paral = {
        "printCode": "打印代码",
        "startup": "启用",
        "commonUse": "常用",
        "name": "名称",
        "fileName": "格式文件名",
        "type": "类型",
        "faxTo": "fax To",
        "exterInterface": "外部接口",
        "note": "备注",
        "applicationMode": "应用方式",
        "occasion": "场合"
    };

    Init();
    function Init() {
        // tableHeight = $("#printFormatTable").height();
        printFormat_table = $("#printFormatTable").DataTable({
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
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
            autoWidth: true,
            scrollX: true,
            serverSide: true,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url":"../mock_data/container-type.json",
                "url": getContextPath() + 'basedataPrintFormat/listByPage.do',
                "data": function (d) {
                    // alert(JSON.stringify($('#printFormat_search_form').serializeObject()));
                    d.keys = JSON.stringify($('#printFormatSearchForm').serializeObject());
                }

            },

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
                    "data": "container_type_id",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                {title: "打印代码", data: "printCode"},
                {
                    title: "启用",
                    data: "startup",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "是" : "否";
                    }

                },
                {
                    title: "常用",
                    data: "commonUse",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "是" : "否";
                    }

                },
                {title: "名称", data: "name"},
                {title: "格式文件名", data: "fileName"},
                {title: "类型", data: "type"},
                {title: "fax To", data: "faxTo"},
                {title: "外部接口", data: "exterInterface"},
                {title: "备注", data: "note"},
                {title: "应用方式", data: "applicationMode"},
                {title: "场合", data: "occasion"},

                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '' : data.creatorName;
                    }
                    // "bSortable": false,
                },

                {title: "最新修改时间", data: "amendTime"}

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
                    targets: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
                }
            ],
            buttons: [
                {
                    extend: 'excelHtml5',
                    exportOptions: {
                        columns: ':visible',
                        rows: { selected: true }
                        },
                    text:"选中行导出Excel",
                    container: '#printFormat_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#printFormat_export-excel-current'

                },
                {
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
                    container: '#printFormat_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#printFormat_export-print-all'
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
                    container: '#printFormat_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#printFormat_export-columnVisibility'
                }
            ],
            select: {
                // blurable: true,
                style: 'multi',//选中多行
                // info: false
            }
        });
    }

// select/not select all
    $('body').on('click', '.printFormat .checkall', function () {
        var check = $(this).prop("checked");
        $(".printFormat .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#printFormatTable tbody tr").each(function () {
            if ( printFormat_table.row( this, { selected: true } ).any() ) {
                printFormat_table.row( this ).deselect();
            }
            else {
                printFormat_table.row( this ).select();
            }
        });

    });


//add
// $("#addPrintFormat").on('click', function () {
    function addPrintFormat() {
        emptyAddForm();
        //$(".error").removeClass("error");
        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPrintFormatModalBody"), 'insert');

        $('#editPrintFormatModal').modal('show');//现实模态框
    }
    // });
//edict item
//$("#editPrintFormat").click(function () {
     function editPrintFormat() {
        emptyAddForm();
        var selectedRowData = printFormat_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editPrintFormatForm input,#editPrintFormatForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });
        $.each($("#editPrintFormatForm input[type=checkbox]"), function (i, input) {


            $(this).prop("checked", data[$(this).attr("name")] == 1 ? true : false);
        });


        setDefaultValue($("#editPrintFormatModalBody"), 'update');
        // $("#editPrintFormatForm input[name='saveType']").val("update");
         $("#editPrintFormatForm input[name='amendTime']").val(  data['amendTime']);

         $('#editPrintFormatModal').modal('show');//现实模态框
    }

//})
//确定增加或者保存编辑；
    function submitEditPrintFormatModal() {
        var data = $("#editPrintFormatForm").serializeObject();

        data.startup == undefined ? data.startup = 0 : data.startup = 1;
        data.commonUse == undefined ? data.commonUse = 0 : data.commonUse = 1;

        var saveType = $("#editPrintFormatForm input[name='saveType']").val();

// 测试使用
        // printFormat_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataPrintFormat/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    callSuccess(res.message);

                    printFormat_table.ajax.reload();
                }
                else
                    callAlert(res.message);


            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
        $('#editPrintFormatModal').modal('hide');//隐藏模态框
        //   });
    }


// delete item
//$("#deletePrintFormat").click(function () {
    function deletePrintFormat() {
        var info;
        var selectedRowData = printFormat_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'PrintFormat_confirmDelete');
        $('.PrintFormat_confirmDelete').unbind('click').click(function () {
            var ids = [];

            $.each(selectedRowData, function () {

                ids.push(this.printFormatId);
            });

            $.ajax({
                url: getContextPath() + 'basedataPrintFormat/delete.do',
                data: {
                    printFormatIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        printFormat_table.ajax.reload();
                    } else
                        callAlert(rsp.message)


                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });
    }

//});


//refesh printFormat_table
//     $("#refreshPrintFormat").click(function () {
//         // printFormat_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
//         printFormat_table.ajax.reload();
//     })

    $('#printFormatTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        // console.log(table.rows('.selected').data().length);
    });


// click item display detail infomation
    $('#printFormatTable tbody').on('dblclick', 'tr', function () {
        var data = printFormat_table.rows($(this)).data()[0];
        // "rfCheckFlag","akCheckFlag",dgCheckFlag 使用0，1表示否是。
        data.startup = data.startup == 1 || data.startup == "1" ? "是" : "否";
        data.commonUse = data.commonUse == 1 || data.commonUse == "1" ? "是" : "否";
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    // $('#showPrintFormatDetail').on('click', function () {
    //     var rows_data = printFormat_table.rows('.selected').data();
    //     if (rows_data.length < 1) {
    //         callAlert("请选择一条数据进行查看");
    //         return;
    //     }
    //     for (var i = 0; i < rows_data.length; i++) {
    //         $("#detail_table").html("");
    //         rows_data[i].startup = rows_data[i].startup == 1 || rows_data[i].startup == "1" ? "是" : "否";
    //         rows_data[i].commonUse = rows_data[i].commonUse == 1 || rows_data[i].commonUse == "1" ? "是" : "否";
    //         DisplayDetail(rows_data[i], paral);
    //     }
    //
    // })


// 清空弹框
    function emptyAddForm() {
        $("#editPrintFormatForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };

    //搜索 datatable搜索
    function doSearch(){

        printFormat_table.ajax.reload();

    }
//作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#printFormatTable tbody tr',
            callback: function (key, options) {
                //var row_data = printFormat_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addPrintFormat();
                        break;
                    case "Delete"://删除该节点
                        $("#printFormatTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deletePrintFormat();
                        break;
                    case "Edit"://编辑该节点
                        $("#printFormatTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editPrintFormat();
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
        editPrintFormat: editPrintFormat,
        addPrintFormat:addPrintFormat,
        deletePrintFormat:deletePrintFormat,
        doSearch:doSearch
    };

})();