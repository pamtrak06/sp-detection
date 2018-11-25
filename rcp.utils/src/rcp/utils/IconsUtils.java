package rcp.utils;


import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;


public final class IconsUtils {

    public static Label createLabelImage(final Composite parent, final String iconId) {
        Label label = new Label(parent, SWT.PUSH);
        Image image2 = JFaceResources.getImageRegistry().get(iconId);
        Image image = new Image(parent.getDisplay(), image2, SWT.IMAGE_COPY);
        label.setImage(image);
        return label;
    }

    public static Image getIcon(final String iconId) {
        return JFaceResources.getImageRegistry().get(iconId);
    }

}
