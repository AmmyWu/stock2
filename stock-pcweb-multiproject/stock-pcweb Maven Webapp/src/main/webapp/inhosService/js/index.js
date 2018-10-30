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


        localStorage.menu = '';
        localStorage.button ='';

        window.location.href = "login.html";
        return false;
    });
    var loginingEmployee = $.cookie("loginingEmployee");//获取当前的user
    $("#loginingUserName").html(JSON.parse(loginingEmployee)['user']['username']);
    var  _menus = JSON.parse(localStorage.menu);


    $('#menu').sidebarMenu(
        _menus
        )

  /*  $('#menu').sidebarMenu({
        data: [{
            id: '1',
            text: '我的工作台 ',
            icon: 'fa fa-dashboard',
            url: 'workbench/html/workbench',
            content:"workbench",
            code:"WDGZT"
        },
       
            {
                id: '2',
                text: '运价管理',
                icon: 'fa fa-calendar',
                url: '',
                code:"YJGL",
                menus:[{
                    id: '2_1',
                    text: '海运管理',
                    icon: 'fa fa-circle-o',
                    url: '',
                    code:"HYGL",
                    menus:[{
                        id: '2_1_1',
                        text: '等级管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shippingRank',
                        content:'shippingRank',
                        code:"DJGL"
                    },{
                        id: '2_1_2',
                        text: '整箱运价管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shippingQuotation',
                        content:'shippingQuotation',
                        code:"ZXYJGL"
                    },{
                        id: '2_1_3',
                        text: '附加费管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shippingSurcharge',
                        content:'shippingSurcharge',
                        code:"FJFGL"
                    },
                        {
                        id: '2_1_4',
                        text: '拼箱运价管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shippingQuotationlcl',
                        content:'shippingQuotationlcl'
                    },{
                        id: '2_1_5',
                        text: '支线运价管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/shippingQuotationBranch',
                        content:'shippingQuotationBranch'
                    }]

                },{
                    id: '2_2',
                    text: '空运运价',
                    icon: 'fa fa-circle-o',
                    url: '',
                    menus:[{
                        id: '2_2_1',
                        text: '等级管理',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/airRank',
                        content:'airRank'
                    },{
                        id: '2_2_2',
                        text: '空运运价维护',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/airQuotation',
                        content:'airQuotation'
                    }]
                },{
                    id: '2_3',
                    text: '陆运运价',
                    icon: 'fa fa-circle-o',
                    url: '',
                    menus:[{
                        id: '2_3_1',
                        text: '内陆运输运价',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/landQuotation',
                        content:'landQuotation'
                    }]
                },{
                    id: '2_4',
                    text: '客户运价管理',
                    icon: 'fa fa-circle-o',
                    url: '',
                    menus:[{
                        id: '2_4_1',
                        text: '客户运价设置',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/quotationAttachment',
                        content:'quotationAttachment'
                    },{
                        id: '2_4_2',
                        text: '海运运价规则维护',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/airQuotation',
                        content:'airQuotation'
                    },{
                        id: '2_4_3',
                        text: '空运运价规则维护',
                        icon: 'fa fa-circle-o',
                        url: 'freight/html/airQuotation',
                        content:'airQuotation'
                    }]
                },{
                    id: '2_5',
                    text: '报价附件管理',
                    icon: 'fa fa-circle-o',
                    url: 'freight/html/airQuotation',
                    content:'airQuotation'
                }]
            },{
                id: '90',
                text: '基础资料',
                icon: 'fa fa-dashboard',
                url: '',
                menus: [
                    {
                        id: '9001',
                        text: '基础数据集',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/common-set',
                        content:'common-set',
                        code:"JCSJJ"
                    },
                    {
                        id: '9002',
                        text: '计费单位',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/rate-base',
                        content:'rate-base',
                        code:"JFDW"
                    },
                    {
                        id: '9003',
                        text: '费用',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/charge',
                        content:'charge',
                        code:"FY"
                    },
                    {
                        id: '9004',
                        text: '箱型',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/container-type',
                        content:'container-type',
                        code:"XX"
                    },
                    {
                        id: '9005',
                        text: '船公司',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/vessel-parameter',
                        content:'vessel-parameter',
                        code:"CGS"
                    },
                    {
                        id: '9006',
                        text: '船舶',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/vessel',
                        content:'vessel',
                        code:"CB"
                    },
                    {
                        id: '9007',
                        text: '船期',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/sailingDate',
                        content:'sailingDate',
                        code:"CQ"
                    },
                    {
                        id: '9008',
                        text: '港口',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/port',
                        content:'port',
                        code:"GK"
                    },
                    {
                        id: '9009',
                        text: '码头',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/wharf',
                        content:'wharf',
                        code:"MT"
                    },

                    {
                        id: '9010',
                        text: '航线',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/service_line',
                        content:'service_line',
                        code:"HX"
                    },
                    {
                        id: '9011',
                        text: '航空公司',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/airline',
                        content:'airline',
                        code:"HKGS"
                    },
                    {
                        id: '9012',
                        text: '货物编码',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/hs_code',
                        content:'hs_code',
                        code:"HWBM"
                    },
                    {
                        id: '9013',
                        text: '序列号',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/serial_number',
                        content:'serial_number',
                        code:"XLH"
                    },
                    {
                        id: '9014',
                        text: '打印格式-单证',
                        icon: 'fa fa-circle-o',
                        url: 'basedata/html/printFormat',
                        content:'printFormat',
                        code:"DYGE-DZ"
                    }


                ]
            }, {
                id: '91',
                text: '系统管理',
                icon: 'fa fa-calendar',
                url: '',
                menus: [
                    {
                    id: '9102',
                    text: '菜单',
                    icon: 'fa fa-circle-o',
                    url: 'sys/html/resource',
                    content:'resource',
                    code:"CD"
                },{
                    id: '9103',
                    text: '角色',
                    icon: 'fa fa-circle-o',
                    url: 'sys/html/role',
                    content:'role',
                    code:"JS"
                },{
                    id: '9104',
                    text: '用户',
                    icon: 'fa fa-circle-o',
                    url: 'sys/html/user',
                    content:'user',
                    code:"YH"
                },{
                    id: '9105',
                    text: '组织架构',
                    icon: 'fa fa-circle-o',
                    url: 'sys/html/organizational-structure',
                    content:'organizational-structure',
                    code:"ZZJG"
                },{
                    id: '9106',
                    text: '员工',
                    icon: 'fa fa-circle-o',
                    url: 'sys/html/employee',
                    content:'employee',
                    code:"YG"
                },{
                        id: '9107',
                        text: '操作日志',
                        icon: 'fa fa-circle-o',
                        url: 'sys/html/log',
                        content:'log',
                        code:"CZRZ"
                    },{
                    id: '9108',
                    text: '登陆日志',
                    icon: 'fa fa-circle-o',
                    url: 'sys/html/loginlog',
                    content:'loginlog',
                        code:"DRRZ"
                    }
                    ]
            },

            ]
    });*/
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
                        selectMenus[count].id=item.id;
                        selectMenus[count].text=item.code;
                        selectMenus[count].title=optionsData;//模拟addTab的功能将数据放入title。
                        count++;
                    }

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

            // indexNavTabsWidth();
        }

        $('#seachMenu').select2({
            placeholder: "选择菜单",
            data: selectMenus
        });
        console.log(selectMenus); 
        var box=getUrlParam("box"); 
        if(box!=null){
            var flag = true;
            for(var i = 0; i < options.data.length;i++){
                if(flag){
                    for(var j = 0 ; j <options.data[i].menus.length ; j++){
                        if(box  == options.data[i].menus[j].id){
                            newTab = options.data[i].menus[j];
                            var test  = {
                                id : newTab.id,
                                content : newTab.content,
                                title : newTab.text,
                                close : true,  
                                url : newTab.url
                            };
                            addTabs(test);
                            flag = false;
                            break;
                        }
                    }
                }
            }
        }
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

