/***********************************************************************
 * @author             :		Janardhan P
 * @description        : 		Listener Class to Generate Report based on ItestListener abstract methods
 * @methods            :
 */
package moneyControl.SupportLibraries



import Util.IPath
import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.ExtentTest
import com.aventstack.extentreports.MediaEntityBuilder
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import main.kotlin.com.moneycontrol.listner.ExtentManager
import org.testng.ITestContext
import org.testng.ITestListener
import org.testng.ITestResult
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


open class Report : ITestListener {

    companion object {
        lateinit var extent: ExtentReports
        lateinit var test: ExtentTest
        lateinit var sExcelDate: String
        lateinit var sExcelPath: String
        lateinit var screenShotPath: String
        lateinit var sStartTime: String
        var sStatus = ArrayList<String>()
        var sStartMethodTime = ArrayList<String>()
        lateinit var parentTest: ExtentTest
        var startTime: Long = 0
        var endtTime: Long = 0
        lateinit var className: String
        lateinit var current_className: String
        var parentCount = 0

        private val extentReportForTestMethod = ThreadLocal<ExtentTest>()

        /**
         * Description : Fetches the object of Extent reports for the test methods.
         * @author janardhan
         * @return ExtentTest
         */
        fun getExtentReportForTestMethods(): ExtentTest? {
            return extentReportForTestMethod.get()
        }

        /**
         * Description :Sets the Extent Test for the test methods.
         * @author janardhan
         * @param ExtentTest
         */
        fun setExtentReportsForTestMethods(extent: ExtentTest) {
            extentReportForTestMethod.set(extent)
        }

        private val extentReportForClassMethod = ThreadLocal<ExtentTest>()

        /**
         * Description : Fetches the object of Extent Test for precondition
         * @author janardhan
         * @return ExtentTest
         */
        fun getExtentReportForClassMethods(): ExtentTest? {
            return extentReportForClassMethod.get()
        }

        /**
         * Description :Sets the Extent Test for the class method
         * @author janardhan
         * @param ExtentTest
         */
        fun setExtentReportsForClassMethods(extent: ExtentTest) {
            extentReportForClassMethod.set(extent)
        }

        private val extentReportForPrecondition = ThreadLocal<ExtentTest>()

        /**
         * Description : Fetches the object of Extent Test for precondition
         * @author janardhan
         * @return ExtentTest
         */
        fun getExtentReportForPrecondition(): ExtentTest? {
            return extentReportForPrecondition.get()
        }

        /**
         * Description :Sets the Extent Test with the precondition
         * @author janardhan
         * @param ExtentTest
         */
        fun setExtentReportsForPrecondition(extent: ExtentTest) {
            extentReportForPrecondition.set(extent)
        }

        private val parentReport = ThreadLocal<ExtentTest>()

        /**
         * Description :Sets the Extent Test for Parent node in report.
         * @author janardhan
         * @param ExtentTest
         */
        fun setParentReport(extentTest: ExtentTest) {
            parentReport.set(extentTest)
        }

        /**
         * Description : Fetches the object of Extent Test for parent node in Report.
         * @author janardhan
         * @return ExtentTest
         */
        fun getParentReport(): ExtentTest {
            return parentReport.get()
        }

        private val testReport = ThreadLocal<ExtentTest>()

        /**
         * Description : Fetches the object of Extent Test for Test Report.
         * @author janardhan
         * @return ExtentTest
         */
        fun getTestReport(): ExtentTest {
            return testReport.get()
        }

        /**
         * Description :Sets the Extent Test for Report.
         * @author janardhan
         * @param ExtentTest
         */
        fun setTestReport(extent: ExtentTest) {
            testReport.set(extent)
        }

        private val exteReport = ThreadLocal<ExtentTest>()

        /**
         * Description :Sets the Extent Test for Report.
         * @author janardhan
         * @param ExtentTest
         */
        fun setReport(extentTest: ExtentTest) {
            exteReport.set(extentTest)
        }

        /**
         * Description : Fetches the object of Extent Test for Report.
         *
         * @author janardhan
         * @return ExtentTest
         */
        fun getReport(): ExtentTest? {
            return exteReport.get()
        }

        fun passTest(stepDetail : String)
        {
            try {
                getTestReport().pass(MarkupHelper.createLabel(stepDetail, ExtentColor.GREEN))
            }
            catch (e :Exception)
            {
                println("Pass Exception on $stepDetail Test")
            }

        }

        fun infoTest(stepDetail: String)
        {
            try{
                getTestReport().info(MarkupHelper.createLabel(stepDetail,ExtentColor.AMBER))
            }catch (e:Exception)
            {
                println("Info Exception on $stepDetail Test")
            }
        }

        fun warningTest(stepDetail: String)
        {
            try {
                getTestReport().warning(MarkupHelper.createLabel(stepDetail,ExtentColor.ORANGE))
            }
            catch (e:Exception)
            {
                println("Warning Exception on $stepDetail Test")
            }
        }

        fun failTest(stepDetail: String)
        {
            try {
                getTestReport().fail(MarkupHelper.createLabel(stepDetail,ExtentColor.RED))
            }
            catch (e:Exception)
            {
                println("Fail Exception on $stepDetail Test")
            }
        }

        fun failwithException(exception : Exception)
        {
            try {
                getTestReport().fail(MarkupHelper.createLabel(exception.message,ExtentColor.RED))
            }
            catch (e:Exception)
            {
                println("Failed with Exception")
            }
        }

        fun passTestWithScreenShot(stepDetail : String,screenShotPath:String)
        {
            try {
                getTestReport().pass(MarkupHelper.createLabel(stepDetail, ExtentColor.GREEN))
                getTestReport().pass(MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build())
            }
            catch (e :Exception)
            {
                println("Pass Exception on $stepDetail Test")
            }
        }

        fun failTestWithScreenShot(stepDetail: String, screenShotPath:String)
        {
            try {
                getTestReport().fail(MarkupHelper.createLabel(stepDetail,ExtentColor.RED))
                getTestReport().fail(MediaEntityBuilder.createScreenCaptureFromPath(screenShotPath).build())
            }
            catch (e:Exception)
            {
                println("Fail Exception on $stepDetail Test")
            }
        }


    }

