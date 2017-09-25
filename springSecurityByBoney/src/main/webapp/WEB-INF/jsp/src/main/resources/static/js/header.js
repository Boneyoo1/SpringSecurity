$(document).ready(function() {
	jconfirm.defaults = {
		title : '',
		closeIcon : true,
		closeIconClass : 'fa fa-close',
		btnClass : 'btn',
		columnClass : 'col-md-5 col-md-offset-4',
	}
	setHomeContainerHeight();
});
$(window).resize(function() {
	setHomeContainerHeight();
});

function setHomeContainerHeight() {
	var headerHeight = $(".app_header").innerHeight();
	var windowHeight = $(window).innerHeight();
	var homeContainerHeight = windowHeight - headerHeight;
	$(".page_container").css("height", homeContainerHeight - 2);
}


function browserCompatabity() {

	navigator.browserSpecs = (function() {
		var ua = navigator.userAgent, tem, M = ua
				.match(/(opera|chrome|safari|firefox|msie|trident(?=\/))\/?\s*(\d+)/i)
				|| [];
		if (/trident/i.test(M[1])) {
			tem = /\brv[ :]+(\d+)/g.exec(ua) || [];
			return {
				name : 'IE',
				version : (tem[1] || '')
			};
		}
		if (M[1] === 'Chrome') {
			tem = ua.match(/\b(OPR|Edge)\/(\d+)/);
			if (tem != null)
				return {
					name : tem[1].replace('OPR', 'Opera'),
					version : tem[2]
				};
		}
		M = M[2] ? [ M[1], M[2] ] : [ navigator.appName, navigator.appVersion,
				'-?' ];
		if ((tem = ua.match(/version\/(\d+)/i)) != null)
			M.splice(1, 1, tem[1]);
		return {
			name : M[0],
			version : M[1]
		};
	})();


	if (navigator.browserSpecs.name == 'Chrome') {
		if (navigator.browserSpecs.version < 59) {
			$
			.alert({
				columnClass : 'xlarge',
				content : "Our website works best with Google Chrome (Version: 58 and higher), Firefox(Version: 52 and higher), Safari(Version: 9.1 and higher) and Microsoft Edge(Version: 16 and higher) browsers. Please login with any of these browsers."
			});
		}
	} else 	if (navigator.browserSpecs.name == 'Firefox') {
		if (navigator.browserSpecs.version < 53) {
			$
			.alert({
				columnClass : 'xlarge',
				content : "Our website works best with Google Chrome (Version: 58 and higher), Firefox(Version: 52 and higher), Safari(Version: 9.1 and higher) and Microsoft Edge(Version: 16 and higher) browsers. Please login with any of these browsers."
			});
		}
	} else 	if (navigator.browserSpecs.name == 'IE') {
		if (navigator.browserSpecs.version <= 11) {
			$
			.alert({
				columnClass : 'xlarge',
				content : "Our website works best with Google Chrome (Version: 58 and higher), Firefox(Version: 52 and higher), Safari(Version: 9.1 and higher) and Microsoft Edge(Version: 16 and higher) browsers. Please login with any of these browsers."
			});
		}
	} else 	if (navigator.browserSpecs.name == 'Edge') {
		if (navigator.browserSpecs.version < 16) {
			$
			.alert({
				columnClass : 'xlarge',
				content : "Our website works best with Google Chrome (Version: 58 and higher), Firefox(Version: 52 and higher), Safari(Version: 9.1 and higher) and Microsoft Edge(Version: 16 and higher) browsers. Please login with any of these browsers."
			});
		}
	}  else 	if (navigator.browserSpecs.name == 'Safari') {
		if (navigator.browserSpecs.version < 9.1) {
			$
			.alert({
				columnClass : 'xlarge',
				content : "Our website works best with Google Chrome (Version: 58 and higher), Firefox(Version: 52 and higher), Safari(Version: 9.1 and higher) and Microsoft Edge(Version: 16 and higher) browsers. Please login with any of these browsers."
			});
		}
	} else 	if (navigator.browserSpecs.name == 'Opera') {
		if (navigator.browserSpecs.version < 43) {
			$
			.alert({
				columnClass : 'xlarge',
				content : "Our website works best with Google Chrome (Version: 58 and higher), Firefox(Version: 52 and higher), Safari(Version: 9.1 and higher), Opera(Version: 44 and higher) and Microsoft Edge(Version: 16 and higher) browsers. Please login with any of these browsers."
			});
		}
	} 


}