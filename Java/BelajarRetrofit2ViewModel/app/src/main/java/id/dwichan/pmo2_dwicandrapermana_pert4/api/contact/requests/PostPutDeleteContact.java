package id.dwichan.pmo2_dwicandrapermana_pert4.api.contact.requests;

import com.google.gson.annotations.SerializedName;

import id.dwichan.pmo2_dwicandrapermana_pert4.data.Contact;

public class PostPutDeleteContact {
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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
    private Contact contact;

    @SerializedName("message")
    private String message;
}
