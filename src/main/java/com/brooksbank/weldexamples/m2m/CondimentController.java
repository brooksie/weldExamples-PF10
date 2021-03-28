/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Condiment;
import com.brooksbank.weldexamples.db.CondimentService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
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
public class CondimentController implements Serializable {

    @Inject
    private CondimentService condimentService;

    @Inject
    private Logger log;
    
    @Inject 
    FacesContext facesContext;

    @Named
    @Produces
    @ViewScoped
    private Condiment condimentBean = new Condiment();

    private String mode = "???";
    private boolean deleteActionDisabled = false;

    /**
     * Creates a new instance of CondimentController
     */
    public CondimentController() {
    }

    @PostConstruct
    public void init() {
        // first get the dlg parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];

            String[] condimentidList = (String[]) paramMap.get("condimentid");
            if (condimentidList != null) {
                condimentBean = condimentService.findByIdWithDrinks(Long.parseLong(condimentidList[0]));
            }
        }
        
        if (condimentBean != null && "Delete".equals(mode)) {
            int numDrinks = condimentService.getCountOfDrinks(condimentBean.getId());
            if (numDrinks != 0) {
                deleteActionDisabled = true;
                facesContext.addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Delete not possible",
                                "Condiment has " + numDrinks + " drinks defined"
                        ));
            }
        }
    }

    public String getMode() {
        return mode;
    }

    public boolean isDeleteActionDisabled() {
        return deleteActionDisabled;
    }
    
    
    public void addCondiment() {
        condimentService.create(condimentBean);
        // Note: this assumes that Drinks can't be linked to the new condiment at this point
        // updateDrinks();
        // condimentService.edit(condimentBean);
        PrimeFaces.current().dialog().closeDynamic(condimentBean);
    }

    public void editCondiment() {
        //updateDrinks();
        condimentService.edit(condimentBean);
        PrimeFaces.current().dialog().closeDynamic(condimentBean);
    }

//    private void updateDrinks() {
//        List<Condiment> oldCondimentList = condimentBean.getCondiments();
//        List<Condiment> newCondimentList = new ArrayList<>(condimentPicklist.getTarget());
//        List<Condiment> deleteList = new ArrayList<>();
//        
//        // First - go through the existing list and check if the old condiments 
//        // are still in the list. If not mark them for removal.
//        for ( Condiment condiment : oldCondimentList ) {
//            if ( !newCondimentList.contains(condiment)) {
//                deleteList.add(condiment);
//            }
//        }
//        // Now remove anything from the old set that needs to be removed.
//        //(Note: this is done as a separate list to avoid ConcurrentModificationExceptions)
//        for (Condiment condiment : deleteList) {
//            oldCondimentList.remove(condiment);
//        }
//        
//        // Lastly - go through the new list and check if the new condiment is already
//        // in the old list. If not, add it.
//        for (Condiment condiment : newCondimentList ) {
//            oldCondimentList.add(condiment);    // this will only add new elements
//        }
//        // and oldCondimentList now has all the new condiments
//    }
    
    public void deleteCondiment() {
        // no need to worry abount drinks as delete is not allowed when any drinks exists for the condiment
        condimentService.remove(condimentBean);
        PrimeFaces.current().dialog().closeDynamic(condimentBean);
    }

    public void validateName(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        Condiment condiment = condimentService.findByName(value);
        if (mode.equalsIgnoreCase("Add")) {
            if (condiment != null) {
                // condiment already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "condiment already exists", "condiment already exists"));
            }
        } else if (mode.equalsIgnoreCase("Edit")) {
            if (condiment != null && !condiment.equals(condimentBean)) {
                // condiment already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "condiment already exists", "condiment already exists"));
            }
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
