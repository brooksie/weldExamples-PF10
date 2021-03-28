/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.brooksbank.weldexamples.m2m;

import com.brooksbank.weldexamples.db.DrinkService;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author sjbro
 */
@Named
@ViewScoped
public class TestHibController implements Serializable {
    
    @Inject
    private DrinkService drinkService;
    @Inject
    private Logger log;

    private Long id1;
    
    /** Creates a new instance of TestHibController */
    public TestHibController() {
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    public void find1Action() {
        log.info("finding...");
        drinkService.findWithEG(id1);
    }
    
}
