package com.example.footlongsubs;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dan on 2018-02-03.
 */

public class AllSubscriptions {

    private ArrayList<Subscription> subList;

    public AllSubscriptions() {
        subList = new ArrayList<Subscription>();
    }

    public void addSubscription(String name, String startDate, String mthlyCharge, String comment)
                    throws InputErrorException {

        Subscription newSub = new Subscription();

        // Add name
        try {
            newSub.setName(name);
        } catch (NameTooLongException e) {
            throw new InputErrorException();
        }

        // Add date
        try {
            newSub.setDate(startDate);
        } catch (DateFormatException e) {
            throw new InputErrorException();
        }

        // Add monthly charge
        try {
            newSub.setCharge(mthlyCharge);
        } catch (ChargeFormatException e) {
            throw new InputErrorException();
        }

        // Add comment
        try {
            newSub.setComment(comment);
        } catch (CommentTooLongException e) {
            throw new InputErrorException();
        }

        // All fields read properly, add to subList
        subList.add(newSub);
    }

    public void deleteSubscription(Subscription deletedSub) {
        subList.remove(deletedSub);
    }

    public ArrayList<Subscription> getSubList() {
        return subList;
    }

    public BigDecimal sumCharges() {
        BigDecimal total = new BigDecimal(0);
        total = total.setScale(2);

        int i = subList.size() - 1;
        while (i >= 0) {
            total = total.add(subList.get(i).getCharge());
            i--;
        }

        return total;
    }

}
