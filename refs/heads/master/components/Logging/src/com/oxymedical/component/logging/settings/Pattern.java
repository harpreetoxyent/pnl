package  com.oxymedical.component.logging.settings;
import java.util.ArrayList;

import com.oxymedical.component.logging.IPattern;

/**
* This function states that Pattern implements IPattern
* and is used to search integration based on pattern
*/
public class Pattern implements IPattern
{
	public static ConfigurationSettings confingData;
	ArrayList <Field> pattern;
	
	public Pattern() 
	{
		loadConfigSettings();
	}
	/**
	* This function is used to get pattern 
	* from an arrayList
	* @return ArrayList
	*/
	public ArrayList<Field> getPattern() {
		return pattern;
	}

	private void loadConfigSettings()
	{
		// Load the config settings into the LoggingConfig Object
	}
	/**
	* This function is used to set pattern in an arrayList
	* @param ArrayList
	* @return void
	*/
	public void setPattern(ArrayList<Field> pattern) {
		this.pattern = pattern;
	}
	
}