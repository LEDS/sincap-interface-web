package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Endereco;
import java.util.List;
import javax.faces.bean.SessionScoped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Notificador;
import br.ifes.leds.sincap.controleInterno.cln.cdp.MotivoRecusa;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Setor;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Sexo;
import br.ifes.leds.sincap.controleInterno.cln.cdp.Telefone;
import br.ifes.leds.sincap.controleInterno.cln.cdp.TipoTelefone;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplCadastroInterno;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplHospital;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplMotivoInviabilidade;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplMotivoRecusa;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplPrincipal;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplTipoMotivoInviabilidade;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Captacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.CausaMortis;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Doacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Encaminhamento;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.EstadoCivil;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Notificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Obito;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Paciente;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Parentesco;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Responsavel;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Testemunha;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.Mensagem;
import br.ifes.leds.sincap.web.model.PacienteForm;
import br.ifes.leds.sincap.web.model.ResumoNotificacao;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import javax.faces.model.SelectItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/notificacao")
@SessionScoped
public class FormNotificacaoController {

    @Autowired
    UsuarioSessao usuarioSessao;
    
    @Autowired
    AplNotificacao aplNotificacao;
    @Autowired
    AplEndereco aplEndereco;
    @Autowired
    AplPrincipal aplPrincipal;
    @Autowired
    AplCadastroInterno aplCadastroInterno;
    @Autowired
    AplMotivoRecusa aplMotivoRecusa;
    @Autowired
    AplHospital aplHospital;
    @Autowired
    AplMotivoInviabilidade aplMotivoInviabilidade;
    @Autowired
    AplTipoMotivoInviabilidade aplTipoMotivoInviabilidade;

    @RequestMapping(value = "/novo", method = RequestMethod.GET)
    public String loadFormNovaNotificacao(ModelMap model) {

        PacienteForm pacienteForm = new PacienteForm();

        preencherForm(pacienteForm, model);

        model.addAttribute("pacienteForm", pacienteForm);
        model.addAttribute("display", "none");

        return "form-notificacao";
    }

    @RequestMapping(value = "/salvarNotificacao", method = RequestMethod.POST)
    public String addNotificacao(@ModelAttribute PacienteForm pacienteForm, ModelMap model) throws Exception {
        
        if (pacienteForm.valido()){
            Notificacao notificacao = preencheNotificacao(pacienteForm);
            
            aplNotificacao.salvar(notificacao);
            
            return "redirect:/notificacao/novo";
        }
        
        model.addAttribute("pacienteForm", pacienteForm);
        model.addAttribute("display", "block");
        preencherForm(pacienteForm, model);
        return "form-notificacao";
    }

    private void preencherForm(PacienteForm pacienteForm, ModelMap model) {

        List<Estado> estados = aplEndereco.obterEstadosPorNomePais("Brasil");

        List<Setor> listaSetor = aplCadastroInterno.obterSetorPorHospital(usuarioSessao.getIdHospital());

        List<CausaMortis> motivosObitos = aplCadastroInterno.obterTodosCausaObito();
        List<MotivoRecusa> contraIndicacoes = aplMotivoRecusa.obterTodosContraindicacaoMedica();
        List<MotivoRecusa> recusasFamiliares = aplMotivoRecusa.obterTodosRecusaFamiliar();
        List<MotivoInviabilidade> problemasLogisticos = aplMotivoInviabilidade.buscarPorTipoMotivoInviabilidade(aplTipoMotivoInviabilidade.obter("Problema Logístico"));
        List<MotivoInviabilidade> problemasEstruturais = aplMotivoInviabilidade.buscarPorTipoMotivoInviabilidade(aplTipoMotivoInviabilidade.obter("Problema Estrutural"));
        
        preencherMotivosInviabilidade(problemasLogisticos, problemasEstruturais, model);
        
        Notificador notificador = aplPrincipal.obterNotificadorPorUsuarioUsername(usuarioSessao.getCpfUsuario());
        pacienteForm.setNomeNotificador(notificador.getNome());

        pacienteForm.setEstados(estados);
        pacienteForm.setSetores(listaSetor);
        pacienteForm.setListaMotivos(motivosObitos);
        pacienteForm.setContraIndicacoesMedicas(contraIndicacoes);
        pacienteForm.setRecusasFamiliares(recusasFamiliares);

    }

