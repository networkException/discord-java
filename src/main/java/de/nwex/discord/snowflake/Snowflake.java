package de.nwex.discord.snowflake;

import java.util.Date;
import java.util.Objects;
import net.dv8tion.jda.api.entities.ISnowflake;
import org.json.JSONObject;

public class Snowflake implements ISnowflake {

    private final Long value;

    public Snowflake(Long value) {
        this.value = value;
    }

    public Snowflake(String value) {
        this.value = Long.parseLong(value);
    }

    public Snowflake(ISnowflake value) {
        this.value = Long.parseLong(value.getId());
    }

    public static boolean validate(Long value) {
        return (value >> 22) + 1420070400000L >= 0;
    }

    public Long getValue() {
        return value;
    }

    public String getValueAsString() {
        return value.toString();
    }

    public Long getInitialWorker() {
        return (value & 0x3E0000) >> 17;
    }

    public Long getInitialProcess() {
        return (value & 0x1F000) >> 12;
    }

    public Long getIncrement() {
        return value & 0xFFF;
    }

    public Long getTimestamp() {
        return (value >> 22) + 1420070400000L;
    }

    public Date getDate() {
        return new Date(getTimestamp());
    }

    public JSONObject toJSON() {
        return new JSONObject()
            .put("value", value)
            .put("worker", getInitialWorker())
            .put("process", getInitialProcess())
            .put("increment", getIncrement())
            .put("timestamp", getTimestamp())
            .put("date", getDate());
    }

    @Override
    public String toString() {
        return getValueAsString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Snowflake)) {
            return false;
        }

        Snowflake snowflake = (Snowflake) o;
        return Objects.equals(value, snowflake.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public long getIdLong() {
        return value;
    }
}
