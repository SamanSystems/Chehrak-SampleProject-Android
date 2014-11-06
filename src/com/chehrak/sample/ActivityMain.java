package com.chehrak.sample;

import java.io.File;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.chehrak.lib.Chehrak;
import com.chehrak.lib.Chehrak.ChehrakListener;


public class ActivityMain extends Activity {

    private static final String PATH_SD    = Environment.getExternalStorageDirectory().toString();
    private final String        DefaultUrl = "http://chehrak.com/theme/Metro/img/pic.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button btnLoad = (Button) findViewById(R.id.btnLoad);
        final ImageView imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        final EditText email = (EditText) findViewById(R.id.edtEmail);

        btnLoad.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String strEmail = email.getText().toString();
                if ( !isValidEmail(strEmail)) {
                    // Toast Invalid Email Adress !
                    Toast.makeText(ActivityMain.this, R.string.EmailError, Toast.LENGTH_SHORT).show();
                    return;
                }

                ////////////////// ***** Chehrak Codes Start *****  //////////////////

                ChehrakListener listener = new ChehrakListener() {

                    @Override
                    public void onSuccess(Bitmap avatarBitmap, File avatarFile, final boolean isAvatarFound) {
                        // loading dialog should dismiss here 
                        imgAvatar.setImageBitmap(avatarBitmap);
                    }


                    @Override
                    public void onFailed() {
                        // loading dialog should dismiss here 
                        Toast.makeText(ActivityMain.this, R.string.onFailed, Toast.LENGTH_SHORT).show();
                        Log.w("Chehrak", "Faild To Connect To Server !");
                    }
                };

                Chehrak chehrak = new Chehrak(PATH_SD, DefaultUrl);
                // You Can Show a loading dialog here 
                chehrak.getAvatar(strEmail, listener, Chehrak.SIZE_128);
                ////////////////// ***** Chehrak Codes End *****  //////////////////

            }
        });

    }


    //Verify Valid Email Address
    public boolean isValidEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

}
