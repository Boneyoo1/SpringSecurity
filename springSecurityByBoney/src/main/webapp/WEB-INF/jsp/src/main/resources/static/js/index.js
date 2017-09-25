/**
 * 
 */

$(document).ready(function() {
	setHomePageVideoHeight();
});
$(window).resize(function() {
	setHomePageVideoHeight();
});

function setHomePageVideoHeight() {
	var headerHeight = $(".app_header").innerHeight();
	var windowHeight = $(window).innerHeight();
	var windowWidth = $(window).innerWidth();
	var homeContainerHeight = windowHeight - headerHeight;
	
}