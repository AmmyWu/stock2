//@ sourceURL=port-group.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行submitEditPortGroupModal
(function() {
    $.validator.setDefaults({
        submitHandler: submitEditPortGroupModal
    });

    $().ready(
        function validatePortGroupForm() {
            $("#editPortgroupForm").validate({
                rules: {
                    portGroupCode: {
                        maxlength: 20
                    },
                    portGroupType: {
                        maxlength: 20
                    }
                },
                messages: {
                    portGroupCode: {
                        maxlength: "最多输入20个字符"
                    },
                    portGroupType: {
                        maxlength: "最多输入20个字符"
                    }
                }
            });
        }
    );

    // var Portgroup_Validator;
    // function submitEditPortGroupModal() {
    //     Portgroup_Validator= $("#editPortgroupForm").validate({
    //         rules:
    //             {
    //                 portGroupCode: "required"
    //             }
    //     });
    //     return Portgroup_Validator.form();
    // }

    var portGroup_table;
    var paral = {
        // "portGroupId":"港口组ID",
        "portGroupCode": "港口组代码",
        "portGroupName": "港口组名称（EN）",
        "portGroupNameCn": "港口组名称（CN）",
        "portGroupDesc": "港口组描述",
        "portGroupType": "港口组类型",
        "description": "描述"
    };

    Init();
    function Init() {
        portGroup_table = $("#portgroupTable").DataTable({
            fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
            // data: dataSet,
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
                // "url": "../mock_data/port-group.json",
                "url": getContextPath() + 'basedataPortGroup/listByPage.do',
                "data": function (d) {
// 					alert( JSON.stringify(value, replacer, space)ringify($('#port-group_search-table').serializeObject()));
                    d.keys = JSON.stringify($('#searchPortgroupForm').serializeObject());
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
                    "data": "portGroupId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
//                { title: "港口组ID", data:"portGroupId"},
                {title: "港口组代码", data: "portGroupCode"},
                {title: "港口组名称（EN）", data: "portGroupName"},
                {title: "港口组名称（CN）", data: "portGroupNameCn"},
                {title: "港口组描述", data: "portGroupDesc"},
                {title: "港口组类型", data: "portGroupType"},
                {title: "描述", data: "description"},
                {
                    title: "最后修改人",
                    "data": "baseModel",
                    "render": function (data, type, full, meta) {
                        return (data == null || data == undefined ) ? '' : data.amenderName;
                    },
                    // "bSortable": false,
                },
                {title: "最后修改时间", data: "amendTime"}


            ],
            columnDefs: [
                {
                    orderable: false,
                    targets: 0
                },
                {
                    "render": function (data, type, full, meta) {
                        return type === 'display' && data.length > 30 ?
                            '<span title="' + data + '">' + data + '</span>' :
                            data;
                    },
                    targets: [1, 2, 3, 4, 5, 6, 7]
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
    $('body').on('click', '.port-group .checkall', function () {
        var check = $(this).prop("checked");
        $(".port-group .checkchild").prop("checked", check);
        if (check) {
            $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
        }
        else {
            $("tr").removeClass("selected");
        }

    });


    //add
    $("#addPortgroup").on('click', function () {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editPortgroupForm"), 'insert');


        $('#editPortgroupModal').modal('show');//现实模态框
    })
    //edict item
    $("#editPortgroup").click(function () {
        emptyAddForm();
        var selectedRowData = portGroup_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editPortgroupForm input,#editPortgroupForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        setDefaultValue($("#editPortgroupForm"), 'update');
        // $("#editPortgroupForm input[name='saveType']").val("update");
        $("#editPortgroupForm input[name='amendTime']").val(  data['amendTime']);

//
//        $("#editPortgroupForm input[name='createTime']").val( $.date.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
//        $("#editPortgroupForm input[name='createrName']").val("admin");

        $('#editPortgroupModal').modal('show');//现实模态框

    })
    //确定增加或者保存编辑；
    function submitEditPortGroupModal() {
        var data = $("#editPortgroupForm").serializeObject();
        var saveType = $("#editPortgroupForm input[name='saveType']").val();

        // 测试使用
//            portGroup_table .row.add(data).draw();//插入一行
//            callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataPortGroup/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            success: function (res) {

                if (res.code == 0) {
                    portGroup_table.ajax.reload();
                    callSuccess(res.message);
                }
                else

                    callAlert(res.message);


            },
            error: function () {
                callAlert("增加失败");
            }
        });
        $('#editPortgroupModal').modal('hide');//现实模态框
    }


    // delete item
    $("#deletePortgroup").click(function () {
        var ids = [];
        var info;
        var selectedRowData = portGroup_table.rows('.selected').data();
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

                ids.push(this.portGroupId);
            });

            $.ajax({
                url: getContextPath() + 'basedataPortGroup/delete.do',
                data: {
                    portGroupIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    if (rsp.code == 0) {
                        portGroup_table.ajax.reload();
                        callSuccess(rsp.message);
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
    $("#refreshPortgroup").click(function () {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        portGroup_table.ajax.reload();
    })

    $('#portgroupTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(portGroup_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#portgroupTable tbody').on('dblclick', 'tr', function () {
        var data = portGroup_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showPortgroupDetail').on('click', function () {
        var rows_data = portGroup_table.rows('.selected').data();
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
        $("#editPortgroupForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };
})();