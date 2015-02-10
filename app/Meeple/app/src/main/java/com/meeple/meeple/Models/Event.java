package com.meeple.meeple.Models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by arkeopix on 2/3/15.
 */
public class Event {
    private int _idEvent;
    private int _idOrganizer;
    private HashMap<String, String> _geolocation;
    private String _descriptionEvent;
    private String _nameEvent;
    private String _dateEvent;
    private List<User> _users;

    public Event(){}
    public Event(int idEvent, int idOrganizer, String geo, String desc, String name, String date) {
        this._idEvent = idEvent;
        this._idOrganizer = idOrganizer;
        this._descriptionEvent = desc;
        this._nameEvent = name;
        this._dateEvent = date;
        String[] parts = geo.split("/");
        this._geolocation.put("long", parts[0]);
        this._geolocation.put("lat", parts[1]);
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

    public HashMap<String, String> get_geolocation() {
        return _geolocation;
    }

    public String get_descriptionEvent() {
        return _descriptionEvent;
    }

    public String get_nameEvent() {
        return _nameEvent;
    }
}
