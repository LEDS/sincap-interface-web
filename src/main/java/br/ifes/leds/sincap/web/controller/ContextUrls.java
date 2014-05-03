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

    public static final String ADMIN = "/admin";
    public static final String APP_NOTIFICADOR = "/notificador";
    public static final String EDITAR = "/editar";
    public static final String APAGAR = "/apagar";
    public static final String ADICIONAR = "/adicionar";
    public static final String SALVAR = "/salvar";

    String admin = ADMIN;
    String app_notificador = APP_NOTIFICADOR;
    String editar = EDITAR;
    String apagar = APAGAR;
    String adicionar = ADICIONAR;
    String salvar = SALVAR;
}
