//@ sourceURL=port-facility.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行 submitEditPortFacilityModal
(function() {
    $.validator.setDefaults({
        submitHandler: submitEditPortFacilityModal
    });

    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();

        // //初始化查询form的港口下拉列表
        initSelect2FromDB("searchPortFacilityForm", "portId", "basedataPort/listByKeys.do", "{}", "portId", "portName");

        //初始化编辑form的港口下拉列表
        initSelect2FromDB("editPortFacilityForm", "portId", "basedataPort/listByKeys.do", "{}", "portId", "portName");

        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
    });


    $().ready(
        function validatePortFacilityForm() {
            $("#editPortFacilityForm").validate({
                rules: {
                    facilityCode: {
                        maxlength: 3
                    },
                    customerCode: {
                        maxlength: 20,
                        digits: true,
                        required: true
                    },
                    isCntrReturnPlace: {
                        maxlength: 1,
                        digits: true
                    },
                    portcode: {
                        maxlength: 5
                    },
                    zipcode: {
                        maxlength: 20
                    }
                },
                messages: {
                    facilityCode: {
                        maxlength: "最多输入3个字符"
                    },
                    customerCode: {
                        maxlength: "最多输入20个字符"
                    },
                    isCntrReturnPlace: {
                        maxlength: "最多输入1位数字"
                    },
                    portcode: {
                        maxlength: "最多输入5个字符"
                    },
                    zipcode: {
                        maxlength: "最多输入20个字符"
                    }
                }
            });
        }
    );
    var portFacility_table;
    var paral = {
        // "portcode":"港口代码",
        // "partFacilityId":"设备ID",
        "portId": "港口",
        "facilityCode": "设备代码",
        "facilityName": "设备名称（EN）",
        "facilityNameCn": "设备名称（CN）",
        "isCntrReturnPlace": "IS_CNTR_RETURN_PLACE",
        "address": "地址",
        "printInfor": "打印信息",
        "zipcode": "邮政编码",
        "isDefaultDepot": "默认仓库",
        "terminalFlag": "终端标志",
        "yardFlag": "YARD_FLAG",
        "yardProperty": "YARD_PROPERTY",
        "useSystemFlag": "USE_SYSTEM_FLAG",
        "repairCompanyFlag": "维修公司标志",
        "craneLoadWeight": "设备载荷能力",
        "containCraneFlag": "设备标志",
        "customerCode": "客户代码",
        "description": "描述",

    };

    Init();
    function Init() {
        portFacility_table = $("#portFacilityTable").DataTable({
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
            bStateSave: false, // 在第三页刷新页面，会自动到第一页
            iDisplayLength: 10, // 默认每页显示多少条记录
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            serverSide: true,
            scrollX: true,
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url" : "../mock_data/port-facility.json",
                "url": getContextPath() + 'basedataPortFacility/listByPage.do',
                "data": function (d) {
//					alert( JSON.stringify($('#port-facility_search-table').serializeObject()));
                    d.keys = JSON.stringify($('#searchPortFacilityForm').serializeObject());
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
                    "data": "portFacilityId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                {title: "港口", data: "portId"},
                {title: "设备代码", data: "facilityCode"},
                {title: "设备名称（EN）", data: "facilityName"},
                {title: "设备名称（CN）", data: "facilityNameCn"},
                {title: "IS_CNTR_RETURN_PLACE", data: "isCntrReturnPlace"},
                {title: "地址", data: "address"},
                {title: "打印信息", data: "printInfor"},
                // { title: "港口代码",data:"portcode" },
                {title: "邮政编码", data: "zipcode"},
                {title: "默认仓库", data: "isDefaultDepot"},
                {title: "终端标志", data: "terminalFlag"},
                {title: "YARD_FLAG", data: "yardFlag"},
                {title: "YARD_PROPERTY", data: "yardProperty"},
                {title: "USE_SYSTEM_FLAG", data: "useSystemFlag"},
                {title: "维修公司标志", data: "repairCompanyFlag"},
                {title: "设备载荷能力", data: "craneLoadWeight"},
                {title: "设备标志", data: "containCraneFlag"},
                {title: "客户代码", data: "customerCode"},
                {title: "描述", data: "description"},
                {
                    title: "最新修改人",
                    "data": "baseModel",

                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '' : data.creatorName;
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
                        else
                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' :
                                data;
                    },
                    targets: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
                }
            ],
            buttons: [
                {
                    extend: 'excelHtml5'
                    // text:"导出Excel"
                },
                {
                    extend: 'copyHtml5',
                    text: '拷贝选中行',
                    header: false,
                    exportOptions: {
                        modifier: {
                            selected: true
                        },
                        orthogonal: 'copy'
                    }
                },
                {
                    extend: 'print',
                    text: '打印全部'
                },
                {
                    extend: 'print',
                    text: '打印选中行',
                    exportOptions: {
                        modifier: {
                            selected: true
                        }
                    }
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
        if (check) {
            $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
        }
        else {
            $("tr").removeClass("selected");
        }

    });


    $('#portFacilityTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选

    });


    //add
    $("#addPortFacility").on('click', function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPortFacilityForm"), 'insert');

        //设置港口默认值为空
        emptySelect2Value("searchPortFacilityForm", "portId");

        // $("#editPortFacilityForm input[name='amendTime']").val( $.date.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        // $("#editPortFacilityForm input[name='amenderName']").val("admin");
        // $("#editPortFacilityForm input[name='saveType']").val("insert");

        $('#editPortFacilityModal').modal('show');//现实模态框
    })
    //edict item
    $("#editPortFacility").click(function () {
        emptyAddForm();
        var selectedRowData = portFacility_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editPortFacilityForm input,#editPortFacilityForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPortFacilityForm"), 'update');
        $("#editPortFacilityForm input[name='amendTime']").val(  data['amendTime']);

        //设置港口的值
        setSelect2Value("editPortFacilityForm", "portId", data["portId"]);

        $('#editPortFacilityModal').modal('show');//现实模态框

    })
    //确定增加或者保存编辑；
    function submitEditPortFacilityModal() {
        var data = $("#editPortFacilityForm").serializeObject();
        var saveType = $("#editPortFacilityForm input[name='saveType']").val();

        // 测试使用
        // portFacility_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            url: getContextPath() + '/basedataPortFacility/' + saveType + '.do',
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
                    portFacility_table.ajax.reload(function () {
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


        $('#editPortFacilityModal').modal('hide');//现实模态框

    }


    // delete item
    $("#deletePortFacility").click(function () {
        var ids = [];
        var info;
        var selectedRowData = portFacility_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "数据吗?";
        callAlertModal(info);

        //确定删除
        $('#alertModal_Submit').click(function () {
            $.each(selectedRowData, function () {

                ids.push(this.portFacilityId);
            });

            $.ajax({
                url: getContextPath() + 'basedataPortFacility/delete.do',
                data: {
                    portFacilityIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp.code == 0) {
                        portFacility_table.ajax.reload(function () {
                            callSuccess(rsp.message);
                        });
                    }
                    else {
                        callAlert(rsp.message)
                    }

                    //portFacility_table.ajax.reload();
                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });

    });


    //refesh table
    $("#refreshPortFacility").click(function () {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        portFacility_table.ajax.reload();
    })

    $('#portFacilityTabletbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(portFacility_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#portFacilityTable tbody').on('dblclick', 'tr', function () {

        var data = portFacility_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);

    });
    $('#showPortFacilityDetail').on('click', function () {
        var rows_data = portFacility_table.rows('.selected').data();
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
    $("#resetSeachPortFacilityForm").click(function () {


        $("#searchPortFacilityForm")[0].reset();

        //设置港口默认值为空
        emptySelect2Value("searchPortFacilityForm", "portId");

    })

    // 清空弹框
    function emptyAddForm() {
        $("#editPortFacilityForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };
})();


