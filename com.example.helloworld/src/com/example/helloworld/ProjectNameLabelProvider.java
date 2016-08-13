package com.example.helloworld;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.LabelProvider;

public class ProjectNameLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		IProject p = (IProject) element;
		return p.getName() + " - " + p.getLocation().toString();
	}

}
