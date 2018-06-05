package backend.helperClasses;

public enum KindOfEvent {
  GOOGLE_EVENT(1),
  OUR_EVENT(0),
  OUTLOOK_EVENT(2);
  private final int kindOfEvent;
  KindOfEvent(int kindOfEVent) {
    this.kindOfEvent=kindOfEVent;
  }
  public int getKindOfEventAsInt() {
    return this.kindOfEvent;
  }
}