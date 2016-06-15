/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager;

import com.allone.projectmanager.dao.wcs.WCSCompanyDAO;
import com.allone.projectmanager.dao.wcs.WCSContactDAO;
import com.allone.projectmanager.dao.wcs.WCSItemTransDAO;
import com.allone.projectmanager.dao.wcs.WCSProjectDAO;
import com.allone.projectmanager.dao.wcs.WCSSaleTransDAO;
import com.allone.projectmanager.dao.wcs.WCSStockTransDAO;
import com.allone.projectmanager.dao.wcs.WCSVesselDAO;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Admin
 */
@Stateless
public class WCSProjectManagerService {

    private static final Logger logger = Logger.getLogger(ProjectManagerService.class.getName());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("WCSProjectManagerPU");

    public WCSProjectManagerService() {
    }

    private final WCSItemTransDAO daoWCSItemTrans = new WCSItemTransDAO(emf);

    private final WCSProjectDAO daoWCSProject = new WCSProjectDAO(emf);

    private final WCSSaleTransDAO daoWCSSaleTrans = new WCSSaleTransDAO(emf);

    private final WCSStockTransDAO daoWCSStockTrans = new WCSStockTransDAO(emf);

    private final WCSCompanyDAO daoWCSCompany = new WCSCompanyDAO(emf);

    private final WCSContactDAO daoWCSContact = new WCSContactDAO(emf);
    
    private final WCSVesselDAO daoWCSVessel = new WCSVesselDAO(emf);

    public static Logger getLogger() {
        return logger;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public WCSItemTransDAO getDaoWCSItemTrans() {
        return daoWCSItemTrans;
    }

    public WCSProjectDAO getDaoWCSProject() {
        return daoWCSProject;
    }

    public WCSSaleTransDAO getDaoWCSSaleTrans() {
        return daoWCSSaleTrans;
    }

    public WCSStockTransDAO getDaoWCSStockTrans() {
        return daoWCSStockTrans;
    }

    public WCSCompanyDAO getDaoWCSCompany() {
        return daoWCSCompany;
    }
    
    public WCSContactDAO getDaoWCSContact() {
        return daoWCSContact;
    }

    public WCSVesselDAO getDaoWCSVessel() {
        return daoWCSVessel;
    }
}
