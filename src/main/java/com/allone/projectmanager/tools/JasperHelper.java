package com.allone.projectmanager.tools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

public class JasperHelper {

    public static String generateReport(String jasper, String filename, Map<?, Object> params) throws IOException, JRException {
        InputStream is = JasperHelper.class.getResourceAsStream(jasper);

        if (is != null) {
            JRDataSource dataSource = new SingleEntryJasperDataSource();
            JasperPrint jasperPrint = JasperFillManager.fillReport(is, (Map<String, Object>) params, dataSource);
            File tempFile = File.createTempFile(filename, ".pdf", new File("c:\\ProjectManager"));
            JasperExportManager.exportReportToPdfFile(jasperPrint, tempFile.getAbsolutePath());

            return tempFile.getAbsolutePath();
        }

        return null;
    }
}
