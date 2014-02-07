package br.ifes.leds.sincap.web.model;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoRecusa;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.CausaMortis;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Encaminhamento;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoCivil;


public class PacienteForm {   
    
    private List<Estado> estados;
    private List<Cidade> cidades;
    private List<Bairro> bairros;
    private List<Setor> setores;
    private List<CausaMortis> listaMotivos;
    private List<MotivoRecusa> contraIndicacoesMedicas;
    private List<MotivoRecusa> recusasFamiliares;

    /** Cabecalho da pagina **/
    private String protocolo;
    private String nomeHospital;
    private String nomeNotificador;
    private String dataNotificacao;

    /**-- Etapa de Obito --**/

    /* -- Aba Paciente -- */
    private String nomePaciente;
    private String rgPaciente;
    private String numeroProntuario;
    private String dataNascimentoPaciente;
    private String[] sexoPaciente;
    private String estadoPaciente;
    private String cidadePaciente;
    private String bairroPaciente;
    private String cepPaciente;
    private String logradouroPaciente;
    private String nomeMaePaciente;

    /* -- Aba Responsavel -- */
    private String nacionalidadeResp;
    private String estadoCivilResp;
    private String rgResp;
    private String nomeResp;
    private String telResp;
    private String sexoResp;

    /* -- Aba Obito -- */
    private String dataObito;
    private String horarioObito;
    private String tipoObito;
    private String setorObito;
    private String motivosObito1;
    private String motivosObito2;
    private String motivosObito3;
    private String motivosObito4;

    /* -- Aba Contra Indicacoes -- */
    private String[] contraIndicacoes;

    /**-- Etapa de Entrevista --**/

    /* -- Aba Entrevista -- */
    private String entrevistaRealizada;
    private String doacaoAutorizada;
    private String[] recusaFamiliar;

    /* -- Aba Responsavel Doacao  -- */
    private String grauParentescoDoacao;
    private String nomeRespEntrevista;
    private String estavoCivilRespDoacao;
    private String logradouroRespDoacao; //???
    private String telefone1Resp;
    private String telefone2Resp;
    private String nacionalidadeRespDoacao;
    private String profissaoRespDoacao;
    
    private String rgResponsavelRespDoacao;
    private String cidadeRespDoacao;
    private String numeroRespDoacao;
    private String bairroRespDoacao;
    private String estadoRespDoacao;
    private String cepRespDoacao;

    /* -- Aba Testemunhas -- */
    private String nomeTestemunha1;
    private String cpfTestemunha1;
    private String nomeTestemunha2;
    private String cpfTestemunha2;

    /**-- Etapa de Captacao --**/
    private String corneasCaptadas;
    private String corpoEncaminhado;
    private String equipeCaptacao;
    private String[] problemasLogisticosEstruturais;
    private String comentarios;

    /** -- Preenche os dropdowns --**/
    public List<SelectItem> getEstadosBanco() {
        
        List<SelectItem> estadosSelectItem = new ArrayList<SelectItem>();

        for(Estado estado : this.estados)
                estadosSelectItem.add(new SelectItem(estado.getId(), estado.getSigla()));

        return estadosSelectItem;
        
    }

    /* Talvez seja desnecessario */
    public List<SelectItem> getCidadesBanco() {
        List<SelectItem> cidadeSelectItem = new ArrayList<SelectItem>();

        for(Cidade cidade : this.cidades)
                cidadeSelectItem.add(new SelectItem(cidade.getId(), cidade.getNome()));

        return cidadeSelectItem;
    }

    /* Talvez seja desnecessario */
    public List<SelectItem> getBairrosBanco() {
        List<SelectItem> bairroSelectItem = new ArrayList<SelectItem>();

        for(Bairro bairro : this.bairros)
                bairroSelectItem.add(new SelectItem(bairro.getId(), bairro.getNome()));

        return bairroSelectItem;
    }

    public List<SelectItem> getSetoresBanco() {
        List<SelectItem> setorSelectItem = new ArrayList<SelectItem>();

        for(Setor setor : this.setores)
                setorSelectItem.add(new SelectItem(setor.getId(), setor.getNome()));

        return setorSelectItem;
    }

    public List<SelectItem> getContraIndicacoesBanco() {

        List<SelectItem> contraIndicacoes = new ArrayList<SelectItem>();

        for(MotivoRecusa contraIndic : this.contraIndicacoesMedicas)
                contraIndicacoes.add(new SelectItem(contraIndic.getId(), contraIndic.getNome()));

        return contraIndicacoes;
        
    }

