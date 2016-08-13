package com.example.helloworld;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.core.runtime.content.IContentTypeManager;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class HelloWorldView extends ViewPart {

	/**
	 * ���݊J����Ă���p�b�P�[�W��\������JFace widget
	 */
	ListViewer viewList;

	public HelloWorldView() {

	}

	public void createPartControl(Composite parent) {

		//�\�������widght���C���X�^���X��
		viewList = new ListViewer(parent);
		
		//�R���e���c�v���o�C�_�̓o�^
		viewList.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object arg0) {
				ArrayList<String> v = (ArrayList<String>) arg0;
				return v.toArray();
			}
		});

		//���x���v���o�C�_�̓o�^
		viewList.setLabelProvider(new ProjectNameLabelProvider());
		
		//�\������Ώۂ�o�^
		viewList.setInput(getProjects());

		//���\�[�X���ω������ۂ̓����ݒ�
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new ResourceChangeListener(parent));
		
		getSite().setSelectionProvider(viewList);
	}

	public void setFocus() {
		// set focus to my widget. For a label, this doesn't
		// make much sense, but for more complex sets of widgets
		// you would decide which one gets the focus.
	}

	/**
	 * �J����Ă���v���W�F�N�g�̖��O��Ԃ��܂��B
	 * 
	 * @param parent
	 */
	private List<IProject> getProjects() {
		// �J����Ă���v���W�F�N�g���擾
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		IProject[] projects = root.getProjects();

		// List�֋l�߂�
		List<IProject> projectList = new ArrayList<>();
		for (int i = 0; i < projects.length; i++) {
			projectList.add(projects[i]);
		}
		
		return projectList;
	}

	/**
	 * Resource�̕ω������ۂ̓�������߂܂��B
	 * 
	 * @see IResourceChangeListener
	 * @author bookstore
	 *
	 */
	public class ResourceChangeListener implements IResourceChangeListener {

		private Composite parent;
		
		public ResourceChangeListener(Composite parent) {
			this.parent = parent;
		}
		
		@Override
		public void resourceChanged(IResourceChangeEvent arg0) {
			
			System.out.println(arg0.getType());
			
			parent.getDisplay().syncExec(new Runnable() {
				
				@Override
				public void run() {
					viewList.setInput(getProjects());
				}
			});
		}
		
	}

	
	private void showConsoleOtherPluginNames() {
		// ����plugin�̖��O���擾
		java.util.List<String> bundleNames = new java.util.ArrayList<>();
		Activator.getDefault().bundles.stream().forEach(p -> bundleNames.add(p.getSymbolicName()));

	}

	private void getPreferenceSample() {
		// IPreferenceService sample .
		IPreferencesService service = Platform.getPreferencesService();
		boolean value = service.getBoolean("com.example.helloworld", "MyPreference", true, null);
		System.out.println("MyPreference value is " + value);
	}

	private void contentTypeSample() {
		// Content type sample .
		IContentTypeManager contentTypeManager = Platform.getContentTypeManager();
		IContentType[] types = contentTypeManager.getAllContentTypes();
		System.out.println("All Content Types is ");
		for (int i = 0; i < types.length; i++) {
			System.out.println(types[i].getName());
		}

		System.out.println();

		try {
			InputStream stream = new FileInputStream(new File("c:\\Users\\bookstore\\Desktop\\sample.txt"));
			IContentType contentType = contentTypeManager.findContentTypeFor(stream, "sample.txt");
			System.out.println("This file is " + contentType.getName() + " id is " + contentType.getId());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}