package utils;

import com.hankcs.hanlp.HanLP;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

public class SimHashUtils {

    // 传入String，计算出它的hash值，并以字符串形式输出
    public static String getHash(String str) {
        try {
            // 使用MD5算法获取hash值
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            return new BigInteger(1, messageDigest.digest(str.getBytes("UTF-8"))).toString(2);
        } catch (Exception e) {
            e.printStackTrace();
            return ""; // 发生异常时返回空字符串
        }
    }

    // 传入String,计算出它的simHash值，并以字符串形式输出
    public static String getSimHash(String str) throws ShortStringException {
        // 检查输入字符串是否为空或者长度不足
        if (str == null || str.isEmpty()) {
            throw new ShortStringException("文件为空或文本过短，难以判断！");
        }
        if (str.length() < 200) {
            throw new ShortStringException("文本过短，难以判断！");
        }

        // 用数组表示特征向量，初始化为128位
        int[] v = new int[128];
        // 分词并提取关键词，关键词数量为文本长度
        List<String> keywordList = HanLP.extractKeyword(str, str.length());
        int size = keywordList.size();
        int i = 0; // 用于遍历关键词的索引

        // 遍历关键词列表，计算每个关键词的hash值并更新特征向量
        for (String keyword : keywordList) {
            String keywordHash = getHash(keyword);
            // 确保hash长度为128位，不足部分补0
            keywordHash = String.format("%128s", keywordHash).replace(' ', '0');

            // 根据hash值更新特征向量
            for (int j = 0; j < v.length; j++) {
                v[j] += keywordHash.charAt(j) == '1' ? (10 - (i / (size / 10))) : -(10 - (i / (size / 10)));
            }
            i++;
        }

        // 将特征向量降维为simHash值
        StringBuilder simHash = new StringBuilder();
        for (int j : v) {
            simHash.append(j > 0 ? '1' : '0');
        }

        return simHash.toString();
    }
}
