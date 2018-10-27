$(document).ready(function() {

  var theLightIsOn = false;
  var timeoutId;

  function onButtonClick(additionalOnCallback) {
    return function() {
      if (theLightIsOn) {
        turnTheLightOff();
      } else {
        turnTheLightOn();
        if (additionalOnCallback) {
          additionalOnCallback();
        }
      }
    }
  }

  function turnTheLightOff() {
    actionRequest('TURN_OFF', false);
  }

  function turnTheLightOn() {
    actionRequest('TURN_ON', true);
  }

  function turnOffIn5Minutes() {
    timeoutId = setTimeout(turnTheLightOff, 5000);
  }

  function actionRequest(action, isLightOnAfter) {
    $.post({
      url: "/api/front-light/actions",
      data: JSON.stringify({
        action: action
      }),
      contentType: 'application/json',
    }).done(function() {
      theLightIsOn = isLightOnAfter;
      updateButtons();
    });
  }

  function updateButtons() {
    if (theLightIsOn) {
      $("a").removeClass("is-danger").addClass("is-success");
      $("#on-off").text("Turn off")
      $('#front-light-5-min-on').attr('disabled', true);
      $('#front-light-5-min-on').off('click');
    } else {
      $("a").removeClass("is-success").addClass("is-danger");
      $("#on-off").text("Turn on")
      $("#front-light-5-min-on").click(onButtonClick(turnOffIn5Minutes));
      $('#front-light-5-min-on').attr('disabled', false);
      clearTimeout(timeoutId);
    }
  }

  $("#front-light-on-off").click(onButtonClick());
  $("#front-light-5-min-on").click(onButtonClick(turnOffIn5Minutes));

});
