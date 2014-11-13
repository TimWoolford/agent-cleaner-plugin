package sns.teamcity;

public class Formatter {
    private Formatter() {
    }

    public static String formatDiskSpace(double freeSpace1) {
        return String.format("%,.2f Mb", (freeSpace1 / (1024 * 1024)));
    }
}
