package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable Long id) {
        TimeEntry timeEntry = timeEntryRepository.find(id);
        return new ResponseEntity<>(timeEntry, timeEntry != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable Long id) {
        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody TimeEntry timeEntry) {
        return new ResponseEntity<>(timeEntryRepository.create(timeEntry), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody TimeEntry timeEntry) {
        timeEntry = timeEntryRepository.update(id, timeEntry);
        return new ResponseEntity<>(timeEntry, timeEntry != null ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping()
    public ResponseEntity<List<TimeEntry>> list() {
        return new ResponseEntity<>(timeEntryRepository.list(), HttpStatus.OK);
    }

}
