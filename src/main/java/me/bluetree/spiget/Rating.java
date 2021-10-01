package me.bluetree.spiget;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rating {

    private final int count;
    private final double average;

    public Rating(int count, double averge) {
        this.count = count;
        this.average = averge;
    }

    public double getAverage() {
        return average;
    }

    public int getCount() {
        return count;
    }

    public Float getRate() {
        return BigDecimal.valueOf(average).setScale(1, RoundingMode.HALF_UP).floatValue();
    }
}
