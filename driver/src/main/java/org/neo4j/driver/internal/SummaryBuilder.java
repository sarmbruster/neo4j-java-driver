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
package org.neo4j.driver.internal;

import org.neo4j.driver.Plan;
import org.neo4j.driver.ProfiledPlan;
import org.neo4j.driver.ResultSummary;
import org.neo4j.driver.Statement;
import org.neo4j.driver.StatementType;
import org.neo4j.driver.UpdateStatistics;
import org.neo4j.driver.Value;
import org.neo4j.driver.exceptions.ClientException;
import org.neo4j.driver.internal.spi.StreamCollector;

import static org.neo4j.driver.internal.SimpleUpdateStatistics.EMPTY_STATS;

public class SummaryBuilder implements StreamCollector
{
    private final Statement statement;

    private StatementType type = null;
    private UpdateStatistics statistics = null;
    private Plan plan = null;
    private ProfiledPlan profile;

    public SummaryBuilder( Statement statement )
    {
        this.statement = statement;
    }

    @Override
    public void fieldNames( String[] names )
    {
        // intentionally empty
    }

    @Override
    public void record( Value[] fields )
    {
        // intentionally empty
    }

    public void statementType( StatementType type )
    {
        if ( this.type == null )
        {
            this.type = type;
        }
        else
        {
            throw new ClientException( "Received statement type twice" );
        }
    }

    public void statementStatistics( UpdateStatistics statistics )
    {
        if ( this.statistics == null )
        {
            this.statistics = statistics;
        }
        else
        {
            throw new ClientException( "Received statement statistics twice" );
        }
    }

    @Override
    public void plan( Plan plan )
    {
        if ( this.plan == null )
        {
            this.plan = plan;
        }
        else
        {
            throw new ClientException( "Received plan twice" );
        }
    }

    @Override
    public void profile( ProfiledPlan plan )
    {
        if ( this.plan == null && this.profile == null )
        {
            this.profile = plan;
            this.plan = plan;
        }
        else
        {
            throw new ClientException( "Received plan twice" );
        }
    }

    public ResultSummary build()
    {
        return new ResultSummary()
        {
            @Override
            public Statement statement()
            {
                return statement;
            }

            @Override
            public UpdateStatistics updateStatistics()
            {
                return statistics == null ? EMPTY_STATS : statistics;
            }

            @Override
            public StatementType statementType()
            {
                return type;
            }

            @Override
            public boolean hasProfile()
            {
                return profile != null;
            }

            @Override
            public boolean hasPlan()
            {
                return plan != null;
            }

            @Override
            public Plan plan()
            {
                return plan;
            }

            @Override
            public ProfiledPlan profile()
            {
                return profile;
            }
        };
    }
}
