package cycling;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Stage implements Serializable {

  protected Integer stageId;
  protected String name;
  protected String description;
  protected String currentCondition;
  //The type of the stage. This is used to determine the amount of points given to the winner
  protected StageType type;
  //Length of stage in kilometers
  protected Double length;
  //The date and time in which the stage will be raced. It cannot be null
  protected LocalDateTime startTime;

  protected ArrayList < Segment > segmentList = new ArrayList < >();
  protected HashMap < Rider,
  Long > stageTimes = new HashMap < >();
  protected HashMap < Rider,
  Integer > riderPoints;

  public Stage(Integer stageId, String name, String description, String currentCondition, StageType type, Double length, LocalDateTime startTime) {
    this.stageId = stageId;
    this.name = name;
    this.description = description;
    this.currentCondition = currentCondition;
    this.type = type;
    this.length = length;
    this.startTime = startTime;
  }

  public Integer getStageId() {
    return stageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String inputName) {
    this.name = inputName;
  }

  public String getCurrentCondition() {
    return currentCondition;
  }

  public void setCurrentCondition(String inputCurrentCondition) {
    this.currentCondition = inputCurrentCondition;
  }

  public double getLength() {
    return length;
  }

  public LocalDateTime getStartTime() {
    return startTime;
  }

  public ArrayList < Segment > getSegmentList() {
    return segmentList;
  }

  public void setSegmentList(ArrayList < Segment > segmentList) {
    this.segmentList = segmentList;
  }

  public HashMap < Rider,
  Long > getStageTimes() {
    return stageTimes;
  }

  public HashMap < Rider,
  Integer > getRiderPoints() {
    return riderPoints;
  }

  public void setRiderPoints(HashMap < Rider, Integer > riderPoints) {
    this.riderPoints = riderPoints;
  }

  public String toString() {
    return ("Race ID: " + stageId + "\nName: " + name + "\nDescription: " + description + "\nCurrent condition: " + currentCondition + "\nType: " + type + "\nLength: " + length + "\nStart time: " + startTime + "\n");
  }

  public void addSegment(Segment inputSegment) {
    segmentList.add(inputSegment);
  }

  public void removeSegmentFromStage(Integer segmentId) {
    for (Segment segment: segmentList) {
      if (segment.getSegmentId().equals(segmentId)) {
        segmentList.remove(segment);
        break;
      }
    }
  }

  public void removeStageTimesFromStage() {
    for (Rider rider: stageTimes.keySet()) {
      stageTimes.remove(rider);
    }
  }

  public void removeRiderStageTimesFromStage(Rider riderToRemove) {
    for (Rider rider: stageTimes.keySet()) {
      if (rider.equals(riderToRemove)) {
        stageTimes.remove(rider);
      }
    }
  }

  public void removeRiderPointsFromStage() {
    for (Rider rider: riderPoints.keySet()) {
      riderPoints.remove(rider);
    }
  }
  public void removeSpecificRiderPointsFromStage(Rider riderToRemove) {
    for (Rider rider: riderPoints.keySet()) {
      if (rider.equals(riderToRemove)) {
        riderPoints.remove(rider);
      }
    }
  }
}