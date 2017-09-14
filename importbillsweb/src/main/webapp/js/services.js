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
            mainurl = "http://104.197.4.247:8080";
            if ($rootScope.location.$$host == "localhost") {
            	mainurl = "http://localhost:8080";
            }	
			service.getInvoiceList = function() {
				return $http
						.get(mainurl + "/importbillservices/invoices");
			};

			return service;
		} ]);
