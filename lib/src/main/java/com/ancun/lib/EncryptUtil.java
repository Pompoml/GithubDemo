package com.ancun.lib;

/**
 * 用于生成localservice中方法的serviceMark
 */
public class EncryptUtil {

    public static void main(String args[]) {
        String[] contentMethodNames = new String[]{"getLocalSnsInfo", "saveLocalSnsInfo",
                "saveVideoThumb", "removeLocalSnsInfo","fetchBmobSNSInfo","uploadBmobSNSInfo"};
        String[] AssistMethodNames = new String[]{"pickupWxFriendInfo", "transferWxSnsNewFile",
                "pickupWxSnsContentText",
                "wxSnsAnalogClick", "analysisItemContent", "pickupWxSnsContentLinkTitle", "getClipData", "getPickupLocalRect"};
        String[] chatMethodNames = new String[]{"getChatRoomList", "getChatRoomMember",
                "getChatRoomMemberSpeakNum", "getChatRoomActiveUser", "getChatRoomRedPacketInfo"};
        String[] wxMethodNames = new String[]{"getUuid", "getQrCodeToSaveLocal",
                "waitForLogin", "getLoginParams", "getAccountInfo"};

        //for (String name : contentMethodNames) {
            //System.out.println(encrypt(name));
        //}


        String service_mark_submitreferrer = encrypt("refreshMemoryAppItem");
        System.out.println(service_mark_submitreferrer);

    }

    //使用方法名的hashcode值作为serviceMark
    private static String encrypt(String str) {
        long n = str.hashCode();
        return String.valueOf(n);
    }

    public static String encryptOffsetByte(String str) {
        byte[] aData = new byte[str.length()];
        for (int i = 0; i < str.length(); ++i) {
            aData[i] = (byte) (str.charAt(i) + 3);
        }
        return new String(aData);
    }

    public static String decryptOffsetByte(String str) {
        byte[] aData = new byte[str.length()];
        for (int i = 0; i < str.length(); ++i) {
            aData[i] = (byte) (str.charAt(i) - 3);
        }
        return new String(aData);
    }

}
