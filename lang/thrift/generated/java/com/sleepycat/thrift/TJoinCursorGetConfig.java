/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sleepycat.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-01-29")
public class TJoinCursorGetConfig implements org.apache.thrift.TBase<TJoinCursorGetConfig, TJoinCursorGetConfig._Fields>, java.io.Serializable, Cloneable, Comparable<TJoinCursorGetConfig> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TJoinCursorGetConfig");

  private static final org.apache.thrift.protocol.TField PAIR_FIELD_DESC = new org.apache.thrift.protocol.TField("pair", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField READ_UNCOMMITTED_FIELD_DESC = new org.apache.thrift.protocol.TField("readUncommitted", org.apache.thrift.protocol.TType.BOOL, (short)2);
  private static final org.apache.thrift.protocol.TField RMW_FIELD_DESC = new org.apache.thrift.protocol.TField("rmw", org.apache.thrift.protocol.TType.BOOL, (short)3);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TJoinCursorGetConfigStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TJoinCursorGetConfigTupleSchemeFactory();

  public TKeyData pair; // required
  public boolean readUncommitted; // optional
  public boolean rmw; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAIR((short)1, "pair"),
    READ_UNCOMMITTED((short)2, "readUncommitted"),
    RMW((short)3, "rmw");

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
        case 1: // PAIR
          return PAIR;
        case 2: // READ_UNCOMMITTED
          return READ_UNCOMMITTED;
        case 3: // RMW
          return RMW;
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
  private static final int __READUNCOMMITTED_ISSET_ID = 0;
  private static final int __RMW_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.READ_UNCOMMITTED,_Fields.RMW};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAIR, new org.apache.thrift.meta_data.FieldMetaData("pair", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TKeyData.class)));
    tmpMap.put(_Fields.READ_UNCOMMITTED, new org.apache.thrift.meta_data.FieldMetaData("readUncommitted", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.RMW, new org.apache.thrift.meta_data.FieldMetaData("rmw", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TJoinCursorGetConfig.class, metaDataMap);
  }

  public TJoinCursorGetConfig() {
  }

  public TJoinCursorGetConfig(
    TKeyData pair)
  {
    this();
    this.pair = pair;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TJoinCursorGetConfig(TJoinCursorGetConfig other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPair()) {
      this.pair = new TKeyData(other.pair);
    }
    this.readUncommitted = other.readUncommitted;
    this.rmw = other.rmw;
  }

  public TJoinCursorGetConfig deepCopy() {
    return new TJoinCursorGetConfig(this);
  }

  @Override
  public void clear() {
    this.pair = null;
    setReadUncommittedIsSet(false);
    this.readUncommitted = false;
    setRmwIsSet(false);
    this.rmw = false;
  }

  public TKeyData getPair() {
    return this.pair;
  }

  public TJoinCursorGetConfig setPair(TKeyData pair) {
    this.pair = pair;
    return this;
  }

  public void unsetPair() {
    this.pair = null;
  }

  /** Returns true if field pair is set (has been assigned a value) and false otherwise */
  public boolean isSetPair() {
    return this.pair != null;
  }

  public void setPairIsSet(boolean value) {
    if (!value) {
      this.pair = null;
    }
  }

  public boolean isReadUncommitted() {
    return this.readUncommitted;
  }

  public TJoinCursorGetConfig setReadUncommitted(boolean readUncommitted) {
    this.readUncommitted = readUncommitted;
    setReadUncommittedIsSet(true);
    return this;
  }

  public void unsetReadUncommitted() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __READUNCOMMITTED_ISSET_ID);
  }

  /** Returns true if field readUncommitted is set (has been assigned a value) and false otherwise */
  public boolean isSetReadUncommitted() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __READUNCOMMITTED_ISSET_ID);
  }

  public void setReadUncommittedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __READUNCOMMITTED_ISSET_ID, value);
  }

  public boolean isRmw() {
    return this.rmw;
  }

  public TJoinCursorGetConfig setRmw(boolean rmw) {
    this.rmw = rmw;
    setRmwIsSet(true);
    return this;
  }

  public void unsetRmw() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __RMW_ISSET_ID);
  }

  /** Returns true if field rmw is set (has been assigned a value) and false otherwise */
  public boolean isSetRmw() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __RMW_ISSET_ID);
  }

  public void setRmwIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __RMW_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case PAIR:
      if (value == null) {
        unsetPair();
      } else {
        setPair((TKeyData)value);
      }
      break;

    case READ_UNCOMMITTED:
      if (value == null) {
        unsetReadUncommitted();
      } else {
        setReadUncommitted((java.lang.Boolean)value);
      }
      break;

    case RMW:
      if (value == null) {
        unsetRmw();
      } else {
        setRmw((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case PAIR:
      return getPair();

    case READ_UNCOMMITTED:
      return isReadUncommitted();

    case RMW:
      return isRmw();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case PAIR:
      return isSetPair();
    case READ_UNCOMMITTED:
      return isSetReadUncommitted();
    case RMW:
      return isSetRmw();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TJoinCursorGetConfig)
      return this.equals((TJoinCursorGetConfig)that);
    return false;
  }

  public boolean equals(TJoinCursorGetConfig that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_pair = true && this.isSetPair();
    boolean that_present_pair = true && that.isSetPair();
    if (this_present_pair || that_present_pair) {
      if (!(this_present_pair && that_present_pair))
        return false;
      if (!this.pair.equals(that.pair))
        return false;
    }

    boolean this_present_readUncommitted = true && this.isSetReadUncommitted();
    boolean that_present_readUncommitted = true && that.isSetReadUncommitted();
    if (this_present_readUncommitted || that_present_readUncommitted) {
      if (!(this_present_readUncommitted && that_present_readUncommitted))
        return false;
      if (this.readUncommitted != that.readUncommitted)
        return false;
    }

    boolean this_present_rmw = true && this.isSetRmw();
    boolean that_present_rmw = true && that.isSetRmw();
    if (this_present_rmw || that_present_rmw) {
      if (!(this_present_rmw && that_present_rmw))
        return false;
      if (this.rmw != that.rmw)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetPair()) ? 131071 : 524287);
    if (isSetPair())
      hashCode = hashCode * 8191 + pair.hashCode();

    hashCode = hashCode * 8191 + ((isSetReadUncommitted()) ? 131071 : 524287);
    if (isSetReadUncommitted())
      hashCode = hashCode * 8191 + ((readUncommitted) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetRmw()) ? 131071 : 524287);
    if (isSetRmw())
      hashCode = hashCode * 8191 + ((rmw) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TJoinCursorGetConfig other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetPair()).compareTo(other.isSetPair());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPair()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pair, other.pair);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetReadUncommitted()).compareTo(other.isSetReadUncommitted());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReadUncommitted()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.readUncommitted, other.readUncommitted);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetRmw()).compareTo(other.isSetRmw());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRmw()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.rmw, other.rmw);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TJoinCursorGetConfig(");
    boolean first = true;

    sb.append("pair:");
    if (this.pair == null) {
      sb.append("null");
    } else {
      sb.append(this.pair);
    }
    first = false;
    if (isSetReadUncommitted()) {
      if (!first) sb.append(", ");
      sb.append("readUncommitted:");
      sb.append(this.readUncommitted);
      first = false;
    }
    if (isSetRmw()) {
      if (!first) sb.append(", ");
      sb.append("rmw:");
      sb.append(this.rmw);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (pair != null) {
      pair.validate();
    }
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

  private static class TJoinCursorGetConfigStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TJoinCursorGetConfigStandardScheme getScheme() {
      return new TJoinCursorGetConfigStandardScheme();
    }
  }

  private static class TJoinCursorGetConfigStandardScheme extends org.apache.thrift.scheme.StandardScheme<TJoinCursorGetConfig> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TJoinCursorGetConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAIR
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.pair = new TKeyData();
              struct.pair.read(iprot);
              struct.setPairIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // READ_UNCOMMITTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.readUncommitted = iprot.readBool();
              struct.setReadUncommittedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // RMW
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.rmw = iprot.readBool();
              struct.setRmwIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TJoinCursorGetConfig struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.pair != null) {
        oprot.writeFieldBegin(PAIR_FIELD_DESC);
        struct.pair.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.isSetReadUncommitted()) {
        oprot.writeFieldBegin(READ_UNCOMMITTED_FIELD_DESC);
        oprot.writeBool(struct.readUncommitted);
        oprot.writeFieldEnd();
      }
      if (struct.isSetRmw()) {
        oprot.writeFieldBegin(RMW_FIELD_DESC);
        oprot.writeBool(struct.rmw);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TJoinCursorGetConfigTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TJoinCursorGetConfigTupleScheme getScheme() {
      return new TJoinCursorGetConfigTupleScheme();
    }
  }

  private static class TJoinCursorGetConfigTupleScheme extends org.apache.thrift.scheme.TupleScheme<TJoinCursorGetConfig> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TJoinCursorGetConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetPair()) {
        optionals.set(0);
      }
      if (struct.isSetReadUncommitted()) {
        optionals.set(1);
      }
      if (struct.isSetRmw()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetPair()) {
        struct.pair.write(oprot);
      }
      if (struct.isSetReadUncommitted()) {
        oprot.writeBool(struct.readUncommitted);
      }
      if (struct.isSetRmw()) {
        oprot.writeBool(struct.rmw);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TJoinCursorGetConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.pair = new TKeyData();
        struct.pair.read(iprot);
        struct.setPairIsSet(true);
      }
      if (incoming.get(1)) {
        struct.readUncommitted = iprot.readBool();
        struct.setReadUncommittedIsSet(true);
      }
      if (incoming.get(2)) {
        struct.rmw = iprot.readBool();
        struct.setRmwIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
