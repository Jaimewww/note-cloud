package edu.cc.notecloud.util;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EncryptorManager implements AttributeConverter<char[], byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(char[] rawPassword) {
        if (rawPassword == null) return null;
        return BCrypt.withDefaults()
                .hash(12, rawPassword);
    }

    @Override
    public char[] convertToEntityAttribute(byte[] dbData) {
        return new char[0];
    }
}
