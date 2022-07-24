package dgroomes;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * A custom Gradle plugin for Hibernate entity codegen.
 *
 * This plugin registers the task "hibernateCodeGen" to execute Hibernate Tool's codegen library to generate Java source
 * code based on the SQL table definitions. Read about the Hibernate Tools project here: https://hibernate.org/tools/
 */
public class HibernateCodeGenPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().register("hibernateCodeGen", HibernateCodeGenTask.class);
    }
}
