package com.ilongli.springboot.scode.a24;

import lombok.extern.slf4j.Slf4j;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author ilongli
 * @date 2023/5/13 17:20
 */
@Slf4j
public class MyDateFormatter implements Formatter<Date> {

    private final String desc;

    public MyDateFormatter(String desc) {
        this.desc = desc;
    }

    @Override
    public String print(Date date, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy|MM|dd");
        return sdf.format(date);
    }

    @Override
    public Date parse(String text, Locale locale) throws ParseException {
        log.debug(">>>>>>>> 进入了：{}", desc);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy|MM|dd");
        return sdf.parse(text);
    }
}
