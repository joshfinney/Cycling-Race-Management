package cycling;

import java.io. * ;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util. * ;

public class CyclingPortal implements CyclingPortalInterface {

  ArrayList < Team > teams = new ArrayList < >();
  ArrayList < Rider > riders = new ArrayList < >();
  ArrayList < StagedRace > races = new ArrayList < >();

  public int random6DigitId() {
    return new Random().nextInt(900000) + 100000;
  }

  public int getRandomIdForRace() {

    boolean alreadyUsed;
    int id;
    do {
      //will run to function to generate a random 6 digit id
      id = random6DigitId();
      alreadyUsed = false;
      //for each race in the arraylist 'races'...
      for (StagedRace race: races) {
        //checking to see if the id is already used for another race...
        if (race.getRaceId().equals(id)) {
          alreadyUsed = true;
          break;
        }
      }
    } while ( alreadyUsed );

    return id;
  }

  public int getRandomIdForTeam() {

    boolean alreadyUsed;
    int id;
    do {
      id = random6DigitId();
      alreadyUsed = false;
      for (Team team: teams) {
        if (team.getTeamId().equals(id)) {
          alreadyUsed = true;
          break;
        }
      }
    } while ( alreadyUsed );

    return id;
  }

  public int getRandomIdForRider() {

    boolean alreadyUsed;
    int id;
    do {
      id = random6DigitId();
      alreadyUsed = false;
      for (Rider rider: riders) {
        if (rider.getRiderId().equals(id)) {
          alreadyUsed = true;
          break;
        }
      }
    } while ( alreadyUsed );

    return id;
  }

  public int getRandomIdForStage() {

    boolean alreadyUsed;
    int id;
    do {
      id = random6DigitId();
      alreadyUsed = false;
      for (StagedRace race: races) {
        for (Stage stage: race.getStageList()) {
          if (stage.getStageId().equals(id)) {
            alreadyUsed = true;
            break;
          }
        }
      }
    } while ( alreadyUsed );

    return id;
  }

  public int getRandomIdForSegment() {

    boolean alreadyUsed;
    int id;
    do {
      id = random6DigitId();
      alreadyUsed = false;
      for (StagedRace race: races) {
        for (Stage stage: race.getStageList()) {
          if (stage.getSegmentList() == null) {
            alreadyUsed = false;
          } else {
            for (Segment segment: stage.getSegmentList()) {
              if (segment.getSegmentId().equals(id)) {
                alreadyUsed = true;
                break;
              }
            }
          }
        }
      }
    } while ( alreadyUsed );

    return id;
  }

  public LocalTime convertMillisecondsToLocaltime(Long milliseconds) {

    int hours = (int)(milliseconds / 3600000);
    int minutes = (int)(milliseconds / 60000) - (hours * 60);
    int seconds = (int)(milliseconds / 1000) - (hours * 3600) - (minutes * 60);
    int nanoseconds = (int)((milliseconds) - (hours * 3600000) - (minutes * 60000) - (seconds * 1000)) * 1000000;

    return LocalTime.of(hours, minutes, seconds, nanoseconds);
  }

  // Gets the races currently created in the platform
  // Returns: An array of race IDs in the system or an empty array if none exists
  public int[] getRaceIds() {

    int raceId;
    //an empty array which will hold the individual raceIDs
    int[] arrayOfRaceIDs = new int[0];

    //for each race within the arraylist 'races'...
    for (StagedRace race: races) {
      //assigns that staged race ID to a variable "raceId"
      raceId = race.getRaceId();
      arrayOfRaceIDs = Arrays.copyOf(arrayOfRaceIDs, arrayOfRaceIDs.length + 1);
      //adds that variable "raceId" to an array
      arrayOfRaceIDs[arrayOfRaceIDs.length - 1] = raceId;
    }
    return arrayOfRaceIDs;
  }

  // Creates a staged race in the platform with the given name and description
  // Returns: the unique ID of the created race
  public int createRace(String name, String description) throws IllegalNameException,
  InvalidNameException {

    //method to assign a race with random 6 digit IO
    int raceId = getRandomIdForRace();

    boolean validName = false;

    for (StagedRace race: races) {
      if (race.getName().equals(name)) {
        throw new IllegalNameException("Race name already exists, try another name");
      }
      if (name == null) {
        throw new InvalidNameException("Race name cannot be null, try inputting a name");
      }
      if (name.length() == 0) {
        throw new InvalidNameException("Race name cannot be empty, try inputting a name");
      }
      if (name.length() > 30) {
        throw new InvalidNameException("Race name is too long, try a shorter name");
      }
      if (name.contains(" ")) {
        throw new InvalidNameException("Race name cannot contain any spaces, try again");
      }
      for (int i = 0; i < name.length(); i = i + 1) {
        validName = Character.isLetter(name.charAt(i));
      }
      if (!validName) {
        throw new InvalidNameException("Race name must only contain characters,try again");
      }
    }

    //creating a race
    StagedRace race = new StagedRace(raceId, name, description);
    //adds the race to an arraylist of races
    races.add(race);

    return raceId;
  }

  // Gets the details from a race
  // Returns: Any formatted string containing the race ID, name, description, the number of stages, and the total length (i.e., the sum of all stages' length)
  public String viewRaceDetails(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    //after looping through the list, if still null means ID does not exist in system
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    return wantedRace.toString();
  }

