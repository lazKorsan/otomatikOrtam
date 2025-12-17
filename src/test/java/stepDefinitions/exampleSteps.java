package stepDefinitions;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import page.QueryCardPage;
import utilities.OptionsMet;
import utilities.ReusableMethods;

import javax.sound.midi.InvalidMidiDataException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static utilities.Driver.getAppiumDriver;
import static utilities.Driver.quitAppiumDriver;
import static utilities.OptionsMet.*;

public class exampleSteps {

    QueryCardPage card=new QueryCardPage();
    Actions actions = new Actions(getAppiumDriver());

    static String categoryName;
    static String popularBrandName;
    static String productName;




    @When("Kullanici sürücü ayarlarini yapar")
    public void kullaniciSurucuAyarlariniYapar() {

        // TODO --- burası sürücü ayarlarini otomatik olarak baslatir
        getAppiumDriver();
    }

    @Given("Kullanici {string} yazili butona tiklar")
    public void kullaniciYaziliButonaTiklar(String description) {
        // TODO -- Buradaki method OptionsMet sinifinin icinde tanimli -- Description dan elementi bulmaya yariyor
        ReusableMethods.wait(6);
        clickButtonByDescription(description);
    }

    @Then("Kullanici Catogories  basligi altindaki tum kategorilerin gorunur oldugunu dogrular")
    public void kullaniciCatogoriesBasligiAltindakiTumKategorilerinGorunurOldugunuDogrular() throws InvalidMidiDataException {

        // todo -- uyuglama icindeki tum baslik dogrumları icin kullanilabilir

        List<String> expectedCategoryNames = new ArrayList<>(Arrays.asList("Men", "Men Clothing", "Men T-Shirt", "Men Shorts",
                "Men Coat", "Men Suit", "Men Shoes", "Men Sneakers", "Men Classic Shoes", "Men Boots",
                "Men Casual Shoes", "Men Accessories", "Men Bags", "Men Socks", "Men Watch", "Women", "Women Clothing",
                "Woman Dresses - Skirts", "Woman T-shirt", "Woman Trousers", "Woman Coat", "Women Shoes",
                "Women Casual Shoes", "Women Classic Shoes", "Women's Boots", "Women Sneakers", "Women Accessories",
                "Women Bags", "Women Watch", "Women Jewelry", "Juniors", "Juniors Clothing", "Girl Clothes",
                "Juniors Sleepwear", "Boy Clothes", "Baby Clothes", "Juniors Shoes", "Girl Shoes",
                "Boy Shoes", "Juniors Accessories", "Juniors Bags", "Juniors Hat & Beres", "Toys"));

        List<String> actualCategoryNames = ReusableMethods.getAllCategories();

        for (int i = 0; i < actualCategoryNames.size(); i++) {
            Assert.assertEquals(expectedCategoryNames.get(i), actualCategoryNames.get(i));
        }
    }

    @And("Kullanici {string} catogorisini secer")
    public void kullaniciCatogorisiniSecer(String assignedCategoryName) {

        // todo -- uygulama icindeki tum kategory secimi icin uygulanabilir

        categoryName = assignedCategoryName;
        ReusableMethods.selectCategory(categoryName);
    }

    @Then("Kullanici secilen catogorye ait butun urunlerin listelendigini dogrular")
    public void kullaniciSecilenCatogoryeAitButunUrunlerinListelendiginiDogrular() {

        // todo urun listeleme islemleri icin kullanilan step
        Assert.assertTrue(card.PageTitle.isDisplayed());
        Assert.assertEquals("Category names are not equals!", categoryName, card.PageTitle.getAttribute("content-desc"));

    }

    @And("Kullanici secilen kategoriden {string} urununu secer")
    public void kullaniciSecilenKategoridenUrununuSecer(String expectedProductName) {
        // todo - onemli adim : descirption verilen urunu bulur ve tiklar
        productName = expectedProductName;
        ReusableMethods.scrollWithPartialContentDesc(expectedProductName);

    }

    @Then("Kullanici secilen urune ait detay sayfasinin goruntulendigini dogrular")
    public void kullaniciSecilenUruneAitDetaySayfasininGoruntulendiginiDogrular() {

        // todo -- buradaki method butun urun deta sayfalari icin kullanilabilir
        ReusableMethods.wait(2);
        if (card.productName == null) {
            throw new AssertionError("Element 'card.productName' bulunamadı!");
        }

        String actual = card.productName.getAttribute("content-desc");

        if (actual == null) {
            throw new AssertionError("Elementin content-desc attribute'u null!");
        }

        if (productName == null) {
            throw new AssertionError("Beklenen ürün adı (productName) null!");
        }

        System.out.println("Content Description: " + actual);
        System.out.println("Expected Product Name: " + productName);

        Assert.assertTrue(actual.contains(productName));


    }



