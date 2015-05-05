(function() {
    var SEIS_HORAS = 21600000;

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
            $(window).scrollTop($('body').offset().top);
            if (step.step === 1) {
                $btnPrev.hide();
                $btnPrev.attr("disabled", "disabled");
            } else if (step.step === 2) {
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

    //Validando os dados do formulario
    var validar_form = function() {
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
                'obito.aptoDoacao': {
                    required: "Por favor, selecione o estado do paciente"
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    };

    var data = function(dataBr) {
        dataArray = dataBr.split('/');
        dataEn = dataArray[1] + '/' + dataArray[0] + '/' + dataArray[2];
        return new Date(dataEn);
    };

    var ehMais6Horas = function (dataHoraObito) {
        return new Date() - dataHoraObito >= SEIS_HORAS;
    };

    var setInapto = function() {
        document.getElementById('obito-aptoDoacao:1').checked = true;
        $("#divCausaNaoDoacao").show();
        $.uniform.update();
    };

    var setAcimaTempoMaximoRetirada = function() {
        document.getElementById('causaNaoDoacao').value = '13';
        $('#causaNaoDoacao').select2();
    };

    var verificarHoraObito = function(dataHoraObito) {
        var tipoObito = document.getElementById('obito-tipoObito').value;

        if(tipoObito === "PCR" && ehMais6Horas(dataHoraObito)) {
            setInapto();
            setAcimaTempoMaximoRetirada();
            $("#msgAlertaHora").show();
        }
    };

    var eventoMudarHora = function () {
        var dataObito = document.getElementById('obito-dataObito').value;
        var horaObito = document.getElementById('horarioObito').value;
        var dataHoraObito = dataObito + ' ' + horaObito;
        var REGEX_DATA_HORA_PREENCHIDAS = /^\d{2}\/\d{2}\/\d{4}\s\d{2}:\d{2}$/;

        if(dataHoraObito.match(REGEX_DATA_HORA_PREENCHIDAS)) {
            verificarHoraObito(data(dataHoraObito));
        }
    };

    var show_hide_contraindicacoes = function() {
        var opt_apto_doacao = document.getElementById('obito-aptoDoacao:0');
        var opt_inapto_doacao = document.getElementById('obito-aptoDoacao:1');

        opt_apto_doacao.addEventListener('click', function() {
            var divCausaNaoDoacao = $("#divCausaNaoDoacao");
            var causaNaoDoacao = $("#causaNaoDoacao");
            divCausaNaoDoacao.hide();
            causaNaoDoacao.val(0);
            causaNaoDoacao.select2();
            $("#msgAlertaHora").hide();
        });

        opt_inapto_doacao.addEventListener('click', function () {
            $("#divCausaNaoDoacao").show();
            eventoMudarHora();

        });
    };

    $(document).ready(function () {
        $('#causaNaoDoacao').select2();
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
                fieldBoxValidatorSuccess(null, document.getElementById('obito-paciente-documentoSocial-documento'));
                $("#obito-paciente-nome").rules('remove');
                fieldBoxValidatorSuccess(null, document.getElementById('obito-paciente-nome'));
                $("#obito-paciente-dataNascimento").rules('remove');
                fieldBoxValidatorSuccess(null, document.getElementById('obito-paciente-dataNascimento'));
                $("#obito-paciente-nomeMae").rules('remove');
                fieldBoxValidatorSuccess(null, document.getElementById('obito-paciente-nomeMae'));
                $("#obito-paciente-numeroSUS").rules('remove');
                fieldBoxValidatorSuccess(null, document.getElementById('obito-paciente-numeroSUS'));
            } else {
                $("#obito-paciente-documentoSocial-documento").rules('add', {required: true});
                $("#obito-paciente-nome").rules('add', {required: true});
                $("#obito-paciente-dataNascimento").rules('add', {required: true});
                $("#obito-paciente-nomeMae").rules('add', {required: true});
                $("#obito-paciente-numeroSUS").rules('add', {required: true});
            }
        });
        $("#msgAlertaHora").hide();
        addListenerMulti(document.getElementById('obito-dataObito'), 'change click keypress', eventoMudarHora);
        addListenerMulti(document.getElementById('horarioObito'), 'change click keypress', eventoMudarHora);
        addListenerMulti(document.getElementById('obito-tipoObito'), 'change click keypress', eventoMudarHora);

        validar_form();
        show_hide_contraindicacoes();
    });
})();
