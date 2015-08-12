/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.tools;

import com.allone.projectmanager.entities.Project;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRImageRenderer;
import net.sf.jasperreports.engine.JRPrintImage;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.type.HorizontalAlignEnum;

/**
 *
 * @author antonia
 */
public class JasperReport {

    private static final String PATH_PROJECT_BILL;

    private static final String PATH_PROJECT;

    private static final int PAGE_WIDTH;

    private static final int PAGE_HEIGHT;

    private static final int PAGE_MARGIN;

    static {
//        PATH_MATERIAL = "C:\\ProjectManager\\Material\\";
        PATH_PROJECT_BILL = "/home/antonia/ProjectManager/ProjectBill/";
//        PATH_PROJECT = "C:\\ProjectManager\\Project\\";
        PATH_PROJECT = "/home/antonia/ProjectManager/Project/";
        PAGE_WIDTH = 600;
        PAGE_HEIGHT = 850;
        PAGE_MARGIN = 20;
    }

    private static void PrintSetNormalStyle(JasperPrint jasperPrint,
                                            JRDesignStyle normalStyle) throws JRException {
        normalStyle.setName("Sans_Normal");
        normalStyle.setDefault(true);
        normalStyle.setFontName("DejaVu Sans");
        normalStyle.setFontSize(8);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        normalStyle.setPdfEmbedded(false);
        jasperPrint.addStyle(normalStyle);
    }

    private static void PrintSetBoldStyle(JasperPrint jasperPrint,
                                          JRDesignStyle boldStyle) throws JRException {
        boldStyle.setName("Sans_Bold");
        boldStyle.setFontName("DejaVu Sans");
        boldStyle.setFontSize(8);
        boldStyle.setBold(true);
        boldStyle.setPdfFontName("Helvetica-Bold");
        boldStyle.setPdfEncoding("Cp1252");
        boldStyle.setPdfEmbedded(false);
        jasperPrint.addStyle(boldStyle);
    }

    private static void PrintSetItalicStyle(JasperPrint jasperPrint,
                                            JRDesignStyle italicStyle) throws JRException {
        italicStyle.setName("Sans_Italic");
        italicStyle.setFontName("DejaVu Sans");
        italicStyle.setFontSize(8);
        italicStyle.setItalic(true);
        italicStyle.setPdfFontName("Helvetica-Oblique");
        italicStyle.setPdfEncoding("Cp1252");
        italicStyle.setPdfEmbedded(false);
        jasperPrint.addStyle(italicStyle);
    }

    private static void PrintText(JasperPrint jasperPrint,
                                  JRPrintPage page,
                                  JRDesignStyle Style,
                                  String strInfo,
                                  HorizontalAlignEnum Align,
                                  int x,
                                  int y,
                                  int w,
                                  int TextSize,
                                  boolean bUnderline)
            throws JRException {
        w = (w == 0) ? strInfo.length() * TextSize : w;
        x = (x == 0) ? PAGE_MARGIN : x;
        if (Align.equals(HorizontalAlignEnum.CENTER)) {
            x = ((PAGE_WIDTH - (2 * PAGE_MARGIN)) / 2) - (w / 2);
        }
        JRPrintText text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        text.setX(x);
        text.setY(y);
        text.setWidth(w);
        text.setHeight(TextSize);
        text.setTextHeight(text.getHeight());
        text.setHorizontalAlignment(Align);
        text.setLineSpacingFactor(1.3133681f);
        text.setLeadingOffset(-4.955078f);
        text.setStyle(Style);
        text.setFontSize(TextSize);
        text.setUnderline(bUnderline);
        text.setText(strInfo);
        String strBad = strInfo;
        strBad.toUpperCase();
        if (strBad.compareTo("BAD") == 0) {
            text.setForecolor(Color.RED);
        }
        page.addElement(text);
    }

