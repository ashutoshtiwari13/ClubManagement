package com.ruuha.bmscms;

/**
 * Created by ruuha on 9/28/2017.
 */

public class ContactRegister {

    int id;
    String name,branch,email,password,mobile;

    public void setId(int id){

        this.id=id;
    }
    public int getId(){

        return this.id;
    }

    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }

    public void setBranch(String branch){
        this.branch=branch;
    }
    public String getBranch(){
        return this.branch;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPass(String password){
        this.password=password;
    }
    public String getPass(){
        return this.password;
    }

    public void setMobile(String mobile){
        this.mobile=mobile;
    }
    public String getMobile(){
        return this.mobile;
    }


}
