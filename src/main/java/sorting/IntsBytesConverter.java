package sorting;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class IntsBytesConverter {
	private static final int BYTES_IN_INT = 4;

    public static int[] bytesToInts(byte [] values) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(values);
            DataInputStream dataInputStream = new DataInputStream(bis);
            int size = values.length / BYTES_IN_INT;
            int[] res = new int[size];
            for (int i = 0; i < size; i++) {
                res[i] = dataInputStream.readInt();
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }	
	
	public static byte[] intsToBytes(int[] values) throws IOException {
		ByteBuffer byteBuffer = ByteBuffer.allocate(values.length * BYTES_IN_INT);        
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(values);
        return byteBuffer.array();
	}  

}
