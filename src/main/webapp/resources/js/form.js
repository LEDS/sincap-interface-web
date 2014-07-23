/**
 * Created by phillipe on 21/07/14.
 */

$(document).ready(function () {
    setNames();
});

function setNames() {
    var replaceFunction = function (index) {
        var input = $(this);
        var name = input.prop("name");
        input.prop("name", name.replace(/-/g, "."));
    };

    $("input").each(replaceFunction);
    $("select").each(replaceFunction);
}

function focus(selector, next) {
    $(selector).keypress(function (event) {
        if(event.key == "Tab") {
            $(next).focus();
        }
    });
}

function definirEstilo() {
    $(".control-group > label").addClass("control-label");
    $(".control-group > input").addClass("span3 inline-input");
    $(".control-group > input").wrap("<div class='controls'></div>");
    $(".control-group > table").wrap("<div class='span3' style='padding-left: 1em'></div>");
    $(".control-group > select").wrap("<div class='span3'></div>");
    $("br + label").css("padding-bottom", "1em");
}