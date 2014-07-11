$(document).ready(function() {
    $('.dataEntrevista').inputmask("dd/mm/yyyy", {placeholder: "_"});
    $('.horaEntrevista').inputmask('hh:mm');
    $('.rg').inputmask({
        mask: ['9.999.999-aa', '99.999.999-aa'],
        placeholder: "_"
    });
    $('.cep').inputmask('99999-999');
    $('.tel').inputmask({
        mask: ["(99)9999-9999", "(99)99999-9999"]
    });
    $('.cpf').inputmask('999.999.999-99');
    setRadioButtonFalse('entrevistaRealizada', function () {
        fadeComponent('entrevistaRealizada', 'divEntrevistaRealizada', 'divEntrevistaNaoRealizada');
    });
    setRadioButtonFalse('doacaoAutorizada', function () {
        fadeComponent('doacaoAutorizada', 'divDoacaoAutorizada', 'divDoacaoNaoAutorizada');
    });
});

function setRadioButtonFalse(radioButtonName, triggerFunction) {
    var radioEntrevistaRealizada = $('[name="' + radioButtonName + '"]').filter('[value=false]');
    radioEntrevistaRealizada.prop('checked', true);
    triggerFunction();
}

function changeNameAttribute(radioButtonVet, selectItem1, selectItem2, name) {
    if ($(radioButtonVet).filter('[value=true]').is(":checked")) {
        $(selectItem1).prop('name', '');
        $(selectItem2).prop('name', name);
    } else {
        $(selectItem1).prop('name', name);
        $(selectItem2).prop('name', '');
    }
}

$('[name="entrevistaRealizada"]').click(function() {
    fadeComponent('entrevistaRealizada', 'divEntrevistaRealizada', 'divEntrevistaNaoRealizada');

    changeNameAttribute(this, "#causaNaoDoacao", "#recusaFamiliar", 'causaNaoDoacao');
});

$('[name="doacaoAutorizada"]').click(function(){
    fadeComponent('doacaoAutorizada', 'divDoacaoAutorizada', 'divDoacaoNaoAutorizada');
});

$("#notifEntrevista").validate({
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
        'entrevista.responsavel.rg': {
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
        'entrevista.responsavel.endereco.logradouro': {
            required: true
        },
        'entrevista.responsavel.endereco.numero': {
            required: true
        },
        //Dados 1° testemunha
        'entrevista.testemunha1.nome': {
            required: true
        },
        'entrevista.testemunha1.cpf': {
            required: true
        },
        //Dados 2° testemunha
        'entrevista.testemunha2.nome': {
            required: true
        },
        'entrevista.testemunha2.cpf': {
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
        'entrevista.responsavel.rg': {
            required: "Por favor, insira o rg do responsavel"
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
        'entrevista.responsavel.endereco.logradouro': {
            required: "Por favor, insira o logradouro do responsavel"
        },
        'entrevista.responsavel.endereco.numero': {
            required: "Por favor, insira o numero do responsavel"
        },
        //Dados 1° testemunha
        'entrevista.testemunha1.nome': {
            required: "Por favor, insira o nome da primeira testemunha"
        },
        'entrevista.testemunha1.cpf': {
            required: "Por favor, insira o cpf da primeira testemunha"
        },
        //Dados 2° testemunha
        'entrevista.testemunha2.nome': {
            required: "Por favor, insira o nome da segunga testemunha"
        },
        'entrevista.testemunha2.cpf': {
            required: "Por favor, insira o cpf da segunda testemunha"
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});