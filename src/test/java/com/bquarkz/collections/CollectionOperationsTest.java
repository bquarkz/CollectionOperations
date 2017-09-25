/**
 * @author "Nilton Constantino" <nilton@gmail.com>
 *
 */
package com.bquarkz.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;

public class CollectionOperationsTest
{
    private static final Integer[] setAUnique = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
    private static final Integer[] setBUnique = new Integer[] { 6, 7, 8, 9, 10, 13 };
    
    private static final Integer[] setANotUnique = new Integer[] { 1, 2, 2, 3, 3 };
    private static final Integer[] setBNotUnique = new Integer[] { 3, 3, 5 };
    
    private static final Integer[] setANI = new Integer[] { 1, 2, 3 };
    private static final Integer[] setBNI = new Integer[] { 4, 5, 6  };

    @Test
    public void testIntersectionNI()
    {
        List< Integer > listA = Arrays.asList( setANI );
        List< Integer > listB = Arrays.asList( setBNI );
        
        Collection< Integer > intersection = CollectionOperations.intersection( listA, listB );
        Assert.assertTrue( intersection.size() == 0 );
    }
    
    @Test
    public void testdistinctUnionNI()
    {
        List< Integer > listA = Arrays.asList( setANI );
        List< Integer > listB = Arrays.asList( setBNI );
        
        Collection< Integer > union = CollectionOperations.distinctUnion( listA, listB );
        Assert.assertTrue( union.size() == 6 );
    }
    
    @Test
    public void testDifferenceNI()
    {
        List< Integer > listA = Arrays.asList( setANI );
        List< Integer > listB = Arrays.asList( setBNI );
        
        Collection< Integer > difference = CollectionOperations.difference( listA, listB );
        Assert.assertTrue( difference.size() == 3 );
    }

    @Test
    public void testIntersectionWithUniqueElements()
    {
        List< Integer > listA = Arrays.asList( setAUnique );
        List< Integer > listB = Arrays.asList( setBUnique );
        
        Collection< Integer > intersection = CollectionOperations.intersection( listA, listB );
        Assert.assertTrue( intersection.size() == 5 );
    }
    
    @Test
    public void testIntersectionWithNotUniqueElements()
    {
        List< Integer > listA = Arrays.asList( setANotUnique );
        List< Integer > listB = Arrays.asList( setBNotUnique );
        
        Collection< Integer > intersection = CollectionOperations.intersection( listA, listB );
        Assert.assertTrue( intersection.size() == 2 );
    }
    
    @Test
    public void testDifferenceWithUniqueElements()
    {
        List< Integer > listA = Arrays.asList( setAUnique );
        List< Integer > listB = Arrays.asList( setBUnique );
        
        Collection< Integer > difference = CollectionOperations.difference( listA, listB );
        Assert.assertTrue( difference.size() == 7 );
    }
    
    @Test
    public void testDifferenceWithNotUniqueElements()
    {
        List< Integer > listA = Arrays.asList( setANotUnique );
        List< Integer > listB = Arrays.asList( setBNotUnique );
        
        Collection< Integer > difference = CollectionOperations.difference( listA, listB );
        Assert.assertTrue( difference.size() == 3 );
    }
    
    @Test
    public void testDistinctUnionWithUniqueElements()
    {
        List< Integer > listA = Arrays.asList( setAUnique );
        List< Integer > listB = Arrays.asList( setBUnique );
        
        Collection< Integer > union = CollectionOperations.distinctUnion( listA, listB );
        Assert.assertTrue( union.size() == 13 );
    }
    
    @Test
    public void testDistinctUnionWithNotUniqueElements()
    {
        List< Integer > listA = Arrays.asList( setANotUnique );
        List< Integer > listB = Arrays.asList( setBNotUnique );
        
        Collection< Integer > union = CollectionOperations.distinctUnion( listA, listB );
        Assert.assertTrue( union.size() == 6 );
    }
    
    @Test
    public void testDistinctUnionForLargerNumberOfStatements()
    {
        final int N = 50_000;
        final int N2 = N / 2;
        
        List< Integer > list1 = new ArrayList<>( N );
        List< Integer > list2 = new ArrayList<>( N );
        
        for( int i = 0; i < N; i++ )
        {
            list1.add( i );
            list2.add( N2 + i );
        }
        
        long t0 = System.currentTimeMillis();
        Collection< Integer > union = CollectionOperations.distinctUnion( list1, list2 );
        long tf = System.currentTimeMillis();
        long total = tf - t0;
        Assert.assertTrue( union.size() == ( N + N2 ) );
        Assert.assertTrue( total < 1500 );
    }
    
