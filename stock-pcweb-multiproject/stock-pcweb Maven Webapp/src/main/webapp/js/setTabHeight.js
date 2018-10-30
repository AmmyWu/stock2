//设置.tab-page高度为.page-content的95%
$(function() {
	var parentHeight = $(".page-content").height();
	$('.tab-page').height(parentHeight * 0.95);
});
$(window).resize(function () {
	var parentHeight = $(".page-content").height();
	$('.tab-page').height(parentHeight * 0.99);
})