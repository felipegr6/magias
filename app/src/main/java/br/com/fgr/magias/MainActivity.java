package br.com.fgr.magias;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private boolean flashLigado = false;

    private Camera cam;
    private Camera.Parameters param;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {

        super.onResume();

        cam = Camera.open();
        param = cam.getParameters();

    }

    @Override
    protected void onPause() {

        super.onPause();

        cam.stopPreview();
        cam.release();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_CODE_SPEECH_INPUT:

                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Toast.makeText(this, result.get(0), Toast.LENGTH_SHORT)
                            .show();

                    if (result.get(0).toLowerCase().equals("lumus")) {

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                luz();
                            }

                        }, 1000);

                    }

                }

                break;

        }

    }

    @OnClick(R.id.btn_mic)
    void microfone() {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent
                .LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(), getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.btn_luz_baixa)
    void luz() {

        if (verificarFlash()) {

            String modo;

            if (!flashLigado) {

                modo = Camera.Parameters.FLASH_MODE_TORCH;
                flashLigado = true;

            } else {

                modo = Camera.Parameters.FLASH_MODE_OFF;
                flashLigado = false;

            }

            param.setFlashMode(modo);
            cam.setParameters(param);
            cam.startPreview();

        } else
            Toast.makeText(this, "Não foi possível ligar o flash.", Toast.LENGTH_SHORT).show();

    }

    private boolean verificarFlash() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

}