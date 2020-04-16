import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

import com.hazelcast.map.MapEvent;
import com.hazelcast.map.impl.DataAwareEntryEvent;

public class EntryEventHandler implements EntryListener {

    private String scope;

    public EntryEventHandler (String scope) {
        this.scope = scope;
    }

    public void entryAdded (EntryEvent event) {
        DataAwareEntryEvent data = (DataAwareEntryEvent) event;
        System.out.println ("- " + scope + " entry " + data.getKey () + ":" + data.getValue () + " added.");
    }

    public void entryUpdated (EntryEvent event) {
        DataAwareEntryEvent data = (DataAwareEntryEvent) event;
        System.out.println ("- " + scope + " entry " + data.getKey () + ":" + data.getValue () + " updated.");
    }

    public void entryRemoved (EntryEvent event) {
        DataAwareEntryEvent data = (DataAwareEntryEvent) event;
        System.out.println ("- " + scope + " entry " + data.getKey () + ":" + data.getValue () + " removed.");
    }

    public void entryEvicted (EntryEvent event) {
        DataAwareEntryEvent data = (DataAwareEntryEvent) event;
        System.out.println ("- " + scope + " entry " + data.getKey () + ":" + data.getValue () + " evicted.");
    }

    public void entryExpired (EntryEvent event) {
        DataAwareEntryEvent data = (DataAwareEntryEvent) event;
        System.out.println ("- " + scope + " entry " + data.getKey () + ":" + data.getValue () + " expired.");
    }

    public void mapCleared (MapEvent event) {
        System.out.println ("- " + scope + " map cleared.");
    }

    public void mapEvicted (MapEvent event) {
        System.out.println ("- " + scope + " map evicted.");
    }
}
