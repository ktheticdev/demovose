package com.gontones.demovo;

        import java.awt.datatransfer.UnsupportedFlavorException;
        import java.io.IOException;
        import java.awt.datatransfer.DataFlavor;
        import java.awt.Toolkit;
        import java.awt.datatransfer.Transferable;
        import java.awt.datatransfer.Clipboard;
        import java.awt.datatransfer.StringSelection;
        import java.awt.datatransfer.ClipboardOwner;

public class TextTransfer implements ClipboardOwner
{
    static StringSelection stringSelection;

    @Override
    public void lostOwnership(final Clipboard clipboard, final Transferable contents) {
    }

    public static void setData(final String data) {
        TextTransfer.stringSelection = new StringSelection(data);
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(TextTransfer.stringSelection, TextTransfer.stringSelection);
    }

    public static String getData() throws IOException, UnsupportedFlavorException {
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        return (String)clipboard.getData(DataFlavor.stringFlavor);
    }
}