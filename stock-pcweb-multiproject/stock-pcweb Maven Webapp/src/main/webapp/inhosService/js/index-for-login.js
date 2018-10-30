$(function () {

    if(localStorage.menu ==undefined || localStorage.menu == ''){

        callAlert("请登陆！");
        window.location.href = "login.html";
        return false;
    }

    //退出登录
    $("#a-logout").click(function(){


        localStorage.menu = '';
        localStorage.button ='';

        window.location.href = "login.html";
        return false;
    });

    var  _menus = JSON.parse(localStorage.menu);
    $('#menu').sidebarMenu({
        data: [{
            id: '0',
            text: '我的工作台 ',
            icon: 'fa iconfont icon-diannao',
            url: 'welcome',
            content:"welcome",
            code:"WDGZT"
        }]});

    $('#menu').sidebarMenu(
        _menus
     
    );
});




(function ($) {
    $.fn.sidebarMenu = function (options) {
        options = $.extend({}, $.fn.sidebarMenu.defaults, options || {});
        var target = $(this);
        target.addClass('nav');
        target.addClass('nav-list');
        if (options.data) {
            init(target, options.data);
        }
        else {
            if (!options.url) return;
            $.getJSON(options.url, options.param, function (data) {
                init(target, data);
            });
        }
        var url = window.location.pathname;
        //menu = target.find("[href='" + url + "']");
        //menu.parent().addClass('active');
        //menu.parent().parentsUntil('.nav-list', 'li').addClass('active').addClass('open');
        function init(target, data) {
            $.each(data, function (i, item) {
                var li = $('<li></li>');
                var a = $('<a></a>');
                var icon = $('<i></i>');
                //icon.addClass('glyphicon');
                icon.addClass(item.icon);
                var text = $('<span></span>');
                text.addClass('menu-text').text(item.text);
                a.append(icon);
                a.append(text);
                if (item.menus&&item.menus.length>0) {
                    a.attr('href', '#');
                    a.addClass('dropdown-toggle');
                    var arrow = $('<span></span>');
                    arrow.addClass('arrow').addClass('icon-angle-down').addClass('pull-right-container');
                    var arr_li = $('<li></li>');
                    arr_li.addClass('fa fa-angle-left pull-right');
                    arrow.append(arr_li);
                    arrow.append(arr_li);
                    a.append(arrow);
                    li.append(a);
                    var menus = $('<ul></ul>');
                    menus.addClass('submenu').addClass('treeview-menu');
                    init(menus, item.menus);
                    li.append(menus);
                }
                else {
                    var href = 'javascript:addTabs({id:\'' + item.id +'\',content:\'' + item.content +  '\',title: \'' + item.text + '\',close: true,url: \'' + item.url + '\'});';
                    var classname = "sideMenu_"+item.content;
                    a.attr('href', href);
                    li.addClass(classname);
                    //if (item.istab)
                    // a.attr('href', href);
                    //else {
                    // a.attr('href', item.url);
                    // a.attr('title', item.text);
                    // a.attr('target', '_blank')
                    //}
                    li.append(a);
                }
                target.append(li);
            });

        }
    }

    $.fn.sidebarMenu.defaults = {
        url: null,
        param: null,
        data: null
    };
})(jQuery);


