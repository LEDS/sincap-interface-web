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
    public static final String APP_MOTIVO_INVIABILIDADE = "/motivoinviabilidade";
    public static final String RLT_DOACAO_HOSPITAL = "/doacaoporhospital";
    public static final String RLT_N_DOACAO_HOSPITAL = "/naodoacaoporhospital";
    public static final String APP_NOTIFICACAO_CAPTACAO = "/captacao";
    /**
     * Métodos
     */
    public static final String EDITAR = "/editar";
    public static final String APAGAR = "/apagar";
    public static final String ADICIONAR = "/adicionar";
    public static final String SALVAR = "/salvar";

    /**
     * Subsistemas
     */
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
    String app_motivo_inviabilidade = APP_MOTIVO_INVIABILIDADE;
    String rlt_doacao_hospital = RLT_DOACAO_HOSPITAL;
    String rlt_n_doacao_hospital = RLT_N_DOACAO_HOSPITAL;
    String app_notificacao_captacao = APP_NOTIFICACAO_CAPTACAO;
    /**
     * Métodos
     */
    String editar = EDITAR;
    String apagar = APAGAR;
    String adicionar = ADICIONAR;
    String salvar = SALVAR;
}
