package theseinitiatives.atma.client.model;

public class KaderViewModel {

    public KaderViewModel()
    {

    }
    private String id;
    String nama  ;

    String Dusuns ;
    String username ;
    String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDusuns() {
        return Dusuns;
    }

    public void setDusuns(String dusuns) {
        Dusuns = dusuns;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
