package me.bluetree.spiget;


public class Rating {
    private int count;
    private int average;
    public Rating(int count , int averge){
        this.count = count;
        this.average = count;
    }

    public int getAverage() {
        return average;
    }

    public int getCount() {
        return count;
    }
}
