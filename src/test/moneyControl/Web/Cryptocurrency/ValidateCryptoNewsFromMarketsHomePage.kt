package moneyControl.Web.Cryptocurrency

import BaseClass.BaseClass
import Driver.DriverUtils
import Util.FileUtility
import moneyControl.Mobile.MobileSupportLibraries.WebCommonUtils
import moneyControl.Web.ObjectRepository.HomePage.HomeScreen
import moneyControl.Web.ObjectRepository.MarketsHomePage.MarketsHomeScreen
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testng.annotations.Test

class ValidateCryptoNewsFromMarketsHomePage : BaseClass() {

    @Test()
    fun validateCrypto()
    {
        wDriver = DriverUtils.createDriver(FileUtility.readDataFromPropertyFile("incognitoMode")) as WebDriver

        var homeScreenObj = HomeScreen(wDriver)
        var marketsHomeObj = MarketsHomeScreen(wDriver)
        var webCommonUtils = WebCommonUtils(wDriver)

        webCommonUtils.launchMCURL()

        homeScreenObj.navigateFromMegaMenu("Markets Home")
        marketsHomeObj.scrollToSectionOfMarketsHomePage("CRYPTO ACTION")
        marketsHomeObj.validateListOfCrypto("Top Gainers",4,"INR")
        //marketsHomeObj.validateListOfCrypto("Top Losers",4,"USD")
        marketsHomeObj.clickRowOfMarketsScreenTables("crypto_tgnse",1,"Crypto Action")


        wDriver.quit()

    }
}