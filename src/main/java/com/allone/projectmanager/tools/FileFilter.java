/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.tools;

import java.io.File;
import java.io.FilenameFilter;

/**
 *
 * @author antonia
 */
public class FileFilter implements FilenameFilter {
    String name;

    public FileFilter(String name) {
        this.name = name;
    }

    public boolean accept(File dir,
                          String name) {
        return name.equals(name);
    }
}
