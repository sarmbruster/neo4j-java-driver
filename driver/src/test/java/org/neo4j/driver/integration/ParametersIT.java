/**
 * Copyright (c) 2002-2015 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.neo4j.driver.integration;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.ClientException;
import org.neo4j.driver.util.TestNeo4jSession;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.neo4j.driver.Values.parameters;

public class ParametersIT
{
    @Rule
    public TestNeo4jSession session = new TestNeo4jSession();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldBeAbleToSetAndReturnBooleanProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", true ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isBoolean(), equalTo( true ) );
            assertThat( value.javaBoolean(), equalTo( true ) );
        }
    }

    @Test
    public void shouldBeAbleToSetAndReturnByteProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", (byte) 1 ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isInteger(), equalTo( true ) );
            assertThat( value.javaLong(), equalTo( 1L ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnShortProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", (short) 1 ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isInteger(), equalTo( true ) );
            assertThat( value.javaLong(), equalTo( 1L ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnIntegerProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", 1 ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isInteger(), equalTo( true ) );
            assertThat( value.javaLong(), equalTo( 1L ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnLongProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", 1L ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isInteger(), equalTo( true ) );
            assertThat( value.javaLong(), equalTo( 1L ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnDoubleProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", 6.28 ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isFloat(), equalTo( true ) );
            assertThat( value.javaDouble(), equalTo( 6.28 ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnCharacterProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", 'ö' ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isText(), equalTo( true ) );
            assertThat( value.javaString(), equalTo( "ö" ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnCharacterArrayProperty()
    {
        // When
        char[] arrayValue = new char[]{'M', 'j', 'ö', 'l', 'n', 'i', 'r'};
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", arrayValue ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isText(), equalTo( true ) );
            assertThat( value.javaString(), equalTo( "Mjölnir" ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnStringProperty()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", "Mjölnir" ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isText(), equalTo( true ) );
            assertThat( value.javaString(), equalTo( "Mjölnir" ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnBooleanArrayProperty()
    {
        // When
        boolean[] arrayValue = new boolean[]{true, true, true};
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", arrayValue ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isList(), equalTo( true ) );
            assertThat( value.size(), equalTo( 3L ) );
            for ( Value item : value )
            {
                assertThat( item.isBoolean(), equalTo( true ) );
                assertThat( item.javaBoolean(), equalTo( true ) );
            }
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnIntegerArrayProperty()
    {
        // When
        int[] arrayValue = new int[]{42, 42, 42};
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", arrayValue ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isList(), equalTo( true ) );
            assertThat( value.size(), equalTo( 3L ) );
            for ( Value item : value )
            {
                assertThat( item.isInteger(), equalTo( true ) );
                assertThat( item.javaLong(), equalTo( 42L ) );
            }
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnDoubleArrayProperty()
    {
        // When
        double[] arrayValue = new double[]{6.28, 6.28, 6.28};
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", arrayValue ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isList(), equalTo( true ) );
            assertThat( value.size(), equalTo( 3L ) );
            for ( Value item : value )
            {
                assertThat( item.isFloat(), equalTo( true ) );
                assertThat( item.javaDouble(), equalTo( 6.28 ) );
            }
        }
    }

    @Test
    public void shouldBeAbleToSetAndReturnSpecialStringArrayProperty()
    {
        // When
        String[] arrayValue = new String[]{"Mjölnir", "Mjölnir", "Mjölnir"};

        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", arrayValue ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isList(), equalTo( true ) );
            assertThat( value.size(), equalTo( 3L ) );
            for ( Value item : value )
            {
                assertThat( item.isText(), equalTo( true ) );
                assertThat( item.javaString(), equalTo( "Mjölnir" ) );
            }
        }
    }

    @Test
    public void shouldBeAbleToSetAndReturnStringArrayProperty()
    {
        // When
        String[] arrayValue = new String[]{"cat", "cat", "cat"};
        Result result = session.run(
                "CREATE (a {value:{value}}) RETURN a.value", parameters( "value", arrayValue ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isList(), equalTo( true ) );
            assertThat( value.size(), equalTo( 3L ) );
            for ( Value item : value )
            {
                assertThat( item.isText(), equalTo( true ) );
                assertThat( item.javaString(), equalTo( "cat" ) );
            }
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnBooleanPropertyWithinMap()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}.v}) RETURN a.value",
                parameters( "value", parameters( "v", true ) ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isBoolean(), equalTo( true ) );
            assertThat( value.javaBoolean(), equalTo( true ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnIntegerPropertyWithinMap()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}.v}) RETURN a.value",
                parameters( "value", parameters( "v", 42 ) ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isInteger(), equalTo( true ) );
            assertThat( value.javaLong(), equalTo( 42L ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnDoublePropertyWithinMap()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}.v}) RETURN a.value",
                parameters( "value", parameters( "v", 6.28 ) ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isFloat(), equalTo( true ) );
            assertThat( value.javaDouble(), equalTo( 6.28 ) );
        }

    }

    @Test
    public void shouldBeAbleToSetAndReturnStringPropertyWithinMap()
    {
        // When
        Result result = session.run(
                "CREATE (a {value:{value}.v}) RETURN a.value",
                parameters( "value", parameters( "v", "Mjölnir" ) ) );

        // Then
        for ( Record record : result.retain() )
        {
            Value value = record.get( "a.value" );
            assertThat( value.isText(), equalTo( true ) );
            assertThat( value.javaString(), equalTo( "Mjölnir" ) );
        }
    }

    @Test
    public void settingInvalidParameterTypeShouldThrowHelpfulError() throws Throwable
    {
        // Expect
        exception.expect( ClientException.class );
        exception.expectMessage( "Unable to convert java.lang.Object to Neo4j Value." );

        // When
        session.run( "anything", parameters( "k", new Object() ) );
    }
}
