package com.example.spalla.lifestylebuddy;

import android.provider.ContactsContract;

/**
 * Created by Spalla on 29/01/2017.
 */
public class Datalogin {

    private String idPerson;
    private String lastname;
    private String name;
    private String email;
    private String picture_url;

    private String height;
    private String weight;
    private String wtime;
    private String hrate;
    private String steps;
    private String sleep;

    private String weightg;
    private String wtimeg;
    private String hrateg;
    private String stepsg;
    private String sleepg;

    private static Datalogin instance;
    public static Datalogin getInstance(){
        if(instance == null){
            instance = new Datalogin();
        }
        return instance;
    }
    public static Datalogin setnull(){
        instance = null;
        return instance;
    }

    public String getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(String idPerson) {
        this.idPerson = idPerson;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWtime() {
        return wtime;
    }

    public void setWtime(String wtime) {
        this.wtime = wtime;
    }

    public String getHrate() {
        return hrate;
    }

    public void setHrate(String hrate) {
        this.hrate = hrate;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getWeightg() {
        return weightg;
    }

    public void setWeightg(String weightg) {
        this.weightg = weightg;
    }

    public String getWtimeg() {
        return wtimeg;
    }

    public void setWtimeg(String wtimeg) {
        this.wtimeg = wtimeg;
    }

    public String getHrateg() {
        return hrateg;
    }

    public void setHrateg(String hrateg) {
        this.hrateg = hrateg;
    }

    public String getStepsg() {
        return stepsg;
    }

    public void setStepsg(String stepsg) {
        this.stepsg = stepsg;
    }

    public String getSleepg() {
        return sleepg;
    }

    public void setSleepg(String sleepg) {
        this.sleepg = sleepg;
    }

    public Datalogin(){

    }
    public Datalogin(String idPerson, String lastname, String name, String email, String picture_url,
                     String height, String weight, String wtime, String hrate, String steps, String sleep,
                     String weightg, String wtimeg, String hrateg, String stepsg, String sleepg) {

        this.idPerson = idPerson;
        this.lastname = lastname;
        this.name = name;
        this.email = email;
        this.picture_url = picture_url;
        this.height = height;
        this.weight = weight;
        this.wtime = wtime;
        this.hrate = hrate;
        this.steps = steps;
        this.sleep = sleep;
        this.weightg = weightg;
        this.wtimeg = wtimeg;
        this.hrateg = hrateg;
        this.stepsg = stepsg;
        this.sleepg = sleepg;
    }



}
