package com.example.findweather;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class Helper {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String epochTimeToLocaleDataTime(String epochTime) {
        long time = Long.parseLong(epochTime);
        return String.valueOf(LocalDateTime.ofEpochSecond(time, 0, ZoneOffset.ofHours(3))
                .format(DateTimeFormatter.ofPattern("HH:mm:ss")));
    }
}
