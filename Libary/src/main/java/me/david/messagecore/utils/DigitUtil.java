package me.david.messagecore.utils;

import java.util.Random;

/*
 * Generats a Digit such as C234-423a-Hda3-J23J
 * Depening on the groups and digits
 */
public class DigitUtil {

    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private int groups;
    private int digits;
    private String pin;
    private Random random = new Random();
    private StringBuilder stringBuilder = new StringBuilder();

    public DigitUtil(){
        this.groups = 6;
        this.digits = 4;
        this.pin = generate();
    }

    public DigitUtil groups(int groups){
        this.groups = groups;
        return this;
    }

    public DigitUtil digits(int digits){
        this.digits = digits;
        return this;
    }

    public final String build(){
        this.pin = generate();
        return this.pin;
    }

    // Generate pin
    private String generate(){
        stringBuilder.delete(0, stringBuilder.length());
        for(int i = 0; i < this.groups * this.digits; i++){
            stringBuilder.append(CHARS.charAt( random.nextInt(CHARS.length())));
            if((i+1) % this.digits == 0 && (i != (this.groups * this.digits)-1))
                stringBuilder.append("-");
        }
        return stringBuilder.toString();
    }

    public static DigitUtil getInstance(){
        return new DigitUtil();
    }
}
