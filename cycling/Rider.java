package cycling;

import java.io.Serializable;

public class Rider implements Serializable {

  //attributes of each rider
  private String name;
  private final Integer riderId;
  private final Integer yearOfBirth;
  public Integer teamId;

  public Rider(Integer riderId, Integer teamId, String name, Integer yearOfBirth) {

    this.riderId = riderId;
    this.teamId = teamId;
    this.name = name;
    this.yearOfBirth = yearOfBirth;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getRiderId() {
    return riderId;
  }

  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  public String toString() {
    return ("RiderID: " + riderId + "\nName: " + name + "\nDOB: " + yearOfBirth + "\nTeamID: " + teamId + "\n");
  }
}