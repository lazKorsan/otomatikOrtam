package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EmulatorController {

    private static Process emulatorProcess;

    /**
     * Emülatörü hızlı bir şekilde, kaydedilmiş bir anlık görüntüden (snapshot) başlatır.
     * Eğer kaydedilmiş bir durum yoksa normal şekilde başlar.
     */
    public static void startEmulator() {
        // Hızlı başlatma için -no-snapshot argümanı kaldırıldı.
        startEmulator(false);
    }

    /**
     * Emülatörü her zaman temiz bir durumda (cold boot) başlatır. Bu daha yavaştır.
     */
    public static void startEmulatorWithCleanState() {
        startEmulator(true);
    }

    private static void startEmulator(boolean cleanState) {
        String avdName = "Pixel_7_Pro";
        String androidHome = System.getProperty("android.home", System.getenv("ANDROID_HOME"));

        if (androidHome == null) {
            System.err.println("Hata: ANDROID_HOME veya 'android.home' ayarlanmamış.");
            return;
        }

        List<String> command = new ArrayList<>();
        command.add(androidHome + "/emulator/emulator");
        command.add("-avd");
        command.add(avdName);
        command.add("-no-audio");
        command.add("-no-boot-anim");

        if (cleanState) {
            command.add("-no-snapshot");
            System.out.println("'" + avdName + "' emülatörü temiz bir durumda (cold boot) başlatılıyor...");
        } else {
            System.out.println("'" + avdName + "' emülatörü hızlı modda (snapshot'tan) başlatılıyor...");
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            emulatorProcess = pb.start();
            System.out.println("Emülatör başlatma komutu gönderildi.");
        } catch (IOException e) {
            System.err.println("Emülatör başlatılırken bir hata oluştu.");
            e.printStackTrace();
        }
    }

    public static boolean isEmulatorRunning() {
        System.out.println("Emülatörün durumu kontrol ediliyor...");
        try {
            ProcessBuilder pb = new ProcessBuilder("adb", "devices");
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("emulator-") && line.contains("device")) {
                    System.out.println("Emülatör zaten çalışır durumda.");
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("ADB komutu çalıştırılırken bir hata oluştu.");
            e.printStackTrace();
        }
        System.out.println("Emülatör çalışmıyor.");
        return false;
    }

    public static void stopEmulator() {
        try {
            System.out.println("Emülatör kapatılıyor...");
            new ProcessBuilder("adb", "emu", "kill").start();
            if (emulatorProcess != null && emulatorProcess.isAlive()) {
                emulatorProcess.destroy();
            }
            System.out.println("Emülatör kapatma komutu gönderildi.");
        } catch (IOException e) {
            System.err.println("Emülatör kapatılırken bir hata oluştu.");
            e.printStackTrace();
        }
    }
}
