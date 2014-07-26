$(function () {
    var $wizard = $('#fuelux-wizard'),
        $btnPrev = $('.wizard-actions .btn-prev'),
        $btnNext = $('.wizard-actions .btn-next'),
        $btnFinish = $(".wizard-actions .btn-finish");

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
            $btnPrev.attr("disabled", "disabled");
        } else if (step.step === 2) {
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
});

$(".wizard-actions .btn-finish").click(function () {
    var processo = $("#processo");
    if (processo.valid()) {
        processo.submit();
    }
});

$(document).ready(function () {
    $('.data').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('.tel').inputmask({
        mask: ["(99)9999-9999", "(99)99999-9999"]
    });
    $('.hora').inputmask('hh:mm');
    $('.cpf').inputmask('999.999.999-99');
    $('.cep').mask('99999-999');
});

$('#aptoDoacao').click(function () {
    fadeComponent("obito.aptoDoacao", "", "divCausaNaoDoacao");
})

$("#form-setor").validate({
    rules: {
        'nome': {
            required: true
        }
    },
    messages: {
        'nome': {
            required: "Por favor, insira o nome"
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});

//Validando os dados do formulario
$("#processo").validate({
    ignore: [],
    errorPlacement: fieldBoxValidatorError,
    success: fieldBoxValidatorSuccess,
    rules: {
        //Notificacao de Obito aba Paciente
        'obito.paciente.nome': {
            required: true
        },
        dataNascimento: {
            required: true
        },
        dataInternacao: {
            required: true
        },
        'obito.paciente.documentoSocial': {
            required: true
        },
        'obito.paciente.telefone.numero': {
            required: true,
            rangelength: [13, 14]
        },
        'obito.paciente.sexo': {
            required: true
        },
        'obito.paciente.numeroSUS': {
            required: true
        },
        'obito.paciente.profissao': {
            required: true
        },
        'obito.paciente.nacionalidade': {
            required: true
        },
        'obito.paciente.numeroProntuario': {
            required: true
        },
        'obito.paciente.endereco.cidade': {
            required: true
        },
        'obito.paciente.endereco.bairro': {
            required: true
        },
        'obito.paciente.endereco.cep': {
            required: true,
            minlength: 9
        },
        // Notificacao de Obito aba Obito
        dataObito: {
            required: true
        },
        horarioObito: {
            required: true,
            minlength: 5
        },
        'obito.setor': {
            required: true
        },
        'obito.primeiraCausaMortis': {
            required: true
        },
        'obito.segundaCausaMortis': {
            required: true
        },
        'obito.aptoDoacao': {
            required: true
        }
    },
    messages: {
        //Notificacao de Obito aba Paciente
        'obito.paciente.nome': {
            required: "Por favor, insira o nome do paciente"
        },
        dataNascimento: {
            required: "Por favor, insira a data de nascimento do paciente"
        },
        dataInternacao: {
            required: "Por favor, insira a data de internação do paciente"
        },
        'obito.paciente.documentoSocial': {
            required: "Por favor, insira um documento social do paciente"
        },
        'obito.paciente.telefone.numero': {
            required: "Por favor, insira um telefone do paciente",
            rangelength: "O telefone de ter entre 10 e 11 dígitos"
        },
        'obito.paciente.sexo': {
            required: "Por favor, selecione o sexo do paciente"
        },
        'obito.paciente.numeroSUS': {
            required: "Por favor, insira o número de SUS do paciente"
        },
        'obito.paciente.profissao': {
            required: "Por favor, insira a profissão do paciente"
        },
        'obito.paciente.nacionalidade': {
            required: "Por favor, insira a nascionalidade do paciente"
        },
        'obito.paciente.numeroProntuario': {
            required: "Por favor, insira o número do prontuário do paciente"
        },
        'obito.paciente.endereco.cidade': {
            required: "Por favor, insira a cidade do paciente"
        },
        'obito.paciente.endereco.bairro': {
            required: "Por favor, insira o bairro do paciente"
        },
        'obito.paciente.endereco.cep': {
            required: "Por favor, insira o cep do paciente"
        },
        //Notificacao de Obito aba Obito
        dataObito: {
            required: "Por favor, insira a data do óbito do paciente"
        },
        horarioObito: {
            required: "Por favor, insira o horário do óbito do paciente"
        },
        'obito.setor': {
            required: "Por favor, insira setor onde ocorreu o óbito"
        },
        'obito.primeiraCausaMortis': {
            required: "Por favor, insira primeiro motivo do obito"
        },
        'obito.segundaCausaMortis': {
            required: "Por favor, insira segundo motivo do obito"
        },
        'obito.aptoDoacao': {
            required: "Por favor, selecione o estado do paciente"
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});
