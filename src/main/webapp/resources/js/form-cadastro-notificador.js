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
        'documentoSocial.documento':{
            required: true
        },
        'documentoSocial.tipoDocumentoComFoto':{
            required: true
        },
        'telefone.numero':{
            required: true
        },
        'senha':{
            required:true,
            minlength: 5
        },
        'confirmar.senha':{
            required: true,
            equalTo: "#senha",
            minlength: 5
        }
    },
    messages: {
        'nome': {
            required: "Por favor, insira o nome"
        },
        'cpf': {
            required: "Por favor, insira o CPF"
        },
        'documentoSocial.documento': {
            required: "Por favor, insira o Documento Social"
        },
        'documentoSocial.tipoDocumentoComFoto':{
            required: "Por favor, insira o tipo do Documento Social"
        },
        'telefone.numero': {
            required: "Por favor, insira o telefone"
        },
        'senha':{
            required: "Por favor informe a senha",
            minlength: "A senha deve conter no mínimo 5 caracteres."
        },
        'confirmar.senha': {
            required: "O campo confirmação de senha é obrigatório.",
            equalTo: "O campo confirmação de senha deve ser identico ao campo senha.",
            minlength: "A senha deve conter no mínimo 5 caracteres."
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});


$(".select2").select2({
    placeholder: "Escolha as Instituições"
});