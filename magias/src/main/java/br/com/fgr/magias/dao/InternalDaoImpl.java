package br.com.fgr.magias.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class InternalDaoImpl implements DaoService {

    private Context context;
    private SharedPreferences preferences;

    private final String internal = "internal";
    private final String uid = "uid";

    public InternalDaoImpl(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(internal, Context.MODE_PRIVATE);
    }

    @Override
    public boolean create(Gravavel gravavel) {

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(uid, new Gson().toJson(gravavel));

        return editor.commit();

    }

    @Override
    public Gravavel read() {
        Gravavel gravavel = new Gson().fromJson(preferences.getString(uid, ""), UIDGravavel.class);
        return gravavel;
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException();
    }

}