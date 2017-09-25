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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CollectionOperations
{
    static < T > Collection< T > intersection( Collection< T > c1, Collection< T > c2 )
    {
        try( Stream< T > stream = c1.stream() ) {
            return stream.parallel()
                    .filter( element -> c2.contains( element ) )
                    .collect( Collectors.toList() );
        }
    }

    static < T, T1 extends T, T2 extends T > Collection< T1 > difference(
            Class< T > interfaceClass,
            Collection< T1 > c1,
            Collection< T2 > c2,
            Predicate< T > predicate
            )
    {
        try( Stream< T1 > stream = c1.stream() ) {
            return stream.parallel()
                    .filter( predicate )
                    .collect( Collectors.toList() );
        }
    }
    
    static < T > Collection< T > difference( Collection< T > c1, Collection< T > c2 )
    {
        try( Stream< T > stream = c1.stream() ) {
            return stream.parallel()
                    .filter( element -> !c2.contains( element ) )
                    .collect( Collectors.toList() );
        }
    }
    
    static < T > Collection< T > distinctUnion( Collection< T > c1, Collection< T > c2 )
    {
        Collection< T > intersection = intersection( c1, c2 );
        Collection< T > all = intersection;
        if( intersection.isEmpty() )
        {
            all.addAll( c1 );
            all.addAll( c2 );
        } else {
            all.addAll( difference( c1, intersection ) );
            all.addAll( difference( c2, intersection ) );
        }
        
        return all;
    }
    
    static < T, E > List< T > createListFromAccordingTo(
            Collection< E > list,
            Function< E, T > subject
            )
    {
        if( list == null ) return null;
        
        List< T > result = new ArrayList<>( list.size() );
        if( list.isEmpty() ) return result;
        
        try( Stream< E > stream = list.stream() )
        {
            return stream.parallel()
                .map( subject )
                .collect( Collectors.toList() );
        }
    }

    static < T, E extends IdentificationReduction< T > > List< T > createIdListFrom( Collection< E > list )
    {
        return createListFromAccordingTo( list, p -> p.getId() );
    }
    
    static < T, E extends IdentificationReduction< T > > List< T > createIdListFrom( E[] array )
    {
        if( array == null ) return null;
        
        return createIdListFrom( Arrays.asList( array ) );
    }
    
    static < T > Optional< T > findFirstOptionIntoWith( Collection< T > collection, Predicate< T > predicate )
    {
        try( Stream< T > stream = collection.stream() )
        {
            T result = stream.parallel().filter( predicate ).collect( firstOptionCollector() );
            return Optional.ofNullable( result );
        }
    }
    
    static < T > Optional< List< T > > findIntoWith( Collection< T > collection, Predicate< T > predicate )
    {
        try( Stream< T > stream = collection.stream() )
        {
            List< T > result = stream.parallel().filter( predicate ).collect( Collectors.toList() );
            return Optional.ofNullable( result == null || result.isEmpty() ? null : result );
        }
    }
    
    static < T > Collector< T, List< T >, T > firstOptionCollector()
    {
        Collector< T, List< T >, T > result = Collector.of( ArrayList::new, List::add,
            ( left, right ) -> {
                left.addAll( right );
                return left;
            },
        
            list -> {
                if( list.size() > 0 )
                {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        );
        
        return result;
    }
}
