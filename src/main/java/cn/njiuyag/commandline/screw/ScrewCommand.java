package cn.njiuyag.commandline.screw;

import cn.hutool.core.util.StrUtil;
import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import picocli.CommandLine;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author hjx
 * @date 2021/1/4
 */
@CommandLine.Command(name = "screw",description = "数据库文档导出",mixinStandardHelpOptions = true)
public class ScrewCommand implements Runnable {
    @CommandLine.Option(names = {"-conf","--configFile"},description = "配置文件",required = true,usageHelp = true,versionHelp = true)
    private File configFile;

    @Override
    public void run() {
        System.out.println("加载配置文件");
        Properties properties = new Properties();
        try(FileInputStream stream=new FileInputStream(configFile)) {
            properties.load(new InputStreamReader(stream,"UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("配置文件加载失败:"+e.getLocalizedMessage());
        }

        System.out.println("设置数据库连接");
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(properties.getProperty(ScrewConfigKey.DATABASE_DRIVER_CLASS_NAME,ScrewConfigKey.DATABASE_DRIVER_CLASS_NAME_DEFAULT_VALUE));
        hikariConfig.setJdbcUrl(properties.getProperty(ScrewConfigKey.DATABASE_URL,""));
        hikariConfig.setUsername(properties.getProperty(ScrewConfigKey.DATABASE_USER_NAME,""));
        hikariConfig.setPassword(properties.getProperty(ScrewConfigKey.DATABASE_PASSWORD,""));
        HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);

        System.out.println("构建EngineConfig");
        EngineConfig engineConfig = EngineConfig.builder()
                .fileOutputDir(properties.getProperty(ScrewConfigKey.OUTPUT_FILE_DIR,""))
                .openOutputDir(true)
                .fileType(EngineFileType.valueOf(properties.getProperty(ScrewConfigKey.OUTPUT_FILE_TYPE,"HTML")))
                .produceType(EngineTemplateType.freemarker)
                .fileName(properties.getProperty(ScrewConfigKey.OUTPUT_FILE_NAME))
                .build();

        List<String> ignoreTableNames = new ArrayList<>();
        List<String> ignoreTablePrefixes = new ArrayList<>();
        List<String> ignoreTableSuffixes = new ArrayList<>();
        String ignoreTableNamesConfig = properties.getProperty(ScrewConfigKey.IGNORE_TABLE_NAMES, "").trim();
        if (!StrUtil.isEmpty(ignoreTableNamesConfig)) {
            ignoreTableNames = Arrays.asList(ignoreTableNamesConfig.split(","));
        }
        String ignoreTablePrefixesConfig = properties.getProperty(ScrewConfigKey.IGNORE_TABLE_PREFIXES,"").trim();
        if (!StrUtil.isEmpty(ignoreTablePrefixesConfig)) {
            ignoreTablePrefixes = Arrays.asList(ignoreTablePrefixesConfig.split(","));
        }
        String ignoreTableSuffixesConfig = properties.getProperty(ScrewConfigKey.IGNORE_TABLE_SUFFIXES,"").trim();
        if (!StrUtil.isEmpty(ignoreTableSuffixesConfig)) {
            ignoreTableSuffixes = Arrays.asList(ignoreTableSuffixesConfig.split(","));
        }
        System.out.println("构建ProcessConfig");
        ProcessConfig processConfig = ProcessConfig.builder()
                .designatedTableName(new ArrayList<>())
                .designatedTablePrefix(new ArrayList<>())
                .designatedTableSuffix(new ArrayList<>())
                .ignoreTableName(ignoreTableNames)
                .ignoreTablePrefix(ignoreTablePrefixes)
                .ignoreTableSuffix(ignoreTableSuffixes)
                .build();
        System.out.println("构建Configuration");
        Configuration configuration=Configuration.builder()
                .version(properties.getProperty(ScrewConfigKey.VERSION))
                .description(properties.getProperty(ScrewConfigKey.DESCRIPTION))
                .dataSource(hikariDataSource)
                .engineConfig(engineConfig)
                .produceConfig(processConfig)
                .build();

        System.out.println("操作执行");
        new DocumentationExecute(configuration).execute();

    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }
}
