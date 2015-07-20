/*
 * To change this license header, choose License Headers in ProjectService Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.common;

import com.allone.projectmanager.ProjectManagerService;
import com.allone.projectmanager.entities.*;
import com.allone.projectmanager.enums.CompanyEnum;
import com.allone.projectmanager.enums.ProjectStatusEnum;
import com.allone.projectmanager.enums.ProjectTypeEnum;
import com.allone.projectmanager.model.SearchCriteria;
import com.allone.projectmanager.model.SearchInfo;
import com.allone.projectmanager.model.User;
import com.google.gson.Gson;
import java.math.BigDecimal;
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
    enum enumTypeCompany {
        Customer(1), Supplier(2), Shipper(3), Company(4);

        private final Integer value;

        private enumTypeCompany(Integer value) {
            this.value = value;
        }

        public Integer getValue() {
            return value;
        }
    }

//    enum enumServices {
//        Collab("Collab"), Company("Company"), Exchange("Exchange"), Contact("Contact"), Email("Email"), Item("Item"), ItemTrans("ItemTrans"), ProjectService("ProjectService"), Repimage("Repimage"),
//        Report("Report"), Rights("Rights"), SalesTrans("SalesTrans"), Stock("Stock"), StockTrans("StockTrans"), Vessel("Vessel"), TypeProject("TypeProject"), StatusProject("StatusProject");
//        
//        private final String value;
//
//        private enumServices(String value) {
//            this.value = value;
//        }
//
//        public String getValue() {
//            return value;
//        }
//    }
    private String projectType;

    private static final User user = new User();

    private Map<Long, ProjectBillItem> mapProjectBillItems = new HashMap<>();

    private String side_bar;

    private String content;
    
    private String title;

    private SearchCriteria searchCriteria;

    private String createSearchType(ProjectManagerService srvProjectManager) {
        List<SearchInfo> info = getSearchCriteriaTypeProject(srvProjectManager);
        String response = "<option value=\"-1\" selected=\"selected\">Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    private String createSearchStatus(ProjectManagerService srvProjectManager) {
        List<SearchInfo> info = getSearchCriteriaStatusProject(srvProjectManager);
        String response = "<option value=\"-1\" selected=\"selected\">Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    private String createSearchVessel(ProjectManagerService srvProjectManager) {
        List<SearchInfo> info = getSearchCriteriaVessel(srvProjectManager);
        String response = "<option value=\"-1\" selected=\"selected\">Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getId() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    private String createSearchCustomer(ProjectManagerService srvProjectManager) {
        List<SearchInfo> info = getSearchCriteriaCustomer(srvProjectManager);
        String response = "<option value=\"-1\" selected=\"selected\">Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getName() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    private String createSearchCompany(ProjectManagerService srvProjectManager) {
        List<SearchInfo> info = getSearchCriteriaCompany(srvProjectManager);
        String response = "<option value=\"-1\" selected=\"selected\">Select</option>";

        if (info != null && info.isEmpty() == false && info.get(0) != null) {
            for (Iterator<SearchInfo> it = info.iterator(); it.hasNext();) {
                SearchInfo si = it.next();

                response += "<option value=\"" + si.getName() + "\">" + si.getName() + "</option>";
            }
        }

        return response;
    }

    public String createSearchReference(ProjectManagerService srvProjectManager, Integer offset, Integer size) {
        List<SearchInfo> info = getSearchCriteriaProject(srvProjectManager, offset, size);
        String response = "<option value=\"-1\" selected=\"selected\">Select</option>";

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
        content.put("type", createSearchType(srvProjectManager));
        content.put("status", createSearchStatus(srvProjectManager));
        content.put("vessel", createSearchVessel(srvProjectManager));
        content.put("customer", createSearchCustomer(srvProjectManager));
        content.put("company", createSearchCompany(srvProjectManager));

        return new Gson().toJson(content);
    }

    public void setHeaderInfo(Model model) {
        model.addAttribute("screen_name", user.getScreen_name());
        model.addAttribute("full_name", user.getFull_name());
        model.addAttribute("last_login", user.getLast_login());
        model.addAttribute("avatar", user.getAvatar());
        model.addAttribute("title", title);
        model.addAttribute("side_bar", side_bar);
        model.addAttribute("content", content);
        model.addAttribute("project_reference", user.getProject_reference());
    }

    public void setItemInfo(Model model,
                            ProjectManagerService srvProjectManager) {
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
        System.out.println("id=" + id);
        
        return mapProjectBillItems.get(id);
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
                si.add(new SearchInfo(value.getId(), value.getReference()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaTypeProject(ProjectManagerService srvProjectManager) {
        List<TypeProject> tp = srvProjectManager.getDaoTypeProject().getAll();

        List<SearchInfo> si = new ArrayList<>();

        if (tp != null && tp.isEmpty() == false && tp.get(0) != null) {
            for (Iterator<TypeProject> it = tp.iterator(); it.hasNext();) {
                TypeProject value = it.next();
                si.add(new SearchInfo(value.getId(), value.getAbbreviation()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaStatusProject(ProjectManagerService srvProjectManager) {
        List<StatusProject> sp = srvProjectManager.getDaoStatusProject().getAll();

        List<SearchInfo> si = new ArrayList<>();

        if (sp != null && sp.isEmpty() == false && sp.get(0) != null) {
            for (Iterator<StatusProject> it = sp.iterator(); it.hasNext();) {
                StatusProject value = it.next();
                si.add(new SearchInfo(value.getId(), value.getAbbreviation()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaVessel(ProjectManagerService srvProjectManager) {
        List<Vessel> v = srvProjectManager.getDaoVessel().getAll();

        List<SearchInfo> si = new ArrayList<>();

        if (v != null && v.isEmpty() == false && v.get(0) != null) {
            for (Iterator<Vessel> it = v.iterator(); it.hasNext();) {
                Vessel value = it.next();
                si.add(new SearchInfo(value.getId(), value.getName()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCustomer(ProjectManagerService srvProjectManager) {
        List<Company> c = srvProjectManager.getDaoCompany().getAll(enumTypeCompany.Customer.getValue());

        List<SearchInfo> si = new ArrayList<>();

        if (c != null && c.isEmpty() == false && c.get(0) != null) {
            for (Iterator<Company> it = c.iterator(); it.hasNext();) {
                Company value = it.next();
                si.add(new SearchInfo(0l, value.getName()));
            }
        }

        return si;
    }

    public List<SearchInfo> getSearchCriteriaCompany(ProjectManagerService srvProjectManager) {
        List<Company> c = srvProjectManager.getDaoCompany().getAll(enumTypeCompany.Company.getValue());

        List<SearchInfo> si = new ArrayList<>();

        if (c != null && c.isEmpty() == false && c.get(0) != null) {
            for (Iterator<Company> it = c.iterator(); it.hasNext();) {
                Company value = it.next();

                si.add(new SearchInfo(0l, value.getName()));
            }
        }

        return si;
    }

    public String setPrjReference(Model model,
                                  ProjectManagerService srvProjectManager) {
        Collabs collab = srvProjectManager.getDaoCollab().getById(getUser().getId());
        String prj_reference = collab.getProjectId() + "/" + collab.getProjectPrefix();

        model.addAttribute("prj_reference", prj_reference);

        return prj_reference;
    }

    public void setProjectBillInfo(Long projectId,
                                   Model model,
                                   ProjectManagerService srvProjectManager) {
        ProjectBill pb = srvProjectManager.getDaoProjectBill().findByProjectId(projectId);

        model.addAttribute("project-bill", pb);
    }

    public void setVirtualProjectBillInfo(Long itemId,
                                          Model model,
                                          ProjectManagerService srvProjectManager) {
        Item item = srvProjectManager.getDaoItem().getById(itemId);

        if (item != null) {
            mapProjectBillItems.put(itemId, new ProjectBillItem(0, item.getQuantity(), item.getPrice(), item));
        } else {
            item = srvProjectManager.getDaoItem().add(new Item(itemId));
            mapProjectBillItems.put(itemId, new ProjectBillItem(0, 0, BigDecimal.ZERO, item));
        }
    }

    public void editVirtualProjectBillInfo(ProjectBillItem pbi) {
        mapProjectBillItems.put(pbi.getItemId().getId(), pbi);
    }

    public void editVirtualProjectBillInfo(ProjectBill pb) {
        for (ProjectBillItem pbi : mapProjectBillItems.values()) {
            pbi.setId(null);
            pbi.setProjectBillId(pb);
        }
    }

    public void removeVirtualProjectBillInfo(Long itemid) {
        mapProjectBillItems.remove(itemid);
    }

    public void clearVirtualProjectBill() {
        mapProjectBillItems.clear();
    }

    public String getProjectTypeName(Long id) {
        for (ProjectTypeEnum value : ProjectTypeEnum.values()) {
            if (value.getId().equals(id)) {
                return value.getValue();
            }
        }

        return "";
    }

    public String getProjectStatusName(Long id) {
        for (ProjectStatusEnum value : ProjectStatusEnum.values()) {
            if (value.getId().equals(id)) {
                return value.getValue();
            }
        }

        return "";
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Long getProjectType(String type) {
        Long result = 0l;

        if (type.equals(ProjectTypeEnum.SALE.getValue()) || type.equals(CompanyEnum.MTS.name())) {
            result = ProjectTypeEnum.SALE.getId();
        } else if (type.equals(ProjectTypeEnum.SERVICE.getValue())) {
            result = ProjectTypeEnum.SERVICE.getId();
        }

        return result;
    }
}
