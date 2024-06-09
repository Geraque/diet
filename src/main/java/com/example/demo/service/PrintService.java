package com.example.demo.service;

import com.example.demo.entity.DayItem;
import com.example.demo.entity.PlanItem;
import com.example.demo.entity.RealDayItem;
import com.example.demo.entity.enums.EatingTime;
import java.io.ByteArrayOutputStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
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
      Sheet sheet = workbook.createSheet("План");

      // Создаем заголовок
      Row titleRow = sheet.createRow(0);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellValue(plan.getName());
      CellStyle style = workbook.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      titleCell.setCellStyle(style);
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, EatingTime.values().length));

      // Создаем заголовки столбцов с русскими названиями
      Row headerRow = sheet.createRow(1);
      CellStyle headerStyle = workbook.createCellStyle();
      headerStyle.setAlignment(HorizontalAlignment.CENTER);
      String[] eatingTimesRussian = {"ЗАВТРАК", "ОБЕД", "УЖИН"};
      for (int i = 0; i < eatingTimesRussian.length; i++) {
        Cell cell = headerRow.createCell(i + 1);
        cell.setCellValue(eatingTimesRussian[i]);
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

      // Русские названия дней недели
      String[] daysOfWeekRussian = {"ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА",
          "СУББОТА", "ВОСКРЕСЕНЬЕ"};

      // Заполняем таблицу
      int rowNumber = 2;
      DayOfWeek[] daysOfWeek = DayOfWeek.values();
      for (int j = 0; j < daysOfWeek.length; j++) {
        DayOfWeek day = daysOfWeek[j];
        Row row = sheet.createRow(rowNumber++);
        Cell dayCell = row.createCell(0);
        dayCell.setCellValue(daysOfWeekRussian[j]);

        Map<EatingTime, List<String>> times = dayMap.getOrDefault(day,
            new EnumMap<>(EatingTime.class));
        for (int i = 0; i < EatingTime.values().length; i++) {
          EatingTime time = EatingTime.values()[i];
          Cell timeCell = row.createCell(i + 1);
          if (times.containsKey(time)) {
            String ingredientsText = String.join(", ", times.get(time));
            timeCell.setCellValue(ingredientsText);
          }
        }
      }

      // Авто-ресайз колонок
      for (int i = 0; i < eatingTimesRussian.length + 1; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      return new byte[]{};
    }
  }

  public byte[] printWeek(PlanItem plan, LocalDate startWeek, LocalDate endWeek) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      Sheet sheet = workbook.createSheet("План");

      // Создаем заголовок
      Row titleRow = sheet.createRow(0);
      Cell titleCell = titleRow.createCell(0);
      titleCell.setCellValue(plan.getName() + " (" + startWeek.format(
          DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " + endWeek.format(
          DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")");
      CellStyle style = workbook.createCellStyle();
      style.setAlignment(HorizontalAlignment.CENTER);
      titleCell.setCellStyle(style);
      sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, EatingTime.values().length));

      // Создаем заголовки столбцов с русскими названиями
      Row headerRow = sheet.createRow(1);
      CellStyle headerStyle = workbook.createCellStyle();
      headerStyle.setAlignment(HorizontalAlignment.CENTER);
      String[] eatingTimesRussian = {"ЗАВТРАК", "ОБЕД", "УЖИН"};
      for (int i = 0; i < eatingTimesRussian.length; i++) {
        Cell cell = headerRow.createCell(i + 1);
        cell.setCellValue(eatingTimesRussian[i]);
        cell.setCellStyle(headerStyle);
      }

      // Подготавливаем данные
      Map<DayOfWeek, Map<EatingTime, List<String>>> dayMap = new TreeMap<>();
      List<RealDayItem> realDays = plan.getRealDays().stream()
          .filter(day -> isDateWithinRange(day.getDate(), startWeek, endWeek))
          .collect(Collectors.toList());
      ;
      for (RealDayItem dayItem : realDays) {
        DayOfWeek day = dayItem.getDay();
        EatingTime time = dayItem.getEatingTime();
        List<String> ingredients = dayItem.getIngredients().stream()
            .map(ingredientItem -> ingredientItem.getIngredient().getName() + " x "
                + ingredientItem.getCount())
            .collect(Collectors.toList());

        dayMap.putIfAbsent(day, new EnumMap<>(EatingTime.class));
        dayMap.get(day).put(time, ingredients);
      }

      // Русские названия дней недели
      String[] daysOfWeekRussian = {"ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА",
          "СУББОТА", "ВОСКРЕСЕНЬЕ"};

      // Заполняем таблицу
      int rowNumber = 2;
      DayOfWeek[] daysOfWeek = DayOfWeek.values();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("ru"));
      for (int j = 0; j < daysOfWeek.length; j++) {
        DayOfWeek day = daysOfWeek[j];
        Row row = sheet.createRow(rowNumber++);
        Cell dayCell = row.createCell(0);
        dayCell.setCellValue(
            daysOfWeekRussian[j] + " (" + startWeek.plusDays(j).format(formatter) + ")");

        Map<EatingTime, List<String>> times = dayMap.getOrDefault(day,
            new EnumMap<>(EatingTime.class));
        for (int i = 0; i < EatingTime.values().length; i++) {
          EatingTime time = EatingTime.values()[i];
          Cell timeCell = row.createCell(i + 1);
          if (times.containsKey(time)) {
            String ingredientsText = String.join(", ", times.get(time));
            timeCell.setCellValue(ingredientsText);
          }
        }
      }

      // Авто-ресайз колонок
      for (int i = 0; i < eatingTimesRussian.length + 1; i++) {
        sheet.autoSizeColumn(i);
      }

      workbook.write(outputStream);
      return outputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      return new byte[]{};
    }
  }

  public byte[] printPlanReal(PlanItem plan) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
      List<RealDayItem> reals = plan.getRealDays();
      for (int c = 0; c < reals.size() / 21; c++) {
        LocalDate startWeek = reals.get(c * 21).getDate();
        LocalDate endWeek = reals.get(21 * (c + 1) - 1).getDate();
        Sheet sheet = workbook.createSheet("(" + startWeek.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " + endWeek.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")");
        // Создаем заголовок
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue(plan.getName() + " (" + startWeek.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " - " + endWeek.format(
            DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ")");
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        titleCell.setCellStyle(style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, EatingTime.values().length - 1));

        // Создаем заголовки столбцов с русскими названиями
        Row headerRow = sheet.createRow(1);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        String[] eatingTimesRussian = {"ЗАВТРАК", "ОБЕД", "УЖИН"};
        for (int i = 0; i < eatingTimesRussian.length; i++) {
          Cell cell = headerRow.createCell(i + 1);
          cell.setCellValue(eatingTimesRussian[i]);
          cell.setCellStyle(headerStyle);
        }

        // Подготавливаем данные
        Map<DayOfWeek, Map<EatingTime, List<String>>> dayMap = new TreeMap<>();
        List<RealDayItem> realDays = plan.getRealDays().stream()
            .filter(day -> isDateWithinRange(day.getDate(), startWeek, endWeek))
            .collect(Collectors.toList());
        for (RealDayItem dayItem : realDays) {
          DayOfWeek day = dayItem.getDay();
          EatingTime time = dayItem.getEatingTime();
          List<String> ingredients = dayItem.getIngredients().stream()
              .map(ingredientItem -> ingredientItem.getIngredient().getName() + " x "
                  + ingredientItem.getCount())
              .collect(Collectors.toList());

          dayMap.putIfAbsent(day, new EnumMap<>(EatingTime.class));
          dayMap.get(day).put(time, ingredients);
        }

        // Русские названия дней недели
        String[] daysOfWeekRussian = {"ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА",
            "СУББОТА", "ВОСКРЕСЕНЬЕ"};

        // Заполняем таблицу
        int rowNumber = 2;
        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM", new Locale("ru"));
        for (int j = 0; j < daysOfWeek.length; j++) {
          DayOfWeek day = daysOfWeek[j];
          Row row = sheet.createRow(rowNumber++);
          Cell dayCell = row.createCell(0);
          dayCell.setCellValue(
              daysOfWeekRussian[j] + " (" + startWeek.plusDays(j).format(formatter) + ")");

          Map<EatingTime, List<String>> times = dayMap.getOrDefault(day,
              new EnumMap<>(EatingTime.class));
          for (int i = 0; i < EatingTime.values().length; i++) {
            EatingTime time = EatingTime.values()[i];
            Cell timeCell = row.createCell(i + 1);
            if (times.containsKey(time)) {
              String ingredientsText = String.join(", ", times.get(time));
              timeCell.setCellValue(ingredientsText);
            }
          }
        }

        // Авто-ресайз колонок
        for (int i = 0; i < eatingTimesRussian.length + 1; i++) {
          sheet.autoSizeColumn(i);
        }
      }

      workbook.write(outputStream); // Переместили эту строку за пределы цикла

      return outputStream.toByteArray();
    } catch (Exception e) {
      e.printStackTrace();
      return new byte[]{};
    }
  }



  private static boolean isDateWithinRange(LocalDate date, LocalDate start, LocalDate end) {
    return (date.isEqual(start) || date.isAfter(start)) &&
        (date.isEqual(end) || date.isBefore(end));
  }
}


