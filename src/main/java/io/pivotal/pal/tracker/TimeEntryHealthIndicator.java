package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private TimeEntryRepository tER;

    public TimeEntryHealthIndicator(TimeEntryRepository tER) {
        this.tER = tER;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        builder.down();

        if ( tER.list().size() < 5 ) builder.up();

        return builder.build();
    }
}
