/**
 * Created by phillipe on 21/07/14.
 */

$(document).ready(function () {
    setNames();
    $("input:checkbox, input:radio").uniform();
});

function setNames() {
    var replaceFunction = function (index) {
        var input = $(this);
        var name = input.prop("name");
        input.prop("name", name.replace(/-/g, "."));
    };

    $("input").each(replaceFunction);
    $("select").each(replaceFunction);
    $("textarea").each(replaceFunction);
}

function focus(selector, next) {
    $(selector).keypress(function (event) {
        if(event.key == "Tab") {
            $(next).focus();
        }
    });
}

function fieldBoxValidatorError(error, element) {
    var $element = $(element);

    var $parent = $element.parent(".field-box");

    if ($parent.length == 0){
        var $parent = $element.parent(".ui-select").parent(".field-box");
    }

    if (!$parent.hasClass("error")) {
        $parent.addClass("error");
        $parent.append("<span class='alert-msg'></span>");
        var $span = $parent.find("span");
        $span.append("<i class='icon-remove-sign' />");
        $span.append(error.text());
    }
}

function fieldBoxValidatorSuccess(label, element) {
    var $element = $(element);
    var $parent = $element.parent(".field-box");
    if ($parent.length == 0){
        var $parent = $element.parent(".ui-select").parent(".field-box");
    }

    if ($parent.hasClass("error")) {
        $parent.removeClass("error");
        $parent.find("span").remove();
    }
}

function definirEstilo() {
    $(".control-group > label").addClass("control-label");
    $(".control-group > input").addClass("span3 inline-input");
    $(".control-group > input").wrap("<div class='controls'></div>");
    $(".control-group > table").wrap("<div class='span3' style='padding-left: 1em'></div>");
    $(".control-group > select").wrap("<div class='span3'></div>");
    $("br + label").css("padding-bottom", "1em");
}
