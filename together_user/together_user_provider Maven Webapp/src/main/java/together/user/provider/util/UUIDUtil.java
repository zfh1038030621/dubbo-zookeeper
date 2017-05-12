/*
 * Copyright 2012-2013 Grandstream.com All right reserved. This software is the confidential and proprietary information
 * of GrandStream.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement you entered into with GrandStream.com.
 */
package together.user.provider.util;

import java.nio.ByteBuffer;
import java.util.UUID;

//import org.apache.commons.codec.binary.Base64;

/**
 * @author thomas.cao Jul 10, 2014 3:36:37 PM
 */
public class UUIDUtil {

    /**
     * 128bit standard UUID 16 hex
     * 
     * @return 36 chars like 37c44c19-1f5f-4ed3-a3dd-16b98b375435
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * 经过转化后的UUID，去掉“-”，并且转为大写
     * 
     * @return
     */
    
    public static String formatedUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }

    public static String get32UUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 88bit encoded UUID
     * 
     * @return 22 chars like p_UngDRlRdexWtNSWM0Tng
     */
    /*
    public static String base64Uuid() {
        UUID uuid = UUID.randomUUID();
        return base64Uuid(uuid);
    }

   protected static String base64Uuid(UUID uuid) {

        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());

        return Base64.encodeBase64URLSafeString(bb.array());
    }

    public static String encodeBase64Uuid(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        return base64Uuid(uuid);
    }

    public static String decodeBase64Uuid(String compressedUuid) {

        byte[] byUuid = Base64.decodeBase64(compressedUuid);

        ByteBuffer bb = ByteBuffer.wrap(byUuid);
        UUID uuid = new UUID(bb.getLong(), bb.getLong());
        return uuid.toString();
    }

    public static void main(String[] args) {
        
    }*/

}
