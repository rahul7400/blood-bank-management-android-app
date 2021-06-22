package in.macrocodes.bloodbankmanage;

public class UserModel {
    String name,email,city,adharcard,bloodgroup,phone,type,uid,donator;

    public UserModel(){

    }
    public UserModel(String name, String email, String city, String adharcard, String bloodgroup, String phone, String type, String uid, String donator) {
        this.name = name;
        this.email = email;
        this.city = city;
        this.adharcard = adharcard;
        this.bloodgroup = bloodgroup;
        this.phone = phone;
        this.type = type;
        this.uid = uid;
        this.donator = donator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAdharcard() {
        return adharcard;
    }

    public void setAdharcard(String adharcard) {
        this.adharcard = adharcard;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDonator() {
        return donator;
    }

    public void setDonator(String donator) {
        this.donator = donator;
    }
}
