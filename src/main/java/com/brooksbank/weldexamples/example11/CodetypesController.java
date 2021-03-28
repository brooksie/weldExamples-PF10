/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import com.brooksbank.weldexamples.db.Codetypes;
import com.brooksbank.weldexamples.db.CodetypesService;
import java.io.Serializable;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class CodetypesController implements Serializable {
    
    @Inject
    private CodetypesService service;
    
    @Inject
    private FacesContext facesContext;
    
    @Named
    @Produces
    @ViewScoped
    private Codetypes codetypeBean = new Codetypes();
    
    private String mode = "???";
    
    private boolean extra1RegexDisabled = true;
    private boolean extra2RegexDisabled = true;
    private boolean extra3RegexDisabled = true;
    
//    @Inject @SessionMap
//    private Map<String, Object> sessionMap;
    
    /**
     * Creates a new instance of CodetypesController
     */
    public CodetypesController() {
    }
    
    @PostConstruct
    public void init() {
//        codetypeList = service.findAll();
        // first get the dlg parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];
            

            String[] codetypeList = (String[]) paramMap.get("codetype");
            if (codetypeList != null) {
                codetypeBean = service.findByCodetype(codetypeList[0]);
            }
        }
        extra1NameChanged();
        extra2NameChanged();
        extra3NameChanged();
    }

    public String getMode() {
        return mode;
    }

    public boolean isExtra1RegexDisabled() {
        return extra1RegexDisabled;
    }

    public boolean isExtra2RegexDisabled() {
        return extra2RegexDisabled;
    }

    public boolean isExtra3RegexDisabled() {
        return extra3RegexDisabled;
    }
    
    public void extra1NameChanged() {
        extra1RegexDisabled = isNullOrBlank(codetypeBean.getExtra1name());
    }
    public void extra2NameChanged() {
        extra2RegexDisabled = isNullOrBlank(codetypeBean.getExtra2name());
    }
    public void extra3NameChanged() {
        extra3RegexDisabled = isNullOrBlank(codetypeBean.getExtra3name());
    }
    
    
    public void addCodetype() {
        service.create(codetypeBean);
//        String msg = MessageFormat.format("{0} code type created", codetypeBean.getCodetype());
//        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, null));

        PrimeFaces.current().dialog().closeDynamic(codetypeBean);

    }
    
    public void editCodetype() {
        service.edit(codetypeBean);
//        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Codetype updated", null));

        PrimeFaces.current().dialog().closeDynamic(codetypeBean);
    }
    
    public void deleteCodetype() {
        service.remove(codetypeBean);
//        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Codetype deleted", null));

        PrimeFaces.current().dialog().closeDynamic(codetypeBean);
    }
    
    public void validateNewCodetype(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        Codetypes codetype = service.findByCodetype(value);
        if (codetype != null) {
            // codetype already exists
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "codetype already exists", null));
        }
    }

    public void validateRegexPattern(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        // compile the regex and catch the exception
        try {
            Pattern.compile(value);
        } catch (PatternSyntaxException ex) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "not a valid regex pattern", null));
        }
    }

    public void validateExtra1Name(FacesContext context, UIComponent component, String value) {
        // name cannot be null if Regex is specified
        if (isNullOrBlank(value) && !isNullOrBlank(codetypeBean.getExtra1regex())) {
            regexNameError();
        }
    }
    public void validateExtra2Name(FacesContext context, UIComponent component, String value) {
        // name cannot be null if Regex is specified
        if (isNullOrBlank(value) && !isNullOrBlank(codetypeBean.getExtra2regex())) {
            regexNameError();
        }
    }
    public void validateExtra3Name(FacesContext context, UIComponent component, String value) {
        // name cannot be null if Regex is specified
        if (isNullOrBlank(value) && !isNullOrBlank(codetypeBean.getExtra3regex())) {
            regexNameError();
        }
    }

    private void regexNameError() {
        throw new ValidatorException(
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "name must be specified when regex pattern is present", null));
    }


    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