    public List<SelectItem> getRecusaFamiliarBanco() {

        List<SelectItem> recusaFamiliarSelectItem = new ArrayList<SelectItem>();

        for(MotivoRecusa recusa : this.recusasFamiliares)
                recusaFamiliarSelectItem.add(new SelectItem(recusa.getId(), recusa.getNome()));

        return recusaFamiliarSelectItem;
        
    }

    public List<SelectItem> getGrauParentescoBanco() {
        
        List<SelectItem> grauParentesco = new ArrayList<SelectItem>();
        grauParentesco.add(new SelectItem("1", "Grau 1"));
        grauParentesco.add(new SelectItem("2", "Grau 2"));
        grauParentesco.add(new SelectItem("3", "Grau 3"));

        return grauParentesco;
        
    }

    public List<SelectItem> getMotivosBanco() {
        
        List<SelectItem> motivosSelectItem = new ArrayList<SelectItem>();

        for(CausaMortis motivo : this.listaMotivos)      
                motivosSelectItem.add(new SelectItem(motivo.getId(), motivo.getDescricao().toString()));                   

        return motivosSelectItem;
        
    }

    public List<SelectItem> getProblemasLogisticosEstruturaisBanco() {
        
        List<SelectItem> problemasSelectItem = new ArrayList<SelectItem>();


        return problemasSelectItem;
        
    }
    
    public List<SelectItem> getEstadosCivisBanco(){
        List<SelectItem> estadosCivisItem = new ArrayList<SelectItem>();
        
        estadosCivisItem.add(new SelectItem("1", EstadoCivil.CASADO.toString()));
        estadosCivisItem.add(new SelectItem("2", EstadoCivil.DIVORCIADO.toString()));
        estadosCivisItem.add(new SelectItem("3", EstadoCivil.SOLTEIRO.toString()));
        estadosCivisItem.add(new SelectItem("4", EstadoCivil.VIUVO.toString()));
                
        return estadosCivisItem;
    }
    
    public List<SelectItem> getEncaminhado(){
        List<SelectItem> corpoEncItem = new ArrayList<SelectItem>();
        
        corpoEncItem.add(new SelectItem("1", Encaminhamento.IML.toString()));
        corpoEncItem.add(new SelectItem("2", Encaminhamento.SVO.toString()));
        corpoEncItem.add(new SelectItem("3", Encaminhamento.NAO_ENCAMINHADO.toString()));
                
        return corpoEncItem;
    }

    /** -- Getters e Setters -- **/
    public List<Estado> getEstados() {
        return estados;
    }

    public void setEstados(List<Estado> estados) {
        this.estados = estados;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }

    public List<Bairro> getBairros() {
        return bairros;
    }

