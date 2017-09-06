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
						.get("http://104.197.4.247:8080/importbillservices/invoices");
			};

			return service;
		} ]);
