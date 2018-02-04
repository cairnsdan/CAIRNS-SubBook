package com.example.footlongsubs;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Dan on 2018-02-03.
 */

public class Subscription {

    private String name;
    private Date startDate;
    private BigDecimal mthlyCharge;
    private String comment;

    public void Subscription () {}

    public void setName(String newName) {
        if (newName.length() <= 20) {
            this.name = newName;
        } else {
            throw new NameTooLongException();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setDate(String newStartDate) {
        // Read string as date, convert using DateFormat??
        this.startDate = newStartDate;
        // Date error throw as well
    }

    public Date getDate() {
        return this.startDate;
    }

    public void setCharge(String newMthlyCharge) {
        this.mthlyCharge.setScale(2);
        this.mthlyCharge = new BigDecimal(newMthlyCharge);
        // Throw exception if it happens
    }

    public BigDecimal getCharge() {
        return this.mthlyCharge;
    }

    public void setComment(String newComment) {
        if (newComment.length() <= 30) {
            this.name = newComment;
        } else {
            throw new NameTooLongException();
        }
    }

    public String getComment() {
        return this.comment;
    }
}
