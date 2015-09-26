package com.android.desafioaudionews.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by rmuhamed on 22/07/2015.
 */
public class StreamToStringConverter {

    /**
     * Convierte la respuesta desde Volley a un String (el JSON) por partes y buffereado
     * Con este mecanismo se intenta evitar el TransactionTooLargeException
     * @param byteResponse
     * @return
     * @throws IllegalStateException
     * @throws IOException
     */
    public static String convert(byte[] byteResponse) throws IllegalStateException, IOException {
        String response = "";
        StringBuilder builder = new StringBuilder("");

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteResponse);
        BufferedReader reader = new BufferedReader(new InputStreamReader(byteArrayInputStream));

        final char[] buffer = new char[1024];
        try {
            for (int c; (c = reader.read(buffer, 0, buffer.length)) != -1; ) {
                builder.append(buffer, 0, c);
            }
        }catch (OutOfMemoryError e){
            response = "";
            return response;
        }

        response = builder.toString();

        return response;
    }

}
