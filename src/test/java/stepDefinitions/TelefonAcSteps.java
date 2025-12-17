package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.AppiumServerController;
import utils.EmulatorController;

public class TelefonAcSteps {

    @Given("the test environment is set up without reset")
    public void the_test_environment_is_set_up_without_reset() {
        System.out.println("Test ortamı manuel olarak kuruluyor (sıfırlama olmadan)...");

        // 1. Emülatörü Başlat (Hızlı Modda)
        if (!EmulatorController.isEmulatorRunning()) {
            System.out.println("Emülatör çalışmıyor. Hızlı modda başlatılıyor...");
            EmulatorController.startEmulator(); // -no-snapshot olmadan, hızlı başlatan metot

            System.out.println("Emülatörün açılması için 30 saniye bekleniyor...");
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Emülatör bekleme tamamlandı.");
        } else {
            System.out.println("Emülatör zaten çalışıyor.");
        }

        // 2. Appium Sunucusunu Başlat
        AppiumServerController.startServer();

        System.out.println("Test ortamı kurulumu tamamlandı.");
    }

    @Then("the test environment is torn down")
    public void the_test_environment_is_torn_down() {
        System.out.println("Test ortamı manuel olarak kapatılıyor...");

        // 1. Appium Sunucusunu Kapat
        AppiumServerController.stopServer();

        // 2. Emülatörü Kapat
        EmulatorController.stopEmulator();

        System.out.println("Test ortamı temizliği tamamlandı.");
    }

}
