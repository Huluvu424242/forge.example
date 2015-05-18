package com.github.funthomas.examples.forge;

import org.jboss.forge.furnace.addons.AddonRegistry;

public interface TaskExcecutor extends Runnable{
	
	public abstract void executeTasks(final AddonRegistry addonRegistry);
	
}