    private static void PrintTextArea(JasperPrint jasperPrint,
                                      JRPrintPage page,
                                      JRDesignStyle Style,
                                      String strInfo,
                                      HorizontalAlignEnum Align,
                                      int x,
                                      int y,
                                      int w,
                                      int h,
                                      int TextSize,
                                      boolean bUnderline) throws JRException {
        w = (w == 0) ? strInfo.length() * TextSize : w;
        x = (x == 0) ? PAGE_MARGIN : x;
        if (Align.equals(HorizontalAlignEnum.CENTER)) {
            x = ((PAGE_WIDTH - (2 * PAGE_MARGIN)) / 2) - (w / 2);
        }
        JRPrintText text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        text.setX(x);
        text.setY(y);
        text.setWidth(w);
        text.setHeight(h);
        text.setTextHeight(text.getHeight());
        text.setHorizontalAlignment(Align);
        text.setLineSpacingFactor(1.3133681f);
        text.setLeadingOffset(-4.955078f);
        text.setStyle(Style);
        text.setFontSize(TextSize);
        text.setUnderline(bUnderline);
        text.setText(strInfo);
        String strBad = strInfo;
        strBad.toUpperCase();
        if (strBad.compareTo("BAD") == 0) {
            text.setForecolor(Color.RED);
        }
        page.addElement(text);
    }

    private static void PrintImage(JasperPrint jasperPrint,
                                   JRPrintPage page,
                                   String strInfo,
                                   ScaleImageEnum Scale,
                                   int y,
                                   int w,
                                   int h) throws JRException {
        JRPrintImage image = new JRBasePrintImage(jasperPrint.getDefaultStyleProvider());
        image.setX(PAGE_MARGIN);
        image.setY(y);
        image.setWidth(w);
        image.setHeight(h);
        image.setScaleImage(Scale);

        image.setRenderer(JRImageRenderer.getInstance(JRLoader.loadBytesFromResource(strInfo)));
        page.addElement(image);
    }

    private static JasperPrint ProjectBillPrint() throws JRException {
        //JasperPrint
        JasperPrint jasperPrint = new JasperPrint();
        JRPrintPage page = new JRBasePrintPage();
        JRDesignStyle normalStyle = new JRDesignStyle();
        JRDesignStyle boldStyle = new JRDesignStyle();
        JRDesignStyle italicStyle = new JRDesignStyle();
        jasperPrint.setName("NoReport");
        jasperPrint.setPageWidth(PAGE_WIDTH);
        jasperPrint.setPageHeight(PAGE_HEIGHT);

        //Fonts
        PrintSetNormalStyle(jasperPrint, normalStyle);
        PrintSetBoldStyle(jasperPrint, boldStyle);
        PrintSetItalicStyle(jasperPrint, italicStyle);

        //Body
        PrintImage(jasperPrint, page, "images/wcslogo.jpg", ScaleImageEnum.CLIP, 10, 550, 100);
        PrintText(jasperPrint, page, boldStyle, "EVALUATION",
                  net.sf.jasperreports.engine.type.HorizontalAlignEnum.CENTER, 0, 105, 0, 14, true);
        PrintText(jasperPrint, page, boldStyle, "COMPANY:", HorizontalAlignEnum.RIGHT, 0, 135, 130, 10, false);
        PrintText(jasperPrint, page, boldStyle, "ATTN:", HorizontalAlignEnum.RIGHT, 0, 150, 130, 10, false);
        int Offset = 250;
        PrintText(jasperPrint, page, boldStyle, "COMMENTS", HorizontalAlignEnum.LEFT, 0, Offset + 15, 0, 10, true);
        String strNotes = "".toString(), strTemp = strNotes + (char) Character.LINE_SEPARATOR, strLine;
        int nWidth = PAGE_WIDTH - (2 * PAGE_MARGIN);
        int nLines = 0, nPos = strTemp.indexOf(Character.LINE_SEPARATOR);

        if (nPos != -1) {
            do {
                strLine = strTemp.substring(0, nPos);
                strTemp = strTemp.substring(nPos + 1);
                float fLines = ((float) strLine.length() * (float) 10) / (float) nWidth;
                int nExtLines = ((fLines % 2.00) == 0) ? (int) fLines : (int) fLines + 1;
                nLines += nExtLines;
            } while ((nPos = strTemp.indexOf(Character.LINE_SEPARATOR)) != -1);
        }

        int nHeight = (nLines + 1) * 10;
        PrintTextArea(jasperPrint, page, normalStyle, strNotes, HorizontalAlignEnum.LEFT, 0, Offset + 30, nWidth,
                      nHeight, 10, false);
        PrintText(jasperPrint, page, boldStyle, "FOR WCS HELLAS & CO", HorizontalAlignEnum.CENTER, 0, Offset + nHeight
                                                                                                      + 45, 0, 10, false);

        jasperPrint.addPage(page);

        return jasperPrint;
    }

