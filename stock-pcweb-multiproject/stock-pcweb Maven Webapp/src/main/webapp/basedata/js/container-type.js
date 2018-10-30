//@ sourceURL=container-type.js
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
//     var Containertype_Validator;
var containerType = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditContainertypeModal
    });
    $().ready(
        function validateContainertypeForm() {
            $("#editContainertypeForm").validate({
                rules: {
                    equipmentCode: {
                        required: true,
                        maxlength: 5
                    },
                    containerType: {
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
                    containerType: {
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
            // return Containertype_Validator.form();
        }
    );


    var containerType_table;
    var paral = {
        // "containerTypeId": "箱型ID",
        "equipmentCode": "箱型代码",
        "containerType": "箱型",
        "containerDesc": "箱型描述（EN）",
        "containerDescCn": "箱型描述（CN）",
        "containerCategory": "类别",
        "containerSize": "尺寸",
        "isoCode": "ISO代码",
        "feu": "FEU",
        "teu": "TEU",
        "dgCheckFlag": "DG检查标志",
        "akCheckFlag": "AK检查标志",
        "akLength": "AK的长度",
        "akWidth": "AK的宽度",
        "akHigth": "AK的高度",
        "rfCheckFlag": "RF检查标志",
        // "sitcFlag": "SITC标志",
        "tareWeight": "箱型质量",
        // "description": "描述"
    };

    Init();
    function Init() {
        // tableHeight = $("#containertypeTable").height();
        containerType_table = $("#containertypeTable").DataTable({
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
            pageLength:20,
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            autoWidth: true,
            scrollX: true,
            serverSide: true,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
//             dom: 'Blfrtip',
            dom:'<"top">Brt<"bottom"flip><"clear">',
            ajax: {
                "type": "POST",
                // "url":"../mock_data/container-type.json",
                "url": getContextPath() + 'basedataContainerType/listByPage.do',
                "data": function (d) {
                    // alert(JSON.stringify($('#containerType_search_form').serializeObject()));
                    d.keys = JSON.stringify($('#containerTypeSearchForm').serializeObject());
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
                // {title: "箱型ID", data: "containerTypeId"},
                {title: "箱型代码", data: "equipmentCode"},
                {title: "箱型", data: "containerType"},
                {title: "箱型描述（EN）", data: "containerDesc"},
                {title: "箱型描述（CN）", data: "containerDescCn"},
                {title: "类别", data: "containerCategory"},
                {title: "尺寸", data: "containerSize"},
                {title: "ISO代码", data: "isoCode"},
                {title: "FEU", data: "feu"},
                {title: "TEU", data: "teu"},
                {
                    title: "DG检查标志",
                    data: "dgCheckFlag",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "是" : "否";
                    }

                },
                {
                    title: "AK检查标志",
                    data: "akCheckFlag",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "是" : "否";
                    }
                },
                {
                    title: "RF检查标志",
                    data: "rfCheckFlag",
                    "render": function (data, type, full, meta) {
                        return (data == 1 || data == "1" ) ? "是" : "否";
                    }
                },
                {title: "AK的长度", data: "akLength"},
                {title: "AK的宽度", data: "akWidth"},
                {title: "AK的高度", data: "akHigth"},
                // {title: "SITC标志", data: "sitcFlag"},
                {title: "箱型质量", data: "tareWeight"},
                // {title: "描述", data: "description"},
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
                                '<span title="' + data + '">' + data + '</span>' :
                                data;
                        else if  (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                        }
                        return data;
                    },
                    targets: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]
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
                    container: '#containerType_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#containerType_export-excel-current'

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
                    container: '#containerType_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#containerType_export-print-all'
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
                    container: '#containerType_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#containerType_export-columnVisibility'
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
    $('body').on('click', '.container-type .checkall', function () {
        var check = $(this).prop("checked");
        $(".container-type .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#containertypeTable tbody tr").each(function () {
            if ( containerType_table.row( this, { selected: true } ).any() ) {
                containerType_table.row( this ).deselect();
            }
            else {
                containerType_table.row( this ).select();
            }
        });

    });


//add
// $("#addContainertype").on('click', function () {
    function addContainertype() {
        emptyAddForm();
        //$(".error").removeClass("error");
        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editContainertypeModalBody"), 'insert');

        $('#editContainertypeModal').modal('show');//现实模态框
    }
    // });
//edict item
//$("#editContainertype").click(function () {
     function editContainertype() {
        emptyAddForm();
        var selectedRowData = containerType_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editContainertypeForm input,#editContainertypeForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });
        $.each($("#editContainertypeForm input[type=checkbox]"), function (i, input) {


            $(this).prop("checked", data[$(this).attr("name")] == 1 ? true : false);
        });


        setDefaultValue($("#editContainertypeModalBody"), 'update');
        // $("#editContainertypeForm input[name='saveType']").val("update");
         $("#editContainertypeForm input[name='amendTime']").val(  data['amendTime']);
        $('#editContainertypeModal').modal('show');//现实模态框
    }

//})
//确定增加或者保存编辑；
    function submitEditContainertypeModal() {
        var data = $("#editContainertypeForm").serializeObject();

        data.akCheckFlag == undefined ? data.akCheckFlag = 0 : data.akCheckFlag = 1;
        data.dgCheckFlag == undefined ? data.dgCheckFlag = 0 : data.dgCheckFlag = 1;
        data.rfCheckFlag == undefined ? data.rfCheckFlag = 0 : data.rfCheckFlag = 1;

        var saveType = $("#editContainertypeForm input[name='saveType']").val();

// 测试使用
        containerType_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataContainerType/' + saveType + '.do',
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

                    containerType_table.ajax.reload();
                }
                else
                    callAlert(res.message);


            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
        $('#editContainertypeModal').modal('hide');//隐藏模态框
        //   });
    }


// delete item
//$("#deleteContainertype").click(function () {
    function deleteContainertype() {
        var info;
        var selectedRowData = containerType_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'Containertype_confirmDelete');
        $('.Containertype_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.containerTypeId);
            });

            $.ajax({
                url: getContextPath() + 'basedataContainerType/delete.do',
                data: {
                    containerTypeIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        containerType_table.ajax.reload();
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


//refesh containerType_table
//     $("#refreshContainertype").click(function () {
//         // containerType_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
//         containerType_table.ajax.reload();
//     })
　
    $('#containertypeTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        // console.log(table.rows('.selected').data().length);
    });


// click item display detail infomation
    $('#containertypeTable tbody').on('dblclick', 'tr', function () {
        var data = containerType_table.rows($(this)).data()[0];
        // "rfCheckFlag","akCheckFlag",dgCheckFlag 使用0，1表示否是。
        data.rfCheckFlag = data.rfCheckFlag == 1 || data.rfCheckFlag == "1" ? "是" : "否";
        data.akCheckFlag = data.akCheckFlag == 1 || data.akCheckFlag == "1" ? "是" : "否";
        data.dgCheckFlag = data.dgCheckFlag == 1 || data.dgCheckFlag == "1" ? "是" : "否";
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showContainertypeDetail').on('click', function () {
        var rows_data = containerType_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            rows_data[i].rfCheckFlag = rows_data[i].rfCheckFlag == 1 || rows_data[i].rfCheckFlag == "1" ? "是" : "否";
            rows_data[i].akCheckFlag = rows_data[i].akCheckFlag == 1 || rows_data[i].akCheckFlag == "1" ? "是" : "否";
            rows_data[i].dgCheckFlag = rows_data[i].dgCheckFlag == 1 || rows_data[i].dgCheckFlag == "1" ? "是" : "否";
            DisplayDetail(rows_data[i], paral);
        }

    })


// 清空弹框
    function emptyAddForm() {
        $("#editContainertypeForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };

    //搜索 datatable搜索
    function doSearch(){

        containerType_table.ajax.reload();

    }
//作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#containertypeTable tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addContainertype();
                        break;
                    case "Delete"://删除该节点
                        $("#containertypeTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteContainertype();
                        break;
                    case "Edit"://编辑该节点
                        $("#containertypeTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editContainertype();
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
        editContainertype: editContainertype,
        addContainertype:addContainertype,
        deleteContainertype:deleteContainertype,
        doSearch:doSearch
    };

})();