package com.accuweather.reports;

import com.aventstack.extentreports.Status;

/**
 * Data holder for steps
 * 
 * @author vishnu
 * @version 1.0
 */
public class StepReportData {

	

	/**
	 * Represents the step status
	 * 
	 * @author rvishnu
	 *
	 */
	public enum StepStatus {
		PASS(Status.PASS, "Passed"), FAIL(Status.FAIL, "Failed"), SKIP(Status.SKIP, "No Run"), INFO(Status.INFO,
				"Info"), WARN(Status.WARNING, "Warning"), ERROR(Status.ERROR, "error");

		private final Status status;

		private StepStatus(Status status, String almStatus) {
			this.status = status;

		}

		/**
		 * @return the status
		 */
		public Status getStatus() {
			return status;
		}
	}

	
}
