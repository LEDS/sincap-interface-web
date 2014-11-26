$(document).ready(function () {
    $('.data').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('.hora').inputmask('hh:mm');
    corneasNaoCaptadasFadeOut();
});

function corneasNaoCaptadasFadeOut() {
    $("#divCorneasNaoCaptadas").fadeOut();
    $("#causaNaoDoacao").prop("value", "");
}

function corneasCaptadasFadeOut() {
    $("#divCorneasCaptadas").fadeOut();
    $("#dataCaptacao").prop("value", "");
    $("#horarioCaptacao").prop("value", "");
}

$("#captacaoRealizada\\:0").click(function () {
    corneasNaoCaptadasFadeOut();
    $("#divCorneasCaptadas").fadeIn();
});

$("#captacaoRealizada\\:1").click(function () {
    $("#divCorneasNaoCaptadas").fadeIn();
    corneasCaptadasFadeOut();
});

$("#processoCaptacao").validate({
    errorPlacement: fieldBoxValidatorError,
    success: fieldBoxValidatorSuccess,
    rules:{
        'captacaoRealizada': {
            required: true
        },
        'causaNaoDoacao': {
            required: true
        },
        'dataCaptacao': {
            required: true
        },
        'horarioCaptacao': {
            required: true
        }
    },
    messages: {
        'captacaoRealizada': {
            required: "Por favor, confirme se a captação foi realizada ou não"
        },
        'causaNaoDoacao': {
            required: "Por favor, selecione o problema logístico"
        },
        'dataCaptacao': {
            required: "Por favor, informe a a data da captação"
        },
        'horarioCaptacao': {
            required: "Por favor, informe o horário da captação"
        }
    }
});