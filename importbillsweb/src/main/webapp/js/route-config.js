(function() {
	angular.module('routes', [ 'ngRoute' ]).config(
			[ '$routeProvider', function($routeProvider) {
				$routeProvider.when('/', {
					templateUrl : 'includes/fileupload.html',
					controller : 'fileUploadCtrl'
				}).when('/invoiceList', {
					templateUrl : 'includes/invoiceList.html',
					controller : 'invoiceListCtrl'
				}).when('/fileupload', {
					templateUrl : 'includes/fileupload.html',
					controller : 'fileUploadCtrl'
				}).when('/imageList', {
					templateUrl : 'includes/invoiceList.html',
					controller : 'imageListCtrl'
				});

				$routeProvider.otherwise({
					redirectTo : '/'
				});
			} ]);
})();