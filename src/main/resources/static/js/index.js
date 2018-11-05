$(document).ready(function() {

  var theLightIsOn = false;
  // var _5minutesAsMillis = 300000;
  var _5minutesAsMillis = 5000;

  function onButtonClick(actionTimeoutMillis) {
    return function() {
      if (theLightIsOn) {
        turnTheLightOff();
      } else {
        turnTheLightOn(actionTimeoutMillis);
      }
    }
  }

  function turnTheLightOff() {
    actionRequest('TURN_OFF');
  }

  function turnTheLightOn(actionTimeoutMillis) {
    actionRequest('TURN_ON', actionTimeoutMillis);
  }

  function actionRequest(action, actionTimeoutMillis) {
    $.post({
      url: "/api/front-light/actions",
      data: JSON.stringify({
        action: action,
        actionTimeout: actionTimeoutMillis || 0
      }),
      contentType: 'application/json',
    });
  }

  window.setInterval(function() {
    $.get('/api/front-light/status', function(data) {
      theLightIsOn = "ON" === data;
      updateButtons();
    });
  }, 500);

  function updateButtons() {
    if (theLightIsOn) {
      $("a").removeClass("is-danger").addClass("is-success");
      $("#on-off").text("Turn off")
      $('#front-light-5-min-on').attr('disabled', true);
      $('#front-light-5-min-on').off('click');
    } else {
      $("a").removeClass("is-success").addClass("is-danger");
      $("#on-off").text("Turn on")
      $('#front-light-5-min-on').off('click');
      $("#front-light-5-min-on").click(onButtonClick(_5minutesAsMillis));
      $('#front-light-5-min-on').attr('disabled', false);
    }
  }

  $("#front-light-on-off").click(onButtonClick());
  $("#front-light-5-min-on").click(onButtonClick(_5minutesAsMillis));

});
