//@ sourceURL=role.js
var Role = (function() {
    var role_Tabe;
// var zTree_resource;//资源树
// var zTree_resourceOS;//二级资源树
    var paral = {
        // "accessGroup":0
        "amendTime": "最后修改时间",
        // "amender":"最近修改人",
        "amenderName": "最后修改人",
        // "checked":false
        // "createTime":"2017-06-05 09:28:22"
        // "creatorv:1
        "description": "角色描述",
        // "isDeleted":0
        "name": "角色名称"
        // "recordVersion":09
        // roleId:24
    };
    // $(document).ready(function () {

        Init();//加载表格
        //InitAddTable();
        function Init() {

            role_Tabe = $("#roleTable").DataTable({
                // bPaginate: true, //翻页功能
                // bLengthChange: true, //改变每页显示数据数量
                // paging: true,
                // lengthChange: false,
                // searching: true,
                // ordering: true,
                // info: true,
                // autoWidth: true,
                // select:true,
                // destroy: true,
                // scrollX: true,
                // //dom: 'Bfrtip',
                // ajax: "../mock_data/role.json",
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
                deferRender: true,// 延迟渲染
                stateSave: true,//开启状态记录，datatabls会记录当前在第几页，可显示的列等datables参数信息
                iDisplayLength: 20, // 默认每页显示多少条记录
                iDisplayStart: 0,
                ordering: false,// 全局禁用排序
                serverSide: true,
                autoWidth: true,
                // scrollX: true,
                colReorder: true,//列位置的拖动
                scrollY:calcDataTableHeight(),
                destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
                // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
                ajax: {
                    "type": "POST",
                    "url": getContextPath() + 'role/getRoles.do',
                    "data": function (d) {
//					alert(JSON.stringify($('#searchForm').serializeObject()));

                        d.keys = JSON.stringify($('#searchForm').serializeObject());
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
                        "data": "roleId",
                        "title": "<input type='checkbox' class='checkall' />",
                        "render": function (data, type, full, meta) {
                            return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                        },
                        "bSortable": false

                    },
                    {title: "角色名称", data: "name"},
                    {title: "角色描述", data: "description"},
                    {title: "最后修改人", data: "amenderName"},
                    {title: "最后修改时间", data: "amendTime"}

                ],
                columnDefs: [
                    {
                        orderable: false,
                        targets: 0
                    },
                    {
                        "render": function (data, type, full, meta) {
                            if  (type === 'display')
                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' : data;
                            else if  (type === 'copy') {
                                var api = new $.fn.dataTable.Api(meta.settings);
                                data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                            }
                            return data;
                        },
                        targets: [1, 2]
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
                        container: '#Role_export-excel-selected'
                    },
                    {
                        extend: 'excelHtml5',
                        text:"当前页导出Excel",
                        container: '#Role_export-excel-current'

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
                        container: '#Role_export-copy'
                    },
                    {
                        extend: 'print',
                        text: '打印全部',
                        container: '#Role_export-print-all'
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
                        container: '#Role_export-print-selected'
                    },
                    {
                        extend: 'colvis',
                        text: '自定义列表头',
                        container: '#Role_export-columnVisibility'
                    }
                ],
                select: {
                    // blurable: true,
                    style: 'multi',//选中多行
                    // info: false
                }
            });


        }

    // });

    // 表格的全选
    $('body').on('click', '.role .checkall', function () {
        var check = $(this).prop("checked");
        $(".role .checkchild").prop("checked", check);
        $("#roleTable tbody tr").each(function () {
            if ( role_Tabe.row( this, { selected: true } ).any() ) {
                role_Tabe.row( this ).deselect();
            }
            else {
                role_Tabe.row( this ).select();
            }
        });

    });


//选中一行
    $('#roleTable tbody').on('click', 'tr', function () {

        $(this).toggleClass('selected');
        $(this).find("input[type=checkbox]").prop("checked", $(this).hasClass('selected') ? true : false);

    });

//删除按钮
//         $("#deleteRole").click(function () {
    function deleteRole() {
        var selectedRowData = role_Tabe.rows('.selected').data();


        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据!"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'Role_confirmDelete');

        $('.Role_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.roleId);
            });

            $.ajax({
                url: getContextPath() + 'role/delete.do',
                data: {
                    roleIds: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    role_Tabe.ajax.reload();
                    if (rsp.code == 0)
                        callSuccess(rsp.message);
                },
                error: function () {
                    callAlert("删除失败！")
                }
            });

        });
    }


