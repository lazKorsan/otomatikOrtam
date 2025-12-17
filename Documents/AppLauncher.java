package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class AppLauncher {

    // Uygulama bilgilerini saklayacağımız map
    private static final Map<String, AppInfo> APP_INFO_MAP = new HashMap<>();

    static {
        // Uygulama bilgilerini ekleyin
        APP_INFO_MAP.put("querycart", new AppInfo("com.wise.querycart", "com.wise.querycart.MainActivity"));
        APP_INFO_MAP.put("camera", new AppInfo("com.android.camera2", "com.android.camera.Camera"));
        APP_INFO_MAP.put("settings", new AppInfo("com.android.settings", "com.android.settings.Settings"));
        APP_INFO_MAP.put("chrome", new AppInfo("com.android.chrome", "com.google.android.apps.chrome.Main"));
        // Diğer uygulamaları ekleyebilirsiniz
    }

    /**
     * Uygulamayı isme göre aç
     */
    public static void launchApp(String appName) {
        try {
            System.out.println("Uygulama açılıyor: " + appName);

            // Uygulama bilgilerini al
            AppInfo appInfo = APP_INFO_MAP.get(appName.toLowerCase());
            if (appInfo == null) {
                System.err.println("Uygulama bilgisi bulunamadı: " + appName);
                return;
            }

            // Driver'ı başlat ve uygulamayı aç
            startDriverWithApp(appInfo);

            System.out.println("✅ Uygulama başarıyla açıldı: " + appName);

        } catch (Exception e) {
            System.err.println("Uygulama açma hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Appium driver'ı belirli uygulama ile başlat
     */
    private static void startDriverWithApp(AppInfo appInfo) throws MalformedURLException {
        if (Driver.getAndroidDriver() != null) {
            Driver.quitAppiumDriver(); // Önceki driver'ı kapat
        }

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android")
                .setAutomationName("UiAutomator2")
                .setAppPackage(appInfo.getPackageName())
                .setAppActivity(appInfo.getActivityName())
                .setNoReset(true)
                .autoGrantPermissions()
                .setNewCommandTimeout(Duration.ofMinutes(20));

        AndroidDriver driver = new AndroidDriver(
                new URL("http://127.0.0.1:4723"),
                options
        );

        // Driver'ı statik olarak sakla
        Driver.setDriver(driver);

        ReusableMethods.bekle(5); // Uygulamanın açılmasını bekle
    }

    /**
     * Uygulama bilgilerini tutan inner class
     */
    private static class AppInfo {
        private String packageName;
        private String activityName;

        public AppInfo(String packageName, String activityName) {
            this.packageName = packageName;
            this.activityName = activityName;
        }

        public String getPackageName() {
            return packageName;
        }

        public String getActivityName() {
            return activityName;
        }
    }
}