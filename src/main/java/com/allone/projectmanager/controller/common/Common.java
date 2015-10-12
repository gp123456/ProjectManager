/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.entities.*;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.SearchCriteria;
import com.allone.projectmanager.model.SearchInfo;
import com.allone.projectmanager.model.User;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.ui.Model;

/**
 *
 * @author antonia
 */
public class Common {

    private ProjectTypeEnum projectType;

    private static final User user = new User();

    private Map<Long, ProjectBillItem> mapProjectBillItems = new HashMap<>();

    private String side_bar;

    private String content;

    private String title;
    
    private String header;

    private SearchCriteria searchCriteria;

    public String createSearchType() {
        List<SearchInfo> info = getSearchCriteriaTypeProject();
        String response = "<option value=\"none\" selected=\"selected\">Select Type</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    private String createSearchStatus() {
        List<SearchInfo> info = getSearchCriteriaStatusProject();
        String response = "<option value=\"none\" selected=\"selected\">Select Status</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String createSearchVessel(ProjectManagerService srvProjectManager, String id) {
        List<SearchInfo> info = getSearchCriteriaVessel(srvProjectManager);
        String response = (Strings.isNullOrEmpty(id)) ? "<option value='-1' selected='selected'>Select Vessel</option>" :
               "<option value='-1' >Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

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

    public String createSearchCustomer(ProjectManagerService srvProjectManager, String name) {
        List<SearchInfo> info = getSearchCriteriaCustomer(srvProjectManager);
        String response = (Strings.isNullOrEmpty(name)) ? "<option value='none' selected='selected'>Select Customer</option>" :
               "<option value='none'>Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                if (!Strings.isNullOrEmpty(name)) {
                    if (si.getId().equals(name)) {
                        response += "<option value='" + si.getName() + "' selected='selected'>" + si.getName() + "</option>";
                    }
                }
                response += "<option value='" + si.getName() + "'>" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String createSearchCompany() {
        List<SearchInfo> info = getSearchCriteriaCompany();
        String response = "<option value=\"none\" selected=\"selected\">Select Company</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getName() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String createSearchContact(ProjectManagerService srvProjectManager, Long id) {
        List<Contact> info = srvProjectManager.getDaoContact().getAll();
        String response = (id == null) ? "<option value='-1' selected='selected'>Select Contact</option>" :
               "<option value='-1' >Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<Contact> it = info.iterator(); it.hasNext();) {
                Contact c = it.next();

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

    public String createSearchReference(ProjectManagerService srvProjectManager, Integer offset, Integer size) {
        List<SearchInfo> info = getSearchCriteriaProject(srvProjectManager, offset, size);
        String response = "<option value=\"none\" selected=\"selected\">Select</option>";

        if (info != null && !info.isEmpty() && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String refreshSearchContent(ProjectManagerService srvProjectManager, Integer offset, Integer size) {
        Map<String, String> content = new HashMap<>();

        content.put("reference", createSearchReference(srvProjectManager, offset, size));
        content.put("type", createSearchType());
        content.put("status", createSearchStatus());
        content.put("vessel", createSearchVessel(srvProjectManager, null));
        content.put("customer", createSearchCustomer(srvProjectManager, null));
        content.put("company", createSearchCompany());
        content.put("contact", createSearchContact(srvProjectManager, null));

        return new Gson().toJson(content);
    }

    public void setHeaderInfo(Model model) {
        model.addAttribute("screen_name", user.getScreen_name());
        model.addAttribute("full_name", user.getFull_name());
        model.addAttribute("last_login", user.getLast_login());
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("title", title);
        model.addAttribute("project_header", header);
        model.addAttribute("side_bar", side_bar);
        model.addAttribute("content", content);
        model.addAttribute("project_reference", user.getProject_reference());
    }

    public void setItemInfo(Model model, ProjectManagerService srvProjectManager) {
        List<Item> items = srvProjectManager.getDaoItem().getAll();

        model.addAttribute("items", items);
    }

    public Collection<ProjectBillItem> getProjectBillItems() {
        return mapProjectBillItems.values();
    }

    public Set<Long> getProjectBillKeys() {
        return mapProjectBillItems.keySet();
    }

    public ProjectBillItem getProjectBillItem(Long id) {
        return mapProjectBillItems.get(id);
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public SearchCriteria getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    public List<SearchInfo> getSearchCriteriaProject(ProjectManagerService srvProjectManager, Integer offset,
                                                     Integer size) {
        List<Project> tp = srvProjectManager.getDaoProject().getAll(offset, size);

        List<SearchInfo> si = new ArrayList<>();

        if (tp != null && tp.isEmpty() == false && tp.get(0) != null) {
            for (Project value : tp) {
                si.add(new SearchInfo(value.getId().toString(), value.getReference()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaTypeProject() {
        List<SearchInfo> si = new ArrayList<>();

        for (ProjectTypeEnum type : ProjectTypeEnum.values()) {
            si.add(new SearchInfo(type.toString(), type.toString()));
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaStatusProject() {
        List<SearchInfo> si = new ArrayList<>();

        for (ProjectStatusEnum status : ProjectStatusEnum.values()) {
            si.add(new SearchInfo(status.toString(), status.toString()));
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaVessel(ProjectManagerService srvProjectManager) {
        List<Vessel> v = srvProjectManager.getDaoVessel().getAll();

        List<SearchInfo> si = new ArrayList<>();

        if (v != null && v.isEmpty() == false && v.get(0) != null) {
            for (Iterator<Vessel> it = v.iterator(); it.hasNext();) {
                Vessel value = it.next();

                si.add(new SearchInfo(value.getId().toString(), value.getName()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCustomer(ProjectManagerService srvProjectManager) {
        List<Company> c = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.CUSTOMER.toString());

        List<SearchInfo> si = new ArrayList<>();

        if (c != null && c.isEmpty() == false && c.get(0) != null) {
            for (Iterator<Company> it = c.iterator(); it.hasNext();) {
                Company value = it.next();

                si.add(new SearchInfo(value.getName(), value.getName()));
            }
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

    public String setPrjReference(Model model, ProjectManagerService srvProjectManager) {
        Collabs collab = srvProjectManager.getDaoCollab().getById(getUser().getId());
        String prj_reference = collab.getProjectId() + "/" + collab.getProjectPrefix();

        model.addAttribute("prj_reference", prj_reference);

        return prj_reference;
    }

    public void setProjectBillInfo(Long projectId, Model model, ProjectManagerService srvProjectManager) {
        ProjectBill pb = srvProjectManager.getDaoProjectBill().findByProjectId(projectId);

        model.addAttribute("project-bill", pb);
    }

    public void setVirtualProjectBillInfo(Long itemId, Model model, ProjectManagerService srvProjectManager) {
        Item item = srvProjectManager.getDaoItem().getById(itemId);

        if (item != null) {
            mapProjectBillItems.put(itemId, new ProjectBillItem.Builder().setAvailable(0).setQuantity(0)
                                    .setPrice(item.getPrice()).setItem(item.getId()).build());
        }
    }

    public void editVirtualProjectBillInfo(ProjectBillItem pbi) {
        mapProjectBillItems.put(pbi.getItem(), pbi);
    }

    public void editVirtualProjectBillInfo(ProjectBill pb) {
        for (ProjectBillItem pbi : mapProjectBillItems.values()) {
            pbi.setId(null);
            pbi.setProjectBill(pb.getId());
        }
    }

    public void removeVirtualProjectBillInfo(Long itemid) {
        mapProjectBillItems.remove(itemid);
    }

    public void clearVirtualProjectBill() {
        mapProjectBillItems.clear();
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
}
