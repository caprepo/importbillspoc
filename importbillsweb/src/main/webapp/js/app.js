'use strict';

/* App Module */
var importbillsApp = angular.module(
		'importbillsApp',
		[ 'routes', 'ngGrid', 'importbillsServices', 'importbillsControllers',
				'importbillsDirectives', 'ngInputModified', 'ngProgress',
				'ngStorage' ]).run(function($rootScope, $location) {
					$rootScope.location = $location;		
});