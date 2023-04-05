//package test.kotlin.Util.LogUtil
//
//import org.apache.log4j.*
//import test.kotlin.Util.ConfigReader.getValue
//import java.io.IOException
//
///**
// * @author TestLink
// */
//object LogUtil {
//    var errorLogger: Logger? = null
//    var normalLogger: Logger? = null
//    var htmlLogger: Logger? = null
//    var normalFileApp: FileAppender? = null
//    var errorFileApp: FileAppender? = null
//    var htmlFileApp: FileAppender? = null
//    var conApp: ConsoleAppender? = null
//    var normalRap: RollingFileAppender? = null
//    var errorRap: RollingFileAppender? = null
//    private var isInit = false
//    var patternLayout = PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n")
//    var consolePatternLayout = PatternLayout("\tLOG-: [%m -  %d{yyyy-MM-dd HH:mm:ss a}] %n")
//    var htmlLayout = HTMLLayout()
//
//    /**
//     * @param clazz
//     */
//    fun init(clazz: Class<*>) {
//        if (!isInit) {
//            try {
//                htmlLogger = Logger.getLogger("$clazz,HtmlLogger")
//                htmlLogger!!.level = Level.INFO
//                htmlLayout.title = "Automation Logs"
//                htmlFileApp = FileAppender(htmlLayout, getValue("logHtmlFilePath"))
//                htmlFileApp!!.immediateFlush = true
//                htmlFileApp!!.append = false
//                htmlFileApp!!.activateOptions()
//                htmlLogger!!.addAppender(htmlFileApp)
//            } catch (e1: IOException) {
//                e1.printStackTrace()
//            }
//            normalLogger = Logger.getLogger("$clazz,NormalLogger")
//            normalLogger!!.level = Level.INFO
//            normalFileApp = FileAppender()
//            normalFileApp!!.layout = patternLayout
//            normalFileApp!!.file = getValue("logInfoFilePath")
//            normalFileApp!!.immediateFlush = true
//            normalLogger!!.addAppender(normalFileApp)
//            normalFileApp!!.activateOptions()
//
//            // Rolling File Appender for maximum 5 mb log file size
//            try {
//                normalRap = RollingFileAppender(patternLayout, normalFileApp!!.file)
//                normalRap!!.maxBackupIndex = 5
//                normalRap!!.maximumFileSize = 5
//                normalRap!!.activateOptions()
//            } catch (e: IOException) {
//                errorLog(LogUtil::class.java, "Exception caught", e)
//            }
//            errorLogger = Logger.getLogger("$clazz,ErrorLogger")
//            errorLogger!!.level = Level.ERROR
//            errorFileApp = FileAppender()
//            errorFileApp!!.layout = patternLayout
//            errorFileApp!!.file = getValue("logErrorFilePath")
//            errorFileApp!!.immediateFlush = true
//            errorLogger!!.addAppender(errorFileApp)
//            errorFileApp!!.activateOptions()
//            try {
//                errorRap = RollingFileAppender(patternLayout, errorFileApp!!.file)
//                normalRap!!.maxBackupIndex = 5
//                normalRap!!.maximumFileSize = 5
//                normalRap!!.activateOptions()
//            } catch (e: IOException) {
//                errorLog(LogUtil::class.java, "Exception caught", e)
//            }
//            conApp = ConsoleAppender()
//            conApp!!.layout = consolePatternLayout
//            conApp!!.target = "System.out"
//            conApp!!.activateOptions()
//            normalLogger!!.addAppender(conApp)
//            isInit = true
//        }
//    }
//
//    /**
//     * @param className
//     */
//    fun init(className: String) {
//        if (!isInit) {
//            try {
//                htmlLogger = Logger.getLogger("$className,HtmlLogger")
//                htmlLogger!!.level = Level.DEBUG
//                htmlLayout.title = "Automation Logs"
//                htmlFileApp = FileAppender(htmlLayout, getValue("logHtmlFilePath"))
//                htmlFileApp!!.immediateFlush = true
//                htmlFileApp!!.activateOptions()
//                htmlLogger!!.addAppender(htmlFileApp)
//            } catch (e1: IOException) {
//                e1.printStackTrace()
//            }
//            normalLogger = Logger.getLogger("$className,NormalLogger")
//            normalLogger!!.level = Level.INFO
//            normalFileApp = FileAppender()
//            normalFileApp!!.layout = patternLayout
//            normalFileApp!!.file = "Logs/AppLog.txt"
//            normalFileApp!!.immediateFlush = true
//            normalLogger!!.addAppender(normalFileApp)
//            normalFileApp!!.activateOptions()
//            errorLogger = Logger.getLogger("$className,ErrorLogger")
//            errorLogger!!.level = Level.DEBUG
//            errorFileApp = FileAppender()
//            errorFileApp!!.layout = patternLayout
//            errorFileApp!!.file = "Logs/ErrorLog.txt"
//            errorFileApp!!.immediateFlush = true
//            errorLogger!!.addAppender(errorFileApp)
//            errorFileApp!!.activateOptions()
//            conApp = ConsoleAppender()
//            conApp!!.layout = consolePatternLayout
//            conApp!!.target = "System.out"
//            conApp!!.activateOptions()
//            normalLogger!!.addAppender(conApp)
//            isInit = true
//        }
//    }
//
//    /**
//     * @param clazz
//     * @param message
//     */
//    fun infoLog(clazz: Class<*>, message: String?) {
//        init(clazz)
//        normalLogger!!.info(message)
//        htmlLogger!!.info(message)
//    }
//
//    /**
//     * @param className
//     * @param message
//     */
//    fun infoLog(className: String, message: String?) {
//        init(className)
//        normalLogger!!.info(message)
//        htmlLogger!!.info(message)
//    }
//
//    /**
//     * @param clazz
//     * @param message
//     * @param t
//     */
//    fun errorLog(clazz: Class<*>, message: String?, t: Throwable?) {
//        init(clazz)
//        htmlLogger!!.error(message, t)
//        errorLogger!!.error(message, t)
//        errorLogger!!.error("----------------------------------------------------------------------")
//    }
//
//    /**
//     * @param clazz
//     * @param message
//     */
//    fun errorLog(clazz: Class<*>, message: String?) {
//        init(clazz)
//        htmlLogger!!.error(message)
//        errorLogger!!.error(message)
//        errorLogger!!.error("-----------------------------------------------------------------------")
//    }
//
//    /**
//     * @param name
//     * @param message
//     */
//    fun errorLog(name: String, message: String?) {
//        init(name)
//        htmlLogger!!.error(message)
//        errorLogger!!.error(message)
//        errorLogger!!.error("-----------------------------------------------------------------------")
//    }
//}