/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Phillipe Lopes
 */
public class NotificadorForm {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String idCelular;
    private String celular;
    private String idResidencial;
    private String residencial;

    public NotificadorForm() {
    }

    public NotificadorForm(Notificador notificador) {
        id = notificador.getId();
        nome = notificador.getNome();
        cpf = notificador.getCpf();
        email = notificador.getEmail();
        senha = notificador.getSenha();

        for (Telefone telefone : notificador.getTelefones()) {
            if (telefone.getTipo() == TipoTelefone.CELULAR) {
                idCelular = telefone.getId().toString();
                celular = telefone.getDdd() + telefone.getNumero();
            } else if (telefone.getTipo() == TipoTelefone.RESIDENCIAL) {
                idResidencial = telefone.getId().toString();
                residencial = telefone.getDdd() + telefone.getNumero();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getResidencial() {
        return residencial;
    }

    public void setResidencial(String residencial) {
        this.residencial = residencial;
    }

    public String getIdCelular() {
        return idCelular;
    }

    public void setIdCelular(String idCelular) {
        this.idCelular = idCelular;
    }

    public String getIdResidencial() {
        return idResidencial;
    }

    public void setIdResidencial(String idResidencial) {
        this.idResidencial = idResidencial;
    }

    public Notificador converterParaNotificador() {
        Notificador notificador = new Notificador();
        Set<Telefone> telefones = new HashSet<>();
        Telefone celularTemp, residencialTemp;

        if (id != null) {
            notificador.setId(id);
        } else {
            notificador.setActive(true);
        }

        notificador.setEmail(email);
        notificador.setNome(nome);
        notificador.setCpf(cpf);
        notificador.setSenha(senha);
        notificador.setTipoUsuario(br.ifes.leds.sincap.controleInterno.cln.cdp.TipoUsuario.NOTIFICADOR);

        if (celular != null) {
            celularTemp = stringParaTelefone(celular, TipoTelefone.CELULAR);
            if (idCelular != null) {
                celularTemp.setId(Long.parseLong(idCelular));
            }
            telefones.add(celularTemp);
        }
        if (residencial != null) {
            residencialTemp = stringParaTelefone(residencial, TipoTelefone.RESIDENCIAL);
            if (idResidencial != null) {
                residencialTemp.setId(Long.parseLong(idResidencial));
            }
            telefones.add(residencialTemp);
        }

        notificador.setTelefones(telefones);

        return notificador;
    }

    private Telefone stringParaTelefone(String telefoneStr, TipoTelefone tipo) {

        Telefone telefoneTemp = new Telefone();

        telefoneTemp.setTipo(tipo);

        telefoneTemp.setDdd(telefoneStr.substring(1, 3));
        telefoneTemp.setNumero(telefoneStr.substring(4, 8) + telefoneStr.substring(9, 13));

        return telefoneTemp;

    }
}
