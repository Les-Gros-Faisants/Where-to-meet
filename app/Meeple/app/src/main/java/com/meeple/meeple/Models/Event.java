package com.meeple.meeple.Models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by arkeopix on 2/3/15.
 */
public class Event {
    private int _idEvent;
    private int _idOrganizer;
    private HashMap<String, Double> _geolocation;
    private String _descriptionEvent;
    private String _nameEvent;
    private String _dateEvent;
    private List<User> _users;

    public Event(){}
    public Event(int idEvent, int idOrganizer, Double lat, Double lng, String desc, String name, String date) {
        this._idEvent = idEvent;
        this._idOrganizer = idOrganizer;
        this._descriptionEvent = desc;
        this._nameEvent = name;
        this._dateEvent = date;
        this._geolocation = new HashMap<String, Double>();
        this._geolocation.put("long", lat);
        this._geolocation.put("lat", lng);
    }


    public Event(int idEvent, int idOrganizer, Double lat, Double lng, String desc, String name, String date, List<User> users) {
        this._idEvent = idEvent;
        this._idOrganizer = idOrganizer;
        this._descriptionEvent = desc;
        this._nameEvent = name;
        this._dateEvent = date;
        this._geolocation = new HashMap<String, Double>();
        this._geolocation.put("long", lat);
        this._geolocation.put("lat", lng);
        this._users = users;

    }


    public void set_users(List<User> _users) {
        this._users = _users;
    }

    public List<User> get_users() {
        return _users;
    }

    public String get_dateEvent() {
        return _dateEvent;
    }

    public int get_idEvent() {
        return _idEvent;
    }

    public int get_idOrganizer() {
        return _idOrganizer;
    }

    public HashMap<String, Double> get_geolocation() {
        return _geolocation;
    }

    public String get_descriptionEvent() {
        return _descriptionEvent;
    }

    public String get_nameEvent() {
        return _nameEvent;
    }
}
