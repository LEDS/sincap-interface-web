/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

/**
 *
 * @author Phillipe Lopes
 */
public class NotificadorDTO {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private TipoUsuario tipoUsuario;
    private boolean active;
    private TelefoneDTO[] telefones;
    private Long[] instituicoesNotificadoras;

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

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public TelefoneDTO[] getTelefones() {
        return telefones;
    }

    public void setTelefones(TelefoneDTO[] telefones) {
        this.telefones = telefones;
    }

    public Long[] getInstituicoesNotificadoras() {
        return instituicoesNotificadoras;
    }

    public void setInstituicoesNotificadoras(Long[] instituicoesNotificadoras) {
        this.instituicoesNotificadoras = instituicoesNotificadoras;
    }

}
