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
			importbillsSeviceUrl = "http://localhost:8090/importbillservices/invoices";
			service.getInvoiceList = function() {
				return $http
						.get(importbillsSeviceUrl);
			};

			return service;
		} ]);
