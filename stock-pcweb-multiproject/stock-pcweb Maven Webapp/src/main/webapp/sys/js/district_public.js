//@ sourceURL=public.js

//取消增加（隐藏增加模块）
  $(".add_cancel").click( function() {

      $(".add-box").addClass("collapsed-box");
      $(".add-box-body").css("display", "none");
      $("#add_fa_btn").removeClass("fa-minus").addClass("fa-plus");
      $(".add_input").each(function () {
          $(this).val("");
      });
  });
$(".add_cancel2").click( function() {

    $(".add-box2").addClass("collapsed-box");
    $(".add-box-body2").css("display", "none");
    $("#add_fa_btn2").removeClass("fa-minus").addClass("fa-plus");
    $(".add_input2").each(function () {
        $(this).val("");
    });
});

// add-authorize-box
function DisplayAddbox(){
    $(".add-box").removeClass("collapsed-box");
    $(".add-box-body").css("display","block");
    $("#add_fa_btn").addClass("fa-minus").removeClass("fa-plus");
}
function DisplayAddBox2(){
    $(".add-box2").removeClass("collapsed-box");
    $(".add-box-body2").css("display","block");
    $("#add_fa_btn2").addClass("fa-minus").removeClass("fa-plus");
}
// //点击表格某一条，现实弹框
// //传入参数是self:被选中的
// function DisplayDetail(data,paral) {
//     console.log("come in DisplayDetail");
//     $("#myModal .modal-body").append("<table id='detail_table'></table>");
//     var count=0;
//     var tr;
//     for(var key in paral){
//         if(count==0){
//             tr="<tr><td>"+paral[key]+"</td><td>"+data[key]+"</td>";
//             count=1;
//         }
//         else {
//             tr +="<td>"+paral[key]+"</td><td>"+data[key]+"</td></tr>";
//             $("#detail_table").append(tr);
//             count=0;
//         }
//         // console.log(index);
//     }
//     if(count==1){
//         tr +="</tr>"
//         $("#detail_table").append(tr);
//     }
//     $('#myModal').modal('show');//现实模态框
//
// }

//重置search form
$(".search_reset").click( function() {
    $(".search-table").find('input').each(function () {
        $(this).val("");
    });
    }
)

//重置add form
$(".add_reset").click( function() {
    $(".add-table").find('input').each(function () {
        $(this).val("");
    });    }
)
