package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static utilities.ReusableMethods.*;

public class AppLauncher {

    private static final Map<String, String> APP_PACKAGES = new HashMap<>();

    static {
        // Önceden tanımlı uygulama paketleri
        APP_PACKAGES.put("camera", "com.android.camera2");
        APP_PACKAGES.put("kamera", "com.android.camera2");
        APP_PACKAGES.put("gallery", "com.google.android.apps.photos");
        APP_PACKAGES.put("galeri", "com.google.android.apps.photos");
        APP_PACKAGES.put("settings", "com.android.settings");
        APP_PACKAGES.put("ayarlar", "com.android.settings");
        APP_PACKAGES.put("phone", "com.android.dialer");
        APP_PACKAGES.put("telefon", "com.android.dialer");
        APP_PACKAGES.put("messages", "com.android.mms");
        APP_PACKAGES.put("mesaj", "com.android.mms");
        APP_PACKAGES.put("mesajlar", "com.android.mms");
    }

    /**
     * Uygulamayı isme göre açmak için ana metod
     */
    public static void launchApp(String appName) {
        try {
            logSuccess("Uygulama açılıyor: " + appName);

            // YÖNTEM 1: Intent ile aç (en hızlı)
            if (tryOpenWithIntent(appName)) return;

            // YÖNTEM 2: Appium app management ile aç
            if (tryOpenWithAppManagement(appName)) return;

            // YÖNTEM 3: ADB ile aç (garantili)
            if (tryOpenWithADB(appName)) return;

            // YÖNTEM 4: Launcher'dan aç (son çare)
            if (tryOpenFromLauncher(appName)) return;

            logError("❌ Hiçbir yöntemle açılamadı: " + appName);

        } catch (Exception e) {
            logError("Uygulama açma hatası: " + e.getMessage());
        }
    }

    /**
     * Intent ile uygulama açma
     */
    private static boolean tryOpenWithIntent(String appName) {
        try {
            AndroidDriver driver = getAndroidDriver();
            Map<String, String> args = getIntentArgs(appName);

            driver.executeScript("mobile: startActivity", args);
            bekle(3);

            if (isAppOpened(appName)) {
                logSuccess("✅ Intent ile açıldı: " + appName);
                return true;
            }
        } catch (Exception e) {
            logWarning("Intent başarısız: " + e.getMessage());
        }
        return false;
    }

    /**
     * Appium app management ile uygulama açma
     */
    private static boolean tryOpenWithAppManagement(String appName) {
        try {
            AndroidDriver driver = getAndroidDriver();
            String packageName = getPackageName(appName);

            if (packageName != null) {
                driver.terminateApp(packageName);
                driver.activateApp(packageName);
            } else {
                driver.terminateApp(appName);
                driver.activateApp(appName);
            }

            bekle(3);

            if (isAppOpened(appName)) {
                logSuccess("✅ App management ile açıldı: " + appName);
                return true;
            }
        } catch (Exception e) {
            logWarning("App management başarısız: " + e.getMessage());
        }
        return false;
    }

    /**
     * ADB komutları ile uygulama açma
     */
    private static boolean tryOpenWithADB(String appName) {
        try {
            String packageName = findPackageWithADB(appName);
            if (packageName != null) {
                Runtime.getRuntime().exec("adb shell monkey -p " + packageName + " -c android.intent.category.LAUNCHER 1");
                bekle(3);

                if (isAppOpened(appName)) {
                    logSuccess("✅ ADB ile açıldı: " + packageName);
                    return true;
                }
            }
        } catch (Exception e) {
            logWarning("ADB başarısız: " + e.getMessage());
        }
        return false;
    }

    /**
     * Launcher'dan uygulama açma
     */
    private static boolean tryOpenFromLauncher(String appName) {
        try {
            AndroidDriver driver = getAndroidDriver();

            // Ana sayfaya git
            driver.pressKey(new KeyEvent(AndroidKey.HOME));
            bekle(2);

            // Uygulama çekmecesini aç (alternatif yöntem)
            openAppDrawer(driver);

            // Uygulama ara ve tıkla
            String appXpath = "//android.widget.TextView[contains(@text, '" + appName + "')]";
            WebElement appElement = driver.findElement(By.xpath(appXpath));
            appElement.click();

            bekle(3);

            if (isAppOpened(appName)) {
                logSuccess("✅ Launcher'dan açıldı: " + appName);
                return true;
            }
        } catch (Exception e) {
            logWarning("Launcher başarısız: " + e.getMessage());
        }
        return false;
    }

