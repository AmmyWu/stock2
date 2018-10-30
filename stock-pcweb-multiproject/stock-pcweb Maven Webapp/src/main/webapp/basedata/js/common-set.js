
//@ sourceURL=common-set.js
//var Commonset_Validator;
var commonSet = (function() {
    $.validator.setDefaults({
        submitHandler: submitEditCommonsetModal
    });
    var commonSet_table;
    var treetableSetting = {
        id: "commonSetId",
        pId: "superiorId",
        name: "cnName"
    };
    var paral = {
        "cnName": "中文名称",
        "enName": "英文名称",
        "superiorId": "父节点ID",
        // "description": "英文描述",
        // "descriptionChinese": "中文描述",
        "code": "代码",
        "code3": "代码3",
        // "accessGroup": "访问组",
        // "name": "名称",
        // "commonSetId": "ID",
        "amender": "最后修改人人员",
        "amendTime": "最后修改时间"
    };
    $(function () {
        //Initialize Select2 Elements，初始化银行下拉框架
        $(".select2").select2();
        InitTable();
        initSerlect2_CommonSet();
        //解决select2 在弹出框中不能搜索的问题
        $.fn.modal.Constructor.prototype.enforceFocus = function () {
        };

    });

//页面加载，新增，修改，删除时均需要执行
    function initSerlect2_CommonSet() {
        //初始化查询form的港口下拉列表
        // initSelect2FromDB("searchCommonSetForm","superiorId","basedataCommonSet/listByKeys.do","{superiorId:0}","commonSetId","cnName");

        //初始化查询form的港口下拉列表 basedataCommonSet_0  0表示根节点类型
        initSelect2FromRedis("searchCommonSetForm", "superiorId", "redisController/listIdNameByName.do?name=basedataCommonSet_0", "{}", "commonSetId", "cnName");

        //初始化查询form的港口下拉列表
        initSelect2FromRedis("editCommonsetModalBody", "superiorId", "redisController/listIdNameByName.do?name=basedataCommonSet_0", "{}", "commonSetId", "cnName");
        // initSelect2FromDB("editCommonsetModalBody","superiorId","basedataCommonSet/listByKeys.do","{superiorId:0}","commonSetId","cnName");

    }

    $().ready(
        function validateCommonsetForm() {
            Commonset_Validator = $("#editCommonsetForm").validate({
                rules: {
                    code: {
                        maxlength: 20
                    },
                    enName: {
                        maxlength: 100
                    },
                    descriptionChinese: {
                        maxlength: 200
                    },
                    cnName: {
                        maxlength: 100
                    },
                    description: {
                        maxlength: 200
                    },
                    code3: {
                        maxlength: 3
                    }
                },
                messages: {
                    code: {
                        maxlength: "请不要超过限制的20个字符数"
                    },
                    enName: {
                        maxlength: "请不要超过限制的100个字符数"
                    },
                    descriptionChinese: {
                        maxlength: "请不要超过限制的200个字符数"
                    },
                    cnName: {
                        maxlength: "请不要超过限制的100个字符数"
                    },
                    description: {
                        maxlength: "请不要超过限制的200个字符数"
                    },
                    code3: {
                        maxlength: "请不要超过限制的3个字符数"
                    }
                }
            });
            //return Commonset_Validator.form();
        }
    );


    function InitTable() {
        $("#commonsetTreetable tbody").html("");
        $.ajax({
            type: 'POST',
            // url: '../mock_data/common-set.json',
            url: getContextPath() + 'basedataCommonSet/listTreeByKeys.do',
            //  jsonp:"jsoncallback",
            // jsonCallback:"callback",
            "data": {
                // alert(JSON.stringify($('#containerType_search_form').serializeObject()));
                keys: JSON.stringify($('#searchCommonSetForm').serializeObject())
            },
            async: false,
            dataType: "json",
            success: function (data) {
                // data = aaData;
                commonSet_table = data;//为子对象展开使用
                $.fn.zTree.init($("#commonsetTreeResouce"), setting, commonSet_table);//初始化treeResouce用作增加和编辑的选择框
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
                console.log(heads);
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
                console.log(data);
                $.TreeTable("commonsetTreetable", heads, data, treetableSetting);
            },
            error: function () {
                hideMask();
                alert("error");
            }
        })
    }

    // ztree的参数以及相关方法
    var setting = {
        view: {
            dblClickExpand: false
        },
        isSimpleData: true,
        data: {
            simpleData: {
                enable: true,//简单数据的模式下可以指定idkey和pIkey
                idKey: "commonSetId",
                pIdKey: "superiorId"
            },
            key: {
                name: "cnName",
                url: "urlX"

            }
        },
        callback: {
            // beforeClick: beforeClick,
            onClick: onClick//回调函数
        }
    };

    function onClick(e, treeId, treeNode) {
        var idKey = setting.data.simpleData.idKey;
        var name = setting.data.key.name;
        var zTree = $.fn.zTree.getZTreeObj("commonsetTreeResouce"),
            nodes = zTree.getSelectedNodes(),
            v = "";
        // parentNode = nodes;
        nodes.sort(function compare(a, b) {
            return a.id - b.id;
        });
        var uuid = nodes[0][idKey];
        for (var i = 0, l = nodes.length; i < l; i++) {
            v += nodes[i][name] + ",";
        }
        if (v.length > 0) v = v.substring(0, v.length - 1);
        // var cityObj = $("#commonsetResourceSel");
        // cityObj.attr("value", v);
        var parentId = treetableSetting.pId;
        $("#editCommonsetForm input[name='parentName']").val(v);
        $("#editCommonsetForm input[name='" + parentId + "']").val(uuid);

    }

    function showMenu() {
        $("#commonsetRtreemenuContent").slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#commonsetRtreemenuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "commonsetRtreemenuContent" || $(event.target).parents("#commonsetRtreemenuContent").length > 0)) {
            hideMenu();
        }
    }


    // $("#addCommonset").click(function () {
function addCommonset() {
        emptyAddForm();

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editCommonsetModalBody"), 'insert');

        //选择行
        var selectedTrs = $("#commonsetTreetable tbody .selected");
        if (selectedTrs.length > 1) {
            info = "请选择一条对象进行编辑";
            callAlert(info);
            return;
        }
        if (selectedTrs.length == 0) {
            //设置select2默认值
            setSelect2Value("editCommonsetModal", "superiorId", "");
        } else { //根据当前选定的行设置父节点默认值

            var obj = {};
            //当前选中的一行
            var selectedTr = selectedTrs[0];

            if ($(selectedTr).attr("title") == 'parent') { //选中的为父节点

                //设置select2默认值
                setSelect2Value("editCommonsetModal", "superiorId", $(selectedTr).attr("data-tt-id"));

            } else {
                var parent = {};

                //通过孩子节点id获取父节点信息
                for (var i = 0; i < $(selectedTr)[0].attributes.length; i++) {
                    if ($(selectedTr)[0].attributes[i].name == "data-tt-parent-id") {
                        parent.id = $(selectedTr)[0].attributes[i].value;
                        // var node = treeObj.getNodeByTId(parent.id);
                        // parent.name = node[treetableSetting.name];
                        var parentId = treetableSetting.pId;
                        obj[parentId] = parent.id;
                    }
                    if ($(selectedTr)[0].attributes[i].name == "data-tt-parent-name") {
                        parent.name = $(selectedTr)[0].attributes[i].value;
                        obj["parentName"] = parent.name;
                    }
                }
                //设置select2默认值
                setSelect2Value("editCommonsetModal", "superiorId", obj["superiorId"]);
            }

        }


        $('#editCommonsetModal').modal('show');//现实模态框

    }
    //点击保存
    function submitEditCommonsetModal() {

        var data = $('#editCommonsetForm').serializeObject();
        var saveType = $("#editCommonsetForm input[name='saveType']").val();

        // alert(JSON.stringify($('#editCommonsetForm').serializeObject()));
        $.ajax({
            type: 'POST',
            url: getContextPath() + 'basedataCommonSet/' + saveType + '.do',
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
                    initSerlect2_CommonSet();
                    callSuccess(res.message);
                    if (data.superiorId != "undefined" || data.superiorId != undefined) {
                        $("#commonsetTreetable").treetable("reveal", data.superiorId);
                    }

                }
                else
                    callAlert(res.message);
            },
            error:function () {
                hideMask();
            }
        });
        $('#editCommonsetModal').modal('hide');//隐藏模态框
        //})
    }

    // delete item
    // $("#deleteCommonset").click(function () {
    function deleteCommonset() {
        var info;
        //选择行
        var selectedTrs = $("#commonsetTreetable tbody .selected");
        if (selectedTrs.length < 1) {
            info = "请选择要删除的数据";
            callAlert(info);
            return;
        }

        info = "确定要删除" + selectedTrs.length + "数据吗?";
        callAlertModal(info,'Commonset_confirmDelete');

        //是否包含父节点
        var isContainParent = false;
        var parendId = 0;

        $('.Commonset_confirmDelete').unbind('click').click(function () {
            var ids = [];
            //当前选中的一行
            var selectedTr = selectedTrs[0];

            if ($(selectedTr).hasClass('selected')) {

                if ('parent' == $(selectedTr).attr('title')) {
                    isContainParent = true;
                }
                for (var i = 0; i < $(selectedTr)[0].attributes.length; i++) {
                    if ($(selectedTr)[0].attributes[i].name == "data-tt-id") {
                        ids.push($(selectedTr)[0].attributes[i].value);
                    }
                    if ($(selectedTr)[0].attributes[i].name == "data-tt-parent-id") {
                        parendId = $(selectedTr)[0].attributes[i].value;
                    }
                }

                if (isContainParent) {
                    callAlert("父节点类型不能被删除，请先删除其子节点!");
                    return;
                }

                $.ajax({
                    url: getContextPath() + '/basedataCommonSet/delete.do',
                    data: {
                        superiorId: parendId,
                        commonSetIds: ids.join(',')
                    },
                    cache: false,
                    dataType: "json",
                    beforeSend: function () {
                        showMask();//显示遮罩层
                    },
                    success: function (res) {
                        hideMask();
                        if (res.code == 0) {
                            InitTable();
                            initSerlect2_CommonSet();
                            callSuccess("删除成功！");
                            if (parendId != 0 || parendId != "0") {
                                $("#commonsetTreetable").treetable("reveal", parendId);
                            }

                        }
                        else {
                            callAlert("删除失败")
                        }
                    },
                    error:function () {
                        hideMask();
                    }
                });

            }

        });

    }
    //

    //edict item
    // $("#editCommonset").click(function () {
    function editCommonset() {
        var info;
        emptyAddForm();

        //选择行
        var selectedTrs = $("#commonsetTreetable tbody .selected");
        if (selectedTrs.length != 1) {
            info = "请选择一条对象进行编辑";
            callAlert(info);
            return;
        }
        // $("#commonsetTreetable tr").each(function () {
        var obj = {};

        //当前选中的一行
        var selectedTr = selectedTrs[0];

        // if ($(selectedTr).hasClass('selected')) {
        obj['commonSetId'] = $(selectedTr).attr("data-tt-id");//获取commonSetId

        var tr = $(selectedTr)[0].childNodes;
        for (var i = 0; i < tr.length; i++) {
            obj[tr[i].title] = tr[i].textContent;
        }

        var parent = {};

        //通过孩子节点id获取父节点信息
        for (var i = 0; i < $(selectedTr)[0].attributes.length; i++) {
            if ($(selectedTr)[0].attributes[i].name == "data-tt-parent-id") {
                parent.id = $(selectedTr)[0].attributes[i].value;
                // var node = treeObj.getNodeByTId(parent.id);
                // parent.name = node[treetableSetting.name];
                var parentId = treetableSetting.pId;
                obj[parentId] = parent.id;
            }
            if ($(selectedTr)[0].attributes[i].name == "data-tt-parent-name") {
                parent.name = $(selectedTr)[0].attributes[i].value;
                obj["parentName"] = parent.name;
            }
        }
        for (var key in obj) {
            $("#editCommonsetModal input[name='" + key + "']").val(obj[key]);
        }
        //设置select2默认值
        setSelect2Value("editCommonsetModal", "superiorId", obj["superiorId"]);

        //设置默认值amender ,amenderName,amendTime,saveType
        setDefaultValue($("#editCommonsetModalBody"), 'update');
        $("#editCommonsetForm input[name='amendTime']").val(  obj['amendTime']);
        $('#editCommonsetModal').modal('show');//现实模态框

        // }
    }

    // });

    //refesh table
    $("#refreshCommonset").click(function () {
        InitTable();
    });
