
# Cycling Race Management
Cycling Race Management Back-End Package powered by object-oriented programming, made for ECM1410 Object-Oriented Programming coursework at Exeter University

## 👨🏽‍💻 Repository
- [Github Repo](https://github.com/joshfinney/Cycling-Race-Management)

## 👨🏽‍🎓 Author
- [@joshfinney](https://github.com/joshfinney)
- Version: 1.0

## 📰 Configuration
This package will only run with the appropriate front-end application, which was not provided to us.

1. Open a command prompt.
2. Go to the directory where you have your .java files
3. Create a directory build
4. Run java compilation from the command line: ```javac -d ./build *.java```
5. If there are no errors in the build directory, you should have your class tree. Then move to the build directory and run the following command: ```jar cvf YourJar.jar *```
6. For adding manifest check jar command line switches
    
## ⛔️ Project structure
```
Cycling-Race-Management/
├── cycling/
│   ├── CyclingPortal.java
│   ├── CyclingPortalInterface.java
│   ├── DuplicatedResultException.java
│   ├── IDNotRecognisedException.java
│   ├── IllegalNameException.java
│   ├── InvalidCheckpointsException.java
│   ├── InvalidLengthException.java
│   ├── InvalidLocationException.java
│   ├── InvalidNameException.java
│   ├── InvalidStageStateException.java
│   ├── InvalidStageTypeException.java
│   ├── MiniCyclingPortalInterface.java
│   ├── NameNotRecognisedException.java
│   ├── Rider.java
│   ├── Segment.java
│   ├── SegmentType.java
│   ├── Stage.java
│   ├── StagedRace.java
│   ├── StageType.java
│   └── Team.java
├── doc/
│   └── ...
├── LICENSE
└── README.md
```


## 📝 License
Copyright © 2022 [Joshua](https://github.com/joshfinney).

This project is [MIT](https://choosealicense.com/licenses/mit/) licensed.
