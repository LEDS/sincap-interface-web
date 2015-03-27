jQuery.validator.addMethod("isValid", function (value, element) {
    var dataIni = $('#datIni').val();
    var dataFim = $('#datFim').val();

    return Date.parse(dataIni) <= Date.parse(dataFim);

}, "* Data final deve ser maior do que a inicial.");

$(".select2").select2({
    placeholder: "Escolha as Instituições"
});


$("#total-docao-instituicao").validate({
    rules: {
        'datIni': {
            required: true
        },
        datFim:{
            required: true,
            isValid: true
        }
    },
    messages: {
        'datIni': {
            required: "Por favor, insira a data inicial."
        },
        'datFim': {
            required: "Por favor, insira a data final."
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});



