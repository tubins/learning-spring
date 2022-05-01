package com.tubz.learningspring.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Date utils.
 */
@Component
public class DateUtils {

    /**
     * Create date from date string.
     *
     * @param dateString date string.
     * @return date object in yyy-MM-dd format.
     */
    public Date createDateFromDateString(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        if (StringUtils.hasText(dateString)) {
            try {
                date = format.parse(dateString);
            } catch (ParseException e) {
                date = new Date();
            }
        } else {
            date = new Date();
        }
        return date;
    }
}
