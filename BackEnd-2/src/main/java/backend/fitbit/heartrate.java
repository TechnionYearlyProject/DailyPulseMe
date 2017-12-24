package backend.fitbit;

import java.io.Serializable;
import java.util.List;

public class heartrate implements Serializable {
    public String dateTime;
    public List<pulse> value;
    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public List<pulse> getValue() {
        return value;
    }

    public void setValue(List<pulse> value) {
        this.value = value;
    }
}
