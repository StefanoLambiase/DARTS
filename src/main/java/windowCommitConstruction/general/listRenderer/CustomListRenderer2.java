package windowCommitConstruction.general.listRenderer;

import javax.swing.*;
import java.awt.*;

public class CustomListRenderer2 extends DefaultListCellRenderer{
    private CustomLabel2 renderer;

    /**
     * Custom renderer constructor.
     * We will use it to create actual renderer component instance.
     * We will also add a custom mouse listener to process close button.
     *
     * @param list our JList instance
     */
    public CustomListRenderer2 ( final JList list )
    {
        super ();
        renderer = new CustomLabel2();
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
    public Component getListCellRendererComponent (JList list, Object value, int index, boolean isSelected, boolean cellHasFocus )
    {
        renderer.setSelected ( isSelected );
        renderer.setData ( (String) value );

        return renderer;
    }
}
