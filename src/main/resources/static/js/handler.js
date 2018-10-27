$(document).ready(function() {

  var theLightIsOn = false;

  function onButtonClick() {
    return function() {
      if (theLightIsOn) {
        theLightIsOn = false;
      } else {
        theLightIsOn = true;
      }
      updateButtons();
    }
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
      $("#front-light-5-min-on").click(onButtonClick("#front-light-5-min-on"));
      $('#front-light-5-min-on').attr('disabled', false);
    }
  }

  $("#front-light-on-off").click(onButtonClick("#front-light-on-off"));
  $("#front-light-5-min-on").click(onButtonClick("#front-light-5-min-on"));



});

// TODO:
// cancel timers when off
