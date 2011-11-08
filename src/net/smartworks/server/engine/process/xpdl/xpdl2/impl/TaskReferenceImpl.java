/**
 * <copyright>
 * </copyright>
 *
 * $Id: TaskReferenceImpl.java,v 1.1 2009/12/22 06:17:49 kmyu Exp $
 */
package net.smartworks.server.engine.process.xpdl.xpdl2.impl;

import net.smartworks.server.engine.process.xpdl.xpdl2.TaskReference;
import net.smartworks.server.engine.process.xpdl.xpdl2.Xpdl2Factory;

import commonj.sdo.Sequence;
import commonj.sdo.Type;

import java.lang.Object;

import org.apache.tuscany.sdo.impl.DataObjectBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Task Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.smartworks.server.engine.process.xpdl.xpdl2.impl.TaskReferenceImpl#getAny <em>Any</em>}</li>
 *   <li>{@link net.smartworks.server.engine.process.xpdl.xpdl2.impl.TaskReferenceImpl#getTaskRef <em>Task Ref</em>}</li>
 *   <li>{@link net.smartworks.server.engine.process.xpdl.xpdl2.impl.TaskReferenceImpl#getAnyAttribute <em>Any Attribute</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TaskReferenceImpl extends DataObjectBase implements TaskReference
{

  public final static int ANY = -1;

  public final static int TASK_REF = 0;

  public final static int ANY_ATTRIBUTE = -2;

  public final static int SDO_PROPERTY_COUNT = 1;

  public final static int EXTENDED_PROPERTY_COUNT = -2;


  /**
   * The internal feature id for the '<em><b>Any</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */ 
  public final static int INTERNAL_ANY = 0;

  /**
   * The internal feature id for the '<em><b>Task Ref</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */ 
  public final static int INTERNAL_TASK_REF = 1;

  /**
   * The internal feature id for the '<em><b>Any Attribute</b></em>' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */ 
  public final static int INTERNAL_ANY_ATTRIBUTE = 2;

  /**
   * The number of properties for this type.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  public final static int INTERNAL_PROPERTY_COUNT = 3;

  protected int internalConvertIndex(int internalIndex)
  {
    switch (internalIndex)
    {
      case INTERNAL_ANY: return ANY;
      case INTERNAL_TASK_REF: return TASK_REF;
      case INTERNAL_ANY_ATTRIBUTE: return ANY_ATTRIBUTE;
    }
    return super.internalConvertIndex(internalIndex);
  }


  /**
   * The cached value of the '{@link #getAny() <em>Any</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAny()
   * @generated
   * @ordered
   */
  
  protected Sequence any = null;
  
  /**
   * The default value of the '{@link #getTaskRef() <em>Task Ref</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTaskRef()
   * @generated
   * @ordered
   */
  protected static final String TASK_REF_DEFAULT_ = null;

  /**
   * The cached value of the '{@link #getTaskRef() <em>Task Ref</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTaskRef()
   * @generated
   * @ordered
   */
  protected String taskRef = TASK_REF_DEFAULT_;

  /**
   * This is true if the Task Ref attribute has been set.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  protected boolean taskRef_set_ = false;

  /**
   * The cached value of the '{@link #getAnyAttribute() <em>Any Attribute</em>}' attribute list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAnyAttribute()
   * @generated
   * @ordered
   */
  
  protected Sequence anyAttribute = null;
  
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public TaskReferenceImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Type getStaticType()
  {
    return ((Xpdl2FactoryImpl)Xpdl2Factory.INSTANCE).getTaskReference();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Sequence getAny()
  {
    if (any == null)
    {
      any = createSequence(INTERNAL_ANY);
    }
    return any;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getTaskRef()
  {
    return taskRef;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setTaskRef(String newTaskRef)
  {
    String oldTaskRef = taskRef;
    taskRef = newTaskRef;
    boolean oldTaskRef_set_ = taskRef_set_;
    taskRef_set_ = true;
    if (isNotifying())
      notify(ChangeKind.SET, INTERNAL_TASK_REF, oldTaskRef, taskRef, !oldTaskRef_set_);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unsetTaskRef()
  {
    String oldTaskRef = taskRef;
    boolean oldTaskRef_set_ = taskRef_set_;
    taskRef = TASK_REF_DEFAULT_;
    taskRef_set_ = false;
    if (isNotifying())
      notify(ChangeKind.UNSET, INTERNAL_TASK_REF, oldTaskRef, TASK_REF_DEFAULT_, oldTaskRef_set_);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSetTaskRef()
  {
    return taskRef_set_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Sequence getAnyAttribute()
  {
    if (anyAttribute == null)
    {
      anyAttribute = createSequence(INTERNAL_ANY_ATTRIBUTE);
    }
    return anyAttribute;
  }
  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ChangeContext inverseRemove(Object otherEnd, int propertyIndex, ChangeContext changeContext)
  {
    switch (propertyIndex)
    {
      case ANY:
        return removeFromSequence(getAny(), otherEnd, changeContext);
      case ANY_ATTRIBUTE:
        return removeFromSequence(getAnyAttribute(), otherEnd, changeContext);
    }
    return super.inverseRemove(otherEnd, propertyIndex, changeContext);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Object get(int propertyIndex, boolean resolve)
  {
    switch (propertyIndex)
    {
      case ANY:
        // XXX query introduce coreType as an argument? -- semantic = if true -- coreType - return the core EMF object if value is a non-EMF wrapper/view
        //if (coreType) 
        return getAny();
      case TASK_REF:
        return getTaskRef();
      case ANY_ATTRIBUTE:
        // XXX query introduce coreType as an argument? -- semantic = if true -- coreType - return the core EMF object if value is a non-EMF wrapper/view
        //if (coreType) 
        return getAnyAttribute();
    }
    return super.get(propertyIndex, resolve);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void set(int propertyIndex, Object newValue)
  {
    switch (propertyIndex)
    {
      case ANY:
      	setSequence(getAny(), newValue);
        return;
      case TASK_REF:
        setTaskRef((String)newValue);
        return;
      case ANY_ATTRIBUTE:
      	setSequence(getAnyAttribute(), newValue);
        return;
    }
    super.set(propertyIndex, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void unset(int propertyIndex)
  {
    switch (propertyIndex)
    {
      case ANY:
        unsetSequence(getAny());
        return;
      case TASK_REF:
        unsetTaskRef();
        return;
      case ANY_ATTRIBUTE:
        unsetSequence(getAnyAttribute());
        return;
    }
    super.unset(propertyIndex);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public boolean isSet(int propertyIndex)
  {
    switch (propertyIndex)
    {
      case ANY:
        return any != null && !isSequenceEmpty(getAny());
      case TASK_REF:
        return isSetTaskRef();
      case ANY_ATTRIBUTE:
        return anyAttribute != null && !isSequenceEmpty(getAnyAttribute());
    }
    return super.isSet(propertyIndex);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String toString()
  {
    if (isProxy(this)) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (any: ");
    result.append(any);
    result.append(", TaskRef: ");
    if (taskRef_set_) result.append(taskRef); else result.append("<unset>");
    result.append(", anyAttribute: ");
    result.append(anyAttribute);
    result.append(')');
    return result.toString();
  }

} //TaskReferenceImpl
