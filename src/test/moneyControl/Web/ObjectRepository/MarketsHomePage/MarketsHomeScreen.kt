package moneyControl.Web.ObjectRepository.MarketsHomePage

import Util.FileUtility
import moneyControl.Mobile.MobileSupportLibraries.WebCommonUtils
import moneyControl.SupportLibraries.API
import moneyControl.SupportLibraries.CommonUtils
import moneyControl.SupportLibraries.Report
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy
import org.openqa.selenium.support.PageFactory

class MarketsHomeScreen(var wDriver: WebDriver){

    private var webCommonUtils=WebCommonUtils(wDriver)
    private var commonUtils=CommonUtils()

    init {
        webCommonUtils = WebCommonUtils(wDriver)
        PageFactory.initElements(wDriver, this)
    }


    @FindBy(xpath="//a[contains(@href,'crypto-market/market-movers') and text()=' View More ']")
    private var viewMoreLinkCryptoSection : MutableCollection<WebElement>? = null

    @FindBy(xpath = "//div[@id='crypto_tgnse']//tbody/tr//p[1]//a")
    private var cryptoTopGainersList : List<WebElement>?=null

    fun scrollToSectionOfMarketsHomePage(sectionName : String)
    {
        try
        {
            when (sectionName.uppercase())
            {
                "MARKET ACTION"->
                {}
                "STOCK ACTION"->
                {}
                "US STOCK ACTION"->
                {}
                "CRYPTO ACTION"->
                {
                    //webCommonUtils.scrollUpForElement(viewMoreLinkCryptoSection!!.first())
                    webCommonUtils.pageScroll(wDriver,0,1800)
                }

            }
            Report.infoTest("Scrolled to $sectionName on the page")
        }
        catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }

    fun selectTab(tabName:String)
    {

    }

    fun clickViewMoreOfSection(sectionName:String)
    {

    }

    /**
     * This function will click on any row of any table under Markets home page
     * @author: Priyank Bhuch
     * @param : div id of the parent of table, row index to be clicked, table name
     */
    fun clickRowOfMarketsScreenTables(tableID : String, rowIndex:Int, tableName:String)
    {
        try
        {
            var cellElement : WebElement = wDriver.findElement(By.xpath("(//div[@id='$tableID']//table//tbody/tr[$rowIndex]/td[1]//a)[1]"))
            webCommonUtils.click(cellElement,"Row $rowIndex of Table : $tableName")
        }
        catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }

    /**
     * This function will validate list of Top crypto on Markets Home Page
     */
    fun validateListOfCrypto(sectionName: String, countOfTop:Int, currency : String)
    {
        var topDesiredListAPI = ArrayList<String>()
        var topDesiredListUI = ArrayList<String>()
        try
        {
           var responseValue = API.getResponse(FileUtility.getAPIRequest("crypto_TopGainer"))
           var jsonObj = org.json.JSONObject(responseValue)
           var topCrypto : MutableList<Any>? = jsonObj.getJSONObject("body").getJSONObject("tableData").getJSONArray(currency.lowercase()).toList()

           for (i in 0 until countOfTop)
           {
               var cryptoVal : Any? = (topCrypto?.get(i) as ArrayList<*>)[2]
               topDesiredListAPI.add(cryptoVal.toString())
               var cryptoRow:WebElement = wDriver.findElement(By.xpath("(//div[@id='crypto_tgnse']//tbody/tr//p[1]//a)[$i+1]"))
               topDesiredListUI.add(cryptoRow.text)
           }

            commonUtils.compareListOfStringValue(topDesiredListUI,topDesiredListAPI)
        }
        catch (e:Exception)
        {
            Report.failwithException(e)
        }
    }
}

