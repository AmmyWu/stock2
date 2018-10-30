//@ sourceURL=duties.js
    // $('#lxy_basicdata_tb').DataTable().empty();
    //标题行
var basicdataset = (function() {

    $.validator.setDefaults({
        submitHandler: submitEditbasicdatasetModal
    });
    
    $(document).ready(function() {
        resize();
    });
    
    $().ready(
        function validateCommonsetForm() {
            Commonset_Validator = $("#editbasicdatasetForm").validate({
                rules: {
                	 type: {
                         maxlength: 30,
                         digits: true
                     },
                    name: {
                        maxlength: 300
                    },
                    creator: {
                        maxlength: 300
                    },
                    strAmendTime: {
                        maxlength: 300
                    },
                    amender: {
                        maxlength: 300
                    }
                },
                messages: {
                	  officeType: {
                          maxlength: "请不要超过限制的30个字符数"
                      },
                    name: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    creator: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    strAmendTime: {
                        maxlength: "请不要超过限制的50个字符数"
                    },
                    amender: {
                        maxlength: "请不要超过限制的50个字符数"
                    }
                }
            });
            //return Commonset_Validator.form();
        }
    );

    var basicdataset_table;
    
    //var Duties_treeObj;
    var treetableSetting = {
        id: "basicDataSetId",
        pId: "superiorId",
        name: "cnName"
    };
    var paral = {
    		 "cnName": "名称",
             //"governmentId":"地区ID",
             "basicDataSetId": "组织架构id", // basicDataSetId + creator
             //"superiorId":"superiorId",
             "creator":"创建Id",
           /*  "creatorName":"创始人",*/
             "strAmendTime":"最后修改时间",
            /* "amenderName":"修改人"*/
    };
    InitTable();
    function InitTable() {
        $("#resourceTreetable tbody").html("");
        $.ajax({
            type: 'POST',
            // url: '../mock_data/duties.json',
            url: getContextPath() + 'basicdataset/listBDSTree.do',
            "data": {
                // alert(JSON.stringify($('#containerType_search_form').serializeObject()));
                /*keys: JSON.parse($.cookie('loginingEmployee'))['customer']['customerId']//JSON.stringify($('#searchbasicdatasetForm').serializeObject())
*/            },
            // async:false,
            dataType: "json",
            async: false,
            success: function (predata) {
                var data = predata;
                if(data.length!=0){
                	$("#basicdataset_import-excel").attr("disabled","true");
                }
                basicdataset_table = data;//为子对象展开使用
                //初始化form弹框的资源树
                InitbasicdatasetzTree("basicdatasetTreeResouce", data);
                // Duties_treeObj = $.fn.zTree.init($("#dutiesTreeResouce"), setting, Duties_table);//初始化treeResouce用作增加和编辑的选择框
                // Duties_treeObj.expandAll(true);//节点全部展开
                var heads = [];
                var name = treetableSetting.name;
                var id = treetableSetting.id;
                var pId = treetableSetting.pId;
                heads.push(paral[name]);
                for (var key in paral) {
                    if (key != pId && key != name) {
                        heads.push(paral[key]);
                    }
                }
                // console.log(heads);
                for (var i = 0; i < data.length; i++) {
                    data[i].td = [];
                    for (var key in paral) {
                        if (key != pId && key != name) {
                            var td_obj = {};
                            td_obj[key] = data[i][key];
                            // data[i].td.push('<label name='+key+'>'+data[i][key]+'</label>');
                            data[i].td.push(td_obj);
                        }

                    }
                }
                $.TreeTable("resourceTreetable", heads, data, treetableSetting);
            },
            error: function () {
                alert("error");
            }
        })
    }


    // $("#addDuties").click(function () {
    function addbasicdataset() {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editbasicdatasetModalBody"), 'insert');

        $('#editbasicdatasetModal').modal('show');//现实模态框

        // });
    }

    function submitEditbasicdatasetModal() {
        //$("#editDutiesModalSubmit").click(function () {
       
        var saveType = $("#editbasicdatasetForm input[name='saveType']").val();

        // insert 才增加，update 用原来的basicDataSetId
        var data = $('#editbasicdatasetForm').serializeObject();
        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basicdataset/' + saveType + '.do',
            data:data,
            cache: false,
            dataType: "json",
            beforeSend: function () {
                showMask();//显示遮罩层
            },
            success: function (res) {
                hideMask();
                if (res.success == true) {
                    InitTable();
                    callSuccess(res.message);
                    // 编辑或者增加保存成功后展开该节点的父节点
                    if (data.superiorId != "") {
                        $("#resourceTreetable").treetable("reveal", data.superiorId);
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
        $('#editbasicdatasetModal').modal('hide');//现实模态
    }


    /*
     * 根据basicDataSetId和creator来修改删除地区
     */
    function deletebasicdataset() {
        var info;
        if ($("#resourceTreetable tbody .selected").length < 1) {
            info = "请选择要删除的数据！";
            callAlert(info);
            return;
        }
        info = "确定要删除" + $("#resourceTreetable tbody .selected").length + "数据吗?";
        callAlertModal(info,'basicdataset_confirmDelete');

        //是否包含父节点
        var isContainParent = false;

        $('.basicdataset_confirmDelete').unbind('click').click(function () {
            var reqData;
            var parentId;
            $("#resourceTreetable tr").each(function () {
                if ($(this).hasClass('selected')) {

                    if ('parent' == $(this).attr('title')) {
                        isContainParent = true;
                    }

                    reqData = {
                        id: $(this).find("td[title='basicDataSetId']")[0].innerHTML,
                        creator: $(this).find("td[title='creator']")[0].innerHTML
                    };
                    parentId = $(this).attr('data-tt-parent-id');
                }
            });

            if (isContainParent) {
                callAlert("父节点类型不能被删除，请先删除其子节点!");
                return;
            }

            if (reqData == null) {
                callAlert("请选择一个数据！");
                return;
            }

            $.ajax({
                url: getContextPath() + 'basicdataset/delete.do',
                data: reqData,
                cache: false,
                dataType: "json",
                async: "false",
                beforeSend: function () {
                    showMask();//显示遮罩层
                },
                success: function (res) {
                    hideMask();
                    if (res.success == true) {
                        InitTable();
                        callSuccess("删除成功！");
                      
                    } else {
                        callAlert("删除失败, " + res.message);
                    }
                },
                error: function () {
                    hideMask();
                }
            });

        });
    }


    function editbasicdataset() {
        var info;
        emptyAddForm();
        if ($("#resourceTreetable tbody .selected").length != 1) {
            info = "请选择一条对象进行编辑";
            callAlert(info);
            return;
        }
        $("#resourceTreetable tr").each(function () {
            var obj = {};
            if ($(this).hasClass('selected')) {
                var tr = $(this)[0].childNodes;
                for (var i = 0; i < tr.length; i++) {
                    obj[tr[i].title] = tr[i].textContent;
                }
                var parent = {};

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
                    $("#editbasicdatasetModal input[name='" + key + "']").val(obj[key]);
                }


                $("#editbasicdatasetForm input[name='amendTime']").val(  obj['amendTime']);

                setDefaultValue($("#editbasicdatasetModalBody"), 'update');

                $('#editbasicdatasetModal').modal('show');//现实模态框

            }
        });
    }

   
    //refesh table
    function refreshbasicdataset() {
    // $("#refreshDuties").click(function () {
        InitTable();
        // Duties_table.ajax.reload();
    // });
    }

    function emptyAddForm() {
        $("#editbasicdatasetForm")[0].reset();
        $("label.error").remove();//清除提示语句
    }


    function searchCommonSet() {

        InitTable();
    }

// click item display detail infomation
    $('#resourceTreetable tbody').on('dblclick', 'tr', function () {
        Showdetail($(this));
    });

    $('#showbasicdatasetDetail').on('click', function () {
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
            selector: '#resourceTreetable tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addbasicdataset();
                        break;
                    case "Delete"://删除该节点
                        options.$trigger.click();//选中该行selected
                        deletebasicdataset();
                        break;
                    case "Edit"://编辑该节点
                        options.$trigger.click();//选中该行selected
                        editbasicdataset();
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
        //  refreshDuties() { editDuties() { addDuties() { deleteDuties
        refreshbasicdataset:refreshbasicdataset,
        editbasicdataset:editbasicdataset,
        addbasicdataset:addbasicdataset,
        deletebasicdataset:deletebasicdataset,
    }
})();