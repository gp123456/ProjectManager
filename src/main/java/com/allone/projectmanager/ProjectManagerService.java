/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager;

import com.allone.projectmanager.dao.CollabsDAO;
import com.allone.projectmanager.dao.CompanyDAO;
import com.allone.projectmanager.dao.EmailDAO;
import com.allone.projectmanager.dao.ExchangeDAO;
import com.allone.projectmanager.dao.ItemDAO;
import com.allone.projectmanager.dao.ItemTransDAO;
import com.allone.projectmanager.dao.ProjectBillCustomItemDAO;
import com.allone.projectmanager.dao.ProjectBillDAO;
import com.allone.projectmanager.dao.ProjectBillItemDAO;
import com.allone.projectmanager.dao.ProjectDAO;
import com.allone.projectmanager.dao.RepimageDAO;
import com.allone.projectmanager.dao.ReportsDAO;
import com.allone.projectmanager.dao.RightsDAO;
import com.allone.projectmanager.dao.SaleTransDAO;
import com.allone.projectmanager.dao.StatusProjectDAO;
import com.allone.projectmanager.dao.StockDAO;
import com.allone.projectmanager.dao.StockTransDAO;
import com.allone.projectmanager.dao.TypeProjectDAO;
import com.allone.projectmanager.dao.VesselDAO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author antonia
 */
@Stateless
public class ProjectManagerService {

    private static final Logger LOG = Logger.getLogger(ProjectManagerService.class.getName());

    private final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ProjectManagerPU");

    public ProjectManagerService() {
    }

    public void setPathProjectBill(String pathProjectBill) {
        this.pathProjectBill = pathProjectBill;
    }

    private final CollabsDAO daoCollabs = new CollabsDAO(emf);

    private final CompanyDAO daoCompany = new CompanyDAO(emf);
    private final ExchangeDAO daoExchange = new ExchangeDAO(emf);
    private final EmailDAO daoEmail = new EmailDAO(emf);
    private final ItemDAO daoItem = new ItemDAO(emf);
    private final ItemTransDAO daoItemTrans = new ItemTransDAO(emf);
    private final ProjectDAO daoProject = new ProjectDAO(emf);
    private final RepimageDAO daoRepimage = new RepimageDAO(emf);
    private final ReportsDAO daoReport = new ReportsDAO(emf);
    private final RightsDAO daoRights = new RightsDAO(emf);
    private final SaleTransDAO daoSalesTrans = new SaleTransDAO(emf);
    private final StockDAO daoStock = new StockDAO(emf);
    private final StockTransDAO daoStockTrans = new StockTransDAO(emf);
    private final VesselDAO daoVessel = new VesselDAO(emf);
    private final TypeProjectDAO daoTypeProject = new TypeProjectDAO(emf);
    private final StatusProjectDAO daoStatusProject = new StatusProjectDAO(emf);
    private final ProjectBillDAO daoProjectBill = new ProjectBillDAO(emf);
    private final ProjectBillItemDAO daoProjectBillItem = new ProjectBillItemDAO(emf);
    private final ProjectBillCustomItemDAO daoProjectBillCustomItem = new ProjectBillCustomItemDAO(emf);

    private String pathProjectBill;
    
    private String pathProject;

    public void loadPropertyValues() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("project_manager.properties");
        Properties prop = new Properties();

        if (in != null) {
            try {
                prop.load(in);
                this.pathProjectBill = prop.getProperty("project-bill-path");
                this.pathProject = prop.getProperty("project-path");
                
                LOG.info(pathProject);
            } catch (IOException ex) {
                Logger.getLogger(ProjectManagerService.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public String getPathProject() {
        return pathProject;
    }

    public void setPathProject(String pathProject) {
        this.pathProject = pathProject;
    }

    public String getPathProjectBill() {
        return pathProjectBill;
    }
    
    public ProjectBillCustomItemDAO getDaoProjectBillCustomItem() {
        return daoProjectBillCustomItem;
    }

    public CollabsDAO getDaoCollabs() {
        return daoCollabs;
    }

    public ProjectBillDAO getDaoProjectBill() {
        return daoProjectBill;
    }

    public ProjectBillItemDAO getDaoProjectBillItem() {
        return daoProjectBillItem;
    }

    public TypeProjectDAO getDaoTypeProject() {
        return daoTypeProject;
    }

    public CollabsDAO getDaoCollab() {
        return daoCollabs;
    }

    public CompanyDAO getDaoCompany() {
        return daoCompany;
    }

    public ExchangeDAO getDaoExchange() {
        return daoExchange;
    }

    public EmailDAO getDaoEmail() {
        return daoEmail;
    }

    public ItemDAO getDaoItem() {
        return daoItem;
    }

    public ItemTransDAO getDaoItemTrans() {
        return daoItemTrans;
    }

    public ProjectDAO getDaoProject() {
        return daoProject;
    }

    public RepimageDAO getDaoRepimage() {
        return daoRepimage;
    }

    public ReportsDAO getDaoReport() {
        return daoReport;
    }

    public RightsDAO getDaoRights() {
        return daoRights;
    }

    public SaleTransDAO getDaoSalesTrans() {
        return daoSalesTrans;
    }

    public StockDAO getDaoStock() {
        return daoStock;
    }

    public StockTransDAO getDaoStockTrans() {
        return daoStockTrans;
    }

    public VesselDAO getDaoVessel() {
        return daoVessel;
    }

    public StatusProjectDAO getDaoStatusProject() {
        return daoStatusProject;
    }
}
