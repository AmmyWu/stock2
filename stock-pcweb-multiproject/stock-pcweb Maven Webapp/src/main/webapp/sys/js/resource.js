//@ sourceURL=resource.js
(function() {
    $.validator.setDefaults({
        submitHandler: submitEditResourceModal
    });
    $().ready(
        function validateResourceForm() {
            Commonset_Validator = $("#resourceAddForm").validate({
                rules: {
                    text: {
                        maxlength: 45
                    },
                    priority: {
                        digits: true
                    },
                    description: {
                        maxlength: 200
                    },
                    type: {
                        maxlength: 45
                    },
                    url: {
                        maxlength: 100,
                        url: true
                    },
                    remark: {
                        maxlength: 45
                    }
                },
                messages: {
                    name: {
                        maxlength: "请不要超过限制的45个字符数"
                    },
                    priority: {
                        digits: "请输入数字"
                    },
                    description: {
                        maxlength: "请不要超过限制的200个字符数"
                    },
                    type: {
                        maxlength: "请不要超过限制的45个字符数"
                    },
                    url: {
                        maxlength: "请不要超过限制的100个字符数",
                        url: "请输入正确网址"
                    },
                    remark: {
                        maxlength: "请不要超过限制的45个字符数"
                    }
                }
            });
            //return Commonset_Validator.form();
        }
    );


    var TableData;
    var treeObj;
    var treetableSetting = {
        id: "resourceId",
        pId: "parentId",
        name: "text"
    };
    var paral = {
        "icon": "图标",
        // "resourceId": "ID",
        "text": "名称",
        "code": "代码",
        // "parentId": "父ID",
        "priority": "优先级",
        "remark": "备注",
        // "status": "状态",
        "type": "类型",
        "url": "网址"
    };
    InitTable();
    function InitTable() {
        $("#resourceTreetable tbody").html("");
        $.ajax({
            type: 'POST',
            url: getContextPath() + 'resource/getResources.do',
            //  url: '../mock_data/resource.json',
            // url: '../js/demo.json',
            async: false,
            dataType: "json",
            success: function (data) {
            	console.log(data);
                TableData = data;//为子对象展开使用
                treeObj = $.fn.zTree.init($("#treeResouce"), setting, TableData);//初始化treeResouce用作增加和编辑的选择框
                treeObj.expandAll(true);//节点全部展开
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

                //通过指定id和pid以及name来生成treetable
                $.TreeTable("resourceTreetable", heads, data, treetableSetting);

            }
        })
    }

// ztree的参数
    var setting = {
        view: {
            dblClickExpand: false
        },
        isSimpleData: true,
        data: {
            simpleData: {
                enable: true,//简单数据的模式下可以指定idkey和pIkey
                idKey: "resourceId",
                pIdKey: "parentId"
            },
            key: {
                name: "text",
                url: "urlX"

            }
        },
        callback: {
            // beforeClick: beforeClick,
            onClick: onClick//回调函数
        }
    };

// function beforeClick(treeId, treeNode) {
//     var check = (treeNode && !treeNode.isParent);
//     if (!check) alert("");
//     return check;
// }

    function onClick(e, treeId, treeNode) {
        var idKey = setting.data.simpleData.idKey;
        var name = setting.data.key.name;
        var zTree = $.fn.zTree.getZTreeObj("treeResouce"),
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
        $("#resourceAddModal input[name='parentName']").val(v);
        $("#resourceAddModal input[name='parentId']").val(uuid);//点击选择，赋给父节点ID
    }

    function showMenu() {
        $("#RtreemenuContent").slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }

    function hideMenu() {
        $("#RtreemenuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }

    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "RtreemenuContent" || $(event.target).parents("#RtreemenuContent").length > 0)) {
            hideMenu();
        }
    }

//编辑资源弹出框
    $("#modifyResource").click(function () {
        var info;
        if ($("#resourceTreetable tbody .selected").length != 1) {
            info = "请选择一条对象进行编辑";
            callAlert(info);
            return;
        }
        emptyAddForm();

        $("#resourceTreetable tr").each(function () {
            var obj = {};
            if ($(this).hasClass('selected')) {
                var tr = $(this)[0].childNodes;
                for (var i = 0; i < tr.length; i++) {
                    obj[tr[i].title] = tr[i].textContent;
                }
                var parent = {};

                obj["resourceId"] = $(this).attr("data-tt-id");
                //通过孩子节点id获取父节点信息
                for (var i = 0; i < $(this)[0].attributes.length; i++) {
                    if ($(this)[0].attributes[i].name == "data-tt-parent-id") {
                        parent.id = $(this)[0].attributes[i].value;
                        // var node = treeObj.getNodeByTId(parent.id);
                        // parent.name = node[treetableSetting.name];
                        obj["parentId"] = parent.id;
                    }
                    if ($(this)[0].attributes[i].name == "data-tt-parent-name") {
                        parent.name = $(this)[0].attributes[i].value;
                        obj["parentName"] = parent.name;
                    }
                }
                //给弹框赋值
                for (var key in obj) {
                    $("#resourceAddModal input[name='" + key + "']").val(obj[key]);
                }
                setDefaultValue($("#resourceAddModal"), 'update');
                $('#resourceAddModal').modal('show');//现实模态框

            }
        });
    });

//增加
    $("#addResource").click(function () {
        // $("#resourceTreetable").treetable("node", "1");
        emptyAddForm();


        setDefaultValue($("#resourceAddModal"), 'insert');
        // $("#createTime").val( $.date.format(new Date(),"yyyy-MM-dd HH:mm:ss"));
        // $('#creatorName').val("admin");
        // $('#optionType').val("insert");
        $('#resourceAddModal').modal('show');//现实模态框
    });
//点击保存
    function submitEditResourceModal() {
        //$("#submitResourceAddModal").click(function () {

        var obj = $('#resourceAddForm').serializeObject();
        var optionType = obj["optionType"];
        // obj = JSON.stringify(obj);

        $.ajax({
            type: 'post',
            url: getContextPath() + 'resource/' + optionType + '.do',
            data: obj,//{optionType:obj},
            cache: false,
            dataType: "json",
            success: function (res) {
                if (res.code == 0) {
                    InitTable();
                    callSuccess("增加成功！");
                    // $("#resourceTreetable").treetable("reveal", obj.parentId);
                }
                else {
                    callAlert("增加失败")
                }
            }
        });
        //emptyAddForm();//清空弹框
        $('#resourceAddModal').modal('hide');//现实模态

        //})
    }

//删除
    $("#deleteResource").click(function () {
        var info;
        if ($("#resourceTreetable tbody .selected").length < 1) {
            info = "请选择要删除的对象";
            callAlert(info);
            return;
        }
        info = "确定要删除" + $("#resourceTreetable tbody .selected").length + "数据吗?";
        callAlertModal(info,'Resource_confirmDelete');
        $('.Resource_confirmDelete').unbind('click').click(function () {
            var ids = [];
            $("#resourceTreetable tr").each(function () {
                if ($(this).hasClass('selected')) {
                    for (var i = 0; i < $(this)[0].attributes.length; i++) {
                        if ($(this)[0].attributes[i].name == "data-tt-id") {
                            ids.push($(this)[0].attributes[i].value);
                        }
                    }
                }
            });
            $.ajax({
                url: getContextPath() + '/basicdataset/delete.do',
                data: ids,
                cache: false,
                dataType: "json",
                success: function (res) {
                    if (data.success()) {
                        callSuccess("删除成功！");
                        //InitTable();
                        //根据后台返回信息如果成功进行删除，无需刷新表格
                        for (var i = 0; i < arr.length; i++) {
                            for (var key in arr[i])
                                $("#resourceTreetable").treetable("removeNode", arr[i][key]);
                        }
                    }
                    else {
                        callAlert("删除失败")
                    }
                }
            });
        });
    })

//刷新
    $("#refreshResource").click(function () {
        // InitTable();
        resourceTreetable.ajax.reload();

    });

//关闭弹框时清空
    function emptyAddForm() {
        $("#resourceAddForm")[0].reset();
        $("label.error").remove();
    };
// click item display detail infomation
    $('#resourceTreetable tbody').on('dblclick', 'tr', function () {
        Showdetail($(this));
    });
    $('#showResourceDetail').on('click', function () {
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
})();

 //  $("#resourceTreetable").treetable({
 //     expandable: true,// 展示
 //     initialState: "expanded",//默认打开所有节点
 //     stringCollapse: '关闭',
 //     stringExpand: '展开',
 //      onNodeCollapse: nodeCollapse,
 //      // nodeIdAttr:'id',
 //      // parentIdAttr:'parentid',
 //     onNodeExpand: nodeExpand,//节点点击时调用函数nodeExpand
 //     // onInitialized:InitTable
 //
 // });
 //  function nodeCollapse() {
 //      alert("collapse");
 //  }
 //
 // function nodeExpand () {
 //     var hasChild = false;  //判断如果没有孩子节点则改为叶子节点
 //     var node = this; 		//判断当前节点是否已经拥有子节点
 //     var childSize = $("#resourceTreetable").find("[data-tt-parent-id='" + node.id + "']").length;
 //     if (childSize > 0) {
 //         return;
 //     }
 //     console.log(childSize);
 //     for(var i=0;i<TableData.length;i++) {//将孩子节点展开
 //         if (TableData[i].parentid == node.id) {
 //             hasChild = true;
 //             var parentNode = $("#resourceTreetable").treetable("node", TableData[i].parentid);
 //             var nodeToAdd = $("#resourceTreetable").treetable("node",TableData[i].id);
 //             // check if node already exists. If not add row to parent node
 //             if(!nodeToAdd){
 //                 var row ='<tr data-tt-id="' +
 //                     TableData[i].id +
 //                     '" data-tt-parent-id="' +
 //                     TableData[i].parentid +'" data-tt-branch="true"';
 //                 row += '>';
 //                 row += "<td title='text'><span><input type='checkbox'/></span><span class='folder'>" + TableData[i].text + "</span></td>";
 //                 row += "<td title='type'>" + TableData[i].type+ "</td>";
 //                 row += "<td title='priority'>" + TableData[i].priority + "</td>";
 //                 row += "<td title='url'>" + TableData[i].url + "</td>";
 //                 row += "<td title='parentid'>" + TableData[i].parentid + "</td>";
 //                 row += "<td title='remark'>" + TableData[i].remark + "</td>";
 //                 row +="</tr>";
 //                 console.log(row);
 //                 $("#resourceTreetable").treetable("loadBranch", node, row);
 //                 $("#resourceTreetable").treetable("expandNode", node.id);// 展开子节点
 //
 //             }
 //             console.log(parentNode);
 //         }
 //     }
 //     if(!hasChild){
 //         var $tr = $("#resourceTreetable").find("[data-tt-id='" + node.id + "']");
 //         $tr.attr("data-tt-branch","false");// data-tt-branch 标记当前节点是否是分支节点，在树被初始化的时候生效
 //         $tr.find("span.indenter").html("");// 移除展开图标
 //         $tr.find("span.folder").removeClass("folder").addClass("file")// 把文件夹图标改为文件图标
 //     }
 //     // $('#resourceTreetable').treetable('collapseAll');
 // }
// var count = 1;
// for (var i=0;i<data.length;i++){
//     if(data[i].parentid==0){
//         var parenttr = "<tr data-tt-id='" + data[i].id + "' data-tt-branch='true'>";
//         parenttr +="<td title='text'><span><input type='checkbox'/></span><span class='folder'>"+data[i].text+"</span></td>";
//         parenttr +="<td title='type'>"+data[i].type+"</td>";
//         parenttr +="<td title='priority'>"+data[i].priority+"</td>";
//         parenttr +="<td title='url'>"+data[i].url+"</td>";
//         parenttr +="<td title='parentid'>"+data[i].parentid+"</td>";
//         parenttr +="<td title='remark'>"+data[i].remark+"</td>";
//         parenttr +="</tr>"
//         $("#resourceTreetable").treetable("loadBranch",null, parenttr);
//         break;
//     }
// }
// for(var i=0;i<data.length;i++) {
//     data[i].icon="";
//     if (data[i].parentid == 1) {
//         var parenttr = "<tr data-tt-id='" + data[i].id + "' data-tt-branch='true'  data-tt-parent-id='1'>";
//         parenttr +="<td title='text'><span><input type='checkbox'/></span><span class='folder'>"+data[i].text+"</span></td>";
//         parenttr +="<td title='type'>"+data[i].type+"</td>";
//         parenttr +="<td title='priority'>"+data[i].priority+"</td>";
//         parenttr +="<td title='url'>"+data[i].url+"</td>";
//         parenttr +="<td title='parentid'>"+data[i].parentid+"</td>";
//         parenttr +="<td title='remark'>"+data[i].remark+"</td>";
//         parenttr +="</tr>"
//         $("#resourceTreetable").treetable("loadBranch",null, parenttr);
//     }
// }