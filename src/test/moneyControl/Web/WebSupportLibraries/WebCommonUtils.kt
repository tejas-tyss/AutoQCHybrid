package moneyControl.Mobile.MobileSupportLibraries


import Util.FileUtility
import moneyControl.SupportLibraries.Report
import org.apache.commons.io.FileUtils
import org.openqa.selenium.*
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.io.File
import java.io.IOException
import java.time.Duration
import java.util.*

class WebCommonUtils(wDriver: WebDriver) {

    var wDriver = wDriver
    private var action : Actions = Actions(wDriver)
    var pageLoadStatus = ""

    /**
     * @author Janardhan
     * This method is used to click on element
     * @param element
     */
    fun click(element: WebElement, text: String) {
        try {
            waitForElementClickable(element, 10)
            element.click()
            Report.infoTest("Clicked on $text")
        } catch (e: Exception) {
            Report.failwithException(e)
        }
    }

    /**
     * @author Janardhan
     * This method will wait until element is visible
     * @param element
     */
    fun waitForElementVisibility(element: WebElement, sec: Int) {
        try{
            val wait = WebDriverWait(wDriver, Duration.ofSeconds(sec.toLong()))
            wait.until(ExpectedConditions.visibilityOf(element))
        }
        catch (e : Exception){
            Report.failTest("Element failed to Load within $sec")
        }
    }

    /**
     * @author Janardhan
     * This method is used to wait till the element is clicked
     * @param element
     */
    fun waitForElementClickable(element: WebElement, sec:Int) {
        try {
            val wait = WebDriverWait(wDriver, Duration.ofSeconds(sec.toLong()))
            wait.until(ExpectedConditions.elementToBeClickable(element))
        }
        catch (e : Exception){
            Report.failTest("Element failed to Load and not clickable within $sec")
        }
    }

    fun scrollUpForElement(element: WebElement): Boolean {
        for (i in 0..5) {
            try {
                if (element.isDisplayed) {
                    return true
                }
            } catch (e: Exception) {
            }

        }
        return false
    }

    /**
     * @author Janardhan
     * @param element
     * @return boolean value
     */
    fun isElementDisplayed(element: WebElement): Boolean {
        return try {
            element.isDisplayed()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * @author Janardhan
     * @param element
     * @return boolean value
     */
    fun isElementDisplayed(element: WebElement, sec:Long): Boolean {
        wDriver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(sec))
        try {
            element.isDisplayed()
            return true
        } catch (e: Exception) {
            return false
        }
    }


    fun hoverOnMenuItem(menuItem:String)
    {
        try
        {
            var menuElement : WebElement = wDriver.findElement(By.xpath("//a[@title='$menuItem']"))
            action.moveToElement(menuElement).perform()

        }catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }

    /*
     * This test case will scroll based on cordinates
     *
     * @author : Priyank Bhuch
     *
     */
    fun pageScroll(driver: WebDriver, x: Int, y: Int) {
        try
        {
            val jse = driver as JavascriptExecutor
            jse.executeScript("window.scrollBy($x,$y)", "")
        } catch (e: Exception) {
            Report.failwithException(e)
        }
    }

    fun pageScrollToElement(element: WebElement) {
        try
        {
            (wDriver as JavascriptExecutor).executeScript("window.scrollTo(0," + element.location.y + ")")
        } catch (e: Exception) {
           Report.failwithException(e)
        }
    }

    /*
    * This function will launch the MoneyControl page and handle all the intermediate page and land on Home Page
    *
    */
    fun launchMCURL()
    {
        try
        {
            wDriver.get(FileUtility.readDataFromPropertyFile("mcURL"))
            Report.infoTest("Money Control Web URL is being launched")
            waitForPageToLoad()
            //Thread.sleep(10000)
            var mcLink : List<WebElement> = wDriver.findElements(By.xpath("//div[@class='headtop']/span[@class='textlik']/a"))
            if (mcLink.isNotEmpty())
            {
                click(mcLink[0],"MC Link on Splash screen")
            }

        }
        catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }

    private fun waitForPageToLoad() {
        try {
            do {
                val js = wDriver as JavascriptExecutor?
                pageLoadStatus = js!!.executeScript("return document.readyState") as String

            } while (pageLoadStatus != "complete")
        } catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }

    open fun getScreenShot(screenShotPath:String): String {
        var screenshot = wDriver as TakesScreenshot
        val src: File = screenshot.getScreenshotAs(OutputType.FILE)
        val path = screenShotPath + System.currentTimeMillis() + ".png"
        val destination = File(path)
        try {
            FileUtils.copyFile(src, destination)
        } catch (e: IOException) {
          Report.failwithException(e)
        }
        return path
    }

}