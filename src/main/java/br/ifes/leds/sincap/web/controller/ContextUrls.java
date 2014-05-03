/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author phillipe
 */
@ManagedBean
@ApplicationScoped
@Getter
@Setter
public class ContextUrls {

    /**
     * Subsistemas
     */
    public static final String ADMIN = "/admin";
    /**
     * Aplicações (Controllers)
     */
    public static final String APP_NOTIFICADOR = "/notificador";
    public static final String APP_HOSPITAL = "/hospital";
    public static final String APP_BANCO_DE_OLHOS = "/bancoolhos";
    public static final String APP_INSTITUICAO_NOTIFICADORA_GENERICA = "/instituicaoNotificadora";
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
    /**
     * Aplicações (Controllers)
     */
    String app_notificador = APP_NOTIFICADOR;
    String app_hospital = APP_HOSPITAL;
    String app_banco_de_olhos = APP_BANCO_DE_OLHOS;
    String app_instituicao_notificadora_generica = APP_INSTITUICAO_NOTIFICADORA_GENERICA;
    /**
     * Métodos
     */
    String editar = EDITAR;
    String apagar = APAGAR;
    String adicionar = ADICIONAR;
    String salvar = SALVAR;
}
