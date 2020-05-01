package br.com.caelum;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class JpaConfigurator {

	@Bean(destroyMethod = "close")//Esse atributo define o m�todo (close) do Pool que o Spring chama quando o Tomcat � desligado. Assim garantimos que todas as conex�es ser�o fechadas corretamente.
	public DataSource getDataSource() throws PropertyVetoException {
		
		ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
		comboPooledDataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
		comboPooledDataSource.setUser("root");
		comboPooledDataSource.setPassword("");
		comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost/projeto_jpa?useTimezone=true&amp&serverTimezone=UTC");
		comboPooledDataSource.setMinPoolSize(5); //conex�es que ser�o criadas de antem�o
		comboPooledDataSource.setMaxPoolSize(6); //nunca passar� de 10, caso passe ficar� esperando a libera��o de uma connection
		comboPooledDataSource.setNumHelperThreads(5);
		comboPooledDataSource.setIdleConnectionTestPeriod(1); //a cada um segundo testamos as conex�es ociosas, assim caso o banco caia nao pegara uma conection quebrada
		return comboPooledDataSource;
		
		
//		Esse dataSource nao � recomendado pois cria uma conex�o a cada requisi��o
//	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//	    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//	    dataSource.setUrl("jdbc:mysql://localhost/projeto_jpa?useTimezone=true&amp&serverTimezone=UTC");
//	    dataSource.setUsername("root");
//	    dataSource.setPassword("");
//
//	    return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();

		entityManagerFactory.setPackagesToScan("br.com.caelum");
		entityManagerFactory.setDataSource(dataSource);

		entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

		Properties props = new Properties();

		props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5InnoDBDialect");
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.hbm2ddl.auto", "create-drop");
		//usando cache de segundo n�vel
		props.setProperty("hibernate.cache.use_second_level_cache", "true");
		//provider
		props.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.ehcache.SingletonEhCacheRegionFactory");
		//enabled chache queries
        props.setProperty("hibernate.cache.use_query_cache", "true");
        //enabled Hibernate Statistics
        props.setProperty("hibernate.generate_statistics", "true");
		

		entityManagerFactory.setJpaProperties(props);
		return entityManagerFactory;
	}
	
	@Bean
	public Statistics statistics(EntityManagerFactory emf) { 
	    return emf.unwrap(SessionFactory.class).getStatistics();
	}

	@Bean
	public JpaTransactionManager getTransactionManager(EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);

		return transactionManager;
	}

}
