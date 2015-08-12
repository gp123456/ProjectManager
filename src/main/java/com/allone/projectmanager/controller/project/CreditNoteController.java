/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.controller.project;

import com.allone.projectmanager.controller.common.Common;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author antonia
 */
@Controller
@RequestMapping(value = "/project-cretid")
public class CreditNoteController extends Common {
    @RequestMapping(value = "/credit-note")
    public String CreditNote(Model model) {
        this.setTitle("Projects-Credit Note");
        this.setSide_bar("../project/sidebar.jsp");
        this.setContent("../project/CreditNote.jsp");
        setHeaderInfo(model);
        
        return "index";
    }
}
