/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.Monopoly.control;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author OCTI-Lucas
 */
public class Formatador {

    public static LocalDate toLocalDate(Date data) {
        if (data == null) {
            return null;
        }
        return LocalDate.from(Instant.ofEpochMilli(data.getTime()).atZone(ZoneId.systemDefault()));
    }

    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static String formatarData(Date date, String formato) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(formato).format(date);
    }

    public static String toDate(Date date) {
        return formatarData(date, "dd/MM/yyyy");
    }

    public static String toMonth(Date date) {
        return formatarData(date, "MM");
    }

    public static String toMonthName(Date date) {
        return formatarData(date, "MMM");
    }

    public static String toFullMonthName(Date date) {
        return formatarData(date, "MMMM");
    }

    public static String toDay(Date date) {
        return formatarData(date, "dd");
    }

    public static String toYear(Date date) {
        return formatarData(date, "yyyy");
    }

    public static String toHour(Date date) {
        return formatarData(date, "HH:mm:ss");
    }

    public static String formartarDouble(String formato, double value) {
        return new DecimalFormat(formato).format(value);
    }
}
