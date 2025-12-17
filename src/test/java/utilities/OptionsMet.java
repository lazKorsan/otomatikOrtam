package utilities;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.sound.midi.InvalidMidiDataException;
import java.time.Duration;
import java.util.*;

import static org.junit.Assert.assertTrue;
import static page.QueryCardPage.driver;
import static utilities.Driver.getAppiumDriver;

public class OptionsMet {
    //
    public static void swipe(int x, int y, int endX, int endY) throws InvalidMidiDataException {
        /******  PointerInput ve Sequence Kullanımı: PointerInput ile parmak hareketlerini
         *      ve Sequence ile bu hareketlerin sırasını tanımlıyoruz.
         addAction metodunu doğru PointerInput nesnesi üzerinde kullanarak sıraya işlemler ekliyoruz.  ***********/
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point start = new Point(x, y);
        Point end = new Point(endX, endY);
        /** Bu sınıflar Selenium WebDriver içinde ekran üzerinde işaretlemeler yapmak için kullanılır.**/
        Sequence swipe = new Sequence(finger, 0); // 0 or any other index

        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(1000),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        getAppiumDriver().perform(Arrays.asList(swipe));
    }

    public static void touchDown(int x, int y) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Point tapPoint = new Point(x, y);
        Sequence tap = new Sequence(finger, 1); //sıralama diziye alma
        tap.addAction(finger.createPointerMove(Duration.ofMillis(0),//parmak olustur
                PointerInput.Origin.viewport(), tapPoint.x, tapPoint.y)); //
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(50)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        getAppiumDriver().perform(Arrays.asList(tap));
    }

    public static void scrollWithUiScrollable(String elementText) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""
                        + elementText + "\").instance(0))"));
    }

    // Ekrandaki bir butona tıklama metodu
    public static void clickButtonByDescription(String description) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + description + "\")"));
        button.click();
    }

    public static void clickAndSendKeys(WebElement element, String context) {
        assertTrue(element.isDisplayed());
        element.click();
        element.clear();
        element.sendKeys(context);
    }

    public static void clickAndVerify(WebElement element) {
        assertTrue(element.isDisplayed());
        assertTrue(element.isEnabled());
        element.click();
    }

    public static void VerifyElementText(String description) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement webElement = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + description + "\")"));
        assertTrue(webElement.isDisplayed());
    }

    public static void hideKeyboard() {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        driver.hideKeyboard(); // Klavyeyi kapatma komutu
    }

    public static void KeyBack() {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        // Geri tuşuna basın
        driver.pressKey(new KeyEvent(AndroidKey.BACK));

    }

    public static void xPathElementClick(String itemName, String reviews, String price) {
        String xpathExpression = String.format("//android.view.View[contains(@content-desc, '%s') and contains(@content-desc, '%s') and contains(@content-desc, '%s')]/android.widget.ImageView", itemName, reviews, price);
        ReusableMethods.wait(3);
        // Öğeyi bulma
        WebElement element = getAppiumDriver().findElement(MobileBy.xpath(xpathExpression));

        // Öğeyle etkileşim
        element.click();

    }

    public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
        // 1. WebDriverWait nesnesini oluştur
        // Burada AppiumDriver'ınızı getAppiumDriver() metodu ile alıyoruz.
        WebDriverWait wait = new WebDriverWait(getAppiumDriver(), Duration.ofSeconds(timeToWaitInSec));

        // 2. Elementin görünür olmasını bekle
        // expectedConditions.visibilityOf(element) koşulu gerçekleşene kadar bekler.
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    public static void viewAndClick(String text) {
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + text + "\")"));

        assertTrue(button.isDisplayed());
        assertTrue(button.isEnabled());
        button.click();
    }

    public static void enterCardNumber(String cardNumber) {
       /* // Kart numarası inputuna tıklamak için mobile: clickGesture kullan
        Map<String, Object> clickArgs = new HashMap<>();
        clickArgs.put("x", x);
        clickArgs.put("y", y);
        clickArgs.put("duration", 200); // ms
        driver.executeScript("mobile: clickGesture", clickArgs);

        // Klavyenin açılması için kısa bekleme
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        // Kart numarasını yazma
        Map<String, Object> typeArgs = new HashMap<>();
        typeArgs.put("text", cardNumber);
        driver.executeScript("mobile: type", typeArgs);
        }

        */
        try {
            //  WebView context'e geç
            Set<String> contexts = driver.getContextHandles();
            for (String context : contexts) {
                if (context.contains("WEBVIEW_com.wise.querycart")) {
                    driver.context(context);
                    System.out.println("Switched to WebView: " + context);
                    break;
                }
            }

            //  Tüm iframe'leri bul
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println(" Iframe count: " + iframes.size());

            WebElement cardInput = null;

            //  Her iframe'in içine gir ve input aramayı dene
            for (int i = 0; i < iframes.size(); i++) {
                driver.switchTo().defaultContent();
                driver.switchTo().frame(iframes.get(i));
                System.out.println("Switched to iframe index: " + i);

                List<WebElement> inputs = driver.findElements(By.cssSelector("input.__PrivateStripeElement-input"));
                if (!inputs.isEmpty()) {
                    cardInput = inputs.get(0);
                    System.out.println(" Found card input in iframe index: " + i);
                    break;
                }
            }

            if (cardInput == null) {
                throw new NoSuchElementException(" Card input not found in any iframe!");
            }

            //  Input'a tıklayıp yaz
            cardInput.click();
            Thread.sleep(500);
            cardInput.sendKeys(cardNumber);
            Thread.sleep(500);
            System.out.println("Card number entered successfully!");

            // Ana frame'e dön
            driver.switchTo().defaultContent();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //  Native'e dön
            driver.context("NATIVE_APP");
        }
    }
}


