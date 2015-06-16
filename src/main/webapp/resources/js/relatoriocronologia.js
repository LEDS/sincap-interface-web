jQuery.validator.addMethod("isValid", function (value, element) {
    var dataInicial = $('#dataInicial').val();
    var dataFinal = $('#dataFinal').val();

    return buildDateBrEn(dataInicial) <= buildDateBrEn(dataFinal);
}, "* Data final deve ser maior do que a inicial.");



$(document).ready(function () {
    $('#dataInicial').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('#dataFinal').inputmask("dd/mm/yyyy", {placeholder: "_"});
});




$("#possibilidade-doacao-tecido").validate({
    rules: {
        'dataInicial': {
            required: true
        },
        dataFinal:{
            required: true,
            isValid: true
        }
    },
    messages: {
        'dataInicial': {
            required: "Por favor, insira a data inicial."
        },
        'dataFinal': {
            required: "Por favor, insira a data final."
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});



