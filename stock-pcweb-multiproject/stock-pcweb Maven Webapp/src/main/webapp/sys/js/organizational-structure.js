//@ sourceURL=organization-structure.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var OrganizationalStructure = (function() {

    $.validator.setDefaults({
        submitHandler: submitEditOrganizationalStructureModal
    });
    $().ready(
        function validateCommonsetForm() {
            Commonset_Validator = $("#editOrganizationalStructureForm").validate({
                rules: {
                    type: {
                        maxlength: 30,
                        digits: true
                    },
                    name: {
                        maxlength: 50
                    },
                    businessRegisterNo: {
                        maxlength: 50
                    },
                    portCode: {
                        maxlength: 10
                    },
                    email: {
                        email: true,
                        maxlength: 200
                    },
                    supplierType: {
                        maxlength: 10
                    },
                    fax: {
                        maxlength: 20
                    },
                    isInternal: {
                        maxlength: 30,
                        digits: true
                    },
                    isSupplier: {
                        maxlength:30,
                        digits: true
                    },
                    officeCode: {
                        maxlength: 10
                    },
                    officeName: {
                        maxlength: 50
                    },
                    abbrev: {
                        maxlength: 20
                    },
                    taxRegisterNo: {
                        maxlength: 50
                    },
                    contact: {
                        maxlength: 100
                    },
                    tel: {
                        maxlength: 20
                    },
                    custType: {
                        maxlength: 10
                    },
                    address: {
                        maxlength: 200
                    },
                    isCustomer: {
                        maxlength: 30,
                        digits: true
                    }

                },
                messages: {
                    officeType: {
                        maxlength: "请不要超过限制的30个字符数"
                    },
                    name: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    businessRegisterNo: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    portCode: {
                        maxlength: "请不要超过限制的10个字符数"
                    },
                    email: {
                        email: "请输入正确格式",
                        maxlength: "请不要超过限制的200个字符数"
                    },
                    supplierType: {
                        maxlength: "请不要超过限制的10个字符数"
                    },
                    fax: {
                        maxlength: "请不要超过限制的20个字符数"
                    },
                    isInternal: {
                        maxlength: "请不要超过限制的30个字符数",
                        digits: "请输入数字"
                    },
                    isSupplier: {
                        maxlength: "请不要超过限制的30个字符数",
                        digits: "请输入数字"
                    },
                    officeCode: {
                        maxlength: "请不要超过限制的10个字符数"
                    },
                    officeName: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    abbrev: {
                        maxlength: "请不要超过限制的20个字符数"
                    },
                    taxRegisterNo: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    contact: {
                        maxlength: "请不要超过限制的100个字符数"
                    },
                    tel: {
                        maxlength: "请不要超过限制的20个字符数"
                    },
                    custType: {
                        maxlength: "请不要超过限制的10个字符数"
                    },
                    address: {
                        maxlength: "请不要超过限制的200个字符数"
                    },
                    isCustomer: {
                        maxlength: "请不要超过限制的30个字符数",
                        digits: "请输入数字"
                    }
                }
            });
            //return Commonset_Validator.form();
        }
    );


    var Organizationalstructure_table;
    //var Organizationalstructure_treeObj;
    var treetableSetting = {
        id: "organizationStructureId",
        pId: "superiorId",
        name: "name"
    };
    var paral = {
        "name": "名称",
        // "organizationStructureId":"组织架构ID",
        // "superiorId":"superiorId",
        "codeName": "公司代码",
        "type": "公司类型",
        // "officeName":"公司名称（EN）",
        "officeNativeName": " 公司名称（CN）",
        "abbrev": "公司名称缩写",
        "businessRegisterNo": "业务登记",
        "taxRegisterNo": "税务登记",
        "portCode": "港口代码",
        "contact": "联系方式",
        "email": "邮箱",
        "tel": "电话",
        "supplierType": "SUPPLIER类型",
        "custType": "客户类型",
        "fax": "传真",
        "address": "地址",
        "isInternal": "内部",
        "isCustomer": "客户",
        "isSupplier": "供应商"
    };
    InitTable();
    function InitTable() {
        $("#organizationalstructureTreeTable tbody").html("");
        $.ajax({
            type: 'POST',
            // url: '../mock_data/organizational-structure.json',
            url: getContextPath() + 'organization/listAll.do',
            "data": {
                // alert(JSON.stringify($('#containerType_search_form').serializeObject()));
                keys: JSON.stringify($('#searchOrganizationalStructureForm').serializeObject())
            },
            // async:false,
            dataType: "json",
            async: false,
            success: function (predata) {
                var data = predata;
                Organizationalstructure_table = data;//为子对象展开使用
                //初始化form弹框的资源树
                InitOrganizationStructurezTree("organizationalstructureTreeResouce", data)
                // Organizationalstructure_treeObj = $.fn.zTree.init($("#organizationalstructureTreeResouce"), setting, Organizationalstructure_table);//初始化treeResouce用作增加和编辑的选择框
                // Organizationalstructure_treeObj.expandAll(true);//节点全部展开
                var heads = [];
                var name = treetableSetting.name;
                var id = treetableSetting.id;
                var pId = treetableSetting.pId;
                heads.push(paral[name]);
                for (var key in paral) {
                    if (key != id && key != pId && key != name) {
                        heads.push(paral[key]);
                    }
                }
                // console.log(heads);
                for (var i = 0; i < data.length; i++) {
                    data[i].td = [];
                    for (var key in paral) {
                        if (key != id && key != pId && key != name) {
                            var td_obj = {};
                            td_obj[key] = data[i][key];
                            // data[i].td.push('<label name='+key+'>'+data[i][key]+'</label>');
                            data[i].td.push(td_obj);
                        }

                    }
                }
                console.log("init");
                $.TreeTable("organizationalstructureTreeTable", heads, data, treetableSetting);
            },
            error: function () {
                alert("error");
            }
        })
    }


    // $("#addOrganizationalStructure").click(function () {
