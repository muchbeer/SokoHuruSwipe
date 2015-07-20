package muchbeer.king.sokohuruswipe.connectdata;

/**
 * Created by muchbeer on 6/25/2015.
 */


//this class enable to maintain single contact as an object using getter and setter
public class Contacts {

    //private variables
    int uid;
    String name;
    String price;
    String image;
    String contact;
    String place;
    String desc;

    //Empty Constructor
    public Contacts() {

    }

    //constructor
    public Contacts(int uid, String name, String price, String image, String contact,
                    String place, String desc) {
        this.uid = uid;
        this.name=name;
        this.price=price;
        this.image = image;
        this.contact=contact;
        this.place = place;
        this.desc = desc;
    }

    //constructor
    public Contacts(String name, String price, String image, String contact,
                    String place, String desc ) {
        this.name=name;
        this.price=price;
        this.image=image;
        this.contact=contact;
        this.place=place;
        this.desc = desc;

    }

    //getting ID
    public int getUid() {
        return  this.uid;
    }

    //setting id
    public void setUid(int uid) {
        this.uid = uid;
    }

    //getting name
    public String getName() {
        return this.name;
    }

    //setting name
    public void setName(String name) {
        this.name=name;
    }

    //getting price
    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price=price;
    }

    //getting name
    public String getImage() {

        return this.image;
    }

    //setting name
    public void setImage(String image) {
        this.image=image;
    }

    //getting name
    public String getContact() {
        return this.contact;
    }

    //setting name
    public void setContact(String contact) {
        this.contact=contact;
    }

    //getting name
    public String getPlace() {
        return this.place;
    }

    //setting name
    public void setPlace(String place) {
        this.place=place;
    }

    //getting name
    public String getDesc() {
        return this.desc;
    }

    //setting name
    public void setDesc(String desc) {
        this.desc=desc;
    }
}

