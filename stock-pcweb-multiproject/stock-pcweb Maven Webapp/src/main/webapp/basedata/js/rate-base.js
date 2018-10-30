//@ sourceURL=rate-base.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var rateBase = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditRatebaseModal
    });
    $().ready(
        function validateCommonsetForm() {
            Commonset_Validator = $("#editRatebaseForm").validate({
                rules: {
                    rateBase: {
                        required: true,
                        maxlength: 10
                    },
                    rateNameCn: {
                        maxlength: 50
                    },
                    rateCode: {
                        maxlength: 10
                    },
                    rateName: {
                        maxlength: 50
                    },
                    description: {
                        maxlength: 200
                    }
                },
                messages: {
                    rateBase: {
                        required: "这是必填字段",
                        maxlength: "请不要超过限制的10个字符数"
                    },
                    rateNameCn: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    rateCode: {
                        maxlength: "请不要超过限制的10个字符数"
                    },
                    rateName: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    description: {
                        maxlength: "请不要超过限制的50个字符数"
                    }
                }
            });
            //return Commonset_Validator.form();
        }
    );


    var ratebase_table;
    var paral = {
        // "rateBaseId":"计价单位ID",
        "rateCode": "计费代码",
        "rateName": "计价单位名称（EN）",
        "rateNameCn": "计价单位名称（CN）",
        "rateBase": "计价单位",
        "description": "描述"
    };

    Init();
    function Init() {
        ratebase_table = $("#ratebaseTable").DataTable({
            // ajax: "../mock_data/rate_base.json",
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
             * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
             */
            ordering : false,// 全局禁用排序
            deferRender: true,// 延迟渲染
            stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
            iDisplayLength: 20, // 默认每页显示多少条记录
            iDisplayStart: 0,
            // ordering: true,// 全局禁用排序
            serverSide: true,
            colReorder: true,//列位置的拖动
            autoWidth: true,
            // scrollX: true,
            scrollY:calcDataTableHeight(),
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            dom:'<"top">Brt<"bottom"flip><"clear">',
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url" : '../mock_data/rate-base.json',
                "url": getContextPath() + 'basedataRateBase/listByPage.do',
                "data": function (d) {
//					alert(JSON.stringify($('#searchForm').serializeObject()));
                    d.keys = JSON.stringify($('#searchRatebaseForm').serializeObject());
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
                    "data": "rateBaseId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                // { title: "计价单位ID", data:"rateBaseId"},
                {title: "计费代码", data: "rateCode"},
                {title: "计价单位名称（EN）", data: "rateName"},
                {title: "计价单位名称（CN）", data: "rateNameCn"},
                {title: "计价单位", data: "rateBase"},
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
                        else if  (type === 'display')
                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' : data;
                        else if  (type === 'copy') {
                            var api = new $.fn.dataTable.Api(meta.settings);
                            data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                        }
                        return data;
                    },
                    targets: [1, 2, 3, 4, 5, 6]
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
                    container: '#rateBase_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#rateBase_export-excel-current'

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
                    container: '#rateBase_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#rateBase_export-print-all'
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
                    container: '#rateBase_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#rateBase_export-columnVisibility'
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
    $('body').on('click', '.rate-base .checkall', function () {
        var check = $(this).prop("checked");
        $(".rate-base .checkchild").prop("checked", check);
        //通过调用datatables的select事件来触发选中
        $("#ratebaseTable tbody tr").each(function () {
            if ( ratebase_table.row( this, { selected: true } ).any() ) {
                ratebase_table.row( this ).deselect();
            }
            else {
                ratebase_table.row( this ).select();
            }
        });

    });
    //search
    $("#ratebaseSearch").click(function () {
            // var data = search_table.$('input, select');
            // console.log(data);
            alert("待完善，请先使用表格上面的Search功能！")
        }
    )

    //add
    function addRatebase() {
        emptyAddForm();
        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editRatebaseModalBody"), 'insert');


        $("#editRatebaseForm input[name='saveType']").val("insert");

        $('#editRatebaseModal').modal('show');//现实模态框
    }
    // $("#addRatebase").on('click', function () {
    //
    // });
    //edict item
    function editRatebase() {
        // $("#editRatebase").click(function () {
        emptyAddForm();
        var selectedRowData = ratebase_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editRatebaseForm input,#editRatebaseForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        //$("#editRatebaseForm input[name='saveType']").val("update");
        setDefaultValue($("#editRatebaseModalBody"), 'update');

        // $("#editRatebaseForm input[name='amendTime']").val($.date.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        $("#editRatebaseForm input[name='amendTime']").val(  data['amendTime']);
        $("#editRatebaseForm input[name='amenderName']").val("admin");
        $("#editRatebaseForm input[name='amender']").val("1");


        $('#editRatebaseModal').modal('show');//现实模态框
    }
    // });

    //确定增加或者保存编辑；
    function submitEditRatebaseModal() {

        //        if (!validateRatebaseForm()) {
        //            // alert("validate error!");
        //            return;
        //        };
        var data = $("#editRatebaseForm").serializeObject();
        var saveType = $("#editRatebaseForm input[name='saveType']").val();

        // 测试使用
        // ratebase_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            url: getContextPath() + '/basedataRateBase/' + saveType + '.do',
            data: data,
            cache: false,
            type: "POST",
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0)
                    callSuccess(res.message);

                ratebase_table.ajax.reload();
            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
        $('#editRatebaseModal').modal('hide');//现实模态
    }

    // delete item
    // $("#deleteRatebase").click(function () {
    function deleteRatebase() {
        var info;
        var selectedRowData = ratebase_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'Ratebase_confirmDelete');
        $('.Ratebase_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.rateBaseId);
            });

            $.ajax({
                url: getContextPath() + 'basedataRateBase/delete.do',
                data: {
                    rateBaseIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {


                    if (rsp.code == 0)
                        callSuccess(rsp.message);

                    ratebase_table.ajax.reload();
                },
                error: function () {
                    callAlert("删除失败！")
                }
            });
        });
    }
    // });
 //copy select
    function copySelectedRatebase() {
        var selectedRowData = ratebase_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要拷贝的数据！";
            callAlert(info);
            return;
        }
        var rowStr="";
        $("#ratebaseTable tr.selected").each(function () {
            var row=[];
            for (var i=1;i<$(this)[0].childNodes.length;i++){
                row.push($(this)[0].childNodes[i].innerText);
            }
             rowStr =row.join(" \n ")+""+rowStr;

        });
        clip.setText(rowStr);


    }

    //refesh table
    // $("#refreshRatebase").click(function () {
    function doSearch() {

        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        ratebase_table.ajax.reload();
    }

    $('#ratebaseTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(ratebase_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#ratebaseTable tbody').on('dblclick', 'tr', function () {
        var data = ratebase_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showRatebaseDetail').on('click', function () {
        var rows_data = ratebase_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    })


    // 清空弹框
    function emptyAddForm() {
        $("#editRatebaseForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };


    function doSearch() {

        ratebase_table.ajax.reload();

    }

    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#ratebaseTable tbody tr',
            callback: function (key, options) {
                //var row_data = ratebase_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addRatebase();
                        break;
                    case "Delete"://删除该节点
                        $("#ratebaseTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteRatebase();
                        break;
                    case "Edit"://编辑该节点
                        $("#ratebaseTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editRatebase();
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
        editRatebase: editRatebase,
        addRatebase:addRatebase,
        deleteRatebase:deleteRatebase,
        doSearch:doSearch,
        copySelectedRatebase:copySelectedRatebase
    };
})();