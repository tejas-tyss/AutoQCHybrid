package BaseClass

import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.ios.IOSDriver
import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.testng.ITestContext
import org.testng.ITestResult
import org.testng.annotations.*
import java.lang.reflect.Method

open class BaseClass {


    companion object {
//        @JvmStatic
//        protected var logger = ThreadLocal<ExtentTest>()
//        protected var threadLocalDriver = ThreadLocal<WebDriver>()
//        val wDriver: WebDriver
//        get() = threadLocalDriver.get()
        lateinit var aDriver: AndroidDriver
        lateinit var wDriver : WebDriver
        lateinit var iDriver : IOSDriver
        lateinit var rDriver : RemoteWebDriver
    }

    @BeforeSuite
    fun beforeSuite() {


    }

    @BeforeClass
    open fun onStart(context: ITestContext) {


    }


    //kill the previously opened node js and chrome wDriver sessions, adb ,

    @BeforeMethod
    open fun beforeMethod(method: Method) {
//        LogUtil.infoLog(BasePage::class.java, "-------------------Before method--------------- : ")
//        val wDriver: WebDriver = getMyDriver()!!
//        threadLocalDriver.set(wDriver)
//        beforeInvocation(method)

    }

    @AfterMethod
    open fun afterMethod(result: ITestResult) {
//        LogUtil.infoLog(BasePage::class.java, "-------------------After method--------------- : ")
//
//        getResult(result)
//        wDriver.close()
//        threadLocalDriver.remove()

    }



    @AfterClass
    open fun onFinish(context: ITestContext) {

    }

    @AfterSuite
    open fun onComplete(){

    }

}