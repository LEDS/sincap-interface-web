package br.ifes.leds.sincap.web.utility;

import br.ifes.leds.reuse.endereco.cdp.Bairro;
import br.ifes.leds.reuse.endereco.cdp.Cidade;
import br.ifes.leds.reuse.endereco.cdp.Estado;
import br.ifes.leds.reuse.endereco.cdp.dto.EnderecoDTO;
import br.ifes.leds.reuse.endereco.cgt.AplEndereco;
import br.ifes.leds.reuse.persistence.ObjetoPersistente;
import br.ifes.leds.sincap.controleInterno.cln.cdp.BancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cdp.InstituicaoNotificadora;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplBancoOlhos;
import br.ifes.leds.sincap.controleInterno.cln.cgt.AplInstituicaoNotificadora;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.*;
import br.ifes.leds.sincap.web.controller.ContextUrls;
import br.ifes.leds.sincap.web.model.MensagemProcesso;
import br.ifes.leds.sincap.web.model.NotificacaoDTO;
import org.joda.time.*;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.FieldError;

import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.DateFormat;
import java.util.*;

import static org.joda.time.Hours.hoursBetween;

@Component
public class UtilityWeb {

    @Autowired
    private AplEndereco aplEndereco;

    public void addConstraintViolations(ConstraintViolationException e, ModelMap model) {
        ConstraintViolation<?>[] constraintViolations = new ConstraintViolation<?>[e.getConstraintViolations().size()];
        e.getConstraintViolations().toArray(constraintViolations);
        model.addAttribute("constraintViolations", constraintViolations);
        model.addAttribute("erro", true);
    }

    public void addConstraintViolations(Set<? extends ConstraintViolation<?>> constraintViolations, ModelMap model) {
        if (constraintViolations != null && constraintViolations.size() > 0) {
            ConstraintViolation<?>[] constraintViolationsArray = new ConstraintViolation<?>[constraintViolations.size()];
            constraintViolations.toArray(constraintViolationsArray);
            model.addAttribute("constraintViolations", constraintViolationsArray);
            model.addAttribute("erro", true);
        }
    }

    public void addConstraintViolations(List<? extends FieldError> errors, ModelMap modelMap) {
        FieldError[] fieldErrors = new FieldError[errors.size()];
        errors.toArray(fieldErrors);
        modelMap.addAttribute("erro", true)
                .addAttribute("fieldErrors", fieldErrors);
    }

    public void preencherEndereco(EnderecoDTO endereco, ModelMap model) {
        preencherEstados(model);
        try {
            preencherCidades(endereco.getEstado(), model);
            preencherBairros(endereco.getCidade(), model);
        } catch (NullPointerException ignored) {
        }

    }

    public void preencherEstados(ModelMap model) {

        List<Estado> listaEstados;
        List<SelectItem> listaEstadoItem = new ArrayList<>();

        listaEstados = aplEndereco.obterEstadosPorNomePais("Brasil");

        for (Estado estado : listaEstados) {
            SelectItem estadoItem = new SelectItem(estado.getId(),
                    estado.getNome());
            listaEstadoItem.add(estadoItem);
        }

        model.addAttribute("listaEstadoItem", listaEstadoItem);
    }

    public void preencherCidades(Long idEstado, ModelMap model) {
        List<Cidade> listaCidades;
        List<SelectItem> listaCidadeItem = new ArrayList<>();

        listaCidades = aplEndereco.obterCidadesPorEstado(idEstado);

        for (Cidade cidade : listaCidades) {
            SelectItem cidadeItem = new SelectItem(cidade.getId(),
                    cidade.getNome());
            listaCidadeItem.add(cidadeItem);
        }

        model.addAttribute("listaCidadeItem", listaCidadeItem);
    }

    public void preencherBairros(Long idCidade, ModelMap model) {
        List<Bairro> listaBairros;
        List<SelectItem> listaBairroItem = new ArrayList<>();

        listaBairros = aplEndereco.obterBairrosPorCidade(idCidade);

        for (Bairro bairro : listaBairros) {
            SelectItem cidadeItem = new SelectItem(bairro.getId(),
                    bairro.getNome());
            listaBairroItem.add(cidadeItem);
        }

        model.addAttribute("listaBairroItem", listaBairroItem);
    }

