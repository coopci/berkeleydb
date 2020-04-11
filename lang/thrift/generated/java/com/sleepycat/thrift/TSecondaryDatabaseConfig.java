/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sleepycat.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-01-29")
public class TSecondaryDatabaseConfig implements org.apache.thrift.TBase<TSecondaryDatabaseConfig, TSecondaryDatabaseConfig._Fields>, java.io.Serializable, Cloneable, Comparable<TSecondaryDatabaseConfig> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TSecondaryDatabaseConfig");

  private static final org.apache.thrift.protocol.TField DB_CONFIG_FIELD_DESC = new org.apache.thrift.protocol.TField("dbConfig", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField FOREIGN_DB_FIELD_DESC = new org.apache.thrift.protocol.TField("foreignDb", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField FOREIGN_KEY_DELETE_ACTION_FIELD_DESC = new org.apache.thrift.protocol.TField("foreignKeyDeleteAction", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField IMMUTABLE_SECONDARY_KEY_FIELD_DESC = new org.apache.thrift.protocol.TField("immutableSecondaryKey", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TSecondaryDatabaseConfigStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TSecondaryDatabaseConfigTupleSchemeFactory();

  public TDatabaseConfig dbConfig; // optional
  public TDatabase foreignDb; // optional
  /**
   * 
   * @see TFKDeleteAction
   */
  public TFKDeleteAction foreignKeyDeleteAction; // optional
  public boolean immutableSecondaryKey; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DB_CONFIG((short)1, "dbConfig"),
    FOREIGN_DB((short)2, "foreignDb"),
    /**
     * 
     * @see TFKDeleteAction
     */
    FOREIGN_KEY_DELETE_ACTION((short)3, "foreignKeyDeleteAction"),
    IMMUTABLE_SECONDARY_KEY((short)4, "immutableSecondaryKey");

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
        case 1: // DB_CONFIG
          return DB_CONFIG;
        case 2: // FOREIGN_DB
          return FOREIGN_DB;
        case 3: // FOREIGN_KEY_DELETE_ACTION
          return FOREIGN_KEY_DELETE_ACTION;
        case 4: // IMMUTABLE_SECONDARY_KEY
          return IMMUTABLE_SECONDARY_KEY;
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
  private static final int __IMMUTABLESECONDARYKEY_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.DB_CONFIG,_Fields.FOREIGN_DB,_Fields.FOREIGN_KEY_DELETE_ACTION,_Fields.IMMUTABLE_SECONDARY_KEY};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DB_CONFIG, new org.apache.thrift.meta_data.FieldMetaData("dbConfig", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TDatabaseConfig.class)));
    tmpMap.put(_Fields.FOREIGN_DB, new org.apache.thrift.meta_data.FieldMetaData("foreignDb", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TDatabase.class)));
    tmpMap.put(_Fields.FOREIGN_KEY_DELETE_ACTION, new org.apache.thrift.meta_data.FieldMetaData("foreignKeyDeleteAction", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, TFKDeleteAction.class)));
    tmpMap.put(_Fields.IMMUTABLE_SECONDARY_KEY, new org.apache.thrift.meta_data.FieldMetaData("immutableSecondaryKey", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TSecondaryDatabaseConfig.class, metaDataMap);
  }

  public TSecondaryDatabaseConfig() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TSecondaryDatabaseConfig(TSecondaryDatabaseConfig other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetDbConfig()) {
      this.dbConfig = new TDatabaseConfig(other.dbConfig);
    }
    if (other.isSetForeignDb()) {
      this.foreignDb = new TDatabase(other.foreignDb);
    }
    if (other.isSetForeignKeyDeleteAction()) {
      this.foreignKeyDeleteAction = other.foreignKeyDeleteAction;
    }
    this.immutableSecondaryKey = other.immutableSecondaryKey;
  }

  public TSecondaryDatabaseConfig deepCopy() {
    return new TSecondaryDatabaseConfig(this);
  }

  @Override
  public void clear() {
    this.dbConfig = null;
    this.foreignDb = null;
    this.foreignKeyDeleteAction = null;
    setImmutableSecondaryKeyIsSet(false);
    this.immutableSecondaryKey = false;
  }

  public TDatabaseConfig getDbConfig() {
    return this.dbConfig;
  }

  public TSecondaryDatabaseConfig setDbConfig(TDatabaseConfig dbConfig) {
    this.dbConfig = dbConfig;
    return this;
  }

  public void unsetDbConfig() {
    this.dbConfig = null;
  }

  /** Returns true if field dbConfig is set (has been assigned a value) and false otherwise */
  public boolean isSetDbConfig() {
    return this.dbConfig != null;
  }

  public void setDbConfigIsSet(boolean value) {
    if (!value) {
      this.dbConfig = null;
    }
  }

  public TDatabase getForeignDb() {
    return this.foreignDb;
  }

  public TSecondaryDatabaseConfig setForeignDb(TDatabase foreignDb) {
    this.foreignDb = foreignDb;
    return this;
  }

  public void unsetForeignDb() {
    this.foreignDb = null;
  }

  /** Returns true if field foreignDb is set (has been assigned a value) and false otherwise */
  public boolean isSetForeignDb() {
    return this.foreignDb != null;
  }

  public void setForeignDbIsSet(boolean value) {
    if (!value) {
      this.foreignDb = null;
    }
  }

  /**
   * 
   * @see TFKDeleteAction
   */
  public TFKDeleteAction getForeignKeyDeleteAction() {
    return this.foreignKeyDeleteAction;
  }

  /**
   * 
   * @see TFKDeleteAction
   */
  public TSecondaryDatabaseConfig setForeignKeyDeleteAction(TFKDeleteAction foreignKeyDeleteAction) {
    this.foreignKeyDeleteAction = foreignKeyDeleteAction;
    return this;
  }

  public void unsetForeignKeyDeleteAction() {
    this.foreignKeyDeleteAction = null;
  }

  /** Returns true if field foreignKeyDeleteAction is set (has been assigned a value) and false otherwise */
  public boolean isSetForeignKeyDeleteAction() {
    return this.foreignKeyDeleteAction != null;
  }

  public void setForeignKeyDeleteActionIsSet(boolean value) {
    if (!value) {
      this.foreignKeyDeleteAction = null;
    }
  }

  public boolean isImmutableSecondaryKey() {
    return this.immutableSecondaryKey;
  }

  public TSecondaryDatabaseConfig setImmutableSecondaryKey(boolean immutableSecondaryKey) {
    this.immutableSecondaryKey = immutableSecondaryKey;
    setImmutableSecondaryKeyIsSet(true);
    return this;
  }

  public void unsetImmutableSecondaryKey() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __IMMUTABLESECONDARYKEY_ISSET_ID);
  }

  /** Returns true if field immutableSecondaryKey is set (has been assigned a value) and false otherwise */
  public boolean isSetImmutableSecondaryKey() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __IMMUTABLESECONDARYKEY_ISSET_ID);
  }

  public void setImmutableSecondaryKeyIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __IMMUTABLESECONDARYKEY_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case DB_CONFIG:
      if (value == null) {
        unsetDbConfig();
      } else {
        setDbConfig((TDatabaseConfig)value);
      }
      break;

    case FOREIGN_DB:
      if (value == null) {
        unsetForeignDb();
      } else {
        setForeignDb((TDatabase)value);
      }
      break;

    case FOREIGN_KEY_DELETE_ACTION:
      if (value == null) {
        unsetForeignKeyDeleteAction();
      } else {
        setForeignKeyDeleteAction((TFKDeleteAction)value);
      }
      break;

    case IMMUTABLE_SECONDARY_KEY:
      if (value == null) {
        unsetImmutableSecondaryKey();
      } else {
        setImmutableSecondaryKey((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case DB_CONFIG:
      return getDbConfig();

    case FOREIGN_DB:
      return getForeignDb();

    case FOREIGN_KEY_DELETE_ACTION:
      return getForeignKeyDeleteAction();

    case IMMUTABLE_SECONDARY_KEY:
      return isImmutableSecondaryKey();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case DB_CONFIG:
      return isSetDbConfig();
    case FOREIGN_DB:
      return isSetForeignDb();
    case FOREIGN_KEY_DELETE_ACTION:
      return isSetForeignKeyDeleteAction();
    case IMMUTABLE_SECONDARY_KEY:
      return isSetImmutableSecondaryKey();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TSecondaryDatabaseConfig)
      return this.equals((TSecondaryDatabaseConfig)that);
    return false;
  }

  public boolean equals(TSecondaryDatabaseConfig that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_dbConfig = true && this.isSetDbConfig();
    boolean that_present_dbConfig = true && that.isSetDbConfig();
    if (this_present_dbConfig || that_present_dbConfig) {
      if (!(this_present_dbConfig && that_present_dbConfig))
        return false;
      if (!this.dbConfig.equals(that.dbConfig))
        return false;
    }

    boolean this_present_foreignDb = true && this.isSetForeignDb();
    boolean that_present_foreignDb = true && that.isSetForeignDb();
    if (this_present_foreignDb || that_present_foreignDb) {
      if (!(this_present_foreignDb && that_present_foreignDb))
        return false;
      if (!this.foreignDb.equals(that.foreignDb))
        return false;
    }

    boolean this_present_foreignKeyDeleteAction = true && this.isSetForeignKeyDeleteAction();
    boolean that_present_foreignKeyDeleteAction = true && that.isSetForeignKeyDeleteAction();
    if (this_present_foreignKeyDeleteAction || that_present_foreignKeyDeleteAction) {
      if (!(this_present_foreignKeyDeleteAction && that_present_foreignKeyDeleteAction))
        return false;
      if (!this.foreignKeyDeleteAction.equals(that.foreignKeyDeleteAction))
        return false;
    }

    boolean this_present_immutableSecondaryKey = true && this.isSetImmutableSecondaryKey();
    boolean that_present_immutableSecondaryKey = true && that.isSetImmutableSecondaryKey();
    if (this_present_immutableSecondaryKey || that_present_immutableSecondaryKey) {
      if (!(this_present_immutableSecondaryKey && that_present_immutableSecondaryKey))
        return false;
      if (this.immutableSecondaryKey != that.immutableSecondaryKey)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetDbConfig()) ? 131071 : 524287);
    if (isSetDbConfig())
      hashCode = hashCode * 8191 + dbConfig.hashCode();

    hashCode = hashCode * 8191 + ((isSetForeignDb()) ? 131071 : 524287);
    if (isSetForeignDb())
      hashCode = hashCode * 8191 + foreignDb.hashCode();

    hashCode = hashCode * 8191 + ((isSetForeignKeyDeleteAction()) ? 131071 : 524287);
    if (isSetForeignKeyDeleteAction())
      hashCode = hashCode * 8191 + foreignKeyDeleteAction.getValue();

    hashCode = hashCode * 8191 + ((isSetImmutableSecondaryKey()) ? 131071 : 524287);
    if (isSetImmutableSecondaryKey())
      hashCode = hashCode * 8191 + ((immutableSecondaryKey) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TSecondaryDatabaseConfig other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetDbConfig()).compareTo(other.isSetDbConfig());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDbConfig()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dbConfig, other.dbConfig);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetForeignDb()).compareTo(other.isSetForeignDb());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetForeignDb()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.foreignDb, other.foreignDb);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetForeignKeyDeleteAction()).compareTo(other.isSetForeignKeyDeleteAction());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetForeignKeyDeleteAction()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.foreignKeyDeleteAction, other.foreignKeyDeleteAction);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetImmutableSecondaryKey()).compareTo(other.isSetImmutableSecondaryKey());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetImmutableSecondaryKey()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.immutableSecondaryKey, other.immutableSecondaryKey);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TSecondaryDatabaseConfig(");
    boolean first = true;

    if (isSetDbConfig()) {
      sb.append("dbConfig:");
      if (this.dbConfig == null) {
        sb.append("null");
      } else {
        sb.append(this.dbConfig);
      }
      first = false;
    }
    if (isSetForeignDb()) {
      if (!first) sb.append(", ");
      sb.append("foreignDb:");
      if (this.foreignDb == null) {
        sb.append("null");
      } else {
        sb.append(this.foreignDb);
      }
      first = false;
    }
    if (isSetForeignKeyDeleteAction()) {
      if (!first) sb.append(", ");
      sb.append("foreignKeyDeleteAction:");
      if (this.foreignKeyDeleteAction == null) {
        sb.append("null");
      } else {
        sb.append(this.foreignKeyDeleteAction);
      }
      first = false;
    }
    if (isSetImmutableSecondaryKey()) {
      if (!first) sb.append(", ");
      sb.append("immutableSecondaryKey:");
      sb.append(this.immutableSecondaryKey);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
    if (dbConfig != null) {
      dbConfig.validate();
    }
    if (foreignDb != null) {
      foreignDb.validate();
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

  private static class TSecondaryDatabaseConfigStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TSecondaryDatabaseConfigStandardScheme getScheme() {
      return new TSecondaryDatabaseConfigStandardScheme();
    }
  }

  private static class TSecondaryDatabaseConfigStandardScheme extends org.apache.thrift.scheme.StandardScheme<TSecondaryDatabaseConfig> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TSecondaryDatabaseConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DB_CONFIG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.dbConfig = new TDatabaseConfig();
              struct.dbConfig.read(iprot);
              struct.setDbConfigIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FOREIGN_DB
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.foreignDb = new TDatabase();
              struct.foreignDb.read(iprot);
              struct.setForeignDbIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FOREIGN_KEY_DELETE_ACTION
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.foreignKeyDeleteAction = com.sleepycat.thrift.TFKDeleteAction.findByValue(iprot.readI32());
              struct.setForeignKeyDeleteActionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // IMMUTABLE_SECONDARY_KEY
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.immutableSecondaryKey = iprot.readBool();
              struct.setImmutableSecondaryKeyIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TSecondaryDatabaseConfig struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.dbConfig != null) {
        if (struct.isSetDbConfig()) {
          oprot.writeFieldBegin(DB_CONFIG_FIELD_DESC);
          struct.dbConfig.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.foreignDb != null) {
        if (struct.isSetForeignDb()) {
          oprot.writeFieldBegin(FOREIGN_DB_FIELD_DESC);
          struct.foreignDb.write(oprot);
          oprot.writeFieldEnd();
        }
      }
      if (struct.foreignKeyDeleteAction != null) {
        if (struct.isSetForeignKeyDeleteAction()) {
          oprot.writeFieldBegin(FOREIGN_KEY_DELETE_ACTION_FIELD_DESC);
          oprot.writeI32(struct.foreignKeyDeleteAction.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetImmutableSecondaryKey()) {
        oprot.writeFieldBegin(IMMUTABLE_SECONDARY_KEY_FIELD_DESC);
        oprot.writeBool(struct.immutableSecondaryKey);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TSecondaryDatabaseConfigTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TSecondaryDatabaseConfigTupleScheme getScheme() {
      return new TSecondaryDatabaseConfigTupleScheme();
    }
  }

  private static class TSecondaryDatabaseConfigTupleScheme extends org.apache.thrift.scheme.TupleScheme<TSecondaryDatabaseConfig> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TSecondaryDatabaseConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetDbConfig()) {
        optionals.set(0);
      }
      if (struct.isSetForeignDb()) {
        optionals.set(1);
      }
      if (struct.isSetForeignKeyDeleteAction()) {
        optionals.set(2);
      }
      if (struct.isSetImmutableSecondaryKey()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetDbConfig()) {
        struct.dbConfig.write(oprot);
      }
      if (struct.isSetForeignDb()) {
        struct.foreignDb.write(oprot);
      }
      if (struct.isSetForeignKeyDeleteAction()) {
        oprot.writeI32(struct.foreignKeyDeleteAction.getValue());
      }
      if (struct.isSetImmutableSecondaryKey()) {
        oprot.writeBool(struct.immutableSecondaryKey);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TSecondaryDatabaseConfig struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.dbConfig = new TDatabaseConfig();
        struct.dbConfig.read(iprot);
        struct.setDbConfigIsSet(true);
      }
      if (incoming.get(1)) {
        struct.foreignDb = new TDatabase();
        struct.foreignDb.read(iprot);
        struct.setForeignDbIsSet(true);
      }
      if (incoming.get(2)) {
        struct.foreignKeyDeleteAction = com.sleepycat.thrift.TFKDeleteAction.findByValue(iprot.readI32());
        struct.setForeignKeyDeleteActionIsSet(true);
      }
      if (incoming.get(3)) {
        struct.immutableSecondaryKey = iprot.readBool();
        struct.setImmutableSecondaryKeyIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

