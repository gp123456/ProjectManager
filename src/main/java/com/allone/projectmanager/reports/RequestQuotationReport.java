/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allone.projectmanager.reports;

import com.allone.projectmanager.tools.Templates;
import com.google.common.base.Strings;
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
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.component.ComponentBuilder;
import net.sf.dynamicreports.report.builder.component.HorizontalListBuilder;
import net.sf.dynamicreports.report.builder.component.SubreportBuilder;
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
public class RequestQuotationReport {

    private static final Logger logger = Logger.getLogger(QuotationReport.class.getName());

    private Map<String, String> titleLeft;

    private Set<Map<String, String>> requestRFQS;

    private Set<Map<String, String>> requestRFQD;

    private String note;

    private String supplierNote;

    private String pdfFile;

    public RequestQuotationReport(
            Map<String, Map<String, String>> info,
            Set<Map<String, String>> requestRFQS,
            Set<Map<String, String>> requestRFQD,
            String note,
            String supplierNote) {
        this.titleLeft = info.get("titleLeft");
        this.requestRFQS = requestRFQS;
        this.requestRFQD = requestRFQD;
        this.note = note;
        this.supplierNote = supplierNote;

        build(true);
    }

    public String getPdfFile() {
        return pdfFile;
    }

    private void build(boolean createFile) {
        SubreportBuilder subreportRFQS = cmp.subreport(new SubreportExpressionRFQS()).setDataSource(new SubreportRFQS());
        SubreportBuilder subreportRFQD = cmp.subreport(new SubreportExpressionRFQD()).setDataSource(new SubreportRFQD());

        try {
            JasperPrint print = report()
                    .title(Templates.createTitleComponent(),
                            cmp.horizontalList().add(cmp.hListCell(createCustomerComponent(titleLeft, 10, 8)).heightFixedOnTop()),
                            cmp.verticalGap(20),
                            cmp.text("Request for Quotation Summary").setStyle(Templates.bold12CenteredStyle))
                    .detail(subreportRFQS,
                            cmp.text("Request for Quotation Details").setStyle(Templates.bold12CenteredStyle),
                            subreportRFQD,
                            cmp.verticalGap(20),
                            cmp.text("Note").setStyle(Templates.boldStyle),
                            cmp.text((!Strings.isNullOrEmpty(note)) ? note : ""),
                            cmp.text("Supplier Note").setStyle(Templates.boldStyle),
                            cmp.text((!Strings.isNullOrEmpty(supplierNote)) ? supplierNote : ""))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(new JREmptyDataSource(1))
                    .toJasperPrint();

            if (createFile == true) {
                pdfFile = "requestquotation" + titleLeft.get("Our Ref").replaceAll("/", "_") + ".pdf";
                JRExporter exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                exporter.setParameter(
                        JRExporterParameter.OUTPUT_STREAM,
                        new FileOutputStream("c:\\ProjectManager\\" + pdfFile)
                );
                exporter.exportReport();
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

    private JRDataSource createDataSourceRFQS() {
        DRDataSource dataSource = new DRDataSource("delivery_cost", "other_expenses", "material_cost", "grand_total");

        if (requestRFQS != null && !requestRFQS.isEmpty()) {
            for (Map<String, String> r : requestRFQS) {
                dataSource.add(
                        r.get("delivery_cost"),
                        r.get("other_expenses"),
                        r.get("material_cost"),
                        r.get("grand_total")
                );
            }
        }

        return dataSource;
    }

    private JRDataSource createDataSourceRFQD() {
        DRDataSource dataSource = new DRDataSource("code", "description", "quantity", "price_unit", "discount", "availability", "net_total");

        if (requestRFQD != null && !requestRFQD.isEmpty()) {
            for (Map<String, String> r : requestRFQD) {
                dataSource.add(
                        r.get("code"),
                        r.get("description"),
                        r.get("quantity"),
                        r.get("price_unit"),
                        r.get("discount"),
                        r.get("availability"),
                        r.get("net_total")
                );
            }
        }

        return dataSource;
    }

    private class SubreportExpressionRFQS extends AbstractSimpleExpression<JasperReportBuilder> {

        private static final long serialVersionUID = 1L;

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
            TextColumnBuilder<String> item = col.column("Delivery Cost", "delivery_cost",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> quantity = col.column("Other Expences", "other_expenses",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> price = col.column("Material Cost", "material_cost",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> available = col.column("Grand Total", "grand_total",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            JasperReportBuilder report = report()
                    .setTemplate(Templates.reportTemplate)
                    .setColumnStyle(textStyle)
                    .columns(item, quantity, price, available);

            return report;
        }
    }

    private class SubreportExpressionRFQD extends AbstractSimpleExpression<JasperReportBuilder> {

        private static final long serialVersionUID = 1L;

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
            TextColumnBuilder<String> code = col.column("Code", "code", type.stringType());
            TextColumnBuilder<String> description = col.column("Description", "description",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> quantity = col.column("Quantity", "quantity",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> priceUnit = col.column("Price Unit", "price_unit",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> discount = col.column("Discount(%)", "discount",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> availability = col.column("Available (Days)", "availability",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> netTotal = col.column("Net Total", "net_total",
                    type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            JasperReportBuilder report = report()
                    .setTemplate(Templates.reportTemplate)
                    .setColumnStyle(textStyle)
                    .columns(code, description, quantity, priceUnit, discount, availability, netTotal);

            return report;
        }
    }

    private class SubreportRFQS extends AbstractSimpleExpression<JRDataSource> {

        private static final long serialVersionUID = 1L;

        @Override
        public JRDataSource evaluate(ReportParameters reportParameters) {
            return createDataSourceRFQS();
        }
    }

    private class SubreportRFQD extends AbstractSimpleExpression<JRDataSource> {

        private static final long serialVersionUID = 1L;

        @Override
        public JRDataSource evaluate(ReportParameters reportParameters) {
            return createDataSourceRFQD();
        }
    }
}
