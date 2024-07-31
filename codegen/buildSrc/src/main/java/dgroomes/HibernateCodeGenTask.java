package dgroomes;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.hibernate.tool.api.export.Exporter;
import org.hibernate.tool.api.export.ExporterConstants;
import org.hibernate.tool.api.export.ExporterFactory;
import org.hibernate.tool.api.export.ExporterType;
import org.hibernate.tool.api.metadata.MetadataConstants;
import org.hibernate.tool.api.metadata.MetadataDescriptor;
import org.hibernate.tool.api.metadata.MetadataDescriptorFactory;
import org.hibernate.tool.api.reveng.RevengSettings;
import org.hibernate.tool.api.reveng.RevengStrategy;
import org.hibernate.tool.api.reveng.RevengStrategyFactory;
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
      RevengStrategy strategy = RevengStrategyFactory.createReverseEngineeringStrategy();
      {
        var settings = new RevengSettings(strategy).setDefaultPackageName("dgroomes.db");
        strategy.setSettings(settings);
      }

      MetadataDescriptor metadataDescriptor;
      {
        Properties properties = new Properties();
        properties.put("hibernate.connection.driver_class", Driver.class.getName());
        properties.put("hibernate.connection.url", "jdbc:postgresql:postgres");
        properties.put("hibernate.connection.username", "postgres");
        properties.put(MetadataConstants.PREFER_BASIC_COMPOSITE_IDS, true);
        metadataDescriptor = MetadataDescriptorFactory.createReverseEngineeringDescriptor(strategy, properties);
      }

      File outputDirectory = new File(getProject().getProjectDir(), "src/main/java");
      Exporter exporter = ExporterFactory.createExporter(ExporterType.JAVA);

      {
        Properties properties = exporter.getProperties();
        properties.put(ExporterConstants.METADATA_DESCRIPTOR, metadataDescriptor);
        properties.put(ExporterConstants.DESTINATION_FOLDER, outputDirectory);

        // These properties are strangely named because of historical reasons.
        // Think about them as "Yes we want JPA annotations and yes we want to use Java language features newer than Java 4".
        properties.setProperty("ejb3", "true");
        properties.setProperty("jdk5", "true");
      }

      log.info("Starting POJO export to directory: " + outputDirectory + "...");
      exporter.start();
    } catch (Exception e) {
      throw new IllegalStateException("Unexpected exception performing Hibernate entity codegen. Check the configuration.", e);
    }
  }
}
