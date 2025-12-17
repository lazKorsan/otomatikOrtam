package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final String DEFAULT_CONFIG_FILE = "configuration.properties";
    private static Properties properties;
    private static boolean initialized = false;
    private static long lastModifiedTime = 0;

    // Private constructor - utility class
    private ConfigReader() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Config dosyasını yükle
     */
    private static synchronized void loadProperties() {
        if (initialized && !isConfigFileModified()) {
            return;
        }

        properties = new Properties();
        InputStream inputStream = null;

        try {
            String configPath = System.getProperty("config.file", DEFAULT_CONFIG_FILE);

            // Önce classpath'ten dene
            inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(configPath);

            // Bulunamazsa file system'den dene
            if (inputStream == null) {
                inputStream = new FileInputStream(configPath);
            }

            properties.load(inputStream);
            initialized = true;

            // Son değiştirme zamanını kaydet
            if (inputStream instanceof FileInputStream) {
                java.io.File configFile = new java.io.File(configPath);
                lastModifiedTime = configFile.lastModified();
            }

            System.out.println("✅ Config dosyası yüklendi: " + configPath);

        } catch (FileNotFoundException e) {
            System.err.println("❌ Config dosyası bulunamadı: " + DEFAULT_CONFIG_FILE);
            throw new RuntimeException("Config dosyası bulunamadı: " + DEFAULT_CONFIG_FILE, e);
        } catch (IOException e) {
            System.err.println("❌ Config dosyası okunamadı: " + e.getMessage());
            throw new RuntimeException("Config dosyası okunamadı", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.err.println("❌ Config stream kapatılamadı: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Config dosyası değişmiş mi kontrol et
     */
    private static boolean isConfigFileModified() {
        try {
            String configPath = System.getProperty("config.file", DEFAULT_CONFIG_FILE);
            java.io.File configFile = new java.io.File(configPath);

            if (configFile.exists()) {
                long currentModifiedTime = configFile.lastModified();
                return currentModifiedTime > lastModifiedTime;
            }
        } catch (Exception e) {
            // Hata durumunda yeniden yükle
            return true;
        }
        return false;
    }

    /**
     * Property değerini al
     */
    public static String getProperty(String key) {
        return getProperty(key, null);
    }

    /**
     * Property değerini al, bulunamazsa default değer döndür
     */
    public static String getProperty(String key, String defaultValue) {
        if (!initialized) {
            loadProperties();
        }

        String value = properties.getProperty(key);

        if (value == null) {
            System.err.println("⚠️ Config property bulunamadı: " + key);
            return defaultValue;
        }

        return value.trim();
    }

    /**
     * Property değerini integer olarak al
     */
    public static int getIntProperty(String key) {
        return getIntProperty(key, 0);
    }

    /**
     * Property değerini integer olarak al, bulunamazsa default değer döndür
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("❌ Integer property format hatası: " + key + " = " + value);
            return defaultValue;
        }
    }

    /**
     * Property değerini boolean olarak al
     */
    public static boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, false);
    }

    /**
     * Property değerini boolean olarak al, bulunamazsa default değer döndür
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        return Boolean.parseBoolean(value);
    }

    /**
     * Property değerini long olarak al
     */
    public static long getLongProperty(String key) {
        return getLongProperty(key, 0L);
    }

    /**
     * Property değerini long olarak al, bulunamazsa default değer döndür
     */
    public static long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            System.err.println("❌ Long property format hatası: " + key + " = " + value);
            return defaultValue;
        }
    }

    /**
     * Property değerini double olarak al
     */
    public static double getDoubleProperty(String key) {
        return getDoubleProperty(key, 0.0);
    }

    /**
     * Property değerini double olarak al, bulunamazsa default değer döndür
     */
    public static double getDoubleProperty(String key, double defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            System.err.println("❌ Double property format hatası: " + key + " = " + value);
            return defaultValue;
        }
    }

    /**
     * Tüm property'leri listele (debug için)
     */
    public static void listAllProperties() {
        if (!initialized) {
            loadProperties();
        }

        System.out.println("📋 Config Properties:");
        for (String key : properties.stringPropertyNames()) {
            String value = properties.getProperty(key);
            // Password gibi sensitive dataları maskele
            if (key.toLowerCase().contains("password") || key.toLowerCase().contains("pass")) {
                value = "*****";
            }
            System.out.println("  " + key + " = " + value);
        }
    }

    /**
     * Belirli bir prefix ile başlayan property'leri al
     */
    public static Properties getPropertiesWithPrefix(String prefix) {
        if (!initialized) {
            loadProperties();
        }

        Properties filteredProps = new Properties();
        for (String key : properties.stringPropertyNames()) {
            if (key.startsWith(prefix)) {
                filteredProps.setProperty(key, properties.getProperty(key));
            }
        }
        return filteredProps;
    }

    /**
     * Config'i yeniden yükle (runtime'da değişiklik yapıldıysa)
     */
    public static void reload() {
        System.out.println("🔄 Config yeniden yükleniyor...");
        initialized = false;
        loadProperties();
    }

    /**
     * Property ekle (runtime'da)
     */
    public static void setProperty(String key, String value) {
        if (!initialized) {
            loadProperties();
        }
        properties.setProperty(key, value);
        System.out.println("➕ Runtime property eklendi: " + key + " = " + value);
    }

    /**
     * Sistem property'si olarak al, yoksa config'ten al
     */
    public static String getSystemOrConfigProperty(String key, String defaultValue) {
        // Önce sistem property'sini dene
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            return systemValue.trim();
        }

        // Sistem property'si yoksa config'ten al
        return getProperty(key, defaultValue);
    }
}