package com.wateryan.acropolis.seneca.core.network;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 8/7/2015.
 */
public class RosterController {

    private Roster roster;

    public RosterController(XMPPConnection connection) {
        this.roster = Roster.getInstanceFor(connection);
    }

    private void persistRosterEntries() {
        // TODO
    }

//    private List<RosterEntry> getPersistentRoster() {
//    // TODO
//    }

    public List<RosterEntry> getRosterEntries() {
        return new ArrayList<>(this.roster.getEntries());
    }
}
