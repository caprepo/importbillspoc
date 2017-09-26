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

importbillsControllers.controller('invoiceListCtrl', [
		'$scope','InvoiceService',
		function($scope, InvoiceService) {
			$scope.invoiceListGridOptions = {
				data : 'invoiceListSummary',
				columnDefs : [ {
					field : 'invoiceId',
					displayName : 'Invoice ID'
				}, {
					field : 'invoiceName',
					displayName : 'Invoice Name'
				}, {
					field : 'invoiceLocation',
					displayName : 'Invoice Location'
				}, {
					field : 'invoiceCreationDate',
					displayName : 'Invoice Creation Date'
				} ],
				enableRowSelection : false,
				showSelectionCheckbox : false,
				multiSelect : false,
				enableHighlighting : true
			};
			getInvoiceList();

			function getInvoiceList() {
				InvoiceService.getInvoiceList().success(
						function(data, status, headers, config) {
							if (data != null) {
								$scope.invoiceListSummary = data;

							}
						}).error(function() {
					$scope.invoiceListSummary = [ {
						"invoiceId" : "9998",
						"invoiceName" : "Test Invoice 9998",
						"invoiceLocation" : "http://loc:8000",
						"invoiceCreationDate" : "2017-01-01"
					}, {
						"invoiceId" : "9999",
						"invoiceName" : "Test Invoice 9999",
						"invoiceLocation" : "http://loc:8001",
						"invoiceCreationDate" : "2017-02-01"
					} ];
				});
			}
		} ]);
		
		importbillsControllers.controller('imageListCtrl', [
	'$scope','ImageService',
	function($scope, ImageService) {
		
		$scope.imageListGridOptions = {
			data : 'imageListSummary',
			columnDefs : [ {
				field : 'imageId',
				displayName : 'Image ID'
			}, {
				field : 'imageName',
				displayName : 'Image Name'
			}, {
				field : 'ImageGoogleStorageLoc',
				displayName : 'image Google Storage Location',
			    cellTemplate: '<a ng-href>{{row.entity.ImageGoogleStorageLoc}}</a>'
			}],
			enableRowSelection : false,
			showSelectionCheckbox : false,
			multiSelect : false,
			enableHighlighting : true
		};
		getImageList();

		function getImageList() {
			ImageService.getImageList().success(
					function(data, status, headers, config) {
						if (data != null) {
							$scope.imageListSummary = data;

						}
					}).error(function() {
						$scope.imageListSummary = [ {
							
						}];
						
						
			});
		}
	} ]);


