package main.support;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SerializationDumper {
    public final LinkedList<Byte> _data = new LinkedList();
    private String _indent = "";
    private int _handleValue = 8257536;
    private final ArrayList<ClassDataDesc> _classDataDescriptions = new ArrayList();
    public boolean _enablePrinting = true;
    private List<Byte> list = new ArrayList();

    public static void main(String[] args) throws Exception {
    }

    public SerializationDumper() {
    }

    private void print(String s) {
        if (this._enablePrinting) {
            System.out.println(this._indent + s);
        }

    }

    private void increaseIndent() {
        this._indent = this._indent + "  ";
    }

    private void decreaseIndent() {
        if (this._indent.length() < 2) {
            throw new RuntimeException("Error: Illegal indentation decrease.");
        } else {
            this._indent = this._indent.substring(0, this._indent.length() - 2);
        }
    }

    private String byteToHex(byte b) {
        return String.format("%02x", b);
    }

    private byte[] hexStrToBytes(String h) {
        byte[] outBytes = new byte[h.length() / 2];

        for(int i = 0; i < outBytes.length; ++i) {
            outBytes[i] = (byte)((Character.digit(h.charAt(i * 2), 16) << 4) + Character.digit(h.charAt(i * 2 + 1), 16));
        }

        return outBytes;
    }

    private String intToHex(int i) {
        return String.format("%02x", (i & -16777216) >> 24) + String.format(" %02x", (i & 16711680) >> 16) + String.format(" %02x", (i & '\uff00') >> 8) + String.format(" %02x", i & 255);
    }

    private int newHandle() {
        int handleValue = this._handleValue;
        this.print("newHandle 0x" + this.intToHex(handleValue));
        ++this._handleValue;
        return handleValue;
    }

    private void rebuildStream(String dumpFile, String outputFile) {
        System.out.println("Rebuilding serialization stream from dump: " + dumpFile);

        ByteArrayOutputStream byteStream;
        try {
            byteStream = new ByteArrayOutputStream();
            BufferedReader reader = new BufferedReader(new FileReader(dumpFile));

            String inputLine;
            while((inputLine = reader.readLine()) != null) {
                if (!inputLine.trim().startsWith("newHandle ") && inputLine.contains("0x")) {
                    if (inputLine.trim().startsWith("Value - ")) {
                        inputLine = inputLine.substring(inputLine.lastIndexOf("0x") + 2);
                    } else {
                        inputLine = inputLine.split("0x", 2)[1];
                    }

                    if (inputLine.contains(" - ")) {
                        inputLine = inputLine.split("-", 2)[0];
                    }

                    inputLine = inputLine.replace(" ", "");
                    byteStream.write(this.hexStrToBytes(inputLine));
                }
            }

            reader.close();
        } catch (FileNotFoundException var15) {
            System.out.println("Error: input file not found (" + dumpFile + ").");
            System.out.println(var15.getMessage());
            return;
        } catch (IOException var16) {
            System.out.println("Error: an exception occurred whilst reading the input file (" + dumpFile + ").");
            System.out.println(var16.getMessage());
            return;
        }

        System.out.println("Stream rebuilt, attempting to parse...");
        this._enablePrinting = false;
        byte[] rebuiltStream = byteStream.toByteArray();
        byte[] var8 = rebuiltStream;
        int var9 = rebuiltStream.length;

        for(int var10 = 0; var10 < var9; ++var10) {
            byte b = var8[var10];
            this._data.add(b);
        }

        try {
            this.parseStream();
        } catch (Exception var14) {
            System.out.println("Warning: An exception was thrown whilst attempting to parse the rebuilt stream.");
            System.out.println(var14.getMessage());
        }

        try {
            FileOutputStream outputFileStream = new FileOutputStream(outputFile);
            outputFileStream.write(rebuiltStream);
            outputFileStream.close();
        } catch (FileNotFoundException var12) {
            System.out.println("Error: exception opening output file (" + outputFile + ").");
            System.out.println(var12.getMessage());
            return;
        } catch (IOException var13) {
            System.out.println("Error: an exception occurred whilst writing the output file (" + outputFile + ").");
            System.out.println(var13.getMessage());
            return;
        }

        System.out.println("Done, rebuilt stream written to " + outputFile);
    }

    public void parseStream() throws Exception {
        byte b1;
        if ((Byte)this._data.peek() != -84) {
            b1 = (Byte)this._data.pop();
            switch(b1) {
                case 80:
                    this.print("RMI Call - 0x50");
                    break;
                case 81:
                    this.print("RMI ReturnData - 0x51");
                    break;
                case 82:
                    this.print("RMI Ping - 0x52");
                    break;
                case 83:
                    this.print("RMI PingAck - 0x53");
                    break;
                case 84:
                    this.print("RMI DgcAck - 0x54");
                    break;
                default:
                    this.print("Unknown RMI packet type - 0x" + this.byteToHex(b1));
            }
        }

        b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        this.print("STREAM_MAGIC - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2));
        if (b1 == -84 && b2 == -19) {
            b1 = (Byte)this._data.pop();
            b2 = (Byte)this._data.pop();
            this.print("STREAM_VERSION - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2));
            if (b1 != 0 || b2 != 5) {
                this.print("Invalid STREAM_VERSION, should be 0x00 05");
            }

            this.print("Contents");
            this.increaseIndent();

            while(this._data.size() > 0) {
                this.readContentElement();
            }

            this.decreaseIndent();
        } else {
            this.print("Invalid STREAM_MAGIC, should be 0xac ed");
        }
    }

    private void readContentElement() {
        switch((Byte)this._data.peek()) {
            case 112:
                this.readNullReference();
                break;
            case 113:
                this.readPrevObject();
                break;
            case 114:
            case 125:
                this.readNewClassDesc();
                break;
            case 115:
                this.readNewObject();
                break;
            case 116:
            case 124:
                this.readNewString();
                break;
            case 117:
                this.readNewArray();
                break;
            case 118:
                this.readNewClass();
                break;
            case 119:
                this.readBlockData();
                break;
            case 120:
            case 121:
            case 123:
            default:
                this.print("Invalid content element type 0x" + this.byteToHex((Byte)this._data.peek()));
                throw new RuntimeException("Error: Illegal content element type.");
            case 122:
                this.readLongBlockData();
                break;
            case 126:
                this.readNewEnum();
        }

    }

    private void readNewEnum() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_ENUM - 0x" + this.byteToHex(b1));
        if (b1 != 126) {
            throw new RuntimeException("Error: Illegal value for TC_ENUM (should be 0x7e)");
        } else {
            this.increaseIndent();
            this.readClassDesc();
            this.newHandle();
            this.readNewString();
            this.decreaseIndent();
        }
    }

    private void readNewObject() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_OBJECT - 0x" + this.byteToHex(b1));
        if (b1 != 115) {
            throw new RuntimeException("Error: Illegal value for TC_OBJECT (should be 0x73)");
        } else {
            this.increaseIndent();
            ClassDataDesc cdd = this.readClassDesc();
            this.newHandle();
            this.readClassData(cdd);
            this.decreaseIndent();
        }
    }

    private ClassDataDesc readClassDesc() {
        switch((Byte)this._data.peek()) {
            case 112:
                this.readNullReference();
                return null;
            case 113:
                int refHandle = this.readPrevObject();
                Iterator var2 = this._classDataDescriptions.iterator();

                while(var2.hasNext()) {
                    ClassDataDesc cdd = (ClassDataDesc)var2.next();

                    for(int classIndex = 0; classIndex < cdd.getClassCount(); ++classIndex) {
                        if (cdd.getClassDetails(classIndex).getHandle() == refHandle) {
                            return cdd.buildClassDataDescFromIndex(classIndex);
                        }
                    }
                }

                throw new RuntimeException("Error: Invalid classDesc reference (0x" + this.intToHex(refHandle) + ")");
            case 114:
            case 125:
                return this.readNewClassDesc();
            default:
                this.print("Invalid classDesc type 0x" + this.byteToHex((Byte)this._data.peek()));
                throw new RuntimeException("Error illegal classDesc type.");
        }
    }

    private ClassDataDesc readNewClassDesc() {
        ClassDataDesc cdd;
        switch((Byte)this._data.peek()) {
            case 114:
                cdd = this.readTC_CLASSDESC();
                this._classDataDescriptions.add(cdd);
                return cdd;
            case 125:
                cdd = this.readTC_PROXYCLASSDESC();
                this._classDataDescriptions.add(cdd);
                return cdd;
            default:
                this.print("Invalid newClassDesc type 0x" + this.byteToHex((Byte)this._data.peek()));
                throw new RuntimeException("Error illegal newClassDesc type.");
        }
    }

    private ClassDataDesc readTC_CLASSDESC() {
        ClassDataDesc cdd = new ClassDataDesc();
        byte b1 = (Byte)this._data.pop();
        this.print("TC_CLASSDESC - 0x" + this.byteToHex(b1));
        if (b1 != 114) {
            throw new RuntimeException("Error: Illegal value for TC_CLASSDESC (should be 0x72)");
        } else {
            this.increaseIndent();
            this.print("className");
            this.increaseIndent();
            cdd.addClass(this.readUtf());
            this.decreaseIndent();
            this.print("serialVersionUID - 0x" + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()) + " " + this.byteToHex((Byte)this._data.pop()));
            cdd.setLastClassHandle(this.newHandle());
            this.readClassDescInfo(cdd);
            this.decreaseIndent();
            return cdd;
        }
    }

    private ClassDataDesc readTC_PROXYCLASSDESC() {
        ClassDataDesc cdd = new ClassDataDesc();
        byte b1 = (Byte)this._data.pop();
        this.print("TC_PROXYCLASSDESC - 0x" + this.byteToHex(b1));
        if (b1 != 125) {
            throw new RuntimeException("Error: Illegal value for TC_PROXYCLASSDESC (should be 0x7d)");
        } else {
            this.increaseIndent();
            cdd.addClass("<Dynamic Proxy Class>");
            cdd.setLastClassHandle(this.newHandle());
            this.readProxyClassDescInfo(cdd);
            this.decreaseIndent();
            return cdd;
        }
    }

    private void readClassDescInfo(ClassDataDesc cdd) {
        String classDescFlags = "";
        byte b1 = (Byte)this._data.pop();
        if ((b1 & 1) == 1) {
            classDescFlags = classDescFlags + "SC_WRITE_METHOD | ";
        }

        if ((b1 & 2) == 2) {
            classDescFlags = classDescFlags + "SC_SERIALIZABLE | ";
        }

        if ((b1 & 4) == 4) {
            classDescFlags = classDescFlags + "SC_EXTERNALIZABLE | ";
        }

        if ((b1 & 8) == 8) {
            classDescFlags = classDescFlags + "SC_BLOCKDATA | ";
        }

        if (classDescFlags.length() > 0) {
            classDescFlags = classDescFlags.substring(0, classDescFlags.length() - 3);
        }

        this.print("classDescFlags - 0x" + this.byteToHex(b1) + " - " + classDescFlags);
        cdd.setLastClassDescFlags(b1);
        if ((b1 & 2) == 2) {
            if ((b1 & 4) == 4) {
                throw new RuntimeException("Error: Illegal classDescFlags, SC_SERIALIZABLE is not compatible with SC_EXTERNALIZABLE.");
            }

            if ((b1 & 8) == 8) {
                throw new RuntimeException("Error: Illegal classDescFlags, SC_SERIALIZABLE is not compatible with SC_BLOCKDATA.");
            }
        } else if ((b1 & 4) == 4) {
            if ((b1 & 1) == 1) {
                throw new RuntimeException("Error: Illegal classDescFlags, SC_EXTERNALIZABLE is not compatible with SC_WRITE_METHOD.");
            }
        } else if (b1 != 0) {
            throw new RuntimeException("Error: Illegal classDescFlags, must include either SC_SERIALIZABLE or SC_EXTERNALIZABLE.");
        }

        this.readFields(cdd);
        this.readClassAnnotation();
        cdd.addSuperClassDesc(this.readSuperClassDesc());
    }

    private void readProxyClassDescInfo(ClassDataDesc cdd) {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        byte b3 = (Byte)this._data.pop();
        byte b4 = (Byte)this._data.pop();
        int count = (b1 << 24 & -16777216) + (b2 << 16 & 16711680) + (b3 << 8 & '\uff00') + (b4 & 255);
        this.print("Interface count - " + count + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4));
        this.print("proxyInterfaceNames");
        this.increaseIndent();

        for(int i = 0; i < count; ++i) {
            this.print(i + ":");
            this.increaseIndent();
            this.readUtf();
            this.decreaseIndent();
        }

        this.decreaseIndent();
        this.readClassAnnotation();
        cdd.addSuperClassDesc(this.readSuperClassDesc());
    }

    private void readClassAnnotation() {
        this.print("classAnnotations");
        this.increaseIndent();

        while((Byte)this._data.peek() != 120) {
            this.readContentElement();
        }

        this._data.pop();
        this.print("TC_ENDBLOCKDATA - 0x78");
        this.decreaseIndent();
    }

    private ClassDataDesc readSuperClassDesc() {
        this.print("superClassDesc");
        this.increaseIndent();
        ClassDataDesc cdd = this.readClassDesc();
        this.decreaseIndent();
        return cdd;
    }

    private void readFields(ClassDataDesc cdd) {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        short count = (short)((b1 << 8 & '\uff00') + (b2 & 255));
        this.print("fieldCount - " + count + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2));
        if (count > 0) {
            this.print("Fields");
            this.increaseIndent();

            for(int i = 0; i < count; ++i) {
                this.print(i + ":");
                this.increaseIndent();
                this.readFieldDesc(cdd);
                this.decreaseIndent();
            }

            this.decreaseIndent();
        }

    }

    private void readFieldDesc(ClassDataDesc cdd) {
        byte b1 = (Byte)this._data.pop();
        cdd.addFieldToLastClass(b1);
        switch((char)b1) {
            case 'B':
                this.print("Byte - B - 0x" + this.byteToHex(b1));
                break;
            case 'C':
                this.print("Char - C - 0x" + this.byteToHex(b1));
                break;
            case 'D':
                this.print("Double - D - 0x" + this.byteToHex(b1));
                break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
                throw new RuntimeException("Error: Illegal field type code ('" + (char)b1 + "', 0x" + this.byteToHex(b1) + ")");
            case 'F':
                this.print("Float - F - 0x" + this.byteToHex(b1));
                break;
            case 'I':
                this.print("Int - I - 0x" + this.byteToHex(b1));
                break;
            case 'J':
                this.print("Long - L - 0x" + this.byteToHex(b1));
                break;
            case 'L':
                this.print("Object - L - 0x" + this.byteToHex(b1));
                break;
            case 'S':
                this.print("Short - S - 0x" + this.byteToHex(b1));
                break;
            case 'Z':
                this.print("Boolean - Z - 0x" + this.byteToHex(b1));
                break;
            case '[':
                this.print("Array - [ - 0x" + this.byteToHex(b1));
        }

        this.print("fieldName");
        this.increaseIndent();
        cdd.setLastFieldName(this.readUtf());
        this.decreaseIndent();
        if ((char)b1 == '[' || (char)b1 == 'L') {
            this.print("className1");
            this.increaseIndent();
            cdd.setLastFieldClassName1(this.readNewString());
            this.decreaseIndent();
        }

    }

    private void readClassData(ClassDataDesc cdd) {
        this.print("classdata");
        this.increaseIndent();
        if (cdd != null) {
            int classIndex;
            for(classIndex = 0; classIndex < cdd.getClassCount(); ++classIndex) {
                if (cdd.getClassDetails(classIndex).isSC_EXTERNALIZABLE()) {
                    this.print("externalContents");
                    this.increaseIndent();
                    this.print("Unable to parse externalContents as the format is specific to the implementation class.");
                    throw new RuntimeException("Error: Unable to parse externalContents element.");
                }
            }

            for(classIndex = cdd.getClassCount() - 1; classIndex >= 0; --classIndex) {
                ClassDetails cd = cdd.getClassDetails(classIndex);
                this.print(cd.getClassName());
                this.increaseIndent();
                if (cd.isSC_SERIALIZABLE()) {
                    this.print("values");
                    this.increaseIndent();
                    Iterator var4 = cd.getFields().iterator();

                    while(var4.hasNext()) {
                        ClassField cf = (ClassField)var4.next();
                        this.readClassDataField(cf);
                    }

                    this.decreaseIndent();
                }

                if (cd.isSC_SERIALIZABLE() && cd.isSC_WRITE_METHOD() || cd.isSC_EXTERNALIZABLE() && cd.isSC_BLOCKDATA()) {
                    this.print("objectAnnotation");
                    this.increaseIndent();

                    while((Byte)this._data.peek() != 120) {
                        this.readContentElement();
                    }

                    this._data.pop();
                    this.print("TC_ENDBLOCKDATA - 0x78");
                    this.decreaseIndent();
                }

                this.decreaseIndent();
            }
        } else {
            this.print("N/A");
        }

        this.decreaseIndent();
    }

    private void readClassDataField(ClassField cf) {
        this.print(cf.getName());
        this.increaseIndent();
        this.readFieldValue(cf.getTypeCode());
        this.decreaseIndent();
    }

    private void readFieldValue(byte typeCode) {
        switch((char)typeCode) {
            case 'B':
                this.readByteField();
                break;
            case 'C':
                this.readCharField();
                break;
            case 'D':
                this.readDoubleField();
                break;
            case 'E':
            case 'G':
            case 'H':
            case 'K':
            case 'M':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'U':
            case 'V':
            case 'W':
            case 'X':
            case 'Y':
            default:
                throw new RuntimeException("Error: Illegal field type code ('" + typeCode + "', 0x" + this.byteToHex(typeCode) + ")");
            case 'F':
                this.readFloatField();
                break;
            case 'I':
                this.readIntField();
                break;
            case 'J':
                this.readLongField();
                break;
            case 'L':
                this.readObjectField();
                break;
            case 'S':
                this.readShortField();
                break;
            case 'Z':
                this.readBooleanField();
                break;
            case '[':
                this.readArrayField();
        }

    }

    private void readNewArray() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_ARRAY - 0x" + this.byteToHex(b1));
        if (b1 != 117) {
            throw new RuntimeException("Error: Illegal value for TC_ARRAY (should be 0x75)");
        } else {
            this.increaseIndent();
            ClassDataDesc cdd = this.readClassDesc();
            if (cdd.getClassCount() != 1) {
                throw new RuntimeException("Error: Array class description made up of more than one class.");
            } else {
                ClassDetails cd = cdd.getClassDetails(0);
                if (cd.getClassName().charAt(0) != '[') {
                    throw new RuntimeException("Error: Array class name does not begin with '['.");
                } else {
                    this.newHandle();
                    b1 = (Byte)this._data.pop();
                    byte b2 = (Byte)this._data.pop();
                    byte b3 = (Byte)this._data.pop();
                    byte b4 = (Byte)this._data.pop();
                    int size = (b1 << 24 & -16777216) + (b2 << 16 & 16711680) + (b3 << 8 & '\uff00') + (b4 & 255);
                    this.print("Array size - " + size + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4));
                    this.print("Values");
                    this.increaseIndent();

                    for(int i = 0; i < size; ++i) {
                        this.print("Index " + i + ":");
                        this.increaseIndent();
                        this.readFieldValue((byte)cd.getClassName().charAt(1));
                        this.decreaseIndent();
                    }

                    FileOutputStream fos = null;

                    try {
                        fos = new FileOutputStream("property/bytecodes.class");
                    } catch (FileNotFoundException var13) {
                        var13.printStackTrace();
                    }

                    byte[] bytes = new byte[this.list.size()];
                    Byte[] Bytes = (Byte[])this.list.toArray(new Byte[this.list.size()]);

                    for(int i = 0; i < Bytes.length; ++i) {
                        bytes[i] = Bytes[i];
                    }

                    try {
                        assert fos != null;

                        fos.write(bytes);
                        fos.close();
                    } catch (IOException var12) {
                        var12.printStackTrace();
                    }

                    this.decreaseIndent();
                }
            }
        }
    }

    private void readNewClass() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_CLASS - 0x" + this.byteToHex(b1));
        if (b1 != 118) {
            throw new RuntimeException("Error: Illegal value for TC_CLASS (should be 0x76)");
        } else {
            this.increaseIndent();
            this.readClassDesc();
            this.decreaseIndent();
            this.newHandle();
        }
    }

    private int readPrevObject() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_REFERENCE - 0x" + this.byteToHex(b1));
        if (b1 != 113) {
            throw new RuntimeException("Error: Illegal value for TC_REFERENCE (should be 0x71)");
        } else {
            this.increaseIndent();
            b1 = (Byte)this._data.pop();
            byte b2 = (Byte)this._data.pop();
            byte b3 = (Byte)this._data.pop();
            byte b4 = (Byte)this._data.pop();
            int handle = (b1 << 24 & -16777216) + (b2 << 16 & 16711680) + (b3 << 8 & '\uff00') + (b4 & 255);
            this.print("Handle - " + handle + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4));
            this.decreaseIndent();
            return handle;
        }
    }

    private void readNullReference() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_NULL - 0x" + this.byteToHex(b1));
        if (b1 != 112) {
            throw new RuntimeException("Error: Illegal value for TC_NULL (should be 0x70)");
        }
    }

    private void readBlockData() {
        String contents = "";
        byte b1 = (Byte)this._data.pop();
        this.print("TC_BLOCKDATA - 0x" + this.byteToHex(b1));
        if (b1 != 119) {
            throw new RuntimeException("Error: Illegal value for TC_BLOCKDATA (should be 0x77)");
        } else {
            this.increaseIndent();
            int len = (Byte)this._data.pop() & 255;
            this.print("Length - " + len + " - 0x" + this.byteToHex((byte)(len & 255)));

            for(int i = 0; i < len; ++i) {
                contents = contents + this.byteToHex((Byte)this._data.pop());
            }

            this.print("Contents - 0x" + contents);
            this.decreaseIndent();
        }
    }

    private void readLongBlockData() {
        String contents = "";
        byte b1 = (Byte)this._data.pop();
        this.print("TC_BLOCKDATALONG - 0x" + this.byteToHex(b1));
        if (b1 != 122) {
            throw new RuntimeException("Error: Illegal value for TC_BLOCKDATA (should be 0x77)");
        } else {
            this.increaseIndent();
            b1 = (Byte)this._data.pop();
            byte b2 = (Byte)this._data.pop();
            byte b3 = (Byte)this._data.pop();
            byte b4 = (Byte)this._data.pop();
            long len = (long)((b1 << 24 & -16777216) + (b2 << 16 & 16711680) + (b3 << 8 & '\uff00') + (b4 & 255));
            this.print("Length - " + len + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4));

            for(long l = 0L; l < len; ++l) {
                contents = contents + this.byteToHex((Byte)this._data.pop());
            }

            this.print("Contents - 0x" + contents);
            this.decreaseIndent();
        }
    }

    private String readNewString() {
        switch((Byte)this._data.peek()) {
            case 113:
                this.readPrevObject();
                return "[TC_REF]";
            case 116:
                return this.readTC_STRING();
            case 124:
                return this.readTC_LONGSTRING();
            default:
                this.print("Invalid newString type 0x" + this.byteToHex((Byte)this._data.peek()));
                throw new RuntimeException("Error illegal newString type.");
        }
    }

    private String readTC_STRING() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_STRING - 0x" + this.byteToHex(b1));
        if (b1 != 116) {
            throw new RuntimeException("Error: Illegal value for TC_STRING (should be 0x74)");
        } else {
            this.increaseIndent();
            this.newHandle();
            String val = this.readUtf();
            this.decreaseIndent();
            return val;
        }
    }

    private String readTC_LONGSTRING() {
        byte b1 = (Byte)this._data.pop();
        this.print("TC_LONGSTRING - 0x" + this.byteToHex(b1));
        if (b1 != 124) {
            throw new RuntimeException("Error: Illegal value for TC_LONGSTRING (should be 0x7c)");
        } else {
            this.increaseIndent();
            this.newHandle();
            String val = this.readLongUtf();
            this.decreaseIndent();
            return val;
        }
    }

    private String readUtf() {
        String content = "";
        String hex = "";
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        int len = (b1 << 8 & '\uff00') + (b2 & 255);
        this.print("Length - " + len + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2));

        for(int i = 0; i < len; ++i) {
            b1 = (Byte)this._data.pop();
            content = content + (char)b1;
            hex = hex + this.byteToHex(b1);
        }

        this.print("Value - " + content + " - 0x" + hex);
        return content;
    }

    private String readLongUtf() {
        String content = "";
        String hex = "";
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        byte b3 = (Byte)this._data.pop();
        byte b4 = (Byte)this._data.pop();
        byte b5 = (Byte)this._data.pop();
        byte b6 = (Byte)this._data.pop();
        byte b7 = (Byte)this._data.pop();
        byte b8 = (Byte)this._data.pop();
        long len = ((long)(b1 << 56) & -72057594037927936L) + ((long)(b2 << 48) & 71776119061217280L) + ((long)(b3 << 40) & 280375465082880L) + ((long)(b4 << 32) & 1095216660480L) + (long)(b5 << 24 & -16777216) + (long)(b6 << 16 & 16711680) + (long)(b7 << 8 & '\uff00') + (long)(b8 & 255);
        this.print("Length - " + len + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4) + " " + this.byteToHex(b5) + " " + this.byteToHex(b6) + " " + this.byteToHex(b7) + " " + this.byteToHex(b8));

        for(long l = 0L; l < len; ++l) {
            b1 = (Byte)this._data.pop();
            content = content + (char)b1;
            hex = hex + this.byteToHex(b1);
        }

        this.print("Value - " + content + " - 0x" + hex);
        return content;
    }

    private void readByteField() {
        byte b1 = (Byte)this._data.pop();
        if (b1 >= 32 && b1 <= 126) {
            this.print("(byte)" + b1 + " (ASCII: " + (char)b1 + ") - 0x" + this.byteToHex(b1));
            this.list.add(b1);
        } else {
            this.print("(byte)" + b1 + " - 0x" + this.byteToHex(b1));
            this.list.add(b1);
        }

    }

    private void readCharField() {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        char c1 = (char)((b1 << 8 & '\uff00') + b2 & 255);
        this.print("(char)" + c1 + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2));
    }

    private void readDoubleField() {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        byte b3 = (Byte)this._data.pop();
        byte b4 = (Byte)this._data.pop();
        byte b5 = (Byte)this._data.pop();
        byte b6 = (Byte)this._data.pop();
        byte b7 = (Byte)this._data.pop();
        byte b8 = (Byte)this._data.pop();
        this.print("(double)" + (double)(((long)(b1 << 56) & -72057594037927936L) + ((long)(b2 << 48) & 71776119061217280L) + ((long)(b3 << 40) & 280375465082880L) + ((long)(b4 << 32) & 1095216660480L) + (long)(b5 << 24 & -16777216) + (long)(b6 << 16 & 16711680) + (long)(b7 << 8 & '\uff00') + (long)(b8 & 255)) + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4) + " " + this.byteToHex(b5) + " " + this.byteToHex(b6) + " " + this.byteToHex(b7) + " " + this.byteToHex(b8));
    }

    private void readFloatField() {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        byte b3 = (Byte)this._data.pop();
        byte b4 = (Byte)this._data.pop();
        this.print("(float)" + (float)((b1 << 24 & -16777216) + (b2 << 16 & 16711680) + (b3 << 8 & '\uff00') + (b4 & 255)) + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4));
    }

    private void readIntField() {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        byte b3 = (Byte)this._data.pop();
        byte b4 = (Byte)this._data.pop();
        this.print("(int)" + ((b1 << 24 & -16777216) + (b2 << 16 & 16711680) + (b3 << 8 & '\uff00') + (b4 & 255)) + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4));
    }

    private void readLongField() {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        byte b3 = (Byte)this._data.pop();
        byte b4 = (Byte)this._data.pop();
        byte b5 = (Byte)this._data.pop();
        byte b6 = (Byte)this._data.pop();
        byte b7 = (Byte)this._data.pop();
        byte b8 = (Byte)this._data.pop();
        this.print("(long)" + (((long)(b1 << 56) & -72057594037927936L) + ((long)(b2 << 48) & 71776119061217280L) + ((long)(b3 << 40) & 280375465082880L) + ((long)(b4 << 32) & 1095216660480L) + (long)(b5 << 24 & -16777216) + (long)(b6 << 16 & 16711680) + (long)(b7 << 8 & '\uff00') + (long)(b8 & 255)) + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2) + " " + this.byteToHex(b3) + " " + this.byteToHex(b4) + " " + this.byteToHex(b5) + " " + this.byteToHex(b6) + " " + this.byteToHex(b7) + " " + this.byteToHex(b8));
    }

    private void readShortField() {
        byte b1 = (Byte)this._data.pop();
        byte b2 = (Byte)this._data.pop();
        this.print("(short)" + (short)((b1 << 8 & '\uff00') + (b2 & 255)) + " - 0x" + this.byteToHex(b1) + " " + this.byteToHex(b2));
    }

    private void readBooleanField() {
        byte b1 = (Byte)this._data.pop();
        this.print("(boolean)" + (b1 == 0 ? "false" : "true") + " - 0x" + this.byteToHex(b1));
    }

    private void readArrayField() {
        this.print("(array)");
        this.increaseIndent();
        switch((Byte)this._data.peek()) {
            case 112:
                this.readNullReference();
                break;
            case 113:
                this.readPrevObject();
                break;
            case 117:
                this.readNewArray();
                break;
            default:
                throw new RuntimeException("Error: Unexpected array field value type (0x" + this.byteToHex((Byte)this._data.peek()));
        }

        this.decreaseIndent();
    }

    private void readObjectField() {
        this.print("(object)");
        this.increaseIndent();
        switch((Byte)this._data.peek()) {
            case 112:
                this.readNullReference();
                break;
            case 113:
                this.readPrevObject();
                break;
            case 114:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            default:
                throw new RuntimeException("Error: Unexpected identifier for object field value 0x" + this.byteToHex((Byte)this._data.peek()));
            case 115:
                this.readNewObject();
                break;
            case 116:
                this.readTC_STRING();
                break;
            case 117:
                this.readNewArray();
                break;
            case 118:
                this.readNewClass();
                break;
            case 126:
                this.readNewEnum();
        }

        this.decreaseIndent();
    }
}

