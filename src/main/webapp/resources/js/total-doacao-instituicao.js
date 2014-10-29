$(document).ready(function () {
    $('#datIni').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('#datFim').inputmask("dd/mm/yyyy", {placeholder: "_"});
});


$(".select2").select2({
    placeholder: "Escolha as Instituições"
});

$("#total-docao-instituicao").validate({
    rules: {
        'datIni': {
            required: true
        },
        datFim:{
            required: true
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