/*
 *
 */
package com.travel.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;


/**
 * 자주 사용하는 {@link String} 유틸리티 모음 클래스.
 * <p>이 클래스는 코어 자바에서 제공하는 {@link String} 과 {@link StringBuilder}
 * 클래스에서 제공하는 기능들을 사용하기 쉽게 재정의 하였습니다.
 * @version 1.0.1
 */
@Slf4j
public abstract class StringUtils {

	//---------------------------------------------------------------------
	// String 포맷 관련 메서드(Method) 모음
	//---------------------------------------------------------------------
	/**
	 * 전달받은 문자열{@code String}이 비어있는지 확인합니다.
	 * <p>이 메서드는 Object를 파라미터로 받아, {@code null} 이거나 공백 문자인지 비교합니다.
	 * <p>파라미터로 전달되는 Object가 non-null이거나 non-String일 경우에는 {@code true}를 반환하지 않습니다.
	 * <p><pre class="code">
	 * StringUtils.isEmpty(null) = false;
	 * StringUtils.isEmpty("") = false;
	 *
	 * StringUtils.isEmpty("  ") = true;
	 * StringUtils.isEmpty("text") = true;
	 * </pre>
	 * @param {@link String}으로 사용되는 str
	 * @since 1.0.0
	 */
	public static boolean isEmpty(Object str) {
		return org.springframework.util.StringUtils.isEmpty(str);
	}

	public static String nvl(Object inputObject) {
		return (String) nvl((Object) inputObject, (Object) "");
	}

	/**
	 * 주어진 문자열이 null 또는 공백일 경우 참 반환<br><br>
	 *
	 * StringUtils.isEmpty("") = true
	 *
	 * @param str 문자열
	 * @return null 또는 공백일 경우 true
	 */
	public static boolean isEmpty(String str) {
		boolean bRet = false;
		if(str != null && str.length() != 0) {
			if(str.equals("null")) bRet = true;
		}else{
			if(str == null || str.length() == 0) bRet = true;
		}

		return bRet;
	}

	public static boolean isEmptyPrice(String str) {
		boolean bRet = true;
		if(str != null && str.length() != 0 && !"0".equals(str))
			bRet = false;
		return bRet;
	}

	/** 동의서 코드 존재여부를 체크합니다. */
	public static boolean isAgreeCode(String code, String key) {
		boolean bRet = false;
		String[] pageArray = code.split("\\|");

		for(String item : pageArray) {
			if(key.equals(item)) {
				bRet = true;
				break;
			}
		}
		return bRet;
	}

	/**
	 * 두개의 문자열을 영문 대소문자를 무시하고 같은지 비교한다.<br />
	 *
	 * <pre>
	 * StringUtils.equalsIgnoreCase(null, null)   = true
	 * StringUtils.equalsIgnoreCase(null, "abc")  = false
	 * StringUtils.equalsIgnoreCase("abc", null)  = false
	 * StringUtils.equalsIgnoreCase("abc", "abc") = true
	 * StringUtils.equalsIgnoreCase("abc", "ABC") = true
	 * </pre>
	 *
	 * @param str1 첫번째 문자열
	 * @param str2 두번째 문자열
	 * @return 두 개의 문자열을 영문 대소문자를 무시하고 비교하여 같으면 true, 아니면 false를 반환
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return org.apache.commons.lang.StringUtils.equalsIgnoreCase(str1, str2);
	}

	/**
	 * 전달받은 {@code CharSequence}이 {@code null}이거나 길이(length)가 0 이상인지 확인합니다.
	 * <p>Note: 이 메서드는 전달받은 str이 완전한 공백({@code null} or {@code ""})인지 확인합니다.
	 * <p><pre class="code">
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param {@code String}타입의 str을 받아 {@code null}이거나 공백 문자인지 확인합니다.
	 * @return {@code String}이 {@code null}이 아니거나 length가 0이상일 경우 {@code true}를 반환합니다.
	 * @see #hasText(String)
	 * @since 1.0.0
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 전달받은 {@code CharSequence}이 {@code null}이거나 길이(length)가 0 이상인지 확인합니다.
	 * <p>Note: 이 메서드는 전달받은 str이 완전한 공백({@code null} or {@code ""})인지 확인합니다.
	 * <p><pre class="code">
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param {@code String}타입의 str을 받아 {@code null}이거나 공백 문자인지 확인합니다.
	 * @return {@code String}이 {@code null}이 아니거나 length가 0이상일 경우 {@code true}를 반환합니다.
	 * @see #hasLength(CharSequence)
	 * @see #hasText(String)
	 * @since 1.0.0
	 */
	public static boolean hasLength(String str) {
		return (str != null && !str.isEmpty());
	}

	/**
	 * 전달받은 {@code CharSequence}에 <em>text</em>가 포함되어 있는지 확인합니다.
	 * <p>전달 받은 str이 {@code null}이거나 공백문자 이거나, 무의미한 공백({@code ""})으로만 이루어져 있을 경우
	 * {@code false}를 반환합니다.
	 * <p><pre class="code">
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param {@code CharSequence}타입의 str({@code null}도 가능합니다).
	 * @return {@code CharSequence}타입이 {@code null}이거나 공백을 제외한 문자열의 길이(length)가 0이상일 경우
	 * {@code true}를 반환합니다. (공백(whitespace)로만 이뤄진 문자열은 {@code false}를 반환합니다)
	 * @see #hasText(String)
	 * @since 1.0.0
	 */
	public static boolean hasText(CharSequence str) {
		return (hasLength(str) && containsText(str));
	}

	/**
	 * 전달받은 {@code CharSequence}에 <em>text</em>가 있는지 확인합니다.
	 * <p>전달 받은 str이 {@code null}이거나 공백문자 이거나, 무의미한 공백({@code ""})으로만 이루어져 있을 경우
	 * {@code false}를 반환합니다.
	 * <p><pre class="code">
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param {@code CharSequence}타입의 str({@code null}도 가능합니다).
	 * @return {@code CharSequence}타입이 {@code null}이거나 공백을 제외한 문자열의 길이(length)가 0이상일 경우
	 * {@code true}를 반환합니다. (공백(whitespace)로만 이뤄진 문자열은 {@code false}를 반환합니다)
	 * @see #hasText(CharSequence)
	 * @since 1.0.0
	 */
	public static boolean hasText(String str) {
		return (hasLength(str) && containsText(str));
	}

