/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.brooksbank.weldexamples.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sjbro
 */
@Stateless
public class CodesService extends AbstractFacade<Codes> {

    private static final int DEFAULT_ITEM_LIMIT = 25;

    @Inject @WeldDatabase
    private EntityManager em;
    
    @Inject @ChangedTable
    private Event<Codes> tableUpdatedEvent;
    
//    @Inject
//    private CodetypesService ctService;


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CodesService() {
        super(Codes.class);
    }

    // **** Overrides *** Overrides *** Overrides *****

    @Override
    public void remove(Codes entity) {

        // get the existing code type and find if the owner Codetype is defined (it should ALWAYS be)
        // If so, select the owning codetype, remove the Codes entity from the collection and save it back again.
        Codes oldCode = this.find(entity.getCodeid());
        if ( oldCode.getCodetypeid() != null ) {
            Codetypes ctOldOwner = (Codetypes)
                    em.find(Codetypes.class, oldCode.getCodetypeid().getCodetypeid());
            ctOldOwner.getCodesCollection().remove(entity);
            em.merge(ctOldOwner);
        }

        // Now delete the entity
        super.remove(entity); //To change body of generated methods, choose Tools | Templates.
        
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void edit(Codes entity) {
        
        //obtain the old verrsion of the Codes entity
        Codes oldCode = this.find(entity.getCodeid());
        // if the old version had an owner Codetype and the new owner is different
        if ( oldCode.getCodetypeid() != null && 
                !oldCode.getCodetypeid().equals(entity.getCodetypeid()) ) {
            // the old codetype has changed (this should NEVER be the case)
            // get the old owner and remove the entity from the collection
            Codetypes ctOldOwner = (Codetypes)
                    em.find(Codetypes.class, oldCode.getCodetypeid().getCodetypeid());
            ctOldOwner.getCodesCollection().remove(entity);
            em.merge(ctOldOwner);
        }
        
        super.edit(entity); //To change body of generated methods, choose Tools | Templates.
        
        // Now check if the new owner exists and is different from the old
        if ( entity.getCodetypeid() != null && 
                !entity.getCodetypeid().equals(oldCode.getCodetypeid()) ) {
            Codetypes ctNewOwner = (Codetypes)
                    em.find(Codetypes.class, entity.getCodetypeid().getCodetypeid());
            ctNewOwner.getCodesCollection().add(entity);
            em.merge(ctNewOwner);
        }
        
        tableUpdatedEvent.fire(entity);
    }

    @Override
    public void create(Codes entity) {
        
        super.create(entity); //To change body of generated methods, choose Tools | Templates.

        // if the Codetype owner is defined (it should ALWAYS be)
        // then select the owner, add the Codes entity to the commention and resave it
        if ( entity.getCodetypeid() != null) {
            Codetypes ctNewOwner = (Codetypes)
                    em.find(Codetypes.class, entity.getCodetypeid().getCodetypeid());
            ctNewOwner.getCodesCollection().add(entity);
            em.merge(ctNewOwner);
        }
        
        tableUpdatedEvent.fire(entity);
    }
    
    // ***** ADDED ***** ADDED ***** ADDED *****
    
    public List<Codes> findCurrentByCodetype(@NotNull String codetype) {
        Query q = em.createNamedQuery("Codes.findCurrentByCodetype");
        q.setParameter("codetype", codetype);
        List<Codes> list = q.getResultList();
        return list;
    }
    
    public List<Codes> findAllByCodetype(@NotNull String codetype) {
        Query q = em.createNamedQuery("Codes.findAllByCodetype");
        q.setParameter("codetype", codetype);
        List<Codes> list = q.getResultList();
        return list;
    }
    
    public List<Codes> findCodetypeForDate(@NotNull String codetype, @NotNull Date date) {
        Query q = em.createNamedQuery("Codes.findCodetypeForDate");
        q.setParameter("codetype", codetype);
        q.setParameter("date", date);
        List<Codes> list = q.getResultList();
        return list;
    }
    

    protected List<SelectItem> getSelectCurrent(@NotNull String codetype, int itemLimit  ) {
        List<Codes> codes = this.findCurrentByCodetype(codetype);
        return formatSelectItems(codes, itemLimit);
    }

    /**
     * Format the select Item list from the list of supplied codes entities
     * 
     * @param codes List of Codes entities to be included in the SelectItems
     * @param itemLimit
     * @return SelectItem of string code values with label and description
     */
    protected List<SelectItem> formatSelectItems(List<Codes> codes, int itemLimit) {
        List<SelectItem> selectItems = new ArrayList<>();
        codes.forEach((c) -> {
            String desc = c.getDescription();
            selectItems.add( new SelectItem(c.getCode(),    // <-- string code value
                    c.getCode() + ": " + ((desc.length()>itemLimit)? desc.substring(0, itemLimit - 1) + "\u2026" : desc), 
                    // <-- label = code plus the first 25 chars of the description 
                    // unicode U+2026 is an ellipsis character ("...") to show more description exists
                    desc ) );   // <-- include the full description in the title/hint/popup
        });
        return selectItems;
    }

    public List<SelectItem> getSelectItemsCurrentCnCodes() {
        return this.getSelectCurrent("CN", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentCountries() {
        return this.getSelectCurrent("CTRY", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentCancReasons() {
        return this.getSelectCurrent("CANC", 50);
    }

    public List<SelectItem> getSelectItemsCurrentDocCertTypes() {
        return this.getSelectCurrent("DOCTY", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentGblConclusions() {
        return this.getSelectCurrent("GCONC", 45);
    }
    
    public List<SelectItem> getSelectItemsCurrentLanguages() {
        return this.getSelectCurrent("LANG", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentMembers() {
        return this.getSelectCurrent("MEMBR", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentPackTypes() {
        return this.getSelectCurrent("PACK", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentTransportModes() {
        return this.getSelectCurrent("TMODE", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentTransportUnits() {
        return this.getSelectCurrent("TUNIT", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentUnsatReasons() {
        return this.getSelectCurrent("UNSAT", DEFAULT_ITEM_LIMIT);
    }

    public List<SelectItem> getSelectItemsCurrentUOM() {
        return this.getSelectCurrent("UOM", DEFAULT_ITEM_LIMIT);
    }
    
    public List<SelectItem> getSelectItemsCurrentWineGrowingZones() {
        return this.getSelectCurrent("WINZO", DEFAULT_ITEM_LIMIT);
    }
    
    public List<SelectItem> getSelectItemsCurrentWineOperations() {
        return this.getSelectCurrent("WINOP", 255);
    }
    
    
    protected List<SelectItem> getSelectItemsCurrentCodetypeExtra1(String codetype, String extra1) {
        Query q = em.createNamedQuery("Codes.findCurrentByCodetypeAndExtra1");
        q.setParameter("codetype", codetype);
        q.setParameter("extra1", extra1);
        List<Codes> codes = q.getResultList();
        return formatSelectItems(codes, DEFAULT_ITEM_LIMIT);
    }
    
    protected List<SelectItem> getSelectItemsCurrentCodetypeExtra2(String codetype, String extra2) {
        Query q = em.createNamedQuery("Codes.findCurrentByCodetypeAndExtra2");
        q.setParameter("codetype", codetype);
        q.setParameter("extra2", extra2);
        List<Codes> codes = q.getResultList();
        return formatSelectItems(codes, DEFAULT_ITEM_LIMIT);
    }
    
    protected List<SelectItem> getSelectItemsCurrentCodetypeExtra3(String codetype, String extra3) {
        Query q = em.createNamedQuery("Codes.findCurrentByCodetypeAndExtra3");
        q.setParameter("codetype", codetype);
        q.setParameter("extra3", extra3);
        List<Codes> codes = q.getResultList();
        return formatSelectItems(codes, DEFAULT_ITEM_LIMIT);
    }
    
    public List<SelectItem> getSelectItemsCurrentCnCodesByEPC(String epc) {
        if ( epc == null ) {
            return getSelectItemsCurrentCnCodes();
        }
        return getSelectItemsCurrentCodetypeExtra1("CN",  epc);
    }
   
    public List<SelectItem> getSelectItemsCurrentGConcByMvtComplete(String complete) {
        if ( complete == null ) {
            return this.getSelectItemsCurrentGblConclusions();
        }
        return getSelectItemsCurrentCodetypeExtra1("GCONC", complete);
    }
   
    public List<SelectItem> getSelectItemsCurrentGConcByExport(String exported) {
        if ( exported == null ) {
            return this.getSelectItemsCurrentGblConclusions();
        }
        return getSelectItemsCurrentCodetypeExtra2("GCONC", exported);
    }
   
    public List<SelectItem> getSelectItemsCurrentGConcByQtyChanged(String changed) {
        if ( changed == null ) {
            return this.getSelectItemsCurrentGblConclusions();
        }
        return getSelectItemsCurrentCodetypeExtra3("GCONC", changed);
    }
   
    // ************************************************************
    
    public Codes findByCodetypeAndCode(String codetype, String code) {
        Query q = em.createNamedQuery("Codes.findByCodetypeAndCode");
        q.setParameter("codetype", codetype);
        q.setParameter("code", code);
        return getSingleResult(q);
    }
    
    public Codes findCnCodeByCode(String code) {
        return findByCodetypeAndCode("CN", code);
    }

    public Codes findUOMByCode(Short code) {
        return findByCodetypeAndCode("UOM", Short.toString(code));
    }

    public Codes findPackTypeByCode(String code) {
        return findByCodetypeAndCode("PACK", code);
    }

    public Codes findGlobalConclusionTypeByCode(String code) {
        return findByCodetypeAndCode("GCONC", code);
    }
    
    // ************************************************************

    public List<Codes> findAllUOM() {
        return this.findAllByCodetype("UOM");
    }
    
    public List<Codes> findAllCnCode() {
        return this.findAllByCodetype("CN");
    }
    
    public List<Codes> findAllMembers() {
        return this.findAllByCodetype("MEMBR");
    }

    // ************************************************************

    public List<Codes> findCurrentMembers() {
        return this.findCurrentByCodetype("MEMBR");
    }

    // ************************************************************

    public List<Codes> findMembersForDate() {
        return this.findCodetypeForDate("MEMBR", new Date());
    }




    private Codes getSingleResult(Query q) {
        try {
            Codes res = (Codes) q.getSingleResult();
            return res;
        } catch (NoResultException ex) {
            return null;    // just pass back null in this case
        } // any other exception (eg. duplicate) will just be thrown upwards
    }
    
}
