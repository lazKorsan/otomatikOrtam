package utilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;


import java.time.Duration;

public class and16_pixel7pro_Starter {

    protected static AndroidDriver driver;
    private static AppiumDriverLocalService service;

    // --- Test Ayarları ---
    private static final String DEVICE_NAME = "Pixel 7 Pro"; // "adb devices" ile kontrol et
    private static final String PLATFORM_VERSION = "13.0"; // Cihazının Android versiyonu

    /**
     * Bu metod, tüm testler başlamadan SADECE BİR KEZ çalışır.
     * Appium sunucusunu otomatik olarak başlatır.
     */

    public static void startAppiumServer() {
        try {
            service = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                    .build();

            service.start();
            ReusableMethods.bekle(5);


        } catch (Exception e) {

            throw new RuntimeException("Appium sunucusu başlatılamadı!", e);
        }
    }

    /**
     * Bu metod, tüm testler bittikten SADECE BİR KEZ çalışır.
     * Başlatılan Appium sunucusunu otomatik olarak durdurur.
     */

    public static void stopAppiumServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            ReusableMethods.logSuccess("Appium sunucusu durduruldu.");
        }
    }

    /**
     * Driver başlatma methodu - Manuel olarak çağrılabilir
     */
    public static AndroidDriver startDriver() {
        ReusableMethods.logSuccess("Driver başlatılıyor...");

        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setPlatformVersion(PLATFORM_VERSION);
        options.setDeviceName(DEVICE_NAME);
        options.setAutomationName("UiAutomator2");

        // Kiwi.com uygulaması için
        // options.setAppPackage("com.pttem.epttavm");
        //options.setAppActivity("com.pttem.epttavm.ui.activities.splash.SplashActivity");

        // Diğer capabilities'ler
        options.setNoReset(false);
        options.setAutoGrantPermissions(true);
        options.setNewCommandTimeout(Duration.ofMinutes(5));

        try {
            driver = new AndroidDriver(service.getUrl(), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

            ReusableMethods.logSuccess("Driver başarıyla oluşturuldu!");
            ReusableMethods.logSuccess("Cihaz: " + DEVICE_NAME);

            return driver;

        } catch (Exception e) {
            ReusableMethods.logError("Driver oluşturulurken hata: " + e.getMessage());
            throw new RuntimeException("Driver oluşturulamadı!", e);
        }
    }

    /**
     * Driver'ı kapatma methodu
     */
    public static void stopDriver() {
        if (driver != null) {
            try {
                driver.quit();
                ReusableMethods.logSuccess("Driver kapatıldı!");
            } catch (Exception e) {
                ReusableMethods.logError("Driver kapatılırken hata: " + e.getMessage());
            }
        }
    }

    /**
     * Driver instance'ını döndürür
     */
    public static AndroidDriver getDriver() {
        return driver;
    }
}