package com.jpaproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class JpaProjectApplication {
	public static void main(String[] args) {
		SpringApplication.run(JpaProjectApplication.class, args);
	}
}
/*
public class JpaProjectApplication implements CommandLineRunner {
	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	public static final EntityManagerFactory fectory = Persistence.createEntityManagerFactory("sql-server-unit");
	public static final EntityManager em = fectory.createEntityManager();

	public static void main(String[] args) {
		SpringApplication.run(JpaProjectApplication.class, args);

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class); // Change to Object[] to hold grouped results
		Root<Employee> employeeRoot = cq.from(Employee.class);

// Join Employee with Department
		Join<Employee, Department> departmentJoin = employeeRoot.join("department");

// Join Employee with Projects (ManyToMany)
		Join<Employee, Project> projectJoin = employeeRoot.join("projects");

// Join Project with Task (OneToMany)
		Join<Project, Task> taskJoin = projectJoin.join("tasks");

// Add where conditions if needed (optional)
		Predicate departmentNameCondition = cb.equal(departmentJoin.get("name"), "IT");
		Predicate taskDescriptionCondition = cb.like(taskJoin.get("description"), "%frontend%");

// Combine predicates using `cb.and`
		cq.where(cb.or(departmentNameCondition, taskDescriptionCondition));

// Group by employee name
		cq.groupBy(employeeRoot.get("name"));

// Select employee name and count of projects (or any other aggregate function)
		cq.select(cb.array(employeeRoot.get("name"), cb.count(projectJoin)));

// Add HAVING clause to filter groups (e.g., having more than 2 projects)
		Predicate havingCondition = cb.gt(cb.count(projectJoin), 2); // Change the number as needed
		cq.having(havingCondition);

// You can also set ordering (optional)
		cq.orderBy(cb.asc(employeeRoot.get("name")));

// Execute query
		TypedQuery<Object[]> query = em.createQuery(cq);
		List<Object[]> results = query.getResultList();

// Print results
		for (Object[] result : results) {
			String employeeName = (String) result[0];
			Long projectCount = (Long) result[1]; // Get the count of projects for the employee
			System.out.println(employeeName + " is associated with " + projectCount + " project(s).");
		}



	}

	@Override
	public void run(String... args) throws Exception {
		// Insert Departments
		Department itDept = new Department();
		itDept.setName("IT");

		Department hrDept = new Department();
		hrDept.setName("HR");

		Department financeDept = new Department();
		financeDept.setName("Finance");

		departmentRepository.save(itDept);
		departmentRepository.save(hrDept);
		departmentRepository.save(financeDept);

		// Insert Employees
		Employee johnDoe = new Employee();
		johnDoe.setName("John Doe");
		johnDoe.setDepartment(itDept);

		Employee janeSmith = new Employee();
		janeSmith.setName("Jane Smith");
		janeSmith.setDepartment(hrDept);

		Employee michaelJohnson = new Employee();
		michaelJohnson.setName("Michael Johnson");
		michaelJohnson.setDepartment(financeDept);

		employeeRepository.save(johnDoe);
		employeeRepository.save(janeSmith);
		employeeRepository.save(michaelJohnson);

		// Insert Projects
		Project emsProject = new Project();
		emsProject.setName("Employee Management System");

		Project payrollProject = new Project();
		payrollProject.setName("Payroll System");

		projectRepository.save(emsProject);
		projectRepository.save(payrollProject);

		// Assign Employees to Projects
		johnDoe.getProjects().add(emsProject);
		janeSmith.getProjects().add(emsProject);
		michaelJohnson.getProjects().add(payrollProject);

		employeeRepository.save(johnDoe);
		employeeRepository.save(janeSmith);
		employeeRepository.save(michaelJohnson);

		// Insert Tasks
		Task frontendTask = new Task();
		frontendTask.setDescription("Build frontend for EMS");
		frontendTask.setProject(emsProject);

		Task payrollTask = new Task();
		payrollTask.setDescription("Setup payroll database");
		payrollTask.setProject(payrollProject);

		taskRepository.save(frontendTask);
		taskRepository.save(payrollTask);
	}

}


		ProductSearchDto filter = new ProductSearchDto(
				"HP",
				"LAPTOP",
				1000,
				66669,
				List.of("HP","DELL")
		);
		transactional((em)->{

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Student> cq = cb.createQuery(Student.class);
			Root<Student> root = cq.from(Student.class);

			cq.multiselect(root.get("name"));// show specific column only
			cq.where(cb.equal(root.get("name"),"mak"));// use where condition
			TypedQuery<Student> q = em.createQuery(cq);
			q.getResultStream().forEach(System.out::println);


			//  show specific column only
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<String> cq = cb.createQuery(String.class);
			Root<Student> root = cq.from(Student.class);

			cq.multiselect(root.get("name"));// show specific column only
			cq.where(cb.equal(root.get("name"),"mak"));// use where condition
			TypedQuery<String> q = em.createQuery(cq);
			q.getResultStream().forEach(System.out::println);


			//  show multiple column only
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
			Root<Student> root = cq.from(Student.class);

			cq.multiselect(root.get("name"),root.get("cgpa"));// show specific column only
			cq.where(cb.equal(root.get("name"),"mak"));// use where condition
			TypedQuery<Object[]> q = em.createQuery(cq);
			q.getResultStream().forEach(a-> {
				System.out.println(a[0] + ", 	" + a[1]);
			});

			// Resolve N+1 problem

			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Student> cq = cb.createQuery(Student.class);
			Root<Student> root = cq.from(Student.class);

			root.fetch("courses");// if not use this method N+1 problem occurs
			TypedQuery<Student> q = em.createQuery(cq);
			q.getResultStream().forEach(System.out::println);



			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Product> cq = cb.createQuery(Product.class);
			Root<Product> root = cq.from(Product.class);

			List<Predicate> list = new ArrayList<>();

			if(filter.search() != null && !filter.search().isBlank()){
				var sv = filter.search().trim();
				var namePredicate = cb.like(root.get("name"),"%"+sv+"%");
				var description = cb.like(root.get("description"),"%"+sv+"%");

				list.add(cb.or(namePredicate,description));
			}

			if(filter.minPrice() <= filter.maxPrice()){
				var minPrice = cb.greaterThanOrEqualTo(root.get("price"),filter.minPrice());
				var maxPrice = cb.lessThanOrEqualTo(root.get("price"),filter.maxPrice());
				list.add(cb.and(maxPrice,maxPrice));
			}

			if(filter.category() != null){
				list.add(cb.equal(root.get("category"),filter.category()));
			}

			if(filter.brands() != null && !filter.brands().isEmpty()){
				list.add(root.get("brand").in(filter.brands()));
			}

			cq.orderBy(cb.desc(root.get("id")),cb.desc(root.get("name")));
			cq.where(list.toArray(new Predicate[0]));
			var q = em.createQuery(cq);
			q.getResultStream().forEach(System.out::println);
		});

	}

	public static void transactional(Consumer<EntityManager> consumer){
		em.getTransaction().begin();// before advice
		try{
			consumer.accept(em);
		}catch (Exception e){
			em.getTransaction().rollback();// after throwing
			throw new RuntimeException("Can't save the object");
		}
		em.getTransaction().commit();// after advice

	}

}

/*

1. how to manage cache spring data jpa?
2. hikari poll kivabe connection rakhe?
3. spring data jpa how to close EntityManager or flush this?
4. jokhon amra 1st time entity get kori find use kore cache e rakhe. ei cache er scope koto toko thakbe?
5. amra student class er config korchi amra all class er jonno config korte cai how to manage this



	public static void main(String[] args) {
		SpringApplication.run(JpaProjectApplication.class, args);

		transactional((em)->{

			Student student = new Student();
			student.setCgpa(44.4);
			student.setName("check");

			em.persist(student);

			//student.setName("mak");


			Student student = new Student();
			student.setCgpa(44.4);
			student.setName("check");

			em.persist(student);

			student.setName("mak");


			var stu = em.find(Student.class,1);// select operation find the result by id
			stu.setName("hridoy");
			em.detach(stu);// remove this entity from persistence context

			em.merge(stu); when merge find this entity exits persistence context or not.
			 if not found call db find this id if found in db update other wise insert


			stu.setName("hello");
			run update query when commit transaction.
			when try to update in this time all column try to update. this is problem.
			in this problem solve hibernate. if use our entity class @DynamicUpdate annotation this column update only update effect in db.



			var s = em.find(Student.class,1);
			var stu = em.getReference(Student.class,1);
			System.out.println(stu);
			var s = em.find(Student.class,1);
			var s2 = em.find(Student.class,2);
			var s3 = em.find(Student.class,1);



//		Query query = em.createQuery("select s from Student s where s.id=:stu_id");
//		query.setParameter("stu_id",1);

//		TypedQuery<Student> query = em.createQuery("select s from Student s where s.id=:stu_id",Student.class);
//		query.setParameter("stu_id",1);
//		Student student1 = query.getSingleResult();
//			System.out.println(student1);

//			TypedQuery<Student> query = em.createQuery("select s from Student s join fetch s.courses ",Student.class);
//
//
//
//			query.setFirstResult(2);
//			query.setMaxResults(2);
//			query.getResultList().forEach(System.out::println);
/*
			var g = em.createEntityGraph("studentWithCourse");
			em.createQuery("select s from Student s",Student.class)
					.setHint(QueryHints.JAKARTA_HINT_FETCHGRAPH,g)
					.getResultList();


	var g = em.createEntityGraph(Student.class);
			g.addAttributeNodes("courses");
					//var g = em.createEntityGraph("studentWithCourse");
					em.createQuery("select s from Student s",Student.class)
		.setHint(QueryHints.JAKARTA_HINT_FETCHGRAPH,g)
		.getResultList();



		});



		}

*/

