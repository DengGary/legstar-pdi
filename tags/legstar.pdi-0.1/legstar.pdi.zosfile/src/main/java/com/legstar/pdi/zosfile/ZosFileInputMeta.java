package com.legstar.pdi.zosfile;

import java.util.List;
import java.util.Map;


import org.pentaho.di.core.*;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.*;
import org.pentaho.di.core.row.*;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.repository.*;
import org.pentaho.di.trans.*;
import org.pentaho.di.trans.step.*;
import org.w3c.dom.Node;

import com.legstar.pdi.CobolFileInputField;
import com.legstar.pdi.CobolToPdi;

/**
 * This is a Kettle step implementation class. Acts as a model and knows how to
 * serialize itself both in XML or a Kettle repository. TODO add host character
 * set
 */
public class ZosFileInputMeta extends BaseStepMeta implements StepMetaInterface {

	private static Class<?> PKG = ZosFileInputMeta.class; // for i18n purposes

	/** The JAXB qualified class name. */
	private String _jaxbQualifiedClassName;

	/** Used to serialize/deserialize the JAXB qualified class name from XML. */
	public static final String JAXBQUALIFIEDCLASSNAME_TAG = "jaxbqualifiedclassname";
	
	/** The mainframe character set.*/
	private String _hostCharset;
	
    /** Used to serialize/deserialize the mainframe host character set from XML. */
    public static final String HOSTCHARSET_TAG = "hostcharset";

	/** The local copy of the z/OS file. */
	private String _filename;

	/** Used to serialize/deserialize the file name from XML. */
	public static final String FILENAME_TAG = "filename";
	
    /** Are the z/OS file records variable length. */
    private boolean _isVariableLength;

    /** Used to serialize/deserialize isVariableLength from XML. */
    public static final String ISVARIABLELENGTH_TAG = "isvariablelength";

    /** Does the z/OS file records start with an RDW?. */
	private boolean _hasRecordDescriptorWord;

    /** Used to serialize/deserialize hasRecordDescriptorWord from XML. */
    public static final String HASRECORDDESCRIPTORWORD_TAG = "hasrdw";
    
    /** Fields from a z/OS file record. */
	private CobolFileInputField[] _inputFields;
	
    /** Field name XML tag. */
    public static final String FIELD_NAME_TAG = "name";

    /** Field type XML tag. */
    public static final String FIELD_TYPE_TAG = "type";

    /** Field length XML tag. */
    public static final String FIELD_LENGTH_TAG = "length";

    /** Field precision XML tag. */
    public static final String FIELD_PRECISION_TAG = "precision";

    /** Field trim type XML tag. */
    public static final String FIELD_TRIM_TYPE_TAG = "trim_type";

    /** Field redefined XML tag. */
    public static final String FIELD_REDEFINED_TAG = "redefined";

    public ZosFileInputMeta() {
		super();
	}

	/*
	 * ------------------------------------------------------------------------
	 * Bean properties section
	 * ------------------------------------------------------------------------
	 */

	/**
	 * @return the local copy of the z/OS file
	 */
	public String getFilename() {
		return _filename;
	}

	/**
	 * @param filename
	 *            the local copy of the z/OS file to set
	 */
	public void setFilename(String filename) {
		_filename = filename;
	}

    /**
     * The mainframe character set
     * @return the mainframe character set
     */
    public String getHostCharset() {
        return _hostCharset;
    }

    /**
     * The mainframe character set.
     * @param hostCharset The mainframe character set
     */
    public void setHostCharset(String hostCharset) {
        _hostCharset = hostCharset;
    }

    /**
     * Are the z/OS file records variable length
     * @return true if the z/OS file records are fixed length
     */
    public boolean isVariableLength() {
        return _isVariableLength;
    }

    /**
     * Are the z/OS file records variable length
     * @param isFixedRecord true if the z/OS file records are fixed length
     */
    public void setIsVariableLength(boolean isFixedRecord) {
        _isVariableLength = isFixedRecord;
    }

    /**
     * Does the z/OS file records start with an RDW?.
     * @return true if z/OS file records start with a record descriptor word
     */
    public boolean hasRecordDescriptorWord() {
        return _hasRecordDescriptorWord;
    }

