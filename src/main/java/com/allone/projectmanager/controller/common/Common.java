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
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.ui.Model;

/**
 *
 * @author antonia
 */
public class Common {

    private static final Logger logger = Logger.getLogger(Common.class.getName());

    private ProjectTypeEnum projectType;

    private static final User user = new User();

    private final Map<Long, List<ProjectBillItem>> mapProjectBillItems = new HashMap<>();

    private String side_bar;

    private String content;

    private String title;

    private String header;

    private SearchCriteria searchCriteria;

    public String createSearchType() {
        List<SearchInfo> info = getSearchCriteriaTypeProject();
        String response = "<option value=\"none\" selected=\"selected\">Select Type</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream()
            .map((si) -> "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>").
            reduce(response, String::concat);
        }

        return response;
    }

    private String createSearchStatus() {
        List<SearchInfo> info = getSearchCriteriaStatusProject();
        String response = "<option value=\"none\" selected=\"selected\">Select Status</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream()
            .map((si) -> "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>").
            reduce(response, String::concat);
        }

        return response;
    }

    public String createSearchVessel(ProjectManagerService srvProjectManager, String id) {
        List<SearchInfo> info = getSearchCriteriaVessel(srvProjectManager);
        String response =
               (Strings.isNullOrEmpty(id)) ? "<option value='-1' selected='selected'>Select Vessel</option>" :
               "<option value='-1' >Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (SearchInfo si : info) {
                if (!Strings.isNullOrEmpty(id)) {
                    if (si.getId().equals(id)) {
                        response += "<option value='" + si.getId() + "' selected='selected'>" + si.getName() +
                        "</option>";
                    }
                }
                response += "<option value='" + si.getId() + "'>" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String createSearchCustomer(ProjectManagerService srvProjectManager, String name) {
        List<SearchInfo> info = getSearchCriteriaCustomer(srvProjectManager);
        String response = (Strings.isNullOrEmpty(name)) ?
               "<option value='none' selected='selected'>Select Customer</option>" :
               "<option value='none'>Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (SearchInfo si : info) {
                if (!Strings.isNullOrEmpty(name)) {
                    if (si.getId().equals(name)) {
                        response += "<option value='" + si.getName() + "' selected='selected'>" + si.getName() +
                        "</option>";
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
            response += info.stream()
            .map((si) -> "<option value=\"" + si.getName() + "\">" + si.getName() + "</option>").reduce(response,
                                                                                                          String::concat);
        }

        return response;
    }

    public String createSearchContact(ProjectManagerService srvProjectManager, Long id) {
        List<Contact> info = srvProjectManager.getDaoContact().getAll();
        String response = (id == null) ? "<option value='-1' selected='selected'>Select Contact</option>" :
               "<option value='-1' >Select</option>";

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

    public String createSearchReference(ProjectManagerService srvProjectManager, Integer offset, Integer size) {
        List<SearchInfo> info = getSearchCriteriaProject(srvProjectManager, offset, size);
        String response = "<option value=\"none\" selected=\"selected\">Select</option>";

        if (info != null && !info.isEmpty() && info.get(0) != null) {
            response = info.stream().
            map((si) -> "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>").reduce(response,
                                                                                                       String::concat);
        }

        return response;
    }

    public String refreshSearchContent(ProjectManagerService srvProjectManager, Integer offset, Integer size) {
        Map<String, String> contentMap = new HashMap<>();

        contentMap.put("reference", createSearchReference(srvProjectManager, offset, size));
        contentMap.put("type", createSearchType());
        contentMap.put("status", createSearchStatus());
        contentMap.put("vessel", createSearchVessel(srvProjectManager, null));
        contentMap.put("customer", createSearchCustomer(srvProjectManager, null));
        contentMap.put("company", createSearchCompany());
        contentMap.put("contact", createSearchContact(srvProjectManager, null));

        return new Gson().toJson(contentMap);
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

    public Collection<ProjectBillItem> getProjectBillItems(Long pdId) {
        return mapProjectBillItems.get(pdId);
    }

    public Set<Long> getProjectBillDetailIds() {
        return mapProjectBillItems.keySet();
    }

    public ProjectBillItem getProjectBillItem(Long pdId, Long itemId) {

        Map<Long, ProjectBillItem> result = mapProjectBillItems.get(pdId).stream().collect(Collectors.toMap(
                                   ProjectBillItem::getItem, (c) -> c));

        return result.get(itemId);
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
            tp.stream().forEach((Project value) -> {
                si.add(new SearchInfo(value.getId().toString(), value.getReference()));
            });
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
            v.stream().forEach((value) -> {
                si.add(new SearchInfo(value.getId().toString(), value.getName()));
            });
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCustomer(ProjectManagerService srvProjectManager) {
        List<Company> c = srvProjectManager.getDaoCompany().getAll(CompanyTypeEnum.CUSTOMER.toString());

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

    public void setVirtualProjectBillInfo(Long pdId, Long itemId, ProjectManagerService srvProjectManager) {
        if (pdId != null && itemId != null) {
            Item item = srvProjectManager.getDaoItem().getById(itemId);
            ProjectBillItem pbi = new ProjectBillItem.Builder().setAvailable(item.getQuantity()).setQuantity(0).
                            setPrice(item.getPrice()).setItem(item.getId()).build();
            List<ProjectBillItem> items = mapProjectBillItems.get(pdId);

            if (items != null) {
                items.add(pbi);
            } else {
                mapProjectBillItems.put(pdId, new ArrayList<>(Arrays.asList(pbi)));
            }
        }
    }

    public void editVirtualProjectBillItem(Long pdId, ProjectBillItem pbi) {
        if (pdId != null && pbi != null && pbi.getItem() != null) {
            mapProjectBillItems.get(pdId).add(new ProjectBillItem.Builder().build(pbi));
        }
    }

    public void setVirtualProjectBillItemBillId(Long pdId, Long billId) {
        if (pdId != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pdId);

            if (items != null && !items.isEmpty()) {
                items.forEach((item) -> {
                    item.setProjectBill(billId);
                });
            }
        }
    }

    public void removeVirtualProjectBillInfo(Long pdId, Long itemid) {
        mapProjectBillItems.get(pdId).remove(itemid);
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