// 新增按钮
//         $('#addRole').on('click', function () {
    function addRole() {
        $("#roleForm")[0].reset();
        setDefaultValue($("#roleForm"), 'insert');
        $('#editRoleModal').modal('show');//现实模态框
    }


//修改按钮
    function editRole() {

        // $("#editRole").click(function () {
        emptyAddForm();

        var selectedRowData = role_Tabe.rows('.selected').data();

        if (selectedRowData.length < 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        if (selectedRowData.length > 1) {
            callAlert("每次只允许选择一条数据进行编辑！")
            return;
        }

        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#editRoleModal input "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });
        // $("#editRoleModal input[name='saveType']").val("update");

        setDefaultValue($("#roleForm"), 'update');
        $("#roleForm input[name='amendTime']").val(  data['amendTime']);

        $('#editRoleModal').modal('show');
    }


//确定增加或者保存编辑；
    $("#saveRole").click(function () {
        var saveType = $("#roleForm input[name='saveType']").val();

        // alert(JSON.stringify($('#roleForm').serializeObject()));

        $.ajax({
            url: getContextPath() + 'role/' + saveType + '.do',
            type: 'POST',
            data: $('#roleForm').serializeObject(),// {data:JSON.stringify($('#userForm').serializeObject())},
            dataType: 'json',
            async: false,
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0)
                    callSuccess(res.message);

                role_Tabe.ajax.reload();

            },
            error: function () {
                hideMask();
                callAlert("修改失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });

    })

//授权操作
    $("#authorizationRole").on('click', function () {
        var selectedRowData = role_Tabe.rows('.selected').data();

        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        // var data = selectedRowData[0];
        var id = selectedRowData[0].roleId;//获取角色id初始化弹框中树形

        $("#editAuthorizeResourceForm input[name='roleId'] ").val(id);
        $("#editAuthorizeResourceOSForm input[name='roleId'] ").val(id);

        $.ajax({
            // url : "sys/mock_data/resource/"+id+".json",
            url: getContextPath() + "roleResource/getRoleResource.do",

            type: 'POST',
            data: {
                roleId: id
            },
            dataType: 'json',
            async: false,
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (data) {
                hideMask();
                //初始化资源树
                InitRolezTree("roleTreeResouce", settingResource, data)
                // $.fn.zTree.init($("#roleTreeResouce"), settingResource, data).expandAll(true);
                // zTree_resource = $.fn.zTree.getZTreeObj("roleTreeResouce");
                // showZTreeChecked(zTree_resource,settingResource.data.key.name,"editAuthorizeResourceForm")
            },
            error: function () {
                hideMask();
                callAlert("获取数据失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });
        // $.ajax({
        //     url : "sys/mock_data/role_second.json",
        //     // url : getContextPath() + "roleResource/getRoleResourceOS.do",
        //
        //     type : 'POST',
        //     data : {
        //         roleId:id
        //     },
        //     async : false,
        //     success : function(data) {
        //初始化资源树OS
        $("#editAuthorizeResourceOSForm .title label").html('请先选择左边的资源');
        InitRolezTree("roleTreeResouceOS", settingResourceOS, null)
        // $.fn.zTree.init($("#roleTreeResouceOS"), settingResourceOS, data).expandAll(true);
        // zTree_resourceOS = $.fn.zTree.getZTreeObj("roleTreeResouceOS");
        // showZTreeChecked(zTree_resourceOS,settingResourceOS.data.key.name,"editAuthorizeResourceOSForm")
        //     },
        //     error : function() {
        //         callAlert("获取数据失败!");
        //         // $.messager.alert('系统提示','申请失败,请重试！','warning');
        //     }
        // });
        $('#editAuthorizeResource').modal('show');

    });


// 刷新按钮
//     $("#refreshRole").click(function () {
function doSearch() {
        role_Tabe.ajax.reload();
    }


// 清空弹框
    function emptyAddForm() {
        $("#roleForm")[0].reset();
    };


    function doSearch() {
        role_Tabe.ajax.reload();

    }

// click item display detail infomation
    $('#roleTable tbody').on('dblclick', 'tr', function () {
        var data = role_Tabe.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showRoleDetail').on('click', function () {
        var rows_data = role_Tabe.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    });
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#roleTable tbody tr',
            callback: function (key, options) {
                //var row_data = role_Tabe.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addRole();
                        break;
                    case "Delete"://删除该节点
                        $("#roleTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteRole();
                        break;
                    case "Edit"://编辑该节点
                        $("#roleTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editRole();
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
        editRole: editRole,
        addRole:addRole,
        deleteRole:deleteRole,
        doSearch:doSearch
    };
})();

