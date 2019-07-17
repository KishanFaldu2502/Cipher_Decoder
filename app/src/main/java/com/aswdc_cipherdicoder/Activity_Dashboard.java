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
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aswdc_cipherdicoder.design.Activity_Developer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class Activity_Dashboard extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    LinearLayout l1;
    RelativeLayout rlshiftkey1;
    EditText etPlainText,etshiftkey,etshiftkey1;
    TextView tvCipherText;
    Spinner spCipherMethod;
    ImageButton imgspeech, imgcopy;
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
                if (position == 1) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    ascii();
                }
                if (position == 2) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    binary();
                }
                if (position == 3) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    caesar();
                }
                if (position == 4) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    hexaDecimal();
                }
                if (position == 5) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    rearrangeWordSentence();
                }
                if (position == 6) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    rearrangeWord();
                }
                if (position == 7) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    atbash();
                }
                if (position == 8) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.VISIBLE);
                    affine();
                }
                if (position == 9) {
                    etshiftkey1.setText("");
                    rlshiftkey1.setVisibility(View.GONE);
                    LetterNumber();
                }
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
                tvCipherText.setText("");
                etshiftkey1.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etshiftkey1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                affine();
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
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
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
        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        final File imagefile = new File(dirPath, "Image-1.jpg");
        shareintent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagefile));
        startActivity(Intent.createChooser(shareintent, "Share ScreenShot"));
    }

    public void init() {
        etPlainText = findViewById(R.id.dashboard_et_plaintext);
        tvCipherText = findViewById(R.id.dashboard_tv_ciphertext);
        spCipherMethod = findViewById(R.id.dashboard_sp_ciphermethod);
        etshiftkey1 = findViewById(R.id.dashboard_et_shiftkey1);
        rlshiftkey1 = findViewById(R.id.dashboard_rl_shiftkey1);
        l1 = findViewById(R.id.dashboard_linear);
        imgspeech = findViewById(R.id.dashboard_et_microphone);
        imgcopy = findViewById(R.id.dashboard_tv_copy);
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
        final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        String plaintext = etPlainText.getText().toString().toLowerCase();
        String shiftkey = etshiftkey.getText().toString();
        if (TextUtils.isEmpty(shiftkey) || Integer.parseInt(shiftkey) > 26) {
            etshiftkey.setError("Enter Proper Shift Key Value");
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