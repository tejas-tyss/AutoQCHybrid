package moneyControl.Mobile.HomePage

import BaseClass.BaseClass
import Driver.DriverUtils
import io.appium.java_client.android.AndroidDriver
import moneyControl.Mobile.ObjectRepository.HomeScreen
import org.testng.annotations.Test

class VerifyUserRedirectedToSearchResultPage : BaseClass() {

    @Test
    fun VerifyUserRedirectedToSearchResultPageTest(){

        aDriver = DriverUtils.createDriver("android") as AndroidDriver

        var homeScreen = HomeScreen(aDriver)

        homeScreen.clickOnHeaderSearchIcon()
        homeScreen.provideInputSearchText()
        homeScreen.checkUserRedirectedToSearchResultPage("tata")

    }
}