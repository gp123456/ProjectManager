/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gpatitakis
 */
public class DownloadFile {

    private static final Logger logger = Logger.getLogger(DownloadFile.class.getName());

    public static boolean get(String strFileName, HttpServletResponse response) {
        logger.log(Level.INFO, "strFileName={0}", strFileName);

        File file = new File("c:\\ProjectManager\\" + strFileName);
        if (file.canRead() == false) {
            return false;
        }

        logger.log(Level.INFO, "strFileName opened");

        response.setContentType("application/octet-stream");
//        response.setHeader("Content-Transfer-Encoding", "binary");
        response.setHeader("Content-Disposition", "attachment; filename=" + strFileName);
        response.setContentLength((int) file.length());

        try {
            OutputStream out = response.getOutputStream();
            InputStream in = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int len;

            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            logger.log(Level.INFO, "strFileName out");

            out.flush();
            in.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);

            return false;
        }

        return true;
    }
}
