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
    }
});