  // The method removes the race and all its related information, i.e., stages, segments, and results.
  // Returns: nothing
  public void removeRaceById(int raceId) throws IDNotRecognisedException {

    StagedRace raceToRemove = null;

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        raceToRemove = race;
        break;
      }
    }
    if (raceToRemove == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    races.remove(raceToRemove);
    //garbage collection - if there is any object in the system not being referenced it is deleted from memory
    System.gc();
  }

  // The method queries the number of stages created for a race
  // Returns: The number of stages created for the race
  public int getNumberOfStages(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    int counter;

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }
    //returns the size of the array of stages within a specific race
    counter = wantedRace.getStageList().size();

    return counter;
  }

  // Creates a new stage and adds it to the race
  // Returns: The unique ID of the stage
  public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime, StageType type)
  throws IDNotRecognisedException,
  IllegalNameException,
  InvalidNameException,
  InvalidLengthException {

    int stageId = getRandomIdForStage();

    StagedRace wantedRace = null;
    boolean validName = true;

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    for (Stage stage: wantedRace.getStageList()) {
      if (stage.getName().equals(stageName)) {
        validName = false;
        break;
      }
    }

    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    if (stageName == null) {
      throw new InvalidNameException("Stage name can't be null, try inputting a name");
    } else if (stageName.length() > 30) {
      throw new InvalidNameException("Stage name must be less than 30 characters, try again");
    } else if (stageName.contains(" ")) {
      throw new InvalidNameException("Stage name contains spaces, try again");
    } else if (!validName) {
      throw new IllegalNameException("Stage name already exists, try another name");
    }

    if (length < 5) {
      throw new InvalidLengthException("Length can't shorter than 5km, try inputting a larger length");
    }

    Stage stage = new Stage(stageId, stageName, description, "Stage created", type, length, startTime);
    wantedRace.addStages(stage);

    return stageId;
  }

  // Retrieves the list of stage IDs of a race
  // Returns: An array of stage IDs ordered (from first to last) by their sequence in the race or an empty array if none exists
  public int[] getRaceStages(int raceId) throws IDNotRecognisedException {

    Integer currentStageID;
    StagedRace wantedRace = null;

    int[] stageIds = new int[0];
    List < Stage > stages;

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    //for each stage within that specific staged race...
    stages = wantedRace.getStageList();
    //will sort the stages based on their start times
    stages.sort(Comparator.comparing(Stage::getStartTime));
    for (Stage stage: stages) {
      //sets currentStageID to that specific index' stage ID...
      currentStageID = stage.getStageId();
      stageIds = Arrays.copyOf(stageIds, stageIds.length + 1);
      //creates a new array with that stage ID added
      stageIds[stageIds.length - 1] = currentStageID;
    }

    return stageIds;
  }

  // Gets the length of a stage in a race, in kilometres
  //Returns: The stage's length
  public double getStageLength(int stageId) throws IDNotRecognisedException {

    Stage wantedStage = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }

    return wantedStage.getLength();
  }

  // Removes a stage and all its related data, i.e., segments and results.
  // Returns: nothing
  public void removeStageById(int stageId) throws IDNotRecognisedException {

    Stage wantedStage = null;
    StagedRace raceOfStage = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          raceOfStage = race;
          break;
        }
      }
    }

    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }

    //removing all segments and their results from the stages in the race
    for (Segment segment: wantedStage.getSegmentList()) {
      wantedStage.removeSegmentFromStage(segment.getSegmentId());
      segment.removeTimeResultsFromSegment();
    }
    raceOfStage.removeStageFromRace(wantedStage.getStageId());
    //garbage collection - if there is any object in the system not being referenced it is deleted from memory
    System.gc();
  }

  // Adds a climb segment to a stage
  // Returns: The ID of the segment created
  public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient, Double length) throws IDNotRecognisedException,
  InvalidLocationException,
  InvalidStageStateException,
  InvalidStageTypeException {

    Stage wantedStage = null;
    int segmentId = getRandomIdForSegment();

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (location > wantedStage.getLength()) {
      throw new InvalidLocationException("Location is not within bounds of the stage," + " try a location within the stage");
    }
    if ("waiting for results".equals(wantedStage.getCurrentCondition())) {
      throw new InvalidStageStateException("The stage is waiting for results, " + "you can't add a segment to the stage");
    }
    if (wantedStage.type.equals(StageType.TT)) {
      throw new InvalidStageTypeException("Time-trial stages cannot contain any segment, try another stage");
    }

    Segment segment = new Segment(segmentId, location, type, averageGradient, length);
    wantedStage.addSegment(segment);

    return segmentId;
  }

  // Adds an intermediate sprint to a stage
  // Returns: The ID of the segment created
  public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
  InvalidLocationException,
  InvalidStageStateException,
  InvalidStageTypeException {

    Stage wantedStage = null;
    int segmentId = getRandomIdForSegment();

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (location > wantedStage.getLength()) {
      throw new InvalidLocationException("Location is not within bounds of the stage, try a location within the stage");
    }
    if ("waiting for results".equals(wantedStage.getCurrentCondition())) {
      throw new InvalidStageStateException("The stage is waiting for results, you can't add a segment to the stage");
    }
    if (wantedStage.type.equals(StageType.TT)) {
      throw new InvalidStageTypeException("Time-trial stages cannot contain any segment, try another stage");
    }

    //Main implementation
    Double averageGradient = 0.0;
    Double length = 0.0;
    Segment segment = new Segment(segmentId, location, SegmentType.SPRINT, averageGradient, length);
    wantedStage.addSegment(segment);

    return segmentId;
  }

  // Removes a segment from a stage
  // Returns: nothing
  public void removeSegment(int segmentId) throws IDNotRecognisedException,
  InvalidStageStateException {

    Segment segmentToRemove = null;
    Stage stageOfSegment = null;

    //for every race in the arraylist races...
    for (StagedRace race: races) {
      //for every stage, in every race...
      for (Stage stage: race.getStageList()) {
        //for every segment in every stage...
        for (Segment segment: stage.getSegmentList()) {
          if (segment.getSegmentId().equals(segmentId)) {
            segmentToRemove = segment;
            stageOfSegment = stage;
            break;
          }
        }
      }
    }
    if (segmentToRemove == null) {
      throw new IDNotRecognisedException("Segment ID not recognised, try another Segment ID");
    }
    if ("waiting for results".equals(stageOfSegment.getCurrentCondition())) {
      throw new InvalidStageStateException("The stage is waiting for results, " + "you can't add a segment to the stage");
    }
    segmentToRemove.removeTimeResultsFromSegment();
    stageOfSegment.removeSegmentFromStage(segmentToRemove.getSegmentId());

  }

  // Concludes the preparation of a stage. After conclusion, the stage's state should be "waiting for results"
  // Returns: Nothing
  public void concludeStagePreparation(int stageId) throws IDNotRecognisedException,
  InvalidStageStateException {

    Stage wantedStage = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (wantedStage.getCurrentCondition().equals("waiting for results")) {
      throw new InvalidStageStateException("Stage has already been concluded and is waiting for results");
    }

    wantedStage.setCurrentCondition("waiting for results");

  }

  // Retrieves the list of segment (mountains and sprints) IDs of a stage
  // Returns: The list of segment IDs ordered (from first to last) by their location in the stage
  public int[] getStageSegments(int stageId) throws IDNotRecognisedException {

    int[] listOfStageSegments = new int[0];

    Stage wantedStage = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    List < Segment > segmentList = wantedStage.getSegmentList();
    segmentList.sort(Comparator.comparing(Segment::getLocation));

    for (Segment segment: segmentList) {
      listOfStageSegments = Arrays.copyOf(listOfStageSegments, listOfStageSegments.length + 1);
      listOfStageSegments[listOfStageSegments.length - 1] = segment.getSegmentId();
    }

    return listOfStageSegments;
  }

  // Creates a team with name and description
  // Returns: The ID of the created team
  public int createTeam(String name, String description) throws IllegalNameException,
  InvalidNameException {

    int teamId = getRandomIdForTeam();

    for (Team team: teams) {
      if (team.getName().equals(name)) {
        throw new cycling.IllegalNameException("Team name already exists, try inputting another name");
      }
      if (name == null) {
        throw new cycling.InvalidNameException("Team name cannot be null, try inputting a name");
      }
      if (name.length() == 0) {
        throw new cycling.InvalidNameException("Team name cannot empty, try inputting a name");
      }
      if (name.length() > 30) {
        throw new cycling.InvalidNameException("Team name is too long, try a shorter name");
      }
      if (name.contains(" ")) {
        throw new cycling.InvalidNameException("Team name cannot contain any spaces, try again");
      }
    }
    Team team = new Team(teamId, name, description);
    teams.add(team);

    return teamId;
  }

  // Removes a team from the system
  // Returns: nothing
  public void removeTeam(int teamId) throws IDNotRecognisedException {

    Team teamToRemove = null;

    for (Team team: teams) {
      if (team.getTeamId().equals(teamId)) {
        teamToRemove = team;
        break;
      }
    }
    if (teamToRemove == null) {
      throw new IDNotRecognisedException("Team ID not recognised, try another team ID");
    }

    //allowing riders and their results to still persist in the system even without a team
    for (Rider rider: teamToRemove.getRiders()) {
      rider.setTeamId(null);

      //removing the team completely
      teams.remove(teamToRemove);
      //garbage collection - if there is any object in the system not being referenced it is deleted from memory
      System.gc();

    }
  }

  // Gets the riders of a team
  // Returns: A list with riders' ID
  public int[] getTeams() {

    int[] listOfTeamIds = new int[0];

    for (Team team: teams) {
      listOfTeamIds = Arrays.copyOf(listOfTeamIds, listOfTeamIds.length + 1);
      listOfTeamIds[listOfTeamIds.length - 1] = team.getTeamId();
    }

    return listOfTeamIds;
  }

  // Gets the riders of a team
  //Returns: A list with riders' ID
  public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {

    int[] listOfTeamRiders = new int[0];
    Team wantedTeam = null;

    for (Team team: teams) {
      if (team.getTeamId().equals(teamId)) {
        wantedTeam = team;
        break;
      }
    }
    if (wantedTeam == null) {
      throw new IDNotRecognisedException("Team ID not recognised, try another team ID");
    }
    for (Rider rider: wantedTeam.getRiders()) {
      listOfTeamRiders = Arrays.copyOf(listOfTeamRiders, listOfTeamRiders.length + 1);
      listOfTeamRiders[listOfTeamRiders.length - 1] = rider.getRiderId();
    }

    return listOfTeamRiders;
  }

  //creates a rider
  //returns: the ID of the rider in the system
  public int createRider(int teamId, String name, int yearOfBirth) throws IDNotRecognisedException,
  IllegalArgumentException {

    Team teamToAddRider = null;
    int riderId = getRandomIdForRider();

    for (Team team: teams) {
      if (team.getTeamId().equals(teamId)) {
        teamToAddRider = team;
        break;
      }
    }
    if (teamToAddRider == null) {
      throw new IDNotRecognisedException("Team ID not recognised, try another team ID");
    }
    if (name == null) {
      throw new IllegalArgumentException("Rider name cannot be null, try inputting a name");
    }
    if (yearOfBirth < 1900) {
      throw new IllegalArgumentException("Year of Birth cannot be less than 1900, try again");
    }

    Rider rider = new Rider(riderId, teamId, name, yearOfBirth);
    riders.add(rider);
    teamToAddRider.addRider(rider);

    return riderId;
  }

  // Removes a rider from the system. When a rider is removed from the platform, all of its results should be also removed. Race results must be updated.
  // Returns: Nothing
  public void removeRider(int riderId) throws IDNotRecognisedException {

    Rider riderToRemove = null;
    Team teamToRemoveRider = null;

    for (Rider rider: riders) {
      if (rider.getRiderId().equals(riderId)) {
        riderToRemove = rider;
        break;
      }
    }
    if (riderToRemove == null) {
      throw new IDNotRecognisedException("Rider not recognised, try another rider ID");
    }

    //removing all the rider results of a rider in each segments
    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        for (Segment segment: stage.getSegmentList()) {
          if (segment.getRiderTimes().containsKey(riderToRemove)) {
            segment.removeRiderTimeResult(riderToRemove);
          }
        }
      }
    }

    //removing all the rider results in each stage
    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.stageTimes.containsKey(riderToRemove)) {
          stage.removeRiderStageTimesFromStage(riderToRemove);
        }
        if (stage.getRiderPoints().containsKey(riderToRemove)) {
          stage.removeSpecificRiderPointsFromStage(riderToRemove);
        }
      }
    }

    //removing the rider from their team also
    Integer riderTeamId = riderToRemove.getTeamId();
    for (Team team: teams) {
      if (riderTeamId.equals(team.getTeamId())) {
        teamToRemoveRider = team;
      }
    }
    teamToRemoveRider.removeRider(riderToRemove.getRiderId());
    riders.remove(riderToRemove);
  }

  // Records the times of a rider in a stage
  // Returns: Nothing
  public void registerRiderResultsInStage(int stageId, int riderId, LocalTime...checkpoints) throws
  IDNotRecognisedException,
  DuplicatedResultException,
  InvalidCheckpointsException,
  InvalidStageStateException {

    Rider wantedRider = null;
    Stage wantedStage = null;
    boolean duplicateResult = false;

    for (Rider rider: riders) {
      if (rider.getRiderId().equals(riderId)) {
        wantedRider = rider;
        break;
      }
    }
    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage.getStageTimes().containsKey(wantedRider)) {
      duplicateResult = true;
    }

    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (wantedRider == null) {
      throw new IDNotRecognisedException("Rider ID not recognised, try another rider ID");
    }
    if (duplicateResult == true) {
      throw new DuplicatedResultException("Rider result has already been inputted");
    }
    if (checkpoints.length + 2 != wantedStage.getSegmentList().size()) {
      throw new InvalidCheckpointsException("Number of segments doesn't match number of checkpoints");
    }
    if (!wantedStage.getCurrentCondition().equals("waiting for results")) {
      throw new InvalidStageStateException("Results can only be added to a stage while it is 'waiting for results'");
    }

    ArrayList < Long > listOfCheckpoints = new ArrayList < >();
    for (LocalTime localTime: checkpoints) {
      listOfCheckpoints.add((long)((localTime.getHour() * 3600000) + (localTime.getMinute() * 60000) + (localTime.getSecond() * 1000) + (localTime.getNano() / 1000000)));
    }
    ArrayList < Long > segmentFinishTime = new ArrayList < >();
    for (int i = 0; i < listOfCheckpoints.size() - 2; i = i + 1) {
      segmentFinishTime.add(listOfCheckpoints.get(i + 1));
    }
    Long startTime = listOfCheckpoints.get(0);
    Long finishTime = listOfCheckpoints.get(listOfCheckpoints.size() - 1);
    Long totalElapsedTime = finishTime - startTime;

    //Registering time results in segments
    ArrayList < Segment > newSegmentList = wantedStage.getSegmentList();

    //Registering elapsed time results in the stage
    wantedStage.getStageTimes().put(wantedRider, totalElapsedTime);

    for (int i = 0; i < newSegmentList.size() - 1; i = i + 1) {
      newSegmentList.get(i).addRiderTimes(wantedRider, segmentFinishTime.get(i));
    }

    wantedStage.setSegmentList(newSegmentList);
  }

  // Gets the times of a rider in a stage
  // Returns: The array of times at which the rider reached each of the segments of the stage and the total elapsed time. The elapsed time is the difference between the finish time and the start time. Return an empty array if there is no result registered for the rider in the stage.
  public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

    Rider wantedRider = null;
    Stage wantedStage = null;

    ArrayList < Long > listOfResultsMilliseconds = new ArrayList < >();
    LocalTime[] listOfResultsLocalTime = new LocalTime[0];

    for (Rider rider: riders) {
      if (rider.getRiderId().equals(riderId)) {
        wantedRider = rider;
        break;
      }
    }
    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (wantedRider == null) {
      throw new IDNotRecognisedException("Rider ID not recognised in stage, try another rider ID");
    }

    //Adding  times at which the rider reached each of the segments of the stage
    for (Segment segment: wantedStage.getSegmentList()) {
      for (Map.Entry < Rider, Long > riderTimeKVP: segment.getRiderTimes().entrySet()) {
        if (riderTimeKVP.getKey().equals(wantedRider)) {
          Long wantedIndex = riderTimeKVP.getValue();
          listOfResultsMilliseconds.add(wantedIndex);
        }
      }
    }
    //Adding the total elapsed time
    Long finishTimeInArray = wantedStage.getStageTimes().get(wantedRider);
    listOfResultsMilliseconds.add(finishTimeInArray);

    //converting all results to LocalTime format
    for (Long listOfResultsMillisecond: listOfResultsMilliseconds) {
      LocalTime tempIndex = convertMillisecondsToLocaltime(listOfResultsMillisecond);
      listOfResultsLocalTime = Arrays.copyOf(listOfResultsLocalTime, listOfResultsLocalTime.length + 1);
      listOfResultsLocalTime[listOfResultsLocalTime.length - 1] = tempIndex;
    }

    // Each value is time split of each segment and final value is elapsed time
    return listOfResultsLocalTime;
  }

  // Gets adjusted elapsed time for a rider in a stage
  // Returns: The adjusted elapsed time for the rider in the stage. Return an empty array if there is no result registered for the rider in the stage.
  public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {

    Stage wantedStage = null;
    Rider wantedRider = null;

    Long adjustedTime;
    long timeBetweenRiders;
    Long wantedAdjustedTime = null;

    //a list of the group of riders who will have their times adjusted to the same time
    List < Map.Entry < Rider,
    Long >> riderTimesToBeAdjusted = new ArrayList < >();
    List < Map.Entry < Rider,
    Long >> listOfElapsedTime;
    HashMap < Rider,
    Long > ridersTotalElapsedTime = new HashMap < >();
    HashMap < Rider,
    Long > adjustedElapsedTime = new HashMap < >();

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        for (Rider rider: stage.getStageTimes().keySet()) {
          if (rider.getRiderId().equals(riderId)) {
            wantedRider = rider;
            break;
          }
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (wantedRider == null) {
      throw new IDNotRecognisedException("Rider ID not recognised within this stage, try another rider ID");
    }

    listOfElapsedTime = Collections.list(Collections.enumeration(wantedStage.getStageTimes().entrySet()));
    for (Map.Entry < Rider, Long > riderTimeKVP: listOfElapsedTime) {
      if (ridersTotalElapsedTime.containsKey(riderTimeKVP.getKey())) {
        ridersTotalElapsedTime.put(riderTimeKVP.getKey(), ridersTotalElapsedTime.get(riderTimeKVP.getKey()) + (riderTimeKVP.getValue()));
      } else {
        ridersTotalElapsedTime.put(riderTimeKVP.getKey(), riderTimeKVP.getValue());
      }
    }

    //copying the HashMap of riders and their time to a list
    List < Map.Entry < Rider,
    Long >> stageTimes = Collections.list(Collections.enumeration(ridersTotalElapsedTime.entrySet()));
    //sorting the list of stageTimes so the shortest time is first
    stageTimes.sort(Map.Entry.comparingByValue());

    //riderTimesToBeAdjusted.add(stageTimes.get(0).getKey().getRiderId());
    //this is the time that all riders will get their time adjusted to
    adjustedTime = stageTimes.get(0).getValue();
    adjustedElapsedTime.put(stageTimes.get(0).getKey(), stageTimes.get(0).getValue());

    for (int i = 0; i < stageTimes.size() - 1; i++) {
      //getting the difference between the time of two riders
      timeBetweenRiders = ((stageTimes.get(i + 1).getValue() - (stageTimes.get(i).getValue())));
      if (timeBetweenRiders < 1000) {
        riderTimesToBeAdjusted.add(stageTimes.get(i + 1));
      }
      for (Map.Entry < Rider, Long > preAdjustedTime: riderTimesToBeAdjusted) {
        preAdjustedTime.setValue(adjustedTime);
        adjustedElapsedTime.put(preAdjustedTime.getKey(), preAdjustedTime.getValue());
      }
      //flushes out / resets the arraylist
      riderTimesToBeAdjusted = new ArrayList < >();
      //will begin to create a new group of riders who need their time adjusted
      adjustedTime = stageTimes.get(i + 1).getValue();
      adjustedElapsedTime.put(stageTimes.get(i + 1).getKey(), stageTimes.get(i + 1).getValue());
    }
    //getting the adjusted time of the specified rider
    for (Map.Entry < Rider, Long > rider: adjustedElapsedTime.entrySet()) {
      if (rider.getKey().equals(wantedRider)) {
        wantedAdjustedTime = rider.getValue();
      }
    }
    return convertMillisecondsToLocaltime(wantedAdjustedTime);
  }

  // Removes the stage results from the rider
  // Returns: Nothing
  public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    Stage wantedStage = null;
    Rider wantedRider = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedRace = race;
          wantedStage = stage;
        }
      }
    }
    for (Rider rider: riders) {
      if (rider.getRiderId().equals(riderId)) {
        wantedRider = rider;
      }
    }
    if (wantedRider == null) {
      throw new IDNotRecognisedException("Rider ID not recognised, try another rider ID");
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    //removing all the rider results of a rider in each segments
    for (Segment segment: wantedStage.getSegmentList()) {
      segment.removeRiderTimeResult(wantedRider);
    }

    //removing all the rider results in each stage
    for (Stage stage: wantedRace.getStageList()) {
      stage.removeRiderStageTimesFromStage(wantedRider);
      stage.removeSpecificRiderPointsFromStage(wantedRider);
    }
  }

  // Get the riders finished position in a a stage
  // Returns: a list of riders ID sorted by their elapsed time. An empty list if there is no result for the stage
  public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {

    Stage wantedStage = null;

    List < Map.Entry < Rider,
    Long >> listOfElapsedTime;
    HashMap < Rider,
    Long > ridersTotalElapsedTimes = new HashMap < >();

    int[] listOfSortedStageTimes;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }

    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }

    listOfElapsedTime = Collections.list(Collections.enumeration(wantedStage.getStageTimes().entrySet()));
    for (Map.Entry < Rider, Long > riderTimeKVP: listOfElapsedTime) {
      if (ridersTotalElapsedTimes.containsKey(riderTimeKVP.getKey())) {
        ridersTotalElapsedTimes.put(riderTimeKVP.getKey(), ridersTotalElapsedTimes.get(riderTimeKVP.getKey()) + (riderTimeKVP.getValue()));
      } else {
        ridersTotalElapsedTimes.put(riderTimeKVP.getKey(), riderTimeKVP.getValue());
      }
    }

    //gets the set of all key:value pair entries in the hashmap - stores them in a list
    List < Map.Entry < Rider,
    Long >> sortedStageTimes = Collections.list(Collections.enumeration(ridersTotalElapsedTimes.entrySet()));
    //sorts the entries (which are in ascending order by default) by their values which is the score
    sortedStageTimes.sort(Map.Entry.comparingByValue());

    //setting the size of the array to the number of riders in the array
    listOfSortedStageTimes = new int[sortedStageTimes.size()];

    for (int i = 0; i < sortedStageTimes.size(); i = i + 1) {
      listOfSortedStageTimes[i] = sortedStageTimes.get(i).getKey().getRiderId();
    }
    return listOfSortedStageTimes;
  }

  // Gets the adjusted elapsed times of riders in a stage
  // The ranked list of adjusted elapsed times sorted by their finish time. An empty list if there is no result for the stage.
  public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {

    Stage wantedStage = null;

    Long adjustedTime;
    long timeBetweenRiders;

    Long[] listOfRidersAdjustedElapsedTimeMilliseconds = new Long[0];
    LocalTime[] listOfRidersAdjustedElapsedTime = new LocalTime[0];

    //a list of the group of riders who will have their times adjusted to the same time
    List < Map.Entry < Rider,
    Long >> riderTimesToBeAdjusted = new ArrayList < >();
    List < Map.Entry < Rider,
    Long >> listOfElapsedTime;
    HashMap < Rider,
    Long > ridersTotalElapsedTime = new HashMap < >();
    HashMap < Rider,
    Long > adjustedElapsedTime = new HashMap < >();

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }

    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }

    listOfElapsedTime = Collections.list(Collections.enumeration(wantedStage.getStageTimes().entrySet()));
    for (Map.Entry < Rider, Long > riderTimeKVP: listOfElapsedTime) {
      if (ridersTotalElapsedTime.containsKey(riderTimeKVP.getKey())) {
        ridersTotalElapsedTime.put(riderTimeKVP.getKey(), ridersTotalElapsedTime.get(riderTimeKVP.getKey()) + (riderTimeKVP.getValue()));
      } else {
        ridersTotalElapsedTime.put(riderTimeKVP.getKey(), riderTimeKVP.getValue());
      }
    }

    //copying the HashMap of riders and their time to a list
    List < Map.Entry < Rider,
    Long >> stageTimes = Collections.list(Collections.enumeration(ridersTotalElapsedTime.entrySet()));
    //sorting the list of stageTimes so the shortest time is first
    stageTimes.sort(Map.Entry.comparingByValue());

    //this is the time that all riders will get their time adjusted to
    adjustedTime = stageTimes.get(0).getValue();

    for (int i = 0; i < stageTimes.size() - 1; i++) {
      adjustedElapsedTime.put(stageTimes.get(i).getKey(), stageTimes.get(i).getValue());
      //getting the difference between the time of two riders
      timeBetweenRiders = ((stageTimes.get(i + 1).getValue() - (stageTimes.get(i).getValue())));
      if (timeBetweenRiders < 1000) {
        riderTimesToBeAdjusted.add(stageTimes.get(i + 1));
      }
      for (Map.Entry < Rider, Long > preAdjustedTime: riderTimesToBeAdjusted) {
        preAdjustedTime.setValue(adjustedTime);
        adjustedElapsedTime.put(preAdjustedTime.getKey(), preAdjustedTime.getValue());
      }
      //flushes out / resets the arraylist
      riderTimesToBeAdjusted = new ArrayList < >();
      //will begin to create a new group of riders who need their time adjusted
      adjustedTime = stageTimes.get(i + 1).getValue();
      adjustedElapsedTime.put(stageTimes.get(i + 1).getKey(), stageTimes.get(i + 1).getValue());
    }

    for (Map.Entry < Rider, Long > rider: adjustedElapsedTime.entrySet()) {
      listOfRidersAdjustedElapsedTimeMilliseconds = Arrays.copyOf(listOfRidersAdjustedElapsedTimeMilliseconds, listOfRidersAdjustedElapsedTimeMilliseconds.length + 1);
      listOfRidersAdjustedElapsedTimeMilliseconds[listOfRidersAdjustedElapsedTimeMilliseconds.length - 1] = rider.getValue();
    }

    //sorting the elements in the list by descending order
    Long temp;
    for (int i = 1; i < listOfRidersAdjustedElapsedTimeMilliseconds.length; i++) {
      for (int j = i; j > 0; j--) {
        if (listOfRidersAdjustedElapsedTimeMilliseconds[j] < listOfRidersAdjustedElapsedTimeMilliseconds[j - 1]) {
          temp = listOfRidersAdjustedElapsedTimeMilliseconds[j];
          listOfRidersAdjustedElapsedTimeMilliseconds[j] = listOfRidersAdjustedElapsedTimeMilliseconds[j - 1];
          listOfRidersAdjustedElapsedTimeMilliseconds[j - 1] = temp;
        }
      }
    }

    for (Long TimeInMilliseconds: listOfRidersAdjustedElapsedTimeMilliseconds) {
      LocalTime currentIndex = convertMillisecondsToLocaltime(TimeInMilliseconds);
      listOfRidersAdjustedElapsedTime = Arrays.copyOf(listOfRidersAdjustedElapsedTime, listOfRidersAdjustedElapsedTime.length + 1);
      listOfRidersAdjustedElapsedTime[listOfRidersAdjustedElapsedTime.length - 1] = currentIndex;
    }

    assert(listOfRidersAdjustedElapsedTime.equals(getRidersRankInStage(stageId))) : "Output does not match riders' rank in stage";

    return listOfRidersAdjustedElapsedTime;

  }

  // Gets the number of points obtained by each rider in a stage
  // The ranked list of points each riders received in the stage, sorted by their elapsed time. An empty list if there is no result for the stage.
  public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {

    int[] flatStagePoints = {
      50,
      30,
      20,
      18,
      16,
      14,
      12,
      10,
      8,
      7,
      6,
      5,
      4,
      3,
      2
    };
    int[] mediumMountainStagePoints = {
      30,
      25,
      22,
      19,
      17,
      15,
      13,
      11,
      9,
      7,
      6,
      5,
      4,
      3,
      2
    };
    int[] highMountainOrTimeTrialPoints = {
      20,
      17,
      15,
      13,
      11,
      10,
      9,
      8,
      7,
      6,
      5,
      4,
      3,
      2,
      1
    };

    int[] pointsArray = new int[0];
    int[] pointsArrayToReturn = new int[0];
    HashMap < Rider,
    Integer > riderPoints = new HashMap < >();

    Stage wantedStage = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }
    if (wantedStage.type.equals(StageType.FLAT)) {
      pointsArray = flatStagePoints;
    } else if (wantedStage.type.equals(StageType.MEDIUM_MOUNTAIN)) {
      pointsArray = mediumMountainStagePoints;
    } else if (wantedStage.type.equals(StageType.HIGH_MOUNTAIN)) {
      pointsArray = highMountainOrTimeTrialPoints;
    } else if (wantedStage.type.equals(StageType.TT)) {
      pointsArray = highMountainOrTimeTrialPoints;
    }

    List < Map.Entry < Rider,
    Long >> riderTimes = Collections.list(Collections.enumeration(wantedStage.getStageTimes().entrySet()));
    riderTimes.sort(Map.Entry.comparingByValue());
    for (int i = 0; i < riderTimes.size(); i = i + 1) {
      pointsArrayToReturn = Arrays.copyOf(pointsArrayToReturn, pointsArrayToReturn.length + 1);
      if (i < pointsArray.length) {
        pointsArrayToReturn[i] = pointsArray[i];
      } else {
        pointsArrayToReturn[i] = 0;
      }
      riderPoints.put(riderTimes.get(i).getKey(), pointsArrayToReturn[i]);
    }

    wantedStage.setRiderPoints(riderPoints);

    assert(pointsArrayToReturn == getRidersRankInStage(stageId)) : "Output does not match riders' rank in stage";

    return pointsArrayToReturn;
  }

  // Gets the number of mountain points obtained by each rider in a stage
  // Returns: The ranked list of mountain points each riders received in the stage, sorted by their finish time. An empty list if there is no result for the stage
  public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {

    int[] hcPoints = {
      20,
      15,
      12,
      10,
      8,
      6,
      4,
      2
    };
    int[] oneCPoints = {
      10,
      8,
      6,
      4,
      2,
      1
    };
    int[] twoCPoints = {
      5,
      3,
      2,
      1
    };
    int[] threeCPoints = {
      2,
      1
    };
    int[] fourCPoints = {
      1
    };

    int[] pointsArray = new int[0];

    HashMap < Rider,
    Integer > riderPointsHashMap = new HashMap < >();
    int[] rankedListOfMountainPoints = new int[0];

    Stage wantedStage = null;

    for (StagedRace race: races) {
      for (Stage stage: race.getStageList()) {
        if (stage.getStageId().equals(stageId)) {
          wantedStage = stage;
          break;
        }
      }
    }
    if (wantedStage == null) {
      throw new IDNotRecognisedException("Stage ID not recognised, try another stage ID");
    }

    for (Segment segment: wantedStage.getSegmentList()) {

      if (segment.getType().equals(SegmentType.HC)) {
        pointsArray = hcPoints;
      }
      if (segment.getType().equals(SegmentType.C1)) {
        pointsArray = oneCPoints;
      }
      if (segment.getType().equals(SegmentType.C2)) {
        pointsArray = twoCPoints;
      }
      if (segment.getType().equals(SegmentType.C3)) {
        pointsArray = threeCPoints;
      }
      if (segment.getType().equals(SegmentType.C4)) {
        pointsArray = fourCPoints;
      }
      List < Map.Entry < Rider,
      Long >> riderTimes = Collections.list(Collections.enumeration(segment.getRiderTimes().entrySet()));
      riderTimes.sort(Map.Entry.comparingByValue());
      for (int i = 0; i < riderTimes.size(); i = i + 1) {
        int pointsToAssign;
        if (i < pointsArray.length) {
          pointsToAssign = pointsArray[i];
        } else {
          pointsToAssign = 0;
        }

        if (riderPointsHashMap.containsKey(riderTimes.get(i).getKey())) {
          riderPointsHashMap.put(riderTimes.get(i).getKey(), riderPointsHashMap.get(riderTimes.get(i).getKey()) + pointsToAssign);
        } else {
          riderPointsHashMap.put(riderTimes.get(i).getKey(), pointsToAssign);
        }
      }
      List < Map.Entry < Rider,
      Integer >> sortedMountainPoints = Collections.list(Collections.enumeration(riderPointsHashMap.entrySet()));
      sortedMountainPoints.sort((Map.Entry.comparingByValue()));

      for (Map.Entry < Rider, Integer > mountainPoint: sortedMountainPoints) {
        rankedListOfMountainPoints = Arrays.copyOf(rankedListOfMountainPoints, rankedListOfMountainPoints.length - 1);
        rankedListOfMountainPoints[rankedListOfMountainPoints.length - 1] = mountainPoint.getValue();
      }
    }
    assert(rankedListOfMountainPoints == getRidersRankInStage(stageId)) : "Output does not match riders' rank in stage";
    return rankedListOfMountainPoints;
  }

  // Removes all contents of cycling portal
  // Returns: nothing
  public void eraseCyclingPortal() {

    teams = new ArrayList < >();
    riders = new ArrayList < >();
    races = new ArrayList < >();

    System.gc();
  }

  // Saves this Cycling Portal contents into a serialised file, with the filename given in the argument
  // Returns: nothing
  public void saveCyclingPortal(String filename) throws IOException {

    FileOutputStream filenameStream = new FileOutputStream(filename + "ser");
    ObjectOutputStream out = new ObjectOutputStream(filenameStream);
    out.writeObject(teams);
    out.writeObject(riders);
    out.writeObject(races);
    out.close();
    filenameStream.close();
  }

  // Loads this Cycling Portal's contents from a serialised file, with the filename given in the argument
  // Returns: nothing
  public void loadCyclingPortal(String filename) throws IOException,
  ClassNotFoundException {

    FileInputStream filenameStream = new FileInputStream(filename + "ser");
    ObjectInputStream in =new ObjectInputStream(filenameStream);
    teams = (ArrayList < Team > ) in .readObject();
    riders = (ArrayList < Rider > ) in .readObject();
    races = (ArrayList < StagedRace > ) in .readObject(); in .close();
    filenameStream.close();
  }

  // Removes the race and all its related information, i.e., stages, segments, and results
  // Returns: Nothing
  public void removeRaceByName(String name) throws NameNotRecognisedException {

    StagedRace raceToRemove = null;

    for (StagedRace race: races) {
      if (race.getName().equals(name)) {
        raceToRemove = race;
        break;
      }
    }
    if (raceToRemove == null) {
      throw new NameNotRecognisedException("Race not recognised, try another race name");
    }
    //removing all segments and their results from the stages in the race
    for (Stage stage: raceToRemove.getStageList()) {
      for (Segment segment: stage.getSegmentList()) {
        segment.removeTimeResultsFromSegment();
        stage.removeSegmentFromStage(segment.getSegmentId());
      }
    }
    //removing all the stages from the race
    for (Stage stage: raceToRemove.getStageList()) {
      stage.removeStageTimesFromStage();
      stage.removeRiderPointsFromStage();
      raceToRemove.removeStageFromRace(stage.getStageId());
    }
    races.remove(raceToRemove);
    //garbage collection - if there is any object in the system not being referenced it is deleted from memory
    System.gc();
  }@Override
  // Gets the general classification times of riders in a race
  // Returns: list of riders' times sorted by the sum of their adjusted elapsed times in all stages of the race. An empty list if there is no result for any stage in the race.
  public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;

    LocalTime[] generalClassificationTimes = new LocalTime[0];

    Long adjustedTime;
    long timeBetweenRiders;

    HashMap < Rider,
    Long > adjustedElapsedTime = new HashMap < >();

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    // sum of all elapsed times
    HashMap < Rider,
    Long > totalAdjustedTimes = new HashMap < >();
    List < Map.Entry < Rider,
    Long >> stageTimes;

    for (Stage stage: wantedRace.getStageList()) {
      List < Map.Entry < Rider,
      Long >> riderTimesToBeAdjusted = new ArrayList < >();
      // stage times for each stage
      stageTimes = Collections.list(Collections.enumeration(stage.getStageTimes().entrySet()));
      stageTimes.sort(Map.Entry.comparingByValue());
      //this is the time that all riders will get their time adjusted to
      adjustedTime = stageTimes.get(0).getValue();

      for (int i = 0; i < stageTimes.size() - 1; i++) {
        adjustedElapsedTime.put(stageTimes.get(i).getKey(), stageTimes.get(i).getValue());
        //getting the difference between the time of two riders
        timeBetweenRiders = ((stageTimes.get(i + 1).getValue() - (stageTimes.get(i).getValue())));
        if (timeBetweenRiders < 1000) {
          riderTimesToBeAdjusted.add(stageTimes.get(i + 1));
        }
        for (Map.Entry < Rider, Long > preAdjustedTime: riderTimesToBeAdjusted) {
          preAdjustedTime.setValue(adjustedTime);
          adjustedElapsedTime.put(preAdjustedTime.getKey(), preAdjustedTime.getValue());
        }
        //flushes out / resets the arraylist
        riderTimesToBeAdjusted = new ArrayList < >();
        //will begin to create a new group of riders who need their time adjusted
        adjustedTime = stageTimes.get(i + 1).getValue();
      }

      // adds the adjusted times for this stage to the total adjusted times hashmap
      for (Map.Entry < Rider, Long > adjustedRiderTime: adjustedElapsedTime.entrySet()) {
        if (totalAdjustedTimes.containsKey(adjustedRiderTime.getKey())) {
          totalAdjustedTimes.put(adjustedRiderTime.getKey(), totalAdjustedTimes.get(adjustedRiderTime.getKey()) + adjustedRiderTime.getValue());
        } else {
          totalAdjustedTimes.put(adjustedRiderTime.getKey(), adjustedRiderTime.getValue());
        }
      }
    }
    ArrayList < Rider > sumOfAdjustedTimes = new ArrayList < >();
    for (Rider rider: totalAdjustedTimes.keySet()) {
      sumOfAdjustedTimes.add(rider);
    }

    wantedRace.setSumOfAdjustedTimes(sumOfAdjustedTimes);

    for (Long adjustedTimeSum: totalAdjustedTimes.values()) {
      generalClassificationTimes = Arrays.copyOf(generalClassificationTimes, generalClassificationTimes.length + 1);
      generalClassificationTimes[generalClassificationTimes.length - 1] = convertMillisecondsToLocaltime(adjustedTimeSum);
    }

    assert(generalClassificationTimes.equals(getRidersGeneralClassificationRank(raceId))) : "Output does not match up with riders' general classification rank";

    return generalClassificationTimes;
  }

  // Gets the overall points of riders in a race
  // Returns: A list of riders' points (i.e., the sum of their points in all stages of the race), sorted by the total elapsed time. An empty list if there is no result for any stage in the race.
  public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    HashMap < Rider,
    Integer > totalPointsPerRider = new HashMap < >();
    int[] listOfRiderPointsInRace = new int[0];

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    for (Stage stage: wantedRace.getStageList()) {
      if (stage.getRiderPoints() != null) {
        List < Map.Entry < Rider,
        Integer >> tempRiderPointsPerStage = Collections.list(Collections.enumeration(stage.getRiderPoints().entrySet()));
        for (Map.Entry < Rider, Integer > riderPointsKVP: tempRiderPointsPerStage) {
          if (totalPointsPerRider.containsKey(riderPointsKVP.getKey())) {
            totalPointsPerRider.put(riderPointsKVP.getKey(), totalPointsPerRider.get(riderPointsKVP.getKey()) + (riderPointsKVP.getValue()));
          } else {
            totalPointsPerRider.put(riderPointsKVP.getKey(), riderPointsKVP.getValue());
          }
        }
      }
    }

    for (Integer riderPoint: totalPointsPerRider.values()) {
      listOfRiderPointsInRace = Arrays.copyOf(listOfRiderPointsInRace, listOfRiderPointsInRace.length + 1);
      listOfRiderPointsInRace[listOfRiderPointsInRace.length - 1] = riderPoint;
    }

    //sorting the elements in the list by descending order
    int temp;
    for (int i = 1; i < listOfRiderPointsInRace.length; i++) {
      for (int j = i; j > 0; j--) {
        if (listOfRiderPointsInRace[j] > listOfRiderPointsInRace[j - 1]) {
          temp = listOfRiderPointsInRace[j];
          listOfRiderPointsInRace[j] = listOfRiderPointsInRace[j - 1];
          listOfRiderPointsInRace[j - 1] = temp;
        }
      }
    }
    assert(listOfRiderPointsInRace == getRidersGeneralClassificationRank(raceId)) : "Rider points output does not match general classification rank";
    return listOfRiderPointsInRace;
  }

  // Gets the overall mountain points of riders in a race
  // Returns: list of riders' mountain points (i.e., the sum of their mountain points in all stages of the race), sorted by the total elapsed time. An empty list if there is no result for any stage in the race.
  public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    HashMap < Rider,
    Integer > totalMountainPointsPerRider = new HashMap < >();
    int[] listOfRidersMountainPointsInRace = new int[0];

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    for (Stage stage: wantedRace.getStageList()) {
      if (stage.type.equals(StageType.MEDIUM_MOUNTAIN) || stage.type.equals(StageType.HIGH_MOUNTAIN)) {
        if (stage.getRiderPoints() != null) {
          List < Map.Entry < Rider,
          Integer >> tempRiderPointsPerStage = Collections.list(Collections.enumeration(stage.getRiderPoints().entrySet()));
          for (Map.Entry < Rider, Integer > riderPointsKVP: tempRiderPointsPerStage) {
            if (totalMountainPointsPerRider.containsKey(riderPointsKVP.getKey())) {
              totalMountainPointsPerRider.put(riderPointsKVP.getKey(), totalMountainPointsPerRider.get(riderPointsKVP.getKey()) + (riderPointsKVP.getValue()));
            } else {
              totalMountainPointsPerRider.put(riderPointsKVP.getKey(), riderPointsKVP.getValue());
            }
          }
        }
      }
    }
    for (Integer riderPoint: totalMountainPointsPerRider.values()) {
      listOfRidersMountainPointsInRace = Arrays.copyOf(listOfRidersMountainPointsInRace, listOfRidersMountainPointsInRace.length + 1);
      listOfRidersMountainPointsInRace[listOfRidersMountainPointsInRace.length - 1] = riderPoint;
    }

    //sorting the elements in the list by descending order
    int temp;
    for (int i = 1; i < listOfRidersMountainPointsInRace.length; i++) {
      for (int j = i; j > 0; j--) {
        if (listOfRidersMountainPointsInRace[j] > listOfRidersMountainPointsInRace[j - 1]) {
          temp = listOfRidersMountainPointsInRace[j];
          listOfRidersMountainPointsInRace[j] = listOfRidersMountainPointsInRace[j - 1];
          listOfRidersMountainPointsInRace[j - 1] = temp;
        }
      }
    }

    assert(listOfRidersMountainPointsInRace == getRidersGeneralClassificationRank(raceId)) : "Rider points output does not match general classification rank";

    return listOfRidersMountainPointsInRace;
  }

  // Gets the general classification rank of riders in a race
  // Returns: A ranked list of riders' IDs sorted ascending by the sum of their adjusted elapsed times in all stages of the race. That is, the first in this list is the winner (least time). An empty list if there is no result for any stage in the race.
  public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    int[] listOfRiderGeneralRankInRace = new int[0];

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }
    for (Rider rider: wantedRace.getSumOfAdjustedTimes()) {
      listOfRiderGeneralRankInRace = Arrays.copyOf(listOfRiderGeneralRankInRace, listOfRiderGeneralRankInRace.length + 1);
      listOfRiderGeneralRankInRace[listOfRiderGeneralRankInRace.length - 1] = rider.getRiderId();
    }

    return listOfRiderGeneralRankInRace;
  }

  // Gets the ranked list of riders based on the points classification in a race
  // Returns: A ranked list of riders' IDs sorted descending by the sum of their points in all stages of the race. That is, the first in this list is the winner (more points)
  public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    HashMap < Rider,
    Integer > totalPointsPerRider = new HashMap < >();
    int[] listOfRiderRankInRace = new int[0];

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    for (Stage stage: wantedRace.getStageList()) {
      if (stage.getRiderPoints() != null) {
        List < Map.Entry < Rider,
        Integer >> tempRiderPointsPerStage = Collections.list(Collections.enumeration(stage.getRiderPoints().entrySet()));
        for (Map.Entry < Rider, Integer > riderPointsKVP: tempRiderPointsPerStage) {
          if (totalPointsPerRider.containsKey(riderPointsKVP.getKey())) {
            totalPointsPerRider.put(riderPointsKVP.getKey(), totalPointsPerRider.get(riderPointsKVP.getKey()) + (riderPointsKVP.getValue()));
          } else {
            totalPointsPerRider.put(riderPointsKVP.getKey(), riderPointsKVP.getValue());
          }
        }
      }
    }
    List < Map.Entry < Rider,
    Integer >> sortedTotalPoints = Collections.list(Collections.enumeration(totalPointsPerRider.entrySet()));
    sortedTotalPoints.sort(Map.Entry.comparingByValue());
    Collections.reverse(sortedTotalPoints);

    for (Map.Entry < Rider, Integer > rider: sortedTotalPoints) {
      listOfRiderRankInRace = Arrays.copyOf(listOfRiderRankInRace, listOfRiderRankInRace.length + 1);
      listOfRiderRankInRace[listOfRiderRankInRace.length - 1] = rider.getKey().getRiderId();
    }

    return listOfRiderRankInRace;
  }

  // Gets the ranked list of riders based on the mountain classification in a race
  // Returns: A ranked list of riders' IDs sorted descending by the sum of their mountain points in all stages of the race. That is, the first in this list is the winner (more points)
  public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {

    StagedRace wantedRace = null;
    HashMap < Rider,
    Integer > totalPointsPerRider = new HashMap < >();
    int[] listOfRiderMountainRankInRace = new int[0];

    for (StagedRace race: races) {
      if (race.getRaceId().equals(raceId)) {
        wantedRace = race;
        break;
      }
    }
    if (wantedRace == null) {
      throw new IDNotRecognisedException("Race ID not recognised, try another race ID");
    }

    for (Stage stage: wantedRace.getStageList()) {
      if (stage.type.equals(StageType.MEDIUM_MOUNTAIN) || stage.type.equals(StageType.HIGH_MOUNTAIN)) {
        if (stage.getRiderPoints() != null) {
          List < Map.Entry < Rider,
          Integer >> tempRiderPointsPerStage = Collections.list(Collections.enumeration(stage.getRiderPoints().entrySet()));
          for (Map.Entry < Rider, Integer > riderPointsKVP: tempRiderPointsPerStage) {
            if (totalPointsPerRider.containsKey(riderPointsKVP.getKey())) {
              totalPointsPerRider.put(riderPointsKVP.getKey(), totalPointsPerRider.get(riderPointsKVP.getKey()) + (riderPointsKVP.getValue()));
            } else {
              totalPointsPerRider.put(riderPointsKVP.getKey(), riderPointsKVP.getValue());
            }
          }
        }
      }
    }
    List < Map.Entry < Rider,
    Integer >> sortedTotalPoints = Collections.list(Collections.enumeration(totalPointsPerRider.entrySet()));
    sortedTotalPoints.sort(Map.Entry.comparingByValue());
    Collections.reverse(sortedTotalPoints);

    for (Map.Entry < Rider, Integer > rider: sortedTotalPoints) {
      listOfRiderMountainRankInRace = Arrays.copyOf(listOfRiderMountainRankInRace, listOfRiderMountainRankInRace.length + 1);
      listOfRiderMountainRankInRace[listOfRiderMountainRankInRace.length - 1] = rider.getKey().getRiderId();
    }

    return listOfRiderMountainRankInRace;
  }
}