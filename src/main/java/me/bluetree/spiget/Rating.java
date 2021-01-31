package me.bluetree.spiget;


import java.math.BigDecimal;
import java.math.RoundingMode;

public class Rating {
    private int count;
    private Long average;
    public Rating(int count , Long averge){
        this.count = count;
        this.average = averge;
    }

    public Long getAverage() {
        return average;
    }

    public int getCount() {
        return count;
    }

    public Float getRate() {
        return BigDecimal.valueOf(average).setScale(1, RoundingMode.HALF_UP).floatValue();
    }


}