// 清空弹框
    function emptyAddForm() {
        $("#editCommonsetForm")[0].reset();


        $("label.error").remove();//清除提示语句
    };

//重置查询条件
    $("#resetSearchCommonSetForm").click(function () {


        $("#searchCommonSetForm")[0].reset();

        //设置父节点值为空
        emptySelect2Value("searchCommonSetForm", "superiorId");


    });
    function searchCommonSet() {
        var formVal = $('#searchCommonSetForm').serializeObject();
        if ($.string.isNullOrEmpty(formVal["superiorId"])) {

            callAlert("请选择查询的父节点!");
            return;
        }

        InitTable();
    }

// click item display detail infomation
    $('#commonsetTreetable tbody').on('dblclick', 'tr', function () {
        Showdetail($(this));
    });
    $('#showCommonsetDetail').on('click', function () {
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
            selector: '#commonsetTreetable tbody tr',
            callback: function (key, options) {
                //var row_data = containerType_table.rows(options.$trigger[0]).data()[0];
                switch (key) {
                    case "Add"://增加一条数据
                        addCommonset();
                        break;
                    case "Delete"://删除该节点
                        options.$trigger.click();//选中该行selected
                        deleteCommonset();
                        break;
                    case "Edit"://编辑该节点
                        options.$trigger.click();//选中该行selected
                        editCommonset();
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
    });
    return{
        searchCommonSet:searchCommonSet,
        editCommonset:editCommonset,
        deleteCommonset:deleteCommonset,
        addCommonset:addCommonset
    }
})();
 // $('#lxy_basicdata_tb').DataTable().empty();
 // //标题行
 // var table;
 // var paral={
 //     "code":"基础数据集代码",
 //     "cn_name":"基础数据集名称（CN）",
 //     "en_name":"基础数据集名称（EN）",
 //     "DESC":"基础资料（EN）",
 //     "DESC_CN":"基础资料（CN）",
 //     "DESCRIPTION":"备注",
 // };
 // Init();
 // InitAddTable();
 // function Init() {
 //     table =  $("#common-set_tb").DataTable( {
 //         // data: dataSet,
 //         bPaginate: true, //翻页功能
 //         bLengthChange: true, //改变每页显示数据数量
 //         paging: true,
 //         lengthChange: false,
 //         searching: true,
 //         ordering: true,
 //         info: true,
 //         autoWidth: true,
 //         select:true,
 //         destroy: true,
 //         scrollX: true,
 //         ajax: "basedata/mock_data/objects.txt",
 //         language: {
 //             "url": "js/Chinese.json"
 //         },
 //         columns: [
 //             {
 //                 "sClass": "text-center",
 //                 "data": "name",
 //                 "title":"<input type='checkbox' class='checkall'/>",
 //                 "render": function (data, type, full, meta) {
 //                     return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
 //                 },
 //                 "bSortable": false,
 //
 //             },
 //             { title: "基础数据集代码", data:"code"},
 //             { title: "基础数据集名称（CN）",data:"cn_name" },
 //             { title: "基础数据集名称（EN）",data:"en_name" },
 //             { title: "基础资料（EN）",data:"DESC" },
 //             { title: "基础资料（CN）",data:"DESC_CN"},
 //             { title: "备注",data:"DESCRIPTION" }
 //         ],
 //         columnDefs: [
 //             {
 //                 orderable: false,
 //                 targets: 0 },
 //             {
 //                 "render": function ( data, type, full, meta ) {
 //                     return type === 'display' && data.length > 30 ?
 //                         '<span title="'+data+'">'+data+'</span>' :
 //                         data;
 //                 },
 //                 targets: [1,2,3,4,5,6]
 //             }
 //         ],
 //
 //     } );
 // }
 //
 //
    //
    // // click item display detail infomation
    //   $('#common-set_tb tbody').on( 'click', 'tr td:not(:first-child)', function () {
    //      $("#detail_table").html("");
    //       var key_arr = [];
    //       for (var key in paral){
    //           key_arr.push(paral[key]);
    //       }
    //       // var key_arr = ["基础数据集代码","基础数据集名称（CN）","基础数据集名称（EN）","基础资料（EN）","基础资料（CN）","备注"];
    //       DisplayDetail($(this),key_arr);
    //
    // } );
    //
