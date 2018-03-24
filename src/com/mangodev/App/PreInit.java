package com.mangodev.App;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.mangodev.crash.DebugException;

@copyright(year = "2018")
@author(name = "Bodie Brewer")
public class PreInit {
	public PreInit() throws DebugException, IOException{
		
		Logger LOGGER = Logger.getLogger(PreInit.class);
		PropertyConfigurator.configure("log4j.properties");
		String className = this.getClass().getName().replace('.', '/');
        String classJar = this.getClass().getResource("/" + className + ".class").toString();
        if(Start.in_dev_mode() && !classJar.startsWith("jar:")){
        	LOGGER.info("Type \"yes\" then enter to debug crash. Otherwise, press enter");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String want_crash = null;
			try {
				want_crash = br.readLine();
			} catch (IOException e) {
				LOGGER.fatal("Error in reading console line");
				throw new IOException("Cannot read console line");
			}
			LOGGER.info("***" + want_crash + "***");
			if(want_crash.contains("yes")){
				LOGGER.info("Triggered Debug Crash");
				throw new DebugException();
			}
        }
	}
}
