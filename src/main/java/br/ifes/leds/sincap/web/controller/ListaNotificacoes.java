/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.leds.sincap.web.controller;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Notificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.BuscarPorDataForm;
import br.ifes.leds.sincap.web.model.DoadorForm;
import br.ifes.leds.sincap.web.model.IndexForm;
import br.ifes.leds.sincap.web.model.NotificacaoForm;
import br.ifes.leds.sincap.web.model.PacienteForm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.SessionScoped;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author 20101BSI0534
 */
@Controller
@RequestMapping("/notificacao/visualizar")
@SessionScoped
public class ListaNotificacoes {

    @Autowired
    AplNotificacao aplNotificacao;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String loadForm(@PathVariable long id, ModelMap model) {

        preencherForm(id, model);

        return "exibir-notificacao";
    }

    private void preencherForm(Long id, ModelMap model) {
        NotificacaoForm notificacaoForm = new NotificacaoForm();
        Notificacao notificacao = aplNotificacao.getNotificacao(id);
        DoadorForm doador = new DoadorForm();
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat horaFormatada = new SimpleDateFormat("HH:mm");

        notificacaoForm.setId(id.toString());
        //Montando o formulário de notificação
        //Inserindo as CausasMortis
        notificacaoForm.setCausaMortis1(notificacao.getObito().getPrimeiraCausaMortis().getDescricao());
        notificacaoForm.setCausaMortis2(notificacao.getObito().getSegundaCausaMortis().getDescricao());
        notificacaoForm.setCausaMortis3(notificacao.getObito().getTerceiraCausaMortis().getDescricao());
        notificacaoForm.setCausaMortis4(notificacao.getObito().getQuartaCausaMortis().getDescricao());

        //Inserindo outros atributos
        notificacaoForm.setInstituicao(notificacao.getInstituicao().getNome());
        notificacaoForm.setUf(notificacao.getInstituicao().getEndereco().getEstado().getSigla());
        notificacaoForm.setDataNotificacao(dataFormatada.format(notificacao.getDataAbertura().getTime()));
        notificacaoForm.setHoraNotificacao(horaFormatada.format(notificacao.getDataAbertura().getTime()));
        notificacaoForm.setNomePaciente(notificacao.getObito().getPaciente().getNome());
        notificacaoForm.setProtocolo(notificacao.getCodigo());

        //Inserindo dados do doador
        //Calculando a idade
        long abertura = notificacao.getObito().getPaciente().getDataNascimento().getTimeInMillis();
        long agora = notificacao.getObito().getDataObito().getTimeInMillis();

        DateTime horaNasc = new DateTime(abertura);
        DateTime horaMorte = new DateTime(agora);

        Integer idade = Years.yearsBetween(horaNasc, horaMorte).getYears();

        doador.setIdade(idade.toString());
        doador.setNome(notificacao.getObito().getPaciente().getNome());
        try {
            doador.setTelefone(notificacao.getObito().getPaciente().getResponsavel().getTelefones().get(0).toString());
        } catch (IndexOutOfBoundsException e) {
            doador.setTelefone(null);
        }

        model.addAttribute("notificacaoForm", notificacaoForm);
        model.addAttribute("doador", doador);
    }

    @RequestMapping(value = "/arquivar", method = RequestMethod.POST)
    public String addNotificacao(@ModelAttribute NotificacaoForm notificacaoForm, ModelMap model)
            throws Exception {

        Notificacao notificacao = aplNotificacao.getNotificacao(new Long(notificacaoForm.getId()));

        notificacao.setDataArquivamento(Calendar.getInstance(Locale.ENGLISH));
        
        aplNotificacao.arquivar(notificacao);

        return "redirect:/notificacao/visualizar/" + notificacaoForm.getId();
    }
    
    public List<IndexForm> preencherListaNotificacoesForm(List<Notificacao> notificacoes)
    {
        List<IndexForm> listaNotificacoesForm = new ArrayList<>();
        for (Notificacao notificacao : notificacoes) {
            IndexForm indexForm = new IndexForm(
                    notificacao.getId().toString(),
                    notificacao.getCodigo(),
                    notificacao.getDataAbertura().getTime(),
                    notificacao.getObito().getDataObito().getTime(),
                    notificacao.getObito().getPaciente().getNome(),
                    notificacao.getSetor().getHospital().toString());

            listaNotificacoesForm.add(indexForm);
        }
        
        return listaNotificacoesForm;
    }
    
    
    @RequestMapping(value = "/arquivadas")
    public String retornarArquivadas(ModelMap model) {
        List<Notificacao> notificacoes;
        notificacoes = aplNotificacao.retornarNotificacaoArquivada();
        List<IndexForm> listaNotificacoesForm  = preencherListaNotificacoesForm(notificacoes);
                
        model.addAttribute("listaNotificacoesForm", listaNotificacoesForm);
        
        return "index";
    }
    
    @RequestMapping(value = "/todas")
    public String retornarTodas(ModelMap model) {
        List<Notificacao> notificacoes;
        notificacoes = aplNotificacao.retornarTodasNotificacoes();
        List<IndexForm> listaNotificacoesForm = preencherListaNotificacoesForm(notificacoes);
    
                
        model.addAttribute("listaNotificacoesForm", listaNotificacoesForm);
        
        return "index";
    }
    
       
    @RequestMapping(value = "/busca")
    public String loadPaginaBusca(ModelMap model)
    {
        BuscarPorDataForm buscarPorDataForm = new BuscarPorDataForm();
        model.addAttribute("buscarPorDataForm", buscarPorDataForm);
        
        return "form-buscarnotificacaodata";
    }
    
    @RequestMapping(value = "/busca/pordata", method = RequestMethod.POST)
    public String retornarPorData(@ModelAttribute BuscarPorDataForm buscarPorDataForm, ModelMap model){
        Calendar dataIni, dataFim;
        dataIni = stringToDate(buscarPorDataForm.getDataIni());
        dataFim = stringToDate(buscarPorDataForm.getDataFim());
        List<Notificacao> notificacoes = aplNotificacao.retornarNotificacaoPorData(dataIni, dataFim);
        List<IndexForm> listaNotificacoesForm = preencherListaNotificacoesForm(notificacoes);
        model.addAttribute("listaNotificacoesForm", listaNotificacoesForm);
        return "index";
    }
    
    private Calendar stringToDate(String data){
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            cal.setTime(sdf.parse(data));
            } catch (Exception e) {
            e.printStackTrace();
            }
        return cal;
    }
}
