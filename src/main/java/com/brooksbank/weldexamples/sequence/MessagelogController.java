/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.sequence;

import com.brooksbank.weldexamples.db.Messagelog;
import com.brooksbank.weldexamples.db.MessagelogService;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
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
public class MessagelogController implements Serializable {

    @Inject
    private MessagelogService service;
    
    @Named
    @Produces
    @ViewScoped
    private Messagelog messagelogBean = new Messagelog();
    
    private String mode = "???";
    
    
    @PostConstruct
    public void init() {
        // first get the dialog parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];
            
            String[] messagelogidList = (String[]) paramMap.get("messagelogid");
            if (messagelogidList != null) {
                messagelogBean = service.find(Long.parseLong(messagelogidList[0]));
            }
        }
    }

    public String getMode() {
        return mode;
    }

    
    public void addCode() {
        service.create(messagelogBean);
        PrimeFaces.current().dialog().closeDynamic(messagelogBean);
    }
    
    public void editCode() {
        service.edit(messagelogBean);
        PrimeFaces.current().dialog().closeDynamic(messagelogBean);
    }
    
    public void deleteCode() {
        service.remove(messagelogBean);
        PrimeFaces.current().dialog().closeDynamic(messagelogBean);
    }
    
    
    
//    public void validateNewCode(FacesContext context, UIComponent component, String newValue) {
//        if (isNullOrBlank(newValue)) {
//            return;     // ignore null values
//        }
//
//        if ( this.messagelogBean == null || this.messagelogBean.getCodetypeid() == null) {
//            throw new ValidatorException(
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
//                            "codetype is missing", null));
//        }
//    }

//    private static boolean isNullOrBlank(String s) {
//        return s == null || s.isBlank();
//    }

}
