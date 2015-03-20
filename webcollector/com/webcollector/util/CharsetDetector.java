package com.webcollector.util;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * 字符集自动检测
 */
public class CharsetDetector {

	/**
	 * 根据字节数组，猜测可能的字符集，如果检测失败，返回utf-8
	 * 
	 * @param bytes
	 *            待检测的字节数组
	 * @return 可能的字符集，如果检测失败，返回utf-8
	 */
	public static String guessEncoding(byte[] bytes) {
		String DEFAULT_ENCODING = "UTF-8";
		UniversalDetector detector = new UniversalDetector(null);
		detector.handleData(bytes, 0, bytes.length);
		detector.dataEnd();
		String encoding = detector.getDetectedCharset();
		detector.reset();
		if (encoding == null) {
			encoding = DEFAULT_ENCODING;
		}
		return encoding;
	}
}
