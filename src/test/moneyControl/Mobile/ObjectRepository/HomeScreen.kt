package moneyControl.Mobile.ObjectRepository

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.nativekey.AndroidKey
import io.appium.java_client.pagefactory.iOSXCUITFindBy
import moneyControl.Mobile.MobileSupportLibraries.MobileCommonUtils
import moneyControl.SupportLibraries.CommonUtils
import moneyControl.SupportLibraries.Report
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindAll
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory
import java.awt.event.KeyEvent

class HomeScreen(aDriver: AndroidDriver) {
    private var mobileCommonUtils:MobileCommonUtils

    init {
        mobileCommonUtils = MobileCommonUtils(aDriver)
        PageFactory.initElements(aDriver, this)
    }

    @FindBy(xpath = "//*[@text='Home']")
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var homeIcon: WebElement

    @FindBy(xpath = "//*[@text='Cryptocurrency']")
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var cryptocurrencyCTA: WebElement

    @FindBy(xpath = "//android.widget.TextView[@text='Cryptocurrency']//ancestor::android.view.ViewGroup[@resource-id='com.divum.MoneyControl:id/rl_tray_container']")
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var cryptocurrencySection: WebElement

    @FindBy(id = "com.divum.MoneyControl:id/tv_crypto_data_heading")
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var crytoCurrencyHeader: MutableList<WebElement>

    //----------------------------tejas----------------------------------
    @FindAll(FindBy(id = "com.divum.MoneyControl:id/loginEmailET"), FindBy(id = "com.divum.MoneyControl.debug:id/loginEmailET"))
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var emailIDTextField: WebElement

    @FindAll(FindBy(id = "com.divum.MoneyControl:id/loginePassET"), FindBy(id = "com.divum.MoneyControl.debug:id/loginePassET"))
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var passwordTextField: WebElement

    @FindAll(FindBy(id = "com.divum.MoneyControl:id/loginBtn"), FindBy(id = "com.divum.MoneyControl.debug:id/loginBtn"))
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var loginButton: WebElement

    @FindAll(FindBy(id = "com.divum.MoneyControl:id/tvLogoutll"), FindBy(id = "com.divum.MoneyControl.debug:id/tvLogoutll"))
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var logoutButton: WebElement

    @FindAll(FindBy(id = "com.divum.MoneyControl:id/header_user_icon_img"), FindBy(id = "com.divum.MoneyControl.debug:id/header_user_icon_img"))
    @iOSXCUITFindBy(iOSNsPredicate = "")
    private lateinit var profileIcon: WebElement

    @FindBy(id = "com.divum.MoneyControl:id/imgPlayPause")
    private lateinit var playPauseButton: WebElement

    @FindBy(id = "com.divum.MoneyControl:id/tvBreakProNews")
    private lateinit var articleHeader: WebElement

    @FindBy(id = "com.divum.MoneyControl:id/header_search_icon")
    private lateinit var searchIcon: WebElement

    @FindBy(id = "com.divum.MoneyControl:id/getQuote_editSearchBox")
    private lateinit var searchBox: WebElement

    @FindBy(id = "com.divum.MoneyControl:id/getquote_noSearchFound")
    private lateinit var searchResults: WebElement

    @FindBy(xpath = "//android.widget.TextView[@text='Indices']")
    private lateinit var indicesText: WebElement

//    @FindBy(xpath = "//android.widget.HorizontalScrollView[@resource-id='com.divum.MoneyControl:id/tabs']/descendant::android.widget.TextView[1]")
@FindBy(xpath = "//android.widget.HorizontalScrollView[@resource-id='com.divum.MoneyControl:id/tabs']/descendant::android.widget.TextView[1]")
    private lateinit var NSE: WebElement

    @FindBy(xpath = "//android.widget.HorizontalScrollView[@resource-id='com.divum.MoneyControl:id/tabs']/descendant::android.widget.TextView[2]")
    private lateinit var BSE: WebElement

    fun tapOnHomeIcon(){
        mobileCommonUtils.click(homeIcon, "HomeIcon")
    }

    fun scrollToSection(section:String){
        when(section){
            "Cryptocurrency" -> {
                mobileCommonUtils.scrollToElement(cryptocurrencyCTA)
//                Report.passTestWithScreenShot("Scrolled till Cryptocurrency section", CommonUtils.get)

//                cryptocurrencyCTA?.let { mobileCommonUtils.scrollUpForElement(it) }

            }
        }

    }

    fun verifySectionHeaderOf(section: String, listOf: List<String>) {
        when(section){
            "Cryptocurrency" -> {
                mobileCommonUtils.compareStringValue(crytoCurrencyHeader[0].text,listOf[0])
                mobileCommonUtils.horizontalScroll(cryptocurrencySection)
                mobileCommonUtils.compareStringValue(crytoCurrencyHeader[0].text,listOf[1])
                mobileCommonUtils.horizontalScroll(cryptocurrencySection)
                mobileCommonUtils.compareStringValue(crytoCurrencyHeader[0].text,listOf[2])
            }
        }

    }

//    --------------------------------tejas-------------------------

    fun loginWithCredentials(username: String, password: String){
        mobileCommonUtils.click(profileIcon, "ProfileIcon")
        mobileCommonUtils.enterDataIntoTextField(emailIDTextField, "proUser", "UserName")
        mobileCommonUtils.enterDataIntoTextField(passwordTextField, "proPassword", "Password")
        mobileCommonUtils.click(loginButton, "Login Button")
    }

    fun verifyPlayPauseIconChange(){
        var article1 = articleHeader.text
        playPauseButton.click()
        Thread.sleep(10000)
        var article2 = articleHeader.text
        if(article1.equals(article2)){
            println("pause changed to play")
        }
        playPauseButton.click()
        Thread.sleep(10000)
        article1 = articleHeader.text
        if(!article1.equals(article2)){
            println("play changed to pause")
        }

    }

    fun clickOnHeaderSearchIcon(){
        searchIcon.click()
        println(searchIcon.getCssValue("color"))
    }

    fun provideInputSearchText(){
        mobileCommonUtils.enterDataIntoTextField(searchBox, "tata", "tata")
        Runtime.getRuntime().exec("adb shell input keyevent 66")
    }

    fun checkUserRedirectedToSearchResultPage(text: String){
        var key = searchResults.text
        println(key)
        if(key.contains(text)){
            println("Navigated to search result page")
        }else{
            println("Not Navigated to search result page")
        }
    }

}