var sincapApp = angular.module('sincapApp', ['ui.event', 'ui.select2']);

sincapApp.controller('ObitoCtrl', function ($scope, $http) {
    $scope.getSetores = function () {
        $http.get("http://" + location.host + "/sincap/obito/getSetores").success(function (data) {
            $scope.setores = data;
        });
    };

    $scope.getContraIndicacoesMedicas = function () {
        $http.get("http://" + location.host + "/sincap/obito/getContraIndicacoesMedicas").success(function (data) {
            $scope.contraIndicacoes = data;
        });
    };
});

sincapApp.controller('EnderecoCtrl', function ($scope, $http) {
    $scope.getEstados = function () {
        $http.get("http://" + location.host + "/sincap/endereco/getEstados").success(function (data) {
            $scope.estados = data;
        });
    };

    $scope.getMunicipios = function (event) {
        $http.get(location.origin + "/sincap/endereco/getMunicipios?estadoId=" + event.target.value).success(function (data) {
            $scope.municipios = data;
        });
    };

    $scope.getBairros = function (event) {
        $http.get(location.origin + "/sincap/endereco/getBairros?cidadeId=" + event.target.value).success(function (data) {
            $scope.bairros = data;
        });
    };
});

function setNome(elemento, nome) {
    $(elemento).prop("name", nome);
}

function setRadioButtonFalse(radioButtonName, triggerFunction) {
    var radioEntrevistaRealizada = $('[name="' + radioButtonName + '"]').filter('[value=false]');
    radioEntrevistaRealizada.prop('checked', true);
    triggerFunction();
}

function fadeComponent(componentToVerify, componentToFadeYes, componentToFadeNo) {

    var val = $('[name="' + componentToVerify + '"]');
    if ($(val[0]).is(":checked")) {
        $("#" + componentToFadeYes).fadeIn();
        $("#" + componentToFadeNo).fadeOut();

    } else {
        $("#" + componentToFadeNo).fadeIn();
        $("#" + componentToFadeYes).fadeOut();
    }

}


function setNomeVarios(nomeAtributo) {
    for (var i = 1; i < arguments.length; i++) {
        var id = $(arguments[i]).prop("id");
        $(arguments[i]).prop("name", nomeAtributo + '.' + id);
    }
}

function ajaxHideElement() {

    var val = $("#username").val();

    $.ajax({
        type: "get",
        url: location.origin + "/sincap/getHospitais?cpf=" + val,
        cache: false,
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {

            $('#hospital').find('option').remove().end(); //limpa o combobox 'hospital'
            var id;
            var valor;

            /*'response' eh o retorno da funcao do controller*/
            $.each(response, function (idx, obj) {
                $.each(obj, function (key, value) { //key eh o nome do campo, value eh o valor que o campo possui
                    if (key == "id")
                        id = value;
                    else
                        valor = value;
                });
                $("#hospital").append("<option value='" + id + "'>" + valor /*aparece para o usuario*/ + "</option>");
            });

        },
        error: function (response, status, error) {
            var $result = $('#result').html("");
            $result.html('Status' + status + "Error: " + error);
        }
    });
}

function ajaxGetHospitais() {

    var val = $("#username").val();
    $('#hospital').find('option').remove().end(); //limpa o combobox 'hospital'

    $.ajax({
        type: "get",
        url: location.origin + "/sincap/getHospitais?cpf=" + val,
        cache: false,
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {

            $('#hospital').find('option').remove().end(); //limpa o combobox 'hospital'
            var id;
            var valor;

            /*'response' eh o retorno da funcao do controller*/
            $.each(response, function (idx, obj) {
                $.each(obj, function (key, value) { //key eh o nome do campo, value eh o valor que o campo possui
                    if (key == "id")
                        id = value;
                    else
                        valor = value;
                });
                $("#hospital").append("<option value='" + id + "'>" + valor /*aparece para o usuario*/ + "</option>");
            });

        },
        error: function (response, status, error) {
            var $result = $('#result').html("");
            $result.html('Status' + status + "Error: " + error);
        }
    });
}

