package cycling;

import java.io.Serializable;
import java.util.* ;

public class StagedRace implements Serializable {

  private Integer raceId;
  private String name;
  private final String description;
  private ArrayList < Stage > stageList = new ArrayList < >(); // Ordered list of stages in the staged race
  private ArrayList < Rider > sumOfAdjustedTimes = new ArrayList < >();

  public StagedRace(Integer raceId, String name, String description) {
    this.raceId = raceId;
    this.name = name;
    this.description = description;
  }

  public Integer getRaceId() {
    return raceId;
  }

  public String getName() {
    return name;
  }

  public void setName(String inputName) {
    this.name = inputName;
  }

  public ArrayList < Stage > getStageList() {
    return stageList;
  }

  public void addStages(Stage inputStage) {
    stageList.add(inputStage);
  }

  public ArrayList < Rider > getSumOfAdjustedTimes() {
    return sumOfAdjustedTimes;
  }

  public void setSumOfAdjustedTimes(ArrayList < Rider > sumOfAdjustedTimes) {
    this.sumOfAdjustedTimes = sumOfAdjustedTimes;
  }

  public double totalStageLength() {
    double totalStageLength = 0.0;
    for (Stage stage: stageList) {
      totalStageLength = totalStageLength + stage.getLength();
    }
    return totalStageLength;

  }

  // Object attributes visualiser
  public String toString() {
    return ("Race ID: " + raceId + "\nName: " + name + "\nDescription: " + description + "\nNumber of Stages: " + stageList.size() + "\nTotal Length: " + totalStageLength() + "\n");
  }

  public void removeStageFromRace(Integer stageId) {
    for (Stage stage: stageList) {
      if (stage.getStageId().equals(stageId)) {
        stageList.remove(stage);
        break;
      }
    }
  }
}