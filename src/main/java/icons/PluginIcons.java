package icons;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public interface PluginIcons {
    Icon load = IconLoader.getIcon("/icons/load.svg", PluginIcons.class);

    Icon Time = IconLoader.getIcon("/icons/time.svg", PluginIcons.class);
    /**
     * ToolWindow使用的图标(灰色) 尺寸13*13
     */
    //Icon ToolWindowGray = IconLoader.getIcon("/icons/note.svg", PluginIcons.class);
    /**
     *  ToolWindow使用的图标(蓝色渐变) 尺寸13*13
     */
    Icon ToolWindowBlue = IconLoader.getIcon("/icons/tool_window.svg", PluginIcons.class);


}