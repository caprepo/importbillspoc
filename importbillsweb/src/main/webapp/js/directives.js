'use strict';
var importbillsDirectives = angular.module('importbillsDirectives', []).config( [
'$compileProvider',
function( $compileProvider )
{
$compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|file|blob|cdvfile):|data:image\//);
}
]);

importbillsDirectives.directive('dropZone', function () {
    return {
        scope: {
            action: "@",
            file : '=',
            fileName : '='            
        },
        link: function (scope, element, attrs) {
            console.log("Creating dropzone");

            // Autoprocess the form
            if (scope.autoProcess != null && scope.autoProcess == "false") {
                scope.autoProcess = false;
            } else {
                scope.autoProcess = true;
            }

            // Max file size
            if (scope.dataMax == null) {
                scope.dataMax = Dropzone.prototype.defaultOptions.maxFilesize;
            } else {
                scope.dataMax = parseInt(scope.dataMax);
            }

            // Message for the uploading
            if (scope.message == null) {
                scope.message = Dropzone.prototype.defaultOptions.dictDefaultMessage;
            }

            element.dropzone({
                url: "http://localhost:8090/importbillservices/uploadfile",
                maxFilesize: scope.dataMax,
                paramName: "file",
                acceptedFiles: scope.mimetypes,
                maxThumbnailFilesize: scope.dataMax,
                dictDefaultMessage: scope.message,
                autoProcessQueue: scope.autoProcess,
                success: function (file, response) {
                	console.log(file);
                	console.log(response);
                    if (scope.callBack != null) {
                        scope.callBack({response: response});
                    }
                }
            });
        }
    }
});


importbillsDirectives.directive('datepicker', function() {
	  return {
		    require: 'ngModel',
		    link: function(scope, el, attr, ngModel) {
		      $(el).datepicker({
		        onSelect: function(dateText) {
		          scope.$apply(function() {
		            ngModel.$setViewValue(dateText);
		          });
		        }
		      });
		    }
		  };
});

importbillsDirectives.directive('menuDirective',  function(){
	return {
		restrict:'E',
		templateUrl: 'includes/menu.html'
	}
});

