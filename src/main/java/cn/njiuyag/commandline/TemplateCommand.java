package cn.njiuyag.commandline;

import picocli.CommandLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author hjx
 * @date 2021/1/7
 */
@CommandLine.Command(name = "template",description = "数据库文档导出",mixinStandardHelpOptions = true)
public class TemplateCommand implements Runnable {

    @CommandLine.Parameters(description = "获取配置文件的命令")
    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/" + command + ".properties");
        try (InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream,"UTF-8")) {
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            for (;;) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
