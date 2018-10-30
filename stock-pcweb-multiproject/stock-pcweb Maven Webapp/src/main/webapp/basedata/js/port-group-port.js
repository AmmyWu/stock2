//@ sourceURL=port-group-port.js
 // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
(function() {
    $.validator.setDefaults({
        submitHandler: submitEditPortGroupPortModal
    });

    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();

        // //初始化查询form的港口下拉列表
        initSelect2FromRedis("searchPortGroupPortForm", "portGroupId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portGroupId", "portGroupCode");

        //初始化编辑form的港口下拉列表
        initSelect2FromRedis("editPortgroupportForm", "portGroupId", "redisController/listIdNameByName.do?name=basedataPort", "{}", "portGroupId", "portGroupCode");

        //港口组发生变化时
        //  $("#editPortgroupportForm select[name='portGroupId']").on('select2:select', function (evt) {
        //      // Do something
        //
        //     alert( $("#editPortgroupportForm select[name='portGroupId'] option:selected").html());
        //      // alert($(this).select2('option:selected').text());
        //  });

        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };
    });


    $().ready(
        function validateBankForm() {
            $("#editPortgroupportForm").validate({
                rules: {
                    portCode: {
                        maxlength: 5
                    }
                },
                messages: {
                    portCode: {
                        maxlength: "最多输入5个字符"
                    }
                }
            });
        }
    );
    var portGroupPort_table;
    var paral = {
        // "portGroupPortsId":"港口组港口ID",
        "basPortGroupId": "港口组标识",
        "portGroupId": "港口组ID ",
        // "portCode":"港口组代码",
        "portName": "港口组港口名称（EN））",
        "portNameCn": "港口组港口名称（CN）",
        "description": "描述"
    };

    Init();
    function Init() {
        portGroupPort_table = $("#portgroupportTable").DataTable({
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
// 			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url": "../mock_data/port-group-port.json",
                "url": getContextPath() + 'basedataPortGroupPorts/listByPage.do',
                "data": function (d) {
                    // alert( JSON.stringify($('#searchPortGroupPortForm').serializeObject()));
                    d.keys = JSON.stringify($('#searchPortGroupPortForm').serializeObject());
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
                    "data": "portGroupPortsId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                // { title: "港口组港口ID", data:"portGroupPortsId"},
                // { title: "港口组标识",data:"basPortGroupId" },
                {title: "港口组ID", data: "portGroupId"},
                // { title: "港口组代码",data:"portCode" },
                {title: "港口组港口名称（EN）", data: "portName"},
                {title: "港口组港口名称（CN）", data: "portNameCn"},
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
                    targets: [1, 2, 3, 4, 5, 6, 7]
                }
            ],
        });
    }

    // select/not select all
    $('body').on('click', '.port-group-port .checkall', function () {
        var check = $(this).prop("checked");
        $(".port-group-port .checkchild").prop("checked", check);
        if (check) {
            $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
        }
        else {
            $("tr").removeClass("selected");
        }

    });

    //add
    $("#addPortgroupport").on('click', function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPortgroupportForm"), 'insert');

        $('#editPortgroupportModal').modal('show');//现实模态框
    })
    //edict item
    $("#editPortgroupport").click(function () {
        emptyAddForm();
        var selectedRowData = portGroupPort_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editPortgroupportForm input,#editPortgroupportForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        // $("#editPortgroupportForm input[name='saveType']").val("update");
        setDefaultValue($("#editPortgroupportForm"), 'update');
        $("#editPortgroupportForm input[name='amendTime']").val(  data['amendTime']);


        $('#editPortgroupportModal').modal('show');//现实模态框

    })
    //确定增加或者保存编辑；
    function submitEditPortGroupPortModal() {
        var data = $("#editPortgroupportForm").serializeObject();
        var saveType = $("#editPortgroupportForm input[name='saveType']").val();

        // 测试使用
        // portGroupPort_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataPortGroupPorts/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    portGroupPort_table.ajax.reload();
                    callSuccess(res.message);
                }
                else

                    callAlert(res.message);
            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
        $('#editPortgroupportModal').modal('hide');//现实模态框
    }

    // delete item
    $("#deletePortgroupport").click(function () {
        var ids = [];
        var info;
        var selectedRowData = portGroupPort_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据！"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "数据吗?";
        callAlertModal(info);

        //确定删除
        $('#alertModal_Submit').click(function () {
            $.each(selectedRowData, function () {

                ids.push(this.portGroupPortsId);
            });

            $.ajax({
                url: getContextPath() + 'basedataPortGroupPorts/delete.do',
                data: {
                    portGroupPortsIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        portGroupPort_table.ajax.reload();
                    } else
                        callAlert(rsp.message)


                },
                error: function () {
                    callAlert("删除失败！")
                }
            });
        });
    });


    //refesh table
    $("#refreshPortgroupport").click(function () {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        portGroupPort_table.ajax.reload();
    })

    $('#portgroupportTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(portGroupPort_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#portgroupportTable tbody').on('dblclick', 'tr', function () {
        var data = portGroupPort_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showPortgroupportDetail').on('click', function () {
        var rows_data = portGroupPort_table.rows('.selected').data();
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
    $("#resetSearchPortGroupPortForm").click(function () {


        $("#searchPortGroupPortForm")[0].reset();

        //设置港口默认值为空
        emptySelect2Value("searchPortGroupPortForm", "portGroupId");

    })

    // 清空弹框
    function emptyAddForm() {
        $("#editPortgroupportForm")[0].reset();

        $("label.error").remove();//清除提示语句
    };

})();

