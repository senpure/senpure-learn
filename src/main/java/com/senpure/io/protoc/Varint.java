package com.senpure.io.protoc;

import io.netty.buffer.ByteBuf;

import java.io.IOException;

/**
 * Created by 罗中正 on 2017/12/2 0002.
 */
public class Varint {


    public static void writeVarlong(ByteBuf buf, long value) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buf.writeByte((int) value);
                return;
            } else {
                buf.writeByte(((int) value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }

    public static void writeVarint(ByteBuf buf, int value) {
        while (true) {
            if ((value & ~0x7F) == 0) {
                buf.writeByte(value);
                return;
            } else {
                buf.writeByte((value & 0x7F) | 0x80);
                value >>>= 7;
            }
        }
    }


    public static int readVarint(ByteBuf buf) {
        byte tmp = buf.readByte();
        if (tmp >= 0) {
            return tmp;
        }
        int result = tmp & 0x7f;
        if ((tmp = buf.readByte()) >= 0) {
            result |= tmp << 7;
        } else {
            result |= (tmp & 0x7f) << 7;
            if ((tmp = buf.readByte()) >= 0) {
                result |= tmp << 14;
            } else {
                result |= (tmp & 0x7f) << 14;
                if ((tmp = buf.readByte()) >= 0) {
                    result |= tmp << 21;
                } else {
                    result |= (tmp & 0x7f) << 21;
                    result |= (tmp = buf.readByte()) << 28;
                    if (tmp < 0) {
                        // Discard upper 32 bits.
                        for (int i = 0; i < 5; i++) {
                            if (buf.readByte() >= 0) {
                                return result;
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    public static long readVarlong(ByteBuf buf) {
        int shift = 0;
        long result = 0;
        while (shift < 64) {
            final byte b = buf.readByte();
            result |= (long) (b & 0x7F) << shift;
            if ((b & 0x80) == 0) {
                return result;
            }
            shift += 7;
        }
        return result;
    }

    public static void main(String[] args) {

        int value = 12;
        System.out.println(Integer.toBinaryString(value));
        value = (value & 0x7F) | 0x80;
        System.out.println(Integer.toBinaryString(value));
    }
}
