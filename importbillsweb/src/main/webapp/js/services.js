'use strict';

/* Services */
var importbillsServices = angular.module('importbillsServices', [ 'ngResource' ]);

importbillsServices.factory('LoginService',['$http','$location','$rootScope', function($http, $location, $rootScope) {
	var service = {};
	service.Login = function(username, password) {
		console.log("In services Login method");
		/*
		return $http.post('/authservice/authenticate', {
			userName : username,
			password : password
		});
		*/
		//return $http.get('/accservices/556678/accounts');
		//return $http.get('/cardservices/556677/cards');
		return $http.get('json/authenticate_success.json');
	};
	service.isLoggedIn = function() {
		$http.defaults.headers.common.authToken = sessionStorage.authToken;
		return $http.post('webapi/security/isLoggedIn');
	};
	return service;
}]);

