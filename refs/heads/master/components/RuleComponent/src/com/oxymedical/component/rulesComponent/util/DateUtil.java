package com.oxymedical.component.rulesComponent.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.oxymedical.component.rulesComponent.constants.EvaluatorConstants;


public class DateUtil {	
		   
    private static final String DATE_FORMAT_MASK    = getDateFormatMask();

	
	 /** Use the simple date formatter to read the date from a string */
    public static Date parseDate(final String input) {

        final SimpleDateFormat df = new SimpleDateFormat( DATE_FORMAT_MASK );
        try {
            return df.parse( input );
        } catch ( final ParseException e ) {
            throw new IllegalArgumentException( "Invalid date input format: [" + input + "] it should follow: [" + DATE_FORMAT_MASK + "]" );
        }
    }

    /** Converts the right hand side date as appropriate */
    public static Date getRightDate(final Object object2) {
        if ( object2 == null ) {
            return null;
        }
        if ( object2 instanceof String ) {
            return parseDate( (String) object2 );
        } else if ( object2 instanceof Date ) {
            return (Date) object2;
        } else {
            throw new IllegalArgumentException( "Unable to convert " + object2.getClass() + " to a Date." );
        }
    }

    /** Check for the system property override, if it exists */
    public static String getDateFormatMask() {
        String fmt = System.getProperty( "rules.dateformat" );
        
        if ( fmt == null ) {
            fmt = EvaluatorConstants.DEFAULT_FORMAT_MASK;
        }
        return fmt;
    }

}
