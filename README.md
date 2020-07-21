# DARTS_DetectionAndRefactoringOfTestSmell
**DARTS (Detection And Refactoring of Test Smells)** is an Intellij plug-in which implements a state-of-the-art detection mechanism to detect instances of three test smell types, *i.e.*, *General Fixture*, *Eager Test*, and *Lack of Cohesion of Test Methods*, at commit-level and enables their automated refactoring through the integrated APIs provided by Intellij.

# Installation guide
1. Download the file **DARTS_DetectionAndRefactoringOfTestSmell.jar** located in the **Installer** directory contained in this project.
2. Open your IntelliJ IDEA and go in the **Settings/Preferences** dialog.
3. Select **Plugins**.
4. On the **Plugins** page, click the *wheel-shaped* button and then click **Install Plugin from Disk**.
5. Select the **DARTS_DetectionAndRefactoringOfTestSmell.jar** and click **OK**.
6. Click **OK** to apply the changes and restart the IDE.

# To contribute
1. Clone this project.
2. Open the project in IntelliJ.
3. Go in the **File/Project Structure/Project** dialog.
4. In the **Project SDK** section, click the **New...** button.
5. Select **IntelliJ Platform Plugin SDK** from the menu.
6. Select your IntelliJ IDEA directory and click the **OK** button.
7. Select the SDK version (1.8 or higher) and click **OK** button.
8. Click **Apply** button.
