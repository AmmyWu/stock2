var flag;
$(function () {

//    websocketStomp.connection();
//
//    //初始化weorkbench的信息
//    workBench.initTopic();
//    workBench.initHeaderTopics();
//    workBench.initMessage();
//    workBench.initHeaderMessages();

	 
   

    //for login
    if(localStorage.menu ==undefined || localStorage.menu == ''){

        callAlert("请登陆！");
        window.location.href = "login.html";
        return false;
    }

    //退出登录
    $("#a-logout").click(function(){
    	localStorage.clear();
        window.location.href = "login.html";
        return false;
    });
    var loginingEmployee = $.cookie("loginingEmployee");//获取当前的user
    $("#loginingUserName").html(JSON.parse(loginingEmployee)['user']['username']);
    // $("loginingUserName").innerHTML(localStorage.username);
    var  _menus = JSON.parse(localStorage.menu);


    $('#menu').sidebarMenu(
        _menus
        )

    $(window).mouseout(function(){
        flag = true;
    });
    $(window).mouseenter(function(){
        flag = false;
    });
    document.onkeydown = function(e){
        e = window.event || e;
        var keycode = e.keyCode || e.which;
        if(keycode == 116){
            flag = false;
        }
    }
});
//该方式通过调用定义好的sidebarMenu的data来初始化左侧菜单
(function ($) {
    var selectMenus = [];
    var count = 0;
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
        //init的方法用来将menu一个个放上去
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
                //如果menu有孩子节点则再次再次遍历
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
        		    if(item.id > 100 && item.url){
        		    	var href = '(addTabs({id:\'' + item.id +'\',content:\'' + item.content +  '\',title: \'' + item.text + '\',close: true,url: \'' + item.url + '\'}))';
            			a.attr('onclick', href);
            			li.append(a);
		              }
                    li.append(menus);
                }
                //如果没有孩子节点则直接添加到页面
                else {
                    var href = 'javascript:addTabs({id:\'' + item.id +'\',content:\'' + item.content +  '\',title: \'' + item.text + '\',close: true,url: \'' + item.url + '\'});';
                    var classname = "sideMenu_"+item.content;
                    a.attr('href', href);
                    li.addClass(classname);
                    //初始化用来选择菜单的数据selectMenus
                    if(item.code!=null||item.code!=undefined){
                        var optionsData={"id":item.id,"content":item.content,"title":item.text,"close":true,"url":item.url};
                        optionsData=JSON.stringify(optionsData);
                        selectMenus[count]={};
                       eselectMenus[count].id=item.id;
                        selectMenus[count].text=item.code;
                        selectMenus[count].title=optionsData;//模拟addTab的功能将数据放入title。
                        count++;
                    }
                    li.append(a);
                }
                target.append(li);

            });

            // indexNavTabsWidth();
        }

        $('#seachMenu').select2({
            placeholder: "选择菜单",
            data: selectMenus
        });
        console.log(selectMenus); 
        
    };

    $.fn.sidebarMenu.defaults = {
        url: null,
        param: null,
        data: null
    };
})(jQuery);

//select2 选择事件
$("#seachMenu").on("select2:select",function(e){
    // e 的话就是一个对象 然后需要什么就 “e.参数” 形式 进行获取
    var options = e.params.data.title;
    options = JSON.parse(options);
    addTabs(options);//选中时模拟点击添加Tab页操作
});

var addTabs = function (options,ob) {
	
    checkSession();
    $(".selectedItem").removeClass("selectedItem");
    $(".sideMenu_"+options.content).addClass("selectedItem");
    var id = "tab_" + options.id;
    $(".index-nav-tabs .active").removeClass("active");
    $(".tab-page.active").removeClass("active");
    //如果TAB不存在，创建一个新的TAB
    if (!document.getElementById(id)){
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
                hideMask();
                if($(".active .treetable").length>0){
                    $(".tree_Table tbody").css({"height":calTreeTableHeight(),"margin-bottom":"10px"});
                }
                if($(".active .dataTable").length>0){
                    var TableId = $(".active .datable-box .dataTable")[$(".active .datable-box .dataTable").length-1].id;
                    $('#'+TableId).dataTable().fnSettings().oScroll.sY = calcDataTableHeight();
                    $('.dataTables_scrollBody:has(#'+TableId+')').height(calcDataTableHeight());
                }
            });
            tabNavallwidth(true);
        }
        else {//没有内容，使用IFRAME打开链接
            content = '<div role="tabpanel" class="tab-pane" id="' + id + '"><iframe src="' + options.url + '" width="100%" height="' + mainHeight +
                '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
        }
        //加入TABS
        $(".index-nav-tabs").append(title);
    }
    //激活TAB
     $("#tab_" + id).addClass('active');
     $("#" + id).addClass("active");
    if($(".active .treetable").length>0){
        $(".tree_Table tbody").css({"height":calTreeTableHeight(),"margin-bottom":"10px"});
    }
    else if($(".active .dataTable").length>0){
        var TableId = $(".active .datable-box .dataTable")[$(".active .datable-box .dataTable").length-1].id;
        //==null||$(".active// .datable-box.dataTable")[1].id==""?$(".active .datable-box .dataTable")[0]:$(".active .datable-box .dataTable")[1].id;
        $('#'+TableId).dataTable().fnSettings().oScroll.sY = calcDataTableHeight();
        $('.dataTables_scrollBody:has(#'+TableId+')').height(calcDataTableHeight());
    }
    //打开一个tab页面后让滚动条回到顶部
    $('html, body').animate({scrollTop:0}, 'slow');
    //设置page-content的初始高度
    var tab_height=$(window).height()-45;
    $(".page-content").css({"min-height":tab_height+"px"});
    
   if(ob){
	   $("#tab_8903").empty();
	   $("#tab_8903").append(ob);
   }
};

