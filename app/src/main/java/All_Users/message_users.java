package All_Users;

import com.google.firebase.auth.FirebaseUser;

/**
 * Created by mehulkothari on 4/2/2017.
 */
public class message_users {
    String text;

    String user;
    public message_users(){

    }

    public message_users(String text, String user) {
        this.text=text;
        this.user=user;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
