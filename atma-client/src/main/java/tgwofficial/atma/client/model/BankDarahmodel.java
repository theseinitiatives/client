package tgwofficial.atma.client.model;

public class BankDarahmodel {
    private String id;
    String nama;
    String golds;
    String nomor ;
    String pendonor ;

    public BankDarahmodel()
    {

    }

    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getGolds() {
        return golds;
    }

    public void setGolds(String golds) {
        this.golds = golds;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getPendonor() {
        return pendonor;
    }

    public void setPendonor(String pendonor) {
        this.pendonor = pendonor;
    }
}
