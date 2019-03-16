package com.aswdc_cipherdicoder.bean;

public class Bean_History {
    public int H_id;
    public String Plain_Text;
    public String Cipher_Method;
    public String Cipher_Result;

    public int getH_id() {
        return H_id;
    }

    public void setH_id(int h_id) {
        H_id = h_id;
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
