package demo.jvm;

import java.io.*;
import java.lang.reflect.Method;

public class HelloXClassLoader extends ClassLoader{
    public static void main(String[] args) {
        try {
            Class helloClass = new HelloXClassLoader().findClass("Hello");
            Object helloObj = helloClass.newInstance();
            Method helloMethod = helloClass.getDeclaredMethod("hello");
            helloMethod.invoke(helloObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = helloXlassDecode();
        return defineClass(name,bytes,0,bytes.length);
    }

    public byte[] helloXlassDecode(){
        byte[] bytes = toByteArray("D:\\Hello.xlass");
        for(int i = 0; i < bytes.length; i++){
            bytes[i] = (byte)(255- bytes[i]);
        }
        return bytes;
    }

    public byte[] toByteArray(String filePath){
        ByteArrayOutputStream bos=null;
        BufferedInputStream in = null;
        try {
            File f = new File(filePath);
            if(f.exists()){
                in = new BufferedInputStream(new FileInputStream(f));
                bos = new ByteArrayOutputStream((int) f.length());

                int buf_size = 1024;
                byte[] buffer = new byte[buf_size];
                int len = 0;
                while (-1 != (len = in.read(buffer, 0, buf_size))) {
                    bos.write(buffer, 0, len);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos.toByteArray();
    }
}
