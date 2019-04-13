package theseinitiatives.atma.client.model.syncmodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IbuData {

    @SerializedName("_id")
    @Expose
    private String id;

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    @SerializedName("unique_id")
    @Expose
    private String unique_id;

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
    @SerializedName("posyandu")
    @Expose
    private String posyandu;
    @SerializedName("gubug")
    @Expose
    private String gubug;



    @SerializedName("hpht")
    @Expose
    private String hpht;

    public String getResiko() {
        return resiko;
    }

    public void setResiko(String resiko) {
        this.resiko = resiko;
    }

    public String getResiko_lainnya() {
        return resiko_lainnya;
    }

    public void setResiko_lainnya(String resiko_lainnya) {
        this.resiko_lainnya = resiko_lainnya;
    }

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
    @SerializedName("nifas_selesai")
    @Expose
    private String nifas_selesai;
    @SerializedName("alasan")
    @Expose
    private String alasan;
    @SerializedName("resiko")
    @Expose
    private String resiko;
    @SerializedName("resiko_lainnya")
    @Expose
    private String resiko_lainnya;
    @SerializedName("kondisi_anak")
    @Expose
    private String kondisiAnak;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("location_id")
    @Expose
    private String location_id;
    @SerializedName("update_id")
    @Expose
    private String update_id;
    @SerializedName("is_send")
    @Expose
    private String isSend;
    @SerializedName("is_sync")
    @Expose
    private String isSync;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;


    public String getGubug() {
        return gubug;
    }

    public void setGubug(String gubug) {
        this.gubug = gubug;
    }
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

    public String getNifas_selesai() {
        return nifas_selesai;
    }

    public void setNifas_selesai(String nifas_selesai) {
        this.nifas_selesai = nifas_selesai;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public String getPosyandu() {
        return posyandu;
    }

    public void setPosyandu(String posyandu) {
        this.posyandu = posyandu;
    }
}

