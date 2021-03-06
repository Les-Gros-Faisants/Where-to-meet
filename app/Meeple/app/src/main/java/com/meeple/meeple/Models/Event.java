package com.meeple.meeple.Models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by arkeopix on 2/3/15.
 */
public class Event {
    private int _idEvent;
    private int _idOrganizer;
    private String _nOrganizer;
    private HashMap<String, Double> _geolocation;
    private String _descriptionEvent;
    private String _nameEvent;
    private String _dateEvent;
    private List<User> _users;
    private List<Tags> _tags;

    public Event(){}

    public Event(int idEvent, String nameEvent, Double lat, Double lng) {
        this._nameEvent = nameEvent;
        this._idEvent = idEvent;
        this._geolocation = new HashMap<String, Double>();
        this._geolocation.put("long", lng);
        this._geolocation.put("lat", lat);
    }

    public Event(int idEvent, int idOrganizer, Double lat, Double lng, String desc, String name, String date) {
        this._idEvent = idEvent;
        this._idOrganizer = idOrganizer;
        this._descriptionEvent = desc;
        this._nameEvent = name;
        this._dateEvent = date;
        this._geolocation = new HashMap<String, Double>();
        this._geolocation.put("long", lng);
        this._geolocation.put("lat", lat);
    }


    public Event(int idEvent, int idOrganizer, Double lat, Double lng, String desc, String name, String date, List<User> users, List<Tags> tags) {
        this._idEvent = idEvent;
        this._idOrganizer = idOrganizer;
        this._descriptionEvent = desc;
        this._nameEvent = name;
        this._dateEvent = date;
        this._geolocation = new HashMap<String, Double>();
        this._geolocation.put("long", lng);
        this._geolocation.put("lat", lat);
        this._users = users;
        this._tags = tags;
    }

    public Event(int idEvent, String pseudo, Double lat, Double lng, String desc, String name, String date, List<User> users, List<Tags> tags) {
        this._idEvent = idEvent;
        this._nOrganizer = pseudo;
        this._descriptionEvent = desc;
        this._nameEvent = name;

        this._dateEvent = date;
        this._geolocation = new HashMap<String, Double>();
        this._geolocation.put("long", lng);
        this._geolocation.put("lat", lat);
        this._users = users;
        this._tags = tags;
    }

    public List<Tags> get_tags() {
        return _tags;
    }

    public void set_users(List<User> _users) {
        this._users = _users;
    }

    public List<User> get_users() {
        return _users;
    }

    public String get_nOrganizer() {
        return _nOrganizer;
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
