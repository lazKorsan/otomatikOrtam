package stepDefinitions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utilities.AppLauncher;
import utilities.ReusableMethods;
import utilities.anyEnvirment;
import utils.EmulatorController;

import java.time.Duration;

public class BackgroundSteps {

    protected static AndroidDriver driver;
    private static AppiumDriverLocalService service;


    // --- Test Ayarları ---
    private static final String DEVICE_NAME = "Pixel 7 Pro"; // "adb devices" ile kontrol et
    private static final String PLATFORM_VERSION = "16.0"; // Cihazının Android versiyonu

    /**
     * Bu metod, tüm testler başlamadan SADECE BİR KEZ çalışır.
     * Appium sunucusunu otomatik olarak başlatır.
     */

    @Given("Kullanici {string} uygulamasi icin test ortamini baslatir")
    public void kullaniciTestOrtaminiBaslatir(String appName) {
        System.out.println("🚀 Test ortamı hazırlanıyor...");

        // 1. Emülatörü Başlat
        if (!EmulatorController.isEmulatorRunning()) {
            System.out.println("Emülatör çalışmıyor. Başlatılıyor...");
            EmulatorController.startEmulator();
            System.out.println("Emülatörün açılması için 30 saniye bekleniyor...");
            try {
                Thread.sleep(35000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Emülatör zaten çalışıyor.");
        }

        // 2. Appium Sunucusunu Başlat (Artık kendi içinde bekliyor)
        //AppiumServerController.startServer();
        AppLauncher.launchApp(appName);
        anyEnvirment.startAppiumServer();
        ReusableMethods.bekle(30);
        AppLauncher.launchApp(appName);

        // 3. Uygulamayı Başlat
        System.out.println("📱 '" + appName + "' uygulaması açılıyor...");
        AppLauncher.launchApp(appName);

        System.out.println("✅ Test ortamı ve uygulama başarıyla başlatıldı!");
    }

    @When("Kullanici {string} uygulamasini acar")
    public void kullaniciUygulamasiniAcar(String appName) {


    }

    @Given("Kullanici {string} uygulamasini acmaya calisir")
    public AndroidDriver kullaniciUygulamasiniAcmayaCalisir(String appName) {

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








}
