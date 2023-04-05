package Driver

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class DeviceUtils {

    fun getConnectedDeviceID(): String? {
        var currentdeviceID = ""
        try {
            val rt = Runtime.getRuntime()
            var p = rt.exec("adb shell getprop ro.serialno")
            var `is`: InputStream? = p.inputStream
            var reader = BufferedReader(InputStreamReader(`is`))
            currentdeviceID = reader.readLine().toString()
            reader.close()
            if (currentdeviceID.isEmpty()) {
                println("Currently no Mobile Device is connected")
            } else if (currentdeviceID.contains("EMULATOR")) {
                p = rt.exec("adb get-serialno")
                `is` = p.inputStream
                reader = BufferedReader(InputStreamReader(`is`))
                currentdeviceID = reader.readLine().toString()
                reader.close()
                println("Emulator has been launched for this execution")
            } else {
                //Report.infoTest("Physical Device with UDID : "+deviceID+" is connected");
                println("Physical Device with UDID : $currentdeviceID is connected")
            }
            return currentdeviceID
        } catch (e: Exception) {
            println("Default Device Emulator is being used")
            currentdeviceID = "emulator-5554"
            //Report.failTest("Failed to get the Device ID");
            //System.out.println(e.getMessage());
        }
        return currentdeviceID
    }

    fun getConnectedDeviceAndroidVersion(): String? {
        var currentAndroidVersion = ""
        try {
            val rt = Runtime.getRuntime()
            val p = rt.exec("adb shell getprop ro.build.version.release")
            val `is`: InputStream = p.inputStream
            val reader = BufferedReader(InputStreamReader(`is`))
            currentAndroidVersion = reader.readLine().trim { it <= ' ' }
            reader.close()
            if (currentAndroidVersion == "error: no devices/emulators found" || currentAndroidVersion.isEmpty()) {
                //Report.infoTest("Currently no Mobile Device is connected");
                println("Currently no Mobile Device is connected")
                currentAndroidVersion = ""
            } else {
                //Report.infoTest("Physical Device with Android Version "+androidVersion+" is connected");
                println("Physical Device with Android Version $currentAndroidVersion is connected")
            }
        } catch (e: Exception) {
            currentAndroidVersion = "No Device Connected"
            //Report.failTest("Failed to get the Android Version of Connected Device");
        }
        return currentAndroidVersion
    }

    fun getConnectedDeviceName(): String? {
        var currentDeviceName = ""
        try {
            val rt = Runtime.getRuntime()
            val p = rt.exec("adb shell getprop ro.product.model")
            val `is`: InputStream = p.inputStream
            val reader = BufferedReader(InputStreamReader(`is`))
            currentDeviceName = reader.readLine().trim { it <= ' ' }
            reader.close()
            if (currentDeviceName.contains("Android SDK")) {
                println("Currently Emulator is connected")
                currentDeviceName = "Android Emulator"
            }
        } catch (e: Exception) {
            currentDeviceName = "No Device Connected"
            //Report.failTest("Failed to get the Device Name of Connected Device");
            //System.out.println(e.getMessage());
        }
        return currentDeviceName
    }

}