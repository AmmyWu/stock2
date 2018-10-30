//@ sourceURL=roleTree.js
var zTree_resource;//资源树
var zTree_resourceOS;//二级资源树
// var IDMark_A = "_a";
function InitRolezTree(zTreeId,settingName,data){
    // $(".mask").css("display","block");
    $.fn.zTree.init($("#"+zTreeId), settingName, data).expandAll(true);
    if(zTreeId=="roleTreeResouce"){
        zTree_resource = $.fn.zTree.getZTreeObj(zTreeId);
        showZTreeChecked(zTree_resource,settingName.data.key.name,"editAuthorizeResourceForm")
    }
    else if(zTreeId=="roleTreeResouceOS"){
        zTree_resourceOS = $.fn.zTree.getZTreeObj(zTreeId);
        showZTreeChecked(zTree_resourceOS,settingName.data.key.name,"editAuthorizeResourceOSForm")
    }

}
//资源树的参数设置
var settingResource = {
    check: {
        enable: true,
        chkboxType: {"Y":"s", "N":"ps"}
    },
    view: {
        dblClickExpand: false,
        addDiyDom: addDiyDom
    },
    simpleData:true,
    data: {
        simpleData: {
            enable: true,//简单模式下，可以根据需求设置idKey和pIdKey
            idKey : "resourceId",
            pIdKey : "parentId"
        },
        key:{
            name:"name",
            url:"urlX"

        }
    },
    callback: {
        beforeClick: beforeClickResource,
        onCheck: onCheckResource
    }
};
//资源树os的参数设置
var settingResourceOS = {
    check: {
        enable: true,
        chkboxType: {"Y":"s", "N":"ps"}
    },
    view: {
        dblClickExpand: false
    },
    simpleData:true,
    data: {
        simpleData: {
            enable: true,
            idKey : "organizationStructureId",
            pIdKey : "superiorId"
        },
        key:{
            name:"name",
            url:"urlX"

        }
    },
    callback: {
        beforeClick: beforeClickResourceOS,
        onCheck: onCheckResourceOS
    }
};
//beforeClick的回调函数
function beforeClickResource(treeId, treeNode) {
    // var zTree_resource = $.fn.zTree.getZTreeObj("roleTreeResouce");
    zTree_resource.checkNode(treeNode, !treeNode.checked, null, true);
    // return false;//返回值为false时click回调函数失效
}
function beforeClickResourceOS(treeId, treeNode) {
    zTree_resourceOS.checkNode(treeNode, !treeNode.checked, null, true);
    // return false;
}

function onCheckResource(e, treeId, treeNode) {
    var id = settingResource.data.simpleData.idKey;
    InsertResource(treeNode,id);
    showZTreeChecked(zTree_resource,settingResource.data.key.name,"editAuthorizeResourceForm")
};

function onCheckResourceOS(e, treeId, treeNode) {
    var id = settingResourceOS.data.simpleData.idKey;
    InsertResourceOS(treeNode,id);
    showZTreeChecked(zTree_resourceOS,settingResourceOS.data.key.name,"editAuthorizeResourceOSForm")
    };

//将被check的节点的id以及其子节点的id返回给后台
function InsertResource(treeNode,id) {
    // var ids=[];
    // var nodes = zTree_resource.transformToArray(treeNode.children);//获取改节点下的所有孩子节点
    // ids.push(treeNode[id]);//将该节点的id放入ids中
    // for(var i=0;i<nodes.length;i++){
    //     ids.push(nodes[i][id]);//将孩子即节点的id放入ids中
    // }

    var roleId = $("#editAuthorizeResourceForm input[name='roleId'] ").val();

    // alert(treeNode[id] + " " + roleId);
    if(treeNode.checked){
        //被选中调用选中的接口
        $.ajax({
            url : getContextPath()+"roleResource/insertResource.do",
            type : 'POST',
            data : {
                roleId:roleId,
                resourceId:treeNode[id]  //当前选定节点的ID
            },
            async : false,
            success : function(data) {
            },
            error : function() {
                treeNode.checked = false;
                zTree_resource.updateNode(treeNode);
                callAlert("资源选择失败！");
            }
        });

    }
    else{
        //取消选中调用反选接口
        $.ajax({
            url :  getContextPath()+"roleResource/deleteRoleResource.do",
            type : 'POST',
            data : {
                roleId:roleId,
                resourceId:treeNode[id]  //当前选定节点的ID
            },
            async : false,
            success : function(data) {
            },
            error : function() {
                treeNode.checked = true;
                zTree_resource.updateNode(treeNode);
                callAlert("取消选择资源失败！");
            }
        });

    }
}
function InsertResourceOS(treeNode,id){
    // var ids=[];
    // var nodes = zTree_resourceOS.transformToArray(treeNode.children);//获取改节点下的所有孩子节点
    // ids.push(treeNode[id]);//将该节点的id放入ids中
    // for(var i=0;i<nodes.length;i++){
    //     ids.push(nodes[i][id]);//将孩子即节点的id放入ids中
    // }
    // var organizationStructureId = treeNode[id];

    var roleId = $("#editAuthorizeResourceForm input[name='roleId'] ").val();
    var resourcesId =  $("#editAuthorizeResourceOSForm input[name='resourceId'] ").val();


    if(treeNode.checked){
        //选中，向后台传ResourceOS的ID
        $.ajax({
            url : getContextPath() + "roleResource/insertRoleResourceOS.do",
            type : 'POST',
            // data : ids,
            data:{
                roleId:roleId,
                resourceId:resourcesId, //当前选定节点的ID
                organizationStructureId:treeNode[id]
            },
            async : false,
            success : function(data) {

            },
            error : function() {
                //获取失败将check置为false
                treeNode.checked = false;
                zTree_resourceOS.updateNode(treeNode);
                callAlert("资源选择失败！");
            }
        });
    }
    else{
        //取消选中向后台传ResourceOS的ID
        $.ajax({
            url : getContextPath() + "roleResource/deleteRoleResourceOS.do",
            type : 'POST',
            // data : ids,
            data:{
                roleId:roleId,
                resourceId:resourcesId, //当前选定节点的ID
                organizationStructureId:treeNode[id]
            },
            async : false,
            success : function(data) {

            },
            error : function() {
                treeNode.checked = true;
                zTree_resourceOS.updateNode(treeNode);
                callAlert("取消选择资源失败！");
            }
        });
    }

}