    public void setBairros(List<Bairro> bairros) {
        this.bairros = bairros;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public void setSetores(List<Setor> setores) {
        this.setores = setores;
    }

    public List<CausaMortis> getListaMotivos() {
        return listaMotivos;
    }

    public void setListaMotivos(List<CausaMortis> listaMotivos) {
        this.listaMotivos = listaMotivos;
    }

    public List<MotivoRecusa> getContraIndicacoesMedicas() {
        return contraIndicacoesMedicas;
    }

    public void setContraIndicacoesMedicas(List<MotivoRecusa> contraIndicacoesMedicas) {
        this.contraIndicacoesMedicas = contraIndicacoesMedicas;
    }

    public List<MotivoRecusa> getRecusasFamiliares() {
        return recusasFamiliares;
    }

    public void setRecusasFamiliares(List<MotivoRecusa> recusasFamiliares) {
        this.recusasFamiliares = recusasFamiliares;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getNomeHospital() {
        return nomeHospital;
    }

    public void setNomeHospital(String nomeHospital) {
        this.nomeHospital = nomeHospital;
    }

    public String getNomeNotificador() {
        return nomeNotificador;
    }

    public void setNomeNotificador(String nomeNotificador) {
        this.nomeNotificador = nomeNotificador;
    }

    public String getDataNotificacao() {
        return dataNotificacao;
    }

    public void setDataNotificacao(String dataNotificacao) {
        this.dataNotificacao = dataNotificacao;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public String getRgPaciente() {
        return rgPaciente;
    }

    public void setRgPaciente(String rgPaciente) {
        this.rgPaciente = rgPaciente;
    }

    public String getNumeroProntuario() {
        return numeroProntuario;
    }

    public void setNumeroProntuario(String numeroProntuario) {
        this.numeroProntuario = numeroProntuario;
    }

    public String getDataNascimentoPaciente() {
        return dataNascimentoPaciente;
    }

    public void setDataNascimentoPaciente(String dataNascimentoPaciente) {
        this.dataNascimentoPaciente = dataNascimentoPaciente;
    }

    public String[] getSexoPaciente() {
        return sexoPaciente;
    }

    public void setSexoPaciente(String[] sexoPaciente) {
        this.sexoPaciente = sexoPaciente;
    }

    public String getEstadoPaciente() {
        return estadoPaciente;
    }

    public void setEstadoPaciente(String estadoPaciente) {
        this.estadoPaciente = estadoPaciente;
    }

    public String getCidadePaciente() {
        return cidadePaciente;
    }

    public void setCidadePaciente(String cidadePaciente) {
        this.cidadePaciente = cidadePaciente;
    }

    public String getBairroPaciente() {
        return bairroPaciente;
    }

    public void setBairroPaciente(String bairroPaciente) {
        this.bairroPaciente = bairroPaciente;
    }

    public String getNomeMaePaciente() {
        return nomeMaePaciente;
    }

    public void setNomeMaePaciente(String nomeMaePaciente) {
        this.nomeMaePaciente = nomeMaePaciente;
    }

    public String getNacionalidadeResp() {
        return nacionalidadeResp;
    }

    public String getCepPaciente() {
        return cepPaciente;
    }

    public void setCepPaciente(String cepPaciente) {
        this.cepPaciente = cepPaciente;
    }

    public String getLogradouroPaciente() {
        return logradouroPaciente;
    }

    public void setLogradouroPaciente(String logradouroPaciente) {
        this.logradouroPaciente = logradouroPaciente;
    }

    public void setNacionalidadeResp(String nacionalidadeResp) {
        this.nacionalidadeResp = nacionalidadeResp;
    }
    public String getRgResp() {
        return rgResp;
    }

    public void setRgResp(String rgResp) {
        this.rgResp = rgResp;
    }

    public String getNomeResp() {
        return nomeResp;
    }

    public void setNomeResp(String nomeResp) {
        this.nomeResp = nomeResp;
    }

    public String getTelResp() {
        return telResp;
    }

    public void setTelResp(String telResp) {
        this.telResp = telResp;
    }

    public String getSexoResp() {
        return sexoResp;
    }

    public void setSexoResp(String sexoResp) {
        this.sexoResp = sexoResp;
    }

    public String getDataObito() {
        return dataObito;
    }

    public void setDataObito(String dataObito) {
        this.dataObito = dataObito;
    }

    public String getHorarioObito() {
        return horarioObito;
    }

    public void setHorarioObito(String horarioObito) {
        this.horarioObito = horarioObito;
    }

    public String getTipoObito() {
        return tipoObito;
    }

    public void setTipoObito(String tipoObito) {
        this.tipoObito = tipoObito;
    }

    public String getSetorObito() {
        return setorObito;
    }

    public void setSetorObito(String setorObito) {
        this.setorObito = setorObito;
    }

    public String getMotivosObito1() {
        return motivosObito1;
    }

    public void setMotivosObito1(String motivosObito1) {
        this.motivosObito1 = motivosObito1;
    }

    public String getMotivosObito2() {
        return motivosObito2;
    }

    public void setMotivosObito2(String motivosObito2) {
        this.motivosObito2 = motivosObito2;
    }

    public String getMotivosObito3() {
        return motivosObito3;
    }

    public void setMotivosObito3(String motivosObito3) {
        this.motivosObito3 = motivosObito3;
    }

    public String getMotivosObito4() {
        return motivosObito4;
    }

    public void setMotivosObito4(String motivosObito4) {
        this.motivosObito4 = motivosObito4;
    }

    public String[] getContraIndicacoes() {
        return contraIndicacoes;
    }

    public void setContraIndicacoes(String[] contraIndicacoes) {
        this.contraIndicacoes = contraIndicacoes;
    }

    public String getEntrevistaRealizada() {
        return entrevistaRealizada;
    }

    public void setEntrevistaRealizada(String entrevistaRealizada) {
        this.entrevistaRealizada = entrevistaRealizada;
    }

    public String getDoacaoAutorizada() {
        return doacaoAutorizada;
    }

    public void setDoacaoAutorizada(String doacaoAutorizada) {
        this.doacaoAutorizada = doacaoAutorizada;
    }

    public String[] getRecusaFamiliar() {
        return recusaFamiliar;
    }

    public void setRecusaFamiliar(String[] recusaFamiliar) {
        this.recusaFamiliar = recusaFamiliar;
    }

    public String getGrauParentescoDoacao() {
        return grauParentescoDoacao;
    }

    public void setGrauParentescoDoacao(String grauParentescoDoacao) {
        this.grauParentescoDoacao = grauParentescoDoacao;
    }

    public String getNomeRespEntrevista() {
        return nomeRespEntrevista;
    }

    public void setNomeRespEntrevista(String nomeRespEntrevista) {
        this.nomeRespEntrevista = nomeRespEntrevista;
    }

    public String getEstavoCivilRespDoacao() {
        return estavoCivilRespDoacao;
    }

    public void setEstavoCivilRespDoacao(String estavoCivilRespDoacao) {
        this.estavoCivilRespDoacao = estavoCivilRespDoacao;
    }

    public String getLogradouroRespDoacao() {
        return logradouroRespDoacao;
    }

    public void setLogradouroRespDoacao(String logradouroRespDoacao) {
        this.logradouroRespDoacao = logradouroRespDoacao;
    }

    public String getTelefone1Resp() {
        return telefone1Resp;
    }

    public void setTelefone1Resp(String telefone1Resp) {
        this.telefone1Resp = telefone1Resp;
    }

    public String getTelefone2Resp() {
        return telefone2Resp;
    }

    public void setTelefone2Resp(String telefone2Resp) {
        this.telefone2Resp = telefone2Resp;
    }

    public String getNacionalidadeRespDoacao() {
        return nacionalidadeRespDoacao;
    }

    public void setNacionalidadeRespDoacao(String nacionalidadeRespDoacao) {
        this.nacionalidadeRespDoacao = nacionalidadeRespDoacao;
    }

    public String getProfissaoRespDoacao() {
        return profissaoRespDoacao;
    }

    public void setProfissaoRespDoacao(String profissaoRespDoacao) {
        this.profissaoRespDoacao = profissaoRespDoacao;
    }

    public String getRgResponsavelRespDoacao() {
        return rgResponsavelRespDoacao;
    }

    public void setRgResponsavelRespDoacao(String rgResponsavelRespDoacao) {
        this.rgResponsavelRespDoacao = rgResponsavelRespDoacao;
    }

    public String getNumeroRespDoacao() {
        return numeroRespDoacao;
    }

    public void setNumeroRespDoacao(String numeroRespDoacao) {
        this.numeroRespDoacao = numeroRespDoacao;
    }

    public String getBairroRespDoacao() {
        return bairroRespDoacao;
    }

    public void setBairroRespDoacao(String bairroRespDoacao) {
        this.bairroRespDoacao = bairroRespDoacao;
    }

    public String getEstadoRespDoacao() {
        return estadoRespDoacao;
    }

    public void setEstadoRespDoacao(String estadoRespDoacao) {
        this.estadoRespDoacao = estadoRespDoacao;
    }

    public String getCepRespDoacao() {
        return cepRespDoacao;
    }

    public void setCepRespDoacao(String cepRespDoacao) {
        this.cepRespDoacao = cepRespDoacao;
    }

    public String getNomeTestemunha1() {
        return nomeTestemunha1;
    }

    public void setNomeTestemunha1(String nomeTestemunha1) {
        this.nomeTestemunha1 = nomeTestemunha1;
    }

    public String getCpfTestemunha1() {
        return cpfTestemunha1;
    }

    public void setCpfTestemunha1(String cpfTestemunha1) {
        this.cpfTestemunha1 = cpfTestemunha1;
    }

    public String getNomeTestemunha2() {
        return nomeTestemunha2;
    }

    public void setNomeTestemunha2(String nomeTestemunha2) {
        this.nomeTestemunha2 = nomeTestemunha2;
    }

    public String getCpfTestemunha2() {
        return cpfTestemunha2;
    }

    public void setCpfTestemunha2(String cpfTestemunha2) {
        this.cpfTestemunha2 = cpfTestemunha2;
    }

    public String getCorneasCaptadas() {
        return corneasCaptadas;
    }

    public void setCorneasCaptadas(String corneasCaptadas) {
        this.corneasCaptadas = corneasCaptadas;
    }

    public String getCorpoEncaminhado() {
        return corpoEncaminhado;
    }

    public void setCorpoEncaminhado(String corpoEncaminhado) {
        this.corpoEncaminhado = corpoEncaminhado;
    }

    public String getEquipeCaptacao() {
        return equipeCaptacao;
    }

    public void setEquipeCaptacao(String equipeCaptacao) {
        this.equipeCaptacao = equipeCaptacao;
    }

    public String[] getProblemasLogisticosEstruturais() {
        return problemasLogisticosEstruturais;
    }

    public void setProblemasLogisticosEstruturais(String[] problemasLogisticosEstruturais) {
        this.problemasLogisticosEstruturais = problemasLogisticosEstruturais;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCidadeRespDoacao() {
        return cidadeRespDoacao;
    }

    public void setCidadeRespDoacao(String cidadeRespDoacao) {
        this.cidadeRespDoacao = cidadeRespDoacao;
    }

    public String getEstadoCivilResp() {
        return estadoCivilResp;
    }

    public void setEstadoCivilResp(String estadoCivilResp) {
        this.estadoCivilResp = estadoCivilResp;
    }
    
}