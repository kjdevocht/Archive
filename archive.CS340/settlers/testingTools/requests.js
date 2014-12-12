function Requests() {
	
}
Requests.login = function(username, password) {
	$.ajax({
    	url:"http://localhost:8081/user/login",
    	async:false,
    	type:"POST",
    	data:{"username":username, "password":password},
    	success:function(result){
    		console.log("User Logged In: "+username);
    	}
	});
};
Requests.register = function(username, password) {
	$.ajax({
    	url:"http://localhost:8081/user/register",
    	async:false,
    	type:"POST",
    	data:{"username":username, "password":password},
    	success:function(result){
    		console.log("User Registered: "+username);
    	}
	});
};
Requests.addAI = function() {
	$.ajax({
    	url:"http://localhost:8081/game/addAI",
    	async:false,
    	type:"POST",
    	data:{"AIType": "LARGEST_ARMY"},
    	success:function(result){
    		console.log("Added AI");
    	}
	});
}
Requests.createGame = function(name) {
	var id;
	$.ajax({
    	url:"http://localhost:8081/games/create",
    	async:false,
    	type:"POST",
    	data:{
		  "randomTiles": false,
		  "randomNumbers": false,
		  "randomPorts": false,
		  "name": name
		},
    	success:function(result){
    		console.log("Game Created: "+name)
    		id = result['id']
    	}
	});
	return id;
};
Requests.joinGame = function(id, color) {
	$.ajax({
    	url:"http://localhost:8081/games/join",
    	async:false,
    	type:"POST",
    	data:{
		  "id": id,
		  "color": color,
		},
    	success:function(result){
    		console.log("Game Joined: "+id)
    	}
	});
};
// Requests.setCommand = function() {
// 	$.ajax({
//     	url:"http://localhost:8081/game/commands",
//     	async:false,
//     	type:"POST",
//     	// crossDomain: true,
//     	contentType:'json',
//     	data:JSON.stringify([
		  
// 	});
// };
Requests.loadState = function(data) {
	console.log(JSON.stringify(data))
	$.ajax({
    	url:"http://localhost:8081/game/commands",
    	async:false,
    	type:"POST",
    	contentType:'json',
    	data:JSON.stringify(data),
    	success:function(result){
    		console.log("loaded");
    	}		  
	});
}
Requests.saveCurrentState = function(name) {
	$.ajax({
    	url:"http://localhost:8081/game/commands",
    	async:false,
    	type:"GET",
    	success:function(result){
    		data = {"name":name, "commands":result}
    		console.log(data);
    		$.ajax({
		    	url:"http://localhost:8000/config",
		    	async:false,
		    	type:"POST",
		    	contentType:'json',
		    	data:JSON.stringify(data),
		    	success:function(result){
		    		console.log("Saved: "+result)
		    	}			  
			});
    	}		  
	});
}
Requests.getSavedCommands = function() {
	var commands = []
    var games = null
	$.ajax({
    	url:"http://localhost:8000/config",
    	async:false,
    	type:"GET",
    	success:function(result){
    		games = result;
    	}		  
	});
	return games;
}
Requests.makeFourPlayerGame = function() {
	Requests.register("p1", "p1");
    var id = Requests.createGame("newgame");
    Requests.joinGame(id, "red");
    Requests.register("p2", "p2");
    Requests.joinGame(id, "orange");
    Requests.register("p3", "p3");
    Requests.joinGame(id, "yellow");
    Requests.register("p4", "p4");
    Requests.joinGame(id, "blue");
}
Requests.makeOnePlayerGame = function() {
	Requests.register("p1", "p1");
    var id = Requests.createGame("newgame");
    Requests.joinGame(id, "red");

    Requests.addAI();
    Requests.addAI();
    Requests.addAI();
}