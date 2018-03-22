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

@copyright(year = "2018")
@author(name = "Bodie Brewer")
public class Start {
    public Start() throws Exception{
        Logger LOGGER = Logger.getLogger(Start.class);
        PropertyConfigurator.configure("log4j.properties");
        CrashReport report = null;

        /**booleans for APP_RUNNING**/
        boolean pre_init_started = false;
        boolean init_started = false;
        boolean post_init_started = false;
        boolean pre_init_finished = false;
        boolean init_finished = false;
        boolean post_init_finished = false;
        boolean app_initilized = false;
        @Deprecated
        boolean is_crashed = false;
        boolean in_dev_mode = true;
        Exception cause_of_crash;

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
                LOGGER.error("Pre_init started: " + pre_init_started + "\n pre_init finished: " + pre_init_finished);
                LOGGER.error("init Started and finished: " + init_started + ", " + init_finished);
                LOGGER.error("Post_init started: " + post_init_started + "\n post_init finished: " + post_init_finished);
                LOGGER.error("An exception was thrown in the pre_initilization phase");
                report = CrashReport.makeCrashReport(e, "CANNOT START PRE_INIT PHASE.");
                CrashReportCategory reportcategory = report.makeCategory("ERROR TYPE");
                reportcategory.addCrashSection("NAME OF EXCEPTION", e.getCause());
                is_crashed = true;
            }
            try {
                init_started = true;
                new Init();
                init_finished = true;
                LOGGER.info("init Started and finished: " + init_started + ", " + init_finished);
            } catch (Exception e) {
            	LOGGER.error("Pre_init started: " + pre_init_started + "\n pre_init finished: " + pre_init_finished);
                LOGGER.error("init started: " + init_started + "\n init finished: " + init_finished);
                LOGGER.error("Post_init started: " + post_init_started + "\n post_init finished: " + post_init_finished);
                LOGGER.error("An exception was thrown in the initilization phase");
                report = CrashReport.makeCrashReport(e, "CANNOT START INIT PHASE.");
                CrashReportCategory reportcategory = report.makeCategory("ERROR TYPE");
                reportcategory.addCrashSection("NAME OF EXCEPTION", e.getCause());
                is_crashed = true;
            }
            try {
                post_init_started = true;
                new PostInit();
                post_init_finished = true;
                LOGGER.info("Post_init Started and finished: " + post_init_started + ", " + post_init_finished);
            } catch (Exception e) {
            	LOGGER.error("Pre_init started: " + pre_init_started + "\n pre_init finished: " + pre_init_finished);
            	LOGGER.error("init Started and finished: " + init_started + ", " + init_finished);
                LOGGER.error("Post_init started: " + post_init_started + "\n post_init finished: " + post_init_finished);
                LOGGER.error("An exception was thrown in the post_initilization phase");
                report = CrashReport.makeCrashReport(e, "CANNOT START POST_INIT PHASE.");
                CrashReportCategory reportcategory = report.makeCategory("ERROR TYPE");
                reportcategory.addCrashSection("NAME OF EXCEPTION", e.getCause());
                is_crashed = true;
            }
            app_initilized = false;
        }
        if(is_crashed && report != null){
            throw new ReportedException(report);
        } else if(is_crashed && report == null){
        	LOGGER.error("is_crashed is initalized but CrashReport is null!");
        	LOGGER.error("Cannot crash the system properly!");
        	LOGGER.error("Attempting to throw an exception");
        	throw new IllegalCrashReportException();
        } else{
        	
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
