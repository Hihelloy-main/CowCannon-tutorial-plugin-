package com.Helloy.cowCannon.Util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    // Converts ticks to seconds
    public static long ticksToSeconds(long ticks) {
        return ticks / 20;
    }

    // Converts ticks to minutes
    public static long ticksToMinutes(long ticks) {
        return ticksToSeconds(ticks) / 60;
    }

    // Converts ticks to hours
    public static long ticksToHours(long ticks) {
        return ticksToMinutes(ticks) / 60;
    }

    // Converts ticks to days (24-hour)
    public static long ticksToDays(long ticks) {
        return ticksToHours(ticks) / 24;
    }

    // Converts ticks to milliseconds
    public static long ticksToMillis(long ticks) {
        return TimeUnit.SECONDS.toMillis(ticksToSeconds(ticks));
    }

    // Format ticks into human-readable format: "Xd Xh Xm Xs"
    public static String formatTicks(long ticks) {
        long totalSeconds = ticks / 20;

        long days = totalSeconds / 86400;
        long hours = (totalSeconds % 86400) / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        StringBuilder sb = new StringBuilder();
        if (days > 0) sb.append(days).append("d ");
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m ");
        if (seconds > 0 || sb.length() == 0) sb.append(seconds).append("s");

        return sb.toString().trim();
    }
}
