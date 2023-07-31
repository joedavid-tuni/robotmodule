## Robot Module

[![CC BY 4.0][cc-by-shield]][cc-by]

This git repository contains supplementary material to the Doctoral Dissertation of Joe David, "_A Design Science Research Approach to Architecting and Developing Information Systems for Collaborative Manufacturing: A Case for Human-Robot Collaboration_". 

> **Note**: For other (dissertation) related git repositories, see the meta git repository [here](https://permanent.link/to/jd-doctoral-dissertation/meta-repository).

The robot module interfaces a real UR5 robot with the agents middleware via sockets. It actively polls the knowledge base of the robot and executes pre-defined trajectories based on run-time message exchanges with the human operator.

## Pre-requisites

All dependencies are managed by MAVEN and listed in the `pom.xml`.

## Getting Started

The entry point to the web server is the `RobotController.java` in [src/main/java](./src/main/java/RobotController.java).

## License

This work is licensed under a [Creative Commons Attribution 4.0 International License][cc-by]. You can find the included license [here](./LICENSE).

[![CC BY 4.0][cc-by-image]][cc-by]

[cc-by]: http://creativecommons.org/licenses/by/4.0/
[cc-by-image]: https://i.creativecommons.org/l/by/4.0/88x31.png
[cc-by-shield]: https://img.shields.io/badge/License-CC%20BY%204.0-lightgrey.svg





