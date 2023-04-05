package Driver

import Util.FileUtility
import Util.IPath
import io.appium.java_client.android.AndroidDriver
import io.appium.java_client.android.options.UiAutomator2Options
import io.appium.java_client.service.local.AppiumDriverLocalService
import io.appium.java_client.service.local.AppiumServiceBuilder
import io.appium.java_client.service.local.flags.GeneralServerFlag
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.firefox.FirefoxProfile
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.net.URL
import java.time.Duration

object DriverUtils {

    private var deviceUtils = DeviceUtils()
    private lateinit var options: UiAutomator2Options
    private lateinit var service: AppiumDriverLocalService
    var driver: AndroidDriver? = null

    fun createDriver(platform: String): Any?
    {
        when (platform.lowercase())
        {
            "android" -> {
                var aDriver: AndroidDriver?
                startAppiumServer()
                options = UiAutomator2Options()
                options.setPlatformName("Android")
                    .setDeviceName(deviceUtils.getConnectedDeviceName())
                    .setUdid(deviceUtils.getConnectedDeviceID())
                    .setAppPackage(FileUtility.readDataFromPropertyFile("mcAppPackage"))
                    .setAppActivity(FileUtility.readDataFromPropertyFile("mcAppActivity"))
                    .setSkipLogCapture(true)
                    .setNoReset(true)
                    .setAutoWebview(true)
                val url = URL("http://127.0.0.1" + ":" + FileUtility.readDataFromPropertyFile("port") + "/wd/hub")
                aDriver = AndroidDriver(url, options)
                aDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100))
                return aDriver
            }

            "chrome" -> {
                if (platform.equals("chrome")) {
                    WebDriverManager.chromedriver().setup()
                    var wDriver: WebDriver
                    var options = ChromeOptions()
                    options.addArguments("--remote-allow-origins=*")
                    options.addArguments("--window-size=1920,1080")
                    options.setPageLoadStrategy(PageLoadStrategy.NONE)
//                    options.addArguments("--headless")
//                options.setExperimentalOption("excludeSwitches", listOf("enable-automation"))
                    options.addArguments("--ignore-certificate-errors")
                    options.addArguments("--test-type")
                    wDriver = ChromeDriver(options)
                    wDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60))
                    wDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5))
                    wDriver.manage().deleteAllCookies()
                    wDriver.manage().window().maximize()
                    return wDriver

                }
            }
//                else if (platform.equals("Firefox")) {
//                    var wDriver: FirefoxDriver
//                    val firefoxOptions = FirefoxOptions()
//                    val allProfiles = FirefoxProfile()
//                    val myProfile = allProfiles.firefoxOptions.setProfile(myProfile)
//                    firefoxOptions.addArguments("--ignore-certificate-errors", "--ignore-ssl-errors")
//                    firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL)
//                    myProfile.setAcceptUntrustedCertificates(true)
//                    myProfile.setAssumeUntrustedCertificateIssuer(false)
//
//                    WebDriverManager.firefoxdriver().setup()
//                    wDriver = FirefoxDriver(firefoxOptions)
//                    wDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20))
//
//                    wDriver.manage().deleteAllCookies()
//                    wDriver.manage().window().maximize()
//                    return wDriver
//
//                }
                "remote"-> {
                    val wDriver: RemoteWebDriver
                    val chromeOptions = ChromeOptions()
                    chromeOptions.setExperimentalOption("useAutomationExtension", false)
                    chromeOptions.addArguments("--window-size=1920,1080")
                    chromeOptions.addArguments("version")
                    chromeOptions.addArguments("--headless")

                    chromeOptions.setCapability("browserVersion", "100")
                    chromeOptions.setCapability("platformName", "Windows")
                    // Showing a test name instead of the session id in the Grid UI
                    chromeOptions.setCapability("se:name", "My simple test")
                    // Other type of metadata can be seen in the Grid UI by clicking on the
                    // session info or via GraphQL
                    chromeOptions.setCapability("se:sampleMetadata", "Sample metadata value")
                    wDriver = RemoteWebDriver(URL("http://localhost:4444"), chromeOptions)
                    wDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20))
                    wDriver.manage().deleteAllCookies()
                    wDriver.manage().window().maximize()
                    return wDriver
                }
                "incognito"->
                {
                    WebDriverManager.chromedriver().setup()
                    var wDriver:WebDriver
                    var options = ChromeOptions()
                    options.addArguments("--remote-allow-origins=*")
                    options.addArguments("--window-size=1920,1080")
                    options.setPageLoadStrategy(PageLoadStrategy.NONE)
//                    options.addArguments("--headless")
//                options.setExperimentalOption("excludeSwitches", listOf("enable-automation"))
                    options.addArguments("--ignore-certificate-errors")
                    options.addArguments("--test-type")
                    options.addArguments("--incognito")
                    wDriver = ChromeDriver(options)
                    wDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60))
                    wDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5))
                    wDriver.manage().deleteAllCookies()
                    wDriver.manage().window().maximize()
                    return wDriver

                }
            }
        return driver
    }

    fun startAppiumServer() {
        val environment = HashMap<String, String>()
        environment["PATH"] = System.getenv("PATH")
        service = AppiumServiceBuilder()
            .withArgument({ "--base-path" }, "/wd/hub")
            .withAppiumJS(File(IPath.nodejsPath))
            .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
            .withArgument(GeneralServerFlag.LOG_LEVEL, "error")
            .withEnvironment(environment)
            .usingPort(4726)
//            .usingAnyFreePort()
            .build()
        service.start()
    }

    fun stopAppiumServer(){
        service.stop()
    }

    fun closeWebdriver(driver:ChromeDriver){
        driver.close()
    }
}