var closeTab = function (id,tabclass) {
    //如果关闭的是当前激活的TAB，激活他的前一个TAB
    if ($(".index-nav-tabs li.active").attr('id') == "tab_" + id) {
        $("#tab_" + id).prev().addClass('active');
        $("#" + id).prev().addClass('active');
//左侧sidebar改选中当前选项卡
        var selectedclass=$("#" + id).prev()[0].className.split(" ")[0];
        $(".selectedItem").removeClass("selectedItem");
        $(".sideMenu_"+selectedclass).addClass("selectedItem");
    }
    if(id == 622){
        window.clearInterval(tg);
    }
    //关闭TAB
    $("#tab_" + id).empty();
    $("#tab_" + id).remove();
    $("#" + id).remove();
    $("."+tabclass).remove();
     tabNavallwidth(false);
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
    var tab_height=$(window).height()-95;
    $(".nav-tabs-workbench .tab-content").css({"height":tab_height-60+"px","overflow-y":"auto"});

});

//点击tab scroll向上滑动
$('body').on('click', '.index-nav-tabs li', function () {
    $('html, body').animate({scrollTop:0}, 'slow');
});

function resize() {
    var tab_height=$(window).height()-95;//95=header hight
    // var page_height=$(".page-content").height();
    $(".page-content").css({"min-height":tab_height+"px"});
    //workbench页面的高度
    $(".nav-tabs-workbench>.tab-content").css({"height":tab_height-60+"px","overflow-x":"auto"});
    var height_search=$(".active .search-box").length==0?-10:$(".active .search-box").outerHeight(true)-10;
    var height_ul=$("#myTab").length==0?-10:$("#myTab").outerHeight(true)+25;

    // alert($(".active .search-box").length);
    //???????

    var height_htns=$(".active .btn_group").outerHeight(true);
    var table_height=tab_height-height_search-height_htns-height_ul-140;
    var tree_table_height = tab_height-height_search-height_htns-130;
    var h = table_height>400?"400px":table_height+"px";
    console.log(table_height);
    var TableId;
    if($(".active .datable-box .dataTable").length>0){
        TableId = $(".active .datable-box .dataTable")[$(".active .datable-box .dataTable").length-1].id;
        $('#'+TableId).dataTable().fnSettings().oScroll.sY = h;
        $('.dataTables_scrollBody:has(#'+TableId+')').height(h);
    }
    if($(".active .tree_Table tbody").length>0){
        $(".tree_Table tbody").css({"height":tree_table_height+"px","overflow-y":"auto","margin-bottom":"10px"});
    }
    tabNavallwidth(false)
    // 计算右上侧tabs的宽度；
    // var tab_width=$(window).width()-$(".main-sidebar").outerWidth(true);
    // $(".index-nav-tabs").css({"width":tab_width+"px"});
}
$(window).resize(function() {
    var tab_height=$(window).height()-95;//95=header hight
    // var page_height=$(".page-content").height();
    $(".page-content").css({"min-height":tab_height+"px"});
    //workbench页面的高度
    $(".nav-tabs-workbench .tab-content").css({"height":tab_height-60+"px","overflow-x":"auto"});
    var height_search=$(".active .search-box").length==0?-10:$(".active .search-box").outerHeight(true)-10;
    var height_htns=$(".active .btn_group").outerHeight(true);
    var table_height=tab_height-height_search-height_htns-140;
    var tree_table_height = tab_height-height_search-height_htns-130;
    var h = table_height>400?"400px":table_height+"px";
    console.log(table_height);
    var TableId;
    if($(".active .dataTable").length>0){
        TableId = $(".active .datable-box .dataTable")[$(".active .datable-box .dataTable").length-1].id;
        $('#'+TableId).dataTable().fnSettings().oScroll.sY = h;
        $('.dataTables_scrollBody:has(#'+TableId+')').height(h);
    }
    if($(".active .tree_Table tbody").length>0){
        $(".tree_Table tbody").css({"height":tree_table_height+"px","overflow-y":"auto","margin-bottom":"10px"});
    }
    tabNavallwidth(false)
    // 计算右上侧tabs的宽度；
    // var tab_width=$(window).width()-$(".main-sidebar").outerWidth(true);
    // $(".index-nav-tabs").css({"width":tab_width+"px"});

    //test code
    console.log("page-content:" + $(".page-content").height() );
    console.log("tab:" + $(".tab_page").height() );
});
var calcDataTableHeight = function() {
    var page_height=$(".page-content").height();
    // var tab_height=$(window).height()-50;//95=header hight
    var height_search=$(".active .search-box").length==0?+70:$(".active .search-box").outerHeight(true);
    var height_htns=$(".active .btn_group").outerHeight(true);
    var table_height=page_height-height_search-height_htns-130;
    return table_height+"px";

};
var calTreeTableHeight = function () {
    var tab_height=$(window).height()-95;//95=header hight
    var height_search=$(".active .search-box").length==0?+50:$(".active .search-box").outerHeight(true)+50;
    var height_htns=$(".active .btn_group").outerHeight(true);
    var tree_table_height = tab_height-height_search-height_htns-88;
    return tree_table_height+"px";
};
// var indexNavTabsWidth = function () {
//     var tab_width=$(window).width()-$(".main-sidebar").outerWidth(true);
//     $(".index-nav-tabs").css({"width":tab_width+"px"})
// };
var num=0,oUl=$("#min_title_list"),hide_nav=$("#wl-tabNav");

