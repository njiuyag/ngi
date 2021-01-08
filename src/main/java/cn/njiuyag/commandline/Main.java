package cn.njiuyag.commandline;

import cn.njiuyag.commandline.screw.ScrewCommand;
import picocli.CommandLine;

/**
 * @author hjx
 * @date 2021/1/4
 */
@CommandLine.Command(name = "ngi",subcommands = {
        ScrewCommand.class,TemplateCommand.class,MybatisGeneratorCommand.class
},description = "njiuyag 命令行工具",mixinStandardHelpOptions = true)
public class Main {
    public static void main(String... args) {
        int exitCode = new CommandLine(new Main()).execute(args);
        System.exit(exitCode);
    }
}
