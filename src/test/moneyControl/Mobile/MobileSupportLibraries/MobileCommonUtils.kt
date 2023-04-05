package moneyControl.Mobile.MobileSupportLibraries

import io.appium.java_client.android.AndroidDriver
import moneyControl.SupportLibraries.Report
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.PointerInput
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.util.*

class MobileCommonUtils(aDriver: AndroidDriver) {

    private val aDriver = aDriver

    /**
     * @author Janardhan
     * This method is used to click on element
     * @param element
     */
    fun click(element: WebElement, text: String) {
        try {
            waitForElementClickable(element, 10)
            element.click()
            Report.test.pass("Tapped on $text")
//            Report.passTest("Tapped on $text")
        } catch (e: Exception) {
//            Report.failwithException(e)
        }
    }

    /**
     * @author Janardhan
     * This method will wait until element is visible
     * @param element
     */
    fun waitForElementVisibility(element: WebElement, sec: Int) {
        try{
            val wait = WebDriverWait(aDriver, Duration.ofSeconds(sec.toLong()))
            wait.until(ExpectedConditions.visibilityOf(element))
        }
        catch (e : Exception){
            Report.test.fail("Element failed to Load within $sec")
        }
    }

    /**
     * @author Janardhan
     * This method is used to wait till the element is clicked
     * @param element
     */
    private fun waitForElementClickable(element: WebElement, sec:Int) {
        try {
            val wait = WebDriverWait(aDriver, Duration.ofSeconds(sec.toLong()))
            wait.until(ExpectedConditions.elementToBeClickable(element))
        }
        catch (e : Exception){
            Report.test.fail("Element failed to Load and not clickable within $sec")
        }
    }

    fun scrollUpForElement(element: WebElement): Boolean {
        for (i in 0..5) {
            try {
                if (element.isDisplayed) {
                    return true
                }
            } catch (e: Exception) {
                return false
            }
            scrollUp()
        }
        return false
    }

    fun scrollUp() {
        val height: Int = aDriver.manage().window().getSize().getHeight()
        val width: Int = aDriver.manage().window().getSize().getWidth()
        val centerX = (width / 2)
        val startY = (height * 0.6).toInt().toDouble()
        val endY = (height * 0.2).toInt().toDouble()
        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
        val swipe = org.openqa.selenium.interactions.Sequence(finger, 1)
        swipe.addAction(
            finger.createPointerMove(
                Duration.ofSeconds(0),
                PointerInput.Origin.viewport(),
                centerX,
                startY.toInt()
            )
        )
        swipe.addAction(finger.createPointerDown(0))
        swipe.addAction(
            finger.createPointerMove(
                Duration.ofMillis(700),
                PointerInput.Origin.viewport(),
                centerX,
                endY.toInt()
            )
        )
        swipe.addAction(finger.createPointerUp(0))
        aDriver.perform(listOf(swipe))
    }

    fun scrollToElement(ele: WebElement) {
        for (i in 0..9) {
            if (isElementDisplayed(ele,3)) {
                var p = ele.location
                val height: Int = aDriver.manage().window().getSize().getHeight()
                var oneForth = height / 4 * 3
                if(p.getY()>oneForth){
                    scrollUp(height-height/3)
                }
                break
            } else {
//                aDriver.findElement(By.androidUIAutomator("new UiScrollable(new UiSelector().resourceId(\"com.divum.MoneyControl:id/recycler_view\").scrollable(true)).setAsVerticalList().scrollForward()"))
                scrollUp()
                Thread.sleep(100)
            }
        }
    }

    /**
     * @author Janardhan
     * @param yCoordinarte
     */
    fun scrollUp(endY: Int) {
        val height: Int = aDriver.manage().window().getSize().getHeight()
        val width: Int = aDriver.manage().window().getSize().getWidth()
        val centerX = (width / 2)
        val startY = (height * 0.8).toInt().toDouble()
        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
        val swipe = org.openqa.selenium.interactions.Sequence(finger, 1)
        swipe.addAction(
            finger.createPointerMove(
                Duration.ofSeconds(0),
                PointerInput.Origin.viewport(),
                centerX,
                startY.toInt()
            )
        )
        swipe.addAction(finger.createPointerDown(0))
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), centerX, endY))
        swipe.addAction(finger.createPointerUp(0))
        aDriver.perform(listOf(swipe))
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
        aDriver?.manage()?.timeouts()?.implicitlyWait(Duration.ofSeconds(sec))
        try {
            element.isDisplayed()
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun horizontalScroll(ele: WebElement) {
        val width = aDriver.manage().window().size.getWidth()
        val height = ele.rect.getHeight()
        val center = ele.location.y + height / 2
        val startX = (width * 0.9).toInt().toDouble()
        val endX = (width * 0.1).toInt().toDouble()
        val finger = PointerInput(PointerInput.Kind.TOUCH, "finger")
        val swipe = org.openqa.selenium.interactions.Sequence(finger, 1)
        swipe.addAction(
            finger.createPointerMove(
                Duration.ofSeconds(0),
                PointerInput.Origin.viewport(),
                startX.toInt(),
                center
            )
        )
        swipe.addAction(finger.createPointerDown(0))
        swipe.addAction(
            finger.createPointerMove(
                Duration.ofMillis(1000),
                PointerInput.Origin.viewport(),
                endX.toInt(),
                center
            )
        )
        swipe.addAction(finger.createPointerUp(0))
        aDriver.perform(Arrays.asList(swipe))
    }

    /**
     * This function will compare 2 string values
     * @param  : actual value, expected value
     * @return : Boolean
     * @author : Priyank Bhuch
     *
     */
    fun compareStringValue(actual : String, expected : String) : Boolean
    {
        try {
            if (actual.equals(expected)){
                Report.test.pass("Both values are exactly same : $actual")
                return true
            }
            else{
                Report.test.fail("Both values are different. Actual : $actual and Expected : $expected")
            }
        }
        catch (e:Exception){
            Report.failwithException(e)
        }
        return false
    }

//    -----------------------tejas-------------------------------

    fun enterDataIntoTextField(ele: WebElement, value: String?, key: String?){
        ele.sendKeys(value)
        Report.test.pass("$key entered into text field")
    }



}