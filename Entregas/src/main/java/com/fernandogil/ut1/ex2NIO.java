/*2. Realiza una aplicación, haciendo uso de las clases de NIO, incluyendo buffers y canales,
para hacer una copia del fichero de acceso directo generado en la anterior aplicación.*/
package com.fernandogil.ut1;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ex2NIO {
    private static int tamRegistro = rafTenista.TAMANO;
    private static int numRegistros = rafTenista.numRegistros;

    public static void main(String[] args) {

        try (RandomAccessFile raf = new RandomAccessFile("rafTenistas.dat", "rw");) {
            FileChannel fileChannel = raf.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((tamRegistro)*(numRegistros));
            while (fileChannel.read(byteBuffer) > 0) {
                byteBuffer.flip();

                while (byteBuffer.hasRemaining())
                    System.out.print((char) byteBuffer.get());
            }
            raf.close();

        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}


