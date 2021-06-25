package com.hit.dialogs.createGame.models;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class MetadataItemRenderer extends BasicComboBoxRenderer {
    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index,
                isSelected, cellHasFocus);

        MetadataItem item = (MetadataItem)value;
        setText( item.getName() );
        return this;
    }

}
