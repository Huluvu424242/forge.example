package com.github.funthomas.examples.forge;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.repositories.AddonRepositoryMode;
import org.jboss.forge.furnace.se.FurnaceFactory;

public abstract class FurnaceLauncher implements TaskExcecutor {

	protected static Furnace engine=startEngine();
	
	private static Furnace startEngine() {

		// Create a Furnace instance. NOTE: This must be called only once
		final Furnace furnace = FurnaceFactory.getInstance();
		// Add repository containing addons specified in pom.xml
		furnace.addRepository(AddonRepositoryMode.IMMUTABLE, new File(
				"target/addons"));
		// Start Furnace in another thread
		final Future<Furnace> future = furnace.startAsync();
		// Wait until Furnace is started and return
		Furnace startedFurnace=null;
		
		try {
			startedFurnace= future.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return startedFurnace;
	}

	public final void run(){
		final AddonRegistry addonRegistry = engine.getAddonRegistry();
		executeTasks(addonRegistry);
		engine.stop();
	}
	

	/**
	 * to override in subclasses
	 * 
	 * @return TaskExcecutor instance from subclass
	 */
	public abstract void executeTasks(AddonRegistry addonRegistry);
	
}
