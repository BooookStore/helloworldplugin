package com.example.helloworld;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "com.example.helloworld"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	public List<Bundle> bundles = new ArrayList<>();
	
	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * このpluginがスタートする際に呼び出されます。
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		System.out.println("hello world stated");

		//システム内の、他のplug-inを取得しコンソールに表示、リストへ格納
		Bundle[] bs = getBundle().getBundleContext().getBundles();
		for (int i = 0; i < bs.length; i++) {
			System.out.println(bs[i]);
			bundles.add(bs[i]);
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		System.out.println("hello world end");
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}
}
