//@ sourceURL=employee.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var Employee = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditEmployeeModal
    });


    $(function () {

        //初始化组织架构树
     //   InitOrganizationZtree();

        //Date picker //时间控件
        $(".time").datepicker({
            autoclose: true,
            format: "yyyy-mm-dd"
        });
    });

    $().ready(
        function validateEmployeeForm() {
            $("#editEmployeeForm").validate({
                rules: {
                    organizationStructureId:{
                        maxlength:11
                    },
                    employeeCode:{
                        required:true,
                        maxlength:20
                    },
                    employeeName:{
                        required:true,
                        maxlength:60
                    },
                    sex: {
                        maxlength:2
                    },
                    idCard:{
                        maxlength:20
                    },
                    //marital: {
                    //
                    //},
                    contactAddress:{
                        maxlength:100
                    },
                    nativeAddress:{
                        maxlength:100
                    },
                    homeAddress:{
                        maxlength:100
                    },
                    education: {
                        maxlength:10
                    },
                    employeeCellPhone: {
                        required:true,
                        maxlength:40,
                        digits:true
                    },
                    employeeOfficePhone:{
                        maxlength:40,
                        digits:true
                    },
                    email: {
                        maxlength:100,
                        email:true
                    },
                    zip: {
                        maxlength:10
                    },
                    //hireDate: "雇佣日期",
                    //birthday: "出生年月"
                },
                messages: {

                }
            });
        }
    );
    var employee_table;
    var zTree_OrganizationStructure;//资源树
    var paral = {
        // "employeeId":"员工ID",
        "organizationStructureId": "组织结构ID",
        "employeeCode": "员工代码",
        "employeeName": "姓名",
        //"firstname": "名字",
        //"middlename": "中间名",
        //"lastname": "姓氏",
        //"superiorEmployeeId": "优秀员工ID",
        //"superiorEmployeeCode": "优秀员工代码",
        "sex": "性别",
        "idCard": "身份证",
        "marital": "婚姻状况",
        "contactAddress": "通讯地址",
        "nativeAddress": "籍贯",
        "homeAddress": "家庭住址",
        "education": "学历",
        "employeeCellPhone": "手机号码",
        "employeeOfficePhone": "办公电话",
        "email": "邮箱",
        "zip": "邮编",
        "hireDate": "雇佣日期",
        "birthday": "出生年月"
    };

    Init();
    function Init() {
        employee_table = $("#employeeTable").DataTable({
            fnRowCallback: rightClick,//利用行回调函数，来实现右键事件
            fnDrawCallback: changePage,//重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
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
            iDisplayStart: 0,
            ordering: false,// 全局禁用排序
            scrollX: true,
            autoWidth: true,
            serverSide: true,
            colReorder: true,//列位置的拖动
            destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
            // "dom": '<l<\'#topPlugin\'>f>rt<ip><"clear">',
//			 ajax: "../mock_data/user.txt",
            ajax: {
                "type": "POST",
                // "url": "../mock_data/employee.json",
                "url": getContextPath() + 'employee/listByPage.do',
                "data": function (d) {
                    // alert(JSON.stringify($('#searchEmployeeForm').serializeObject()));
                    d.keys = JSON.stringify($('#searchEmployeeForm').serializeObject());
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
                    "data": "employeeId",
                    "title": "<input type='checkbox' class='checkall' />",
                    "render": function (data, type, full, meta) {
                        return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                    },
                    "bSortable": false,

                },
                // { title: "员工ID", data:"employeeId"},
                // { title: "组织结构ID",data:"organizationStructureId" },
                {title: "组织结构", data: "organizationStructureName"},
                {title: "员工代码", data: "employeeCode"},
                {title: "姓名", data: "employeeName"},
                //{title: "名字", data: "firstname"},
                //{title: "中间名", data: "middlename"},
                //{title: "姓氏", data: "lastname"},
                //{title: "优秀员工ID", data: "superiorEmployeeId"},
                //{title: "优秀员工代码", data: "superiorEmployeeCode"},
                {title: "性别", data: "sex"},
                {title: "身份证", data: "idCard"},
                {title: "婚姻状况", data: "marital"},
                {title: "通讯地址", data: "contactAddress"},
                {title: "籍贯", data: "nativeAddress"},
                {title: "家庭住址", data: "homeAddress"},
                {title: "学历", data: "education"},
                {title: "手机号码", data: "employeeCellPhone"},
                {title:"办公电话",data:"employeeOfficePhone"},
                {title:"邮箱",data:"email"},
                {title: "邮编", data: "zip"},
                {title: "雇佣日期", data: "hireDate"},
                {title: "出生年月", data: "birthday"},
                // {
                //     title: "最新修改人",
                //     "data": "baseModel",
                //
                //     "render": function (data, type, full, meta) {
                //         return (data == null || data == undefined ) ? '' : data.creatorName;
                //     },
                //     // "bSortable": false,
                // },
                {title: "最新修改人", data: "amenderName"},

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
                    targets: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
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
                    container: '#Employee_export-excel-selected'
                },
                {
                    extend: 'excelHtml5',
                    text:"当前页导出Excel",
                    container: '#Employee_export-excel-current'

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
                    container: '#Employee_export-copy'
                },
                {
                    extend: 'print',
                    text: '打印全部',
                    container: '#Employee_export-print-all'
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
                    container: '#Employee_export-print-selected'
                },
                {
                    extend: 'colvis',
                    text: '自定义列表头',
                    container: '#Employee_export-columnVisibility'
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
    $('body').on('click', '.employee .checkall', function () {
        var check = $(this).prop("checked");
        $(".employee .checkchild").prop("checked", check);
        $("#employeeTable tbody tr").each(function () {
            if ( employee_table.row( this, { selected: true } ).any() ) {
                employee_table.row( this ).deselect();
            }
            else {
                employee_table.row( this ).select();
            }
        });

    });

    //add
    function addEmployee() {
        // $("#addEmployee").on('click', function () {
        emptyAddForm();

        InitOrganizationZtree(); // 初始化资源树

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editEmployeeModalBody"), 'insert');

        //员工代码表单可编辑
        $("#editEmployeeForm input[name='employeeCode']").attr('readonly',false);

        $('#editEmployeeModal').modal('show');//现实模态框
        // });
    }
    //edict item
    function editEmployee() {
        // $("#editEmployee").click(function () {
        emptyAddForm();
        InitOrganizationZtree();// 初始化资源树

        var selectedRowData = employee_table.rows('.selected').data();
        if (selectedRowData.length != 1) {
            callAlert("请选择一条记录进行编辑！")
            return;
        }
        var data = selectedRowData[0];

        //"departmentName": "海盟集团 || 贸易子公司 || 贸易代理部"，作处理截取最后一部分；
        var organizationStructureNameAttr = data.organizationStructureName.split("||");
        data.organizationStructureName = organizationStructureNameAttr[organizationStructureNameAttr.length - 1].trim();

        // 循环给表单赋值
        $.each($("#editEmployeeForm input,#editEmployeeForm select "), function (i, input) {

            $(this).val(data[$(this).attr("name")]);

        });
        $("#editEmployeeForm input[name='name']").val(  data['organizationStructureName']);
        //$("#editEmployeeForm input[name='saveType']").val("update");
        setDefaultValue($("#editEmployeeModalBody"), 'update');
        $("#editEmployeeModalBody input[name='amendTime']").val(  data['amendTime']);

        //设置表单不可编辑
        $("#editEmployeeForm input[name='employeeCode']").attr('readonly',true);
        $('#editEmployeeModal').modal('show');//现实模态框

        // });
    }
    //确定增加或者保存编辑；
    function submitEditEmployeeModal() {

        var data = $("#editEmployeeForm").serializeObject();
        var saveType = $("#editEmployeeForm input[name='saveType']").val();
        //data["organizationStructureId"]=$("#editEmployeeForm input[name='gocode']").val();
        //data["employeeDepartment"]=$("#editEmployeeForm input[name='gocode']").val();
        // 测试使用
        // employee_table.row.add(data).draw();//插入一行
        // callSuccess("保存成功");

        $.ajax({
            type: 'POST',
            url: getContextPath() + 'employee/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();

                if (res.code == 0) {
                    $('#editEmployeeModal').modal('hide');//现实模态框
                    employee_table.ajax.reload(function () {
                            callSuccess('操作成功！');
                        }
                    );
                } else
                    callAlert(res.message);
            },
            error: function () {
                hideMask();
                callAlert("增加失败");
            }
        });
    }


    // delete item
    // $("#deleteEmployee").click(function () {
    function deleteEmployee() {
        var info;
        var selectedRowData = employee_table.rows('.selected').data();
        if (selectedRowData.length < 1) {
            callAlert("请选择需要删除的数据！");
            return;
        }
        info = "删除员工时，员工对应的用户信息将一起被删除，确定要删除" + selectedRowData.length + "条数据吗?";

        callAlertModal(info,'Employee_confirmDelete');

        $('.Employee_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $.each(selectedRowData, function () {

                ids.push(this.employeeId);
            });

            $.ajax({
                url: getContextPath() + 'employee/delete.do',
                data: {
                    employeeIds: ids.join(',')
                },
                // dataType: 'json',
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (rsp) {
                    hideMask();

                    if (rsp == 'success') {
                        employee_table.ajax.reload(
                            function () {
                                callSuccess('删除成功！');
                            }
                        );
                    } else
                        callAlert('删除失败!')


                },
                error: function () {
                    hideMask();
                    callAlert("删除失败！")
                }
            });
        });
    }
    // });
    function importExcel() {
        $('#form2Administration input[name=excel]').val("");
        $('#importExcelAdministrationModal1').modal('show');//现实模态框
    }

    function importExcel2(formId, selectedFile, showUploadedFileLabelId) {
        // var modelName = "";
        var titleName = $('#form2Administration input[name=excel]').val();
        if (!titleName) {
            callAlert("请选择文件");
            return;
        }
        // $("#importResultsTable tbody .importResultsTable").remove();
        var form = $('#' + formId)[0];
        var formdata = new FormData(form);
        // formdata.append("type","宁波港");
        $.ajax({
            type: 'POST',
            mimeType: "multipart/form-data",
            url: getContextPath() +"employee/importExcel.do",
            data: formdata,
            contentType: false,
            cache: false,
            processData: false,
            dataType: "json",
            success: function (rsp) {
                if (rsp.code == 0) {
                    callSuccess(rsp.message);
                    // configure.configure_table.ajax.reload();
                    $("#importExcelAdministrationModal").modal('hide');
                    refreshAdministration();
                } 
            },
            error: function () {
                // hideMask();
                callAlert("处理数据失败！")
            }
        });
    }
    function refreshAdministration() {
        // $("#refreshDuties").click(function () {
    	Init();
            // Duties_table.ajax.reload();
        // });
        }
    //refesh table
    // $("#refreshEmployee").click(function () {
    function doSearch() {
        // table.ajax.url( '../mock_data/objects_public_02.txt' ).load();
        employee_table.ajax.reload();
    }

    $('#employeeTable tbody').on('click', 'tr', function () {
        // $(".selected").not(this).removeClass("selected");
        $(this).toggleClass("selected");
        var check = $(this).hasClass("selected")
        $(this).find("input[type=checkbox]").prop("checked", check);//把查找到checkbox并且勾选
        console.log(employee_table.rows('.selected').data().length);
    });


    // click item display detail infomation
    $('#employeeTable tbody').on('dblclick', 'tr', function () {
        var data = employee_table.rows($(this)).data()[0];
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    });
    $('#showEmployeeDetail').on('click', function () {
        var rows_data = employee_table.rows('.selected').data();
        if (rows_data.length < 1) {
            callAlert("请选择一条数据进行查看");
            return;
        }
        for (var i = 0; i < rows_data.length; i++) {
            $("#detail_table").html("");
            DisplayDetail(rows_data[i], paral);
        }

    });


    // 清空弹框
    function emptyAddForm() {
        $("#editEmployeeForm")[0].reset();
        $("label.error").remove();//清除提示语句
    };


    function InitOrganizationZtree() {
        $.ajax({
            // url : "../mock_data/organizational-structure.json",
            url: getContextPath() + 'organization/listAll.do',
            type: 'POST',
            "data": {
                /*keys: JSON.parse($.cookie('loginingEmployee'))['customer']['customerId']*/
            },
            dataType: 'json',
            async: false,
            success: function (data) {
                //初始化form弹框的资源树
            	InitAdministrationzTree("employeeTreeResouce", data)

                InitAdministrationzTree("employeeOrganizationalstructureTreeResouce_search", data);

            },
            error: function () {
                callAlert("获取数据失败!");
                // $.messager.alert('系统提示','申请失败,请重试！','warning');
            }
        });
    };


//重置查询条件
    $("#resetSearchEmployeeForm").click(function () {


        $("#searchEmployeeForm")[0].reset();
        Init();

    })
    //作为fnRowCallback的回调函数增加右键菜单功能
    function rightClick() {
        console.log("fnRowCallback");
        $.contextMenu({
            selector: '#employeeTable tbody tr',
            callback: function (key, options) {
                //var row_data = employee_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addEmployee();
                        break;
                    case "Delete"://删除该节点
                        $("#employeeTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        deleteEmployee();
                        break;
                    case "Edit"://编辑该节点
                        $("#employeeTable tr.selected").removeClass("selected").find("input[type=checkbox]").prop("checked", false);//把行取消选中；
                        options.$trigger.click();//选中该行selected
                        editEmployee();
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
        editEmployee: editEmployee,
        addEmployee:addEmployee,
        deleteEmployee:deleteEmployee,
        doSearch:doSearch,
        importExcel:importExcel,
        importExcel2:importExcel2
    };
})();

