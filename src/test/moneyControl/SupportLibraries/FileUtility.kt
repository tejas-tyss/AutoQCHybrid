package Util

import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * @author Janardhan
 * This Class Contains Method to read the data From property file
 */
object FileUtility {
    /**
     * @author Janardhan
     * Method is used to read the data from the property file using Properties,FileInputStream java.util,java.io
     * @param key
     * @return String
     */
    fun readDataFromPropertyFile(key: String?): String {
        val prop = Properties()
        try {
            prop.load(FileInputStream(IPath.propertyFilePath))
        }
        catch (e: Exception) {
            println("Error in finding the property file for the given Key -> $key")
        }
        return prop.getProperty(key)
    }

    /**
     * This function will create folder
     *
     * @param : Path where folder is to be created
     * @return : Boolean
     * @author : Priyank Bhuch
     * */
    fun createFolder(folderPath: String): Boolean {
        var result = false
        try {
            val directory = File(folderPath)
            if (!directory.exists()) {
                result = directory.mkdirs()
            }
        } catch (e: Exception) {
            println("Error while creating the specific folder. Location $folderPath. Error message ${e.message}")
        }
        return result
    }

    fun getAPIRequest(key: String?): String {
        val prop = Properties()
        try {
            prop.load(FileInputStream(IPath.ListOFAPIFilePath))
        }
        catch (e: Exception) {
            println("Error in finding the API property file for the given Key -> $key")
        }
        return prop.getProperty(key)
    }
}