package moneyControl.Mobile.HomePage

import BaseClass.BaseClass
import Driver.DriverUtils
import io.appium.java_client.android.AndroidDriver
import moneyControl.Mobile.ObjectRepository.HomeScreen
import org.testng.annotations.Test

class VerifyPPButton: BaseClass() {

    @Test
    fun verify(){

        aDriver = DriverUtils.createDriver("android") as AndroidDriver

        var homeScreen = HomeScreen(aDriver)

        homeScreen.verifyPlayPauseIconChange()

        aDriver.quit()
    }
}