/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sleepycat.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-01-29")
public class TTransactionConfig implements org.apache.thrift.TBase<TTransactionConfig, TTransactionConfig._Fields>, java.io.Serializable, Cloneable, Comparable<TTransactionConfig> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TTransactionConfig");

  private static final org.apache.thrift.protocol.TField BULK_FIELD_DESC = new org.apache.thrift.protocol.TField("bulk", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField DURABILITY_FIELD_DESC = new org.apache.thrift.protocol.TField("durability", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField ISO_LEVEL_FIELD_DESC = new org.apache.thrift.protocol.TField("isoLevel", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField WAIT_FIELD_DESC = new org.apache.thrift.protocol.TField("wait", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TTransactionConfigStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TTransactionConfigTupleSchemeFactory();

  public boolean bulk; // optional
  /**
   * 
   * @see TDurabilityPolicy
   */
  public TDurabilityPolicy durability; // optional
  /**
   * 
   * @see TIsolationLevel
   */
  public TIsolationLevel isoLevel; // optional
  public boolean wait; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    BULK((short)1, "bulk"),
    /**
     * 
     * @see TDurabilityPolicy
     */
    DURABILITY((short)2, "durability"),
    /**
     * 
     * @see TIsolationLevel
     */
    ISO_LEVEL((short)3, "isoLevel"),
    WAIT((short)4, "wait");

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
        case 1: // BULK
          return BULK;
        case 2: // DURABILITY
          return DURABILITY;
        case 3: // ISO_LEVEL
          return ISO_LEVEL;
        case 4: // WAIT
          return WAIT;
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
  private static final int __BULK_ISSET_ID = 0;
  private static final int __WAIT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.BULK,_Fields.DURABILITY,_Fields.ISO_LEVEL,_Fields.WAIT};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.BULK, new org.apache.thrift.meta_data.FieldMetaData("bulk", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.DURABILITY, new org.apache.thrift.meta_data.FieldMetaData("durability", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TDurabilityPolicy.class)));
    tmpMap.put(_Fields.ISO_LEVEL, new org.apache.thrift.meta_data.FieldMetaData("isoLevel", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TIsolationLevel.class)));
    tmpMap.put(_Fields.WAIT, new org.apache.thrift.meta_data.FieldMetaData("wait", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TTransactionConfig.class, metaDataMap);
  }

  public TTransactionConfig() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TTransactionConfig(TTransactionConfig other) {
    __isset_bitfield = other.__isset_bitfield;
    this.bulk = other.bulk;
    if (other.isSetDurability()) {
      this.durability = other.durability;
    }
    if (other.isSetIsoLevel()) {
      this.isoLevel = other.isoLevel;
    }
    this.wait = other.wait;
  }

  public TTransactionConfig deepCopy() {
    return new TTransactionConfig(this);
  }

  @Override
  public void clear() {
    setBulkIsSet(false);
    this.bulk = false;
    this.durability = null;
    this.isoLevel = null;
    setWaitIsSet(false);
    this.wait = false;
  }

  public boolean isBulk() {
    return this.bulk;
  }

  public TTransactionConfig setBulk(boolean bulk) {
    this.bulk = bulk;
    setBulkIsSet(true);
    return this;
  }

  public void unsetBulk() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __BULK_ISSET_ID);
  }

  /** Returns true if field bulk is set (has been assigned a value) and false otherwise */
  public boolean isSetBulk() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __BULK_ISSET_ID);
  }

  public void setBulkIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __BULK_ISSET_ID, value);
  }

  /**
   * 
   * @see TDurabilityPolicy
   */
  public TDurabilityPolicy getDurability() {
    return this.durability;
  }

  /**
   * 
   * @see TDurabilityPolicy
   */
  public TTransactionConfig setDurability(TDurabilityPolicy durability) {
    this.durability = durability;
    return this;
  }

  public void unsetDurability() {
    this.durability = null;
  }

  /** Returns true if field durability is set (has been assigned a value) and false otherwise */
  public boolean isSetDurability() {
    return this.durability != null;
  }

  public void setDurabilityIsSet(boolean value) {
    if (!value) {
      this.durability = null;
    }
  }

  /**
   * 
   * @see TIsolationLevel
   */
  public TIsolationLevel getIsoLevel() {
    return this.isoLevel;
  }

  /**
   * 
   * @see TIsolationLevel
   */
  public TTransactionConfig setIsoLevel(TIsolationLevel isoLevel) {
    this.isoLevel = isoLevel;
    return this;
  }

  public void unsetIsoLevel() {
    this.isoLevel = null;
  }

  /** Returns true if field isoLevel is set (has been assigned a value) and false otherwise */
  public boolean isSetIsoLevel() {
    return this.isoLevel != null;
  }

  public void setIsoLevelIsSet(boolean value) {
    if (!value) {
      this.isoLevel = null;
    }
  }

  public boolean isWait() {
    return this.wait;
  }

  public TTransactionConfig setWait(boolean wait) {
    this.wait = wait;
    setWaitIsSet(true);
    return this;
  }

  public void unsetWait() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __WAIT_ISSET_ID);
  }

  /** Returns true if field wait is set (has been assigned a value) and false otherwise */
  public boolean isSetWait() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __WAIT_ISSET_ID);
  }

  public void setWaitIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __WAIT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case BULK:
      if (value == null) {
        unsetBulk();
      } else {
        setBulk((java.lang.Boolean)value);
      }
      break;

    case DURABILITY:
      if (value == null) {
        unsetDurability();
      } else {
        setDurability((TDurabilityPolicy)value);
      }
      break;

    case ISO_LEVEL:
      if (value == null) {
        unsetIsoLevel();
      } else {
        setIsoLevel((TIsolationLevel)value);
      }
      break;

    case WAIT:
      if (value == null) {
        unsetWait();
      } else {
        setWait((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case BULK:
      return isBulk();

    case DURABILITY:
      return getDurability();

    case ISO_LEVEL:
      return getIsoLevel();

    case WAIT:
      return isWait();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case BULK:
      return isSetBulk();
    case DURABILITY:
      return isSetDurability();
    case ISO_LEVEL:
      return isSetIsoLevel();
    case WAIT:
      return isSetWait();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TTransactionConfig)
      return this.equals((TTransactionConfig)that);
    return false;
  }

  public boolean equals(TTransactionConfig that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_bulk = true && this.isSetBulk();
    boolean that_present_bulk = true && that.isSetBulk();
    if (this_present_bulk || that_present_bulk) {
      if (!(this_present_bulk && that_present_bulk))
        return false;
      if (this.bulk != that.bulk)
        return false;
    }

    boolean this_present_durability = true && this.isSetDurability();
    boolean that_present_durability = true && that.isSetDurability();
    if (this_present_durability || that_present_durability) {
      if (!(this_present_durability && that_present_durability))
        return false;
      if (!this.durability.equals(that.durability))
        return false;
    }

    boolean this_present_isoLevel = true && this.isSetIsoLevel();
    boolean that_present_isoLevel = true && that.isSetIsoLevel();
    if (this_present_isoLevel || that_present_isoLevel) {
      if (!(this_present_isoLevel && that_present_isoLevel))
        return false;
      if (!this.isoLevel.equals(that.isoLevel))
        return false;
    }

    boolean this_present_wait = true && this.isSetWait();
    boolean that_present_wait = true && that.isSetWait();
    if (this_present_wait || that_present_wait) {
      if (!(this_present_wait && that_present_wait))
        return false;
      if (this.wait != that.wait)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetBulk()) ? 131071 : 524287);
    if (isSetBulk())
      hashCode = hashCode * 8191 + ((bulk) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetDurability()) ? 131071 : 524287);
    if (isSetDurability())
      hashCode = hashCode * 8191 + durability.getValue();

    hashCode = hashCode * 8191 + ((isSetIsoLevel()) ? 131071 : 524287);
    if (isSetIsoLevel())
      hashCode = hashCode * 8191 + isoLevel.getValue();

    hashCode = hashCode * 8191 + ((isSetWait()) ? 131071 : 524287);
    if (isSetWait())
      hashCode = hashCode * 8191 + ((wait) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TTransactionConfig other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetBulk()).compareTo(other.isSetBulk());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBulk()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bulk, other.bulk);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDurability()).compareTo(other.isSetDurability());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDurability()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.durability, other.durability);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetIsoLevel()).compareTo(other.isSetIsoLevel());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIsoLevel()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.isoLevel, other.isoLevel);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetWait()).compareTo(other.isSetWait());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWait()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.wait, other.wait);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TTransactionConfig(");
    boolean first = true;

    if (isSetBulk()) {
      sb.append("bulk:");
      sb.append(this.bulk);
      first = false;
    }
    if (isSetDurability()) {
      if (!first) sb.append(", ");
      sb.append("durability:");
      if (this.durability == null) {
        sb.append("null");
      } else {
        sb.append(this.durability);
      }
      first = false;
    }
    if (isSetIsoLevel()) {
      if (!first) sb.append(", ");
      sb.append("isoLevel:");
      if (this.isoLevel == null) {
        sb.append("null");
      } else {
        sb.append(this.isoLevel);
      }
      first = false;
    }
    if (isSetWait()) {
      if (!first) sb.append(", ");
      sb.append("wait:");
      sb.append(this.wait);
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

  private static class TTransactionConfigStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTransactionConfigStandardScheme getScheme() {
      return new TTransactionConfigStandardScheme();
    }
  }

  private static class TTransactionConfigStandardScheme extends org.apache.thrift.scheme.StandardScheme<TTransactionConfig> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TTransactionConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // BULK
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.bulk = iprot.readBool();
              struct.setBulkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DURABILITY
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.durability = com.sleepycat.thrift.TDurabilityPolicy.findByValue(iprot.readI32());
              struct.setDurabilityIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ISO_LEVEL
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.isoLevel = com.sleepycat.thrift.TIsolationLevel.findByValue(iprot.readI32());
              struct.setIsoLevelIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // WAIT
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.wait = iprot.readBool();
              struct.setWaitIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TTransactionConfig struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetBulk()) {
        oprot.writeFieldBegin(BULK_FIELD_DESC);
        oprot.writeBool(struct.bulk);
        oprot.writeFieldEnd();
      }
      if (struct.durability != null) {
        if (struct.isSetDurability()) {
          oprot.writeFieldBegin(DURABILITY_FIELD_DESC);
          oprot.writeI32(struct.durability.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isoLevel != null) {
        if (struct.isSetIsoLevel()) {
          oprot.writeFieldBegin(ISO_LEVEL_FIELD_DESC);
          oprot.writeI32(struct.isoLevel.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetWait()) {
        oprot.writeFieldBegin(WAIT_FIELD_DESC);
        oprot.writeBool(struct.wait);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TTransactionConfigTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TTransactionConfigTupleScheme getScheme() {
      return new TTransactionConfigTupleScheme();
    }
  }

  private static class TTransactionConfigTupleScheme extends org.apache.thrift.scheme.TupleScheme<TTransactionConfig> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TTransactionConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetBulk()) {
        optionals.set(0);
      }
      if (struct.isSetDurability()) {
        optionals.set(1);
      }
      if (struct.isSetIsoLevel()) {
        optionals.set(2);
      }
      if (struct.isSetWait()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetBulk()) {
        oprot.writeBool(struct.bulk);
      }
      if (struct.isSetDurability()) {
        oprot.writeI32(struct.durability.getValue());
      }
      if (struct.isSetIsoLevel()) {
        oprot.writeI32(struct.isoLevel.getValue());
      }
      if (struct.isSetWait()) {
        oprot.writeBool(struct.wait);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TTransactionConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.bulk = iprot.readBool();
        struct.setBulkIsSet(true);
      }
      if (incoming.get(1)) {
        struct.durability = com.sleepycat.thrift.TDurabilityPolicy.findByValue(iprot.readI32());
        struct.setDurabilityIsSet(true);
      }
      if (incoming.get(2)) {
        struct.isoLevel = com.sleepycat.thrift.TIsolationLevel.findByValue(iprot.readI32());
        struct.setIsoLevelIsSet(true);
      }
      if (incoming.get(3)) {
        struct.wait = iprot.readBool();
        struct.setWaitIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

