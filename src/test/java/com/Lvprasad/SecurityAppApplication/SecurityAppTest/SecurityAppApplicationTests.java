package com.Lvprasad.SecurityAppApplication.SecurityAppTest;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SecurityAppApplicationTests {

//	@Autowired
//	private EmployeeClient employeeClient;

	// @Test
	// @Order(3)
	// void getAllEmployeesTest() {
	// 	List<EmployeeDTO> employeeDTOList = employeeClient.getAllEmployees();
	// 	System.out.println(employeeDTOList);
	// }

	// @Test
	// @Order(2)
	// void getEmployeeByIdTest() {
	// 	EmployeeDTO employeeDTO = employeeClient.getEmployeeById(100L);
	// 	System.out.println(employeeDTO);
	// }

	// @Test
	// @Order(1)
	// void createNewEmployeeTest() {
	// 	EmployeeDTO employeeDTO = new EmployeeDTO(null, "Anuj", "anuj@gmail.com", 22,
	// 			"USER", 5000.0, LocalDate.of(2020, 12, 1), true);
	// 	EmployeeDTO savedEmployeeDTO = employeeClient.createNewEmployee(employeeDTO);
	// 	System.out.println(savedEmployeeDTO);
	// }
}
