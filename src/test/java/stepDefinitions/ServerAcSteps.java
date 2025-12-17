package stepDefinitions;

import io.cucumber.java.en.Given;
import utilities.ReusableMethods;

import static utilities.AppLauncher.launchApp;
import static utilities.anyEnvirment.startAppiumServer;
import static utilities.anyEnvirment.startDriver;


public class ServerAcSteps {

    @Given("the Appium server is ready for testing")
    public void the_appium_server_is_ready_for_testing() {
        // Bu adımın içi bilinçli olarak boş bırakılmıştır.
        // Emülatör ve Appium sunucusunu başlatma işlemleri Hooks sınıfındaki @BeforeAll metodu tarafından yönetilmektedir.
        // Bu adım, sadece test senaryosunu çalıştırmak ve Hooks mekanizmasını tetiklemek için bir araçtır.
        System.out.println("Feature dosyası adımı çalıştı. Appium sunucusunun Hooks tarafından başlatılmış olması gerekiyor.");
    }

    @Given("kullanici ortam olusturur")
    public void kullaniciOrtamOlusturur() {
        startAppiumServer();
        ReusableMethods.bekle(5);
        startDriver();
        launchApp("QueryCart");






    }
}
