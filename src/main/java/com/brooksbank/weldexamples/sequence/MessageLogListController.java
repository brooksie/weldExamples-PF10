/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.sequence;

import com.brooksbank.weldexamples.db.Messagelog;
import com.brooksbank.weldexamples.db.MessagelogService;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
    
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
    

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class MessageLogListController implements Serializable {

    @Inject
    private MessagelogService service;

    private List<Messagelog> messageList;
    
    private Messagelog selectedMessagelog;
    
    /** Creates a new instance of MessageLogController */
    public MessageLogListController() {
    }
    
    @PostConstruct
    private void init() {
        setList();
    }

    public List<Messagelog> getMessageList() {
        return messageList;
    }

    public Messagelog getSelectedMessagelog() {
        return selectedMessagelog;
    }

    public void setSelectedMessagelog(Messagelog selectedMessagelog) {
        this.selectedMessagelog = selectedMessagelog;
    }
    
    public void addAction() {
        invokeDialog("Add", null);
    }
    
    public void editAction() {
        invokeDialog("Edit", selectedMessagelog);
    }
    
    public void deleteAction() {
        invokeDialog("Delete", selectedMessagelog);
    }

    public void viewAction() {
        invokeDialog("View", selectedMessagelog);
    }
    
    private void invokeDialog(String mode, Messagelog cd) {
        Map<String,Object> options = new HashMap<>();
        options.put("resizable", false);
        options.put("modal", true);
        
        Map<String,List<String>> params = new HashMap<>();
        params.put("mode", Arrays.asList(mode) );
        
        if ( cd != null ) {
            params.put("messagelogid", Arrays.asList(cd.getMessageid().toString()) );
        }
        PrimeFaces.current().dialog().openDynamic("messagelog_dlg", options, params);
    }

    public void refreshAction() {
        setList();
    }

    public void onAddReturn(SelectEvent event) {
        setList();
    }

    public void onEditReturn(SelectEvent event) {
        setList();
    }

    public void onDeleteReturn(SelectEvent event) {
        setList();
    }

    private void setList() {
        messageList = service.findAll();
    }
    
}
