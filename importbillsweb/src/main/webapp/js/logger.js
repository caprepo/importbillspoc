(function(){
	angular.module('logger',[])
	.factory('log', log);
	
	function log($log){
		var service = {
			error: error,
			warn: warn,
			info: info,
			
			log: $log
		};
		return service;
		
		function error(message, data){
			$log.error("Error: "+message, data);
		}
		function warn(message, data){
			$log.warn("Warn: "+message, data);
		}
		function info(message, data){
			$log.info("Info: "+message, data);
		}
	}
	
})(this.angular);