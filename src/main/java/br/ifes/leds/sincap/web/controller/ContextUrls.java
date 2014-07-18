package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.Getter;

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
    /**
     * Aplicações (Controllers)
     */
    public static final String APP_NOTIFICADOR = "/notificador";
    public static final String APP_CAPTADOR = "/captador";
    public static final String APP_HOSPITAL = "/hospital";
    public static final String APP_BANCO_DE_OLHOS = "/bancoolhos";
    public static final String APP_INSTITUICAO_NOTIFICADORA_GENERICA = "/instituicaoNotificadora";
    public static final String APP_SETOR = "/setor";
    public static final String RLT_DOACAO_HOSPITAL = "/doacaoporhospital";
    public static final String RLT_N_DOACAO_HOSPITAL = "/naodoacaoporhospital";
    public static final String APP_NOTIFICACAO_CAPTACAO = "/captacao";
    public static final String APP_NOTIFICACAO_OBITO = "/obito";
    public static final String APP_NOTIFICACAO_ENTREVISTA = "/entrevista";
    public static final String APP_ANALISAR = "/analisar";
    public static final String APP_ENDERECO = "/endereco";
    public static final String APP_CAUSA_NAO_DOACAO = "/causa-nao-doacao";
    /**
     * Métodos
     */
    public static final String VISUALIZAR = "/visualizar";
    public static final String EDITAR = "/editar";
    public static final String APAGAR = "/apagar";
    public static final String ADICIONAR = "/adicionar";
    public static final String SALVAR = "/salvar";
    public static final String CONFIRMAR = "/confirmar";
    public static final String RECUSAR = "/recusar";
    public static final String ARQUIVAR = "/arquivar";
    public static final String GET_MUNICIPIOS = "/getMunicipios";
    public static final String GET_BAIRROS = "/getBairros";

    /**
     * Subsistemas
     */
    String index = INDEX;
    String admin = ADMIN;
    String relatorios = RELATORIOS;
    /**
     * Aplicações (Controllers)
     */
    String app_notificador = APP_NOTIFICADOR;
    String app_captador = APP_CAPTADOR;
    String app_hospital = APP_HOSPITAL;
    String app_banco_de_olhos = APP_BANCO_DE_OLHOS;
    String app_instituicao_notificadora_generica = APP_INSTITUICAO_NOTIFICADORA_GENERICA;
    String app_setor = APP_SETOR;
    String rlt_doacao_hospital = RLT_DOACAO_HOSPITAL;
    String rlt_n_doacao_hospital = RLT_N_DOACAO_HOSPITAL;
    String app_notificacao_captacao = APP_NOTIFICACAO_CAPTACAO;
    String app_notificacao_obito = APP_NOTIFICACAO_OBITO;
    String app_notificacao_entrevista = APP_NOTIFICACAO_ENTREVISTA;
    String app_analisar = APP_ANALISAR;
    String app_endereco = APP_ENDERECO;
    String app_causa_nao_doacao = APP_CAUSA_NAO_DOACAO;
    /**
     * Métodos
     */
    String visualizar = VISUALIZAR;
    String editar = EDITAR;
    String apagar = APAGAR;
    String adicionar = ADICIONAR;
    String salvar = SALVAR;
    String confirmar = CONFIRMAR;
    String recusar = RECUSAR;
    String arquivar = ARQUIVAR;
    String get_municipios = GET_MUNICIPIOS;
    String get_bairros = GET_BAIRROS;
}
