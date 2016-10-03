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
    point(xn, yn, context, 'red');
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

  var getMessage = function(data) {
    var message = JSON.parse(data), out = {};
    out.message = message.message;
    out.time = new Date(message.time);
    if (_.contains(messageIds, message.id)) {
      out.self = true;
      messageIds = _.remove(messageIds, message.id);
    }
    return out;
  };

  var startListener = function() {
    socket.stomp.subscribe(service.CHAT_TOPIC, function(data) {
      listener.notify(getMessage(data.body));
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
      ChatService.send(xn,yn, "P1");
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