function ajaxGetMunicipios(estado, municipio) {

    var val = $(estado).val();
    $(municipio).find('option').remove().end(); //limpa o combobox 'municipio'
    $.ajax({
        type: "get",
        url: location.origin + "/sincap/endereco/getMunicipios?estadoId=" + val,
        cache: false,
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {

            $(municipio).find('option').remove().end(); //limpa o combobox 'municipio'
            var id;
            var valor;

            /*'response' eh o retorno da funcao do controller*/
            $.each(response, function (idx, obj) {
                $.each(obj, function (key, value) { //key eh o nome do campo, value eh o valor que o campo possui
                    if (key == "id")
                        id = value;
                    else
                        valor = value;
                });
                $(municipio).append("<option value='" + id + "'>" + valor /*aparece para o usuario*/ + "</option>");
            });

        },
        error: function (response, status, error) {
            var $result = $('#result').html("");
            $result.html('Status' + status + "Error: " + error);
        }
    });
}

function ajaxGetBairros(idMunicipio, idBairro) {

    var val = $(idMunicipio).val();
    $(idBairro).find('option').remove().end(); //limpa o combobox 'bairro'
    $.ajax({
        type: "get",
        url: location.origin + "/sincap/endereco/getBairros?cidadeId=" + val,
        cache: false,
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {

            $(idBairro).find('option').remove().end(); //limpa o combobox 'bairro'
            var id;
            var valor;

            /*'response' eh o retorno da funcao do controller*/
            $.each(response, function (idx, obj) {
                $.each(obj, function (key, value) { //key eh o nome do campo, value eh o valor que o campo possui
                    if (key == "id")
                        id = value;
                    else
                        valor = value;
                });
                $(idBairro).append("<option value='" + id + "'>" + valor /*aparece para o usuario*/ + "</option>");
            });

        },
        error: function (response, status, error) {
            var $result = $('#result').html("");
            $result.html('Status' + status + "Error: " + error);
        }
    });
}

/**
 * Pega as novas notificações do banco de dados, e atualiza os elementos do painel
 * de novas notificações.
 */
function ajaxGetNovasNotificacoes() {
    // Limpa todo o html do painel.
    $('#painelNovasNotificacoes').html("");

    // Faz a requisição ao controlador.
    $.ajax({
        type: "get",
        url: location.origin + "/sincap/processo/getNotificarInteressados",
        cache: false,
        Accept: "application/json",
        contentType: "application/json",
        success: function (response) {
            // Variável que conta o número de notificações.
            var quantasNotificacoes = 0;

            // Variável que agregará os vários itens do painel.
            var itens = [];

            var id;
            var codigo;
            var estado;
            var tempo;
            var url;

            $.each(response, function (idx, notificacao) {
                // Para cada notificação, aumenta 1 no número de notificações.
                quantasNotificacoes += 1;

                // Pega os campos necessários da notificação.
                $.each(notificacao, function (key, value) { //key eh o nome do campo, value eh o valor que o campo possui
                    if (key === "id")
                        id = value;
                    if (key === "codigo")
                        codigo = value;
                    if (key === "estado")
                        estado = value;
                    if (key === "tempo")
                        tempo = value;
                    if (key === "urlRelativa")
                        url = value;
                });

                // Acrescenta um item ao painel, com os dados da notificação.
                itens[idx] =
                    "<input hidden=\"hidden\" id=\"id\" name=\"id\" value=" + id +"/>"
                    + "<a href=\"" + location.origin + url + "\" class=\"item\">"
                    + "<span class=\"time\"><i class=\"icon-time\"></i> " + tempo + "</span>"
                    + "<i class=\"icon-envelope-alt\"></i>" + estado
                    + "</a>";
            });

            // Atualiza o contador do número de novas notificações e o título do painel.
            $("#contador").html(quantasNotificacoes);

            // Anexa o cabeçalho do painel.
            var $painelNovasNotificacoes = $("#painelNovasNotificacoes").append(
                    "<h3>Você tem " + quantasNotificacoes + " novas notificações</h3>");

            // Anexa os itens do painel.
            $painelNovasNotificacoes.append(itens);

            // Anexa o rodapé a lista.
            $painelNovasNotificacoes.append(
                    "<div class=\"footer\">"
                    + "<a href=\"" + location.origin + "/sincap/buscar/todos" + "\" class=\"logout\">Ver todas as notificações</a>"
                    + "</div>");
        },
        error: function (response, status, error) {
            $("#painelNovasNotificacoes").append(
                "<h3>Erro no javascript do painel de novas notificações</h3>");
        }
    });
}

function definirEstilo() {
    $(".control-group > label").addClass("control-label");
    var $control_group_input = $(".control-group > input").addClass("span3 inline-input");
    $control_group_input.wrap("<div class='controls'></div>");
    $(".control-group > table").wrap("<div class='span3' style='padding-left: 1em'></div>");
    $(".control-group > select").wrap("<div class='span3'></div>");
    $("br + label").css("padding-bottom", "1em");
}
