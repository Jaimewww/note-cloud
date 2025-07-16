package edu.cc.notecloud.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptorManager implements AttributeConverter<char[], byte[]> {
    /**
     * Convierte una contraseña en texto plano (char[]) a un hash seguro (byte[]).
     * Utiliza BCrypt para generar el hash con un costo de 12.
     *
     * @param rawPassword la contraseña en texto plano
     * @return el hash de la contraseña, o null si rawPassword es null
     */
    @Override
    public byte[] convertToDatabaseColumn(char[] rawPassword) {
        if (rawPassword == null) return null;
        return BCrypt.withDefaults()
                .hash(12, rawPassword);
    }


    /* Este método no se usa en la aplicación, pero es necesario
     * para cumplir con la interfaz AttributeConverter.
     * Retorna un arreglo vacío para evitar problemas de conversión.
     */
    @Override
    public char[] convertToEntityAttribute(byte[] dbData) {
        return new char[0];
    }
}
