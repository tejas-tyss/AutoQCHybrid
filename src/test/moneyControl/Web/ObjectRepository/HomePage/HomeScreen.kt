package moneyControl.Web.ObjectRepository.HomePage

import moneyControl.Mobile.MobileSupportLibraries.WebCommonUtils
import moneyControl.SupportLibraries.Report
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class HomeScreen(wDriver :WebDriver) {

    var wDriver = wDriver
    var webCommonUtils:WebCommonUtils

    init {
        webCommonUtils = WebCommonUtils(wDriver)
        PageFactory.initElements(wDriver, this)
    }



    @FindBy(xpath = "//ul[@class='clearfix main_nav']//li//a[@title='Cryptocurrency']")
    private lateinit var cryptocurrencyLink : WebElement

    @FindBy(xpath = "//div[@class='FL']/a[@title='CryptoCurrency']")
    private lateinit var cryptocurrencyBreadcrum : WebElement

    @FindBy(xpath = "//a[contains(@href,'stocksmarketsindia') and @title='Home']")
    private lateinit var marketsHomeMegaMenu : WebElement

    @FindBy(xpath = "//div[@class='main_cont']//h1[text()='Markets']")
    private lateinit var marketsBreadcrum : WebElement

    /**
     * This function will navigate to a screen from Home page Mega Menu
     * @author : Priyank Bhuch
     * @sample : CRYPTO, MARKETS HOME, MUTUAL FUNDS HOME
     * */
    fun navigateFromMegaMenu(sectionName : String)
    {
        try {
            when(sectionName.uppercase())
            {
                "CRYPTO","CRYPTOCURRENCY","CRYPTO CURRENCY"->
                {
                    webCommonUtils.hoverOnMenuItem("Markets")
                    webCommonUtils.click(cryptocurrencyLink,"Mega menu Cryptocurrency")
                    webCommonUtils.waitForElementVisibility(cryptocurrencyBreadcrum,2)
                }
                "MARKETS HOME"->
                {
                    webCommonUtils.hoverOnMenuItem("Markets")
                    webCommonUtils.click(marketsHomeMegaMenu,"Mega Menu Markets > Home")
                    webCommonUtils.waitForElementVisibility(marketsBreadcrum,2)
                }
                "NEWS HOMEPAGE"->
                {

                }
            }
            Report.infoTest("Navigated to $sectionName from Mega Menu")
        }
        catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }

    fun navigateToUrl()
    {
        try {


        }
        catch (e:Exception)
        {

        }
    }
}