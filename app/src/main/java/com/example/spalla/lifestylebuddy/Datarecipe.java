package com.example.spalla.lifestylebuddy;

/**
 * Created by Spalla on 29/01/2017.
 */
public class Datarecipe {

    private String imagel;
    private String labell;
    private String urll;
    private String imaged;
    private String labeld;
    private String urld;

    public String getImagel() {
        return imagel;
    }

    public void setImagel(String imagel) {
        this.imagel = imagel;
    }

    public String getLabell() {
        return labell;
    }

    public void setLabell(String labell) {
        this.labell = labell;
    }

    public String getUrll() {
        return urll;
    }

    public void setUrll(String urll) {
        this.urll = urll;
    }

    public String getImaged() {
        return imaged;
    }

    public void setImaged(String imaged) {
        this.imaged = imaged;
    }

    public String getLabeld() {
        return labeld;
    }

    public void setLabeld(String labeld) {
        this.labeld = labeld;
    }

    public String getUrld() {
        return urld;
    }

    public void setUrld(String urld) {
        this.urld = urld;
    }

    private static Datarecipe instance;
    public static Datarecipe getInstance(){
        if(instance == null){
            instance = new Datarecipe();
        }
        return instance;
    }
    public static Datarecipe setnull(){
        instance = null;
        return instance;
    }

    public Datarecipe(String imagel, String labell, String urll, String imaged, String labeld, String urld) {

        this.imagel = imagel;
        this.labell = labell;
        this.urll = urll;

    }

    public Datarecipe(){

    }
}
