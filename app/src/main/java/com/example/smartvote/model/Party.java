package com.example.smartvote.model;

public class Party {

    public final int id;
    public final String partyName;
    public final String logo;

    public Party(int id, String partyName, String logo) {
        this.id = id;
        this.partyName = partyName;
        this.logo = logo;
    }

    @Override
    public String toString() {
        return partyName;
    }
}