function addOrganizationalStructure() {

    emptyAddForm();

    //设置默认值amender ,amenderName,amendTime,saveType
    setDefaultValue($("#editOrganizationalStructureModalBody"), 'insert');

    $('#editOrganizationalStructureModal').modal('show');//现实模态框

    // });
}
    //点击保存

    function submitEditOrganizationalStructureModal() {
        //$("#editOrganizationalStructureModalSubmit").click(function () {
        var data = $('#editOrganizationalStructureForm').serializeObject();
        var saveType = $("#editOrganizationalStructureForm input[name='saveType']").val();
        console.log(JSON.stringify($('#editOrganizationalStructureForm').serializeObject()));
        $.ajax({
            type: 'POST',
            url: getContextPath() + 'organization/' + saveType + '.do',
            data: data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();
                if (res.code == 0) {
                    InitTable();
                    callSuccess(res.message);
                    // 编辑或者增加保存成功后展开该节点的父节点
                    if (data.superiorId != "") {
                        $("#organizationalstructureTreeTable").treetable("reveal", data.superiorId);
                    }
                }
                else
                    callAlert(res.message);
            },
            error: function () {
                hideMask();
                callAlert("修改失败！");
            }
        });
        $('#editOrganizationalStructureModal').modal('hide');//现实模态
        //})
    }

    // $("#OrganizationalStructureSearch").click( function() {
    //         // var data = search_table.$('input, select');
    //         // console.log(data);
    //         alert("待完善，请先使用表格上面的Search功能！")
    //     }
    // )

    // delete item
    // $("#deleteOrganizationalStructure").click(function () {
