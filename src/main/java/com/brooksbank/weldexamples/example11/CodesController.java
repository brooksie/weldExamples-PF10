/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import com.brooksbank.weldexamples.db.Codes;
import com.brooksbank.weldexamples.db.CodesService;
import com.brooksbank.weldexamples.db.Codetypes;
import com.brooksbank.weldexamples.db.CodetypesService;
import java.io.Serializable;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class CodesController implements Serializable {

    @Inject
    private CodesService service;
    
    @Inject 
    private CodetypesService ctService;
    
    @Named
    @Produces
    @ViewScoped
    private Codes codeBean = new Codes();
    
    private String mode = "???";
    
    private Codetypes codetypeBean = null;
    private boolean codetypeEditable = false;
    private List<Codetypes> codetypeList = null;
    
    private boolean extra1Present = false;
    private boolean extra2Present = false;
    private boolean extra3Present = false;
    private String extra1Name;
    private String extra2Name;
    private String extra3Name;
    
//    @Inject @SessionMap
//    private Map<String, Object> sessionMap;
    
    /**
     * Creates a new instance of CodesController
     */
    public CodesController() {
    }
    
    @PostConstruct
    public void init() {
        // first get the dialog parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];
            

            String[] codeidList = (String[]) paramMap.get("codeid");
            if (codeidList != null) {
                codeBean = service.find(Integer.parseInt(codeidList[0]));
            }

            String[] codetypecodeList = (String[]) paramMap.get("codetypecode");
            if (codetypecodeList != null) {
                codetypeBean = ctService.findByCodetype(codetypecodeList[0]);
            }
        }
        if ( "Add".equals(mode)) {
            if (codetypeBean == null) {
                codetypeEditable = true;
                codetypeList = ctService.findAll();
            } else {
                codeBean.setCodetypeid(codetypeBean);
            }
            // set default ValidFrom and ValidTo dates
            codeBean.setValidfrom(Date.from(
                    LocalDate.now()         // LocalDate
                            .atStartOfDay() // --> LocalDateTime
                            .atZone(ZoneId.systemDefault()) //  --> ZonedDateTime
                            .toInstant()    // --> Instant
            ));
            codeBean.setValidto(Date.from(
                    LocalDate.of(3000, Month.JANUARY, 1)
                            .atStartOfDay()
                            .atZone(ZoneId.systemDefault())
                            .toInstant()
            ));
        }
        codetypeChanged();
    }

    public String getMode() {
        return mode;
    }

    public boolean isCodetypeEditable() {
        return codetypeEditable;
    }

    public List<Codetypes> getCodetypeList() {
        return codetypeList;
    }

    public boolean isExtra1Present() {
        return extra1Present;
    }

    public boolean isExtra2Present() {
        return extra2Present;
    }

    public boolean isExtra3Present() {
        return extra3Present;
    }

    public String getExtra1Name() {
        return extra1Name;
    }

    public String getExtra2Name() {
        return extra2Name;
    }

    public String getExtra3Name() {
        return extra3Name;
    }
    
    
    /**
     * codetype field has been updated
     */
    public void codetypeChanged() {
        codetypeBean = codeBean.getCodetypeid();
        extra1Present = (codetypeBean != null && !isNullOrBlank(codetypeBean.getExtra1name()));
        extra2Present = (codetypeBean != null && !isNullOrBlank(codetypeBean.getExtra2name()));
        extra3Present = (codetypeBean != null && !isNullOrBlank(codetypeBean.getExtra3name()));
        extra1Name = (codetypeBean == null ? null : codetypeBean.getExtra1name());
        extra2Name = (codetypeBean == null ? null : codetypeBean.getExtra2name());
        extra3Name = (codetypeBean == null ? null : codetypeBean.getExtra3name());
    }
    
    public void addCode() {
        service.create(codeBean);
        PrimeFaces.current().dialog().closeDynamic(codeBean);
    }
    
    public void editCode() {
        service.edit(codeBean);
        PrimeFaces.current().dialog().closeDynamic(codeBean);
    }
    
    public void deleteCode() {
        service.remove(codeBean);
        PrimeFaces.current().dialog().closeDynamic(codeBean);
    }
    
    
    
    public void validateNewCode(FacesContext context, UIComponent component, String newValue) {
        if (isNullOrBlank(newValue)) {
            return;     // ignore null values
        }

        // part 1: does the code already exists?
        
        if ( this.codeBean == null || this.codeBean.getCodetypeid() == null) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "codetype is missing", null));
        }
        String ct = this.codeBean.getCodetypeid().getCodetype();
        Codes existingCode = service.findByCodetypeAndCode(ct, newValue);
        
        if (existingCode != null) {
            // code already exists
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "code already exists", null));
        }

        // part 2: does the code match the defined regex?
        
        String patternString = this.codeBean.getCodetypeid().getRegex();
        if (!isNullOrBlank(patternString) ) {
            validateRegex(newValue, patternString);
        }
    }

    public void validateExtra1(FacesContext context, UIComponent component, String newValue) {
        if (isNullOrBlank(newValue)) {
            return;     // ignore null values
        }
        String patternString = this.codeBean.getCodetypeid().getExtra1regex();
        if (!isNullOrBlank(patternString) ) {
            validateRegex(newValue, patternString);
        }
    }

    public void validateExtra2(FacesContext context, UIComponent component, String newValue) {
        if (isNullOrBlank(newValue)) {
            return;     // ignore null values
        }
        String patternString = this.codeBean.getCodetypeid().getExtra2regex();
        if (!isNullOrBlank(patternString) ) {
            validateRegex(newValue, patternString);
        }
    }

    public void validateExtra3(FacesContext context, UIComponent component, String newValue) {
        if (isNullOrBlank(newValue)) {
            return;     // ignore null values
        }
        String patternString = this.codeBean.getCodetypeid().getExtra3regex();
        if (!isNullOrBlank(patternString) ) {
            validateRegex(newValue, patternString);
        }
    }

    private void validateRegex(String newValue, String patternString) {
        if (isNullOrBlank(newValue) || isNullOrBlank(patternString)) {
            return;     // ignore null values
        }

        Pattern p = Pattern.compile(patternString);
        Matcher m = p.matcher(newValue);
        if (!m.matches()) {
            // newValue does not match the defined pattern
            String msg = MessageFormat.format("does not match {0}", patternString);
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            msg, null));
        }
    }

    public void validateValidTo(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return;     // ignore null values
        }

        Date newValidTo = (Date) value;
        if ( codeBean.getValidfrom().compareTo(newValidTo) >= 0 ) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "date must be after ValidFrom", null));
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
