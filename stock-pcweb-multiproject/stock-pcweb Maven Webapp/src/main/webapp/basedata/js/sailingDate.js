//@ sourceURL=SailingDate.js
// $(document).ready(function() {
// $('#lxy_basicdata_tb').DataTable().empty();
//标题行
// var SailingDate_Validator;
var sailingDate = (function() {
    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();

        initSelect2_SailingDate();

        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };

        //Date picker
        $('.sailingDate .startTime,.sailingDate .endTime').datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        });

        //Date picker
        $('.form_datetime').datetimepicker({
            autoclose: true,
            format: "yyyy-mm-dd hh:ii"
        });
    });

    function initSelect2_SailingDate() {

        // //初始化查询form的港口下拉列表sailingDateSearchForm
        initSelect2FromRedis("sailingDateSearchForm", "serviceLineId", "redisController/listIdNameByName.do?name=basedataServiceLine", "{}", "serviceLineId", "serviceLineName");
        initSelect2FromRedis("sailingDateSearchForm", "vesselParameterId", "redisController/listIdNameByName.do?name=basedataVesselParameter", "{}", "vesselParameterId", "vesselParameterName");
        initSelect2FromRedis("sailingDateSearchForm", "portId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portId", "portName");

        //初始化编辑form的港口下拉列表
        initSelect2FromRedis("editSailingDateForm", "serviceLineId", "redisController/listIdNameByName.do?name=basedataServiceLine", "{}", "serviceLineId", "serviceLineName");
        initSelect2FromRedis("editSailingDateForm", "vesselParameterId", "redisController/listIdNameByName.do?name=basedataVesselParameter", "{}", "vesselParameterId", "vesselParameterName");
        initSelect2FromRedis("editSailingDateForm", "portId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portId", "portName");


    }

    $("#sailingDateSearchForm select[name='vesselParameterId']").on("select2:select", function (e) {

        var vesselParameterId = $("#sailingDateSearchForm select[name='vesselParameterId']").val();

        initSelect2FromRedis("sailingDateSearchForm", "vesselId", "redisController/listIdNameByName.do?name=basedataVessel_" + vesselParameterId, "{}", "vesselId", "vesselName");

    });

    $("#editSailingDateForm select[name='vesselParameterId']").on("select2:select", function (e) {

        var vesselParameterId = $("#editSailingDateForm select[name='vesselParameterId']").val();

        initSelect2FromRedis("editSailingDateForm", "vesselId", "redisController/listIdNameByName.do?name=basedataVessel_" + vesselParameterId, "{}", "vesselId", "vesselName");

    });


    $.validator.setDefaults({
        submitHandler: submitEditSailingDateModal
    });

    $().ready(
        function validateSailingDateForm() {
            $("#editSailingDateForm").validate({
                rules: {
                    serviceLineId: {
                        maxlength: 11,
                        digits: true
                    },
                    vesselId: {
                        maxlength: 11,
                        digits: true
                    },
                    voyage: {
                        maxlength: 20
                    },
                    course: {
                        maxlength: 20
                    },
                    dischargingPortId: {
                        maxlength: 11,
                        digits: true
                    },
                    vesselParameterCode: {
                        maxlength: 20
                    },
                    type: {
                        maxlength: 20
                    }
                },
                messages: {
                    serviceLineId: {
                        maxlength: "最多输入11个字符",
                        digits: "请输入数字"
                    },
                    vesselId: {
                        maxlength: "最多输入11个字符",
                        digits: "请输入数字"
                    },
                    voyage: {
                        maxlength: "最多输入20个字符"
                    },
                    course: {
                        maxlength: "最多输入20个字符"
                    },
                    dischargingPortId: {
                        maxlength: "最多输入11个字符",
                        digits: "请输入数字"
                    },
                    vesselParameterCode: {
                        maxlength: "最多输入20个字符"
                    },
                    type: {
                        maxlength: "最多输入20个字符"
                    }
                }
            });
        }
    );
    console.log("sailingDate");
    var sailingDate_table;
    var paral = {
        // "sailingDateId": "银行ID",
        "serviceLineName": "航线",
        "vesselParameterName": "船名",
        "vesselName": "船名",
        "voyage": "航次",
        "dischargingPortName": "停靠港",
        "ltaTime": "LTA",
        "ltdTime": "LTD",
        "etaAtaTime": "ETA/ATA",
        "etaAtdTime": "ETD/ATD",
        "type": "类型",
        "TransitTime": "航线天数",
        "CyCutoff": "截止进箱时间",
        "description": "备注"
    };
    InitSailingDate();
    function InitSailingDate() {
        sailingDate_table = $("#sailingDateTable").DataTable({
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
            //scrollX: true,
            scrollY:calcDataTableHeight(),
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // buttons: [ 'colvis' ],
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
// 			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // url: '../mock_data/SailingDate.json',

                "url": getContextPath() + 'basedataSailingDate/listByPage.do',
                "data": function (d) {
                    // alert( JSON.stringify($('#port-group-port_search-form').serializeObject()));
                    d.keys = JSON.stringify($('#sailingDateSearchForm').serializeObject());
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
                    "data": "sailingDateId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false

                },
                // {title: "航线", data: "serviceLineId"},
                // {title: "船司", data: "vesselParameterId"},
                // {title: "船名", data: "vesselId"},
                {title: "航线", data: "serviceLineName"},
                {title: "船司", data: "vesselParameterName"},
                {title: "船名", data: "vesselName"},
                {title: "航次", data: "voyage"},
                {title: "停靠港", data: "dischargingPortName"},
                {title: "LTA", data: "ltaTime"},
                {title: "LTD", data: "ltdTime"},
                {title: "ETA/ATA", data: "etaAtaTime"},
                {title: "ETD/ATD", data: "etdAtdTime"},
                {title: "类型", data: "type"},
                {title: "航线天数", data: "sailingDays"},
                {title: "截止进箱时间", data: "cutoffTime"},
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
                    targets: [1, 2, 3, 4, 5, 6, 7, 8, 9]
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
                    container: '#sailingDate_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#sailingDate_export-excel-current'

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
                    container: '#sailingDate_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#sailingDate_export-print-all'
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
                    container: '#sailingDate_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#sailingDate_export-columnVisibility'
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
    $('body').on('click', '.sailingDate .checkall', function () {
        var check = $(this).prop("checked");
        $(".sailingDate .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#sailingDateTable tbody tr").each(function () {
            if ( sailingDate_table.row( this, { selected: true } ).any() ) {
                sailingDate_table.row( this ).deselect();
            }
            else {
                sailingDate_table.row( this ).select();
            }
        });

    });


//add
    function addSailingDate() {
    // $("#addSailingDate").on('click', function () {
    emptyAddForm();
    //设置默认值amender ,amenderName,amendTime,saveType
    setDefaultValue($("#editSailingDateForm"), 'insert');

    $('#editSailingDateModal').modal('show');//现实模态框

    // })
}

//edict item
    function editSailingDate() {
    // $("#editSailingDate").click(function () {
        emptyAddForm();
        var selectedRowData = sailingDate_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editSailingDateForm input,#editSailingDateForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //设置国家和航线默认值为空
        setFormSelect2Value("editSailingDateForm", ["serviceLineId", "vesselParameterId", "vesselId", "portId", "isIssued"],
            [data["serviceLineId"], data["vesselParameterId"], data["vesselId"], data["portId"], data["isIssued"]]);


        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editSailingDateModalBody"), 'update');
        $("#editSailingDateForm input[name='amendTime']").val(  data['amendTime']);

        $('#editSailingDateModal').modal('show');//现实模态框

    // })
    }
    // 弹出框提交按钮
    function submitEditSailingDateModal() {
        // if(!validateSailingDateForm()){
        //     alert("validate error!");
        //     return;
        // }
        var data = $("#editSailingDateForm").serializeObject();

        var saveType = $("#editSailingDateForm input[name='saveType']").val();

        $.ajax({
            url: getContextPath() + 'basedataSailingDate/' + saveType + '.do',
            type: 'POST',
            data: data,
            dataType: 'json',
            async: false,
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();
                if (res.code == 0) {
                    callSuccess(res.message);

                    sailingDate_table.ajax.reload();
                }
                else

                    callAlert(res.message);

            },
            error: function () {
                hideMask();
                alert("修改失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });

        $('#editSailingDateModal').modal('hide');//现实模态框
    }


// delete item
    function deleteSailingDate() {
//     $("#deleteSailingDate").click(function () {
        var info;
        var selectedRowData = sailingDate_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据!"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'SailingDate_confirmDelete');
        $('.SailingDate_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.sailingDateId);
            });

            $.ajax({
                url: getContextPath() + 'basedataSailingDate/delete.do',
                data: {
                    sailingDateIds: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();
                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        sailingDate_table.ajax.reload();
                    } else
                        callAlert(rsp.message)


                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });

        // });
    }




// 清空弹框
    function emptyAddForm() {
        $("#editSailingDateForm")[0].reset();

        //设置select2默认值为空
        emptyFormSelect2Value("editSailingDateForm", ["serviceLineId", "vesselParameterId", "vesselId", "portId", "isIssued"]);

        $("label.error").remove();//清除提示语句
    };

//refesh SailingDate_table
//     $("#refreshSailingDate").click(function () {
    function doSearch() {
        // SailingDate_table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        // $("#editSailingDateForm")[0].reset();
        sailingDate_table.ajax.reload();
    }

    $('#sailingDateTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(sailingDate_table.rows('.selected').data().length);
    });


// click item display detail infomation
    $('#sailingDateTable tbody').on('dblclick', 'tr', function () {
        var data = sailingDate_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showSailingDateDetail').on('click', function () {
        var rows_data = sailingDate_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    });

//重置查询条件
    $("#resetSailingDateForm").click(function () {


        $("#sailingDateSearchForm")[0].reset();

        //设置select2默认值为空
        emptyFormSelect2Value("sailingDateSearchForm", ["serviceLineId", "vesselParameterId", "vesselId", "portId", "isIssued"]);

    });
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#sailingDateTable tbody tr',
            callback: function (key, options) {
                //var row_data = sailingDate_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addSailingDate();
                        break;
                    case "Delete"://删除该节点
                        $("#sailingDateTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteSailingDate();
                        break;
                    case "Edit"://编辑该节点
                        $("#sailingDateTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editSailingDate();
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

    //<!--航线/船司/船名/航次/航向-->的查询集合
    //$(function(){
    //
    //    $("#shipSet").change(function(){
    //        //var ship=document.getElementById("shipSet");
    //        var x=document.getElementById("shipSet").selectedIndex;
    //        var y=document.getElementById("shipSet").options;
    //        var ship=y[x].index;
    //        //alert(ship);
    //        $("#shipSet").closest("label").nextAll().css("display","none");
    //        if(ship<3){
    //            $("#shipSet").closest("label").nextAll().eq(ship*2+1).css("display","inline-block");
    //        }else if(ship<4){
    //            $("#shipSet").closest("label").nextAll().eq(ship*2).css("display","inline-block");
    //        }else{
    //            $("#shipSet").closest("label").nextAll().eq(ship*2-1).css("display","inline-block");
    //        }
    //
    //    });
    //});
    return {
        // 将供页面该方法调用
        editSailingDate: editSailingDate,
        addSailingDate:addSailingDate,
        deleteSailingDate:deleteSailingDate,
        doSearch:doSearch
    };

})();

