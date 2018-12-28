package theseinitiatives.atma.client.model;

public class IdentitasModel {

    public IdentitasModel()
    {

    }
    private String id;
    String nama  ;
    String pasangan ;
    String status1 ;
    String Dusuns ;
    String resiko;
    boolean haveBirth;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPasangan() {
        return pasangan;
    }

    public void setPasangan(String pasangan) {
        this.pasangan = pasangan;
    }

    public String getStatus1() {
        return status1;
    }

    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    public String getDusuns() {
        return Dusuns;
    }

    public void setDusuns(String dusuns) {
        Dusuns = dusuns;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    public void setResiko(String resiko){this.resiko=resiko;}
    public String getResiko(){return this.resiko;}
    public void setHaveBirth(boolean cond){haveBirth=cond;}
    public boolean getHaveBirth(){return haveBirth;}
}
