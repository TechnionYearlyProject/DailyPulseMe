package backend.helperClasses;

public enum BandType {
    FITBIT_BAND(1),
    GOOGLEFIT_BAND(0),

    NUM_OF_BANDS(2);
    private final int bandType;

    BandType(int bandType) {
        this.bandType = bandType;
    }

    public int getBandTypeAsInt() {
        return bandType;
    }
}
