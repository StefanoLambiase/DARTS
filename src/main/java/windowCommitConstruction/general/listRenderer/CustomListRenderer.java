package windowCommitConstruction.general.listRenderer;

import testSmellDetection.testSmellInfo.TestSmellInfo;

import javax.swing.*;
import java.awt.*;

public class CustomListRenderer extends DefaultListCellRenderer{
    private CustomLabel renderer;

    /**
     * Custom renderer constructor.
     * We will use it to create actual renderer component instance.
     * We will also add a custom mouse listener to process close button.
     *
     * @param list our JList instance
     */
    public CustomListRenderer ( final JList list )
    {
        super ();
        renderer = new CustomLabel();
    }

    /**
     * Returns custom renderer for each cell of the list.
     *
     * @param list         list to process
     * @param value        cell value (CustomData object in our case)
     * @param index        cell index
     * @param isSelected   whether cell is selected or not
     * @param cellHasFocus whether cell has focus or not
     * @return custom renderer for each cell of the list
     */
    @Override
    public Component getListCellRendererComponent ( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus )
    {
        renderer.setSelected ( isSelected );
        renderer.setData ( (TestSmellInfo) value );

        return renderer;
    }

}