	/*
	 * !! private Method.
	 * Modifier를 private 레벨 이상으로 설정하지 말아주세요
	 */
	private static boolean containsText(CharSequence str) {
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>Removes a substring only if it is at the begining of a source string,
	 * otherwise returns the source string.</p>
	 *
	 * <p>A <code>null</code> source string will return <code>null</code>.
	 * An empty ("") source string will return the empty string.
	 * A <code>null</code> search string will return the source string.</p>
	 *
	 * <pre>
	 * StringUtils.removeStart(null, *)      = null
	 * StringUtils.removeStart("", *)        = ""
	 * StringUtils.removeStart(*, null)      = *
	 * StringUtils.removeStart("www.domain.com", "www.")   = "domain.com"
	 * StringUtils.removeStart("domain.com", "www.")       = "domain.com"
	 * StringUtils.removeStart("www.domain.com", "domain") = "www.domain.com"
	 * StringUtils.removeStart("abc", "")    = "abc"
	 * </pre>
	 *
	 * @param str  the source String to search, may be null
	 * @param remove  the String to search for and remove, may be null
	 * @return the substring with the string removed if found,
	 *  <code>null</code> if null String input
	 * @since 2.1
	*/
	public static String removeStart(String str, String remove) {
		if (isEmpty(str) || isEmpty(remove)) {
			return str;
		}
		if (str.startsWith(remove)){
			return str.substring(remove.length());
		}
		return str;
	}


	//---------------------------------------------------------------------
	// String Array 관련 메서드(Method) 모음
	//---------------------------------------------------------------------

	/**
	 * 전달받은 문자열 배열({@code strArr})에 문자열({@code str})을 요소로 추가합니다.
	 * <p> Note: 파라미터{@code str}에 대한 whitespace를 확인하지 않습니다.
	 * <br>전달받은 문자열 배열이 {@code null}일 경우, 새 문자열 배열을 생성하고 {@code str}을 요소로 추가합니다.
	 *
	 * @param strArr
	 * @param str
	 * @return copy된 새 문자열 배열을 반환합니다({@code null}은 반환하지 않습니다).
	 * @since 1.0.0
	 */
	public static String[] addStringToArray(String[] strArr, String str) {
		if( strArr == null || strArr.length == 0 ) {
			return new String[] {str};
		}

		String[] newArr = new String[strArr.length + 1];
		System.arraycopy(strArr, 0, newArr, 0, strArr.length);
		newArr[strArr.length] = str;
		return newArr;
	}

	/**
	 * 전달받은 String 배열인 {@code strArr}를 List<String>으로 변환합니다.
	 * <p>Note: strArr가 {@code null}이거나 비어있는 배열일 경우 비어있는 {@link List}를 반환합니다.
	 * @param {@link ArrayList}로 변환할 {@link String} 배열
	 * @return {@code ArrayList<String>}을 반환합니다.
	 * 전달받은 strArr이 {@code null}이거나 비어있는 배열일 경우 {@code null}을 반환하지 않고
	 * 비어있는 {@link List}를 반환합니다.
	 * @since 1.0.0
	 */
	public static List<String> convertArrayToList(String[] strArr) {
		List<String> resultList = new ArrayList<>();
		if( strArr == null ) {
			return resultList;
		}

		return Arrays.asList(strArr);
	}

	/**
	 * 전달받은 {@code String} {@code Collection}을 {@code String}배열로 변환합니다..
	 * <p>{@code Collection}에는 반드시 {@code String} 요소만 있어야 합니다.
	 * @param 문자열 배열로 변환할 {@code String} {@code Collection}
	 * @return {@code String} 배열
	 * @since 1.0.0
	 */
	public static String[] toStringArray(Collection<String> collection) {
		if (collection == null) {
			return null;
		}

		return collection.toArray(new String[collection.size()]);
	}

	/**
	 * <p>낙타 표기법(camelCase)의 문자열을 언더스코어 표기법으로 변경합니다.</p>
	 * <pre>
	 *  hyunkwonIsSexy --> hyunkwon_is_sexy
	 * </pre>
	 *
	 * @param str 낙타 표기법으로 표현된 문자열
	 * @return underscore type String
	 */
	public static String camelCaseToUnderscore(String str) {
		Matcher m = Pattern.compile("(?<=[a-z])[A-Z]").matcher(str);

		StringBuffer sb = new StringBuffer();
		while (m.find())
			m.appendReplacement(sb, "_"+m.group().toLowerCase());

		m.appendTail(sb);
		return sb.toString();
	}


	/**
	 * <p>문자열을 낙타 표기법(camelCase) 변경합니다.</p>
	 * <pre>
	 *  honda_hitomi_is_cute --> hondaHitomiIsCute
	 * </pre>
	 *
	 * @param str
	 * @return camelCase type String
	 */
	public static List<Map<String, Object>> inputCamelText(List<Map<String,Object>> list){
		String key = "";
	    Object value = "";
	    Iterator<Entry<String,Object>> iterator = null;

	    List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

	    Entry<String,Object> entry = null;
	    int listSize = list.size();
	    for(int i=0; i<listSize; i++){
	    	Map<String,Object> inputMap = new HashMap<>();
	        iterator = list.get(i).entrySet().iterator();
	        while(iterator.hasNext()){
	            entry = iterator.next();
	            key = entry.getKey().toString();
	            value = entry.getValue().toString();
	            iterator.remove();
	            inputMap.put(convert2CamelCase(key), value);
	        }
	        returnList.add(inputMap);
	    }

	    return returnList;
	}

	public static String convert2CamelCase(String underScore) {
		if (underScore.indexOf('_') < 0	&& Character.isLowerCase(underScore.charAt(0)))
			return underScore;

		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = underScore.length();

		for (int i = 0; i < len; i++) {
			char currentChar = underScore.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	public static String encodeFileNm(String fileName, String browser) {

	    String encodedFilename = null;
	    if ("MSIE".equals(browser)) {
	      try {
	        encodedFilename = URLEncoder.encode(fileName, "UTF-8");
	      } catch (UnsupportedEncodingException e) {
	        log.error(e.getMessage());
	      }
	    } else if ("Firefox".equals(browser)) {
	      try {
	        encodedFilename =

	            "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
	      } catch (UnsupportedEncodingException e) {
	    	  log.error(e.getMessage());
	      }
	    } else if ("Opera".equals(browser)) {
	      try {
	        encodedFilename =

	            "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
	      } catch (UnsupportedEncodingException e) {
	    	  log.error(e.getMessage());
	      }
	    } else if ("Chrome".equals(browser)) {
	      StringBuffer sb = new StringBuffer();
	      for (int i = 0; i < fileName.length(); i++) {
	        char c = fileName.charAt(i);
	        if (c > '~') {
	          try {
	            sb.append(URLEncoder.encode("" + c, "UTF-8"));
	          } catch (UnsupportedEncodingException e) {
	        	  log.error(e.getMessage());
	          }
	        } else {
	          sb.append(c);
	        }
	      }
	      encodedFilename = sb.toString();
	    } else if ("Safari".equals(browser)) {
	      try {
	        encodedFilename =

	            "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
	      } catch (UnsupportedEncodingException e) {
	    	  log.error(e.getMessage());
	      }
	    } else {
	      try {
	        encodedFilename = URLEncoder.encode(fileName, "UTF-8");
	      } catch (UnsupportedEncodingException e) {
	    	  log.error(e.getMessage());
	      }
	    }

	    return encodedFilename;
	}

	/**
	 * <p>CharSequence에 숫자만 포함되어있는지 확인합니다.
	 * 소숫점은 유니코드 숫자가 아니며 {@code false}를 반환합니다.
	 * </p>
	 *
	 * <p>{@code null}은 {@code false}를 반환합니다..
	 * 공백 Charsequence (length()=0) 또한 {@code false}를 반환합니다.</p>
	 *
	 * <p>이 메서드는 양수 또는 음수의 선행 부호를 허용하지 않습니다.
	 * 또한 String이 아래 메서드를 통과하더라도, 이후에 Integer.parseInt 또는 Long.parseLong으로 구문 분석 할 때 NumberFormatException을 생성 할 수 있습니다
	 * </p>
	 *
	 * <pre>
	 * StringUtils.isNumeric(null)   = false
	 * StringUtils.isNumeric("")     = false
	 * StringUtils.isNumeric("  ")   = false
	 * StringUtils.isNumeric("123")  = true
	 * StringUtils.isNumeric("\u0967\u0968\u0969")  = true
	 * StringUtils.isNumeric("12 3") = false
	 * StringUtils.isNumeric("ab2c") = false
	 * StringUtils.isNumeric("12-3") = false
	 * StringUtils.isNumeric("12.3") = false
	 * StringUtils.isNumeric("-123") = false
	 * StringUtils.isNumeric("+123") = false
	 * </pre>
	 *
	 * @param cs  the CharSequence to check, may be null
	 * @return {@code true} CharSequence가 {@code null}이 아니고 모두 숫자일 경우
	 * @author Eddie Cho
	 * @since 1.0.0
	 */
	public static boolean isNumeric(final CharSequence cs) {
		if (isEmpty(cs)) {
			return false;
		}
		final int sz = cs.length();
		for (int i = 0; i < sz; i++) {
			if (!Character.isDigit(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>{@code str}로 넘어오는 문자열을 통째로 URI인코딩 합니다.</p>
	 * <p>Note: 이 메서드는 매개변수로 넘어오는 문자열을 통째로 URI인코딩 합니다.</p>
	 *
	 * <p>8bit 코드페이지가 없거나 16진수로 나눠질 수 없는 문자열들은 {@code UnsupportedEncodingException}를 발생시킬 수 있습니다.
	 * 이 경우에는 매개변수로 넘어온 {@code str}을 그대로 반환합니돠.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.encodeURIComponent(null)             = ""
	 * StringUtils.encodeURIComponent("")               = ""
	 * StringUtils.encodeURIComponent("조현권")           = "%EC%A1%B0%ED%98%84%EA%B6%8C"
	 * StringUtils.encodeURIComponent("Eddie Cho")      = "Eddie%20Cho"
	 * StringUtils.encodeURIComponent("?name=야부키 나코") = "%3Fname%3D%EC%95%BC%EB%B6%80%ED%82%A4%20%EB%82%98%EC%BD%94"
	 * </pre>
	 *
	 * @author Eddie Cho
	 * @param str URI인코딩할 문자열
	 * @return URI인코딩 된 문자열
	 */
	public static String encodeURIComponent(String str) {
		if (isEmpty(str)) return "";
		String result;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			result = str;
		}
		return result;
	}

	/**
	 * <p>{@code str}로 넘어오는 문자열을 통째로 URI디코딩 합니다.</p>
	 * <p>Note: 이 메서드는 매개변수로 넘어오는 문자열을 통째로 URI디코딩 합니다.</p>
	 *
	 * <p>퍼센트 인코딩이 되어있지 않거나 8비트 코드페이지를 지원하지 않는 문자열들은 {@code UnsupportedEncodingException}를 발생시킬 수 있습니다.
	 * 이 경우에는 매개변수로 넘어온 {@code str}을 그대로 반환합니돠.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.encodeURIComponent(null)                          = ""
	 * StringUtils.encodeURIComponent("")                            = ""
	 * StringUtils.encodeURIComponent("%EC%A1%B0%ED%98%84%EA%B6%8C") = "조현권"
	 * StringUtils.encodeURIComponent("Eddie%20Cho")                 = "Eddie Cho"
	 * StringUtils.encodeURIComponent("%3Fname%3D%EC%95%BC%EB%B6%80%ED%82%A4%20%EB%82%98%EC%BD%94") = "?name=야부키 나코"
	 * </pre>
	 *
	 * @author Eddie Cho
	 * @param str URI디코딩할 문자열
	 * @return URI디코딩 된 문자열
	 */
	public static String decodeURIComponent(String str) {
		if (isEmpty(str)) return "";
		String result;
		try {
			result = URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			result = str;
		}
		return result;
	}

	/**
	 * <p>매개변수 {@code str1}과 {@code str2}를 비교합니다.</p>
	 *
	 * @param str1
	 * @param str2
	 *
	 * @return 매개변수로 받은 두 문자열의 비교
	 */
	public static boolean equals(String str1, String str2) {
		if( str1 == null ) return str1 == str2;
		else return str1.equals(avoidNull(str2).trim());
	}

	public static String avoidNull(String str1) {
		if(str1 != null && !"null".equals(str1) && !"".equals(str1))
			return str1;
		else
			return "";
	}
	public static String avoidNull(String str1, String str2) {
		return isEmpty(str1) ? str2 : str1;
	}

	/**
	 * Mske biz number format.
	 * 사업자번호 형식 변환
	 *
	 * @param src the src
	 * @return the string
	 */
	public static String mskeBizNumberFormat(String src) {
		if(src == null) {
			return "";
		}
		String tmp = src.replaceAll("-", "");
		if(tmp.matches("([0-9]{3})([0-9]{2})([0-9]{5})$")) {
			return tmp.replaceFirst("([0-9]{3})([0-9]{2})([0-9]{5})$", "$1-$2-$3");
		}
		return src;
	}

	public static String makePhoneNumberFormat(String src) {
		if (src == null) {
			return "";
		}
		src = src.replaceAll("-", "");
		if (src.length() == 8) {
			return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
		} else if (src.length() == 12) {
			return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
		}
		return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	}

	public static String makeNumberFormat(String value, String pattern) {
		if (isEmpty(value)) return "";

		String str = value.toString().replaceAll("\\D", "");
		if (isEmpty(str)) return "";

		String ret = "";
		int pos = 0;
		for (int i=0; i<pattern.length() ; i++) {
			if ("#".equals(pattern.substring(i,i+1))) {
				ret += str.substring(pos,pos+1);
				pos++;
				if (pos >= str.length()) return ret;
			} else {
				ret += pattern.substring(i,i+1);
			}
		}
		return ret;
	}

	public static String makeDecimalFormat(BigDecimal val, String pattern) {
		DecimalFormat df = new DecimalFormat(pattern);
		return df.format(val);
	}

	/**
	 * 금액표시형식
	 * @param s
	 * @return
	 */
	public static String makeMeneyFormat(String s) {
		if (isEmpty(s)) return "";
		String str = s.replaceAll("\\D", "");
		if (isEmpty(str)) return "";
		DecimalFormat formatter = new DecimalFormat("###,###");
		return formatter.format(Integer.parseInt(str));
	}

	public static String leftPad(String str, int size, String padStr) {
		return padString(str, size, padStr, true);
	}

	public static String rightPad(String str, int size, String padStr) {
		return padString(str, size, padStr, false);
	}

	private static String padString(String str, int size, String padStr, boolean isLeft) {
		if (str == null) {
			return null;
		}
		int originalStrLength = str.length();

		if (size < originalStrLength) {
			return str;
		}

		int difference = size - originalStrLength;

		String tempPad = "";
		if (difference > 0) {
			if (padStr == null || "".equals(padStr)) {
				padStr = " ";
			}
			do {
				for (int j = 0; j < padStr.length(); j++) {
					tempPad += padStr.charAt(j);
					if (str.length() + tempPad.length() >= size) {
						break;
					}
				}
			} while (difference > tempPad.length());
			if (isLeft) {
				str = tempPad + str;
			} else {
				str = str + tempPad;
			}
		}

		return str;
	}

	public static String addComma(double num) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(num);
	}


	public static String trim(String s) {
		return org.springframework.util.StringUtils.trimWhitespace(s);
	}

	/**
	 * 해당문자열의 사이즈 만큼 앞에서부터 자른다.<br><br>
	 *
	 * StringUtils.splitHead("Thinktree Java Test", 3) = "Thi"
	 *
	 * @param str 문자열
	 * @param size 문자열을 자를 길이
	 * @return 길이만큼 앞에서부터 자른 문자열
	 */
	public static String splitHead(String str, int size) {
		if (str == null) {
			return "";
		}
		if (str.length() > size) {
			str = str.substring(0, size);
		}
		return str;
	}

	/**
	 * 주어진 String 객체에 대해서 주어진 길이만큼 앞부분을 떼어 반환한다.<br>
	 * 주어진 길이보다 주어진 String의 길이가 작을 경우에는 주어진 String을 그대로 반환한다.<br>
	 * 떼어낸 앞부분의 뒤에 "..."을 붙여서 반환한다.<br><br>
	 *
	 * StringUtils.splitHead("12345678", 3) = "123..."
	 *
	 * @param str
	 *            the String to get the leftmost characters from, may be null
	 * @param len
	 *            the length of the required String
	 * @return the leftmost characters with ellipsis, null if null String input
	 */
	public static String splitHeadWithEllipsis(String str, int len) {
		if (str == null) {
			return null;
		} else if (len <= 0 || str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len) + "...";
		}
	}

	public static String convertNewLine(String s) {
		return avoidNull(s).replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
	}


	/**
	 * Masking 처리
	 *
	 */
	public static class masking {
		/** FIXME - Masking 처리를 위한 함수(Carrier PC 에 CoTopComponent 클래스에 있는데 여기 있는게 맞을둡?) */
		public static String getMaskingText(String maskingStr) {
			//참고 : https://ssodang.tistory.com/entry/%EA%B0%9C%EC%9D%B8%EC%A0%95%EB%B3%B4-%EB%A7%88%EC%8A%A4%ED%82%B9%EC%B2%98%EB%A6%AC-%ED%9C%B4%EB%8C%80%ED%8F%B0%EB%B2%88%ED%98%B8-%EC%9D%B4%EB%A9%94%EC%9D%BC
			String emailRegex = "^[\\.a-zA-Z0-9_\\-]+@[a-zA-Z0-9_\\-\\.]+\\.[a-zA-Z0-9_\\-]{2}(\\.[a-zA-Z0-9]{2,}|[a-zA-Z0-9]*)$";	//이메일 정규식
			String phoneRegex = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";	//휴대전화 정규식
			String txtRegex = "^[a-zA-Z0-9]*$";	//일반텍스트

			if (Pattern.compile(emailRegex).matcher(maskingStr).matches()) {
				//email
				return getMaskingTextEmailType(maskingStr);
			} else if (Pattern.compile(phoneRegex).matcher(maskingStr).matches()) {
				//phone
				return getMaskingTextPhoneType(maskingStr);
			} else if (Pattern.compile(txtRegex).matcher(maskingStr).matches()) {
				//text
				return getMaskingTextNormalType(maskingStr);
			}
			return maskingStr;
		}

		/** FIXME - email 형식 텍스트 마스킹 함수 */
		public static String getMaskingTextEmailType(String str) {
			String regex = "\\b(\\S+)+@(\\S+.\\S+)";
			Matcher matcher = Pattern.compile(regex).matcher(str);
			if (matcher.find()) {
				String id = matcher.group(1);	//도메인을 제외한 아이디를 찾는다.(@ 앞에 문자열)
				int len = id.length();
				if (len < 3) {
					//3자리 미만의 경우 전체 마스킹 처리(**@domain.com)
					char[] c = new char[len];
					Arrays.fill(c, '*');
					return str.replace(id, String.valueOf(c));
//				} else if (len == 3) {
//					//3자리인 경우 뒤의 2자리 마스킹 처리(a**@domain.com)
//					return str.replaceAll("\\b(\\S+)[^@][^@]+@(\\S+)", "$1**@$2");
				} else {
					//3자리 이상의 경우 앞의 2자리를 제외하고 전체 마스킹 처리(ab*******@domain.com)
					String replaceRegex = "\\b(\\S+)";
					String replacement = "$1";
					int replaceLen = len - 2;
					for (int i = 0; i < replaceLen; i++) {
						replaceRegex += "[^@]";
						replacement += "*";
					}
					replaceRegex += "+@(\\S+)";
					replacement += "@$2";
					return str.replaceAll(replaceRegex, replacement);
				}
			}
			return str;
		}

		/** FIXME - 휴대전화번호('-' 포함) 마스킹 함수 */
		public static String getMaskingTextPhoneType(String str) {
			//가운데 번호만 마스킹 처리(010-****-1234)
			String regex = "(01(?:0|1|[6-9]))-(\\d{3,4})-\\d{4}$";
			Matcher matcher = Pattern.compile(regex).matcher(str);
			if (matcher.find()) {
				String replaceTarget = matcher.group(2);
				char[] c = new char[replaceTarget.length()];
				Arrays.fill(c, '*');
				return str.replace(replaceTarget, String.valueOf(c));
			}
			return str;
		}

		/** FIXME - 일반 텍스트(영문, 숫자) 마스킹 함수 */
		public static String getMaskingTextNormalType(String str) {
			int len = str.length();
			if (len < 3) {
				//3자리 이하의 경우 전체 마스킹 처리(***)
				char[] c = new char[len];
				Arrays.fill(c, '*');
				return str.replace(str, String.valueOf(c));
			} else {
				//3자리 이상의 경우 앞의 2자리를 제외하고 전체 마스킹 처리(ab*******)
				String replaceRegex = "\\b(\\S+)";
				String replacement = "$1";
				int replaceLen = len - 2;
				for (int i = 0; i < replaceLen; i++) {
					replaceRegex += "[^@]";
					replacement += "*";
				}
				return str.replaceAll(replaceRegex, replacement);
			}
		}

		/**
		 * Masked Name.
		 *
		 * @param str the str
		 * @return the string
		 */
		public static String name (String str) {

			if(str == null || str.length() < 2) {
				return str;
			}

			String replaceString = str;

			String pattern = "";
			if(str.length() == 2) {
				pattern = "^(.)(.+)$";
			} else {
				pattern = "^(.)(.+)(.)$";
			}

			Matcher matcher = Pattern.compile(pattern).matcher(str);

			if(matcher.matches()) {
				replaceString = "";

				for(int i=1;i<=matcher.groupCount();i++) {
					String replaceTarget = matcher.group(i);
					if(i == 2) {
						char[] c = new char[replaceTarget.length()];
						Arrays.fill(c, '*');

						replaceString = replaceString + String.valueOf(c);
					} else {
						replaceString = replaceString + replaceTarget;
					}

				}
			}

			return replaceString;
		}

		/**
		 * Phone num.
		 * 전화번호 마스킹 처리
		 *
		 * @param str the str
		 * @return the string
		 */
		public static String phoneNum (String str) {
			if(str == null) {
				return str;
			}
			String replaceString = str;

			Matcher matcher = Pattern.compile("^(\\d{2,3})-?(\\d{3,4})-?(\\d{4})$").matcher(str);

			if(matcher.matches()) {
				replaceString = "";

				boolean isHyphen = false;
				if(str.indexOf("-") > -1) {
					isHyphen = true;
				}

				for(int i=1;i<=matcher.groupCount();i++) {
					String replaceTarget = matcher.group(i);
					if(i == 2) {
						char[] c = new char[replaceTarget.length()];
						Arrays.fill(c, '*');

						replaceString = replaceString + String.valueOf(c);
					} else {
						replaceString = replaceString + replaceTarget;
					}

					if(isHyphen && i < matcher.groupCount()) {
						replaceString = replaceString + "-";
					}
				}
			}

			return replaceString;
		}

		public static String email(String str) {
			if(str == null) {
				return str;
			}
			String replaceString = str;

			Matcher matcher = Pattern.compile("^(..)(.*)([@]{1})(.*)$").matcher(str);

			if(matcher.matches()) {
				replaceString = "";

				for(int i=1;i<=matcher.groupCount();i++) {
					String replaceTarget = matcher.group(i);
					if(i == 2) {
						char[] c = new char[replaceTarget.length()];
						Arrays.fill(c, '*');

						replaceString = replaceString + String.valueOf(c);
					} else {
						replaceString = replaceString + replaceTarget;
					}
				}

			}

			return replaceString;
		}

		public static String ip(String str) {
			String replaceString = str;

			Matcher matcher = Pattern.compile("^([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})\\.([0-9]{1,3})$").matcher(str);

			if(matcher.matches()) {
				replaceString = "";

				for(int i=1;i<=matcher.groupCount();i++) {
					String replaceTarget = matcher.group(i);
					if(i == 3) {
						char[] c = new char[replaceTarget.length()];
						Arrays.fill(c, '*');

						replaceString = replaceString + String.valueOf(c);
					} else {
						replaceString = replaceString + replaceTarget;
					}
					if(i < matcher.groupCount()) {
						replaceString =replaceString + ".";
					}
				}
			}

			return replaceString;
		}
	}

	/**
	 * 랜덤 문자 생성 클래스
	 *
	 */
	public static class random {

		// 휴대전화 인증 번호 길이
		private static int certNumLength = 6;

		//
	    private static final char[] characterTable = {
	    		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
                };

	    // 영대소문자 + 숫자 + 특수문자
	    private static final char[] passwordTable =  { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
                'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
                '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };



		/**
		 * Cert number.
		 * 휴대전화 인증번호 생성
		 *
		 * @return the string
		 */
		public static String certNumber() {
			Random random = new Random(System.currentTimeMillis());

	        int range = (int)Math.pow(10,certNumLength);
	        int trim = (int)Math.pow(10, certNumLength-1);
	        int result = random.nextInt(range)+trim;

	        if(result>range){
	            result = result - trim;
	        }

	        return String.valueOf(result);
		}

		/**
		 * Alphabet number.
		 * 문자 + 숫자 랜덤 생성
		 *
		 * @param len the len
		 * @return the string
		 */
		public static String alphabetNumber(int len) {
	        Random random = new Random(System.currentTimeMillis());
	        int tablelength = characterTable.length;
	        StringBuffer buf = new StringBuffer();

	        for(int i = 0; i < len; i++) {
	            buf.append(characterTable[random.nextInt(tablelength)]);
	        }

	        return buf.toString();
		}

		/**
		 * Character.
		 * 문자 + 숫자 + 특수문자
		 * 패스워드 재발급등에서 사용한다.
		 *
		 * @param len the len
		 * @return the string
		 */
		public static String character(int len) {
	        Random random = new Random(System.currentTimeMillis());
	        int tablelength = passwordTable.length;
	        StringBuffer buf = new StringBuffer();

	        for(int i = 0; i < len; i++) {
	            buf.append(passwordTable[random.nextInt(tablelength)]);
	        }

	        return buf.toString();
		}

	}

	private StringUtils () {};	// SINGLETON

	public static String getRandomString(String type, int length) {
		String ret = new String();

		Random rnd = new Random();

		String key = "abcdefghijklmnopqrstuvwxyz0123456789";

		for (int i=0; i<length; i++) {
			int r = rnd.nextInt(36);
			ret += key.substring(r, r+1);
		}

		if ("hi".equals(type)) ret = ret.toUpperCase();
		return ret;
	}

	public static String getCommaPrice(String val) {
		val = val.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");
		return val;
	}

	/** make file random string(s.e.c.r.e.t.K.e.y) */
	public static String getRandomStr(int size) {
		if(size > 0) {
			char[] tmp = new char[size];
			for(int i=0; i<tmp.length; i++) {
				int div = (int) Math.floor( Math.random() * 2 );

				if(div == 0) { // 0이면 숫자로
					tmp[i] = (char) (Math.random() * 10 + '0') ;
				}else { //1이면 알파벳
					tmp[i] = (char) (Math.random() * 26 + 'A') ;
				}
			}
			return new String(tmp);
		}
		return "ERROR : Size is required.";
	}

	/**
	 * 문자열이 <code>null</code>이면 공백를 반환하고 아니면 <code>str</code>을 반환한다.<br />
	 *
	 * <pre>
	 * StringUtils.defaultString(null)  = ""
	 * StringUtils.defaultString("")    = ""
	 * StringUtils.defaultString("bat") = "bat"
	 * </pre>
	 *
	 * @param str 문자열
	 * @return 결과문자열, 입력문자열이 null일경우 공백
	 */
	public static String defaultString(String str) {
		return org.apache.commons.lang.StringUtils.defaultString(str);
	}

	 /**
	 * 문자열을 숫자로 변환하여 리턴한다.<br><br>
	 *
	 * StringUtils.string2integer("14") = 14
	 *
	 * @param str
	 *            string representation of a number
	 * @return integer integer type of string
	 */
	public static int string2integer(String str) {
		int ret = Integer.parseInt(str.trim());

		return ret;
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * trim한 문자열이 null 또는 공백일 경우 참 반환<br><br>
	 *
	 * StringUtils.isEmptyTrimmed(" ") = true
	 *
	 * @param str 문자열
	 * @return trim한 문자열이 null 또는 공백일 경우 true
	 */

	public static boolean isEmptyTrimmed(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean notEquals(String str1, String str2) {
		return !org.apache.commons.lang.StringUtils.equals(str1, str2);
	}

	// Mid
	//-----------------------------------------------------------------------
	/**
	 * 주어진 문자열이 null 또는 공백이 아닐 경우 참 반환<br><br>
	 *
	 * StringUtils.isNotEmpty("abc") = true
	 *
	 * @param str 문자열
	 * @return null 또는 공백이 아닐 경우 true
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	// TextArea 개행 처리 및 quotation 처리
	public static String escapeJsEval(String text){
		if(StringUtils.isNotEmpty(text)) {
			text = text.replaceAll("(\\r|\\n|\\r\\n)+", "\\\\n");	//개행처리
			text = text.replace("'", "\\\'");						// single quotation 처리
			text = text.replace("\"", "\\\"");						// duuble quotation 처리
		}
		return text;
	};

	/**
	 * 주어진 Object가 null이 아닐 경우 그 Object를 반환하고, null일 경우 default Object를 반환한다.<br><br>
	 *
	 * StringUtils.nvl(null, "NULL TEST") = "NULL TEST"<br>
	 * StringUtils.nvl("test", "NULL TEST") = "test"
	 *
	 * @param inputObject
	 *            the Object to check
	 * @param defaultObject
	 *            the default Object
	 * @return Returns the default Object if the given Object is null, returns
	 *         the given Object if not
	 */
	public static Object nvl(Object inputObject, Object defaultObject) {
		return inputObject != null ? inputObject : defaultObject;
	}

	//전화번호 자동 하이픈 생성
	public static String phone(String src) {
	    if (src == null) {
	      return "";
	    }
	    if (src.length() == 8) {
	      return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
	    } else if (src.length() == 12) {
	      return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
	    }
	    return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
	  }

	//금액 천의단위 콤마
	 public static String toNumFormat(int num) {
		  DecimalFormat df = new DecimalFormat("#,###");
		  return df.format(num);
	 }

	public static String onlyNumber(Object obj) {
		if(!org.springframework.util.StringUtils.isEmpty(obj)) {
			String number = String.format("%s", obj);
			number = number.replaceAll("[^0-9]","");
			return number;
		}else {
			return "";
		}
	}

	public static String ignoreSpecialCharacters(Object obj) {
		if(!org.springframework.util.StringUtils.isEmpty(obj)) {
			String number = String.format("%s", obj);
			number = number.replaceAll("[^0-9a-zA-Z]","");
			return number;
		}else {
			return "";
		}
	}

	 //생년월일 날짜 구하기
	 public static String getBirthDay(String jumin) {

		  String yy = jumin.substring(0, 2);
		  String mm = jumin.substring(2, 4);
		  String dd = jumin.substring(4, 6);
		  String gender_code = jumin.substring(6, 7);

		  if (gender_code.equals("1") || gender_code.equals("2")) {
		   yy = "19" + yy;
		  } else {
		   yy = "20" + yy;
		  }

		  String result = yy+"."+mm+"."+dd;
		  return result;

	 }

	 //하이픈 붙이기
	 public static String phoneSetHyphen(String num){
			String result = "";
			if(StringUtils.isNotEmpty(num)){
				Pattern seoulRegex = Pattern.compile("(^02.{0})([0-9]{3,4})([0-9]{4})");
				Pattern faxRegex = Pattern.compile("(^0505.{0})([0-9]{3,4})([0-9]{4})");
				Pattern phoneRegex = Pattern.compile("(^01.{1})([0-9]{3,4})([0-9]{4})");
				Pattern otherRegex = Pattern.compile("([0-9]{3})([0-9]{3,4})([0-9]{4})");

				Matcher matcher1 = seoulRegex.matcher(num);
				Matcher matcher2 = faxRegex.matcher(num);
				Matcher matcher3 = phoneRegex.matcher(num);
				Matcher matcher4 = otherRegex.matcher(num);

				boolean seoul = matcher1.matches();
				boolean fax = matcher2.matches();
				boolean phone = matcher3.matches();
				boolean other = matcher4.matches();

				if(seoul){
					if(num.length()>9){
						//02-****-****
						result = num.substring(0, 2)+"-"+num.substring(2, 6)+"-"+num.substring(6, num.length());
					}else{
						//02-***-****
						result = num.substring(0, 2)+"-"+num.substring(2, 5)+"-"+num.substring(5, num.length());
					}
				}else if(fax){
					if(num.length()>11){
						//0505-****-****
						result = num.substring(0, 4)+"-"+num.substring(4, 8)+"-"+num.substring(8, num.length());
					}else{
						//0505-***-****
						result = num.substring(0, 4)+"-"+num.substring(4, 7)+"-"+num.substring(7, num.length());
					}
				}else if(phone){
					if(num.length()>10){
						//01*-****-****
						result = num.substring(0, 3)+"-"+num.substring(3, 7)+"-"+num.substring(7, num.length());
					}else{
						//01*-***-****
						result = num.substring(0, 3)+"-"+num.substring(3, 6)+"-"+num.substring(6, num.length());
					}
				}else if(other){
					if(num.length()>10){
						//***-****-****
						result = num.substring(0, 3)+"-"+num.substring(3, 7)+"-"+num.substring(7, num.length());
					}else{
						//***-***-****
						result = num.substring(0, 3)+"-"+num.substring(3, 6)+"-"+num.substring(6, num.length());
					}
				}else{
					result = "";
				}
			}
			return result;
	}

	/**
	 * 문자열이 <code>null</code> 또는 공백이면 <code>defaultStr</code>을 반환하고<br />
	 * 아니면 <code>str</code>을 반환한다.<br />
	 *
	 * <pre>
	 * StringUtils.defaultIfEmpty(null, "NULL")  = "NULL"
	 * StringUtils.defaultIfEmpty("", "NULL")    = "NULL"
	 * StringUtils.defaultIfEmpty("bat", "NULL") = "bat"
	 * StringUtils.defaultIfEmpty("", null)      = null
	 * </pre>
	 *
	 * @param str 문자열
	 * @param defaultStr 초기설정문자
	 * @return 결과문자열
	 */
	public static String defaultIfEmpty(String str, String defaultStr) {
		return org.apache.commons.lang.StringUtils.defaultIfEmpty(str, defaultStr);
	}

    /**
     * 문자열 내에서 문자열을 취득한다.<br />
     *
     * <p>
     * 문자열의 마이너스도 사용가능한 <code>n</code>번째에서 부터 마지막까지의 문자열을 취득.
     * </p>
     *
     * <p>
     * 문자열이 <code>null</code>일경우 <code>null</code>을 반환.
     * 문자열이 공백일경우 공백을 반환.
     * </p>
     *
     * <pre>
     * StringUtils.substring(null, *, *)    = null
     * StringUtils.substring("", * ,  *)    = "";
     * StringUtils.substring("abc", 0, 2)   = "ab"
     * StringUtils.substring("abc", 2, 0)   = ""
     * StringUtils.substring("abc", 2, 4)   = "c"
     * StringUtils.substring("abc", 4, 6)   = ""
     * StringUtils.substring("abc", 2, 2)   = ""
     * StringUtils.substring("abc", -2, -1) = "b"
     * StringUtils.substring("abc", -4, 2)  = "ab"
     * </pre>
     *
     * @param str 문자열
     * @param start 시작점
     * @param end 종료점
     * @return 시작점으로부터 종료점까지의 문자열, 문자열이 null일경우 <code>null</code>
     */
    public static String substring(String str, int start, int end) {
    	return org.apache.commons.lang.StringUtils.substring(str, start, end);
    }

	/**
	 * 주어진 String에 대해서 separator(char)를 이용하여 tokenize한 후 String[]로 뽑아낸다.<br>
	 * 연속된 separator 사이는 token이 되지 않는다.<br>
	 * 주어진 String이 null일 경우, null을 return한다.<br><br>
	 *
	 * StringUtils.split("aaVbbVcc", 'V') = {"aa", "bb", "cc"}
	 *
	 * @param str
	 *            the String to parse
	 * @param separator
	 *            the character used as the delimiter
	 * @return an array of parsed Strings
	 */
	public static String[] split(String str, char separator) {
		StringBuffer tempStringBuffer = new StringBuffer();
		tempStringBuffer.append(separator);
		return tokenizeToStringArray(str, tempStringBuffer.toString(), false,
				false);
	}

    /**
	 * 주어진 String에 대해서 delimiter를 이용하여 tokenize한 후 String[]로 뽑아낸다.<br>
	 * Java의 StringTokenizer를 이용하여 처리한다.<br>
	 * 옵션에 따라 공백(space)에 대한 처리(trim), 값이 ""인 token에 대한 포함 여부를 결정할 수 있다.<br>
	 * StringTokenizer를 이용하므로, 연속된 delimiter 사이는 token이 되지 않는다.<br>
	 * 주어진 String이 null일 경우 null을 return한다.<br>
	 * delimiter가 null일 경우 주어진 String을 하나의 element로 가지는 String[]를 return한다.<br><br>
	 *
	 * StringUtils.tokenizeToStringArray("aaa.bbb.ccc.ddd", ".", true, true) = {"aaa", "bbb", "ccc", "ddd"}
	 *
	 * @param str 문자열
	 * @param separator 구분자
	 * @param trimTokens 공백제거 여부
	 * @param ignoreEmptyTokens 빈 토큰 무시 여부
	 * @return an array of parsed Strings
	 */
	public static String[] tokenizeToStringArray(String str, String separator, boolean trimTokens, boolean ignoreEmptyTokens) {
		if (str == null) {
			return null;
		}
		if (separator == null) {
			return new String[] {str};
		}
		StringTokenizer st = new StringTokenizer(str, separator);
		List<String> tokens = new ArrayList<String>();
		do {
			if (!st.hasMoreTokens()) {
				break;
			}
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() != 0) {
				tokens.add(token);
			}
		} while (true);
		return tokens.toArray(new String[tokens.size()]);
	}

	public static String toHyphenNumber(String phoneNo, int idx) {
		String regex = "(^02.{0}|^01.{1}|[0-9]{3}|^0505.{0})([0-9]+)([0-9]{4})";
		if(!Pattern.matches(regex, phoneNo)) return "";
		return phoneNo.replaceAll(regex, "$" + idx);
	}

	public static String toHyphenNumberMask(String phoneNo, int idx) {
		String regex = "(^02.{0}|^01.{1}|[0-9]{3}|^0505.{0})([0-9]+)([*]{4})";
		if(!Pattern.matches(regex, phoneNo)) return "";
		return phoneNo.replaceAll(regex, "$" + idx);
	}

    /**
     * <pre>
     * 1. 설명 : 2진 비트 연산(And)을 통해 메뉴나 체크박스 등의 선택 여부를 간략히 확인할 수 있다.
     * 2. 동작 : A, B 두 값의 Bit 연산(And)을 수행하여 Boolean형 결과를 리턴한다.
     * 	         ex 1) 15 (1111) & 8 (1000) = 8 (1000) : true
     *          ex 2) 11 (0111) & 2 (0010) = 8 (0010) : true
     *          ex 3) 4 (0100) & 8 (1000) = 0 (0000) : false
     *          ex 4) jsp taglib
     *              <c:choose>
     *                  <c:when test="${ct:bitOperAnd(5,4)}">true</c:when>
     *                  <c:otherwise>false</c:otherwise>
     *              </c:choose>
     * 3. Input : int A, int B
     * 4. Output : boolean(true, false)
     * 5. 수정내역
     * ----------------------------------------------------------------
     * 변경일                 작성자                                            변경내용
     * ----------------------------------------------------------------
     * 2015. 8. 17.     ks-choi         최초작성
     * ----------------------------------------------------------------
     * </pre>
     *
     * @param a the a
     * @param b the b
     * @return boolean (true: 가능, false: 불가능)
     */
    public static boolean bitOperAnd(int a, int b) {
		return (a & b) > 0;
	}


    public static String objToStr(Object obj) {
    	return String.format("%s", obj).trim();
    }

    public static String getAstrick(int nAstrick) {
    	String astrick = "";
		for (int i = 0; i <= nAstrick; i++) {
			astrick += "*";
		}
		return astrick;
    }

	//데이터 콤마 제거
    public static void ignoreComma(String type, Map<String, Object> params, List<String> ignoreList) {
		if("number".equals(type)){
			for(String item : ignoreList) {
				params.put(item, StringUtils.onlyNumber(params.get(item)));
			}
		}
	}

}