    @And("Kullanici {string} yazili buton uzerinde ki metni dogrular")
    public void kullaniciYaziliButonUzerindeKiMetniDogrular(String name) {

        // todo -- buton uzerindeki metni dogrulamak icin kullanilan method

        ReusableMethods.wait(1);
        VerifyElementText(name);
    }

    @And("Kullanici phonenumberBox kutusuna {string} telefon numarasini girer")
    public void kullaniciPhonenumberBoxKutusunaTelefonNumarasiniGirer(String phone) {

        // todo --
        //  buradaki phone number configurations.properties e kadar uzaniyor arada yardımcı method var
        //  -----------------------------

        card.ForgetPasswordPhoneBox(phone);
    }

    @When("Kullanici New Password ve ConfirmPassword alanlarına {string} yeni password girer")
    public void kullaniciNewPasswordVeConfirmPasswordAlanlarınaYeniPasswordGirer(String newPassword) {

        // todo -- password dogrulaması icin ozel uretilmis method.
        card.NewPassword(newPassword);

    }

    @Then("Kullanici profilIcon uzerinde {string} yazili oldugunu dogrular")
    public void kullaniciProfilIconUzerindeYaziliOldugunuDogrular(String username) {

        // todo -- profil iconu uzerinde ki ismi dogrular
        ReusableMethods.wait(2);
        VerifyElementText(username);
    }

    @And("Kullanici surucuyu kapatir")
    public void kullaniciSurucuyuKapatir() {

        // todo -- uygulamayi kapatir
        quitAppiumDriver();
    }

    @Then("Kullanici sisteme {string} ve {string} ile giris yapar")
    public void kullaniciSistemeVeIleGirisYapar(String phoneNumber, String password) {


    }

    @And("Kullanici urunAdi {string} ve yorum sayisi {string} ve fiyati {string} olan urunu WishList e ekler")
    public void kullaniciUrunAdiVeYorumSayisiVeFiyatiOlanUrunuWishListEEkler(String itemName, String reviews, String price) {

        // todo -- ilgili urunu parcalanmis xpath degerinden bulur ve uzerine tiklar
        xPathElementClick(itemName, reviews, price);

    }

    @Then("Kullanici urunun WishList e eklendigini dogrular")
    public void kullaniciUrununWishListEEklendiginiDogrular() {

        // todo -- wiskhlist dogrulama
        assertTrue(card.addWishListToast.isDisplayed());
    }

    @And("Kullanici shoppingCard a bir urun ekler ve ve shoppingCard detaylarinin oldugu sayfaya gider")
    public void kullaniciShoppingCardABirUrunEklerVeVeShoppingCardDetaylarininOlduguSayfayaGider() throws
            InvalidMidiDataException  {


        card.firstElementOfMostPopuler.click();
        card.LSizeButton.click();
        OptionsMet.swipe(832, 1772, 832, 1242);
        ReusableMethods.wait(2);
        OptionsMet.clickButtonByDescription("Add To Cart");
        ReusableMethods.wait(1);
        card.sepetIcon.click();
        ReusableMethods.wait(1);


    }

    @And("Kullanici kargo icin adres secer")
    public void kullaniciKargoIcinAdresSecer() throws InvalidMidiDataException {

        touchDown(889, 838);
        OptionsMet.swipe(1185, 2017, 1185, 1282);
    }

