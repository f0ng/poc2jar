//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.support;

import java.util.ArrayList;

public class ClassDetails {
    private final String _className;
    private int _refHandle;
    private byte _classDescFlags;
    private final ArrayList<ClassField> _fieldDescriptions;

    public ClassDetails(String className) {
        this._className = className;
        this._refHandle = -1;
        this._classDescFlags = 0;
        this._fieldDescriptions = new ArrayList();
    }

    public String getClassName() {
        return this._className;
    }

    public void setHandle(int handle) {
        this._refHandle = handle;
    }

    public int getHandle() {
        return this._refHandle;
    }

    public void setClassDescFlags(byte classDescFlags) {
        this._classDescFlags = classDescFlags;
    }

    public boolean isSC_SERIALIZABLE() {
        return (this._classDescFlags & 2) == 2;
    }

    public boolean isSC_EXTERNALIZABLE() {
        return (this._classDescFlags & 4) == 4;
    }

    public boolean isSC_WRITE_METHOD() {
        return (this._classDescFlags & 1) == 1;
    }

    public boolean isSC_BLOCKDATA() {
        return (this._classDescFlags & 8) == 8;
    }

    public void addField(ClassField cf) {
        this._fieldDescriptions.add(cf);
    }

    public ArrayList<ClassField> getFields() {
        return this._fieldDescriptions;
    }

    public void setLastFieldName(String name) {
        ((ClassField)this._fieldDescriptions.get(this._fieldDescriptions.size() - 1)).setName(name);
    }

    public void setLastFieldClassName1(String cn1) {
        ((ClassField)this._fieldDescriptions.get(this._fieldDescriptions.size() - 1)).setClassName1(cn1);
    }
}
