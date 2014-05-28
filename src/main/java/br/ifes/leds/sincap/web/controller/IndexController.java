package br.ifes.leds.sincap.web.controller;


import br.ifes.leds.sincap.gerenciaNotificacao.cln.cdp.Notificacao;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.ifes.leds.sincap.gerenciaNotificacao.cln.cgt.AplNotificacao;
import br.ifes.leds.sincap.web.model.IndexForm;
import br.ifes.leds.sincap.web.model.UsuarioSessao;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

@Controller
@RequestMapping("/index")
@SessionScoped
public class IndexController {

    @Autowired
    AplNotificacao aplNotificacao;
     @Autowired
    UsuarioSessao usuarioSessao;

    @RequestMapping(method = RequestMethod.GET)
    public String loadForm(ModelMap model) {

        IndexForm indexForm = new IndexForm();
        
       

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // JavaScript code in a String
        String script = "function hello(name) { alert('Hello, ' + name); }";
        String script2 = "function hide(id) {\n" +
"    document.getElementById(id).style.display = 'none';\n" +
"}";
        try {
            // evaluate script
            engine.eval(script2);
        } catch (ScriptException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // javax.script.Invocable is an optional interface.
        // Check whether your script engine implements or not!
        // Note that the JavaScript engine implements Invocable interface.
        Invocable inv = (Invocable) engine;
        try {
            // invoke the global function named "hello"
            inv.invokeFunction("hide", "principal" );
        } catch (ScriptException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(IndexController.class.getName()).log(Level.SEVERE, null, ex);
        }

        preencherForm(model);
        model.addAttribute("indexForm", indexForm);

        return "index";
    }

    private void preencherForm(ModelMap model) {
        List<Notificacao> notificacoes;
        List<IndexForm> listaNotificacoesForm = new ArrayList<>();

        notificacoes = aplNotificacao.retornarNotificacaoNaoArquivada();
        
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

        model.addAttribute("listaNotificacoesForm", listaNotificacoesForm);
    }
}
