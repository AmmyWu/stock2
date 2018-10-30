//@ sourceURL=user.js
var User = (function() {
    var user_tb, role_tb, authentication_tb;
    var paral = {
        // accessGroup: null
        // amendTime: null,
        // amender: null,
        // canLogin: null,
        // canLoginweb: null,
        // canShowinweb: null,
        // counterMan: null,
        // createTime: null,
        // creator: 0,
        creatorName: "创建人",
        // defaultLang: null
        // description: null,
        // isDeleted: null,
        // loginStatus: null,
        // password: null,
        // pwdEffictiveDay: null,
        // pwdModifyDate: null,
        // recordVersion: null
        roleNames: "角色名称",
        // sCreateTime: "创建人",
        sLastLoginTime: "创建时间",
        // signature: null,
        // smtpServer: null,
        // status: null,
        type: "员工",
        userDetailId: "客户或者员工ID",
        userDetailName: "客户或者员工姓名",
        // userId: 8
        username: "用户名称",
        status:"状态"
    }
    $.validator.setDefaults({
        submitHandler: submitEditUserModal
    });


    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();

        // //初始化查询form的港口下拉列表
        // initSelect2("searchCommonSetForm","superiorId","basedataCommonSet/listByKeys.do","{superiorId:0}","commonSetId","cnName");

        //初始化编辑form的下拉列表
        // initSelect2FromDB("userForm", "userDetailId", "employee/listEmployeesOfUnallotUsers.do", "{}", "employeeId", "employeeName");

        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };

    });


    $().ready(
        function validateCommonsetForm() {
            Commonset_Validator = $("#userForm").validate({
                rules: {
                    userDetailId: {
                        maxlength: 11,
                        digits: true
                    }
                },
                messages: {
                    userDetailId: {
                        digits: "请输入数字",
                        maxlength: "最多输入11位数字"

                    }
                }
            });
        }
    );


//$(document).ready(function () {

    init_user_tb();

    $('#user_tb tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
            $(this).find("input[type=checkbox]").prop("checked", $(this).hasClass('selected') ? true : false);

        });
    // 表格的全选
    $('body').on('click', '.role .checkall', function () {
        var check = $(this).prop("checked");
        $(".user .checkchild").prop("checked", check);
        $("#user_tb tbody tr").each(function () {
            if ( user_tb.row( this, { selected: true } ).any() ) {
                user_tb.row( this ).deselect();
            }
            else {
                user_tb.row( this ).select();
            }
        });

    });
//					// 查询按钮
//					$("#searchUser").click(function() {
//						
////						user_tb.search.value = $('#searchUserForm').serializeObject();
//						
//						user_tb.ajax.load();
//
//					})

    // 角色分配弹出框
    function setUserRole() {
        // $("#setUserRole").click(function () {

        var selectedRowData = user_tb.rows('.selected').data();
        if (selectedRowData.length < 1) {
            callAlert("请选择一条记录进行角色分配！")
            return;
        }
        if (selectedRowData.length > 1) {
            callAlert("每次只允许选择一条数据进行角色分配！")
            return;
        }

        if (role_tb == undefined)
            init_role_tb(selectedRowData[0].userId);
        else {
            //todo 重新初始化问题未解决 {userId:selectedRowData[0].userId}
            role_tb.ajax.reload();

        }

        // 引入user_role.json文件的内容,生成模态框内容
        $('#user_roleModal').modal('show');
        // });
    }


    // 授权账号弹出框
    function setUserAuthenticationAccount() {
        // $("#setUserAuthenticationAccount").click(function () {

        var selectedRowData = user_tb.rows('.selected').data();

        if (selectedRowData.length < 1) {
            callAlert("请选择设置账号的用户！")
            return;
        }


        if (selectedRowData.length > 1) {
            callAlert("一次只能设置一个用户的账号！")
            return;
        }


        if (authentication_tb == undefined)
            init_authentication_tb(selectedRowData[0].userId);
        else {
            //todo 重新初始化问题未解决 {userId:selectedRowData[0].userId}

            var url = getContextPath() + "authentication/getAuthenticationMap.do?userId=" + selectedRowData[0].userId;
            authentication_tb.ajax.url(url).load();
        }


        // 引入user_role.json文件的内容,生成模态框内容
        $('#user_authenticationModal').modal('show');
    }
    // });

    // 删除按钮
    // $("#deleteUser").click(function () {
