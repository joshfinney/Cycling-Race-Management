package cycling;

import java.io.Serializable;
import java.util.*;

public class Team implements Serializable {

  public Integer teamId;
  private String name;
  private final String description;
  private ArrayList < Rider > riders;

  public Team(Integer teamId, String name, String description) {

    riders = new ArrayList < >();

    this.teamId = teamId;
    this.name = name;
    this.description = description;
  }

  public Integer getTeamId() {
    return teamId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ArrayList < Rider > getRiders() {
    return riders;
  }

  public String toString() {
    return ("Team ID : " + teamId + "\nTeam Name: " + name + "\nDescription: " + description + "\nRiders: " + riders + "\n");
  }

  //checking to see if rider is already part of a team
  public void addRider(Rider rider) {
    if (rider.getTeamId().equals(this.teamId)) {
      riders.add(rider);

    }
  }

  public void removeRider(Integer riderId) {
    for (Rider rider: riders) {
      if (rider.getRiderId().equals(riderId)) {
        riders.remove(rider);
        break;
      }
    }
  }
}