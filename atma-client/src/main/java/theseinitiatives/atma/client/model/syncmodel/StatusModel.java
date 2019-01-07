package theseinitiatives.atma.client.model.syncmodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("id_ibu")
    @Expose
    private String id_ibu;

    public String getTempat_lainnya() {
        return tempat_lainnya;
    }

    public void setTempat_lainnya(String tempat_lainnya) {
        this.tempat_lainnya = tempat_lainnya;
    }

    @SerializedName("tempat_lainnya")
    @Expose
    private String tempat_lainnya;

    @SerializedName("status_bersalin")
    @Expose
    private String status_bersalin;
    @SerializedName("tgl_persalinan")
    @Expose
    private String tgl_persalinan;
    @SerializedName("kondisi_ibu")
    @Expose
    private String kondisi_ibu;
    @SerializedName("kondisi_anak")
    @Expose
    private String kondisi_anak;
    @SerializedName("jumlah_bayi")
    @Expose
    private String jumlah_bayi;
    @SerializedName("jenis_kelamin")
    @Expose
    private String jenis_kelamin;
    @SerializedName("komplikasi_ibu")
    @Expose
    private String komplikasi_ibu;
    @SerializedName("komplikasi_anak")
    @Expose
    private String komplikasi_anak;
    @SerializedName("tempat_persalinan")
    @Expose
    private String tempat_persalinan;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_ibu() {
        return id_ibu;
    }

    public void setId_ibu(String id_ibu) {
        this.id_ibu = id_ibu;
    }

    public String getStatus_bersalin() {
        return status_bersalin;
    }

    public void setStatus_bersalin(String status_bersalin) {
        this.status_bersalin = status_bersalin;
    }

    public String getTgl_persalinan() {
        return tgl_persalinan;
    }

    public void setTgl_persalinan(String tgl_persalinan) {
        this.tgl_persalinan = tgl_persalinan;
    }

    public String getKondisi_ibu() {
        return kondisi_ibu;
    }

    public void setKondisi_ibu(String kondisi_ibu) {
        this.kondisi_ibu = kondisi_ibu;
    }

    public String getKondisi_anak() {
        return kondisi_anak;
    }

    public void setKondisi_anak(String kondisi_anak) {
        this.kondisi_anak = kondisi_anak;
    }

    public String getJumlah_bayi() {
        return jumlah_bayi;
    }

    public void setJumlah_bayi(String jumlah_bayi) {
        this.jumlah_bayi = jumlah_bayi;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getKomplikasi_ibu() {
        return komplikasi_ibu;
    }

    public void setKomplikasi_ibu(String komplikasi_ibu) {
        this.komplikasi_ibu = komplikasi_ibu;
    }

    public String getKomplikasi_anak() {
        return komplikasi_anak;
    }

    public void setKomplikasi_anak(String komplikasi_anak) {
        this.komplikasi_anak = komplikasi_anak;
    }

    public String getTempat_persalinan() {
        return tempat_persalinan;
    }

    public void setTempat_persalinan(String tempat_persalinan) {
        this.tempat_persalinan = tempat_persalinan;
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
