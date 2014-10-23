package sns.teamcity;

public class Formatter {
    static String formatDiskSpace(double freeSpace1) {
        return String.format("%,.2f Mb", (freeSpace1 / (1024 * 1024)));
    }
}
