/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.reports;

import com.allone.projectmanager.tools.Templates;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 *
 * @author gpatitakis
 */
public class BillMaterialReport {

    private static final Logger logger = Logger.getLogger(QuotationReport.class.getName());

    private Map<String, String> titleLeft;

    private Map<String, String> titleRight;

    private Set<Map<String, String>> request;

    private String note;

    private String pdfFile;

    public BillMaterialReport(Map<String, Map<String, String>> info, Set<Map<String, String>> request, String note) {
        this.titleLeft = info.get("titleLeft");
        this.titleRight = info.get("titleRight");
        this.request = request;
        this.note = note;

        getTotalPages();
        build(true);
    }

    public String getPdfFile() {
        return pdfFile;
    }

    private void getTotalPages() {
        build(false);
    }

    private void build(boolean createFile) {
        SubreportBuilder subreport = cmp.subreport(new SubreportExpression()).setDataSource(new SubreportDataSourceExpression());

        try {
            JasperPrint print = report()
                    .title(Templates.createTitleComponent(),
                            cmp.horizontalList()
                            .add(
                                    cmp.hListCell(createCustomerComponent(titleLeft, 10, 4)).heightFixedOnTop(),
                                    cmp.hListCell(createCustomerComponent(titleRight, 10, 8)).heightFixedOnTop()
                            ),
                            cmp.verticalGap(20),
                            cmp.text("REQUEST DETAILS").setStyle(Templates.bold12CenteredStyle))
                    .detail(subreport,
                            cmp.verticalGap(20),
                            cmp.text("NOTE").setStyle(Templates.boldStyle),
                            cmp.text(note))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(new JREmptyDataSource(1))
                    .toJasperPrint();

            if (createFile == true) {
                pdfFile = "billmaterial" + titleRight.get("Our Ref").replaceAll("/", "_") + ".pdf";
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(
                        JRExporterParameter.OUTPUT_STREAM,
                        new FileOutputStream("c:\\ProjectManager\\" + pdfFile)
                );
                exporter.exportReport();
            } else {
                titleRight.put("Pages", Integer.toString(print.getPages().size()) + " (including this)");
            }
        } catch (DRException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            logger.log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    private ComponentBuilder<?, ?> createCustomerComponent(Map<String, String> title, int padding, int columns) {
        if (title != null && !title.isEmpty()) {
            HorizontalListBuilder list = cmp.horizontalList().setBaseStyle(stl.style().setLeftPadding(padding));

            for (String key : title.keySet()) {
                addCustomerAttribute(list, key, title.get(key), columns);
            }

            return cmp.verticalList(list);
        }

        return null;
    }

    private void addCustomerAttribute(HorizontalListBuilder list, String label, String value, int columns) {
        if (value != null) {
            list.add(cmp.text(label + ":").setFixedColumns(columns).setStyle(Templates.boldStyle), cmp.text(value)).newRow();
        }
    }

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("item", "price", "available", "quantity");

        if (request != null && !request.isEmpty()) {
            for (Map<String, String> r : request) {
                if (request != null && !request.isEmpty()) {
                    dataSource.add(
                            r.get("item"),
                            r.get("price"),
                            r.get("available"),
                            r.get("quantity")
                    );
                }
            }
        }

        return dataSource;
    }

    private class SubreportExpression extends AbstractSimpleExpression<JasperReportBuilder> {

        private static final long serialVersionUID = 1L;

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
            TextColumnBuilder<String> item = col.column("Item", "item", type.stringType());
            TextColumnBuilder<String> quantity = col.column("Q'nty", "quantity", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> price = col.column("Price/Pc", "price", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> available = col.column("Available", "available", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            JasperReportBuilder report = report()
                    .setTemplate(Templates.reportTemplate)
                    .setColumnStyle(textStyle)
                    .columns(item, quantity, price, available);

            return report;
        }
    }

    private class SubreportDataSourceExpression extends AbstractSimpleExpression<JRDataSource> {

        private static final long serialVersionUID = 1L;

        @Override
        public JRDataSource evaluate(ReportParameters reportParameters) {
            return createDataSource();
        }
    }
}
