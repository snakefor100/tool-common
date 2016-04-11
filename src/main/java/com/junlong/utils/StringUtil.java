package com.junlong.utils;

import com.junlong.common.domain.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by niuniu on 2016/4/4.
 */
public class StringUtil {

//    public static String replaceSequenced( String originalStr, Object... replacementParams ) {
//
//        if ( StringUtil.isBlank( originalStr ) )
//            return Constants.EMPTY_STRING;
//        if ( null == replacementParams || 0 == replacementParams.length )
//            return originalStr;
//
//        for ( int i = 0; i < replacementParams.length; i++ ) {
//            String elementOfParams = replacementParams[i] + Constants.EMPTY_STRING;
//            if ( StringUtil.trimToEmpty( elementOfParams ).equalsIgnoreCase( "null" ) )
//                elementOfParams = Constants.EMPTY_STRING;
//            originalStr = originalStr.replace( "{" + i + "}", StringUtil.trimToEmpty( elementOfParams ) );
//        }
//
//        return originalStr;
//    }
//
//
//    public static String defaultIfBlank( String originalStr, String defaultStr ) {
//        if ( StringUtils.isBlank( originalStr ) ) {
//            return defaultStr;
//        }
//        return originalStr;
    }
//
//    public static String trimToEmpty( String originalStr ) {
//        if ( null == originalStr || originalStr.isEmpty() )
//            return Constants.EMPTY_STRING;
//        if ( originalStr.equals( Constants.WORD_SEPARATOR ) )
//            return originalStr;
//        return originalStr.trim();
//    }
//
//    public static List< String > findAllByRegex(String originalStr, String regex ) {
//
//        if ( StringUtils.isBlank( originalStr ) || StringUtils.isBlank( regex ) )
//            return null;
//
//        List< String > targetStrList = new ArrayList< String >();
//        final Pattern patternOfTargetStr = Pattern.compile( regex, Pattern.CANON_EQ );
//        final Matcher matcherOfTargetStr = patternOfTargetStr.matcher( originalStr );
//        while ( matcherOfTargetStr.find() ) {
//            targetStrList.add( StringUtil.trimToEmpty( matcherOfTargetStr.group() ) );
//        }
//        return targetStrList;
//    }
//
//    public static boolean isBlank( String... originalStrArray ) {
//        if ( null == originalStrArray || 0 == originalStrArray.length )
//            return true;
//        for ( int i = 0; i < originalStrArray.length; i++ ) {
//            if ( isBlank( originalStrArray[i] ) )
//                return true;
//        }
//        return false;
//    }
//
//    public static boolean isBlank( String originalStr ) {
//        if ( null == originalStr ) {
//            return true;
//        }
//        if ( originalStr.contains( Constants.WORD_SEPARATOR ) ) {
//            return false;
//        }
//        return trimToEmpty( originalStr ).isEmpty();
//    }
//}
