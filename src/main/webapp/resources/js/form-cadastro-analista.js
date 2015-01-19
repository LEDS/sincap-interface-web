function definirMascaras(){
    $('#telefone-numero').inputmask({
        mask: ["(99)9999-9999", "(99)99999-9999"]
    });
    $('#cpf').inputmask('999.999.999-99');
    $('#endereco-cep').mask('99999-999');
}

definirMascaras();

$("#form-cadastro-analista").validate({
    rules: {
        'nome': {
            required: true
        },
        'cpf':{
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
            required: "Por favor informe a senha"
        }
    },
    submitHandler: function (form) {
        form.submit();
    }
});
