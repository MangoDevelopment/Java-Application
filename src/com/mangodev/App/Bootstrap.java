package com.mangodev.App;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@copyright(year = "2018")
@author(name = "Bodie Brewer")
public class Bootstrap {
    public static final PrintStream SYSOUT = System.out;
    public static void printToSYSOUT(String message)
    {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        SYSOUT.println("[" + timeStamp + "][SYSTEM/" + methodName + "]" + message);
    }
}
