(function(window){
	if(!window.ddzj)window.ddzj = {};
	var Tool = function(){
		/*
		 * 判断对象是否为空
		 */
		this.isNull = function(obj){
			if(obj === null || obj === undefined){
				return true;
			}
			return false;
		}
		
		
		this.toJson = function(obj){
			var jsonData = JSON.stringify(obj);
			
			return jsonData;
		}
	}
	window.ddzj.tool = new Tool();
})(window);
