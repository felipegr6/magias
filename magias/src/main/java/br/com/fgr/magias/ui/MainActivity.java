package br.com.fgr.magias.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import br.com.fgr.magias.R;
import br.com.fgr.magias.dao.Acender;
import br.com.fgr.magias.dao.DaoService;
import br.com.fgr.magias.dao.DaoServiceImpl;
import br.com.fgr.magias.dao.Gravavel;
import br.com.fgr.magias.model.ControlCamera;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;

    private ControlCamera controlCamera;
    private DaoService dao;
    private Gravavel acender;

    @Bind(R.id.btn_luz_baixa)
    Button btnLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        controlCamera = new ControlCamera(this);
        dao = new DaoServiceImpl();


    }

    @Override
    protected void onStart() {

        super.onStart();

        controlCamera.openCamera();

    }

    @Override
    protected void onStop() {

        super.onStop();

        controlCamera.closeCamera();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQ_CODE_SPEECH_INPUT:

                if (resultCode == RESULT_OK && null != data) {

                    DaoService dao = new DaoServiceImpl();
                    String recognizedVoice;
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    recognizedVoice = result.get(0).toLowerCase();
                    Toast.makeText(this, recognizedVoice, Toast.LENGTH_SHORT)
                            .show();

                    switch (recognizedVoice) {

                        case ControlCamera.LUMUS:
                            controlCamera.lightOn();
                            acender = new Acender(ControlCamera.LUMUS, (new Date()).toString());
                            dao.create(acender);
                            break;
                        case ControlCamera.NOX:
                            controlCamera.lightOff();
                            acender = new Acender(ControlCamera.NOX, (new Date()).toString());
                            dao.create(acender);
                            break;

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

        if (controlCamera.verifyCamera()) {

            if (!controlCamera.isFlashOn()) {

                controlCamera.lightOn();
                btnLuz.setText(getText(R.string.btn_apagar_luz));
                acender = new Acender(ControlCamera.LUMUS, (new Date()).toString());
                dao.create(acender);

            } else {

                controlCamera.lightOff();
                btnLuz.setText(getText(R.string.btn_acender_luz));
                acender = new Acender(ControlCamera.NOX, (new Date()).toString());
                dao.create(acender);

            }

        } else
            Toast.makeText(this, R.string.msg_flash_nao_ligou, Toast.LENGTH_SHORT).show();

    }

}