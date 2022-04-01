//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package main.support;

import java.util.ArrayList;

public class ClassDataDesc {
    private final ArrayList<ClassDetails> _classDetails;

    public ClassDataDesc() {
        this._classDetails = new ArrayList();
    }

    private ClassDataDesc(ArrayList<ClassDetails> cd) {
        this._classDetails = cd;
    }

    public ClassDataDesc buildClassDataDescFromIndex(int index) {
        ArrayList<ClassDetails> cd = new ArrayList();

        for(int i = index; i < this._classDetails.size(); ++i) {
            cd.add(this._classDetails.get(i));
        }

        return new ClassDataDesc(cd);
    }

    public void addSuperClassDesc(ClassDataDesc scdd) {
        if (scdd != null) {
            for(int i = 0; i < scdd.getClassCount(); ++i) {
                this._classDetails.add(scdd.getClassDetails(i));
            }
        }

    }

    public void addClass(String className) {
        this._classDetails.add(new ClassDetails(className));
    }

    public void setLastClassHandle(int handle) {
        ((ClassDetails)this._classDetails.get(this._classDetails.size() - 1)).setHandle(handle);
    }

    public void setLastClassDescFlags(byte classDescFlags) {
        ((ClassDetails)this._classDetails.get(this._classDetails.size() - 1)).setClassDescFlags(classDescFlags);
    }

    public void addFieldToLastClass(byte typeCode) {
        ((ClassDetails)this._classDetails.get(this._classDetails.size() - 1)).addField(new ClassField(typeCode));
    }

    public void setLastFieldName(String name) {
        ((ClassDetails)this._classDetails.get(this._classDetails.size() - 1)).setLastFieldName(name);
    }

    public void setLastFieldClassName1(String cn1) {
        ((ClassDetails)this._classDetails.get(this._classDetails.size() - 1)).setLastFieldClassName1(cn1);
    }

    public ClassDetails getClassDetails(int index) {
        return (ClassDetails)this._classDetails.get(index);
    }

    public int getClassCount() {
        return this._classDetails.size();
    }
}