    @RequestMapping(value = "/getMunicipios", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Mensagem>> getMunicipios(@RequestBody String estadoId) {

        List<Mensagem> listaMensagemMunicipios = new ArrayList<Mensagem>();

        try {

            List<Cidade> listaCidades = aplEndereco.obterCidadesPorEstado(Long.parseLong(estadoId));

            for (Cidade cidade : listaCidades) {

                Mensagem mensagem = new Mensagem();

                mensagem.setDado(cidade.getNome());
                mensagem.setId(cidade.getId().toString());

                listaMensagemMunicipios.add(mensagem);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<List<Mensagem>>(listaMensagemMunicipios, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Mensagem>>(listaMensagemMunicipios, HttpStatus.OK);

    }

    @RequestMapping(value = "/getBairros", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<List<Mensagem>> getBairros(@RequestBody String cidadeId) {

        List<Mensagem> listaMensagemBairros = new ArrayList<Mensagem>();

        try {

            List<Bairro> listaBairros = aplEndereco.obterBairrosPorCidade(Long.parseLong(cidadeId));

            for (Bairro bairro : listaBairros) {

                Mensagem mensagem = new Mensagem();

                mensagem.setDado(bairro.getNome());
                mensagem.setId(bairro.getId().toString());

                listaMensagemBairros.add(mensagem);
            }

        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<List<Mensagem>>(listaMensagemBairros, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<List<Mensagem>>(listaMensagemBairros, HttpStatus.OK);

    }

    private Notificacao preencheNotificacao(PacienteForm pacienteForm) {

        Notificacao notificacao = new Notificacao();
        GregorianCalendar dataNotificacao = new GregorianCalendar();
        Notificador notificador;

        notificacao.setInstituicao(aplHospital.obter(usuarioSessao.getIdHospital()));
        notificador = aplPrincipal.obterNotificadorPorUsuarioUsername(usuarioSessao.getCpfUsuario());

        //TODO
        //Primeira etapa feita, mas falta acertar o fato de um notificação ter um setor ou uma instuição como local
        preencherNotificacaoObito(pacienteForm, notificacao);
        preencherNotificacaoEntrevista(pacienteForm, notificacao);
        preencherNotificacaoCaptacao(pacienteForm, notificacao);

        notificacao.setNotificador(notificador);
        notificacao.setDataAbertura(dataNotificacao);

        return notificacao;
    }

    private void preencherNotificacaoObito(PacienteForm pacienteForm, Notificacao notificacao) {

        try {
            preencherNotificacaoObitoAbaPaciente(pacienteForm, notificacao);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        preencherNotificacaoObitoAbaResponsavel(pacienteForm, notificacao);
        preencherNotificacaoObitoAbaObito(pacienteForm, notificacao);
    }

    private void preencherNotificacaoObitoAbaPaciente(PacienteForm pacienteForm, Notificacao notificacao) throws ClassNotFoundException {

        Paciente paciente = new Paciente();
        Endereco endereco = new Endereco();
        Obito obito = new Obito();
        Bairro bairro;
        Cidade cidade;
        Estado estado;

        paciente.setNome(pacienteForm.getNomePaciente());
        paciente.setRg(pacienteForm.getRgPaciente());
        paciente.setNumeroProntuario(pacienteForm.getNumeroProntuario());
        paciente.setDataNascimento(stringToDate(pacienteForm.getDataNascimentoPaciente()));
        paciente.setNomeMae(pacienteForm.getNomeMaePaciente());

        if (pacienteForm.getSexoPaciente() != null && pacienteForm.getSexoPaciente().equals(Sexo.MASCULINO.toString())) {
            paciente.setSexo(Sexo.MASCULINO);
        } else {
            paciente.setSexo(Sexo.FEMININO);
        }

        estado = aplEndereco.obterEstadosPorID(Long.parseLong(pacienteForm.getEstadoPaciente()));
        cidade = aplEndereco.obterCidadePorID(Long.parseLong(pacienteForm.getCidadePaciente()));
        bairro = aplEndereco.obterBairroPorID(Long.parseLong(pacienteForm.getBairroPaciente()));

        /*atribuicoes dos campos*/
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCEP(pacienteForm.getCepPaciente());
        endereco.setComplemento("Complemento");
        endereco.setLogradouro(pacienteForm.getLogradouroPaciente());
        paciente.setEndereco(endereco);

        obito.setPaciente(paciente);

        notificacao.setObito(obito);        
    }

    private void preencherNotificacaoObitoAbaResponsavel(PacienteForm pacienteForm, Notificacao notificacao) {

        Responsavel responsavel = new Responsavel();
        Telefone telefone = new Telefone();
        List<Telefone> telefones = new ArrayList<Telefone>();

        /*atribuicao dos campos*/
        responsavel.setNome(pacienteForm.getNomeResp());
        responsavel.setRg(pacienteForm.getRgResp());
        /*Ainda sem DDD*/
        telefone.setNumero(pacienteForm.getTelResp());
        /*teste*/
        telefone.setTipo(TipoTelefone.RESIDENCIAL);
        telefones.add(telefone);
        responsavel.setTelefones(telefones);

        /*atribuicoes dos campos a notificacao*/
        notificacao.getObito().getPaciente().setResponsavel(responsavel);
        
    }

    private void preencherNotificacaoObitoAbaObito(PacienteForm pacienteForm, Notificacao notificacao) {

        GregorianCalendar dataHorarioObito = new GregorianCalendar();
        Setor setor = this.aplCadastroInterno.obterSetorPorId(Long.parseLong(pacienteForm.getSetorObito()));

        dataHorarioObito.clear();
        dataHorarioObito = stringToDateAndTime(pacienteForm.getDataObito(), pacienteForm.getHorarioObito());

        CausaMortis causaMortis1 = new CausaMortis();
        causaMortis1.setDescricao(pacienteForm.getMotivosObito1());

        CausaMortis causaMortis2 = new CausaMortis();
        causaMortis2.setDescricao(pacienteForm.getMotivosObito2());

        CausaMortis causaMortis3 = new CausaMortis();
        causaMortis3.setDescricao(pacienteForm.getMotivosObito3());

        CausaMortis causaMortis4 = new CausaMortis();
        causaMortis4.setDescricao(pacienteForm.getMotivosObito4());

        /*atribuicoes dos campos a notificacao*/
        notificacao.getObito().setDataObito(dataHorarioObito);
        notificacao.getObito().setPrimeiraCausaMortis(causaMortis1);
        notificacao.getObito().setSegundaCausaMortis(causaMortis2);
        notificacao.getObito().setTerceiraCausaMortis(causaMortis3);
        notificacao.getObito().setQuartaCausaMortis(causaMortis4);

        if (setor != null) {
            notificacao.setSetor(setor);
        }       
    }

    private Notificacao preencherNotificacaoAbaContraindicacao(PacienteForm pacienteForm, Notificacao notificacao) {

        Doacao doacao = new Doacao();

        if(pacienteForm.getContraIndicacoes() != null){
            for (int i = 0; i < pacienteForm.getContraIndicacoes().length; i++) {
                MotivoRecusa contraIndicacao = this.aplMotivoRecusa.obter(Long.parseLong(pacienteForm.getContraIndicacoes()[i]));
                doacao.addMotivoRecusa(contraIndicacao);
            }
        }

        notificacao.getObito().getPaciente().setDoacao(doacao);

        return notificacao;
    }

    private void preencherNotificacaoEntrevista(PacienteForm pacienteForm, Notificacao notificacao) {

        notificacao = preencherNotificacaoAbaContraindicacao(pacienteForm, notificacao);
        notificacao = preencherNotificacaoEntrevistaAbaEntrevista(pacienteForm, notificacao);
        notificacao = preencherNotificacaoEntrevistaAbaResponsavelLegal(pacienteForm, notificacao);
        notificacao = preencherNotificacaoEntrevistaAbaTestemunhas(pacienteForm, notificacao);
    }

    private Notificacao preencherNotificacaoEntrevistaAbaEntrevista(PacienteForm pacienteForm, Notificacao notificacao) {

        //falta salvar o campo: entrevista realizada
        Doacao doacao = notificacao.getObito().getPaciente().getDoacao();

        if (pacienteForm.getDoacaoAutorizada().equals("SIM")) {
            doacao.setAutorizada(true);
        } else {
            doacao.setAutorizada(false);
        }

        Calendar dtEntrevista = this.stringToDate(pacienteForm.getDtEntrevista());
        Calendar hrEntrevista = this.stringToDateAndTime(pacienteForm.getDtEntrevista(), pacienteForm.getHrEntrevista());

        if(pacienteForm.getRecusaFamiliar() != null){
            for (int i = 0; i < pacienteForm.getRecusaFamiliar().length; i++) {

                MotivoRecusa recusaF = this.aplMotivoRecusa.obter(Long.parseLong(pacienteForm.getRecusaFamiliar()[i]));
                doacao.addMotivoRecusa(recusaF);
            }
        }

        doacao.setDataEntrevista(dtEntrevista);
        doacao.setHrEntrevista(hrEntrevista);
        notificacao.getObito().getPaciente().setDoacao(doacao);

        return notificacao;

    }

    private Notificacao preencherNotificacaoEntrevistaAbaResponsavelLegal(PacienteForm pacienteForm, Notificacao notificacao) {

        Responsavel responsavel = new Responsavel();
        Set<Responsavel> responsaveis = new HashSet<>();
        Telefone telefone1 = new Telefone();
        Telefone telefone2 = new Telefone();
        List<Telefone> telefones = new ArrayList<>();

        Endereco endereco = new Endereco();
        Bairro bairro;
        Cidade cidade;
        Estado estado;

        responsavel.setNome(pacienteForm.getNomeRespEntrevista());
        responsavel.setRg(pacienteForm.getRgResponsavelRespDoacao());

        responsavel.setEstadoCivil(EstadoCivil.valueOf(pacienteForm.getEstavoCivilRespDoacao()));
        responsavel.setParentesco(Parentesco.valueOf(pacienteForm.getParentesco()));
        telefone1.setNumero(pacienteForm.getTelefone1Resp());
        telefone1.setTipo(TipoTelefone.RESIDENCIAL);
        telefone2.setNumero(pacienteForm.getTelefone2Resp());
        telefone2.setTipo(TipoTelefone.RESIDENCIAL);
        telefones.add(telefone1);
        telefones.add(telefone2);
        responsavel.setTelefones(telefones);
        responsavel.setProfissao(pacienteForm.getProfissaoRespDoacao());

        responsavel.setNacionalidade(pacienteForm.getNacionalidadeRespDoacao());

        bairro = this.aplEndereco.obterBairroPorID(Long.parseLong(pacienteForm.getBairroRespDoacao()));
        estado = this.aplEndereco.obterEstadosPorID(Long.parseLong(pacienteForm.getEstadoRespDoacao()));
        cidade = this.aplEndereco.obterCidadePorID(Long.parseLong(pacienteForm.getCidadeRespDoacao()));
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setCEP(pacienteForm.getCepRespDoacao());
        endereco.setLogradouro(pacienteForm.getLogradouroRespDoacao());
        endereco.setNumero(pacienteForm.getNumeroRespDoacao());
        responsavel.setEndereco(endereco);
        responsaveis.add(responsavel);

        notificacao.getObito().getPaciente().getDoacao().setResponsaveis(responsaveis);

        return notificacao;
    }

    private Notificacao preencherNotificacaoEntrevistaAbaTestemunhas(PacienteForm pacienteForm, Notificacao notificacao) {

        Testemunha testemunha1 = new Testemunha();
        Testemunha testemunha2 = new Testemunha();
        Set<Testemunha> testemunhas = new HashSet<Testemunha>();

        testemunha1.setCpf(pacienteForm.getCpfTestemunha1());
        testemunha1.setNome(pacienteForm.getNomeTestemunha1());
        testemunhas.add(testemunha1);
        testemunha2.setCpf(pacienteForm.getCpfTestemunha2());
        testemunha2.setNome(pacienteForm.getNomeTestemunha2());
        testemunhas.add(testemunha2);

        notificacao.getObito().getPaciente().getDoacao().setTestemunhas(testemunhas);

        return notificacao;
    }

    private void preencherNotificacaoCaptacao(PacienteForm pacienteForm, Notificacao notificacao) {

        Captacao captacao = new Captacao();

        if (pacienteForm.getCorneasCaptadas().equalsIgnoreCase("sim")) {
            captacao.setCorneasCaptadas(true);
        } else {
            captacao.setCorneasCaptadas(false);
        }

        if (pacienteForm.getCorpoEncaminhado().equalsIgnoreCase("iml")) {
            notificacao.getObito().setEncaminhamento(Encaminhamento.IML);
        } else {
            notificacao.getObito().setEncaminhamento(Encaminhamento.SVO);
        }

        if (pacienteForm.getEquipeCaptacao().equalsIgnoreCase("disponivel")) {
            captacao.setEquipeCaptacaoDisponivel(true);
        } else {
            captacao.setEquipeCaptacaoDisponivel(false);
        }

        if(pacienteForm.getProblemasEstruturais() != null){
            for (Long idEstrutural : pacienteForm.getProblemasEstruturais()) {
                captacao.addMotivoInviabilidade(aplMotivoInviabilidade.buscar(idEstrutural));
            }
        }

        if(pacienteForm.getProblemasLogisticos() != null){
            for (Long idLogistico : pacienteForm.getProblemasLogisticos()) {
                captacao.addMotivoInviabilidade(aplMotivoInviabilidade.buscar(idLogistico));
            }
        }

        captacao.setComentario(pacienteForm.getComentarios());

        notificacao.getObito().getPaciente().getDoacao().setCaptacao(captacao);
    }

    private GregorianCalendar stringToDateAndTime(String data, String horario) {

        int ano, mes, dia, hora, minuto;

        dia = Integer.parseInt(data.substring(0, 2));
        mes = Integer.parseInt(data.substring(3, 5));
        ano = Integer.parseInt(data.substring(6, 10));
        hora = Integer.parseInt(horario.substring(0, 2));
        minuto = Integer.parseInt(horario.substring(4, 5));

        GregorianCalendar novaData = new GregorianCalendar(ano, mes - 1, dia, hora, minuto);

        return novaData;
    }

    private GregorianCalendar stringToDate(String data) {

        int ano, mes, dia;
        //     dd/MM/aaaa
        dia = Integer.parseInt(data.substring(0, 2));
        mes = Integer.parseInt(data.substring(3, 5));
        ano = Integer.parseInt(data.substring(6, 10));

        GregorianCalendar novaData = new GregorianCalendar(ano, mes - 1, dia);

        return novaData;
    }

    private Enum<EstadoCivil> estadoCivilStringEnum(String str) {
        Enum ret;
        switch (str.charAt(0)) {
            case '1':
                ret = EstadoCivil.CASADO;
            //break;

            case '2':
                ret = EstadoCivil.DIVORCIADO;
                break;

            case '3':
                ret = EstadoCivil.SOLTEIRO;
                break;

            case '4':
                ret = EstadoCivil.VIUVO;
                break;
            default:
                ret = EstadoCivil.SOLTEIRO;
                break;
        }
        return ret;
    }

    /**
     * Pega todas as notificações novas (não arquivadas).
     *
     * @return Resumo das notificações
     */
    @RequestMapping(value = "/getNovasNotificacoes", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<ResumoNotificacao>> getNovasNotificacoes() {

        List<ResumoNotificacao> resumos = null;
        try {
            // Pega as notificações não arquivadas.
            List<Notificacao> novasNotificacoes = aplNotificacao.retornarNotificacaoNaoArquivada();

            // Monta os resumos das notificações, para que as notificações sejam facilmente
            // interpretadas pelo AJAX.
            resumos = new ArrayList<>();
            for (Notificacao notificacao : novasNotificacoes) {
                long id = notificacao.getId();
                String codigo = notificacao.getCodigo();
                String paciente = notificacao.getObito().getPaciente().getNome();
                Calendar dataAbertura = notificacao.getDataAbertura();
                resumos.add(new ResumoNotificacao(id, codigo, paciente, dataAbertura));
            }

            // Retorna a resposta.
            return new ResponseEntity<>(resumos, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(resumos, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    private void preencherMotivosInviabilidade(List<MotivoInviabilidade> problemasLogisticos, List<MotivoInviabilidade> problemasEstruturais, ModelMap model) {
       
        List<SelectItem> ProblemaLogisticoItem = new ArrayList<SelectItem>();
        List<SelectItem> ProblemaEstruturalItem = new ArrayList<SelectItem>();
        
        for(MotivoInviabilidade motivoInviabilidade : problemasLogisticos){
            ProblemaLogisticoItem.add(new SelectItem(motivoInviabilidade.getId(), motivoInviabilidade.getNome()));
        }
        
        for(MotivoInviabilidade motivoInviabilidade : problemasEstruturais){
            ProblemaEstruturalItem.add(new SelectItem(motivoInviabilidade.getId(), motivoInviabilidade.getNome()));
        }
        
        model.addAttribute("ProblemaLogisticoItem", ProblemaLogisticoItem);
        model.addAttribute("ProblemaEstruturalItem", ProblemaEstruturalItem);
    }
}
