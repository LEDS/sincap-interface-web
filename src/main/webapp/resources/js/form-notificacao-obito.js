(function() {
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
                window.scrollTo(0,0);
                $btnPrev.hide();
                $btnPrev.attr("disabled", "disabled");
            } else if (step.step === 2) {
                window.scrollTo(0,0);
                $btnNext.hide();
                $btnPrev.show();
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

    function mergeDates() {
        var $processo = document.forms["processo"];
        var data = $processo.elements["obito.dataObito"].value;
        $('#obito-dataObito').inputmask("remove");
        $processo.elements["obito.dataObito"].value = data + " " + $processo.elements["horarioObito"].value;
        console.log($processo.elements["obito.dataObito"].value);
        $processo.submit();
    }

    $(".wizard-actions .btn-finish").click(function () {
        var processo = $("#processo");
        if (processo.valid()) {
            mergeDates();
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
        
        var tipoDocumento = document.getElementById('obito-paciente-documentoSocial-tipoDocumentoComFoto');
        tipoDocumento.addEventListener('click', function() {
            if (tipoDocumento.value === 'PNI') {
                $("#obito-paciente-documentoSocial-documento").rules('remove');
                $("#obito-paciente-nome").rules('remove');
                $("#obito-paciente-dataNascimento").rules('remove');
                $("#obito-paciente-nomeMae").rules('remove');
                $("#obito-paciente-numeroSUS").rules('remove');
            } else {
                $("#obito-paciente-documentoSocial-documento").rules('add', {required: true});
                $("#obito-paciente-nome").rules('add', {required: true});
                $("#obito-paciente-dataNascimento").rules('add', {required: true});
                $("#obito-paciente-nomeMae").rules('add', {required: true});
                $("#obito-paciente-numeroSUS").rules('add', {required: true});
            }
        });

        if(document.getElementById("obito-aptoDoacao:0").checked) {
            $("#divCausaNaoDoacao").hide();
        }

        function getIdade(){

            datIni = new Date(document.getElementById("obito-paciente-dataNascimento").value);
            datFim = new Date(document.getElementById("obito-dataObito").value);

            return (Math.floor(Math.ceil(Math.abs(datIni.getTime() - datFim.getTime()) / (1000 * 3600 * 24)) / 365.25));

        }

        function setInaptoDoacao(){
            idade = getIdade();
            if (!(idade > 2) || idade > 23){
                document.getElementById("obito-aptoDoacao:1").click();
                document.getElementById("causaNaoDoacao").value = 17;
            }
        }

        focus('#obito-paciente-telefone-numero', "#obito-paciente-sexo\\:0");
        $("#obito-paciente-endereco-logradouro").keypress(function (event) {
            if (event.key === "Tab") {
                $("#btn-next").click();
                $("#obito-dataObito").focus();
            }
        });



        var $divCausaNaoDoacao = $("#divCausaNaoDoacao");
        document.getElementById("obito-aptoDoacao:0").addEventListener("click", function () {
            $divCausaNaoDoacao.fadeOut();
        });
        document.getElementById("obito-aptoDoacao:1").addEventListener("click", function () {
            $divCausaNaoDoacao.fadeIn();
        });

    });

    $("#obito-corpoEncaminhamento").change(function (){
        var $divCausasMortis = $("#divCausasMortis");

        if((document.getElementById("obito-corpoEncaminhamento").value == "SVO") || (document.getElementById("obito-corpoEncaminhamento").value == "IML"))
        {
            $divCausasMortis.fadeOut();
        }
        else
        {
            $divCausasMortis.fadeIn();
        }
    });


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
            'obito.paciente.dataInternacao': {
                required: true
            },
            'obito.paciente.documentoSocial.documento': {
                required: true
            },
            'obito.paciente.documentoSocial.tipoDocumentoComFoto': {
                required: true
            },
            'obito.paciente.sexo': {
                required: true
            },
            'obito.paciente.numeroSUS': {
                required: true
            },
            'obito.paciente.numeroProntuario': {
                required: true
            },
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
            'obito.paciente.dataInternacao': {
                required: "Por favor, insira a data de internação do paciente"
            },
            'obito.paciente.documentoSocial.documento': {
                required: "Por favor, insira um documento social do paciente"
            },
            'obito.paciente.documentoSocial.tipoDocumentoComFoto': {
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
})();
