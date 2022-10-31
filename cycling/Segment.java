package cycling;

import java.io.Serializable;
import java.util.*;

public class Segment implements Serializable {

  private final Integer segmentId;
  private final Double location;
  private final SegmentType type;
  private final Double averageGradient;
  private final Double Length;

  //Results of rider's in the segment
  private HashMap < Rider,
  Long > riderTimes = new HashMap < >();

  public Segment(Integer segmentId, Double location, SegmentType type, Double averageGradient, Double length) {

    this.segmentId = segmentId;
    this.location = location;
    this.type = type;
    this.averageGradient = averageGradient;
    Length = length;
  }

  public Integer getSegmentId() {
    return segmentId;
  }

  public Double getLocation() {
    return location;
  }

  public SegmentType getType() {
    return type;
  }

  public HashMap < Rider,
  Long > getRiderTimes() {
    return riderTimes;
  }

  public void addRiderTimes(Rider inputRider, Long inputTimeMilliseconds) {
    riderTimes.put(inputRider, inputTimeMilliseconds);
  }

  public String toString() {
    return ("Segment ID: " + segmentId + "\nLocation: " + location + "\nType: " + type + "\n");
  }

  public void removeTimeResultsFromSegment() {
    for (Rider rider: riderTimes.keySet()) {
      riderTimes.remove(rider);
    }
  }

  public void removeRiderTimeResult(Rider riderToRemove) {
    for (Rider rider: riderTimes.keySet()) {
      if (rider.equals(riderToRemove)) {
        riderTimes.remove(rider);
        break;
      }
    }
  }
}