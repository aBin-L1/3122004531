import utils.HammingUtils;
import utils.SimHashUtils;
import utils.TxtIOUtils;

public class MainPaperCheck {

    public static void main(String[] args) {
        // 检查输入参数是否正确
        if (args.length != 3) {
            System.out.println("参数个数错误，请按样例输入：java -jar xx.jar [原文件的绝对路径] [抄袭文件的绝对路径] [答案文件的绝对路径]");
            System.exit(0);
        }

        // 读取文件内容
        String str0 = TxtIOUtils.readTxt(args[0]);
        String str1 = TxtIOUtils.readTxt(args[1]);
        String resultFileName = args[2];

        try {
            // 计算两个文本的simHash值
            String simHash0 = SimHashUtils.getSimHash(str0);
            String simHash1 = SimHashUtils.getSimHash(str1);

            // 计算相似度
            double similarity = HammingUtils.getSimilarity(simHash0, simHash1);

            // 将相似度写入结果文件
            TxtIOUtils.writeTxt(similarity, resultFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }
}
