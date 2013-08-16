package ro.kuberam.maven.expathPlugin.mojos;

import static org.twdata.maven.mojoexecutor.MojoExecutor.artifactId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.configuration;
import static org.twdata.maven.mojoexecutor.MojoExecutor.dependencies;
import static org.twdata.maven.mojoexecutor.MojoExecutor.dependency;
import static org.twdata.maven.mojoexecutor.MojoExecutor.element;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executeMojo;
import static org.twdata.maven.mojoexecutor.MojoExecutor.executionEnvironment;
import static org.twdata.maven.mojoexecutor.MojoExecutor.goal;
import static org.twdata.maven.mojoexecutor.MojoExecutor.groupId;
import static org.twdata.maven.mojoexecutor.MojoExecutor.name;
import static org.twdata.maven.mojoexecutor.MojoExecutor.plugin;
import static org.twdata.maven.mojoexecutor.MojoExecutor.version;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.codehaus.plexus.util.io.RawInputStreamFacade;

@Mojo(name = "generate-specs-index")
public class GenerateSpecsIndexMojo extends AbstractExpathMojo {

	@Parameter(required = true)
	private File specsDir;

	@Parameter(required = true)
	private String includeSpecIds;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {

		String specsDirPath = specsDir.getAbsolutePath();
		String specsIndexTmpDir = projectBuildDirectory.getAbsolutePath() + File.separator + "specs-index-tmp-" + UUID.randomUUID();

		// create a copy of the XSL file used for generation of index file
		File xslFile = new File(specsIndexTmpDir + File.separator + "generate-specs-index.xsl");
		try {
			FileUtils
					.copyStreamToFile(
							new RawInputStreamFacade(this.getClass().getResourceAsStream("/ro/kuberam/maven/expathPlugin/generate-specs-index.xsl")),
							xslFile);
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		System.out.println("specsDir: " + specsDir);
		System.out.println("specsIndexTmpDir: " + specsIndexTmpDir);

		// generate the index
		executeMojo(
				plugin(groupId("org.codehaus.mojo"), artifactId("xml-maven-plugin"), version("1.0"),
						dependencies(dependency("net.sf.saxon", "Saxon-HE", "9.4.0.7"))),
				goal("transform"),
				configuration(
						element(name("forceCreation"), "true"),
						element(name("transformationSets"),
								element(name("transformationSet"),
										element(name("dir"), specsIndexTmpDir),
										element(name("includes"), element(name("include"), "generate-specs-index.xsl")),
										element(name("stylesheet"), xslFile.getAbsolutePath()),
										element(name("outputDir"), specsDirPath),
										element(name("parameters"),
												element(name("parameter"), element(name("name"), "specsDir"),
														element(name("value"), specsDir.getAbsolutePath())),
												element(name("parameter"), element(name("name"), "includeSpecIds"),
														element(name("value"), includeSpecIds)),
												element(name("parameter"), element(name("name"), "outputDir"),
														element(name("value"), specsDirPath)))))),
				executionEnvironment(project, session, pluginManager));
	}

}