    public List<SelectItem> getParentescoSelectItem() {
        List<SelectItem> parentescos = new ArrayList<>();

        for (Parentesco parentesco : Parentesco.values()) {
            parentescos
                    .add(new SelectItem(parentesco, parentesco.name()));
        }

        return parentescos;
    }

    public List<SelectItem> getTipoDocumentoComFotoSelectItem() {
        List<SelectItem> tipos = new ArrayList<>();

        for (TipoDocumentoComFoto tipo : TipoDocumentoComFoto.values()) {
            tipos
                    .add(new SelectItem(tipo, tipo.name()));
        }

        return tipos;
    }

    public List<SelectItem> getCorpoEncaminhamento(){
        List<SelectItem> listEncaminhamento = new ArrayList<>();

        for (CorpoEncaminhamento encaminhento : CorpoEncaminhamento.values()) {
            listEncaminhamento.add(new SelectItem(encaminhento,encaminhento.name()));
        }

        return listEncaminhamento;
    }


    public List<SelectItem> getTipoObitoSelectItem() {
        List<SelectItem> tipos = new ArrayList<>();

        for (TipoObito tipo : TipoObito.values()) {
            tipos
                    .add(new SelectItem(tipo, tipo.name()));
        }

        return tipos;
    }

    public void preencherTipoObito(ModelMap model) {
        model.addAttribute("tiposObito", this.getTipoObitoSelectItem());
    }


    public List<SelectItem> getEstadoCivilSelectItem() {
        List<SelectItem> estadosCivis = new ArrayList<>();

        for (EstadoCivil estadoCivil : EstadoCivil.values()) {
            estadosCivis
                    .add(new SelectItem(estadoCivil, estadoCivil.name()));
        }

        return estadosCivis;
    }

    public List<SelectItem> getTipoNaoDoacaoSelectItem() {
        List<SelectItem> tiposNaoDoacao = new ArrayList<>();

        for (TipoNaoDoacao tipoNaoDoacao : TipoNaoDoacao.values()) {
            tiposNaoDoacao
                    .add(new SelectItem(tipoNaoDoacao, tipoNaoDoacao.name()));
        }

        return tiposNaoDoacao;
    }

    public List<SelectItem> getEscolaridadeSelectItem() {
        List<SelectItem> escolaridadeSelectItem = new ArrayList<>();

        for (Escolaridade escolaridade : Escolaridade.values()) {
            escolaridadeSelectItem
                    .add(new SelectItem(escolaridade, escolaridade.name()));
        }

        return escolaridadeSelectItem;
    }

    public void getBancoOlhos(ModelMap model, AplBancoOlhos aplBancoOlhos) {

        List<BancoOlhos> listaBancoOlhos;
        List<SelectItem> listaBancoOlhosItem = new ArrayList<>();

        listaBancoOlhos = aplBancoOlhos.obter();

        for (BancoOlhos bancoOlhos : listaBancoOlhos) {
            SelectItem bancoItem = new SelectItem(bancoOlhos.getId(),
                    bancoOlhos.getNome());
            listaBancoOlhosItem.add(bancoItem);
        }

        model.addAttribute("listaBancoOlhosItem", listaBancoOlhosItem);
    }

    @SuppressWarnings("unused")
    public void getInstituicaoNotificadora(ModelMap model, AplInstituicaoNotificadora aplInstituicaoNotificadora) {

        List<InstituicaoNotificadora> listaInstituicaoNotificadora;
        List<SelectItem> listaInstituicaoNotificadoraItem = new ArrayList<>();

        listaInstituicaoNotificadora = aplInstituicaoNotificadora.obterTodasInstituicoesNotificadoras();

        for (InstituicaoNotificadora instituicaoNotificadora : listaInstituicaoNotificadora) {
            SelectItem instituicaoNotificadoraItem = new SelectItem(instituicaoNotificadora.getId(),
                    instituicaoNotificadora.getNome());
            listaInstituicaoNotificadoraItem.add(instituicaoNotificadoraItem);
        }

        model.addAttribute("listaInstituicaoNotificadoraItem", listaInstituicaoNotificadoraItem);
    }

