package moneyControl.SupportLibraries

import BaseClass.BaseClass
import org.apache.commons.io.FileUtils
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import java.io.File
import java.net.InetAddress
import java.net.UnknownHostException
import java.time.LocalDateTime

class CommonUtils() {

    /**
     * This function will return Random Number having current date time stamp.
     *
     * @return : String : randomNumber
     * @author : Priyank Bhuch
     */
    fun randomNumber(): String? {
        var randomNumber: String? = null
        try {
            randomNumber =
                LocalDateTime.now().toString().replace("-", "_").replace("T", "_").replace(":", "_").substring(0, 16)
            return randomNumber
        } catch (e: Exception) {
            println(e.message)
        }
        return randomNumber
    }


    /**
     * This function will return the hostname of the execution machine
     *
     * @return : hostname
     * @sample : NW18MLTEC_PRIYB
     * @author : Priyank Bhuch
     */
    public fun getHostNameOfMachine(): String {
        var hostname: String = "Unknown"
        try {
            var addr: InetAddress = InetAddress.getLocalHost()
            hostname = addr.hostAddress
        } catch (e: UnknownHostException) {
            println("Host Name cannot be resolved")
        }
        return hostname
    }

    public fun captureScreenshot(driver: WebDriver, screenshotName: String, resultFolderPath: String): String? {
        var source: File? = null
        var FileName: String? = null
        var randomTime = randomNumber()
        try {
            // If else conditions can be added for Web/Remote/Android/iOS Driver
            val ts = driver as TakesScreenshot
            source = ts.getScreenshotAs(OutputType.FILE)
            FileName = "$screenshotName$randomTime.jpg"
            val dest = "$resultFolderPath/$screenshotName$randomTime.jpg"
            val destination = File(dest)
            FileUtils.copyFile(source, destination)
            return FileName
        } catch (e: Exception) {
            println(e.message)
        }
        return FileName
    }

    /**
     * This function will compare 2 string values
     * @param  : actual value, expected value
     * @return : Boolean
     * @author : Priyank Bhuch
     *
     */
    public fun compareStringValue(actual: String, expected: String): Boolean {
        try {
            if (actual.equals(expected)) {
                Report.test.pass("Both values are exactly same : $actual")
                return true
            } else {
                Report.test.fail("Both values are different. Actual : $actual and Expected : $expected")
            }
        } catch (e: Exception) {
                Report.failwithException(e)
        }
        return false
    }

    public fun compareListOfStringValue(actual: List<String>, expected: List<String>): Boolean {
        try {
            if (actual == expected) {
                Report.passTest("Both List of Values are exactly same : $actual")
                return true
            } else {
                Report.failTest("List of Values are different. Actual : $actual and Expected : $expected")
            }
        } catch (e: Exception) {
            Report.failwithException(e)
        }
        return false
    }

}