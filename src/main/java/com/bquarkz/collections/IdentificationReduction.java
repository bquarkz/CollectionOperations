/**
 * Copyright (C) 2017 - Xetec - Flexmaint
 *
 * @author "Nilton Constantino" <nilton@xetec.com>
 *
 * Everything about the respective software copyright can be found in the
 * "LICENSE" file included in the project source tree.
 *
 */
package com.xetec.utilitybelt.collections;

public interface IdentificationReduction< ID_TYPE >
{
    /**
     * just to reduce the object to an identification (id)
     * @return
     */
    ID_TYPE getId();
}
