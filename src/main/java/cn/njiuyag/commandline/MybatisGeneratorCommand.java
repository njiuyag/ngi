package cn.njiuyag.commandline;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hjx
 * @date 2021/1/8
 */
@CommandLine.Command(name = "mybatis_generator",aliases = "mg",description = "mybatis mapper 自动生成",mixinStandardHelpOptions = true)
public class MybatisGeneratorCommand implements Runnable{

    @CommandLine.Parameters(description = "配置文件")
    private File configFile;

    @CommandLine.Option(names = {"-o", "--overwrite"}, description = "是否重写")
    private Boolean overwrite = true;


    @Override
    public void run() {
        List<String> warnings = new ArrayList<>();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        try {
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            System.out.println("生成异常"+e.getLocalizedMessage());
        }

    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }


}
