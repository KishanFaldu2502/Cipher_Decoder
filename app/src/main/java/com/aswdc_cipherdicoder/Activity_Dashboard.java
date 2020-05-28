package com.aswdc_cipherdicoder;

import  android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aswdc_cipherdicoder.design.Activity_Developer;

import java.util.ArrayList;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class Activity_Dashboard extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    EditText etPlainText,etcaesarkey,etaffinekey1,etaffinekey2;
    TextView tvCipherText;
    Spinner spCipherMethod;
    Button btncaesar,btnaffine;
    ImageView imgcopy,imgpaste,imgcancel,imgcopy1;
    Dialog dialog;
    String[] Cipher;
    int spposition;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private ClipboardManager myClipboard;
    private ClipData myClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String[] mPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission[0])
                    != MockPackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(this, mPermission[1])
                            != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this,
                        mPermission, REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        init();

        tvCipherText.setMovementMethod(new ScrollingMovementMethod());
        Cipher = getResources().getStringArray(R.array.dashboard_spinner);
        ArrayAdapter<String> cipheradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Cipher);
        cipheradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCipherMethod.setAdapter(cipheradapter);

        imgcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String Text = etPlainText.getText().toString();
                myClip = ClipData.newPlainText("text", Text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });
        imgpaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager myclipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String pasteData = "";
                if (!(myClipboard.hasPrimaryClip()) || !(myClipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))){
                    imgpaste.setEnabled(false);
                }
                else {
                    imgpaste.setEnabled(true);
                }
                ClipData.Item item=myclipboard.getPrimaryClip().getItemAt(0);
                pasteData=item.getText().toString();
                etPlainText.setText(etPlainText.getText().toString()+pasteData);
            }
        });
        imgcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPlainText.setText("");
            }
        });
        imgcopy1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String Text = tvCipherText.getText().toString();
                myClip = ClipData.newPlainText("text", Text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

        spCipherMethod.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etPlainText.getWindowToken(), 0);
                return false;
            }
        });

        spCipherMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spposition = position;
                position();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etPlainText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPlainText.getText().toString().isEmpty()) {
                    tvCipherText.setText("");
                    spCipherMethod.setSelection(0);
                }else{
                    position();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_share, menu);
        menuInflater.inflate(R.menu.menu_developer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT,
                    "Cipher Decoder\n\nPlain Text :- " + etPlainText.getText().toString() + "\n\n"
                            + "Cipher Method :- " + spCipherMethod.getSelectedItem() + "\n\n"
                    + "Cipher Result :- " + tvCipherText.getText().toString());
            startActivity(intent);
        }
        if (item.getItemId() == R.id.menu_developer) {
            Intent intent = new Intent(Activity_Dashboard.this, Activity_Developer.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        etPlainText = findViewById(R.id.dashboard_et_plaintext);
        tvCipherText = findViewById(R.id.dashboard_tv_ciphertext);
        spCipherMethod = findViewById(R.id.dashboard_sp_ciphermethod);
        imgcopy = findViewById(R.id.dashboard_et_copy);
        imgpaste= findViewById(R.id.dashboard_et_paste);
        imgcancel= findViewById(R.id.dashboard_et_cancel);
        imgcopy1 = findViewById(R.id.dashboard_tv_copy);
        dialog=new Dialog(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    etPlainText.setText(result.get(0));
                }
                break;
            }
        }
    }
    public void position(){
        if (spposition == 1) {
            ascii();
        }
        if (spposition == 2) {
            binary();
        }
        if (spposition == 3) {
            caesar();
        }
        if (spposition == 4) {
            hexaDecimal();
        }
        if (spposition == 5) {
            rearrangeWordSentence();
        }
        if (spposition == 6) {
            rearrangeWord();
        }
        if (spposition == 7) {
            atbash();
        }
        if (spposition == 8) {
            affine();
        }
        if (spposition == 9) {
            LetterNumber();
        }
    }

    public void ascii() {
        StringBuilder sb = new StringBuilder();
        String plaintext = etPlainText.getText().toString();
        char[] ascii = plaintext.toCharArray();
        for (char c : ascii) {
            sb.append((int) c);
            sb.append(' ');
        }
        tvCipherText.setText(sb.toString());
    }

    public void binary() {
        String plaintext = etPlainText.getText().toString();
        byte[] bytes = plaintext.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            binary.append(' ');
            tvCipherText.setText(binary.toString());
        }
    }

    public void caesar() {
        dialog.setContentView(R.layout.dialog_caesar);
        etcaesarkey=dialog.findViewById(R.id.dialog_et);
        btncaesar=dialog.findViewById(R.id.dialog_btn);
        dialog.show();
        btncaesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                intent.putExtra("caesarkey",etcaesarkey.getText().toString());
                dialog.dismiss();
                final Intent caesarintent=getIntent();
                final String caesarkey=caesarintent.getStringExtra("caesarkey");

                final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
                String plaintext = etPlainText.getText().toString().toLowerCase();
                String shiftkey = caesarkey;
                if (TextUtils.isEmpty(shiftkey)) {
                    tvCipherText.setText(plaintext);
                    Toast.makeText(getApplicationContext(), "Key value is not defined", Toast.LENGTH_LONG).show();
                } else {
                    int shiftkeyval = Integer.parseInt(shiftkey);
                    StringBuilder ciphertext=new StringBuilder();
                    for (int i = 0; i < plaintext.length(); i++) {
                        int charposition = ALPHABET.indexOf(plaintext.charAt(i));
                        int keyval = (shiftkeyval + charposition) % 26;
                        char replaceval = ALPHABET.charAt(keyval);
                        ciphertext.append(replaceval);
                    }
                    tvCipherText.setText(ciphertext);
                }
            }
        });
    }

    public void hexaDecimal() {
        String plaintext = etPlainText.getText().toString();
        StringBuilder hexadecimal = new StringBuilder();
        byte[] bytes = plaintext.getBytes();
        for (byte b : bytes) {
            int intval = b & 0xff;
            if (intval < 0x10) {
                hexadecimal.append("0");
            }
            hexadecimal.append(Integer.toHexString(intval));
            hexadecimal.append(' ');
        }
        tvCipherText.setText(hexadecimal.toString());
    }

    public void rearrangeWordSentence() {
        String plaintext = etPlainText.getText().toString();
        StringBuffer rws = new StringBuffer(plaintext).reverse();
        tvCipherText.setText(rws);
    }

    public void rearrangeWord() {
        String plaintext = etPlainText.getText().toString();
        String[] words = plaintext.split(" ");
        String reverseString = "";

        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            StringBuilder reverseWord = new StringBuilder();
            for (int j = word.length() - 1; j >= 0; j--) {
                reverseWord.append(word.charAt(j));
            }

            reverseString = reverseString + reverseWord + " ";
        }
        tvCipherText.setText(reverseString);
    }

    public void atbash() {
        String plaintext = etPlainText.getText().toString();
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String TEBAHPLA = "ZYXWVUTSRQPONMLKJIHGFEDCBA";
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        String tebahpla = "zyxwvutsrqponmlkjihgfedcba";
        String decode_string = "";
        for (int i = 0; i < plaintext.length(); i++) {
            if (plaintext.charAt(i) == (char) 32) {
                decode_string += " ";
            } else {
                if (Character.isUpperCase(plaintext.charAt(i))) {
                    for (int j = 0; j < ALPHABET.length(); j++) {
                        if (plaintext.charAt(i) == ALPHABET.charAt(j)) {
                            decode_string += TEBAHPLA.charAt(j);
                            break;
                        }
                    }
                } else if (Character.isLowerCase(plaintext.charAt(i))) {
                    for (int j = 0; j < alphabet.length(); j++) {
                        if (plaintext.charAt(i) == alphabet.charAt(j)) {
                            decode_string += tebahpla.charAt(j);
                            break;
                        }
                    }
                }
            }
        }
        tvCipherText.setText(decode_string);
    }

    public void affine() {
        dialog.setContentView(R.layout.dialog_affine);
        etaffinekey1=dialog.findViewById(R.id.dialog_et1);
        etaffinekey2=dialog.findViewById(R.id.dialog_et2);
        btnaffine=dialog.findViewById(R.id.dialog_btn);
        dialog.show();
        btnaffine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=getIntent();
                intent.putExtra("affinekey1",etaffinekey1.getText().toString());
                intent.putExtra("affinekey2",etaffinekey2.getText().toString());
                dialog.dismiss();
                final Intent affineintent=getIntent();
                final String affinekey1=affineintent.getStringExtra("affinekey1");
                final String affinekey2=affineintent.getStringExtra("affinekey2");

                String plaintext = etPlainText.getText().toString();
                char[] msg = plaintext.toCharArray();
                String shiftkey1 = affinekey1;
                String shiftkey2 = affinekey2;
                String cipher = "";

                if (TextUtils.isEmpty(shiftkey1) || TextUtils.isEmpty(shiftkey2)) {
                    tvCipherText.setText(plaintext);
                    Toast.makeText(getApplicationContext(), "Key value is not defined", Toast.LENGTH_LONG).show();
                }
                else{
                    int number1 = Integer.parseInt(shiftkey1);
                    int number2 = Integer.parseInt(shiftkey2);
                    for (int i = 0; i < plaintext.length(); i++) {
                        if (msg[i] != ' ') {
                            cipher = cipher + (char) ((((number1 * (msg[i] - 'A') + number2) % 26) + 'A')) + " ";
                        }
                    }
                    tvCipherText.setText(cipher);
                }
            }
        });
    }

    public void LetterNumber() {
        String plaintext = etPlainText.getText().toString().toLowerCase();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int position;
        String ciphertext = " ";
        for (int i = 0; i < plaintext.length(); i++) {
            position = alphabet.indexOf(plaintext.charAt(i) + 1);
            ciphertext = ciphertext + position + " ";
        }
        tvCipherText.setText(String.valueOf(ciphertext));
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}