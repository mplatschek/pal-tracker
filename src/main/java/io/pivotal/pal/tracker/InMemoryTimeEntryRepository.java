package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    HashMap<Long, TimeEntry> database = new HashMap();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        Long objectId = new Long(database.size() + 1);
        timeEntry.setId(objectId);

        database.put(objectId, timeEntry);

        return database.get(objectId);
    }

    @Override
    public TimeEntry find(Long id) {
        return database.get(id);
    }

    @Override
    public List<TimeEntry> list() {
        return new ArrayList<>(database.values());
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        timeEntry.setId(id);
        database.put(id, timeEntry);
        return database.get(id);
    }

    @Override
    public void delete(Long id) {
        database.remove(id);
    }
}
