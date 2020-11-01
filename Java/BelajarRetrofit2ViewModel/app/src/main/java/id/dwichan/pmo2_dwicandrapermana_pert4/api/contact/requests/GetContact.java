package id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.requests;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import id.dwichan.pmo2_dwicandrapermana_pert4.data.Contact;

public class GetContact {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Contact> getListContact() {
        return listContact;
    }

    public void setListContact(ArrayList<Contact> listContact) {
        this.listContact = listContact;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("status")
    private String status;

    @SerializedName("result")
    private ArrayList<Contact> listContact;

    @SerializedName("message")
    private String message;
}
