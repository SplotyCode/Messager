package me.david.messagecore.utils;

public final class AccountUtil {

    private static String charpatter = "/^([a-zA-Z0-9][\\s_.-]?)+$/";

    public static ValidRespone isValidName(String name){
        if(name.trim().length() != name.length())return ValidRespone.WHITESPLACE;
        if(name.length() < 5) return ValidRespone.SMALL;
        if(name.length() > 25) return ValidRespone.LONG;
        if(name.matches(charpatter)) return ValidRespone.CHARATERS;
        return ValidRespone.OKAY;
    }

    public enum ValidRespone {
        OKAY,
        WHITESPLACE,
        SMALL,
        LONG,
        CHARATERS;
    }

    public static ValidRespone isValidMail(String mail){
        if(mail.trim().length() != mail.length())return ValidRespone.WHITESPLACE;
        if(mail.length() > 64) return ValidRespone.LONG;
        if(mail.matches("/^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$/")) return ValidRespone.CHARATERS;
        return ValidRespone.OKAY;
    }

    public static ValidRespone isValidPassword(String password){
        if(password.trim().length() != password.length())return ValidRespone.WHITESPLACE;
        if(password.length() < 8) return ValidRespone.SMALL;
        if(password.length() > 32) return ValidRespone.LONG;
        if(password.matches(charpatter)) return ValidRespone.CHARATERS;
        return ValidRespone.OKAY;
    }
}
