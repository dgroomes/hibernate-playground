package dgroomes;

import org.apache.tools.ant.BuildException;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.hibernate.cfg.reveng.ReverseEngineeringSettings;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.dialect.*;
import org.hibernate.tool.api.metadata.MetadataDescriptor;
import org.hibernate.tool.api.metadata.MetadataDescriptorFactory;
import org.hibernate.tool.hbm2x.POJOExporter;
import org.postgresql.Driver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Use Hibernates Tool's codegen library to generate Java source code based on the SQL table definitions.
 */
public class HibernateCodeGenTask extends DefaultTask {

  private static final Logger log = LoggerFactory.getLogger(HibernateCodeGenTask.class);

  /**
   * Run the code gen
   *
   * @throws IOException
   */
  @TaskAction
  public void codeGen() throws IOException {
    try {
      ReverseEngineeringStrategy strategy = setupReverseEngineeringStrategy();
      Properties properties = new Properties();
      {
        properties.setProperty("hibernate.dialect", PostgreSQL10Dialect.class.getName());
        properties.setProperty("hibernate.connection.driver_class", Driver.class.getName());
        properties.setProperty("hibernate.connection.url", "jdbc:postgresql:postgres");
        properties.setProperty("hibernate.connection.username", "postgres");
      }
      executeExporter(createJdbcDescriptor(strategy, properties));
    } catch (Exception e) {
      throw new IllegalStateException("Unexpected exception performing Hibernate entity codegen. Check the configuration.", e);
    }
  }

  private MetadataDescriptor createJdbcDescriptor(ReverseEngineeringStrategy strategy, Properties properties) {
    return MetadataDescriptorFactory
            .createJdbcDescriptor(
                    strategy,
                    properties,
                    true);
  }

  private ReverseEngineeringStrategy setupReverseEngineeringStrategy() {
    ReverseEngineeringStrategy strategy;
    try {
      strategy = ReverseEngineeringStrategy.class.cast(Class.forName("org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy").newInstance());
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | ClassCastException e) {
      throw new BuildException("org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy" + " not instanced.", e);
    }

    //    if (revengFile != null) {
    //      OverrideRepository override = new OverrideRepository();
    //      override.addFile(revengFile);
    //      strategy = override.getReverseEngineeringStrategy(strategy);
    //    }

    ReverseEngineeringSettings settings =
            new ReverseEngineeringSettings(strategy)
                    .setDefaultPackageName("dgroomes.db")
                    .setDetectManyToMany(true)
                    .setDetectOneToOne(true)
                    .setDetectOptimisticLock(true)
                    .setCreateCollectionForForeignKey(true)
                    .setCreateManyToOneForForeignKey(true);

    strategy.setSettings(settings);
    return strategy;
  }

  private void executeExporter(MetadataDescriptor metadataDescriptor) throws Exception {
    POJOExporter pojoExporter = new POJOExporter();
    pojoExporter.setMetadataDescriptor(metadataDescriptor);
    File outputDirectory = new File(getProject().getBuildDir(), "generated-sources");
    pojoExporter.setOutputDirectory(outputDirectory);
    //    if (templatePath != null) {
    //      log.info("Setting template path to: " + templatePath);
    //      pojoExporter.setTemplatePath(new String[]{templatePath});
    //    }
    pojoExporter.getProperties().setProperty("ejb3", "false");
    pojoExporter.getProperties().setProperty("jdk5", "false");
    log.info("Starting POJO export to directory: " + outputDirectory + "...");
    pojoExporter.start();
  }
}