function deleteOrganizationalStructure() {
    var info;
    if ($("#organizationalstructureTreeTable tbody .selected").length < 1) {
        info = "请选择要删除的数据！";
        callAlert(info);
        return;
    }
    info = "确定要删除" + $("#organizationalstructureTreeTable tbody .selected").length + "数据吗?";
    callAlertModal(info,'OrganizationalStructure_confirmDelete');

    //是否包含父节点
    var isContainParent = false;

    $('.OrganizationalStructure_confirmDelete').unbind('click').click(function () {
        var ids = [];
        var parentId;
        $("#organizationalstructureTreeTable tr").each(function () {
            if ($(this).hasClass('selected')) {

                if ('parent' == $(this).attr('title')) {
                    isContainParent = true;
                }

                for (var i = 0; i < $(this)[0].attributes.length; i++) {
                    if ($(this)[0].attributes[i].name == "data-tt-id") {
                        ids.push($(this)[0].attributes[i].value);
                    }
                    if ($(this)[0].attributes[i].name == "data-tt-parent-id") {
                        parentId = $(this)[0].attributes[i].value;
                    }
                }
            }

        });
        if (isContainParent) {
            callAlert("父节点类型不能被删除，请先删除其子节点!");
            return;
        } else {
            $.ajax({
                url: getContextPath() + '/organization/delete.do',
                data: {
                    organizationStructureIds: ids.join(',')
                },
                cache: false,
                // dataType: "json",
                async: "false",
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (res) {
                    hideMask();
                    if (res == 'success') {
                        InitTable();
                        callSuccess("删除成功！");
                        if (parentId != "undefined" || parentId != undefined) {
                            $("#organizationalstructureTreeTable").treetable("reveal", parentId);
                            // // 编辑或者增加保存成功后展开该节点的父节点
                        }
                    }
                    else {
                        callAlert("删除失败" + res);
                    }
                },
                error: function () {
                    hideMask();
                }
            });

        }
    });
}
    // })


    //edict item
    // $("#editOrganizationalStructure").click(function () {
    function editOrganizationalStructure() {
        var info;
        emptyAddForm();
        if ($("#organizationalstructureTreeTable tbody .selected").length != 1) {
            info = "请选择一条对象进行编辑";
            callAlert(info);
            return;
        }
        $("#organizationalstructureTreeTable tr").each(function () {
            var obj = {};
            if ($(this).hasClass('selected')) {
                var tr = $(this)[0].childNodes;
                for (var i = 0; i < tr.length; i++) {
                    obj[tr[i].title] = tr[i].textContent;
                }
                var parent = {};

                //加入组织架构Id
                obj["organizationStructureId"] = $(this).attr("data-tt-id");

                //通过孩子节点id获取父节点信息
                for (var i = 0; i < $(this)[0].attributes.length; i++) {
                    if ($(this)[0].attributes[i].name == "data-tt-parent-id") {
                        parent.id = $(this)[0].attributes[i].value;
                        // var node = treeObj.getNodeByTId(parent.id);
                        // parent.name = node[treetableSetting.name];
                        var parentId = treetableSetting.pId;
                        obj[parentId] = parent.id;
                    }
                    if ($(this)[0].attributes[i].name == "data-tt-parent-name") {
                        parent.name = $(this)[0].attributes[i].value;
                        obj["parentName"] = parent.name;
                    }
                }
                for (var key in obj) {
                    $("#editOrganizationalStructureModal input[name='" + key + "']").val(obj[key]);
                }

                // $("#editOrganizationalStructureForm input[name='amendTime']").val($.date.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                $("#editOrganizationalStructureForm input[name='amenderName']").val("admin");
                $("#editOrganizationalStructureForm input[name='amendTime']").val(  data['amendTime']);
                //$("#editOrganizationalStructureForm input[name='saveType']").val("update");
                setDefaultValue($("#editOrganizationalStructureModalBody"), 'update');

                $('#editOrganizationalStructureModal').modal('show');//现实模态框

            }
        });
    }
    // });

    //refesh table
    function refreshOrganizationalStructure() {
    // $("#refreshOrganizationalStructure").click(function () {
        InitTable();
        // Organizationalstructure_table.ajax.reload();
    // });
    }

    function emptyAddForm() {
        $("#editOrganizationalStructureForm")[0].reset();
        $("label.error").remove();//清除提示语句
    }


    function searchCommonSet() {

        InitTable();
    }

// click item display detail infomation
    $('#organizationalstructureTreeTable tbody').on('dblclick', 'tr', function () {
        Showdetail($(this));
    });

    $('#showOrganizationalStructureDetail').on('click', function () {
        Showdetail($("tr.selected"));
    });
    function Showdetail(self) {
        var chNodes = self[0].childNodes;
        var data = {};
        for (var i = 0; i < chNodes.length; i++) {
            var key = chNodes[i].title;
            var value = chNodes[i].innerText;
            data[key] = value;
        }
        $("#detail_table").html("");
        DisplayDetail(data, paral);
    };
    $(function(){
        $.contextMenu({
            selector: '#organizationalstructureTreeTable tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addOrganizationalStructure();
                        break;
                    case "Delete"://删除该节点
                        options.$trigger.click();//选中该行selected
                        deleteOrganizationalStructure();
                        break;
                    case "Edit"://编辑该节点
                        options.$trigger.click();//选中该行selected
                        editOrganizationalStructure();
                        break;
                    default:
                        options.$trigger.removeClass("selected").find("input[type=checkbox]").prop("checked", false);//取消选择selected
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
    });
    return{
        //  refreshOrganizationalStructure() { editOrganizationalStructure() { addOrganizationalStructure() { deleteOrganizationalStructure
        refreshOrganizationalStructure:refreshOrganizationalStructure,
        editOrganizationalStructure:editOrganizationalStructure,
        addOrganizationalStructure:addOrganizationalStructure,
        deleteOrganizationalStructure:deleteOrganizationalStructure

    }
})();