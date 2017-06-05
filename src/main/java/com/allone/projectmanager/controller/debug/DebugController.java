/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.debug;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.WCSProjectManagerService;
import com.allone.projectmanager.controller.common.Common;
import com.allone.projectmanager.entities.Company;
import com.allone.projectmanager.entities.Contact;
import com.allone.projectmanager.entities.Vessel;
import com.allone.projectmanager.entities.wcs.WCSCompany;
import com.allone.projectmanager.entities.wcs.WCSContact;
import com.allone.projectmanager.entities.wcs.WCSVessel;
import com.allone.projectmanager.enums.DBFilesEnum;
import com.google.common.base.Strings;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/debug")
public class DebugController extends Common {

    private static final Logger logger = Logger.getLogger(DebugController.class.getName());

    @Autowired
    ProjectManagerService srvProjectManager;

    WCSProjectManagerService srvWCSProjectManager;

    public WCSProjectManagerService getSrvWCSProjectManager() {
        return srvWCSProjectManager;
    }

//    public void setSrvWCSProjectManager(WCSProjectManagerService srvWCSProjectManager) {
//        this.srvWCSProjectManager = srvWCSProjectManager;
//    }
    @RequestMapping(value = "/snapshot")
    public String Snapshot(HttpServletRequest request, Model model) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                setUser(srvProjectManager.getUser());
                this.setTitle("Debug");
                this.setHeader(null);
                this.setSide_bar("../debug/sidebar.jsp");
                this.setContent(null);
                setHeaderInfo(model);

                return "index";
            }
        }

        return "";
    }

    @RequestMapping(value = "/db-files")
    @ResponseBody
    public String getDBFiles() {
        String response = "";

        for (String value : DBFilesEnum.values()) {
            response += "<input type='radio' name='radio-db-file' id='" + value + "' value='" + value + "'><label for='" + value + "' class='radio-label'>" + value + "</label><br>";
        }

        return response;
    }

    @RequestMapping(value = "/db-files/transfer")
    @ResponseBody
    public String transferDBFiles(String value) {
        String response = "";

        logger.log(Level.INFO, "transfer DBFiles={0}", value);

        switch (value) {
            case DBFilesEnum.COMPANY:
                response = tranferCompany();
                break;
            case DBFilesEnum.VESSEL:
                response = tranferVessel();
                break;
            case DBFilesEnum.CONTACT:
                response = tranferContact();
                break;
        }

        return response;
    }

    private String tranferCompany() {
        String response = "";
        List<WCSCompany> companies = srvWCSProjectManager.getDaoWCSCompany().getAll();
        Long records = 0l;

        if (companies != null && !companies.isEmpty()) {
            logger.log(Level.INFO, "transfer size={0}", companies.size());

            for (WCSCompany company : companies) {
                srvProjectManager.getDaoCompany()
                        .add(new Company.Builder()
                                .setAddress(company.getAddress())
                                .setCountry(company.getCountry())
                                .setDoy(company.getDoy())
                                .setEmail1(company.getEmail1())
                                .setEmail2(company.getEmail2())
                                .setEmail3(company.getEmail3())
                                .setFax1(company.getFax1())
                                .setFax2(company.getFax2())
                                .setName(company.getName())
                                .setNote(company.getNote())
                                .setPostCode(company.getPostCode())
                                .setPostCountry(company.getPostCountry())
                                .setReferenceNumber(company.getReference())
                                .setTelefone1(company.getTelephone1())
                                .setTelefone2(company.getTelephone2())
                                .setTelefone3(company.getTelephone3())
                                .setType(company.getTypeDescription())
                                .setVat(company.getVat())
                                .build());
                records++;
            }
            response = records.toString();
        }

        return response;
    }

    private String tranferVessel() {
        String response = "";
        List<WCSVessel> vessels = srvWCSProjectManager.getDaoWCSVessel().getAll();
        Long records = 0l;

        if (vessels != null && !vessels.isEmpty()) {
            logger.log(Level.INFO, "transfer size={0}", vessels.size());

            for (WCSVessel vessel : vessels) {
                srvProjectManager.getDaoVessel()
                        .add(new Vessel.Builder()
                                .setClass1((vessel.getGlass() != null) ? vessel.getGlass().toString() : "")
                                .setCompany(vessel.getComapnyName())
                                .setDocument(vessel.getDocument())
                                .setEmail1(vessel.getEmail1())
                                .setEmail2(vessel.getEmail2())
                                .setEmail3(vessel.getEmail3())
                                .setFlag(vessel.getImo())
                                .setLog(vessel.getLogComm())
                                .setName(vessel.getName())
                                .setNextDd(vessel.getNextDD())
                                .setNote(vessel.getNote())
                                .build());
                records++;
            }
            response = records.toString();
        }

        return response;
    }

    private String tranferContact() {
        logger.log(Level.INFO, "tranferContact");

        String response = "";

        List<WCSContact> contacts = srvWCSProjectManager.getDaoWCSContact().getAll();
        Long records = 0l;

        if (contacts != null && !contacts.isEmpty()) {
            logger.log(Level.INFO, "transfer size={0}", contacts.size());

            for (WCSContact contact : contacts) {
                srvProjectManager.getDaoContact()
                        .add(new Contact.Builder()
                                .setCompany(contact.getCompany())
                                .setDepartment(contact.getTitle())
                                .setEmail(contact.getEmail())
                                .setName(contact.getName())
                                .setNote(contact.getNote())
                                .setPhone(contact.getPhone())
                                .setSurname(contact.getSurname())
                                .setVessel((!Strings.isNullOrEmpty(contact.getVessel())) ? Long.valueOf(contact.getVessel()) : 0l)
                                .build());
                records++;
            }
            response = records.toString();
        }

        return response;
    }
}