var addTabs = function (options) {
    //var rand = Math.random().toString();
    //var id = rand.substring(rand.indexOf('.') + 1);
    // var url = window.location.protocol + '//' + window.location.host;
    //options.url = url + options.url;
    // $(this).parent.addClass("selectedItem");
    $(".selectedItem").removeClass("selectedItem");
    $(".sideMenu_"+options.content).addClass("selectedItem");
    var id = "tab_" + options.id;
    $(".active").removeClass("active");
    //如果TAB不存在，创建一个新的TAB
    // if (!$("#tab_" + id)[0])
    if (!document.getElementById(id))
    {
        //固定TAB中IFRAME高度
        var optionString = JSON.stringify(options);//让option作为一个属性赋给li，为刷新页面调用
        mainHeight = $(document.body).height() - 90;
        //创建新TAB的title
        title = '<li info='+optionString+' class="every-nav-tab" role="presentation" id="tab_' + id + '"><a href="#' + id + '" aria-controls="' + id + '" role="tab"' +
            ' data-toggle="tab">' + options.title;
        //是否允许关闭
        if (options.close) {
            title += ' <i class="fa fa-times-circle" tabclose="' + id + '" tabclass="'+options.content+'"></i>';
        }
        title += '</a></li>';
        //是否指定TAB内容
        if (options.content) {
            showMask();
            $.get(options.url+'.html',function (data) {
                $('.page-content').append(data).find('#'+id);
                $("#tab_" + id).addClass('active');
                //$("#div").attr("class");
                $("#" + id).addClass("active");
                console.log("get page");
                // 如果当前页面存在treetable计算treetable的高度
                if($(".active .treetable").length>0){
                    $(".tree_Table tbody").css({"height":calTreeTableHeight(),"margin-bottom":"10px"});
                }
                if($(".active .dataTable").length>0){
                    TableId = $(".active .dataTable")[0].id;
                    $('#'+TableId).dataTable().fnSettings().oScroll.sY = calcDataTableHeight();
                    $('.dataTables_scrollBody:has(#'+TableId+')').height(calcDataTableHeight());
                }
                hideMask();

            });
        }
        else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + options.url + '" width="100%" height="' + mainHeight +
                '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
        }
        //加入TABS
        $(".index-nav-tabs").append(title);


        // $(".tab-content").append(content);
    }
    //激活TAB
    $("#tab_" + id).addClass('active');
    $("#" + id).addClass("active");
    //打开一个tab页面后让滚动条回到顶部
    $('html, body').animate({scrollTop:0}, 'slow');
    //设置page-content的初始高度
    var tab_height=$(window).height()-95;
    $(".page-content").css({"min-height":tab_height+"px"});



};
var closeTab = function (id,tabclass) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($("li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
//左侧sidebar改选中当前选项卡
        var selectedclass=$("#" + id).prev()[0].className.split(" ")[0]
        $(".selectedItem").removeClass("selectedItem");
        $(".sideMenu_"+selectedclass).addClass("selectedItem");
    }
    //关闭TAB
    $("#tab_" + id).empty();
    $("#tab_" + id).remove();
    $("#" + id).remove();
    $("."+tabclass).remove();
    // $(".common-set").remove();

    // $("#tab_" + id).attr("id","#tab_" + id+"_empty");
    //$("#" + id).attr("id","#" + id+"_empty");
    //after remove id still on page
};
$(function () {
    mainHeight = $(document.body).height() - 45;
    $('.main-left,.main-right').height(mainHeight);
    $("[addtabs]").click(function () {
        addTabs({ id: $(this).attr("id"), title: $(this).attr('title'), close: true });
    });
    $(".index-nav-tabs").on("click", "[tabclose]", function (e) {
        id = $(this).attr("tabclose");
        tabclass = $(this).attr("tabclass");
        closeTab(id,tabclass);
    });
});
//点击tab scroll向上滑动
$('body').on('click', '.index-nav-tabs li', function () {
    $('html, body').animate({scrollTop:0}, 'slow');
});
// $(document).ready(function() {
//     $(window).resize(function () {          //当浏览器大小变化时
//         var tab_height=$(window).height()-95;//95=header hight
//         var height_search=$(".search-box").outerHeight(true);
//         $(".page-content").css({"min-height":tab_height+"px"});
//         var height_htns=$(".btn_group").outerHeight(true);
//         // table_height=tab_height-height_search-height_htns;
//         tableHeight = $("#containertypeTable body").height();
//         console.log(tableHeight);
//       //  console.log(tab_height);          //浏览器时下窗口可视区域高度
//     });
// });

$(window).resize(function() {
    var tab_height=$(window).height()-95;//95=header hight
    $(".page-content").css({"min-height":tab_height+"px"});
    var height_search=$(".active .search-box").length==0?-10:$(".active .search-box").outerHeight(true)-10;
    var height_htns=$(".active .btn_group").outerHeight(true);
    var table_height=tab_height-height_search-height_htns-160;
    var tree_table_height = tab_height-height_search-height_htns-130;
    var h = table_height>400?"400px":table_height+"px";
    console.log(table_height);
    var TableId;
    if($(".active .dataTable").length>0){
        TableId = $(".active .dataTable")[1].id;
        $('#'+TableId).dataTable().fnSettings().oScroll.sY = h;
        $('.dataTables_scrollBody:has(#'+TableId+')').height(h);
    }
    if($(".active .tree_Table tbody").length>0){
        $(".tree_Table tbody").css({"height":tree_table_height+"px","overflow-y":"auto","margin-bottom":"10px"});
    }

});
var calcDataTableHeight = function() {
    var tab_height=$(window).height()-95;//95=header hight
    var height_search=$(".active .search-box").length==0?+70:$(".active .search-box").outerHeight(true);
    var height_htns=$(".active .btn_group").outerHeight(true);
    var table_height=tab_height-height_search-height_htns-150;
    return table_height+"px";
};
var calTreeTableHeight = function () {
    var tab_height=$(window).height()-95;//95=header hight
    var height_search=$(".active .search-box").length==0?+50:$(".active .search-box").outerHeight(true)+50;
    var height_htns=$(".active .btn_group").outerHeight(true);
    var tree_table_height = tab_height-height_search-height_htns-70;
    return tree_table_height+"px";
};

