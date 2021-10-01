package me.bluetree.spiget;

public class Review {

    private final Rating rating;
    private final String message;
    private final String version;
    private final Long Date;
    private final int ID;

    public Review(String message, String version, Long Date, int id, Rating rating) {
        this.message = message;
        this.version = version;
        this.Date = Date;
        this.ID = id;
        this.rating = rating;
    }
    public Rating getRating() {
        return rating;
    }
    public String getMessage() {
        return message;
    }
    public String getVersion() {
        return version;
    }
    public Long getDate() {
        return Date;
    }
    public int getId() {
        return ID;
    }
}
