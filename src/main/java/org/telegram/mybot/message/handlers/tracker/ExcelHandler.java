package org.telegram.mybot.message.handlers.tracker;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xddf.usermodel.chart.*;
import org.apache.poi.xssf.usermodel.*;
import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.tracker.entity.PlanRecord;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelHandler extends Handler<Message> {
    private final User user;
    private final ServiceManager serviceManager;

    public ExcelHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve (Message msg) {
        String name = user.getUserName() + " plans.xlsx";
        sender.sendDocument(SendDocument.builder()
                .chatId(msg.getChatId())
                .caption("Your plans:")
                .document(getExel(serviceManager.getTrackerService().getAllPlans(user), name ))
                .build()
        );
        try {
           Files.deleteIfExists(Paths.get(name));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private InputFile getExel(Map<LocalDate, List<PlanRecord>> allPlans, String name) {

        try(XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet sheet = wb.createSheet("Plans");
            XSSFCellStyle dateStyle = wb.createCellStyle();
            dateStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd"));
            List<LocalDate> list = allPlans.keySet().stream().sorted().toList();

            fillDate(sheet, dateStyle, list);
            fillPercentComplete(sheet, allPlans, list);

            XSSFCellStyle completeStyle = wb.createCellStyle();
            completeStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            completeStyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
            XSSFCellStyle inCompleteStyle = wb.createCellStyle();
            inCompleteStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            inCompleteStyle.setFillForegroundColor(IndexedColors.RED.getIndex());

            Integer maxRows = allPlans.values()
                    .stream()
                    .map(List::size)
                    .max(Integer::compareTo)
                    .orElse(0);

            fillPlans(sheet, completeStyle, inCompleteStyle, list, allPlans, maxRows);

            for (int i = 0; i < list.size(); i++) {
                sheet.autoSizeColumn(i);
            }

            createLineChart(sheet, list.size());

            try (FileOutputStream fileOut = new FileOutputStream(name)) {
                wb.write(fileOut);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new InputFile(new File(name));
    }

    private void createLineChart(XSSFSheet sheet, int size) {
        if(size == 0 ) return;
        XSSFDrawing drawing = sheet.createDrawingPatriarch();
        XSSFClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 0, sheet.getLastRowNum() + 2, 30, sheet.getLastRowNum() + 12);
        XSSFChart chart =  drawing.createChart(anchor);

        XDDFCategoryAxis categoryAxis = chart.createCategoryAxis(AxisPosition.BOTTOM);
        categoryAxis.setVisible(true);
        XDDFValueAxis valueAxis = chart.createValueAxis(AxisPosition.LEFT);
        valueAxis.setMinimum(0);
        XDDFLineChartData data = (XDDFLineChartData) chart.createData( ChartTypes.LINE, categoryAxis, valueAxis);

        XDDFDataSource<String> xs = XDDFDataSourcesFactory.fromStringCellRange(sheet, new CellRangeAddress(0, 0, 0, size-1));
        XDDFNumericalDataSource<Double> ys = XDDFDataSourcesFactory.fromNumericCellRange(sheet, new CellRangeAddress(1, 1, 0, size-1));

        data.addSeries(xs, ys);
        chart.plot(data);

    }

    private void fillPlans(XSSFSheet sheet, XSSFCellStyle completeStyle, XSSFCellStyle inCompleteStyle, List<LocalDate> dateList, Map<LocalDate, List<PlanRecord>> allPlans, Integer maxRows) {
        ArrayList<Row> rows = new ArrayList<>(maxRows);
        for (int i = 0; i < maxRows; i++) {
            rows.add(sheet.createRow(i + 2));
        }

        for (int i = 0; i < dateList.size(); i++) {
            List<PlanRecord> plans = allPlans.get(dateList.get(i));
            for (int j = 0; j < plans.size(); j++) {
                Cell cell = rows.get(j).createCell(i);
                cell.setCellStyle(plans.get(j).getIsComplete() ? completeStyle : inCompleteStyle);
                cell.setCellValue(plans.get(j).getPlan());
            }
        }

    }

    private void fillPercentComplete(XSSFSheet sheet, Map<LocalDate, List<PlanRecord>> allPlans, List<LocalDate> dates) {
        Row row = sheet.createRow(1);
        for (int i = 0; i < dates.size(); i++) {
            List<PlanRecord> plans = allPlans.get(dates.get(i));
            double percent = 100.0 * plans.stream().filter(PlanRecord::getIsComplete).count() / plans.size();
            Cell cell = row.createCell(i);
            cell.setCellValue(percent);
        }
    }

    private void fillDate(XSSFSheet sheet, XSSFCellStyle dateStyle, List<LocalDate> dates) {
        Row dateRow = sheet.createRow(0);
        for (int i = 0; i < dates.size(); i++) {
            Cell cell = dateRow.createCell(i);
            cell.setCellStyle(dateStyle);
            cell.setCellValue(dates.get(i));
        }

    }



}