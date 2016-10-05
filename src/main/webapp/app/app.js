var app = angular.module('myApp', []);

//grid width and height
var bw = 400;
var bh = 400;
//padding around grid
var p = 0;
var r = 5;
var delta = 40;
//size of canvas
var cw = bw + (p * 2) + 1;
var ch = bh + (p * 2) + 1;
var color = '';
var user = '';

function getColor() {
  if(color != null) {
    return color;
  }
  else {
    return color = "red";
  }
}

function setColor(color) {
  this.color = color;
}

function point(x, y, context, color){
  context.beginPath();
  context.arc(x, y, r, 0, 2 * Math.PI, true);
  context.fillStyle = color;
  context.fill();
}

function drawPoints(context) {
  for (var x = 0; x <= bw; x += delta) {
    for (var y = 0; y <= bh; y += delta) {
      //var pointCanvas = $('<canvas/>').appendTo(canvas);

      // var pointContext = canvas.get(0).getContext("2d");
      point(x + p, y + p, context);
    }
  }
}

function isClickOnPoint(x,y, context)
{
  xn=Math.floor((x+r)/delta)*delta;
  yn=Math.floor((y+r)/delta)*delta;
  if((xn-r<=x && xn+r>=x) && (yn-r<=y && y<=yn+r))
  {
    point(xn, yn, context, getColor());
    console.log(true);
    return true;
  }
  console.log('xn='+xn+'; yn='+yn);
  console.log(false);
  return false;
}


app.service("ChatService", function($q, $timeout) {

  var service = {}, listener = $q.defer(), socket = {
    client: null,
    stomp: null
  }, messageIds = [];

  service.RECONNECT_TIMEOUT = 30000;
  service.SOCKET_URL = "/spring-ng-dots/chat";
  service.CHAT_TOPIC = "/topic/message";
  service.CHAT_BROKER = "/app/chat";

  service.receive = function() {
    return listener.promise;
  };

  service.send = function(x, y, dotValues) {
    socket.stomp.send(service.CHAT_BROKER, {
      priority: 9
    }, JSON.stringify({
      x: x,
      y: y,
      dotValues: dotValues
    }));
    // messageIds.push(id);
  };

  var reconnect = function() {
    $timeout(function() {
      initialize();
    }, this.RECONNECT_TIMEOUT);
  };

  var globalHandler = function(data) {
    //1 занята ли точка
    //2 перерисовка точек
    //2.1 блокированные точки проставлять серым
    //3 рисование циклов


    var context = $('#canvas')[0].getContext("2d");
    // var varCycles = JSON.parse(data), out = {};

    var allData = JSON.parse(data);
    if(allData.free != true) {
      console.log("Dot is not free");
      return;
    }

    var gameField = allData.gameField;
    var fieldPoint = gameField.fieldPoints;

    var fieldPoint = gameField.fieldPoints;
    fieldPoint.forEach(function(points)
    {
      points.forEach(function(point) {
        console.log(point.value);
      })
    })
    var count = 0;
    for(var i=0; i<fieldPoint.length; i++)
    {
      for (var j=0; j<fieldPoint.length; j++)
      {
        count++;
        console.log(count);
        var val = fieldPoint[i][j].value;
        if (val == "F")
        {
          point(i*delta+p, j*delta+p, context, "black")
        }
        if (val == "P1")
        {
          point(i*delta+p, j*delta+p, context, "red")
        }
        if (val == "P2")
        {
          point(i*delta+p, j*delta+p, context, "blue")
        }
        if (val == "B1" || val == "B2")
        {
          point(i*delta+p, j*delta+p, context, "grey")
        }
        if (val == "C1")
        {
          point(i*delta+p, j*delta+p, context, "yellow")
        }
        if (val == "C2")
        {
          point(i*delta+p, j*delta+p, context, "green")
        }
      }
    }

    var varCycles = allData.allCyclesToDraw;
    varCycles.forEach(function(cycle) {
      context.moveTo(cycle[0].x*delta+p, cycle[0].y*delta+p);
      cycle.forEach(function(item, i) {
        console.log( i + ": " + item.x + ", " + item.y);
        context.lineTo(0.5 + item.x*delta + p, item.y*delta + p);
        context.moveTo(0.5 + item.x*delta + p, item.y*delta+p);
        context.strokeStyle = "red";
        context.stroke();
      })});
    out.m1essage = varCycles.message;
    out.time = new Date(varCycles.time);
    if (_.contains(messageIds, varCycles.id)) {
      out.self = true;
      messageIds = _.remove(messageIds, varCycles.id);
    }
    return out;
  };

  var startListener = function() {
    socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
      listener.notify(globalHandler(data.body));
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


app.controller("ChatCtrl", function($scope, ChatService) {
  $scope.messages = [];
  $scope.message = "";
  $scope.max = 140;

  $scope.addMessage = function() {
    ChatService.send($scope.message);
    $scope.message = "";
  };

  ChatService.receive().then(null, null, function(message) {
    $scope.messages.push(message);
  });
});

app.controller('myCtrl', function ($scope, ChatService) {

  console.log("HI");
  $scope.context = $('#canvas')[0].getContext("2d");
  $scope.doClick = function(e){
    console.log(e.clientX +' :  '+e.clientY);
    var context = $('#canvas')[0].getContext("2d");
    if(isClickOnPoint(e.clientX, e.clientY, $scope.context)) {
      xn=Math.floor((e.clientX+r)/delta);
      yn=Math.floor((e.clientY+r)/delta);
      ChatService.send(xn,yn, user);
    }
  };
  $scope.drawBoard = function () {
    var context = $scope.context;
    for (var x = 0; x <= bw; x += delta) {
      context.moveTo(0.5 + x + p, p);
      context.lineTo(0.5 + x + p, bh + p);
    }

    for (var x = 0; x <= bh; x += delta) {
      context.moveTo(p, 0.5 + x + p);
      context.lineTo(bw + p, 0.5 + x + p);
    }

    context.strokeStyle = "black";
    context.stroke();
    drawPoints(context);

  };
});


app.controller('btnController', function ($scope) {
  $scope.ButtonClick = function (message) {

    if(message === "P1" )
    {
      setColor("red");
      $scope.Message = "Button clicked." + message;
      $scope.Color = "Color is " + getColor();
      user = "P1";
    }
    else
    {
      setColor("blue");
      $scope.Color = "Color is " + getColor();
      user = "P2";
    }
  }
});


