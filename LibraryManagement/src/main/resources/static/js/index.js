var changePage = function(_url){
	$.ajax({
		type:"GET",
		url: _url,
		success: function(data){
			$(".main-content").html(data);
		}
	});
}

//function searchEvent(){
//	$('#searchform').submit(function(e){
//		e.preventDefault();
//		$.ajax({
//			type:"post",
//			url:"/home",
//			data: $(this).serialize(),
//			success: function(data){
//				changeTableData(data)
//			}
//	});
//  })
//}

var navbarChangeLink = function(){
	$(".sidenav a").each(function(index,object){
		$(object).on("click",function(e){
			$(".active").removeClass("active");
			$(this).parent().addClass("active");
			$("#page-title").html($(this).html());
			if($(this).attr("href")!="/"){
				e.preventDefault();
				changePage($(this).attr("href"));
			}
			
		});
	});
}
$(document).ready(function(){
//		searchEvent();
		navbarChangeLink();
});