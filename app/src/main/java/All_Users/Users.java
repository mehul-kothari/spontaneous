package All_Users;

/**
 * Created by mehulkothari on 3/8/2017.
 */
public class Users {
    String name, email, about,company_name,comp_addr,profession,description;

    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String name,String email, String profession, String description)
    {
        this.name=name;
        this.email=email;
        this.profession=profession;
        this.description=description;
    }
    public Users(String name,String email, String about,String company_name,String comp_addr)
    {
        this.name=name;
        this.email=email;
        this.about=about;
        this.company_name=company_name;
        this.comp_addr=comp_addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getComp_addr() {
        return comp_addr;
    }

    public void setComp_addr(String comp_addr) {
        this.comp_addr = comp_addr;
    }
}