    private static class TestClassForGetIds implements IdentificationReduction< Integer >
    {
        private int id;
        
        public TestClassForGetIds( int id )
        {
            this.id = id;
        }

        @Override
        public Integer getIdentification()
        {
            return id;
        }
    }
    
    @Test
    public void testGetIds()
    {
        List< TestClassForGetIds > list = new LinkedList<>();
        
        int N = 5;
        
        for( int i = 0; i  < N; i++ )
        {
            list.add( new TestClassForGetIds( i ) );
        }
        
        List< Integer > ids = CollectionOperations.createIdListFrom( list );
        
        // keep the same order
        for( int i = 0; i < N; i++ )
        {
            Assert.assertTrue( ids.get( i ) == i );
        }
    }
    
    @Test
    public void testGetIdsFromSubject()
    {
        List< TestClassForGetIds > list = new LinkedList<>();
        
        int N = 5;
        
        for( int i = 0; i  < N; i++ )
        {
            list.add( new TestClassForGetIds( i ) );
        }
        
        List< Integer > ids = CollectionOperations.createListFromAccordingTo( list, p -> p.getIdentification() );
        
        for( int i = 0; i < N; i++ )
        {
            Assert.assertTrue( ids.get( i ) == i );
        }
    }
    
    private static class Dummy
    {
        Integer id;
        
        Dummy( Integer id )
        {
            this.id = id;
        }
    }
    
    @Test
    public void testFirstOptionOK()
    {
        Dummy dummy0 = new Dummy( 0 );
        Dummy dummy1 = new Dummy( 1 );
        Dummy dummy2 = new Dummy( 2 );
        Dummy dummy3 = new Dummy( 3 );
        Dummy dummy4 = new Dummy( 1 );
        
        Dummy[] array = new Dummy[] {  dummy0, dummy1, dummy2, dummy3, dummy4 };
        
        Optional< Dummy > optional = CollectionOperations.findFirstOptionIntoWith( Arrays.asList( array ), d -> d.id == dummy1.id );
        Assert.assertTrue( optional.isPresent() );
        Dummy dummyR = optional.get();
        Assert.assertNotNull( dummyR );
        Assert.assertTrue( dummyR.id == dummy1.id );
        Assert.assertTrue( dummyR == dummy1 );
    }
    
    @Test( expected = NoSuchElementException.class )
    public void testFirstOptionNotOK()
    {
        Dummy dummy0 = new Dummy( 0 );
        Dummy dummy1 = new Dummy( 1 );
        Dummy dummy2 = new Dummy( 2 );
        Dummy dummy3 = new Dummy( 3 );
        Dummy dummy4 = new Dummy( 1 );
        
        Dummy[] array = new Dummy[] {  dummy0, dummy1, dummy2, dummy3, dummy4 };
        
        Optional< Dummy > optional = CollectionOperations.findFirstOptionIntoWith( Arrays.asList( array ), d -> d.id == 2017 );
        Assert.assertFalse( optional.isPresent() );
        optional.get();
    }
    
    @Test
    public void testFindIntoWithOK()
    {
        Dummy dummy0 = new Dummy( 0 );
        Dummy dummy1 = new Dummy( 1 );
        Dummy dummy2 = new Dummy( 2 );
        Dummy dummy3 = new Dummy( 3 );
        Dummy dummy4 = new Dummy( 1 );
        
        Dummy[] array = new Dummy[] {  dummy0, dummy1, dummy2, dummy3, dummy4 };
        
        Optional< List< Dummy > > optional = CollectionOperations.findIntoWith( Arrays.asList( array ), d -> d.id == 1 );
        Assert.assertTrue( optional.isPresent() );
        List< Dummy > dummyL = optional.get();
        Assert.assertNotNull( dummyL );
        Assert.assertTrue( dummyL.size() == 2 );
        Assert.assertTrue( dummyL.get( 0 ).id == 1 );
        Assert.assertTrue( dummyL.get( 1 ).id == 1 );
    }
    
    @Test( expected = NoSuchElementException.class )
    public void testFindIntoWithNotOK()
    {
        Dummy dummy0 = new Dummy( 0 );
        Dummy dummy1 = new Dummy( 1 );
        Dummy dummy2 = new Dummy( 2 );
        Dummy dummy3 = new Dummy( 3 );
        Dummy dummy4 = new Dummy( 1 );
        
        Dummy[] array = new Dummy[] {  dummy0, dummy1, dummy2, dummy3, dummy4 };
        
        Optional< List< Dummy > > optional = CollectionOperations.findIntoWith( Arrays.asList( array ), d -> d.id == 2017 );
        Assert.assertFalse( optional.isPresent() );
        optional.get();
    }
}
