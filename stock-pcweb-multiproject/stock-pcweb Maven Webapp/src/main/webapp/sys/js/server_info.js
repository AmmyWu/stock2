//@ sourceURL=server-info.js
(function() {
    $(document).ready(function () {
        // $('#lxy_basicdata_tb').DataTable().empty();
        //标题行
        var table;
        init_server_tb();

        $('#server_tb tbody').on('click', 'tr', function () {

            $(this).toggleClass('selected');
            $(this).find("input[type=checkbox]").prop("checked", $(this).hasClass('selected') ? true : false);
        });

        // 表格的全选
        $('body').on('click', '.server_info .checkall', function () {
            var check = $(this).prop("checked");
            $(".server_info .checkchild").prop("checked", check);
            if (check) {
                $("tr").addClass("selected");// 如果checkbox全选，给tr添加selected的class属性
            }
            else {
                $("tr").removeClass("selected");
            }

        });
        function init_server_tb() {
            table = $("#server_tb").DataTable({
                fnDrawCallback: changePage, //重绘的回调函数，调用changePage方法用来初始化跳转到指定页面
                // data: dataSet,
                bPaginate: true, //翻页功能
                bLengthChange: true, //改变每页显示数据数量
                paging: true,
                lengthChange: false,
                searching: false,
                ordering: true,
                info: true,
                autoWidth: false,
                select: true,
                scrollX: true,
                destroy:true, //Cannot reinitialise DataTable,解决重新加载表格内容问题
                ajax: "sys/mock_data/server_info.txt",
                dom: 'Blfrtip',
                language: {
                    "url": "js/Chinese.json",
                    select: {
                        rows: "%d 行被选中"
                    },
                    buttons: {
                        copyTitle: '复制到剪切板',
                        // copyKeys: 'Appuyez sur <i>ctrl</i> ou <i>\u2318</i> + <i>C</i> pour copier les données du tableau à votre presse-papiers. <br><br>Pour annuler, cliquez sur ce message ou appuyez sur Echap.',
                        copySuccess: {
                            _: '将%d 行复制到剪切板',
                            1: '将1行复制到剪切板'
                        }
                    }
                },
                columns: [
                    {
                        "sClass": "text-center",
                        "data": "NAME",
                        "title": "<input type='checkbox' class='checkall' />",
                        "render": function (data, type, full, meta) {
                            return '<input type="checkbox"  class="checkchild"  value="' + data + '" />';
                        },
                        "bSortable": false

                    },
                    {title: "日志目录", data: "logDirectory"},
                    {title: "服务器ip", data: "serverIp"},
                    {title: "端口", data: "port"},
                    {title: "服务器名称", data: "serverName"},
                    {title: "描述", data: "description"},
                    {title: "接口组", data: "accessGroup"},
                    {title: "记录版本", data: "recordVersion"},
                    {title: "录入人", data: "amender"},
                    {title: "录入时间", data: "amendTime"}
                ],
                columnDefs: [
                    {
                        orderable: false,
                        targets: 0
                    },
                    {
                        "render": function (data, type, full, meta) {
                            if  (type === 'display')
                            return type === 'display' && data.length > 30 ?
                                '<span title="' + data + '">' + data + '</span>' : data;
                            else if  (type === 'copy') {
                                var api = new $.fn.dataTable.Api(meta.settings);
                                data = $(api.column(meta.col).header()).text() + ": " + data+"  ";
                            }
                            return data;
                        },
                        targets: [1, 2, 3, 4, 5, 6, 7, 8, 9]
                    }
                ],
                buttons: [
                    {
                        extend: 'excelHtml5'
                        // text:"导出Excel"
                    },
                    {
                        extend: 'copyHtml5',
                        text: '拷贝选中行',
                        header: false,
                        exportOptions: {
                            modifier: {
                                selected: true
                            },
                            orthogonal: 'copy'
                        }
                    },
                    {
                        extend: 'print',
                        text: '打印全部'
                    },
                    {
                        extend: 'print',
                        text: '打印选中行',
                        exportOptions: {
                            modifier: {
                                selected: true
                            }
                        }
                    }
                ],
                select: {
                    // blurable: true,
                    style: 'multi',//选中多行
                    // info: false
                }
            });
        }

        // 查询按钮
        $("#server_info_search").click(function () {
            alert("待完善，请先使用表格上面的Search功能！");
        })

        // 删除按钮
        $("#deleteServer").click(function () {
            var info;
            if ($(".server_info .checkchild:checked").length < 1) {
                info = "请选择要删除的对象";
                callAlert(info);
                return;
            }
            info = "确定要删除" + $(".server_info .checkchild:checked").length + "数据吗?";
            callAlertModal(info,'Serverinfo_confirmDelete');
            $('.Serverinfo_confirmDelete').click(function () {
                $(".server_info .checkchild").each(function () {
                    if ($(this).is(':checked')) {
                        table.row($(this).parentNode).remove().draw(false);
                    }
                });
                callSuccess("删除成功！");
            })

        })

        //取消按钮resetServer
        $("#cancelServer").click(function () {
            table.ajax.reload();
        })

        //查询栏取消按钮
        $("#resetServer").click(function () {
            $('#serverInfo_search-table input').val('');
        })

        //新增/修改模态框取消按钮
        $("#cancelServer2").click(function () {
            $('#editServerModal input').val('');
        })

        // 新增按钮
        $('#addServer').on('click', function () {

            $("input[name='saveType']").val("insert");

            $('#editServerModal').modal('show');

        });

        // 修改按钮
        $('#editServer').on('click', function () {
            var selectedRowData = table.rows('.selected').data();

            if (selectedRowData.length < 1) {
                callAlert("请选择一条记录进行编辑！")
                return;
            }
            if (selectedRowData.length > 1) {
                callAlert("每次只允许选择一条数据进行编辑！")
                return;
            }

            var data = selectedRowData[0];
            // console.log(data);

            //循环给表单赋值
            $.each($("#serverForm input"), function (i, input) {
                $(this).val(data[$(this).attr("name")])
            });

            $("input[name='saveType']").val("update");

            $('#editServerModal').modal('show');

        });


        // 新增/修改模态框保存按钮
        $('#saveServer').on('click', function () {
            var saveContent = $('#serverForm').serializeObject();
            table.row().add(saveContent).draw(false);
//     	var saveType =  $("input[name='saveType']").val();
//
// 		  $.ajax({
// 		      url :'/stockWebappBase/user/'+ saveType +'.do',
// 		      type : 'POST',
// 		      data:$('#employeeForm').serializeObject(),//{data:JSON.stringify($('#userForm').serializeObject())},
// 		      dataType : 'json',
// 		      async : false,
// 		      success : function(res) {
// 		    	  	table.ajax.reload();
// 		      },
// 		      error : function() {
// 		    	  alert("修改失败!");
// //		          $.messager.alert('系统提示','申请失败,请重试！','warning');
// 		      }
// 		  });
        });
    });
})();