package tgwofficial.atma.client.model.syncmodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IbuData {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("spousename")
    @Expose
    private String spousename;
    @SerializedName("tgl_lahir")
    @Expose
    private String tglLahir;
    @SerializedName("dusun")
    @Expose
    private String dusun;
    @SerializedName("hpht")
    @Expose
    private String hpht;
    @SerializedName("htp")
    @Expose
    private String htp;
    @SerializedName("gol_darah")
    @Expose
    private String golDarah;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("kader")
    @Expose
    private String kader;
    @SerializedName("telp")
    @Expose
    private String telp;
    @SerializedName("tgl_persalinan")
    @Expose
    private String tglPersalinan;
    @SerializedName("kondisi_ibu")
    @Expose
    private String kondisiIbu;
    @SerializedName("kondisi_anak")
    @Expose
    private String kondisiAnak;
    @SerializedName("is_send")
    @Expose
    private String isSend;
    @SerializedName("is_sync")
    @Expose
    private String isSync;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpousename() {
        return spousename;
    }

    public void setSpousename(String spousename) {
        this.spousename = spousename;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getHpht() {
        return hpht;
    }

    public void setHpht(String hpht) {
        this.hpht = hpht;
    }

    public String getHtp() {
        return htp;
    }

    public void setHtp(String htp) {
        this.htp = htp;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKader() {
        return kader;
    }

    public void setKader(String kader) {
        this.kader = kader;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getTglPersalinan() {
        return tglPersalinan;
    }

    public void setTglPersalinan(String tglPersalinan) {
        this.tglPersalinan = tglPersalinan;
    }

    public String getKondisiIbu() {
        return kondisiIbu;
    }

    public void setKondisiIbu(String kondisiIbu) {
        this.kondisiIbu = kondisiIbu;
    }

    public String getKondisiAnak() {
        return kondisiAnak;
    }

    public void setKondisiAnak(String kondisiAnak) {
        this.kondisiAnak = kondisiAnak;
    }

    public String getIsSend() {
        return isSend;
    }

    public void setIsSend(String isSend) {
        this.isSend = isSend;
    }

    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}