    /**
     * Does the z/OS file records start with an RDW?.
     * 
     * @param _hasRecordDescriptorWord true if z/OS file records start with a
     *            record descriptor word
     */
    public void setHasRecordDescriptorWord(boolean hasRecordDescriptorWord) {
        _hasRecordDescriptorWord = hasRecordDescriptorWord;
    }

	/**
	 * @return the inputFields
	 */
	public CobolFileInputField[] getInputFields() {
		return _inputFields;
	}

	/**
	 * @param inputFields
	 *            the inputFields to set
	 */
	public void setInputFields(CobolFileInputField[] inputFields) {
		_inputFields = inputFields;
	}

	/**
	 * @return the JAXB class name
	 */
	public String getJaxbQualifiedClassName() {
		return _jaxbQualifiedClassName;
	}

	/**
	 * @param jaxbQualifiedClassName
	 *            the JAXB class name to set
	 */
	public void setJaxbQualifiedClassName(String jaxbQualifiedClassName) {
		_jaxbQualifiedClassName = jaxbQualifiedClassName;
	}

	/*
	 * ------------------------------------------------------------------------
	 * Initial bean properties values
	 * ------------------------------------------------------------------------
	 */
	public void setDefault() {
		_inputFields = new CobolFileInputField[0];
		_hostCharset = CobolToPdi.getDefaultHostCharset();
	}

	/*
	 * ------------------------------------------------------------------------
	 * XML Serialization section
	 * ------------------------------------------------------------------------
	 */

	/** {@inheritDoc} */
	public String getXML() throws KettleValueException {
		StringBuffer retval = new StringBuffer();
		retval.append("    ").append(
				XMLHandler.addTagValue(JAXBQUALIFIEDCLASSNAME_TAG, _jaxbQualifiedClassName));
        retval.append("    ").append(
                XMLHandler.addTagValue(HOSTCHARSET_TAG, _hostCharset));
		retval.append("    ").append(
				XMLHandler.addTagValue(FILENAME_TAG, _filename));
        retval.append("    ").append(
                XMLHandler.addTagValue(ISVARIABLELENGTH_TAG, _isVariableLength));
        retval.append("    ").append(
                XMLHandler.addTagValue(HASRECORDDESCRIPTORWORD_TAG, _hasRecordDescriptorWord));

        retval.append("    <fields>").append(Const.CR);
        for (int i = 0; i < _inputFields.length; i++) {
            CobolFileInputField field = _inputFields[i];

            retval.append("      <field>").append(Const.CR);
            retval.append("        ").append(
                    XMLHandler.addTagValue(FIELD_NAME_TAG, field.getName()));
            retval.append("        ").append(
                    XMLHandler.addTagValue(FIELD_TYPE_TAG,
                            ValueMeta.getTypeDesc(field.getType())));
            retval.append("        ").append(
                    XMLHandler.addTagValue(FIELD_LENGTH_TAG,
                            field.getLength()));
            retval.append("        ").append(
                    XMLHandler.addTagValue(FIELD_PRECISION_TAG,
                            field.getPrecision()));
            retval.append("        ").append(
                    XMLHandler.addTagValue(FIELD_TRIM_TYPE_TAG, ValueMeta
                            .getTrimTypeCode(field.getTrimType())));
            retval.append("        ").append(
                    XMLHandler.addTagValue(FIELD_REDEFINED_TAG,
                            field.isRedefined()));
            retval.append("      </field>").append(Const.CR);
        }
        retval.append("    </fields>").append(Const.CR);
		return retval.toString();
	}

