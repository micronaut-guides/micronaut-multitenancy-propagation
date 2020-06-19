import org.openqa.selenium.htmlunit.HtmlUnitDriver

driver = {
    HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver()
    htmlUnitDriver.javascriptEnabled = true
    htmlUnitDriver
}

environments {
    htmlUnit {
        driver = {
            HtmlUnitDriver htmlUnitDriver = new HtmlUnitDriver()
            htmlUnitDriver.javascriptEnabled = true
            htmlUnitDriver
        }
    }
}