// $(document).ready(function() {
//     $(".page-content").scroll(function () {          //当出现scrollbar时候
//        var scrollHeight =  $(".page-content").scrollTop();
//        if(scrollHeight>5){
//            $(".content-header").css({"padding-top":"4px"});
//            $(".btn_group").css({"margin-left":"230px","background-color":"white"});
//        }
//        else {
//            $(".content-header").css({"padding-top":"10px"});
//            $(".btn_group").css({"margin-left":"initial","background-color":"initial"});
//        }
//        // console.log(scrollHeight);
//     });
// });
$(function(){
    $.contextMenu({
        selector: '.every-nav-tab',
        callback: function (key, options) {
            switch (key) {
                case "refresh"://刷新页面
                    RclickRefreshTab(options.$trigger[0].attributes);//传入attributes
                    break;
                case "close"://关闭该选项卡
                    RclickCloseTab(options.$trigger[0].attributes);
                    break;
                case "closeAll"://关闭所有选项卡
                    RclickCloseAllTab();
                    break;
                case "closeOthers"://关闭其他选项卡
                    RclickCloseOthersTab(options.$trigger);
                    break;
                case "closeLeft"://关闭其他选项卡
                    RclickCloseLeftTab(options.$trigger);
                    break;
                case "closeRight"://关闭其他选项卡
                    RclickCloseRightTab(options.$trigger);
                    break;
                default:
                    options.$trigger.removeClass("selected").find("input[type=checkbox]").prop("checked", false);;//取消选择selected
            }
        },
        items: {
            "refresh": {name: "刷新", icon: ""},
            "sep1": "---------",
            "close": {name: "关闭该选项卡", icon: ""},
            "closeAll": {name: "关闭所有选项卡", icon: ""},
            "closeOthers": {name: "关闭其他选项卡", icon: ""},
            "closeLeft": {name: "关闭左侧选项卡", icon: ""},
            "closeRight": {name: "关闭右侧选项卡", icon: ""},
            "sep2": "---------"
        }
    });
});
//根据attributes找到info获取到options的数据，按照添加的方式来更新页面
function RclickRefreshTab(attributes) {
    var optionString;
    for(var i=0;i<attributes.length;i++){
        if(attributes[i].name == "info"){
            optionString=attributes[i].value;
            break
        }
    }
    var options = JSON.parse(optionString);
    var id = "tab_" + options.id;
    if(!$("#tab_" + id).hasClass("active")){
        return;//如果不是当前打开的选项卡，不做刷新操作
    }
    $("#" + id).remove();//先把当前选项卡的内容移出
    if (options.content) {
        $.get(options.url+'.html',function (data) {
            $('.page-content').append(data).find('#'+id);
            $("#tab_" + id).addClass('active');
            //$("#div").attr("class");
            $("#" + id).addClass("active");
            console.log("get page");
        });
    }
    else {//没有内容，使用IFRAME打开链接
        content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + options.url + '" width="100%" height="' + mainHeight +
            '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
    }
}

//根据attributes找到info获取到options的数据,调用close方法关闭改选项卡
function RclickCloseTab(attributes) {
    var optionString;
    for(var i=0;i<attributes.length;i++){
        if(attributes[i].name == "info"){
            optionString=attributes[i].value;
            break
        }
    }
    var options = JSON.parse(optionString);
    var id = "tab_" + options.id;
    var tab_name = options.content;
    closeTab(id,tab_name);
}
//选项卡的head的和body全部移出
function RclickCloseAllTab() {
    $(".tab-page").remove();
    $(".every-nav-tab").remove();
    $(".index-nav-tab").addClass("active");//将index选项卡激活
}
function RclickCloseOthersTab(selectedTab) {
    var attributes = selectedTab[0].attributes;
    var optionString;
    for(var i=0;i<attributes.length;i++){
        if(attributes[i].name == "info"){
            optionString=attributes[i].value;
            break
        }
    }
    var options = JSON.parse(optionString);
    var id = "tab_" + options.id;
    selectedTab.siblings(".every-nav-tab").remove();
    selectedTab.addClass("active");
    $(".tab-page").each(function(){
        if($(this)[0].id!=id){
            $(this).remove();
        }
        else {
            $(this).addClass("active");
        }
    });

}
function RclickCloseLeftTab(selectedTab){
    selectedTab.prevAll(".every-nav-tab").remove();
    var attributes = selectedTab[0].attributes;
    var optionString;
    for(var i=0;i<attributes.length;i++){
        if(attributes[i].name == "info"){
            optionString=attributes[i].value;
            break
        }
    }
    var options = JSON.parse(optionString);
    var id = "tab_" + options.id;
    $("#"+id).prevAll().remove();
}
function RclickCloseRightTab(selectedTab){
    selectedTab.nextAll(".every-nav-tab").remove();
    var attributes = selectedTab[0].attributes;
    var optionString;
    for(var i=0;i<attributes.length;i++){
        if(attributes[i].name == "info"){
            optionString=attributes[i].value;
            break
        }
    }
    var options = JSON.parse(optionString);
    var id = "tab_" + options.id;
    $("#"+id).nextAll().remove();
}