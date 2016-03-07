package com.test.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class BlemallAESUtil {
	// 约定加密串
	public final static String KEY = "dda26438a3ae4177";
	private static String ivValue = "";
	private final static String HEX = "0123456789ABCDEF";

	// private final static String HEX = "1234567890123456";

	// key(16位) encryptStr:需要加密的原始串
	public static String encrypt(String key, String encryptStr) {
		String iv = HEX;
		ivValue = iv;
		byte[] rawKey;
		try {
			if (key.length() != 16) {
				rawKey = getRawKey(key.getBytes());
			} else {
				rawKey = key.getBytes();
			}
			byte[] result = encrypt(rawKey, encryptStr.getBytes("UTF-8"));
			String ivAToHex = toHex(iv.getBytes());
			String resultAToHex = toHex(result);
			return ivAToHex + resultAToHex;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	private static byte[] getRawKey(byte[] seed) throws Exception {
		byte[] afterSeed = new byte[2 * seed.length];
		for (int i = 0; i < seed.length; i++) {
			afterSeed[i] = seed[i];
		}
		for (int i = seed.length; i < afterSeed.length; i++) {
			afterSeed[i] = 0;
		}
		return afterSeed;
	}

	private static byte[] encrypt(byte[] raw, byte[] clear) {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		byte[] encrypted = null;
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			int blockSize = cipher.getBlockSize();

			byte[] dataBytes = clear;
			int plaintextLength = dataBytes.length;
			if (plaintextLength % blockSize != 0) {
				plaintextLength = plaintextLength
						+ (blockSize - (plaintextLength % blockSize));
			}
			byte[] plaintext = new byte[plaintextLength];
			System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

			IvParameterSpec iv = new IvParameterSpec(ivValue.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			encrypted = cipher.doFinal(plaintext);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return encrypted;
	}

	public static String toHex(byte[] buf) {
		if (buf == null) {
			return "";
		}
		StringBuffer result = new StringBuffer(2 * buf.length);
		for (int i = 0; i < buf.length; i++) {
			appendHex(result, buf[i]);
		}
		return result.toString();
	}

	private static void appendHex(StringBuffer sb, byte b) {
		sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
	}

	// 转化字符串为十六进制编码
	public static String toHexString(String s) {
		String str = "";

		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return "0x" + str;// 0x表示十六进制
	}

	// 转换十六进制编码为字符串
	public static String toStringHex(String s) {
		if ("0x".equals(s.substring(0, 2))) {
			s = s.substring(2);
		}
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			baKeyword[i] = (byte) (0xff & Integer.parseInt(
					s.substring(i * 2, i * 2 + 2), 16));
		}

		try {
			s = new String(baKeyword, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// UTF-16le:Not
		return s;
	}

	public static String decrypt(String key, String decryptStr) {
		try {

			byte[] rawKey = null;

			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			// 获取偏转值
			String ivHexStr = decryptStr.substring(0, 32);
			// 偏转向量
			IvParameterSpec iv = new IvParameterSpec(
					HexStringToBinary(ivHexStr));
			String decryStr = decryptStr.substring(32);

			if (key.length() != 16) {
				rawKey = getRawKey(key.getBytes());
			} else {
				rawKey = key.getBytes();
			}

			SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			return new String(cipher.doFinal(HexStringToBinary(decryStr)),
					"UTF-8").trim();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(encrypt(password, "540869995", HEX));
		String str = "{\"member_token\":\"GFpDOin_S-O19fmSX61lyw\",\"orderList\":[{\"addressInfo\":{\"zipcode\":null,\"province\":\"866\",\"city\":\"866\",\"district\":\"873\",\"addressId\":null},\"sendInfo\":{\"extrPlace\":null,\"sendType\":\"送货上门(快递)\",\"extrPlaceSid\":null,\"sendTypeSid\":\"0\",\"sendTime\":\"0\",\"storeCode\":null},\"groupId\":null,\"goodsList\":[{\"activityId\":null,\"goodsId\":\"96423\",\"goodsNumber\":1}],\"orderSourceCode\":\"2\"}],\"memberId\":\"100000000008532\"}";
		String encryptResultStr = encrypt(KEY, str);

		String test = "303132333435363738394142434445460126F8EEEEE71EB859EB0446572E5B94FE7DEA97CFB2D011094B74E66EA39BD7BA6AD051E0062817A4C52D31A20F510EB58C4E703A5ED0AE781EC0D5242BD0AA8C331ACCDE5210EE6ED8CFE48361E7CDA330E481EFECFC039626ADEA26402E17F533EB46B05FB7007F18D64B8D0DF90BD05E499AB8B9F910EE107ABC2DB526DBF64967BDB3005EB0DF3DE0EA96BC2653D6A27155C79CE112DA14D8A9FA6AA5B0038798622A2228E526692AEBD131AD1052049567C1257062D43FE60EDEB103E5";

		String decryptResultStr = decrypt(KEY, test);
		System.out.println("加密前字符串:" + str);
		System.out.println("加密后字符串:" + encryptResultStr);
		System.out.println("解密后字符串:" + decryptResultStr);

	}

	/**
	 * 将16进制转换为二进制 .
	 * 
	 * @param hexStr
	 *            .
	 * @return .
	 */
	@Deprecated
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
					16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 
	 * @param hexString
	 * @return 将十六进制转换为字节数组
	 */
	public static byte[] HexStringToBinary(String hexString) {
		// hexString的长度对2取整，作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位

		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((HEX.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) HEX.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}
		return bytes;
	}

}
