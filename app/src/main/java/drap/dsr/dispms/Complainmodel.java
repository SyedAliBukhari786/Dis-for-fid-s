package drap.dsr.dispms;

import java.util.Date;

public class Complainmodel {

    private  String Address;
    private  String City;
    private  String Contact;
    private  String Dosage_Form;
    private  String ID;
    private  String Location;
    private  String Name;
    private  String Name_of_Drug;
    private  String Remarks;
    private  String TYPE;
    private Date Complain_Date;




    public Complainmodel(){


    }

    public Complainmodel(String address, String city, String contact, String dosage_Form, String ID, String location, String name, String name_of_Drug, String remarks, String TYPE,Date complain_Date) {
        Address = address;
        City = city;
        Contact = contact;
        Dosage_Form = dosage_Form;
        this.ID = ID;
        Location = location;
        Name = name;
        Name_of_Drug = name_of_Drug;
        Remarks = remarks;
        this.TYPE = TYPE;
        Complain_Date = complain_Date;

    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getDosage_Form() {
        return Dosage_Form;
    }

    public void setDosage_Form(String dosage_Form) {
        Dosage_Form = dosage_Form;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getName_of_Drug() {
        return Name_of_Drug;
    }

    public void setName_of_Drug(String name_of_Drug) {
        Name_of_Drug = name_of_Drug;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public Date getComplain_Date() {
        return Complain_Date;
    }

    public void setComplain_Date(Date complain_Date) {
        Complain_Date = complain_Date;
    }



}
