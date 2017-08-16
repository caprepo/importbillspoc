'use strict';

/* Controllers */
var importbillsControllers = angular.module('importbillsControllers', [
		'ngStorage', 'importbillsDirectives' ]);

angular.module('routes').controller('routeController',
		[ '$scope', '$location', function($scope, $location) {
			$scope.showMenu = true;
		} ]);


importbillsControllers.controller('fileUploadCtrl', [ '$scope',
	function($scope) {
		$scope.var1 = "Test scope";
	} ]);

