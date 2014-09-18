package br.ifes.leds.sincap.web.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import java.util.TimeZone;

@Aspect
@Component
public class DefaultTimeZone {

    @After("execution(* br.ifes.leds.sincap.web..*(org.springframework.ui.ModelMap,..)) && " +
            "args(modelMap,..) && " +
            "@annotation(br.ifes.leds.sincap.web.annotations.DefaultTimeZone)")
    public void addDefaultTimeZoneToModel(ModelMap modelMap) {
        modelMap.addAttribute("timeZone", TimeZone.getDefault());
    }
}
