package net.yorksolutions.apiexercise;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeInformation {
    public String date;
    @JsonProperty("milliseconds_since_epoch")
    public long millisecondsSinceEpoch;
    public String time;

    @Autowired
    public DateTimeInformation() {
        var now = new Date();
        var dateFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        this.date = LocalDate.now().format(dateFormatter);
        this.millisecondsSinceEpoch = System.currentTimeMillis();
        var timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
        this.time = LocalTime.now().format(timeFormatter);
        //        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        System.out.println(formatter.format(now));

    }
}
