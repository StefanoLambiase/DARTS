# DARTS

**DARTS** (**D**etection **A**nd **R**efactoring of **T**est **S**mells) is an Intellij plug-in which implements a state-of-the-art detection mechanism to detect instances of three test smell types, *i.e., General Fixture, Eager Test, and Lack of Cohesion of Test Methods*, at commit-level and enables their automated refactoring through the integrated APIs provided by Intellij. 
It also provide a mechanism able to mine data from projects' repositories in order to evaluate if detected smells impact on change- and defect- proneness of the production classes.

## Getting Started

1. Download the file `DARTS_DetectionAndRefactoringOfTestSmell.jar` in the Installer directory contained in this project.
2. Open your IntelliJ IDEA and go in the Settings/Preferences dialog.
3. Select Plugins.
4. On the Plugins page, click the wheel-shaped button and then click Install Plugin from Disk.
5. Select the `DARTS_DetectionAndRefactoringOfTestSmell.jar` and click OK.
6. Click OK to apply the changes and restart the IDE.

In order to perform the detection, first of all build the project, then click on the Analyze menu > Test Smell Detection > Select between textual and structural detection.
DARTS perfom an automatic analysis at commit phase; specifically when some smell code are committed it will perform the analysis on the code commited.

### Development

A step by step series of examples that tell you how to get a development env running.

1. Clone this project.
2. Open the project in IntelliJ.
3. Go in the File/Project Structure/Project dialog.
4. In the Project SDK section, click the `New...` button.
5. Select IntelliJ Platform Plugin SDK from the menu.
6. Select your IntelliJ IDEA directory and click the OK button.
7. Select the SDK version (1.8 or higher) and click OK button.
8. Click Apply button.

## Contributing

If you want to contribute:
1. Start by forking the repository and clone it locally
2. Create your own branch
```
feature/featurename
```
3. Do all the dirty work and write good commits message
4. Create a Pull Request
