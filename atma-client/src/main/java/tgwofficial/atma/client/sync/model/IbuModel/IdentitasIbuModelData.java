package tgwofficial.atma.client.sync.model.IbuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IdentitasIbuModelData {



    private String _id;
    private String name;
    private String spousename;
    private String tgl_lahir;
    private String dusun;
    private String hpht;
    private String htp;
    private String gol_darah;
    private String status;
    private String kader;
    private String telp;
    private String tgl_persalinan;
    private String kondisi_ibu;
    private String kondisi_anak;
    private String is_send;
    private String is_sync;
    private String timestamp;

    @Override
    public String toString(){
        return "_id : " + _id + "\nname : " + name + "\nspousename : " + spousename+ "\ntgl_lahir : " + tgl_lahir
                + "\ndusun : " + dusun
                + "\nhpht : " + hpht
                + "\nhtp : " + htp
                + "\nstatus : " + status
                + "\nstatus : " + status
                + "\nis_send : " + is_send
                + "\nis_sync : " + is_sync
                + "\ntimestamp : " + tgl_lahir;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
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

    public String getGol_darah() {
        return gol_darah;
    }

    public void setGol_darah(String gol_darah) {
        this.gol_darah = gol_darah;
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

    public String getIs_send() {
        return is_send;
    }

    public void setIs_send(String is_send) {
        this.is_send = is_send;
    }

    public String getIs_sync() {
        return is_sync;
    }

    public void setIs_sync(String is_sync) {
        this.is_sync = is_sync;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
