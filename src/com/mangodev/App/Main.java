package com.mangodev.App;

import com.mangodev.crash.CrashReport;
import com.mangodev.crash.CrashReportCategory;
import com.mangodev.crash.ReportedException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import java.util.concurrent.TimeUnit;

@copyright(year = "2018")
@author(name = "Bodie Brewer")
public class Main {
    /*MAIN METHOD*/
    public static void main(String[] args){
        Logger LOGGER = Logger.getLogger(Main.class);
        System.out.println("dsf");
        PropertyConfigurator.configure("log4j.properties");
        System.out.println("23434");
        try {
            LOGGER.info("Starting main method");
            //TODO: create main method
            new Start();
			LOGGER.info("Finished Main Method");
			TimeUnit.SECONDS.sleep(1);
        } catch (ReportedException reportedexception) {
                LOGGER.fatal("Reported exception thrown!", (Throwable)reportedexception);
                Start.displayCrashReport(reportedexception.getCrashReport());
        } catch(Exception e2){
            CrashReport r = CrashReport.makeCrashReport(e2, "The Start() method had an exception thrown.");
            CrashReportCategory rc = r.makeCategory("Exception Names");
            rc.addCrashSection("Exception Name", e2.getCause()  );
            Start.displayCrashReport(r);
        }
    }
}
