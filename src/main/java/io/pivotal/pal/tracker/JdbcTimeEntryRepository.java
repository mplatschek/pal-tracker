package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
        rs.getLong("id"),
        rs.getLong("project_id"),
        rs.getLong("user_id"),
        rs.getDate("date").toLocalDate(),
        rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.jdbcTemplate.update(con -> {
            PreparedStatement prep = con.prepareStatement(
                    "INSERT INTO time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            prep.setLong(1, timeEntry.getProjectId());
            prep.setLong(2, timeEntry.getUserId());
            prep.setDate(3, Date.valueOf(timeEntry.getDate()));
            prep.setInt(4, timeEntry.getHours());

            return prep;
        }, keyHolder);

        return this.find(keyHolder.getKey().longValue());
    }

    @Override
    public TimeEntry find(Long id) {
        return this.jdbcTemplate.query( "SELECT * FROM time_entries WHERE id = ?",
                new Object[] {id},
                extractor);
    }

    @Override
    public List<TimeEntry> list() {
        return this.jdbcTemplate.query("SELECT * FROM time_entries", mapper);
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        this.jdbcTemplate.update("UPDATE time_entries " +
                        "SET project_id = ?, user_id = ?, date = ?,  hours = ? " +
                        "WHERE id = ?",
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                Date.valueOf(timeEntry.getDate()),
                timeEntry.getHours(),
                id);

        return this.find(id);
    }

    @Override
    public void delete(Long id) {
        this.jdbcTemplate.update("DELETE FROM time_entries WHERE id = ?", id);
    }
}
