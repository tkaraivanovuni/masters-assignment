var me;
    	  
function getAuthorization(){
    $.ajax({
        url: "/authorize",
    	method: "GET",
    	complete: function(data){
    		
    		switch(data.status){
    		case 200:
    			me = data.responseJSON;
    			break;
    		case 401:
    			window.location.href = "index.html";
    			break;
    		}
    				  
    	},
    	fail: function(){
    		window.location.href = "index.html";
    	}
    				  
    });
}

function logout(){
	$.ajax({
		  url: "/logUserOut",
		  method: "POST",
		  complete: function(data){
			  if(data.status == 200){
				  window.location.href = "index.html";
			  }else{
				  alert("Error!");
				  window.location.href = "index.html";
			  }
		  }
	});
}