    @When("Kullanici {string} yazili butonun gorulur ve tiklanabilir oldugunu dogrular")
    public void kullaniciYaziliButonunGorulurVeTiklanabilirOldugunuDogrular(String text) {

        // todo -- bu method verilen butonun gorunurlugunu ve tıklanabilir oldugunu test eder
        //  -- arkasından butona tiklar
        // OptionsMet.viewAndClick(text);
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + text + "\")"));

        assertTrue(button.isDisplayed());
        assertTrue(button.isEnabled());
        button.click();

    }

    @Then("Kullanici cardInformations  doldurur ve ConfirmButon a basar")
    public void kullaniciCardInformationsDoldururVeConfirmButonABasar() {

        // FIXME: bu method simdilik isler degil uzerinde iyilestirme gerekiyor
        //  yuksek oncelikli ıyilestirilmesi gerekiyor
        //  alternatif olarak baska bilgi girme methodlari kullanilabilir
        //  email kutusuna bilgi girme methodlari kullanilabilri

        ReusableMethods.wait(2);
        OptionsMet.enterCardNumber("42424242424242421226123");
        ReusableMethods.wait(2);
        touchDown(691, 777); // Confirm butonuna tıklama
        ReusableMethods.wait(2);


    }

    @And("Kullanici scrollMethod ile {string} yazili butona tiklar")
    public void kullaniciScrollMethodIleYaziliButonaTiklar(String delivered) {

        ReusableMethods.wait(2);
        ReusableMethods.scrollWithPartialContentDesc(delivered);
    }

    @Then("Kullanici ekrani {double} noktasindan {double} noktasina kaydirir")
    public void kullaniciEkraniNoktasindanNoktasinaKaydirir(Integer x, Integer y, Integer endX, Integer endY) throws InvalidMidiDataException  {

        // todo -- ekrani A(x1,y1) den B(X2,Y2) ye kadar kaydirir
        ReusableMethods.wait(2);
        OptionsMet.swipe(x, y, endX, endY);
        ReusableMethods.wait(2);


    }
    @And("Kullanici {int}, {int} koordinatlarina dokunur")
    public void kullaniciKoordinatlarinaDokunur(Integer x, Integer y) {
        // todo -- belirli bir koordinata dokunmak icin kullanilan method
        ReusableMethods.wait(1);
        OptionsMet.touchDown(x, y);
        ReusableMethods.wait(1);
    }

    @And("Kullanici ekrani {int}, {int}, {int}, {int} noktasina kaydirir")
    public void kullaniciEkraniNoktasinaKaydirir(Integer x, Integer y, Integer endX, Integer endY) throws InvalidMidiDataException {
        // todo -- ekrani A(x1,y1) den B(X2,Y2) ye kadar kaydirir
        ReusableMethods.wait(2);
        OptionsMet.swipe(x, y, endX, endY);
        ReusableMethods.wait(2);
    }

    @Then("Kullanici {int}, {int} koordinatlarina tiklar")
    public void kullaniciKoordinatlarinaTiklar(Integer x, Integer y) {
        // TODO -- belirli bir koordinata tiklamak icin kullanilan method

        ReusableMethods.wait(1);
        OptionsMet.touchDown(x, y);
        ReusableMethods.wait(1);
    }









    // todo <! -- == buradan sonrası ingilizce feature adimlarina ait
    @Given("User makes driver adjustments")
    public void user_makes_driver_adjustments() {
        getAppiumDriver();
    }

    @Given("User clicks the button with description {string}")
    public void user_clicks_the_button_with_description(String description) {
        ReusableMethods.wait(6);
        clickButtonByDescription(description);
    }

    @Given("Verify all the categories are visible under the Categories heading.")
    public void verify_all_the_categories_are_visible_under_the_categories_heading() throws InvalidMidiDataException {

        List<String> expectedCategoryNames = new ArrayList<>(Arrays.asList("Men", "Men Clothing", "Men T-Shirt", "Men Shorts",
                "Men Coat", "Men Suit", "Men Shoes", "Men Sneakers", "Men Classic Shoes", "Men Boots",
                "Men Casual Shoes", "Men Accessories", "Men Bags", "Men Socks", "Men Watch", "Women", "Women Clothing",
                "Woman Dresses - Skirts", "Woman T-shirt", "Woman Trousers", "Woman Coat", "Women Shoes",
                "Women Casual Shoes", "Women Classic Shoes", "Women's Boots", "Women Sneakers", "Women Accessories",
                "Women Bags", "Women Watch", "Women Jewelry", "Juniors", "Juniors Clothing", "Girl Clothes",
                "Juniors Sleepwear", "Boy Clothes", "Baby Clothes", "Juniors Shoes", "Girl Shoes",
                "Boy Shoes", "Juniors Accessories", "Juniors Bags", "Juniors Hat & Beres", "Toys"));

        List<String> actualCategoryNames = ReusableMethods.getAllCategories();

        for (int i = 0; i < actualCategoryNames.size(); i++) {
            Assert.assertEquals(expectedCategoryNames.get(i), actualCategoryNames.get(i));
        }
    }

    @Given("Select {string} category.")
    public void select_category(String assignedCategoryName) {
        categoryName = assignedCategoryName;
        ReusableMethods.selectCategory(categoryName);
    }

    @Given("Verify that the product listing for the selected category is displayed.")
    public void verify_that_the_product_listing_for_the_selected_category_is_displayed() {
        Assert.assertTrue(card.PageTitle.isDisplayed());
        Assert.assertEquals("Category names are not equals!", categoryName, card.PageTitle.getAttribute("content-desc"));

    }

    @Given("Select {string} from the category.")
    public void select_from_the_category(String expectedProductName) {
        productName = expectedProductName;
        ReusableMethods.scrollWithPartialContentDesc(expectedProductName);
    }

    @Given("Verify that the product details page for the selected product is displayed.")
    public void verify_that_the_product_details_page_for_the_selected_product_is_displayed() {

        ReusableMethods.wait(2);
        if (card.productName == null) {
            throw new AssertionError("Element 'card.productName' bulunamadı!");
        }

        String actual = card.productName.getAttribute("content-desc");

        if (actual == null) {
            throw new AssertionError("Elementin content-desc attribute'u null!");
        }

        if (productName == null) {
            throw new AssertionError("Beklenen ürün adı (productName) null!");
        }

        System.out.println("Content Description: " + actual);
        System.out.println("Expected Product Name: " + productName);

        Assert.assertTrue(actual.contains(productName));
    }

    //27


    @Given("User clicks tap coordinates {int}, {int}")
    public void user_clicks_tap_coordinates(Integer x, Integer y) {
        ReusableMethods.wait(1);
        OptionsMet.touchDown(x, y);
        ReusableMethods.wait(1);
    }

    @Given("User swipe to screen coordinates {int}, {int}, {int}, {int}")
    public void user_swipe_to_screen_coordinates(Integer x, Integer y, Integer endX, Integer endY) throws InvalidMidiDataException {
        ReusableMethods.wait(2);
        OptionsMet.swipe(x, y, endX, endY);
        ReusableMethods.wait(2);
    }

    /***US 11   **/
    @Given("User clicks the button with itemName {string} and {string} and {string} added WishList")
    public void user_clicks_the_button_with_item_name_and_and_added_wish_list(String itemName, String reviews, String price) {
        xPathElementClick(itemName, reviews, price);
    }

    @Given("Toaster is displayed")
    public void toast_is_displayed() {
        // System.out.println(card.addWishListToast.getText());
        assertTrue(card.addWishListToast.isDisplayed());
    }

    @Given("Driver turns off")
    public void driver_turns_off() {
        quitAppiumDriver();


    }

    //06
    @Then("Verifies that the text {string} is displayed on the screen")
    public void verifies_that_the_text_is_displayed_on_the_screen(String name) {
        ReusableMethods.wait(1);
        VerifyElementText(name);
    }

    @When("User enters phone number {string} into the phone number textbox")
    public void user_enters_phone_number_into_the_phone_number_textbox(String phone) {
        card.ForgetPasswordPhoneBox(phone);
    }

    @When("User enters {string} into both the New Password and Confirm Password textboxes")
    public void user_enters_into_both_the_new_password_and_confirm_password_textboxes(String newPassword) {
        card.NewPassword(newPassword);
    }

    @Then("Verifies that the username {string} is displayed on the screen")
    public void verifies_that_the_username_is_displayed_on_the_screen(String username) {
        ReusableMethods.wait(2);
        VerifyElementText(username);


    }

    //25
    @Given("Select with description {string}")
    public void select_with_description(String delivered) {
        ReusableMethods.wait(2);
        ReusableMethods.scrollWithPartialContentDesc(delivered);
    }

    //24
    @Given("User adds an item to shopping card and goes to the shopping card.")
    public void user_adds_an_item_to_shopping_card_and_goes_to_the_shopping_card() throws
            InvalidMidiDataException {
        card.firstElementOfMostPopuler.click();
        card.LSizeButton.click();
        OptionsMet.swipe(832, 1772, 832, 1242);
        ReusableMethods.wait(2);
        OptionsMet.clickButtonByDescription("Add To Cart");
        ReusableMethods.wait(1);
        card.sepetIcon.click();
        ReusableMethods.wait(1);


    }

    @Given("User selects an address for shipping.")
    public void user_selects_an_address_for_shipping() throws InvalidMidiDataException {
        touchDown(889, 838);
        OptionsMet.swipe(1185, 2017, 1185, 1282);
    }

    @Given("User verifies the {string} button is viewable and clickable")
    public void user_verifies_the_button_is_viewable_and_clickable(String text) {

        // OptionsMet.viewAndClick(text);
        AndroidDriver driver = (AndroidDriver) getAppiumDriver();
        WebElement button = driver.findElement(MobileBy.AndroidUIAutomator(
                "new UiSelector().description(\"" + text + "\")"));

        assertTrue(button.isDisplayed());
        assertTrue(button.isEnabled());
        button.click();

    }

    @Given("User fills card informations and clicks the confirm button")
    public void user_fills_card_informations_and_clicks_the_confirm_button() {
       /*
        ReusableMethods.wait(6);
       // touchDown(628, 548);
       // ReusableMethods.wait(3);
        OptionsMet.enterCardNumber(628,548,"42424242424242421226123");
        //actions.sendKeys("424242424242424212261231234512").perform();
        ReusableMethods.wait(2);
        touchDown(691, 777);
        ReusableMethods.wait(5);

        */

        ReusableMethods.wait(2);
        OptionsMet.enterCardNumber("42424242424242421226123");
        ReusableMethods.wait(2);
        touchDown(691, 777); // Confirm butonuna tıklama
        ReusableMethods.wait(2);
    }



}

