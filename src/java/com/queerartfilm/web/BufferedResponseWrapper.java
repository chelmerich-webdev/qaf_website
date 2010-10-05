package com.queerartfilm.web;

import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * A <code>HttpServletResponseWrapper</code> that allows access to the
 * buffered data of the underlying Response.
 *
 * @author Curt Helmerich
 * @author ch67dev@gmail.com
 */
public class BufferedResponseWrapper extends HttpServletResponseWrapper {

    private static final String ENCODING = "UTF-8";
    private ByteArrayOutputStream byteBuffer;
    private ServletOutputStream outputStream;
    private CharArrayWriter charBuffer;
    private PrintWriter writer;

    /**
     * Constructor
     *
     * @param response the Response object to be wrapped.
     */
    public BufferedResponseWrapper(HttpServletResponse response) {
        super(response);
    }


    /**
     * Returns the contents of the buffer as a <code>String</code>.
     *
     * @return the buffer as a character string.
     * @throws IOException
     */
    public String getBufferAsString() throws IOException {
        String buffer = null;

        if (charBuffer != null) {
            writer.flush();
            charBuffer.flush();
            buffer = charBuffer.toString();
        } else if (byteBuffer != null) {
            outputStream.flush();
            byteBuffer.flush();
            buffer = byteBuffer.toString(ENCODING);
        } else {
            buffer = "";
        }

        return buffer;
    }

    /**
     * Returns a character writer.
     *
     * @return a character writer.
     * @throws IOException
     */
    @Override
    public PrintWriter getWriter() throws IOException {
        if (outputStream != null) {
            throw new IOException("getOutputStream() was already called!");
        }
        
        if (writer == null) {
            charBuffer = new CharArrayWriter();
            writer = new PrintWriter(charBuffer);
        }

        return writer;
    }

    /**
     * Returns the contents of the buffer as a byte array.
     *
     * @return the buffer as a byte array.
     * @throws IOException
     */
    public byte[] getBufferAsByteArray() throws IOException {
        byte[] buffer = null;

        if (byteBuffer != null) {
            outputStream.flush();
            byteBuffer.flush();
            buffer = byteBuffer.toByteArray();
        } else if (charBuffer != null) {
            writer.flush();
            charBuffer.flush();
            buffer = charBuffer.toString().getBytes();
        } else {
            buffer = new byte[0];
        }

        return buffer;
    }

    /**
     * Create a subclass of <code>ServletOutputStream</code> that delegates
     * everything to a <code>ByteArrayOutputStream</code>.
     *
     * @return a binary output stream.
     * @throws IOException
     */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (writer != null) {
            throw new IllegalStateException("getWriter() was already called!");
        }
        if (outputStream == null) {
            byteBuffer = new ByteArrayOutputStream();
            outputStream = new ServletOutputStream() {

                @Override
                public void write(int c) throws IOException {
                    byteBuffer.write(c);
                }
            };
        }

        return outputStream;
    }
}
