(function () {
    var SEIS_HORAS = 21600000;

    var $formulario = $('#processo');
    var $wizard = $('#fuelux-wizard');
    var $btnPrev = $('.wizard-actions .btn-prev');
    var $btnNext = $('.wizard-actions .btn-next');
    var $btnFinish = $(".wizard-actions .btn-finish");

    $btnPrev.hide();
    $wizard.wizard().on('finished', function () {
        // wizard complete code
    }).on("changed", function () {
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
        if ($formulario.valid()) {
            $wizard.wizard('next');
        }
    });

    function mergeDates() {
        var $processo = document.forms["processo"];
        var data = $processo.elements["obito.dataObito"].value;
        $('#obito-dataObito').inputmask("remove");
        $processo.elements["obito.dataObito"].value = data + " " + $processo.elements["horarioObito"].value;
        console.log($processo.elements["obito.dataObito"].value);
        $processo.submit();
    }

    $btnFinish.click(function () {
        var processo = $("#processo");
        if (processo.valid()) {
            mergeDates();
        }
    });

    //Validando os dados do formulario
    var validar_form = function () {
        $("#processo").validate({
            errorPlacement: fieldBoxValidatorError,
            success: fieldBoxValidatorSuccess,
            rules: {
                'obito.tipoObito': {
                    required: true
                },
                'obito.paciente.documentoSocial.tipoDocumentoComFoto': {
                    required: true
                },
                'obito.paciente.documentoSocial.documento': {
                    required: true
                },
                'obito.paciente.nome': {
                    required: true
                },
                'obito.paciente.dataNascimento': {
                    required: true
                },
                'obito.paciente.dataInternacao': {
                    required: true
                },
                'obito.paciente.nomeMae': {
                    required: true
                },
                'obito.paciente.numeroSUS': {
                    required: true
                },
                'obito.paciente.numeroProntuario': {
                    required: true
                },
                'obito.dataObito': {
                    required: true
                },
                'horarioObito': {
                    required: true
                },
                'obito.setor': {
                    required: true
                },
                'obito.corpoEncaminhamento': {
                    required: true
                },
                'causaNaoDoacao': {
                    required: true
                },
                'obito.primeiraCausaMortis': {
                    required: true
                }
            },
            messages: {
                'obito.tipoObito': {
                    required: 'Por favor, preencha o tipo do óbito'
                },
                'obito.paciente.documentoSocial.tipoDocumentoComFoto': {
                    required: 'Por favor, preencha o tipo do documento'
                },
                'obito.paciente.documentoSocial.documento': {
                    required: 'Por favor, preencha o documento'
                },
                'obito.paciente.nome': {
                    required: 'Por favor, preencha o nome'
                },
                'obito.paciente.dataNascimento': {
                    required: 'Por favor, preencha a data de nascimento'
                },
                'obito.paciente.dataInternacao': {
                    required: 'Por favor, preencha a data de internação'
                },
                'obito.paciente.nomeMae': {
                    required: 'Por favor, preencha o nome da mãe'
                },
                'obito.paciente.numeroSUS': {
                    required: 'Por favor, preencha o número do cartão SUS'
                },
                'obito.paciente.numeroProntuario': {
                    required: 'Por favor, preencha o número de prontuário'
                },
                'obito.dataObito': {
                    required: 'Por favor, preencha a data de óbito'
                },
                'horarioObito': {
                    required: 'Por favor, preencha o horário do óbito'
                },
                'obito.setor': {
                    required: 'Por favor, preencha o setor em que ocorreu o óbito'
                },
                'obito.corpoEncaminhamento': {
                    required: 'Por favor, preencha o encaminhamento do corpo'
                },
                'causaNaoDoacao': {
                    required: 'Por favor, preencha a causa de não doação'
                },
                'obito.primeiraCausaMortis': {
                    required: 'Por favor, preencha a primeira causa mortis'
                }
            },
            submitHandler: function (form) {
                form.submit();
            }
        });
    };

    var data = function (dataBr) {
        var dataArray = dataBr.split('/');
        var dataEn = dataArray[1] + '/' + dataArray[0] + '/' + dataArray[2];
        return new Date(dataEn);
    };

    var ehMais6Horas = function (dataHoraObito) {
        return new Date() - dataHoraObito >= SEIS_HORAS;
    };

    var setInapto = function () {
        document.getElementById('obito-aptoDoacao:1').checked = true;
        $("#divCausaNaoDoacao").show();
        $.uniform.update();
    };

    var setAcimaTempoMaximoRetirada = function () {
        document.getElementById('causaNaoDoacao').value = '13';
        $('#causaNaoDoacao').select2();
    };

    var verificarHoraObito = function (dataHoraObito) {
        var tipoObito = document.getElementById('obito-tipoObito').value;

        if (tipoObito === "PCR" && ehMais6Horas(dataHoraObito)) {
            setInapto();
            setAcimaTempoMaximoRetirada();
            $("#msgAlertaHora").show();
        }
    };

    var eventoMudarHora = function () {
        $("#msgAlertaHora").hide();
        var dataObito = document.getElementById('obito-dataObito').value;
        var horaObito = document.getElementById('horarioObito').value;
        var dataHoraObito = dataObito + ' ' + horaObito;
        var REGEX_DATA_HORA_PREENCHIDAS = /^\d{2}\/\d{2}\/\d{4}\s\d{2}:\d{2}$/;

        if (dataHoraObito.match(REGEX_DATA_HORA_PREENCHIDAS)) {
            verificarHoraObito(data(dataHoraObito));
        }
    };

    var show_hide_contraindicacoes = function () {
        var opt_apto_doacao = document.getElementById('obito-aptoDoacao:0');
        var opt_inapto_doacao = document.getElementById('obito-aptoDoacao:1');

        opt_apto_doacao.addEventListener('click', function () {
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
        tipoDocumento.addEventListener('change', function () {
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

        var corpo_encaminhamento = document.getElementById('obito-corpoEncaminhamento');
        corpo_encaminhamento.addEventListener('change', function () {
            var $primeiraCausaMortis = $("#obito-primeiraCausaMortis");

            if (corpo_encaminhamento.value !== 'NAO_ENCAMINHADO') {
                $primeiraCausaMortis.rules('remove');
                fieldBoxValidatorSuccess(null, document.getElementById('obito-primeiraCausaMortis'));
            } else {
                $primeiraCausaMortis.rules('add', {required: true});
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
