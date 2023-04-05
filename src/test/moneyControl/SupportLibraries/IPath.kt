package Util

/**
 * @author DanishR
 * This interface defines the common resources path
 */
interface IPath {
    companion object {
        val sDirPath = System.getProperty("user.dir")
        val homePath = System.getProperty("user.home")
        val propertyFilePath = "$sDirPath/src/main/resources/PropertyFile.properties"
        val ListOFAPIFilePath = "$sDirPath/src/main/resources/ListOfAPIs.properties"
        val nodejsPath = "$homePath/AppData/Roaming/npm/node_modules/appium/build/lib/main.js"
        val loadXMLConfig = "$sDirPath/src/main/resources/extent-config.xml"
        val htmlPath = "./Reports/HtmlReport.html"
        val MCLogoURL = "<img src=\"https://images.moneycontrol.com/static-mcnews/2017/03/Moneycontrol_Logo-1-770x433.jpg?impolicy=website&width=770&height=431.png\" style=\"width: 140px; height: 50px;\">"
    }
}