    public <T extends ObjetoPersistente, E extends ObjetoPersistente> Map<Long, Boolean> getLongBooleanMap(Collection<E> colecaoPadrao, Collection<T> colecaoAtual) {
        Map<Long, Boolean> mapEstahNaColecaoAtual = new HashMap<>();

        for (E instituicao : colecaoPadrao) {
            mapEstahNaColecaoAtual.put(instituicao.getId(), Boolean.FALSE);
        }

        for (T instituicao : colecaoAtual) {
            mapEstahNaColecaoAtual.put(instituicao.getId(), Boolean.TRUE);
        }

        return mapEstahNaColecaoAtual;
    }

    public static List<String> authoritiesSetToStringList(Collection<? extends GrantedAuthority> authorities) {
        List<String> auth = new ArrayList<>();

        for (GrantedAuthority grantedAuthority : authorities) {
            auth.add(grantedAuthority.toString());
        }

        return auth;
    }


    public List<MensagemProcesso> ProcessoToMensagem(List<ProcessoNotificacao> notificacoesInteressados) {
        List<MensagemProcesso> mensagens = new ArrayList<>();
        DateFormat dfo = new java.text.SimpleDateFormat("HH:mm:ss");

        for (ProcessoNotificacao notificacao : notificacoesInteressados) {
            MensagemProcesso mensagem = new MensagemProcesso();

            mensagem.setId(notificacao.getId());
            mensagem.setCodigo(notificacao.getCodigo());
            mensagem.setEstado(notificacao.getUltimoEstado().getEstadoNotificacao().getNome());
            mensagem.setTempo(this.formatToHHMMSSTString(notificacao.getDataAbertura()));
            criarUrlRelativa(mensagem);
            mensagens.add(mensagem);
        }

        return mensagens;
    }

    private void criarUrlRelativa(MensagemProcesso mensagem){
        //Criando a URL relativa ao objeto
        String status = mensagem.getEstado();
        String partialUrl = "/sincap";

        String obito = "Óbito";
        String entrevista = "Entrevista";
        String captacao = "Captação";
        String correcao = "Correção";
        String analise = "Análise";

        /*Criar link para notificações de óbito*/
        if(status.contains(obito)){
            partialUrl+= ContextUrls.APP_NOTIFICACAO_OBITO;
            if(status.contains(correcao)){
                partialUrl+=ContextUrls.EDITAR;
            }else if(status.contains(analise)){
                partialUrl+=ContextUrls.APP_ANALISAR;
            }
        /*Criar link para notificações de entrevista*/
        }else if(status.contains(entrevista)){
            partialUrl+=ContextUrls.APP_NOTIFICACAO_ENTREVISTA;
            if(status.contains(analise)){
                partialUrl+=ContextUrls.APP_ANALISAR;
            }else if(status.contains(correcao)){
                partialUrl+=ContextUrls.EDITAR;
            }else{
                partialUrl+=ContextUrls.ADICIONAR;
            }
        /*Criar link para notificações de captação*/
        }else if(status.contains(captacao)){
            partialUrl+=ContextUrls.APP_NOTIFICACAO_CAPTACAO;
            if(status.contains(analise)){
                partialUrl+=ContextUrls.APP_ANALISAR;
            }else if(status.contains(correcao)){
                partialUrl+=ContextUrls.EDITAR;
            }else{
                partialUrl+=ContextUrls.ADICIONAR;
            }
        }

        mensagem.setUrlRelativa(partialUrl+"/"+mensagem.getId());

    }

