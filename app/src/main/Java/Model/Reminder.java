package Model;

import java.util.Random;


public class Reminder {
    /* fields */
    private int ID;
    private String courseName;
    private long dueDate;
    //private boolean alarm;
    private String nameAssignment;
    private String phoneNum;
    //private Date alarmDateTime;

    /*Constructors*/
    public Reminder(){
        Random rand = new Random();
        ID = rand.nextInt(999) + 1;
    }

    public Reminder(int id, String courseName,String nameAssignment,String phoneNum,long dueDate){
        ID=id;
        this.courseName = courseName;
        this.nameAssignment=nameAssignment;
        this.phoneNum=phoneNum;
        this.dueDate = dueDate;
    }

    /* Getters */
    public int getID(){return ID;}

    public long getdueDate(){
        return dueDate;
    }

    //public boolean getAlarm(){
       // return alarm;
    //}

    public String getCourseName() {
        return courseName;
    }

    public String getNameAssignment(){return nameAssignment;}

    public String getPhoneNum(){return phoneNum;}

    //public Date getAlarm_DateTime(){return alarmDateTime;}

    /* Setters */
    public void setID(int id){ID=id;}
    public void setCourseName(String courseName1){courseName=courseName1;}
    public void setDueDate(long dueDate1){dueDate=dueDate1;}
    //public void setAlarm(boolean alarm1){alarm=alarm1;}
    public void setNameAssignment(String nameAssignment1){nameAssignment=nameAssignment1;}
    public void setPhoneNum(String p){phoneNum=p;}
    //public void setAlarm_DateTime(Date d){alarmDateTime=d;}
}
