package com.sarah.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * A class to represent a review
 * Created by sarah on 9/26/2017.
 */
@Entity
@Table(name = "review")
public class Review {

    private int reviewId;
    private String body;
    private int userId;
    private int locationId;
    private LocalDateTime date;

    public Review(){
    }

    public Review(String body, int userId, int locationId, LocalDateTime date) {
        this.body = body;
        this.userId = userId;
        this.locationId = locationId;
        this.date = date;
    }

    public Review(int reviewId, String body, int userId, int locationId, LocalDateTime date) {
        this.reviewId = reviewId;
        this.body = body;
        this.userId = userId;
        this.locationId = locationId;
        this.date = date;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }



    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
