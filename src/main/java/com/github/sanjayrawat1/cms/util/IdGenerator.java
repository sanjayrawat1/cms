package com.github.sanjayrawat1.cms.util;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import java.math.BigInteger;

/**
 * @author Sanjay Singh Rawat
 */
public class IdGenerator {

    private static final int HASH_LENGTH = 8;

    private static final BaseEncoding SHORT_ENCODING = BaseEncoding.base64Url().omitPadding();

    private static final HashFunction HASH = Hashing.sha256();

    public static String generate() {
        byte[] data = BigInteger.valueOf(System.currentTimeMillis()).toByteArray();
        int length = data.length;
        int offset = 0;
        if (length > HASH_LENGTH) {
            data = HASH.hashBytes(data, offset, length).asBytes();
            length = data.length;
        }
        return SHORT_ENCODING.encode(data, offset, length);
    }
}
