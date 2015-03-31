jQuery.validator.addMethod("isValid", function () {
    var dataIni = String($('#datIni').val()).split("/");
    var dataFim = String($('#datFim').val()).split("/");

    dataIni = dataIni[1] + "/" + dataIni[0] + "/" + dataIni[2];
    dataFim = dataFim[1] + "/" + dataFim[0] + "/" + dataFim[2];

    return Date.parse(String(dataIni)) <= Date.parse(String(dataFim));

}, "* Data final deve ser maior do que a inicial.");



$(document).ready(function () {
    $('#datIni').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('#datFim').inputmask("dd/mm/yyyy", {placeholder: "_"});
});


$(".select2").select2({
    placeholder: "Escolha as Instituições"
});


$("#qualificacao-recusa-familiar").validate({
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



