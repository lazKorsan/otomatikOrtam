package utilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.SessionNotCreatedException;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Driver {
    private Driver() {
        // Utility class - instantiation yok
    }

    private static AppiumDriver driver;
    private static final String DEFAULT_APPIUM_URL = "http://127.0.0.1:4723";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofMinutes(20);

    /**
     * Appium Driver'ı alır, eğer yoksa hata fırlatır
     */
    public static AppiumDriver getAppiumDriver() {
        if (driver == null) {
            throw new IllegalStateException("Driver henüz başlatılmadı! Önce launchApp() kullanın.");
        }
        return driver;
    }

    /**
     * AndroidDriver'ı güvenli şekilde alır
     */
    public static AndroidDriver getAndroidDriver() {
        AppiumDriver appiumDriver = getAppiumDriver();
        if (!(appiumDriver instanceof AndroidDriver)) {
            throw new IllegalStateException("Driver AndroidDriver değil!");
        }
        return (AndroidDriver) appiumDriver;
    }

    /**
     * Driver başlatma metodu (AppLauncher yerine direkt kullanılabilir)
     */
    public static void launchApp(String packageName, String activityName) {
        launchApp(packageName, activityName, true);
    }

    /**
     * Driver başlatma metodu (detaylı)
     */
    public static void launchApp(String packageName, String activityName, boolean noReset) {
        try {
            System.out.println("🚀 Uygulama başlatılıyor: " + packageName);

            // Önceki driver varsa kapat
            quitAppiumDriver();

            // Options oluştur
            UiAutomator2Options options = new UiAutomator2Options()
                    .setPlatformName("Android")
                    .setAutomationName("UiAutomator2")
                    .setDeviceName(ConfigReader.getProperty("deviceName", "emulator-5554"))
                    .setUdid(ConfigReader.getProperty("udid", "emulator-5554"))
                    .setPlatformVersion(ConfigReader.getProperty("platformVersion", "13"))
                    .setAppPackage(packageName)
                    .setAppActivity(activityName)
                    .setNoReset(noReset)
                    .autoGrantPermissions()
                    .setNewCommandTimeout(DEFAULT_TIMEOUT);

            // Driver başlat
            driver = new AndroidDriver(new URL(DEFAULT_APPIUM_URL), options);

            System.out.println("✅ Driver başarıyla başlatıldı: " + packageName);

        } catch (MalformedURLException e) {
            throw new RuntimeException("Geçersiz Appium URL: " + DEFAULT_APPIUM_URL, e);
        } catch (SessionNotCreatedException e) {
            throw new RuntimeException("Appium session oluşturulamadı: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Driver başlatma hatası: " + e.getMessage(), e);
        }
    }

    /**
     * Driver'ı yeniden başlat
     */
    public static void restartDriver() {
        if (driver != null) {
            try {
                String currentPackage = getCurrentPackage();
                String currentActivity = getCurrentActivity();

                if (currentPackage != null && currentActivity != null) {
                    System.out.println("🔄 Driver yeniden başlatılıyor...");
                    quitAppiumDriver();
                    launchApp(currentPackage, currentActivity, true);
                }
            } catch (Exception e) {
                System.err.println("Driver restart hatası: " + e.getMessage());
            }
        }
    }

    /**
     * Driver'ı set et (AppLauncher için)
     */
    static void setDriver(AppiumDriver newDriver) {
        if (driver != null) {
            System.out.println("⚠️ Mevcut driver kapatılıyor...");
            quitAppiumDriver();
        }
        driver = newDriver;
    }

    /**
     * Driver'ı kapat
     */
    public static void quitAppiumDriver() {
        if (driver != null) {
            try {
                System.out.println("🛑 Driver kapatılıyor...");
                driver.quit();
                System.out.println("✅ Driver başarıyla kapatıldı");
            } catch (Exception e) {
                System.err.println("❌ Driver kapatma hatası: " + e.getMessage());
            } finally {
                driver = null;
            }
        }
    }

    /**
     * Driver'ın aktif olup olmadığını kontrol et
     */
    public static boolean isDriverActive() {
        try {
            return driver != null && ((AndroidDriver) driver).getSessionId() != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Mevcut paket adını al
     */
    public static String getCurrentPackage() {
        try {
            return getAndroidDriver().getCurrentPackage();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Mevcut activity'yi al
     */
    public static String getCurrentActivity() {
        try {
            return getAndroidDriver().currentActivity();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Uygulamayı background'dan foreground'a getir
     */
    public static void activateApp() {
        try {
            String packageName = getCurrentPackage();
            if (packageName != null) {
                getAndroidDriver().activateApp(packageName);
            }
        } catch (Exception e) {
            System.err.println("Uygulama aktifleştirme hatası: " + e.getMessage());
        }
    }

    /**
     * Uygulamayı background'a al
     */
    public static void backgroundApp() {
        try {
            getAndroidDriver().runAppInBackground(Duration.ofSeconds(-1));
        } catch (Exception e) {
            System.err.println("Uygulama background hatası: " + e.getMessage());
        }
    }

    /**
     * Ekran görüntüsü al
     */
    public static void takeScreenshot(String fileName) {
        try {
            // Screenshot mantığı buraya eklenebilir
            System.out.println("📸 Screenshot alındı: " + fileName);
        } catch (Exception e) {
            System.err.println("Screenshot hatası: " + e.getMessage());
        }
    }
}

