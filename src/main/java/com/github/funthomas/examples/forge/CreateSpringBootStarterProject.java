package com.github.funthomas.examples.forge;

import java.io.File;

import org.jboss.forge.addon.parser.java.projects.JavaWebProjectType;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.util.OperatingSystemUtils;

public class CreateSpringBootStarterProject extends FurnaceLauncher {

	public static void main(final String[] args) {
		final CreateSpringBootStarterProject excecutor = new CreateSpringBootStarterProject();
		excecutor.run();
	}

	@Override
	public void executeTasks(AddonRegistry addonRegistry) {
		
		// Create a temporary directory as an example
		final File tmpDir = OperatingSystemUtils.createTempDir();
		
		createProject(addonRegistry, tmpDir);
	}

	protected void createProject(AddonRegistry addonRegistry, final File tmpDir) {
		final ProjectFactory projectFactory = addonRegistry.getServices(
				ProjectFactory.class).get();
		final ResourceFactory resourceFactory = addonRegistry.getServices(
				ResourceFactory.class).get();

		final Resource<File> projectDir = resourceFactory.create(tmpDir);

		// This could return more than one provider, but since the maven addon
		// is the only one deployed, this is ok
		final ProjectProvider projectProvider = addonRegistry.getServices(
				ProjectProvider.class).get();
			
		// Creating WAR project
		final JavaWebProjectType javaWebProjectType = addonRegistry.getServices(
				JavaWebProjectType.class).get();
		final Project project = projectFactory.createProject(projectDir,
				projectProvider, javaWebProjectType.getRequiredFacets());

		// Changing metadata
		final MetadataFacet facet = project.getFacet(MetadataFacet.class);
		facet.setProjectName("my-demo-project");
		facet.setProjectVersion("1.0.0-SNAPSHOT");
		facet.setProjectGroupName("com.mycompany.project");

		System.out.println("Project Created in: " + project);
	}

}
