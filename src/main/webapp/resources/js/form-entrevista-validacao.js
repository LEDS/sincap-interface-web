(function () {
    var regras = {
        'entrevista.dataCadastro': {
            required: true
        },
        'divEntrevistaNaoRealizada': {
            required: true
        },
        'problemasEstruturais': {
            required: true
        },
        'codigo': {
            required: true
        },
        'recusaFamiliar': {
            required: true
        },
        'dataEntrevista': {
            required: true
        },
        'horaEntrevista': {
            required: true
        },
        'obito.paciente.nome': {
            required: true
        },
        'obito.paciente.profissao': {
            required: true
        },
        'obito.paciente.religiao': {
            required: true
        },
        'obito.paciente.nacionalidade': {
            required: true
        },
        'obito.paciente.nomeMae': {
            required: true
        },
        'obito.paciente.endereco.estado': {
            required: true
        },
        'obito.paciente.endereco.cidade': {
            required: true
        },
        'obito.paciente.endereco.bairro': {
            required: true
        },
        'obito.paciente.endereco.cep': {
            required: true
        },
        'obito.paciente.endereco.logradouro': {
            required: true
        },
        'obito.paciente.endereco.numero': {
            required: true
        },
        'obito.paciente.dataInternacao': {
            required: true
        },
        'obito.paciente.dataNascimento': {
            required: true
        },
        'obito.paciente.numeroProntuario': {
            required: true
        },
        'obito.paciente.numeroSUS': {
            required: true
        },
        'obito.paciente.estadoCivil': {
            required: true
        },
        'obito.paciente.documentoSocial.tipoDocumentoComFoto': {
            required: true
        },
        'obito.paciente.documentoSocial.documento': {
            required: true
        },
        'entrevista.responsavel.nome': {
            required: true
        },
        'entrevista.responsavel.dataNascimento': {
            required: true
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
        'entrevista.responsavel.endereco.logradouro': {
            required: true
        },
        'entrevista.responsavel.endereco.numero': {
            required: true
        },
        'entrevista.responsavel2.nome': {
            required: true
        },
        'entrevista.responsavel2.dataNascimento': {
            required: true
        },
        'entrevista.responsavel2.documentoSocial.documento': {
            required: true
        },
        'entrevista.responsavel2.documentoSocial.tipoDocumentoComFoto': {
            required: true
        },
        'entrevista.responsavel2.parentesco': {
            required: true
        },
        'entrevista.responsavel2.estadoCivil': {
            required: true
        },
        'entrevista.responsavel2.telefone.numero': {
            required: true
        },
        'entrevista.responsavel2.telefone2.numero': {
            required: true
        },
        'entrevista.responsavel2.profissao': {
            required: true
        },
        'entrevista.responsavel2.religiao': {
            required: true
        },
        'entrevista.responsavel2.grauEscolaridade': {
            required: true
        },
        'entrevista.responsavel2.endereco.cep': {
            required: true
        },
        'entrevista.responsavel2.nacionalidade': {
            required: true
        },
        'entrevista.responsavel2.endereco.estado': {
            required: true
        },
        'entrevista.responsavel2.endereco.cidade': {
            required: true
        },
        'entrevista.responsavel2.endereco.bairro': {
            required: true
        },
        'entrevista.responsavel2.endereco.logradouro': {
            required: true
        },
        'entrevista.responsavel2.endereco.numero': {
            required: true
        },
        'entrevista.testemunha1.nome': {
            required: true
        },
        'entrevista.testemunha1.documentoSocial.tipoDocumentoComFoto': {
            required: true
        },
        'entrevista.testemunha1.documentoSocial.documento': {
            required: true
        },
        'entrevista.testemunha2.nome': {
            required: true
        },
        'entrevista.testemunha2.documentoSocial.tipoDocumentoComFoto': {
            required: true
        },
        'entrevista.testemunha2.documentoSocial.documento': {
            required: true
        }
    };

    var mensagens = {
        entrevistaRealizada: {
            required: "Por favor, selecione uma opcao"
        },
        problemasEstruturais: {
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
            required: "Por favor, selecione uma Recusa Familiar"
        },
        'obito.paciente.profissao': {
            required: "Por favor, informe a profissão."
        },
        'obito.paciente.religiao': {
            required: "Por favor, informe a religião."
        },
        'obito.paciente.nacionalidade': {
            required: "Por favor, informe a nacionalidade."
        },
        'obito.paciente.nomeMae': {
            required: "Por favor, informe o nome da mãe."
        },
        'obito.paciente.endereco.estado': {
            required: "Por favor, informe o estado do Paciente."
        },
        'obito.paciente.endereco.cidade': {
            required: "Por favor, informe a cidade do Paciente."
        },
        'obito.paciente.endereco.bairro': {
            required: "Por favor, informe o bairro do Paciente."
        },
        'obito.paciente.endereco.cep': {
            required: "Por favor, informe o cep do Paciente."
        },
        'obito.paciente.endereco.logradouro': {
            required: "Por favor, informe o logradouro do Paciente."
        },
        'obito.paciente.endereco.numero': {
            required: "Por favor, informe o numero do Paciente."
        },
        'obito.paciente.datainternacao': {
            required: "Por favor, informe a data de internação."
        },
        'obito.paciente.dataNascimento': {
            required: "Por favor, informe a data de nascimento."
        },
        'obito.paciente.numeroProntuario': {
            required: "Por favor, informe o número de prontuário."
        },
        'obito.paciente.numeroSUS': {
            required: "Por favor, informe o número do SUS."
        },
        'obito.paciente.sexo': {
            required: "Por favor, informe o número do sexo."
        },
        'obito.paciente.estadoCivil': {
            required: "Por favor, informe o estado civil."
        },
        'obito.paciente.documentoSocial.documento': {
            required: "Por favor, informe o documento social."
        },
        'obito.paciente.documentoSocial.tipoDocumentoComFoto': {
            required: "Por favor, informe o tipo de documento."
        },
        //Dados do Responsavel Legal
        'entrevista.responsavel.nome': {
            required: "Por favor, insira o nome do responsavel"
        },
        'entrevista.responsavel.dataNascimento': {
            required: "Por favor, insira a data de nascimento"
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
            required: "Por favor, insira o telefone 1 do responsavel"
        },
        'entrevista.responsavel.telefone2.numero': {
            required: "Por favor, insira o telefone 2 do responsavel"
        },

        'entrevista.responsavel.sexo': {
            required: "Por favor, insira o sexo do responsavel"
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
        'entrevista.responsavel.endereco.logradouro': {
            required: "Por favor, insira o logradouro"
        },

        'entrevista.responsavel.endereco.numero': {
            required: "Por favor, insira o número"
        },

        //Dados do 2º Responsavel Legal
        'entrevista.responsavel2.nome': {
            required: "Por favor, insira o nome 2º do responsavel"
        },
        'entrevista.responsavel2.dataNascimento': {
            required: "Por favor, insira a data de nascimento"
        },
        'entrevista.responsavel2.documentoSocial.documento': {
            required: "Por favor, insira o número do documento com foto"
        },
        'entrevista.responsavel2.documentoSocial.tipoDocumentoComFoto': {
            required: "Por favor, insira o tipo do documento com foto"
        },
        'entrevista.responsavel2.parentesco': {
            required: "Por favor, insira o parentesco do 2º responsavel"
        },
        'entrevista.responsavel2.estadoCivil': {
            required: "Por favor, insira o estao civil do 2º responsavel"
        },
        'entrevista.responsavel2.telefone.numero': {
            required: "Por favor, insira o telefone1 do 2º responsavel"
        },
        'entrevista.responsavel2.telefone2.numero': {
            required: "Por favor, insira o telefone2 do 2º responsavel"
        },

        'entrevista.responsavel2.sexo': {
            required: "Por favor, insira o sexo do 2º responsavel"
        },
        'entrevista.responsavel2.profissao': {
            required: "Por favor, insira a profissao do 2º responsavel"
        },
        'entrevista.responsavel2.religiao': {
            required: "Por favor, insira a religiao do 2º responsavel"
        },
        'entrevista.responsavel2.grauEscolaridade': {
            required: "Por favor, insira o grau de escolaridade do 2º responsavel"
        },
        'entrevista.responsavel2.endereco.cep': {
            required: "Por favor, insira o cep do 2º responsavel"
        },
        'entrevista.responsavel2.nacionalidade': {
            required: "Por favor, insira a nacionalidade do 2º responsavel"
        },
        'entrevista.responsavel2.endereco.estado': {
            required: "Por favor, selecione o uf do 2º responsavel"
        },
        'entrevista.responsavel2.endereco.cidade': {
            required: "Por favor, selecione a cidade do 2º responsavel"
        },
        'entrevista.responsavel2-endereco.bairro': {
            required: "Por favor, selecione o bairro do 2º responsavel"
        },
        'entrevista.responsavel2.endereco.logradouro': {
            required: "Por favor, insira o logradouro do 2º responsavel"
        },

        'entrevista.responsavel2.endereco.numero': {
            required: "Por favor, insira o número do 2º responsavel"
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
            required: "Por favor, insira o nome da 2º testemunha"
        },
        'entrevista.testemunha2.documentoSocial.documento': {
            required: "Por favor, insira o número do documento com foto"
        },
        'entrevista.testemunha2.documentoSocial.tipoDocumentoComFoto': {
            required: "Por favor, insira o tipo do documento com foto"
        }
    };

    $(document).ready(function () {
        $("#notifEntrevista").validate({
            errorPlacement: fieldBoxValidatorError,
            success: fieldBoxValidatorSuccess,
            rules: regras,
            messages: mensagens,
            submitHandler: function (form) {
                form.submit();
            }
        });
    });
}());