package com.sbs.sbsgroup7.service;

public class OTP {
    public static String generatePIN()
    {
        //generate a 6 digit integer 1000 <10000
        Integer randomPIN = (int)(Math.random()*98765)+100000;

        //Store integer in a string
        String pin=randomPIN.toString();
        return pin;
    }
}
