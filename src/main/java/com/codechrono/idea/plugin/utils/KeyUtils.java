package com.codechrono.idea.plugin.utils;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class KeyUtils {
    /**
     * 使用SHA-256算法对字符串进行哈希处理
     *
     * @param input 要哈希的字符串
     * @return 哈希后的十六进制字符串
     */
    private static String hashString(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // 理论上不应该发生，因为SHA-256是Java标准算法之一
            throw new RuntimeException(e);
        }
    }

    private static StringBuilder getFormattedKey(String generatedKey,int length,String c) {
        StringBuilder formattedKey = new StringBuilder();
        for (int i = 0; i < generatedKey.length(); i++) {
            formattedKey.append(generatedKey.charAt(i));
            if ((i + 1) % length == 0 && i + 1 < generatedKey.length()) {
                formattedKey.append(c);
            }
        }
        return formattedKey;
    }

    /**
     * 生成密钥，其中每5个字符用_分隔（基于输入字符串和当前时间的SHA-256哈希值）
     *
     * @param input 输入字符串
     * @return 使用_分隔的十六进制密钥字符串
     * @throws NoSuchAlgorithmException 如果SHA-256算法不可用
     */
    public static String generateKey(String input,long timestamp) {

        // 将输入字符串和时间戳组合起来
        String inputWithTimestamp = input + "_" + timestamp;

        String generatedKey = hashString(inputWithTimestamp);

        // 在每5个字符后插入_（除了最后一个分隔符之后）
        StringBuilder formattedKey = getFormattedKey(generatedKey,5,"_");

        // 如果需要固定长度的密钥，可以在这里进行截断（但通常不推荐，因为会降低安全性）
        // 注意：截断密钥可能会降低其唯一性和安全性

        return formattedKey.toString();
    }
    /**
     * 校验密钥是否有效
     *
     * @param character 原始字符（如用户名、ID等）
     * @param timestamp 创建时间（假设为字符串形式，格式为YYYY-MM-DD HH:mm:ss）
     * @param expectedKey 预期的密钥
     * @return 如果密钥有效则返回true，否则返回false
     */
    public static boolean validateKey(String character, long timestamp, String expectedKey) {
        // 构造用于生成密钥的字符串
        String inputString = character + "_" + timestamp;

        // 计算输入字符串的哈希值（这里使用SHA-256作为示例）
        String generatedKey = hashString(inputString);

        String formattedKey = String.valueOf(getFormattedKey(generatedKey,5,"_"));
        formattedKey=formattedKey.substring(0,29);
        System.out.println("传入"+formattedKey);

        // 比较生成的密钥和预期的密钥
        return expectedKey.equals(formattedKey);
    }
    public static void main(String[] args) {

            String input = "Hello, World!";
            // 获取当前时间戳（毫秒级）
            long timestamp = System.currentTimeMillis();


            String key = generateKey(input,timestamp);
            System.out.println(key); // 将打印类似于 "5eb63bb...（此处省略部分字符）_...（每5个字符后有一个_）" 的字符串

            System.out.println(validateKey(input,timestamp,key));

    }



}