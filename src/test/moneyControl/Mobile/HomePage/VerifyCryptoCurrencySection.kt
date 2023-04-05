package AppLaunch

import BaseClass.BaseClass
import Driver.DriverUtils
import io.appium.java_client.android.AndroidDriver
import moneyControl.Mobile.ObjectRepository.HomeScreen
import org.testng.annotations.Test

class VerifyCryptoCurrencySection : BaseClass() {

    @Test(description = "MC_386 : Verify sections available for Cryptocurrency in Home page")
    fun verifyCryptoCurrencySec(){
        aDriver = DriverUtils.createDriver("android") as AndroidDriver
        var homeScreen = HomeScreen(aDriver)

        homeScreen.tapOnHomeIcon()
        homeScreen.scrollToSection("Cryptocurrency")
        homeScreen.verifySectionHeaderOf("Cryptocurrency", listOf("TOP CRYPTOS", "TOP GAINERS", "TOP LOSERS"))

        aDriver.quit()

    }
}