package br.com.fgr.magias.dao;

import java.util.HashMap;
import java.util.Map;

public class Acender implements Gravavel {

    private String comando;
    private String data;

    public Acender(String comando, String data) {
        this.comando = comando;
        this.data = data;
    }

    public String getComando() {
        return comando;
    }

    public String getData() {
        return data;
    }

    @Override
    public String tipoClasse() {
        return getClass().getSimpleName().toLowerCase();
    }

    @Override
    public Map<String, String> getMap() {

        Map<String, String> map = new HashMap<>();

        map.put("comando", comando);
        map.put("data", data);

        return map;

    }

}