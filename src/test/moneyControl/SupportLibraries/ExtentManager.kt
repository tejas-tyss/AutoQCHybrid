package main.kotlin.com.moneycontrol.listner

import Util.IPath
import com.aventstack.extentreports.ExtentReports
import com.aventstack.extentreports.reporter.ExtentSparkReporter

class ExtentManager {
    lateinit var extent: ExtentReports

    fun getInstance() : ExtentReports {
        if (extent == null)
            createInstance(IPath.htmlPath)
        return extent
    }

    fun createInstance(fileName: String): ExtentReports {
        val extent = ExtentReports()
        val sparkReporter = ExtentSparkReporter(fileName)
        extent.attachReporter(sparkReporter)
        sparkReporter.loadXMLConfig(IPath.loadXMLConfig)
        return extent
    }
}