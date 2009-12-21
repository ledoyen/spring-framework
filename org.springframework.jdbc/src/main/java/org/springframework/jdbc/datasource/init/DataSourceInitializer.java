/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.jdbc.datasource.init;

import javax.sql.DataSource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Used to populate a database during initialization.
 * @author Dave Syer
 * @since 3.0
 * @see DatabasePopulator
 */
public class DataSourceInitializer implements InitializingBean {

	private DataSource dataSource;

	private DatabasePopulator databasePopulator;

	private boolean enabled = true;

	/**
	 * Flag to explicitly enable or disable the database populator.
	 * @param enabled true if the database populator will be called on startup
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * The {@link DatabasePopulator} to use to populate the data source.  Mandatory with no default.
	 * @param databasePopulator the database populator to use.
	 */
	public void setDatabasePopulator(DatabasePopulator databasePopulator) {
		this.databasePopulator = databasePopulator;
	}

	/**
	 * The {@link DataSource} to populate when this component is initialized.  Mandatory with no default.
	 * @param dataSource the DataSource
	 */
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Use the populator to set up data in the data source.  Both properties are mandatory with no defaults. 
	 * @see InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		if (enabled) {
			Assert.state(dataSource != null, "DataSource must be provided");
			Assert.state(databasePopulator != null, "DatabasePopulator must be provided");
			databasePopulator.populate(dataSource.getConnection());
		}
	}
}