/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2016 Ricardo Mariaca
 * http://www.dynamicreports.org
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */
package com.allone.projectmanager.reports;

import com.allone.projectmanager.tools.Templates;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import static net.sf.dynamicreports.report.builder.DynamicReports.*;

import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;

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
 * @author Ricardo Mariaca (r.mariaca@dynamicreports.org)
 */
public class QuotationReport {

    private static final Logger logger = Logger.getLogger(QuotationReport.class.getName());

    private Map<String, String> titleLeft;

    private Map<String, String> titleRight;

    private String welcome;

    private Set<Map<String, Set<Map<String, String>>>> offers;

    private String remarks;

    private Map<String, String> additional;

    private String notes;

    private String pdfFile;

    public QuotationReport(Map<String, Map<String, String>> info, Set<Map<String, Set<Map<String, String>>>> offers) {
        this.titleLeft = info.get("titleLeft");
        this.titleRight = info.get("titleRight");
        this.welcome = info.get("comments").get("welcome");
        this.offers = offers;
        this.remarks = info.get("comments").get("remarks");
        this.additional = info.get("additional");
        this.notes = info.get("comments").get("notes");

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
                            cmp.text(welcome),
                            cmp.verticalGap(20),
                            cmp.text("OFFER DETAILS").setStyle(Templates.bold12CenteredStyle))
                    .detail(subreport, cmp.verticalGap(20),
                            cmp.text(remarks).setStyle(Templates.styleItalicUnderlineBold),
                            cmp.verticalGap(20),
                            cmp.text("ADDITIONAL DETAILS").setStyle(Templates.bold12CenteredStyle),
                            cmp.horizontalList().add(cmp.hListCell(createCustomerComponent(additional, 10, 10)).heightFixedOnTop()),
                            cmp.verticalGap(20),
                            cmp.text(notes))
                    .pageFooter(Templates.footerComponent)
                    .setDataSource(new JREmptyDataSource(1))
                    .toJasperPrint();

            if (createFile == true) {
                pdfFile = "quotation" + titleRight.get("Our Ref").replaceAll("/", "_") + ".pdf";
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

    private JRDataSource createDataSource() {
        DRDataSource dataSource = new DRDataSource("location", "description", "type", "quantity", "price", "discount", "total");

        if (offers != null && !offers.isEmpty()) {
            for (Map<String, Set<Map<String, String>>> offer : offers) {
                if (offer != null && !offer.isEmpty()) {
                    for (String location : offer.keySet()) {
                        Set<Map<String, String>> values = offer.get(location);

                        if (values != null && !values.isEmpty()) {
                            for (Map<String, String> value : values) {
                                dataSource.add(
                                        location,
                                        value.get("description"),
                                        value.get("type"),
                                        value.get("quantity"),
                                        value.get("price"),
                                        value.get("discount"),
                                        value.get("total")
                                );
                            }
                        }
                    }
                }
            }
        }

        return dataSource;
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

    private class SubreportExpression extends AbstractSimpleExpression<JasperReportBuilder> {

        private static final long serialVersionUID = 1L;

        @Override
        public JasperReportBuilder evaluate(ReportParameters reportParameters) {
            StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
            TextColumnBuilder<String> location = col.column("Location", "location", type.stringType()).setStyle(Templates.boldStyle);
            TextColumnBuilder<String> description = col.column("Description", "description", type.stringType());
            TextColumnBuilder<String> _type = col.column("Type", "type", type.stringType());
            TextColumnBuilder<String> quantity = col.column("Q'nty", "quantity", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> price = col.column("Price/Pc", "price", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> discount = col.column("Disc(%)", "discount", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            TextColumnBuilder<String> total = col.column("Total", "total", type.stringType()).setHorizontalAlignment(HorizontalAlignment.RIGHT);
            ColumnGroupBuilder locationGroup = grp.group(location);
            JasperReportBuilder report = report()
                    .setTemplate(Templates.reportTemplate)
                    .setColumnStyle(textStyle)
                    .columns(description, _type, quantity, price, discount, total)
                    .groupBy(locationGroup);

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

    public static void main(String[] args) {
        new QuotationReport(null, null);
    }
}
