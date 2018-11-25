package rcp.utils;

import java.io.File;

import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;

public final class WorkSpaceUtils {
	public static final File getRootFolder() {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IWorkspaceRoot root = workspace.getRoot();
        File rootFoolder = new File(root.getLocation().toFile().getAbsolutePath());
		return rootFoolder;
	}
}
