package theseinitiatives.atma.client.model.syncmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankdarahData {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("dusun")
    @Expose
    private String dusun;
    @SerializedName("name_pendonor")
    @Expose
    private String name_pendonor;
    @SerializedName("kapasitas_kendaraan")
    @Expose
    private String kapasitas_kendaraan;
    @SerializedName("jenis_kendaraan")
    @Expose
    private String jenis_kendaraan;
    @SerializedName("gubug")
    @Expose
    private String gubug;
    @SerializedName("profesi")
    @Expose
    private String profesi;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;
    @SerializedName("gol_darah")
    @Expose
    private String golDarah;
    @SerializedName("telp")
    @Expose
    private String telp;

    public String getTgl_donor() {
        return tgl_donor;
    }

    public void setTgl_donor(String tgl_donor) {
        this.tgl_donor = tgl_donor;
    }

    @SerializedName("tgl_donor")
    @Expose
    private String tgl_donor;
    @SerializedName("tutup_data")
    @Expose
    private String tutupStatus;
    @SerializedName("alasan")
    @Expose
    private String alasan;



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

    public String getUniqueId() {
        return unique_id;
    }

    public void setUniqueId(String unique_id) {
        this.unique_id = unique_id;
    }

    @SerializedName("unique_id")
    @Expose
    private String unique_id;

    public void setId(String id) {
        this.id = id;
    }
    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getGolDarah() {
        return golDarah;
    }

    public void setGolDarah(String golDarah) {
        this.golDarah = golDarah;
    }
    public String getDusun() {
        return dusun;
    }

    public void setDusun(String dusun) {
        this.dusun = dusun;
    }

    public String getName_pendonor() {
        return name_pendonor;
    }

    public void setName_pendonor(String name_pendonor) {
        this.name_pendonor = name_pendonor;
    }

    public String getKapasitas_kendaraan() {
        return kapasitas_kendaraan;
    }

    public void setKapasitas_kendaraan(String kapasitas_kendaraan) {
        this.kapasitas_kendaraan = kapasitas_kendaraan;
    }

    public String getJenis_kendaraan() {
        return jenis_kendaraan;
    }

    public void setJenis_kendaraan(String jenis_kendaraan) {
        this.jenis_kendaraan = jenis_kendaraan;
    }

    public String getGubug() {
        return gubug;
    }

    public void setGubug(String gubug) {
        this.gubug = gubug;
    }

    public String getProfesi() {
        return profesi;
    }

    public void setProfesi(String profesi) {
        this.profesi = profesi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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

    public String getTutupStatus() {
        return tutupStatus;
    }

    public void setTutupStatus(String tutupStatus) {
        this.tutupStatus = tutupStatus;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }
}
