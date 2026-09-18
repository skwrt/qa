package qtriptest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class ReportSingleton {

    private static ExtentReports report;

    private ReportSingleton() {
    }

    public static ExtentReports getReport(){
        if(report==null){
            report= new ExtentReports(System.getProperty("user.dir")+"/OurExtentReport.html");
            report.loadConfig(new File("/home/crio-user/workspace/sandeepkanodia14-ME_QTRIP_QA_V2/app/extent_customization_configs.xml")); 
        }
        return report;

    }

    public static String capture(WebDriver driver) throws IOException {

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    
        String path = System.getProperty("user.dir")
                + "/reports/screenshots/"
                + System.currentTimeMillis() + ".png";
    
        File dest = new File(path);
        FileUtils.copyFile(src, dest);
    
        return path;
    }


}