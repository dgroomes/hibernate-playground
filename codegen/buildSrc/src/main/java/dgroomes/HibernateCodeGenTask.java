package dgroomes;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.hibernate.cfg.reveng.ReverseEngineeringSettings;
import org.hibernate.cfg.reveng.ReverseEngineeringStrategy;
import org.hibernate.dialect.PostgreSQL10Dialect;
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
    ReverseEngineeringStrategy strategy = new org.hibernate.cfg.reveng.DefaultReverseEngineeringStrategy();

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
    File outputDirectory = new File(getProject().getProjectDir(), "src/main/java");
    pojoExporter.setOutputDirectory(outputDirectory);

    // These properties are strangely named because of historical reasons.
    // Think about them as "Yes we want JPA annotations and yes we want to use Java language features newer than Java 4".
    pojoExporter.getProperties().setProperty("ejb3", "true");
    pojoExporter.getProperties().setProperty("jdk5", "true");
    log.info("Starting POJO export to directory: " + outputDirectory + "...");
    pojoExporter.start();
  }
}
