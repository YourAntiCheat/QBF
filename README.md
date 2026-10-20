# QBF

QBF is a standalone Java application built with Maven. It is packaged as a `.jar` file that can be run directly from the command line.

## Prerequisites

You must have **Java 17 or higher** installed to run this application. Older versions such as Java 8 are not supported.

To verify your Java version, open a terminal or command prompt and run:

```bash
java -version
```
java version "17.0.x"
If you see a version like 1.8.0_xxx, you're running Java 8 and will need to upgrade.

# Downloading
For the corrected Windows build, download the latest successful **Build and test QBF** artifact named `QBF-Windows-JAR` from [GitHub Actions](https://github.com/YourAntiCheat/QBF/actions/workflows/build.yml). Extract the ZIP to obtain `QBF.jar`.

# Building
To build the application, ensure you have Maven installed. Then run:

```bash
mvn clean package
```
This will compile the project and produce the runnable file `target/QBF.jar`. Build on Windows for a Windows-compatible JavaFX JAR.

# Running
To run the application after building:

```bash
java -jar target/QBF.jar
```

## Selecting Litematica regions

After you load a `.litematic`, QBF lists its regions with checkboxes. All are
selected by default, so existing workflows continue to work. Use **Select all**
or **Select none** to change the selection quickly. At least one region must
be selected to export.

Coordinates entered in **Corner 1** and **Corner 2** must still describe the
bounding box of the **complete** schematic placement, even if you select only
some regions. Filtering regions does not change the coordinate origin.

If selected regions overlap, QBF exports only one waypoint per block
position. When two selected regions contain different searched block types
at the same position, the first matching region in file order supplies the
waypoint's block name/color. This also applies to Xaero and VoxelMap exports.
