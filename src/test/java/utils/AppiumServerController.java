package utils;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.time.Duration;

public class AppiumServerController {

    private static AppiumDriverLocalService service;
    private static final int MAX_STARTUP_TIME_SECONDS = 30; // Süreyi biraz artıralım

    /**
     * Appium sunucusunu başlatır, logları konsola yazar ve hazır olana kadar bekler.
     */
    public static void startServer() {
        if (service != null && service.isRunning()) {
            System.out.println("✅ Appium sunucusu zaten çalışıyor.");
            return;
        }

        System.out.println("🔄 Appium sunucusu başlatılıyor... (Loglar aşağıda görünecektir)");

        // Appium Service Builder'ı yapılandır
        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withIPAddress("0.0.0.0")
                .usingPort(4723)
                .withTimeout(Duration.ofSeconds(MAX_STARTUP_TIME_SECONDS))
                // ÖNEMLİ: Appium loglarını konsola yazdırmak için
                .withLogOutput(System.out)
                // Daha detaylı loglar için (opsiyonel)
                .withArgument(GeneralServerFlag.LOG_LEVEL, "debug");

        service = AppiumDriverLocalService.buildService(builder);

        // Sunucuyu başlat
        service.start();

        // Sunucunun gerçekten çalışır duruma gelmesini bekle
        if (!service.isRunning()) {
            throw new RuntimeException("Appium sunucusu " + MAX_STARTUP_TIME_SECONDS + " saniye içinde başlatılamadı. Lütfen konsol loglarını kontrol edin.");
        }

        System.out.println("✅ Appium sunucusu başarıyla başlatıldı: " + service.getUrl());
    }

    /**
     * Başlatılmış olan Appium sunucusunu durdurur.
     */
    public static void stopServer() {
        if (service != null && service.isRunning()) {
            System.out.println("🛑 Appium sunucusu kapatılıyor...");
            service.stop();
            System.out.println("✅ Appium sunucusu başarıyla kapatıldı.");
            service = null;
        } else {
            System.out.println("ℹ️ Appium sunucusu zaten çalışmıyor veya başlatılmamış.");
        }
    }
}