/**
 * Created by phillipe on 21/07/14.
 */

$(document).ready(function () {
    setNames();
    if($('input:checkbox').size() != 0){
        $('input:checkbox').uniform();
    }
    if($('input:radio').size() != 0){
        $('input:radio').uniform();
    }
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

var fieldBoxValidatorError = function (error, element) {
    var $element = $(element);

    var $fieldBox = $element.parent().parent(".field-box");
    var $parent = $element.parent();

    if (!$fieldBox.hasClass("error")) {
        $fieldBox.addClass("error");
        $parent.append("<span class='alert-msg'></span>");
        var $span = $fieldBox.find("span");
        $span.append("<i class='icon-remove-sign' />");
        $span.append(error.text());
    }
};

var fieldBoxValidatorSuccess = function (label, element) {
    var $element = $(element);

    var $parent = $element.parent().parent(".field-box");

    if ($parent.hasClass("error")) {
        $parent.removeClass("error");
        $parent.find("span").remove();
    }
};

function definirEstilo() {
    $(".control-group > label").addClass("control-label");
    $(".control-group > input").addClass("span3 inline-input");
    $(".control-group > input").wrap("<div class='controls'></div>");
    $(".control-group > table").wrap("<div class='span3' style='padding-left: 1em'></div>");
    $(".control-group > select").wrap("<div class='span3'></div>");
    $("br + label").css("padding-bottom", "1em");
}
