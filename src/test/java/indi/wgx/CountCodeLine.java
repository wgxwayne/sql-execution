package indi.wgx;

import java.io.*;
/**
 * @author
 * @since 2020/10/31 9:23
 */
public class CountCodeLine {
    static int count = 0;
    public static void main(String[] args) throws IOException {
        //获取所要查询文件夹路径
        String path = "E:\\IdeaProjects\\sql-execution\\src\\main";
        myCodeCount(new File(path));
        System.out.println("总代码行数为"+count);
    }
    /**
     * 递归统计代码总行数
     * @throws IOException
     */
    public static void myCodeCount(File file) throws IOException {
        //文件是否为普通文件并以.java为后缀结尾
        if (file.isFile() && (file.getName().endsWith(".java"))) {
            int i = readData(file);
            count += i;
        }
        //测试此抽象路径名表示的文件是否为目录
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                myCodeCount(f);
            }
        }
    }
    /**
     * 单个文件中的行数
     */
    public static int readData(File file) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        int i = 0;
        while (br.readLine() !=null) {
            i++;
        }
        br.close();
        System.out.println(file.getName()+"的行数为"+i);
        return i;
    }
}
