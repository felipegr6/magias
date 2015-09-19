package br.com.fgr.magias.dao;

import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class DaoServiceImpl implements DaoService {

    private Firebase ref;

    public DaoServiceImpl() {
        ref = new Firebase("https://fiery-inferno-4161.firebaseio.com/");
    }

    @Override
    public boolean create(Gravavel gravavel) {

        ref.child(gravavel.tipoClasse());
        ref.push().setValue(gravavel.getMap(), new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                if (firebaseError != null)
                    Log.e("Firebase", firebaseError.getMessage() + "");

            }

        });

        return true;

    }

    @Override
    public void read() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }

}