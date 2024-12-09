package com.jpaproject;


import com.jpaproject.entity.Student;
import com.jpaproject.repository.SqlServerStudentRepository;
import com.jpaproject.repository.impl.ProductRepo;
import jakarta.persistence.*;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceProvider;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

class MakDataSource implements DataSource{

	@Override
	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(
				"jdbc:sqlserver://localhost:1433;databaseName=quiz_app;encrypt=true;trustServerCertificate=true;",
				"sa",
				"Makhsodur123"
		);
		return connection;
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return null;
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return null;
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {

	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {

	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}

class MakUnitInfo implements PersistenceUnitInfo {

	@Override
	public String getPersistenceUnitName() {
		return "default";
	}



	@Override
	public String getPersistenceProviderClassName() {
		return "org.hibernate.jpa.HibernatePersistenceProvider";
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		return PersistenceUnitTransactionType.RESOURCE_LOCAL;
	}

	@Override
	public DataSource getJtaDataSource() {
		return  null;
//		return DataSourceBuilder.create()
//				.url("")
//				.username("")
//				.password("")
//				.driverClassName("")
//				.build();

	}

	@Override
	public DataSource getNonJtaDataSource() {
		return new MakDataSource();
	}

	@Override
	public List<String> getMappingFileNames() {
		return null;
	}

	@Override
	public List<URL> getJarFileUrls() {
		return null;
	}

	@Override
	public URL getPersistenceUnitRootUrl() {
		return null;
	}

	@Override
	public List<String> getManagedClassNames() {
		return List.of(
				"com.jpaproject.entity.Student"
		);
	}

	@Override
	public boolean excludeUnlistedClasses() {
		return false;
	}

	@Override
	public SharedCacheMode getSharedCacheMode() {
		return null;
	}

	@Override
	public ValidationMode getValidationMode() {
		return null;
	}

	@Override
	public Properties getProperties() {
		Properties props = new Properties();
		props.setProperty("hibernate.hbm2ddl.auto","update");
		props.setProperty("hibernate.format_sql","true");
		props.setProperty("hibernate.show_sql","true");
		return props;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		return null;
	}

	@Override
	public void addTransformer(ClassTransformer classTransformer) {

	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		return null;
	}
}
@SpringBootApplication(
		exclude = {
				DataSourceAutoConfiguration.class,
				HibernateJpaAutoConfiguration.class,
				TransactionAutoConfiguration.class,
				JpaRepositoriesAutoConfiguration.class
		}
)
@Transactional(readOnly = true)
public class JpaProjectApplication implements CommandLineRunner {

	@Autowired
	private SqlServerStudentRepository repository;

	@Autowired
	private ProductRepo productRepo;
	public static void main(String[] args) {
		SpringApplication.run(JpaProjectApplication.class,args);

/*
		PersistenceUnitInfo info = new MakUnitInfo();
		PersistenceProvider provider = new HibernatePersistenceProvider();

		EntityManagerFactory factory = provider.createContainerEntityManagerFactory(info, Map.of());
		EntityManager em = factory.createEntityManager();

		em.getTransaction().begin();
		Student student = new Student("mak",3.44);
		em.persist(student);
		em.getTransaction().commit();



 */
	}

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		List<Student> findAll = repository.findAll();
		findAll.forEach(System.out::println);

		var s = new Student("asdkfjasdkjf",4.44);
		repository.save(s);
//
//		productRepo.findAllByNameLike("Phone")
//				.and(productRepo.findAllByCategory_name("I_PHONE"))
//				.forEach(System.out::println);

		try(var stream = productRepo.findAllByCategory_name("I_PHONE")) {
			stream.skip(0)
					.forEach(System.out::println);
		}
	}
}



