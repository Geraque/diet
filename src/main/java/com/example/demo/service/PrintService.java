package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.enums.EatingTime;
import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class PrintService {

  public byte[] printPlan(PlanItem plan) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Plan");

      // Создаем заголовок
      Row titleRow = sheet.createRow(0);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellValue(plan.getName());
      CellStyle style = workbook.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      titleCell.setCellStyle(style);
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3)); // Объединяем ячейки для заголовка

      // Создаем заголовки столбцов
      Row headerRow = sheet.createRow(1);
      CellStyle headerStyle = workbook.createCellStyle();
      headerStyle.setAlignment(HorizontalAlignment.CENTER);
      String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
          "SUNDAY"};
      for (int i = 0; i < daysOfWeek.length; i++) {
        Cell cell = headerRow.createCell(i+1);
        cell.setCellValue(daysOfWeek[i]);
        cell.setCellStyle(headerStyle);
      }

      // Подготавливаем данные
      Map<EatingTime, Map<DayOfWeek, List<String>>> dataMap = new EnumMap<>(EatingTime.class);
      for (EatingTime time : EatingTime.values()) {
        dataMap.put(time, new EnumMap<>(DayOfWeek.class));
      }

      for (DayItem dayItem : plan.getDays()) {
        DayOfWeek day = dayItem.getDay();
        EatingTime time = dayItem.getEatingTime();
        List<String> ingredients = dayItem.getIngredients().stream()
            .map(ingredientDayItem -> ingredientDayItem.getIngredient().getName() + " x " + ingredientDayItem.getCount())
            .collect(Collectors.toList());
        dataMap.get(time).put(day, ingredients);
      }

      // Заполняем таблицу
      int rowNum = 2;
      for (EatingTime time : EatingTime.values()) {
        Row row = sheet.createRow(rowNum++);;
        Cell timeCell = row.createCell(0);
        timeCell.setCellValue(time.name());
        Map<DayOfWeek, List<String>> dayMap = dataMap.get(time);

        for (int i = 0; i < daysOfWeek.length; i++) {
          DayOfWeek day = DayOfWeek.valueOf(daysOfWeek[i]);
          Cell dayCell = row.createCell(i+1);
          if (dayMap.containsKey(day)) {
            String ingredientText = String.join(", ", dayMap.get(day));
            dayCell.setCellValue(ingredientText);
          }
        }
      }

      // Авто-ресайз колонок
      for (int i = 0; i < daysOfWeek.length + 1; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      return new byte[]{};
    }
  }

  public byte[] printPlan2(PlanItem plan) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("Plan");

      // Создаем заголовок
      Row titleRow = sheet.createRow(0);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellValue(plan.getName());
      CellStyle style = workbook.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      titleCell.setCellStyle(style);
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
          EatingTime.values().length)); // Объединяем ячейки для заголовка

      // Создаем заголовки столбцов
      Row headerRow = sheet.createRow(1);
      CellStyle headerStyle = workbook.createCellStyle();
      headerStyle.setAlignment(HorizontalAlignment.CENTER);
      EatingTime[] eatingTimes = EatingTime.values();
      for (int i = 0; i < eatingTimes.length; i++) {
        Cell cell = headerRow.createCell(i + 1);
        cell.setCellValue(eatingTimes[i].name());
        cell.setCellStyle(headerStyle);
      }

      // Подготавливаем данные
      Map<DayOfWeek, Map<EatingTime, List<String>>> dayMap = new TreeMap<>();
      for (DayItem dayItem : plan.getDays()) {
        DayOfWeek day = dayItem.getDay();
        EatingTime time = dayItem.getEatingTime();
        List<String> ingredients = dayItem.getIngredients().stream()
            .map(ingredientItem -> ingredientItem.getIngredient().getName() + " x "
                + ingredientItem.getCount())
            .collect(Collectors.toList());

        dayMap.putIfAbsent(day, new EnumMap<>(EatingTime.class));
        dayMap.get(day).put(time, ingredients);
      }

      // Заполняем таблицу
      int rowNumber = 2;
      String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY",
          "SUNDAY"};
      for (String dayOfWeek : daysOfWeek) {
        DayOfWeek day = DayOfWeek.valueOf(dayOfWeek);
        Row row = sheet.createRow(rowNumber++);
        Cell dayCell = row.createCell(0);
        dayCell.setCellValue(day.toString());

        Map<EatingTime, List<String>> times = dayMap.getOrDefault(day,
            new EnumMap<>(EatingTime.class));
        for (int i = 0; i < eatingTimes.length; i++) {
          EatingTime time = eatingTimes[i];
          Cell timeCell = row.createCell(i + 1);
          if (times.containsKey(time)) {
            String ingredientsText = String.join(", ", times.get(time));
            timeCell.setCellValue(ingredientsText);
          }
        }
      }

      // Авто-ресайз колонок
      for (int i = 0; i <= eatingTimes.length; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      return new byte[]{};
    }
  }
}


