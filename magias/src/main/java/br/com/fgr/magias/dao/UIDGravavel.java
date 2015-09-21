package br.com.fgr.magias.dao;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class UIDGravavel implements Gravavel {

    private String uid;

    public UIDGravavel(@NonNull String uid) {
        this.uid = uid;
    }

    @Override
    public String tipoClasse() {
        return getClass().getSimpleName();
    }

    @Override
    public Map<String, String> getMap() {

        Map<String, String> map = new HashMap<>();

        map.put("uid", uid);

        return map;

    }

}