    private static JasperPrint ProjectPrint(Project p,
                                            String status,
                                            String type,
                                            String userName,
                                            String vesselName) throws
            JRException {
        //JasperPrint
        JasperPrint jasperPrint = new JasperPrint();
        JRPrintPage page = new JRBasePrintPage();
        JRDesignStyle normalStyle = new JRDesignStyle();
        JRDesignStyle boldStyle = new JRDesignStyle();
        JRDesignStyle italicStyle = new JRDesignStyle();
        jasperPrint.setName("NoReport");
        jasperPrint.setPageWidth(PAGE_WIDTH);
        jasperPrint.setPageHeight(PAGE_HEIGHT);

        //Fonts
        PrintSetNormalStyle(jasperPrint, normalStyle);
        PrintSetBoldStyle(jasperPrint, boldStyle);
        PrintSetItalicStyle(jasperPrint, italicStyle);

        //Body
        PrintImage(jasperPrint, page, "images/wcslogo.jpg", ScaleImageEnum.CLIP, 10, 550, 100);
        PrintText(jasperPrint, page, boldStyle, p.getReference(),
                  net.sf.jasperreports.engine.type.HorizontalAlignEnum.CENTER, 0, 105, 0, 14, true);
        PrintText(jasperPrint, page, boldStyle, "COMPANY:", HorizontalAlignEnum.RIGHT, 0, 135, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, p.getCompany(), HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2)
                                                                                            + 145, 135, 0, 10, false);
        PrintText(jasperPrint, page, boldStyle, "VESSEL:", HorizontalAlignEnum.RIGHT, 0, 150, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, vesselName, HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2) + 145,
                  150, 0, 10, false);
        PrintText(jasperPrint, page, boldStyle, "CUSTOMER:", HorizontalAlignEnum.RIGHT, 0, 165, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, p.getCustomer(), HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2)
                                                                                             + 145, 165, 0, 10, false);
        PrintText(jasperPrint, page, boldStyle, "ATTN:", HorizontalAlignEnum.RIGHT, 0, 180, 130, 10, false);
        PrintText(jasperPrint, page, boldStyle, "USER:", HorizontalAlignEnum.RIGHT, 0, 195, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, userName, HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2) + 145, 195,
                  0, 10, false);
        PrintText(jasperPrint, page, boldStyle, "STATUS:", HorizontalAlignEnum.RIGHT, 0, 210, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, status, HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2) + 145, 210,
                  0, 10, false);
        PrintText(jasperPrint, page, boldStyle, "TYPE:", HorizontalAlignEnum.RIGHT, 0, 225, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, type, HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2) + 145, 225, 0,
                  10, false);
        PrintText(jasperPrint, page, boldStyle, "Create at:", HorizontalAlignEnum.RIGHT, 0, 240, 130, 10, false);
        PrintText(jasperPrint, page, normalStyle, new SimpleDateFormat("yyyy-MM-dd").format(p.getCreated()),
                  HorizontalAlignEnum.LEFT, (int) (PAGE_MARGIN / 2) + 145, 240, 0, 10, false);

        jasperPrint.addPage(page);

        return jasperPrint;
    }

    public static void createProjectBillReport(String path,
                                            String fileName) throws JRException {
        File f = new File(PATH_PROJECT_BILL);

        if (!f.exists()) {
            f.mkdirs();
        }

        String strPath = PATH_PROJECT_BILL + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";

        f = new File(strPath);

        if (!f.exists()) {
            f.mkdirs();
        } else {
            File[] files = f.listFiles(new FileFilter(fileName));

            for (File file : files) {
                file.delete();
            }
        }

        File destFile = new File(strPath + fileName);

        JRPdfExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = ProjectBillPrint();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.exportReport();
    }

    public static void createProjectReport(Project p,
                                           String status,
                                           String type,
                                           String userName,
                                           String vesselName) throws
            JRException {
        String fileName = p.getReference().replace("/", "_") + ".pdf";
        File f = new File(PATH_PROJECT);

        if (!f.exists()) {
            f.mkdirs();
        }

        String strPath = PATH_PROJECT + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";

        f = new File(strPath);

        if (!f.exists()) {
            f.mkdirs();
        }

        File destFile = new File(strPath + fileName);

        JRPdfExporter exporter = new JRPdfExporter();
        JasperPrint jasperPrint = ProjectPrint(p, status, type, userName, vesselName);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());
        exporter.exportReport();
    }

    public static String getPATH_PROJECT_BILL() {
        return PATH_PROJECT_BILL;
    }

    public static String getPATH_PROJECT() {
        return PATH_PROJECT;
    }
}
