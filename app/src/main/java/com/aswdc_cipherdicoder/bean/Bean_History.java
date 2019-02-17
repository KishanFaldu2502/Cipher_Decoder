package com.aswdc_cipherdicoder.bean;

public class Bean_History {
    private int H_Id;
    private String Plain_Text;
    private String Cipher_Method;
    private String Cipher_Result;

    public int getH_Id() {
        return H_Id;
    }

    public void setH_Id(int h_Id) {
        H_Id = h_Id;
    }

    public String getPlain_Text() {
        return Plain_Text;
    }

    public void setPlain_Text(String plain_Text) {
        Plain_Text = plain_Text;
    }

    public String getCipher_Method() {
        return Cipher_Method;
    }

    public void setCipher_Method(String cipher_Method) {
        Cipher_Method = cipher_Method;
    }

    public String getCipher_Result() {
        return Cipher_Result;
    }

    public void setCipher_Result(String cipher_Result) {
        Cipher_Result = cipher_Result;
    }
}