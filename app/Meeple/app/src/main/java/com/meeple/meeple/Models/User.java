package com.meeple.meeple.Models;

import java.util.List;

/**
 * Created by arkeopix on 2/3/15.
 */
public class User {
    private int         _idUser;
    private String      _pseudoUser;
    private String      _mailUser;
    private List<Event> _events;
    private List<Tags> _tags;

    public User(){}
    public User(int idUser, String pseudoUser, String mailUser, List<Event> events, List<Tags> _tags) {
        this._idUser = idUser;
        this._pseudoUser = pseudoUser;
        this._mailUser = mailUser;
        this._events = events;
        this._tags = _tags;

    }
    public User(int idUser, String _pseudoUser) {
        this._idUser = idUser;
        this._pseudoUser = _pseudoUser;
    }

    public List<Tags> get_tags() {
        return _tags;
    }

    public void set_tags(List<Tags> _tags) {
        this._tags = _tags;
    }

    public void set_mailUser(String _mailUser) {
        this._mailUser = _mailUser;
    }

    public void set_pseudoUser(String _pseudoUser) {
        this._pseudoUser = _pseudoUser;
    }

    public void set_idUser(int _idUser) {
        this._idUser = _idUser;
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

    public List<Event> get_events() {
        return _events;
    }

    public void set_events(List<Event> _events) {
        this._events = _events;
    }
}
