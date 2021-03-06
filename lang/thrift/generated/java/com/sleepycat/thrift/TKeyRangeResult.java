/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sleepycat.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-01-29")
public class TKeyRangeResult implements org.apache.thrift.TBase<TKeyRangeResult, TKeyRangeResult._Fields>, java.io.Serializable, Cloneable, Comparable<TKeyRangeResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TKeyRangeResult");

  private static final org.apache.thrift.protocol.TField EQUAL_FIELD_DESC = new org.apache.thrift.protocol.TField("equal", org.apache.thrift.protocol.TType.DOUBLE, (short)1);
  private static final org.apache.thrift.protocol.TField GREATER_FIELD_DESC = new org.apache.thrift.protocol.TField("greater", org.apache.thrift.protocol.TType.DOUBLE, (short)2);
  private static final org.apache.thrift.protocol.TField LESS_FIELD_DESC = new org.apache.thrift.protocol.TField("less", org.apache.thrift.protocol.TType.DOUBLE, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TKeyRangeResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TKeyRangeResultTupleSchemeFactory();

  public double equal; // optional
  public double greater; // optional
  public double less; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    EQUAL((short)1, "equal"),
    GREATER((short)2, "greater"),
    LESS((short)3, "less");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // EQUAL
          return EQUAL;
        case 2: // GREATER
          return GREATER;
        case 3: // LESS
          return LESS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __EQUAL_ISSET_ID = 0;
  private static final int __GREATER_ISSET_ID = 1;
  private static final int __LESS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.EQUAL,_Fields.GREATER,_Fields.LESS};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EQUAL, new org.apache.thrift.meta_data.FieldMetaData("equal", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.GREATER, new org.apache.thrift.meta_data.FieldMetaData("greater", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.LESS, new org.apache.thrift.meta_data.FieldMetaData("less", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TKeyRangeResult.class, metaDataMap);
  }

  public TKeyRangeResult() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TKeyRangeResult(TKeyRangeResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.equal = other.equal;
    this.greater = other.greater;
    this.less = other.less;
  }

  public TKeyRangeResult deepCopy() {
    return new TKeyRangeResult(this);
  }

  @Override
  public void clear() {
    setEqualIsSet(false);
    this.equal = 0.0;
    setGreaterIsSet(false);
    this.greater = 0.0;
    setLessIsSet(false);
    this.less = 0.0;
  }

  public double getEqual() {
    return this.equal;
  }

  public TKeyRangeResult setEqual(double equal) {
    this.equal = equal;
    setEqualIsSet(true);
    return this;
  }

  public void unsetEqual() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __EQUAL_ISSET_ID);
  }

  /** Returns true if field equal is set (has been assigned a value) and false otherwise */
  public boolean isSetEqual() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __EQUAL_ISSET_ID);
  }

  public void setEqualIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __EQUAL_ISSET_ID, value);
  }

  public double getGreater() {
    return this.greater;
  }

  public TKeyRangeResult setGreater(double greater) {
    this.greater = greater;
    setGreaterIsSet(true);
    return this;
  }

  public void unsetGreater() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __GREATER_ISSET_ID);
  }

  /** Returns true if field greater is set (has been assigned a value) and false otherwise */
  public boolean isSetGreater() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __GREATER_ISSET_ID);
  }

  public void setGreaterIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __GREATER_ISSET_ID, value);
  }

  public double getLess() {
    return this.less;
  }

  public TKeyRangeResult setLess(double less) {
    this.less = less;
    setLessIsSet(true);
    return this;
  }

  public void unsetLess() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __LESS_ISSET_ID);
  }

  /** Returns true if field less is set (has been assigned a value) and false otherwise */
  public boolean isSetLess() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __LESS_ISSET_ID);
  }

  public void setLessIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __LESS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case EQUAL:
      if (value == null) {
        unsetEqual();
      } else {
        setEqual((java.lang.Double)value);
      }
      break;

    case GREATER:
      if (value == null) {
        unsetGreater();
      } else {
        setGreater((java.lang.Double)value);
      }
      break;

    case LESS:
      if (value == null) {
        unsetLess();
      } else {
        setLess((java.lang.Double)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case EQUAL:
      return getEqual();

    case GREATER:
      return getGreater();

    case LESS:
      return getLess();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case EQUAL:
      return isSetEqual();
    case GREATER:
      return isSetGreater();
    case LESS:
      return isSetLess();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TKeyRangeResult)
      return this.equals((TKeyRangeResult)that);
    return false;
  }

  public boolean equals(TKeyRangeResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_equal = true && this.isSetEqual();
    boolean that_present_equal = true && that.isSetEqual();
    if (this_present_equal || that_present_equal) {
      if (!(this_present_equal && that_present_equal))
        return false;
      if (this.equal != that.equal)
        return false;
    }

    boolean this_present_greater = true && this.isSetGreater();
    boolean that_present_greater = true && that.isSetGreater();
    if (this_present_greater || that_present_greater) {
      if (!(this_present_greater && that_present_greater))
        return false;
      if (this.greater != that.greater)
        return false;
    }

    boolean this_present_less = true && this.isSetLess();
    boolean that_present_less = true && that.isSetLess();
    if (this_present_less || that_present_less) {
      if (!(this_present_less && that_present_less))
        return false;
      if (this.less != that.less)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetEqual()) ? 131071 : 524287);
    if (isSetEqual())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(equal);

    hashCode = hashCode * 8191 + ((isSetGreater()) ? 131071 : 524287);
    if (isSetGreater())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(greater);

    hashCode = hashCode * 8191 + ((isSetLess()) ? 131071 : 524287);
    if (isSetLess())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(less);

    return hashCode;
  }

  @Override
  public int compareTo(TKeyRangeResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetEqual()).compareTo(other.isSetEqual());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEqual()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.equal, other.equal);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetGreater()).compareTo(other.isSetGreater());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGreater()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.greater, other.greater);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetLess()).compareTo(other.isSetLess());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLess()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.less, other.less);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TKeyRangeResult(");
    boolean first = true;

    if (isSetEqual()) {
      sb.append("equal:");
      sb.append(this.equal);
      first = false;
    }
    if (isSetGreater()) {
      if (!first) sb.append(", ");
      sb.append("greater:");
      sb.append(this.greater);
      first = false;
    }
    if (isSetLess()) {
      if (!first) sb.append(", ");
      sb.append("less:");
      sb.append(this.less);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TKeyRangeResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TKeyRangeResultStandardScheme getScheme() {
      return new TKeyRangeResultStandardScheme();
    }
  }

  private static class TKeyRangeResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<TKeyRangeResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TKeyRangeResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // EQUAL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.equal = iprot.readDouble();
              struct.setEqualIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GREATER
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.greater = iprot.readDouble();
              struct.setGreaterIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // LESS
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.less = iprot.readDouble();
              struct.setLessIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TKeyRangeResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetEqual()) {
        oprot.writeFieldBegin(EQUAL_FIELD_DESC);
        oprot.writeDouble(struct.equal);
        oprot.writeFieldEnd();
      }
      if (struct.isSetGreater()) {
        oprot.writeFieldBegin(GREATER_FIELD_DESC);
        oprot.writeDouble(struct.greater);
        oprot.writeFieldEnd();
      }
      if (struct.isSetLess()) {
        oprot.writeFieldBegin(LESS_FIELD_DESC);
        oprot.writeDouble(struct.less);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TKeyRangeResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TKeyRangeResultTupleScheme getScheme() {
      return new TKeyRangeResultTupleScheme();
    }
  }

  private static class TKeyRangeResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<TKeyRangeResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TKeyRangeResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetEqual()) {
        optionals.set(0);
      }
      if (struct.isSetGreater()) {
        optionals.set(1);
      }
      if (struct.isSetLess()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetEqual()) {
        oprot.writeDouble(struct.equal);
      }
      if (struct.isSetGreater()) {
        oprot.writeDouble(struct.greater);
      }
      if (struct.isSetLess()) {
        oprot.writeDouble(struct.less);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TKeyRangeResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.equal = iprot.readDouble();
        struct.setEqualIsSet(true);
      }
      if (incoming.get(1)) {
        struct.greater = iprot.readDouble();
        struct.setGreaterIsSet(true);
      }
      if (incoming.get(2)) {
        struct.less = iprot.readDouble();
        struct.setLessIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