    /**
     * Uygulama çekmecesini aç
     */
    private static void openAppDrawer(AndroidDriver driver) {
        try {
            // Yöntem 1: All apps butonuna tıkla
            driver.findElement(By.id("com.android.launcher3:id/all_apps_handle")).click();
        } catch (Exception e) {
            try {
                // Yöntem 2: Home ekranında kaydır
                driver.executeScript("mobile: scroll", Map.of("direction", "up"));
            } catch (Exception ex) {
                logWarning("App drawer açılamadı");
            }
        }
        bekle(2);
    }

    /**
     * Intent argümanlarını hazırla
     */
    private static Map<String, String> getIntentArgs(String appName) {
        Map<String, String> args = new HashMap<>();

        switch (appName.toLowerCase()) {
            case "kamera":
            case "camera":
                args.put("action", "android.media.action.IMAGE_CAPTURE");
                break;
            case "galeri":
            case "gallery":
                args.put("action", "android.intent.action.VIEW");
                args.put("type", "image/*");
                break;
            case "ayarlar":
            case "settings":
                args.put("action", "android.settings.SETTINGS");
                break;
            case "telefon":
            case "phone":
                args.put("action", "android.intent.action.DIAL");
                break;
            case "mesaj":
            case "message":
            case "mesajlar":
            case "messages":
                args.put("action", "android.intent.action.SENDTO");
                break;
            default:
                args.put("action", "android.intent.action.MAIN");
                args.put("category", "android.intent.category.LAUNCHER");
        }
        return args;
    }

    /**
     * ADB ile paket ismi bul
     */
    private static String findPackageWithADB(String appName) {
        try {
            String[] commands = {
                    "adb shell pm list packages -3", // 3rd party apps
                    "adb shell pm list packages -s"  // System apps
            };

            for (String command : commands) {
                Process process = Runtime.getRuntime().exec(command);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.toLowerCase().contains(appName.toLowerCase())) {
                        return line.replace("package:", "").trim();
                    }
                }
            }
        } catch (Exception e) {
            logError("ADB ile paket bulunamadı: " + e.getMessage());
        }
        return null;
    }

    /**
     * Önceden tanımlı paket ismini getir
     */
    private static String getPackageName(String appName) {
        return APP_PACKAGES.get(appName.toLowerCase());
    }

    /**
     * Uygulamanın açılıp açılmadığını kontrol et
     */
    public static boolean isAppOpened(String appName) {
        try {
            String currentActivity = getAndroidDriver().currentActivity().toLowerCase();
            String appNameLower = appName.toLowerCase();

            // Activity kontrolü
            switch (appNameLower) {
                case "kamera":
                case "camera":
                    return currentActivity.contains("camera");
                case "galeri":
                case "gallery":
                    return currentActivity.contains("gallery") || currentActivity.contains("photos");
                case "ayarlar":
                case "settings":
                    return currentActivity.contains("settings");
                case "telefon":
                case "phone":
                    return currentActivity.contains("dialer");
                case "mesaj":
                case "message":
                case "mesajlar":
                case "messages":
                    return currentActivity.contains("mms") || currentActivity.contains("message");
                default:
                    return !currentActivity.contains("launcher") && !currentActivity.contains("home");
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Android driver'ı güvenli şekilde al
     */
    private static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) Driver.getAndroidDriver();
    }

    /**
     * Uygulamayı kapat
     */
    public static void closeApp(String appName) {
        try {
            AndroidDriver driver = getAndroidDriver();
            String packageName = getPackageName(appName);

            if (packageName != null) {
                driver.terminateApp(packageName);
            } else {
                driver.terminateApp(appName);
            }

            logSuccess("Uygulama kapatıldı: " + appName);
        } catch (Exception e) {
            logError("Uygulama kapatma hatası: " + e.getMessage());
        }
    }
}