var addTabs = function (options) {
    //var rand = Math.random().toString();
    //var id = rand.substring(rand.indexOf('.') + 1);
   // var url = window.location.protocol + '//' + window.location.host;
    //options.url = url + options.url;
    // $(this).parent.addClass("selectedItem");
    $(".selectedItem").removeClass("selectedItem");
    $(".sideMenu_"+options.content).addClass("selectedItem");
    var id = "tab_" + options.id;
    $(".index-nav-tabs .active").removeClass("active");
    $(".tab-page.active").removeClass("active");
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
                hideMask();
                if($(".active .treetable").length>0){
                    $(".tree_Table tbody").css({"height":calTreeTableHeight(),"margin-bottom":"10px"});
                }
                if($(".active .dataTable").length>0){
                    var TableId = $(".active .datable-box .dataTable")[$(".active .datable-box .dataTable").length-1].id;
                    $('#'+TableId).dataTable().fnSettings().oScroll.sY = calcDataTableHeight();
                    $('.dataTables_scrollBody:has(#'+TableId+')').height(calcDataTableHeight());
                }
//                if(options.content=="workbench"){
//                    workBench.getTopics();//如果点击工作台的摁钮则初始化
//                }

            });
            tabNavallwidth(true);
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
    if($(".active .treetable").length>0){
        $(".tree_Table tbody").css({"height":calTreeTableHeight(),"margin-bottom":"10px"});
    }
    else if($(".active .dataTable").length>0){
        var TableId = $(".active .datable-box .dataTable")[$(".active .datable-box .dataTable").length-1].id;
        //==null||$(".active// .datable-box.dataTable")[1].id==""?$(".active .datable-box .dataTable")[0]:$(".active .datable-box .dataTable")[1].id;
        $('#'+TableId).dataTable().fnSettings().oScroll.sY = calcDataTableHeight();
        $('.dataTables_scrollBody:has(#'+TableId+')').height(calcDataTableHeight());
    }

    //模拟点击事件，主要针对搜索menu成功时展开父节点
    if($(".sideMenu_"+options.content).parent().parent().parent().prev("a").length>0&&$(".sideMenu_"+options.content).parent().parent().parent(".menu-open").length<=0){
        $(".sideMenu_"+options.content).parent().parent().parent().prev("a").click();
    }
    //如果menu未被打开则打开menu
    if($(".sideMenu_"+options.content).parent(".menu-open").length<=0){
        $(".sideMenu_"+options.content).parent("").prev("a").click();
    }

    //打开一个tab页面后让滚动条回到顶部
    $('html, body').animate({scrollTop:0}, 'slow');
    //设置page-content的初始高度
    var tab_height=$(window).height()-45;
    $(".page-content").css({"min-height":tab_height+"px"});


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
