package net.yorksolutions.apiexercise;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Scanner;


public class Md5Information {

    public String original;
    public String md5;


    public Md5Information(String original) throws NoSuchAlgorithmException {
        this.original = original;
        this.md5 = getMd5();
    }

    private String getMd5() throws NoSuchAlgorithmException {
        var msg = original.getBytes();
        byte[] hash = null;

        var md = MessageDigest.getInstance("MD5");
        hash = md.digest(msg);

        var strBuilder = new StringBuilder();
        for (byte b : hash) {
            strBuilder.append(String.format("%02x", b));
        }
        var strHash = strBuilder.toString();
        return strHash;
    }

}
