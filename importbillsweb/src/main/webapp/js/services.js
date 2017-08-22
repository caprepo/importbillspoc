'use strict';

/* Services */
var importbillsServices = angular.module('importbillsServices',
		[ 'ngResource' ]);

importbillsServices.factory('InvoiceService', [
		'$http',
		'$location',
		'$rootScope',
		function($http, $location, $rootScope) {
			var service = {};
			service.getInvoiceList = function() {
				return $http
						.get("http://localhost:8090/importbillservices/invoices");
			};

			return service;
		} ]);
