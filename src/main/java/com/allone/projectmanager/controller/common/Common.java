/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.entities.*;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.LocationEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.enums.WCSProjectStatusEnum;
import com.allone.projectmanager.model.SearchCriteria;
import com.allone.projectmanager.model.SearchInfo;
import com.allone.projectmanager.model.User;
import com.google.common.base.Strings;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

/**
 *
 * @author antonia
 */
public class Common {

    private static final Logger logger = Logger.getLogger(Common.class.getName());

    private ProjectTypeEnum projectType;

    private static final Map<String, User> user = new HashMap<>();

    private String side_bar;

    private String content;

    private String classContent;

    private String title;

    private String header;

    private SearchCriteria searchCriteria;

    public String fillSearchType() {
        List<SearchInfo> info = getSearchCriteriaTypeProject();
        String finalResponse = "<option value=\"none\" selected=\"selected\">Select Type</option>";
        String response = "";
        int i = 0;

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream().map((si) -> "<option value=\""
                    + si.getId() + "\">"
                    + si.getName() + "</option>").
                    reduce(response, String::concat);
            finalResponse += response;
        }

        return finalResponse;
    }

    public String fillSearchStatus(String version) {
        List<SearchInfo> info = getSearchCriteriaStatusProject(version);
        String finalResponse = "<option value=\"none\" selected=\"selected\">Select Status</option>";
        String response = "";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream()
                    .map((si) -> "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>").
                    reduce(response, String::concat);
            finalResponse += response;
        }

        return finalResponse;
    }

    public String fillSearchVessel(ProjectManagerService srvProjectManager, String id) {
        List<SearchInfo> info = getSearchCriteriaVessel(srvProjectManager);
        String response = "<option value='-1' selected='selected'>Select Vessel</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (SearchInfo si : info) {
                if (!Strings.isNullOrEmpty(id)) {
                    if (si.getId().equals(id)) {
                        response += "<option value='" + si.getId() + "' selected='selected'>" + si.getName() + "</option>";
                    }
                }
                response += "<option value='" + si.getId() + "'>" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String fillSearchCompany(ProjectManagerService srvProjectManager, String name, CompanyTypeEnum type) {
        List<SearchInfo> info = getSearchCriteriaCompany(srvProjectManager, type);
        String response = (Strings.isNullOrEmpty(name))
                ? "<option value='none' selected='selected'>Select " + type.toString().toLowerCase() + "</option>"
                : "<option value='none'>Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (SearchInfo si : info) {
                if (!Strings.isNullOrEmpty(name)) {
                    if (si.getId().equals(name)) {
                        response += "<option value='" + si.getName() + "' selected='selected'>" + si.getName()
                                + "</option>";
                    }
                }
                response += "<option value='" + si.getName() + "'>" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String fillSearchOwnCompany() {
        List<SearchInfo> info = getSearchCriteriaCompany();
        String finalResponse = "<option value=\"none\" selected=\"selected\">Select Company</option>";
        String response = "";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream()
                    .map((si) -> "<option value=\"" + si.getName() + "\">" + si.getName() + "</option>").reduce(response,
                    String::concat);
            finalResponse += response;
        }

        return finalResponse;
    }

    public String fillSearchContact(ProjectManagerService srvProjectManager, Long id) {
        List<Contact> info = srvProjectManager.getDaoContact().getAll();
        String response = (id == null) ? "<option value='-1' selected='selected'>Select Contact</option>"
                : "<option value='-1' >Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Contact c : info) {
                if (id != null) {
                    if (c.getVessel().equals(id)) {
                        response += "<option value='" + c.getId() + "' selected='selected'>" + c.getName() + "</option>";
                    }
                }
                response += "<option value=\"" + c.getId() + "\">" + c.getName() + "</option>";
            }
        }

        return response;
    }

    public void setHeaderInfo(HttpSession session, Model model) {
        User user = getUser(session.getId());

        if (user != null) {
            model.addAttribute("screen_name", user.getScreen_name());
            model.addAttribute("full_name", user.getFull_name());
            model.addAttribute("last_login", user.getLast_login());
            model.addAttribute("avatar", user.getAvatar());
            model.addAttribute("title", title);
            model.addAttribute("project_header", header);
            model.addAttribute("side_bar", side_bar);
            model.addAttribute("content", content);
            model.addAttribute("classContent", classContent);
            model.addAttribute("project_reference", user.getProject_reference());
            model.addAttribute("email", user.getEmail());
        }
    }

    public void setItemInfo(Model model, ProjectManagerService srvProjectManager) {
        List<Item> items = srvProjectManager.getDaoItem().getAll();

        model.addAttribute("items", items);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSide_bar() {
        return side_bar;
    }

    public void setSide_bar(String side_bar) {
        this.side_bar = side_bar;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getClassContent() {
        return classContent;
    }

    public void setClassContent(String classContent) {
        this.classContent = classContent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(String session, User user) {
        this.user.put(session, user);
    }

    public User getUser(String session) {
        User u = user.get(session);

        return u;
    }

    public User setUserProjectId(String session, Long projectId) {
        User u = user.get(session);

        if (u != null) {
            u.setProjectId(projectId);
        }

        return u;
    }

    public Boolean isLockProject(Long userId, Long projectId) {
        for (User u : user.values()) {
            Long pId = u.getProjectId();

            if (!u.getId().equals(userId) && pId != null && pId.equals(projectId)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }

    public User removeUser(String session) {
        return user.remove(session);
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<SearchInfo> getSearchCriteriaTypeProject() {
        List<SearchInfo> si = new ArrayList<>();

        for (ProjectTypeEnum type : ProjectTypeEnum.values()) {
            si.add(new SearchInfo(type.toString(), type.toString()));
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaStatusProject(String version) {
        List<SearchInfo> si = new ArrayList<>();

        if (version.equals("new")) {
            for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
                si.add(new SearchInfo(status.toString(), status.toString()));
            }
        } else if (version.equals("old")) {
            for (WCSProjectStatusEnum status : WCSProjectStatusEnum.values()) {
                si.add(new SearchInfo(status.toString(), status.toString()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaVessel(ProjectManagerService srvProjectManager) {
        List<Vessel> v = srvProjectManager.getDaoVessel().getAll();

        List<SearchInfo> si = new ArrayList<>();

        if (v != null && v.isEmpty() == false && v.get(0) != null) {
            v.stream().forEach((value) -> {
                si.add(new SearchInfo(value.getId().toString(), value.getName()));
            });
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCompany(ProjectManagerService srvProjectManager, CompanyTypeEnum type) {
        List<Company> c = srvProjectManager.getDaoCompany().getAll(type.name());

        List<SearchInfo> si = new ArrayList<>();

        if (c != null && c.isEmpty() == false && c.get(0) != null) {
            c.stream().forEach((value) -> {
                si.add(new SearchInfo(value.getName(), value.getName()));
            });
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCompany() {
        List<SearchInfo> si = new ArrayList<>();

        for (OwnCompanyEnum comp : OwnCompanyEnum.values()) {
            si.add(new SearchInfo(comp.toString(), comp.toString()));
        }

        return si;
    }

    public String setPrjReference(HttpServletRequest request, Model model, ProjectManagerService srvProjectManager) {
        if (request != null) {
            HttpSession session = request.getSession();

            if (session != null) {
                Collabs collab = srvProjectManager.getDaoCollabs().getById(getUser(session.getId()).getId());
                String prj_reference = collab.getProjectId() + "/" + collab.getProjectPrefix();

                model.addAttribute("prj_reference", prj_reference);

                return prj_reference;
            }
        }

        return null;
    }

    public String getProjectTypeName(String id) {
        for (ProjectTypeEnum type : ProjectTypeEnum.values()) {
            if (type.toString().equals(id)) {
                return type.toString();
            }
        }

        return "";
    }

    public String getProjectStatusName(String id) {
        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            if (status.toString().equals(id)) {
                return status.toString();
            }
        }

        return "";
    }

    public ProjectTypeEnum getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectTypeEnum projectType) {
        this.projectType = projectType;
    }

    public String getCurrencyById(Integer id) {
        String currency = CurrencyEnum.EUR.toString();

        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.getId().equals(id)) {
                currency = value.toString();
            }
        }

        return currency;
    }

    public String getLocationNameById(Integer id) {
        for (LocationEnum pl : LocationEnum.values()) {
            if (pl.getId().equals(id)) {
                return pl.getName();
            }
        }

        return "";
    }

    public Integer getLocationIdByName(String name) {
        for (LocationEnum pl : LocationEnum.values()) {
            if (pl.getName().equals(name)) {
                return pl.getId();
            }
        }

        return 0;
    }

    public String createLocations() {
        String response = "";

        for (LocationEnum location : LocationEnum.values()) {
            response += (location.equals(LocationEnum.GREECE))
                    ? "<option value='" + location.getId() + "' selected>" + location.toString() + "</option>"
                    : "<option value='" + location.getId() + "'>" + location.toString() + "</option>";
        }

        return response;
    }

    public String createCurrency(CurrencyEnum selected) {
        String response = "";

        for (CurrencyEnum currency : CurrencyEnum.values()) {
            response += (currency.equals(selected))
                    ? "<option value='" + currency.getId() + "' selected>" + currency.toString() + "</option>"
                    : "<option value='" + currency.getId() + "'>" + currency.toString() + "</option>";
        }

        return response;
    }

    public void sendMail(String mailServer, String from, String to, String cc, String subject, String messageBody) throws AddressException, MessagingException {
        MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
        mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
        mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
        mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
        mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
        CommandMap.setDefaultCommandMap(mc);

        // Setup mail server
        Properties props = System.getProperties();
        props.put("mail.smtp.host", mailServer);
        //props.put("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "" + 25);
        props.setProperty("mail.smtp.starttls.enable", "true");
        // Get a mail session

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("gpatitakis@hol.gr", "gp!21031965");
            }
        });

        Message message = new MimeMessage(session);
        // Define a new mail message
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        if (!Strings.isNullOrEmpty(cc)) {
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
        }
        message.setSubject(subject);
        // Create a message part to represent the body text
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(messageBody);
        Multipart multipart = new MimeMultipart();
        //add the message body to the mime message
        multipart.addBodyPart(messageBodyPart);
        // Put all message parts in the message
        message.setContent(multipart);
        message.saveChanges();
        // -- Send the message --
        logger.log(Level.INFO, "Send From:{0}", from);
        logger.log(Level.INFO, "Send To:{0}", to);
        logger.log(Level.INFO, "Send CC:{0}", cc);
        // Send the message
        Transport transport = session.getTransport("smtp");
        //transport.connect(mailServer, "technical@marpo.gr", "ma87#37d");
//        transport.connect(mailServer, "info@wcs.com.gr", "n3wp@ssword");
        transport.send(message);
        transport.close();
    }

    public String getCurrencyName(Integer currency) {
        String name = "";

        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.getId().equals(currency)) {
                name = value.getSymbol();
                break;
            }
        }

        return name;
    }
}