function deleteUser() {
        var selectedRowData = user_tb.rows('.selected').data();

        if (selectedRowData.length < 1) {
            info = "请选择需要删除的数据!"
            callAlert(info);
            return;
        }
        info = "确定要删除" + selectedRowData.length + "条数据吗?";
        callAlertModal(info,'User_confirmDelete');

        $('.User_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.userId);
            });


            $.ajax({
                url: getContextPath() + 'user/delete.do',
                data: {
                    usersId: ids.join(',')
                },
                dataType: 'json',
                success: function (rsp) {

                    user_tb.ajax.reload();
                    callSuccess("删除成功！");
                },
                error: function () {

                }
            });

        });
    }

    // 刷新按钮
    // $("#refreshUser").click(function () {
function doSearch() {
        user_tb.ajax.reload();
    }

    // 新增按钮

    //$('#addUser').on('click', function () {
function addUser() {
        // $('#userForm')[0].reset();
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#userForm"), 'insert');

        //初始化编辑form的下拉列表
        initSelect2FromDB("userForm", "userDetailId", "employee/listEmployeesOfUnallotUsers.do", "{}", "employeeId", "employeeName");

        //将角色输入框设置为只读
        setReadonlyForInputs([ $("#userForm input[name='roleNames']")]);

        $('#addModalLabel').html("新增");
       $('#editUserModal').modal('show');//现实模态框

    }

    // 修改按钮
    // $('#editUser').on('click', function () {
    function editUser() {
            emptyAddForm();
            var selectedRowData = user_tb.rows('.selected')
                .data();

            if (selectedRowData.length < 1) {
                callAlert("请选择一条记录进行编辑！")
                return;
            }
            if (selectedRowData.length > 1) {
                callAlert("每次只允许选择一条数据进行编辑！")
                return;
            }

        //初始化编辑form的下拉列表
        initSelect2FromDB("userForm", "userDetailId", "employee/listEmployeesOfUnallotUsers.do", "{}", "employeeId", "employeeName");

        var data = selectedRowData[0];

            // 循环给表单赋值
            $.each($("#userForm input,#userForm select "), function (i, input) {

                $(this).val(data[$(this).attr("name")]);

            });

            //设置默认值amender ,saveType
            setDefaultValue($("#userForm"), 'update');
            $("#userForm input[name='amendTime']").val(  data['sAmendTime']);

            //将角色输入框设置为只读
            setReadonlyForInputs([ $("#userForm input[name='roleNames']")]);
            //设置下拉值
            setSelect2Value("userForm", "userDetailId", data['userDetailId']);

            $('#addModalLabel').html("修改");


            $('#editUserModal').modal('show');

        }

    // 新增/修改模态框保存按钮
    function submitEditUserModal() {
        // alert(JSON.stringify($('#userForm').serializeObject()));

        if($.string.isNullOrEmpty ($("#userForm input[name='username']").val() ) ||
            $.string.isNullOrEmpty ($("#userForm select[name='userDetailId']").val() ) ){
            callAlert("用户名或姓名不能为空！");

            return ;
        }


        var saveType = $("#userForm input[name='saveType']").val();

        $.ajax({
            url: getContextPath() + 'user/' + saveType + '.do',
            type: 'POST',
            data: $('#userForm').serializeObject(),// {data:JSON.stringify($('#userForm').serializeObject())},
            dataType: 'json',
            async: false,
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    user_tb.ajax.reload();
                } else
                    callAllert(rsp.message);
            },
            error: function () {
                alert("修改失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });
        $('#editUserModal').modal('hide');//现实模态框
    }

    // 角色分配保存按钮
    $('#user_roleModal .save_roleModal_btn').on('click', function () {

            var selectedRowData = role_tb.rows('.selected').data();

            if (selectedRowData.length < 1) {
                callAlert("请选择分配的编辑！")
                return;
            }


            var userId = user_tb.rows('.selected').data()[0].userId;

            var ids = [];


            $.each(selectedRowData, function () {

                ids.push(this.roleId);
            });

            $.ajax({
                url: getContextPath() + 'userRole/insert.do',
                data: {
                    userId: userId,
                    roleId: ids.join(',')
                },
                dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {

                    hideMask();
                    if (rsp.code == 0) {
                        callSuccess(rsp.message);
                        user_tb.ajax.reload();
                    } else
                        callAllert(rsp.message);

                },
                error: function () {
                    hideMask();
                    callAllert("角色分配失败！");
                }
            });


        });

    //授权账号 新增按钮
    $('#user_authenticationModal .add_authenticationModal_btn').on('click', function () {
        emptyAddForm();

        $('#authenticationForm')[0].reset();

        var selectedRow = user_tb.row(".selected").data();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#authenticationForm"), 'insert');


        $("#authenticationForm input[name='userId']").val(selectedRow.userId);
        //
        // $("#authenticationForm input[name='saveType']").val("insert");
        //
        // $("#authenticationForm input[name='sCreateTime']").val($.date.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        // $("#authenticationForm input[name='creatorName']").val("admin");

        $('#user_authenticationAddModal').modal('show');

    });


    // 授权账号修改按钮
    $('#user_authenticationModal .edit_authenticationModal_btn')
        .on(
            'click',
            function () {


                var selectedRowData = authentication_tb.rows('.selected').data();

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
                $.each($("#authenticationForm input,#authenticationForm select "), function (i, input) {

                    $(this).val(data[$(this).attr("name")]);

                });

                $("#authenticationForm input[name='saveType']").val("update");

                $('#user_authenticationAddModal').modal('show');
            });

    // 授权账号新增/修改保存按钮
    $('#user_authenticationAddModal .save_authenticationModal_btn').on('click', function () {

            if (!isCorrectInput())
                return;

            var saveType = $("input[name='saveType']").val();

            //增加时判断账号是否已存在
            if (saveType == 'insert' && isAuthenticationExist()) {

                callAlert("该账号已存在！");
                return;
            }

            $.ajax({
                url: getContextPath() + 'authentication/' + saveType
                + '.do',
                type: 'POST',
                data: $('#authenticationForm').serializeObject(),// {data:JSON.stringify($('#userForm').serializeObject())},
                dataType: 'json',
                async: false,
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (res) {
                    hideMask();

                    if (res.code == 0)
                        callSuccess(res.message);
                    else
                        callAlert(res.message);

                    authentication_tb.ajax.reload();

                    $('#user_authenticationAddModal').modal('hide');
                },
                error: function () {
                    hideMask();
                    alert("修改失败!");
                    // $.messager.alert('系统提示','申请失败,请重试！','warning');
                }
            });

        });
//});


    var isAuthenticationExist = function () {

        var result = true;

        $.ajax({
            url: getContextPath() + 'authentication/isExistAccount.do',
            type: 'POST',
            data: {
                account: $("#authenticationForm input[name='account']").val()
            },
            dataType: 'json',
            async: false,
            success: function (res) {

                result = res;

            },
            error: function () {
                alert("修改失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');


                result = true;
            }
        });


        return result;
    }


//修改密码
    var isCorrectInput = function () {

        account = $("#authenticationForm input[name='account']").val();
        password = $("#authenticationForm input[name='password']").val();

        confirmPassword = $("#authenticationForm input[name='confirmPassword']").val();
//     type = $('#type');

        if (account == '') {
            callAlert('用户名不能为空！');
            return false;
        }
        if (password == '') {
            callAlert('请输入新密码！');
            return false;
        }
        if (confirmPassword == '') {
            callAlert('请输入确认密码！');
            return false;
        }
        if (password.length < 6) {
            callAlert('密码长度不能小于6位！');
            return false;
        }
        if (confirmPassword != password) {
            callAlert('两次密码不一致！请重新输入');
            return false;
        }


//    if ($('#type').combobox('getValue') == '') {
//    	alert('类型不能为空！');
//        return false;
//    }

        return true;
    }

    function changeUserType(selectObject, selectedValue) {

        // alert('aa' + selectedValue);
        if ("客户" == selectedValue) {
            alert('客户表还未对接！');
            $(selectObject).val('员工');
            // initSelect2FromDB("userForm","userDetailId","","{}","employeeId","employeeName");
        } else
            initSelect2FromDB("userForm", "userDetailId", "employee/listEmployeesOfUnallotUsers.do", "{}", "employeeId", "employeeName");

    }

    function init_user_tb() {

        user_tb = $("#user_tb").DataTable(
                {
                    // data: dataSet,
                    // bPaginate: true, //翻页功能
                    // bLengthChange: true, //改变每页显示数据数量
                    // paging: true,
                    // lengthChange: false,
                    // searching: false,
                    // ordering: true,
                    // info: true,
                    // autoWidth: false,
                    // select:true,
                    // destroy: true,
                    // scrollX: true,
                    // ajax: "sys/mock_data/user.txt",

                    // 动态分页加载数据方式
                    fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
                    fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
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
                    colReorder: true,//列位置的拖动
                    // scrollX: true,
                    autoWidth: true,
                    scrollY:calcDataTableHeight(),
                    destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
                    // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//						 ajax: "../mock_data/user.txt",
                    ajax: {
                        "type": "POST",
                        "url": getContextPath() + 'user/getUsers.do',
                        // "url":  '../mock_data/user.json',
                        "data": function (d) {
//								alert(JSON.stringify($('#searchUserForm').serializeObject()));

                            d.keys = JSON.stringify($('#searchUserForm').serializeObject());
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
                            "data": "userId",
                            "title": "<input type='checkbox' class='checkall' />",
                            "render": function (data, type, full, meta) {

                                return '<input type="checkbox"  class="checkchild"  value="'
                                    + data + '" />';
                            },
                            "bSortable": false,

                        }, {
                            title: "用户名称",
                            data: "username"
                        }, {
                            title: "用户角色",
                            data: "roleNames"
                        }, {
                            title: "客户或员工姓名",
                            data: "userDetailName"
                        }, {
                            title: "客户或员工",
                            data: "userDetailId"
                        }, {
                            title: "类型",
                            data: "type"
                        },
                        {
                            title: "状态",
                            data: "status"
                        },
                        // { title: "最后登录时间",data:"sLastLoginTime" },
                        // { title: "最后登录IP",data:"lastLoginIp" },
                        {
                            title: "最后修改时间",
                            data: "sAmendTime"
                        }, {
                            title: "修改人",
                            data: "amenderName"
                        }],
                    columnDefs: [
                        {
                            orderable: false,
                            targets: 0
                        },
                        {
                            "render": function (data, type, full, meta) {

                                // return data;
                                if (data == null || data == undefined)
                                    return "";
                                else if  (type === 'display')
                                    return type === 'display'
                                    && data.length > 30 ? '<span title="' + data + '">' + data + '</span>' : data;
                                else if  (type === 'copy') {
                                    var api = new $.fn.dataTable.Api(meta.settings);
                                    data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                                }
                                return data;
                            },
                            targets: [1, 3, 4, 5, 6]
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
                        container: '#User_export-excel-selected'
                    },
                    {
                        extend: 'excelHtml5',
                        text:"当前页导出Excel",
                        container: '#User_export-excel-current'

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
                        container: '#User_export-copy'
                    },
                    {
                        extend: 'print',
                        text: '打印全部',
                        container: '#User_export-print-all'
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
                        container: '#User_export-print-selected'
                    },
                    {
                        extend: 'colvis',
                        text: '自定义列表头',
                        container: '#User_export-columnVisibility'
                    }
                ],
                select: {
                    // blurable: true,
                    style: 'multi',//选中多行
                    // info: false
                }
                });


    }


//根据选择的用户，初始化角色datatable
    function init_role_tb(userId) {
        role_tb = $("#role_tb")
            .DataTable(
                {
                    // 动态分页加载数据方式
                    bPaginate: false, // 翻页功能
                    bProcessing: true,
                    bServerSide: true,
                    searching: false,// 禁用搜索
                    lengthChange: false, // 是否启用改变每页显示多少条数据的控件
                    /*
                     * sort : "position",
                     * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
                     */
                    deferRender: true,// 延迟渲染
                    // iDisplayLength : 3, //默认每页显示多少条记录
                    // iDisplayStart : 0,
//						ordering : false,// 全局禁用排序
                    // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
                    // ajax: "../mock_data/user_role.txt",
                    ajax: {
                        "type": "POST",
                        "url": '/stockWebappBase/userRole/getUserRoleVOMap.do',
                        data: {
                            userId: userId

                        }
                    },

                    language: {
                        "url": "js/Chinese.json"
                    },
                    columns: [
                        {
                            "sClass": "text-center",
                            "data": "roleId",
                            "title": "<input type='checkbox' class='checkall' />",
                            "render": function (data, type, full, meta) {

                                return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                            },
                            "Sortable": false

                        }, {
                            title: "用户角色",
                            data: "name"
                        }, {
                            title: "描述",
                            data: "description"
                        }],
                    columnDefs: [{
                        orderable: false,
                        targets: [0]
                    }]
                });


        $('#role_tb tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
                $(this).find("input[type=checkbox]").prop("checked", $(this).hasClass('selected') ? true : false);
                ;
            });

    }


//根据选择的用户，初始化角色datatable
    function init_authentication_tb(userId) {
        authentication_tb = $("#authenticationModal_Table")
            .DataTable(
                {

                    // 动态分页加载数据方式
                    paging: false,

                    bPaginate: false, // 翻页功能
                    bProcessing: false,
                    bServerSide: false,
                    searching: false,// 禁用搜索
                    lengthChange: false, // 是否启用改变每页显示多少条数据的控件
                    /*
                     * sort : "position",
                     * //是否开启列排序，对单独列的设置在每一列的bSortable选项中指定
                     */
                    deferRender: false,// 延迟渲染
                    // iDisplayLength : 3, //默认每页显示多少条记录
                    // iDisplayStart : 0,
//						ordering : false,// 全局禁用排序
                    // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//						 ajax : "../mock_data/authentication.txt",
                    ajax: {
                        "type": "POST",
                        "url": getContextPath() + 'authentication/getAuthenticationMap.do',
                        data: {
                            userId: userId

                        }
                    },

                    language: {
                        "url": "js/Chinese.json"
                    },
                    columns: [
                        {
                            "sClass": "text-center",
                            "data": "authenticationId",
                            "title": "<input type='checkbox' class='checkall' />",
                            "render": function (data, type, full, meta) {

                                return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                            },
                            "Sortable": false

                        }, {
                            title: "账号",
                            data: "account"
                        }, {
                            title: "类型",
                            data: "type"
                        }, {
                            title: "最后登录时间",
                            data: "lastTime"
                        }, {
                            title: "最后登录IP",
                            data: "lastIp"
                        }],
                    columnDefs: [{
                        orderable: false,
                        targets: [0]
                    }]
                });


        $('#authenticationModal_Table tbody').on('click', 'tr', function () {
                $(this).toggleClass('selected');
                $(this).find("input[type=checkbox]").prop("checked", $(this).hasClass('selected') ? true : false);
                ;
            });
    }

    function doSearch() {

        user_tb.ajax.reload();

    }

//重置查询条件
    $("#resetSearchUserForm").click(function () {


        $("#searchUserForm")[0].reset();

        //设置港口默认值为空
        // emptySelect2Value("searchPortGroupPortForm","portGroupId");

    })

// 清空弹框
    function emptyAddForm() {
        $("#userForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };

// click item display detail infomation
    $('#user_tb tbody').on('dblclick', 'tr', function () {
        var data = user_tb.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    // $('#showUserDetail').on('click', function () {
    function showUserDetail() {
        var rows_data = user_tb.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }
    }
    // });
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#user_tb tbody tr',
            callback: function (key, options) {
                //var row_data = user_tb.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addUser();
                        break;
                    case "Delete"://删除该节点
                        $("#user_tb tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteUser();
                        break;
                    case "Edit"://编辑该节点
                        $("#user_tb tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editUser();
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
        editUser: editUser,
        addUser:addUser,
        deleteUser:deleteUser,
        doSearch:doSearch,
        changeUserType:changeUserType,
        setUserRole:setUserRole,
        setUserAuthenticationAccount:setUserAuthenticationAccount,
        showUserDetail:showUserDetail
    };

})();
