package com.example.jokeapp.business;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gerry
 */
public class Joke {
    private String sJoke="";
    private String sPunchline="";
    private int iCategoryID;

    public Joke() {
    }

    public Joke(String sJoke) {
        this.sJoke=sJoke;
    }
    
    public Joke(String sJoke,String sPunchLine) {
        this.sJoke=sJoke;
        this.sPunchline=sPunchLine;
    }
    
    public Joke(String sJoke,String sPunchLine,int iCatId) {
        this.sJoke=sJoke;
        this.sPunchline=sPunchLine;
        this.iCategoryID=iCatId;
    }
    

    public int getiCategoryID() {
        return iCategoryID;
    }

    public void setiCategoryID(int iCategoryID) {
        this.iCategoryID = iCategoryID;
    }
        
    public String getsJoke() {
        return sJoke;
    }

    public void setsJoke(String sJoke) {
        this.sJoke = sJoke;
    }

    public String getsPunchline() {
        return sPunchline;
    }

    public void setsPunchline(String sPunchline) {
        this.sPunchline = sPunchline;
    }
    
    public boolean bHasPunchLine()
    {
        if(sPunchline.equals(""))
            return false;
        else
            return true;
    }
}
