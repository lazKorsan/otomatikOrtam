package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.AppiumServerController;
import utils.EmulatorController;

public class Hooks {

    @Before("@Background")
    public void beforeScenarioWithBackground(Scenario scenario) {
        System.out.println("📱 Background tag'li senaryo başlıyor: " + scenario.getName());
        // Ortam hazırlığı BackgroundSteps'e taşındı.
        // Bu alan artık genel başlangıç işlemleri için kullanılabilir.
    }

    @After("@Background")
    public void afterScenarioWithBackground(Scenario scenario) {
        System.out.println("🧹 Background tag'li senaryo bitiyor: " + scenario.getName());

        // Appium sunucusunu durdur
        AppiumServerController.stopServer();

        // Emülatörü kapat (opsiyonel, testlerin hızlı tekrarı için açık bırakılabilir)
        // EmulatorController.stopEmulator();

        System.out.println("✅ Temizlik tamamlandı.");
    }

    // Tüm senaryolar için genel hooks (opsiyonel)
    @Before(order = 1)
    public void beforeAnyScenario(Scenario scenario) {
        System.out.println("➡️ Senaryo başlıyor: " + scenario.getName());
    }

    @After(order = 1)
    public void afterAnyScenario(Scenario scenario) {
        System.out.println("⬅️ Senaryo bitti: " + scenario.getName());
        if (scenario.isFailed()) {
            System.out.println("❌ Senaryo başarısız oldu!");
            // Ekran görüntüsü alma gibi işlemler burada yapılabilir.
        }
    }
}