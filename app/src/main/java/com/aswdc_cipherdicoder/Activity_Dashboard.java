package com.aswdc_cipherdicoder;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aswdc_cipherdicoder.bean.Bean_History;
import com.aswdc_cipherdicoder.dbHelper.DBHelper_History;
import com.aswdc_cipherdicoder.design.Activity_Developer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class Activity_Dashboard extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    LinearLayout l1;
    EditText etPlainText, etshiftkey, etshiftkey1;
    TextInputLayout etshift, etshift1;
    TextView tvCipherText;
    Spinner spCipherMethod;
    Button btnconvert;
    ImageButton imgspeech, imgcopy,imgsave;
    String[] Cipher;
    int spposition;
    private static final int REQUEST_CODE_PERMISSION = 2;
    private ClipboardManager myClipboard;
    private ClipData myClip;
    int position;
    DBHelper_History dbh;
    Bean_History bh;

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
        Cipher = getResources().getStringArray(R.array.dashboard_spinner);
        ArrayAdapter<String> cipheradapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Cipher);
        cipheradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCipherMethod.setAdapter(cipheradapter);

        imgspeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        imgcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                String Text = tvCipherText.getText().toString();
                myClip = ClipData.newPlainText("text", Text);
                myClipboard.setPrimaryClip(myClip);
                Toast.makeText(getApplicationContext(), "Text Copied", Toast.LENGTH_SHORT).show();
            }
        });

        imgsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bh=new Bean_History();
                if(etPlainText.getText().length()>0 && tvCipherText.getText().length()>0)
                {
                    bh.setPlain_Text(etPlainText.getText().toString());
                    bh.setCipher_Method(spCipherMethod.getSelectedItem().toString());
                    bh.setCipher_Result(tvCipherText.getText().toString());
                    dbh.insert(bh);
                    Toast.makeText(Activity_Dashboard.this,"saved="+dbh.getCount(),Toast.LENGTH_SHORT).show();

                }else {
                    etPlainText.setError("some field is blank");
                }
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
                if (position == 0) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 1) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 2) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.VISIBLE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 3) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 4) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 5) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 6) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
                if (position == 7) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.VISIBLE);
                    etshift1.setVisibility(View.VISIBLE);
                }
                if (position == 8) {
                    etshiftkey.setText("");
                    etshiftkey1.setText("");
                    etshift.setVisibility(View.GONE);
                    etshift1.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnconvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = spCipherMethod.getSelectedItemPosition();
                if (position == 0) {
                    ascii();
                }
                if (position == 1) {
                    binary();
                }
                if (position == 2) {
                    caesar();
                }
                if (position == 3) {
                    hexaDecimal();
                }
                if (position == 4) {
                    rearrangeWordSentence();
                }
                if (position == 5) {
                    rearrangeWord();
                }
                if (position == 6) {
                    atbash();
                }
                if (position == 7) {
                    affine();
                }
                if (position == 8) {
                    LetterNumber();
                }
            }
        });
        etPlainText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvCipherText.setText("");
                etshiftkey.setText("");
                etshiftkey1.setText("");
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
        menuInflater.inflate(R.menu.menu_history,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
            Bitmap bitmap = getScreenShot(rootView);
            store(bitmap);
            shareImage();
        }
        if (item.getItemId() == R.id.menu_developer) {
            Intent intent = new Intent(Activity_Dashboard.this, Activity_Developer.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public static Bitmap getScreenShot(View v) {
        View screenView = v.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public void store(Bitmap bm) {
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Screenshots";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        String fileName = "Image-1.jpg";
        File file = new File(dirPath, fileName);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareImage() {
        final Intent shareintent = new Intent(Intent.ACTION_SEND);
        shareintent.setType("image/jpg");
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath().toString() + "/Screenshots";
        final File imagefile = new File(dirPath, "Image-1.jpg");
        shareintent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagefile));
        startActivity(Intent.createChooser(shareintent, "Share ScreenShot"));
    }

    public void init() {
        etPlainText = findViewById(R.id.dashboard_et_plaintext);
        tvCipherText = findViewById(R.id.dashboard_tv_ciphertext);
        spCipherMethod = findViewById(R.id.dashboard_sp_ciphermethod);
        etshift = findViewById(R.id.text_input_layout1);
        etshift1 = findViewById(R.id.text_input_layout2);
        etshiftkey = findViewById(R.id.dashboard_et_shiftkey);
        etshiftkey1 = findViewById(R.id.dashboard_et_shiftkey1);
        l1 = findViewById(R.id.dashboard_linear);
        imgspeech = findViewById(R.id.dashboard_et_microphone);
        imgcopy = findViewById(R.id.dashboard_tv_copy);
        imgsave=findViewById(R.id.dashboard_tv_save);
        btnconvert = findViewById(R.id.dashboard_btn_convert);
        dbh=new DBHelper_History(this);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
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

    public void ascii() {
        StringBuilder sb = new StringBuilder();
        String plaintext = etPlainText.getText().toString();
        char[] ascii = plaintext.toCharArray();
        for (char c : ascii) {
            sb.append((int) c);
            sb.append(' ');
        }
        String ciphertext = sb.toString();
        tvCipherText.setText(ciphertext);
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
            String ciphertext = binary.toString();
            tvCipherText.setText(ciphertext);
        }
    }

    public void caesar() {
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        String plaintext = etPlainText.getText().toString().toLowerCase();
        String shiftkey = etshiftkey.getText().toString();
        if (TextUtils.isEmpty(shiftkey) || Integer.parseInt(shiftkey) > 26) {
            etshiftkey.setError("Enter Proper Shift Key Value");
        } else {
            int shiftkeyval = Integer.parseInt(shiftkey);
            String ciphertext = "";
            for (int i = 0; i < plaintext.length(); i++) {
                int charposition = ALPHABET.indexOf(plaintext.charAt(i));
                int keyval = (shiftkeyval + charposition) % 26;
                char replaceval = ALPHABET.charAt(keyval);
                ciphertext += replaceval;
            }
            tvCipherText.setText(ciphertext);
        }
    }

    public void hexaDecimal() {
        String plaintext = etPlainText.getText().toString();
        StringBuffer hexadecimal = new StringBuffer();
        byte[] bytes = plaintext.getBytes();
        for (byte b : bytes) {
            int intval = b & 0xff;
            if (intval < 0x10) {
                hexadecimal.append("0");
            }
            hexadecimal.append(Integer.toHexString(intval));
            hexadecimal.append(' ');
        }
        String ciphertext = hexadecimal.toString();
        tvCipherText.setText(ciphertext);
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
            String reverseWord = "";
            for (int j = word.length() - 1; j >= 0; j--) {
                reverseWord = reverseWord + word.charAt(j);
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
        String plaintext = etPlainText.getText().toString();
        char[] msg = plaintext.toCharArray();
        String shiftkey = etshiftkey.getText().toString();
        String shiftkey1 = etshiftkey1.getText().toString();
        String cipher = "";
        if (TextUtils.isEmpty(shiftkey)) {
            etshiftkey.setError("Enter Proper Shift Key Value");

        }
        if (TextUtils.isEmpty(shiftkey1)) {
            etshiftkey1.setError("Enter Proper Shift Key Value");
        } else {
            int number = Integer.parseInt(shiftkey);
            int number1 = Integer.parseInt(shiftkey1);
            for (int i = 0; i < plaintext.length(); i++) {
                if (msg[i] != ' ') {
                    cipher = cipher + (char) ((((number * (msg[i] - 'A') + number1) % 26) + 'A')) + " ";
                }
            }
            tvCipherText.setText(cipher);
        }
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