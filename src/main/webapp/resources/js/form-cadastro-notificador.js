function definirMascaras(){
    $('#telefone-numero').inputmask({
        mask: ["(99)9999-9999", "(99)99999-9999"]
    });
    $('#cpf').inputmask('999.999.999-99');
    $('#endereco-cep').mask('99999-999');
}

definirMascaras();

$("#form-cadastro-notificador").validate({
    rules: {
        'nome': {
            required: true

        },
        cpf:{
            required: true
        },
        documentoSocial:{
            required: true
        },
        'telefone.numero':{
            required: true
        },
        'senha':{
            required:true
        }
    },
    messages: {
        'nome': {
            required: "Por favor, insira o nome"
        },
        'cpf': {
            required: "Por favor, insira o CPF"
        },
        'documentoSocial': {
            required: "Por favor, insira o Documento Social"
        },
        'telefone.numero': {
            required: "Por favor, insira o telefone"
        },
        'senha':{
            required: "Por favor informe a senha"
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});


$(".select2").select2({
    placeholder: "Escolha as Instituições"
});