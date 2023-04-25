package uiu.app.bloodbank;

import java.io.Serializable;

public class Ambulance_data implements Serializable {

    String amb_name,amb_phone,amb_addr,amb_pos,amb_dis;

    public Ambulance_data(String amb_name, String amb_phone, String amb_addr, String amb_pos, String amb_dis) {
        this.amb_name = amb_name;
        this.amb_phone = amb_phone;
        this.amb_addr = amb_addr;
        this.amb_pos = amb_pos;
        this.amb_dis = amb_dis;
    }
}
