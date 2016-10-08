var app = angular.module('myApp', []);

//grid width and height
var bw = 600;
var bh = 600;
//padding around grid
var p = 10;
var r = 6;
var delta = 40;

var color = '';
var user = '';

function getColor() {
 return color;
}

function setColor(string) {
  if(string=="P1")
  {
    this.color = "red";
  }
  else
  {
    this.color = "blue";
  }

}

function point(x, y, context, color){
  context.beginPath();
  context.arc(x, y, r, 0, 2 * Math.PI, true);
  context.fillStyle = color;
  context.fill();
}

function drawPoints(context) {
  for (var x = 0; x < bw; x += delta) {
    for (var y = 0; y < bh; y += delta) {
      point(x + p, y + p, context);
    }
  }
}

function isClickOnPoint(x,y)
{
  xn=Math.floor((x+r)/delta)*delta;
  yn=Math.floor((y+r)/delta)*delta;
  if((xn-r+p<=x && xn+r+p>=x) && (yn-r+p<=y && y<=yn+r+p))
  {
    console.log(true);
    return true;
  }
  console.log('xn='+xn+'; yn='+yn);
  console.log(false);
  return false;
}


function getColorByUser(owner) {
  if(owner == "P1")
  {
    return "red";
  }
  return "blue";
}
function reInitField(allData, context) {
  var gameField = allData.gameField;
  var fieldPoint = gameField.fieldPoints;
  for (var i = 0; i < fieldPoint.length; i++) {
    for (var j = 0; j < fieldPoint.length; j++) {
      var val = fieldPoint[i][j].value;
      if (val == "F") {
        point(i * delta + p, j * delta + p, context, "black")
      }
      else if (val == "P1") {
        point(i * delta + p, j * delta + p, context, "red")
      }
      else if (val == "P2") {
        point(i * delta + p, j * delta + p, context, "blue")
      }
      else if (val == "B1" || val == "B2" || val == "B") {
        point(i * delta + p, j * delta + p, context, "grey")
      }
      else if (val == "C1") {
        point(i * delta + p, j * delta + p, context, "yellow")
      }
      else if (val == "C2") {
        point(i * delta + p, j * delta + p, context, "green")
      }
    }
  }
}
app.service("FieldService", function($q, $timeout) {

  var service = {}, listener = $q.defer(), socket = {
    client: null,
    stomp: null
  };

  service.RECONNECT_TIMEOUT = 30000;
  service.SOCKET_URL = "/dots";
  service.GAME_TOPIC = "/field/action";
  service.GAME_BROKER = "/app/dots";

  service.receive = function() {
    return listener.promise;
  };

  service.send = function (commands, user, x, y, dotValues) {
    var dotDto = {x: x,
      y: y,
      dotValues: dotValues};
    socket.stomp.send(service.GAME_BROKER, {
      priority: 9
    }, JSON.stringify({
      manageCommands: commands,
      user: user,
      dotDTO: dotDto
    }));
  };

  var reconnect = function() {
    $timeout(function() {
      initialize();
    }, this.RECONNECT_TIMEOUT);
  };

  var globalHandler = function(data) {
    var context = $('#canvas')[0].getContext("2d");

    var allData = JSON.parse(data);
    reInitField(allData, context);
    if(allData.free != true) {
      console.log("Dot is not free");
      return;
    }
    if(allData.finish)
    {
      if(allData.scorePlayer1 == allData.scorePlayer2)
      {
        alert("DEAD HEAT");
      }
      else {
        alert("The game is over! The winner is " +
        allData.scorePlayer1 > allData.scorePlayer2 ? "PLAYER 1" : "PLAYER 2");
      }
      return;
    }

    if(allData.clear) {
      context.clearRect(0, 0, bw, bh);
      location.reload();
    }

    var varCycles = allData.allCyclesToDraw;
    varCycles.forEach(function(cycle) {
      context.beginPath();
      context.moveTo(0.5 + cycle[0].x*delta+p, cycle[0].y*delta+p);
      cycle.forEach(function(item, i) {
        console.log( i + ": " + item.x + ", " + item.y);
        context.lineTo(0.5 + item.x*delta + p, item.y*delta + p);
        context.moveTo(0.5 + item.x*delta + p, item.y*delta+p);
        context.lineWidth = 2;
        context.strokeStyle = getColorByUser(item.dotValues.value);
        context.stroke();
      });
      context.lineTo(0.5 + cycle[0].x*delta+p, cycle[0].y*delta+p);
      context.lineWidth = 2;
      context.strokeStyle = getColorByUser(cycle[0].dotValues.value);
      context.stroke();
    });

    var score = $('#score')[0];
    score.textContent=allData.scorePlayer1 + " : " + allData.scorePlayer2;

  };

  var startListener = function() {
    socket.stomp.subscribe(service.GAME_TOPIC, function(data) {
      // listener.notify(globalHandler(data.body));
      globalHandler(data.body);
    });
  };

  var initialize = function() {
    socket.client = new SockJS(service.SOCKET_URL);
    socket.stomp = Stomp.over(socket.client);
    socket.stomp.connect({}, startListener);
    socket.stomp.onclose = reconnect;
  };

  initialize();
  return service;
});

app.controller('myCtrl', function ($scope, FieldService) {

  $scope.context = $('#canvas')[0].getContext("2d");
  $scope.doClick = function(e){
    console.log(e.clientX +' :  '+e.clientY);
    var context = $('#canvas')[0].getContext("2d");
    if(isClickOnPoint(e.clientX, e.clientY, $scope.context)) {
      xn=Math.floor((e.clientX+r)/delta);
      yn=Math.floor((e.clientY+r)/delta);
      FieldService.send(["SET_DOT", "GET_FIELD_STATE", "GET_SCORE"], "player", xn,yn, user);
    }
  };
  $scope.drawBoard = function () {
    var context = $scope.context;
    for (var x = 0; x < bw; x += delta) {
      context.moveTo(0.5 + x + p, p);
      context.lineTo(0.5 + x + p, bh + p-delta);
    }

    for (var x = 0; x < bh; x += delta) {
      context.moveTo(p, 0.5 + x + p);
      context.lineTo(bw + p-delta, 0.5 + x + p);
    }

    context.strokeStyle = "black";
    context.stroke();
    drawPoints(context);

  };
});

app.controller('btnController', function ($scope, FieldService) {
  $scope.ButtonClick = function (string) {
    if(string == "Finish")
    {
      $scope.Message="The game is over!";
      FieldService.send(["GET_SCORE","FINISH_GAME", "GET_FIELD_STATE"]);
    }
    if(string == "New game")
    {
      $scope.Message="New game start!";
      FieldService.send(["NEW_GAME", "GET_SCORE", "GET_FIELD_STATE"]);
    }
    else {
      user = string;
      setColor(string);
      $scope.Message = "Button clicked!";
      $scope.Color = "Your color is " + getColor(string);
    }
}});


