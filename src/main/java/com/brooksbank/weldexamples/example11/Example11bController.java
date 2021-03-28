/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.example11;

import com.brooksbank.weldexamples.db.Codes;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
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
public class Example11bController implements Serializable {

    @Inject
    Logger log;
    
    @Inject 
    private CodeCatalog codeCatalog;

    @Inject
    List<Codes> codeList;
    
    private List<Codes> filteredCodeList;
    
    private Codes selectedCode;

    private String selectedCodetypeCode;

    // **********************************************

    public String getSelectedCodetypeCode() {
        return selectedCodetypeCode;
    }

    public void setSelectedCodetypeCode(String selectedCodetypeCode) {
        this.selectedCodetypeCode = selectedCodetypeCode;
    }

    
    public List<Codes> getFilteredCodeList() {
        return filteredCodeList;
    }
    public void setFilteredCodeList(List<Codes> filteredCodeList) {
        this.filteredCodeList = filteredCodeList;
    }

    public Codes getSelectedCode() {
        return selectedCode;
    }
    public void setSelectedCode(Codes selectedCode) {
        this.selectedCode = selectedCode;
    }

    


    public void searchAction() {
        // re-obtain the list of codes based on the new search criteria
        if ( !isNullOrBlank(selectedCodetypeCode)) {
            codeCatalog.setRestrictedCodetype(this.selectedCodetypeCode);
        }
    }

    public void addAction() {
        invokeDialog("Add", null, 
                "*".equals(selectedCodetypeCode) ? null : selectedCodetypeCode);
    }
    
    public void editAction() {
        invokeDialog("Edit", selectedCode, null);
    }
    
    public void deleteAction() {
        invokeDialog("Delete", selectedCode, null);
    }

    public void viewAction() {
        invokeDialog("View", selectedCode, null);
    }
    
    private void invokeDialog(String mode, Codes cd, String ct) {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode) );
        if ( cd != null ) {
            params.put("codeid", Arrays.asList(cd.getCodeid().toString()) );
        }
        if ( !isNullOrBlank(ct) ) {
            params.put("codetypecode", Arrays.asList(ct) );
        }
        PrimeFaces.current().dialog().openDynamic("example11b_dlg", options, params);
    }

//    public void clearAllFilters() {
//
//        DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("mainfrm:cdTab");
//        if (!dataTable.getFilterBy().isEmpty()) {
//            
//            dataTable.getFilterBy().clear();
//            dataTable.reset();
//
//            PrimeFaces.current().ajax().update("mainfrm:cdTab");
//        }
//    }
    
    private boolean isNullOrBlank(String s) {
        return s == null || s.isBlank();
    }

}
