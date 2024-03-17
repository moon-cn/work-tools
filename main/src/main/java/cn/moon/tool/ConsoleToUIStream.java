package cn.moon.tool;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

class ConsoleToUIStream extends OutputStream {
    private JTextArea textArea;

    public ConsoleToUIStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    ByteBuffer bf = ByteBuffer.allocate(1024);

    @Override
    public void write(int b) throws IOException {
        bf.put((byte) b);

        if (b == '\n') {
            byte[] array = bf.array();
            textArea.append(new String(array, 0, bf.position()));
            textArea.setCaretPosition(textArea.getDocument().getLength());

            bf.clear();
        }


    }
}