package com.meeple.meeple.Models;

/**
 * Created by arkeopix on 2/3/15.
 */
public class Tags {
    private int _idTags;
    private int _idAggressor;
    private int _idVictim;
    private String _tagName;
    private boolean _selected;
    private int     _occurence;

    public Tags(){}
    public Tags(int _idTags, int _idAggressor, int _idVictim, String _tagName) {
        this._idTags = _idTags;
        this._idAggressor = _idAggressor;
        this._idVictim = _idVictim;
        this._tagName = _tagName;
    }

    public Tags(String _tagName, int tagOccurence) {
        this._occurence = tagOccurence;
        this._tagName = _tagName;
    }

    public int get_idTags() {
        return _idTags;
    }

    public int get_idAggressor() {
        return _idAggressor;
    }

    public int get_idVictim() {
        return _idVictim;
    }

    public String get_tagName() {
        return _tagName;
    }

    public boolean isSelected() {
        return _selected;
    }

    public void setSelected(boolean selected){
        _selected = selected;
    }
}
