package br.com.fgr.magias.dao;

public interface DaoService {

    boolean create(Gravavel gravavel);

    Gravavel read();

    void update();

    void delete();

}