function getResourceOSID(treeNodeId,treeNodeName) {
    //var zTree_resource = $.fn.zTree.getZTreeObj("roleTreeResouce");
    //var zTree_resourceOS = $.fn.zTree.getZTreeObj("roleTreeResouceOS");
    //     $(".mask").css("display","none");
        $("#editAuthorizeResourceOSForm .title label").html(treeNodeName);

        // zTree_resourceOS.checkAllNodes(false);//所有节点的选中状态置为false；

        var roleId = $("#editAuthorizeResourceForm input[name='roleId'] ").val();

    //设置form中的资源Id
     $("#editAuthorizeResourceOSForm input[name='resourceId'] ").val(treeNodeId);

    // var id = settingResourceOS.data.simpleData.idKey;
        // var ids = [];
        // ids.push(treeNodeId);
        // var aa=treeNodeId;
        //被选中调用选中的接口
        $.ajax({
            // url : "",
            url : getContextPath() + "roleResource/getRoleResourceOS.do",

            type : 'POST',
            data : {  //ids,//如果直接treeNodeId返回报错，未能解决，采用数组形式返回则可行－lxy注
                roleId:roleId,
                resourceId:treeNodeId  //当前选定资源节点的ID

            },
            dataType:'json',
            async : false,
            success : function(data) {
                //测试
                InitRolezTree("roleTreeResouceOS",settingResourceOS,data)
                // var data=[10,11,12];
                // for(var i=0;i<data.length;i++){
                //     //根据id字段和id的值来寻找相应的节点。
                //     var nodes = zTree_resourceOS.getNodesByParam(id,data[i],null);
                //     nodes[0].checked = true;//置为选中
                //     zTree_resourceOS.updateNode(nodes[0]);
                // }

            },
            error : function() {
                var data=[10,11,12];
                for(var i=0;i<data.length;i++){
                    //根据id字段和id的值来寻找相应的节点。
                    var nodes = zTree_resourceOS.getNodesByParam(id,data[i],null);
                    nodes[0].checked = true;//置为选中
                    zTree_resourceOS.updateNode(nodes[0]);
                }

            }
        });
        // showZTreeChecked(zTree_resourceOS,settingResourceOS.data.key.name,"editAuthorizeResourceOSForm")
}
//该方法用于input中展示ztree中被选中的值
// zTree:树
// name：名称
//FormId:表单ID
function showZTreeChecked(zTree,name,FormId) {
    //var zTree_resource = $.fn.zTree.getZTreeObj("roleTreeResouce");
    // var name = settingResource.data.key.name;
    var nodes = zTree.getCheckedNodes();
    var v="";
    for (var i=0, l=nodes.length; i<l; i++) {
        v += nodes[i][name] + ",";
    }
    if (v.length > 0 ) v = v.substring(0, v.length-1);
    $("#"+FormId+" input[name='parentName']").val(v);
}


function addDiyDom(treeId, treeNode) {
    // if (treeNode.parentNode && treeNode.parentNode.id!=2) return;
    zTree_resource = $.fn.zTree.getZTreeObj(treeId);
    var id = settingResource.data.simpleData.idKey;
    var relname = settingResource.data.key.name;
    var aObj = $("#" + treeNode.tId);
    if (treeNode.type=="数据") {
        var editStr = "<span class='ztreeIcon' id='zTreeDiyBtn" +treeNode[id]+ "' title='"+treeNode.name+"' onfocus='this.blur();'><span class='button icon01' title='点击编辑该资源的组织架构'></span></span>";
        aObj.append(editStr);
        var btn = $("#zTreeDiyBtn"+treeNode[id]);
        if (btn) btn.bind("click", function(){
            //点击数据类型的节点的左侧摁钮，如果改节点checked＝true则将id传给getResourceID函数
            if(treeNode.checked){
                getResourceOSID(treeNode[id],treeNode.getParentNode().name + "-> "+ treeNode[relname]);
            }
            else {
                callAlert("为选中的节点不能进行该操作！");
            }
        });
    }
}