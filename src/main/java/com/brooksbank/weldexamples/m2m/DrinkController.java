/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.Drink;
import com.brooksbank.weldexamples.db.DrinkService;
import com.brooksbank.weldexamples.db.Condiment;
import com.brooksbank.weldexamples.db.CondimentService;
import com.brooksbank.weldexamples.db.DrinkCondiment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
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
import org.primefaces.model.DualListModel;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class DrinkController implements Serializable {

    @Inject
    private DrinkService service;

    @Inject
    private CondimentService condimentService;

    @Inject
    private Logger log;

    @Named
    @Produces
    @ViewScoped
    private Drink drinkBean = new Drink();

    private String mode = "???";

    private DualListModel<Condiment> condimentPicklist = null;

    /**
     * Creates a new instance of DrinkController
     */
    public DrinkController() {
    }

    @PostConstruct
    public void init() {
        // first get the dlg parameters
        Map<String, String[]> paramMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterValuesMap();
        if (paramMap != null && !paramMap.isEmpty()) {

            String[] modeList = (String[]) paramMap.get("mode");
            mode = (modeList == null) ? null : modeList[0];

            String[] drinkidList = (String[]) paramMap.get("drinkid");
            if (drinkidList != null) {
                drinkBean = service.findByIdEG(Long.parseLong(drinkidList[0]));
            }
        }
    }
    
    protected void populateCondimentPicklist() {
        List<Condiment> source = new ArrayList<>();
        condimentService.findAll().forEach(condiment -> {
            source.add(condiment);
        });

        List<Condiment> target = new ArrayList<>();
        if (drinkBean != null) {
            List<DrinkCondiment> drinkCondiments = drinkBean.getCondiments();
            drinkCondiments.stream().map(dc -> dc.getCondiment()).forEachOrdered(c -> {
                if (source.remove(c)) {
                    target.add(c);
                } else {
                    log.log(Level.WARNING,
                            "Existing condiment ({0}) not found in full list of condiments for drink {1}. Existing "
                                    + "condiment has been ignored and not included in the target list",
                            new Object[]{c, drinkBean});
                }
            });
        }
        condimentPicklist = new DualListModel<>(source, target);
    }

    public String getMode() {
        return mode;
    }

    public DualListModel<Condiment> getCondimentPicklist() {
        if ( condimentPicklist == null ) {
            populateCondimentPicklist();    // lazy instantiation
        }
        return condimentPicklist;
    }

    public void setCondimentPicklist(DualListModel<Condiment> condimentPicklist) {
        this.condimentPicklist = condimentPicklist;
    }

    public void addDrink() {
        //updateCondiments();
        service.create(drinkBean);
        updateCondiments();
        service.edit(drinkBean);
        PrimeFaces.current().dialog().closeDynamic(drinkBean);
    }

    private void updateCondiments() {
        List<DrinkCondiment> oldDCList = drinkBean.getCondiments();
        List<Condiment> newCondimentList = condimentPicklist.getTarget();
        List<DrinkCondiment> deleteList = new ArrayList<>();
        
        // First - go through the existing list and check if the old condiments 
        // are still in the list. If not mark them for removal.
        oldDCList.forEach(dc -> {
            Condiment oldCondiment = dc.getCondiment();
            if (!newCondimentList.contains(oldCondiment)) {
                deleteList.add(dc);
            } 
        });

        // Now remove anything from the old set that needs to be removed.
        // (Note: this is done as a separate list to avoid ConcurrentModificationExceptions)
        deleteList.forEach(dc -> {
            Condiment oldCondi = condimentService.findByIdWithDrinks(dc.getPk().getCondimentId());
            drinkBean.removeCondiment(oldCondi);
        });
        
        // Lastly - go through the new list and check if the new condiment is already
        // in the old list. If not, add it.
        newCondimentList.forEach(condiment -> {
            // make sure condiment / DC / drinks all loaded in the current session
            Condiment newCondi = condimentService.findByIdWithDrinks(condiment.getId());

            // check if the condiment is already present in the existing list
            boolean alreadyPresent = false;
            for ( DrinkCondiment dc : oldDCList) {
                if ( Objects.equals(dc.getPk().getCondimentId(), newCondi.getId())) {
                    alreadyPresent = true;
                    break;
                }
            }
            if (!alreadyPresent) {
                // add the new condiment to the list
                drinkBean.addCondiment(newCondi);
            }
        });
        // and oldCondimentList now has all the new condiments
    }

    public void editDrink() {
        updateCondiments();
        service.edit(drinkBean);
        PrimeFaces.current().dialog().closeDynamic(drinkBean);
    }

    public void deleteDrink() {
        // delete should not be called unless all condiments have already been removed.
        service.remove(drinkBean);
        PrimeFaces.current().dialog().closeDynamic(drinkBean);
    }

    public void validateName(FacesContext context, UIComponent component, String value) {
        if (isNullOrBlank(value)) {
            return;     // ignore null values
        }

        Drink drink = service.findByName(value);
        if (mode.equalsIgnoreCase("Add")) {
            if (drink != null) {
                // drink already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "drink already exists", "drink already exists"));
            }
        } else if (mode.equalsIgnoreCase("Edit")) {
            if (drink != null && !drink.equals(drinkBean)) {
                // drink already exists
                throw new ValidatorException(
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "drink already exists", "drink already exists"));
            }
        }
    }

    private static boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
