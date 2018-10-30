//@ sourceURL=user.js
var User = (function () {
    var user_tb, role_tb, authentication_tb;
    var paral = {
        hosp_key: "医院名称",
        hosp_kind: "医院类别",
        depart_key: "科室",
        pharmacist_key: "药师工号",
        pharmacist_name: "药师姓名",
        pharmacist_prof: "药师职称",
        pharmacist_level: "医生职级",
        adjust_permit: "调剂权",
        register_flag: "按时注册",
        antibiotics_flag: "抗菌药物处方调剂权 ",
        amender: "最后修改人",
        amender_time: "最后修改时间"
    }
    $.validator.setDefaults({
        submitHandler: submitEditUserModal
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
            if (user_tb.row(this, { selected: true }).any()) {
                user_tb.row(this).deselect();
            }
            else {
                user_tb.row(this).select();
            }
        });

    });


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
        callAlertModal(info, 'User_confirmDelete');

        $('.User_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.physical_id);
            });

            $.ajax({
                url: getContextPath() + 'pharmacistBaseInfo/delete.do',
                data: {
                    physicalIds: ids.join(',')
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

        getList("hosp_key2")
        getList("hosp_kind2")
        getList("depart_key2")
        getList("pharmacist_prof2")
        getList("pharmacist_level2")

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
        var data = selectedRowData[0];

        // 循环给表单赋值
        $.each($("#userForm input,#userForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });

        getList("hos_name2")
        getList("hos_type2")
        getList("office2")
        getList("doctor_prof2")
        getList("doctor_level2")

        //设置默认值amender ,saveType
        setDefaultValue($("#userForm"), 'update');
        $("#userForm input[name='amender_time']").val(data['amender_time']);

        $('#addModalLabel').html("修改");


        $('#editUserModal').modal('show');

    }

    // 新增/修改模态框保存按钮
    function submitEditUserModal() {

        if (
            $.string.isNullOrEmpty($("#userForm select[name='hosp_key']").val()) ||
            $.string.isNullOrEmpty($("#userForm select[name='hosp_kind']").val()) ||
            $.string.isNullOrEmpty($("#userForm select[name='depart_key']").val()) ||
            $.string.isNullOrEmpty($("#userForm input[name='pharmacist_key']").val()) ||
            $.string.isNullOrEmpty($("#userForm input[name='pharmacist_name']").val())) {
            callAlert("不能为空！");
            return;
        }
        var saveType = $("#userForm input[name='saveType']").val();

        $.ajax({
            url: getContextPath() + 'pharmacistBaseInfo/' + saveType + '.do',
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

    function init_user_tb() {


        getList("hosp_key")
        getList("hosp_kind")
        getList("depart_key")
        getList("pharmacist_prof")
        getList("pharmacist_level")

        user_tb = $("#user_tb").DataTable(
            {
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
                scrollY: calcDataTableHeight(),
                destroy: true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
                // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
                //						 ajax: "../mock_data/user.txt",
                ajax: {
                    "type": "POST",
                    "url": getContextPath() + 'doctorBaseInfo/getByPage.do',
                    // "url":  '../mock_data/user.json',
                    "data": function (d) {
                        d.keys = JSON.stringify($('#searchUserForm').serializeObject());
                    }

                },
                dom: '<"top">Brt<"bottom"flip><"clear">',
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
                        "data": "physical_id",
                        "title": "<input type='checkbox' class='checkall' />",
                        "render": function (data, type, full, meta) {

                            return '<input type="checkbox"  class="checkchild"  value="'
                                + data + '" />';
                        },
                        "bSortable": false,

                    },
                    { title: "医院名称", data: "hospKey" },
                    { title: "医院类别", data: "hospKind" },
                    { title: "科室", data: "departKey" },
                    { title: "药师工号", data: "pharmacistKey" },
                    { title: "药师姓名", data: "pharmacistName" },
                    { title: "药师职称", data: "pharmacistProf" },
                    { title: "药师职级", data: "pharmacistLevel" },
                    { title: "调剂权", data: "adjustPermit" },
                    { title: "按时注册", data: "registerFlag" },
                    { title: "抗菌药物处方调剂权", data: "antibioticsFlag" },
                    { title: "最后修改人", data: "amender" },
                    { title: "最后修改时间", data: "amenderTime" }],
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
                            else if (type === 'display')
                                return type === 'display'
                                    && data.length > 30 ? '<span title="' + data + '">' + data + '</span>' : data;
                            else if (type === 'copy') {
                                var api = new $.fn.dataTable.Api(meta.settings);
                                data = $(api.column(meta.col).header()).text() + ": " + data + "  ";
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
                        text: "选中行导出Excel",
                        container: '#User_export-excel-selected'
                    },
                    {
                        extend: 'excelHtml5',
                        text: "当前页导出Excel",
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
                "Edit": { name: "修改", icon: "edit" },
                // "cut": {name: "Cut", icon: "cut"},
                // copy: {name: "Copy", icon: "copy"},
                // "paste": {name: "Paste", icon: "paste"},
                "Delete": { name: "删除", icon: "delete" },
                "Add": { name: "新增", icon: "add" },
                "sep1": "---------",
                "quit": {
                    name: "取消操作", icon: function () {
                        return 'context-menu-icon context-menu-icon-quit';
                    }
                }
            }
        });
    };

    function getList(args, parentId) {
        $.ajax({
            type: "POST",     //提交方式
            url: getContextPath() + "",    //提交的页面，方法名
            data: null,    //参数，如果没有，可以为null
            success: function (data) { //如果执行成功，那么执行此方法
                for (var i = 0; i < data.length; i++) {
                    $(args).append("<option" + " " + "value=" + data[i].cn_name + ">" + data[i].cn_name + "</option>");
                }        //用data.d来获取后台传过来的json语句，或者是单纯的语句
            },
            error: function (err) { //如果执行不成功，那么执行此方法
                alert("err:" + err);
            }
        });
    };

    $("#hos_name").change(function () {
        getList("office")
    });

    $("#hos_name2").change(function () {
        getList("office2")
    });

    return {
        // 将供页面该方法调用
        editUser: editUser,
        addUser: addUser,
        deleteUser: deleteUser,
        doSearch: doSearch,
        showUserDetail: showUserDetail
    };

})();