/*获取顶部选项卡总长度*/
// isSlideLeft 如果是添加Tab调用该函数则在显示滑动按钮时调用slideLeft()函数，如果是页面大小改变调用则不调用
function tabNavallwidth(isSlideLeft){
    var taballwidth=0,
        $tabNav = hide_nav.find(".nav-tabs"),
        $tabNavWp = hide_nav.find(".wl-tabNav-wp"),
        $tabNavitem = hide_nav.find(".nav-tabs li"),
        $tabNavmore =hide_nav.find(".wl-tabNav-more");
    if (!$tabNav[0]){return}
    $tabNavitem.each(function(index, element) {
        taballwidth += Number(parseFloat($(this).width()+30))
    });
    // $tabNav.width(taballwidth+25);
    var w = $tabNavWp.width();
    if(taballwidth>w){
        $tabNavmore.show();
        if(isSlideLeft)slideLeft()
    }
    else{
        $tabNavmore.hide();
        $tabNav.css({left:0})
    }
}
function slideLeft() {
    var $tabNavitem = hide_nav.find(".nav-tabs li");
    var slideLeft = -$tabNavitem[$tabNavitem.length-1].clientWidth-10+oUl.position().left;
    oUl.stop().animate({'left':slideLeft+"px"},100);
}
$('.roll-left').click(function(){
    num==oUl.find('li').length-1?num=oUl.find('li').length-1:num++;
    toNavPos();
});
$('.roll-right').click(function(){
    num==0?num=0:num--;
    toNavPos();
});

function toNavPos(){
    oUl.stop().animate({'left':-num*70},100);
}

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
   //index选项卡归位
    $("#min_title_list").css({'left':'0px'});
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
    //index选项卡归位
    $("#min_title_list").css({'left':'0px'});
    tabNavallwidth(false);
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

 function getUrlParam(key) {//用来获取页面url的参数
        // 获取参数
        var url = window.location.search;
        // 正则筛选地址栏
        var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
        // 匹配目标参数
        var result = url.substr(1).match(reg);
        //返回参数值
        return result ? decodeURIComponent(result[2]) : null;
    }
function fineReportOpenTabs(myid){
    var options = JSON.parse(localStorage.menu);		
    for(var i = 0; i < options.data.length;i++){
        for(var j = 0 ; j <options.data[i].menus.length ; j++){
            for(var k = 0 ; k <options.data[i].menus[j].menus.length ; k++){
                if(myid  == options.data[i].menus[j].menus[k].id){
                    newTab = options.data[i].menus[j].menus[k];
                    var test  = {
                        id : newTab.id,
                        content : newTab.content,
                        title : newTab.text,
                        close : true,  
                        url : newTab.url
                    };
                    addTabs(test);
                    return true;
                }
            }
        }
    }
}

 //finereport url
var finereportUrl = "http://47.92.30.74:8080/WebReport/ReportServer?formlet=";
function addFinereportUrl( fileName ) {
    return "http://47.92.30.74:8080/WebReport/ReportServer?formlet=" + fileName;
}

window.onbeforeunload = function(){
    console.log("just a pause");
    if(flag){
        localStorage.clear();
    }
};
function saveHistoryRe(myUrl,name) {
	var reportData = new Object;
	reportData.templetdataName=name;
	reportData.templetdataLink=myUrl;
	
     $.ajax({
         type: 'POST',
         url: getContextPath() + 'templetData/insert.do',
         data: {"templetData":JSON.stringify(reportData)},
         cache: false,
         dataType: "json",
        
         success: function (res) {

            alert("success")
         },
         error: function () {
             callAlert("增加失败");
         }
     });
 }
function checkSession(){
    $.ajax({
        type: "POST",
        url: getContextPath() + "authentication/checkSession.do", //baseURL
        dataType: "text",
        async: false,
        success: function (res) {
        	console.log("session connecting!");
        },
        error: function () {
        	alert("session close！please login again！")
        	localStorage.clear();
        	window.location.href = "login.html";
        }
    });
}