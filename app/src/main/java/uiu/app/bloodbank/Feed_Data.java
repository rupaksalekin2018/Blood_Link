package uiu.app.bloodbank;

public class Feed_Data {
    String b_group,gender,amount_blood,date,time,add_hospital,msg,user,id;

    public Feed_Data(String id,String b_group, String gender, String amount_blood, String date, String time, String add_hospital, String msg, String user) {
        this.id = id;
        this.b_group = b_group;
        this.gender = gender;
        this.amount_blood = amount_blood;
        this.date = date;
        this.time = time;
        this.add_hospital = add_hospital;
        this.msg = msg;
        this.user = user;
    }
}
