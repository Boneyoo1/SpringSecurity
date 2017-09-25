var selectedTabIndex = 0;
var completedSectionsCount = 0;
$(document)
		.ready(
				function() {
					$(".nav_menu").removeClass("active");
					$("#apply_now_menu").addClass("active");

					$("#profile_completed_sections_count").text(
							completedSectionsCount);
					$("#profile_total_sections_count").text("7");

					$(".apply-tab-content").hide();
					$("div.apply-tab>div.apply-tab-content").eq(0).show();
					$("div.apply-tab-menu>div.list-group>a").click(
							function(e) {
								e.preventDefault();
								$(".apply-tab-content").hide();
								$(this).siblings('a.tab_active').removeClass(
										"tab_active");
								$(this).addClass("tab_active");
								selectedTabIndex = $(this).index();
								$("div.apply-tab>div.apply-tab-content").eq(
										selectedTabIndex).show();
							});
					setLoadingContainer();
					var tabName = $("#hidden_tab_name").val();
					if (tabName != "") {
						$(".apply-tab-content").hide();
						$("div.apply-tab-menu>div.list-group>a").siblings(
								'a.tab_active').removeClass("tab_active");
					}
					if (tabName == "profile") {
						$("div.apply-tab-menu>div.list-group>a").eq(0)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(0).show();
					} else if (tabName == "education") {
						$("div.apply-tab-menu>div.list-group>a").eq(1)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(1).show();
					} else if (tabName == "testing") {
						$("div.apply-tab-menu>div.list-group>a").eq(2)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(2).show();
					} else if (tabName == "writing") {
						$("div.apply-tab-menu>div.list-group>a").eq(3)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(3).show();
					} else if (tabName == "documents") {
						$("div.apply-tab-menu>div.list-group>a").eq(4)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(4).show();
					} else if (tabName == "employement") {
						$("div.apply-tab-menu>div.list-group>a").eq(5)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(5).show();
					} else if (tabName == "recommenders") {
						$("div.apply-tab-menu>div.list-group>a").eq(6)
								.addClass("tab_active");
						$("div.apply-tab>div.apply-tab-content").eq(6).show();
					}
				});
$(window).resize(function() {
	setLoadingContainer();
});
function setLoadingContainer() {
	$(".loading-bg").css("left", $(".apply-tab-menu").width());
	$(".loading-bg").css("height", $(".apply-tab-menu").height());
}

function closeLoadingContainer() {
	$(".loading-bg").empty();
	$(".loading-bg").remove();
}