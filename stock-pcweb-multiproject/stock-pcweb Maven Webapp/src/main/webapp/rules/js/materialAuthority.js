


init();


function init() {
    console.log("atast")
    getList("p1");
    getList("p2");
    getList("p3");

    getList("hospital");
    getList("office");
};

function getList(args) {
    $.ajax({
        type: "POST",     //提交方式
        url: getContextPath() + "",    //提交的页面，方法名
        data: null,    //参数，如果没有，可以为null
        success: function (data) { //如果执行成功，那么执行此方法
            console.log("select[name="+args+"]")
            $("select[name="+args+"]").append("<option value='处方药'>处方药</option>");
            $("select[name="+args+"]").trigger('change');
            for (var i = 0; i < data.length; i++) {
                $("select[name="+args+"]").append("<option" + " " + "value=" + data[i].cn_name + ">" + data[i].cn_name + "</option>");
                $("select[name="+args+"]").trigger('change');
            }        //用data.d来获取后台传过来的json语句，或者是单纯的语句
        },
        error: function (err) { //如果执行不成功，那么执行此方法
            alert("err:" + err);
        }
    });
};

function FillP1() {
    $.ajax({
        async: false,
        url: "cl.php",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            var str = "";
            for (var sj in data) {
                str = str + "<option value = '" + data[sj].AreaCode + "'>" + data[sj].AreaName + "</option>";
            }
            $("#p1").html(str);
        }
    });
};

function FillP2() {
    var pcode = $("#p1").val();
    //根据父级代号查数据
    $.ajax({
        async: false,
        //取消异步
        url: "cl.php",
        data: { pcode: pcode },
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            var str = "";
            for (var sj in data) {

                str = str + "<option value = '" + data[sj].AreaCode + "'>" + data[sj].AreaName + "</option>";
            }
            $("#p2").html(str);
        }
    });
};
function FillP3() {
    //根据父级代号
    //取父级代号
    var pcode = $("#p2").val();
    //根据父级代号查数据
    $.ajax({
        url: "cl.php",
        data: { pcode: pcode },
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            var str = "";
            for (var sj in data) {

                str = str + "<option value = '" + data[sj].AreaCode + "'>" + data[sj].AreaName + "</option>";
            }
            $("#p3").html(str);
        }
    });
};

function FillHospital() {
    $.ajax({
        async: false,
        url: "cl.php",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            var str = "";
            for (var sj in data) {
                str = str + "<option value = '" + data[sj].AreaCode + "'>" + data[sj].AreaName + "</option>";
            }
            $("#hospital").html(str);
        }
    });
};

function FillOffice() {
    var pcode = $("#hospital").val();
    //根据父级代号查数据
    $.ajax({
        url: "cl.php",
        data: { pcode: pcode },
        type: "POST",
        dataType: "JSON",
        success: function (data) {
            var str = "";
            for (var sj in data) {
                str = str + "<option value = '" + data[sj].AreaCode + "'>" + data[sj].AreaName + "</option>";
            }
            $("#office").html(str);
        }
    });
};

$("#createRule").click(function () {
    $.ajax({
        url: getContextPath() + '',
        type: 'POST',
        data: {
            doctorBaseInfo: $('#searchForm').serializeObject()
        },// {data:JSON.stringify($('#userForm').serializeObject())},
        dataType: 'json',
        async: false,
    });
})

$("#p1").change(function () {
    FillP2();
    FillP3();
});

$("#p2").change(function () {
    FillP3();
});

$("#hospital").change(function () {
    FillOffice();
});


