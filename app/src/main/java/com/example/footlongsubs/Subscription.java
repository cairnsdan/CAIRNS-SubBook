package com.example.footlongsubs;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dan on 2018-02-03.
 */

public class Subscription {

    private String name;
    private Date startDate;
    private BigDecimal mthlyCharge;
    private String comment;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Subscription() {}

    public void setName(String newName) throws NameTooLongException {
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
