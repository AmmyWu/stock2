//@ sourceURL=organizationStructure.js
// ztree的参数以及相关方法
//zTreeId 生成树的ul标签的ID；data 用于生成树的数据
function InitOrganizationStructurezTree(zTreeId,data){
    var Organizationalstructure_treeObj = $.fn.zTree.init($("#"+zTreeId), OrganizationStructuresetting,data);//初始化treeResouce用作增加和编辑的选择框
    Organizationalstructure_treeObj.expandAll(true);//节点全部展开
}
var OrganizationStructuresetting = {
    view: {
        dblClickExpand: false
    },
    isSimpleData : true,
    data: {
        simpleData: {
            enable: true,//简单数据的模式下可以指定idkey和pIkey
            idKey : "organizationStructureId",
            pIdKey : "superiorId"
        },
        key:{
            name: "name",
            url : "urlX"

        }
    },
    callback: {
        // beforeClick: beforeClick,
        onClick: onClickOrganizationStructure//回调函数
    }
};
//点击事件，通过点击来获取树的节点的字段和id赋值给form表单的相应字段
function onClickOrganizationStructure(e, treeId, treeNode) {
    var idKey = OrganizationStructuresetting.data.simpleData.idKey;
    var name = OrganizationStructuresetting.data.key.name;
    var pIdKey = OrganizationStructuresetting.data.simpleData.pIdKey;
    var formId = $("#"+treeId).closest("form")[0].id;
    var zTree = $.fn.zTree.getZTreeObj(treeId),
        nodes = zTree.getSelectedNodes(),
        v = "";
    // parentNode = nodes;
    nodes.sort(function compare(a,b){return a.id-b.id;});
    var uuid = nodes[0][idKey];
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i][name] + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    // var cityObj = $("#commonsetResourceSel");
    // cityObj.attr("value", v);
    //区别是employee模块和organization-structure模块的使用
    if(formId.toLowerCase().indexOf("employee") >= 0 || formId.toLowerCase().indexOf("serialnumber") >= 0  ){
        $("#"+formId+" input[name='"+idKey+"']").val(uuid);
        $("#"+formId+" input[name='organizationStructureName']").val(v);//employee中的组织架构树
    }
    // else if(){
    //     console.log(uuid);   //结点级数
    //     console.log(v);  //部门名称
    //     $("#"+formId+" input[name='"+idKey+"']").val(uuid);
    //     $("#"+formId+" input[name='organizationStructureName']").val(v);//employee中的组织架构树
    // }
    else{
        var parentId = pIdKey;
        $("#"+formId+" input[name='parentName']").val(v);//organization-structure中的组织架构树
        $("#"+formId+" input[name='"+parentId+"']").val(uuid);
    }


}

function showOrgstruMenu(eachMenuContent) {
    $("."+eachMenuContent+"").slideDown("fast");
    $("body").bind("mousedown", onBodyDown);
}
function hideMenu() {
    $(".menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.class == "menuContent" || $(event.target).parents(".menuContent").length>0)) {
        hideMenu();
    }
}