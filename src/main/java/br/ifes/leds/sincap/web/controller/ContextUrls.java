package br.ifes.leds.sincap.web.controller;

import lombok.Getter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author phillipe
 */
@ManagedBean
@ApplicationScoped
@Getter
public class ContextUrls {

    /**
     * Subsistemas
     */
    public static final String INDEX = "/index";
    public static final String ADMIN = "/admin";
    public static final String RELATORIOS = "/relatorio";
    public static final String REST = "/rest";
    /**
     * Aplicações (Controllers)
     */
    public static final String APP_PROCESSO = "/processo";
    public static final String APP_NOTIFICADOR = "/notificador";
    public static final String APP_CAPTADOR = "/captador";
    public static final String APP_ANALISTA = "/analista";
    public static final String APP_HOSPITAL = "/hospital";
    public static final String APP_BANCO_DE_OLHOS = "/bancoolhos";
    public static final String APP_INSTITUICAO_NOTIFICADORA_GENERICA = "/instituicaoNotificadora";
    public static final String APP_SETOR = "/setor";
    public static final String RLT_DOACAO_HOSPITAL = "/doacaoporhospital";
    public static final String RLT_N_DOACAO_HOSPITAL = "/naodoacaoporhospital";
    public static final String RLT_TERMO_AUTORIZACAO_DOACAO = "/termoautorizacao";
    public static final String RLT_TERMO_NAO_DOACAO = "/termonaodoacao";
    public static final String RLT_TOTAL_DOACAO_INSTITUICAO = "/total-doacao-instituicao";
    public static final String RLT_QUALIFICACAO_RECUSA_FAMILIAR = "/qualificacao-recusa-familiar";
    public static final String RLT_TOTAL_NAO_DOACAO_INSTITUICAO = "/total-nao-doacao-instituicao";
    public static final String RLT_POSSIBILIDADE_DOACAO_TECIDO = "/possibilidade-doacao-tecido";
    public static final String APP_NOTIFICACAO_CAPTACAO = "/captacao";
    public static final String APP_NOTIFICACAO_OBITO = "/obito";
    public static final String APP_NOTIFICACAO_ENTREVISTA = "/entrevista";
    public static final String APP_ENDERECO = "/endereco";
    public static final String APP_CAUSA_NAO_DOACAO = "/causa-nao-doacao";
    public static final String APP_FUNCIONARIO = "/funcionario";
    public static final String APP_BUSCAR = "/buscar";

    /**
     * Métodos
     */
    public static final String AUTENTICAR = "/autenticar";
    public static final String LOGOUT = "logout";
    public static final String GET_HOSPITAIS = "/getHospitais";
    public static final String VISUALIZAR = "/visualizar";
    public static final String EDITAR = "/editar";
    public static final String APP_ANALISAR = "/analisar";
    public static final String APAGAR = "/apagar";
    public static final String ADICIONAR = "/adicionar";
    public static final String SALVAR = "/salvar";
    public static final String CONFIRMAR = "/confirmar";
    public static final String RECUSAR = "/recusar";
    public static final String ARQUIVAR = "/arquivar";
    public static final String EXCLUIR = "/excluir";
    public static final String IMPRIMIR = "/imprimir";
    public static final String BUSCAR_TODOS = "/todos";
    public static final String EXIBIR = "/exibir";
    public static final String PROXIMA_ETAPA = "/proxima-etapa";
    public static final String GET_MUNICIPIOS = "/getMunicipios";
    public static final String GET_BAIRROS = "/getBairros";
    public static final String GET_ESTADOS = "/getEstados";
    public static final String GET_SETORES = "/getSetores";
    public static final String GET_CONTRA_INDICACAO = "/getContraIndicacoesMedicas";
    public static final String GET_NOTIFICAR_INTERESSADOS = "/getNotificarInteressados";
    public static final String RLT_ATIVIDADE_MENSAL = "/atividade-mensal";
    public static final String GET_ANALISE_OBITO_PENDENTE = "/getAnaliseObitoPendente";
    public static final String GET_ANALISE_ENTREVISTA_PENDENTE = "/getAnaliseEntrevistaPendente";
    public static final String GET_ANALISE_CAPTACAO_PENDENTE = "/getAnaliseCaptacaoPendente";
    public static final String GET_NOTIFICACOES_AGUARDANDO_ARQUIVAMENTO = "/getNotificacoesAguardandoArquivamento";
    public static final String GET_OBITO_AGUARDANDO_CORRECAO = "/getObitoAguardandoCorrecao";
    public static final String GET_OBITO_AGUARDANDO_ENTREVISTA = "/getObitoAguardandoEntrevista";
    public static final String GET_ENTREVISTA_AGUARDANDO_CORRECAO = "/getEntrevistaAguardandoCorrecao";
    public static final String GET_ENTREVISTA_AGUARDANDO_CAPTACAO = "/getEntrevistaAguardandoCaptacao";
    public static final String GET_CAPTACAO_AGUARDANDO_CORRECAO = "/getCaptacaoAguardandoCorrecao";

