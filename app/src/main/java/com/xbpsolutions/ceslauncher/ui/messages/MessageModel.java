package com.xbpsolutions.ceslauncher.ui.messages;

import java.util.Date;

/**
 * Created by excellent-3 on 15/04/17.
 */

public class MessageModel {

    private String number;
    private String body;
    private Date receivedDate;
    private String person;

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public MessageModel() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }
}
