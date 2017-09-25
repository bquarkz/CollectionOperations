/**
 * @author "Nilton Constantino" <nilton@gmail.com>
 *
 */
package com.bquarkz.collections;

public interface IdentificationReduction< ID_TYPE >
{
    /**
     * just to reduce the object to an identification (id)
     * @return
     */
    ID_TYPE getIdentification(); // getId
}
