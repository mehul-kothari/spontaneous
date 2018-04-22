package CreateEventPack;

import java.util.Date;

/**
 * Created by mehulkothari on 3/20/2017.
 */
public class CreateEventObject {

    String name, college_name, address, about_the_event, type, footfall;
    int likes;

    Date event_date;
    public CreateEventObject(){

    }

    public CreateEventObject(String s1, String s2, String s3, String s4, String s5, String s6, Date d1,int likes) {
        this.name = s1;
        this.college_name = s2;
        this.address = s3;
        this.about_the_event = s4;
        this.type = s5;
        this.footfall = s6;
        this.event_date=d1;
        this.likes=likes;


    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAbout_the_event() {
        return about_the_event;
    }

    public void setAbout_the_event(String about_the_event) {
        this.about_the_event = about_the_event;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFootfall() {
        return footfall;
    }

    public void setFootfall(String footfall) {
        this.footfall = footfall;
    }

    public Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }
}

