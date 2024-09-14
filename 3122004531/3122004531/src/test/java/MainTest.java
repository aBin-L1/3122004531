import org.junit.jupiter.api.Test;
import utils.HammingUtils;
import utils.SimHashUtils;
import utils.TxtIOUtils;
import utils.ShortStringException;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
public class MainTest {

    // 验证文件路径是否存在的辅助方法
    private void validateFilePath(String path) throws Exception {
        File file = new File(path);
        if (!file.exists()) {
            throw new Exception("文件不存在: " + path);
        }
    }

    @Test // 测试所有文件
    public void origAndAllTest() throws ShortStringException, Exception {
        String basePath = "src/main/resources/";
        String[] files = {
                "orig.txt", "orig_0.8_add.txt", "orig_0.8_del.txt",
                "orig_0.8_dis_1.txt", "orig_0.8_dis_10.txt", "orig_0.8_dis_15.txt"
        };
        String ansFileName = "src/main/resources/test1.txt";

        // 验证所有文件路径
        for (String file : files) {
            validateFilePath(basePath + file);
        }

        String[] str = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            str[i] = TxtIOUtils.readTxt(basePath + files[i]);
        }

        for (int i = 0; i <= 5; i++) {
            double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str[0]), SimHashUtils.getSimHash(str[i]));
            TxtIOUtils.writeTxt(ans, ansFileName);
        }
    }

    @Test // 测试原文件与原文件
    public void origAndOrigTest() throws ShortStringException, Exception {
        String path = "src/main/resources/orig.txt";
        validateFilePath(path);

        String str0 = TxtIOUtils.readTxt(path);
        String str1 = TxtIOUtils.readTxt(path);
        String ansFileName = "src/main/resources/test2.txt";
        double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        TxtIOUtils.writeTxt(ans, ansFileName);
    }

    @Test // 测试原文件与抄袭文件1添加
    public void origAndAddTest() throws ShortStringException, Exception {
        String origPath = "src/main/resources/orig.txt";
        String addPath = "src/main/resources/orig_0.8_add.txt";
        validateFilePath(origPath);
        validateFilePath(addPath);

        String str0 = TxtIOUtils.readTxt(origPath);
        String str1 = TxtIOUtils.readTxt(addPath);
        String ansFileName = "src/main/resources/test3.txt";
        double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        TxtIOUtils.writeTxt(ans, ansFileName);
    }

    @Test // 测试原文件与抄袭文件2删减
    public void origAndDelTest() throws ShortStringException, Exception {
        String origPath = "src/main/resources/orig.txt";
        String delPath = "src/main/resources/orig_0.8_del.txt";
        validateFilePath(origPath);
        validateFilePath(delPath);

        String str0 = TxtIOUtils.readTxt(origPath);
        String str1 = TxtIOUtils.readTxt(delPath);
        String ansFileName = "src/main/resources/test4.txt";
        double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        TxtIOUtils.writeTxt(ans, ansFileName);
    }

    @Test // 测试原文件与抄袭文件3
    public void origAndDis1Test() throws ShortStringException, Exception {
        String origPath = "src/main/resources/orig.txt";
        String dis1Path = "src/main/resources/orig_0.8_dis_1.txt";
        validateFilePath(origPath);
        validateFilePath(dis1Path);

        String str0 = TxtIOUtils.readTxt(origPath);
        String str1 = TxtIOUtils.readTxt(dis1Path);
        String ansFileName = "src/main/resources/test5.txt";
        double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        TxtIOUtils.writeTxt(ans, ansFileName);
    }

    @Test // 测试原文件与抄袭文件4
    public void origAndDis10Test() throws ShortStringException, Exception {
        String origPath = "src/main/resources/orig.txt";
        String dis10Path = "src/main/resources/orig_0.8_dis_10.txt";
        validateFilePath(origPath);
        validateFilePath(dis10Path);

        String str0 = TxtIOUtils.readTxt(origPath);
        String str1 = TxtIOUtils.readTxt(dis10Path);
        String ansFileName = "src/main/resources/test6.txt";
        double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        TxtIOUtils.writeTxt(ans, ansFileName);
    }

    @Test // 测试原文件与抄袭文件5
    public void origAndDis15Test() throws ShortStringException, Exception {
        String origPath = "src/main/resources/orig.txt";
        String dis15Path = "src/main/resources/orig_0.8_dis_15.txt";
        validateFilePath(origPath);
        validateFilePath(dis15Path);

        String str0 = TxtIOUtils.readTxt(origPath);
        String str1 = TxtIOUtils.readTxt(dis15Path);
        String ansFileName = "src/main/resources/test7.txt";
        double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
        TxtIOUtils.writeTxt(ans, ansFileName);
    }

    @Test // 测试原文件不存在
    public void testFileNotExist() {
        String origPath = "src/main/resources/orig.txt";
        String nonExistentPath = "src/main/resources/orig_0.8_dis_nonexistent.txt";
        String ansFileName = "src/main/resources/test8.txt";

        Exception exception = assertThrows(Exception.class, () -> {
            validateFilePath(origPath);
            validateFilePath(nonExistentPath);

            String str0 = TxtIOUtils.readTxt(origPath);
            String str1 = TxtIOUtils.readTxt(nonExistentPath);
            double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
            TxtIOUtils.writeTxt(ans, ansFileName);
        });

        System.out.println(exception.getMessage());
    }

    @Test // 测试文件为空异常
    public void testFileIsEmpty() throws Exception {
        String origPath = "src/main/resources/orig.txt";
        String emptyPath = "src/main/resources/orig_0.1.txt"; // 假设 orig_0.1.txt 是一个空文件
        validateFilePath(origPath);
        validateFilePath(emptyPath);

        // 验证在处理空文件时是否抛出 ShortStringException
        Exception exception = assertThrows(ShortStringException.class, () -> {
            String str0 = TxtIOUtils.readTxt(origPath);
            String str1 = TxtIOUtils.readTxt(emptyPath);
            String ansFileName = "src/main/resources/test9.txt";
            double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
            TxtIOUtils.writeTxt(ans, ansFileName);
        });

        // 检查异常信息是否符合预期
        assertEquals("文件为空或文本过短，难以判断！", exception.getMessage());
    }

    @Test // 测试文件字数太少异常
    public void testShortText() throws ShortStringException, Exception {
        String origPath = "src/main/resources/orig.txt";
        String shortTextPath = "src/main/resources/orig_0.2.txt"; // 假设此文件内容过短
        validateFilePath(origPath);
        validateFilePath(shortTextPath);

        Exception exception = assertThrows(ShortStringException.class, () -> {
            String str0 = TxtIOUtils.readTxt(origPath);
            String str1 = TxtIOUtils.readTxt(shortTextPath);
            String ansFileName = "src/main/resources/test10.txt";
            double ans = HammingUtils.getSimilarity(SimHashUtils.getSimHash(str0), SimHashUtils.getSimHash(str1));
            TxtIOUtils.writeTxt(ans, ansFileName);
        });

        System.out.println(exception.getMessage());
    }
}
