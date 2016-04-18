/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager;

import com.allone.projectmanager.dao.CollabsDAO;
import com.allone.projectmanager.dao.CompanyDAO;
import com.allone.projectmanager.dao.ItemDAO;
import com.allone.projectmanager.dao.ItemTransDAO;
import com.allone.projectmanager.dao.BillMaterialServiceDAO;
import com.allone.projectmanager.dao.BillMaterialServiceItemDAO;
import com.allone.projectmanager.dao.ProjectDAO;
import com.allone.projectmanager.dao.RepimageDAO;
import com.allone.projectmanager.dao.ReportsDAO;
import com.allone.projectmanager.dao.RightsDAO;
import com.allone.projectmanager.dao.SaleTransDAO;
import com.allone.projectmanager.dao.StockDAO;
import com.allone.projectmanager.dao.StockTransDAO;
import com.allone.projectmanager.dao.VesselDAO;
import com.allone.projectmanager.dao.ContactDAO;
import com.allone.projectmanager.dao.ProjectDetailDAO;
import com.allone.projectmanager.dao.RequestQuotationDAO;
import com.allone.projectmanager.dao.RequestQuotationItemDAO;
import com.allone.projectmanager.dao.ServiceCollabDAO;
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
    private final ItemDAO daoItem = new ItemDAO(emf);
    private final ItemTransDAO daoItemTrans = new ItemTransDAO(emf);
    private final ProjectDAO daoProject = new ProjectDAO(emf);
    private final ProjectDetailDAO daoProjectDetail = new ProjectDetailDAO(emf);
    private final RepimageDAO daoRepimage = new RepimageDAO(emf);
    private final ReportsDAO daoReport = new ReportsDAO(emf);
    private final RightsDAO daoRights = new RightsDAO(emf);
    private final SaleTransDAO daoSalesTrans = new SaleTransDAO(emf);
    private final StockDAO daoStock = new StockDAO(emf);
    private final StockTransDAO daoStockTrans = new StockTransDAO(emf);
    private final VesselDAO daoVessel = new VesselDAO(emf);
    private final BillMaterialServiceDAO daoBillMaterialService = new BillMaterialServiceDAO(emf);
    private final BillMaterialServiceItemDAO daoBillMaterialServiceItem = new BillMaterialServiceItemDAO(emf);
    private final RequestQuotationDAO daoRequestQuotation = new RequestQuotationDAO(emf);
    private final RequestQuotationItemDAO daoRequestQuotationItem = new RequestQuotationItemDAO(emf);
    private final ContactDAO daoContact = new ContactDAO(emf);
    private final ServiceCollabDAO daoServiceCollab = new ServiceCollabDAO(emf);

    private String pathProjectBill;

    private String pathProject;

    public ProjectDetailDAO getDaoProjectDetail() {
        return daoProjectDetail;
    }

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

    public CollabsDAO getDaoCollabs() {
        return daoCollabs;
    }

    public BillMaterialServiceDAO getDaoBillMaterialService() {
        return daoBillMaterialService;
    }

    public BillMaterialServiceItemDAO getDaoBillMaterialServiceItem() {
        return daoBillMaterialServiceItem;
    }

    public RequestQuotationDAO getDaoRequestQuotation() {
        return daoRequestQuotation;
    }

    public RequestQuotationItemDAO getDaoRequestQuotationItem() {
        return daoRequestQuotationItem;
    }

    public CollabsDAO getDaoCollab() {
        return daoCollabs;
    }

    public CompanyDAO getDaoCompany() {
        return daoCompany;
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

    public ContactDAO getDaoContact() {
        return daoContact;
    }
    
    public ServiceCollabDAO getDaoServiceCollab() {
        return daoServiceCollab;
    }
}
