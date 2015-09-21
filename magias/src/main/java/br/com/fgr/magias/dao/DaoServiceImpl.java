package br.com.fgr.magias.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class DaoServiceImpl implements DaoService {

    private Firebase ref;

    public DaoServiceImpl(@NonNull String uid) {
        ref = new Firebase("https://fiery-inferno-4161.firebaseio.com/").child(uid);
    }

    @Override
    public boolean create(Gravavel gravavel) {

        ref.setValue(gravavel.getMap(), new Firebase.CompletionListener() {

            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                if (firebaseError != null)
                    Log.e("Firebase", firebaseError.getMessage());

            }

        });

        return true;

    }

    @Override
    public Gravavel read() {
        throw new UnsupportedOperationException();
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