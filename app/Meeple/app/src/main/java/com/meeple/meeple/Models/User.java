package com.meeple.meeple.Models;

/**
 * Created by arkeopix on 2/3/15.
 */
public class User {
    private int      _idUser;
    private String   _pseudoUser;
    private String   _mailUser;

    public User(){}
    public User(int idUser, String pseudoUser, String mailUser) {
        this._idUser = idUser;
        this._pseudoUser = pseudoUser;
        this._mailUser = mailUser;
    }

    public int get_idUser() {
        return _idUser;
    }

    public String get_pseudoUser() {
        return _pseudoUser;
    }

    public String get_mailUser() {
        return _mailUser;
    }
}
