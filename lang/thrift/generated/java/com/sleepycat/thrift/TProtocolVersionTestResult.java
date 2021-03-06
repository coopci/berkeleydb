/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.sleepycat.thrift;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * *************************
 *   Data Type Definitions  *
 * *************************
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-01-29")
public class TProtocolVersionTestResult implements org.apache.thrift.TBase<TProtocolVersionTestResult, TProtocolVersionTestResult._Fields>, java.io.Serializable, Cloneable, Comparable<TProtocolVersionTestResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TProtocolVersionTestResult");

  private static final org.apache.thrift.protocol.TField SUPPORTED_FIELD_DESC = new org.apache.thrift.protocol.TField("supported", org.apache.thrift.protocol.TType.BOOL, (short)1);
  private static final org.apache.thrift.protocol.TField SERVER_PROTOCOL_VERSION_FIELD_DESC = new org.apache.thrift.protocol.TField("serverProtocolVersion", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SERVER_BIG_ENDIAN_FIELD_DESC = new org.apache.thrift.protocol.TField("serverBigEndian", org.apache.thrift.protocol.TType.BOOL, (short)3);
  private static final org.apache.thrift.protocol.TField MESSAGE_FIELD_DESC = new org.apache.thrift.protocol.TField("message", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TProtocolVersionTestResultStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TProtocolVersionTestResultTupleSchemeFactory();

  public boolean supported; // required
  public java.lang.String serverProtocolVersion; // required
  public boolean serverBigEndian; // required
  public java.lang.String message; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUPPORTED((short)1, "supported"),
    SERVER_PROTOCOL_VERSION((short)2, "serverProtocolVersion"),
    SERVER_BIG_ENDIAN((short)3, "serverBigEndian"),
    MESSAGE((short)4, "message");

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
        case 1: // SUPPORTED
          return SUPPORTED;
        case 2: // SERVER_PROTOCOL_VERSION
          return SERVER_PROTOCOL_VERSION;
        case 3: // SERVER_BIG_ENDIAN
          return SERVER_BIG_ENDIAN;
        case 4: // MESSAGE
          return MESSAGE;
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
  private static final int __SUPPORTED_ISSET_ID = 0;
  private static final int __SERVERBIGENDIAN_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.MESSAGE};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUPPORTED, new org.apache.thrift.meta_data.FieldMetaData("supported", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.SERVER_PROTOCOL_VERSION, new org.apache.thrift.meta_data.FieldMetaData("serverProtocolVersion", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SERVER_BIG_ENDIAN, new org.apache.thrift.meta_data.FieldMetaData("serverBigEndian", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    tmpMap.put(_Fields.MESSAGE, new org.apache.thrift.meta_data.FieldMetaData("message", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TProtocolVersionTestResult.class, metaDataMap);
  }

  public TProtocolVersionTestResult() {
  }

  public TProtocolVersionTestResult(
    boolean supported,
    java.lang.String serverProtocolVersion,
    boolean serverBigEndian)
  {
    this();
    this.supported = supported;
    setSupportedIsSet(true);
    this.serverProtocolVersion = serverProtocolVersion;
    this.serverBigEndian = serverBigEndian;
    setServerBigEndianIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TProtocolVersionTestResult(TProtocolVersionTestResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.supported = other.supported;
    if (other.isSetServerProtocolVersion()) {
      this.serverProtocolVersion = other.serverProtocolVersion;
    }
    this.serverBigEndian = other.serverBigEndian;
    if (other.isSetMessage()) {
      this.message = other.message;
    }
  }

  public TProtocolVersionTestResult deepCopy() {
    return new TProtocolVersionTestResult(this);
  }

  @Override
  public void clear() {
    setSupportedIsSet(false);
    this.supported = false;
    this.serverProtocolVersion = null;
    setServerBigEndianIsSet(false);
    this.serverBigEndian = false;
    this.message = null;
  }

  public boolean isSupported() {
    return this.supported;
  }

  public TProtocolVersionTestResult setSupported(boolean supported) {
    this.supported = supported;
    setSupportedIsSet(true);
    return this;
  }

  public void unsetSupported() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SUPPORTED_ISSET_ID);
  }

  /** Returns true if field supported is set (has been assigned a value) and false otherwise */
  public boolean isSetSupported() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SUPPORTED_ISSET_ID);
  }

  public void setSupportedIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SUPPORTED_ISSET_ID, value);
  }

  public java.lang.String getServerProtocolVersion() {
    return this.serverProtocolVersion;
  }

  public TProtocolVersionTestResult setServerProtocolVersion(java.lang.String serverProtocolVersion) {
    this.serverProtocolVersion = serverProtocolVersion;
    return this;
  }

  public void unsetServerProtocolVersion() {
    this.serverProtocolVersion = null;
  }

  /** Returns true if field serverProtocolVersion is set (has been assigned a value) and false otherwise */
  public boolean isSetServerProtocolVersion() {
    return this.serverProtocolVersion != null;
  }

  public void setServerProtocolVersionIsSet(boolean value) {
    if (!value) {
      this.serverProtocolVersion = null;
    }
  }

  public boolean isServerBigEndian() {
    return this.serverBigEndian;
  }

  public TProtocolVersionTestResult setServerBigEndian(boolean serverBigEndian) {
    this.serverBigEndian = serverBigEndian;
    setServerBigEndianIsSet(true);
    return this;
  }

  public void unsetServerBigEndian() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __SERVERBIGENDIAN_ISSET_ID);
  }

  /** Returns true if field serverBigEndian is set (has been assigned a value) and false otherwise */
  public boolean isSetServerBigEndian() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __SERVERBIGENDIAN_ISSET_ID);
  }

  public void setServerBigEndianIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __SERVERBIGENDIAN_ISSET_ID, value);
  }

  public java.lang.String getMessage() {
    return this.message;
  }

  public TProtocolVersionTestResult setMessage(java.lang.String message) {
    this.message = message;
    return this;
  }

  public void unsetMessage() {
    this.message = null;
  }

  /** Returns true if field message is set (has been assigned a value) and false otherwise */
  public boolean isSetMessage() {
    return this.message != null;
  }

  public void setMessageIsSet(boolean value) {
    if (!value) {
      this.message = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case SUPPORTED:
      if (value == null) {
        unsetSupported();
      } else {
        setSupported((java.lang.Boolean)value);
      }
      break;

    case SERVER_PROTOCOL_VERSION:
      if (value == null) {
        unsetServerProtocolVersion();
      } else {
        setServerProtocolVersion((java.lang.String)value);
      }
      break;

    case SERVER_BIG_ENDIAN:
      if (value == null) {
        unsetServerBigEndian();
      } else {
        setServerBigEndian((java.lang.Boolean)value);
      }
      break;

    case MESSAGE:
      if (value == null) {
        unsetMessage();
      } else {
        setMessage((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case SUPPORTED:
      return isSupported();

    case SERVER_PROTOCOL_VERSION:
      return getServerProtocolVersion();

    case SERVER_BIG_ENDIAN:
      return isServerBigEndian();

    case MESSAGE:
      return getMessage();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case SUPPORTED:
      return isSetSupported();
    case SERVER_PROTOCOL_VERSION:
      return isSetServerProtocolVersion();
    case SERVER_BIG_ENDIAN:
      return isSetServerBigEndian();
    case MESSAGE:
      return isSetMessage();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TProtocolVersionTestResult)
      return this.equals((TProtocolVersionTestResult)that);
    return false;
  }

  public boolean equals(TProtocolVersionTestResult that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_supported = true;
    boolean that_present_supported = true;
    if (this_present_supported || that_present_supported) {
      if (!(this_present_supported && that_present_supported))
        return false;
      if (this.supported != that.supported)
        return false;
    }

    boolean this_present_serverProtocolVersion = true && this.isSetServerProtocolVersion();
    boolean that_present_serverProtocolVersion = true && that.isSetServerProtocolVersion();
    if (this_present_serverProtocolVersion || that_present_serverProtocolVersion) {
      if (!(this_present_serverProtocolVersion && that_present_serverProtocolVersion))
        return false;
      if (!this.serverProtocolVersion.equals(that.serverProtocolVersion))
        return false;
    }

    boolean this_present_serverBigEndian = true;
    boolean that_present_serverBigEndian = true;
    if (this_present_serverBigEndian || that_present_serverBigEndian) {
      if (!(this_present_serverBigEndian && that_present_serverBigEndian))
        return false;
      if (this.serverBigEndian != that.serverBigEndian)
        return false;
    }

    boolean this_present_message = true && this.isSetMessage();
    boolean that_present_message = true && that.isSetMessage();
    if (this_present_message || that_present_message) {
      if (!(this_present_message && that_present_message))
        return false;
      if (!this.message.equals(that.message))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((supported) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetServerProtocolVersion()) ? 131071 : 524287);
    if (isSetServerProtocolVersion())
      hashCode = hashCode * 8191 + serverProtocolVersion.hashCode();

    hashCode = hashCode * 8191 + ((serverBigEndian) ? 131071 : 524287);

    hashCode = hashCode * 8191 + ((isSetMessage()) ? 131071 : 524287);
    if (isSetMessage())
      hashCode = hashCode * 8191 + message.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TProtocolVersionTestResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetSupported()).compareTo(other.isSetSupported());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSupported()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.supported, other.supported);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetServerProtocolVersion()).compareTo(other.isSetServerProtocolVersion());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServerProtocolVersion()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serverProtocolVersion, other.serverProtocolVersion);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetServerBigEndian()).compareTo(other.isSetServerBigEndian());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetServerBigEndian()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serverBigEndian, other.serverBigEndian);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetMessage()).compareTo(other.isSetMessage());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMessage()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.message, other.message);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TProtocolVersionTestResult(");
    boolean first = true;

    sb.append("supported:");
    sb.append(this.supported);
    first = false;
    if (!first) sb.append(", ");
    sb.append("serverProtocolVersion:");
    if (this.serverProtocolVersion == null) {
      sb.append("null");
    } else {
      sb.append(this.serverProtocolVersion);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("serverBigEndian:");
    sb.append(this.serverBigEndian);
    first = false;
    if (isSetMessage()) {
      if (!first) sb.append(", ");
      sb.append("message:");
      if (this.message == null) {
        sb.append("null");
      } else {
        sb.append(this.message);
      }
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

  private static class TProtocolVersionTestResultStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TProtocolVersionTestResultStandardScheme getScheme() {
      return new TProtocolVersionTestResultStandardScheme();
    }
  }

  private static class TProtocolVersionTestResultStandardScheme extends org.apache.thrift.scheme.StandardScheme<TProtocolVersionTestResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TProtocolVersionTestResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUPPORTED
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.supported = iprot.readBool();
              struct.setSupportedIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // SERVER_PROTOCOL_VERSION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.serverProtocolVersion = iprot.readString();
              struct.setServerProtocolVersionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SERVER_BIG_ENDIAN
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.serverBigEndian = iprot.readBool();
              struct.setServerBigEndianIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // MESSAGE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.message = iprot.readString();
              struct.setMessageIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TProtocolVersionTestResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(SUPPORTED_FIELD_DESC);
      oprot.writeBool(struct.supported);
      oprot.writeFieldEnd();
      if (struct.serverProtocolVersion != null) {
        oprot.writeFieldBegin(SERVER_PROTOCOL_VERSION_FIELD_DESC);
        oprot.writeString(struct.serverProtocolVersion);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(SERVER_BIG_ENDIAN_FIELD_DESC);
      oprot.writeBool(struct.serverBigEndian);
      oprot.writeFieldEnd();
      if (struct.message != null) {
        if (struct.isSetMessage()) {
          oprot.writeFieldBegin(MESSAGE_FIELD_DESC);
          oprot.writeString(struct.message);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TProtocolVersionTestResultTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TProtocolVersionTestResultTupleScheme getScheme() {
      return new TProtocolVersionTestResultTupleScheme();
    }
  }

  private static class TProtocolVersionTestResultTupleScheme extends org.apache.thrift.scheme.TupleScheme<TProtocolVersionTestResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TProtocolVersionTestResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetSupported()) {
        optionals.set(0);
      }
      if (struct.isSetServerProtocolVersion()) {
        optionals.set(1);
      }
      if (struct.isSetServerBigEndian()) {
        optionals.set(2);
      }
      if (struct.isSetMessage()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetSupported()) {
        oprot.writeBool(struct.supported);
      }
      if (struct.isSetServerProtocolVersion()) {
        oprot.writeString(struct.serverProtocolVersion);
      }
      if (struct.isSetServerBigEndian()) {
        oprot.writeBool(struct.serverBigEndian);
      }
      if (struct.isSetMessage()) {
        oprot.writeString(struct.message);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TProtocolVersionTestResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.supported = iprot.readBool();
        struct.setSupportedIsSet(true);
      }
      if (incoming.get(1)) {
        struct.serverProtocolVersion = iprot.readString();
        struct.setServerProtocolVersionIsSet(true);
      }
      if (incoming.get(2)) {
        struct.serverBigEndian = iprot.readBool();
        struct.setServerBigEndianIsSet(true);
      }
      if (incoming.get(3)) {
        struct.message = iprot.readString();
        struct.setMessageIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

