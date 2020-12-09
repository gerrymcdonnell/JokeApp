package com.example.jokeapp.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MyRandomNumber {
    private List<Integer> randomNumber;
    private int endRange;
    int iIndex=-1;
    boolean bUseShuffle=true;
    
    
    public MyRandomNumber(int n,boolean bUseShuffle){
        this.endRange=n;
        this.bUseShuffle= bUseShuffle;
        init();
    }
    
    
    public MyRandomNumber(int n){
        this.endRange=n;
        init();
    }
    
    
    public int getEndRange()
    {
        return endRange;
    }
    
    
    public void setEndRange(int n)
    {
        this.endRange=n;
        init();
    }
    
    
    private void init(){
        iIndex=-1;
        
        randomNumber = new ArrayList<Integer>(endRange);
        
        for (int i = 0; i<= endRange; i++) {                
            randomNumber.add(i);
        }
        if( bUseShuffle==true)
        	Collections.shuffle(randomNumber);
    }
    
    public void reShuffle(){
    	if( bUseShuffle==true)
    		Collections.shuffle(randomNumber);
    }
    
    public int getNum(){
        int n;
        iIndex++;   
        
        n= randomNumber.get(iIndex);
        
        if(iIndex==endRange){
            init();            
        }
        return n;
    }
    
    
    public int goBack(){
        if(iIndex-1>-1){
            iIndex--;
        }
        return randomNumber.get(iIndex);
    }
    
    public int goForward(){
        if(iIndex+1<=endRange){
            iIndex++;
        }
        return randomNumber.get(iIndex);
    }
    
    
    public int getCurrentNum()
    {
    	return randomNumber.get(iIndex);
    }
    
    
}