    /*
	 * @author:Janardhan P
	 * Description: On Test Starts Create a report based on @Test Method Name
	 */

    override fun onTestStart(result: ITestResult) {
        println("onTestStart")
        val methodName: String = result.getMethod().getMethodName().toString()
        current_className = result.getTestClass().getName().toString()
//        if (parentCount == 0 || !className.equals(current_className)) {
        if (parentCount == 0) {
            className = result.getTestClass().getName().toString()
            className = className.substring(className.lastIndexOf(".") + 1, className.length)
            println(className)
            parentTest = extent.createTest(className)
            setParentReport(parentTest)
            parentCount = parentCount + 1
        }
        val description: String = result.getMethod().getDescription()
        test = getParentReport().createNode(methodName, description).assignCategory("Sanity").assignDevice("Android")
        setTestReport(test)
        startTime = result.getStartMillis()
        sStartMethodTime.add(startTime.toString() + "")
        test.info("Launched MoneyControl App")
        test.info("$methodName has started")
    }

    /*
	 * @author:Janardhan P
	 * Description: On Test Success Write Status Passed to Extent Report
	 */
    override fun onTestSuccess(result: ITestResult) {
        endtTime = result.getEndMillis()
        sStatus.add("Completed")
        test.info(
            MarkupHelper.createLabel(
                result.getMethod().getMethodName().toString() + " case has Completed",
                ExtentColor.PURPLE
            )
        )
        try {
            extent.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
	 * @author:Janardhan P
	 * Description: On Test Failure Write Status Failed to Extent Report With Label
	 * RED
	 */
    override fun onTestFailure(result: ITestResult) {
        val temp:String
        sStatus.add("Failed")
        test.fail(MarkupHelper.createLabel(" Test script has FAILED", ExtentColor.RED))
        try {
//                temp = commonConfig.getScreenShot(screenShotPath)
//                test.fail(result.throwable.message, MediaEntityBuilder.createScreenCaptureFromPath(temp).build())
        } catch (e2: Exception) {
            e2.printStackTrace()
        }
    }

    /*
	 * @author:Janardhan P
	 *
	 * Description: On Test Skipped Write Status Skipped to Extent Report With Label
	 * YELLOW
	 */
    override fun onTestSkipped(result: ITestResult) {
        sStatus.add("Skipped")
        test.skip(MarkupHelper.createLabel("$className test script has SKIPPED", ExtentColor.YELLOW))
        try {
            extent.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onTestFailedButWithinSuccessPercentage(result: ITestResult) {
        // TODO Auto-generated method stub
    }

    /*
	 * @author:Janardhan P
	 *
	 * Description: On Suite Starts 1. Create Directories for Extent Reports, Excel
	 * Report and Screenshots based on system time 2. Add Execution Info Based
	 * Execution Type
	 */
    override fun onStart(context: ITestContext) {
        val date = Date()
        val sdf = SimpleDateFormat("dd-MM-yyyy-hh-mm")
        val sDate: String = sdf.format(date)
        sExcelDate = sDate
        sStartTime = sDate
//        val testOutputDir: String = IPath.sDirPath + "/test-output"
        val htmlDir: String = IPath.sDirPath + "/reports" + "/Run-" + sDate + "/extent/"
        val excelDir: String = IPath.sDirPath + "/reports" + "/Run-" + sDate + "/excel/"
        sExcelPath = excelDir
        screenShotPath = IPath.sDirPath + "/reports" + "/Run-" + sDate + "/extent/screenshots/"
        println(htmlDir)

        val file = File(htmlDir)
        if (!file.exists()) {
            file.mkdirs()
            println("--extent folder created")
        }
        val filepath = htmlDir + "extent_" + sDate + ".html"
        println(filepath)
        val file1 = File(filepath)
        if (!file1.exists()) {
            try {
                file1.createNewFile()
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace()
            }
        }

        // Setting ScreenShot Report Location
        val file3 = File(screenShotPath)
        if (!file3.exists()) {
            file3.mkdirs()
            println("--screenshot folder created")
        }
         val ext = ExtentManager()
        extent = ext.createInstance(file1.toString())
    }

    override fun onFinish(context: ITestContext) {
        extent.flush()
    }


}