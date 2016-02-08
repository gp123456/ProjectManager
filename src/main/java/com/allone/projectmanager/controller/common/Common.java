/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.WCSProjectManagerService;
import com.allone.projectmanager.entities.*;
import com.allone.projectmanager.entities.wcs.WCSCompany;
import com.allone.projectmanager.entities.wcs.WCSVessel;
import com.allone.projectmanager.enums.CompanyTypeEnum;
import com.allone.projectmanager.enums.CurrencyEnum;
import com.allone.projectmanager.enums.OwnCompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.enums.WCSProjectStatusEnum;
import com.allone.projectmanager.model.ProjectBillModel;
import com.allone.projectmanager.model.SearchCriteria;
import com.allone.projectmanager.model.SearchInfo;
import com.allone.projectmanager.model.User;
import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
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

    private final Map<ProjectBillModel, List<ProjectBillItem>> mapProjectBillItems = new HashMap<>();

    private final Map<ProjectBillModel, ProjectBill> mapProjectBill = new HashMap<>();

    private String side_bar;

    private String content;

    private String title;

    private String header;

    private SearchCriteria searchCriteria;

    public String createSearchType() {
        List<SearchInfo> info = getSearchCriteriaTypeProject();
        String finalResponse = "<option value=\"none\" selected=\"selected\">Select Type</option>";
        String response = "";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            response += info.stream().map((si) -> "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>").
            reduce(response, String::concat);
            finalResponse += response;
        }

        return finalResponse;
    }

    public String createSearchVessel(WCSProjectManagerService srvWCSProjectManager, String id) {
        List<SearchInfo> info = getSearchCriteriaVessel(srvWCSProjectManager);
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

    public String createSearchCompany(WCSProjectManagerService srvWCSProjectManager, String name, CompanyTypeEnum type) {
        List<SearchInfo> info = getSearchCriteriaCompany(srvWCSProjectManager, type);
        String response = (Strings.isNullOrEmpty(name)) ?
               "<option value='none' selected='selected'>Select " + type.toString().toLowerCase() + "</option>" :
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

    public Long getNextNoStockItemId() {
        return 1000000l + mapProjectBillItems.size() + 1;
    }

    public Collection<ProjectBillItem> getProjectBillItems(ProjectBillModel pbm) {
        return mapProjectBillItems.get(pbm);
    }

    public Set<ProjectBillModel> getProjectBillDetailIds() {
        return mapProjectBillItems.keySet();
    }

    public Set<ProjectBillModel> getPBMIKeysByPDId(ProjectBillModel pbm) {
        if (pbm != null && !mapProjectBillItems.isEmpty()) {
            Set<ProjectBillModel> keys = new HashSet<>();

            getProjectBillDetailIds().stream().filter((key) -> (key.equals(pbm))).forEach((key) -> {
                keys.add(key);
            });

            return keys;
        }

        return null;
    }

    public ProjectBill getProjectBill(ProjectBillModel pdm) {
        return mapProjectBill.get(pdm);
    }

    public Set<ProjectBillModel> getProjectBillIds() {
        return (mapProjectBill != null && !mapProjectBill.isEmpty()) ? mapProjectBill.keySet() : null;
    }

    public Set<ProjectBillModel> getPBMKeysByPDId(ProjectBillModel pbm) {
        if (pbm != null && !mapProjectBill.isEmpty()) {
            Set<ProjectBillModel> keys = new HashSet<>();

            getProjectBillIds().stream().filter((key) -> (key.equals(pbm))).forEach((key) -> {
                keys.add(key);
            });

            return keys;
        }

        return null;
    }

    public ProjectBillItem getProjectBillItem(ProjectBillModel pbm, Long itemId) {
        if (!mapProjectBillItems.isEmpty()) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null && !items.isEmpty()) {
                Map<Long, ProjectBillItem> result = items.stream().collect(Collectors.toMap(
                                           ProjectBillItem::getItem, (c) -> c));

                return result.get(itemId);
            }
        }

        return null;
    }

    public ProjectBillItem getFirstProjectBillItem(ProjectBillModel pbm) {
        if (!mapProjectBillItems.isEmpty() && pbm != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null && !items.isEmpty()) {
                return items.get(0);
            }
        }

        return null;
    }

    public Boolean changeCurrencyFirstItem(ProjectBillModel pbm, ProjectBillItem pbi) {
        if (!mapProjectBillItems.isEmpty() && pbm != null && pbi != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null && !items.isEmpty()) {
                ProjectBillItem item = items.get(0);
                Integer currency = item.getCurrency();

                return item.getItem().equals(pbi.getItem()) && currency != null && !currency.equals(pbi.getCurrency());
            }
        }

        return Boolean.FALSE;
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

    public List<SearchInfo> getSearchCriteriaVessel(WCSProjectManagerService srvWCSProjectManager) {
        List<WCSVessel> v = srvWCSProjectManager.getDaoWCSVessel().getAll();

        List<SearchInfo> si = new ArrayList<>();

        if (v != null && v.isEmpty() == false && v.get(0) != null) {
            logger.log(Level.INFO, "Vessel size={0}", v.size());
            
            v.stream().forEach((value) -> {
                si.add(new SearchInfo(value.getId(), value.getName()));
            });
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCompany(WCSProjectManagerService srvWCSProjectManager, CompanyTypeEnum type) {
        List<WCSCompany> c = srvWCSProjectManager.getDaoWCSCompany().getAllByType(type.toString());

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

    public void setVirtualProjectBill(ProjectBill pb, Integer locationId) {
        if (pb != null) {
            mapProjectBill.put(new ProjectBillModel(pb.getProject(), locationId), pb);
        }
    }

    public void removeVirtualProjectBill(ProjectBillModel pbm) {
        if (pbm != null) {
            mapProjectBill.remove(pbm);
        }
    }

    public void setVirtualProjectBillItem(ProjectBillModel pbm, ProjectBillItem pbi) {
        if (pbm != null && pbi != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null) {
                items.add(pbi);
            } else {
                mapProjectBillItems.put(pbm, new ArrayList<>(Arrays.asList(pbi)));
            }
        }
    }

    public void editVirtualProjectBillItem(ProjectBillModel pbm, ProjectBillItem pbi) {
        if (pbm != null && pbi != null && pbi.getItem() != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null && !items.isEmpty()) {
                items.stream().
                        filter((item) -> (item.getItem().equals(pbi.getItem()))).
                        forEach((item) -> {
                    item = pbi;
                });
            }
        }
    }

    public void saveVirtualProjectBillItem(ProjectBillModel pbm) {
        if (pbm != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null && !items.isEmpty()) {
                items.stream().forEach((item) -> {
                    item.setClassSave("button");
                });
            }
        }
    }

    public void editCurrencyVirtualProjectBillItems(ProjectBillModel pdm, Integer currency) {
        if (pdm != null && currency != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pdm);

            if (items != null && !items.isEmpty()) {
                items.stream().forEach((item) -> {
                    item.setCurrency(currency);
                });
            }
        }
    }

    public void setVirtualProjectBillItemBillId(ProjectBillModel pbm, Long billId) {
        if (pbm != null && billId != null) {
            List<ProjectBillItem> items = mapProjectBillItems.get(pbm);

            if (items != null && !items.isEmpty()) {
                items.forEach((item) -> {
                    item.setProjectBill(billId);
                });
            }
        }
    }

    public void removeVirtualProjectBillItem(Long pdId, Integer location, Long itemid) {
        List<ProjectBillItem> items = mapProjectBillItems.get(new ProjectBillModel(pdId, location));

        if (items != null && !items.isEmpty()) {
            for (ProjectBillItem item : items) {
                if (item.getItem().equals(itemid)) {
                    items.remove(item);
                    break;
                }
            }
        }
    }

    public void clearVirtualProjectBill(ProjectBillModel pbm) {
        mapProjectBillItems.remove(pbm);
        mapProjectBill.remove(pbm);
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
}
