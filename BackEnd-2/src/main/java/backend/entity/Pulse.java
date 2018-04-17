package backend.entity;

public class Pulse {

    private int value;

    @Override
    public boolean equals(Object obj) {
        return value==((Pulse)obj).getValue();
    }

    public Pulse(int value) {

        this.value = value;
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