	/**
	 * Load the values for this step from an XML Node
	 * 
	 * @param stepnode
	 *            the Node to get the info from
	 * @param databases
	 *            The available list of databases to reference to
	 * @param counters
	 *            Counters to reference.
	 * @throws KettleXMLException
	 *             When an unexpected XML error occurred. (malformed etc.)
	 */
	public void loadXML(Node stepnode, List<DatabaseMeta> databases,
			Map<String, Counter> counters) throws KettleXMLException {

		try {
            _jaxbQualifiedClassName = XMLHandler
                    .getTagValue(stepnode, JAXBQUALIFIEDCLASSNAME_TAG);
            _hostCharset = XMLHandler
                    .getTagValue(stepnode, HOSTCHARSET_TAG);
            _filename = XMLHandler.getTagValue(stepnode, FILENAME_TAG);
            _isVariableLength = "Y".equalsIgnoreCase(XMLHandler
                    .getTagValue(stepnode, ISVARIABLELENGTH_TAG));
            _hasRecordDescriptorWord = "Y".equalsIgnoreCase(XMLHandler
                    .getTagValue(stepnode, HASRECORDDESCRIPTORWORD_TAG));

			Node fields = XMLHandler.getSubNode(stepnode, "fields");
			int nFields = XMLHandler.countNodes(fields, "field");

			_inputFields = new CobolFileInputField[nFields];
			for (int i = 0; i < nFields; i++) {
				_inputFields[i] = new CobolFileInputField();

				Node fnode = XMLHandler.getSubNodeByNr(fields, "field", i);

				_inputFields[i].setName(XMLHandler.getTagValue(fnode, FIELD_NAME_TAG));
				_inputFields[i].setType(ValueMeta.getType(XMLHandler
						.getTagValue(fnode, FIELD_TYPE_TAG)));
				_inputFields[i].setLength(Const.toInt(XMLHandler.getTagValue(
						fnode, FIELD_LENGTH_TAG), -1));
				_inputFields[i].setPrecision(Const.toInt(XMLHandler
						.getTagValue(fnode, FIELD_PRECISION_TAG), -1));
				_inputFields[i].setTrimType(ValueMeta
						.getTrimTypeByCode(XMLHandler.getTagValue(fnode,
								FIELD_TRIM_TYPE_TAG)));
                _inputFields[i].setRedefined("Y".equalsIgnoreCase(XMLHandler
                        .getTagValue(fnode, FIELD_REDEFINED_TAG)));
			}
		} catch (Exception e) {
			throw new KettleXMLException("Unable to load step info from XML", e);
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * Repository Serialization section
	 * ------------------------------------------------------------------------
	 */

	public void readRep(Repository rep, ObjectId id_step,
			List<DatabaseMeta> databases, Map<String, Counter> counters)
			throws KettleException {
		try {
            _jaxbQualifiedClassName = rep.getStepAttributeString(id_step,
                    JAXBQUALIFIEDCLASSNAME_TAG);
            _hostCharset = rep.getStepAttributeString(id_step,
                    HOSTCHARSET_TAG);
            _filename = rep.getStepAttributeString(id_step, FILENAME_TAG);
            _isVariableLength = rep.getStepAttributeBoolean(id_step,
                    "ISFIXEDRECORD_TAG");
            _hasRecordDescriptorWord = rep.getStepAttributeBoolean(id_step,
                    "HASRECORDDESCRIPTORWORD_TAG");

			int nFields = rep.countNrStepAttributes(id_step, "field_" + FIELD_NAME_TAG);
			_inputFields = new CobolFileInputField[nFields];

            for (int i = 0; i < nFields; i++) {
                _inputFields[i] = new CobolFileInputField();

                _inputFields[i].setName(rep.getStepAttributeString(id_step, i,
                        "field_" + FIELD_NAME_TAG));
                _inputFields[i].setType(ValueMeta.getType(rep
                        .getStepAttributeString(id_step, i, "field_"
                                + FIELD_TYPE_TAG)));
                _inputFields[i].setLength((int) rep.getStepAttributeInteger(
                        id_step, i, "field_" + FIELD_LENGTH_TAG));
                _inputFields[i].setPrecision((int) rep.getStepAttributeInteger(
                        id_step, i, "field_" + FIELD_PRECISION_TAG));
                _inputFields[i]
                        .setTrimType(ValueMeta.getTrimTypeByCode(rep
                                .getStepAttributeString(id_step, i,
                                        "field_" + FIELD_TRIM_TYPE_TAG)));
                _inputFields[i].setRedefined(rep.getStepAttributeBoolean(
                        id_step, i, "field_" + FIELD_REDEFINED_TAG));
            }
		} catch (Exception e) {
			throw new KettleException(BaseMessages.getString(PKG,
					"TemplateStep.Exception.UnexpectedErrorInReadingStepInfo"),
					e);
		}
	}

	public void saveRep(Repository rep, ObjectId id_transformation,
			ObjectId id_step) throws KettleException {
		try {
            rep.saveStepAttribute(id_transformation, id_step,
                    JAXBQUALIFIEDCLASSNAME_TAG, _jaxbQualifiedClassName);
            rep.saveStepAttribute(id_transformation, id_step,
                    HOSTCHARSET_TAG, _hostCharset);
            rep.saveStepAttribute(id_transformation, id_step, FILENAME_TAG,
                    _filename);
            rep.saveStepAttribute(id_transformation, id_step,
                    ISVARIABLELENGTH_TAG, _isVariableLength);
            rep.saveStepAttribute(id_transformation, id_step,
                    HASRECORDDESCRIPTORWORD_TAG, _hasRecordDescriptorWord);

			for (int i = 0; i < _inputFields.length; i++) {
				CobolFileInputField field = _inputFields[i];

				rep.saveStepAttribute(id_transformation, id_step, i,
				        "field_" + FIELD_NAME_TAG, field.getName());
				rep.saveStepAttribute(id_transformation, id_step, i,
				        "field_" + FIELD_TYPE_TAG, ValueMeta.getTypeDesc(field.getType()));
				rep.saveStepAttribute(id_transformation, id_step, i,
				        "field_" + FIELD_LENGTH_TAG, field.getLength());
				rep.saveStepAttribute(id_transformation, id_step, i,
				        "field_" + FIELD_PRECISION_TAG, field.getPrecision());
				rep.saveStepAttribute(id_transformation, id_step, i,
				        "field_" + FIELD_TRIM_TYPE_TAG, ValueMeta.getTrimTypeCode(field
								.getTrimType()));
                rep.saveStepAttribute(id_transformation, id_step, i,
                        "field_" + FIELD_REDEFINED_TAG, field.isRedefined());
			}
		} catch (Exception e) {
			throw new KettleException(BaseMessages.getString(PKG,
					"TemplateStep.Exception.UnableToSaveStepInfoToRepository")
					+ id_step, e);
		}
	}

	/*
	 * ------------------------------------------------------------------------
	 * Defines what gets sent. A row is a flat sequence of fields.
	 * ------------------------------------------------------------------------
	 */

	public void getFields(RowMetaInterface rowMeta, String origin,
			RowMetaInterface[] info, StepMeta nextStep, VariableSpace space) {

		CobolToPdi.fieldsToRowMeta(_inputFields, origin, rowMeta);
	}

	/*
	 * ------------------------------------------------------------------------
	 * Check sanity of step position in a transformation context.
	 * ------------------------------------------------------------------------
	 */

	public void check(List<CheckResultInterface> remarks, TransMeta transmeta,
			StepMeta stepMeta, RowMetaInterface prev, String input[],
			String output[], RowMetaInterface info) {
		CheckResult cr;

		// See if we have input streams leading to this step!
		if (input.length > 0) {
			cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, BaseMessages
					.getString(PKG,
							"ZosFileInputMeta.CheckResult.StepReceivingData"),
					stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(
					CheckResult.TYPE_RESULT_OK,
					BaseMessages
							.getString(PKG,
									"ZosFileInputMeta.CheckResult.NoInputReceivedFromOtherSteps"),
					stepMeta);
			remarks.add(cr);
		}

	}

	/*
	 * ------------------------------------------------------------------------
	 * Return associated Step implementation and StepData.
	 * ------------------------------------------------------------------------
	 */

	public StepInterface getStep(StepMeta stepMeta,
			StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
			Trans disp) {
		return new ZosFileInput(stepMeta, stepDataInterface, cnr, transMeta,
				disp);
	}

	public StepDataInterface getStepData() {
		return new ZosFileInputData();
	}

	/*
	 * ------------------------------------------------------------------------
	 * Java object overrides.
	 * ------------------------------------------------------------------------
	 */
	public Object clone() {
		Object retval = super.clone();
		return retval;
	}

}