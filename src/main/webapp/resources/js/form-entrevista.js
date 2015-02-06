$(document).ready(function() {
    if(document.getElementById("entrevistaRealizada:1").checked) {
        $('#divEntrevistaRealizada').hide();
    } else {
        $('#divEntrevistaNaoRealizada').hide();
    }

    if(document.getElementById("doacaoAutorizada:1").checked) {
        $('#divDoacaoAutorizada').hide();
    } else {
        $('#divDoacaoNaoAutorizada').hide();
    }

    document.getElementById("entrevista-responsavel-telefone-numero").addEventListener("keydown", function (e) {
        if(e.key === 'Tab') {
            e.preventDefault();
            document.getElementById("entrevista-responsavel-telefone2-numero").focus();
        }
    });

    document.getElementById("entrevista-responsavel-telefone2-numero").addEventListener("keydown", function (e) {
        if(e.key === 'Tab') {
            e.preventDefault();
            document.getElementById("entrevista-responsavel-profissao").focus();
        }
    });

    $('#entrevistaRealizada\\:0').click(function () {
        $('#divEntrevistaRealizada').fadeIn();
        $('#divEntrevistaNaoRealizada').fadeOut();
    });
    $('#entrevistaRealizada\\:1').click(function () {
        $('#divEntrevistaNaoRealizada').fadeIn();
        $('#divEntrevistaRealizada').fadeOut();
    });
    $('#doacaoAutorizada\\:0').click(function () {
        $('#divDoacaoAutorizada').fadeIn();
        $('#divDoacaoNaoAutorizada').fadeOut();
    });
    $('#doacaoAutorizada\\:1').click(function () {
        $('#divDoacaoNaoAutorizada').fadeIn();
        $('#divDoacaoAutorizada').fadeOut();
    });
});

function init () {
    definirMascaras();
    definirEstilo();
}

init();

function definirMascaras() {
    $('.dataEntrevista').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('.horaEntrevista').inputmask('hh:mm');
    $('.cep').inputmask('99999-999');
    $('.tel').inputmask({
        mask: ["(99)9999-9999", "(99)99999-9999"]
    });
    $('.cpf').inputmask('999.999.999-99');
    $('.dataNascimento').inputmask("dd/mm/yyyy", {placeholder: "_"});
}

