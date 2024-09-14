import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import utils.HammingUtils;
import utils.ShortStringException;
import utils.SimHashUtils;
import utils.TxtIOUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    // 测试文件路径，用于 TxtIOUtils 测试
    private final String testFilePath = "test.txt";

    // 每个测试方法执行后，清理测试文件，避免影响后续测试
    @AfterEach
    public void cleanUp() {
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete(); // 删除测试文件
        }
    }

    // 测试 HammingUtils 的 getHammingDistance 方法
    @Test
    public void testGetHammingDistance() {
        // 验证正常情况
        assertEquals(1, HammingUtils.getHammingDistance("1010", "1011"));
        assertEquals(0, HammingUtils.getHammingDistance("1111", "1111"));
        assertEquals(4, HammingUtils.getHammingDistance("0000", "1111"));

    }

    // 测试 HammingUtils 的 getSimilarity 方法
    @Test
    public void testGetSimilarity() {
        // 验证正常情况的相似度计算
        assertEquals(0.9921875, HammingUtils.getSimilarity("1010", "1011"), 0.0001);
        assertEquals(1.00, HammingUtils.getSimilarity("1111", "1111"), 0.0001);
        assertEquals(0.96875, HammingUtils.getSimilarity("0000", "1111"), 0.0001);
    }

    // 测试 ShortStringException 是否能够正确创建并传递异常消息
    @Test
    public void testShortStringException() {
        ShortStringException exception = new ShortStringException("测试异常消息");
        assertEquals("测试异常消息", exception.getMessage());
    }

    // 测试 SimHashUtils 的 getHash 方法，检查 hash 值是否正确生成
    @Test
    public void testGetHash() {
        String hash = SimHashUtils.getHash("你好，世界");
        assertNotNull(hash);
        assertTrue(hash.length() > 0, "Hash 值长度应大于 0");
    }

    // 测试 SimHashUtils 的 getSimHash 方法，包括正常和异常情况
    @Test
    public void testGetSimHash() {
        // 测试长文本的 simHash 值计算
        String longText = "这是一个用于测试的中文文本，用于测试 SimHash 工具的正确性。" +
                "为了确保 SimHash 能够正确地处理中文内容，我们需要一个足够长的文本。" +
                "这个文本必须包含足够的字符，以满足 getSimHash 方法对文本长度的要求。" +
                "文本的长度必须超过 200 个字符，因此我们需要确保内容足够丰富。" +
                "这样可以避免在测试过程中因文本过短而引发的异常。" +
                "这个文本的目的是确保 getSimHash 方法的正确性，同时检验文本处理的稳定性。";

        try {
            String simHash = SimHashUtils.getSimHash(longText);
            assertNotNull(simHash);
            assertEquals(128, simHash.length(), "SimHash 值的长度应为 128 位");
        } catch (ShortStringException e) {
            fail("不应抛出异常：" + e.getMessage());
        }
    }

    // 测试短文本是否正确抛出异常
    @Test
    public void testGetSimHashWithShortText() {
        // 预期短文本抛出 ShortStringException 异常
        Exception exception = assertThrows(ShortStringException.class, () -> {
            SimHashUtils.getSimHash("短文本");
        });
        // 验证异常消息内容
        assertEquals("文本过短，难以判断！", exception.getMessage());
    }

    // 测试 TxtIOUtils 的读取文件功能，确保能够正确读取内容
    @Test
    public void testReadTxt() {
        TxtIOUtils.writeTxt(0.98, testFilePath);
        String content = TxtIOUtils.readTxt(testFilePath);
        assertTrue(content.contains("论文相似度比例为： 0.98"));
    }

    // 测试 TxtIOUtils 的写入文件功能，确保写入内容正确
    @Test
    public void testWriteTxt() {
        TxtIOUtils.writeTxt(0.75, testFilePath);
        File file = new File(testFilePath);
        assertTrue(file.exists());
        String content = TxtIOUtils.readTxt(testFilePath);
        assertTrue(content.contains("论文相似度比例为： 0.75"));
    }
}