    private void criarUrlRelativa(NotificacaoDTO mensagem){
        //Criando a URL relativa ao objeto
        String status = mensagem.getEstado();
        String partialUrl = "/sincap";

        String obito = "Óbito";
        String entrevista = "Entrevista";
        String captacao = "Captação";
        String correcao = "Correção";
        String analise = "Análise";

        /*Criar link para notificações de óbito*/
        if(status.contains(obito)){
            partialUrl+= ContextUrls.APP_NOTIFICACAO_OBITO;
            if(status.contains(correcao)){
                partialUrl+=ContextUrls.EDITAR;
            }else if(status.contains(analise)){
                partialUrl+=ContextUrls.APP_ANALISAR;
            }
        /*Criar link para notificações de entrevista*/
        }else if(status.contains(entrevista)){
            partialUrl+=ContextUrls.APP_NOTIFICACAO_ENTREVISTA;
            if(status.contains(analise)){
                partialUrl+=ContextUrls.APP_ANALISAR;
            }else if(status.contains(correcao)){
                partialUrl+=ContextUrls.EDITAR;
            }else{
                partialUrl+=ContextUrls.ADICIONAR;
            }
        /*Criar link para notificações de captação*/
        }else if(status.contains(captacao)){
            partialUrl+=ContextUrls.APP_NOTIFICACAO_CAPTACAO;
            if(status.contains(analise)){
                partialUrl+=ContextUrls.APP_ANALISAR;
            }else if(status.contains(correcao)){
                partialUrl+=ContextUrls.EDITAR;
            }else{
                partialUrl+=ContextUrls.ADICIONAR;
            }
        }

        mensagem.setUrlRelativa(partialUrl+"/"+mensagem.getId());

    }

    public int getIdade(Date dataNasc, Date dataObito) {
        //Cria um objeto com a data de nascimento
        Calendar dateOfBirth = new GregorianCalendar();
        dateOfBirth.setTime(dataNasc);

        // Cria um objeto calendar com a data de obito

        Calendar obito = new GregorianCalendar();
        obito.setTime(dataObito);

        // Obtém a idade baseado no ano

        int idade = obito.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);

        dateOfBirth.add(Calendar.YEAR, idade);

        //se a data de obito é antes da data de Nascimento, então diminui 1(um)

        if (obito.before(dateOfBirth)) {

            idade--;

        }

        return idade;
    }

    public String formatToHHMMSSTString(Calendar dataAbertura){
        DateTime abertura = new DateTime(dataAbertura);
        DateTime now = DateTime.now();
        Period period = new Period(abertura, now);
        PeriodFormatter HHMMSSFormater = new PeriodFormatterBuilder()
                .printZeroAlways()
                .minimumPrintedDigits(2)
                .appendHours().appendSeparator(":")
                .appendMinutes().appendSeparator(":")
                .appendSeconds().
        toFormatter(); // produce thread-safe formatter
        return HHMMSSFormater.print(period);
    }

    public List<NotificacaoDTO> ProcessoToNotificacaoDTO(List<ProcessoNotificacao> notificacoes) {
        List<NotificacaoDTO> notificacoesDTO = new ArrayList<>();
        DateFormat dmy = new java.text.SimpleDateFormat("dd/MM/yyyy");
        DateFormat hr = new java.text.SimpleDateFormat("HH:mm");

        for (ProcessoNotificacao notificacao : notificacoes) {
            NotificacaoDTO notificacaoDTO = new NotificacaoDTO();

            notificacaoDTO.setProtocolo(notificacao.getCodigo());
            notificacaoDTO.setDataNotificacao(dmy.format(notificacao.getDataAbertura().getTime()));
            notificacaoDTO.setHoraNotificacao(hr.format(notificacao.getDataAbertura().getTime()));
            notificacaoDTO.setPaciente(notificacao.getObito().getPaciente().getNome());
            notificacaoDTO.setDataObito(dmy.format(notificacao.getObito().getDataObito().getTime()));
            notificacaoDTO.setHoraObito(hr.format(notificacao.getObito().getDataObito().getTime()));
            notificacaoDTO.setHospital(notificacao.getObito().getHospital().getNome());
            notificacaoDTO.setNotificador(notificacao.getNotificador().getNome());
            notificacaoDTO.setId(notificacao.getId());
            notificacaoDTO.setEstado(notificacao.getUltimoEstado().getEstadoNotificacao().getNome());

            this.criarUrlRelativa(notificacaoDTO);

            notificacoesDTO.add(notificacaoDTO);
        }

        return notificacoesDTO;
    }

}
