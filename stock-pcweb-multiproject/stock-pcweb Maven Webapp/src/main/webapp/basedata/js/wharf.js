//@ sourceURL=wharf.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行 submitEditWharfModal
var Wharf = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditWharfModal
    });

    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();

        // //初始化查询form的港口下拉列表
        // initSelect2("searchWharfForm","portId","basedataPort/listIdNameByKeys.do","{}","portId","portName");
        initSelect2FromRedis("searchWharfForm", "portId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portId", "portName");

        //初始化编辑form的港口下拉列表
        initSelect2FromRedis("editWharfForm", "portId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portId", "portName");


        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
    });


    $("#editWharfForm select[name='portId']").on("select2:select", function (e) {

        var portId = $("#editWharfForm select[name='portId']").val();

        // initSelect2FromRedis("sailingDateSearchForm", "vesselId", "redisController/listIdNameByName.do?name=basedataVessel_" + vesselParameterId, "{}", "vesselId", "vesselName");

        $.ajax({
            url: getContextPath() + 'basedataPort/getCountryNameById.do?portId='+portId,
            cache: false,
            dataType: "text",
            type: "GET",
            success: function (res) {

                $("#editWharfForm input[name='countryName']").val(res);

            },
            error: function () {
                callAlert("获取国家名称失败!");
            }
        });

    });


    $().ready(
        function validateWharfForm() {
            $("#editWharfForm").validate({
                rules: {
                    portId: {
                        required: true
                    },
                    countryName: {
                        maxlength:50
                    },
                    wharfCode: {
                        maxlength: 20
                        //digits: true,
                        //required: true
                    },
                    nameEn: {
                        maxlength:50
                    },
                    nameCn: {
                        maxlength:50
                    },
                    wharfUrl: {
                        maxlength: 100,
                        url:true
                    }
                },
                messages: {
                    //facilityCode: {
                    //    maxlength: "最多输入3个字符"
                    //},
                    //customerCode: {
                    //    maxlength: "最多输入20个字符"
                    //},
                    //isCntrReturnPlace: {
                    //    maxlength: "最多输入1位数字"
                    //},
                    //portcode: {
                    //    maxlength: "最多输入5个字符"
                    //},
                    //zipcode: {
                    //    maxlength: "最多输入20个字符"
                    //}
                }
            });
        }
    );
    var Wharf_table;
    var paral = {
        // "portcode":"港口代码",
        // "partFacilityId":"设备ID",
        "portId": "码头代码",
        "nameCn": "中文名称",
        "nameEn": "英文名称",
        // "facilityNameCn":"港口名称",
        "countryName": "国家名称",
        "wharfUrl": "网址",
        "description": "备注"

    };

    Init();
    function Init() {
        Wharf_table = $("#WharfTable").DataTable({
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            bProcessing: true,
            bServerSide: true,
            aLengthMenu: [10, 20, 40, 60], // 动态指定分页后每页显示的记录数。
            searching: false,// 禁用搜索
            lengthChange: true, // 是否启用改变每页显示多少条数据的控件
            /*
             * sort : "position",
             * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
             */
            deferRender: true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            serverSide: true,
            autoWidth: true,
            scrollX: true,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url" : "../mock_data/port-facility.json",
                "url": getContextPath() + 'basedataWharf/listByPage.do',
                "data": function (d) {
//					alert( JSON.stringify($('#port-facility_search-table').serializeObject()));
                    d.keys = JSON.stringify($('#searchWharfForm').serializeObject());
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
                    "data": "wharfId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                {title: "码头代码", data: "wharfCode"},
                {title: "中文名称", data: "nameCn"},
                {title: "英文名称", data: "nameEn"},
                // { title: "港口Id",data:"portId"},
                {title: "港口名称", data: "portName"},
                {title: "国家名称", data: "countryName"},
                {title: "码头网址", data: "wharfUrl"},
                {title: "备注", data: "description"},
                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '' : data.amenderName;
                    },
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
                                '<span title="' + data + '">' + data + '</span>' :data;
                        else if  (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                        }
                        return data;
                    },
                    targets: [1, 2, 3, 4, 5, 6, 7, 8]
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
                    container: '#Wharf_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#Wharf_export-excel-current'

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
                    container: '#Wharf_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#Wharf_export-print-all'
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
                    container: '#Wharf_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#Wharf_export-columnVisibility'
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
    $('body').on('click', '.port-facility .checkall', function () {
        var check = $(this).prop("checked");
        $(".port-facility .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#WharfTable tbody tr").each(function () {
            if ( Wharf_table.row( this, { selected: true } ).any() ) {
                Wharf_table.row( this ).deselect();
            }
            else {
                Wharf_table.row( this ).select();
            }
        });

    });


    $('#WharfTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选

    });


    //add
    function addWharf() {
        // $("#addWharf").on('click', function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editWharfModalBody"), 'insert');

        //设置港口默认值为空
        emptySelect2Value("editWharfForm", "portId");

        // initSelect2("editWharfForm","portId","basedataPort/listIdNameByKeys.do","{}","portId","portName");

        $('#editWharfModal').modal('show');//现实模态框
        // })
    }
    //edict item
    function editWharf () {
        // $("#editWharf").click(function () {
        emptyAddForm();
        var selectedRowData = Wharf_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editWharfForm input,#editWharfForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editWharfModalBody"), 'update');
        $("#editWharfForm input[name='amendTime']").val(  data['amendTime']);

        //设置港口的值
        setSelect2Value("editWharfForm", "portId", data["portId"]);

        $('#editWharfModal').modal('show');//现实模态框

        // })
    }
    //确定增加或者保存编辑；
    function submitEditWharfModal() {
        var data = $("#editWharfForm").serializeObject();
        var saveType = $("#editWharfForm input[name='saveType']").val();

        // 测试使用
        // Wharf_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            url: getContextPath() + '/basedataWharf/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            type: "POST",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    Wharf_table.ajax.reload(function () {
                        callSuccess(res.message);
                        //在回调函数里面调用，避免提示提前出现
                    });

                }
                else

                    callAlert(res.message);

            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });


        $('#editWharfModal').modal('hide');//现实模态框

    }


    // delete item
    function deleteWharf() {
    // $("#deleteWharf").click(function () {
        var info;
        var selectedRowData = Wharf_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "数据吗?";
        callAlertModal(info,'Wharf_confirmDelete');

        //确定删除
        $('.Wharf_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.wharfId);
            });

            $.ajax({
                url: getContextPath() + 'basedataWharf/delete.do',
                data: {
                    wharfIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp.code == 0) {
                        Wharf_table.ajax.reload(function () {
                            callSuccess(rsp.message);
                        });
                    }
                    else {
                        callAlert(rsp.message)
                    }

                    //Wharf_table.ajax.reload();
                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });

        // });
    }

    //refesh table
   // $("#refreshWharf").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        Wharf_table.ajax.reload();
    }

    $('#WharfTabletbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(Wharf_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#WharfTable tbody').on('dblclick', 'tr', function () {

        var data = Wharf_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);

    });
    $('#showWharfDetail').on('click', function () {
        var rows_data = Wharf_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    })

    //重置查询条件
    $("#resetSeachWharfForm").click(function () {


        $("#searchWharfForm")[0].reset();

        //设置港口默认值为空
        emptySelect2Value("searchWharfForm", "portId");

    })

    // 清空弹框
    function emptyAddForm() {
        $("#editWharfForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#WharfTable tbody tr',
            callback: function (key, options) {
                //var row_data = Wharf_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addWharf();
                        break;
                    case "Delete"://删除该节点
                        $("#WharfTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteWharf();
                        break;
                    case "Edit"://编辑该节点
                        $("#WharfTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editWharf();
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
        editWharf: editWharf,
        addWharf:addWharf,
        deleteWharf:deleteWharf,
        doSearch:doSearch
    };


})();

