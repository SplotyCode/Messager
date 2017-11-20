package me.david.messagecore.netwok;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.DecoderException;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

/*
 * Convert byte to Objects(String, int, List etc)
 */
public class PacketSerializer extends ByteBuf {

    private final ByteBuf buf;
    public PacketSerializer(ByteBuf buf) {
        this.buf = buf;
    }

    public void writeVarInt(int value) {
        byte part;
        do {
            part = (byte) (value & 0x7F);
            value >>>= 7;
            if(value != 0) part |= 0x80;
            this.buf.writeByte(part);
        } while (value != 0);
    }
    public int readVarInt() {
        int out = 0, bytes = 0;
        byte part;
        do {
            part = this.buf.readByte();
            out |= (part & 0x7F) << (bytes++ * 7);
            if(bytes > 5) throw new DecoderException(String.format("VarInt is too long! (%d > 5)", bytes));
        } while ((part & 0x80) == 0x80);
        return out;
    }

    public <T> T readEnum(Class<T> enumClass) {
        return enumClass.getEnumConstants()[this.readVarInt()];
    }
    public void writeEnum(Enum<?> source) {
        this.writeVarInt(source.ordinal());
    }

    public Packet readPacket(Packet packet) {
        try {
            packet.read(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return packet;
    }
    public void writePacket(Packet packet) {
        try {
            packet.write(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String readString() {
        byte[] b = new byte[readVarInt()];
        readBytes(b);
        return new String(b, CharsetUtil.UTF_8);
    }
    public void writeString(String string) {
        byte[] abyte = string.getBytes(CharsetUtil.UTF_8);
        this.writeVarInt(abyte.length);
        this.writeBytes(abyte);
    }
    public ArrayList<String> readStringList(){
        int i = readVarInt();
        ArrayList<String> list = new ArrayList<String>();
        while(i != 0) {
            list.add(readString());
            i--;
        }
        return list;
    }
    public ArrayList<Integer> readIntList(){
        int i = readVarInt();
        ArrayList<Integer> list = new ArrayList<Integer>();
        while(i != 0) {
            list.add(readVarInt());
            i--;
        }
        return list;
    }
    public void writeIntList(ArrayList<Integer> list) {
        writeVarInt(list.size());
        for(Integer i : list)
            writeVarInt(i);;
    }
    public void writeStringList(ArrayList<String>list) {
        writeVarInt(list.size());
        for(String s : list)
            writeString(s);
    }
    public int capacity() {
        return 0;
    }

    public ByteBuf capacity(int i) {
        return null;
    }

    public int maxCapacity() {
        return 0;
    }

    public ByteBufAllocator alloc() {
        return null;
    }

    public ByteOrder order() {
        return null;
    }

    public ByteBuf order(ByteOrder byteOrder) {
        return null;
    }

    public ByteBuf unwrap() {
        return null;
    }

    public boolean isDirect() {
        return false;
    }

    public boolean isReadOnly() {
        return false;
    }

    public ByteBuf asReadOnly() {
        return null;
    }

    public int readerIndex() {
        return 0;
    }

    public ByteBuf readerIndex(int i) {
        return null;
    }

    public int writerIndex() {
        return 0;
    }

    public ByteBuf writerIndex(int i) {
        return null;
    }

    public ByteBuf setIndex(int i, int i1) {
        return null;
    }

    public int readableBytes() {
        return 0;
    }

    public int writableBytes() {
        return 0;
    }

    public int maxWritableBytes() {
        return 0;
    }

    public boolean isReadable() {
        return false;
    }

    public boolean isReadable(int i) {
        return false;
    }

    public boolean isWritable() {
        return false;
    }

    public boolean isWritable(int i) {
        return false;
    }

    public ByteBuf clear() {
        return null;
    }

    public ByteBuf markReaderIndex() {
        return null;
    }

    public ByteBuf resetReaderIndex() {
        return null;
    }

    public ByteBuf markWriterIndex() {
        return null;
    }

    public ByteBuf resetWriterIndex() {
        return null;
    }

    public ByteBuf discardReadBytes() {
        return null;
    }

    public ByteBuf discardSomeReadBytes() {
        return null;
    }

    public ByteBuf ensureWritable(int i) {
        return null;
    }

    public int ensureWritable(int i, boolean b) {
        return 0;
    }

    public boolean getBoolean(int i) {
        return false;
    }

    public byte getByte(int i) {
        return 0;
    }

    public short getUnsignedByte(int i) {
        return 0;
    }

    public short getShort(int i) {
        return 0;
    }

    public short getShortLE(int i) {
        return 0;
    }

    public int getUnsignedShort(int i) {
        return 0;
    }

    public int getUnsignedShortLE(int i) {
        return 0;
    }

    public int getMedium(int i) {
        return 0;
    }

    public int getMediumLE(int i) {
        return 0;
    }

    public int getUnsignedMedium(int i) {
        return 0;
    }

    public int getUnsignedMediumLE(int i) {
        return 0;
    }

    public int getInt(int i) {
        return 0;
    }

    public int getIntLE(int i) {
        return 0;
    }

    public long getUnsignedInt(int i) {
        return 0;
    }

    public long getUnsignedIntLE(int i) {
        return 0;
    }

    public long getLong(int i) {
        return 0;
    }

    public long getLongLE(int i) {
        return 0;
    }

    public char getChar(int i) {
        return 0;
    }

    public float getFloat(int i) {
        return 0;
    }

    public double getDouble(int i) {
        return 0;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf) {
        return null;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1) {
        return null;
    }

    public ByteBuf getBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        return null;
    }

    public ByteBuf getBytes(int i, byte[] bytes) {
        return null;
    }

    public ByteBuf getBytes(int i, byte[] bytes, int i1, int i2) {
        return null;
    }

    public ByteBuf getBytes(int i, java.nio.ByteBuffer byteBuffer) {
        return null;
    }

    public ByteBuf getBytes(int i, OutputStream outputStream, int i1) throws IOException {
        return null;
    }

    public int getBytes(int i, GatheringByteChannel gatheringByteChannel, int i1) throws IOException {
        return 0;
    }

    public int getBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return 0;
    }

    public CharSequence getCharSequence(int i, int i1, Charset charset) {
        return null;
    }

    public ByteBuf setBoolean(int i, boolean b) {
        return null;
    }

    public ByteBuf setByte(int i, int i1) {
        return null;
    }

    public ByteBuf setShort(int i, int i1) {
        return null;
    }

    public ByteBuf setShortLE(int i, int i1) {
        return null;
    }

    public ByteBuf setMedium(int i, int i1) {
        return null;
    }

    public ByteBuf setMediumLE(int i, int i1) {
        return null;
    }

    public ByteBuf setInt(int i, int i1) {
        return null;
    }

    public ByteBuf setIntLE(int i, int i1) {
        return null;
    }

    public ByteBuf setLong(int i, long l) {
        return null;
    }

    public ByteBuf setLongLE(int i, long l) {
        return null;
    }

    public ByteBuf setChar(int i, int i1) {
        return null;
    }

    public ByteBuf setFloat(int i, float v) {
        return null;
    }

    public ByteBuf setDouble(int i, double v) {
        return null;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf) {
        return null;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1) {
        return null;
    }

    public ByteBuf setBytes(int i, ByteBuf byteBuf, int i1, int i2) {
        return null;
    }

    public ByteBuf setBytes(int i, byte[] bytes) {
        return null;
    }

    public ByteBuf setBytes(int i, byte[] bytes, int i1, int i2) {
        return null;
    }

    public ByteBuf setBytes(int i, java.nio.ByteBuffer byteBuffer) {
        return null;
    }

    public int setBytes(int i, InputStream inputStream, int i1) throws IOException {
        return 0;
    }

    public int setBytes(int i, ScatteringByteChannel scatteringByteChannel, int i1) throws IOException {
        return 0;
    }

    public int setBytes(int i, FileChannel fileChannel, long l, int i1) throws IOException {
        return 0;
    }

    public ByteBuf setZero(int i, int i1) {
        return null;
    }

    public int setCharSequence(int i, CharSequence charSequence, Charset charset) {
        return 0;
    }

    public boolean readBoolean() {
        return false;
    }

    public byte readByte() {
        return 0;
    }

    public short readUnsignedByte() {
        return 0;
    }

    public short readShort() {
        return 0;
    }

    public short readShortLE() {
        return 0;
    }

    public int readUnsignedShort() {
        return 0;
    }

    public int readUnsignedShortLE() {
        return 0;
    }

    public int readMedium() {
        return 0;
    }

    public int readMediumLE() {
        return 0;
    }

    public int readUnsignedMedium() {
        return 0;
    }

    public int readUnsignedMediumLE() {
        return 0;
    }

    public int readInt() {
        return 0;
    }

    public int readIntLE() {
        return 0;
    }

    public long readUnsignedInt() {
        return 0;
    }

    public long readUnsignedIntLE() {
        return 0;
    }

    public long readLong() {
        return 0;
    }

    public long readLongLE() {
        return 0;
    }

    public char readChar() {
        return 0;
    }

    public float readFloat() {
        return 0;
    }

    public double readDouble() {
        return 0;
    }

    public ByteBuf readBytes(int i) {
        return null;
    }

    public ByteBuf readSlice(int i) {
        return null;
    }

    public ByteBuf readRetainedSlice(int i) {
        return null;
    }

    public ByteBuf readBytes(ByteBuf byteBuf) {
        return null;
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int i) {
        return null;
    }

    public ByteBuf readBytes(ByteBuf byteBuf, int i, int i1) {
        return null;
    }

    public ByteBuf readBytes(byte[] bytes) {
        return null;
    }

    public ByteBuf readBytes(byte[] bytes, int i, int i1) {
        return null;
    }

    public ByteBuf readBytes(java.nio.ByteBuffer byteBuffer) {
        return null;
    }

    public ByteBuf readBytes(OutputStream outputStream, int i) throws IOException {
        return null;
    }

    public int readBytes(GatheringByteChannel gatheringByteChannel, int i) throws IOException {
        return 0;
    }

    public CharSequence readCharSequence(int i, Charset charset) {
        return null;
    }

    public int readBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return 0;
    }

    public ByteBuf skipBytes(int i) {
        return null;
    }

    public ByteBuf writeBoolean(boolean b) {
        return null;
    }

    public ByteBuf writeByte(int i) {
        return null;
    }

    public ByteBuf writeShort(int i) {
        return null;
    }

    public ByteBuf writeShortLE(int i) {
        return null;
    }

    public ByteBuf writeMedium(int i) {
        return null;
    }

    public ByteBuf writeMediumLE(int i) {
        return null;
    }

    public ByteBuf writeInt(int i) {
        return null;
    }

    public ByteBuf writeIntLE(int i) {
        return null;
    }

    public ByteBuf writeLong(long l) {
        return null;
    }

    public ByteBuf writeLongLE(long l) {
        return null;
    }

    public ByteBuf writeChar(int i) {
        return null;
    }

    public ByteBuf writeFloat(float v) {
        return null;
    }

    public ByteBuf writeDouble(double v) {
        return null;
    }

    public ByteBuf writeBytes(ByteBuf byteBuf) {
        return null;
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int i) {
        return null;
    }

    public ByteBuf writeBytes(ByteBuf byteBuf, int i, int i1) {
        return null;
    }

    public ByteBuf writeBytes(byte[] bytes) {
        return null;
    }

    public ByteBuf writeBytes(byte[] bytes, int i, int i1) {
        return null;
    }

    public ByteBuf writeBytes(java.nio.ByteBuffer byteBuffer) {
        return null;
    }

    public int writeBytes(InputStream inputStream, int i) throws IOException {
        return 0;
    }

    public int writeBytes(ScatteringByteChannel scatteringByteChannel, int i) throws IOException {
        return 0;
    }

    public int writeBytes(FileChannel fileChannel, long l, int i) throws IOException {
        return 0;
    }

    public ByteBuf writeZero(int i) {
        return null;
    }

    public int writeCharSequence(CharSequence charSequence, Charset charset) {
        return 0;
    }

    public int indexOf(int i, int i1, byte b) {
        return 0;
    }

    public int bytesBefore(byte b) {
        return 0;
    }

    public int bytesBefore(int i, byte b) {
        return 0;
    }

    public int bytesBefore(int i, int i1, byte b) {
        return 0;
    }

    public int forEachByte(ByteProcessor byteProcessor) {
        return 0;
    }

    public int forEachByte(int i, int i1, ByteProcessor byteProcessor) {
        return 0;
    }

    public int forEachByteDesc(ByteProcessor byteProcessor) {
        return 0;
    }

    public int forEachByteDesc(int i, int i1, ByteProcessor byteProcessor) {
        return 0;
    }

    public ByteBuf copy() {
        return null;
    }

    public ByteBuf copy(int i, int i1) {
        return null;
    }

    public ByteBuf slice() {
        return null;
    }

    public ByteBuf retainedSlice() {
        return null;
    }

    public ByteBuf slice(int i, int i1) {
        return null;
    }

    public ByteBuf retainedSlice(int i, int i1) {
        return null;
    }

    public ByteBuf duplicate() {
        return null;
    }

    public ByteBuf retainedDuplicate() {
        return null;
    }

    public int nioBufferCount() {
        return 0;
    }

    public java.nio.ByteBuffer nioBuffer() {
        return null;
    }

    public java.nio.ByteBuffer nioBuffer(int i, int i1) {
        return null;
    }

    public java.nio.ByteBuffer internalNioBuffer(int i, int i1) {
        return null;
    }

    public java.nio.ByteBuffer[] nioBuffers() {
        return new java.nio.ByteBuffer[0];
    }

    public java.nio.ByteBuffer[] nioBuffers(int i, int i1) {
        return new java.nio.ByteBuffer[0];
    }

    public boolean hasArray() {
        return false;
    }

    public byte[] array() {
        return new byte[0];
    }

    public int arrayOffset() {
        return 0;
    }

    public boolean hasMemoryAddress() {
        return false;
    }

    public long memoryAddress() {
        return 0;
    }

    public String toString(Charset charset) {
        return null;
    }

    public String toString(int i, int i1, Charset charset) {
        return null;
    }

    public int hashCode() {
        return 0;
    }

    public boolean equals(Object o) {
        return false;
    }

    public int compareTo(ByteBuf byteBuf) {
        return 0;
    }

    public String toString() {
        return null;
    }

    public ByteBuf retain(int i) {
        return null;
    }

    public int refCnt() {
        return 0;
    }

    public ByteBuf retain() {
        return null;
    }

    public ByteBuf touch() {
        return null;
    }

    public ByteBuf touch(Object o) {
        return null;
    }

    public boolean release() {
        return false;
    }

    public boolean release(int i) {
        return false;
    }
}
