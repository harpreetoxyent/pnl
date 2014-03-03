package com.oxymedical.core.commonData.app;

/**
 * This class should contain constant values that we need to use across
 * components that are specific to the application.
 * 
 * @author hs
 * 
 */
public class ApplicationConstant
{
	public static final String KEY_PATIENT_MRN = "PATIENTMRN";
	public static final String KEY_PATIENT_ID = "PATIENTID";
	public static final String KEY_SCHEDULE_ID = "SCHEDULEID";
	public static final String KEY_SCHEDULE_WORK_AREA = "SCHEDULEWORKAREA";
	public static final String KEY_WORKFLOW_NAME = "WORKFLOWNAME";
	public static final String KEY_NODE_NAME = "NODENAME";
	public static String[] REQUIRED_KEYS_FOR_UNIQUE_DO = {
			KEY_PATIENT_ID, 
			KEY_PATIENT_MRN,
			KEY_SCHEDULE_ID
		};
}
