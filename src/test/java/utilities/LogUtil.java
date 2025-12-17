package utilities;

public class LogUtil {

    public static void logError(String message) {
        System.err.println("[ERROR] " + new java.util.Date() + " - " + message);
    }

    public static void logSuccess(String message) {
        System.out.println("[SUCCESS] " + new java.util.Date() + " - " + message);
    }

    public static void logWarning(String message) {
        System.out.println("[WARNING] " + new java.util.Date() + " - " + message);
    }

    public static void logInfo(String message) {
        System.out.println("[INFO] " + new java.util.Date() + " - " + message);
    }
}