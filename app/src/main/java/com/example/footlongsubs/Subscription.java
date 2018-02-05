package com.example.footlongsubs;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Subscription class holds the data for a single subscription and makes that
 * data available through an array of getters and setters. The setters throw
 * exceptions if the inputs are invalid.
 *
 * @author Daniel Cairns
 * @see AllSubscriptions
 */
public class Subscription {

    private String name;
    private Date startDate;
    private BigDecimal mthlyCharge;
    private String comment;

    // Desired date format expressed as a SimpleDateFormat
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Subscription() {} // Data set individually rather than all at once

    public void setName(String newName) throws NameTooLongException {
        // Make sure name meets length requirement
        if (newName.length() <= 20) {
            this.name = newName;
        } else {
            throw new NameTooLongException();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setDate (String newStartDate)throws DateFormatException {
        try {
            this.startDate = dateFormat.parse(newStartDate);
        } catch (ParseException e) {
            throw new DateFormatException();
        }
    }

    public Date getDate() {
        return this.startDate;
    }

    public void setCharge(String newMthlyCharge) throws ChargeFormatException {
        try {
            this.mthlyCharge = new BigDecimal(newMthlyCharge);
            this.mthlyCharge.setScale(2);
        } catch (NumberFormatException e) {
            throw new ChargeFormatException();
        }
    }

    public BigDecimal getCharge() {
        return this.mthlyCharge;
    }

    public void setComment(String newComment) throws CommentTooLongException {
        if (newComment.length() <= 30) {
            this.name = newComment;
        } else {
            throw new CommentTooLongException();
        }
    }

    public String getComment() {
        return this.comment;
    }
}
