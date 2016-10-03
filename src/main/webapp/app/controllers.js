/*
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


app.controller("myCtrl", function ($scope)
{
  console.log("HI");
  // $scope.context = $('#canvas')[0].getContext("2d");
  $scope.context = document.getElementById('canvas')[0].getContext("2d");
  $scope.doClick = function(e){
    console.log(e.clientX +' :  '+e.clientY);
    // var context = $('#canvas')[0].getContext("2d");
    isClickOnPoint(e.clientX, e.clientY, $scope.context);
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
  // var canvas = $('<canvas/>').attr({width: cw, height: ch}).appendTo('body');
  // var context = canvas.get(0).getContext("2d");
  // drawBoard(context);
});*/
