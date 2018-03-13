package com.mangodev.crash;

public class ReportedException extends RuntimeException{

	private static final long serialVersionUID = 5006776632564055464L;
	/** The crash report associated with this exception */
    private final CrashReport crashReport;

    public ReportedException(CrashReport report)
    {
        this.crashReport = report;
    }

    /**
     * Gets the CrashReport wrapped by this exception.
     */
    public CrashReport getCrashReport()
    {
        return this.crashReport;
    }

}
