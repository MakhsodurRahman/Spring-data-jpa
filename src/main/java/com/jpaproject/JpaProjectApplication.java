package com.jpaproject;

import com.jpaproject.dto.InvoiceDescItem;
import com.jpaproject.dto.InvoiceInfo;
import com.jpaproject.entity.*;
import com.jpaproject.repository.AppUserRepository;
import com.jpaproject.repository.OrderRepository;
import com.jpaproject.repository.SqlServerStudentRepository;
import com.jpaproject.repository.impl.ProductRepo;
import com.jpaproject.specification.ProductSpecification;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.*;
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
@EnableAsync
@SpringBootApplication(
		exclude = {
				DataSourceAutoConfiguration.class,
				HibernateJpaAutoConfiguration.class,
				TransactionAutoConfiguration.class,
				JpaRepositoriesAutoConfiguration.class
		}
)

public class JpaProjectApplication implements CommandLineRunner {

	@Autowired
	private SqlServerStudentRepository repository;

	@Autowired
	private ProductRepo productRepo;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender mailSender;
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
	//@Transactional
	public void run(String... args) throws Exception {
//		List<Student> findAll = repository.findAll();
//		findAll.forEach(System.out::println);

//		var s = new Student("asdkfjasdkjf",4.44);
//		repository.save(s);
//
//		List<Product> products = new ArrayList<>();
//		Random random = new Random();
//		for (int i = 0; i < 1000; i++) {
//			Product product = new Product();
//			product.setName("Product " + i);
//			product.setPrice((long) (random.nextInt(1000) + 100)); // Random price between 100 and 1100
//			products.add(product);
//		}
//
//		productRepo.saveAll(products);
//
//		productRepo.findAllByNameLike("Phone")
//				.and(productRepo.findAllByCategory_name("I_PHONE"))
//				.forEach(System.out::println);

//		try(var stream = productRepo.findAllByCategory_name("I_PHONE")) {
//			stream.skip(0)
//					.forEach(System.out::println);
//		}
/*
		Sort.TypedSort<Product> typedSort = (Sort.TypedSort<Product>) Sort.sort(Product.class).by(Product::getId).ascending();
		Sort.TypedSort<Product> sort = Sort.sort(Product.class);
		Sort sort1 = sort.by(Product::getId).descending();
		Sort sort2 = Sort.by("id").descending().and(Sort.by("name")).descending();
		Sort sort3 = Sort.by(Sort.Direction.ASC,"id","name");
		Sort sort4 = Sort.by(Sort.Order.by("id"));

		Pageable pageable = PageRequest.of(2,10, Sort.Direction.ASC,"id");
		//Page<Product> page = productRepo.findAllByCategory_name("I_PHONE", pageable);
		//System.out.println(page);


		// use limit sort individual
		Limit limit = Limit.of(10);
		List<Product> products = productRepo.findAllByCategory_name("I_PHONE",limit,sort2);

		//var position = ScrollPosition.offset();
		//
		// var position = ScrollPosition.offset(10);// use this
		//var position = ScrollPosition.keyset();// if use this working on primary key
		var position = ScrollPosition.forward(Map.of("id","24","price","1000"));
		var sort = Sort.by(Sort.Direction.ASC,"price");
		var window = productRepo.findProductsByCategory_NameInOrderById(List.of("I_PHONE","S_PHONE"),position,Limit.of(10),sort);
		window.forEach(System.out::println);

		System.out.println("---------------------------------------------");
		var window2 = productRepo.findProductsByCategory_NameInOrderById(List.of("I_PHONE","S_PHONE"),window.positionAt(window.size() - 1),Limit.of(10),sort);
		window2.forEach(System.out::println);



		List<Object[]> results = productRepo.findProductNameAndPriceById(1);
		var list = productRepo.findProductsByCategory_Name("I_PHONE");
		list.forEach(objects -> System.out.println(Arrays.toString(objects)));
		var map = productRepo.findProductById(2);
		System.out.println(map.get("id") +" "+ map.get("name")+ map.get("categoryName"));



		var tuple = productRepo.findProductById(2);
		Long id = tuple.get("product_id",Long.class);
		String name = tuple.get("name",String.class);
		Long price = tuple.get(2,Long.class);
		System.out.println(id+"   -> "+name);

		var dto = productRepo.findProductById(2);
		System.out.println(dto);


		var page = productRepo.findProductsByCategoryName("I_PHONE",PageRequest.of(1,20));
		page.getContent().forEach(System.out::println);


 	var product = productRepo.findProductById(1);
	 product.setName("newName");
		System.out.println(product);


 *

		ProductNamePriceDto data = productRepo.findProductNameAndPriceById(1);
		System.out.println(data);


		var projection = productRepo.findProductById(1);
		System.out.println(projection.getCategory().getName());

		var c = productRepo.findCategoryById(1L);
		System.out.println(c);

		List<Product> id = productRepo.findAll((Specification<Product>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), 1));
		Optional<Product> productRepoOne = productRepo.findOne((Specification<Product>) (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), 1));
		System.out.println(id.get(0));
		System.out.println(productRepoOne);



		Specification<Product> specification = Specification.where(
				ProductSpecification.byCategoryName(Set.of("LAPTOP"))
						.and(ProductSpecification.byPriceRange(1000.00,116000.00))
		);

		Page<Product> productPage = productRepo.findAll(specification, PageRequest.of(1,20));
		productPage.forEach(System.out::println);


		Product exampleProduct = new Product();
		exampleProduct.setId(1L);

		Product product = productRepo.findOne(Example.of(exampleProduct)).get();
		List<Product> productList = productRepo.findAll(Example.of(exampleProduct),PageRequest.of(1,10)).getContent();
		System.out.println("product -> " 	+ product);

		Category category = new Category();
		category.setName("I_PHONE");

		Product product1 = new Product();
		product1.setId(10L);
		product1.setCategory(category);
		var example = Example.of(product1,ExampleMatcher.matchingAll().withIgnorePaths(Product_.NAME));
		/*
			ExampleMatcher.matchingAll().withIgnoreAll()-> AND operation
			.matchingAny() -> OR operation

			.

		 var list = productRepo.findAll(example);



		var appUser = new AppUser("mak","mak@gmail.com","dhaka");
		appUserRepository.save(appUser);

		var orders = new Orders(appUser,List.of(
				new OrderItem(new Product(1L),2),
				new OrderItem(new Product(2),20),
				new OrderItem(new Product(3),30)
		));

		orderRepository.save(orders);

		 */



	}


}



