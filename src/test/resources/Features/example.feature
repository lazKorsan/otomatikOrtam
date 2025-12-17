Feature: Arkadaslar ilk senaryoyu mutlaka calistirin. Bu bizim ortam degiskenlerini ayarlamamıza fayda saglayacak

  Background: Kullanici uygulamayi acar
    * Kullanici sürücü ayarlarini yapar

  @US04
  Scenario: Ana Sayfada Kategorilerin Görünür ve Seçilebilir Olduğunu Doğrula
    Given Kullanici "Categories" yazili butona tiklar
    Then Kullanici Catogories  basligi altindaki tum kategorilerin gorunur oldugunu dogrular
    And Kullanici "Toys" catogorisini secer
    Then Kullanici secilen catogorye ait butun urunlerin listelendigini dogrular
    And Kullanici secilen kategoriden "45cm Anime POKEMON Pikachu Large Stuffed Dolls Soft Plush Animal Toy Gift" urununu secer
    Then Kullanici secilen urune ait detay sayfasinin goruntulendigini dogrular



  @US06
  Scenario: Sifremi unuttum testi
    Given Kullanici "Profile" yazili butona tiklar
    Then  Kullanici "Sign In" yazili butona tiklar
    And Kullanici "Forgot Password" yazili buton uzerinde ki metni dogrular
    Then  Kullanici "Forgot Password" yazili butona tiklar
    Then Kullanici "Back to sign in" yazili buton uzerinde ki metni dogrular
    And Kullanici phonenumberBox kutusuna "9988776655" telefon numarasini girer
    Then Kullanici "Get OTP" yazili butona tiklar
    When Kullanici New Password ve ConfirmPassword alanlarına "Wise123*" yeni password girer
    And Kullanici "Submit" yazili butona tiklar
    And Kullanici "Profile" yazili butona tiklar
    Then Kullanici profilIcon uzerinde "Ahmet" yazili oldugunu dogrular
     ## And Kullanici surucuyu kapatir


  @US11
  Scenario: Kullanici favori urunlerden secip wishList ekler
    Given Kullanici "Profile" yazili butona tiklar
    And Kullanici "Sign In" yazili butona tiklar
    Then Kullanici sisteme "phoneNumber" ve "password" ile giris yapar
    And Kullanici urunAdi "Flower Print Foil T-shirt" ve yorum sayisi "0 (0  Reviews)" ve fiyati "$65.00" olan urunu WishList e ekler
    Then Kullanici urunun WishList e eklendigini dogrular


  @US24
  Scenario: Odeme methodu olarak Stripe kullanıldıgında kart bilgileri formunun doldurulabildigi ve odeme yapalabildigi test edilir
    Given Kullanici "Profile" yazili butona tiklar
    And Kullanici "Sign In" yazili butona tiklar
    Then Kullanici sisteme "phoneNumber" ve "password" ile giris yapar
    And Kullanici shoppingCard a bir urun ekler ve ve shoppingCard detaylarinin oldugu sayfaya gider
    Then Kullanici "Proceed to Checkout" yazili butona tiklar
    And Kullanici kargo icin adres secer
    Then Kullanici "Save & Pay" yazili butona tiklar
    When Kullanici "Stripe" yazili butonun gorulur ve tiklanabilir oldugunu dogrular
    And Kullanici "Confirm Order" yazili butona tiklar
    Then Kullanici cardInformations  doldurur ve ConfirmButon a basar

  @US25
  Scenario: Kullanici teslim edilen siparisler icin iade butonunun gorunurlugunu ve tiklanabilirligini test eder
    Given Kullanici "Profile" yazili butona tiklar
    And Kullanici "Sign In" yazili butona tiklar
    Then Kullanici sisteme "phoneNumber" ve "password" ile giris yapar
    And Kullanici "Profile" yazili butona tiklar
    Then Kullanici "Order History" yazili butona tiklar
    And Kullanici scrollMethod ile "Delivered" yazili butona tiklar
    Then Kullanici ekrani 700,2400 noktasindan 700,400 noktasina kaydirir

  @US27
  Scenario: Kullanici shoppingCard urun ekler
    Given Kullanici "Profile" yazili butona tiklar
    And Kullanici "Sign In" yazili butona tiklar
    Then Kullanici sisteme "phoneNumber" ve "password" ile giris yapar
    And Kullanici 313, 2408 koordinatlarina dokunur
    Then Kullanici 433, 2657 koordinatlarina dokunur
    And Kullanici ekrani 643, 2499, 660, 1537 noktasina kaydirir
    Then Kullanici 1207, 2710 koordinatlarina tiklar





  # Burdan sonrasi Ingilizce feature adimlarina ait






  @27
  Scenario: Produkte add to card
    * User clicks the button with description "Profile"
    * User clicks the button with description "Sign In"
    * As a user muss be "phoneNumber" phone and "password" password Login
    * User clicks tap coordinates 313, 2408
    * User clicks tap coordinates 433, 2657
    * User swipe to screen coordinates 643, 2499, 660, 1537
    * User clicks tap coordinates 455, 1867
    * User clicks tap coordinates 1207, 2710



  @25
  Scenario: TC01 Test to Verify the Visibility and Activity of the Return Request Button for Delivered Orders in the Dashboard Order History Page
    * User clicks the button with description "Profile"
    * User clicks the button with description "Sign In"
    * As a user muss be "phoneNumber" phone and "password" password Login
    * User clicks the button with description "Profile"
    * User clicks the button with description "Order History"
    * Select with description "Delivered"
    * User swipe to screen coordinates 700, 2400, 700, 400
    * Verifies that the username "Return Request" is displayed on the screen







  @24
  Scenario: Testing that card information form of Stripe Payment Method could be filled and payment could be made
    * User makes driver adjustments
    * User clicks the button with description "Profile"
    * User clicks the button with description "Sign In"
    * As a user muss be "phoneNumber" phone and "password" password Login
    * User adds an item to shopping card and goes to the shopping card.
    * User clicks the button with description "Proceed to Checkout"
    * User selects an address for shipping.
    * User clicks the button with description "Save & Pay"
    * User verifies the "Stripe" button is viewable and clickable
    * User clicks the button with description "Confirm Order"
    * User fills card informations and clicks the confirm button







  @Add
  Scenario:  Test to select favorite products and add to wishlist
    * User clicks the button with description "Profile"
    * User clicks the button with description "Sign In"
    * As a user muss be "phoneNumber" phone and "password" password Login
    * User clicks the button with itemName "Flower Print Foil T-shirt" and "0 (0  Reviews)" and "$65.00" added WishList
    * Toaster is displayed
    * Driver turns off


  @06
  Scenario: Forgot password test
    Given User clicks the button with description "Profile"
    When User clicks the button with description "Sign In"
    When User clicks the button with description "Forgot Password"
    Then Verifies that the text "Forgot Password" is displayed on the screen
    Then Verifies that the text "Back to sign in" is displayed on the screen
    When User enters phone number "9988776655" into the phone number textbox
    When User clicks the button with description "Get OTP"
    When User enters "Wise123*" into both the New Password and Confirm Password textboxes
    When User clicks the button with description "Submit"
    When User clicks the button with description "Profile"
    Then Verifies that the username "elif" is displayed on the screen
    Then Driver turns off

  @04
  Scenario:  Verify Categories are Visible and Selectable on Homepage
    * User clicks the button with description "Categories"
    * Verify all the categories are visible under the Categories heading.
    * Select "Toys" category.
    * Verify that the product listing for the selected category is displayed.
    * Select "45cm Anime POKEMON Pikachu Large Stuffed Dolls Soft Plush Animal Toy Gift" from the category.
    * Verify that the product details page for the selected product is displayed.




































  @US06english
  Scenario: Forgot password test
    Given User clicks the button with description "Profile"
    When User clicks the button with description "Sign In"
    When User clicks the button with description "Forgot Password"
    Then Verifies that the text "Forgot Password" is displayed on the screen
    Then Verifies that the text "Back to sign in" is displayed on the screen
    When User enters phone number "9988776655" into the phone number textbox
    When User clicks the button with description "Get OTP"
    When User enters "Wise123*" into both the New Password and Confirm Password textboxes
    When User clicks the button with description "Submit"
    When User clicks the button with description "Profile"
    Then Verifies that the username "elif" is displayed on the screen
    Then Driver turns off


  @04
  Scenario:  Verify Categories are Visible and Selectable on Homepage
    * User clicks the button with description "Categories"
    * Verify all the categories are visible under the Categories heading.
    * Select "Toys" category.
    * Verify that the product listing for the selected category is displayed.
    * Select "45cm Anime POKEMON Pikachu Large Stuffed Dolls Soft Plush Animal Toy Gift" from the category.
    * Verify that the product details page for the selected product is displayed.





