    /**
     * Subsistemas
     */
    String index = INDEX;
    String admin = ADMIN;
    String relatorios = RELATORIOS;
    /**
     * Aplicações (Controllers)
     */
    String app_processo = APP_PROCESSO;
    String app_notificador = APP_NOTIFICADOR;
    String app_captador = APP_CAPTADOR;
    String app_analista = APP_ANALISTA;
    String app_hospital = APP_HOSPITAL;
    String app_banco_de_olhos = APP_BANCO_DE_OLHOS;
    String app_instituicao_notificadora_generica = APP_INSTITUICAO_NOTIFICADORA_GENERICA;
    String app_setor = APP_SETOR;
    String rlt_doacao_hospital = RLT_DOACAO_HOSPITAL;
    String rlt_n_doacao_hospital = RLT_N_DOACAO_HOSPITAL;
    String rlt_termo_autorizacao_doacao = RLT_TERMO_AUTORIZACAO_DOACAO;
    String rlt_termo_nao_doacao = RLT_TERMO_NAO_DOACAO;
    String rlt_total_doacao_instituicao = RLT_TOTAL_DOACAO_INSTITUICAO;
    String rlt_qualificacao_recusa_familiar = RLT_QUALIFICACAO_RECUSA_FAMILIAR;
    String rlt_total_nao_doacao_instituicao = RLT_TOTAL_NAO_DOACAO_INSTITUICAO;
    String rlt_atividade_mensal = RLT_ATIVIDADE_MENSAL;
    String rlt_possibilidade_doacao_tecido = RLT_POSSIBILIDADE_DOACAO_TECIDO;
    String app_notificacao_captacao = APP_NOTIFICACAO_CAPTACAO;
    String app_notificacao_obito = APP_NOTIFICACAO_OBITO;
    String app_notificacao_entrevista = APP_NOTIFICACAO_ENTREVISTA;
    String app_endereco = APP_ENDERECO;
    String app_causa_nao_doacao = APP_CAUSA_NAO_DOACAO;
    String app_funcionario = APP_FUNCIONARIO;
    String app_buscar = APP_BUSCAR;
    /**
     * Métodos
     */
    String visualizar = VISUALIZAR;
    String editar = EDITAR;
    String app_analisar = APP_ANALISAR;
    String apagar = APAGAR;
    String adicionar = ADICIONAR;
    String salvar = SALVAR;
    String confirmar = CONFIRMAR;
    String recusar = RECUSAR;
    String arquivar = ARQUIVAR;
    String excluir = EXCLUIR;
    String imprimir = IMPRIMIR;
    String buscar_todos = BUSCAR_TODOS;
    String exibir = EXIBIR;
    String proxima_etapa = PROXIMA_ETAPA;
    String get_municipios = GET_MUNICIPIOS;
    String get_bairros = GET_BAIRROS;
    String get_estados = GET_ESTADOS;
    String get_setores = GET_SETORES;
    String get_contra_indicacao = GET_CONTRA_INDICACAO;
    String get_notificar_interessados = GET_NOTIFICAR_INTERESSADOS;
    String get_analise_obito_pendente = GET_ANALISE_OBITO_PENDENTE;
    String get_analise_entrevista_pendente = GET_ANALISE_ENTREVISTA_PENDENTE;
    String get_analise_captacao_pendente = GET_ANALISE_CAPTACAO_PENDENTE;
    String get_notificacoes_aguardando_arquivamento = GET_NOTIFICACOES_AGUARDANDO_ARQUIVAMENTO;
    String get_obito_aguardando_correcao = GET_OBITO_AGUARDANDO_CORRECAO;
    String get_obito_aguardando_entrevista = GET_OBITO_AGUARDANDO_ENTREVISTA;
    String get_entrevista_aguardando_correcao = GET_ENTREVISTA_AGUARDANDO_CORRECAO;
    String get_entrevista_aguardando_captacao = GET_ENTREVISTA_AGUARDANDO_CAPTACAO;
    String get_captacao_aguardando_correcao = GET_CAPTACAO_AGUARDANDO_CORRECAO;
    String logout = LOGOUT;
}
