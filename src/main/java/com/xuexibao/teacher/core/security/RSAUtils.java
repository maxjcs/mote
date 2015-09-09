package com.xuexibao.teacher.core.security;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Enumeration;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.bouncycastle.asn1.ASN1Sequence;
import org.bouncycastle.asn1.pkcs.RSAPrivateKeyStructure;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.encodings.PKCS1Encoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.encoders.Hex;

import com.xuexibao.teacher.util.URLUtil;

public class RSAUtils {

	final public static int RAW = 1;
	final public static int PKCS1 = 2;
	private static String privateKey;

	static {
		try {
			privateKey = IOUtils.toString(new FileReader(URLUtil.getResourcePath("private/private.pem")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String decode(String code) throws IOException, Exception {
		RSAPrivateKeyStructure asn1PrivKey = new RSAPrivateKeyStructure((ASN1Sequence) ASN1Sequence.fromByteArray(Base64Utils
				.decode(privateKey)));

		byte[] result = RSAUtils.rsaPriDecrypt(asn1PrivKey, Base64Utils.decode(code), 2);
		return new String(result);
	}

	public static void main(String[] args) throws Exception {
		String code = "RAgP1i7r+jLQxFd+VmL/5NBNznPtmW/6YxoTQ4QOZvNptzTbxbAz1zE5jUemdXKRYD9SK7tFlmwV45EdVbeYlD8Lt85Xr74b220z6klJFm2RjjZTr121xJeoUrUDV9FRhcTfhabbMndrBCIEo7NYTJ7K/9MoiimxbMgIUTHOVEqygNDKH7QxAW5BNCSYj8Utcilgd2Qeulx/sGMSfLcrdqdlAg2Y19f3h/QPRVNu5pGitagstapOnn6S+NCPV17WTGIfU1UKhL+n+60zSs/SxVNHrj/wbw+kO2HnN5RKGuJTHkPCGmN9aqX1i1EVm0KobQw4zJSo4w8VbPOyQhcCPQ==";
		 
		System.out.print(decode(code));
	}

	/*
	 * 产生RSA公私钥对
	 */
	public static KeyPair genRSAKeyPair() {
		KeyPairGenerator rsaKeyGen = null;
		KeyPair rsaKeyPair = null;
		try {
			System.out.println("Generating a pair of RSA key ... ");
			rsaKeyGen = KeyPairGenerator.getInstance("RSA");
			SecureRandom random = new SecureRandom();
			random.nextBytes(new byte[1]);
			rsaKeyGen.initialize(1024, new SecureRandom());
			rsaKeyPair = rsaKeyGen.genKeyPair();
//			PublicKey rsaPublic = rsaKeyPair.getPublic();
//			PrivateKey rsaPrivate = rsaKeyPair.getPrivate();
			System.out.println("1024-bit RSA key GENERATED.");
		} catch (Exception e) {
			System.out.println("Exception in keypair generation. Reason: " + e);
		}

		return rsaKeyPair;
	}

	/*
	 * 列出密钥库中指定的条目
	 */
	public static void showAllEntry(String filename, String pass) {
		try {
			FileInputStream inKeyStoreFile = new FileInputStream(filename);
			char[] password = pass.toCharArray();
			KeyStore from = KeyStore.getInstance("JKS", "SUN");
			from.load(null, null);
			from.load(inKeyStoreFile, password);
			Enumeration e = from.aliases();
			System.out.println("Entry List:");
			while (e.hasMoreElements()) {
				System.out.println((String) e.nextElement());
			}
			inKeyStoreFile.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/*
	 * 列出密钥库中所有的条目
	 */
	public static Vector getAllEntry(String filename, String pass) {
		Vector v = new Vector();
		try {
			FileInputStream inKeyStoreFile = new FileInputStream(filename);
			char[] password = pass.toCharArray();
			KeyStore from = KeyStore.getInstance("JKS", "SUN");
			from.load(null, null);
			from.load(inKeyStoreFile, password);
			Enumeration e = from.aliases();
			System.out.println("Entry List:");
			while (e.hasMoreElements()) {
				v.addElement((String) e.nextElement());

			}
			inKeyStoreFile.close();
			return v;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * 获得私钥
	 */
	public static RSAPrivateKey loadPrivateKey(String filename, String keyName, String pass) throws Exception {
		FileInputStream inKeyStoreFile = new FileInputStream(filename);
		char[] password = pass.toCharArray();
		KeyStore from = KeyStore.getInstance("JKS", "SUN");
		from.load(null, null);
		from.load(inKeyStoreFile, password);
		Key testkey = from.getKey(keyName, password);
		RSAPrivateKey pvtKey = (RSAPrivateKey) testkey;
		System.out.println("Private key exponent =/r/n" + pvtKey.getPrivateExponent().toString(16) + "/r/n");
		inKeyStoreFile.close();
		return pvtKey;
	}

	/*
	 * 获得公钥
	 */
	public static RSAPublicKey loadPublicKey(String filename, String keyName, String pass) throws Exception {
		FileInputStream inKeyStoreFile = new FileInputStream(filename);
		char[] password = pass.toCharArray();
		KeyStore from = KeyStore.getInstance("JKS", "SUN");
		from.load(null, null);
		from.load(inKeyStoreFile, password);
		java.security.cert.Certificate c = from.getCertificate(keyName);
		RSAPublicKey pubKey = (RSAPublicKey) c.getPublicKey();
		System.out.println("Public key exponent =/r/n" + pubKey.getPublicExponent().toString(16) + "/r/n");
		inKeyStoreFile.close();
		return pubKey;
	}

	/*
	 * 使用公钥加密
	 */
	public static byte[] rsaPubEncrypt(RSAPublicKey PubKey, byte[] clearBytes, int type) {

		BigInteger mod = PubKey.getModulus();
		BigInteger pubExp = PubKey.getPublicExponent();

		RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);

		System.out.println("mod:/r/n" + mod.toString(16));
		System.out.println("pubExp:/r/n" + pubExp.toString(16));
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(true, pubParameters);
		byte[] data = null;
		try {
			System.out.println("clearBytes:/r/n" + new String(Hex.encode(clearBytes)));
			data = eng.processBlock(clearBytes, 0, clearBytes.length);
			System.out.println("EncBytes:/r/n" + new String(Hex.encode(data)));
			return data;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * 公钥解密
	 */
	public static byte[] rsaPubDecrypt(RSAPublicKey PubKey, byte[] clearBytes, int type) {

		BigInteger mod = PubKey.getModulus();
		BigInteger pubExp = PubKey.getPublicExponent();

		RSAKeyParameters pubParameters = new RSAKeyParameters(false, mod, pubExp);

		System.out.println("mod:/r/n" + mod.toString(16));
		System.out.println("pubExp:/r/n" + pubExp.toString(16));
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(false, pubParameters);
		byte[] data = null;
		try {
			System.out.println("clearBytes:/r/n" + new String(Hex.encode(clearBytes)));
			data = eng.processBlock(clearBytes, 0, clearBytes.length);
			System.out.println("EncBytes:/r/n" + new String(Hex.encode(data)));
			return data;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/*
	 * 私钥解密
	 */
	public static byte[] rsaPriDecrypt(RSAPrivateKeyStructure prvKey, byte[] encodedBytes, int type) {

		// RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) prvKeyIn;

		BigInteger mod = prvKey.getModulus();
		BigInteger pubExp = prvKey.getPublicExponent();
		BigInteger privExp = prvKey.getPrivateExponent();
		BigInteger pExp = prvKey.getExponent1();
		BigInteger qExp = prvKey.getExponent2();
		BigInteger p = prvKey.getPrime1();
		BigInteger q = prvKey.getPrime2();
		BigInteger crtCoef = prvKey.getCoefficient();

		RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);

		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);

		eng.init(false, privParameters);
		byte[] data = null;
		try {
			data = eng.processBlock(encodedBytes, 0, encodedBytes.length);
			return data;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return null;
		}

	}

	/*
	 * 使用私钥加密
	 */
	public static byte[] rsaPriEncrypt(RSAPrivateKey prvKeyIn, byte[] encodedBytes, int type) {
		RSAPrivateCrtKey prvKey = (RSAPrivateCrtKey) prvKeyIn;
		BigInteger mod = prvKey.getModulus();
		BigInteger pubExp = prvKey.getPublicExponent();
		BigInteger privExp = prvKey.getPrivateExponent();
		BigInteger pExp = prvKey.getPrimeExponentP();
		BigInteger qExp = prvKey.getPrimeExponentQ();
		BigInteger p = prvKey.getPrimeP();
		BigInteger q = prvKey.getPrimeQ();
		BigInteger crtCoef = prvKey.getCrtCoefficient();
		RSAKeyParameters privParameters = new RSAPrivateCrtKeyParameters(mod, pubExp, privExp, p, q, pExp, qExp, crtCoef);
		AsymmetricBlockCipher eng = new RSAEngine();
		if (type == PKCS1)
			eng = new PKCS1Encoding(eng);
		eng.init(true, privParameters);
		byte[] data = null;
		try {
			data = eng.processBlock(encodedBytes, 0, encodedBytes.length);
			return data;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}

	}
}