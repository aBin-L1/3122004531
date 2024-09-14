package utils;

import java.io.*;

public class TxtIOUtils {

    // 读取txt文件内容并返回为字符串
    public static String readTxt(String txtPath) {
        StringBuilder str = new StringBuilder();
        File file = new File(txtPath);

        // 使用try-with-resources确保资源关闭
        try (FileInputStream fileInputStream = new FileInputStream(file);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String strLine;
            // 按行读取文件内容并拼接
            while ((strLine = bufferedReader.readLine()) != null) {
                str.append(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

    // 将相似度结果写入指定文件
    public static void writeTxt(double txtElem, String txtPath) {
        try (FileWriter fileWriter = new FileWriter(txtPath, true)) {
            fileWriter.write("论文相似度比例为： ");
            fileWriter.write(String.format("%.2f", txtElem)); // 格式化输出相似度
            fileWriter.write("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
