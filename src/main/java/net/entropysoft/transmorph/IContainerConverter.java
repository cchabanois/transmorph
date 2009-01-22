package net.entropysoft.transmorph;

/**
 * <p>
 * Some converters needs to convert elements from the source object to elements
 * of the destination object. This is the case for converters handling
 * collections, arrays, or beans.
 * </p>
 * <p>
 * If the element converter is not set by the user, it will be set by
 * {@link Converter} constructor to the created Converter
 * </p>
 * <p>
 * If set, the element converter allows you to use different converters to
 * convert elements than the converters used for converting the containing
 * object.
 * </p>
 * 
 * @author Cedric Chabanois (cchabanois at gmail.com)
 * 
 */
public interface IContainerConverter extends IConverter {

	/**
	 * Set the element converter.
	 * 
	 * @param elementConverter
	 */
	public void setElementConverter(IConverter elementConverter);

	/**
	 * Get the element converter.
	 * 
	 * @return
	 */
	public IConverter getElementConverter();

}
