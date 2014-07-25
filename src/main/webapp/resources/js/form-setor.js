$(document).ready(function() {
    
});

$('#aptoDoacao').click(function() {
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
    submitHandler: function(form) {
        form.submit();
    }
});
