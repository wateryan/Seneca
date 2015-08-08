package com.wateryan.acropolis.seneca.model;

import org.jivesoftware.smack.roster.RosterEntry;

import java.io.Serializable;

/**
 * Created on 8/7/2015.
 * Serializable Wrapper for RosterEntry
 */
public class Contact implements Serializable {

    RosterEntry entry;

    public Contact(RosterEntry entry) {
        this.entry = entry;
    }

    public RosterEntry getEntry() {
        return this.entry;
    }
}