$("#notifEntrevista").validate({
    errorPlacement: fieldBoxValidatorError,
    success: fieldBoxValidatorSuccess,
    rules: {
        entrevistaRealizada: {
            required: true
        },
        doacaoAutorizada: {
            required: true
        },
        dataEntrevista: {
            required: true
        },
        horaEntrevista: {
            required: true
        },
        recusaFamiliar: {
            required: true
        },
        causaNaoDoacao: {
            required: true
        },

        //Dados do Responsavel Legal
        'entrevista.responsavel.nome': {
            required: true,
            minlength: 3
        },
        'entrevista.responsavel.dataNascimento':{
            required:true
        },
        'entrevista.responsavel.documentoSocial.documento': {
            required: true
        },
        'entrevista.responsavel.documentoSocial.tipoDocumentoComFoto': {
            required: true
        },
        'entrevista.responsavel.parentesco': {
            required: true
        },
        'entrevista.responsavel.estadoCivil': {
            required: true
        },
        'entrevista.responsavel.telefone.numero': {
            required: true
        },
        'entrevista.responsavel.telefone2.numero': {
            required: true
        },
        'entrevista.responsavel.profissao': {
            required: true
        },
        'entrevista.responsavel.religiao': {
            required: true
        },
        'entrevista.responsavel.grauEscolaridade': {
            required: true
        },
        'entrevista.responsavel.endereco.cep': {
            required: true
        },
        'entrevista.responsavel.nacionalidade': {
            required: true
        },
        'entrevista.responsavel.endereco.estado': {
            required: true
        },
        'entrevista.responsavel.endereco.cidade': {
            required: true
        },
        'entrevista.responsavel.endereco.bairro': {
            required: true
        },
        //Dados 1° testemunha
        'entrevista.testemunha1.nome': {
            required: true
        },
        'entrevista.testemunha1.documentoSocial': {
            required: true
        },
        'entrevista.testemunha1.documentoSocial.tipo': {
            required: true
        },
        //Dados 2° testemunha
        'entrevista.testemunha2.nome': {
            required: true
        },
        'entrevista.testemunha2.documentoSocial.documento': {
            required: true
        },
        'entrevista.testemunha2.documentoSocial.tipo': {
            required: true
        }
    },
    messages: {
        entrevistaRealizada: {
            required: "Por favor, selecione uma opcao"
        },
        doacaoAutorizada: {
            required: "Por favor, selecione uma opcao"
        },
        dataEntrevista: {
            required: "Por favor, insira a data de entrevista do responsavel"
        },
        horaEntrevista: {
            required: "Por favor, insira a hora da entrevista do responsavel"
        },
        causaNaoDoacao: {
            required: "Por favor, selecione  o problema estrutural do responsavel"
        },
        recusaFamiliar: {
            required: "Por favor, selecione uma recusaFamiliar"
        },
        //Dados do Responsavel Legal
        'entrevista.responsavel.nome': {
            required: "Por favor, insira o nome do responsavel"
        },
        'entrevista.responsavel.dataNascimento':{
            required:"Por favor, insira a data de nascimento"
        },
        'entrevista.responsavel.documentoSocial.documento': {
            required: "Por favor, insira o número do documento com foto"
        },
        'entrevista.responsavel.documentoSocial.tipoDocumentoComFoto': {
            required: "Por favor, insira o tipo do documento com foto"
        },
        'entrevista.responsavel.parentesco': {
            required: "Por favor, insira o parentesco do responsavel"
        },
        'entrevista.responsavel.estadoCivil': {
            required: "Por favor, insira o estao civil do responsavel"
        },
        'entrevista.responsavel.telefone.numero': {
            required: "Por favor, insira o telefone1 do responsavel"
        },
        'entrevista.responsavel.telefone2.numero': {
            required: "Por favor, insira o telefone2 do responsavel"
        },
        'entrevista.responsavel.profissao': {
            required: "Por favor, insira a profissao do responsavel"
        },
        'entrevista.responsavel.religiao': {
            required: "Por favor, insira a religiao do responsavel"
        },
        'entrevista.responsavel.grauEscolaridade': {
            required: "Por favor, insira o grau de escolaridade do responsavel"
        },
        'entrevista.responsavel.endereco.cep': {
            required: "Por favor, insira o cep do responsavel"
        },
        'entrevista.responsavel.nacionalidade': {
            required: "Por favor, insira a nacionalidade do responsavel"
        },
        'entrevista.responsavel.endereco.estado': {
            required: "Por favor, selecione o uf do responsavel"
        },
        'entrevista.responsavel.endereco.cidade': {
            required: "Por favor, selecione a cidade do responsavel"
        },
        'entrevista.responsavel.endereco.bairro': {
            required: "Por favor, selecione o bairro do responsavel"
        },
        //Dados 1° testemunha
        'entrevista.testemunha1.nome': {
            required: "Por favor, insira o nome"
        },
        'entrevista.testemunha1.documentoSocial.documento': {
            required: "Por favor, insira o número de um documento com foto"
        },
        'entrevista.testemunha1.documentoSocial.tipoDocumentoComFoto': {
            required: "Por favor, insira o tipo do documento com foto"
        },
        //Dados 2° testemunha
        'entrevista.testemunha2.nome': {
            required: "Por favor, insira o nome da segunga testemunha"
        },
        'entrevista.testemunha2.documentoSocial.documento': {
            required: "Por favor, insira o número do documento com foto"
        },
        'entrevista.testemunha2.documentoSocial.tipoDocumentoComFoto': {
            required: "Por favor, insira o tipo do documento com foto"
        }
    },
    submitHandler: function (form) {
        form.submit();
    }

});

$(function () {
    var $wizard = $('#fuelux-wizard'),
        $btnPrev = $('.wizard-actions .btn-prev'),
        $btnNext = $('.wizard-actions .btn-next'),
        $btnFinish = $(".wizard-actions .btn-finish");

    $btnPrev.hide();
    $wizard.wizard().on('finished', function (e) {
        // wizard complete code
        console.log("finish");
    }).on("changed", function (e) {
        var step = $wizard.wizard("selectedItem");
        // reset states
        $btnNext.removeAttr("disabled");
        $btnPrev.removeAttr("disabled");
        $btnNext.show();
        $btnFinish.hide();

        if (step.step === 1) {
            $btnPrev.hide();
            $btnNext.show();
            $btnFinish.hide();
        } else if (step.step === 2) {
            $btnPrev.show();
            $btnNext.show();
        } else if (step.step ===3 ) {
            $btnPrev.show();
            $btnNext.hide();
            $btnFinish.show();
        }
    });

    $btnPrev.on('click', function () {
        $wizard.wizard('previous');
    });
    $btnNext.on('click', function () {
        $wizard.wizard('next');
    });

    $(".wizard-actions .btn-finish").click(function () {
        var notifEntrevista = $("#notifEntrevista");
        if (notifEntrevista.valid()) {
            notifEntrevista.submit();
        }
    });
});