//      $("#common-setTreetable").treetable({
//          expandable: true,// 展示
//          initialState: "collapsed",//默认打开所有节点
//          stringCollapse: '关闭',
//          stringExpand: '展开',
//          onNodeExpand: function () {
//              var hasChild = false;  //判断如果没有孩子节点则改为叶子节点
//              var node = this; 		//判断当前节点是否已经拥有子节点
//              var childSize = $("#common-setTreetable").find("[data-tt-parent-id='" + node.id + "']").length;
//              if (childSize > 0) {
//                  return;
//              }
//              for(var i=0;i<commonSet_table.length;i++) {
//                  if (commonSet_table[i].parentNodeId == node.id) {
//                      hasChild =true;
//                      var parentNode = $("#common-setTreetable").treetable("node", commonSet_table[i].parentNodeId);
//                      var nodeToAdd = $("#common-setTreetable").treetable("node",commonSet_table[i].id);
//                      // check if node already exists. If not add row to parent node
//                      if(!nodeToAdd){
//                          var row ='<tr data-tt-id="' +
//                              commonSet_table[i].id +
//                              '" data-tt-parent-id="' +
//                              commonSet_table[i].parentNodeId + '" ';
//                          row += '>';
//                          row += "<td><span><input type='checkbox'/></span><span class='file'>" + commonSet_table[i].text + "</span></td>";
//                          row += "<td>" + commonSet_table[i].basicDataType+ "</td>";
//                          row += "<td>" + commonSet_table[i].basicDataRemark + "</td>";
//                          row +="</tr>";
//                          $("#common-setTreetable").treetable("loadBranch", node, row);
//                          $("#common-setTreetable").treetable("expandNode", node.id);// 展开子节点
//
//                      }
//                      console.log(parentNode);
//                  }
//              }
//              if(!hasChild){
//
//              }
//              $('#common-setTreetable').treetable('collapseAll');
//          }
//      });
//
// });
