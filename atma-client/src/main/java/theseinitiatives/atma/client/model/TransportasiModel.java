package theseinitiatives.atma.client.model;

public class TransportasiModel {

    public TransportasiModel()
    {

    }
    private String id;
    String nama  ;

    String Dusuns ;

    public void setId(String id){
        this.id=id;
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

    public String getDusuns() {
        return Dusuns;
    }

    public void setDusuns(String dusuns) {
        Dusuns = dusuns;
    }

    public String getKendaraan() {
        return kendaraan;
    }

    public void setKendaraan(String kendaraan) {
        this.kendaraan = kendaraan;
    }

    String kendaraan ;


}
