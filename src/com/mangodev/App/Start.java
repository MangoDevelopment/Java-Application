package com.mangodev.App;

import com.mangodev.crash.CrashReport;
import com.mangodev.crash.CrashReportCategory;
import com.mangodev.crash.IllegalCrashReportException;
import com.mangodev.crash.ReportedException;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.Logger;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;

@copyright(year = "2018")
@author(name = "Bodie Brewer")
public class Start {
	boolean pre_init_started = false;
    boolean init_started = false;
    boolean post_init_started = false;
    boolean pre_init_finished = false;
    boolean init_finished = false;
    boolean post_init_finished = false;
    boolean app_initilized = false;
    @Deprecated
    boolean is_crashed = false;
    static boolean in_dev_mode = true;
    Exception cause_of_crash;
    Logger LOGGER = null;
    CrashReport report = null;
    
    public void printInitDetails(String secCrashed, Exception e3){
        report = CrashReport.makeCrashReport(e3, "CANNOT START " + secCrashed + " PHASE.");
        CrashReportCategory reportcategory = report.makeCategory("ERROR TYPE");
        reportcategory.addCrashSection("NAME OF EXCEPTION", e3.getCause());
        is_crashed = true;
        checkForCrash();
    }
    public static boolean in_dev_mode(){
    	return in_dev_mode;
    }
    public Start() throws Exception{
    	Logger LOGGER = Logger.getLogger(Start.class);
        PropertyConfigurator.configure("log4j.properties");

        String className = this.getClass().getName().replace('.', '/');
        String classJar = this.getClass().getResource("/" + className + ".class").toString();
        if(in_dev_mode && classJar.startsWith("jar:")){
            Exception e = new IllegalAccessException();
			report = CrashReport.makeCrashReport(e, "TRYING TO ACCESS APP WHILE IN DEVELOPMENT MODE.");
			cause_of_crash = new IllegalAccessException();
			is_crashed = true;
        }

        //TODO: Make to where you can init the app
        app_initilized = true;
        if(app_initilized) {

            try {
                pre_init_started = true;
                new PreInit();
                pre_init_finished = true;
                LOGGER.info("Pre_init Started and finished: " + pre_init_started + ", " + pre_init_finished);
            } catch (Exception e) {
            	printInitDetails("PostInit", e);
            }
            try {
                init_started = true;
                new Init();
                init_finished = true;
                LOGGER.info("init Started and finished: " + init_started + ", " + init_finished);
            } catch (Exception e) {
            	printInitDetails("PostInit", e);
            }
            try {
                post_init_started = true;
                new PostInit();
                post_init_finished = true;
                LOGGER.info("Post_init Started and finished: " + post_init_started + ", " + post_init_finished);
            } catch (Exception e) {
            	printInitDetails("PostInit", e);
            }
            app_initilized = false;
        }
    }
    public void checkForCrash() {
    	if(is_crashed && report != null){
    		throw new ReportedException(report);
    	} else if(is_crashed && report == null){
    		LOGGER.error("is_crashed is initalized but CrashReport is null!");
    		LOGGER.error("Cannot crash the system properly!");
    		LOGGER.error("Attempting to throw an exception");
    		throw new IllegalCrashReportException();
    	} else{
    		LOGGER.info("Checked for crash. All is well.");
    	}
    }
   
    public static void displayCrashReport(CrashReport crashReportIn)
    {
        File file2 = new File("crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
        Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());

        if (crashReportIn.getFile() != null)
        {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
            System.exit(-1);
        }
        else if (crashReportIn.saveToFile(file2))
        {
            Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
            System.exit(-1);
        }
        else
        {
            Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
            System.exit(-2);
        }
    }
}
