package tgwofficial.atma.client.sync.model.IbuModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class IdentitasIbuModel {


    public List <IdentitasIbuModelData> identitasIbuModelData;



    private String update_id;

        private String form_name;

        private String location_id;

        private String user_id;

        @Override
        public String toString(){
        return "update_id : " + update_id + "\nform_name : " + form_name + "\nlocation_id : " + location_id+ "\nuser_id : " + user_id
            ;
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
    public List<IdentitasIbuModelData> getIdentitasIbuModelData() {
        return identitasIbuModelData;
    }

    public void setIdentitasIbuModelData(List<IdentitasIbuModelData> identitasIbuModelData) {
        this.identitasIbuModelData = identitasIbuModelData;
    }




}

