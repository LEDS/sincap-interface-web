/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.model;

import br.ifes.leds.reuse.endereco.cdp.Endereco;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadoraGenerica;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Phillipe Lopes
 */
public class InstituicaoNotificadoraForm {
    private Long id;
    private String nome;
    private String fantasia;
    private String sigla;
    private String email;
    private Long idTelefone;
    private String telefone;
    private Long idFax;
    private String fax;
    private Long idEndereco;
    private Long estado;
    private Long cidade;
    private Long bairro;
    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    
    public InstituicaoNotificadoraForm() {
    }
    
    public InstituicaoNotificadoraForm(InstituicaoNotificadoraGenerica instituicaoNotificadora) {
        id = instituicaoNotificadora.getId();
        nome = instituicaoNotificadora.getNome();
        fantasia = instituicaoNotificadora.getFantasia();
        sigla = instituicaoNotificadora.getSigla();
        email = instituicaoNotificadora.getEmail();
        
        for (Telefone telefoneTemp : instituicaoNotificadora.getTelefones()) {
            if (telefoneTemp.getTipo() == TipoTelefone.COMERCIAL) {
                idTelefone = telefoneTemp.getId();
                telefone = telefoneTemp.getDdd() + telefoneTemp.getNumero();
            } else {
                idFax = telefoneTemp.getId();
                fax = telefoneTemp.getDdd() + telefoneTemp.getNumero();
            }
        }
        
        if (instituicaoNotificadora.getEndereco() != null) {
            idEndereco = instituicaoNotificadora.getEndereco().getId();
            estado = instituicaoNotificadora.getEndereco().getEstado().getId();
            cidade = instituicaoNotificadora.getEndereco().getCidade().getId();
            bairro = instituicaoNotificadora.getEndereco().getBairro().getId();
            cep = instituicaoNotificadora.getEndereco().getCEP();
            logradouro = instituicaoNotificadora.getEndereco().getLogradouro();
            numero = instituicaoNotificadora.getEndereco().getNumero();
            complemento = instituicaoNotificadora.getEndereco().getComplemento();
        }
        
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getFantasia() {
        return fantasia;
    }
    
    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public String getSigla() {
        return sigla;
    }
    
    public void setSigla(String sigla) {
        this.sigla = sigla;
    }
    
    public String getFax() {
        return fax;
    }
    
    public void setFax(String fax) {
        this.fax = fax;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getCidade() {
        return cidade;
    }
    
    public void setCidade(Long cidade) {
        this.cidade = cidade;
    }
    
    public Long getEstado() {
        return estado;
    }
    
    public void setEstado(Long estado) {
        this.estado = estado;
    }
    
    public Long getBairro() {
        return bairro;
    }
    
    public void setBairro(Long bairro) {
        this.bairro = bairro;
    }
    
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    public String getLogradouro() {
        return logradouro;
    }
    
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }
    
    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getComplemento() {
        return complemento;
    }
    
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getIdTelefone() {
        return idTelefone;
    }
    
    public void setIdTelefone(Long idTelefone) {
        this.idTelefone = idTelefone;
    }
    
    public Long getIdEndereco() {
        return idEndereco;
    }
    
    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }
    
    public Long getIdFax() {
        return idFax;
    }
    
    public void setIdFax(Long idFax) {
        this.idFax = idFax;
    }
    
    public InstituicaoNotificadoraGenerica converterParaInstituicaoNotificadora(AplEndereco aplEndereco) {
        InstituicaoNotificadoraGenerica instituicaoNotificadora = new InstituicaoNotificadoraGenerica();
        Set<Telefone> telefones = new HashSet<>();
        Endereco endereco = new Endereco();
        Telefone telefoneTemp, faxTemp;
        
        instituicaoNotificadora.setId(id);
        instituicaoNotificadora.setNome(nome);
        instituicaoNotificadora.setFantasia(fantasia);
        instituicaoNotificadora.setSigla(sigla);
        instituicaoNotificadora.setEmail(email);
        instituicaoNotificadora.setNome(nome);
        
        telefoneTemp = stringParaTelefone(telefone, TipoTelefone.COMERCIAL);
        if (idTelefone != null) {
            telefoneTemp.setId(idTelefone);
        }
        telefones.add(telefoneTemp);
        
        faxTemp = stringParaTelefone(fax, TipoTelefone.FAX);
        if (idFax != null) {
            faxTemp.setId(idFax);
        }
        telefones.add(faxTemp);
        
        instituicaoNotificadora.setTelefones(telefones);
        
        endereco.setCEP(cep);
        endereco.setLogradouro(logradouro);
        endereco.setNumero(numero);
        endereco.setComplemento(complemento);
        endereco.setEstado(aplEndereco.obterEstadosPorID(estado));
        endereco.setCidade(aplEndereco.obterCidadePorID(cidade));
        endereco.setBairro(aplEndereco.obterBairroPorID(bairro));
        if (idEndereco != null) {
            endereco.setId(idEndereco);
        }
        instituicaoNotificadora.setEndereco(endereco);
        
        return instituicaoNotificadora;
    }
    
    private Telefone stringParaTelefone(String telefoneStr, TipoTelefone tipo) {
        
        Telefone telefoneTemp = new Telefone();
        
        telefoneTemp.setTipo(tipo);
        
        telefoneTemp.setDdd(telefoneStr.substring(1, 3));
        telefoneTemp.setNumero(telefoneStr.substring(4, 8) + telefoneStr.substring(9, 13));
        
        return telefoneTemp;
        
    }
    
}
