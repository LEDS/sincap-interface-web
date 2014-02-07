package br.ifes.leds.sincap.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Notificacao;
import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.IndexForm;

@Controller
@RequestMapping("/index")
@SessionScoped
public class IndexController {
	
	@Autowired
	AplNotificacao aplNotificacao;
	
	@RequestMapping(method = RequestMethod.GET)
	public String loadForm(ModelMap model) {
		
		IndexForm indexForm = new IndexForm();
		
		try {
			//preencherTelaPrincipal(indexForm); ---------------------
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		model.addAttribute("indexForm", indexForm);
		
		return "index";
	}
	
//	private void preencherTelaPrincipal(IndexForm indexForm) throws Exception -----------------------
//	{
//		
//		List<Notificacao> notificacoes = aplNotificacao.obterNotificacao();
//		List<IndexForm> listaNotificacoes = new ArrayList<IndexForm>();
//		SimpleDateFormat s = new SimpleDateFormat("dd/MM/yyyy"); 
//		
//		for(Notificacao notificacao : notificacoes){
//			IndexForm elementoIndexForm = new IndexForm(
//					notificacao.getCodigo(), s.format(notificacao
//							.getDataNotificacao().getTime()), notificacao.getObito()
//							.getPaciente().getNome(), notificacao
//							.getObito().getSetor().getNome());
//			listaNotificacoes.add(elementoIndexForm);
//		}
//		
//		indexForm.setListaNotificacoes(listaNotificacoes);
//
//	}	
}