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
	            var mainurl = "http://104.197.4.247:8090";
	            if ($rootScope.location.$$host == "localhost") {
	            	mainurl = "http://localhost:8090";
	            }	
				return $http
						.get(mainurl + "/importbillservices/invoices");
			};

			return service;
		} ]);
