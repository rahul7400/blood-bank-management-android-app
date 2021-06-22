package in.macrocodes.bloodbankmanage;

public class HospitalModel {
    String name,email,phone,hospitalref,type,uid,city;

    public HospitalModel(String name, String email, String phone, String hospitalref, String type, String uid, String city) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hospitalref = hospitalref;
        this.type = type;
        this.uid = uid;
        this.city = city;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHospitalref() {
        return hospitalref;
    }

    public void setHospitalref(String hospitalref) {
        this.hospitalref = hospitalref;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public HospitalModel(){

    }
}
