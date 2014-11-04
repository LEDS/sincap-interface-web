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
    public static final String RLT_TOTAL_DOACAO_INSTITUICAO = "/total-doacao-instituicao";
    public static final String RLT_QUALIFICACAO_RECUSA_FAMILIAR = "/qualificacao-recusa-familiar";
    public static final String RLT_TOTAL_NAO_DOACAO_INSTITUICAO = "/total-nao-doacao-instituicao";
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
    public static final String CORRIGIR = "/corrigir";
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
    String rlt_total_doacao_instituicao = RLT_TOTAL_DOACAO_INSTITUICAO;
    String rlt_qualificacao_recusa_familiar = RLT_QUALIFICACAO_RECUSA_FAMILIAR;
    String rlt_total_nao_doacao_instituicao = RLT_TOTAL_NAO_DOACAO_INSTITUICAO;
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
    String corrigir = CORRIGIR;
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
    String logout = LOGOUT;
}
