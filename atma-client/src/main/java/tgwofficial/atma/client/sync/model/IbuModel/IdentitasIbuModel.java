package tgwofficial.atma.client.sync.model.IbuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class IdentitasIbuModel {

    @SerializedName("update_id")
    @Expose
    private String update_id;
    @SerializedName("form_name")
    @Expose
    private String form_name;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("location_id")
    @Expose
    private String location_id;
    @SerializedName("user_id")
    @Expose
    private String user_id;


    @Override
    public String toString() {
        return "IdentitasIbuModel{" +
                ",\n update_id='" + update_id + '\'' +
                ",\n form_name='" + form_name + '\'' +
                ",\n location_id='" + location_id + '\'' +
                ", \nuser_id='" + user_id + '\'' +
               "\ndata=" + data +
                '}';
    }

    public String getupdate_id() {
            return update_id;
        }

        public void setupdate_id(String update_id) {
            this.update_id = update_id;
        }

        public String getform_name() {
            return form_name;
        }

        public void setform_name(String form_name) {
            this.form_name = form_name;
        }

        public String getlocation_id() {
            return location_id;
        }

        public void setlocation_id(String location_id) {
            this.location_id = location_id;
        }

        public String getuser_id() {
            return user_id;
        }

        public void setuser_id(String user_id) {
            this.user_id = user_id;
        }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }




}

