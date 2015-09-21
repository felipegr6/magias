package br.com.fgr.magias.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import br.com.fgr.magias.R;
import br.com.fgr.magias.dao.DaoService;
import br.com.fgr.magias.dao.InternalDaoImpl;
import br.com.fgr.magias.dao.UIDGravavel;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UIDActivity extends AppCompatActivity {

    @Bind(R.id.txt_id_msg)
    EditText txtUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uid);

        ButterKnife.bind(this);

        DaoService daoService = new InternalDaoImpl(getApplicationContext());
        String uid = daoService.read().getMap().get("uid");

        if (uid != null && !uid.isEmpty()) {

            startActivity(new Intent(this, MainActivity.class));
            finish();

        }

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.btn_confirm_id)
    void gravarUid() {

        String uid = txtUID.getText().toString();

        if (!uid.isEmpty()) {

            DaoService daoService = new InternalDaoImpl(getApplicationContext());
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("UID", uid);

            daoService.create(new UIDGravavel(uid));

            startActivity(intent);
            finish();

        } else
            Toast.makeText(this, "Preencha algum valor.", Toast.LENGTH_LONG).show();

    }

}